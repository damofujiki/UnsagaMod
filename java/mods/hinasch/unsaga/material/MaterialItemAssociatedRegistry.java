package mods.hinasch.unsaga.material;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.block.BlockUnsagaStone;
import mods.hinasch.unsaga.init.BlockOrePropertyRegistry;
import mods.hinasch.unsaga.villager.smith.SmithMaterialRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MaterialItemAssociatedRegistry {


	Map<UnsagaMaterial,ItemStack> materialAssociatedItems = Maps.newHashMap();
	Map<ItemStack,Float> materialRepairAmountMap = Maps.newHashMap();
	UnsagaMaterials materials = UnsagaMaterials.instance();

	private static MaterialItemAssociatedRegistry INSTANCE;

	public static MaterialItemAssociatedRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new MaterialItemAssociatedRegistry();
		}
		return INSTANCE;
	}

	protected MaterialItemAssociatedRegistry(){

	}

	public void init(){
		this.associateVanillaThings();
		this.registerOthers();
		this.validate();
	}
	protected void associateVanillaThings(){
		addAssociation(materials.feather, new ItemStack(Items.FEATHER,1),0.5F);
		addAssociation(materials.wood, new ItemStack(Items.STICK,1),0.1F);
		addAssociation(materials.bone1, new ItemStack(Items.BONE,1),0.5F);
		addAssociation(materials.quartz, new ItemStack(Items.QUARTZ,1),0.5F);
		addAssociation(materials.obsidian, new ItemStack(Blocks.OBSIDIAN,1),0.5F);
		addAssociation(materials.iron, new ItemStack(Items.IRON_INGOT,1),0.5F);
		addAssociation(materials.ironOre, new ItemStack(Blocks.IRON_ORE,1),0.5F);
		addAssociation(materials.diamond, new ItemStack(Items.DIAMOND,1),0.5F);
	}

	protected void registerOthers(){
		addAssociation(materials.serpentine, BlockUnsagaStone.EnumType.SERPENTINE.getStack(1),0.5F);
		addAssociation(materials.copperOre, new ItemStack(BlockOrePropertyRegistry.instance().copper.getBlock(),1),0.5F);
	}


	protected void addAssociation(UnsagaMaterial m,ItemStack is,float amount){
		materialAssociatedItems.put(m, is);
		this.materialRepairAmountMap.put(is, amount);
	}
	protected void registerOreDictionary(){

	}

	public OptionalDouble getAmoutInDurability(ItemStack other){
		if(SmithMaterialRegistry.instance().find(other).isPresent()){
			return OptionalDouble.of(SmithMaterialRegistry.instance().find(other).get().getAmount());
		}
		return this.materialRepairAmountMap.entrySet().stream().filter(in -> in.getKey().isItemEqual(other)).mapToDouble(in -> in.getValue()).findFirst();
	}
	public void registerAssociation(UnsagaMaterial m,ItemStack is){
		materialAssociatedItems.put(m, is);
	}

	public Optional<UnsagaMaterial> getMaterialFromStack(ItemStack is){
		return this.materialAssociatedItems.entrySet().stream().filter(in -> in.getValue().isItemEqual(is)).map(in -> in.getKey()).findFirst();
	}
	public ItemStack getAssociatedStack(UnsagaMaterial m){
		if(this.materialAssociatedItems.containsKey(m)){
			return this.materialAssociatedItems.get(m);
		}
		return new ItemStack(Items.FEATHER,1);
	}
	protected void validate(){
		UnsagaMod.core.materialsNew.merchandiseMaterial.stream().forEach(in ->{
			Preconditions.checkNotNull(materialAssociatedItems.get(in),in);
			ItemStack is = materialAssociatedItems.get(in);
			Preconditions.checkArgument(getAmoutInDurability(is).isPresent());
		});
	}
}
