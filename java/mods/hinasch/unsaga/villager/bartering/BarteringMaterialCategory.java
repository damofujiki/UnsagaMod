package mods.hinasch.unsaga.villager.bartering;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;

public class BarteringMaterialCategory {

	public static enum Type{
		FEATHER("feather"),CLOTH("cloth"),LEATHER("leather"),WOOD("wood"),BONE("bone")
		,SCALE("scale"),JEWEL_MAGICAL("preciousJewel"),
		ORE("ore"),BESTIAL("bestial"),JEWEL("jewel"),METAL("metal"),METAL_PRECIOUS("preciousMetal"),RARE("rare"),UNKNOWN("unknown");

		final String name;
		private Type(String name){
			this.name = name;
		}

		public String getName(){
			return this.name;
		}

		public String getLocalized(){
			return HSLibs.translateKey("gui.unsaga.bartering.shopType."+this.getName());
		}
		public Set<UnsagaMaterial> getMaterials(){
			return BarteringMaterialCategory.instance().getMaterialsFromType(this);
		}
	}

	public final EnumSet<Type> merchandises = EnumSet.of(Type.FEATHER,Type.CLOTH,Type.LEATHER
			,Type.WOOD,Type.BONE,Type.SCALE,Type.JEWEL,Type.JEWEL_MAGICAL
			,Type.ORE,Type.BESTIAL,Type.JEWEL,Type.METAL,Type.METAL_PRECIOUS);
	private static BarteringMaterialCategory INSTANCE;

	public static BarteringMaterialCategory instance(){
		if(INSTANCE == null){
			INSTANCE = new BarteringMaterialCategory();
		}
		return INSTANCE;
	}
	protected BarteringMaterialCategory(){

	}
	Map<UnsagaMaterial,Type> merchandiseMaterialCategory = Maps.newHashMap();
	UnsagaMaterials m = UnsagaMod.core.materialsNew;
	public void init(){
		this.add(Type.FEATHER, m.feather);
		this.add(Type.CLOTH, m.cotton,m.silk,m.velvet,m.liveSilk);
		this.add(Type.LEATHER, m.fur,m.snakeLeather,m.crocodileLeather,m.hydraLeather);
		this.add(Type.WOOD, m.wood,m.cypress,m.oak,m.toneriko,m.acacia,m.birch,m.spruce,m.darkOak,m.jungleWood);
		this.add(Type.BONE, m.tusk1,m.tusk2,m.bone1,m.bone2);
		this.add(Type.SCALE, m.thinScale,m.chitin,m.ancientScale,m.dragonScale);
		this.add(Type.JEWEL_MAGICAL, m.darkStone,m.lightStone);
		this.add(Type.ORE, m.debris1,m.debris2);
		this.add(Type.BESTIAL, m.carnelian,m.opal,m.ravenite,m.topaz,m.lazuli,m.serpentine);
		this.add(Type.ORE, m.copperOre,m.quartz,m.meteorite,m.ironOre,m.debris1,m.debris2);
		this.add(Type.JEWEL, m.silver,m.obsidian,m.ruby,m.sapphire,m.diamond);
		this.add(Type.METAL, m.copper,m.lead,m.iron,m.meteoricIron,m.steel1,m.steel2);
		this.add(Type.METAL_PRECIOUS,m.faerieSilver,m.damascus);
		this.add(Type.RARE, m.sivaQueen);
		this.add(Type.UNKNOWN, m.dummy);
	}

	private void add(Type type,UnsagaMaterial... materials){
		for(UnsagaMaterial mate:materials){
			merchandiseMaterialCategory.put(mate, type);
		}
	}

	public Set<UnsagaMaterial> getMaterialsFromType(BarteringMaterialCategory.Type type){
		return this.merchandiseMaterialCategory.entrySet().stream().filter(in -> in.getValue()==type).map(in -> in.getKey()).collect(Collectors.toSet());
	}


	public static Type getType(UnsagaMaterial m){
		if(BarteringMaterialCategory.instance().merchandiseMaterialCategory.containsKey(m)){
			return BarteringMaterialCategory.instance().merchandiseMaterialCategory.get(m);
		}
		return Type.UNKNOWN;
	}
}
