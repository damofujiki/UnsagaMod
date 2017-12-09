package mods.hinasch.unsaga.plugin.jei;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import jline.internal.Preconditions;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.minsaga.MinsagaForging;
import mods.hinasch.unsaga.plugin.jei.forgeunsaga.ForgeUnsagaRecipeCategory;
import mods.hinasch.unsaga.plugin.jei.forgeunsaga.ForgeUnsagaRecipeHandler;
import mods.hinasch.unsaga.plugin.jei.ingredient.ElementIngredientHelper;
import mods.hinasch.unsaga.plugin.jei.ingredient.ElementIngredientRenderer;
import mods.hinasch.unsaga.plugin.jei.ingredient.ElementPair;
import mods.hinasch.unsaga.plugin.jei.ingredient.MinsagaForgingMaterialHelper;
import mods.hinasch.unsaga.plugin.jei.ingredient.MinsagaForgingMaterialRenderer;
import mods.hinasch.unsaga.plugin.jei.ingredient.SpellIngredientHelper;
import mods.hinasch.unsaga.plugin.jei.ingredient.SpellIngredientRenderer;
import mods.hinasch.unsaga.plugin.jei.ingredient.SpellWrapper;
import mods.hinasch.unsaga.plugin.jei.ingredient.UnsagaMaterialHelper;
import mods.hinasch.unsaga.plugin.jei.ingredient.UnsagaMaterialRenderer;
import mods.hinasch.unsaga.plugin.jei.magicblend.MagicBlendRecipeCategory;
import mods.hinasch.unsaga.plugin.jei.magicblend.MagicBlendRecipeHandler;
import mods.hinasch.unsaga.plugin.jei.magicblend.RecipeBlend;
import mods.hinasch.unsaga.plugin.jei.materiallist.MaterialListCategory;
import mods.hinasch.unsaga.plugin.jei.materiallist.MaterialListHandler;
import mods.hinasch.unsaga.plugin.jei.materiallist.MaterialWrapper;
import mods.hinasch.unsaga.villager.smith.MaterialTransformRegistry;
import mods.hinasch.unsaga.villager.smith.MaterialTransformRegistry.MaterialTransform;
import mods.hinasch.unsagamagic.spell.SpellBlend;
import mods.hinasch.unsagamagic.spell.SpellRegistry;

@JEIPlugin
public class JEIUnsagaPlugin extends BlankModPlugin{

	public static final String ID_CUSTOMTOOL = UnsagaMod.MODID+".customizeTool";
	public static final String ID_MAGICBLEND = UnsagaMod.MODID+".magicBlend";
	public static final String ID_MATERIALLIST = UnsagaMod.MODID+".materialList";

	public JEIUnsagaPlugin(){

	}
    @Override
    public void register(@Nonnull IModRegistry registry) {
    	IJeiHelpers helper = registry.getJeiHelpers();

    	registry.addRecipeCategories(new ForgeUnsagaRecipeCategory(helper.getGuiHelper()),new MagicBlendRecipeCategory(helper.getGuiHelper())
    			,new MaterialListCategory(helper.getGuiHelper()));
    	registry.addRecipeHandlers(new ForgeUnsagaRecipeHandler(),new MagicBlendRecipeHandler()
    			,new MaterialListHandler());
    	List<MaterialTransform> recipeMaterial = Lists.newArrayList();
    	recipeMaterial.addAll(MaterialTransformRegistry.instance().getList());

    	List<RecipeBlend> recipeSpell = Lists.newArrayList();
    	for(SpellBlend spell:SpellRegistry.instance().getBlendSpells()){
    		Preconditions.checkNotNull(spell);
    		spell.getRequireElementTable().entrySet().forEach(in ->{
    			RecipeBlend recipe = new RecipeBlend(in.getKey(),in.getValue(),spell);
    			recipeSpell.add(recipe);
    		});
    	}

    	List<MaterialWrapper> materials = Lists.newArrayList();
    	UnsagaMaterials.instance().getProperties().forEach(in ->{
    		materials.add(new MaterialWrapper(in));
    	});
    	MinsagaForging.instance().registry().forEach(in ->{
    		materials.add(new MaterialWrapper(in));
    	});
    	Collections.sort(materials);

//    	com.google.common.base.Preconditions.checkArgument(recipes.stream().anyMatch(in -> in instanceof RecipeBlend));
    	registry.addRecipes(recipeMaterial);
    	registry.addRecipes(recipeSpell);
    	registry.addRecipes(materials);
    }

	@Override
	public void registerIngredients(IModIngredientRegistration ingredientRegistry) {

		Set<ElementPair> list = Sets.newHashSet();
    	for(SpellBlend spell:SpellRegistry.instance().getBlendSpells()){
    		spell.getRequireElementTable().entrySet().forEach(in ->{
    			for(FiveElements.Type type:FiveElements.Type.values()){
    				list.add(new ElementPair(type,in.getValue().getInt(type)));
    			}
    		});
    	}

    	List<SpellWrapper> spells = SpellRegistry.instance().getProperties().stream().map(in -> new SpellWrapper(in)).sorted().collect(Collectors.toList());

    	List<UnsagaMaterial> materials = UnsagaMaterials.instance().getProperties().stream().sorted().collect(Collectors.toList());

		ingredientRegistry.register(UnsagaMaterial.class, materials, new UnsagaMaterialHelper(), new UnsagaMaterialRenderer());
		ingredientRegistry.register(MinsagaForging.Material.class, Lists.newArrayList(), new MinsagaForgingMaterialHelper(), new MinsagaForgingMaterialRenderer());
		ingredientRegistry.register(ElementPair.class, Lists.newArrayList(), new ElementIngredientHelper(), new ElementIngredientRenderer());
		ingredientRegistry.register(SpellWrapper.class, ImmutableList.copyOf(spells), new SpellIngredientHelper(), new SpellIngredientRenderer());
	}
}
