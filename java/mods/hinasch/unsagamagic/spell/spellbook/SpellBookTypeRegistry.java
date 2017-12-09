package mods.hinasch.unsagamagic.spell.spellbook;

import mods.hinasch.lib.item.RecipeUtilNew;
import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SpellBookTypeRegistry extends PropertyRegistry<SpellBookTypeRegistry.Property>{

	private static SpellBookTypeRegistry INSTANCE;

	public static SpellBookTypeRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new SpellBookTypeRegistry();
		}
		return INSTANCE;
	}

	public Property capa1 = new Property("capa1",1,1);
	public Property capa2 = new Property("capa2",2,1);
	public Property capa3 = new Property("capa3",3,1);
	public Property capa4 = new Property("capa4",4,1);
	public Property acceleratedCapa1 = new Property("accel1.5capa1",1,1.5F);
	public Property acceleratedCapa2 = new Property("accel1.5capa2",2,1.5F);
	public Property creative = new Property("creative",4,3F);



	public static class Property extends PropertyElementBase implements Comparable<Property>{


		final float acceleration;
		final int capability;
		public Property(String name,int capability,float castSpeed) {
			super(new ResourceLocation(name), name);
			// TODO 自動生成されたコンストラクター・スタブ
			this.capability = capability;
			this.acceleration = castSpeed;
		}

		public int getCapability(){
			return this.capability;
		}

		public float getAcceleration(){
			return this.acceleration;
		}


		public ItemStack getStack(){
			ItemStack is = new ItemStack(UnsagaMagicItems.instance().spellBook,1);
			SpellBookCapability.adapter.getCapability(is).setAcceleration(this.getAcceleration());
			SpellBookCapability.adapter.getCapability(is).setCapacity(this.getCapability());
			return is;
		}
		@Override
		public Class getParentClass() {
			// TODO 自動生成されたメソッド・スタブ
			return Property.class;
		}

		@Override
		public int compareTo(Property o) {
			// TODO 自動生成されたメソッド・スタブ
			return Integer.compare(this.getCapability(), o.getCapability());
		}

	}


	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerRecipes();
	}


	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	protected void registerRecipes(){
		RawMaterialItemRegistry rawMaterials = RawMaterialItemRegistry.instance();
		RecipeUtilNew.RecipeShaped.create().setBase("oBo").addAssociation('o', "gemBestial").addAssociation('B', Items.BOOK)
		.setOutput(capa1.getStack()).register();
		RecipeUtilNew.RecipeShaped.create().setBase(" T ","oBo").addAssociation('o', "gemBestial").addAssociation('B', Items.BOOK)
		.addAssociation('T', rawMaterials.fraxinus.getItemStack(1)).setOutput(capa2.getStack()).register();
		RecipeUtilNew.RecipeShaped.create().setBase(" T ","oBo"," T ").addAssociation('o', "gemBestial").addAssociation('B', Items.BOOK)
		.addAssociation('T', rawMaterials.fraxinus.getItemStack(1)).setOutput(capa3.getStack()).register();
		RecipeUtilNew.RecipeShaped.create().setBase("STS","oBo","STS").addAssociation('o', "gemBestial").addAssociation('B', Items.BOOK)
		.addAssociation('T', rawMaterials.fraxinus.getItemStack(1)).addAssociation('S', rawMaterials.ancientFishScale.getItemStack(1)).setOutput(capa4.getStack()).register();

	}


	@Override
	protected void registerObjects() {
		// TODO 自動生成されたメソッド・スタブ
		this.put(capa1);
		this.put(capa2);
		this.put(capa3);
		this.put(capa4);
		this.put(acceleratedCapa1);
		this.put(acceleratedCapa2);
		this.put(creative);
	}
}
