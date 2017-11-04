package mods.hinasch.unsaga.villager.smith;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.unsaga.ability.AbilityRegistry;
import mods.hinasch.unsaga.ability.IAbility;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;

public class ByproductAbilityRegistry {

	private Map<UnsagaMaterial,IAbility> map = Maps.newHashMap();

	private static ByproductAbilityRegistry INSTANCE;

	public static ByproductAbilityRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new ByproductAbilityRegistry();
		}
		return INSTANCE;
	}

	protected ByproductAbilityRegistry(){

	}

	public IAbility getByproductAbility(UnsagaMaterial m){
		return map.get(m);
	}
	public boolean hasByproductAbility(UnsagaMaterial m){
		return this.map.containsKey(m);
	}
	public void register(){
		this.map.put(UnsagaMaterials.instance().carnelian, AbilityRegistry.instance().spellFire);
		this.map.put(UnsagaMaterials.instance().ravenite, AbilityRegistry.instance().spellWater);
		this.map.put(UnsagaMaterials.instance().opal, AbilityRegistry.instance().spellMetal);
		this.map.put(UnsagaMaterials.instance().topaz, AbilityRegistry.instance().spellEarth);
		this.map.put(UnsagaMaterials.instance().lazuli, AbilityRegistry.instance().spellWood);
		this.map.put(UnsagaMaterials.instance().darkStone, AbilityRegistry.instance().spellForbidden);
	}
}
