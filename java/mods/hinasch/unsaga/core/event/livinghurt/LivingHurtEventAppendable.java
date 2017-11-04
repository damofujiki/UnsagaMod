package mods.hinasch.unsaga.core.event.livinghurt;

import java.util.Map;
import java.util.function.Function;

import com.google.common.collect.Maps;

import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess.EventContainer;
import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess.IParentContainer;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingHurtEventAppendable extends LivingHurtEventUnsagaBase{

	Map<IParentContainer,Function<EventContainer,Float>> hooks = Maps.newHashMap();

	@Override
	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		// TODO 自動生成されたメソッド・スタブ
		return !hooks.isEmpty();
	}

	@Override
	public String getName() {
		String childs = "";
		for(IParentContainer event:this.hooks.keySet()){
			childs += event.getParent().toString();
		}
		return "appended childs:["+childs+"]";
	}

	@Override
	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		for(Function<EventContainer,Float> event:hooks.values()){
			e.setAmount(event.apply(new EventContainer(e, dsu, e.getAmount(), dsu.getEntity())));
		}
		return dsu;
	}

	public Map<IParentContainer,Function<EventContainer,Float>> getHooks(){
		return this.hooks;
	}
}
