package mods.hinasch.unsaga.common.specialaction;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantment;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentCapability;
import mods.hinasch.unsagamagic.spell.action.SpellCaster;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;

public class ActionItem<T extends IActionPerformer> implements IAction<T>{

	BiFunction<T,ItemStack,EnumActionResult> function;
	@Override
	public EnumActionResult apply(T context) {
		if(context.getPerformer().getHeldItemOffhand()!=null){
			return this.function.apply(context, context.getPerformer().getHeldItemOffhand());
		}
		return EnumActionResult.PASS;
	}

	public ActionItem<T> setFunction(BiFunction<T,ItemStack,EnumActionResult> function){
		this.function = function;
		return this;
	}

	public static class SpellActionRepair extends ActionItem<SpellCaster>{

		final ItemPredicate predicate;
		public SpellActionRepair(ItemPredicate predicate){
			this.predicate = predicate;
			this.function = new BiFunction<SpellCaster,ItemStack,EnumActionResult>(){

				@Override
				public EnumActionResult apply(SpellCaster context, ItemStack target) {
					if(isItemApplicable(target)){
						int repair = (int) context.getEffectModifiedStrength().hp();
						if(!target.isItemStackDamageable()){
							return EnumActionResult.PASS;
						}
						if(target.getItemDamage()==0){
							return EnumActionResult.PASS;
						}
						if(target.getItemDamage()<repair){
							repair = target.getItemDamage();
						}
						context.playSound(XYZPos.createFrom(context.getPerformer()), SoundEvents.BLOCK_ANVIL_USE, false);
						target.damageItem(-repair, context.getPerformer());
						context.broadCastMessage(HSLibs.translateKey("msg.spell.recycle", target.getDisplayName(),repair));
						return EnumActionResult.SUCCESS;
					}
					return EnumActionResult.PASS;
				}


			};
		}

		public boolean isItemApplicable(ItemStack is){
			return this.predicate.test(is);
		}
	}
	public static class SpellActionBless extends ActionItem<SpellCaster>{


		final UnsagaEnchantment enchant;
		final ItemPredicate predicate;

		public SpellActionBless(UnsagaEnchantment enchant,ItemPredicate predicate){
			this.enchant = enchant;
			this.predicate = predicate;
			this.function = new BiFunction<SpellCaster,ItemStack,EnumActionResult>(){

				@Override
				public EnumActionResult apply(SpellCaster context, ItemStack target) {
					if(UnsagaEnchantmentCapability.adapter.hasCapability(target) && isItemApplicable(target)){
						context.playSound(XYZPos.createFrom(context.getPerformer()), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, false);
						Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(target);
						int duration = (int)(context.getActionProperty().getDuration() * context.getAmplify());
						int amp = MathHelper.clamp_int((int)context.getAmplify(), 1, 3);

						map.put(enchant.getEnchantment(), (int)context.getAmplify());
						EnchantmentHelper.setEnchantments(map, target);
						UnsagaEnchantmentCapability.adapter.getCapability(target).setEnchantmentRemaining(enchant, duration);




						return EnumActionResult.SUCCESS;
					}
					return EnumActionResult.PASS;
				}


			};


		}

		public boolean isItemApplicable(ItemStack is){
			return this.predicate.test(is);
		}
	}

	public static interface ItemPredicate extends Predicate<ItemStack>{

	}
}
