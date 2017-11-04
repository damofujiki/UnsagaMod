package mods.hinasch.unsagaadditional.world;

import mods.hinasch.lib.world.OreGenerator;
import mods.hinasch.unsaga.core.world.WorldGeneratorUnsaga;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import mods.hinasch.unsagaadditional.init.ConfigHandler;
import mods.hinasch.unsagaadditional.init.OreBlockPropertyGroup;

public class OreGeneraterGroup {

	ConfigHandler config = UnsagaModAdditional.configHandler;
	OreBlockPropertyGroup ores = UnsagaModAdditional.ores;
	OreGenerator cinnabarGen = OreGenerator.create(ores.ORE_CINNABAR.getBlock(),config.isEnableGenCinnabar()).setMinMax(10, 40).setDensity(8).setGenerateChance(4);
	OreGenerator rockSaltGen = OreGenerator.create(ores.ROCKSALT.getBlock(),config.isEnableGenRockSalt()).setMinMax(50, 80).setDensity(15).setGenerateChance(10);
	OreGenerator bismuthGen = OreGenerator.create(ores.ORE_BISMUTH.getBlock(),config.isEnableGenBismuth()).setMinMax(60, 120).setDensity(8).setGenerateChance(4);


	public void register(){
		WorldGeneratorUnsaga.instance().addOreGeneration(cinnabarGen);
		WorldGeneratorUnsaga.instance().addOreGeneration(rockSaltGen);
		WorldGeneratorUnsaga.instance().addOreGeneration(bismuthGen);
	}
}
