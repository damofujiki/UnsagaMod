package mods.hinasch.unsaga.core.entity;

import java.util.Set;

import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.iface.IRequireInitializing;

public interface IEntityState extends IRequireInitializing,ISyncCapability{

	public void init();
	public State getState(StateProperty prop);
	public void setState(StateProperty prop,State state);
	public Set<StateProperty> getStateProperties();
}
