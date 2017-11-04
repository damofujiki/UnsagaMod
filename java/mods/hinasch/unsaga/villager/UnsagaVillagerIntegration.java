package mods.hinasch.unsaga.villager;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.villager.bartering.BarteringMaterialCategory;
import mods.hinasch.unsaga.villager.bartering.DisplayPriceEvent;
import mods.hinasch.unsaga.villager.bartering.MerchandiseCapability;
import mods.hinasch.unsaga.villager.smith.ByproductAbilityRegistry;
import mods.hinasch.unsaga.villager.smith.DisplaySmithInfoEvent;
import mods.hinasch.unsaga.villager.smith.MaterialTransformRegistry;
import mods.hinasch.unsaga.villager.smith.ValidPaymentRegistry;

public class UnsagaVillagerIntegration {

	public UnsagaVillagerProfession profession;
	public BarteringMaterialCategory barteringShopType;
	public ValidPaymentRegistry validPayments;
	public MaterialTransformRegistry materialTransform;
	public ByproductAbilityRegistry byproductAbility;
	private static UnsagaVillagerIntegration INSTANCE;

	public static UnsagaVillagerIntegration instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaVillagerIntegration();
		}
		return INSTANCE;
	}
	public void registerCapabilities(){
		UnsagaVillagerCapability.adapterBase.registerCapability();
		InteractionInfoCapability.adapterBase.registerCapability();
		MerchandiseCapability.adapterBase.registerCapability();
	}

	public void registerCapabilityAttachEvents(){
		UnsagaVillagerCapability.registerEvents();
		InteractionInfoCapability.registerEvents();
		MerchandiseCapability.registerAttachEvents();
	}
	public void preInit(){
		this.profession = UnsagaVillagerProfession.instance();
		this.validPayments = ValidPaymentRegistry.instance();
		this.materialTransform = MaterialTransformRegistry.instance();
		this.byproductAbility = ByproductAbilityRegistry.instance();
	}

	public void init(){
		this.barteringShopType = BarteringMaterialCategory.instance();
		this.barteringShopType.init();
		this.validPayments.register();
		this.materialTransform.register();
		this.byproductAbility.register();

		DisplayPriceEvent.register();
		HSLibs.registerEvent(new DisplaySmithInfoEvent());
	}
}
