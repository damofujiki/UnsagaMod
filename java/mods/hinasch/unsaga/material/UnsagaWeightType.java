package mods.hinasch.unsaga.material;

import mods.hinasch.unsaga.common.ComponentUnsagaWeapon;

public enum UnsagaWeightType {



	HEAVY,LIGHT;

	public String getName(){
		return this == HEAVY ? "Heavy" : "Light";
	}
	public static UnsagaWeightType fromWeight(int weight){
		if(ComponentUnsagaWeapon.THRESHOLD_WEIGHT<weight){
			return HEAVY;
		}
		return LIGHT;
	}
}
