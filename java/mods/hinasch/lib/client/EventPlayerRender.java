package mods.hinasch.lib.client;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventPlayerRender {


	@SubscribeEvent
	public void onEntityRender(RenderPlayerEvent.Pre pre){
		if(pre.getRenderer() instanceof RenderPlayerDebug){

		}else{
			pre.setCanceled(true);
			RenderPlayerDebug renderer = new RenderPlayerDebug(pre.getRenderer().getRenderManager());
			renderer.doRender((AbstractClientPlayer) pre.getEntityPlayer(), pre.getX(), pre.getY(), pre.getZ(), 0, 0);
		}




	}
}
