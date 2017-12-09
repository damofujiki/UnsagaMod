package mods.hinasch.unsaga.villager.smith;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.common.LibraryRegistry;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SmithMaterialRegistry extends LibraryRegistry<SmithMaterialRegistry.Info>{

	UnsagaMaterials reg = UnsagaMaterials.instance();
//	Map<Predicate<ItemStack>,Info> materialRegistry = Maps.<Predicate<ItemStack>,Info>newHashMap();
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
		this.addBlockAll(Blocks.IRON_BARS,reg.iron,1.0F);
		this.add(Items.BUCKET, reg.iron, 1.0F);
		this.add(Items.WOODEN_AXE, reg.wood, 1.0F);
		this.add(Items.WOODEN_HOE, reg.wood, 1.0F);
		this.add(Items.WOODEN_PICKAXE, reg.wood, 1.0F);
		this.add(Items.WOODEN_SHOVEL, reg.wood, 1.0F);
		this.add(Items.WOODEN_SWORD, reg.wood, 1.0F);
		this.add(Items.IRON_AXE, reg.iron, 1.0F);
		this.add(Items.IRON_HOE, reg.iron, 1.0F);
		this.add(Items.IRON_HORSE_ARMOR, reg.iron, 1.0F);
		this.add(Items.IRON_PICKAXE, reg.iron, 1.0F);
		this.add(Items.IRON_SHOVEL, reg.iron, 1.0F);
		this.add(Items.IRON_SWORD, reg.iron, 1.0F);
		this.add(Items.IRON_BOOTS, reg.iron, 1.0F);
		this.add(Items.IRON_CHESTPLATE, reg.iron, 1.0F);
		this.add(Items.IRON_HELMET, reg.iron, 1.0F);
		this.add(Items.IRON_LEGGINGS, reg.iron, 1.0F);
		this.add(Items.IRON_DOOR, reg.iron, 1.0F);
		this.addBlock(Blocks.LOG,BlockPlanks.EnumType.OAK.getMetadata(),reg.oak,0.25F);
		this.add(Items.OAK_DOOR, reg.oak, 0.5F);
		this.addBlock(Blocks.LOG,BlockPlanks.EnumType.BIRCH.getMetadata(),reg.birch,0.25F);
		this.add(Items.BIRCH_DOOR, reg.birch, 0.5F);
		this.addBlock(Blocks.LOG,BlockPlanks.EnumType.SPRUCE.getMetadata(),reg.spruce,0.25F);
		this.add(Items.SPRUCE_DOOR, reg.spruce, 0.5F);
		this.addBlock(Blocks.LOG,BlockPlanks.EnumType.JUNGLE.getMetadata(),reg.jungleWood,0.25F);
		this.add(Items.JUNGLE_DOOR, reg.jungleWood, 0.5F);
		this.addBlock(Blocks.LOG2,BlockPlanks.EnumType.ACACIA.getMetadata()-4,reg.acacia,0.25F);
		this.add(Items.ACACIA_DOOR, reg.acacia, 0.5F);
		this.addBlock(Blocks.LOG2,BlockPlanks.EnumType.DARK_OAK.getMetadata()-4,reg.darkOak,0.25F);
		this.add(Items.DARK_OAK_DOOR, reg.darkOak, 0.5F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.OAK.getMetadata(),reg.oak,1.0F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.BIRCH.getMetadata(),reg.birch,1.0F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.SPRUCE.getMetadata(),reg.spruce,1.0F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.JUNGLE.getMetadata(),reg.jungleWood,1.0F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.ACACIA.getMetadata(),reg.acacia,1.0F);
		this.addBlock(Blocks.PLANKS,BlockPlanks.EnumType.DARK_OAK.getMetadata(),reg.darkOak,1.0F);
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
		this.addBlockAll(Blocks.COBBLESTONE,reg.debris1,0.5F);
		this.add(UnsagaItemRegistry.instance().musket,reg.iron ,0.5F);
	}

	public List<Predicate<ItemStack>> findByMaterial(UnsagaMaterial m){
		return this.materialRegistry.entrySet().stream().filter(in -> in.getValue().getMaterial()==m).map(in -> in.getKey()).collect(Collectors.toList());
	}

	public List<ItemStack> findItemStacksByMaterial(UnsagaMaterial m){
		return this.findByMaterial(m).stream().filter(in -> in instanceof IGetItemStack).map(in -> (IGetItemStack)in)
		.flatMap(in -> in.getItemStack().stream()).collect(Collectors.toList());
	}
	public Optional<Info> find(@Nullable ItemStack is){
		if(is!=null){
			return this.materialRegistry.entrySet().stream().filter(in -> in.getKey().test(is)).map(in -> in.getValue()).findFirst();
		}
		return Optional.empty();
	}

//	public void add(Predicate<ItemStack> item,UnsagaMaterial m,float f){
//		this.materialRegistry.put(item, new Info(m,f));
//	}
//	public void add(Item item,UnsagaMaterial m,float f){
//		this.materialRegistry.put(new PredicateItem(item,OreDictionary.WILDCARD_VALUE), new Info(m,f));
//	}
//	public void add(String string,UnsagaMaterial m,float f){
//		this.materialRegistry.put(new PredicateOre(string), new Info(m,f));
//	}
//	public void add(Block block,UnsagaMaterial m,float f){
//		this.add(block,OreDictionary.WILDCARD_VALUE, m, f);
//	}
//	public void add(Block block,int damage,UnsagaMaterial m,float f){
//		this.materialRegistry.put(new PredicateItem(Item.getItemFromBlock(block),damage), new Info(m,f));
//	}

	public static class PredicateOre extends PredicateBase<String>{

//		final String ore;

		public PredicateOre(String ore){
			super(ore);
		}
		@Override
		public boolean test(ItemStack t) {
			return HSLibs.getOreNames(t).stream().anyMatch(in -> in.equals(object));
		}
		@Override
		public List<ItemStack> getItemStack() {
			// TODO 自動生成されたメソッド・スタブ
			return OreDictionary.getOres(this.object);
		}

	}
	public static class PredicateItem extends PredicateBase<Item>{


		final int damage;

		public PredicateItem(Item item,int damage){
			super(item);
			this.damage = damage;
		}
		@Override
		public boolean test(ItemStack t) {
			if(damage==OreDictionary.WILDCARD_VALUE){
				return t.getItem() == this.object;
			}
			return t.getItem()==this.object && t.getItemDamage() == damage;
		}
		@Override
		public List<ItemStack> getItemStack() {
			if(damage==OreDictionary.WILDCARD_VALUE){
				return Lists.newArrayList(new ItemStack(object));
			}
			return Lists.newArrayList(new ItemStack(object,1,damage));
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

	public static abstract class PredicateBase<T> implements Predicate<ItemStack>,IGetItemStack{

		public final T object;

		public PredicateBase(T obj){
			this.object = obj;
		}



	}
	public static interface IGetItemStack{

		public List<ItemStack> getItemStack();
	}
	@Override
	public Info preRegister(Object... in) {
		// TODO 自動生成されたメソッド・スタブ
		return new Info((UnsagaMaterial)in[0],(Float)in[1]);
	}
}
