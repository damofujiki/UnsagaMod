//package mods.hinasch.unsaga.damage;
//
//import java.util.Random;
//
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.capability.UnsagaCapability;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.monster.EntityEnderman;
//import net.minecraft.entity.monster.EntitySpider;
//import net.minecraft.entity.monster.EntityZombie;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemHoe;
//import net.minecraft.item.ItemPickaxe;
//import net.minecraft.item.ItemSword;
//import net.minecraft.item.ItemTool;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilityProvider;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class DamageSourceAttacherHelper {
//
//	public static DamageSourceSupplier getDamageSourceFromEntity(EntityLivingBase living){
//		if(living instanceof EntityZombie){
//
//			return new DamageSourceSupplier(){
//
//				@Override
//				public DamageSourceUnsaga apply(EntityLivingBase input) {
//					Random rand = input.getRNG();
//					return rand.nextInt(2)==0 ? DamageHelper.DEFAULT_PUNCH.apply(input):DamageHelper.DEFAULT_SWORD.apply(input);
//				}
//			};
//
//		}
//		if(living instanceof EntityEnderman){
//
//			return new DamageSourceSupplier(){
//
//				@Override
//				public DamageSourceUnsaga apply(EntityLivingBase input) {
//					Random rand = input.getRNG();
//					return DamageHelper.DEFAULT_PUNCH.apply(input).setStrLPHurt(0.6F);
//				}
//			};
//
//		}
//		if(living instanceof EntitySpider){
//
//			return new DamageSourceSupplier(){
//
//				@Override
//				public DamageSourceUnsaga apply(EntityLivingBase input) {
//					Random rand = input.getRNG();
//					return DamageHelper.DEFAULT_SPEAR.apply(input);
//				}
//			};
//
//		}
//		return DamageHelper.DEFAULT_PUNCH;
//	}
//
//	public static DamageSourceSupplier fromItem(Item item){
//
//		if(item instanceof ItemPickaxe || item instanceof ItemHoe){
//			return new DamageSourceSupplier(){
//
//				@Override
//				public DamageSourceUnsaga apply(EntityLivingBase input) {
//					// TODO 自動生成されたメソッド・スタブ
//					return DamageSourceUnsaga.create(input, 0.3F, General.PUNCH,General.SPEAR);
//				}
//			};
//
//
//		}
//		if(item instanceof ItemSword){
//			return DamageHelper.DEFAULT_SWORD;
//		}
//		return DamageHelper.DEFAULT_PUNCH;
//	}
//
//	@SubscribeEvent
//	public void onEntityConstrucr(final AttachCapabilitiesEvent.Entity e){
//		if(e.getEntity() instanceof EntityLivingBase){
//			e.addCapability(new ResourceLocation(UnsagaMod.MODID,"customDamageSource"), new ICapabilityProvider(){
//
//				IUnsagaDamageSource inst = UnsagaCapability.customDamageSource().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.customDamageSource()!=null && capability==UnsagaCapability.customDamageSource();
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.customDamageSource()!=null && capability==UnsagaCapability.customDamageSource()){
//						if(inst instanceof IUnsagaDamageSource){
//							inst.setUnsagaDamageSourceSupplier(getDamageSourceFromEntity((EntityLivingBase) e.getEntity()));
//
//						}
//						return (T)inst;
//					}
//					return null;
//				}});
//		}
//	}
//
//	@SubscribeEvent
//	public void onItemStackConstruct(final AttachCapabilitiesEvent.Item e){
//
//		if(e.getItem() instanceof ItemTool){
//			e.addCapability(new ResourceLocation(UnsagaMod.MODID,"customDamageSource.item"), new ICapabilityProvider(){
//
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return false;
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return null;
//				}}
//			);
//		}
//
//	}
//}
