//package mods.hinasch.unsaga.core.event;
//
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.ability.waza.WazaAttacher;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.monster.EntityMob;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraftforge.event.entity.living.LivingAttackEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class EventLearningChance {
//
//	@SubscribeEvent
//	public void onPlayerAttack(LivingAttackEvent ev){
//		Entity attacker = ev.getSource().getEntity();
//		EntityLivingBase entityHurt = ev.getEntityLiving();
//
//		if(ev.getSource()!=null && attacker instanceof EntityPlayer){
//			if(entityHurt instanceof EntityMob){
//				EntityPlayer ep = (EntityPlayer)attacker;
//				ItemStack weapon = ep.getHeldItemMainhand();
//
//				if(weapon!=null && AbilityHelper.hasCapability(weapon)){
//					WazaAttacher helper = new WazaAttacher(ep,weapon,AbilityHelper.getCapability(weapon));
//					helper.drawChanceToGainAbility(ep.getRNG(),entityHurt);
//
//
//				}
//			}
//
//		}
//	}
//}
