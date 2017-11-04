package mods.hinasch.unsaga.core.event.livingupdate;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.lp.LPLogicManager;
import mods.hinasch.unsaga.lp.LifePoint.ILifePoint;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdateLPEvent extends ILivingUpdateEvent{



	@Override
	public void update(LivingUpdateEvent e) {
		if(UnsagaMod.configHandler.isEnabledLifePointSystem()){
			if(LPLogicManager.hasCapability(e.getEntityLiving())){
				ILifePoint capa = LPLogicManager.getCapability(e.getEntityLiving());
				if(capa.getHurtInterval()>0){
					if(e.getEntityLiving().ticksExisted % 20 * 12 == 0){
						capa.setHurtInterval(capa.getHurtInterval() -1);
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "LP Hurt Interval decr. Event";
	}
}
