package mods.hinasch.unsaga.villager.bartering;

import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import mods.hinasch.lib.misc.Triplet;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.villager.UnsagaVillagerCapability;
import mods.hinasch.unsaga.villager.bartering.ItemFactory.MerchandiseFactory;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class MerchantBehavior {

	World world;
	EntityVillager villager;
	MerchandiseFactory factory;

	public MerchantBehavior(World world,EntityVillager villager){
		this.world = world;
		this.villager = villager;
		this.factory = new MerchandiseFactory(world.rand);
	}
	public boolean hasComeUpdateTime(){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			if(UnsagaVillagerCapability.adapter.getCapability(villager).getRecentStockedTime()<=0){
				return true;
			}
			if(world.getTotalWorldTime() - UnsagaVillagerCapability.adapter.getCapability(villager).getRecentStockedTime()>24000){
				return true;
			}

		}

		return false;
	}


	public void addTransactionPoint(int point){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			int prev = UnsagaVillagerCapability.adapter.getCapability(villager).getTransactionPoint();
			UnsagaVillagerCapability.adapter.getCapability(villager).setTransactionPoint(prev + point);
		}
	}

	private void checkDistributionLevelUp(){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			int threshold = BarteringUtil.calcNextTransactionThreshold(UnsagaVillagerCapability.adapter.getCapability(villager).getDistributionLevel());
			if(UnsagaVillagerCapability.adapter.getCapability(villager).getTransactionPoint()>=threshold){
				int base = UnsagaVillagerCapability.adapter.getCapability(villager).getDistributionLevel();
				UnsagaVillagerCapability.adapter.getCapability(villager).setDistributionLevel(base+1);
				UnsagaVillagerCapability.adapter.getCapability(villager).setTransactionPoint(0);
			}
		}
	}
	public boolean hasDisplayedSecrets(){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			return UnsagaVillagerCapability.adapter.getCapability(villager).hasDisplayedSecretMerchandises();
		}
		return false;
	}

	public void resetDisplayedSecretMerchandise(){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			UnsagaVillagerCapability.adapter.getCapability(villager).setHasDisplayedSecrets(false);
		}
	}
	/**
	 * 生成レベル１：店レベル/10・・・商品の半分<br>
	 * 生成レベル２：(店レベル +流通レベル*4)/10・・・商品の半分<br>
	 * 生成レベル３：生成レベル２に＋２したもの・・・目利き<br>
	 */
	public void updateMerchandises(){



		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){

			this.checkDistributionLevelUp();
			UnsagaVillagerCapability.adapter.getCapability(villager).setStockedTime(this.world.getTotalWorldTime());
			int distLV = UnsagaVillagerCapability.adapter.getCapability(villager).getDistributionLevel();
			Triplet<Integer,Integer,Integer> generateLevel = this.getGenerateLevels(distLV);
			Set<ToolCategory> category = UnsagaVillagerCapability.adapter.getCapability(villager).getShopType().getAvailableMerchandiseCategory();
			Set<UnsagaMaterial> stricts = UnsagaVillagerCapability.adapter.getCapability(villager).getShopType().getAvailableMerchandiseMaterials();
			List<ItemStack> merchandises1 = factory.createMerchandises(4, generateLevel.first, category,stricts);
			List<ItemStack> merchandises2 = factory.createMerchandises(5, generateLevel.second, category,stricts);
			ItemStackList list = new ItemStackList(9);
			merchandises1.addAll(merchandises2);
			list.setStacks(merchandises1);
			UnsagaVillagerCapability.adapter.getCapability(villager).setMerchandises(list);
			UnsagaVillagerCapability.adapter.getCapability(villager).setSecretMerchandises(new ItemStackList(9));
		}
	}

	public @Nullable ItemStackList createSecretMerchandises(int num){
		if(UnsagaVillagerCapability.adapter.hasCapability(villager) && !UnsagaVillagerCapability.adapter.getCapability(villager).hasDisplayedSecretMerchandises()){
			int distLV = UnsagaVillagerCapability.adapter.getCapability(villager).getDistributionLevel();
			Triplet<Integer,Integer,Integer> generateLevel = this.getGenerateLevels(distLV);
			Set<ToolCategory> category = UnsagaVillagerCapability.adapter.getCapability(villager).getShopType().getAvailableMerchandiseCategory();
			Set<UnsagaMaterial> stricts = UnsagaVillagerCapability.adapter.getCapability(villager).getShopType().getAvailableMerchandiseMaterials();
			List<ItemStack> merchandises1 = factory.createMerchandises(4, generateLevel.first, category,stricts);
			List<ItemStack> merchandises2 = factory.createMerchandises(5, generateLevel.second, category,stricts);
			List<ItemStack> merchandises3 = factory.createMerchandises(num, generateLevel.third(), category,stricts);
			ItemStackList list = new ItemStackList(9);
			list.setStacks(merchandises3);
//			UnsagaVillagerCapability.adapter.getCapability(villager).setSecretMerchandises(list);
			UnsagaVillagerCapability.adapter.getCapability(villager).setHasDisplayedSecrets(true);
			return list;
		}
		return null;
	}

	private Triplet<Integer,Integer,Integer> getGenerateLevels(int distLV){
		int generateLevel1 = MathHelper.clamp_int(this.calcShopLevel(villager)/10,2,20);
		int generateLevel2 = MathHelper.clamp_int((this.calcShopLevel(villager) + distLV * 4)/10,2,20);
		int generateLevel3 = MathHelper.clamp_int(generateLevel2  + 2, 2,20);
		return Triplet.of(generateLevel1, generateLevel2, generateLevel3);
	}
	/**
	 * ベース店レベル＋村の半径/3+村人数/3
	 * @param villager
	 * @return
	 */
	public int calcShopLevel(EntityVillager villager){
		Village village = villager.getEntityWorld().getVillageCollection().getNearestVillage(villager.getPosition(), 32);
		if(UnsagaVillagerCapability.adapter.hasCapability(villager)){
			if(village!=null){
				return (int)(village.getVillageRadius() / 3 + village.getNumVillagers() / 3) + UnsagaVillagerCapability.adapter.getCapability(villager).getBaseShopLevel();
			}else{
				return UnsagaVillagerCapability.adapter.getCapability(villager).getBaseShopLevel();
			}
		}

		return 1;
	}
}
