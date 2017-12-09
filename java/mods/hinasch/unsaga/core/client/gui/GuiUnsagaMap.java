package mods.hinasch.unsaga.core.client.gui;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.GuiContainerBase;
import mods.hinasch.lib.client.RenderHelperHS;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.inventory.container.ContainerUnsagaMap;
import mods.hinasch.unsaga.core.potion.StatePropertyOreDetecter.StateOreDetecter;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class GuiUnsagaMap extends GuiContainerBase{

	public static enum Type{
		FIELD,DUNGEON;
	}
	World world;
	EntityPlayer ep;
	int resolution = 3;
	Type type;
	public GuiUnsagaMap(EntityPlayer ep,Type type) {
		super(new ContainerUnsagaMap(ep));
		this.world = ep.getEntityWorld();
		this.ep = ep;
		this.type = type;

		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		switch(type){
		case DUNGEON:
			this.drawCaveMap(par1, par2);
			break;
		case FIELD:
			this.drawFieldMap(par1, par2);
			break;
		default:
			break;

		}

		this.drawEntity();


	}

	@Override
	public String getGuiTextureName(){
		return UnsagaMod.MODID+":textures/gui/container/black.png";
	}

	public void drawCaveMap(int par1,int par2){
		int max = 175/resolution;
		int maxheight = 10;
		BlockPos mapOrigin = ep.getPosition().add(-max/2, -maxheight/2, -max/2);
		for(int i=0;i<max;i++){
			for(int j=0;j<max;j++){
				int v= 0;
				for(int z=0;z<maxheight;z++){
					BlockPos pos = mapOrigin.add(i, z, j);
					IBlockState state = world.getBlockState(pos);
					if(state.getBlock()==Blocks.AIR){
						v ++;
					}
				}
				v= MathHelper.clamp_int(v, 1, 15);
				int gray = 1118481 * v;


				RenderHelperHS.drawRect(i*resolution, j*resolution, i*resolution+resolution, j*resolution+resolution, gray,255);
			}
		}
	}
	public void drawEntity(){
		int max = 175/resolution;
		BlockPos mapOrigin = ep.getPosition().add(-max/2, 0, -max/2);
		this.world.getEntitiesWithinAABB(EntityLivingBase.class, HSLibs.getBounding(ep.getPosition(), 30, 4))
		.forEach(in ->{
			checkPotionExpire(in, UnsagaPotions.instance().detected);
			if(in.isPotionActive(MobEffects.GLOWING) || in==this.ep){
				BlockPos enp = in.getPosition().subtract(mapOrigin);
				GlStateManager.disableBlend();
				ClientHelper.bindTextureToTextureManager(new ResourceLocation(this.getGuiTextureName()));
				if(in!=this.ep){
					this.drawTexturedModalRect(enp.getX()*resolution, enp.getZ()*resolution, 0, 168, 8, 8);
				}else{
					this.drawTexturedModalRect(enp.getX()*resolution, enp.getZ()*resolution, 8, 168, 8, 8);
				}

				GlStateManager.enableBlend();
			}

		});
		this.checkPotionExpire(ep, UnsagaPotions.instance().detectTreasure);
		if(this.ep.isPotionActive(UnsagaPotions.instance().detectTreasure)){
			if(EntityStateCapability.adapter.hasCapability(ep)){
				StateOreDetecter state = (StateOreDetecter) EntityStateCapability.adapter.getCapability(ep).getState(StateRegistry.instance().stateOreDetecter);
				state.getOrePosList().forEach(in ->{
					BlockPos enp = in.subtract(mapOrigin);
					GlStateManager.disableBlend();
					ClientHelper.bindTextureToTextureManager(new ResourceLocation(this.getGuiTextureName()));
					this.drawTexturedModalRect(enp.getX()*resolution, enp.getZ()*resolution, 16, 168, 8, 8);
					GlStateManager.enableBlend();
				});
			}
		}
	}


	private void checkPotionExpire(EntityLivingBase el,Potion potion){
		if(el.getActivePotionEffect(potion)!=null){
			if(el.getActivePotionEffect(potion).getDuration()<=0){
				el.removePotionEffect(potion);
			}
		}
	}

	public int getSeaLightLevel(BlockPos height){
		if(height.getY()-world.getSeaLevel()>0){
			int fromSea = height.getY()-world.getSeaLevel();
			int a = (fromSea)/5;
		}

		int z = height.getY()-world.getSeaLevel();
		z= z /5 + 5;
		z = MathHelper.clamp_int(z, 1, 15);
		return z;
	}
	public void drawFieldMap(int par1,int par2){
		int max = 175/resolution;

		BlockPos mapOrigin = ep.getPosition().add(-max/2, 0, -max/2);
		for(int j=0;j<max;j++){
			int prevHeight = world.getTopSolidOrLiquidBlock(mapOrigin.add(-1,0,j)).getY();
			for(int i=0;i<max;i++){

				BlockPos pos = mapOrigin.add(i, 0, j);
				BlockPos height = world.getTopSolidOrLiquidBlock(pos);
				int z= 5;
				if(prevHeight<height.getY()){
					z = 8;
				}
				if(prevHeight>height.getY()){
					z = 3;
				}
				prevHeight = height.getY();

				IBlockState state = world.getBlockState(height);
				if(state.getMaterial()==Material.WATER){
					z = this.getSeaLightLevel(height);
				}
				if(state.getBlock()==Blocks.AIR){
					state = world.getBlockState(height.down());
				}
				MapColor color = state.getMapColor();
				int col = color.colorValue;
				int alpha = 255;

				//				int z= this.getLightValue(height.down());


				//				if(height.getY()-world.getSeaLevel()>0){
				//					int fromSea = height.getY()-world.getSeaLevel();
				//					int a = (fromSea)/5;
				//					alpha = 16 *a;
				//					alpha = MathHelper.clamp_int(alpha, 0, 255);
				//				}
				//
				//				int z = height.getY()-world.getSeaLevel();
				//				z= z /5 + 5;
				//				z = MathHelper.clamp_int(z, 1, 15);
				int gray = 1118481 * z;

				RenderHelperHS.drawRect(i*resolution, j*resolution, i*resolution+resolution, j*resolution+resolution, gray,255);
				RenderHelperHS.drawRect(i*resolution, j*resolution, i*resolution+resolution, j*resolution+resolution, color.colorValue,100);
			}
		}
	}
}
