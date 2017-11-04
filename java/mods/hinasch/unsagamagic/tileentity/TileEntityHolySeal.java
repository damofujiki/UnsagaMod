package mods.hinasch.unsagamagic.tileentity;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityHolySeal extends TileEntity implements ITickable{

	XYZPos position;
	ItemStack spellBook;

	public ItemStack getBook(){
		return this.spellBook;
	}

	public void setBook(ItemStack par1){
		this.spellBook = par1;
	}
	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {

    	NBTTagCompound child = UtilNBT.compound();

    	spellBook.writeToNBT(child);
    	compound.setTag("book", child);
    	return super.writeToNBT(compound);
    }

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {
		super.readFromNBT(compound);
		NBTTagCompound child = (NBTTagCompound) compound.getTag("book");
		this.spellBook = ItemStack.loadItemStackFromNBT(child);
    }

	@Override
	public void update() {
		this.position = new XYZPos(this.getPos());
		this.worldObj.getEntitiesWithinAABB(EntityCreature.class, HSLibs.getBounding(position, 20.0D, 5.0D))
		.forEach(living ->{
			if(living.getMaxHealth() < 50.0D && living.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
				double vx = this.position.dx > living.posX ? -1 : 1;
				double vz = this.position.dz > living.posZ ? -1 : 1;
				living.addVelocity(vx*0.05D, 0, vz*0.05D);
			}

		});

	}
}
