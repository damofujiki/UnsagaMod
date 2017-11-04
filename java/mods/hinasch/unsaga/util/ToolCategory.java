package mods.hinasch.unsaga.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum ToolCategory {
	KNIFE("knife"),SWORD("sword"),STAFF("staff"),SPEAR("spear"),BOW("bow"),AXE("axe"),RAW_MATERIAL("rawMaterial"),
	ARMOR("armor"),HELMET("helmet"),LEGGINS("leggins"),BOOTS("boots"),ACCESSORY("accessory"),PICKAXE("pickaxe"),GUN("gun"),SHIELD("shield");

	final String prefix;

	public static Map<ToolCategory,Item> categoryItemMap = Maps.newHashMap();

	private ToolCategory(String str){

		this.prefix = str;

	}

	public String getPrefix(){
		return this.prefix;
	}

	public boolean isWeapon(){
		return weaponSet.contains(this);
	}

	public int getCategoryPrice(int base,ItemStack stack){
		float basePrice = (float)base;
		if(stack.getItem() instanceof IUnsagaMaterialSelector){
			float durability = (float)stack.getMaxDamage() - (float)stack.getItemDamage();
			float dura_p = durability / stack.getMaxDamage() * 100;
			if(weaponSet.contains(this) || this==ACCESSORY || this==SHIELD){
				return (int)(basePrice * dura_p * 0.01F);
			}
			if(this==HELMET){
				return (int)(basePrice * 0.6F);
			}
			if(this==ARMOR){
				return (int)(basePrice * 0.75F);
			}
			if(this==LEGGINS || this==BOOTS){
				return (int)(basePrice * 0.45F);
			}
			return (int)(basePrice * 0.3F);
		}
		return 0;
	}
	public Item getAssociatedItem(){
		if(categoryItemMap.containsKey(this)){
			return categoryItemMap.get(this);
		}
		return UnsagaItemRegistry.instance().sword;

	}
	public static ToolCategory getCategoryFromItem(Item item){
		if(item!=null){
			Optional<ToolCategory> cate = categoryItemMap.entrySet().stream().filter(in -> in.getValue()==item).map(in -> in.getKey()).findFirst();
			return cate.isPresent() ? cate.get() : ToolCategory.ACCESSORY;
		}

		return null;
	}
	public static ToolCategory fromString(String str){
		for(ToolCategory cate:ToolCategory.values()){
			if(cate.prefix.equals(str)){
				return cate;
			}
		}
		return null;
	}

	public EntityEquipmentSlot getEquipmentSlot(){
		if(this==ARMOR){
			return EntityEquipmentSlot.CHEST;
		}
		if(this==HELMET){
			return EntityEquipmentSlot.HEAD;
		}
		if(this==LEGGINS){
			return EntityEquipmentSlot.LEGS;
		}
		if(this==BOOTS){
			return EntityEquipmentSlot.FEET;
		}
		return EntityEquipmentSlot.MAINHAND;
	}
	/**
	 * 今のところ宝箱・店で使用。
	 */
	public static final Set<ToolCategory> weaponSet = Sets.immutableEnumSet(ToolCategory.SWORD,ToolCategory.STAFF,ToolCategory.SPEAR,ToolCategory.BOW,ToolCategory.AXE,ToolCategory.KNIFE);
	/**
	 * 今のところ使ってない。
	 */
	public static final Set<ToolCategory> armorSet = Sets.immutableEnumSet(ToolCategory.HELMET,ToolCategory.LEGGINS,ToolCategory.ARMOR,ToolCategory.BOOTS);
	public static final Set<ToolCategory> merchandiseSet = Sets.immutableEnumSet(ToolCategory.SWORD,ToolCategory.STAFF,ToolCategory.SPEAR,ToolCategory.BOW,ToolCategory.AXE,ToolCategory.ARMOR,
			ToolCategory.HELMET,ToolCategory.LEGGINS,ToolCategory.BOOTS,ToolCategory.ACCESSORY,ToolCategory.KNIFE,ToolCategory.SHIELD,ToolCategory.RAW_MATERIAL);

	/**
	 * used in Smith now.
	 * ここに追加すると鍛冶のジャンルに現れる。
	 */
	public static final List<ToolCategory> toolArray = Lists.newArrayList(ToolCategory.SWORD,ToolCategory.STAFF,ToolCategory.SPEAR,ToolCategory.BOW,ToolCategory.AXE,ToolCategory.ACCESSORY,ToolCategory.KNIFE,ToolCategory.SHIELD);

	public static final Set<ToolCategory> weaponsExceptBow = Sets.immutableEnumSet(ToolCategory.SWORD,ToolCategory.STAFF,ToolCategory.SPEAR,ToolCategory.AXE,ToolCategory.KNIFE);

	public static HashSet<String> toString(ImmutableSet<ToolCategory> immutableSet){
		HashSet<String> newSet = new HashSet();
		for(Iterator<ToolCategory> ite=immutableSet.iterator();ite.hasNext();){
			newSet.add(ite.next().toString());
		}
		return newSet;
	}
//
	public String getLocalized(){
		return HSLibs.translateKey("unsaga.category."+this.getPrefix());
	}

	public static void registerAssociation(){
		categoryItemMap.put(ToolCategory.ACCESSORY, UnsagaItemRegistry.instance().accessory);
		categoryItemMap.put(ToolCategory.ARMOR, UnsagaItemRegistry.instance().armor);
		categoryItemMap.put(ToolCategory.AXE, UnsagaItemRegistry.instance().axe);
		categoryItemMap.put(ToolCategory.BOOTS, UnsagaItemRegistry.instance().boots);
		categoryItemMap.put(ToolCategory.BOW, UnsagaItemRegistry.instance().bow);
		categoryItemMap.put(ToolCategory.GUN, UnsagaItemRegistry.instance().musket);
		categoryItemMap.put(ToolCategory.HELMET, UnsagaItemRegistry.instance().helmet);
		categoryItemMap.put(ToolCategory.KNIFE, UnsagaItemRegistry.instance().knife);
		categoryItemMap.put(ToolCategory.LEGGINS, UnsagaItemRegistry.instance().leggins);
		categoryItemMap.put(ToolCategory.RAW_MATERIAL, UnsagaItemRegistry.instance().rawMaterials);
		categoryItemMap.put(ToolCategory.SHIELD, UnsagaItemRegistry.instance().shield);
		categoryItemMap.put(ToolCategory.SPEAR, UnsagaItemRegistry.instance().spear);
		categoryItemMap.put(ToolCategory.STAFF, UnsagaItemRegistry.instance().staff);
		categoryItemMap.put(ToolCategory.SWORD, UnsagaItemRegistry.instance().sword);
	}
	public static boolean categoryContains(ToolCategory cate,Collection colle){
		boolean isString = false;
		for(Iterator ite=colle.iterator();ite.hasNext();){
			if(ite.next() instanceof String){
				isString = true;
			}
		}
		if(isString){
			return colle.contains(cate.toString());
		}
		return colle.contains(cate);
	}
}
