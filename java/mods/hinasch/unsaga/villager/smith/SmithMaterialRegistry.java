package mods.hinasch.unsaga.villager.smith;

import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.oredict.OreDictionary;

public class SmithMaterialRegistry {

	UnsagaMaterials reg = UnsagaMaterials.instance();
	RegistrySimple<Predicate<ItemStack>,Info> materialRegistry = new RegistrySimple();
	protected static SmithMaterialRegistry INSTANCE;

	public static SmithMaterialRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new SmithMaterialRegistry();
		}
		return INSTANCE;
	}

	protected SmithMaterialRegistry(){

	}
	public void register(){
		this.add(Items.STICK,reg.wood, 0.125F);
		this.add(Items.BOW, reg.wood, 1.0F);
		this.add(in ->{
			if(in.getItem() instanceof ItemTool){
				return ((ItemTool)in.getItem()).getToolMaterial() == Item.ToolMaterial.IRON;
			}
			return false;
		}, reg.iron, 1.0F);
		this.add(Items.BUCKET, reg.iron, 1.0F);
		this.add(Blocks.LOG,BlockPlanks.EnumType.OAK.getMetadata(),reg.oak,1.0F);
		this.add(Blocks.LOG,BlockPlanks.EnumType.BIRCH.getMetadata(),reg.birch,1.0F);
		this.add(Blocks.LOG,BlockPlanks.EnumType.SPRUCE.getMetadata(),reg.spruce,1.0F);
		this.add(Blocks.LOG,BlockPlanks.EnumType.JUNGLE.getMetadata(),reg.jungleWood,1.0F);
		this.add(Blocks.LOG2,BlockPlanks.EnumType.ACACIA.getMetadata(),reg.acacia,1.0F);
		this.add(Blocks.LOG2,BlockPlanks.EnumType.DARK_OAK.getMetadata(),reg.darkOak,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.OAK.getMetadata(),reg.oak,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.BIRCH.getMetadata(),reg.birch,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.SPRUCE.getMetadata(),reg.spruce,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.JUNGLE.getMetadata(),reg.jungleWood,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.ACACIA.getMetadata(),reg.acacia,1.0F);
		this.add(Blocks.PLANKS,BlockPlanks.EnumType.DARK_OAK.getMetadata(),reg.darkOak,1.0F);
		this.add("ingotIron", reg.iron,0.5F);
		this.add("oreIron", reg.ironOre, 0.5F);
		this.add("ingotCopper", reg.copper, 0.5F);
		this.add("oreCopper", reg.copperOre, 0.5F);
		this.add("gemRuby", reg.ruby, 0.5F);
		this.add("gemSapphire", reg.sapphire,0.5F);
		this.add("ingotDamuscus", reg.damascus, 0.5F);
		this.add("ingotOsmium", reg.osmium, 0.5F);
		this.add("ingotGold", reg.gold, 0.5F);
		this.add("ingotSteel", reg.steel1, 0.5F);
		this.add("ingotBrass", reg.brass, 0.5F);
		this.add("ingotSilver", reg.silver, 0.5F);
		this.add("ingotLead", reg.lead, 0.5F);
		this.add("gemChalcedonyBlue", reg.chalcedony, 0.5F);
		this.add("gemChalcedonyWhite", reg.chalcedony, 0.5F);
		this.add("gemChalcedonyRed", reg.carnelian, 0.5F);
		this.add("gemQuartz", reg.quartz, 0.5F);
		this.add("gemChalcedonyRed", reg.carnelian, 0.5F);
		this.add("gemLapis", reg.lazuli, 0.5F);
		this.add("gemOpal", reg.opal, 0.5F);
		this.add("gemTopaz", reg.topaz, 0.5F);
		this.add("oreSerpentine", reg.serpentine, 0.5F);
		this.add(Blocks.COBBLESTONE,reg.debris1,0.5F);
		this.add(Blocks.STONE,reg.debris1,0.5F);
	}

	public Optional<Info> find(@Nullable ItemStack is){
		if(is!=null){
			return this.materialRegistry.getKeys().stream().filter(in -> in.test(is)).map(in -> this.materialRegistry.getObject(in)).findFirst();
		}
		return Optional.empty();
	}
	public void add(Predicate<ItemStack> item,UnsagaMaterial m,float f){
		this.materialRegistry.putObject(item, new Info(m,f));
	}
	public void add(Item item,UnsagaMaterial m,float f){
		this.materialRegistry.putObject(new PredicateItem(item,OreDictionary.WILDCARD_VALUE), new Info(m,f));
	}
	public void add(String string,UnsagaMaterial m,float f){
		this.materialRegistry.putObject(new PredicateOre(string), new Info(m,f));
	}
	public void add(Block block,UnsagaMaterial m,float f){
		this.add(block,OreDictionary.WILDCARD_VALUE, m, f);
	}
	public void add(Block block,int damage,UnsagaMaterial m,float f){
		this.materialRegistry.putObject(new PredicateItem(Item.getItemFromBlock(block),damage), new Info(m,f));
	}

	public static class PredicateOre implements Predicate<ItemStack>{

		final String ore;

		public PredicateOre(String ore){
			this.ore = ore;
		}
		@Override
		public boolean test(ItemStack t) {
			return HSLibs.getOreNames(t).stream().anyMatch(in -> in.equals(ore));
		}

	}
	public static class PredicateItem implements Predicate<ItemStack>{

		final Item item;
		final int damage;

		public PredicateItem(Item item,int damage){
			this.item = item;
			this.damage = damage;
		}
		@Override
		public boolean test(ItemStack t) {
			if(damage==OreDictionary.WILDCARD_VALUE){
				return t.getItem() == item;
			}
			return t.getItem()==item && t.getItemDamage() == damage;
		}

	}

	public static class Info{

		final float amount;
		public float getAmount() {
			return amount;
		}

		public UnsagaMaterial getMaterial() {
			return material;
		}

		final UnsagaMaterial material;

		public Info(UnsagaMaterial m,float am){
			this.amount = am;
			this.material = m;
		}
	}
}
