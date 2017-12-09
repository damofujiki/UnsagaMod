package mods.hinasch.unsaga.villager;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerCareer;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class UnsagaVillagerProfession {

	public VillagerProfession merchant;
	public VillagerProfession magicMerchant;
	public VillagerProfession unsagaSmith;
	public List<VillagerProfession> professions = Lists.newArrayList();

	protected static UnsagaVillagerProfession INSTANCE;
	public static UnsagaVillagerProfession instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaVillagerProfession();
		}
		return INSTANCE;
	}

	public static boolean isUnsagaVillager(EntityVillager villager){
		UnsagaVillagerProfession professions = UnsagaVillagerProfession.instance();
		if(villager.getProfessionForge()==professions.merchant){
			return true;
		}
		if(villager.getProfessionForge()==professions.magicMerchant){
			return true;
		}
		if(villager.getProfessionForge()==professions.unsagaSmith){
			return true;
		}
		return false;
	}

	public VillagerProfession getRandomProfession(Random rand){
		return professions.get(rand.nextInt(professions.size()));

	}
	protected UnsagaVillagerProfession(){
		this.merchant = new VillagerProfession(UnsagaMod.MODID+":merchant"
				,UnsagaMod.MODID+":textures/entity/villager/merchant.png"
				,"minecraft:textures/entity/zombie_villager/zombie_butcher.png");
		VillagerRegistry.instance().register(merchant);
		new VillagerCareer(this.merchant,"merchant");
		this.professions.add(merchant);

		this.magicMerchant = new VillagerProfession(UnsagaMod.MODID+":magicMerchant"
				,UnsagaMod.MODID+":textures/entity/villager/magic_merchant.png"
				,"minecraft:textures/entity/zombie_villager/zombie_butcher.png");
		VillagerRegistry.instance().register(magicMerchant);
		new VillagerCareer(this.magicMerchant,"magicMerchant");
		this.professions.add(magicMerchant);

		this.unsagaSmith = new VillagerProfession(UnsagaMod.MODID+":blackSmith"
				,UnsagaMod.MODID+":textures/entity/villager/unsaga_smith.png"
				,"minecraft:textures/entity/zombie_villager/zombie_smith.png");
		VillagerRegistry.instance().register(unsagaSmith);
		new VillagerCareer(this.unsagaSmith,"blackSmith");
		this.professions.add(unsagaSmith);
	}


}
