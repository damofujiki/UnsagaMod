package mods.hinasch.unsagamagic.inventory;

import mods.hinasch.lib.container.inventory.InventoryBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventoryBlender extends InventoryBase{

	public InventoryBlender() {
		super(9);

	}


	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}


	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}


	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "inventory.blender";
	}

	@Override
	public boolean hasCustomName() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}


}
