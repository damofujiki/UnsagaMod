package mods.hinasch.unsaga.common.specialaction;

import java.util.function.Function;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.common.specialaction.IActionPerformer.TargetType;
import net.minecraft.util.EnumActionResult;

public class ActionAsyncEvent<T extends IActionPerformer> implements IAction<T>{


	Function<T,AsyncUpdateEvent> eventGetter;

	@Override
	public EnumActionResult apply(T context) {
		if(context.getTargetType()==TargetType.POSITION){
			if(context.getTargetCoordinate().isPresent()){
				AsyncUpdateEvent event = this.eventGetter.apply(context);
				if(event!=null){
					HSLib.core().events.scannerEventPool.addEvent(event);
				}

				return EnumActionResult.SUCCESS;
			}

		}else{
			AsyncUpdateEvent event = this.eventGetter.apply(context);
			if(event!=null){
				HSLib.core().events.scannerEventPool.addEvent(event);
			}

			return EnumActionResult.SUCCESS;
		}


		return EnumActionResult.PASS;
	}

	public ActionAsyncEvent setEventFactory(Function<T,AsyncUpdateEvent> factory){
		this.eventGetter = factory;
		return this;
	}

}
