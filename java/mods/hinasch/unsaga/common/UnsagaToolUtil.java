package mods.hinasch.unsaga.common;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import net.minecraft.item.ItemStack;

public class UnsagaToolUtil {


	public static UnsagaMaterial def = UnsagaMod.core.materialsNew.stone;
    public static UnsagaMaterial getMaterial(ItemStack is){
    	if(UnsagaMaterialCapability.adapter.hasCapability(is)){
    		return UnsagaMaterialCapability.adapter.getCapability(is).getMaterial();
    	}
    	return def;
    }

    public static void setMaterial(ItemStack is,UnsagaMaterial m){
    	if(UnsagaMaterialCapability.adapter.hasCapability(is)){
    		UnsagaMaterialCapability.adapter.getCapability(is).setMaterial(m);
    	}
    }

    public static float getEfficiencyOnProperMaterial(ItemStack is){
    	if(UnsagaMaterialCapability.adapter.hasCapability(is)){
    		return UnsagaMaterialCapability.adapter.getCapability(is).getMaterial().getToolMaterial().getEfficiencyOnProperMaterial();
    	}
    	return def.getToolMaterial().getEfficiencyOnProperMaterial();
    }

    public static int getItemEnchantability(ItemStack is){
    	if(UnsagaMaterialCapability.adapter.hasCapability(is)){
    		return UnsagaMaterialCapability.adapter.getCapability(is).getMaterial().getToolMaterial().getEnchantability();
    	}
    	return def.getToolMaterial().getEnchantability();
    }
}
