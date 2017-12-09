package mods.hinasch.unsaga.material;

import java.util.List;
import java.util.stream.Collectors;

import mods.hinasch.lib.client.ClientHelper.IIconName;
import mods.hinasch.lib.item.ItemProperty;
import mods.hinasch.lib.item.PropertyRegistryItem;
import mods.hinasch.lib.item.RecipeUtilNew;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry.RawMaterial;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RawMaterialItemRegistry extends PropertyRegistryItem<RawMaterial>{

	protected static RawMaterialItemRegistry INSTANCE;

	public RawMaterial cotton,silk,liveSilk,velvet,fur,snakeLeather,hydraLeather;
	public RawMaterial cypress,oak,fraxinus,tusk1,tusk2,bone,thinScale;
	public RawMaterial chitin,ancientFishScale,dragonScale,crocodileLeather,lightStone,darkStone;
	public RawMaterial debris1,debris2,carnelian,ravenite,opal,topaz,lapis;
	public RawMaterial meteorite,silver,ruby,sapphire,copper,lead,meteoricIron;
	public RawMaterial steel1,steel2,faerieSilver,damascus,dragonHeart;
	public RawMaterial jungleWood,birch,darkOak,spruce,acacia;
	public static RawMaterialItemRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new RawMaterialItemRegistry();
		}
		return INSTANCE;
	}

	protected RawMaterialItemRegistry(){

	}
	UnsagaMaterials m = UnsagaMod.core.materialsNew;
	@Override
	public void init() {
		this.associateToMaterial();
		this.registerOreDicts();
	}

	@Override
	public void preInit() {
		this.registerObjects();

	}

	public RawMaterial getPropertyFromStack(ItemStack is){
		int damage = is.getItemDamage();
		return this.getObjectById(damage);
	}

	public ItemStack getStackFromProperty(RawMaterial prop){
		int damage = prop.getMeta();
		return new ItemStack(UnsagaMod.core.itemsNew.rawMaterials,1,damage);
	}
	public List<String> getIconNames(){
		return this.getProperties().stream().map(in -> in.getIconName()).collect(Collectors.toList());
	}


	protected RawMaterial put(ItemProperty par1){
		return super.put((RawMaterial) par1);
	}

	@Override
	protected void registerObjects() {
		// TODO 自動生成されたメソッド・スタブ
		cotton = this.put(new RawMaterial(1,"cotton",m.cotton).setOreDictID("cloth").setIconName("cloth"));
		silk = this.put(new RawMaterial(2,"silk",m.silk).setOreDictID("cloth").setIconName("cloth"));
		velvet = this.put(new RawMaterial(3,"velvet",m.velvet).setOreDictID("cloth").setIconName("cloth"));
		liveSilk = this.put(new RawMaterial(4,"liveSilk",m.liveSilk).setOreDictID("cloth").setIconName("cloth"));
		fur = this.put(new RawMaterial(5,"fur",m.fur));
		snakeLeather = this.put(new RawMaterial(6,"snakeLeather",m.snakeLeather).setOreDictID("leather"));
		hydraLeather = this.put(new RawMaterial(7,"hydraLeather",m.hydraLeather).setOreDictID("leather"));
		crocodileLeather = this.put(new RawMaterial(8,"crocodileLeather",m.crocodileLeather).setOreDictID("leather"));
		cypress = this.put(new RawMaterial(9,"cypress",m.cypress).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		oak = this.put(new RawMaterial(10,"oak",m.oak).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		fraxinus = this.put(new RawMaterial(11,"fraxinus",m.toneriko).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		tusk1 = this.put(new RawMaterial(12,"tusk1","tusk",m.tusk1));
		tusk2 = this.put(new RawMaterial(13,"tusk2","tusk",m.tusk2));
		bone = this.put(new RawMaterial(14,"bone",m.bone2).setOreDictID("bone"));
		thinScale = this.put(new RawMaterial(15,"thinScale",m.thinScale));
		chitin = this.put(new RawMaterial(16,"chitin",m.chitin));
		ancientFishScale = this.put(new RawMaterial(17,"ancientFishScale",m.ancientScale));
		dragonScale = this.put(new RawMaterial(18,"dragonScale",m.dragonScale));
		lightStone = this.put(new RawMaterial(19,"lightStone",m.lightStone).setOreDictID("gemLightStone"));
		darkStone = this.put(new RawMaterial(20,"darkStone",m.darkStone).setOreDictID("gemDarkStone"));
		debris1 = this.put(new RawMaterial(21,"debris1","debris",m.debris1).setOreDictID("debris")).setAmount(0.125F);
		debris2 = this.put(new RawMaterial(22,"debris2","debris",m.debris2).setOreDictID("debris")).setAmount(0.125F);
		carnelian = this.put(new RawMaterial(23,"carnelian",m.carnelian).setOreDictID("gemCarnelian"));
		topaz = this.put(new RawMaterial(24,"topaz",m.topaz).setOreDictID("gemTopaz"));
		opal = this.put(new RawMaterial(25,"opal",m.opal).setOreDictID("gemOpal"));
		ravenite = this.put(new RawMaterial(26,"ravenite",m.ravenite).setOreDictID("gemRavenite"));
		lapis = this.put(new RawMaterial(27,"lapis",m.lazuli).setOreDictID("gemLapis"));
		meteorite = this.put(new RawMaterial(28,"meteorite",m.meteorite).setOreDictID("stoneMeteorite"));
		silver = this.put(new RawMaterial(29,"silver",m.silver).setOreDictID("ingotSilver"));
		ruby = this.put(new RawMaterial(30,"ruby",m.ruby).setOreDictID("gemRuby"));
		sapphire = this.put(new RawMaterial(31,"sapphire",m.sapphire).setOreDictID("gemSapphire"));
		copper = this.put(new RawMaterial(32,"copper",m.copper).setOreDictID("ingotCopper"));
		lead = this.put(new RawMaterial(33,"lead",m.lead).setOreDictID("ingotLead"));
		meteoricIron = this.put(new RawMaterial(34,"meteoricIron",m.meteoricIron).setOreDictID("ingotMeteoricIron"));
		steel1 = this.put(new RawMaterial(35,"steel1","steel",m.steel1).setOreDictID("ingotSteel"));
		steel2 = this.put(new RawMaterial(36,"steel2","steel",m.steel2).setOreDictID("ingotSteel"));
		faerieSilver = this.put(new RawMaterial(37,"faerieSilver",m.faerieSilver).setOreDictID("ingotFaerieSilver"));
		damascus = this.put(new RawMaterial(38,"damascus",m.damascus).setOreDictID("ingotDamascus"));
		dragonHeart = this.put(new RawMaterial(39,"dragonHeart",m.dragonHeart)).setAmount(1.0F);
		jungleWood = this.put(new RawMaterial(40,"jungleWood",m.jungleWood).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		birch = this.put(new RawMaterial(41,"birch",m.birch).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		spruce = this.put(new RawMaterial(42,"spruce",m.spruce).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		acacia = this.put(new RawMaterial(43,"acacia",m.acacia).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
		darkOak = this.put(new RawMaterial(44,"darkOak",m.darkOak).setItemColored(true).setIconName("woodpile").setOreDictID("woodpile"));
	}

	protected void associateToMaterial(){
		this.getProperties().forEach(in ->{
			ItemStack is = new ItemStack(UnsagaItemRegistry.instance().rawMaterials,1,in.getId());
			MaterialItemAssociatedRegistry.instance().registerAssociation(in.getAssociatedMaterial(), is);
			MaterialItemAssociatedRegistry.instance().materialRepairAmountMap.put(is,in.getAmount());
		});
	}
	protected void registerOreDicts(){
		this.getProperties().forEach(in -> {
			if(in.getOreDictID().isPresent()){
//				UnsagaMod.logger.trace(this.getClass().getName(), this.getStackFromProperty(in));
				OreDictionary.registerOre(in.getOreDictID().get(), getStackFromProperty(in));
			}
		});

		OreDictionary.registerOre("gemBestial", this.carnelian.getItemStack(1));
		OreDictionary.registerOre("gemBestial", this.lapis.getItemStack(1));
		OreDictionary.registerOre("gemBestial", this.opal.getItemStack(1));
		OreDictionary.registerOre("gemBestial", this.topaz.getItemStack(1));
		OreDictionary.registerOre("gemBestial", this.ravenite.getItemStack(1));

		RecipeUtilNew.RecipeShaped.create().setBase("###","PPP").addAssociation('#', "cloth")
		.addAssociation('P', new ItemStack(Blocks.PLANKS,1,OreDictionary.WILDCARD_VALUE)).setOutput(new ItemStack(Items.BED,1))
		.register();

		RecipeUtilNew.RecipeShaped.create().setBase("P").addAssociation('P',"woodpile").setOutput(new ItemStack(Items.STICK,4))
		.register();

		RecipeUtilNew.RecipeShaped.create().setBase("C").addAssociation('C',this.getStackFromProperty(silk)).setOutput(new ItemStack(Items.STRING,4))
		.register();

		RecipeUtilNew.RecipeShaped.create().setBase("SS","SS").addAssociation('S', "debris").setOutput(new ItemStack(Blocks.COBBLESTONE,1))
		.register();

		this.getProperties().forEach(in ->{

			if(in.getOreDictID().isPresent() && in.getOreDictID().get().equals("woodpile")){
				UnsagaMod.core.fuelHandler.addFuel(this.getStackFromProperty(in), 80, true);
			}
		});
	}
	public static class RawMaterial extends ItemProperty implements IIconName{

		final UnsagaMaterial m;
		boolean isItemColor = false;
		String iconname;
		float amount = 0.5F;
		public RawMaterial(int id, String name,UnsagaMaterial m) {
			super(id, name);
			this.m = m;
			this.iconname = name;

			// TODO 自動生成されたコンストラクター・スタブ
		}

		public RawMaterial(int id, String name,String unlname,UnsagaMaterial m) {
			super(id, name,unlname);
			this.m = m;
			this.iconname = unlname;
			// TODO 自動生成されたコンストラクター・スタブ
		}


		public RawMaterial setItemColored(boolean par1){
			this.isItemColor = par1;
			return this;
		}

		public boolean isItemColored(){
			return this.isItemColor;
		}


		/** ツール生成時の耐久力の割合。初期値は0.5（半分）*/
		public float getAmount(){
			return this.amount;
		}

		public RawMaterial setAmount(float par1){
			this.amount = par1;
			return this;
		}
		public UnsagaMaterial getAssociatedMaterial(){
			return this.m;
		}
		@Override
		public Item getItem() {
			// TODO 自動生成されたメソッド・スタブ
			return UnsagaMod.core.itemsNew.rawMaterials;
		}

	}

}
