package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.iface.IRequireInitializing;
import net.minecraft.entity.EntityLivingBase;

public interface IEquipmentCache extends IRequireInitializing {

	public void update(EntityLivingBase el);
	public boolean hasChanged(EntityLivingBase el);
}
