package mods.hinasch.unsaga.core.event;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.status.UnsagaXPCapability;
import mods.hinasch.unsaga.status.UnsagaXPCapability.IUnsagaXP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventGetSkillPoint {

	Item knowledgeBook = UnsagaMod.magic.items.knowledgeBook;
	@SubscribeEvent
	public void onExpPickUp(PlayerPickupXpEvent ev){
		if(UnsagaXPCapability.hasCapability(ev.getEntityPlayer())){
			IUnsagaXP capa = UnsagaXPCapability.getCapability(ev.getEntityPlayer());
			int xpValue = ev.getOrb().getXpValue();
			capa.addSkillPointPiece(xpValue*UnsagaMod.configHandler.getSkillXPMultiply());
			if(ev.getEntityPlayer().inventory.hasItemStack(new ItemStack(knowledgeBook,1))){
				capa.addDecipheringPoint(xpValue*UnsagaMod.configHandler.getDecipheringXPMultiply());
			}

		}
	}
}
