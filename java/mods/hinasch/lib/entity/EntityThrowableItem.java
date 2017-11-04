package mods.hinasch.lib.entity;

import java.util.Map;

import org.apache.logging.log4j.Level;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;



public abstract class EntityThrowableItem extends EntityThrowableBase{

	public static class Status{

		public static Status fromInt(int par1){

			for(Status st:EntityThrowableItem.throwableItemStatusRegistry.values()){

				if(st.getInt()==par1){
					return st;
				}
			}

			return EntityThrowableItem.NORMAL;
		}

		final int meta;

		public Status(int par1){
			this.meta = par1;
		}


		public int getInt(){
			return this.meta;
		}
	}
	private static final DataParameter<Optional<ItemStack>> INNER_ITEM  = EntityDataManager.<Optional<ItemStack>>createKey(EntityThrowableItem.class, DataSerializers.OPTIONAL_ITEM_STACK);

	private static final DataParameter<Byte> ENTITY_STATUS_FLAG  = EntityDataManager.<Byte>createKey(EntityThrowableItem.class, DataSerializers.BYTE);

	public static final Map<Integer,Status> throwableItemStatusRegistry = Maps.<Integer,Status>newHashMap();

	public static final Status NORMAL = putStatus(new Status(0));

	public static final Status DEAD = putStatus(new Status(16));


	//	public static enum EntityStatus{
	//
	//		NORMAL(0),
	//		DEAD(4);
	//
	//		int meta;
	//		private EntityStatus(int par1){
	//
	//			this.meta = par1;
	//		}
	//		public int getInt(){
	//			return this.meta;
	//		}
	//
	//		public static EntityStatus fromInt(int par1){
	//			for(EntityStatus st:EntityStatus.values()){
	//				if(st.getInt()==par1)return st;
	//			}
	//			return NORMAL;
	//		}
	//	}

	public static Status putStatus(Status st){
		throwableItemStatusRegistry.put(st.getInt(),st);
		return st;
	}

	protected static final int STATUS_DEAD = 4;

	public EntityThrowableItem(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public EntityThrowableItem(World p_i1777_1_, EntityLivingBase p_i1777_2_)
	{
		super(p_i1777_1_,p_i1777_2_);
	}

	//    public void setDead()
	//    {
	////    	UnsagaMod.logger.trace("setdead");
	//        this.isDead = true;
	//    }

	//	public EntityThrowableItem(World par1World, EntityLivingBase par2EntityLiving, float par3)
	//	{
	//		super(par1World,par2EntityLiving,par3);
	//
	//	}
	//
	//    public EntityThrowableItem(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
	//    {
	//    	super(par1World,par2EntityLivingBase,par3EntityLivingBase,par4,par5);
	//    }
	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.getDataManager().register(INNER_ITEM	, Optional.<ItemStack>absent());
		this.getDataManager().register(ENTITY_STATUS_FLAG, (byte)0);
		//		this.getDataWatcher().addObjectByDataType(10, 5);
		//		this.getDataWatcher().addObject(ENTITY_STATUS_FLAG, (byte)0);
		//		this.getDataWatcher().updateObject(ENTITY_STATUS_FLAG, (byte)0);

	}

	protected Status getEntityStatus(){
		return EntityThrowableItem.Status.fromInt((int) this.getDataManager().get(ENTITY_STATUS_FLAG));
	}

	public SoundEvent getHitSound(){
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}

	public ItemStack getItemStack()
	{
		Optional<ItemStack> itemstack = this.getDataManager().get(INNER_ITEM);

		if (!itemstack.isPresent())
		{
			if (this.worldObj != null)
			{
				FMLLog.log(Level.WARN, "Item entity %d has no item?!", this.getEntityId());
			}

			return new ItemStack(Blocks.STONE);
		}
		else
		{
			return itemstack.get();
		}
	}

	public boolean hasFire(){
		Map<Enchantment,Integer> enchantmap = EnchantmentHelper.getEnchantments(this.getItemStack());
		return enchantmap.containsKey(Enchantments.FIRE_ASPECT);
	}

	public void markDirtyEntityDead(){
		this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);
	}
	@Override
	protected void onImpact(RayTraceResult result) {

		super.onImpact(result);
		this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);

		if(result!=null){
			//Unsaga.debug("hit!side:"+this.worldObj.isRemote);
			if(this.getHitSound()!=null){
				this.playSound(this.getHitSound(), 1.5F,1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
			}

			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.motionX,this.motionY,this.motionZ, 1.0D, 0.0D, 0.0D);

		}

	}

	@Override
	public void onImpactEntity(RayTraceResult result){
		if(this.hasFire()){
			result.entityHit.setFire(5);
		}
		super.onImpactEntity(result);
	}
	@Override
	public void onUpdate(){
		super.onUpdate();
		UnsagaMod.logger.trace(this.getClass().getName(), "called");
		if(this.getEntityStatus()==EntityThrowableItem.DEAD){
			this.transformToEntityItem();
		}
	}


	@Override
	public void readEntityFromNBT(NBTTagCompound nbttag)
	{
		super.readEntityFromNBT(nbttag);
		this.setEntityStatusAndUpdate(EntityThrowableItem.Status.fromInt(nbttag.getInteger("entityStatus")));
		NBTTagCompound nbttagcompound1 = nbttag.getCompoundTag("Item");
		this.setItemStack(ItemStack.loadItemStackFromNBT(nbttagcompound1));

		Optional<ItemStack> item = this.getDataManager().get(INNER_ITEM);

		if(item.isPresent()){
			if (item.get().stackSize <= 0)
			{
				this.setDead();
			}
		}

	}

	protected void setEntityStatusAndUpdate(Status par1){
		this.getDataManager().set(ENTITY_STATUS_FLAG, (byte)par1.getInt());
	}
	public void setItemStack(ItemStack par1ItemStack)
	{
		this.getDataManager().set(INNER_ITEM, par1ItemStack!=null?Optional.of(par1ItemStack) : Optional.<ItemStack>absent());
	}
	//アイテムエンティティと化す
	protected void transformToEntityItem()
	{
		//Unsaga.debug("Dead side:"+this.worldObj.isRemote);
		EntityItem entityItem = new EntityItem(this.worldObj, (double)this.posX, (double)this.posY, (double)this.posZ, this.getItemStack());
		entityItem.setPickupDelay(10);
		entityItem.setAlwaysRenderNameTag(true);
		entityItem.setCustomNameTag("HERE");
		entityItem.setGlowing(true);
		if(WorldHelper.isServer(worldObj) && entityItem!=null){
			this.worldObj.spawnEntityInWorld(entityItem);
		}
		this.setDead();

	}
	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("entityStatus", this.getEntityStatus().getInt());
		if (this.getItemStack() != null)
		{
			par1NBTTagCompound.setTag("Item", this.getItemStack().writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void onImpactBlock(RayTraceResult result) {
		super.onImpactBlock(result);
		this.setEntityStatusAndUpdate(EntityThrowableItem.DEAD);
	}

}
