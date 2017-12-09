package mods.hinasch.unsagamagic.enchant;

import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public class EnchantmentProperty extends PropertyElementBase implements Comparable<EnchantmentProperty>{


	Enchantment enchant;
	public EnchantmentProperty(String name) {
		super(new ResourceLocation(name), name);
	}

	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return EnchantmentProperty.class;
	}

	public EnchantmentProperty setEnchantmentSupplier(Enchantment e){
		this.enchant = e;
		this.enchant.setRegistryName(new ResourceLocation(UnsagaMod.MODID,this.getKey().getResourcePath().toString()));
		this.enchant.setName(this.getPropertyName());
		return this;
	}

	public Enchantment getEnchantment(){
		return this.enchant;
	}

	@Override
	public int compareTo(EnchantmentProperty o) {
		// TODO 自動生成されたメソッド・スタブ
		return this.getPropertyName().compareTo(o.getPropertyName());
	}


	public String getLocalized(){
		return HSLibs.translateKey("enchantment."+getPropertyName());
	}


}
