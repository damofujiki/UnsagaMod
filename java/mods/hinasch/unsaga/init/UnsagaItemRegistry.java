package mods.hinasch.unsaga.init;

import mods.hinasch.lib.item.ItemCustomEntityEgg;
import mods.hinasch.lib.item.ItemIcon;
import mods.hinasch.lib.item.RecipeUtilNew;
import mods.hinasch.lib.item.SimpleCreativeTab;
import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.ability.AbilityCapability;
import mods.hinasch.unsaga.ability.AbilityRegistry;
import mods.hinasch.unsaga.common.ItemBowUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemAccessoryUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemArmorUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemAxeUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemGunUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemKnifeUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemShieldUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemSpearUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemStaffUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemSwordUnsaga;
import mods.hinasch.unsaga.core.item.newitem.misc.ItemElementChecker;
import mods.hinasch.unsaga.core.item.newitem.misc.ItemRawMaterial;
import mods.hinasch.unsaga.core.item.newitem.misc.ItemSkillPanel;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.villager.UnsagaVillagerProfession;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UnsagaItemRegistry extends BlockItemRegistry{

	private static UnsagaItemRegistry INSTANCE;

	public static UnsagaItemRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaItemRegistry();
		}
		return INSTANCE;
	}
	public Item sword;
	public Item staff;
	public Item axe;
	public Item knife;
	public Item spear;
	public Item bow;;
	public Item armor;
	public Item helmet;
	public Item boots;
	public Item leggins;
	public Item shield;
	public Item accessory;
	public Item elementChecker;
	public Item skillPanel;
	public Item ammo;
	public Item musket;
	public Item rawMaterials;
	public ItemCustomEntityEgg entityEggs;

	public Item iconCondition;
	protected UnsagaItemRegistry() {
		super(UnsagaMod.MODID);
		this.setUnlocalizedNamePrefix("unsaga");
	}
	@Override
	public void register() {
		SimpleCreativeTab tabTools = UnsagaModCore.instance().tabTools;
		SimpleCreativeTab tabMisc = UnsagaModCore.instance().tabMisc;
		SimpleCreativeTab tabSkillPanels = UnsagaModCore.instance().tabSkillPanels;

		this.sword = (Item) this.put(new ItemSwordUnsaga(), "sword", tabTools);
		this.axe = (Item) this.put(new ItemAxeUnsaga(), "axe", tabTools);
		this.knife = (Item) this.put(new ItemKnifeUnsaga(), "knife", tabTools);
		this.staff = (Item) this.put(new ItemStaffUnsaga(), "staff", tabTools);
		this.bow = (Item) this.put(new ItemBowUnsaga(), "bow", tabTools);
		this.spear = (Item) this.put(new ItemSpearUnsaga(), "spear", tabTools);
		this.helmet = (Item) this.put(new ItemArmorUnsaga(ToolCategory.HELMET), "helmet", tabTools);
		this.boots = (Item) this.put(new ItemArmorUnsaga(ToolCategory.BOOTS), "boots", tabTools);
		this.leggins = (Item) this.put(new ItemArmorUnsaga(ToolCategory.LEGGINS), "leggins", tabTools);
		this.armor = (Item) this.put(new ItemArmorUnsaga(ToolCategory.ARMOR), "armor", tabTools);
		this.shield = (Item)this.put(new ItemShieldUnsaga(), "shield", tabTools);
		this.accessory = (Item) this.put(new ItemAccessoryUnsaga(), "accessory", tabTools);
		this.elementChecker = (Item) this.put(new ItemElementChecker(), "elementChecker", tabMisc);
		this.skillPanel = (Item) this.put(new ItemSkillPanel(), "skillPanel", tabSkillPanels);
		this.iconCondition = (Item) this.put(new ItemIcon(), "icon.condition", null);
		this.rawMaterials = (Item) this.put(new ItemRawMaterial("unsaga.rawMaterial"), "rawMaterial", tabMisc);
		this.entityEggs = (ItemCustomEntityEgg) put(new ItemCustomEntityEgg(),"entityEgg",tabMisc);

		this.entityEggs.addMaping(0, EntityVillager.class, "unsaga.villager.merchant", 0xff0000, 0x000000,(w,e,p)->{
			if(e instanceof EntityVillager){
				((EntityVillager)e).setProfession(UnsagaVillagerProfession.instance().merchant);
			}
			return e;
		});
		this.entityEggs.addMaping(1, EntityVillager.class, "unsaga.villager.magicMerchant", 0xff0000, 0x000000,(w,e,p)->{
			if(e instanceof EntityVillager){
				((EntityVillager)e).setProfession(UnsagaVillagerProfession.instance().magicMerchant);
			}
			return e;
		});
		this.entityEggs.addMaping(2, EntityVillager.class, "unsaga.villager.blackSmith", 0xff0000, 0x000000,(w,e,p)->{
			if(e instanceof EntityVillager){
				((EntityVillager)e).setProfession(UnsagaVillagerProfession.instance().unsagaSmith);
			}
			return e;
		});


		this.musket = (Item) this.put(new ItemGunUnsaga(), "musket", tabMisc);
		this.ammo = (Item) this.put(new Item(), "ammo", tabMisc);
	}

	public void registerRecipes(){
		RecipeUtilNew.RecipeShaped.create().setBase("G I"," P ").addAssociation('G', Items.GUNPOWDER)
		.addAssociation('I', Items.IRON_INGOT).addAssociation('P', Items.PAPER).setOutput(new ItemStack(ammo,4)).register();
	}

	public static ItemStack getItemStack(Item item,UnsagaMaterial mate,int meta){
		ItemStack newStack = new ItemStack(item,1,meta);
		UnsagaMaterial material = mate;
		if(item==UnsagaItemRegistry.instance().musket){
			material = UnsagaMaterials.instance().iron;
		}
		if(material==UnsagaMaterials.instance().dragonHeart){
			if(AbilityCapability.adapter.hasCapability(newStack)){
				AbilityCapability.adapter.getCapability(newStack).setAbility(3, AbilityRegistry.instance().superHealing);
			}
		}
		if(UnsagaMaterialCapability.adapter.hasCapability(newStack)){
			UnsagaMaterialCapability.adapter.getCapability(newStack).setMaterial(material);
			UnsagaMaterialCapability.adapter.getCapability(newStack).setWeight(material.getWeight());
		}
		return newStack;
	}

}
