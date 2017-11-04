package mods.hinasch.unsaga.ability.specialmove.action;

import java.util.EnumSet;

import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.common.specialaction.ActionBase;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;

public class SpecialMoveBase extends ActionBase<SpecialMoveInvoker>{

	final EnumSet<InvokeType> invokeTypes;

	public SpecialMoveBase(InvokeType type){
		this.invokeTypes = EnumSet.of(type);
	}

	public SpecialMoveBase(InvokeType type,InvokeType type2){
		this.invokeTypes = EnumSet.of(type,type2);
	}
	public EnumSet<InvokeType> getInvokeTypes(){
		return this.invokeTypes;
	}

	public static ISpecialMoveAction of(ISpecialMoveAction invoker){
		return invoker;
	}

	@Override
	public SpecialMoveBase addAction(IAction<SpecialMoveInvoker> action){
		this.actionList.add(action);
		return this;
	}

	public static SpecialMoveBase create(InvokeType type){
		return new SpecialMoveBase(type);
	}

	public static SpecialMoveBase create(InvokeType type,InvokeType type2){
		return new SpecialMoveBase(type,type2);
	}
	public static interface ISpecialMoveAction extends IAction<SpecialMoveInvoker>{

	}
	@Override
	public boolean isBenefical() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}
}
