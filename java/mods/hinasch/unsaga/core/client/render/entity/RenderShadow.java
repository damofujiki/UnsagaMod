package mods.hinasch.unsaga.core.client.render.entity;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.passive.EntityShadow;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderShadow extends RenderBiped<EntityShadow>{

	public static final ResourceLocation SHADOW_TEXTURE = new ResourceLocation(UnsagaMod.MODID,"textures/entity/shadow.png");
	public static ModelBiped model = new ModelBiped();

	public RenderShadow(RenderManager renderManagerIn, float shadowSize) {
		super(renderManagerIn, model, shadowSize);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    protected ResourceLocation getEntityTexture(EntityShadow entity){
    	return SHADOW_TEXTURE;
    }

	@Override
    protected void preRenderCallback(EntityShadow entitylivingbaseIn, float partialTickTime)
    {
		GlStateManager.enableBlend();
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }

}
