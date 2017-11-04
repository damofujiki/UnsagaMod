package mods.hinasch.unsaga.lp.event;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.lp.LifePoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LivingDeathEventLP {

	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent ev){
		UnsagaMod.logger.trace(this.getClass().toString(), "called");
		if(UnsagaMod.configHandler.isEnabledLifePointSystem()){
//			UnsagaMod.logger.trace(this.getClass().toString(), "called");
			if(LifePoint.adapter.hasCapability(ev.getEntityLiving())){
				EntityLivingBase victim = ev.getEntityLiving();
				int lp = LifePoint.adapter.getCapability(victim).getLifePoint();
//				UnsagaMod.logger.trace(this.getClass().toString(), "lp",lp,ev.getEntityLiving().getEntityWorld().toString());
				if(lp>0){ //LP０以上なら死なない
					ev.getEntityLiving().setHealth(0.1F); //healではダメ（Life0より上の時だけなので）
					ev.setCanceled(true);
				}
			}
		}
	}
}
