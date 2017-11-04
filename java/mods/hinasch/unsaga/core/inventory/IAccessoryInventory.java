package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.iface.IRequireInitializing;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;

public interface IAccessoryInventory extends IRequireInitializing,ISyncCapability{




	public void setStacks(ItemStackList list);
	public ItemStackList getEquippedList();
	public boolean hasEmptySlot();

}
