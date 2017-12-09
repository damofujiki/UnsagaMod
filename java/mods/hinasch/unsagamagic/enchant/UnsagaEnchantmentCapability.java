package mods.hinasch.unsagamagic.enchant;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

		Map<EnchantmentProperty,EnchantmentState> remainings = Maps.newHashMap();
		boolean init = false;

		public void init(){
//			UnsagaEnchantmentRegistry.instance().getProperties().forEach(in ->{
//				remainings.put(in, 0);
//			});
			this.setInitialized(true);
		}
		@Override
		public @Nullable EnchantmentState getEnchantment(EnchantmentProperty enchant) {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.get(enchant);
		}

		@Override
		public void setEnchantment(EnchantmentProperty enchant,EnchantmentState remaining) {
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
		public Set<Entry<EnchantmentProperty,EnchantmentState>> getEntries() {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.entrySet();
		}
		@Override
		public void setMap(Map<EnchantmentProperty, EnchantmentState> map) {
			map.entrySet().stream().forEach(in ->{
				this.remainings.put(in.getKey(), in.getValue());
			});
		}

		@Override
		public boolean isEmpty() {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.isEmpty();
		}
		@Override
		public boolean hasEnchant(EnchantmentProperty e) {
			// TODO 自動生成されたメソッド・スタブ
			return this.remainings.containsKey(e);
		}

		public void remove(EntityLivingBase living,EnchantmentProperty p){
			this.remainings.remove(p);
			UnsagaEnchantmentEvent.refleshApplyEnchantment(living);
		}
		@Override
		public void checkExpireTime(EntityLivingBase living,long totalWorldTime) {
			Queue<EnchantmentProperty> queue = new ArrayBlockingQueue(10);
			for(EnchantmentProperty en:this.remainings.keySet()){
				if(this.remainings.get(en).getExpireTime()<totalWorldTime){
					queue.offer(en);
				}
			}

			for(int i=0;i<queue.size();i++){
				EnchantmentProperty p = queue.poll();
				this.remainings.remove(p);
			}
		}
		@Override
		public float getBowModifier() {
			for(EnchantmentProperty prop:this.remainings.keySet()){
				if(prop instanceof EnchantmentWeapon){
					EnchantmentState state = this.getEnchantment(prop);
					return ((EnchantmentWeapon)prop).getAttackModifier(state.getLevel());
				}
			}
			return 0;
		}



	}

	public static class Storage extends CapabilityStorage<IUnsagaEnchantable>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IUnsagaEnchantable> capability,
				IUnsagaEnchantable instance, EnumFacing side) {
			if(!instance.isEmpty()){
				NBTTagList tagList = UtilNBT.newTagList();
				for(Entry<EnchantmentProperty,EnchantmentState> entry:instance.getEntries()){
					NBTTagCompound child = UtilNBT.compound();
					child.setString("key",entry.getKey().getKey().getResourcePath());
					entry.getValue().writeToNBT(child);
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
				Map<EnchantmentProperty,EnchantmentState> map = Maps.newHashMap();
				for(int i=0;i<tagList.tagCount();i++){
					NBTTagCompound child = tagList.getCompoundTagAt(i);
					UnsagaMod.logger.trace("ロード", child);
					String key = child.getString("key");
					EnchantmentProperty enchant = UnsagaEnchantmentRegistry.instance().get(key);
					EnchantmentState expire = EnchantmentState.RESTORE.apply(child);
					UnsagaMod.logger.trace("ロード", key,expire);
					map.put(enchant, expire);
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

	public static boolean hasCapability(ItemStack entity){
		return adapter.hasCapability(entity);
	}
	public static class EnchantmentState implements INBTWritable{
		public EnchantmentState(long expireTime, int level) {
			super();
			this.expireTime = expireTime;
			this.level = level;
		}
		final long expireTime;
		final int level;
		public long getExpireTime() {
			return expireTime;
		}
		public int getLevel() {
			return level;
		}
		@Override
		public void writeToNBT(NBTTagCompound stream) {
			// TODO 自動生成されたメソッド・スタブ
			stream.setLong("expire", this.expireTime);
			stream.setInteger("level", level);
		}

		public static RestoreFunc<EnchantmentState> RESTORE = input -> {
			long expire = input.getLong("expire");
			int level = input.getInteger("level");
			return new EnchantmentState(expire,level);
		};

	}
}
