package mods.hinasch.unsagamagic.enchant;

import mods.hinasch.lib.registry.PropertyRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UnsagaEnchantmentRegistry extends PropertyRegistry<UnsagaEnchantment>{

	private static UnsagaEnchantmentRegistry INSTANCE;

	public static UnsagaEnchantmentRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaEnchantmentRegistry();
		}
		return INSTANCE;
	}

	protected UnsagaEnchantmentRegistry(){

	}
	public final EntityEquipmentSlot[] slots = {EntityEquipmentSlot.MAINHAND};
	public final EntityEquipmentSlot[] slots2 = {EntityEquipmentSlot.CHEST,EntityEquipmentSlot.CHEST.FEET,EntityEquipmentSlot.CHEST.HEAD,EntityEquipmentSlot.CHEST.LEGS};
	public UnsagaEnchantment weaponBless = new UnsagaEnchantment("weaponBless").setEnchantmentSupplier(new EnchantmentWeaponBless(slots));
	public UnsagaEnchantment armorBless = new UnsagaEnchantment("armorBless").setEnchantmentSupplier(new EnchantmentArmorBless(slots2));
	public UnsagaEnchantment sharpness = new UnsagaEnchantment("sharpness").setEnchantmentSupplier(new EnchantmentSharpness(slots));
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ


	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	@Override
	protected void registerObjects() {
		// TODO 自動生成されたメソッド・スタブ
		this.put(weaponBless);
		this.put(armorBless);
		this.put(sharpness);

		GameRegistry.register(weaponBless.getEnchantment());
		GameRegistry.register(armorBless.getEnchantment());
		GameRegistry.register(sharpness.getEnchantment());
	}

	public static abstract class EnchantmentBase extends Enchantment{

		protected EnchantmentBase(EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
			super(Rarity.COMMON, typeIn, slots);
			// TODO 自動生成されたコンストラクター・スタブ
		}


	    @Override
	    public boolean canApplyAtEnchantingTable(ItemStack stack)
	    {
	        return false;
	    }

	    @Override
	    public boolean isAllowedOnBooks()
	    {
	        return false;
	    }

	    @Override
	    public int getMaxLevel()
	    {
	        return 3;
	    }
	}

	public static class EnchantmentWeaponBless extends EnchantmentBase{

		protected EnchantmentWeaponBless(EntityEquipmentSlot[] slots) {
			super(EnumEnchantmentType.WEAPON, slots);

		}

		@Override
	    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
	    {
	        return 0.5F * level;
	    }
	}

	public static class EnchantmentArmorBless extends EnchantmentBase{

		protected EnchantmentArmorBless(EntityEquipmentSlot[] slots) {
			super(EnumEnchantmentType.ARMOR, slots);

		}

		@Override
	    public int calcModifierDamage(int level, DamageSource source)
	    {
	        return source.canHarmInCreative() ? 0 : level;
	    }
	}

	public static class EnchantmentSharpness extends EnchantmentBase{

		protected EnchantmentSharpness(EntityEquipmentSlot[] slots) {
			super(EnumEnchantmentType.ALL, slots);

		}

		@Override
	    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
	    {
	        return 1.0F * level;
	    }
	}


	public static void registerEvents(){

	}
}
