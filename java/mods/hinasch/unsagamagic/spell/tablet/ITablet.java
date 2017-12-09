package mods.hinasch.unsagamagic.spell.tablet;

import java.util.List;
import java.util.Map;

import mods.hinasch.lib.iface.IRequireInitializing;
import mods.hinasch.unsagamagic.item.ItemTablet.DecipheringPair;
import mods.hinasch.unsagamagic.spell.Spell;


public interface ITablet extends IRequireInitializing{

	public MagicTablet getTabletData();
	public void setTabletData(MagicTablet tablet);
	public void progressDecipher(Spell spell,int progress);
	public int getProgress(Spell spell);
	public Map<Spell,Integer> getDecipheringProgressMap();
	public List<DecipheringPair> getAsList();
	public void setDecipheringProgressMap(Map<Spell,Integer> progressList);
}
