package mods.hinasch.unsaga.core.entity;

import mods.hinasch.lib.registry.PropertyElementBase;
import net.minecraft.util.ResourceLocation;

public class StateProperty extends PropertyElementBase{


	public StateProperty(String name) {
		super(new ResourceLocation(name), name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return StateProperty.class;
	}

	public State createState(){
		return null;
	}

}
