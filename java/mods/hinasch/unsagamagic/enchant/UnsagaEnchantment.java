package mods.hinasch.unsagamagic.enchant;

import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.ResourceLocation;

public class UnsagaEnchantment extends PropertyElementBase{


	Enchantment enchant;
	public UnsagaEnchantment(String name) {
		super(new ResourceLocation(name), name);
	}

	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaEnchantment.class;
	}

	public UnsagaEnchantment setEnchantmentSupplier(Enchantment e){
		this.enchant = e;
		this.enchant.setRegistryName(new ResourceLocation(UnsagaMod.MODID,this.getKey().getResourcePath().toString()));
		this.enchant.setName(this.getName());
		return this;
	}

	public Enchantment getEnchantment(){
		return this.enchant;
	}


}
