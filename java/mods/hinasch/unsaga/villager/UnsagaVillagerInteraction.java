package mods.hinasch.unsaga.villager;

import java.util.Optional;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.init.UnsagaGui;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UnsagaVillagerInteraction {

	@SubscribeEvent
	public void onInteract(EntityInteractSpecific e){
		if(e.getTarget() instanceof EntityVillager && UnsagaVillagerProfession.isUnsagaVillager((EntityVillager) e.getTarget())){
			e.setCanceled(true);

			EntityVillager villager = (EntityVillager) e.getTarget();
			if(!villager.isChild()){
				if(InteractionInfoCapability.adapter.hasCapability(e.getEntityPlayer())){
					InteractionInfoCapability.adapter.getCapability(e.getEntityPlayer()).setMerchant(Optional.of(villager));
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().merchant){
						this.openGui(e, UnsagaGui.Type.BARTERING);
					}
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().magicMerchant){
						this.openGui(e, UnsagaGui.Type.BARTERING);
					}
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().unsagaSmith){
						this.openGui(e, UnsagaGui.Type.SMITH);
					}
				}
			}


		}
	}

	private void openGui(EntityInteractSpecific e,IGuiAttribute gui){
		if(WorldHelper.isClient(e.getWorld())){
			HSLibs.openGuiFromClient(gui, XYZPos.createFrom(ClientHelper.getPlayer()));
		}
	}
}
