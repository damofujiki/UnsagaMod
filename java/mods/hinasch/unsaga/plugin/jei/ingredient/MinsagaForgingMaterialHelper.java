package mods.hinasch.unsaga.plugin.jei.ingredient;

import java.awt.Color;
import java.util.List;

import mezz.jei.api.ingredients.IIngredientHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.minsaga.MinsagaForging;
import mods.hinasch.unsaga.minsaga.MinsagaForging.Material;

public class MinsagaForgingMaterialHelper implements IIngredientHelper<MinsagaForging.Material>{

	public static final String ID = UnsagaMod.MODID+".materialMinsaga";


	@Override
	public List<Material> expandSubtypes(List<Material> ingredients) {
		// TODO 自動生成されたメソッド・スタブ
		return ingredients;
	}

	@Override
	public Material getMatch(Iterable<Material> ingredients, Material ingredientToMatch) {
		for(Material in:ingredients){
			if(in==ingredientToMatch){
				return in;
			}
		}
		return null;
	}

	@Override
	public String getDisplayName(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return ingredient.getPropertyName();
	}

	@Override
	public String getUniqueId(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return ID;
	}

	@Override
	public String getWildcardId(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return this.getUniqueId(ingredient);
	}

	@Override
	public String getModId(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaMod.MODID;
	}

	@Override
	public Iterable<Color> getColors(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public String getErrorInfo(Material ingredient) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
