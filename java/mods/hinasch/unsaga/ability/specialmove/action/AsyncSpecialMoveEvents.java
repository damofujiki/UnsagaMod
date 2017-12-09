package mods.hinasch.unsaga.ability.specialmove.action;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import mods.hinasch.lib.sync.SafeUpdateEventByInterval;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.world.ScannerNew;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.action.StatePropertySpecialMove.StateSpecialMove;
import mods.hinasch.unsaga.common.specialaction.ActionAsyncEvent.AsyncEventFactory;
import mods.hinasch.unsaga.common.specialaction.ISimpleMelee;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import mods.hinasch.unsaga.util.AsyncConnectedBlocksBreaker;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AsyncSpecialMoveEvents {
	public static class CrashConnected implements AsyncEventFactory<SpecialMoveInvoker>{

		Set<Block> blackList = Sets.newHashSet(Blocks.COBBLESTONE,Blocks.STONE,Blocks.SANDSTONE);
		String toolClass;
		public CrashConnected(String clazz){
			this.toolClass = clazz;
		}
		@Override
		public AsyncUpdateEvent apply(SpecialMoveInvoker t) {
			if(WorldHelper.isClient(t.getWorld())){
				return null;
			}
			IBlockState state = t.getWorld().getBlockState(t.getTargetCoordinate().get());
			if(HSLibs.canBreakAndEffectiveBlock(t.getWorld(), t.getPerformer(), toolClass, t.getTargetCoordinate().get())){
				if(!blackList.contains(state.getBlock())){
					AsyncConnectedBlocksBreaker scannerBreak = new AsyncConnectedBlocksBreaker(t.getWorld(),5,Sets.newHashSet(state), t.getTargetCoordinate().get(), t.getPerformer());
					return scannerBreak;
				}

			}
			return null;
		}

	}


	public static class GrandSlam implements AsyncEventFactory<SpecialMoveInvoker>{

		@Override
		public AsyncUpdateEvent apply(SpecialMoveInvoker context) {
			boolean heavy = context.getArticle().isPresent() && UnsagaMaterialCapability.adapter.hasCapability(context.getArticle().get()) && UnsagaMaterialCapability.adapter.getCapability(context.getArticle().get()).getWeight()>10;
			List<BlockPos> expos = Lists.newArrayList();
			AxisAlignedBB bb = context.getPerformer().getEntityBoundingBox().expand(8.0D, 3.0D, 8.0D);
			RangedHelper<SpecialMoveInvoker> helper = RangedHelper.create(context.getWorld(), context.getPerformer(),Lists.newArrayList(bb));
			helper.setSelector((self,target)->target.onGround).setConsumer((self,target)->{
				target.addVelocity(0, 1.0D, 0);
				expos.add(target.getPosition());
			}).invoke();
			if(!expos.isEmpty()){
				return new AsyncGrandslam(context.getWorld(), context.getModifiedStrengthHP(), expos, heavy, context.getPerformer());
			}
			return null;

		}

	}
	public static class Pulverizer implements AsyncEventFactory<SpecialMoveInvoker>{

		@Override
		public AsyncUpdateEvent apply(SpecialMoveInvoker t) {
			if(WorldHelper.isClient(t.getWorld())){
				return null;
			}
			return new ScannerPulverizer(t.getWorld(), t.getPerformer(),ScannerNew.create().base(t.getTargetCoordinate().get()).range(1).ready());
		}

	}

	public static class ArrowKnock extends SafeUpdateEventByInterval{


		Queue<EntityArrow> arrows;

		boolean first = false;

		public ArrowKnock(EntityLivingBase sender, List<EntityArrow> arrows) {
			super(sender, "arrowknock");
			this.forceFirstRun = true;
			this.arrows = new ArrayBlockingQueue(5);
			for(EntityArrow arrow:arrows){
				this.arrows.offer(arrow);
			}
		}

		@Override
		public int getIntervalThresold() {
			// TODO 自動生成されたメソッド・スタブ
			return 3000;
		}

		@Override
		public void loopByInterval() {
			UnsagaMod.logger.trace(this.getClass().getName(), "called");

			EntityArrow arrow = this.arrows.poll();
			if(arrow!=null){
				first = true;
				HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.ENTITY_ARROW_SHOOT, sender), (EntityPlayerMP) sender);
				WorldHelper.safeSpawn(sender.getEntityWorld(), arrow);
			}

		}

		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return arrows.isEmpty();
		}

	}
	public static class ScannerPulverizer extends SafeUpdateEventByInterval{

		static final String ID = "pulverizer";
		ScannerNew scanner;
		final World world;
		final EntityLivingBase owner;
		Iterator<BlockPos> iterator;

		public ScannerPulverizer(World world,EntityLivingBase owner,ScannerNew scanner) {
			super(owner,ID);
			this.world = world;
			this.scanner = scanner;
			this.owner = owner;
			this.iterator = this.scanner.getIterableInstance().iterator();
		}

		@Override
		public int getIntervalThresold() {
			// TODO 自動生成されたメソッド・スタブ
			return 0;
		}

		@Override
		public void loopByInterval() {

			BlockPos pos = this.iterator.next();
			UnsagaMod.logger.trace("loop", pos);
//			if(this.iterator.hasNext()){
				boolean flag = HSLibs.canBreakAndEffectiveBlock(world,owner,"pickaxe",pos);

				if(flag){
					IBlockState id = world.getBlockState(pos);
					id.getBlock().dropXpOnBlockBreak(world, pos, id.getBlock().getExpDrop(id, world, pos, 0));
					SoundAndSFX.playBlockBreakSFX(world, pos, id);

				}
//			}


		}

		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return !this.iterator.hasNext();
		}

	}

	public static class ContinuousAttack extends SafeUpdateEventByInterval implements ISimpleMelee<SpecialMoveInvoker>{

		final int expire;
		int time = 0;
		World world;
		SpecialMoveInvoker invoker;
		public ContinuousAttack(EntityLivingBase sender,int expire,SpecialMoveInvoker invoker) {
			super(sender, "continuousAttack");
			this.expire = expire;
			this.world = sender.getEntityWorld();
			this.invoker = invoker;
		}

		@Override
		public int getIntervalThresold() {
			// TODO 自動生成されたメソッド・スタブ
			return 2;
		}

		@Override
		public void loopByInterval() {

			SoundAndSFX.playSound(world, XYZPos.createFrom(sender), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

			sender.swingArm(EnumHand.MAIN_HAND);
			this.performSimpleAttack(invoker);
			time ++;
		}

		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return expire<time;
		}

		@Override
		public EnumSet<General> getAttributes() {
			// TODO 自動生成されたメソッド・スタブ
			return EnumSet.of(General.SPEAR);
		}

		@Override
		public EnumSet<Sub> getSubAttributes() {
			// TODO 自動生成されたメソッド・スタブ
			return EnumSet.noneOf(Sub.class);
		}

		@Override
		public BiConsumer<SpecialMoveInvoker, EntityLivingBase> getAdditionalBehavior() {
			// TODO 自動生成されたメソッド・スタブ
			return (context,target)->{
				ParticleHelper.MovingType.FOUNTAIN.spawnParticle(world, new XYZPos(target.getPosition().up()), EnumParticleTypes.REDSTONE, world.rand, 10, 0.1D,new int[0]);
				target.hurtResistantTime = 0;
				target.hurtTime = 0;
			};
		}

		@Override
		public float getDamage(SpecialMoveInvoker context,EntityLivingBase target, float base) {
			return invoker.getModifiedStrength().hp();
		}

		@Override
		public float getReach(SpecialMoveInvoker context) {
			// TODO 自動生成されたメソッド・スタブ
			return context.getReach() ;
		}

	}
	public static class MovingAttack extends SafeUpdateEventByInterval{

		final int expire;
		int time =0;
		OptionalDouble x;
		OptionalDouble y;
		OptionalDouble z;
		final double range;
		final World world;
		final DamageSourceUnsaga dsu;
		final double damage;
		boolean attacked = false;
		boolean isStopOnHit = false;
		ImmutableSet<Potion> potions = ImmutableSet.of();
		boolean isCancelFall = false;
		@Nullable Consumer<MovingAttack> consumer;

		public MovingAttack(EntityLivingBase sender,OptionalDouble x,OptionalDouble y,OptionalDouble z,int expire,double range,DamageSourceUnsaga dsu,double damage) {
			super(sender, "movingAttack");
			if(EntityStateCapability.adapter.hasCapability(sender)){
				StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(sender).getState(StateRegistry.instance().stateSpecialMove);
				state.setCancelHurt(65535);
				if(isCancelFall){
					state.setCancelFall(true);
				}
			}
			this.world = sender.getEntityWorld();
			this.x = x;
			this.y = y;
			this.z = z;
			this.expire = expire;
			this.range = range;
			this.dsu = dsu;
			this.damage = damage;

			// TODO 自動生成されたコンストラクター・スタブ
		}

		public MovingAttack setConsumer(Consumer<MovingAttack> c){
			this.consumer = c;
			return this;
		}
		@Override
		public int getIntervalThresold() {
			// TODO 自動生成されたメソッド・スタブ
			return 1;
		}

		public MovingAttack setCancelFall(boolean par1){
			this.isCancelFall = par1;
			return this;
		}
		public MovingAttack setStopOnHit(boolean par1){
			this.isStopOnHit = par1;
			return this;
		}
		public MovingAttack setPotions(Potion... potions){
			this.potions = ImmutableSet.copyOf(potions);
			return this;
		}
		@Override
		public void loopByInterval() {


			if(this.isCancelFall){
				this.sender.fallDistance = 0;
			}
			if(x.isPresent()){
				if(x.getAsDouble()==0 && z.getAsDouble()==0){
					sender.setVelocity(0, sender.motionY, 0);
				}else{
					sender.addVelocity(x.getAsDouble(), 0, 0);
				}
//				sender.motionX += x.getAsDouble();

			}
			if(y.isPresent()){
//				sender.motionY += y.getAsDouble();
				sender.addVelocity(0,y.getAsDouble(), 0);
			}
			if(z.isPresent()){
//				sender.motionZ += z.getAsDouble();
				sender.addVelocity(0,0,z.getAsDouble());
			}

			if(this.consumer!=null){
				this.consumer.accept(this);
			}

			if(this.sender.onGround){
				IBlockState state = world.getBlockState(sender.getPosition().down());
				if(state.getBlock()!=Blocks.AIR){
					ParticleHelper.MovingType.FOUNTAIN.spawnParticle(world, XYZPos.createFrom(sender), EnumParticleTypes.BLOCK_DUST, sender.getRNG(), 10, 0.1D, Block.getIdFromBlock(state.getBlock()));
				}

			}
			if(range>0 && !this.attacked){


				RangedHelper.<SpecialMoveInvoker>create(world,sender, range).setConsumer((self,target)->{
					target.attackEntityFrom(dsu, (float) damage);
					if(this.isStopOnHit){
						this.time = 65535;
					}
					if(!this.potions.isEmpty()){
						this.potions.forEach(in ->{
							target.addPotionEffect(new PotionEffect(in,ItemUtil.getPotionTime(20),1));
						});
					}
//					SoundAndSFX.playSound(world, XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
				}).invoke();
//				attacked = true;
			}

			time ++;
			UnsagaMod.logger.trace("time", time);
		}

		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return time>expire;
		}

		@Override
		public void onExpire(){
			if(EntityStateCapability.adapter.hasCapability(sender)){
				StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(sender).getState(StateRegistry.instance().stateSpecialMove);
				state.setCancelHurt(0);
				state.setCancelFall(false);
			}
		}
	}
}
