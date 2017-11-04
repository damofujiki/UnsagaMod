package mods.hinasch.unsaga.core.entity;

import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.ability.specialmove.action.StatePropertySpecialMove;
import mods.hinasch.unsaga.core.potion.StatePropertyOreDetecter;
import mods.hinasch.unsaga.core.potion.StatePropertyPotion;
import mods.hinasch.unsagamagic.spell.StatePropertySpellCast;

/** キャパビリティをさらに子にしたもの（全部これで置き換えられる気がしてきた）*/
public class StateRegistry extends PropertyRegistry<StateProperty>{

	protected static StateRegistry INSTANCE;

	public StateProperty statePotion = new StatePropertyPotion("statePotion");
	public StateProperty stateOreDetecter = new StatePropertyOreDetecter("oreDetecter");
	public StateProperty stateArrow  = new StatePropertyArrow("stateArrow");
	public StateProperty stateSpell = new StatePropertySpellCast("stateSpellPoint");
	public StateProperty stateSpecialMove = new StatePropertySpecialMove("stateSpecialMove");
	public static StateRegistry instance(){
		if(INSTANCE==null){
			INSTANCE = new StateRegistry();
		}
		return INSTANCE;
	}
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	@Override
	protected void registerObjects() {
		// TODO 自動生成されたメソッド・スタブ
		this.put(statePotion);
		this.put(stateOreDetecter);
		this.put(stateArrow);
		this.put(stateSpell);
		this.put(stateSpecialMove);
	}

}
