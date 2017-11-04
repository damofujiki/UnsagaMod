package mods.hinasch.unsaga.core.client.render.projectile;

import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.IProjectile;

public class RenderItemAttackable extends RenderEntityItem implements IProjectile{

	public RenderItemAttackable(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
		super(renderManagerIn, p_i46167_2_);

	}

	@Override
	public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
