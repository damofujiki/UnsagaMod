package mods.hinasch.unsaga.skillpanel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventPacifist {

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e){
		if(e.getSource().getEntity() instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer) e.getSource().getEntity();
			SkillPanelRegistry.instance().negativeSkills2.forEach(in ->{
				if(SkillPanelAPI.hasPanel(ep, in.first()) && in.second().test(e.getEntityLiving())){
					ep.attackEntityFrom(DamageSource.generic,5.0F);
				}
			});
		}
	}
}
