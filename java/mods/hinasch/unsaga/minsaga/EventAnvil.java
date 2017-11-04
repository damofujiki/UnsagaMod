package mods.hinasch.unsaga.minsaga;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.minsaga.ForgingCapability.ForgeAttribute;
import mods.hinasch.unsaga.minsaga.ForgingCapability.IMinsagaForge;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventAnvil {


	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent e){


//		UnsagaMod.logger.trace("anvil", "called");
		if(e.getLeft()!=null && e.getRight()!=null){
			if(ForgingCapability.adapter.hasCapability(e.getLeft())){
				IMinsagaForge capa = ForgingCapability.adapter.getCapability(e.getLeft());
				if(capa.isForged()){

					ForgeAttribute attribute = capa.getCurrentForge();
					int cost = e.getCost() - capa.getCostModifier();
//					e.setCanceled(true);

					UnsagaMod.logger.trace("material", attribute.getForgedMaterial(),e.getRight(),e.getCost(),cost);
					if(attribute.getForgedMaterial().isMaterialItem(e.getRight())){

						e.setCost(MathHelper.clamp_int(cost, 1, 256));
						int damage = e.getLeft().getItemDamage() - attribute.getForgedMaterial().getRepairDamage();
						ItemStack newStack = e.getLeft().copy();
						newStack.setItemDamage(MathHelper.clamp_int(damage, 0, 65535));
						newStack.setRepairCost(newStack.getRepairCost()+1);
						e.setOutput(newStack);

					}else{
						e.setCanceled(true);
					}
				}
			}
		}
	}
}
