package mods.hinasch.unsaga.villager;

import java.util.Optional;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.capability.StorageDummy;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class InteractionInfoCapability {

	@CapabilityInject(IInteractionInfo.class)
	public static Capability<IInteractionInfo> CAPA;
	public static final String SYNC_ID = "unsagaCustomer";

	public static ICapabilityAdapterPlan<IInteractionInfo> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IInteractionInfo.class;
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

	public static CapabilityAdapterFrame<IInteractionInfo> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IInteractionInfo> adapter = adapterBase.createChildEntity("unsagaCustomer");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof EntityPlayer);
	}

	public static class DefaultImpl implements IInteractionInfo{

		Optional<EntityVillager> villager = Optional.empty();
		Optional<EntityUnsagaChestNew> chest = Optional.empty();
		@Override
		public void setMerchant(Optional<EntityVillager> villager) {
			this.villager = villager;

		}

		@Override
		public Optional<EntityVillager> getMerchant() {
			// TODO 自動生成されたメソッド・スタブ
			return this.villager;
		}

		@Override
		public void setEntityChest(EntityUnsagaChestNew chest) {
			// TODO 自動生成されたメソッド・スタブ
			this.chest = Optional.of(chest);
		}

		@Override
		public Optional<EntityUnsagaChestNew> getChest() {
			// TODO 自動生成されたメソッド・スタブ
			return this.chest;
		}

	}

	public static void registerEvents(){
		adapter.registerAttachEvent();
	}
}
