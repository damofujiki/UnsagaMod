package mods.hinasch.unsaga.core.entity;

import net.minecraft.nbt.NBTTagCompound;

public abstract class State {

	final boolean isRequireSerialize;

	public State(boolean par1){
		this.isRequireSerialize = par1;
	}

	public NBTTagCompound writeNBT(NBTTagCompound tag){
		return tag;
	}

	public NBTTagCompound readNBT(NBTTagCompound tag){
		return tag;
	}
}
