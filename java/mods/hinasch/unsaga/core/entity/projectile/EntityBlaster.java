package mods.hinasch.unsaga.core.entity.projectile;

import javax.annotation.Nullable;

import mods.hinasch.lib.entity.EntityThrowableBase;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBlaster extends EntityThrowableBase{

	int time = 0;
	double speed = 0;
	private static final DataParameter<Integer> TARGET  = EntityDataManager.<Integer>createKey(EntityBlaster.class, DataSerializers.VARINT);

	public EntityBlaster(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public EntityBlaster(World worldIn,EntityLivingBase shooter) {
		super(worldIn,shooter);
	}
	public @Nullable EntityLivingBase getTarget(){
		int id = this.getDataManager().get(TARGET);
		if(this.worldObj.getEntityByID(id) instanceof EntityLivingBase){
			return (EntityLivingBase) this.worldObj.getEntityByID(id);
		}
		return null;
	}

	public void setTarget(EntityLivingBase target){
		this.getDataManager().set(TARGET, target.getEntityId());
	}
	@Override
    protected void entityInit()
    {
		this.getDataManager().register(TARGET, -1);
    }
	@Override
    public void onUpdate()
    {
		super.onUpdate();
		time ++;

		speed += 0.002F;
		if(speed>0.5F){
			speed = 0.1F;
		}

		if(this.getTarget()!=null){
			Vec3d vec = this.getTarget().getPositionVector().addVector(0, 0.5D, 0).subtract(this.getPositionVector()).normalize().scale(speed);
			this.setVelocity(vec.xCoord,vec.yCoord,vec.zCoord);

		}

		if(time>200){
			this.setDead();
		}
		if(this.ticksExisted % 5 ==0){
			ParticleHelper.MovingType.FLOATING.spawnParticle(worldObj, XYZPos.createFrom(this), EnumParticleTypes.PORTAL, rand, 10, 0.05D);
		}
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    	super.writeEntityToNBT(tagCompound);
    	tagCompound.setInteger("time", this.time);

    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    	super.readEntityFromNBT(tagCompund);

    	if(tagCompund.hasKey("time")){
    		this.time = tagCompund.getInteger("time");
    	}

    }
	@Override
	public void onImpactEntity(RayTraceResult result){
		super.onImpactEntity(result);
		this.setDead();
	}
	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(), this, SpellRegistry.instance().blaster.getEffectStrength().lp(), General.MAGIC);
	}

    protected float getGravityVelocity()
    {
        return 0.0F;
    }
}
