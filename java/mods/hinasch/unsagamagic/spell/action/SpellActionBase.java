package mods.hinasch.unsagamagic.spell.action;

import mods.hinasch.unsaga.common.specialaction.ActionBase;
import mods.hinasch.unsaga.common.specialaction.ActionHealing;
import mods.hinasch.unsaga.common.specialaction.ActionStatusEffect;

public class SpellActionBase extends ActionBase<SpellCaster>{


	public boolean isBenefical(){
		if(this.actionList.stream().anyMatch(in -> in instanceof ActionHealing)){
			return true;
		}
		if(this.actionList.stream().filter( in -> in instanceof ActionStatusEffect).map(in ->(ActionStatusEffect)in)
				.anyMatch(in -> !in.isDebuff())){
			return true;
		}
		return false;
	}
}
