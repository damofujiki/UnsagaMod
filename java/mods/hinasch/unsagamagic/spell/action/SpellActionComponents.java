package mods.hinasch.unsagamagic.spell.action;

import java.util.Random;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.common.specialaction.ActionList;
import mods.hinasch.unsaga.common.specialaction.ActionProjectile;
import mods.hinasch.unsaga.common.specialaction.ActionRangedAttack;
import mods.hinasch.unsaga.common.specialaction.ActionTargettable;
import mods.hinasch.unsaga.common.specialaction.ActionWorld;
import mods.hinasch.unsaga.common.specialaction.IActionPerformer.TargetType;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityBlaster;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityBubbleBlow;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import mods.hinasch.unsaga.core.net.packet.PacketClientScanner;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumParticleTypes;

public class SpellActionComponents {


	public static final IAction<SpellCaster> SHOCK_EFFECT = self ->{
		XYZPos casterPos = XYZPos.createFrom(self.getPerformer());
		if(self.getActionProperty()==SpellRegistry.instance().shock){
			self.playSound(casterPos, SoundEvents.BLOCK_GLASS_BREAK, true);
		}else{
			self.playSound(casterPos, SoundEvents.ENTITY_GENERIC_EXPLODE, true);
		}
		IBlockState block = self.getWorld().getBlockState(self.getPerformer().getPosition().down());
		EnumParticleTypes type = null;
		if(block.getBlock()==Blocks.AIR){
			type = EnumParticleTypes.CRIT;
		}else{
			type = EnumParticleTypes.BLOCK_DUST;
		}
		ParticleHelper.MovingType.WAVE.spawnParticle(self.getWorld(), casterPos, type, self.getWorld().rand, 10, 1.2D, Block.getStateId(block));
		return EnumActionResult.PASS;
	};

	public static SpellActionBase deadlyDrive(){
		UnsagaPotions up = UnsagaPotions.instance();
		SpellActionBase deadlyDriveBase = createRangedDebuffSpell(true,12.0D,null,MobEffects.SLOWNESS,MobEffects.WEAKNESS,up.downDex,up.downInt,up.downVit);
		deadlyDriveBase.addAction(new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster context) {
				context.playSound(XYZPos.createFrom(context.getPerformer()), SoundEvents.ENTITY_WITHER_SPAWN, false);
				return EnumActionResult.PASS;
			}
		}, 0);
		return deadlyDriveBase;
	}
	public static SpellActionBase detectGold(){
		SpellActionBase detectGoldBase = new SpellActionBase();
		detectGoldBase.addAction(new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster context) {
				if(context.getPerformer() instanceof EntityPlayer){
					if(WorldHelper.isServer(context.getWorld())){
						if(context.getAmplify()>=2.0F && context.getActionProperty()== SpellRegistry.instance().detectGold){
							UnsagaMod.packetDispatcher.sendTo(new PacketClientScanner(16,PacketClientScanner.Type.DETECT_GOLD_AMP), (EntityPlayerMP) context.getPerformer());
						}
						if(context.getAmplify()<2.0F && context.getActionProperty()== SpellRegistry.instance().detectGold){
							UnsagaMod.packetDispatcher.sendTo(new PacketClientScanner(16,PacketClientScanner.Type.DETECT_GOLD), (EntityPlayerMP) context.getPerformer());
						}
						if(context.getActionProperty()== SpellRegistry.instance().detectTreasure){
							UnsagaMod.packetDispatcher.sendTo(new PacketClientScanner(16,PacketClientScanner.Type.DETECT_TREASURE), (EntityPlayerMP) context.getPerformer());
						}


					}
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}}
		);
		return detectGoldBase;
	}
	public static SpellActionBase boulder(){
		SpellActionBase boulderBase = new SpellActionBase();
		boulderBase.addAction(new ActionProjectile<SpellCaster>().setProjectileFunction((in,target)->{
			EntityBoulder boulder = new EntityBoulder(in.getWorld(),in.getPerformer());
			boulder.setHeadingFromThrower(in.getPerformer(), in.getPerformer().rotationPitch, in.getPerformer().rotationYaw, 0.0F, 1.0F, 1.0F);
			boulder.setDamage(in.getEffectModifiedStrength().hp());
			return boulder;
		}).setShootSound(SoundEvents.ENTITY_GHAST_SHOOT));
		return boulderBase;
	}
	public static SpellActionBase bubbleBlow(){
		SpellActionBase bubbleBlowBase = new SpellActionBase();
		bubbleBlowBase.addAction(new ActionProjectile<SpellCaster>().setProjectileFunction((in,target)->{
			EntityBubbleBlow bubble = new EntityBubbleBlow(in.getWorld(),in.getPerformer());
			bubble.setHeadingFromThrower(in.getPerformer(), in.getPerformer().rotationPitch, in.getPerformer().rotationYaw, 0.0F, 1.0F, 1.0F);
			bubble.setDamage(in.getEffectModifiedStrength().hp());
			return bubble;
		}).setShootSound(SoundEvents.ENTITY_EGG_THROW));
		return bubbleBlowBase;
	}
	public static SpellActionBase blaster(){
		SpellActionBase blasterBase = new SpellActionBase();
		ActionList<SpellCaster> spellList = new ActionList();
		ActionProjectile<SpellCaster> actionBlaster = new ActionProjectile<SpellCaster>().setProjectileFunction((in,target)->{
			if(in.getTarget().isPresent()){
				EntityBlaster blaster = new EntityBlaster(in.getWorld(),in.getPerformer());
				XYZPos pos = XYZPos.createFrom(in.getPerformer());
				Random rand = in.getWorld().rand;
				blaster.setPosition(pos.dx + rand.nextFloat() - 0.5F,pos.dy+0.5F,pos.dz + rand.nextFloat() - 0.5F);
				blaster.setTarget(in.getTarget().get());
				blaster.setDamage(in.getEffectModifiedStrength().hp());
				return blaster;
			}
			return null;
		}).setShootSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT);

		spellList.addAction(actionBlaster);
		blasterBase.addAction(new ActionTargettable(spellList).setStrictType(TargetType.TARGET));
		return blasterBase;
	}

	public static SpellActionBase fireArrow(){
		SpellActionBase fireArrowBase = new SpellActionBase();
		fireArrowBase.addAction(new ActionProjectile<SpellCaster>().setProjectileFunction((in,target)->{
			if(in.getPerformer().isInWater()){
				return null;
			}
			EntityCustomArrow arrow = new EntityCustomArrow(in.getWorld(),in.getPerformer());
			arrow.setFire(65535);
			arrow.setArrowType(StateArrow.Type.MAGIC_ARROW);
			arrow.pickupStatus = PickupStatus.DISALLOWED;
			arrow.setAim(in.getPerformer(), in.getPerformer().rotationPitch, in.getPerformer().rotationYaw, 0.0F, 2.0F, 1.0F);
			arrow.setDamage(in.getEffectModifiedStrength().hp());
			if(in.getAmplify()>=1.5F){
				arrow.setIsCritical(true);
			}
			UnsagaMod.logger.trace("firearrow", arrow);
			return arrow;
		}).setShootSound(SoundEvents.ENTITY_GHAST_SHOOT));
		return fireArrowBase;
	}
	public static SpellActionBase overGrowth(){
		SpellActionBase overGrowthBase = new SpellActionBase();
		overGrowthBase.addAction(new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster t) {
				t.playSound(XYZPos.createFrom(t.getPerformer()), SoundEvents.ENTITY_PLAYER_LEVELUP, false);
				return EnumActionResult.PASS;
			}}).addAction(new ActionWorld<SpellCaster>(5,5)
				.setWorldConsumer((context,pos)->{
					Random rand = context.getWorld().rand;
					int trynum = (int)context.getAmplify();
					for(int i=0;i<trynum;i++){
						if(rand.nextFloat()<0.6F){
							ItemDye.applyBonemeal(new ItemStack(Items.DYE), context.getWorld(), pos, (EntityPlayer) context.getPerformer());
						}
					}
					return EnumActionResult.SUCCESS;
				})
		);
		return overGrowthBase;
	}
	public static SpellActionBase createBuffSpell(boolean isDebuff,Potion... potions){
		SpellActionBase base = createTargettableSpell(list ->{
			SpellActionStatusEffect buff = new SpellActionStatusEffect(isDebuff,potions);
			list.addAction(buff);
			return list;
		});
		return base;
	}
	public static SpellActionBase createTargettableSpell(UnaryOperator<ActionList> uo){
		SpellActionBase base = new SpellActionBase();
		ActionList<SpellCaster> targetted = new ActionList();
		targetted = uo.apply(targetted);
		base.addAction(new ActionTargettable<SpellCaster>(targetted));
		return base;
	}
	public static SpellActionBase createRangedDebuffSpell(boolean isDebuff,double range,@Nullable BiPredicate<RangedHelper<SpellCaster>,EntityLivingBase> selector,Potion... potions){
		SpellActionBase base = new SpellActionBase();
		ActionRangedAttack<SpellCaster> ranged = new ActionRangedAttack().setAttackFlag(false);
		ranged.setDebuffSetter(new SpellActionStatusEffect(isDebuff,potions)).setBoundingBoxFunction(in ->{
			return Lists.newArrayList(in.getPerformer().getEntityBoundingBox().expand(range, range, range));
		});
		if(selector==null){
			base.addAction(ranged);
		}else{
			base.addAction(ranged.setEntitySelector(selector));
		}

		return base;
	}
}
