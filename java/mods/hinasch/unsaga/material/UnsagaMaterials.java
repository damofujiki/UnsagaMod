package mods.hinasch.unsaga.material;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;

import mods.hinasch.lib.misc.JsonHelper;
import mods.hinasch.lib.misc.JsonHelper.IJsonParser;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.item.newitem.combat.MaterialArmorTextureSetting;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class UnsagaMaterials extends PropertyRegistry<UnsagaMaterial>{


	public static class Category{
		public class PropertyGetter implements IItemPropertyGetter{

			Category cate;
			public PropertyGetter(UnsagaMaterials.Category m){

				this.cate = m;
			}
			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {

				if(UnsagaMaterialCapability.adapter.hasCapability(stack)){
					UnsagaMaterial mat = UnsagaMaterialCapability.adapter.getCapability(stack).getMaterial();

					if(mat.getParent().isPresent() && mat.getParent().get()==this.cate){
//						UnsagaMod.logger.trace(this.getClass().getName(),this.m);
						return 1.0F;
					}

				}
				return 0;
			}

		}
		public static final UnsagaMaterial DEFAULT = new UnsagaMaterial("test");
		static{
			DEFAULT.setToolMaterial(ToolMaterial.STONE);
			DEFAULT.setArmorMaterial(ArmorMaterial.LEATHER);
		}
		final String name;

		List<UnsagaMaterial> list = Lists.newArrayList();
		UnsagaMaterial defaultMaterial = DEFAULT;
		boolean isUseParentMaterial = false;
		Map<ToolCategory,Boolean> isUseParentMaterialMap = Maps.newHashMap();


		public Category(String name,UnsagaMaterial... materials){
			for(UnsagaMaterial m:materials){
				m.setParent(this);
			}
			this.name = name;
			list.addAll(Lists.newArrayList(materials));
		}
		public void addMaterial(UnsagaMaterial m){
			this.list.add(m);
		}
		public void contains(UnsagaMaterial m){
			this.list.contains(m);
		}
		public List<UnsagaMaterial> getChildMaterials(){
			return this.list;
		}

		public UnsagaMaterial getDefaultMaterial(){
			return this.defaultMaterial;
		}
		public String getName() {
			return name;
		}
		public IItemPropertyGetter getPropertyGetter(){
			return new PropertyGetter(this);
		}

		public boolean isUseParentMaterial(ToolCategory cate) {
			if(!this.isUseParentMaterial){
				if(this.isUseParentMaterialMap.containsKey(cate)){
					return this.isUseParentMaterialMap.get(cate);
				}
			}
			return isUseParentMaterial;
		}

		public void setDefaultMaterial(UnsagaMaterial m){
			this.defaultMaterial = m;
		}

		public void setUseParentMaterial(boolean isUseParentMaterial) {
			this.isUseParentMaterial = isUseParentMaterial;
		}

		public void setUseParentMaterial(ToolCategory cate,boolean isUseParentMaterial) {
			this.isUseParentMaterialMap.put(cate, isUseParentMaterial);
		}
	}

	public static class JsonParserMaterial implements IJsonParser{

		String name;
		int rank;
		int weight;
		int price;
		int harvestLevel = -1;
		int maxUses;
		float efficiency;
		float attack;
		int enchantWeapon;
		int damageFactor = -1;
		int[] reduction;
		int enchantArmor;
		float toughness;

		@Override
		public void parse(JsonObject jo){
			name = jo.get("name").getAsString();
			rank = jo.get("rank").getAsInt();
			weight = jo.get("weight").getAsInt();
			price = jo.get("price").getAsInt();
			if(!jo.get("harvest_level").getAsString().equals("") && jo.has("harvest_level")){
				harvestLevel = jo.get("harvest_level").getAsInt();
				maxUses = jo.get("max_uses").getAsInt();
				efficiency = jo.get("efficiency").getAsFloat();
				attack = jo.get("attack").getAsFloat();
				enchantWeapon = jo.get("w_enchant").getAsInt();
			}

			if(!jo.get("max_damagefactor").getAsString().equals("") && jo.has("max_damagefactor")){
				damageFactor = jo.get("max_damagefactor").getAsInt();
				String str = jo.get("reduction").getAsString();
				String[] strs = str.split(",");
				reduction = new int[strs.length];
				for(int i=0;i<strs.length;i++){
					reduction[i] = Integer.valueOf(strs[i]);
				}
				enchantArmor = jo.get("a_enchant").getAsInt();
				toughness = jo.get("toughness").getAsFloat();
			}


		}
	}
	private static UnsagaMaterials instance;
	public static UnsagaMaterials instance(){
		if(instance==null){
			instance = new UnsagaMaterials();
		}
		return instance;
	}
	public final UnsagaMaterial dummy = new UnsagaMaterial("dummy");
	public final UnsagaMaterial feather = new UnsagaMaterial("feather");
	public final UnsagaMaterial cotton = new UnsagaMaterial("cotton");

	public final UnsagaMaterial silk = new UnsagaMaterial("silk");
	public final UnsagaMaterial velvet = new UnsagaMaterial("velvet");
	public final UnsagaMaterial liveSilk = new UnsagaMaterial("liveSilk");
	public final UnsagaMaterial fur = new UnsagaMaterial("fur");

	public final UnsagaMaterial snakeLeather = new UnsagaMaterial("snakeLeather");
	public final UnsagaMaterial crocodileLeather = new UnsagaMaterial("crocodileLeather");
	public final UnsagaMaterial hydraLeather = new UnsagaMaterial("hydraLeather");
	public final UnsagaMaterial wood = new UnsagaMaterial("wood");

	public final UnsagaMaterial cypress = new UnsagaMaterial("cypress");
	public final UnsagaMaterial oak = new UnsagaMaterial("oak");
	public final UnsagaMaterial toneriko = new UnsagaMaterial("fraxinus");
	public final UnsagaMaterial tusk1 = new UnsagaMaterial("tusk1","tusk");

	public final UnsagaMaterial tusk2 = new UnsagaMaterial("tusk2","tusk");
	public final UnsagaMaterial bone1 = new UnsagaMaterial("bone1","bone");
	public final UnsagaMaterial bone2 = new UnsagaMaterial("bone2","bone");
	public final UnsagaMaterial thinScale = new UnsagaMaterial("thinScale");

	public final UnsagaMaterial chitin = new UnsagaMaterial("chitin");
	public final UnsagaMaterial ancientScale = new UnsagaMaterial("ancientFishScale");

	public final UnsagaMaterial dragonScale = new UnsagaMaterial("dragonScale");
	public final UnsagaMaterial lightStone = new UnsagaMaterial("lightStone");

	public final UnsagaMaterial darkStone = new UnsagaMaterial("darkStone");
	public final UnsagaMaterial debris1 = new UnsagaMaterial("debris1","debris");
	public final UnsagaMaterial debris2 = new UnsagaMaterial("debris2","debris");
	public final UnsagaMaterial carnelian = new UnsagaMaterial("carnelian");
	public final UnsagaMaterial topaz = new UnsagaMaterial("topaz");

	public final UnsagaMaterial ravenite = new UnsagaMaterial("ravenite");

	public final UnsagaMaterial lazuli = new UnsagaMaterial("lazuli");
	public final UnsagaMaterial opal = new UnsagaMaterial("opal");
	public final UnsagaMaterial serpentine = new UnsagaMaterial("serpentine");
	public final UnsagaMaterial copperOre = new UnsagaMaterial("copperOre");
	public final UnsagaMaterial quartz = new UnsagaMaterial("quartz");
	public final UnsagaMaterial meteorite = new UnsagaMaterial("meteorite");
	public final UnsagaMaterial ironOre = new UnsagaMaterial("ironOre");
	public final UnsagaMaterial silver = new UnsagaMaterial("silver");

	public final UnsagaMaterial obsidian = new UnsagaMaterial("obsidian");
	public final UnsagaMaterial ruby = new UnsagaMaterial("ruby","corundum");
	public final UnsagaMaterial sapphire = new UnsagaMaterial("sapphire","corundum");
	public final UnsagaMaterial diamond = new UnsagaMaterial("diamond");
	public final UnsagaMaterial copper = new UnsagaMaterial("copper");
	public final UnsagaMaterial lead = new UnsagaMaterial("lead");
	public final UnsagaMaterial iron = new UnsagaMaterial("iron");

	public final UnsagaMaterial meteoricIron = new UnsagaMaterial("meteoricIron");
	public final UnsagaMaterial steel1 = new UnsagaMaterial("steel1","steel");
	public final UnsagaMaterial steel2 = new UnsagaMaterial("steel2","steel");
	public final UnsagaMaterial faerieSilver = new UnsagaMaterial("faerieSilver");
	public final UnsagaMaterial damascus = new UnsagaMaterial("damascus");
	public final UnsagaMaterial dragonHeart = new UnsagaMaterial("dragonHeart");
	public final UnsagaMaterial sivaQueen = new UnsagaMaterial("sivaQueen");

	//////////////////こっから追加要素

	public final UnsagaMaterial jungleWood = new UnsagaMaterial("jungleWood");
	public final UnsagaMaterial birch = new UnsagaMaterial("birch");
	public final UnsagaMaterial spruce = new UnsagaMaterial("spruce");
	public final UnsagaMaterial acacia = new UnsagaMaterial("acacia");
	public final UnsagaMaterial darkOak = new UnsagaMaterial("darkOak");
	public final UnsagaMaterial gold = new UnsagaMaterial("gold");
	public final UnsagaMaterial brass = new UnsagaMaterial("brass");
	public final UnsagaMaterial nickelSilver = new UnsagaMaterial("nickelSilver");
	public final UnsagaMaterial chalcedony = new UnsagaMaterial("chalcedony");


	public final UnsagaMaterial bamboo = new UnsagaMaterial("bamboo");
	public final UnsagaMaterial osmium = new UnsagaMaterial("osmium");

	public final UnsagaMaterial stone = new UnsagaMaterial("stone");
	public final UnsagaMaterial prismarine = new UnsagaMaterial("prismarine");
	public final UnsagaMaterial shulker = new UnsagaMaterial("shulker");

	public List<Category> categories = Lists.newArrayList();
	public final Category categoryBestials = new Category("bestials",carnelian,topaz,ravenite,lazuli,opal);
	public final Category categoryDebris = new Category("debris",debris1,debris2);
	public final Category categoryWoods = new Category("woods",cypress,oak,toneriko,jungleWood
			,birch,spruce,acacia,darkOak);
	public final Category categoryTusks = new Category("tusks",tusk1,tusk2);
	public final Category categoryBones = new Category("bones",bone1,bone2);
	public final Category categoryScales = new Category("scales",thinScale,chitin,ancientScale,dragonScale,prismarine,shulker);
	public final Category categoryRocks = new Category("rocks",serpentine,quartz,copperOre,ironOre,meteorite,obsidian,diamond);
	public final Category categoryCorundums = new Category("corundums",ruby,sapphire);
	public final Category categoryMetals = new Category("metals",lead,iron,silver,meteoricIron,faerieSilver,damascus,copper);
	public final Category categorySteels = new Category("steels",steel1,steel2);

	public final Category categoryClothes = new Category("clothes",cotton,silk,velvet,liveSilk);

	public final Category categoryLeathers = new Category("leathers",fur,snakeLeather,crocodileLeather,hydraLeather);
	public final SuitableLists suitableLists = SuitableLists.instance();

	public List<String> subIconGetterNames = Lists.newArrayList();
	protected Map<String,List<UnsagaMaterial>> categorised = Maps.newHashMap();

	public ImmutableList<UnsagaMaterial> merchandiseMaterial;

	private UnsagaMaterials(){

	}

	@Override
	public void init() {



		categories.add(categoryBestials);
		categories.add(categoryDebris);
		categories.add(categoryWoods);
		categories.add(categoryTusks);
		categories.add(categoryBones);
		categories.add(categoryScales);
		categories.add(categoryRocks);
		categories.add(categoryCorundums);
		categories.add(categoryMetals);
		categories.add(categorySteels);
		categories.add(categoryClothes);
		categories.add(categoryLeathers);


		this.categoryWoods.setUseParentMaterial(true);
		this.categoryRocks.setUseParentMaterial(true);
		this.categoryBestials.setUseParentMaterial(true);
		this.categoryClothes.setUseParentMaterial(true);
		this.categoryLeathers.setUseParentMaterial(true);
		this.categoryScales.setUseParentMaterial(ToolCategory.ACCESSORY, true);


//
//		this.obsidian.addCategoryUseOriginalName(ToolCategory.SWORD);
//		this.obsidian.addCategoryUseOriginalName(ToolCategory.SPEAR);
//		this.toneriko.addCategoryUseOriginalName(ToolCategory.BOW);
//		this.hydraLeather.addCategoryUseOriginalName(ToolCategory.HELMET);
//		this.fur.addCategoryUseOriginalName(ToolCategory.HELMET);
//		this.diamond.addCategoryUseOriginalName(ToolCategory.HELMET);
//		this.meteorite.addCategoryUseOriginalName(ToolCategory.BOOTS);
//		this.obsidian.addCategoryUseOriginalName(ToolCategory.BOOTS);
//		this.hydraLeather.addCategoryUseOriginalName(ToolCategory.BOOTS);
//		this.liveSilk.addCategoryUseOriginalName(ToolCategory.ARMOR);
//		this.obsidian.addCategoryUseOriginalName(ToolCategory.ARMOR);
//		this.prismarine.addCategoryUseOriginalName(ToolCategory.ARMOR);
//		this.crocodileLeather.addCategoryUseOriginalName(ToolCategory.ARMOR);
//		this.addCategoryOriginalName(ToolCategory.ARMOR, carnelian,opal,lazuli,ravenite,topaz);
//		this.addCategoryOriginalName(ToolCategory.ACCESSORY, faerieSilver,meteoricIron,diamond,meteorite,obsidian);

		List<UnsagaMaterial> woods = Lists.newArrayList(oak,jungleWood,birch,acacia,spruce,cypress,darkOak);
		woods.forEach(in ->{
			in.setArmorMaterial(oak.getArmorMaterial());
			in.setToolMaterial(ToolMaterial.WOOD);
			if(in.rank<=0){
				in.rank = wood.rank;
			}
			if(in.price<=0){
				in.price = wood.price;
			}
		});
		woods.add(toneriko);
		woods.forEach(in->{
			in.setAnotherName(ToolCategory.KNIFE, "wood");
			in.setAnotherName(ToolCategory.SWORD, "wood");
			in.setAnotherName(ToolCategory.SPEAR, "wood");
			if(in!=toneriko){
				in.setAnotherName(ToolCategory.STAFF, "wood");
				in.setAnotherName(ToolCategory.BOW, "wood");
			}
			in.setAnotherName(ToolCategory.SHIELD, "wood");
			in.setAnotherName(ToolCategory.ACCESSORY, "wood");
			in.setAnotherName(ToolCategory.ARMOR, "wood");
			in.setAnotherName(ToolCategory.BOOTS, "wood");
		});

		List<UnsagaMaterial> scales = Lists.newArrayList(ancientScale,thinScale,dragonScale,chitin,prismarine,shulker);
		scales.forEach(in->{
			if(in!=dragonScale){
				in.setAnotherName(ToolCategory.KNIFE, "scale");
				in.setAnotherName(ToolCategory.SPEAR, "scale");
				in.setAnotherName(ToolCategory.SHIELD, "scale");
			}
			if(in!=dragonScale || in!=chitin || in!=shulker){
				in.setAnotherName(ToolCategory.SWORD, "scale");
			}
			if(in!=dragonScale || in!=ancientScale){
				in.setAnotherName(ToolCategory.ARMOR, "scale");
			}
			in.setAnotherName(ToolCategory.ACCESSORY, "scale");
			in.setAnotherName(ToolCategory.HELMET, "scale");
		});

		List<UnsagaMaterial> bestials = Lists.newArrayList(carnelian,opal,topaz,lazuli,ravenite);
		bestials.forEach(in ->{
			in.setAnotherName(ToolCategory.KNIFE, "bestial");
			in.setAnotherName(ToolCategory.SWORD, "bestial");
			in.setAnotherName(ToolCategory.AXE, "bestial");
			in.setAnotherName(ToolCategory.STAFF, "bestial");
			in.setAnotherName(ToolCategory.SPEAR, "bestial");
			in.setAnotherName(ToolCategory.SHIELD, "bestial");
			in.setAnotherName(ToolCategory.ACCESSORY, "bestial");
			in.setAnotherName(ToolCategory.HELMET, "stone");
			in.setAnotherName(ToolCategory.LEGGINS, "stone");
		});

		List<UnsagaMaterial> rocks = Lists.newArrayList(serpentine,copperOre,quartz,meteorite,ironOre);
		rocks.forEach(in ->{
			in.setAnotherName(ToolCategory.SWORD, "stone");
			in.setAnotherName(ToolCategory.KNIFE, "stone");
			in.setAnotherName(ToolCategory.AXE, "stone");
			in.setAnotherName(ToolCategory.STAFF, "stone");
			in.setAnotherName(ToolCategory.SPEAR, "stone");
			in.setAnotherName(ToolCategory.SHIELD, "stone");
			if(in!=meteorite){
				in.setAnotherName(ToolCategory.ACCESSORY, "stone");
			}
			in.setAnotherName(ToolCategory.HELMET, "stone");
			in.setAnotherName(ToolCategory.ARMOR, "stone");
			in.setAnotherName(ToolCategory.LEGGINS, "stone");
		});

		List<UnsagaMaterial> leathers = Lists.newArrayList(fur,snakeLeather,crocodileLeather,hydraLeather);
		leathers.forEach(in ->{
			in.setAnotherName(ToolCategory.SHIELD, "leather");
			if(hydraLeather!=in){
				in.setAnotherName(ToolCategory.BOOTS, "leather");
			}

			if(crocodileLeather!=in || hydraLeather!=in){
				in.setAnotherName(ToolCategory.ARMOR, "leather");
			}
		});

		List<UnsagaMaterial> clothes = Lists.newArrayList(cotton,silk,velvet);
		clothes.forEach(in ->{
			in.setAnotherName(ToolCategory.ARMOR, "cloth");
			in.setAnotherName(ToolCategory.LEGGINS, "cloth");
			in.setAnotherName(ToolCategory.HELMET, "cloth");
		});
//		UnsagaMaterial clothes = new UnsagaMaterial("clothes");
//		clothes.setArmorMaterial(ArmorMaterial.LEATHER);
//		this.categoryClothes.setDefaultMaterial(clothes);
//
//		UnsagaMaterial leathers = new UnsagaMaterial("leathers");
//		leathers.setArmorMaterial(ArmorMaterial.LEATHER);
//		this.categoryLeathers.setDefaultMaterial(leathers);
//
//		UnsagaMaterial debris = new UnsagaMaterial("debris");
//		debris.setToolMaterial(1,80,4.0F,1.0F,5);
//		debris.setArmorMaterial(2, new int[]{1,3,3,1}, 8);
//		this.categoryDebris.setDefaultMaterial(debris);
//
//
//		UnsagaMaterial bestial = new UnsagaMaterial("bestials");
//		bestial.setToolMaterial(1, 120, 4.0F, 1.0F, 15);
//		bestial.setArmorMaterial(6, new int[]{1,3,3,1}, 17);
//		this.categoryBestials.setDefaultMaterial(bestial);
//
//		UnsagaMaterial tusks = new UnsagaMaterial("tusks");
//		tusks.setToolMaterial(UtilUnsagaMaterial.getVanilla("tusks", ToolMaterial.STONE,-1F,170F,-1F,-1F,12F));
//		this.categoryTusks.setDefaultMaterial(tusks);
//
//		UnsagaMaterial bones = new UnsagaMaterial("bones");
//		bones.setToolMaterial(UtilUnsagaMaterial.getVanilla("bones", ToolMaterial.STONE,-1F,100F,-1F,-1F,18F));
//		bones.setArmorMaterial(ArmorMaterial.LEATHER);
//		this.categoryBones.setDefaultMaterial(bones);
//
//		UnsagaMaterial scales = new UnsagaMaterial("scales");
//		scales.setToolMaterial(UtilUnsagaMaterial.getVanilla("scales", ToolMaterial.STONE,-1F,105F,4.5F,-1F,18F));
//		scales.setArmorMaterial(UtilUnsagaMaterial.getVanilla("scales", "", ArmorMaterial.CHAIN, 12, 15, -1F));
//		this.categoryScales.setDefaultMaterial(scales);
//
//		UnsagaMaterial woods = new UnsagaMaterial("woods");
//		woods.setToolMaterial(ToolMaterial.WOOD);
//		woods.setArmorMaterial(ArmorMaterial.LEATHER);
//		this.categoryWoods.setDefaultMaterial(woods);
//
//
//		UnsagaMaterial stones = new UnsagaMaterial("stones");
//		debris.setToolMaterial(1,80,4.0F,1.0F,5);
//		debris.setArmorMaterial(2, new int[]{1,3,3,1}, 8);
//		this.categoryDebris.setDefaultMaterial(debris);
//		this.categoryRocks.setDefaultMaterial(stones);


		this.suitableLists.registerSuitableMaterials();

		this.copper.setMaterialColor(0x9E7E47);
		this.lead.setMaterialColor(0x6B717C);
		this.serpentine.setMaterialColor(0x52693B);
		this.obsidian.setMaterialColor(0x1A1A1A);
		this.lazuli.setMaterialColor(0x2632B3);
		this.damascus.setMaterialColor(0x614636);
		this.carnelian.setMaterialColor(0xE0362D);
		this.topaz.setMaterialColor(0xE7BE41);
		this.ravenite.setMaterialColor(0x2E314E);
		this.iron.setMaterialColor(0xA4A4A4);
		this.steel1.setMaterialColor(0x8B8B8B);
		this.steel2.setMaterialColor(0x777777);
		this.silver.setMaterialColor(0xDDDDDD);
		this.faerieSilver.setMaterialColor(0xDDDDDD);
		this.debris1.setMaterialColor(0x9F9F9F);
		this.debris2.setMaterialColor(0x9F9F9F);
		this.copperOre.setMaterialColor(0x675C4A);
		this.ironOre.setMaterialColor(0x6F554F);
//		stones.setMaterialColor(0x9F9F9F);
		this.meteorite.setMaterialColor(0x414144);
		this.meteoricIron.setMaterialColor(0xA4A4A4);
		this.wood.setMaterialColor(0xb98c46);
		this.oak.setMaterialColor(0xdeb068);
		this.spruce.setMaterialColor(0x8d6449);
		this.jungleWood.setMaterialColor(0x8d6449);
		this.acacia.setMaterialColor(0xcd5e3c);
		this.darkOak.setMaterialColor(0x583822);
		this.bone2.setMaterialColor(0x555555);
		this.ruby.setMaterialColor(0xe21616);
		this.sapphire.setMaterialColor(0x133ba0);
		this.faerieSilver.setMaterialColor(0x9eb2e2);
		this.snakeLeather.setMaterialColor(0x9fc487);
		this.crocodileLeather.setMaterialColor(0x72995a);
		this.hydraLeather.setMaterialColor(0xc14343);
		this.dragonScale.setMaterialColor(0x494f45);
		this.diamond.setMaterialColor(0x42f4eb);
		this.shulker.setMaterialColor(0x6b2c66);
		this.prismarine.setMaterialColor(0x46a09e);
		this.lightStone.setMaterialColor(0xc0c1e8);
		this.darkStone.setMaterialColor(0x42633f);
		this.sivaQueen.setMaterialColor(0xd144d6);
		this.dragonHeart.setMaterialColor(0x700303);

		this.initArmorMaterial();
		this.initToolMaterial();
		this.initShields();
		this.initMerchandiseSettings();

		MaterialArmorTextureSetting.register();

	}

	public List<String> getSubIconGetterNames(){
		return this.subIconGetterNames;
	}

	public List<UnsagaMaterial> getMaterialsByRank(int rank){
		return this.getProperties().stream().filter(in->in.rank==rank).collect(Collectors.toList());
	}
	public List<UnsagaMaterial> getMaterialsByRank(int min,int max){
		return this.getProperties().stream().filter(in->in.rank>=min && in.rank<=max).collect(Collectors.toList());
	}

	public List<UnsagaMaterial> getMerchandiseMaterials(int min,int max){
		return this.merchandiseMaterial.stream().filter(in->in.rank>=min && in.rank<=max).collect(Collectors.toList());
	}
	protected void setSubIconGetter(String s,UnsagaMaterial... materials){
		for(UnsagaMaterial m:materials){
			m.setSubIconGetter(s);
		}
		this.categorised.put(s, Lists.newArrayList(materials));
		this.subIconGetterNames.add(s);
	}
	protected void initMerchandiseSettings(){
		List<UnsagaMaterial> mutable = Lists.newArrayList();
		mutable.addAll(this.categories.stream().flatMap(in ->
		in.getChildMaterials().stream()).filter(in-> in != prismarine && in!=shulker).collect(Collectors.toList()));
		mutable.add(darkStone);
		mutable.add(lightStone);
		this.merchandiseMaterial = ImmutableList.copyOf(mutable);
	}



	protected void initShields(){
		this.setShieldPoints(this.categoryClothes, 1);
		this.fur.setShieldPower(10);
		this.snakeLeather.setShieldPower(10);
		this.setShieldPoints(this.categoryWoods, 10);
		this.crocodileLeather.setShieldPower(12);
		this.setShieldPoints(this.categoryDebris, 12);
		this.cypress.setShieldPower(13);
		this.setShieldPoints(this.categoryBestials, 14);
		this.oak.setShieldPower(15);
		this.toneriko.setShieldPower(15);
		this.setShieldPoints(this.categoryScales, 16);
		this.setShieldPoints(this.categoryRocks, 16);
		this.setShieldPoints(this.categoryMetals, 18);
		this.copper.setShieldPower(16);
		this.lead.setShieldPower(16);
		this.quartz.setShieldPower(18);
		this.meteorite.setShieldPower(18);
		this.ironOre.setShieldPower(18);
		this.ancientScale.setShieldPower(20);
		this.silver.setShieldPower(20);
		this.iron.setShieldPower(20);
		this.meteoricIron.setShieldPower(20);
		this.hydraLeather.setShieldPower(22);
		this.obsidian.setShieldPower(25);
		this.setShieldPoints(this.categorySteels, 25);
		this.faerieSilver.setShieldPower(25);
		this.sivaQueen.setShieldPower(25);
		this.dragonScale.setShieldPower(27);
		this.damascus.setShieldPower(30);

	}

	protected void setShieldPoints(Category cate,int p){
		for(UnsagaMaterial m:cate.getChildMaterials()){
			m.setShieldPower(p);
		}
	}
//	protected void addCategoryOriginalName(ToolCategory cate,UnsagaMaterial... materials){
//		for(UnsagaMaterial m:materials){
//			m.addCategoryUseOriginalName(cate);
//		}
//	}
	protected void initArmorMaterial(){
		diamond.setArmorMaterial(ArmorMaterial.GOLD);
		gold.setArmorMaterial(ArmorMaterial.GOLD);
		iron.setArmorMaterial(ArmorMaterial.IRON);
	}
	protected void initToolMaterial(){
		toneriko.setToolMaterial(UtilUnsagaMaterial.getVanilla("toneriko", ToolMaterial.WOOD, -1F,-1F,-1F,-1F,35F));
		silver.setToolMaterial(UtilUnsagaMaterial.getVanilla("silver", ToolMaterial.GOLD, 1F,130F,7.0F,-1F,25F));

		diamond.setToolMaterial(ToolMaterial.DIAMOND);
		gold.setToolMaterial(ToolMaterial.GOLD);
		iron.setToolMaterial(ToolMaterial.IRON);
		stone.setToolMaterial(ToolMaterial.STONE);
	}

	@Override
	public void preInit() {
		this.setSubIconGetter("allDebris", debris1,debris2);
		this.setSubIconGetter("bestials", carnelian,opal,topaz,ravenite,lazuli);
		this.setSubIconGetter("woods", wood,toneriko,cypress,oak,birch,spruce,jungleWood,darkOak,acacia);
		this.setSubIconGetter("tusks", tusk1,tusk2);
		this.setSubIconGetter("bones", bone1,bone2);
		this.setSubIconGetter("scales", ancientScale,thinScale,dragonScale,chitin,prismarine,shulker);
		this.setSubIconGetter("corundums", ruby,sapphire);
		this.setSubIconGetter("metals", lead,iron,silver,meteoricIron,faerieSilver,damascus,copper);
		this.setSubIconGetter("steels", steel1,steel2);
		this.setSubIconGetter("rocks", serpentine,quartz,copperOre,ironOre,meteorite,obsidian,diamond);
		this.setSubIconGetter("clothes", cotton,silk,velvet,liveSilk);
		this.setSubIconGetter("leathers", fur,snakeLeather,crocodileLeather,hydraLeather);

		this.registerObjects();

	}
//	protected void put(UnsagaMaterial... materials){
//		for(UnsagaMaterial mat:materials){
//			this.put(mat);
//		}
//	}

	@Override
	protected void registerObjects() {

		this.put(feather,cotton,silk,velvet,liveSilk);
		this.put(fur,snakeLeather,crocodileLeather,hydraLeather);
		this.put(wood,cypress,toneriko,oak);
		this.put(tusk1,tusk2,bone1,bone2);
		this.put(thinScale,chitin,ancientScale,dragonScale);
		this.put(lightStone,darkStone);
		this.put(debris1,debris2);
		this.put(carnelian,topaz,ravenite,opal,lazuli,serpentine);
		this.put(copper,copperOre,iron,ironOre,silver,obsidian);
		this.put(meteorite,meteoricIron);
		this.put(ruby,sapphire,obsidian,quartz);
		this.put(diamond,lead,steel1,steel2);
		this.put(faerieSilver,damascus,dragonHeart,sivaQueen);

		this.put(jungleWood,birch,spruce,acacia,darkOak);
		this.put(gold,brass,nickelSilver,chalcedony,bamboo,osmium,stone);
		this.put(prismarine,shulker);

		this.categories.add(categoryBestials);
		this.categories.add(categoryDebris);
		this.categories.add(categoryWoods);
		this.categories.add(categoryTusks);
		this.categories.add(categoryBones);
		this.categories.add(categoryScales);
		this.categories.add(categoryRocks);
		this.categories.add(categoryCorundums);
		this.categories.add(categoryMetals);
		this.categories.add(categorySteels);
		this.categories.add(categoryClothes);
		this.categories.add(categoryLeathers);

		ResourceLocation res = new ResourceLocation(UnsagaMod.MODID,"data/materials.json");

		JsonHelper<UnsagaMaterial,JsonParserMaterial> helper = new JsonHelper<UnsagaMaterial,JsonParserMaterial>(res,in -> new JsonParserMaterial(),in -> this.get(in.name));
		helper.parse();
//		try {
//			Gson gson = new Gson();
//			InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream();
//			BufferedReader  reader = new BufferedReader (new InputStreamReader(in));
//			JsonElement je = gson.fromJson(reader, JsonElement.class);
//			JsonArray ja = je.getAsJsonArray();
//			for(Iterator<JsonElement> ite=ja.iterator();ite.hasNext();){
//				JsonObject obj = ite.next().getAsJsonObject();
//				JsonParser parser = new JsonParser(obj);
//				parser.parse();
//				UnsagaMaterial m = this.get(parser.name);
//				if(m!=null){
//					m.apply(parser);
//					UnsagaMod.logger.trace(this.getClass().getName(), parser.name+" is successfully applied.");
//				}else{
//					UnsagaMod.logger.trace(this.getClass().getName(), parser.name+" is not found.");
//				}
//			}
////			JsonObject jo = ja.get(0).getAsJsonObject();
////			String name = jo.get("name").getAsString();
//
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}

	}
}
