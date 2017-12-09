package mods.hinasch.unsaga.core.entity.passive;

import joptsimple.internal.Strings;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.chest.ChestCapability;
import mods.hinasch.unsaga.chest.IChestBehavior;
import mods.hinasch.unsaga.chest.IChestCapability;
import mods.hinasch.unsaga.core.stats.UnsagaAchievementRegistry;
import mods.hinasch.unsaga.init.UnsagaGui;
import mods.hinasch.unsaga.villager.InteractionInfoCapability;
import mods.hinasch.unsaga.villager.bartering.ItemFactory;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityUnsagaChestNew extends EntityLiving implements IChestBehavior<EntityUnsagaChestNew>{

	private static final DataParameter<String> DATA_SYNC = EntityDataManager.<String>createKey(EntityUnsagaChestNew.class, DataSerializers.STRING);
	private ItemFactory factory;

	public EntityUnsagaChestNew(World worldIn) {
		super(worldIn);

		this.isImmuneToFire = true;
		if(worldIn!=null){
			this.factory = new ItemFactory(worldIn.rand);
		}

		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);

	}



	@Override
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
		if(ChestCapability.adapterEntity.hasCapability(this)){
			if(ChestCapability.adapterEntity.getCapability(this).hasOpened()){
				this.setDead();
			}
		}
    }
	@Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, ItemStack stack, EnumHand hand)
    {
		if(InteractionInfoCapability.adapter.hasCapability(player)){
			InteractionInfoCapability.adapter.getCapability(player).setEntityChest(this);
		}
		if(ChestCapability.adapterEntity.hasCapability(this)){
			if(!ChestCapability.adapterEntity.getCapability(this).getOpeningPlayer().isPresent()){
				if(!ChestCapability.adapterEntity.getCapability(this).hasOpened()){
					this.sync(player);

					HSLibs.openGui(player, UnsagaMod.instance, UnsagaGui.Type.CHEST.getMeta(), player.getEntityWorld(), XYZPos.createFrom(this));
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
    }


	@Override
    public boolean isPotionApplicable(PotionEffect potioneffectIn)
    {
        return false;
    }

	@Override
    public boolean canAttackClass(Class p_70686_1_)
    {
        return p_70686_1_  == EntityPlayer.class;
    }

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected void entityInit(){
		super.entityInit();
		this.getDataManager().register(DATA_SYNC, Strings.EMPTY);
	}

	@Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_GENERIC_EXPLODE;
    }

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}


    @Override
	public int getTotalArmorValue()
	{
		return 10;
	}

	@Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        if(source.isMagicDamage()){
        	return true;
        }
        return super.isEntityInvulnerable(source);
    }

	@Override
	public float getPrevLidAngle() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getPrevLidAngle();
	}

	@Override
	public float getLidAngle() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getLidAngle();
	}

	@Override
	public boolean isOpened() {
		if(ChestCapability.adapterEntity.hasCapability(this)){
			return ChestCapability.adapterEntity.getCapability(this).hasOpened();
		}
		return false;
	}

	@Override
	public XYZPos getChestPosition() {
		// TODO 自動生成されたメソッド・スタブ
		return XYZPos.createFrom(this);
	}

	@Override
	public EntityUnsagaChestNew getChestParent() {
		// TODO 自動生成されたメソッド・スタブ
		return this;
	}

	@Override
    public void onDeath(DamageSource cause)
    {
    	super.onDeath(cause);
    	if(cause.getEntity() instanceof EntityPlayer){
    		if(WorldHelper.isServer(worldObj)){
    			EntityPlayer ep = (EntityPlayer) cause.getEntity();
    			ep.addStat(UnsagaAchievementRegistry.instance().breakChest);
    			if(this.worldObj.rand.nextInt(2)==0){
    				this.dropTreasure();
    			}else{
    				ChatHandler.sendChatToPlayer(ep,HSLibs.translateKey("gui.unsaga.chest.broken.item"));
    			}
    		}
    	}
    }

	public void dropTreasure(){
		if(ChestCapability.adapterEntity.hasCapability(this)){
			int level = ChestCapability.adapterEntity.getCapability(this).getLevel();
			ItemStack treasure = ChestCapability.adapterEntity.getCapability(this).getTreasureType().createTreasure(this.worldObj.rand, level, factory);
			ItemUtil.dropItem(worldObj, treasure, this.getChestPosition());
			this.setDead();
		}
	}
	@Override
	public void sync(EntityPlayer player) {
		NBTTagCompound nbt = UtilNBT.compound();
		nbt.setInteger("entityid", this.getEntityId());
		if(WorldHelper.isServer(player.getEntityWorld())){
			HSLib.core().getPacketDispatcher().sendToAll(PacketSyncCapability.create(ChestCapability.CAPA, ChestCapability.adapterEntity.getCapability(this), nbt));
		}
	}

	@Override
	public IChestCapability getCapability() {
		// TODO 自動生成されたメソッド・スタブ
		return ChestCapability.adapterEntity.getCapability(this);
	}
}
