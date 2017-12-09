package mods.hinasch.unsagamagic.enchant;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.util.HSLibs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class EnchantmentUnsaga extends Enchantment{

	final int enchantTier;
	protected EnchantmentUnsaga(EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots,int level) {
		super(Rarity.COMMON, typeIn, slots);
		this.enchantTier = level;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public int getEnchantTier(){
		return this.enchantTier;
	}
    public String getTranslatedName(int level)
    {
        String s = HSLibs.translateKey(this.getName());
        if(HSLib.isDebug()){
        	return s + "/expire:"+level;
        }
        return s;
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
        return 65535;
    }

    @Override
    public int getMinLevel()
    {
        return 0;
    }

    public static interface IWeaponEnchant{

    	public float getBowAttackModifier(int level,EnumCreatureAttribute creatureType);
    }
}