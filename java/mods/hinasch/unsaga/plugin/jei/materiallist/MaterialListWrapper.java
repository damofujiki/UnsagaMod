package mods.hinasch.unsaga.plugin.jei.materiallist;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.minsaga.MinsagaForging;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class MaterialListWrapper implements IRecipeWrapper {

	public MaterialWrapper wrapper;
	public MaterialListWrapper(MaterialWrapper wrapper){
		this.wrapper = wrapper;
	}
	@Override
	public void getIngredients(IIngredients ingredients) {
		// TODO 自動生成されたメソッド・スタブ
		if(wrapper.getMaterial() instanceof UnsagaMaterial){
			ingredients.setInput(UnsagaMaterial.class, (UnsagaMaterial)wrapper.getMaterial());
		}
		if(wrapper.getMaterial() instanceof MinsagaForging.Material){
			ingredients.setInput(MinsagaForging.Material.class, (MinsagaForging.Material)wrapper.getMaterial());
		}

		List<List<ItemStack>> listContainer = Lists.newArrayList();
		listContainer.add(wrapper.getStacks());
		ingredients.setInputLists(ItemStack.class, listContainer);
	}

	public boolean isMinsagaForging(){
		return this.wrapper.getMaterial() instanceof MinsagaForging.Material;
	}
	@Override
	public List getInputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List getOutputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void drawAnimations(Minecraft minecraft, int recipeWidth, int recipeHeight) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public List<String> getTooltipStrings(int mouseX, int mouseY) {
		// TODO 自動生成されたメソッド・スタブ
		return Lists.newArrayList();
	}

	@Override
	public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
