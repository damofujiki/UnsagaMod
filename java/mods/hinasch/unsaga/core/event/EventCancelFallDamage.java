//package mods.hinasch.unsaga.core.event;
//
//import mods.hinasch.lib.debuff.DebuffHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.debuff.DebuffRegistry;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraftforge.event.entity.living.LivingFallEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class EventCancelFallDamage {
//	@SubscribeEvent
//	public void onLivingFall(LivingFallEvent e){
//		EntityLivingBase hurtEntity = e.getEntityLiving();
//		DebuffRegistry debuffs = UnsagaMod.debuffs;
//		if(DebuffHelper.hasDebuff(hurtEntity, debuffs.antiFallDamage)){
//			//hurtEntity.fallDistance = 0;
//			if(e.getDistance()<10.0D){
//				e.setCanceled(true);
//			}
//
////			DebuffHelper.removeDebuff(hurtEntity, debuffs.antiFallDamage);
//			return;
//		}
//		if(DebuffHelper.hasDebuff(hurtEntity, debuffs.gravity)){
//			e.setDamageMultiplier(e.getDamageMultiplier() * 2.0F);
//		}
//
//	}
//}
