package mods.hinasch.unsagamagic.inventory.container;

import mods.hinasch.lib.container.AbstractContainerBinder;
import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.misc.Tuple;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.inventory.InventorySpellBookBinder;
import mods.hinasch.unsagamagic.inventory.slot.SlotBlender;
import mods.hinasch.unsagamagic.item.ItemSpellBookBinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpellBookBinder extends AbstractContainerBinder{

	public ContainerSpellBookBinder(EntityPlayer ep, ItemStack is) {
		super(ep, is, new InventorySpellBookBinder(ep.worldObj.isRemote, is, ItemSpellBookBinder.MAX_BINDER));
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public Slot getBinderSlot(IInventory inv, int par1, int par2, int par3) {
		// TODO 自動生成されたメソッド・スタブ
		return new SlotBlender(inv, par1, par2, par3);
	}
	@Override
    public ItemStack transferStackInSlot(Slot slot)
    {
		InventoryHandler hinvEp = InventoryHandler.create(this.invEP);
		InventoryHandler hinvBinder = InventoryHandler.create(this.invBinder);

		if(slot instanceof SlotBlender){
			if(slot.getStack()!=null && hinvEp.getFirstEmptySlotNum().isPresent()){
				hinvEp.swapFirstEmptySlot(slot);
			}
		}else{
			if(slot.getStack()!=null && hinvBinder.getFirstEmptySlotNum().isPresent()){
				if(SlotBlender.isItemStackValid(slot.getStack())){
					hinvBinder.mergeSlot(slot, hinvBinder.getFirstEmptySlotNum().get());
				}

			}
		}
        return super.transferStackInSlot(slot);
    }

	@Override
	public Tuple getBinderItem() {
		// TODO 自動生成されたメソッド・スタブ
		return Tuple.of(UnsagaMod.magic.items.spellBookBinder, null);
	}


}
