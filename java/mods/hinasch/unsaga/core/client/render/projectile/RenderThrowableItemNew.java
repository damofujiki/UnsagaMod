package mods.hinasch.unsaga.core.client.render.projectile;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.RenderBase;
import mods.hinasch.lib.entity.EntityThrowableItem;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderThrowableItemNew extends RenderBase<EntityThrowableItem>{

	final ItemStack defaultStack;
	public RenderThrowableItemNew(RenderManager renderManager,ItemStack defaultStack) {
		super(renderManager);
		this.defaultStack = defaultStack;
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
    public void doRender(EntityThrowableItem entity, double x, double y, double z, float entityYaw, float partialTicks)
    {

		ItemStack stack = entity.getItemStack()!=null ? entity.getItemStack() : this.defaultStack.copy();
		TextureAtlasSprite icon = ClientHelper.getTextureAtlasSprite(stack.getItem());
//		UnsagaMod.logger.trace("render", icon,stack);
		this.renderSquare(icon,entity, x, y, z, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityThrowableItem entity) {
		// TODO 自動生成されたメソッド・スタブ
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	public void preRenderSquare(EntityThrowableItem entity,double x,double y,double z,float u,float v,float mu,float mv,float f2){
		GlStateManager.rotate(60, 1.0F, 0, 0);
	}
}
