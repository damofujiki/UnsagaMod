package mods.hinasch.unsaga.core.item.newitem.misc;

import mods.hinasch.lib.item.ItemNoFunction;
import mods.hinasch.lib.item.ItemProperty;
import mods.hinasch.lib.item.PropertyRegistryItem;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry.RawMaterial;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemRawMaterial extends ItemNoFunction implements IItemColor{

	public ItemRawMaterial(String prefix) {
		super(prefix);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public PropertyRegistryItem<? extends ItemProperty> getItemProperties() {
		// TODO 自動生成されたメソッド・スタブ
		return RawMaterialItemRegistry.instance();
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		if(RawMaterialItemRegistry.instance().getPropertyFromStack(stack)!=null){
			RawMaterial prop = RawMaterialItemRegistry.instance().getPropertyFromStack(stack);
			if(prop.isItemColored()){
				return prop.getAssociatedMaterial().getMaterialColor();
			}
		}
		return Statics.COLOR_NONE;
	}

//	@Override
//	public String getUnlocalizedName(ItemStack par1ItemStack)
//	{
//
//		if(RawMaterialItemRegistry.instance().getPropertyFromStack(par1ItemStack)!=null){
//			return HSLibs.translateKey("material."+RawMaterialItemRegistry.instance().getPropertyFromStack(par1ItemStack).getAssociatedMaterial().getName().replace(".name", ""));
//		}
//		return "null";
//	}
}
