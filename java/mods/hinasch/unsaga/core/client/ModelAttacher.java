package mods.hinasch.unsaga.core.client;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.ClientHelper.ModelHelper;
import mods.hinasch.lib.client.ClientHelper.PluralVariantsModelFactory;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.core.block.BlockUnsagaStone;
import mods.hinasch.unsaga.init.UnsagaBlockRegistry;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import net.minecraft.item.Item;

public class ModelAttacher {

	private final ModelHelper modelAgent = new ModelHelper(UnsagaMod.MODID);
	private final PluralVariantsModelFactory pluralVariantsModelFactory = PluralVariantsModelFactory.create(modelAgent, null);

	private UnsagaItemRegistry itemsNew = UnsagaMod.core.itemsNew;
	private UnsagaBlockRegistry blocks = UnsagaMod.core.blocks;

	public void register(){
//		List<String> list = Lists.newArrayList();
//		list.add("sword");
//		list.add("sword.failed");
//		list.add("axe");
//		pluralVariantsModelFactory.create(itemsNew.sword);
//		.prepareVariants(list)
//		.attach();

		this.registerModelAndColor(this.itemsNew.sword, "sword");
		this.registerModelAndColor(this.itemsNew.axe, "axe");
		this.registerModelAndColor(this.itemsNew.knife, "knife");
		this.registerModelAndColor(this.itemsNew.staff, "staff");
		this.registerModelAndColor(this.itemsNew.bow, "bow");
		this.registerModelAndColor(this.itemsNew.spear, "spear");
		this.registerModelAndColor(this.itemsNew.helmet, "helmet");
		this.registerModelAndColor(this.itemsNew.armor, "armor");
		this.registerModelAndColor(this.itemsNew.boots, "boots");
		this.registerModelAndColor(this.itemsNew.leggins, "leggins");
		this.registerModelAndColor(this.itemsNew.shield, "shield");
		this.registerModelAndColor(this.itemsNew.accessory, "accessory");
		this.registerModelAndColor(this.itemsNew.entityEggs, "entityEgg",3);
		this.registerModelAndColor(this.itemsNew.skillPanel, "skillPanel");
		this.registerModelAndColor(this.itemsNew.ammo, "ammo");
		this.registerModelAndColor(this.itemsNew.musket, "musket");
		modelAgent.registerModelMesher(this.itemsNew.elementChecker, 0,"elementChecker");
//		modelAgent.registerModelMesher(this.itemsNew.iconCondition, 0,"icon.condition");
//		modelAgent.registerModelMesher(this.itemsNew.iconCondition, 1,"icon.condition.hot");
//		modelAgent.registerModelMesher(this.itemsNew.iconCondition, 2,"icon.condition.cold");
//		modelAgent.registerModelMesher(this.itemsNew.iconCondition, 3,"icon.condition.humid");
//		pluralVariantsModelFactory.create(this.itemsNew.iconCondition)
//		.prepareVariants(ItemIconCondition.Type.getIconNames())
//		.attach();

		pluralVariantsModelFactory.target(this.itemsNew.rawMaterials)
		.prepareVariants(RawMaterialItemRegistry.instance().getIconNames())
		.attachBy(RawMaterialItemRegistry.instance().getProperties(), in -> modelAgent.getNewModelResource(in.getIconName(),"inventory"));;
		ClientHelper.registerColorItem(this.itemsNew.rawMaterials);

		pluralVariantsModelFactory.target(Item.getItemFromBlock(blocks.stonesAndMetals))
		.prepareVariants(BlockUnsagaStone.EnumType.getJsonNames())
		.attach();

		UnsagaModCore.instance().oreBlocks.getProperties()
		.forEach(ore -> modelAgent.registerModelMesher(ore.getBlockAsItem(), 0,modelAgent.getNewModelResource(ore.getName(), "inventory")));

//		modelAgent.registerModelMesher(this.itemsNew.iconCondition, 1,"icon.condition");
	}

	private void registerModelAndColor(Item item,String name,int... length){
		int len = 1;
		if(length.length>0){
			len = length[0];
		}
		for(int i=0;i<len;i++){
			modelAgent.registerModelMesher(item, i,name);
			ClientHelper.registerColorItem(item);
		}

	}

}
