package mods.hinasch.unsaga.util;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.ComponentCapabilityAdapter;
import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.capability.StorageDummy;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.HSLibEvents;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LivingHelper {

	//unsagamodへ移行

	public static String SYNC_ID = "livingHelper";
	public static List<EventEquipmentsChanged> events = Lists.newArrayList();
	public static List<CacheType> registeredTypes = Lists.newArrayList();
	public static List<Hook> hooks = Lists.newArrayList();
	public static enum CacheType{ARMOR,HAND,ACCESSORY};
	@CapabilityInject(ILivingHelper.class)
	public static Capability<ILivingHelper> CAPA;

	public static ICapabilityAdapterPlan capabilityAdapter = new ICapabilityAdapterPlan<ILivingHelper>(){


		@Override
		public Capability<ILivingHelper> getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class<ILivingHelper> getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return ILivingHelper.class;
		}

		@Override
		public Class<? extends ILivingHelper> getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage<ILivingHelper> getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new StorageDummy();
		}};
	public static CapabilityAdapterFrame adapterBase = HSLib.core().capabilityAdapterFactory.create(capabilityAdapter);
	public static ComponentCapabilityAdapter<ILivingHelper,AttachCapabilitiesEvent.Entity,Entity> adapter = adapterBase.createChildEntity("livingHelper")
			.setRequireSerialize(false);

	static{
		adapter.setPredicate(input -> input.getEntity() instanceof EntityPlayer);
	}
	public static interface ILivingHelper extends ISyncCapability{

		public boolean checkStacks(Iterable<ItemStack> ite,ItemStack isIn);
		public int getWeaponGuardCooling();
		public void decrWeaponGuardProgress();
		public void resetWeaponGuardProgress();
		public void decrWeaponGuardCooling();
		public void watchHeldDamage(EntityPlayer ep);
		public int getWeaponGuardProgress();
		public void refleshEquipmentsCache(EntityLivingBase living);
		public boolean hasChangedEquipment(EntityLivingBase living);
		public Map<CacheType,List<Equipment>> getMap();
	}

	public static Map<CacheType,List<Equipment>> initMap(){
		Map<CacheType,List<Equipment>> map = Maps.newHashMap();
		for(CacheType type:registeredTypes){
			map.put(type, Lists.<Equipment>newArrayList());
		}
		return map;
	}
	public static class DefaultImpl implements ILivingHelper{
		int guardCooling = 0;
		int guardProgress = 0;
		boolean init = false;
		Map<CacheType,List<Equipment>> map = initMap();
		ItemStack prevTool;
		OptionalInt prevDamage;

		public void watchHeldDamage(EntityPlayer ep){
			if(this.prevTool!=null){
				if(this.prevTool==ep.getHeldItemMainhand()){
					if(this.prevDamage.isPresent() && this.prevDamage.getAsInt()!=ep.getHeldItemMainhand().getItemDamage()){
						UnsagaMod.logger.trace("ダメージ", ep.getHeldItemMainhand().getItemDamage()-this.prevDamage.getAsInt());
					}
				}
			}

			this.prevTool = ep.getHeldItemMainhand();
			if(this.prevTool!=null){
				this.prevDamage = OptionalInt.of(this.prevTool.getItemDamage());
			}



		}
		public void refleshEquipmentsCache(EntityLivingBase living){
			this.map = initMap();
			for(ItemStack is:living.getArmorInventoryList()){
				if(is!=null){
					map.get(CacheType.ARMOR).add(new Equipment(is));
				}else{
					map.get(CacheType.ARMOR).add(NONE);
				}
			}
			for(ItemStack is:living.getHeldEquipment()){
				if(is!=null){
					map.get(CacheType.HAND).add(new Equipment(is));
				}else{
					map.get(CacheType.HAND).add(NONE);
				}
			}
			for(Hook hook:hooks){
				hook.onRefleshEquipments(this, living);
			}
		}

		public boolean hasChangedEquipment(EntityLivingBase living){
			if(!this.init){
				this.init = true;
				return true;
			}

			int index = 0;

			for(ItemStack is:living.getArmorInventoryList()){
				if(!this.map.get(CacheType.ARMOR).get(index).compare(is)){
					return true;
				}
				index ++;
			}

			index = 0;
			for(ItemStack is:living.getHeldEquipment()){
				if(!this.map.get(CacheType.HAND).get(index).compare(is)){
					return true;
				}
				index ++;
			}
//			for(ItemStack is:this.map.get(CacheType.HAND)){
//				if(is!=null){
//					if(!this.checkStacks(living.getHeldEquipment(), is)){
//						return true;
//					}
//				}
//			}

			for(Hook hook:hooks){
				if(hook.onHasChangedEquipments(this, living)){
					return true;
				}
			}
			return false;
		}

		public boolean checkStacks(Iterable<ItemStack> ite,ItemStack isIn){
			for(ItemStack is:ite){
				if(is!=null && isIn==is){
					return true;
				}
			}
			return false;
		}

		@Override
		public Map<CacheType, List<Equipment>> getMap() {
			// TODO 自動生成されたメソッド・スタブ
			return this.map;
		}

		@Override
		public NBTTagCompound getSendingData() {
			NBTTagCompound comp = UtilNBT.compound();
			comp.setByte("id", (byte) 0);
			return comp;
		}

		@Override
		public void catchSyncData(NBTTagCompound nbt) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onPacket(PacketSyncCapability message, MessageContext ctx) {

			if(ctx.side.isServer()){
				EntityPlayer ep = ctx.getServerHandler().playerEntity;
				for(EventEquipmentsChanged ev:events){
					ev.onEquipmentsChanged(ep);
				}
			}
		}

		@Override
		public String getIdentifyName() {
			// TODO 自動生成されたメソッド・スタブ
			return SYNC_ID;
		}


		@Override
		public int getWeaponGuardProgress() {
			// TODO 自動生成されたメソッド・スタブ
			return this.guardProgress;
		}

		@Override
		public int getWeaponGuardCooling() {
			return this.guardCooling;

		}

		@Override
		public void resetWeaponGuardProgress() {
			this.guardProgress = 4;
			this.guardCooling = 10;
		}

		@Override
		public void decrWeaponGuardProgress() {
			this.guardProgress -= 1;
			if(this.guardProgress<0){
				this.guardProgress = 0;
			}
		}

		@Override
		public void decrWeaponGuardCooling() {
			// TODO 自動生成されたメソッド・スタブ
			this.guardCooling -= 1;
			if(this.guardCooling<0){
				this.guardCooling = 0;
			}
		}
	}

	public static void registerEvents(){
		PacketSyncCapability.registerSyncCapability(SYNC_ID, CAPA);
		registeredTypes.addAll(Lists.newArrayList(CacheType.values()));
//		HSLibEvents.livingHurt.getEventsPost().add(new ILivingHurtEventUnsaga(){
//
//			@Override
//			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//				return adapter.hasCapability(e.getEntityLiving());
//			}
//
//			@Override
//			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//				if(adapter.getCapability(e.getEntityLiving()).getWeaponGuardProgress()>0){
//					if(WorldHelper.isServer(e.getEntityLiving().getEntityWorld())){
//						SoundPacket.sendTo(PacketSound.atEntity(SoundEvents.ITEM_SHIELD_BLOCK, e.getEntityLiving()),(EntityPlayerMP) e.getEntityLiving());
//					}
//					if(e.getEntityLiving().getHeldItemOffhand()!=null){
//						e.getEntityLiving().getHeldItemOffhand().damageItem(1, e.getEntityLiving());
//					}
////					e.getEntityLiving().playSound(SoundEvents.block_anvil_fall, 1.0F, 1.0F);
//					if(dsu.isProjectile()){
//						dsu.setStrLPHurt(0);
//						e.setAmount(0);
//					}else{
//						dsu.setStrLPHurt(dsu.getStrLPHurt() * 0.33F);
//						e.setAmount(e.getAmount() * 0.33F);
//					}
//
//				}
//				return dsu;
//			}
//
//			@Override
//			public String getName() {
//				// TODO 自動生成されたメソッド・スタブ
//				return "sword blocking";
//			}}
//		);
//		HSLib.eventLivingUpdate.getEvents().add(new ILivingUpdateEvent(){
//
//			@Override
//			public void update(LivingUpdateEvent e) {
//				if(e.getEntityLiving() instanceof EntityPlayer){
//					if(LivingHelper.adapter.hasCapability(e.getEntityLiving())){
//						LivingHelper.adapter.getCapability(e.getEntityLiving()).watchHeldDamage((EntityPlayer) e.getEntityLiving());
//					}
//				}
//
//			}
//
//			@Override
//			public String getName() {
//				// TODO 自動生成されたメソッド・スタブ
//				return "watchToolDamage";
//			}}
//		);
		HSLibEvents.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(adapter.hasCapability(e.getEntityLiving()) && e.getEntityLiving().ticksExisted % 10 * 12 ==0){
					ILivingHelper capa = adapter.getCapability(e.getEntityLiving());
					if(capa.hasChangedEquipment(e.getEntityLiving())){
						capa.refleshEquipmentsCache(e.getEntityLiving());
						this.onChangedEquipments(e.getEntityLiving());
					}

					if(capa.getWeaponGuardProgress()>0){
						capa.decrWeaponGuardProgress();
						UnsagaMod.logger.trace("swing", capa.getWeaponGuardProgress());
					}
					if(capa.getWeaponGuardCooling()>0){
						capa.decrWeaponGuardCooling();
					}
				}

			}

			public void onChangedEquipments(EntityLivingBase living){

				HSLib.logger.trace(getName(), "changed");
				for(EventEquipmentsChanged ev:events){
					ev.onEquipmentsChanged(living);
				}
			}
			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "watchChangeEquipment";
			}}
		);

		adapter.registerAttachEvent();

	}

	public static void registerEquipmentsChangedEvent(EventEquipmentsChanged ev){

		events.add(ev);
	}
	public static void registerHook(Hook hook){
		hooks.add(hook);
	}
	public static interface Hook{

		public void onRefleshEquipments(ILivingHelper callback,EntityLivingBase living);
		public boolean onHasChangedEquipments(ILivingHelper callback,EntityLivingBase living);
	}

	public static interface EventEquipmentsChanged{

		public void onEquipmentsChanged(EntityLivingBase living);
	}

	public static final Equipment NONE = new Equipment(null);
	public static class Equipment{

		ItemStack is;

		public ItemStack getStack() {
			return is;
		}

		public Equipment(ItemStack is) {
			super();
			this.is = is;
		}

		public boolean compare(ItemStack in){
			if(this.is==null){
				return in==null;
			}else{
				return is==in;
			}
		}
	}
}
