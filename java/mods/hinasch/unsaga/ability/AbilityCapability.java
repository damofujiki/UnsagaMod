package mods.hinasch.unsaga.ability;

import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class AbilityCapability {


	@CapabilityInject(IAbilityAttachable.class)
	public static Capability<IAbilityAttachable> CAPA;
	public static final String SYNC_ID = "unsagaAbilityAttachable";

	public static ICapabilityAdapterPlan<IAbilityAttachable> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IAbilityAttachable.class;
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

	public static CapabilityAdapterFrame<IAbilityAttachable> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<IAbilityAttachable> adapter = adapterBase.createChildItem("unsagaAbilityAttachable");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof IAbilitySelector);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IAbilityAttachable{

		int maxAbilitySize = 1;
		LinkedList<IAbility> list = Lists.newLinkedList();
		List<IAbility> egoAbilityList = Lists.newArrayList();
		boolean hasInitialized = false;

		@Override
		public int getMaxAbilitySize() {
			// TODO 自動生成されたメソッド・スタブ
			return maxAbilitySize;
		}

		@Override
		public void setMaxAbilitySize(int size) {
			this.maxAbilitySize = size;
		}

		@Override
		public LinkedList<IAbility> getLearnedAbilities() {
			return list;
		}

		@Override
		public void setAbilities(LinkedList<IAbility> list) {
			this.list = list;

		}

		@Override
		public void removeAbility(IAbility ab) {
			if(this.list.indexOf(ab)>=0){
				this.list.set(this.list.indexOf(ab),AbilityRegistry.instance().empty);
			}

		}

		@Override
		public boolean hasAbility(IAbility ab) {
			return this.list.contains(ab);
		}

		@Override
		public void addAbility(IAbility ab) {
			if(this.list.indexOf(AbilityRegistry.instance().empty)>=0){
				this.list.set(this.list.indexOf(AbilityRegistry.instance().empty),ab);
			}



		}

		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return hasInitialized;
		}

		@Override
		public void setInitialized(boolean par1) {
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
		public boolean isUniqueItem() {
			// TODO 自動生成されたメソッド・スタブ
			return !this.egoAbilityList.isEmpty();
		}


		@Override
		public void setLearnableUniqueAbilities(List<IAbility> in) {
			// TODO 自動生成されたメソッド・スタブ
			this.egoAbilityList =  in;
		}

		@Override
		public List<IAbility> getLearnableUniqueAbilities() {
			// TODO 自動生成されたメソッド・スタブ
			return this.egoAbilityList;
		}

		@Override
		public boolean isAbilityFull() {
			// TODO 自動生成されたメソッド・スタブ
			return !this.list.contains(AbilityRegistry.empty());
		}

		@Override
		public boolean isAbilityEmpty() {
			// TODO 自動生成されたメソッド・スタブ
			return this.list.stream().allMatch(in -> in==AbilityRegistry.empty());
		}

		@Override
		public void setAbility(int index, IAbility ab) {
			this.list.set(index, ab);

		}

		@Override
		public void clearAbility(int size) {
			this.list = HSLibs.filledList(size, AbilityRegistry.empty());
		}

	}

	public static class Storage extends CapabilityStorage<IAbilityAttachable>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IAbilityAttachable> capability,
				IAbilityAttachable instance, EnumFacing side) {
			comp.setBoolean("initialized", instance.hasInitialized());
			comp.setByte("size", (byte)instance.getMaxAbilitySize());
			UtilNBT.writeListToNBT(instance.getLearnedAbilities(), comp, "abilities");
			if(instance.isUniqueItem()){
				UtilNBT.writeListToNBT(instance.getLearnableUniqueAbilities(), comp, "egoAbilities");
			}
		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IAbilityAttachable> capability, IAbilityAttachable instance,
				EnumFacing side) {
			if(comp.hasKey("initialized")){
				instance.setInitialized(comp.getBoolean("initialized"));
			}
			if(comp.hasKey("size")){
				instance.setMaxAbilitySize((int)comp.getByte("size"));
			}
			if(comp.hasKey("abilities")){
				List<IAbility> list = UtilNBT.readListFromNBT(comp, "abilities", Ability.FUNC_RESTORE);
				instance.setAbilities(Lists.newLinkedList(list));
			}
			if(comp.hasKey("egoAbilities")){
				List<IAbility> list = UtilNBT.readListFromNBT(comp, "egoAbilities", Ability.FUNC_RESTORE);
				instance.setLearnableUniqueAbilities(list);
			}

		}

	}

	public static void registerEvents(){
		PacketSyncCapability.registerSyncCapability(AbilityCapability.SYNC_ID, AbilityCapability.CAPA);
		adapter.registerAttachEvent((inst,capa,face,ev)->{
			if(ev.getObject() instanceof IAbilitySelector && !inst.hasInitialized()){
				IAbilitySelector intf = (IAbilitySelector) ev.getObject();
				inst.setMaxAbilitySize(intf.getMaxAbilitySize());
				inst.setAbilities(HSLibs.filledList(intf.getMaxAbilitySize(), AbilityRegistry.empty()));
				inst.setInitialized(true);
			}
		});
	}
}
