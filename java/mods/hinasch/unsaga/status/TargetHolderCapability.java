package mods.hinasch.unsaga.status;

import java.util.Optional;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.capability.StorageDummy;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TargetHolderCapability {


	@CapabilityInject(ITargetHolder.class)
	public static Capability<ITargetHolder> CAPA;
	public static final String SYNC_ID = "unsagaTargetHolder";

	public static ICapabilityAdapterPlan<ITargetHolder> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return ITargetHolder.class;
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

	public static CapabilityAdapterFrame<ITargetHolder> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<ITargetHolder> adapter = adapterBase.createChildEntity("unsagaTargetHolder");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof EntityLivingBase);
//		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements ITargetHolder{

		Optional<EntityLivingBase> target = Optional.empty();

		@Override
		public void updateTarget(EntityLivingBase target) {
//			UnsagaMod.logger.trace("[target holder]"+owner.getName()+" targetted "+target.getName()+":"+WorldHelper.debug(owner.worldObj));
			this.target = Optional.of(target);
		}

		@Override
		public Optional<EntityLivingBase> getTarget() {
			// TODO 自動生成されたメソッド・スタブ
			return this.target;
		}

		@Override
		public double getTargetDistance(EntityLivingBase other) {
			// TODO 自動生成されたメソッド・スタブ
			return target.isPresent() ? target.get().getDistanceToEntity(other) : 0.0D;
		}



	}


	public static void registerEvents(){

		adapter.registerAttachEvent();
	}
}
