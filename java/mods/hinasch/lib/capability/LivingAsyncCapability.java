package mods.hinasch.lib.capability;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.sync.AsyncEventPool;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingAsyncCapability {

	@CapabilityInject(ILivingAsync.class)
	public static Capability<ILivingAsync> CAPA;
	public static final String SYNC_ID = "asyncLivingEvent";


	public static interface ILivingAsync{
		public void addEvent(AsyncUpdateEvent e);
		public void updateEvents();
	}

	public static class DefaultImpl implements ILivingAsync{

		AsyncEventPool pool = AsyncEventPool.create();


		@Override
		public void updateEvents() {
			// TODO 自動生成されたメソッド・スタブ
			pool.update(null);
		}

		public void addEvent(AsyncUpdateEvent e){
			pool.addEvent(e);
		}


	}

	public static ICapabilityAdapterPlan<ILivingAsync> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return ILivingAsync.class;
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

	public static CapabilityAdapterFrame<ILivingAsync> adapterBase = HSLib.core().capabilityAdapterFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<ILivingAsync> adapter = adapterBase.createChildEntity("asyncLivingUpdateEvent");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof EntityLivingBase);
//		adapter.setRequireSerialize(true);
	}

	public static void addEvent(EntityLivingBase living,AsyncUpdateEvent e){
		if(LivingAsyncCapability.adapter.hasCapability(living)){
			LivingAsyncCapability.adapter.getCapability(living).addEvent(e);
		}
	}

	public static void registerEvents(){
		adapter.registerAttachEvent();
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				if(LivingAsyncCapability.adapter.hasCapability(e.getEntityLiving())){
					LivingAsyncCapability.adapter.getCapability(e.getEntityLiving()).updateEvents();
				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "AsyncLivingUpdateEvent";
			}}
		);
	}
}
