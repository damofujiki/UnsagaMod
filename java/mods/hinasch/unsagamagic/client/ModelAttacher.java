package mods.hinasch.unsagamagic.client;

import mods.hinasch.lib.client.ClientHelper.ModelHelper;
import mods.hinasch.lib.client.ClientHelper.PluralVariantsModelFactory;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.block.UnsagaMagicBlocks;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import net.minecraft.item.Item;

public class ModelAttacher {

	ModelHelper modelAgent = new ModelHelper(UnsagaMod.MODID);
	private final PluralVariantsModelFactory pluralVariantsModelFactory = PluralVariantsModelFactory.create(modelAgent, null);
	UnsagaMagicItems items = UnsagaMagicItems.instance();
	UnsagaMagicBlocks blocks = UnsagaMagicBlocks.instance();

	public void register(){
		modelAgent.registerModelMesher(items.tablet, 0, modelAgent.getNewModelResource("magicTablet", "inventory"));
		modelAgent.registerModelMesher(items.spellBook, 0, modelAgent.getNewModelResource("spellBook", "inventory"));
//		ClientHelper.registerColorItem(items.spellBook);
//		modelAgent.registerModelMesher(items.knowledgeBook, 0, modelAgent.getNewModelResource("knowledgeBook", "inventory"));
		modelAgent.registerModelMesher(items.iconSpell, 0,modelAgent.getNewModelResource("spellIcon", "inventory"));
//		ClientHelper.registerColorItem(items.iconSpell);
//		modelAgent.registerModelMesher(Item.getItemFromBlock(blocks.fireWall), 0,"fireWall");
		modelAgent.registerModelMesher(Item.getItemFromBlock(blocks.decipheringTable), 0, modelAgent.getNewModelResource("decipheringTable", "inventory"));
//		modelAgent.registerModelMesher(Item.getItemFromBlock(blocks.holySeal), 0, "holySeal");
//		modelAgent.registerModelMesher(items.spellBookBinder, 0, "spellBookBinder");
	}
}
