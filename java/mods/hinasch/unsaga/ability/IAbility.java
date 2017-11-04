package mods.hinasch.unsaga.ability;

import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.registry.IPropertyElement;
import net.minecraft.nbt.NBTTagCompound;

public interface IAbility extends INBTWritable,IPropertyElement{

	public String getUnlocalizedName();
	public void writeToNBT(NBTTagCompound stream);
	public Class getParentClass();
	public String getLocalized();
}
