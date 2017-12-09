package mods.hinasch.unsaga.core.entity.passive;

import javax.annotation.Nullable;

import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.VecUtil;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.core.entity.ai.EntityAIArrowAttack;
import mods.hinasch.unsaga.core.entity.ai.EntityAISpell.ISpellAI;
import mods.hinasch.unsagamagic.item.ItemSpellBook;
import mods.hinasch.unsaga.core.entity.ai.EntityAISpellFromBook;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityShadow extends EntityTameable implements IRangedAttackMob,ISpellAI{


	public static enum AIMode implements IIntSerializable{
		NONE(0),MELEE(1),MAGIC(2);

		final private int meta;
		private AIMode(int meta){
			this.meta = meta;
		}
		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return meta;
		}

		public static AIMode fromMeta(int meta){
			return HSLibs.fromMeta(AIMode.values(), meta);
		}
	}
	AIMode mode = AIMode.NONE;
	EntityAIBase aiMelee = new EntityAIAttackMelee(this, 1.0F, true);
	EntityAIBase aiArrow = new EntityAIArrowAttack(this, 1.0F, 60, 1.5F);


	public EntityShadow(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 1.0F);
	}

	public EntityShadow(World worldIn,EntityLivingBase owner,ItemStack stack) {
		super(worldIn);
		this.setOwnerId(owner.getUniqueID());
		this.setTamed(true);
		if(stack!=null){
			if(stack.getItem() instanceof ItemSpellBook){
				this.mode = AIMode.MAGIC;
			}else{
				this.mode = AIMode.MELEE;
			}
		}else{
			this.mode = AIMode.MELEE;
		}
		this.setAIFromMode(this.mode);
	}

	private void setAIFromMode(AIMode mode){

		switch(this.mode){
		case MAGIC:
			this.tasks.addTask(1, new EntityAISwimming(this));
	        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
			this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
	        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 8.0F));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
			this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
			this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
			this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, false,false,IMob.VISIBLE_MOB_SELECTOR));
			this.targetTasks.addTask(4, new EntityAISpellFromBook(this,1.0D,100,30.0F,10));
			break;
		case MELEE:
			this.tasks.addTask(1, new EntityAISwimming(this));
			this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
			this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0F, true));
	        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
			this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
	        this.tasks.addTask(6, new EntityAILookIdle(this));
	        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 8.0F));
			this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
			this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
			this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, false,false,IMob.VISIBLE_MOB_SELECTOR));
			break;
		case NONE:
			break;
		default:
			break;

		}
	}
//	private void buildAIFromWeapon(ItemStack is){
//		this.tasks.addTask(1, new EntityAISwimming(this));
//		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
//		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0F, true));
//        this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
//		this.tasks.addTask(7, new EntityAIWander(this, 0.8D));
//        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityMob.class, 8.0F));
//		this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
//		this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
//
//		this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, false,false,IMob.VISIBLE_MOB_SELECTOR));
//		if(ItemUtil.isItemStackPresent(is)){
//			if(is.getItem() instanceof ItemBow){
//				this.targetTasks.addTask(4, new EntityAIArrowAttack(this, 1.0F, 60, 1.5F));
//			}else{
//				if(is.getItem() instanceof ItemSpellBook){
//					if(SpellBookCapability.adapter.hasCapability(is)){
//						List<SpellAIData> spells = SpellBookCapability.adapter.getCapability(is).getSpells().stream().map(in -> new SpellAIData(in,30.0D,0,in.getBaseCastingTime()))
//								.collect(Collectors.toList());
//						if(!spells.isEmpty()){
//							this.targetTasks.addTask(4, new EntityAISpell(this, spells,1.0D,200,15.0F,10));
//						}else{
//							this.initMeleeAI();
//						}
//					}else{
//						this.initMeleeAI();
//					}
//
//				}else{
//
//					this.initMeleeAI();
//				}
//			}
//
//		}else{
//
//			this.initMeleeAI();
//		}
//
//	}

	private void initMeleeAI(){
		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0F, true));


	}
	//	@Override
	//	protected void initEntityAI()
	//	{
	//		this.tasks.addTask(1, new EntityAISwimming(this));
	//		this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
	//		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0F, true));
	//		this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
	//		//        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	//		//        this.tasks.addTask(6, new EntityAILookIdle(this));
	//		this.targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
	//		this.targetTasks.addTask(2, new EntityAIOwnerHurtByTarget(this));
	//        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, false,false,IMob.VISIBLE_MOB_SELECTOR));
	//	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);


		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);


		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}
	protected void entityInit()
	{
		super.entityInit();

	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		int i = 0;

		if (entityIn instanceof EntityLivingBase)
		{
			f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
			i += EnchantmentHelper.getKnockbackModifier(this);
		}

		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

		if (flag)
		{
			if (i > 0 && entityIn instanceof EntityLivingBase)
			{
				((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
				this.motionX *= 0.6D;
				this.motionZ *= 0.6D;
			}

			int j = EnchantmentHelper.getFireAspectModifier(this);

			if (j > 0)
			{
				entityIn.setFire(j * 4);
			}

			if (entityIn instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer)entityIn;
				ItemStack itemstack = this.getHeldItemMainhand();
				ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;

				if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == Items.SHIELD)
				{
					float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

					if (this.rand.nextFloat() < f1)
					{
						entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
						this.worldObj.setEntityState(entityplayer, (byte)30);
					}
				}
			}

			this.applyEnchantments(this, entityIn);
		}

		return flag;
	}
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		//		//		if(this.swingProgressInt<=0){
		//		//			this.swingArm(EnumHand.MAIN_HAND);
		//		//		}
		//
		//		if(WorldHelper.isServer(getEntityWorld())){
		//			if(this.target==null || this.target.isDead){
		//				this.setDead();
		//			}
		//
		//			if(this.target!=null && this.getDistanceToEntity(target)>30.0D){
		//				this.setDead();
		//			}
		//		}
		//
		//
		//		if(this.age>10 && !this.attacked){
		//			this.attacked = true;
		//			this.swingArm(EnumHand.MAIN_HAND);
		//			if(this.target!=null && WorldHelper.isServer(worldObj)){
		//				this.target.attackEntityFrom(DamageSourceUnsaga.create(this, 1.0F, General.SWORD), this.damage);
		//			}
		//		}
		//
		//		//		this.swingArm(EnumHand.MAIN_HAND);
		//		//		if(this.swingProgressInt==1){
		//		//			this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expandXyz(2.0D),in ->in!=this && in!=this.owner)
		//		//			.forEach(in ->{
		//		//				if(this.owner instanceof EntityPlayer){
		//		//					EntityPlayer ep = (EntityPlayer) this.owner;
		//		//					ep.attackTargetEntityWithCurrentItem(in);
		//		//				}else{
		//		//					float at = 1.0F;
		//		//					if(this.owner instanceof EntityMob){
		//		//						at = (float) this.owner.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		//		//					}
		//		//					in.attackEntityFrom(DamageSource.causeMobDamage(this), at);
		//		//
		//		//				}
		//		//			});
		//		//		}
		//		age ++;
		//
		//		//		UnsagaMod.logger.trace("age", age);
		//		if(age>this.maxage){
		//			this.setDead();
		//		}
		//		if(this.attacked && this.age>10){
		//			this.setDead();
		//		}
		//		//		this.canBeCollidedWith()
	}

	//    public boolean attackEntityAsMob(Entity entityIn)
	//    {
	//    	boolean flag = false;
	//    	if(!this.attacked){
	//    		flag = super.attackEntityAsMob(entityIn);
	//    		this.attacked = true;
	//    	}
	//
	//
	//    	return flag;
	//    }

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack)
	{
		//		UnsagaMod.logger.trace(this.getName(), "called");
		ItemStack held = player.getHeldItemMainhand();
		ItemStack offHeld = player.getHeldItemOffhand();

		if(player.isSneaking()){
			if(ItemUtil.isItemStackPresent(held) && held.getItem()==Items.BONE){
				this.onDeath(DamageSource.causeMobDamage(this.getOwner()));
				this.setDead();
				return true;
			}
			if(ItemUtil.isItemStackPresent(held) && ItemUtil.isItemStackNull(this.getHeldItemOffhand())){
				this.setHeldItem(EnumHand.OFF_HAND, held.copy());
				player.setHeldItem(EnumHand.MAIN_HAND, null);
				return true;
			}
			if(ItemUtil.isItemStackNull(held) && ItemUtil.isItemStackPresent(this.getHeldItemOffhand())){

				player.setHeldItem(EnumHand.MAIN_HAND, this.getHeldItemOffhand().copy());
				this.setHeldItem(EnumHand.OFF_HAND, null);
				return true;
			}
		}else{
			if(ItemUtil.isItemStackPresent(held) && ItemUtil.isItemStackNull(this.getHeldItemMainhand())){
				this.setHeldItem(EnumHand.MAIN_HAND, held.copy());
				player.setHeldItem(EnumHand.MAIN_HAND, null);
				return true;
			}
			if(ItemUtil.isItemStackNull(held) && ItemUtil.isItemStackPresent(this.getHeldItemMainhand())){

				player.setHeldItem(EnumHand.MAIN_HAND, this.getHeldItemMainhand().copy());
				this.setHeldItem(EnumHand.MAIN_HAND, null);
				return true;
			}
		}


		return false;
	}


	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {

		return null;
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		if(this.getHeldItemMainhand()!=null){
			ItemUtil.dropItem(worldObj, getHeldItemMainhand(), XYZPos.createFrom(this));
		}
		if(this.getHeldItemOffhand()!=null){
			ItemUtil.dropItem(worldObj, getHeldItemOffhand(), XYZPos.createFrom(this));
		}
	}
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
		ItemStack held = this.getHeldItemMainhand();
		ItemStack offhand = this.getHeldItemOffhand();
		if(ItemUtil.isItemStackPresent(held) && held.getItem() instanceof ItemBow){


			if(ItemUtil.isItemStackPresent(offhand) && offhand.getItem() instanceof ItemArrow){
				ItemArrow arrow = (ItemArrow) offhand.getItem();
				EntityArrow entityArrow = arrow.createArrow(worldObj, offhand, this);
				VecUtil.setThrowableToTarget(this, target, entityArrow);
				WorldHelper.safeSpawn(worldObj, entityArrow);
				ItemUtil.decrStackSize(offhand, 1);
				if(offhand.stackSize<=0){
					ItemUtil.setStackNull(offhand);
				}
			}
		}

	}


	@Override
	public boolean canCastSpell() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

	@Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        compound.setInteger("aimode", this.mode.getMeta());
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
		super.readEntityFromNBT(compound);
		if(compound.hasKey("aimode")){
			this.mode = AIMode.fromMeta(compound.getInteger("aimode"));
			this.setAIFromMode(mode);
		}
    }
}
