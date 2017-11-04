package mods.hinasch.unsaga.element;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import mods.hinasch.lib.misc.ComparableMap;
import mods.hinasch.lib.misc.Quartet;
import mods.hinasch.lib.misc.Triplet;
import mods.hinasch.unsaga.element.newele.ElementTable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class ElementPointLibrary {


	public static class ElementProvider implements Comparable<Triplet<Block,Material,BiomeDictionary.Type>>{

		Quartet<Block,Material,BiomeDictionary.Type,Class> elementChecker;

		public ElementProvider(Quartet<Block, Material, Type, Class> elementProvider) {
			super();
			this.elementChecker = elementProvider;
		}



		public Object getElementProvider() {
			return elementChecker;
		}



		@Override
		public int compareTo(Triplet<Block,Material,BiomeDictionary.Type> in) {
			if(in.first!=null){
				if(this.elementChecker.first!=null){
					if(in.first == this.elementChecker.first){
						return 0;
					}

				}
				if(this.elementChecker.fourth()!=null){
					if(this.elementChecker.fourth().isInstance(in.first)){
						return 0;
					}
				}
			}
			if(in.second!=null){
				if(this.elementChecker.second!=null){
					if(in.second==this.elementChecker.second){
						return 0;
					}
				}
				if(this.elementChecker.fourth()!=null){
					if(this.elementChecker.fourth().isInstance(in.second)){
						return 0;
					}
				}
			}
			if(in.third()!=null){
				if(this.elementChecker.third()!=null){
					if(in.third()==this.elementChecker.third()){
						return 0;
					}

				}
			}

			return -1;
		}



	}

	ComparableMap<ElementProvider,ElementTable,Triplet<Block,Material,BiomeDictionary.Type>> map = new ComparableMap();


	public ElementPointLibrary(){



		put(Material.WATER, new ElementTable(-0.05F,0,0,0.5F,0,0));
		put(Material.SNOW, new ElementTable(-0.04F,0,0,0.5F,0,0));
		put(Material.ICE, new ElementTable(-0.04F,0,0,0.5F,0,0));
		put(Material.CRAFTED_SNOW, new ElementTable(-0.04F,0,0,0.8F,0,0));
		put(Material.GRASS, new ElementTable(FiveElements.Type.WOOD,0.1F));
		put(Material.WOOD, new ElementTable(FiveElements.Type.WOOD,0.05F));
		put(Material.PLANTS, new ElementTable(FiveElements.Type.WOOD,0.02F));
		put(Material.LAVA, new ElementTable(FiveElements.Type.FIRE,0.08F));
		put(Material.LEAVES, new ElementTable(FiveElements.Type.WOOD,0.01F));
		put(Material.IRON, new ElementTable(FiveElements.Type.METAL,1.0F));

		put(Blocks.LAVA, new ElementTable(FiveElements.Type.FIRE,1.0F));
		put(Blocks.FLOWING_LAVA, new ElementTable(FiveElements.Type.FIRE,0.5F));
		put(Blocks.FIRE, new ElementTable(FiveElements.Type.FIRE,1.0F));
		put(Blocks.FLOWING_WATER, new ElementTable(-0.04F,0,0,0.5F,0,0));
		put(Blocks.WATER, new ElementTable(-0.04F,0,0,0.8F,0,0));
		put(Blocks.LOG, new ElementTable(FiveElements.Type.WOOD,0.1F));
		put(Blocks.LOG2, new ElementTable(FiveElements.Type.WOOD,0.1F));
		put(Blocks.PLANKS, new ElementTable(FiveElements.Type.WOOD,0.1F));
		put(Blocks.NETHERRACK, new ElementTable(FiveElements.Type.FORBIDDEN,0.01F));
		put(Blocks.NETHER_BRICK, new ElementTable(FiveElements.Type.FORBIDDEN,0.05F));
		put(Blocks.END_STONE, new ElementTable(FiveElements.Type.FORBIDDEN,0.1F));
		put(Blocks.MAGMA, new ElementTable(1.0F,0,0,0,0,0.5F));
		put(Blocks.STONE, new ElementTable(FiveElements.Type.EARTH,0.005F));
		put(Blocks.COBBLESTONE, new ElementTable(FiveElements.Type.EARTH,0.01F));
		put(BlockOre.class, new ElementTable(FiveElements.Type.METAL,0.2F));

		put(Type.HOT,new ElementTable(FiveElements.Type.FIRE,10));
		put(Type.BEACH,new ElementTable(FiveElements.Type.WATER,10));
		put(Type.DRY,new ElementTable(FiveElements.Type.WATER,-5));
		put(Type.WET,new ElementTable(FiveElements.Type.WATER,5));
		put(Type.SANDY,new ElementTable(FiveElements.Type.WATER,-5));
		put(Type.FOREST,new ElementTable(FiveElements.Type.WOOD,10));
		put(Type.SNOWY,new ElementTable(FiveElements.Type.WATER,10));
		put(Type.MAGICAL,new ElementTable(FiveElements.Type.FORBIDDEN,10));
		put(Type.WASTELAND,new ElementTable(FiveElements.Type.EARTH,20));
		put(Type.HILLS,new ElementTable(FiveElements.Type.EARTH,5));
		put(Type.MOUNTAIN,new ElementTable(FiveElements.Type.EARTH,10));
		put(Type.MESA,new ElementTable(FiveElements.Type.EARTH,12));
		put(Type.JUNGLE,new ElementTable(FiveElements.Type.WOOD,15));
		put(Type.SWAMP,new ElementTable(0,5.0F,0,5.0F,0,0));
		put(Type.CONIFEROUS,new ElementTable(FiveElements.Type.FORBIDDEN,30));
		put(Type.NETHER,new ElementTable(FiveElements.Type.FORBIDDEN,40));


	}


	public Optional<ElementTable> find(Triplet<Block,Material,BiomeDictionary.Type> triplet){

		return this.map.getFirst(triplet);
	}

	public Optional<ElementTable> find(IBlockState state){
//		if(UnsagaMod.plugin.isLoadedHAC()){
//			Block block = state.getBlock();
//			int meta = block.getMetaFromState(state);
//			if(UnsagaPluginHAC.getElementsTable(block, meta)!=null){
//				return Optional.of(UnsagaPluginHAC.getElementsTable(block, meta));
//			}
//		}
		return this.find(Triplet.of(state.getBlock(), state.getMaterial(), (Type)null));
	}

	public Optional<ElementTable> find(Type type){
		return this.find(Triplet.of((Block)null, (Material)null,type));
	}
	public void put(Material obj,ElementTable table){
		this.map.put(new ElementProvider(Quartet.of((Block)null,obj,(Type)null,(Class)null)), table);
	}

	public void put(Block obj,ElementTable table){
		this.map.put(new ElementProvider(new Quartet(obj,null,null,null)), table);
	}

	public void put(Type obj,ElementTable table){
		this.map.put(new ElementProvider(new Quartet(null,null,obj,null)), table);
	}
	public void put(Class obj,ElementTable table){
		this.map.put(new ElementProvider(new Quartet(null,null,null,obj)), table);
	}
	public void put(Predicate obj,ElementTable table){
		this.map.put(new ElementProvider(new Quartet(null,null,null,obj)), table);
	}
//	@Override
//	public Optional<LibraryBook> find(Object object){
//		LibraryBook returnbook = null;
//		if(object instanceof Block){
//			Block block = (Block)object;
//			Material material = block.getMaterial();
//			for(LibraryBook book:libSet){
//				ElementLibraryBook bookelement = (ElementLibraryBook)book;
//				if(bookelement.childkey==bookelement.MATERIAL && material==bookelement.material){
//					returnbook = bookelement;
//				}
//				if(bookelement.childkey==bookelement.BLOCK && bookelement.block==block){
//					returnbook = bookelement;
//				}
//				if(bookelement.childkey==bookelement._CLASS && sameOrInstanceOf(bookelement._class,block.getClass())){
//					returnbook = bookelement;
//				}
//
//			}
//
//		}
//		if(object instanceof BiomeDictionary.Type){
//			BiomeDictionary.Type type = (BiomeDictionary.Type)object;
//			for(LibraryBook book:libSet){
//				ElementLibraryBook bookelement = (ElementLibraryBook)book;
//				if(bookelement.childkey==bookelement.BIOMETYPE && bookelement.biomeType==type){
//					returnbook = bookelement;
//				}
//			}
//		}
//		if(returnbook!=null){
//			return Optional.of(returnbook);
//		}
//		return Optional.absent();
//		//return super.find(object);
//	}
//
//	public boolean sameOrInstanceOf(Class class1,Class class2){
//		if(class1.isInstance(class2)){
//			return true;
//		}
//		if(class2.isInstance(class1)){
//			return true;
//		}
//		if(class1==class2){
//			return true;
//		}
//		return false;
//	}


}
