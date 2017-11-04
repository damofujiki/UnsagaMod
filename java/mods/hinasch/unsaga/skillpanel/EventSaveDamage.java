package mods.hinasch.unsaga.skillpanel;

import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventSaveDamage {

	protected static SkillPanel savingDamage = SkillPanelRegistry.instance().thriftSaver;
	protected static final int BASE_PROB = 30;

	public static List<Consumer<EntityLivingBase>> events = Lists.newArrayList();

	static{
		events.add(living ->{
			if(living instanceof EntityPlayer){
				EntityPlayer ep = (EntityPlayer) living;
				World world = ep.getEntityWorld();

				if(SkillPanelAPI.hasPanel(ep	, savingDamage)){
					int level = SkillPanelAPI.getHighestPanelLevel(ep, savingDamage).getAsInt()+1;
					if(ep.getHeldItemMainhand()!=null){
						if(ep.getRNG().nextInt(100)<BASE_PROB + (8*level))
							UnsagaMod.logger.trace("節約にせいこう", "");
							ep.getHeldItemMainhand().damageItem(-1, ep);
					}
				}
			}
		});
	}

	@SubscribeEvent
	public void onAttack(LivingAttackEvent ev){
		this.saveDamage(ev.getEntityLiving());
	}
	@SubscribeEvent
	public void onBreakBlock(BreakEvent ev){
		this.saveDamage(ev.getPlayer());
	}

	public void saveDamage(EntityLivingBase living){
		for(Consumer<EntityLivingBase> event:events){
			event.accept(living);
		}
	}
}
