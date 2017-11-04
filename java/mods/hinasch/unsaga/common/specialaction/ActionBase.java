package mods.hinasch.unsaga.common.specialaction;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.util.EnumActionResult;

public abstract class ActionBase<T extends IActionPerformer> implements ISpecialActionBase<T>{

	protected List<IAction<T>> actionList = Lists.newArrayList();




	@Override
	public EnumActionResult perform(T context) {
		EnumActionResult result = EnumActionResult.PASS;
		for(IAction<T> act:this.actionList){
			result = act.apply(context);
			UnsagaMod.logger.trace(this.getClass().getName(), result,act);
		}
		return result;
	}

	public ActionBase<T> addAction(IAction<T> action,int num){
		this.actionList.add(num,action);
		return this;
	}
	public ActionBase<T> addAction(IAction<T> action){
		this.actionList.add(action);
		return this;
	}
	@Override
	public Consumer<T> getPrePerform() {

		return null;
	}



	public static interface IAction<V extends IActionPerformer> extends Function<V,EnumActionResult>{

		/** PASSを返すとコストは消費されない*/
		EnumActionResult apply(V t);
	}
}
