package mods.hinasch.unsaga.core.item.newitem.combat;

import mods.hinasch.unsaga.core.item.newitem.combat.ItemArmorUnsaga.PairArmorTexture;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.util.ToolCategory;

public class MaterialArmorTextureSetting {

	static UnsagaMaterials m = UnsagaMaterials.instance();

	public static enum RenderSize{
		NORMAL,THIN;
	}
	public static final PairArmorTexture THIN_ARMOR = new PairArmorTexture("armor","armor2").setRenderSize(RenderSize.THIN);
	public static final PairArmorTexture FEATHER = new PairArmorTexture("feather","armor2").setRenderSize(RenderSize.THIN);
	public static final PairArmorTexture FUR = new PairArmorTexture("fur","armor2");
	public static final PairArmorTexture WOOD = new PairArmorTexture("wood","armor2");
	public static final PairArmorTexture BOOTS = new PairArmorTexture("fur","armor2").setRenderSize(RenderSize.THIN);
	public static final PairArmorTexture CIRCLET = new PairArmorTexture("circlet","armor2").setRenderSize(RenderSize.THIN);
	public static final PairArmorTexture HEADBAND = new PairArmorTexture("headband","armor2").setRenderSize(RenderSize.THIN);
	public static final PairArmorTexture MASK = new PairArmorTexture("mask","armor2");
	public static final PairArmorTexture TRANSPARENT = new PairArmorTexture("nothing","nothing");
	public static void register(){
		m.silk.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.velvet.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.cotton.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.liveSilk.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.silver.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.diamond.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.sapphire.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.ruby.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.obsidian.addSpecialArmorTexture(ToolCategory.HELMET,MASK);
		m.silk.addSpecialArmorTexture(ToolCategory.LEGGINS,TRANSPARENT);
		m.velvet.addSpecialArmorTexture(ToolCategory.LEGGINS,TRANSPARENT);
		m.cotton.addSpecialArmorTexture(ToolCategory.LEGGINS,TRANSPARENT);
		m.liveSilk.addSpecialArmorTexture(ToolCategory.LEGGINS,TRANSPARENT);
		m.hydraLeather.addSpecialArmorTexture(ToolCategory.HELMET,CIRCLET);
		m.crocodileLeather.addSpecialArmorTexture(ToolCategory.HELMET,HEADBAND);
		m.snakeLeather.addSpecialArmorTexture(ToolCategory.HELMET,HEADBAND);
		m.fur.addSpecialArmorTexture(ToolCategory.HELMET, FUR);
		m.feather.addSpecialArmorTexture(ToolCategory.HELMET, FEATHER);
		m.crocodileLeather.addSpecialArmorTexture(ToolCategory.BOOTS,BOOTS);
		m.snakeLeather.addSpecialArmorTexture(ToolCategory.BOOTS,BOOTS);
		m.fur.addSpecialArmorTexture(ToolCategory.BOOTS,BOOTS);
		m.hydraLeather.addSpecialArmorTexture(ToolCategory.BOOTS,BOOTS);
		addToCategory(m.categoryClothes,ToolCategory.ARMOR,THIN_ARMOR);
		addToCategory(m.categoryWoods,ToolCategory.ARMOR,WOOD);
		addToCategory(m.categoryWoods,ToolCategory.ARMOR,WOOD);
		addToCategory(m.categoryWoods,ToolCategory.HELMET,WOOD);
		addToCategory(m.categoryWoods,ToolCategory.BOOTS,WOOD);
	}

	public static void addToCategory(UnsagaMaterials.Category cate,ToolCategory tool,PairArmorTexture tex){
		for(UnsagaMaterial m:cate.getChildMaterials()){
			m.addSpecialArmorTexture(tool, tex);
		}
	}
}
