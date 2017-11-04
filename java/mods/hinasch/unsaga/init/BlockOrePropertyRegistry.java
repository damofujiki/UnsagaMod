package mods.hinasch.unsaga.init;

import mods.hinasch.lib.block.BlockOreProperty;
import mods.hinasch.lib.registry.PropertyRegistryOre;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import net.minecraft.block.Block;

public class BlockOrePropertyRegistry extends PropertyRegistryOre<BlockOrePropertyRegistry.Property>{
	//Old Reigstry
//	public List<String> oreDictionaryList = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
//	public List<String> unlocalizedNames = Lists.newArrayList("oreLead","oreRuby","oreSapphire","oreSilver","oreCopper","oreAngelite","oreDemonite");
//	public List<Float> exps = Lists.newArrayList(0.7F,1.0F,1.0F,0.7F,0.7F,1.0F,1.0F);
//	public List<Integer> smelted = Lists.newArrayList(5,6,7,4,3,16,17);
//	//public static List<String> localizedNameJP = Lists.newArrayList("鉛鉱石","鋼玉鉱石","鋼玉鉱石","銀鉱石","銅鉱石","聖石鉱石","魔石鉱石");
//	public List<Integer> harvestLevel = Lists.newArrayList(1,2,2,1,1,1,1);
//	//public static List<String> localizedNameEN = Lists.newArrayList("Lead Ore","Corundum Ore","Corundum Ore","Silver Ore","Copper Ore","Angelite Ore","Demonite Ore");
//	public List<Integer> containerItem = Lists.newArrayList(-1,6,7,-1,-1,16,17);

	//New Reigstry

	protected static BlockOrePropertyRegistry INSTANCE;

	public static BlockOrePropertyRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new BlockOrePropertyRegistry();
		}
		return INSTANCE;
	}
	public final Property lead,ruby,sapphire,silver,copper,angelite,demonite;
//	protected RegistryNamespaced<ResourceLocation,OreProperty> oreMap;
//
//	public static final int LEAD = 0;
//	public static final int RUBY = 1;
//	public static final int SAPPHIRE = 2;
//	public static final int SILVER = 3;
//	public static final int COPPER = 4;
//	public static final int ANGELITE = 5;
//	public static final int DEMONITE = 6;

	public BlockOrePropertyRegistry(){
		this.lead = new Property(0,"oreLead","oreLead",0.7F,1);
		this.ruby = new Property(1,"oreRuby","oreRuby",1.0F,2);
		this.sapphire = new Property(2,"oreSapphire","oreSapphire",1.0F,2);
		this.silver = new Property(3,"oreSilver","oreSilver",0.7F,1);
		this.copper = new Property(4,"oreCopper","oreCopper",0.7F,0);
		this.angelite = new Property(5,"oreAngelite","oreAngelite",1.0F,1);
		this.demonite = new Property(6,"oreDemonite","oreDemonite",1.0F,1);

	}
	@Override
	protected void registerObjects() {
		this.put(lead);
		this.put(ruby);
		this.put(sapphire);
		this.put(silver);
		this.put(copper);
		this.put(angelite);
		this.put(demonite);
	}
//	public void setSmelted(){
//		this.lead.setSmelted(UnsagaMod.miscItems.leadIngot);
//		this.ruby.setSmeltedAndInside(UnsagaMod.miscItems.corundumRuby);
//		this.sapphire.setSmeltedAndInside(UnsagaMod.miscItems.corundumSapphire);
//		this.silver.setSmelted(UnsagaMod.miscItems.silverIngot);
//		this.copper.setSmelted(UnsagaMod.miscItems.copperIngot);
//		this.angelite.setSmeltedAndInside(UnsagaMod.miscItems.angelite);
//		this.demonite.setSmeltedAndInside(UnsagaMod.miscItems.demonite);
//		this.registerSmeltingAndAssociation();
//	}


//
//	public OreProperty getData(int number){
//		return this.oreMap.getObjectById(number);
//	}
//
//	public Iterator<OreProperty> getOreProperties(){
//		return this.oreMap.iterator();
//	}

	@Deprecated
	private void registerSmeltingAndAssociation(){



//		this.registerSmelted();
//		this.registerOreDicts();

//		BlockOreBase.registerSmeltsAndDicts(this.oreMap.iterator(), in -> {
//			OreProperty p = (OreProperty) in;
//			return UnsagaMod.blocks.ores.getObject(p.getId());
//		});
//		for(int i=0;i<UnsagaMod.blocks.ores.getKeys().size();i++){
//			//ItemStack blockitem = new ItemStack(UnsagaBlocks.blocksOreUnsaga[i],1,i);
//			//ItemStack smeltedItemStack = Unsaga.noFunctionItems.getItemStack(1, smelted.get(i));
////			Unsaga.debug(this.oreMap.get(i));
////			Unsaga.debug(this.oreMap.get(i),this.oreMap.get(i).getSmelted());
////			Unsaga.debug(this.oreMap.get(i),this.oreMap.get(i).getSmelted().getItemStack(1));
//			ItemStack smelted = this.oreMap.getObjectById(i).getSmelted().get().getItemStack(1);
//			float exp = this.oreMap.getObjectById(i).getExp();
//			FurnaceRecipes.instance().addSmeltingRecipeForBlock(UnsagaMod.blocks.ores.getObject(i),smelted, exp);
//			OreDictionary.registerOre(oreMap.getObjectById(i).getOreDictID(),UnsagaMod.blocks.ores.getObject(i));
//		}

//		UnsagaMaterials.materialToItem.putObject(UnsagaMod.materials.copperOre, PairItem.of(this.copper.getBlockAsItem(),0));
//		Unsaga.materials.copperOre.associate(this.copper.getItemStack(1));

	}

	public static Property getOreData(int num){
		return BlockOrePropertyRegistry.instance().getObjectById(num);
	}
//	public static class OreProperty extends NameAndNumberAndID<ResourceLocation> implements IOreProperty{
//
//		public final String oreDictID;
//
//		public String getOreDictID() {
//			return oreDictID;
//		}
//
//		public final float exp;
//		public float getExp() {
//			return exp;
//		}
//
//		private MiscItem smelted;
//		private MiscItem insideItem;
//		public final int harvestLevel;
//		int id;
//
//
//
//		public OreProperty(int number,String name,String id,float exp,int harvestLevel){
//			super(new ResourceLocation(name),name, number);
//
//			this.oreDictID = id;
//			this.exp = exp;
//			this.harvestLevel = harvestLevel;
//		}
//
//		public OreProperty setSmeltedAndInside(MiscItem data){
//			this.setSmelted(data);
//			this.setInside(data);
//			return this;
//		}
//		public OreProperty setSmelted(MiscItem data){
//			this.smelted = data;
//			return this;
//		}
//
//		public OreProperty setInside(MiscItem data){
//			this.insideItem = data;
//			return this;
//		}
//
//		public Optional<MiscItem> getSmelted(){
//			return this.smelted!=null ? Optional.of(this.smelted) : Optional.absent();
//		}
//		public Optional<MiscItem> getInsideItemData(){
//
//			if(this.insideItem==null){
//				return Optional.absent();
//			}
//			return Optional.of(this.insideItem);
//		}
//
//		public OreProperty addTo(RegistryNamespaced<ResourceLocation,OreProperty> map){
//			map.register(this.getId(),this.getKey(),this);
//			return this;
//		}
//
//		public ItemStack getItemStack(int amount){
//			return new ItemStack(UnsagaMod.blocks.ores.getObject(this.getId()),amount);
//		}
//
//		public Item getItemBlock(){
//			return Item.getItemFromBlock(this.getBlock());
//		}
//
//		public Block getBlock(){
//			return UnsagaMod.blocks.ores.getObject(this.getId());
//		}
//
//		@Override
//		public Class getParentClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.getClass();
//		}
//
//		@Override
//		public int getHarvestLevel() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.harvestLevel;
//		}
//
//		@Override
//		public Item getItem() {
//			// TODO 自動生成されたメソッド・スタブ
//			return UnsagaMod.items.misc;
//		}
//
//	}

	public static class Property extends BlockOreProperty{

		String oreDictID;
		public Property(int id, String name,String oreDictID, float exp, int harvest) {
			super(id, name, exp, harvest);
			this.oreDictID = oreDictID;
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public Block getBlock() {
			// TODO 自動生成されたメソッド・スタブ
			return UnsagaBlockRegistry.instance().ores.getObject(this.getId());
		}

		@Override
		public String getOreDict() {
			// TODO 自動生成されたメソッド・スタブ
			return this.oreDictID;
		}

	}

	@Override
	public void initSmelted() {
		this.lead.setSmelted(RawMaterialItemRegistry.instance().lead);
		this.ruby.setSmeltedAndInside(RawMaterialItemRegistry.instance().ruby);
		this.sapphire.setSmeltedAndInside(RawMaterialItemRegistry.instance().sapphire);
		this.silver.setSmelted(RawMaterialItemRegistry.instance().silver);
		this.copper.setSmelted(RawMaterialItemRegistry.instance().copper);
		this.angelite.setSmeltedAndInside(RawMaterialItemRegistry.instance().lightStone);
		this.demonite.setSmeltedAndInside(RawMaterialItemRegistry.instance().darkStone);
	}

	@Override
	public void initOreDicts() {
		// TODO 自動生成されたメソッド・スタブ

	}
	@Override
	public void preInit() {
		this.registerObjects();

	}
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerSmeltedAndOreDicts();

	}


}
