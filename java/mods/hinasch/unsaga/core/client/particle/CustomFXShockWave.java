package mods.hinasch.unsaga.core.client.particle;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomFXShockWave extends Particle
{
	private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation(UnsagaMod.MODID,"textures/entity/shockwave.png");
	private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
	private int life;
	private final int lifeTime;
	/** The Rendering Engine. */
	private final TextureManager theRenderEngine;
	private final float size;

	boolean rotated = false;
	public CustomFXShockWave(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.theRenderEngine = renderEngine;
		this.lifeTime = 6 + this.rand.nextInt(4);
		float f = this.rand.nextFloat() * 0.6F + 0.4F;
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		this.particleAngle = (float) (0.5F * Math.PI);
//		 BlockPos pos = UnsagaMod.proxy.getDebugPos();
//		 GlStateManager.rotate(pos.getX(), 0,1.0F,0);
//		 BlockPos pos = UnsagaMod.proxy.getDebugPos();
//		 this.particleAngle = (float) Math.toRadians(pos.getX());
		this.size = 1.0F - (float)p_i1213_9_ * 0.5F;
	}

	/**
	 * Renders the particle
	 */
	 public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	 {
		 int i = (int)(((float)this.life + partialTicks) * 15.0F / (float)this.lifeTime);


		 if (i <= 15)
		 {
			 this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
			 float f = (float)(i % 4) / 4.0F;
			 float f1 = f + 0.24975F;
			 float f2 = (float)(i / 4) / 4.0F;
			 float f3 = f2 + 0.24975F;
			 float f4 = 2.0F * this.size;
			 float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks - interpPosX);
			 float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks - interpPosY);
			 float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks - interpPosZ);
			 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			 if(!this.rotated){
				 this.rotated = true;

			 }
			 BlockPos pos =UnsagaMod.proxy.getDebugPos();
			 GlStateManager.rotate(pos.getX(), 1.0F,0,0);
//			 GlStateManager.translate(pos.getX(),pos.getY(),pos.getZ());

		        Vec3d[] avec3d = new Vec3d[] {new Vec3d((double)(-rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(-rotationYZ * f4 - rotationXZ * f4)), new Vec3d((double)(-rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(-rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 + rotationXY * f4), (double)(rotationZ * f4), (double)(rotationYZ * f4 + rotationXZ * f4)), new Vec3d((double)(rotationX * f4 - rotationXY * f4), (double)(-rotationZ * f4), (double)(rotationYZ * f4 - rotationXZ * f4))};

		        if (this.particleAngle != 0.0F)
		        {
		            float f8 = this.particleAngle + (this.particleAngle - this.prevParticleAngle) * partialTicks;
		            float f9 = MathHelper.cos(f8 * 0.5F);
		            float f10 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.xCoord;
		            float f11 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.yCoord;
		            float f12 = MathHelper.sin(f8 * 0.5F) * (float)cameraViewDir.zCoord;
		            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

		            for (int l = 0; l < 4; ++l)
		            {
		                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
		            }
		        }
//				 BlockPos pos = UnsagaMod.proxy.getDebugPos();
//				 double d = pos.getX() * 0.01D;
			 GlStateManager.disableLighting();
			 RenderHelper.disableStandardItemLighting();
			 worldRendererIn.begin(7, VERTEX_FORMAT);
			 worldRendererIn.pos((double)f5 + avec3d[0].xCoord, (double)f6 + avec3d[0].yCoord , (double)f7 + avec3d[0].zCoord).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			 worldRendererIn.pos((double)f5 + avec3d[1].xCoord, (double)f6 + avec3d[1].yCoord, (double)f7 + avec3d[1].zCoord).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			 worldRendererIn.pos((double)f5 + avec3d[2].xCoord, (double)f6 + avec3d[2].yCoord, (double)f7 + avec3d[2].zCoord).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			 worldRendererIn.pos((double)f5 + avec3d[3].xCoord, (double)f6 + avec3d[3].yCoord, (double)f7 + avec3d[3].zCoord).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//			 worldRendererIn.pos((double)(f5 - rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 - rotationYZ * f4 - rotationXZ * f4)).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//			 worldRendererIn.pos((double)(f5 - rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 - rotationYZ * f4 + rotationXZ * f4)).tex((double)f1, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//			 worldRendererIn.pos((double)(f5 + rotationX * f4 + rotationXY * f4), (double)(f6 + rotationZ * f4), (double)(f7 + rotationYZ * f4 + rotationXZ * f4)).tex((double)f, (double)f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
//			 worldRendererIn.pos((double)(f5 + rotationX * f4 - rotationXY * f4), (double)(f6 - rotationZ * f4), (double)(f7 + rotationYZ * f4 - rotationXZ * f4)).tex((double)f, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
			 Tessellator.getInstance().draw();
			 GlStateManager.enableLighting();
		 }
	 }

	 public int getBrightnessForRender(float p_189214_1_)
	 {
		 return 61680;
	 }

	 public void onUpdate()
	 {
		 this.prevPosX = this.posX;
		 this.prevPosY = this.posY;
		 this.prevPosZ = this.posZ;
		 ++this.life;

		this.prevParticleAngle = particleAngle;

		 if (this.life == this.lifeTime)
		 {
			 this.setExpired();
		 }
	 }

	 /**
	  * Retrieve what effect layer (what texture) the particle should be rendered with. 0 for the particle sprite sheet,
	  * 1 for the main Texture atlas, and 3 for a custom texture
	  */
	 public int getFXLayer()
	 {
		 return 3;
	 }

	 @SideOnly(Side.CLIENT)
	 public static class Factory implements IParticleFactory
	 {
		 public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
		 {
			 return new CustomFXShockWave(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		 }
	 }

}