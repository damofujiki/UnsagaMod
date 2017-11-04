package mods.hinasch.unsagamagic.block;

import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.UnsagaMagic;
import mods.hinasch.unsagamagic.tileentity.TileEntityDecipheringTable;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UnsagaMagicBlocks extends BlockItemRegistry<Block>{


	private static UnsagaMagicBlocks INSTANCE;
	public static UnsagaMagicBlocks instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaMagicBlocks();
		}
		return INSTANCE;
	}

	protected UnsagaMagicBlocks() {
		super(UnsagaMod.MODID);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	public Block holySeal;
	public Block decipheringTable;
	public Block fireWall;
	@Override
	public void register(){

		this.setUnlocalizedNamePrefix("unsaga");
		CreativeTabs tab = UnsagaMagic.instance().tabMagic;
//		this.holySeal = put(new BlockHolySeal().setHardness(1.5F),"holySeal",null);
		this.decipheringTable = put(new BlockDecipheringTable().setHardness(Statics.HARDNESS_COBBLE_STONE),"decipheringTable",tab,null);
//		this.fireWall = put(new BlockFireWall(),"fireWall",HSLib.configHandler.isDebug() ? tab : null);
		GameRegistry.registerTileEntity(TileEntityDecipheringTable.class, UnsagaMod.MODID+".decipheringTable");
//		GameRegistry.registerTileEntity(TileEntityFireWall.class, UnsagaMod.MODID+".fireWall");
//		GameRegistry.registerTileEntity(TileEntityHolySeal.class, UnsagaMod.MODID+".holySeal");
	}

//	public Block put(Block block,String name){
//		ResourceLocation res = new ResourceLocation(UnsagaMod.MODID,name);
//		block.setCreativeTab(UnsagaMagic.instance().tabMagic);
//		block.setRegistryName(res);
//		ItemBlock itemBlock = new ItemBlock(block);
//		itemBlock.setRegistryName(res);
//		GameRegistry.register(block);
//		GameRegistry.register(itemBlock);
//		this.putObject(res, block);
//		return this.getObject(res);
//	}
}
