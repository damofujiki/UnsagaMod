package mods.hinasch.unsaga.core.item.newitem.combat;

import java.util.Set;

import com.google.common.collect.Sets;

import mods.hinasch.unsaga.common.ItemWeaponUnsaga;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.IShearable;


/** 剣より軽くて弱い。強度は剣より２０％低い
 * ハサミと同じ効果も持つ（攻撃時）*/
public class ItemKnifeUnsaga extends ItemWeaponUnsaga{

	public ItemKnifeUnsaga() {
		super(ToolCategory.KNIFE);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public boolean canHarvest(IBlockState blockIn, ItemStack stack) {
		return Items.STONE_SWORD.canHarvestBlock(blockIn, stack);
	}



	@Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
		if(entity instanceof IShearable){
			boolean flag = Items.SHEARS.itemInteractionForEntity(stack, player, player, EnumHand.MAIN_HAND);
			if(flag){
				stack.damageItem(1, player);
				return true;
			}
		}
        return false;
    }

	@Override
	public float getBaseAttackDamage() {
		// TODO 自動生成されたメソッド・スタブ
		return 2.0F;
	}

	@Override
	public double getBaseAttackSpeed() {
		// TODO 自動生成されたメソッド・スタブ
		return -1.4D;
	}

	@Override
	public float getMaxDamageMultiply(){
		return 0.8F;
	}


	@Override
	public Set<Block> getEffectiveBlockSet() {
		// TODO 自動生成されたメソッド・スタブ
		return Sets.newHashSet();
	}

	@Override
	public int getHarvestLevelModifier() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public int getItemDamageOnBlockDestroyed() {
		// TODO 自動生成されたメソッド・スタブ
		return 2;
	}

	@Override
	public int getItemDamageOnHitEntity() {
		// TODO 自動生成されたメソッド・スタブ
		return 1;
	}

	@Override
	public Set<String> getToolClassStrings() {
		// TODO 自動生成されたメソッド・スタブ
		return Sets.newHashSet("sword");
	}

	@Override
	public String getUnlocalizedCategoryName() {
		// TODO 自動生成されたメソッド・スタブ
		return "knife";
	}

	@Override
	public boolean isEffectiveOn(ItemStack stack, IBlockState state) {
		return Items.STONE_SWORD.getStrVsBlock(stack, state)>1.0F;
	}

	@Override
	public ToolCategory getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return ToolCategory.KNIFE;
	}


}
