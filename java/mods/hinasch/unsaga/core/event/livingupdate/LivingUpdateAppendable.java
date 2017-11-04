package mods.hinasch.unsaga.core.event.livingupdate;

import java.util.Map;
import java.util.function.Consumer;

import com.google.common.collect.Maps;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess;
import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess.IParentContainer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdateAppendable extends ILivingUpdateEvent{

	Map<IParentContainer,Consumer<LivingUpdateEvent>> hooks = Maps.newHashMap();
	public Map<IParentContainer,Consumer<LivingUpdateEvent>> getHooks(){
		return this.hooks;
	}

	@Override
	public void update(LivingUpdateEvent e) {
		for(Consumer<LivingUpdateEvent> func:this.hooks.values()){
			func.accept(e);
		}

	}

	@Override
	public String getName() {
		String childs = "";
		for(IParentContainer event:this.hooks.keySet()){
			childs += event.getParent().toString();
		}
		return "appended [childs:"+childs+"]";
	}

}
