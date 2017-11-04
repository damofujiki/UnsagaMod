package mods.hinasch.lib.client;

import mods.hinasch.lib.entity.EntityThrowableItem;
import mods.hinasch.unsaga.core.client.render.projectile.IRotation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public abstract class RenderBase<T extends Entity> extends Render<T>{

	protected RenderBase(RenderManager renderManager) {
		super(renderManager);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public void renderSquare(T entity,double x,double y,double z,float u,float v,float mu,float mv,float f2){
		renderSquare(null,entity, x, y, z, u, v, mu, mv, f2);
	}
	public void renderSquare(TextureAtlasSprite icon,T entity,double x,double y,double z,float u,float v,float mu,float mv,float f2){

		GlStateManager.pushMatrix();
		this.bindEntityTexture(entity);
		GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.disableLighting();
		GlStateManager.scale(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		float f3 = u;
		float f4 = mu;
		float f5 = v;
		float f6 = mv;
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;
		if(icon!=null){
			f3 = icon.getMinU();
			f4 = icon.getMaxU();
			f5 = icon.getMinV();
			f6 = icon.getMaxV();
		}
		if(entity instanceof EntityThrowableItem){
			ItemStack stack = ((EntityThrowableItem)entity).getItemStack();
			if(stack!=null && stack.getItem() instanceof IItemColor){
				IItemColor iItemColor = ((IItemColor) stack.getItem());
				int k = iItemColor.getColorFromItemstack(stack, stack.getItemDamage());

				float f31 = (float)(k >> 16 & 255) / 255.0F;
				float f32 = (float)(k >> 8 & 255) / 255.0F;
				float f33 = (float)(k & 255) / 255.0F;
				GlStateManager.color(f31, f32, f33, 1.0F);
			}
		}


        GlStateManager.enableRescaleNormal();
		GlStateManager.rotate(180.0F - this.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-this.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		this.preRenderSquare(entity, x, y, z, u, v, mu, mv, f2);
		if(entity instanceof IRotation){
			GlStateManager.rotate(((IRotation) entity).getEntityRotation(), 0.0F, 1.0F, 1.0F);
		}
		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		vertexBuffer.pos(-0.5D, -0.25D, 0.0D).tex((double)f3, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(0.5D, -0.25D, 0.0D).tex((double)f4, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(0.5D, 0.75D, 0.0D).tex((double)f4, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(-0.5D, 0.75D, 0.0D).tex((double)f3, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
		tessellator.draw();


        GlStateManager.disableRescaleNormal();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
	}

	public void preRenderSquare(T entity,double x,double y,double z,float u,float v,float mu,float mv,float f2){

	}
}
