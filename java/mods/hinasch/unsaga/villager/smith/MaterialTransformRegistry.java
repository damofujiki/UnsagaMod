package mods.hinasch.unsaga.villager.smith;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.villager.bartering.BarteringMaterialCategory;
import net.minecraft.util.WeightedRandom;

public class MaterialTransformRegistry {

	List<MaterialTransform> list = Lists.newArrayList();

	UnsagaMaterials m = UnsagaMaterials.instance();

	private static MaterialTransformRegistry INSTANCE;

	public static MaterialTransformRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new MaterialTransformRegistry();
		}
		return INSTANCE;
	}
	public static enum WILDCARD{
		NONE,WOOD,BESTIAL,COMMON_SCALE;

		public Set<UnsagaMaterial> getMaterials(){
			Set<UnsagaMaterial> set = Sets.newHashSet();
			switch(this){
			case BESTIAL:
				set = BarteringMaterialCategory.Type.BESTIAL.getMaterials();
				set.remove(UnsagaMaterials.instance().serpentine);
				return set;
			case WOOD:
				return BarteringMaterialCategory.Type.WOOD.getMaterials();
			case COMMON_SCALE:
				set = BarteringMaterialCategory.Type.SCALE.getMaterials();
				set.remove(UnsagaMaterials.instance().dragonScale);
				set.remove(UnsagaMaterials.instance().ancientScale);
				return set;
			default:
				break;

			}
			return set;
		}
	}
	protected MaterialTransformRegistry(){

	}
	public void register(){
		this.list.add(new MaterialTransform(m.quartz,m.wood,m.debris1,0.15F));
		this.list.add(new MaterialTransform(m.silver,m.wood,m.debris1,0.15F));
		this.list.add(new MaterialTransform(m.obsidian,m.wood,m.debris1,0.15F));

		this.list.add(new MaterialTransform(m.debris1,m.debris1,m.debris2,1.00F));

		this.list.add(new MaterialTransform(m.debris1,WILDCARD.BESTIAL,m.debris2,1.00F));
		this.list.add(new MaterialTransform(m.silver,WILDCARD.BESTIAL,m.faerieSilver,0.15F));


		this.list.add(new MaterialTransform(m.serpentine,m.wood,m.carnelian,0.15F));

		this.list.add(new MaterialTransform(m.copperOre,WILDCARD.WOOD,m.copper,1.00F));
		this.list.add(new MaterialTransform(m.ironOre,WILDCARD.WOOD,m.iron,0.85F));
		this.list.add(new MaterialTransform(m.ironOre,WILDCARD.WOOD,m.steel2,0.15F));
		this.list.add(new MaterialTransform(m.steel1,WILDCARD.WOOD,m.steel2,0.15F));
		this.list.add(new MaterialTransform(m.iron,WILDCARD.WOOD,m.steel2,0.15F));
		this.list.add(new MaterialTransform(m.iron,WILDCARD.WOOD,m.steel1,0.85F));


		this.list.add(new MaterialTransform(m.steel2,m.debris1,m.steel1,0.15F));
		this.list.add(new MaterialTransform(m.meteorite,m.ancientScale,m.meteoricIron,1.00F));
		this.list.add(new MaterialTransform(m.meteorite,m.dragonScale,m.meteoricIron,1.00F));
		this.list.add(new MaterialTransform(m.meteorite,m.bone1,m.meteoricIron,0.15F));
		this.list.add(new MaterialTransform(m.meteorite,m.bone2,m.meteoricIron,0.15F));
		this.list.add(new MaterialTransform(m.meteorite,WILDCARD.COMMON_SCALE,m.meteoricIron,0.15F));

		this.list.add(new MaterialTransform(m.steel2,m.debris2,m.damascus,1.00F));
		this.list.add(new MaterialTransform(m.ruby,m.sapphire,m.sivaQueen,0.15F));
		this.list.add(new MaterialTransform(m.sapphire,m.ruby,m.sivaQueen,0.15F));
	}

//	public static Optional<UnsagaMaterial> tryTransform(Random rand,UnsagaMaterial baseIn,UnsagaMaterial subIn){
//		return MaterialTransformRegistry.instance().getTransformedOrNot(rand, baseIn, subIn);
//	}

	protected List<UnsagaMaterial> getForgeableMaterials(UnsagaMaterial baseIn,UnsagaMaterial subIn){
		List<MaterialTransform> transforms = Lists.newArrayList();
		List<UnsagaMaterial> rt = Lists.newArrayList();
		this.list.stream().forEach(in ->{
			if(in.match(baseIn, subIn)){
				transforms.add(in);
			}
		});
		rt.addAll(transforms.stream().map(in -> in.getTransformed()).collect(Collectors.toList()));
		double amount = transforms.stream().mapToDouble(in -> in.getProb()).sum();
		if(amount<1.0D){
			rt.add(baseIn);
		}

		return rt;
	}
	public UnsagaMaterial getTransformedOrNot(Random rand,UnsagaMaterial baseIn,UnsagaMaterial subIn){
		List<WeightedRandomTrans> list = Lists.newArrayList();
		this.list.stream().forEach(in ->{
			if(in.match(baseIn, subIn)){
				list.add(new WeightedRandomTrans((int)(in.getProb()*100.0F),in.getTransformed()));
			}
		});
		if(list.isEmpty()){
			return baseIn;
		}
		if(list.size()==1){
			return list.get(0).mate;
		}
		if(WeightedRandom.getTotalWeight(list)<100){
			double remain = 1.0D - this.list.stream().mapToDouble(in -> in.getProb()).sum();
			list.add(new WeightedRandomTrans((int)(remain*100),baseIn));
		}
		WeightedRandomTrans w = WeightedRandom.getRandomItem(rand, list);
		UnsagaMaterial transformed = w.mate;
		return transformed;
	}
	public static class MaterialTransform{

		public UnsagaMaterial getBase() {
			return base;
		}

		public UnsagaMaterial getSub() {
			return sub;
		}

		public UnsagaMaterial getTransformed() {
			return transformed;
		}

		public float getProb() {
			return prob;
		}

		final WILDCARD wildcard;
		final UnsagaMaterial base;
		final UnsagaMaterial sub;
		final UnsagaMaterial transformed;
		final float prob;

		public MaterialTransform(UnsagaMaterial base,UnsagaMaterial sub,UnsagaMaterial transform,float prob){
			this.base = base;
			this.sub = sub;
			this.transformed = transform;
			this.prob = prob;
			this.wildcard = WILDCARD.NONE;
		}
		public MaterialTransform(UnsagaMaterial base,WILDCARD wildcard,UnsagaMaterial transform,float prob){
			this.base = base;
			this.sub = UnsagaMaterials.instance().dummy;
			this.transformed = transform;
			this.prob = prob;
			this.wildcard = wildcard;
		}

		public  boolean match(UnsagaMaterial baseIn,UnsagaMaterial subIn){
			if(this.sub!=UnsagaMaterials.instance().dummy){
				return baseIn==this.base && this.sub==subIn;
			}
			if(this.wildcard!=WILDCARD.NONE){
				return baseIn==this.base && this.wildcard.getMaterials().stream().anyMatch(in -> {
//					UnsagaMod.logger.trace("match", subIn,in, in==subIn);
					return in==subIn;
				});
			}
			return false;
		}
	}

	public static class WeightedRandomTrans extends WeightedRandom.Item{


		public UnsagaMaterial mate;
		public WeightedRandomTrans(int itemWeightIn,UnsagaMaterial mate) {
			super(itemWeightIn);
			this.mate = mate;
			// TODO 自動生成されたコンストラクター・スタブ
		}

	}
}
