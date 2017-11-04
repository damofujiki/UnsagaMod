package mods.hinasch.unsaga.core.inventory;

import mods.hinasch.lib.container.inventory.InventoryBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class InventorySmithUnsaga extends InventoryBase {


	protected final IMerchant theSmith;
	public InventorySmithUnsaga(EntityPlayer ep, IMerchant theMerchant) {
		super(4);
		this.theSmith = theMerchant;

	}

	public ItemStack getPayment(){
		return this.getStackInSlot(0);
	}

	public ItemStack getBaseItem(){
		return this.getStackInSlot(1);
	}

	public ItemStack getMaterial(){
		return this.getStackInSlot(2);
	}

	public ItemStack getForged(){
		return this.getStackInSlot(3);
	}



	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsaga.smith.inventory";
	}



	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}



	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ

		return this.theSmith!=null && this.theSmith.getCustomer() == entityplayer;
	}




}
