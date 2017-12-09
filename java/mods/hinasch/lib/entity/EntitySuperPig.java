package mods.hinasch.lib.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * デバッグ用動物
 */
public class EntitySuperPig extends EntityPig{

	public EntitySuperPig(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
		if(this.worldObj.isRemote){
//			int a = UnsagaMod.proxy.getDebugPos().getX();


//			IBlockState state = Blocks.STONE.getDefaultState();
//	    	ParticleHelper.MovingType.WAVE.spawnParticle(worldObj, XYZPos.createFrom(this)
//	    			, EnumParticleTypes.BLOCK_DUST, rand, 10,1D,Block.getStateId(state));
		}
//		if(source.getEntity() instanceof EntityPlayer){
//			EntityShadow shadow = new EntityShadow(this.getEntityWorld(),(EntityLivingBase) source.getEntity(),this,50);
//			shadow.setPositionAndRotation(this.posX, this.posY+2.0F, this.posZ,source.getEntity().rotationYaw,source.getEntity().rotationPitch);
//			if(WorldHelper.isServer(getEntityWorld())){
//				this.worldObj.spawnEntityInWorld(shadow);
//			}
//		}

		this.heal(100F);
		return super.attackEntityFrom(source, amount);
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }
    protected void initEntityAI()
    {

            this.tasks.addTask(0, new EntityAISwimming(this));
            this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
            this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
            this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
            this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
            this.tasks.addTask(8, new EntityAILookIdle(this));

    }

    @Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	if(rand.nextFloat()<0.3F){

//    		ParticleHelper.spawnShockWave(getEntityWorld(), XYZPos.createFrom(this));
    	}
//    	if(rand.nextFloat()<0.1F){
//        	this.worldObj.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, this.posX, this.posY, this.posZ
//        			, 0, 0, 0);
//    	}
    }
}
