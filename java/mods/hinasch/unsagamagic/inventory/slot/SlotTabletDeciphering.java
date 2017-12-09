package mods.hinasch.unsagamagic.inventory.slot;

import mods.hinasch.unsagamagic.item.ItemTablet;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotTabletDeciphering extends Slot{

	public SlotTabletDeciphering(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_,
			int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    public int getSlotStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return this.isItemStackValid(par1ItemStack);
    }

    public static boolean isItemStackValid(ItemStack par1ItemStack)
    {
        Item item = (par1ItemStack == null ? null : par1ItemStack.getItem());
        return (item instanceof ItemTablet)? true : false;
    }
}
