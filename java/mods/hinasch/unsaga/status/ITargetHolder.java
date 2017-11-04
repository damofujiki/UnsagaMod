package mods.hinasch.unsaga.status;

import java.util.Optional;

import net.minecraft.entity.EntityLivingBase;

public interface ITargetHolder {

	public void updateTarget(EntityLivingBase target);
	public Optional<EntityLivingBase> getTarget();
	public double getTargetDistance(EntityLivingBase other);
}
