package mods.hinasch.unsagamagic.tileentity;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

public class TileEntityFireWall extends TileEntity implements ITickable{

	protected int remaining = 100;
	protected int fireStrength = 1;



	public int getFireStrength() {
		return fireStrength;
	}


	public void setFireStrength(int fireStrength) {
		this.fireStrength = fireStrength;
	}


	public TileEntityFireWall(){

	}


	public void init(int par1,int str){
//		Unsaga.debug("FireWall remains "+par1,this.getClass());
		this.remaining = par1;
		this.fireStrength = str;
	}


	public void setRemaining(int par1){
		this.remaining = par1;
	}
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {

		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new SPacketUpdateTileEntity(this.getPos(), 0, nbttagcompound);
	}

	@Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
		this.remaining = pkt.getNbtCompound().getInteger("remaining");
    	this.fireStrength = pkt.getNbtCompound().getInteger("fireStr");
    }

	@Override
    public void readFromNBT(NBTTagCompound compound)
    {

    	super.readFromNBT(compound);
    	this.remaining = compound.getInteger("remaining");
    	this.fireStrength = compound.getInteger("fireStr");

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {


    	compound.setInteger("remaining", this.remaining);
    	compound.setInteger("fireStr", getFireStrength());
    	return super.writeToNBT(compound);
    }


    protected void setDeath(){
    	this.worldObj.setBlockToAir(this.getPos());
    }

	@Override
	public void update() {
    	this.remaining -=1;

    	this.getWorld()
    	.getEntitiesWithinAABB(EntityLivingBase.class, HSLibs.getBounding(new XYZPos(this.getPos()), 1.0D, 1.0D))
    	.forEach(living ->{
			if(!living.isPotionActive(MobEffects.FIRE_RESISTANCE)){
				float damage = (float)this.getFireStrength();
				damage = MathHelper.clamp_float(damage, 2.0F, 9.0F);
				living.attackEntityFrom(DamageSource.inFire, damage);
				living.setFire(5);
				float yaw = MathHelper.wrapDegrees(living.rotationYaw + 180.0F);
				float i = 1.0F;
				living.addVelocity((double)(-MathHelper.sin((yaw) * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));


			}
    	});

//    	List<Entity> projectiles = ListHelper.
    	if(this.remaining<0){
    		this.setDeath();
    	}
	}
}
