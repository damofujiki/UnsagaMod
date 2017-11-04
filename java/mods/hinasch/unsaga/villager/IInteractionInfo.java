package mods.hinasch.unsaga.villager;

import java.util.Optional;

import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import net.minecraft.entity.passive.EntityVillager;

public interface IInteractionInfo {

	public void setMerchant(Optional<EntityVillager> villager);
	public Optional<EntityVillager> getMerchant();
	public void setEntityChest(EntityUnsagaChestNew chest);
	public Optional<EntityUnsagaChestNew> getChest();
}
