package mods.hinasch.unsagaadditional.client;

import mods.hinasch.lib.client.ClientHelper.ModelHelper;
import mods.hinasch.lib.client.ClientHelper.PluralVariantsModelFactory;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;

public class ModelAttacher {
	private final ModelHelper modelAgent = new ModelHelper(UnsagaModAdditional.MODID);
	private final PluralVariantsModelFactory pluralVariantsModelFactory = PluralVariantsModelFactory.create(modelAgent, null);

	public void register(){

		pluralVariantsModelFactory.target(UnsagaModAdditional.items.misc)
		.prepareVariants(UnsagaModAdditional.miscItems.getNameList())
		.attach();

		UnsagaModAdditional.ores.getProperties().forEach(in ->{

			modelAgent.registerModelMesher(in.getBlockAsItem(), 0, in.getName());
		});
	}
}
