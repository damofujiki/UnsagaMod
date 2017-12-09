package mods.hinasch.unsaga.minsaga;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.Lists;
import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsaga.minsaga.ForgingCapability.IStatusModifier;
import mods.hinasch.unsaga.villager.smith.SmithMaterialRegistry;
import mods.hinasch.unsaga.villager.smith.SmithMaterialRegistry.IGetItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;
import net.minecraftforge.oredict.OreDictionary;

public class MinsagaForging {

	public static enum Ability implements IStringSerializable{SEA("Sea"),ABYSS("Abyss"),LOOT("Looting"),FAERIE("Faerie")
		,QUICKSILVER("Quicksilver"),HARVEST("Harvest +1"),METEOR("Meteor"),DARK("dark"),WEAKNESS("weak");

		String name;

		private Ability(String name){
			this.name = name;
		}
		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return name;
		}


	};

	public static MinsagaForging INSTANCE;

	public static MinsagaForging instance(){
		if(INSTANCE==null){
			INSTANCE = new MinsagaForging();
		}
		return INSTANCE;
	}
	public static class Material extends PropertyElementBase implements IStatusModifier{

		int repairCost =5;
		int repairDamage = 0;
		float attackModifier = 0;
		float efficiencyModifier = 0;
		int durabilityModifier = 0;
		List<Ability> abilities = Lists.newArrayList();
		public float getEfficiencyModifier() {
			return efficiencyModifier;
		}


		public void setEfficiencyModifier(float efficiencyModifier) {
			this.efficiencyModifier = efficiencyModifier;
		}
		ModifierPair pairModifier = new ModifierPair(0.0F, 0.0F);


		int weight;


		Predicate<ItemStack> predicateItem;


		public Material(String name) {
			super(new ResourceLocation(name), name);

		}


		public String getLocalized(){
			return HSLibs.translateKey(this.getUnlocalizedName());
		}
		public String getUnlocalizedName(){
			return "minsaga.material."+this.getPropertyName();
		}
		public ModifierPair getArmorModifier() {
			return this.pairModifier;
		}

		public Predicate<ItemStack> checker(){
			return this.predicateItem;
		}

		public float getAttackModifier() {
			return attackModifier;
		}


		@Override
		public Class getParentClass() {
			// TODO 自動生成されたメソッド・スタブ
			return Material.class;
		}


		public int getCostModifier() {
			return repairCost;
		}


		public int getRepairDamage() {
			return repairDamage;
		}


		public void setAbility(Ability... abilities){
			this.abilities = Lists.newArrayList(abilities);
		}
		public List<Ability> getAbilities(){
			return this.abilities;
		}
		public boolean hasAbilities(){
			return !this.abilities.isEmpty();
		}
		public boolean hasAbility(Ability ab){
			return this.abilities.contains(ab);
		}

		public boolean isMaterialItem(ItemStack stack){
			if(this.predicateItem==null){
				return false;
			}
			return this.predicateItem.test(stack);
		}
		public Material setArmorModifier(float armor,float magic) {
			this.pairModifier = new ModifierPair(armor,magic);
			return this;
		}
		public Material setAttackModifier(float attackModifier) {
			this.attackModifier = attackModifier;
			return this;
		}
		public Material setMaterialChecker(Predicate<ItemStack> predicate){
			this.predicateItem = predicate;
			return this;
		}


		public Material setRepairCost(int repairCost) {
			this.repairCost = repairCost;
			return this;
		}

		public Material setRepairDamage(int repairDamage) {
			this.repairDamage = repairDamage;
			return this;
		}
		public Material setWeight(int weight) {
			this.weight = weight;
			return this;
		}



		public Material setDurabilityModifier(int par1){
			this.durabilityModifier = par1;
			return this;
		}
		@Override
		public int getDurabilityModifier() {
			// TODO 自動生成されたメソッド・スタブ
			return this.durabilityModifier;
		}


		@Override
		public int getWeightModifier() {
			// TODO 自動生成されたメソッド・スタブ
			return this.weight;
		}

	}

	public RegistrySimple<ResourceLocation,Material> allMaterials = new RegistrySimple();
	public Material silver = new Material("silver");
	public Material bronze = new Material("bronze");
	public Material cinnabar = new Material("cinnabar");
	public Material bismuth = new Material("bismuth");
	public Material electrum = new Material("electrum");
	public Material marcasite = new Material("marcasite");
	public Material meteoricIron = new Material("meteoricIron");
	public Material chitin = new Material("chitin");
	public Material damascus = new Material("damascus");
	public Material abyssCrystal = new Material("abyssCrystal");
	public Material darkCrystal = new Material("darkCrystal");
	public Material devilsweedSeed = new Material("devilsweedSeed");
	public Material gold = new Material("gold");
	public Material ancientFishScale = new Material("ancientFishScale");
	public Material faerieSilverPlate = new Material("faerieSilver");
	public Material halite = new Material("halite");
	public Material amber = new Material("amber");

	public Material debris1 = new Material("debris1");
	public Material debris2 = new Material("debris2");
	public Material debris3 = new Material("debris3");
	public Material debris4 = new Material("debris4");
	public Material debris5 = new Material("debris5");
	public Material debris6 = new Material("debris6");
	public Material debris7 = new Material("debris7");

	protected MinsagaForging(){

	}
	public void init(){
		UnsagaMod.logger.get().info("registering minsaga forging properties...");
		this.initPredicates();
		this.initModifiers();
		this.initRepairData();
		this.initAbility();
		this.initWeight();
		this.initDurability();
		this.registerMaterials();
		MinsagaForgingEvent.registerEvents();
		//		UnsagaMod.logger.trace("test", this.allMaterials.getKeys().stream().map(in -> this.allMaterials.getObject(in)).collect(Collectors.toList()));
	}
	private void registerMaterials(){
		this.registerMaterial(silver);
		this.registerMaterial(bronze);
		this.registerMaterial(cinnabar);
		this.registerMaterial(bismuth);
		this.registerMaterial(electrum);
		this.registerMaterial(marcasite);
		this.registerMaterial(meteoricIron);
		this.registerMaterial(chitin);
		this.registerMaterial(damascus);
		this.registerMaterial(abyssCrystal);
		this.registerMaterial(darkCrystal);
		this.registerMaterial(gold);
		this.registerMaterial(ancientFishScale);
		this.registerMaterial(faerieSilverPlate);
		this.registerMaterial(halite);
		this.registerMaterial(amber);
		this.registerMaterial(devilsweedSeed);
		this.registerMaterial(debris1);
		this.registerMaterial(debris2);
		this.registerMaterial(debris3);
		this.registerMaterial(debris4);
		this.registerMaterial(debris5);
		this.registerMaterial(debris6);
		this.registerMaterial(debris7);

	}

	public Optional<Material> getMaterialFromItemStack(ItemStack is){
		return this.allMaterials.getKeys().stream().map(in -> this.allMaterials.getObject(in))
				.filter(in -> in.isMaterialItem(is)).findFirst();
	}

	private void registerMaterial(Material material){
		UnsagaMod.logger.trace("registering minsaga material", material.getPropertyName());
		this.allMaterials.putObject(new ResourceLocation(material.getPropertyName()),material);
	}

	public RegistrySimple<ResourceLocation,Material> registry(){
		return this.allMaterials;
	}
	private void initDurability(){
		silver.setDurabilityModifier(+2);
		bronze.setDurabilityModifier(+1);
		cinnabar.setDurabilityModifier(+1);
		bismuth.setDurabilityModifier(+1);
		marcasite.setDurabilityModifier(-1);
		meteoricIron.setDurabilityModifier(-1);
		chitin.setDurabilityModifier(+1);
		damascus.setDurabilityModifier(-1);
		abyssCrystal.setDurabilityModifier(-3);
		darkCrystal.setDurabilityModifier(-3);
		gold.setDurabilityModifier(+2);
		ancientFishScale.setDurabilityModifier(+1);
		faerieSilverPlate.setDurabilityModifier(+1);
		amber.setDurabilityModifier(+1);
		halite.setDurabilityModifier(-2);
		devilsweedSeed.setDurabilityModifier(0);
		debris1.setDurabilityModifier(0);
		debris2.setDurabilityModifier(+1);
		debris3.setDurabilityModifier(-1);
		debris4.setDurabilityModifier(-1);
		debris5.setDurabilityModifier(-2);
		debris6.setDurabilityModifier(-3);
		debris7.setDurabilityModifier(-2);
	}
	private void initPredicates(){
		silver.setMaterialChecker(this.getOreDictChecker("ingotSilver"));
		bronze.setMaterialChecker(this.getOreDictChecker("ingotBronze"));
		cinnabar.setMaterialChecker(this.getOreDictChecker("crystalCinnabar"));
		bismuth.setMaterialChecker(this.getOreDictChecker("ingotBismuth"));
		electrum.setMaterialChecker(this.getOreDictChecker("ingotElectrum"));
		marcasite.setMaterialChecker(this.getOreDictChecker("oreMarcasite"));
		meteoricIron.setMaterialChecker(this.getOreDictChecker("meteoricIron"));
		chitin.setMaterialChecker(this.getOreDictChecker("chitin"));
		damascus.setMaterialChecker(this.getOreDictChecker("ingotDamascusSteel"));
		abyssCrystal.setMaterialChecker(this.getOreDictChecker("enderpearl"));
		darkCrystal.setMaterialChecker(this.getOreDictChecker("gemDemonite"));
		gold.setMaterialChecker(this.getOreDictChecker("ingotGold"));
		ancientFishScale.setMaterialChecker(this.getOreDictChecker("scaleFish"));
		faerieSilverPlate.setMaterialChecker(this.getOreDictChecker("ingotFaerieSilver"));
		amber.setMaterialChecker(this.getOreDictChecker("gemAmber"));
		devilsweedSeed.setMaterialChecker(this.getOreDictChecker("cropNetherWart"));
		debris1.setMaterialChecker(this.getItemStackChecker(RawMaterialItemRegistry.instance().debris1.getItem(), RawMaterialItemRegistry.instance().debris1.getMeta()));
		debris2.setMaterialChecker(this.getItemStackChecker(RawMaterialItemRegistry.instance().debris2.getItem(), RawMaterialItemRegistry.instance().debris2.getMeta()));
	}

	private void initWeight(){
		silver.setWeight(2);
		bronze.setWeight(1);
		cinnabar.setWeight(2);
		bismuth.setWeight(2);
		electrum.setWeight(2);
		marcasite.setWeight(1);
		meteoricIron.setWeight(2);
		chitin.setWeight(1);
		damascus.setWeight(2);
		abyssCrystal.setWeight(2);
		darkCrystal.setWeight(1);
		gold.setWeight(3);
		amber.setWeight(1);
		ancientFishScale.setWeight(2);
		faerieSilverPlate.setWeight(1);
		halite.setWeight(1);
		devilsweedSeed.setWeight(1);
		debris1.setWeight(1);
		debris2.setWeight(1);
		debris3.setWeight(1);
		debris4.setWeight(1);
		debris5.setWeight(1);
		debris6.setWeight(2);
		debris7.setWeight(1);
	}
	private void initRepairData(){
		silver.setRepairDamage(20).setRepairCost(-1);
		bronze.setRepairDamage(40).setRepairCost(-1);
		cinnabar.setRepairDamage(40).setRepairCost(+1);
		bismuth.setRepairCost(-2).setRepairDamage(60);
		electrum.setRepairDamage(30).setRepairCost(-2);
		marcasite.setRepairDamage(30).setRepairCost(+2);
		meteoricIron.setRepairDamage(40).setRepairCost(+2);
		chitin.setRepairDamage(15).setRepairCost(-1);
		damascus.setRepairDamage(40).setRepairCost(-1);
		abyssCrystal.setRepairCost(+3).setRepairDamage(30);
		darkCrystal.setRepairCost(+3).setRepairDamage(30);
		gold.setRepairDamage(40).setRepairCost(-1);
		ancientFishScale.setRepairCost(-1).setRepairDamage(20);
		faerieSilverPlate.setRepairCost(-1).setRepairDamage(20);
		halite.setRepairDamage(15).setRepairCost(+2);
		amber.setRepairCost(+1).setRepairDamage(15);
		devilsweedSeed.setRepairCost(+1).setRepairDamage(10);
		debris1.setRepairDamage(30);
		debris2.setRepairCost(-1).setRepairDamage(15);

	}

	private void initModifiers(){
		silver.setAttackModifier(-0.5F).setArmorModifier(0.0F,0.5F).setEfficiencyModifier(-0.5F);
		bronze.setAttackModifier(+0.5F).setArmorModifier(0.5F,0.0F).setEfficiencyModifier(1.0F);
		cinnabar.setArmorModifier(0.5F, 0.5F);
		bismuth.setAttackModifier(-0.5F).setArmorModifier(0.2F, 0.3F).setEfficiencyModifier(-0.5F);
		electrum.setArmorModifier(-0.5F,0.5F).setEfficiencyModifier(-0.5F);
		marcasite.setArmorModifier(0.5F,-0.5F).setEfficiencyModifier(1.0F);
		meteoricIron.setAttackModifier(0.5F).setArmorModifier(0.0F,0.5F).setEfficiencyModifier(1.5F);
		chitin.setArmorModifier(0.3F, 0.3F);
		damascus.setAttackModifier(+1.5F).setArmorModifier(1.5F, -0.5F).setEfficiencyModifier(2.0F);
		abyssCrystal.setAttackModifier(+1.5F).setArmorModifier(-1.0F, 1.0F);
		darkCrystal.setAttackModifier(+1.5F).setArmorModifier(-1.0F, 1.0F);
		gold.setAttackModifier(-0.5F).setArmorModifier(-0.5F, 0.5F).setEfficiencyModifier(-0.5F);
		ancientFishScale.setRepairCost(-1).setArmorModifier(0.3F, -0.3F);
		faerieSilverPlate.setRepairCost(-1).setArmorModifier(0.4F, 0.4F);
		amber.setAttackModifier(+0.5F).setArmorModifier(0.0F, 1.0F);
		halite.setAttackModifier(-1.0F).setArmorModifier(0.0F, -0.3F);
		devilsweedSeed.setAttackModifier(-0.5F).setArmorModifier(+0.5F, 0);
		debris1.setAttackModifier(-1.0F).setArmorModifier(0.3F, 0.3F);
		debris2.setAttackModifier(-0.5F).setArmorModifier(0.0F, 0.3F);
	}

	private void initAbility(){
		ancientFishScale.setAbility(Ability.SEA);
		abyssCrystal.setAbility(Ability.ABYSS);
		darkCrystal.setAbility(Ability.DARK);
		faerieSilverPlate.setAbility(Ability.FAERIE);
		electrum.setAbility(Ability.LOOT);
		cinnabar.setAbility(Ability.QUICKSILVER);
		meteoricIron.setAbility(Ability.METEOR);
		devilsweedSeed.setAbility(MinsagaForging.Ability.WEAKNESS);


	}
	private Predicate<ItemStack> getOreDictChecker(String orename){
		return new SmithMaterialRegistry.PredicateOre(orename);
	}

	private Predicate<ItemStack> getItemStackChecker(Item item,int damage){
		return new SmithMaterialRegistry.PredicateItem(item, damage);
	}

	public Material getMaterial(String name){
		ResourceLocation res = new ResourceLocation(name);
		if(this.allMaterials.containsKey(res)){
			return this.allMaterials.getObject(res);
		}
		return this.debris1;
	}
	public static class OreNameChecker implements Predicate<ItemStack>,IGetItemStack{

		final String orename;

		public OreNameChecker(String orename){
			this.orename = orename;
		}

		public String getOreName(){
			return this.orename;
		}
		@Override
		public boolean test(ItemStack is) {
			//			UnsagaMod.logger.trace("ores", this.getOreName(),HSLibs.getOreNames(is));
			if(is!=null){

				return HSLibs.getOreNames(is).stream().anyMatch(in -> in.equals(this.getOreName()));
			}
			return false;
		}

		@Override
		public List<ItemStack> getItemStack() {
			// TODO 自動生成されたメソッド・スタブ
			return OreDictionary.getOres(orename);
		}

	}

	public static class ModifierPair extends Pair<Float,Float>{

		public ModifierPair(Float first, Float second) {
			super(first, second);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public float melee(){
			return this.first();
		}

		public float magic(){
			return this.second();
		}
	}
}
