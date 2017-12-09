package mods.hinasch.lib.capability;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.event.LivingEquipChangedEvent;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class EquipmentCacheCapability {


	@CapabilityInject(IEquipmentCache.class)
	public static Capability<IEquipmentCache> CAPA;
//	public static final String SYNC_ID = "unsagaAccessorySlot";

	public static ICapabilityAdapterPlan<IEquipmentCache> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IEquipmentCache.class;
		}

		@Override
		public Class getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new StorageDummy();
		}

	};

	public static CapabilityAdapterFrame<IEquipmentCache> adapterBase = HSLib.core().capabilityAdapterFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IEquipmentCache> adapter = adapterBase.createChildEntity("equipmentCache");

	static{
		adapter.setPredicate(ev -> ev.getEntity() instanceof EntityLivingBase);

	}
	public static class DefaultImpl implements IEquipmentCache{

		ItemStackList cacheList = new ItemStackList(6);
		int counter = 0;
		boolean changed = false;
		boolean hasInitialized = false;

		public void update(EntityLivingBase el){
			counter = 0;
			el.getHeldEquipment().forEach(is ->{
				cacheList.setStack(counter, is);
				counter ++;
			});
			el.getArmorInventoryList().forEach(is ->{
				cacheList.setStack(counter, is);
				counter ++;
			});
//			if(AccessorySlotCapability.adapter.hasCapability(el)){
//				AccessorySlotCapability.adapter.getCapability(el).getEquippedList()
//				.getRawList().forEach(is ->{
//					cacheList.setStack(counter, is);
//					counter ++;
//				});
//			}

//			this.cacheList.toString();
//			this.cacheList.getStacks().forEach(is -> {
//				if(is==ItemUtil.EMPTY_STACK){
//					UnsagaMod.logger.trace("update", "empty");
//				}else{
//					UnsagaMod.logger.trace("update", is);
//				}
//			});
//			UnsagaMod.logger.trace("update", this.cacheList.getStacks());

			EquipmentCacheCapability.onEquipChanged(el);
		}

		public boolean hasChanged(EntityLivingBase el){
			counter = 0;
			this.changed = false;
			el.getHeldEquipment().forEach(is ->{
				if(!cacheList.isSame(counter, is)){
					this.changed = true;
				}
				counter ++ ;

			});
			el.getArmorInventoryList().forEach(is -> {
				if(!cacheList.isSame(counter, is)){
					this.changed = true;
				}
				counter ++ ;
			});
//			if(AccessorySlotCapability.adapter.hasCapability(el)){
//				AccessorySlotCapability.adapter.getCapability(el).getEquippedList()
//				.getRawList().forEach(is ->{
//					if(!cacheList.isSame(counter, is)){
//						this.changed = true;
//					}
//					counter ++;
//				});
//			}
			return changed;
		}

		@Override
		public boolean hasInitialized() {

			return this.hasInitialized;
		}

		@Override
		public void setInitialized(boolean par1) {
			this.hasInitialized = par1;

		}
	}

	public static void onEquipChanged(EntityLivingBase el){
//		int amount = AbilityAPI.getHealAbilityAmount(el);
//		double healTime = el.getEntityAttribute(AdditionalStatus.NATURAL_HEAL_SPEED).getAttributeValue();
//		healTime = healTime + (double)amount;
		MinecraftForge.EVENT_BUS.post(new LivingEquipChangedEvent(el));

	}

	public static void registerEvents(){
		adapter.registerAttachEvent();

		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(adapter.hasCapability(e.getEntityLiving())){
//					UnsagaMod.logger.trace("update", this.getClass());
					if(!adapter.getCapability(e.getEntityLiving()).hasInitialized()){
						EquipmentCacheCapability.adapter.getCapability(e.getEntityLiving()).update(e.getEntityLiving());
						EquipmentCacheCapability.adapter.getCapability(e.getEntityLiving()).setInitialized(true);

					}
					if(EquipmentCacheCapability.adapter.getCapability(e.getEntityLiving()).hasChanged(e.getEntityLiving())){
//						UnsagaMod.logger.trace("changed", this.getClass());
						EquipmentCacheCapability.adapter.getCapability(e.getEntityLiving()).update(e.getEntityLiving());
					}

				}
//				UnsagaMod.logger.trace("update", adapter.hasCapability(e.getEntityLiving()));
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "equipmentCacheEvent";
			}}
		);
	}
}
