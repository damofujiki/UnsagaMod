package mods.hinasch.unsaga.core.entity.passive;

import mods.hinasch.lib.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityShadow extends EntityCreature{



	EntityLivingBase owner;
	EntityLivingBase target;
	boolean attacked = false;
	final int maxage;
	int age = 0;
	public EntityShadow(World worldIn) {
		super(worldIn);
		this.maxage = 50;
	}

	public EntityShadow(World worldIn,EntityLivingBase owner,EntityLivingBase target,int maxage) {
		super(worldIn);
		this.owner = owner;
		this.target = target;
		this.maxage = maxage;
		if(owner.getHeldItem(EnumHand.MAIN_HAND)!=null){
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, this.owner.getHeldItemMainhand().copy());
		}

	}

	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0F, true));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
//        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, in -> in==this.target));
    }

    protected void entityInit()
    {
        super.entityInit();

    }

	@Override
    public void onUpdate()
    {
		super.onUpdate();
//		if(this.swingProgressInt<=0){
//			this.swingArm(EnumHand.MAIN_HAND);
//		}

		if(WorldHelper.isServer(getEntityWorld())){
			if(this.target==null || this.target.isDead){
				this.setDead();
			}

			if(this.target!=null && this.getDistanceToEntity(target)>30.0D){
				this.setDead();
			}
		}


//		this.swingArm(EnumHand.MAIN_HAND);
//		if(this.swingProgressInt==1){
//			this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expandXyz(2.0D),in ->in!=this && in!=this.owner)
//			.forEach(in ->{
//				if(this.owner instanceof EntityPlayer){
//					EntityPlayer ep = (EntityPlayer) this.owner;
//					ep.attackTargetEntityWithCurrentItem(in);
//				}else{
//					float at = 1.0F;
//					if(this.owner instanceof EntityMob){
//						at = (float) this.owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
//					}
//					in.attackEntityFrom(DamageSource.causeMobDamage(this), at);
//
//				}
//			});
//		}
		age ++;

//		UnsagaMod.logger.trace("age", age);
		if(age>this.maxage){
			this.setDead();
		}
		if(this.attacked && this.age>10){
			this.setDead();
		}
//		this.canBeCollidedWith()
	}


	@Override
    public boolean canBeAttackedWithItem()
    {
        return false;
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
    	boolean flag = false;
    	if(!this.attacked){
    		flag = super.attackEntityAsMob(entityIn);
    		this.attacked = true;
    	}


    	return flag;
    }

	@Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        return true;
    }
	@Override
    public boolean canBeCollidedWith()
    {
        return false;
    }
}
