/**
 *
 */
package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.container.inventory.InventoryBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;

/**
 *
 */
public class InventoryMerchant extends InventoryBase{

	EntityPlayer customer;
	IMerchant merchant;
	public InventoryMerchant(int size,EntityPlayer ep,IMerchant merchant) {
		super(size);
		this.customer = ep;
		this.merchant = merchant;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ

		return this.merchant!=null && this.merchant.getCustomer() == entityplayer;
	}
}
