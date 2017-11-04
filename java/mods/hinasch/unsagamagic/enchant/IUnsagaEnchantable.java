package mods.hinasch.unsagamagic.enchant;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mods.hinasch.lib.iface.IRequireInitializing;

public interface IUnsagaEnchantable extends IRequireInitializing{

	public void init();
	public int getEnchantmentRemaining(UnsagaEnchantment e);
	public void setEnchantmentRemaining(UnsagaEnchantment e,int r);
	public Set<Entry<UnsagaEnchantment,Integer>> getEntries();
	public void setMap(Map<UnsagaEnchantment,Integer> map);
	public void reduceRemainings(UnsagaEnchantment e);
	public void onAttack();
	public void onHurt();
	public boolean isEmpty();
}
