package mods.hinasch.unsaga.common.specialaction;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import net.minecraft.util.EnumActionResult;

public class ActionSelector implements IAction<SpecialMoveInvoker>{

	Map<InvokeType,IAction<SpecialMoveInvoker>> selectableMap = Maps.newHashMap();

	public ActionSelector addAction(InvokeType type,IAction<SpecialMoveInvoker> action){
		this.selectableMap.put(type, action);
		return this;
	}

	@Override
	public EnumActionResult apply(SpecialMoveInvoker context) {
		IAction<SpecialMoveInvoker> action = selectableMap.get(context.getInvokeType());
		if(action!=null){
			return action.apply(context);
		}
		return EnumActionResult.PASS;
	}

}
