package mods.hinasch.unsaga.ability;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.specialmove.SpecialMove;
import mods.hinasch.unsaga.core.inventory.AccessorySlotCapability;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialCapability;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

public class AbilityAPI {

	public static List<IAbility> getAssociatedAbilities(ToolCategory cate,UnsagaMaterial m){
		return AbilityAssociateRegistry.instance().getAbilityList(cate, m);
	}

	public static Optional<SpecialMove> getLearnedSpecialMove(ItemStack is){
		return getAttachedAbilities(is).stream().filter(in -> in instanceof SpecialMove).map(in -> (SpecialMove)in).findFirst();
	}
	public static List<IAbility> getAttachedAbilities(ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is)){
			return AbilityCapability.adapter.getCapability(is).getLearnedAbilities();
		}
		return Lists.newArrayList();
	}

	public static List<Ability> getAttachedPassiveAbilities(ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is)){
			return AbilityCapability.adapter.getCapability(is).getLearnedAbilities().stream().filter(in -> in instanceof Ability).map(in ->(Ability)in).collect(Collectors.toList());
		}
		return Lists.newArrayList();
	}
	/**
	 * そのアイテムで覚える事のできるアビリティを返す。
	 * @param is
	 * @return
	 */
	public static List<IAbility> getApplicableAbilities(ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is)){
			IAbilityAttachable capa = AbilityCapability.adapter.getCapability(is);
			if(capa.isUniqueItem()){
				return capa.getLearnableUniqueAbilities();
			}

			return AbilityAssociateRegistry.instance().getAbilityList(is);

		}
		return Lists.newArrayList();
	}

	public static boolean existLearnableAbility(ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is)){
			List<IAbility> learned = AbilityCapability.adapter.getCapability(is).getLearnedAbilities().stream().filter(in -> in!=AbilityRegistry.empty())
					.collect(Collectors.toList());
			List<IAbility> learnable = getApplicableAbilities(is);
			if(learned.stream().anyMatch(in -> in instanceof SpecialMove)){
				learnable.removeIf(in -> in instanceof SpecialMove);
			}
			return !learnable.stream().allMatch(in -> learned.contains(in));
		}
		return false;
	}
	public static AbilityRegistry ability(){
		return AbilityRegistry.instance();
	}

	public static IAbility getAbilityByID(String id){
		return AbilityRegistry.instance().get(id);
	}


	/**
	 * 回復アビリティの効果量を全て足した数を返す。最小０。
	 * @param el
	 * @return
	 */
	public static int getHealAbilityAmount(EntityLivingBase el){
		return getEffectiveAllAbilities(el).stream().filter(in -> AbilityRegistry.instance().healAmountMap.containsKey(in))
		.mapToInt(in -> AbilityRegistry.instance().healAmountMap.get(in)).sum();
	}

	public static int getAbilityAmount(EntityLivingBase el,IAbility ab){
		return (int)getEffectiveAllAbilities(el).stream().filter(in -> in==ab).count();
	}

	public static void forgetRandomAbility(Random rand,ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is)){
			List<IAbility> list = AbilityCapability.adapter.getCapability(is).getLearnedAbilities();
			if(!list.isEmpty()){
				IAbility forgetAbility = HSLibs.randomPick(rand, list);
				AbilityCapability.adapter.getCapability(is).removeAbility(forgetAbility);
			}
		}
	}

	public static boolean hasWeaponSpecialArt(ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is) && UnsagaMaterialCapability.adapter.hasCapability(is)){
			if(AbilityCapability.adapter.getCapability(is).getLearnedAbilities().stream().anyMatch(in -> in instanceof SpecialMove)){
				return true;
			}
		}
		return false;
	}
	public static Optional<IAbility> learnRandomAbility(Random rand,ItemStack is){
		if(AbilityCapability.adapter.hasCapability(is) && UnsagaMaterialCapability.adapter.hasCapability(is)){
			if(!existLearnableAbility(is)){
				return Optional.empty();
			}
			ToolCategory cate = ToolCategory.getCategoryFromItem(is.getItem());
			UnsagaMaterial mate = UnsagaMaterialCapability.adapter.getCapability(is).getMaterial();
			List<IAbility> list = AbilityAssociateRegistry.instance().getAbilityList(is);
			if(cate.isWeapon()){
				list = AbilityAssociateRegistry.instance().getAbilityList(is);
				if(hasWeaponSpecialArt(is)){
					list.removeIf(in -> in instanceof SpecialMove);
				}
			}
			list.removeIf(in -> AbilityCapability.adapter.getCapability(is).getLearnedAbilities().contains(in));

			if(!list.isEmpty()){
				IAbility learnAbility = HSLibs.randomPick(rand, list);

				AbilityCapability.adapter.getCapability(is).addAbility(learnAbility);
				return Optional.of(learnAbility);
			}
		}
		return Optional.empty();
	}
	public static List<ItemStack> getAllEquippedArmors(EntityLivingBase el){
		return getAllEquippedItems(el,true);
	}

	public static List<ItemStack> getAllEquippedItems(EntityLivingBase el,boolean ignoreHelds){
		List<ItemStack> stacks = Lists.newArrayList();
		if(!ignoreHelds){
			el.getHeldEquipment().iterator().forEachRemaining(is -> {
				if(ItemUtil.isItemStackPresent(is)){
					stacks.add(is);
				}
			});
		}

		el.getArmorInventoryList().forEach(is ->{
			if(ItemUtil.isItemStackPresent(is)){
				stacks.add(is);
			}
		});
		if(AccessorySlotCapability.adapter.hasCapability(el)){
			AccessorySlotCapability.adapter.getCapability(el).getEquippedList().getRawList().forEach(in ->{
				if(ItemUtil.isItemStackPresent(in)){
					stacks.add(in);
				}
			});
		}
		return stacks;
	}

	public static List<Tuple<IAttribute,Double>> getAllAbilityModifiers(EntityLivingBase el){
		return getEffectiveAllAbilities(el).stream().filter(in -> in instanceof Ability).map(in -> (Ability)in)
		.filter(in -> in.getAttributeModifier()!=null).map(in -> in.getAttributeModifier())
		.collect(Collectors.toList());
	}

	/** パッシブアビリティ(Ability not SpecialMove)を取得する*/
	public static List<Ability> getEffectiveAllPassiveAbilities(EntityLivingBase el){
		return getEffectiveAllAbilities(el).stream().filter(in -> in instanceof Ability).map(in ->(Ability)in).collect(Collectors.toList());
	}
	/**
	 * 身体、手、アクセサリの身につけているもの全部のアビリティを取得
	 * @param el
	 * @return
	 */
	public static List<IAbility> getEffectiveAllAbilities(EntityLivingBase el){
		List<ItemStack> stacks = getAllEquippedItems(el,false);
		List<IAbility> list = Lists.newArrayList();
		if(!stacks.isEmpty()){
			list = stacks.stream().flatMap(in ->{
				List<IAbility> rt = Lists.newArrayList();
				if(AbilityCapability.adapter.hasCapability(in)){
					rt.addAll(AbilityCapability.adapter.getCapability(in).getLearnedAbilities());
				}
				return rt.stream();
			}).filter(in -> in!=AbilityRegistry.empty()).collect(Collectors.toList());

//			stacks.forEach(is ->{
//				if(AbilityCapability.adapter.hasCapability(is)){
//					AbilityCapability.adapter.getCapability(is).getLearnedAbilities().forEach(ability -> list.add(ability));
//				}
//			});
		}
//		ObjectCounter<IAbility> counter = ObjectCounter.of(counterin ->{
//			if(!stacks.isEmpty()){
//				stacks.iterator().forEachRemaining(is ->{
//					if(AbilityCapability.adapter.hasCapability(is)){
//						AbilityCapability.adapter.getCapability(is).getLearnedAbilities().forEach(ability -> counterin.add(ability));
//					}
//				});
//			}
//		});
		return list;
	}
}
