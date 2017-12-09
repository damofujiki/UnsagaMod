package mods.hinasch.unsaga.ability;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mods.hinasch.unsaga.ability.specialmove.SpecialMove;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveRegistry;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.material.UnsagaWeightType;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AbilityAssociateRegistry {

	public Map<ResourceLocation,List<IAbility>> abilityAssociationMap = Maps.newHashMap();

	protected static AbilityAssociateRegistry INSTANCE;
	UnsagaMaterials m = UnsagaMaterials.instance();
	SpecialMoveRegistry s = SpecialMoveRegistry.instance();
	AbilityRegistry a = AbilityRegistry.instance();

	protected AbilityAssociateRegistry(){

	}
	public static AbilityAssociateRegistry instance(){
		if(INSTANCE ==null){
			INSTANCE = new AbilityAssociateRegistry();
		}
		return INSTANCE;
	}

	public void preinit(){
		this.associateAbilities();
		this.associateSpecialArts();
	}

	public void init(){

	}
	public void associateSpecialArts(){
		this.addAssociation(ToolCategory.SWORD, UnsagaWeightType.HEAVY, s.smash,s.vandalize,s.gust);
		this.addAssociation(ToolCategory.SWORD, UnsagaWeightType.LIGHT, s.chargeBlade,s.hawkBlade,s.gust);
		this.addAssociation(ToolCategory.AXE, UnsagaWeightType.HEAVY, s.fujiView,s.firewoodChopper,s.woodChopper,s.skullCrash);
		this.addAssociation(ToolCategory.AXE, UnsagaWeightType.LIGHT, s.yoyo,s.woodChopper,s.skyDrive);
		this.addAssociation(ToolCategory.SPEAR, UnsagaWeightType.HEAVY, s.aiming,s.acupuncture,s.grasshopper);
		this.addAssociation(ToolCategory.SPEAR, UnsagaWeightType.LIGHT, s.swing,s.armOfLight,s.grasshopper);
		this.addAssociation(ToolCategory.KNIFE, UnsagaWeightType.HEAVY, s.bloodMary,s.lightningThrust,s.stunner);
		this.addAssociation(ToolCategory.KNIFE, UnsagaWeightType.LIGHT, s.knifeThrow,s.blitz,s.hawkBlade);
		this.addAssociation(ToolCategory.STAFF, UnsagaWeightType.HEAVY, s.earthDragon,s.grandSlam,s.gonger,s.rockCrasher);
		this.addAssociation(ToolCategory.STAFF, UnsagaWeightType.LIGHT, s.pulverizer,s.rockCrasher,s.skullCrash);
		this.addAssociation(ToolCategory.BOW, UnsagaWeightType.HEAVY, s.exorcist,s.phoenix,s.zapper);
		this.addAssociation(ToolCategory.BOW, UnsagaWeightType.LIGHT, s.exorcist,s.quickChecker,s.shadowStitching);
	}
	public void associateAbilities(){
		a.supportFire.addAssociation(ToolCategory.HELMET, m.feather);
		a.supportFire.addAssociation(ToolCategory.HELMET, m.categoryClothes);
		a.supportMetal.addAssociation(ToolCategory.HELMET, m.categoryLeathers);
		a.supportWater.addAssociation(ToolCategory.HELMET, m.faerieSilver);
		a.supportWater.addAssociation(ToolCategory.HELMET, m.silver);
		a.armorColdEx.addAssociation(ToolCategory.HELMET, m.fur);
		a.armorColdEx.addAssociation(ToolCategory.HELMET, m.hydraLeather);
		a.antiPoison.addAssociation(ToolCategory.HELMET, m.hydraLeather);
		a.armorFireEx.addAssociation(ToolCategory.HELMET, m.hydraLeather);
		a.armorPierce.addAssociation(ToolCategory.HELMET, m.silver);
		a.armorPierce.addAssociation(ToolCategory.HELMET, m.faerieSilver);

		AbilityList abilities = AbilityList.of(a.healDown1,a.antiBlind,a.intelligenceProtection);
		abilities.addAssociation(ToolCategory.HELMET, m.hydraLeather);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryBones);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryScales);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryDebris);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryBestials);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryRocks);
		abilities.addAssociation(ToolCategory.HELMET, m.categoryMetals);
		abilities.addAssociation(ToolCategory.HELMET, m.categorySteels);
		this.removeAssociation(ToolCategory.HELMET, m.silver, a.healDown1);
		this.removeAssociation(ToolCategory.HELMET, m.faerieSilver, a.healDown1);
		this.removeAssociation(ToolCategory.HELMET, m.silver, a.antiBlind);
		this.removeAssociation(ToolCategory.HELMET, m.faerieSilver, a.antiBlind);
		this.removeAssociation(ToolCategory.HELMET, m.diamond, a.antiBlind);
		this.removeAssociation(ToolCategory.HELMET, m.ruby, a.antiBlind);
		this.removeAssociation(ToolCategory.HELMET, m.sapphire, a.antiBlind);
		a.armorFire.addAssociation(ToolCategory.HELMET, m.ruby);
		a.armorSlash.addAssociation(ToolCategory.HELMET, m.sapphire);
		a.armorDebuff.addAssociation(ToolCategory.HELMET, m.diamond);
		a.antiWither.addAssociation(ToolCategory.HELMET, m.faerieSilver);

		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.feather);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryWoods);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryTusks);
		a.healDown1.addAssociation(ToolCategory.ACCESSORY, m.categoryBones);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryScales);
		a.healUp2.addAssociation(ToolCategory.ACCESSORY, m.lightStone);
		a.healDown4.addAssociation(ToolCategory.ACCESSORY, m.darkStone);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryDebris);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryBestials);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryRocks);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryMetals);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categorySteels);
		a.healUp1.addAssociation(ToolCategory.ACCESSORY, m.categoryCorundums);
		a.healUp2.addAssociation(ToolCategory.ACCESSORY, m.sivaQueen);
		a.healUp2.swapAssociation(ToolCategory.ACCESSORY, m.faerieSilver,a.healUp1);
		a.supportFire.addAssociation(ToolCategory.ACCESSORY, m.feather);
		a.supportFire.addAssociation(ToolCategory.ACCESSORY, m.categoryWoods);
		a.supportWood.addAssociation(ToolCategory.ACCESSORY, m.categoryTusks);
		a.supportWood.addAssociation(ToolCategory.ACCESSORY, m.categoryBones);
		a.supportWood.addAssociation(ToolCategory.ACCESSORY, m.categoryScales);
		a.supportWood.addAssociation(ToolCategory.ACCESSORY, m.lightStone);
		a.spellForbidden.addAssociation(ToolCategory.ACCESSORY, m.darkStone);
		a.supportMetal.addAssociation(ToolCategory.ACCESSORY, m.categoryDebris);
		a.supportMetal.addAssociation(ToolCategory.ACCESSORY, m.categoryMetals);
		a.supportWater.swapAssociation(ToolCategory.ACCESSORY, m.silver,a.supportMetal);
		a.supportWater.addAssociation(ToolCategory.ACCESSORY, m.sapphire);
		a.supportEarth.addAssociation(ToolCategory.ACCESSORY, m.ruby);
		a.supportFire.swapAssociation(ToolCategory.ACCESSORY, m.diamond,a.supportMetal);
		a.supportWater.addAssociation(ToolCategory.ACCESSORY, m.categoryMetals);
		a.supportWater.addAssociation(ToolCategory.ACCESSORY, m.categorySteels);
		a.lifeProtection.addAssociation(ToolCategory.ACCESSORY, m.categoryBones);
		a.lifeProtection.addAssociation(ToolCategory.ACCESSORY, m.lightStone);
		a.lifeProtection.addAssociation(ToolCategory.ACCESSORY, m.damascus);
		a.spellFire.addAssociation(ToolCategory.ACCESSORY, m.carnelian);
		a.spellEarth.addAssociation(ToolCategory.ACCESSORY, m.topaz);
		a.spellMetal.addAssociation(ToolCategory.ACCESSORY, m.opal);
		a.spellWater.addAssociation(ToolCategory.ACCESSORY, m.ravenite);
		a.spellWood.addAssociation(ToolCategory.ACCESSORY, m.lazuli);
		a.supportFire.addAssociation(ToolCategory.ACCESSORY, m.meteorite);
		a.supportMetal.addAssociation(ToolCategory.ACCESSORY, m.meteorite);
		a.antiBlind.addAssociation(ToolCategory.ACCESSORY, m.obsidian);
		a.antiWither.addAssociation(ToolCategory.ACCESSORY, m.diamond);
		a.supportWood.addAssociation(ToolCategory.ACCESSORY, m.meteoricIron);
		a.supportMetal.addAssociation(ToolCategory.ACCESSORY, m.meteoricIron);
		a.strengthProtection.addAssociation(ToolCategory.ACCESSORY, m.sivaQueen);
		a.dextalityProtection.addAssociation(ToolCategory.ACCESSORY, m.sivaQueen);
		a.vitalityProtection.addAssociation(ToolCategory.ACCESSORY, m.sivaQueen);

		a.healDown1.addAssociation(ToolCategory.ARMOR, m.categoryClothes);
		a.healDown1.addAssociation(ToolCategory.ARMOR, m.categoryLeathers);
		a.healDown1.addAssociation(ToolCategory.ARMOR, m.categoryWoods);
		a.healDown2.swapAssociation(ToolCategory.ARMOR, m.hydraLeather, a.healDown1);
		a.healDown2.addAssociation(ToolCategory.ARMOR, m.categoryBones);
		a.healDown2.addAssociation(ToolCategory.ARMOR, m.categoryScales);
		a.healDown2.addAssociation(ToolCategory.ARMOR, m.categoryBestials);
		a.healDown3.swapAssociation(ToolCategory.ARMOR, m.dragonScale,a.healDown2);
		a.healDown4.addAssociation(ToolCategory.ARMOR, m.categoryDebris);
		a.healDown4.addAssociation(ToolCategory.ARMOR, m.categoryRocks);
		a.healDown5.swapAssociation(ToolCategory.ARMOR, m.obsidian,a.healDown3);
		a.healDown5.addAssociation(ToolCategory.ARMOR, m.categoryMetals);
		a.healDown3.swapAssociation(ToolCategory.ARMOR, m.faerieSilver,a.healDown5);
		a.healDown3.swapAssociation(ToolCategory.ARMOR, m.silver,a.healDown5);
		a.armorCold.addAssociation(ToolCategory.ARMOR, m.categoryClothes);
		a.antiSleep.addAssociation(ToolCategory.ARMOR, m.liveSilk);
		a.mindProtection.addAssociation(ToolCategory.ARMOR, m.liveSilk);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryClothes);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryLeathers);
		a.antiPoison.swapAssociation(ToolCategory.ARMOR, m.hydraLeather,a.lifeProtection);
		a.armorColdEx.swapAssociation(ToolCategory.ARMOR, m.hydraLeather,a.armorCold);
		a.armorColdEx.swapAssociation(ToolCategory.ARMOR, m.crocodileLeather,a.armorCold);
		a.armorBruise.addAssociation(ToolCategory.ARMOR, m.crocodileLeather);
		a.armorFireEx.addAssociation(ToolCategory.ARMOR, m.hydraLeather);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryWoods);
		a.supportFire.addAssociation(ToolCategory.ARMOR, m.categoryWoods);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryBones);
		a.armorPierceBad.addAssociation(ToolCategory.ARMOR, m.categoryBones);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryScales);
		a.armorElectricEx.swapAssociation(ToolCategory.ARMOR, m.dragonScale,a.lifeProtection);
		a.armorFireEx.addAssociation(ToolCategory.ARMOR, m.dragonScale);
		a.armorColdEx.addAssociation(ToolCategory.ARMOR, m.dragonScale);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryDebris);
		a.supportWater.addAssociation(ToolCategory.ARMOR, m.opal);
		a.supportFire.addAssociation(ToolCategory.ARMOR, m.lazuli);
		a.supportEarth.addAssociation(ToolCategory.ARMOR, m.carnelian);
		a.supportMetal.addAssociation(ToolCategory.ARMOR, m.topaz);
		a.supportWood.addAssociation(ToolCategory.ARMOR, m.ravenite);
		a.lifeProtection.addAssociation(ToolCategory.ARMOR, m.categoryRocks);
		a.armorElectricEx.swapAssociation(ToolCategory.ARMOR, m.meteorite, a.lifeProtection);
		a.vitalityProtection.addAssociation(ToolCategory.ARMOR, m.categoryMetals);
		a.supportWater.addAssociation(ToolCategory.ARMOR, m.silver);
		a.armorLight.addAssociation(ToolCategory.ARMOR, m.meteoricIron);
		a.armorElectricEx.addAssociation(ToolCategory.ARMOR, m.meteoricIron);
		a.antiWither.addAssociation(ToolCategory.ARMOR, m.faerieSilver);
		a.antiPoison.addAssociation(ToolCategory.ARMOR, m.faerieSilver);

		a.armorCold.addAssociation(ToolCategory.LEGGINS, m.categoryClothes);
		a.healDown1.addAssociation(ToolCategory.LEGGINS, m.categoryBestials);
		a.healDown1.addAssociation(ToolCategory.LEGGINS, m.serpentine);
		a.healDown1.addAssociation(ToolCategory.BOOTS, m.hydraLeather);
		a.healDown1.addAssociation(ToolCategory.LEGGINS, m.categoryDebris);
		a.healDown2.addAssociation(ToolCategory.LEGGINS, m.quartz);
		a.healDown2.addAssociation(ToolCategory.BOOTS,m.obsidian);
		a.healDown1.addAssociation(ToolCategory.LEGGINS, m.copper,m.lead,m.meteoricIron);
		a.healDown2.addAssociation(ToolCategory.BOOTS, m.obsidian,m.lead,m.copper,m.meteoricIron);
		a.armorCold.addAssociation(ToolCategory.BOOTS, m.categoryClothes);
		a.armorCold.addAssociation(ToolCategory.BOOTS, m.fur,m.snakeLeather,m.crocodileLeather);
		a.armorColdEx.addAssociation(ToolCategory.BOOTS, m.hydraLeather);
		a.armorFireEx.addAssociation(ToolCategory.BOOTS, m.hydraLeather);
		a.antiPoison.addAssociation(ToolCategory.BOOTS, m.hydraLeather);
		a.supportWater.addAssociation(ToolCategory.LEGGINS, m.copperOre);
		a.supportWater.addAssociation(ToolCategory.LEGGINS, m.iron);
		a.antiLevitation.addAssociation(ToolCategory.LEGGINS, m.iron);

		a.healDown1.addAssociation(ToolCategory.SHIELD, m.categoryWoods);
		a.healDown1.addAssociation(ToolCategory.SHIELD, m.categoryLeathers);
		a.healDown2.addAssociation(ToolCategory.SHIELD, m.categoryScales);
		a.healDown2.addAssociation(ToolCategory.SHIELD, m.categoryDebris);
		a.healDown2.addAssociation(ToolCategory.SHIELD, m.categoryBestials);
		a.healDown2.addAssociation(ToolCategory.SHIELD, m.categoryRocks);
		a.healDown4.swapAssociation(ToolCategory.SHIELD, m.obsidian,a.healDown2);
		a.healDown3.addAssociation(ToolCategory.SHIELD, m.categoryMetals);
		a.healDown3.addAssociation(ToolCategory.SHIELD, m.categorySteels);
		a.healDown1.swapAssociation(ToolCategory.SHIELD, m.silver, a.healDown3);
		a.blockBreath.addAssociation(ToolCategory.SHIELD, m.dragonScale);
		a.supportWater.addAssociation(ToolCategory.SHIELD, m.ironOre);
		a.supportWater.addAssociation(ToolCategory.SHIELD, m.silver);
		a.healDown2.addAssociation(ToolCategory.SHIELD, m.sivaQueen);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryWoods);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryScales);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryDebris);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryRocks);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryBestials);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categoryMetals);
		a.blockMelee.addAssociation(ToolCategory.SHIELD, m.categorySteels);

		a.blockBreath.addAssociation(ToolCategory.SHIELD, m.dragonScale);
		a.blockBlast.addAssociation(ToolCategory.SHIELD, m.sivaQueen);



	}

	public List<IAbility> getAbilityList(ItemStack is){
		if(is!=null && UnsagaMaterialCapability.adapter.hasCapability(is) && AbilityCapability.adapter.hasCapability(is)){
			IUnsagaMaterialSelector intf = ((IUnsagaMaterialSelector)is.getItem());
			if(!intf.getCategory().isWeapon()){
				return this.getAbilityList(intf.getCategory(), UnsagaMaterialCapability.adapter.getCapability(is).getMaterial());
			}else{
				UnsagaWeightType type = UnsagaWeightType.fromWeight(UnsagaMaterialCapability.adapter.getCapability(is).getWeight());
				return this.getSpecialArts(intf.getCategory(), type);
			}
		}
		return Lists.newArrayList();
	}
	protected void addAssciation(ToolCategory category,UnsagaMaterial material,Ability ab){
		ResourceLocation key = this.buildKey(category, material);
		List<IAbility> list;
		if(abilityAssociationMap.containsKey(key)){
			list = abilityAssociationMap.get(key);
		}else{
			list = Lists.newArrayList();
		}
		list.add(ab);
		abilityAssociationMap.put(key, list);
		return;
	}
	protected void addAssociation(ToolCategory category,UnsagaMaterials.Category cate,Ability ab){
		cate.getChildMaterials().stream().forEach(m -> this.addAssciation(category, m, ab));
	}

	protected void addAssociation(ToolCategory category,List<UnsagaMaterial> materials,Ability ab){
		materials.stream().forEach(m -> this.addAssciation(category, m, ab));
	}

	protected void addAssociation(ToolCategory category,UnsagaWeightType weight,SpecialMove... arts){
		ResourceLocation key = this.buildKey(category, weight);
		List<IAbility> list = Lists.newArrayList(arts);
		this.abilityAssociationMap.put(key, list);

	}

	protected ResourceLocation buildKey(ToolCategory category,UnsagaWeightType weight){
		return new ResourceLocation("specialArt."+category.getPrefix() + "." + weight.getName());
	}
	protected void removeAssociation(ToolCategory category,UnsagaMaterial material,Ability ab){
		ResourceLocation key = this.buildKey(category, material);
		List<IAbility> list = this.abilityAssociationMap.get(key);
		if(list!=null){
			list.remove(ab);
		}
	}
	protected ResourceLocation buildKey(ToolCategory category,UnsagaMaterial material){
		return new ResourceLocation("ability."+category.getPrefix() + "." + material.getKey().getResourcePath());
	}

	protected List<IAbility> getSpecialArts(ToolCategory cate,UnsagaWeightType type){
		ResourceLocation key = this.buildKey(cate, type);
		List<IAbility> list = this.abilityAssociationMap.get(key);
		return list!=null? list : Lists.newArrayList();
	}

	protected List<IAbility> getAbilityList(ToolCategory cate,UnsagaMaterial m){

		ResourceLocation key = this.buildKey(cate, m);
		List<IAbility> list = this.abilityAssociationMap.get(key);
		return list!=null? list : Lists.newArrayList();
	}

	public static class AbilityList {
		List<Ability> list = Lists.newArrayList();

		public static AbilityList of(Ability... a){
			return new AbilityList(a);
		}
		public AbilityList(Ability... a){
			this.list = Lists.newArrayList(a);
		}

		public void addAssociation(ToolCategory cate,UnsagaMaterials.Category materials){
			list.stream().forEach(ab -> ab.addAssociation(cate, materials));
		}

		public void addAssociation(ToolCategory cate,UnsagaMaterial material){
			list.stream().forEach(ab -> ab.addAssociation(cate, material));
		}
	}
}
