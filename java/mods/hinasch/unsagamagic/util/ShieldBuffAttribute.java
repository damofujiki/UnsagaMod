//package mods.hinasch.unsagamagic.util;
//
//import mods.hinasch.lib.debuff.DebuffHelper;
//import mods.hinasch.lib.network.PacketSound;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.network.SoundPacket;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.debuff.Buff;
//import mods.hinasch.unsaga.debuff.effect.LivingBuff;
//import mods.hinasch.unsaga.element.FiveElements;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.util.math.MathHelper;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
//
//public abstract class ShieldBuffAttribute {
//
//	public final boolean isGuardAll;
//	protected final Buff parentBuff;
//	public final FiveElements.Type element;
////	protected PacketSound ps;
//
//	public ShieldBuffAttribute(Buff parent,boolean isGuardAll,FiveElements.Type element){
//		this.parentBuff = parent;
//		this.isGuardAll = isGuardAll;
//		this.element = element;
//	}
//
//	public DamageSourceUnsaga checkShield(LivingHurtEvent e,DamageSourceUnsaga dsu){
//		EntityLivingBase hurtLiving = e.getEntityLiving();
//		if(this.isEffective(e,dsu)){
//			if(DebuffHelper.hasDebuff(hurtLiving, parentBuff)){
//				if(DebuffHelper.getLivingDebuff(hurtLiving, parentBuff).isPresent()){
//					this.processGuard(hurtLiving,e,dsu);
//				}
//			}
//		}
//
//
//		return dsu;
//	}
//
//	public void processGuard(EntityLivingBase hurtLiving, LivingHurtEvent e,DamageSourceUnsaga dsu){
//		LivingBuff shield = (LivingBuff)DebuffHelper.getLivingDebuff(hurtLiving, parentBuff).get();
//		int prob = (int)(shield.getAmp()*30.0F);
//		prob = MathHelper.clamp_int(prob, 30, 100);
//		if((!this.isGuardAll && hurtLiving.getRNG().nextInt(100)<prob) || this.isGuardAll){
//			e.setAmount(0);
//			dsu.setStrLPHurt(0.1F);
//
//
//			TargetPoint tp = PacketUtil.getTargetPointNear(e.getEntityLiving());
//			SoundPacket.sendToAllAround(PacketSound.atEntity(SoundEvents.ITEM_SHIELD_BLOCK,hurtLiving), tp);
//		}
//	}
//
//	abstract public boolean isEffective(LivingHurtEvent e,DamageSourceUnsaga dsu);
//}
