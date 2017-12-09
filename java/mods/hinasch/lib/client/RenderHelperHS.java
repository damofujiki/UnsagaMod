package mods.hinasch.lib.client;

import org.lwjgl.opengl.GL11;

import mods.hinasch.lib.world.XYZPos;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class RenderHelperHS {

	FontRenderer fontRenderer;
	RenderManager renderManager;

	protected RenderHelperHS(FontRenderer f,RenderManager r){

		this.fontRenderer = f;
		this.renderManager = r;
	}

	public static RenderHelperHS create(FontRenderer f,RenderManager r){
		return new RenderHelperHS(f, r);
	}


	public static void drawTexturedRect(float x,float y,float textureX,float textureY,float width,float height){
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), 100).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), 100).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), 100).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), 100).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();

		GlStateManager.color(1, 1, 1, 1);
	}
    public static void drawRect(int left, int top, int right, int bottom, int color,int alpha)
    {
        if (left < right)
        {
            int i = left;
            left = right;
            right = i;
        }

        if (top < bottom)
        {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float f3 =(float)(alpha / 255.0F);
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos((double)left, (double)bottom, 0.0D).endVertex();
        vertexbuffer.pos((double)right, (double)bottom, 0.0D).endVertex();
        vertexbuffer.pos((double)right, (double)top, 0.0D).endVertex();
        vertexbuffer.pos((double)left, (double)top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	/*
	 *
	 * renderPos = ワールド座標でなく画面上のレンダー座標。
	 */
	@SideOnly(Side.CLIENT)
	public void renderStringAt(Entity entity, String str, XYZPos renderPos, int dist,int color)
	{

		double d3 = entity.getDistanceSqToEntity(renderManager.renderViewEntity);

		Tessellator tessellator = Tessellator.getInstance();
		double x = renderPos.dx;
		double y = renderPos.dy;
		double z = renderPos.dz;
		if (d3 <= (double)(dist * dist))
		{
			FontRenderer fontrenderer = fontRenderer;
			float f = 1.6F;
			float f1 = 0.016666668F * f;
			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x + 0.0F, (float)y + entity.height + 0.5F, (float)z);
			GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
			GlStateManager.scale(-f1, -f1, f1);
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			GL11.glDisable(GL11.GL_DEPTH_TEST);



			GlStateManager.enableBlend();
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);

			VertexBuffer worldRenderer = Tessellator.getInstance().getBuffer();
			byte b0 = 0;


			GlStateManager.disableTexture2D();
			int j = fontrenderer.getStringWidth(str) / 2;
			//	        worldRenderer.startDrawingQuads();
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR); //これがないとヌルぽ
			worldRenderer.color(0.0F, 0.0F, 0.0F, 0.25F);
			worldRenderer.pos((double)(-j - 1), (double)(-1 + b0), 0.0D);
			worldRenderer.pos((double)(-j - 1), (double)(8 + b0), 0.0D);
			worldRenderer.pos((double)(j + 1), (double)(8 + b0), 0.0D);
			worldRenderer.pos((double)(j + 1), (double)(-1 + b0), 0.0D);
			tessellator.draw();
			GlStateManager.enableTexture2D();
			fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, color,true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GlStateManager.enableDepth();
			fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, b0, -1);
			GlStateManager.enableLighting();

			GlStateManager.disableBlend();


			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.popMatrix();
		}
	}

	//	@SideOnly(Side.CLIENT)
	//	public static void spawnParticleHappy(World par0World, int par1, int par2, int par3, int par4,Random itemRand)
	//	{
	//		IBlockState block = par0World.getBlockState(par1, par2, par3);
	//
	//		if (par4 == 0)
	//		{
	//			par4 = 15;
	//		}
	//
	//		//Block block = i1 > 0 && i1 < Block.blocksList.length ? Block.blocksList[i1] : null;
	//
	//		if (block != null)
	//		{
	//			block.setBlockBoundsBasedOnState(par0World, par1, par2, par3);
	//
	//			for (int j1 = 0; j1 < par4; ++j1)
	//			{
	//				double d0 = itemRand.nextGaussian() * 0.02D;
	//				double d1 = itemRand.nextGaussian() * 0.02D;
	//				double d2 = itemRand.nextGaussian() * 0.02D;
	//				par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * block.getBlockBoundsMaxY(), (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
	//			}
	//		}
	//		else
	//		{
	//			for (int j1 = 0; j1 < par4; ++j1)
	//			{
	//				double d0 = itemRand.nextGaussian() * 0.02D;
	//				double d1 = itemRand.nextGaussian() * 0.02D;
	//				double d2 = itemRand.nextGaussian() * 0.02D;
	//				par0World.spawnParticle("happyVillager", (double)((float)par1 + itemRand.nextFloat()), (double)par2 + (double)itemRand.nextFloat() * 1.0f, (double)((float)par3 + itemRand.nextFloat()), d0, d1, d2);
	//			}
	//		}
	//	}

	public static void putSmokeParticle(World world,double x,double y,double z,int sx,int sy,int sz){
		double d0 = (double)((float)sx + world.rand.nextFloat());
		double d1 = (double)((float)sy + world.rand.nextFloat());
		double d2 = (double)((float)sz + world.rand.nextFloat());
		double d3 = d0 - x;
		double d4 = d1 - y;
		double d5 = d2 - z;
		double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
		d3 /= d6;
		d4 /= d6;
		d5 /= d6;
		double d7 = 0.5D / (d6 / (double)1.0D+ 0.1D);
		d7 *= (double)(world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
		d3 *= d7;
		d4 *= d7;
		d5 *= d7;
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
	}
	//    public static MovingObjectPosition getMovingObjectPosition(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
	//    {
	//        float f = 1.0F;
	//        float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
	//        float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
	//        double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * (double)f;
	//        double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * (double)f + 1.62D - (double)par2EntityPlayer.yOffset;
	//        double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * (double)f;
	//        Vec3 vec3 = par1World.getWorldVec3Pool().getVecFromPool(d0, d1, d2);
	//        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
	//        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
	//        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
	//        float f6 = MathHelper.sin(-f1 * 0.017453292F);
	//        float f7 = f4 * f5;
	//        float f8 = f3 * f5;
	//        double d3 = 5.0D;
	//        if (par2EntityPlayer instanceof EntityPlayerMP)
	//        {
	//            d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
	//        }
	//        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
	//        return par1World.rayTraceBlocks_do_do(vec3, vec31, par3, !par3);
	//    }

}
