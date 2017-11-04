package mods.hinasch.unsaga.core.client.render.projectile;


import mods.hinasch.lib.entity.EntityThrowableBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



@SideOnly(Side.CLIENT)
public class RenderThrowableItem<T extends EntityThrowableBase> extends Render<T>
{
    private float field_77002_a;
    private final Item referItem;
    private final int damage;

	//    private static final ResourceLocation fireballTexture = new ResourceLocation(UnsagaMod.MODID,"textures/items/fireball.png");
    private ComponentRenderFlyingItem<T> component = new ComponentRenderFlyingItem<T>(){

		@Override
		public void adapterBindTexture(T entity) {
			bindEntityTexture(entity);

		}

		@Override
		public RenderManager adapterGetRenderManager() {
			// TODO 自動生成されたメソッド・スタブ
			return getRenderManager();
		}

		@Override
		public Item getItem() {
			// TODO 自動生成されたメソッド・スタブ
			return getReferItem();
		}

		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return getDamage();
		}

		@Override
		public float getScale() {
			// TODO 自動生成されたメソッド・スタブ
			return field_77002_a;
		}

    };

	public RenderThrowableItem(RenderManager rm,float par1,Item item,int damage)
    {
    	super(rm);
        this.field_77002_a = par1;
        this.referItem = item;
        this.damage = damage;
    }

	@Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        this.component.doRender(entity, x, y, z, entityYaw, partialTicks);
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }


    public int getDamage() {
		return damage;
	}

    @Override
	protected ResourceLocation getEntityTexture(T throwable){
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

//    /**
//     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
//     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
//     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
//     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
//     */
//    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
//    {
//        this.doRenderFireball((EntityThrowable)par1Entity, par2, par4, par6, par8, par9);
//    }
//
//	@Override
//	protected ResourceLocation getEntityTexture(Entity entity) {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.getEntityTexture((EntityThrowable)entity);
//	}

    public Item getReferItem() {
		return referItem;
	}
}
