package mods.hinasch.unsaga.core.client.render.projectile;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.unsaga.core.entity.IItemStackable;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;

public abstract class ComponentRenderFlyingItem<T extends Entity> {

	protected Render parent;


	public void doRender(T axe, double x, double y, double z, float yaw, float partialTicks)
	{

		//UnsagaMod.logger.trace("render");

		GlStateManager.pushMatrix();



//		this.bindEntityTexture(axe);
		this.adapterBindTexture(axe);

		GlStateManager.translate((float)x, (float)y, (float)z);
		GlStateManager.enableRescaleNormal();
//		float f2 = this.field_77002_a;
		float f2 = this.getScale();
		GlStateManager.scale(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
		TextureAtlasSprite icon = ClientHelper.getTextureAtlasSprite(this.getItem(),this.getMeta());



//		UnsagaMod.logger.trace("icon", icon);



		if(axe instanceof IItemStackable){
			IItemStackable iItemStacker = (IItemStackable) axe;
			if(iItemStacker.getInnerItemStack()!=null){
				IItemColor iItemColor = (IItemColor) iItemStacker.getInnerItemStack().getItem();
				int k = iItemColor.getColorFromItemstack(iItemStacker.getInnerItemStack(), 0);

				float f31 = (float)(k >> 16 & 255) / 255.0F;
				float f32 = (float)(k >> 8 & 255) / 255.0F;
				float f33 = (float)(k & 255) / 255.0F;
				GlStateManager.color(f31, f32, f33, 1.0F);
			}
		}



		//System.out.println("render:"+axe.itemstackaxe);
		//this.loadTexture("/gui/items.png");
		this.renderSquare(icon, axe);

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

	}

	public void renderSquare(TextureAtlasSprite icon,T axe){

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer vertexBuffer = tessellator.getBuffer();
		float f3 = icon.getMinU();
		float f4 = icon.getMaxU();
		float f5 = icon.getMinV();
		float f6 = icon.getMaxV();
		float f7 = 1.0F;
		float f8 = 0.5F;
		float f9 = 0.25F;
		GlStateManager.rotate(180.0F - adapterGetRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-adapterGetRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		if(axe instanceof IRotation){
			GlStateManager.rotate(((IRotation) axe).getEntityRotation(), 0.0F, 1.0F, 1.0F);
		}

		vertexBuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
		vertexBuffer.pos(-0.5D, -0.25D, 0.0D).tex((double)f3, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(0.5D, -0.25D, 0.0D).tex((double)f4, (double)f6).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(0.5D, 0.75D, 0.0D).tex((double)f4, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
		vertexBuffer.pos(-0.5D, 0.75D, 0.0D).tex((double)f3, (double)f5).normal(0.0F, 1.0F, 0.0F).endVertex();
		tessellator.draw();
	}
	public abstract void adapterBindTexture(T entity);
	public abstract float getScale();
	public abstract RenderManager adapterGetRenderManager();
	public abstract Item getItem();
	public int getMeta(){
		return 0;
	}
}
