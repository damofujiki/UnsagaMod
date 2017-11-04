package mods.hinasch.unsaga.core.entity.projectile;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.particle.PacketParticle;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.net.packet.PacketClientThunder;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityCustomArrow extends EntityTippedArrow{

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCustomArrow.class, DataSerializers.VARINT);

	ItemStack arrowStack;

	public EntityCustomArrow(World world){
		super(world);
	}
	public EntityCustomArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);

	}

    @Override
    protected void arrowHit(EntityLivingBase living)
    {
    	super.arrowHit(living);
//    	if(this.getArrowType()==StateArrow.Type.MAGIC_ARROW){
//    		this.setDead();
//    	}

    	if(this.getArrowType()==StateArrow.Type.ZAPPER){
//    		UnsagaMod.logger.trace(getName(), "called");
    		HSLib.core().getPacketDispatcher().sendToAllAround(PacketSound.atEntity(SoundEvents.ENTITY_LIGHTNING_THUNDER, this), PacketUtil.getTargetPointNear(this));

    		UnsagaMod.packetDispatcher.sendToAllAround(PacketClientThunder.create(XYZPos.createFrom(this)), PacketUtil.getTargetPointNear(this));
    	}
    	if(this.getArrowType()==StateArrow.Type.EXORCIST){
    		if(living.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
        		HSLib.core().getPacketDispatcher().sendToAllAround(PacketSound.atEntity(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this), PacketUtil.getTargetPointNear(this));
        		HSLib.core().getPacketDispatcher().sendToAllAround(PacketParticle.create(XYZPos.createFrom(this), EnumParticleTypes.VILLAGER_HAPPY	, 10), PacketUtil.getTargetPointNear(this));
    		}

    	}
    	if(this.getArrowType()==StateArrow.Type.PHOENIX && this.shootingEntity instanceof EntityLivingBase){

//    		ParticleHelper.MovingType.FLOATING.spawnParticle(worldObj, XYZPos.createFrom(this.shootingEntity), EnumParticleTypes.VILLAGER_HAPPY, rand, 10, 0.05D);
    		if(shootingEntity!=null){
    			HSLib.core().getPacketDispatcher().sendToAllAround(PacketSound.atEntity(SoundEvents.ENTITY_PLAYER_LEVELUP, this.shootingEntity), PacketUtil.getTargetPointNear(this));
    			HSLib.core().getPacketDispatcher().sendToAllAround(PacketParticle.create(XYZPos.createFrom(this.shootingEntity), EnumParticleTypes.VILLAGER_HAPPY	, 10), PacketUtil.getTargetPointNear(this));

        		((EntityLivingBase) this.shootingEntity).heal(1.0F);
    		}

    	}
    }
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(TYPE, Integer.valueOf(0));
    }

	@Override
    protected ItemStack getArrowStack()
    {
        if(this.arrowStack!=null){
        	return this.arrowStack;
        }
        return super.getArrowStack();
    }

	public StateArrow.Type getArrowType(){
		int type = this.getDataManager().get(TYPE).intValue();
		return StateArrow.Type.fromMeta(type);
	}
	@Override
    public void onUpdate()
    {
    	super.onUpdate();

    	if(this.timeInGround>0){
        	if(this.getArrowType()==StateArrow.Type.SHADOW_STITCH){

        		RangedHelper.create(worldObj, this, 3.0D).setSelector((self,target)->target.onGround && target!=this.shootingEntity)
        		.setConsumer((self,target)->target.addPotionEffect(new PotionEffect(UnsagaPotions.instance().sleep,ItemUtil.getPotionTime(20),0)))
        		.invoke();
        		this.setArrowType(StateArrow.Type.NONE);
        	}

//            UnsagaMod.logger.trace("tick", this.timeInGround);
    	}else{

            if (this.ticksExisted % 2 == 0)
            {
                this.spawnParticles(10);
            }
    	}

//    	if(this.tickOnGround>1000){
//    		this.setArrowType(StateArrow.Type.NONE);
//    	}

    }
	@Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	if(compound.hasKey("arrowType")){
    		int meta = compound.getInteger("arrowType");
    		this.setArrowType(StateArrow.Type.fromMeta(meta));
    	}
    	if(compound.hasKey("arrowItem")){
    		NBTTagCompound child = compound.getCompoundTag("arrowItem");
    		this.arrowStack = ItemStack.loadItemStackFromNBT(child);
    	}

    }

    public void setArrowStack(ItemStack arrow){
		this.arrowStack = arrow;
	}

    public void setArrowType(StateArrow.Type type){
		this.getDataManager().set(TYPE, type.getMeta());
	}
    private void spawnParticles(int particleCount)
    {
//    	UnsagaMod.logger.trace("called", this.getArrowType());
//       StateArrow.Type type = this.getArrowType();

        if (this.getArrowType()!=StateArrow.Type.NONE && particleCount > 0)
        {
        	ParticleHelper.MovingType.DIVERGE.spawnParticle(worldObj, XYZPos.createFrom(this), this.getArrowType().getParticle(), rand, particleCount, 0.05D);
//            double d0 = (double)(i >> 16 & 255) / 255.0D;
//            double d1 = (double)(i >> 8 & 255) / 255.0D;
//            double d2 = (double)(i >> 0 & 255) / 255.0D;
//
//            for (int j = 0; j < particleCount; ++j)
//            {
//                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2, new int[0]);
//            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setInteger("arrowType",this.getArrowType().getMeta());
    	if(this.arrowStack!=null){
    		NBTTagCompound child = UtilNBT.compound();
    		this.arrowStack.writeToNBT(child);
    		compound.setTag("arrowItem", child);
    	}
    }
}
