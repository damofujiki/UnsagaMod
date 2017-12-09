package mods.hinasch.unsaga.core.client.render.projectile;

import mods.hinasch.lib.client.RenderBase;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.projectile.EntityIceNeedle;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderIceNeedle extends RenderBase<EntityIceNeedle>{

	public static final ResourceLocation RES = new ResourceLocation(UnsagaMod.MODID,"textures/entity/projectiles/needle.png");
	public RenderIceNeedle(RenderManager renderManager) {
		super(renderManager);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    public void doRender(EntityIceNeedle entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
		this.renderSquare(entity, x, y, z, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
	@Override
	protected ResourceLocation getEntityTexture(EntityIceNeedle entity) {
		// TODO 自動生成されたメソッド・スタブ
		return RES;
	}

}
