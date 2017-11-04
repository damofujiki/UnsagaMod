//package mods.hinasch.unsaga.core.event;
//
//import mods.hinasch.lib.util.Statics;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.capability.IAccessorySlot;
//import mods.hinasch.unsaga.capability.ILifePoint;
//import mods.hinasch.unsaga.capability.ITaggedArrow;
//import mods.hinasch.unsaga.capability.IUnsagaVillager;
//import mods.hinasch.unsaga.capability.IUnsagaVillager.SmithProfessionality;
//import mods.hinasch.unsaga.capability.IUnsagaVillager.VillagerType;
//import mods.hinasch.unsaga.capability.UnsagaCapability;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
//import mods.hinasch.unsaga.util.XPHelper.IUnsagaExp;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.passive.EntityVillager;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class EventCapabilityAttach {
//
//	@SubscribeEvent
//	public void onEntityConstrucr(final AttachCapabilitiesEvent.Entity e){
//
//		if(e.getEntity() instanceof EntityVillager){
//			e.addCapability(getResourceLocation("villager"), new ICapabilitySerializable<NBTBase>(){
//
//				IUnsagaVillager inst = UnsagaCapability.villager().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.villager()!=null && capability==UnsagaCapability.villager();
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.villager()!=null && UnsagaCapability.villager()==capability){
//						EntityVillager villager = (EntityVillager) e.getEntity();
//						if(Statics.checkProfession(villager.getProfessionForge(), Statics.VILLAGER_BLACKSMITH)){
//
//							if(!inst.hasInitialized()){
//								inst.setType(VillagerType.SMITH);
//								inst.setSmithProfessionality(SmithProfessionality.getRandomType(villager.getRNG()));
//							}
////							inst.setSmithProfessionality(SmithProfessionality.getRandomType(villager.getRNG()));
//						}else{
//							if(!inst.hasInitialized()){
//								inst.setType(VillagerType.BARTERING);
//							}
//
//						}
//
//						return (T)inst;
//					}
//					return null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.villager().getStorage().writeNBT(UnsagaCapability.villager(), inst, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//					// TODO 自動生成されたメソッド・スタブ
//					UnsagaCapability.villager().getStorage().readNBT(UnsagaCapability.villager(), inst, null, nbt);
//				}}
//			);
//		}
//
//		if(e.getEntity() instanceof EntityArrow){
//			e.addCapability(getResourceLocation("taggedArrow"), new ICapabilitySerializable<NBTBase>(){
//
//				ITaggedArrow defaultArrow = UnsagaCapability.arrow().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.arrow()!=null && capability==UnsagaCapability.arrow();
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.arrow()!=null && UnsagaCapability.arrow()==capability){
//						 defaultArrow.setUnsagaDamageSourceSupplier(new DamageSourceSupplier(){
//
//							@Override
//							public DamageSourceUnsaga apply(EntityLivingBase input) {
//								float lphurt = DamageHelper.DEFAULT_BOW.apply(input).getStrLPHurt();
//								return DamageSourceUnsaga.createProjectile(input, e.getEntity(), lphurt, General.SPEAR);
//							}}
//						);
//						 return (T) defaultArrow;
//
//					}
//					return null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.arrow().getStorage().writeNBT(UnsagaCapability.arrow(), defaultArrow, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//					// TODO 自動生成されたメソッド・スタブ
//
//					UnsagaCapability.arrow().getStorage().readNBT(UnsagaCapability.arrow(), defaultArrow, null, nbt);
//				}}
//			);
//
//		}
//
//		if(e.getEntity() instanceof EntityPlayer){
//
//			e.addCapability(this.getResourceLocation("skillPoint"), new ICapabilitySerializable<NBTBase>(){
//
//				IUnsagaExp inst = UnsagaCapability.exp().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.exp()!=null && UnsagaCapability.exp()==capability;
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.exp()!=null && UnsagaCapability.exp()==capability){
//						return (T)inst;
//					}
//					return null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.exp().getStorage().writeNBT(UnsagaCapability.exp(), inst, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//					// TODO 自動生成されたメソッド・スタブ
//					UnsagaCapability.exp().getStorage().readNBT(UnsagaCapability.exp(), inst, null, nbt);
//				}}
//			);
//
//			e.addCapability(new ResourceLocation(UnsagaMod.MODID,"accessories"), new ICapabilitySerializable<NBTBase>(){
//
//				IAccessorySlot defaultSlot = UnsagaCapability.accessorySlots().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					return UnsagaCapability.accessorySlots()!=null && capability==UnsagaCapability.accessorySlots();
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.accessorySlots()!=null && UnsagaCapability.accessorySlots()==capability){
//						return (T)defaultSlot;
//
//					}
//					return null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					return UnsagaCapability.accessorySlots().getStorage().writeNBT(UnsagaCapability.accessorySlots(), defaultSlot, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//
//
//					UnsagaCapability.accessorySlots().getStorage().readNBT(UnsagaCapability.accessorySlots(), defaultSlot, null, nbt);
//				}
//			});
//		}
//		if(e.getEntity() instanceof EntityLivingBase){
//
//
//			e.addCapability(new ResourceLocation(UnsagaMod.MODID,"LifePoint"),new ICapabilitySerializable<NBTBase>(){
//				ILifePoint lpDefault = UnsagaCapability.LP().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					return UnsagaCapability.LP()!=null && capability==UnsagaCapability.LP();
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					if(UnsagaCapability.LP()!=null && UnsagaCapability.LP()==capability){
//						if(!lpDefault.hasInitialized()){
//							lpDefault.init((EntityLivingBase) e.getEntity());
//						}
//
//						return (T)lpDefault;
//
//					}
//					return null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaCapability.LP().getStorage().writeNBT(UnsagaCapability.LP(), lpDefault, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//					// TODO 自動生成されたメソッド・スタブ
//
//					UnsagaCapability.LP().getStorage().readNBT(UnsagaCapability.LP(), lpDefault, null, nbt);
//				}}
//			);
//		}
//
//
//	}
//
//	protected ResourceLocation getResourceLocation(String name){
//		return new ResourceLocation(name);
//	}
//}
