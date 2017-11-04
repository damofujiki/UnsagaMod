package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.block.BlockOreBase;
import mods.hinasch.lib.block.BlockOreProperty;
import mods.hinasch.lib.registry.PropertyRegistryOre;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import net.minecraft.block.Block;

public class OreBlockPropertyGroup extends PropertyRegistryOre<OreBlockPropertyGroup.Property>{


	public static final Property ORE_BISMUTH = new Property(0,"oreBismuth",0.7F,BlockOreBase.HARVEST_BY_STONE);
	public static final Property ORE_CINNABAR = new Property(1,"oreCinnabar",0.7F,BlockOreBase.HARVEST_BY_IRON);
	public static final Property ROCKSALT = new Property(2,"rockSalt",0.0F,BlockOreBase.HARVEST_BY_STONE);

	public void initInsideItem(){
		this.ORE_CINNABAR.setInsideItem(UnsagaModAdditional.miscItems.CRYSTAL_CINNABAR);
	}


	public static class Property extends BlockOreProperty{

		public Property(int id, String name, float exp, int harvest) {
			super(id, name, exp, harvest);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public String getOreDict() {
			// TODO 自動生成されたメソッド・スタブ
			return this.getName();
		}

		@Override
		public Block getBlock() {
			// TODO 自動生成されたメソッド・スタブ
			return UnsagaModAdditional.blocks.ores.getObject(this.getKey());
		}

	}
//	public static class OreProperty extends NameAndNumberAndID<ResourceLocation> implements IOreProperty {
//
//
//		public OreProperty(int id,String name,float exp,int harvest) {
//			super(new ResourceLocation(name), name, id);
//			this.exp = exp;
//			this.harvestLevel = harvest;
//			this.dictID = name;
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//
//		String dictID;
//		final float exp;
//		final int harvestLevel;
//		Supplier<Item> insideItemSupplier = () -> UnsagaModAdditional.items.misc;
//		MiscItemPropertyGroup.MiscItem insideStack;
//		MiscItemPropertyGroup.MiscItem smeltedStack;
//
//		public void setInsideItem(MiscItemPropertyGroup.MiscItem data){
//			this.insideStack = data;
//		}
//
//		@Override
//		public Optional<MiscItemPropertyGroup.MiscItem> getInsideItemData() {
//			// TODO 自動生成されたメソッド・スタブ
//			return insideStack!=null ? Optional.of(this.insideStack) : Optional.absent();
//		}
//
//		@Override
//		public int getHarvestLevel() {
//			// TODO 自動生成されたメソッド・スタブ
//			return harvestLevel;
//		}
//
//		@Override
//		public Item getItem() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.insideItemSupplier.get();
//		}
//
//		@Override
//		public float getExp() {
//			// TODO 自動生成されたメソッド・スタブ
//			return exp;
//		}
//
//		@Override
//		public Class getParentClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return OreProperty.class;
//		}
//
//		@Override
//		public Optional<MiscItemPropertyGroup.MiscItem> getSmelted() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.smeltedStack!=null ? Optional.of(this.smeltedStack) : Optional.absent();
//		}
//
//		@Override
//		public String getOreDictID() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.dictID;
//		}
//
//	}

	@Override
	public void initOreDicts() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void registerObjects() {
		this.initInsideItem();
		this.put(ORE_BISMUTH);
		this.put(ORE_CINNABAR);
		this.put(ROCKSALT);
	}

	@Override
	public void initSmelted() {
		MiscItemPropertyGroup miscs = UnsagaModAdditional.miscItems;
		ORE_BISMUTH.setSmelted(miscs.INGOT_BISMUTH);
		ORE_CINNABAR.setSmelted(miscs.CRYSTAL_CINNABAR);
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerSmeltedAndOreDicts();
	}
}
