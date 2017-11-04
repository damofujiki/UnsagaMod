package mods.hinasch.unsaga.core.client.render.projectile;


import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFlyingAxe extends Render<EntityFlyingAxe>
{
	private float field_77002_a;

	protected ComponentRenderFlyingItem<EntityFlyingAxe> component = new ComponentRenderFlyingItem<EntityFlyingAxe>(){

		@Override
		public void adapterBindTexture(EntityFlyingAxe entity) {
			bindEntityTexture(entity);

		}

		@Override
		public float getScale() {
			// TODO 自動生成されたメソッド・スタブ
			return field_77002_a ;
		}

		@Override
		public RenderManager adapterGetRenderManager() {
			// TODO 自動生成されたメソッド・スタブ
			return getRenderManager();
		}

		@Override
		public Item getItem() {
			// TODO 自動生成されたメソッド・スタブ
			return Items.IRON_AXE;
		}
	};


	public RenderFlyingAxe(RenderManager rm,float par1)
	{
		super(rm);
		this.field_77002_a = par1;
	}


	@Override
	public void doRender(EntityFlyingAxe axe, double x, double y, double z, float yaw, float partialTicks)
	{



		this.component.doRender(axe, x, y, z, yaw, partialTicks);

		super.doRender(axe, x, y, z, yaw, partialTicks);
	}


	@Override
	protected ResourceLocation getEntityTexture(EntityFlyingAxe entity) {
		// TODO 自動生成されたメソッド・スタブ
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}



}
