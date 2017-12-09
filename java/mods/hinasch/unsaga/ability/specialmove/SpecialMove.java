package mods.hinasch.unsaga.ability.specialmove;

import java.util.OptionalInt;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.AbilityBase;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialMoveBase;
import mods.hinasch.unsaga.damage.PairDamage;

public class SpecialMove extends AbilityBase{


	public static final SpecialMoveBase EMPTY = new SpecialMoveBase(InvokeType.BOW);

	int cost = 1;
	boolean isRequireTarget = false;
	PairDamage str = PairDamage.of(1, 1);
	OptionalInt coolingTime = OptionalInt.empty();
	public SpecialMove(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public SpecialMoveBase getAction(){
		if(SpecialMoveRegistry.instance().getAssociatedAction(this)!=null){
			return SpecialMoveRegistry.instance().getAssociatedAction(this);
		}
		return EMPTY;
	}

	public SpecialMove setRequireTarget(boolean par1){
		this.isRequireTarget = par1;
		return this;
	}
	public boolean isRequireTarget(){
		return this.isRequireTarget;
	}
	public OptionalInt getCoolingTime(){
		return this.coolingTime;
	}

	public int getCost() {
		// TODO 自動生成されたメソッド・スタブ
		return cost;
	}
	@Override
	public String getLocalized() {
		return HSLibs.translateKey("ability."+this.getPropertyName());
	}
	public PairDamage getStrength(){
		return this.str;
	}

	public SpecialMove setCoolingTime(int cool){
		this.coolingTime = OptionalInt.of(cool);
		return this;
	}

	public SpecialMove setCost(int cost) {
		this.cost = cost;
		return this;
	}

	public SpecialMove setStrength(float hp,float lp){
		this.str = PairDamage.of(hp, lp);
		return this;
	}
}
