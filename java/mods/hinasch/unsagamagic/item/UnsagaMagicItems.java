package mods.hinasch.unsagamagic.item;

import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.UnsagaMagic;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class UnsagaMagicItems extends BlockItemRegistry<Item>{

	private static UnsagaMagicItems INSTANCE;
	public static UnsagaMagicItems instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaMagicItems();
		}
		return INSTANCE;
	}
	protected UnsagaMagicItems() {
		super(UnsagaMod.MODID);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Item tablet;
	public Item spellBook;
	public Item iconSpell;
	public Item knowledgeBook;
	public Item spellBookBinder;

	public void register(){
		CreativeTabs tab = UnsagaMagic.instance().tabMagic;
		this.setUnlocalizedNamePrefix("unsaga");
		this.tablet = put(new ItemTablet(),"magicTablet",tab);
		this.spellBook = put(new ItemSpellBook(),"spellBook",tab);
		this.iconSpell = put(new ItemIconSpellNew(),"spellIcon",null);
//		this.knowledgeBook = put(new ItemKnowledgeBook(),"knowledgeBook",tab);
		//バグありのためタブに入れない
//		this.spellBookBinder = put(new ItemSpellBookBinder(),"spellBookBinder",null);
	}


//	private Item put(Item item,String name){
//		ResourceLocation res = new ResourceLocation(UnsagaMod.MODID,name);
//		if(!(item instanceof IIconItem)){
//			item.setCreativeTab(UnsagaMagic.instance().tabMagic);
//		}
//		item.setUnlocalizedName("unsaga."+name);
//		this.registryObjects.put(res, item);
//		GameRegistry.register(item.setRegistryName(name));
//		return this.registryObjects.get(res);
//	}
}
