package mods.hinasch.unsagamagic;

import mods.hinasch.lib.item.SimpleCreativeTab;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.block.UnsagaMagicBlocks;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentCapability;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentEvent;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentRegistry;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import mods.hinasch.unsagamagic.item.newitem.ItemIconSpellNew;
import mods.hinasch.unsagamagic.spell.SpellBookCapability;
import mods.hinasch.unsagamagic.spell.StatePropertySpellCast;
import mods.hinasch.unsagamagic.spell.TabletCapability;
import mods.hinasch.unsagamagic.spell.TabletRegistry;
import net.minecraft.creativetab.CreativeTabs;

public class UnsagaMagic {

	private static UnsagaMagic INSTANCE;
	public static UnsagaMagic instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaMagic();
		}
		return INSTANCE;
	}
	protected UnsagaMagic() {

	}


	public final UnsagaMagicItems items = UnsagaMagicItems.instance();
	public final UnsagaMagicBlocks blocks = UnsagaMagicBlocks.instance();


	public mods.hinasch.unsagamagic.spell.TabletRegistry tabletsNew;
	public mods.hinasch.unsagamagic.spell.SpellRegistry spellsNew;
	public final CreativeTabs tabMagic = SimpleCreativeTab.createSimpleTab("unsaga.magic");
	public UnsagaEnchantmentRegistry enchantments;


	public void preInit(){
		UnsagaMod.logger.get().trace("start initializing unsagamod/magic");
//		TabletHelper.base.registerCapability();
//		SpellBookHelper.base.registerCapability();

		this.registerCapabilities();
		this.spellsNew = mods.hinasch.unsagamagic.spell.SpellRegistry.instance();
		this.spellsNew.preInit();
		this.tabletsNew = TabletRegistry.instance();
		this.tabletsNew.preInit();
		this.enchantments = UnsagaEnchantmentRegistry.instance();
		this.enchantments.preInit();
//		this.tablets = Tablets.instance();
//		this.tablets.preInit();
//		HSLibs.registerCapability(ITablet.class, new StorageITablet(), DefaultITablet.class);

//		HSLibs.registerCapability(ISpellBook.class, new StorageISpellBook(), DefaultISpellBook.class);
//		HSLibs.registerCapability(IBlessedItem.class, new StorageIBlessedItem(), DefaultIBlessedItem.class);
//		CustomBless.adapterBase.registerCapability();
//		spellManager.initBlendData();
		items.register();
		blocks.register();
	}

	private void registerCapabilities(){
		TabletCapability.adapterBase.registerCapability();
		ItemIconSpellNew.adapterBase.registerCapability();
		SpellBookCapability.adapterBase.registerCapability();
		UnsagaEnchantmentCapability.adapterBase.registerCapability();

	}

	private void registerCapabilityAttachEvents(){
		TabletCapability.registerEvents();
		ItemIconSpellNew.registerEvents();
		SpellBookCapability.register();
		UnsagaEnchantmentCapability.registerEvents();

	}
	public void init(){

		this.spellsNew.init();
		this.tabletsNew.init();
		this.enchantments.init();
		SimpleCreativeTab.setIconItemToTab(tabMagic, UnsagaMagicItems.instance().tablet);

		this.registerCapabilityAttachEvents();
		UnsagaEnchantmentEvent.registerEvent();
		StatePropertySpellCast.register();
//		SpellBookHelper.adapter.registerAttachEvent();
//		TabletHelper.adapter.registerAttachEvent();
//		CapabilityInventory.predicates.put(ev -> ev.getItem() instanceof ItemSpellBookBinder, ItemSpellBookBinder.MAX_BINDER);
//		ItemStack craftTable = new ItemStack(Blocks.CRAFTING_TABLE);
//		ItemStack redCarpet = new ItemStack(Blocks.CARPET,1,EnumDyeColor.RED.getMetadata());
//		ItemStack angelite = UnsagaModCore.instance().miscItems.angelite.getItemStack(1);
//		GameRegistry.addShapelessRecipe(new ItemStack(blocks.decipheringTable,1), new Object[]{craftTable,redCarpet,Items.BOOK});
////		GameRegistry.addShapelessRecipe(new ItemStack(items.knowledgeBook,1), new Object[]{angelite,Items.BOOK});
//		RecipeUtilNew.RecipeShapeless recipe = new RecipeUtilNew.RecipeShapeless();
//		recipe.setOutput(new ItemStack(items.knowledgeBook,1));
//		recipe.addRecipeOre("gemBestial");
//		recipe.addRecipeItem(new ItemStack(Items.BOOK));
//		recipe.register();
////		CustomBless.registerEvents();
//		SimpleCreativeTab.setIconItemToTab(tabMagic, items.tablet);
//		HSLibEvents.livingHurt.getEventsPost().add(new EventSpellBuff());
	}
}
