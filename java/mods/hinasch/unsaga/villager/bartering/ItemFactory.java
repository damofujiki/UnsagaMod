package mods.hinasch.unsaga.villager.bartering;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import jline.internal.Preconditions;
import mods.hinasch.lib.item.WeightedRandomStack;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.ability.AbilityCapability;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsaga.material.SuitableLists;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class ItemFactory {

	public static class WeightedRandomRank extends WeightedRandom.Item{

		public final int number;

		public WeightedRandomRank(int weight,int num) {
			super(weight);
			this.number = num;

		}

	}

	public Random random;
	public ItemFactory(Random rand){
		this.random = rand;
	}
	public List<ItemStack> createMerchandises(int amount,int generateLevel,final Collection<ToolCategory> availables,final Set<UnsagaMaterial> strictMaterials){


		int minLevel = strictMaterials.stream().mapToInt(in -> in.rank).min().isPresent() ? strictMaterials.stream().mapToInt(in -> in.rank).min().getAsInt() : 3;
		int fixedLevel = minLevel>generateLevel ? minLevel : generateLevel;

		List<ItemStack> merchandises = Lists.newArrayList();
		for(int i=0;i<amount;i++){
//			int rank = random.nextInt(generateLevel);
			List<WeightedRandomStack> weighted = Lists.newArrayList();
			for(int j=0;j<5;j++){
				//取扱素材から選ぶ
				List<UnsagaMaterial> materials = UnsagaMaterials.instance().getMerchandiseMaterials(0,fixedLevel).stream().filter(in -> strictMaterials.contains(in))
						.collect(Collectors.toList());
				Collections.shuffle(materials, random);
				UnsagaMaterial chosenMaterial = materials.get(0);
				UnsagaMod.logger.trace(this.getClass().getName(), chosenMaterial,materials);
				//取扱カテゴリからひとつ選ぶ
				List<ToolCategory> availablesCopy = Lists.newArrayList(availables);
				Collections.shuffle(availablesCopy,random);


				ToolCategory chosenCategory = availablesCopy.get(0);
				ItemStack stack;
				//生素材の場合
				if(chosenCategory==ToolCategory.RAW_MATERIAL){
					stack = MaterialItemAssociatedRegistry.instance().getAssociatedStack(chosenMaterial);
				}else{
					if(SuitableLists.instance().getSuitableList(chosenCategory)!=null){
						//出来損ない武器になってしまう場合、確率で他の適合する素材になる
						if(!SuitableLists.instance().getSuitableList(chosenCategory).contain(chosenMaterial) && random.nextInt(4)==0){
							chosenMaterial = SuitableLists.instance().getSuitableList(chosenCategory).getRandom(random,true);

						}

					}
					stack = UnsagaItemRegistry.getItemStack(chosenCategory.getAssociatedItem(), chosenMaterial,0);
					if(random.nextInt(5)==0){
						this.putRandomAbility(stack, random);
					}

				}
//				UnsagaMod.logger.trace(this.getClass().getName(), chosenCategory,availablesCopy);

				Preconditions.checkNotNull(chosenCategory.getAssociatedItem());
//				UnsagaMod.logger.trace(this.getClass().getName(), chosenCategory.getAssociatedItem());
				//選択重みをつける
				int calculatedWeight = this.calcWeight(chosenMaterial.rank,fixedLevel);
				weighted.add(new WeightedRandomStack(calculatedWeight,stack));
			}
			ItemStack stack = WeightedRandom.getRandomItem(random, weighted).is;

//			Preconditions.checkArgument(materials.isEmpty(),rank);






			if(stack==null){
				UnsagaMod.logger.warn("[Warning]Item is null!!", fixedLevel);
				stack = RawMaterialItemRegistry.instance().debris1.getItemStack(1);
			}
//			Preconditions.checkNotNull(stack);
			merchandises.add(stack);
		}
//		UnsagaMod.logger.trace("item", merchandises);
		return merchandises;
	}

	private void putRandomAbility(ItemStack stack,Random rand){
		if(AbilityCapability.adapter.hasCapability(stack)){
			if(!AbilityAPI.getApplicableAbilities(stack).isEmpty()){
				for(int i=0;i<rand.nextInt(4);i++){
					AbilityAPI.learnRandomAbility(rand, stack);
				}

			}
		}
	}
	private int calcWeight(int rank,int generateLevel){
		if(rank<=6){
			return 100 - rank*rank;
		}
		int b = generateLevel - 9;
		b = b < 0 ? 0 : b;
		int a = 11 - rank + (b*3);
		return a<1 ? 1 : a;
	}


	public static class MerchandiseFactory extends ItemFactory{

		public MerchandiseFactory(Random rand) {
			super(rand);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public List<ItemStack> createMerchandises(int amount,int generateLevel,Collection<ToolCategory> availables,Set<UnsagaMaterial> strictMaterials){
			List<ItemStack> stacks = super.createMerchandises(amount, generateLevel, availables, strictMaterials);
			stacks.forEach(in ->{
				MerchandiseCapability.adapter.getCapability(in).setMerchandise(true);
			});

			return stacks;
		}


	}
//	private int drawRank(int generateLevel,Random random){
//		List<WeightedRandomRank> list = this.prepareWeightedList(generateLevel);
//		WeightedRandomRank w = WeightedRandom.getRandomItem(random, list);
//		return w.number;
//	}
//	private List<WeightedRandomRank> prepareWeightedList(int generateLevel){
//		List<WeightedRandomRank> list = Lists.newArrayList();
//		int a = generateLevel <=2 ? 2 : generateLevel;
//		generateLevel = generateLevel >= 20 ? 20 : generateLevel;
//		for(int i=0;i<a;i++){
//			list.add(this.getWeighted(i, generateLevel));
//		}
//		return list;
//	}
//	private WeightedRandomRank getWeighted(int requireRank,int generateLevel){
//		if(generateLevel<requireRank){
//			return new WeightedRandomRank(1,requireRank);
//		}
//		if(requireRank<=6){
//			int a = -10 * requireRank + 100;
//			return new WeightedRandomRank(a,requireRank);
//		}
//		int b = generateLevel - 9;
//		b = b < 0 ? 0 : b;
//		int a = - 5 * (requireRank -7) + (10 + b * 2);
//		a = a < 1 ? 1 : a;
//		return new WeightedRandomRank(a,requireRank);
//	}
}
