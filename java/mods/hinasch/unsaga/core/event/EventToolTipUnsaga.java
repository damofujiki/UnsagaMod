package mods.hinasch.unsaga.core.event;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.common.ComponentUnsagaWeapon.IPredicateDisplayInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventToolTipUnsaga {



	public static List<IComponentDisplayInfo> list = Lists.newArrayList();
	@SubscribeEvent
	public void toolTipHooks(ItemTooltipEvent ev){


		Collections.sort(list);

		for(IComponentDisplayInfo comp:list){
			if(comp.predicate(ev.getItemStack(), ev.getEntityPlayer(), ev.getToolTip(), false)){
				comp.addInfo(ev.getItemStack(), ev.getEntityPlayer(), ev.getToolTip(), false);
			}
		}

	}
    public static abstract class ComponentDisplayInfo implements  IComponentDisplayInfo<ComponentDisplayInfo>{

    	IPredicateDisplayInfo predicate;
    	int priority;


    	public ComponentDisplayInfo(int priority,IPredicateDisplayInfo predicate){

    		this.predicate =predicate;
    		this.priority = priority;

    	}
		@Override
		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
			return this.predicate.predicate(is, ep, dispList, par4);
		}

	    public int compareTo(ComponentDisplayInfo o){
	    	return Integer.valueOf(priority).compareTo(o.priority);
	    }


    }
    public static interface IComponentDisplayInfo<T> extends Comparable<T>{

    	public boolean predicate(ItemStack is, EntityPlayer ep, final List dispList, boolean par4);

    	public void addInfo(ItemStack is, EntityPlayer ep, final List dispList, boolean par4);
    }
}
