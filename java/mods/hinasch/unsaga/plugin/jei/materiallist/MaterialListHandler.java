package mods.hinasch.unsaga.plugin.jei.materiallist;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mods.hinasch.unsaga.plugin.jei.JEIUnsagaPlugin;

public class MaterialListHandler implements IRecipeHandler<MaterialWrapper>{

	@Override
	public Class<MaterialWrapper> getRecipeClass() {
		// TODO 自動生成されたメソッド・スタブ
		return MaterialWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		// TODO 自動生成されたメソッド・スタブ
		return JEIUnsagaPlugin.ID_MATERIALLIST;
	}

	@Override
	public String getRecipeCategoryUid(MaterialWrapper recipe) {
		// TODO 自動生成されたメソッド・スタブ
		return this.getRecipeCategoryUid();
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(MaterialWrapper recipe) {
		// TODO 自動生成されたメソッド・スタブ
		return new MaterialListWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(MaterialWrapper recipe) {
		// TODO 自動生成されたメソッド・スタブ
		return recipe.getMaterial()!=null && !recipe.getStacks().isEmpty();
	}

}
