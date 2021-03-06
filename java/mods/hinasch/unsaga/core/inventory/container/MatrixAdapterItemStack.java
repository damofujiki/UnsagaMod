package mods.hinasch.unsaga.core.inventory.container;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsaga.core.item.newitem.SkillPanelCapability;
import mods.hinasch.unsaga.skillpanel.SkillPanel;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class MatrixAdapterItemStack extends MatrixAdapter<ItemStack>{

	IInventory inv;

	public MatrixAdapterItemStack(IInventory inv){
		this.inv = inv;
	}
	@Override
	public ItemStack getMatrix(int x, int y) {
		int j = y==0 ? 0 : y==1 ? 2 : y==2 ? 5: 0;
		ItemStack is = inv.getStackInSlot(j+x);
		return is;
	}

	@Override
	public boolean isSame(ItemStack o1, ItemStack o2) {
		if(ItemUtil.isItemStackPresent(o1,o2)){
			if(SkillPanelCapability.adapter.hasCapability(o1) && SkillPanelCapability.adapter.hasCapability(o2)){
				SkillPanel panel1 = SkillPanelCapability.adapter.getCapability(o1).getPanel();
				SkillPanel panel2 = SkillPanelCapability.adapter.getCapability(o2).getPanel();
				return panel1.getIconType()==panel2.getIconType();
			}

		}

		return false;
	}
}
