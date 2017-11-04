package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.item.RecipeUtilNew;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {


	MiscItemPropertyGroup miscItems = UnsagaModAdditional.miscItems;
	public void register(){
		RecipeUtilNew.RecipeShapeless recipe = new RecipeUtilNew.RecipeShapeless();
		recipe.addRecipeOre("dustSilver");
		recipe.addRecipeOre("dustGold");
		recipe.setOutput(miscItems.DUST_ELECTRUM.getItemStack(1));
		recipe.register();
		recipe.clear();

		GameRegistry.addSmelting(miscItems.DUST_ELECTRUM.getItemStack(1), miscItems.INGOT_ELECTRUM.getItemStack(1),0.0F);


		recipe.setOutput(miscItems.SALT.getItemStack(9));
		recipe.addRecipeOre("rockSalt");
		recipe.register();
		recipe.clear();



		RecipeUtilNew.RecipeShaped recipe2 = new RecipeUtilNew.RecipeShaped();

		recipe2.setOutput(new ItemStack(UnsagaModAdditional.ores.ROCKSALT.getBlock(),1));
		recipe2.setBase(RecipeUtilNew.Recipes.FILLED);
		recipe2.addAssociation('B', miscItems.SALT.getItemStack(1));
		recipe2.register();
	}
}
