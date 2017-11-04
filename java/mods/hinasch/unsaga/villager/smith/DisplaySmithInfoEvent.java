package mods.hinasch.unsaga.villager.smith;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.core.client.gui.GuiSmithUnsaga;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.SuitableLists;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialTool;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * TIPSへの表示
 */
public class DisplaySmithInfoEvent {


	@SubscribeEvent
	public void onDisplayToolTip(ItemTooltipEvent ev){
		ItemStack stack = ev.getItemStack();
		if(ClientHelper.getCurrentGui() instanceof GuiSmithUnsaga){
			GuiSmithUnsaga gui = (GuiSmithUnsaga) ClientHelper.getCurrentGui();
			ToolCategory selectedCategory = ToolCategory.toolArray.get(gui.getCurrentCategory());
			if(ItemUtil.isItemStackPresent(stack)){
				UnsagaMaterial material = null;
				if(MaterialItemAssociatedRegistry.instance().getMaterialFromStack(stack).isPresent()){
					material = MaterialItemAssociatedRegistry.instance().getMaterialFromStack(stack).get();
				}
				if(UnsagaMaterialTool.adapter.hasCapability(stack)){

					material = UnsagaMaterialTool.adapter.getCapability(stack).getMaterial();

				}

				if(material!=null){
					ev.getToolTip().add(UnsagaTextFormatting.SIGNIFICANT+HSLibs.translateKey("gui.unsaga.smith.materialInfo", material.getLocalized()));
					if(SuitableLists.instance().getSuitableList(selectedCategory).contain(material)){
						ev.getToolTip().add(UnsagaTextFormatting.SIGNIFICANT+HSLibs.translateKey("gui.unsaga.smith.suitableInfo", selectedCategory.getLocalized()));
					}

				}

				if(ValidPaymentRegistry.getValue(stack).isPresent()){
					ev.getToolTip().add(UnsagaTextFormatting.SIGNIFICANT+HSLibs.translateKey("gui.unsaga.smith.paymentInfo"));
				}
			}
		}

	}
}
