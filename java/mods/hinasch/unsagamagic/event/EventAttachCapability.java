//package mods.hinasch.unsagamagic.event;
//
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsagamagic.capability.IBlessedItem;
//import mods.hinasch.unsagamagic.capability.UnsagaMagicCapability;
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//
//public class EventAttachCapability {
//
//
//	public void eventAttachCapability(AttachCapabilitiesEvent.Item ev){
//		if(ev.getItemStack()!=null){
//			ev.addCapability(new ResourceLocation(UnsagaMod.MODID, "customEnchant"),new ICapabilitySerializable<NBTBase>(){
//
//				IBlessedItem inst = UnsagaMagicCapability.bless().getDefaultInstance();
//				@Override
//				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaMagicCapability.bless()!=null && UnsagaMagicCapability.bless()==capability;
//				}
//
//				@Override
//				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//					// TODO 自動生成されたメソッド・スタブ
//					return (UnsagaMagicCapability.bless()!=null && UnsagaMagicCapability.bless()==capability)? (T)inst : null;
//				}
//
//				@Override
//				public NBTBase serializeNBT() {
//					// TODO 自動生成されたメソッド・スタブ
//					return UnsagaMagicCapability.bless().getStorage().writeNBT(UnsagaMagicCapability.bless(), inst, null);
//				}
//
//				@Override
//				public void deserializeNBT(NBTBase nbt) {
//					// TODO 自動生成されたメソッド・スタブ
//					UnsagaMagicCapability.bless().getStorage().readNBT(UnsagaMagicCapability.bless(), inst, null, nbt);
//				}});
//		}
//	}
//}
