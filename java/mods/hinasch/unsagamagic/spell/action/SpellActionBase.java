package mods.hinasch.unsagamagic.spell.action;

import mods.hinasch.unsaga.common.specialaction.ActionBase;

public class SpellActionBase extends ActionBase<SpellCaster>{


//	@Override
//	public boolean isBenefical(){
//		boolean rt = false;
//		UnsagaMod.logger.trace(this.getClass().getName(), "calle");
//		rt = this.actionList.stream().anyMatch(in ->{
//			if(in instanceof ActionHealing){
//				return true;
//			}
//			if(in instanceof ActionStatusEffect){
//				UnsagaMod.logger.trace(this.getClass().getName(), "callee");
//				return !((ActionStatusEffect)in).isDebuff();
//			}
//			return false;
//		});
////		if(this.actionList.stream().anyMatch(in -> in instanceof ActionHealing)){
////			UnsagaMod.logger.trace(this.getClass().getName(), "ヒールでした");
////			return true;
////		}
////		if(this.actionList.stream().filter( in -> in instanceof ActionStatusEffect).map(in ->(ActionStatusEffect)in)
////				.anyMatch(in -> !in.isDebuff())){
////			UnsagaMod.logger.trace(this.getClass().getName(), "バフでした");
////			return true;
////		}
//		return rt;
//	}
}
