//package mods.hinasch.unsaga.core.event;
//
//import java.util.Arrays;
//
//import mods.hinasch.lib.item.ItemUtil;
//import mods.hinasch.lib.world.WorldHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.AbilityAttacher;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.util.AccessoryHelper;
//import mods.hinasch.unsaga.util.AccessoryHelper.IAccessorySlot;
//import mods.hinasch.unsagamagic.UnsagaMagic;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.event.entity.living.LivingDeathEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class EventLivingDeath {
//
//	//protected WorldHelper worldHelper;
//	protected UnsagaMagic magic = UnsagaMod.magic;
//
//	@SubscribeEvent
//	public void onLivingDeath(LivingDeathEvent e){
//		EntityLivingBase entityDeath = e.getEntityLiving();
//		Entity attacker = e.getSource().getEntity();
//
//
//		//プレイヤーが死亡
//		if(entityDeath instanceof EntityPlayer){
//			this.dropAccessoriesOnDeath(e);
//		}
//
//		//プレイヤーがモブを倒した時
//		if(attacker!=null){
//
//			this.onPlayerKilledMob(attacker, e);
//		}
//	}
//	public void dropAccessoriesOnDeath(LivingDeathEvent e) {
//		if(!(e.getEntityLiving() instanceof EntityPlayer))return;
//		EntityPlayer ep = (EntityPlayer)e.getEntityLiving();
//		if(AccessoryHelper.hasCapability(ep)){
//			IAccessorySlot data = AccessoryHelper.getCapability(ep);
//			data.getAccessoryList().forEach(acs -> ItemUtil.dropItem(acs, ep));
////			ListHelper.stream(Lists.newArrayList(data.getAccessories())).forEach(ItemUtil.getItemDropConsumer(),ep);
//			//			ItemUtil.dropItem(data.getTablet(), ep);
//		}
//
//
//	}
//	public void onPlayerKilledMob(Entity attacker,LivingDeathEvent e){
//
//		if(attacker instanceof EntityPlayer){
//			EntityLivingBase enemy = e.getEntityLiving();
//			EntityPlayer ep = (EntityPlayer) attacker;
//
//			this.learnAbilityOnPlayerKilledMob(e, enemy);
//
//		}
//	}
//
//	public void learnAbilityOnPlayerKilledMob(LivingDeathEvent e,EntityLivingBase enemy){
//		//		Unsaga.debug("呼ばれてる");
//		EntityPlayer ep = (EntityPlayer)e.getSource().getEntity();
//
//
//		if(AccessoryHelper.hasCapability(ep)){
//			IAccessorySlot pdata = AccessoryHelper.getCapability(ep);
//			pdata.getAccessoryList().forEach(is ->{
//				if(AbilityHelper.hasCapability(is)){
//					AbilityAttacher helper = (AbilityAttacher) AbilityHelper.createAttacherFrom(ep, is);
//					if(WorldHelper.isServer(ep.getEntityWorld()) && !helper.isAbilityFull()){
//						helper.drawChanceToGainAbility(ep.getRNG(), enemy);
//					}
//				}
//			});
//
//
//			Arrays.stream(ep.inventory.armorInventory)
//			.filter(armor -> armor!=null)
//			.filter(armor -> AbilityHelper.hasCapability(armor))
//			.forEach(armor ->{
//				AbilityAttacher helper = (AbilityAttacher) AbilityHelper.createAttacherFrom(ep, armor);
//				if(!ep.worldObj.isRemote && !helper.isAbilityFull()){
//					helper.drawChanceToGainAbility(ep.getRNG(), enemy);
//				}
//			});
//
//
//		}
//
//
//	}
//
//}
