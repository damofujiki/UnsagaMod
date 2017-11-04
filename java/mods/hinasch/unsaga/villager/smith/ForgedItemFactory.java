package mods.hinasch.unsaga.villager.smith;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.ability.AbilityCapability;
import mods.hinasch.unsaga.ability.AbilityRegistry;
import mods.hinasch.unsaga.ability.IAbility;
import mods.hinasch.unsaga.common.ComponentUnsagaWeapon;
import mods.hinasch.unsaga.core.inventory.container.ContainerSmithUnsaga;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialTool;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.villager.bartering.BarteringMaterialCategory;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class ForgedItemFactory {

	ContainerSmithUnsaga parent;

	public static enum ForgeResult{
		/** アビリティを忘れる*/
		BAD,
		/** 普通*/
		GOOD,
		/** アビリティを引き出す*/
		VERY_GOOD;

		public SoundEvent getResultSound(){
			switch(this){
			case BAD:
				return SoundEvents.BLOCK_ANVIL_DESTROY;
			case GOOD:
				return SoundEvents.BLOCK_ANVIL_USE;
			case VERY_GOOD:
				return SoundEvents.BLOCK_ANVIL_PLACE;
			default:
				break;

			}
			return SoundEvents.BLOCK_ANVIL_DESTROY;
		}
	}
	Random rand;
	public ForgedItemFactory(Random rand,ContainerSmithUnsaga parent){
		this.rand = rand;
		this.parent = parent;
	}

	public ValidPaymentRegistry.Value getPaymentValue(){
		return ValidPaymentRegistry.getValue(this.parent.getPaymentStack()).get();

	}

	public BlackSmithType getSmithType(){
		return this.parent.getSmithType();
	}

	public ToolCategory getToolCategory(){
		return this.parent.getCurrentCategory();
	}
	public ForgingProcess create(UnsagaMaterial base,UnsagaMaterial sub,ItemStack baseStack,ItemStack subStack){
		return new ForgingProcess(this, base, baseStack, sub, subStack);
	}

	public static class ForgingProcess{
		ForgedItemFactory parent;
		public UnsagaMaterial getBaseMaterial() {
			return baseMaterial;
		}

		public UnsagaMaterial getSubMaterial() {
			return subMaterial;
		}

		public ItemStack getBaseStack() {
			return baseStack;
		}

		public ItemStack getSubStack() {
			return subStack;
		}

		UnsagaMaterial baseMaterial;
		UnsagaMaterial subMaterial;
		ItemStack baseStack;
		ItemStack subStack;
		Random rand;

		public ForgingProcess(ForgedItemFactory parent,UnsagaMaterial base,ItemStack baseStack,UnsagaMaterial sub,ItemStack subStack){
			this.baseMaterial = Preconditions.checkNotNull(base);
			this.subMaterial = Preconditions.checkNotNull(sub);
			this.baseStack = Preconditions.checkNotNull(baseStack);
			this.subStack = Preconditions.checkNotNull(subStack);
			this.parent = parent;
		}

		public static List<UnsagaMaterial> getForgeableMaterials(UnsagaMaterial base,UnsagaMaterial sub){
			return MaterialTransformRegistry.instance().getForgeableMaterials(base, sub);
		}

		public static int getDurability(UnsagaMaterial baseMaterial,ItemStack baseStack){
			int base= 0;
			if(baseStack.isItemStackDamageable()){
				base = baseStack.getMaxDamage() - baseStack.getItemDamage();
			}else{
				base = baseMaterial.getToolMaterial().getMaxUses();
				OptionalDouble opt = MaterialItemAssociatedRegistry.instance().getAmoutInDurability(baseStack);
				float amount = (float) (opt.isPresent() ? opt.getAsDouble() : 1.0F);
				base = (int)((float)base * amount);
				base = base * baseStack.stackSize;
			}
			return base;
		}

		public Optional<ToolCategory> getBaseToolCategory(){
			if(ToolCategory.getCategoryFromItem(this.baseStack.getItem())!=null){
				return Optional.of(ToolCategory.getCategoryFromItem(this.baseStack.getItem()));
			}
			return Optional.empty();
		}

		public boolean isChangeMaterial(){
			if(this.getBaseToolCategory().isPresent()){
				return this.getBaseToolCategory().get()!=this.parent.getToolCategory();
			}
			return true;
		}
		public static int getRepairAmount(UnsagaMaterial baseMaterial,UnsagaMaterial subMaterial,ItemStack subStack){
			int repair = getDurability(subMaterial, subStack);
			if(baseMaterial!=subMaterial){

				if(BarteringMaterialCategory.getType(baseMaterial)!=null){
					if(!BarteringMaterialCategory.getType(baseMaterial).getMaterials().contains(subMaterial)){
						repair = (int)((float)repair * 0.4F);
					}
				}

				if(baseMaterial.rank>subMaterial.rank){
					repair = (int)((float)repair * 0.5F);
//					repair = MathHelper.clamp_int(repair, 0, 100);
				}
			}
			return repair;
		}
		public static int getForgedDurability(UnsagaMaterial baseMaterial,UnsagaMaterial subMaterial,ItemStack baseStack,ItemStack subStack){

			int base = getDurability(baseMaterial, baseStack);

			int repair = getRepairAmount(baseMaterial,subMaterial, subStack);


			return base + repair;
		}

		Optional<UnsagaMaterial> forgedMaterial = Optional.empty();
		OptionalInt forgedDurability = OptionalInt.empty();
		Optional<ItemStack> forgedStack = Optional.empty();
		OptionalInt forgedWeight = OptionalInt.empty();
//		Optional<LinkedList<IAbility>> forgedAbility = Optional.empty();
		public ForgingProcess decideForgedMaterial(){
			this.forgedMaterial = Optional.of(MaterialTransformRegistry.instance().getTransformedOrNot(this.parent.rand, baseMaterial, this.subMaterial));
			return this;
		}

		public ForgingProcess decideForgedDurability(){
			int base = this.getForgedDurability();
			ValidPaymentRegistry.Value value = this.parent.getPaymentValue();
			if(value==ValidPaymentRegistry.Value.LOW){
				base = (int)((float)base * 0.8F);
			}
			if(value==ValidPaymentRegistry.Value.HIGH){
				base = (int)((float)base * 1.2F);
			}
			if(value==ValidPaymentRegistry.Value.RICH){
				base = (int)((float)base * 1.5F);
			}
			if(this.parent.getSmithType()==BlackSmithType.DURABILITY){
				base = (int)((float)base * 1.3F);
			}
			this.forgedDurability = OptionalInt.of(base);
			return this;
		}

		public int getWeight(UnsagaMaterial base,ItemStack stack){
			if(UnsagaMaterialTool.adapter.hasCapability(stack)){
				return UnsagaMaterialTool.adapter.getCapability(stack).getWeight();
			}
			return base.weight;
		}

		public LinkedList<IAbility> decideAbility(ItemStack forged){

			if(AbilityCapability.adapter.hasCapability(forged)){
				int size = AbilityCapability.adapter.getCapability(forged).getMaxAbilitySize();
				if(this.isChangeMaterial()){
					return HSLibs.filledList(size, AbilityRegistry.empty());
				}
				if(AbilityCapability.adapter.hasCapability(baseStack)){
					return AbilityCapability.adapter.getCapability(baseStack).getLearnedAbilities();
				}else{
					return HSLibs.filledList(size, AbilityRegistry.empty());
				}
			}

			return HSLibs.filledList(4, AbilityRegistry.empty());
		}
		public ForgingProcess decideForgedWeight(){

			int baseWeight = this.getWeight(baseMaterial, getBaseStack());
			int forgedWeight = baseWeight;
			int subWeight = this.getWeight(this.subMaterial, this.getSubStack());
			if(baseWeight>subWeight){
				forgedWeight = forgedWeight - this.parent.rand.nextInt(2);
			}else{
				forgedWeight = forgedWeight + this.parent.rand.nextInt(2);
			}
			forgedWeight = MathHelper.clamp_int(forgedWeight, 0, 10);
			this.forgedWeight = OptionalInt.of(forgedWeight);
			return this;
		}

		/**
		 * アビリティの変化。大体原作準拠
		 * @param is
		 */
		public void transformAbility(ItemStack is){
			if(!AbilityCapability.adapter.hasCapability(is)){
				return;
			}
//			if(BarteringMaterialCategory.Type.BESTIAL.getMaterials().contains(this.subMaterial) && this.subMaterial!=UnsagaMaterials.instance().serpentine){
				if(ByproductAbilityRegistry.instance().hasByproductAbility(subMaterial)){
					if(this.parent.rand.nextFloat()<0.20F){
						List<IAbility> abilities = AbilityCapability.adapter.getCapability(is).getLearnedAbilities();
						int index = this.parent.getToolCategory()==ToolCategory.SHIELD ? 3 : 1;
						AbilityCapability.adapter.getCapability(is).setAbility(index, ByproductAbilityRegistry.instance().getByproductAbility(subMaterial));
					}
				}


//			}
		}
		public ForgeResult bakeForgedStack(){
			ForgeResult result = ForgeResult.GOOD;
			if(this.forgedDurability.isPresent() && this.forgedMaterial.isPresent()
					&& this.forgedWeight.isPresent()){
				ItemStack forged = UnsagaItemRegistry.getItemStack(this.parent.getToolCategory().getAssociatedItem(), this.forgedMaterial.get(), 0);
				int durability = this.forgedDurability.getAsInt();
				int weight = this.forgedWeight.getAsInt();
				LinkedList<IAbility> abilities = this.decideAbility(forged);
				if(durability>forged.getMaxDamage()){
					durability = forged.getMaxDamage();
				}
				if(durability<1){
					durability = 1;
				}
				int damage = forged.getMaxDamage() - durability;
				forged.setItemDamage(damage);

				if(AbilityCapability.adapter.hasCapability(forged)){
					AbilityCapability.adapter.getCapability(forged).setAbilities(abilities);

					float fix = 0.0F;
					if(this.parent.getSmithType()==BlackSmithType.ABILITY){
						fix = 0.7F;
					}

					//支払いによって出来が変化
					if(this.parent.getPaymentValue()==ValidPaymentRegistry.Value.LOW){
						if(this.parent.rand.nextFloat()<0.25F-fix){
							AbilityAPI.forgetRandomAbility(this.parent.rand, forged);
							result = ForgeResult.BAD;
						}

					}
					if(this.parent.getPaymentValue()==ValidPaymentRegistry.Value.MID){
						if(this.parent.rand.nextFloat()<0.10F-fix){
							AbilityAPI.forgetRandomAbility(this.parent.rand, forged);
							result = ForgeResult.BAD;
						}

					}
					if(this.parent.getPaymentValue()==ValidPaymentRegistry.Value.HIGH){
						if(this.parent.rand.nextFloat()<0.15F+fix){
							AbilityAPI.learnRandomAbility(this.parent.rand, forged);
							result = ForgeResult.VERY_GOOD;
						}
					}
					if(this.parent.getPaymentValue()==ValidPaymentRegistry.Value.RICH){
							AbilityAPI.learnRandomAbility(this.parent.rand, forged);
							result = ForgeResult.VERY_GOOD;
					}
				}
				UnsagaMaterialTool.adapter.getCapability(forged).setWeight(weight);
				this.refleshWeightModifier(forged);
				this.transformAbility(forged);
				this.forgedStack = Optional.of(forged);
			}
			return result;
		}
		public List<UnsagaMaterial> getForgeableMaterials(){
			return getForgeableMaterials(this.baseMaterial,this.subMaterial);
		}

		public int getForgedDurability(){
			return getForgedDurability(this.baseMaterial,this.subMaterial,this.baseStack,this.subStack);
		}

		public Optional<ItemStack> getForgedStack(){
			return this.forgedStack;
		}

		/**
		 * 重さから攻撃速度を再計算
		 * @param is
		 */
		public void refleshWeightModifier(ItemStack is){
			Multimap<String, AttributeModifier> multimap = is.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
            if(UnsagaMaterialTool.adapter.hasCapability(is)){
            	int weight = UnsagaMaterialTool.adapter.getCapability(is).getWeight();
            	if(weight>=0){
            		double mod = ComponentUnsagaWeapon.getSpeedModifierFromWeight(weight);
                	multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ComponentUnsagaWeapon.WEIGHT_EFFECTED_SPEED, "Weapon modifier", mod, 0));

            	}
            }
		}
		@Override
		public String toString(){
			StringBuilder builder = new StringBuilder("");
			builder.append(this.baseMaterial);
			builder.append(this.subMaterial);
			builder.append(this.baseStack);
			builder.append(this.subStack);
			return builder.toString();
		}
	}
}
