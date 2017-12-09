package mods.hinasch.lib.client;

import mods.hinasch.lib.core.HSLib;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;

public class RenderPlayerDebug extends RenderPlayer{

	public static final ResourceLocation SKIN = new ResourceLocation(HSLib.MODID,"textures/entity/player.png");
	public RenderPlayerDebug(RenderManager renderManager) {
		super(renderManager);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
    protected ResourceLocation getEntityTexture(AbstractClientPlayer entity)
    {

        return SKIN;
    }
}
