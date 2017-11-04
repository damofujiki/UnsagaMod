package mods.hinasch.unsaga.villager;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.villager.bartering.BarteringShopType;
import mods.hinasch.unsaga.villager.smith.BlackSmithType;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class UnsagaVillagerCapability {

	@CapabilityInject(IUnsagaVillager.class)
	public static Capability<IUnsagaVillager> CAPA;
	public static final String SYNC_ID = "unsagaVillager";

	public static ICapabilityAdapterPlan<IUnsagaVillager> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IUnsagaVillager.class;
		}

		@Override
		public Class getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new Storage();
		}

	};

	public static CapabilityAdapterFrame<IUnsagaVillager> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IUnsagaVillager> adapter = adapterBase.createChildEntity("unsagaVillager");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof EntityVillager);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IUnsagaVillager{

		boolean init = false;
		boolean hasDisplayedSecretMerchandise = false;

		BarteringShopType shopType = BarteringShopType.UNKNOWN;
		UnsagaVillagerType villagerType = UnsagaVillagerType.UNKNOWN;

		BlackSmithType type = BlackSmithType.NONE;

		ItemStackList merchandises = new ItemStackList(9);
		ItemStackList secrets = new ItemStackList(9);

		int distributionLevel = 0;

		long purchaseTime = 0;

		int carrierID = -1;

		int transactionPoint = 0;

		int baseShopLevel = 0;

		@Override
		public BlackSmithType getBlackSmithType() {
			// TODO 自動生成されたメソッド・スタブ
			return type;
		}

		@Override
		public void setBlackSmithType(BlackSmithType type) {
			// TODO 自動生成されたメソッド・スタブ
			this.type = type;
		}

		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return init;
		}

		@Override
		public void setInitialized(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.init = par1;
		}

		@Override
		public long getRecentStockedTime() {
			// TODO 自動生成されたメソッド・スタブ
			return purchaseTime;
		}

		@Override
		public void setStockedTime(long time) {
			// TODO 自動生成されたメソッド・スタブ
			this.purchaseTime = time;
		}

		@Override
		public ItemStackList getMerchandises() {
			// TODO 自動生成されたメソッド・スタブ
			return this.merchandises;
		}

		@Override
		public void setMerchandises(ItemStackList list) {
			// TODO 自動生成されたメソッド・スタブ
			this.merchandises = list;
		}

		@Override
		public ItemStackList getSecretMerchandises() {
			// TODO 自動生成されたメソッド・スタブ
			return this.secrets;
		}

		@Override
		public void setSecretMerchandises(ItemStackList list) {
			// TODO 自動生成されたメソッド・スタブ
			this.secrets = list;
		}

		@Override
		public int getDistributionLevel() {
			// TODO 自動生成されたメソッド・スタブ
			return this.distributionLevel;
		}

		@Override
		public void setDistributionLevel(int par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.distributionLevel = par1;
		}

		@Override
		public int getCarrierID() {
			// TODO 自動生成されたメソッド・スタブ
			return this.carrierID;
		}

		@Override
		public void setCarrierID(int par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.carrierID = par1;
		}

		@Override
		public UnsagaVillagerType getVillagerType() {
			// TODO 自動生成されたメソッド・スタブ
			return this.villagerType;
		}

		@Override
		public void setVillagerType(UnsagaVillagerType type) {
			// TODO 自動生成されたメソッド・スタブ
			this.villagerType = type;
		}

		@Override
		public int getTransactionPoint() {
			// TODO 自動生成されたメソッド・スタブ
			return this.transactionPoint;
		}

		@Override
		public void setTransactionPoint(int par1) {
			this.transactionPoint = par1;

		}

		@Override
		public BarteringShopType getShopType() {
			// TODO 自動生成されたメソッド・スタブ
			return this.shopType;
		}

		@Override
		public void setBarteringShopType(BarteringShopType type) {
			this.shopType = type;

		}

		@Override
		public void setBaseShopLevel(int par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.baseShopLevel = par1;
		}

		@Override
		public int getBaseShopLevel() {
			// TODO 自動生成されたメソッド・スタブ
			return this.baseShopLevel;
		}

		@Override
		public boolean hasDisplayedSecretMerchandises() {
			// TODO 自動生成されたメソッド・スタブ
			return this.hasDisplayedSecretMerchandise;
		}

		@Override
		public void setHasDisplayedSecrets(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.hasDisplayedSecretMerchandise = par1;
		}


	}

	public static class Storage extends CapabilityStorage<IUnsagaVillager>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IUnsagaVillager> capability, IUnsagaVillager instance,
				EnumFacing side) {
			comp.setByte("villagerType", (byte)instance.getVillagerType().getMeta());
			switch(instance.getVillagerType()){
			case BARTERING:
				NBTTagList tag1 = UtilNBT.newTagList();
				instance.getMerchandises().writeToNBT(tag1);
				comp.setTag("merchandises", tag1);
				NBTTagList tag2 = UtilNBT.newTagList();
				instance.getSecretMerchandises().writeToNBT(tag2);
				comp.setTag("secrets", tag2);
				comp.setInteger("distributionLevel", instance.getDistributionLevel());
				comp.setLong("purchaseTime", instance.getRecentStockedTime());
				comp.setInteger("transactionPoint", instance.getTransactionPoint());
				comp.setByte("shopType", (byte)instance.getShopType().getMeta());
				comp.setInteger("baseShopLevel", instance.getBaseShopLevel());
				comp.setBoolean("displayedSercrets", instance.hasDisplayedSecretMerchandises());
				break;
			case CARRIER:
				comp.setInteger("carrierID", instance.getCarrierID());
				break;
			case SMITH:
				comp.setByte("smithType", (byte)instance.getBlackSmithType().getMeta());
				break;
			case UNKNOWN:
				break;
			default:
				break;

			}

			comp.setBoolean("init", instance.hasInitialized());
		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IUnsagaVillager> capability, IUnsagaVillager instance,
				EnumFacing side) {
			if(comp.hasKey("villagerType")){
				instance.setVillagerType(UnsagaVillagerType.fromMeta((int)comp.getByte("villagerType")));
			}

			switch(instance.getVillagerType()){
			case BARTERING:
				if(comp.hasKey("merchandises")){
					NBTTagList tag = UtilNBT.getTagList(comp, "merchandises");
					instance.setMerchandises(ItemStackList.readFromNBT(tag, 9));
				}
				if(comp.hasKey("secrets")){
					NBTTagList tag2 = UtilNBT.getTagList(comp, "secrets");
					instance.setSecretMerchandises(ItemStackList.readFromNBT(tag2, 9));
				}
				if(comp.hasKey("purchaseTime")){
					instance.setStockedTime(comp.getLong("purchaseTime"));
				}
				if(comp.hasKey("distributionLevel")){
					instance.setDistributionLevel(comp.getInteger("distributionLevel"));
				}
				if(comp.hasKey("transactionPoint")){
					instance.setTransactionPoint(comp.getInteger("transactionPoint"));
				}
				if(comp.hasKey("shopType")){
					instance.setBarteringShopType(BarteringShopType.fromMeta((int)comp.getByte("shopType")));
				}
				if(comp.hasKey("baseShopLevel")){
					instance.setBaseShopLevel(comp.getInteger("baseShopLevel"));
				}
				if(comp.hasKey("displayedSercrets")){
					instance.setHasDisplayedSecrets(comp.getBoolean("displayedSercrets"));
				}
				break;
			case CARRIER:
				if(comp.hasKey("carrierID")){
					instance.setCarrierID(comp.getInteger("carrierID"));
				}
				break;
			case SMITH:
				if(comp.hasKey("smithType")){
					instance.setBlackSmithType(BlackSmithType.fromMeta((int)comp.getByte("smithType")));
				}
				break;
			case UNKNOWN:
				break;
			default:
				break;

			}

			if(comp.hasKey("init")){
				instance.setInitialized(comp.getBoolean("init"));
			}
		}

	}

	public static void registerEvents(){
		adapter.registerAttachEvent((inst,capa,face,ev)->{
			if(ev.getObject() instanceof EntityVillager){
				EntityVillager villager = (EntityVillager) ev.getObject();
				if(!inst.hasInitialized()){
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().merchant){
						inst.setVillagerType(UnsagaVillagerType.BARTERING);
					}
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().magicMerchant){
						inst.setVillagerType(UnsagaVillagerType.BARTERING);
					}
					if(villager.getProfessionForge()==UnsagaVillagerProfession.instance().unsagaSmith){
						inst.setVillagerType(UnsagaVillagerType.SMITH);
					}


					if(inst.getVillagerType()==UnsagaVillagerType.BARTERING && WorldHelper.isServer(villager.getEntityWorld())){

						BarteringShopType type = BarteringShopType.decideBarteringType(villager.getEntityWorld(), villager);
						inst.setBarteringShopType(type);
						inst.setBaseShopLevel(villager.getRNG().nextInt(9)+1);
						inst.setInitialized(true);


					}
					if(inst.getVillagerType()==UnsagaVillagerType.SMITH && WorldHelper.isServer(villager.getEntityWorld())){

						int r = villager.getEntityWorld().rand.nextInt(2)+1;
						BlackSmithType type = BlackSmithType.fromMeta(r);
						inst.setBlackSmithType(type);
						inst.setInitialized(true);

					}
				}

			}
		});
	}
}
