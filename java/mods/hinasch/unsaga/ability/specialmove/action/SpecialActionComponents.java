package mods.hinasch.unsaga.ability.specialmove.action;

import java.util.EnumSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.particle.ParticleHelper.MovingType;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.util.VecUtil;
import mods.hinasch.lib.world.ScannerNew;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.ability.specialmove.action.AsyncSpecialMoveEvents.MovingAttack;
import mods.hinasch.unsaga.ability.specialmove.action.StatePropertySpecialMove.StateSpecialMove;
import mods.hinasch.unsaga.common.specialaction.ActionAsyncEvent;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.common.specialaction.ActionCharged;
import mods.hinasch.unsaga.common.specialaction.ActionList;
import mods.hinasch.unsaga.common.specialaction.ActionMelee;
import mods.hinasch.unsaga.common.specialaction.ActionProjectile;
import mods.hinasch.unsaga.common.specialaction.ActionRangedAttack;
import mods.hinasch.unsaga.common.specialaction.ActionRequireJump;
import mods.hinasch.unsaga.common.specialaction.ActionSelector;
import mods.hinasch.unsaga.common.specialaction.ActionTargettable;
import mods.hinasch.unsaga.common.specialaction.ActionWorld;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.entity.passive.EntityBeam;
import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
import mods.hinasch.unsaga.core.entity.projectile.EntityThrowingKnife;
import mods.hinasch.unsaga.core.net.packet.PacketClientThunder;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SpecialActionComponents {

	public static final IAction<SpecialMoveInvoker> SWINGSOUND_NOPARTICLE =  in ->{
		in.swingMainHand(true, false);
		return EnumActionResult.PASS;
	};
	public static final IAction<SpecialMoveInvoker> SWINGSOUND_AND_PARTICLE= in ->{
		in.swingMainHand(true, true);
		return EnumActionResult.PASS;
	};

	public static final IAction<SpecialMoveInvoker> STAFF_EFFECT = in ->{
		XYZPos pos = new XYZPos(in.getTargetCoordinate().get());
		in.playSound(pos, SoundEvents.ENTITY_GENERIC_EXPLODE, true);

		in.spawnParticle(MovingType.DIVERGE, pos, EnumParticleTypes.SMOKE_NORMAL, 20, 0.5D);
		return EnumActionResult.PASS;
	};
	public static final IAction<SpecialMoveInvoker> WAVE_PARTICLE = in ->{
		in.playSound(new XYZPos(in.getTargetCoordinate().get()), SoundEvents.ENTITY_GENERIC_EXPLODE, true);
		BlockPos pos = in.getTargetCoordinate().get();
		IBlockState state = in.getWorld().getBlockState(pos);
		Block block = state.getBlock();
		if(block!=Blocks.AIR){
			in.spawnParticle(MovingType.WAVE, new XYZPos(pos.up()), EnumParticleTypes.BLOCK_DUST, 10, 1.2D,Block.getStateId(state));
		}
		return EnumActionResult.PASS;
	};

	public static SpecialMoveBase acupuncture(){
		ActionCharged<ActionMelee> chargedAc = ActionCharged.simpleChargedMelee(General.SPEAR,General.PUNCH,General.SWORD).setChargeThreshold(30);
		chargedAc.getAction().setAdditionalBehavior((context,target)->{
			context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false);
			context.spawnParticle(MovingType.DIVERGE, target, EnumParticleTypes.PORTAL, 25,1.0D);
			target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().downDex,1,ItemUtil.getPotionTime(30)));
		});
		SpecialMoveBase base = SpecialMoveBase.create(InvokeType.CHARGE).addAction(chargedAc);
		return base;
	}
	public static SpecialMoveBase armOfLight(){
		SpecialMoveBase base = SpecialMoveBase.create(InvokeType.RIGHTCLICK);
		IAction<SpecialMoveInvoker> action = new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {


				if(t.getTarget().isPresent()){
					EntityBeam beam = new EntityBeam(t.getWorld(),t);
					XYZPos pos = XYZPos.createFrom(t.getPerformer());
					t.playSound(pos, SoundEvents.ENTITY_SHULKER_SHOOT, false);
					beam.setPositionAndRotation(pos.dx, pos.dy, pos.dz,t.getPerformer().rotationYaw,t.getPerformer().rotationPitch);
					beam.setOwner(t.getPerformer());
					beam.setTarget(t.getTarget().get());
					if(WorldHelper.isServer(t.getWorld())){
						WorldHelper.safeSpawn(t.getWorld(), beam);
					}
					return EnumActionResult.SUCCESS;
				}



				return EnumActionResult.FAIL;
			}
		};
		base.addAction(SWINGSOUND_NOPARTICLE);
		base.addAction(new ActionTargettable(action));
		return base;

	}
	public static SpecialMoveBase blitz(){
		SpecialMoveBase blitz = new SpecialMoveBase(InvokeType.RIGHTCLICK);
		blitz.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {
				t.playSound(XYZPos.createFrom(t.getPerformer()), SoundEvents.ENTITY_WITHER_SHOOT, false);
				Vec3d vec = t.getPerformer().getLookVec().normalize().scale(1.0D);
				DamageSourceUnsaga dsu = DamageSourceUnsaga.create(t.getPerformer(), t.getStrength().lp(), General.SPEAR);
				AsyncUpdateEvent ev = new MovingAttack(t.getPerformer(),OptionalDouble.of(vec.xCoord),OptionalDouble.empty(),OptionalDouble.of(vec.zCoord),4,2,dsu,t.getModifiedStrength().hp());
//				HSLib.core().events.scannerEventPool.addEvent(new MovingAttack(t.getPerformer(),OptionalDouble.of(vec.xCoord),OptionalDouble.empty(),OptionalDouble.of(vec.zCoord),150,2,dsu,t.getModifiedStrength().hp()));
				HSLib.core().addAsyncEvent(t.getPerformer(), ev);
				return EnumActionResult.SUCCESS;
			}}
				);
		return blitz;
	}
	public static SpecialMoveBase bloodyMary(){
		SpecialMoveBase base = new SpecialMoveBase(InvokeType.RIGHTCLICK);

		IAction<SpecialMoveInvoker> action = new ActionMelee(General.SPEAR)
				.setAdditionalBehavior((context,target)->{
					target.hurtResistantTime = 0;
					target.hurtTime = 0;
					target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().sleep,ItemUtil.getPotionTime(3),0));
					AsyncSpecialMoveEvents.ContinuousAttack event = new AsyncSpecialMoveEvents.ContinuousAttack(context.getPerformer(), 6,context);
					HSLib.core().addAsyncEvent(context.getPerformer(), event);
				});
		base.addAction(action);
		return base;
	}
	public static SpecialMoveBase chargeBlade(){
		SpecialMoveBase chargeBladeAction = new SpecialMoveBase(InvokeType.SPRINT_RIGHTCLICK);

		chargeBladeAction.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {
				if(t.getPerformer().isSprinting()){
					t.swingMainHand(true, true);
					DamageSourceUnsaga dsu = DamageSourceUnsaga.create(t.getPerformer(), t.getStrength().lp(), General.SWORD,General.PUNCH);
					RangedHelper.<SpecialMoveInvoker>create(t.getWorld(), t.getPerformer(), 4.0D).setConsumer((self,target)->{
						target.attackEntityFrom(dsu, t.getModifiedStrengthHP());
						VecUtil.knockback(t.getPerformer(), target, 1.0D, 0.5D);
						SoundAndSFX.playSound(t.getWorld(), XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
					}).invoke();
					AsyncUpdateEvent ev = new MovingAttack(t.getPerformer(),OptionalDouble.of(0),OptionalDouble.empty(),OptionalDouble.of(0),10,-1,null,0);

//					HSLib.core().events.scannerEventPool.addEvent(new MovingAttack(t.getPerformer(),OptionalDouble.of(0),OptionalDouble.empty(),OptionalDouble.of(0),500,-1,null,0));
					HSLib.core().addAsyncEvent(t.getPerformer(), ev);
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}}
				);
		return chargeBladeAction;
	}

	public static SpecialMoveBase firewoodChopper(){
		ActionMelee melee = new ActionMeleeWoodChop(General.PUNCH,General.SWORD);

		ActionWorld<SpecialMoveInvoker> actionWorld = new ActionWorld<SpecialMoveInvoker>(1, 1)
				.setWorldConsumer((self,pos)->{
					IBlockState state = self.getWorld().getBlockState(pos);
					if(state.getBlock().isWood(self.getWorld(), pos) ){
						SoundAndSFX.playBlockBreakSFX(self.getWorld(), pos, state,false);
						Stream.generate(()->new ItemStack(Items.STICK,1)).limit(9).forEach(in ->ItemUtil.dropAndFlyItem(self.getWorld(), in, new XYZPos(pos)));
						return EnumActionResult.SUCCESS;
					}
					return EnumActionResult.PASS;
				}
		);
		ActionSelector selector = new ActionSelector();
		selector.addAction(InvokeType.RIGHTCLICK, melee);
		selector.addAction(InvokeType.USE, actionWorld);
		ActionList actionList = new ActionList();
		actionList.addAction(SWINGSOUND_NOPARTICLE);
		actionList.addAction(selector);
		ActionRequireJump jump = new ActionRequireJump(actionList);
		SpecialMoveBase base = SpecialMoveBase.create(InvokeType.RIGHTCLICK,InvokeType.USE).addAction(jump);
		return base;
	}
	public static SpecialMoveBase earthDragon(){
		ActionRangedAttack<SpecialMoveInvoker> rangedEarthDragon = new ActionRangedAttack<SpecialMoveInvoker>(General.PUNCH)
				.setBoundingBoxFunction(in ->{
					BlockPos pos = in.getTargetCoordinate().get();
					return  Lists.newArrayList(HSLibs.getBounding(pos, 4.0D, 3.0D));
				}
						);
		rangedEarthDragon.setSubBehavior((self,target)->{
			VecUtil.knockback(self.getParent().getPerformer(), target, 1.0D, 0.2D);
		});
		rangedEarthDragon.setEntitySelector((self,target)->target.onGround);
		SpecialMoveBase earthDragonBase = new SpecialMoveBase(InvokeType.USE)
				.addAction(SWINGSOUND_NOPARTICLE)
				.addAction(WAVE_PARTICLE)
				.addAction(rangedEarthDragon);
		return earthDragonBase;
	}
	public static SpecialMoveBase fujiView(){
		SpecialMoveBase base = new SpecialMoveBase(InvokeType.CHARGE);
		ActionMelee action = new ActionMelee(General.PUNCH,General.SWORD).setAdditionalBehavior((self,target)->{
			if(self.getPerformer().onGround){
				return;
			}
			Vec3d lookVec = self.getPerformer().getLookVec().normalize().scale(0.6D);
			lookVec = lookVec.rotateYaw((float) Math.toRadians(180));
			self.getPerformer().addVelocity(lookVec.xCoord,0.3D, lookVec.zCoord);
			Random rand = self.getWorld().rand;
			XYZPos targetPos = XYZPos.createFrom(target);
			self.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_GENERIC_EXPLODE, false);

			target.addVelocity(0, 1.0D, 0);
			if(StatePropertySpecialMove.getState(target).isPresent()){
				StatePropertySpecialMove.getState(target).get().setFallParticle(true);
			}
			IBlockState down = self.getWorld().getBlockState(targetPos.down());
			if(down.getBlock()!=Blocks.AIR){
				int id = Block.getIdFromBlock(down.getBlock());
				self.spawnParticle(MovingType.FOUNTAIN,XYZPos.createFrom(target), EnumParticleTypes.BLOCK_DUST, 20, 0.1D,id);
			}
			self.spawnParticle(MovingType.FLOATING,target, EnumParticleTypes.VILLAGER_HAPPY, 20, 0.1D);
			self.spawnParticle(MovingType.FOUNTAIN,target, EnumParticleTypes.SMOKE_LARGE, 25, 0.05D);

			ScannerNew.create().base(target).range(1).ready().stream().forEach(pos ->{
				List<ItemStack> list = Lists.newArrayList();
				if(!self.getWorld().isAirBlock(pos)){
					IBlockState state = self.getWorld().getBlockState(pos);
					if(HSLibs.canBreakAndEffectiveBlock(self.getWorld(), null,"pickaxe", pos,new ItemStack(Items.IRON_PICKAXE))){

						list.addAll(state.getBlock().getDrops(self.getWorld(), pos, state, 0));
						WorldHelper.setAir(self.getWorld(), pos);
					}else{
						if(HSLibs.canBreakAndEffectiveBlock(self.getWorld(), null, "shovel", pos,new ItemStack(Items.IRON_SHOVEL))){

							list.addAll(state.getBlock().getDrops(self.getWorld(), pos, state, 0));
							WorldHelper.setAir(self.getWorld(), pos);
						}
					}

				}
				list.forEach(in ->ItemUtil.dropAndFlyItem(self.getWorld(), in,new XYZPos(pos)));
			});


		});
		base.addAction(SWINGSOUND_NOPARTICLE);
		base.addAction(new ActionCharged(action));
		return base;
	}
	public static SpecialMoveBase gonger(){
		SpecialMoveBase base = new SpecialMoveBase(InvokeType.RIGHTCLICK,InvokeType.USE);
		ActionMelee melee = new ActionMelee(General.PUNCH);
		melee.setAdditionalBehavior((context,target)->{
			context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false);
		});
		ActionList listAction = new ActionList();
		listAction.addAction(SpecialActionComponents.WAVE_PARTICLE);
		listAction.addAction(new ActionWorld.AirRoomDetector());
		base.addAction(new ActionSelector()
				.addAction(InvokeType.RIGHTCLICK, melee)
				.addAction(InvokeType.USE, listAction));
		return base;
	}
	public static SpecialMoveBase grandslam(){
		ActionRangedAttack<SpecialMoveInvoker> rangedGrandSlam = new ActionRangedAttack<SpecialMoveInvoker>(General.PUNCH)
				.setBoundingBoxFunction(in ->{
					BlockPos pos = in.getTargetCoordinate().get();
					return  Lists.newArrayList(HSLibs.getBounding(pos, 9.0D, 5.0D));
				}
						);
		rangedGrandSlam.setAttackFlag(false);
		rangedGrandSlam.setSubBehavior((self,target)->{
			if(EntityStateCapability.adapter.hasCapability(target)){
				StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(target).getState(StateRegistry.instance().stateSpecialMove);
				state.setScheduledExplode(15);
			}
			self.getParent().spawnParticle(MovingType.FLOATING, target, EnumParticleTypes.EXPLOSION_NORMAL, 3, 0.2D);
			VecUtil.knockback(self.getParent().getPerformer(), target, 5.0D, 1.0D);
		});
		rangedGrandSlam.setEntitySelector((self,target)->target.onGround);
		SpecialMoveBase grandSlamBase = new SpecialMoveBase(InvokeType.USE)
				.addAction(SWINGSOUND_NOPARTICLE)
				.addAction(WAVE_PARTICLE)
				.addAction(rangedGrandSlam);
		return grandSlamBase;
	}
	public static SpecialMoveBase grassHopper(){
		SpecialMoveBase grassHopperAction = new SpecialMoveBase(InvokeType.RIGHTCLICK,InvokeType.USE);
		ActionRangedAttack<SpecialMoveInvoker> rangedGrassHopper = new ActionRangedAttack(General.SWORD,General.SPEAR);
		rangedGrassHopper.setBoundingBoxFunction(new RangedBoundingBoxMakerGroup.RangeSwing(4));
		rangedGrassHopper.setEntitySelector((self,target)->target.onGround);
		rangedGrassHopper.setSubBehavior((self,target)->VecUtil.knockback(self.getParent().getPerformer(), target, 0.5D, 0.3D));
		ActionWorld weedCutter = new ActionWorld(2,1);
		weedCutter.setWorldConsumer(new ActionWorld.WeedCutter());
		grassHopperAction.addAction(SWINGSOUND_AND_PARTICLE);
		grassHopperAction.addAction(new ActionSelector()
				.addAction(InvokeType.RIGHTCLICK, rangedGrassHopper)
				.addAction(InvokeType.USE, weedCutter));
		return grassHopperAction;
	}

	public static SpecialMoveBase gust(){
		SpecialMoveBase gustBase = new SpecialMoveBase(InvokeType.CHARGE);
		gustBase.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {
				t.playSound(XYZPos.createFrom(t.getPerformer()), SoundEvents.ENTITY_WITHER_SHOOT, false);
				Vec3d vec = t.getPerformer().getLookVec().normalize().scale(0.8D);
				DamageSourceUnsaga dsu = DamageSourceUnsaga.create(t.getPerformer(), t.getStrength().lp(), General.SWORD);
				AsyncUpdateEvent ev = new MovingAttack(t.getPerformer(),OptionalDouble.of(vec.xCoord),OptionalDouble.empty(),OptionalDouble.of(vec.zCoord),10,2,dsu,t.getModifiedStrength().hp()).setPotions(UnsagaPotions.instance().downDex).setConsumer(in ->{
					ScannerNew.create().base(in.getSender()).range(1).ready()
					.stream().forEach(pos ->{
						IBlockState state = in.world.getBlockState(pos);
						if(state.getBlock() instanceof BlockLeaves || state.getBlock() instanceof BlockTallGrass || state.getBlock() instanceof BlockWeb){
							SoundAndSFX.playBlockBreakSFX(in.world, pos, state);
						}

					});
				});
				HSLib.core().addAsyncEvent(t.getPerformer(), ev);
				return EnumActionResult.SUCCESS;
			}}
				);
		return gustBase;
	}
	public static SpecialMoveBase hawkBlade(){
		SpecialMoveBase hawkBlade = new SpecialMoveBase(InvokeType.RIGHTCLICK);
		hawkBlade.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {
				if(!t.getPerformer().onGround){
					t.playSound(XYZPos.createFrom(t.getPerformer()), SoundEvents.ENTITY_WITHER_SHOOT, false);
					Vec3d vec = t.getPerformer().getLookVec().normalize().scale(0.3D);
					DamageSourceUnsaga dsu = DamageSourceUnsaga.create(t.getPerformer(), t.getStrength().lp(), General.SWORD);
					AsyncUpdateEvent ev = new MovingAttack(t.getPerformer(),OptionalDouble.of(vec.xCoord),OptionalDouble.of(0.1D),OptionalDouble.of(vec.zCoord),15,3,dsu,t.getModifiedStrength().hp()).setCancelFall(true);
					HSLib.core().addAsyncEvent(t.getPerformer(), ev);
//					HSLib.core().events.scannerEventPool.addEvent(new MovingAttack(t.getPerformer(),OptionalDouble.of(vec.xCoord),OptionalDouble.of(0.002D),OptionalDouble.of(vec.zCoord),400,3,dsu,t.getModifiedStrength().hp()).setCancelFall(true));
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}}
				);
		return hawkBlade;
	}
	public static SpecialMoveBase knifeThrow(){
		SpecialMoveBase knifeThrowBase = SpecialMoveBase.create(InvokeType.CHARGE);

		ActionProjectile<SpecialMoveInvoker> projectileKnife = new ActionProjectile<SpecialMoveInvoker>().setProjectileFunction((context,target)->{
			EntityThrowingKnife knife = new EntityThrowingKnife(context.getWorld(),context.getPerformer(),context.getArticle().get().copy());
			knife.setDamage(context.getModifiedStrength().hp());
			knife.setLPDamage(context.getModifiedStrength().lp());
			knife.setTarget(context.getPerformer());
			context.getPerformer().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
			XYZPos pos = XYZPos.createFrom(context.getPerformer());
			knife.setHeadingFromThrower(context.getPerformer(), context.getPerformer().rotationPitch, context.getPerformer().rotationYaw, 0, 2.0F, 1.0F);
			return knife;
		}).setShootSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
		knifeThrowBase.addAction(new ActionCharged(projectileKnife).setChargeThreshold(15));
		return knifeThrowBase;
	}
	public static SpecialMoveBase lightningThrust(){
		SpecialMoveBase lightningThrustBase = new SpecialMoveBase(InvokeType.RIGHTCLICK);
		ActionMelee meleeLightningThrust = new ActionMelee(General.SPEAR)
				.setAdditionalBehavior((context,target)->{
					target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().sleep,ItemUtil.getPotionTime(30),0));
					UnsagaMod.packetDispatcher.sendToAllAround(PacketClientThunder.create(XYZPos.createFrom(target)), PacketUtil.getTargetPointNear(target));
					context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_LIGHTNING_THUNDER, false);
				}).setSubAttributes(EnumSet.of(Sub.ELECTRIC));
		lightningThrustBase.addAction(meleeLightningThrust);
		return lightningThrustBase;
	}
	public static SpecialMoveBase pulverizer(){
		SpecialMoveBase pulverizerBase = new SpecialMoveBase(InvokeType.RIGHTCLICK,InvokeType.USE);
		ActionMelee meleePulverizer = new ActionMelee(General.PUNCH);
		meleePulverizer.setAdditionalBehavior((context,target)->{
			context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false);
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,ItemUtil.getPotionTime(30),0));
		});
		ActionList mainPulverizer = new ActionList();
		mainPulverizer.addAction(STAFF_EFFECT);
		mainPulverizer.addAction(new ActionAsyncEvent().setEventFactory(new AsyncSpecialMoveEvents.CrashConnected("pickaxe")));
		pulverizerBase.addAction(new ActionSelector()
				.addAction(InvokeType.RIGHTCLICK, meleePulverizer)
				.addAction(InvokeType.USE, mainPulverizer));
		return pulverizerBase;
	}
	public static SpecialMoveBase quickChecker(){
		SpecialMoveBase quickCheckerBase = new SpecialMoveBase(InvokeType.RIGHTCLICK);
		quickCheckerBase.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker t) {
				if(t.getArticle().isPresent() && t.getArticle().get().getItem() instanceof ItemBow){
					ItemBow bow = (ItemBow) t.getArticle().get().getItem();
					bow.onPlayerStoppedUsing(t.getArticle().get(), t.getWorld(), t.getPerformer(), 71400);
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}}
				);
		return quickCheckerBase;
	}
	public static SpecialMoveBase rockCrasher(){
		SpecialMoveBase rockCrasherBase = new SpecialMoveBase(InvokeType.RIGHTCLICK,InvokeType.USE);
		ActionMelee meleeRockCrasher = new ActionMelee(General.PUNCH);
		meleeRockCrasher.setAdditionalBehavior((context,target)->{
			context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false);
			target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().downVit,ItemUtil.getPotionTime(30),0));
		});
		ActionList mainRockCrasher = new ActionList();
		mainRockCrasher.addAction(STAFF_EFFECT);
		mainRockCrasher.addAction(new ActionAsyncEvent().setEventFactory(new AsyncSpecialMoveEvents.Pulverizer()));
		rockCrasherBase.addAction(new ActionSelector()
				.addAction(InvokeType.RIGHTCLICK, meleeRockCrasher)
				.addAction(InvokeType.USE, mainRockCrasher));
		return rockCrasherBase;
	}
	public static SpecialMoveBase skullCrasher(){
		SpecialMoveBase skullCrashBase = new SpecialMoveBase(InvokeType.RIGHTCLICK)
				.addAction(new ActionMelee(General.PUNCH){

					@Override
					public float getDamage(SpecialMoveInvoker context, EntityLivingBase target,float base) {

						if(target instanceof EntitySkeleton){
							return base *1.5F;
						}

						return base;
					}
				}
				.setAdditionalBehavior((context,target)->{
					context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false);
					target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,ItemUtil.getPotionTime(30),0));
				})
						);
		return skullCrashBase;
	}
	public static SpecialMoveBase skyDrive(){
		SpecialMoveBase skyDriveBase = SpecialMoveBase.create(InvokeType.CHARGE);
		ActionProjectile<SpecialMoveInvoker> projectileSkyDrive = new ActionProjectile<SpecialMoveInvoker>().setProjectileFunction((context,target)->{
			if(context.getTarget().isPresent()){
				EntityFlyingAxe axe = new EntityFlyingAxe(context.getWorld(),context.getPerformer(),context.getArticle().get().copy());
				axe.setDamage(context.getModifiedStrength().hp());
				axe.setLPDamage(context.getModifiedStrength().lp());
				axe.setTarget(context.getTarget().get());
				axe.setSkyDrive(true);
				context.getPerformer().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
				return axe;
			}
			return null;
		}).setShootSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
		skyDriveBase.addAction(new ActionCharged(new ActionTargettable(projectileSkyDrive)).setChargeThreshold(20));
		return skyDriveBase;
	}
	public static SpecialMoveBase stunner(){
		SpecialMoveBase stunnerBase = new SpecialMoveBase(InvokeType.RIGHTCLICK)
				.addAction(new ActionMelee(General.SPEAR)
						.setAdditionalBehavior((context,target)->{
							context.spawnParticle(MovingType.DIVERGE, target, EnumParticleTypes.CRIT_MAGIC, 10, 1D);
							target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().sleep,ItemUtil.getPotionTime(10),0));
						})
						);
		return stunnerBase;
	}
	public static SpecialMoveBase swing(){
		SpecialMoveBase swingBase = new SpecialMoveBase(InvokeType.RIGHTCLICK)
				.addAction(SWINGSOUND_AND_PARTICLE)
				.addAction(new ActionRangedAttack<SpecialMoveInvoker>(General.SWORD,General.SPEAR)
						.setBoundingBoxFunction(new RangedBoundingBoxMakerGroup.RangeSwing(4)));
		return swingBase;
	}
	public static SpecialMoveBase tomahawk(){
		SpecialMoveBase tomahawkBase = SpecialMoveBase.create(InvokeType.CHARGE);
		ActionProjectile<SpecialMoveInvoker> projectileTomahawk = new ActionProjectile<SpecialMoveInvoker>().setProjectileFunction((context,target)->{
			EntityFlyingAxe axe = new EntityFlyingAxe(context.getWorld(),context.getPerformer(),context.getArticle().get().copy());
			axe.setDamage(context.getModifiedStrengthHP());
			context.getPerformer().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
			XYZPos pos = XYZPos.createFrom(context.getPerformer());
			axe.setHeadingFromThrower(context.getPerformer(), context.getPerformer().rotationPitch, context.getPerformer().rotationYaw, 0, 2.0F, 1.0F);

			return axe;
		}).setShootSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
		tomahawkBase.addAction(new ActionCharged(projectileTomahawk).setChargeThreshold(20));
		return tomahawkBase;
	}
	public static SpecialMoveBase yoyo(){
		SpecialMoveBase base = SpecialMoveBase.create(InvokeType.CHARGE);
		ActionProjectile<SpecialMoveInvoker> action = new ActionProjectile<SpecialMoveInvoker>().setProjectileFunction((context,target)->{
			EntityFlyingAxe axe = new EntityFlyingAxe(context.getWorld(),context.getPerformer(),context.getArticle().get().copy());
			axe.setDamage(context.getModifiedStrength().hp());
			axe.setLPDamage(context.getModifiedStrength().lp());
			context.getPerformer().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, null);
			XYZPos pos = XYZPos.createFrom(context.getPerformer());
			axe.setHeadingFromThrower(context.getPerformer(), context.getPerformer().rotationPitch, context.getPerformer().rotationYaw, 0, 2.0F, 1.0F);
			axe.setYoYo(true);
			axe.setTarget(context.getPerformer());
			return axe;
		}).setShootSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP);
		base.addAction(new ActionCharged(action).setChargeThreshold(20));
		return base;
	}
	public static SpecialMoveBase vandalize(){
		SpecialMoveBase vandalizeBase = SpecialMoveBase.create(InvokeType.CHARGE);
		ActionMelee meleeVandalize = new ActionMelee(General.SWORD).setAdditionalBehavior((context,target)->{
			XYZPos pos = XYZPos.createFrom(target);
			context.getWorld().createExplosion(context.getPerformer(), pos.dx, pos.dy, pos.dz, 3, false);
		});
		vandalizeBase.addAction(new ActionCharged(meleeVandalize).setChargeThreshold(25));
		return vandalizeBase;
	}


	public static SpecialMoveBase woodChopper(){
		SpecialMoveBase woodChopperAction = new SpecialMoveBase(InvokeType.RIGHTCLICK,InvokeType.USE);
		ActionMelee meleeWoodChopper = new ActionMeleeWoodChop(General.PUNCH,General.SWORD);
		meleeWoodChopper.setAdditionalBehavior((context,target)->	context.playSound(XYZPos.createFrom(target), SoundEvents.ENTITY_IRONGOLEM_HURT, false));
		ActionAsyncEvent asyncWoodChopper = new ActionAsyncEvent();
		asyncWoodChopper.setEventFactory(new AsyncSpecialMoveEvents.CrashConnected("axe"));
		woodChopperAction.addAction(new ActionSelector().addAction(InvokeType.RIGHTCLICK, meleeWoodChopper)
				.addAction(InvokeType.USE, asyncWoodChopper));
		return woodChopperAction;
	}

	public static class ActionMeleeWoodChop extends ActionMelee{
		public ActionMeleeWoodChop(General... attributes) {
			super(attributes);
		}
		@Override
		public float getDamage(SpecialMoveInvoker context,EntityLivingBase target, float base) {
			if(target.getCreatureAttribute()==EntityRuffleTree.PLANT){
				return base * 1.5F;
			}
			return base;
		}
	}
}
