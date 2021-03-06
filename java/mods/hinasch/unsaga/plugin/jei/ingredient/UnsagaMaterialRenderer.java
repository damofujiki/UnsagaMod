package mods.hinasch.unsaga.plugin.jei.ingredient;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredientRenderer;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.RenderHelperHS;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.plugin.jei.forgeunsaga.ForgeUnsagaRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;

public class UnsagaMaterialRenderer extends Gui implements IIngredientRenderer<UnsagaMaterial>{


	@Override
	public void render(Minecraft minecraft, int xPosition, int yPosition, UnsagaMaterial ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		ItemStack s = MaterialItemAssociatedRegistry.instance().getAssociatedStack(ingredient);
//		if(s== null){
//			s = new ItemStack(Items.APPLE);
//		}

		if(s==null){
			GlStateManager.enableAlpha();
			ClientHelper.bindTextureToTextureManager(ForgeUnsagaRecipeCategory.RES);
			RenderHelperHS.drawTexturedRect(xPosition, yPosition, 16, 168, 16, 16);

			return;
		}
		if (s != null) {
			RenderHelper.enableGUIStandardItemLighting();
			FontRenderer font = getFontRenderer(minecraft, ingredient);
			minecraft.getRenderItem().renderItemAndEffectIntoGUI(null, s, xPosition, yPosition);
			minecraft.getRenderItem().renderItemOverlayIntoGUI(font, s, xPosition, yPosition, null);
			GlStateManager.disableBlend();
			RenderHelper.disableStandardItemLighting();
		}
	}

	@Override
	public List<String> getTooltip(Minecraft minecraft, UnsagaMaterial ingredient) {
		List<String> list = Lists.newArrayList();
		UnsagaMaterials materials = UnsagaMaterials.instance();
		String alt = "";
		if(ingredient==materials.debris2 || ingredient==materials.steel2 || ingredient==materials.bone2 || ingredient==materials.tusk2){
			alt = "(Alternative)";
		}
		list.add(HSLibs.translateKey("jei."+UnsagaMaterialHelper.ID)+":"+ingredient.getLocalized()+alt);
		list.add("Tier:"+ingredient.rank);
		list.add("Base Price:"+ingredient.price);
		list.add("Weight:"+ingredient.weight);

		return list;
	}

	@Override
	public FontRenderer getFontRenderer(Minecraft minecraft, UnsagaMaterial ingredient) {
		return minecraft.fontRendererObj;
	}

}
