package mods.hinasch.unsagamagic.tileentity;

import java.util.UUID;

import mods.hinasch.lib.tileentity.TileEntityBlockStateUpdatable;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.UnsagaMagic;
import mods.hinasch.unsagamagic.block.BlockDecipheringTable;
import mods.hinasch.unsagamagic.block.UnsagaMagicBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityDecipheringTable extends TileEntityBlockStateUpdatable{

	public ItemStack tablet;
	public UUID user;

	public UUID getUser() {

		return user;
	}

	public void setUser(EntityPlayer user) {
		if(user!=null){
			this.user = user.getUniqueID();
		}

	}

	public ItemStack getTablet() {
		return tablet;
	}

	public void setTablet(ItemStack tablet) {
		this.tablet = tablet;
//		if(WorldHelper.isClient(this.worldObj)){
		if(this.worldObj!=null){
			if(this.worldObj.getBlockState(getPos()).getBlock()==UnsagaMagicBlocks.instance().decipheringTable){
				IBlockState state = this.worldObj.getBlockState(getPos()).withProperty(BlockDecipheringTable.TABLET, this.getTablet()!=null);

				this.worldObj.setBlockState(getPos(), state);
			}

		}

//		}

	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {

    	if(tablet!=null){
    		UnsagaMod.logger.trace(this.getClass().getName(), "保存しました");
    		NBTTagCompound child = UtilNBT.compound();
    		tablet.writeToNBT(child);
    		compound.setTag("tablet", child);
    	}

    	return super.writeToNBT(compound);
    }

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
    	super.readFromNBT(compound);
    	if(compound.hasKey("tablet")){
      		UnsagaMod.logger.trace(this.getClass().getName(), "読み込みます");
    		NBTTagCompound comp = (NBTTagCompound) compound.getTag("tablet");
    		ItemStack stack = ItemStack.loadItemStackFromNBT(comp);
    		this.setTablet(stack);
    	}
    }

	@Override
	public Block getParentBlock() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaMagic.instance().blocks.decipheringTable;
	}
}
