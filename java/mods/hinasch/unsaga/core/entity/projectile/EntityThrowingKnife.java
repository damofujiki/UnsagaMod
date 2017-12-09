package mods.hinasch.unsaga.core.entity.projectile;

import mods.hinasch.lib.entity.EntityThrowableItem;
import mods.hinasch.unsaga.core.client.IRotation;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityThrowingKnife extends EntityThrowableItem implements IRotation{

	protected float lpDamage = 0;

	protected float rotation = 0;

	int knifeTick = 0;

	private static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(EntityFlyingAxe.class, DataSerializers.VARINT);

	public EntityThrowingKnife(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	public EntityThrowingKnife(World par1World, EntityLivingBase par2EntityLiving, ItemStack par4)
	{
		super(par1World,par2EntityLiving);
		this.setKnifeItemStack(par4);

	}

    @Override
    protected float getGravityVelocity()
    {
    	return 0.0F;
    }

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(TARGET_ID, (int)-1);

	}

	public Entity getTarget(){
		int entityid = this.getDataManager().get(TARGET_ID);
		Entity tar = this.worldObj.getEntityByID(entityid);
		return tar;
	}

	public void setTarget(Entity par1){

		this.getDataManager().set(TARGET_ID, (int)par1.getEntityId());

	}

	@Override
	public void onImpactEntity(RayTraceResult result){
		if(this.knifeTick>10){
			if(result.entityHit==this.getThrower()){
				this.markDirtyEntityDead();
			}
		}
		super.onImpactEntity(result);
	}
	@Override
	public void onUpdate(){
		super.onUpdate();

		if(this.getTarget()!=null){
			Vec3d vec = this.getTarget().getPositionVector().addVector(0, 1.0D, 0).subtract(this.getPositionVector()).normalize().scale(0.03D);
			this.addVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
		}else{
			this.markDirtyEntityDead();
		}

		this.knifeTick ++;

		if(this.knifeTick>100){
			this.markDirtyEntityDead();
		}
		this.addEntityRotation(50.0F);
		this.setEntityRotation(MathHelper.wrapDegrees(this.rotation));
	}

	public ItemStack getKnifeItemStack()
    {
       return this.getItemStack();
    }

	public void setKnifeItemStack(ItemStack par1ItemStack)
    {
        this.setItemStack(par1ItemStack);
    }

    public float getLPDamage(){
		return this.lpDamage;
	}

	public void setLPDamage(float par1){
		this.lpDamage = par1;
	}


	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(),this, this.lpDamage, General.SPEAR);
	}


	@Override
	public float getEntityRotation() {
		// TODO 自動生成されたメソッド・スタブ
		return this.rotation;
	}


	@Override
	public void setEntityRotation(float par1) {
		this.rotation = par1;

	}


	@Override
	public void addEntityRotation(float par1) {
		this.rotation += par1;

	}




}
