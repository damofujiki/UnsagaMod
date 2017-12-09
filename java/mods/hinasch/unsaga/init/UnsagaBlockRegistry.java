package mods.hinasch.unsaga.init;

import java.util.Arrays;

import mods.hinasch.lib.block.BlockOreBase;
import mods.hinasch.lib.item.RecipeUtilNew;
import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.core.block.BlockUnsagaStone;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class UnsagaBlockRegistry extends BlockItemRegistry<Block>{

	public RegistrySimple<Integer,Block> ores = new RegistrySimple();
//	public Block bonusChest;
	public Block stonesAndMetals;


	protected static UnsagaBlockRegistry INSTANCE;

	public static UnsagaBlockRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaBlockRegistry();
		}
		return INSTANCE;
	}

	protected UnsagaBlockRegistry() {
		super(UnsagaMod.MODID);
		this.setUnlocalizedNamePrefix("unsaga");
	}


	@Override
	public void register() {
		// TODO 自動生成されたメソッド・スタブ
		UnsagaModCore.instance().oreBlocks.getProperties().forEach(input ->{
			Block block = put(new BlockOreBase(BlockOrePropertyRegistry.getOreData(input.getId()), () -> UnsagaMod.secureRandom){}
					,input.getPropertyName(),UnsagaMod.core.tabMisc);
			ores.putObject(input.getId(), block);
		});

//		bonusChest = put(new BlockUnsagaChest().setHardness(Statics.HARDNESS_COBBLE_STONE),"bonusChest",CreativeTabsUnsaga.tabUnsaga);
		stonesAndMetals = put(new BlockUnsagaStone(Material.ROCK).setHardness(1.5F),"stonesAndMetals",UnsagaMod.core.tabMisc
				,block ->{
					ItemBlock item = new ItemMultiTexture(block, block, is ->{
						return BlockUnsagaStone.EnumType.fromMeta(is.getItemDamage()).getUnlocalizedName();
					});

					Arrays.stream(BlockUnsagaStone.EnumType.values()).forEach(in ->{
						item.addPropertyOverride(new ResourceLocation(in.getName()), new BlockVariantGetter(in));
					});

					return item;
				}
		);

//		GameRegistry.registerTileEntity(TileEntityUnsagaChest.class, UnsagaMod.MODID+":chest");
	}

	/**
	 * レシピや鉱石辞書へブロック登録。
	 */
	public void registerRecipesAndOres(){
		OreDictionary.registerOre("oreSerpentine", BlockUnsagaStone.EnumType.SERPENTINE.getStack(1));
		OreDictionary.registerOre("stoneSerpentine", BlockUnsagaStone.EnumType.SERPENTINE.getStack(1));
		OreDictionary.registerOre("blockLead", BlockUnsagaStone.EnumType.LEAD.getStack(1));
		OreDictionary.registerOre("blockDamascus", BlockUnsagaStone.EnumType.DAMASCUS.getStack(1));
		OreDictionary.registerOre("blockFaerieSilver", BlockUnsagaStone.EnumType.FAERIE_SILVER.getStack(1));
		OreDictionary.registerOre("blockSilver", BlockUnsagaStone.EnumType.SILVER.getStack(1));
		OreDictionary.registerOre("blockMeteoricIron", BlockUnsagaStone.EnumType.METEORIC_IRON.getStack(1));
		OreDictionary.registerOre("blockCopper", BlockUnsagaStone.EnumType.COPPER.getStack(1));
		OreDictionary.registerOre("blockSteel", BlockUnsagaStone.EnumType.STEEL.getStack(1));
		OreDictionary.registerOre("blockSteel", BlockUnsagaStone.EnumType.STEEL2.getStack(1));
		OreDictionary.registerOre("blockMeteorite", BlockUnsagaStone.EnumType.METEORITE.getStack(1));


		RecipeUtilNew.RecipeShaped.create().setBase(RecipeUtilNew.Recipes.CUBE_MIDDLE)
		.addAssociation('B', BlockUnsagaStone.EnumType.SERPENTINE.getStack(1)).setOutput(BlockUnsagaStone.EnumType.SERPENTINE_SMOOTH.getStack(4))
		.register();
		RecipeUtilNew.RecipeShapeless shapeless = new RecipeUtilNew.RecipeShapeless();

		for(BlockUnsagaStone.EnumType type:BlockUnsagaStone.EnumType.values()){
			if(type.getBaseItem()!=null){
				RecipeUtilNew.RecipeShaped.create().setBase(RecipeUtilNew.Recipes.FILLED)
				.addAssociation('B', type.getBaseItem()).setOutput(type.getStack(1))
				.register();
//				shaped.setOutput(type.getStack(1));
//				shaped.setBase(RecipeUtilNew.Recipes.FILLED);
//				shaped.addAssociation('B', type.getBaseItem());
//				shaped.register();
//				shaped.clear();
				RecipeUtilNew.RecipeShaped.create().setBase("S")
				.addAssociation('S',type.getStack(1)).setOutput(type.getBaseItem(9))
				.register();

			}

		}
	}

	public static class BlockVariantGetter implements IItemPropertyGetter{

		BlockUnsagaStone.EnumType type;
		public BlockVariantGetter(BlockUnsagaStone.EnumType type){
			this.type = type;
		}
		@Override
		public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
			if(stack.getItemDamage()==type.getMeta()){
				return 1.0F;
			}
			return 0;
		}

	}
}
