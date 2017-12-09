package mods.hinasch.unsaga;

import mods.hinasch.lib.capability.EquipmentCacheCapability;
import mods.hinasch.lib.item.FuelHandlerCustom;
import mods.hinasch.lib.item.SimpleCreativeTab;
import mods.hinasch.unsaga.ability.AbilityCapability;
import mods.hinasch.unsaga.ability.specialmove.SparklingPointRegistry;
import mods.hinasch.unsaga.chest.ChestCapability;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.event.UnsagaEvents;
import mods.hinasch.unsaga.core.inventory.AccessorySlotCapability;
import mods.hinasch.unsaga.core.item.newitem.SkillPanelCapability;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemGunUnsaga;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.core.stats.UnsagaAchievementRegistry;
import mods.hinasch.unsaga.init.BlockOrePropertyRegistry;
import mods.hinasch.unsaga.init.UnsagaBlockRegistry;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.init.UnsagaLibrary;
import mods.hinasch.unsaga.lp.LifePoint;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import mods.hinasch.unsaga.minsaga.ForgingCapability;
import mods.hinasch.unsaga.minsaga.MinsagaForging;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import mods.hinasch.unsaga.status.TargetHolderCapability;
import mods.hinasch.unsaga.status.UnsagaXPCapability;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.villager.UnsagaVillagerIntegration;
import mods.hinasch.unsagamagic.UnsagaMagic;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UnsagaModCore {

	protected static UnsagaModCore instance;

	public static UnsagaModCore instance(){
		if(instance==null){
			instance = new UnsagaModCore();
		}
		return instance;
	}


	public mods.hinasch.unsaga.ability.AbilityRegistry abilitiesNew;

	public UnsagaBlockRegistry blocks;
//	public UnsagaEntityRegistry entities;
	public mods.hinasch.unsaga.core.entity.UnsagaEntityRegistry entitiesNew;
	public SimpleCreativeTab tabCombat;
	public SimpleCreativeTab tabTools;
	public SimpleCreativeTab tabMisc;
	public SimpleCreativeTab tabSkillPanels;
	public UnsagaMagic magic;
	public UnsagaLibrary library = UnsagaLibrary.instance();
	public BlockOrePropertyRegistry oreBlocks;
	public MinsagaForging minsagaForging ;
	public UnsagaEvents events = new UnsagaEvents();
	public UnsagaAchievementRegistry achievements = UnsagaAchievementRegistry.instance();
	public UnsagaModEvents eventsNew;

//	public LivingHelper.CacheType ACCESSORY;

	public mods.hinasch.unsaga.material.UnsagaMaterials materialsNew;
	public UnsagaItemRegistry itemsNew = UnsagaItemRegistry.instance();
	public UnsagaPotions potions;
	public StateRegistry states;
	public RawMaterialItemRegistry rawMaterials;
	public FuelHandlerCustom fuelHandler = new FuelHandlerCustom();


	public SkillPanelRegistry newSkillPanels;
	public MaterialItemAssociatedRegistry materialItemAssociation;
	public UnsagaVillagerIntegration villager;
	public SparklingPointRegistry sparklingPoint;


//	public static Item itemElementChecker = new ItemElementChecker();
//	public static ItemSwordBase itemTest = new ItemSwordBase();
	private UnsagaModCore(){

	}
	public void init(FMLInitializationEvent e){
		this.materialsNew.init();
		this.abilitiesNew.init();
		this.registerCapabilityAttachEvents();
		this.eventsNew = new UnsagaModEvents();
		eventsNew.regiser();
		library.init();


//		if(e.getSide()==Side.CLIENT){
//			HSLibs.registerEvent(new EventRenderText());
//		}



		this.tabTools.setIconItemStack(UnsagaItemRegistry.getItemStack(UnsagaMod.core.itemsNew.axe,this.materialsNew.damascus, 0));
		this.tabMisc.setIconItem(Items.APPLE);
		this.tabSkillPanels.setIconItemStack(SkillPanelRegistry.instance().getItemStack(SkillPanelRegistry.instance().locksmith, 1));
		this.tabMisc.setIconItemStack(RawMaterialItemRegistry.instance().carnelian.getItemStack(1));
		UnsagaPotions.registerEvent();

		this.oreBlocks.init();
		this.blocks.registerRecipesAndOres();

		this.materialItemAssociation.init();

		this.villager.init();

		ToolCategory.registerAssociation();

		this.sparklingPoint.register();
		this.minsagaForging.init();

		this.itemsNew.registerRecipes();
//
//		events.init();
//		if(e.getSide()==Side.CLIENT){
//			events.initClientEvents();
//		}
//		items.registerRecipes();
//		blocks.registerOthers();
//		debuffs.registerEventHooks();
//		abilities.registerEventHooks();
//		skillPanels.registerHooks();
//		UnsagaModIntegration.miscItems.init();
//		UnsagaModIntegration.oreBlocks.init();
//		materials.associateItemWithMaterial();
//		materials.initByproductAbility();
//
//
//
//		SimpleCreativeTab.setIconItemToTab(CreativeTabsUnsaga.tabUnsaga, items.getItem(ToolCategory.AXE, materials.damascus));
//		SimpleCreativeTab.setIconItemStackToTab(CreativeTabsUnsaga.tabPanels, skillPanels.unlock.getItemStack());
//
//

		magic.init();
//
//		LivingHelper.registerEvents();
//		LivingHelper.registeredTypes.add(LivingHelper.CacheType.ACCESSORY);
//		LivingHelper.registerHook(new LivingHelper.Hook() {
//
//			@Override
//			public void onRefleshEquipments(ILivingHelper callback,EntityLivingBase living) {
//				if(living instanceof EntityPlayer && AccessorySlotCapability.adapter.hasCapability((EntityPlayer) living)){
//
//
//					for(ItemStack is:AccessorySlotCapability.adapter.getCapability((EntityPlayer)living).getEquippedAccessories()){
//						if(is!=null){
//							callback.getMap().get(LivingHelper.CacheType.ACCESSORY).add(new Equipment(is));
//						}else{
//							callback.getMap().get(LivingHelper.CacheType.ACCESSORY).add(LivingHelper.NONE);
//						}
//					}
//				}
//
//			}
//
//			@Override
//			public boolean onHasChangedEquipments(ILivingHelper callback,EntityLivingBase living) {
//				if(living instanceof EntityPlayer && AccessorySlotCapability.adapter.hasCapability((EntityPlayer) living)){
//
//					int index = 0;
//					for(ItemStack is:AccessorySlotCapability.adapter.getCapability((EntityPlayer) living).getEquippedAccessories()){
//						if(!callback.getMap().get(LivingHelper.CacheType.ACCESSORY).get(index).compare(is)){
//							return true;
//						}
//
//						index ++;
//					}
////					for(ItemStack is:AccessoryHelper.getCapability((EntityPlayer)living).getAccessories()){
////						if(is!=null){
////							if(!callback.getMap().get(ACCESSORY).contains(is)){
////								return true;
////							}
////						}
////					}
//				}
//				return false;
//			}
//		});
//
//		AccessoryHelper.adapter.registerEvent();
//		LifePoint.registerEvents();
//		UnsagaVillager.registerEvents();
//		ItemBowUnsaga.registerEvents();
//		UnsagaEntityAttributes.registerEvents();
//		MaterialAnalyzer.register();
//		BarteringPriceSupplier.register();
//		ItemSkillPanel.adapter.registerEvent();
//		ComponentSelectableIcon.adapter.registerEvent();
//		ItemSkillBook.adapter.registerEvent();
//		ItemGunUnsaga.adapter.registerEvent();
//		WatchingOutCounter.adapter.registerEvent();
//		ForgingCapability.adapter.registerEvent();
//		MinsagaForgingEvent.registerEvents();
//		XPHelper.adapter.registerEvent();
//		TaggedArrowHelper.registerEvents();
//
//		materials.initDisplayItemStack();
		achievements.init();
		this.entitiesNew.init();
		this.newSkillPanels.init();
//		GameRegistry.registerWorldGenerator(WorldGeneratorUnsaga.instance(), 8);
	}

	public void preInit(){

		this.villager = this.villager.instance();
		this.registerCapabilities();
		this.materialsNew = mods.hinasch.unsaga.material.UnsagaMaterials.instance();
		this.materialsNew.preInit();
		this.rawMaterials = RawMaterialItemRegistry.instance();
		this.rawMaterials.preInit();
		this.abilitiesNew = mods.hinasch.unsaga.ability.AbilityRegistry.instance();
		this.abilitiesNew.preInit();

		this.oreBlocks = BlockOrePropertyRegistry.instance();
		this.oreBlocks.preInit();
		this.tabTools = SimpleCreativeTab.createSimpleTab("unsaga.tools");
		this.tabMisc = SimpleCreativeTab.createSimpleTab("unsaga.misc");
		this.tabSkillPanels = SimpleCreativeTab.createSimpleTab("unsaga.skillPanels");
		this.itemsNew.register();
		this.blocks = UnsagaBlockRegistry.instance();
		this.blocks.register();


		this.villager.preInit();
		this.sparklingPoint = this.sparklingPoint.instance();
		this.minsagaForging = MinsagaForging.instance();
//		itemElementChecker.setCreativeTab(CreativeTabs.TOOLS);
//		itemElementChecker.setRegistryName(UnsagaMod.MODID, "elementChecker");
//		GameRegistry.register(itemElementChecker);
//		itemTest.setCreativeTab(CreativeTabs.COMBAT);
//		itemTest.setRegistryName(UnsagaMod.MODID, "testSword");
//		itemTest.initPropertyGetter();
//		GameRegistry.register(itemTest);

//		UnsagaModIntegration.oreBlocks.preInit();
//
//		materials.init();
//		abilities.init();
//		debuffs.init();
//		skillPanels.init();
//		miscItems.preInit();
//
//		HSLibs.registerCapability(IUnsagaPropertyItem.class, new StorageIUnsagaItem(), DefaultIUnsagaPropertyItem.class);
////		HSLibs.registerCapability(IAbility.class, new StorageIAbility(), DefaultIAbility.class);
////		HSLibs.registerCapability(IAccessorySlot.class, new StorageIAccessorySlot(), DefaultIAccessorySlot.class);
////		HSLibs.registerCapability(ILifePoint.class, new StorageILifePoint(), DefaultILifePoint.class);
//
//		HSLibs.registerCapability(IUnsagaDamageSource.class, new StorageDummy<IUnsagaDamageSource>(){}, DefaultIUnsagaDamageSource.class);
////		HSLibs.registerCapability(IInfoOnEntity.class, new StorageIInfoOnEntity(), DefaultIInfoOnEntity.class);
////		HSLibs.registerCapability(IUnsagaVillager.class, new StorageIUnsagaVillager(), DefaultIUnsagaVillager.class);
//
//		AccessoryHelper.base.registerCapability();
//		LifePoint.base.registerCapability();
//		UnsagaVillager.base.registerCapability();
//		LivingHelper.adapterBase.registerCapability();
//		BarteringPriceSupplier.adapterBase.registerCapability();
//		ChestBehavior.adapterBase.registerCapability();;
//		ItemSkillPanel.adapterBase.registerCapability();
//		ComponentSelectableIcon.adapterBase.registerCapability();
//		ItemSkillBook.adapterBase.registerCapability();
//		ItemGunUnsaga.adapterBase.registerCapability();
//		WatchingOutCounter.adapterBase.registerCapability();
//		ForgingCapability.base.registerCapability();
//		XPHelper.base.registerCapability();
//		TaggedArrowHelper.base.registerCapability();
//		if(HSLib.configHandler.isDebug()){
//			testItem = new ItemTest(materials.angelite).setCreativeTab(CreativeTabs.COMBAT);
//		}
//		items.register();
//		blocks.register();
//
////		GameRegistry.register(testItem).setRegistryName("test");
//
		this.entitiesNew = mods.hinasch.unsaga.core.entity.UnsagaEntityRegistry.instance();
		entitiesNew.preInit();
//
//		abilities.initAgainstPotionMap();
//
		magic = UnsagaMagic.instance();
		magic.preInit();
//
//		minsagaForging.init();
		this.newSkillPanels = SkillPanelRegistry.instance();
		this.newSkillPanels.preInit();
		this.potions = UnsagaPotions.instance();
		this.potions.preInit();
		this.states = StateRegistry.instance();
		this.states.preInit();

		this.rawMaterials.init();
		GameRegistry.registerFuelHandler(fuelHandler);

		this.materialItemAssociation = MaterialItemAssociatedRegistry.instance();
	}

	public void postInit(){

	}
	private void registerCapabilities(){
		LifePoint.base.registerCapability();
		UnsagaMaterialCapability.base.registerCapability();
		AbilityCapability.adapterBase.registerCapability();
		AccessorySlotCapability.adapterBase.registerCapability();
		EquipmentCacheCapability.adapterBase.registerCapability();
		EntityStateCapability.adapterBase.registerCapability();
		SkillPanelCapability.adapterBase.registerCapability();
		UnsagaXPCapability.base.registerCapability();
		TargetHolderCapability.adapterBase.registerCapability();
		ChestCapability.adapterBase.registerCapability();
		this.villager.registerCapabilities();
		ItemGunUnsaga.adapterBase.registerCapability();
		ForgingCapability.base.registerCapability();
	}

	/** Capabilityのattachまたは初期化イベント*/
	private void registerCapabilityAttachEvents(){
		LifePoint.registerEvents();
		UnsagaMaterialCapability.register();
		AbilityCapability.registerEvents();
		EquipmentCacheCapability.registerEvents();
		AccessorySlotCapability.registerEvents();
		EntityStateCapability.register();
		SkillPanelCapability.registerEvents();
		UnsagaXPCapability.registerEvents();
		TargetHolderCapability.registerEvents();
		ChestCapability.register();
		this.villager.registerCapabilityAttachEvents();
		ItemGunUnsaga.adapter.registerAttachEvent();
		ForgingCapability.adapter.registerAttachEvent();
	}
}
