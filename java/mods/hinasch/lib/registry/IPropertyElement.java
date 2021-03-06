package mods.hinasch.lib.registry;

import net.minecraft.util.ResourceLocation;

public interface IPropertyElement<T> extends IPropertyElementFrame<ResourceLocation> {

	public String getPropertyName();
	public ResourceLocation getKey();
}
