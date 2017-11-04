//package mods.hinasch.unsagamagic.util;
//
//import java.util.Set;
//import java.util.function.Function;
//
//import com.google.common.collect.Sets;
//
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess.EventContainer;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumHand;
//import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
//import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
//import net.minecraftforge.event.world.BlockEvent.BreakEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class CustomBlessGroup {
//
//	public static CustomBless ARMOR = new CustomBless("armor",1){
//
//		@Override
//		public void registerEvent() {
//			UnsagaMod.events.getLivingHurtEventAppendable().getHooks().put(this, new Function<EventContainer,Float>(){
//
//				@Override
//				public Float apply(EventContainer t) {
//
//					int amount = 0;
//					float ampAmount = 0;
//					for(ItemStack is:t.getVictim().getArmorInventoryList()){
//						if(is!=null && CustomBless.hasCapability(is)){
//							if(CustomBless.getCapability(is).isBlessEffective(ARMOR)){
//								amount += 1;
//								ampAmount += CustomBless.getCapability(is).getBless(ARMOR).getAmp();
//								CustomBless.getCapability(is).decrRemaining(ARMOR, 1);
//								if(t.getVictim() instanceof EntityPlayer){
//									CustomBless.syncCapability((EntityPlayer)t.getVictim() , CustomBless.getCapability(is),EnumHand.MAIN_HAND);
//								}
//
//							}
//						}
//					}
//
//					if(amount>0){
//						float par1 = DamageHelper.calcReduceAmount(t.getBaseDamage(), 4 ,(int)( 8.0F * ampAmount), amount, t.getVictim().getRNG());
//						UnsagaMod.logger.trace("blessweapon", par1);
//						return t.getBaseDamage() - par1;
//					}
//
//					return t.getBaseDamage();
//				}}
//					);
//
//		}
//
//
//
//	};
//	public static class EventBreakSpeedSharpness{
//
//		@SubscribeEvent
//		public void onBreakingBlock(BreakSpeed ev){
//			ItemStack is = ev.getEntityPlayer().getHeldItemMainhand();
//			if(is!=null && CustomBless.hasCapability(is)){
//				if(CustomBless.getCapability(is).isBlessEffective(CustomBlessGroup.SHARPNESS)){
//					float amp = CustomBless.getCapability(is).getBless(CustomBlessGroup.SHARPNESS).getAmp();
//					float speedOri = ev.getOriginalSpeed();
//					float newSpeed = ev.getOriginalSpeed() * (1.0F + ((int)amp * 0.2F));
//					ev.setNewSpeed(newSpeed);
//				}
//			}
//		}
//
//		@SubscribeEvent
//		public void onBreakBlock(BreakEvent ev){
//			ItemStack is = ev.getPlayer().getHeldItemMainhand();
//			if(is!=null && CustomBless.hasCapability(is)){
//				if(CustomBless.getCapability(is).isBlessEffective(CustomBlessGroup.SHARPNESS)){
//					CustomBless.getCapability(is).decrRemaining(SHARPNESS, 1);
//					CustomBless.syncCapability(ev.getPlayer(), CustomBless.getCapability(is),EnumHand.MAIN_HAND);
//
//				}
//			}
//		}
//
//		@SubscribeEvent
//		public void onHarvestCheck(HarvestCheck ev){
//			ItemStack is = ev.getEntityPlayer().getHeldItemMainhand();
//			if(is!=null && CustomBless.hasCapability(is)){
//				if(CustomBless.getCapability(is).isBlessEffective(CustomBlessGroup.SHARPNESS)){
//					boolean canHarvest = Items.IRON_PICKAXE.canHarvestBlock(ev.getTargetBlock()) || is.canHarvestBlock(ev.getTargetBlock());
//					ev.setCanHarvest(canHarvest);
//				}
//			}
//		}
//	}
//	public static CustomBless SHARPNESS = new CustomBless("sharpness",2){
//
//		@Override
//		public void registerEvent() {
//			HSLibs.registerEvent(new EventBreakSpeedSharpness());
//
//		}
//
//
//
//	};
//	public static CustomBless WEAPON = new CustomBless("weapon",3){
//
//		@Override
//		public void registerEvent() {
//			UnsagaMod.events.getLivingHurtEventAppendable().getHooks().put(this, new Function<EventContainer,Float>(){
//
//				@Override
//				public Float apply(EventContainer t) {
//					UnsagaMod.logger.trace(this.getClass().getName(), "called");
//					if(t.getAttacker() instanceof EntityLivingBase){
//						EntityLivingBase living = (EntityLivingBase) t.getAttacker();
//						ItemStack is = living.getHeldItemMainhand();
//						if(is!=null && CustomBless.hasCapability(is)){
//							if(CustomBless.getCapability(is).isBlessEffective(WEAPON) && !t.getDamageSource().isMagicDamage()){
//
//								float amp = CustomBless.getCapability(is).getBless(WEAPON).getAmp();
////								float par1 = DamageHelper.calcReduceAmount(t.getBaseDamage(), 8, 12, (int)amp, living.getRNG());
//								CustomBless.getCapability(is).decrRemaining(WEAPON, 1);
//								if(living instanceof EntityPlayer){
//									CustomBless.syncCapability((EntityPlayer) living, CustomBless.getCapability(is),EnumHand.MAIN_HAND);
//								}
////								UnsagaMod.logger.trace("blessweapon", par1);
//								return t.getBaseDamage() + amp;
//							}
//						}
//					}
//					return t.getBaseDamage();
//				}}
//					);
//
//		}
//
//
//
//
//
//	};
//	public static Set<CustomBless> BLESS_SET = Sets.newHashSet(ARMOR,WEAPON,SHARPNESS);
//
//	public static CustomBless fromID(int id){
//		return HSLibs.fromMeta(BLESS_SET, id);
//	}
//}
