package mods.hinasch.unsagamagic.enchant;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mods.hinasch.lib.iface.IRequireInitializing;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentCapability.EnchantmentState;
import net.minecraft.entity.EntityLivingBase;

public interface IUnsagaEnchantable extends IRequireInitializing{

	public void init();
	public EnchantmentState getEnchantment(EnchantmentProperty e);
	public void setEnchantment(EnchantmentProperty e,EnchantmentState time);
	public Set<Entry<EnchantmentProperty,EnchantmentState>> getEntries();
	public void setMap(Map<EnchantmentProperty,EnchantmentState> map);
	public boolean isEmpty();
	public boolean hasEnchant(EnchantmentProperty e);
	public void checkExpireTime(EntityLivingBase living,long totalWorldTime);
	public float getBowModifier();
}
