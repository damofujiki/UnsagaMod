package mods.hinasch.unsaga.ability;

import java.util.LinkedList;
import java.util.List;

import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.iface.IRequireInitializing;

public interface IAbilityAttachable extends IRequireInitializing,ISyncCapability{

	public int getMaxAbilitySize();
	public void setMaxAbilitySize(int size);
	public LinkedList<IAbility> getLearnedAbilities();
	public void setAbility(int index,IAbility ab);
	public void setAbilities(LinkedList<IAbility> list);
	public void removeAbility(IAbility ab);
	public boolean hasAbility(IAbility ab);
	public boolean isAbilityEmpty();
	public void clearAbility(int size);
	public void addAbility(IAbility ab);
	public boolean isUniqueItem();
	public List<IAbility> getLearnableUniqueAbilities();
	public void setLearnableUniqueAbilities(List<IAbility> list);
	public boolean isAbilityFull();
}
