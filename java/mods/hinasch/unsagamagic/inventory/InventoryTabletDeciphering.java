package mods.hinasch.unsagamagic.inventory;

import mods.hinasch.lib.container.inventory.InventoryBase;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsagamagic.inventory.container.ContainerTabletDeciphering;
import mods.hinasch.unsagamagic.tileentity.TileEntityDecipheringTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InventoryTabletDeciphering extends InventoryBase{


	protected ContainerTabletDeciphering parentConatainer;
	protected TileEntityDecipheringTable table;
	protected World world;
	protected XYZPos pos;

	public InventoryTabletDeciphering(int size, World world,XYZPos pos,TileEntityDecipheringTable te) {
		super(size);
		this.world = world;
		this.table = te;
		this.pos = pos;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void setParent(ContainerTabletDeciphering parent){
		this.parentConatainer = parent;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO 自動生成されたメソッド・スタブ



		return table.getUser() ==entityplayer.getUniqueID() && entityplayer.getEntityWorld().getTileEntity(pos)!=null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {

		if(i==0){
			this.parentConatainer.clearSpellSlots();
		}
		return super.decrStackSize(i, j);
	}
	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {

		super.setInventorySlotContents(i, itemstack);
		if(i==0){
			this.parentConatainer.onTabletSlotChanged(this);
		}
	}
}
