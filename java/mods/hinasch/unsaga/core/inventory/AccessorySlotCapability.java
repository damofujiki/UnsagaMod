package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.event.EventEntityJoinWorld;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AccessorySlotCapability {

	@CapabilityInject(IAccessoryInventory.class)
	public static Capability<IAccessoryInventory> CAPA;
	public static final String SYNC_ID = "unsagaAccessorySlot";

	public static ICapabilityAdapterPlan<IAccessoryInventory> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IAccessoryInventory.class;
		}

		@Override
		public Class getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new Storage();
		}

	};

	public static CapabilityAdapterFrame<IAccessoryInventory> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IAccessoryInventory> adapter = adapterBase.createChildEntity("unsagaAccessorySlot");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof EntityPlayer);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IAccessoryInventory{

		ItemStackList accessorySlots = new ItemStackList(2);
//		ItemStack[] accessorySlot = new ItemStack[2];
		boolean hasInitialized = false;
		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return hasInitialized;
		}

		@Override
		public void setInitialized(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.hasInitialized = par1;
		}

		@Override
		public NBTTagCompound getSendingData() {
			return (NBTTagCompound) CAPA.writeNBT(this, null);
		}

		@Override
		public void catchSyncData(NBTTagCompound nbt) {
			CAPA.readNBT(this, null, nbt);

		}

		@Override
		public void onPacket(PacketSyncCapability message, MessageContext ctx) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public String getIdentifyName() {
			// TODO 自動生成されたメソッド・スタブ
			return SYNC_ID;
		}



		@Override
		public ItemStackList getEquippedList() {
			// TODO 自動生成されたメソッド・スタブ
			return this.accessorySlots;
		}


		@Override
		public boolean hasEmptySlot() {
			return this.accessorySlots.getRawList().stream().anyMatch(slot -> slot!=ItemUtil.EMPTY_STACK);
		}

		@Override
		public void setStacks(ItemStackList list) {
			// TODO 自動生成されたメソッド・スタブ
			this.accessorySlots = list;
		}




	}

	public static class Storage extends CapabilityStorage<IAccessoryInventory>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IAccessoryInventory> capability,
				IAccessoryInventory instance, EnumFacing side) {

			comp.setBoolean("initialized", instance.hasInitialized());
			NBTTagList tag = UtilNBT.newTagList();
			instance.getEquippedList().writeToNBT(tag);
//			UtilNBT.writeItemStacksToNBTTag(tag, instance.getEquippedList().toArray());
			comp.setTag("items", tag);
		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IAccessoryInventory> capability,
				IAccessoryInventory instance, EnumFacing side) {
//			UnsagaMod.logger.trace(this.getClass().getName(), "ok");
			if(comp.hasKey("initialized")){
				instance.setInitialized(comp.getBoolean("initialized"));
			}
			if(comp.hasKey("items")){
				NBTTagList tag = UtilNBT.getTagList(comp, "items");
				ItemStackList list = ItemStackList.readFromNBT(tag, 2);
//				ItemStack[] stacks = UtilNBT.getItemStacksFromNBT(tag, 2);
				instance.setStacks(list);
			}

		}

	}

	public static class AccessoryDropEvent{
		@SubscribeEvent
		public void onLivingDeath(LivingDeathEvent e){
			if(adapter.hasCapability(e.getEntityLiving())){
				adapter.getCapability(e.getEntityLiving()).getEquippedList().getRawList().stream()
				.forEach(in -> {
					if(ItemUtil.isItemStackPresent(in)){
						ItemUtil.dropItem(e.getEntityLiving().getEntityWorld(), in,XYZPos.createFrom(e.getEntityLiving()));
					}
				});
			}
		}
	}
	public static void registerEvents(){
		adapter.registerAttachEvent();
		PacketSyncCapability.registerSyncCapability(SYNC_ID, CAPA);
		EventEntityJoinWorld.addEvent(e ->{
			if(e.getEntity() instanceof EntityPlayerMP){
				IAccessoryInventory inst = AccessorySlotCapability.adapter.getCapability(e.getEntity());
				HSLib.core().getPacketDispatcher().sendTo(PacketSyncCapability.create(AccessorySlotCapability.CAPA,inst), (EntityPlayerMP)e.getEntity());
//				HSLib.core().getPacketDispatcher().
			}
		});
		HSLibs.registerEvent(new AccessoryDropEvent());
	}
}
