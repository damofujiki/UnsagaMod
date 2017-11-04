package mods.hinasch.unsaga.core.world;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.world.OreGenerator;
import mods.hinasch.lib.world.WorldGeneratorBase;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.init.BlockOrePropertyRegistry;
import mods.hinasch.unsaga.init.UnsagaConfigHandler.OreSetting;
import net.minecraft.world.World;

public class WorldGeneratorUnsaga extends WorldGeneratorBase{


	private static WorldGeneratorUnsaga INSTANCE;

	public static WorldGeneratorUnsaga instance(){
		if(INSTANCE==null){
			INSTANCE = new WorldGeneratorUnsaga();
		}
		return INSTANCE;
	}
	BlockOrePropertyRegistry ores = UnsagaModCore.instance().oreBlocks;
	OreSetting config = UnsagaMod.configHandler.getOreSetting();

	OreGenerator copperGen = new OreGenerator(ores.copper.getBlock(),config.enableCopper).setMinMax(10,40).setDensity(8).setGenerateChance(20);
	OreGenerator leadGen = new OreGenerator(ores.lead.getBlock(),config.enableLead).setMinMax(30, 80).setDensity(8).setGenerateChance(4);
	OreGenerator silverGen = new OreGenerator(ores.silver.getBlock(),config.enableSilver).setMinMax(20, 40).setDensity(8).setGenerateChance(2);
	OreGenerator SapphireGen = new OreGenerator(ores.sapphire.getBlock(),config.enableSapphire).setMinMax(0, 24).setDensity(6).setGenerateChance(1);
	OreGenerator rubyGen = new OreGenerator(ores.ruby.getBlock(),config.enableRuby).setMinMax(0, 24).setDensity(6).setGenerateChance(1);
	OreGenerator serpentine;// = new OreGenerator(UnsagaMod.blocks.stonesAndMetals,config.enableSerpentine).setMinMax(0,80).setDensity(15).setGenerateChance(10);
//	OreGenerator chestInStone = new OreGenerator(UnsagaMod.blocks.bonusChest,true).setMinMax(0,60).setDensity(1).setGenerateChance(10);
	protected List<OreGenerator> listOreGeneration = Lists.newArrayList(copperGen, leadGen,silverGen,SapphireGen,rubyGen,serpentine);
	public void generateEnd(World var1, Random var2, int var3, int var4) {


	}

	public void generateNether(World var1, Random var2, int var3, int var4) {


	}

	public void generateOverworld(World world, Random rand, int x, int z) {
//		if(HSLib.configHandler.isAllowOreGeneration()){
//			if(UnsagaMod.configHandler.isEnableOreGeneration()){
//				XYZPos xyz = new XYZPos(x,0,z);
//				listOreGeneration.forEach(gen -> gen.genStandardOre1(world, rand, xyz));
//			}
//
//		}
//		if(UnsagaMod.configHandler.isEnableChestGeneration()){
//			if(rand.nextInt(100)<UnsagaMod.configHandler.getChestGenerationWeight()){
//				int posX = x + rand.nextInt(16);
//				int posZ = z + rand.nextInt(16);
//				BlockPos pos = world.getHeight(new BlockPos(posX,0,posZ));
//				if(world.isSideSolid(pos.down(),EnumFacing.UP)){
////					world.setBlockState(pos, UnsagaMod.blocks.bonusChest.getDefaultState());
////					if(world.getTileEntity(pos) instanceof TileEntityUnsagaChest){
////						TileEntityUnsagaChest te = (TileEntityUnsagaChest) world.getTileEntity(pos);
////						te.init(world);
////					}
//				}
//
//			}
//
//		}
	}

	@Override
	public boolean arrowGenerationInOverworld(){
		return HSLib.configHandler.isAllowOreGenerationOverworld();
	}

	public void addOreGeneration(OreGenerator gen){
		this.listOreGeneration.add(gen);
	}

}
