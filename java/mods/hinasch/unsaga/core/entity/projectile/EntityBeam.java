package mods.hinasch.unsaga.core.entity.projectile;

import java.util.List;

import javax.annotation.Nullable;

import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBeam extends Entity{

	private static final DataParameter<Integer> TARGET_ID = EntityDataManager.<Integer>createKey(EntityBeam.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> OWNER_ID = EntityDataManager.<Integer>createKey(EntityBeam.class, DataSerializers.VARINT);
	final SpecialMoveInvoker invoker;
	public int tick;

	public int innerRotation;
	public EntityBeam(World w){
		super(w);
		this.invoker = null;
	}
	public EntityBeam(World worldIn,SpecialMoveInvoker invoker) {
		super(worldIn);
		this.invoker = invoker;
		this.tick = 0;
		this.innerRotation = rand.nextInt(10000);
	}

	public @Nullable EntityLivingBase getOwner(){
		int id = this.getDataManager().get(OWNER_ID);

		return (EntityLivingBase) this.worldObj.getEntityByID(id);
	}

	public void setOwner(EntityLivingBase par1){

		if(par1!=null){
			this.dataManager.set(OWNER_ID, par1.getEntityId());
		}

	}
	public @Nullable EntityLivingBase getTarget(){
		int id = this.getDataManager().get(TARGET_ID);

		return (EntityLivingBase) this.worldObj.getEntityByID(id);
	}

//    public void setPosition(double x, double y, double z)
//    {
//        this.posX = x;
//        this.posY = y;
//        this.posZ = z;
//        float f = this.width / 2.0F;
//        float f1 = this.height;
//
//       	Vec3 vec3 = (new Vec3(this.posX-1,this.posY-1,this.posZ+5)).rotateYaw(this.rotationYaw);;
//    	Vec3 vec32 =( new Vec3(this.posX+1,this.posY+1,this.posZ)).rotateYaw(this.rotationYaw);
//        this.setEntityBoundingBox(new AxisAlignedBB(vec3.xCoord,vec3.yCoord,vec3.zCoord,vec32.xCoord,vec32.yCoord,vec32.zCoord));
//    }
//    public AxisAlignedBB getEntityBoundingBox()
//    {
//        return this.boundingBox;
//    }
	public void setTarget(EntityLivingBase par1){
		if(par1!=null){
			this.dataManager.set(TARGET_ID, par1.getEntityId());
		}

	}
	@Override
	protected void entityInit() {


		this.getDataManager().register(TARGET_ID, -1);
		this.getDataManager().register(OWNER_ID, -1);
	}

    public void onUpdate()
    {
        super.onUpdate();
        this.tick +=1;
        ++this.innerRotation;
        if(this.getOwner()==null && this.getTarget()==null){
        	this.setDead();
        }

        if(this.getOwner()!=null && this.getTarget()!=null){
            WorldHelper.setEntityPosition(this, XYZPos.createFrom(this.getOwner()));

            final EntityLivingBase p = this.getOwner();
            final EntityLivingBase t = this.getTarget();

        	List<EntityLivingBase> list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0D, 10.0D, 10.0D), input ->{
				if(input!=getOwner()){
					EntityLivingBase el = (EntityLivingBase) input;

//					Vec3d added = new Vec3d(p.posX,p.posY+0.5D,p.posZ).addVector(lookvec.xCoord, 0, lookvec.zCoord);
					for(int i=0;i<5;i++){
						double d1 = (t.posX-p.posX)/5*(i+1);
						double d2 = (t.posZ-p.posZ)/5*(i+1);
						Vec3d disvec = new Vec3d(d1,0,d2);
						Vec3d added = p.getPositionVector().add(disvec);
//						Unsaga.debug(added);
    					if(el.getEntityBoundingBox().expand(0.25D, 0.25D, 0.25D).isVecInside(added)){
    						return true;
    					}
					}


				}
				return false;
        	});


        	if(WorldHelper.isServer(worldObj)){
            	list.forEach(input ->{
    				DamageSource ds = DamageSource.causeMobDamage(getOwner());
    				DamageSourceUnsaga uds = DamageSourceUnsaga.fromVanilla(ds);
    				uds.setDamageTypeUnsaga(General.SPEAR);
    				uds.setStrLPHurt(invoker.getModifiedStrength().lp());

    				input.attackEntityFrom(uds,invoker.getModifiedStrength().hp());
            	});
        	}



//            if(this.rotationYaw<this.getOwner().rotationYaw){
//            	this.rotationYaw +=10;
//            }else{
//            	this.rotationYaw -=10;
//            }
        }



    	//this.setEntityBoundingBox(new AxisAlignedBB(vec3.xCoord,vec3.yCoord,vec3.zCoord,vec32.xCoord,vec32.yCoord,vec32.zCoord));
        if(this.tick>100){
//        	Unsaga.debug(this.getClass(),"ビーム消します");
        	this.setDead();
        }
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund) {


	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		// TODO 自動生成されたメソッド・スタブ


	}

}
