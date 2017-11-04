package mods.hinasch.unsagamagic.enchant;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Maps;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class UnsagaEnchantmentCapability {

	@CapabilityInject(IUnsagaEnchantable.class)
	public static Capability<IUnsagaEnchantable> CAPA;
	public static final String SYNC_ID = "unsagaEnchantment";

	public static ICapabilityAdapterPlan<IUnsagaEnchantable> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IUnsagaEnchantable.class;
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

	public static CapabilityAdapterFrame<IUnsagaEnchantable> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<IUnsagaEnchantable> adapter = adapterBase.createChildItem("unsagaEnchantment");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof Item);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IUnsagaEnchantable{

		Map<UnsagaEnchantment,Integer> remainings = Maps.newHashMap();
		boolean init = false;

		public void init(){
//			UnsagaEnchantmentRegistry.instance().getProperties().forEach(in ->{
//				remainings.put(in, 0);
//			});
			this.setInitialized(true);
		}
		@Override
		public int getEnchantmentRemaining(UnsagaEnchantment enchant) {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.get(enchant) !=null ? this.remainings.get(enchant) : 0;
		}

		@Override
		public void setEnchantmentRemaining(UnsagaEnchantment enchant,int remaining) {
			this.remainings.put(enchant, remaining);

		}
		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return this.init;
		}
		@Override
		public void setInitialized(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.init = par1;
		}
		@Override
		public Set<Entry<UnsagaEnchantment,Integer>> getEntries() {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.entrySet();
		}
		@Override
		public void setMap(Map<UnsagaEnchantment, Integer> map) {
			map.entrySet().stream().forEach(in ->{
				this.remainings.put(in.getKey(), in.getValue());
			});
		}

		@Override
		public void reduceRemainings(UnsagaEnchantment e) {
			UnsagaMod.logger.trace(this.getClass().getName(), e.getName(),"called");
			int remain = this.getEnchantmentRemaining(e);
			remain --;
			if(remain<=0){
				remain = 0;
				this.remainings.remove(e);
			}else{
				UnsagaMod.logger.trace(this.getClass().getName(), e.getName(),remain);
				this.setEnchantmentRemaining(e, remain);
			}

		}
		@Override
		public void onAttack() {
			// TODO 自動生成されたメソッド・スタブ
			this.reduceRemainings(UnsagaEnchantmentRegistry.instance().weaponBless);
			this.reduceRemainings(UnsagaEnchantmentRegistry.instance().sharpness);
		}
		@Override
		public void onHurt() {
			// TODO 自動生成されたメソッド・スタブ
			this.reduceRemainings(UnsagaEnchantmentRegistry.instance().armorBless);
		}
		@Override
		public boolean isEmpty() {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.isEmpty();
		}


	}

	public static class Storage extends CapabilityStorage<IUnsagaEnchantable>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IUnsagaEnchantable> capability,
				IUnsagaEnchantable instance, EnumFacing side) {
			if(!instance.isEmpty()){
				NBTTagList tagList = UtilNBT.newTagList();
				for(Entry<UnsagaEnchantment,Integer> entry:instance.getEntries()){
					NBTTagCompound child = UtilNBT.compound();
					child.setString("key",entry.getKey().getKey().getResourcePath());
					child.setInteger("remain", entry.getValue());
					tagList.appendTag(child);
				}

				comp.setTag("enchants", tagList);
			}


//			comp.setBoolean("init", instance.hasInitialized());


		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IUnsagaEnchantable> capability, IUnsagaEnchantable instance,
				EnumFacing side) {
			if(comp.hasKey("enchants")){
				NBTTagList tagList = UtilNBT.getTagList(comp, "enchants");
				Map<UnsagaEnchantment,Integer> map = Maps.newHashMap();
				for(int i=0;i<tagList.tagCount();i++){
					NBTTagCompound child = tagList.getCompoundTagAt(i);
					UnsagaMod.logger.trace("ロード", child);
					String key = child.getString("key");
					UnsagaEnchantment enchant = UnsagaEnchantmentRegistry.instance().get(key);
					int remain = child.getInteger("remain");
					UnsagaMod.logger.trace("ロード", key,remain);
					map.put(enchant, remain);
				}
				instance.setMap(map);
			}
//
//			if(comp.hasKey("init")){
//				instance.setInitialized(comp.getBoolean("init"));
//
//			}

		}


	}

	public static void registerEvents(){
		adapter.registerAttachEvent();
	}
}
