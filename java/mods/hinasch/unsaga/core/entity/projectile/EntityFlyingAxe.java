package mods.hinasch.unsaga.core.entity.projectile;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.entity.EntityThrowableItem;
import mods.hinasch.lib.util.VecUtil;
import mods.hinasch.unsaga.core.client.render.projectile.IRotation;
import mods.hinasch.unsaga.core.entity.IItemStackable;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFlyingAxe extends EntityThrowableItem implements IProjectile,IItemStackable,IRotation{

	private static final DataParameter<Integer> TARGET_ID = EntityDataManager.createKey(EntityFlyingAxe.class, DataSerializers.VARINT);
	private static final Status SKYDRIVE_START= EntityThrowableItem.putStatus(new Status(1));
	//	private static final Status SKYDRIVE_LOCKON = EntityThrowableItem.putStatus(new Status(2));
	private static final Status SKYDRIVE_ATTACK = EntityThrowableItem.putStatus(new Status(3));
	private static final Status YOYO = EntityThrowableItem.putStatus(new Status(4));
	public static final ImmutableSet<Block> DESTROYABLE = ImmutableSet.of(Blocks.GLASS_PANE,Blocks.FLOWER_POT,Blocks.GLOWSTONE,Blocks.GLASS,Blocks.GLOWSTONE);


	//protected final int SKYDRIVE_FLAG = 23;



	//	protected float damage = 2.0F;
//	protected float drive = 1.0F;
	//protected boolean isSkyDrive = false;
	//protected ItemStack itemStackAxe;
	//protected int knockbackModifier = 0;
	protected float lpDamage = 0;
	protected float rotation = 0;

	protected Entity target;



	protected int tickAxe = 0;

	public EntityFlyingAxe(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}




	public EntityFlyingAxe(World par1World, EntityLivingBase par2EntityLiving,ItemStack par4)
	{
		super(par1World,par2EntityLiving);
//		this.setDrive(par3);
		this.setAxeItemStack(par4);

	}

	@Override
	public void addEntityRotation(float par1) {
		this.rotation += par1;

	}



	/**
	 *コピーしてから消す。
	 * @param par1ItemStack
	 */
	@Deprecated
	public void copyAxeItemStackAndDeleteHeld(ItemStack par1ItemStack)
    {
        this.setItemStack(par1ItemStack.copy());
        ItemStack held = this.getThrower().getHeldItemMainhand();
        --held.stackSize;
        if(held.stackSize<0){
        	if(this.getThrower() instanceof EntityPlayer){
        		((EntityPlayer)this.getThrower()).inventory.deleteStack(held);
        	}else{
        		this.getThrower().setHeldItem(EnumHand.MAIN_HAND, null);
        	}
        }

    }

	@Override
	protected void entityInit()
	{
		super.entityInit();
		//this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
//		this.getDataWatcher().addObject(SKYDRIVE_FLAG, (byte)0);
//		this.getDataWatcher().updateObject(SKYDRIVE_FLAG, (byte)0);
		//this.getDataWatcher().addObjectByDataType(10, 5);
		//this.getDataWatcher().addObjectByDataType(DRIVE, 3);
		this.getDataManager().register(TARGET_ID, (int)-1);
//		this.getDataWatcher().addObject(TARGETID, (int)-1);
//		this.getDataWatcher().updateObject(TARGETID, -1);
		//this.getDataWatcher().updateObject(DRIVE, (Float)1.0F);
	}

	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(), this, this.lpDamage,General.PUNCH,General.SWORD);
	}



//	protected void setDrive(float par1){
//		this.getDataWatcher().updateObject(DRIVE, (Float)par1);
//		this.getDataWatcher().setObjectWatched(DRIVE);
//	}
//
//	protected float getDrive(){
//		float f = this.getDataWatcher().getWatchableObjectFloat(DRIVE);
//		return f;
//	}

//	@Override
//    protected float getVelocity()
//    {
//        return this.getDrive() * 1.5F;
//    }

	public Set<Block> getDestroyableBlocks(){
		return this.DESTROYABLE;
	}


	public Set<Material> getDestroyableMaterials(){
		return Sets.newHashSet(Material.VINE);
	}

	public float getEntityRotation() {
		// TODO 自動生成されたメソッド・スタブ
		return this.rotation;
	}

    @Override
    protected float getGravityVelocity()
    {
    	if(this.isSkyDrive() || this.isYoYo()){
            return 0.00F;
    	}

    	return super.getGravityVelocity();
    }
//	@Override
//    protected float getGravityVelocity()
//    {
//		if(this.isSkyDrive()){
//			return 0.0F;
//		}
//        return 0.03F;
//    }

	public ItemStack getInnerItemStack()
    {
        return this.getItemStack();
    }

	public int getKnockbackModifier(){
		return EnchantmentHelper.getEnchantmentLevel(Enchantments.KNOCKBACK, this.getInnerItemStack());

	}




	public float getLPDamage(){
		return this.lpDamage;
	}


	protected Status getSkyDriveStatus(){
		return this.getEntityStatus();
	}

	public Entity getTarget(){
		int entityid = this.getDataManager().get(TARGET_ID);
		Entity tar = this.worldObj.getEntityByID(entityid);
		return tar;
	}
	protected boolean isSkyDrive(){
		return Lists.newArrayList(SKYDRIVE_START,SKYDRIVE_ATTACK).contains(this.getEntityStatus());
	}

	public boolean isYoYo(){
		return this.getEntityStatus()==YOYO;
	}

	@Override
	public void onImpactEntity(RayTraceResult result){
		if(this.isYoYo() && this.tickAxe>10){
			if(result.entityHit==this.getThrower()){
				this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);
			}
		}
		super.onImpactEntity(result);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();


		this.addEntityRotation(50.0F);
		this.setEntityRotation(MathHelper.wrapDegrees(this.rotation));


		this.tickAxe += 1;


//		UnsagaMod.logger.trace("tick", this.tickAxe);

		if(this.isYoYo()){
//			speed += 0.002D;
			if(this.getTarget()!=null){
				Vec3d vec = this.getTarget().getPositionVector().addVector(0, 1.0D, 0).subtract(this.getPositionVector()).normalize().scale(0.03D);
				this.addVelocity(vec.xCoord, vec.yCoord, vec.zCoord);
			}else{
				this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);
			}



		}

		if(this.tickAxe>30 && this.isSkyDrive()){
			this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT,1.0F, 1.5F);
			this.setEntityStatusAndUpdate(SKYDRIVE_ATTACK);
			Entity target = null;
			if(this.getTarget()!=null && !this.getTarget().isDead){
				target = this.getTarget();
			}else{
				target = this.getThrower();
			}

			if(target!=null){
		        Vec3d vec = VecUtil.getHeadingToEntityVec(this, target);
		        this.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 1.6F, 6);
			}else{
				this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);
			}

		}
//		Unsaga.debug("flyingAxe:"+new XYZPos(motionX,motionY,motionZ)+" side;"+this.worldObj.isRemote+" tick:"+this.tickAxe+" status:"+this.getSkyDriveStatus());
//		if(this.tickAxe > 100){
//			this.transformToEntityItem();
//		}
//		if(this.isSkyDrive()){
//			this.onUpdateSkyDrive();
//		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttag)
	{

		super.readEntityFromNBT(nbttag);
		this.setLPDamage(nbttag.getFloat("damage.LP"));
		this.tickAxe = nbttag.getInteger("tickAxe");
		//this.setDrive(nbttag.getFloat("drive"));
		//this.knockbackModifier = par1NBTTagCompound.getInteger("knockbackModifier");


	}



	public void setAxeItemStack(ItemStack par1ItemStack)
    {
        this.setItemStack(par1ItemStack);
    }



	@Override
	public void setEntityRotation(float par1) {
		this.rotation = par1;

	}

	public void setLPDamage(float par1){
		this.lpDamage = par1;
	}

	public void setSkyDrive(boolean par1){
		this.setEntityStatusAndUpdate(par1 ? SKYDRIVE_START : EntityThrowableItem.NORMAL);
//		this.setEntityStatusAndUpdate(par1 ? 1: 0);
	}

	public void setTarget(Entity par1){
		//this.target = par1;
		this.getDataManager().set(TARGET_ID, (int)par1.getEntityId());
//		this.getDataWatcher().updateObject(TARGETID, (int)par1.getEntityId());
//		this.getDataWatcher().setObjectWatched(TARGETID);
	}

	public void setYoYo(boolean par1){
		this.setEntityStatusAndUpdate(par1 ? YOYO : EntityThrowableItem.NORMAL);
	}
	protected void updateSkyDriveStatus(Status status){
		this.setEntityStatusAndUpdate(status);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setFloat("damage.LP", this.getLPDamage());
		par1NBTTagCompound.setInteger("tickAxe", this.tickAxe);
		//par1NBTTagCompound.setFloat("drive",this.getDrive());
		//par1NBTTagCompound.setInteger("knockbackModifier", this.getKnockbackModifier());

	}




}
