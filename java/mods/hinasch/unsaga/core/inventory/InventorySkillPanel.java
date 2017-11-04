package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.container.inventory.InventoryBase;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import net.minecraft.entity.player.EntityPlayer;

public class InventorySkillPanel extends InventoryBase{


	public InventorySkillPanel() {
		super(9);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public ItemStackList getPlayerPanels(){
		ItemStackList list = new ItemStackList(7);

		for(int i=0;i<7;i++){
			list.setStack(i, !ItemUtil.isItemStackNull(this.theInventory.get(i)) ? this.theInventory.get(i).copy() : ItemUtil.EMPTY_STACK);

		}
		return list;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ

		return entityplayer.openContainer != entityplayer.inventoryContainer;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	public void applyItemStackList(ItemStackList panelList){
		if(panelList!=null){
			for(int i=0;i<7;i++){
				if(ItemUtil.isItemStackPresent(panelList.get(i))){
					this.setInventorySlotContents(i, panelList.get(i));
				}

			}
		}
	}
}
