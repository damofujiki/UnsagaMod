package mods.hinasch.unsaga.common;

import java.util.List;
import java.util.Set;

import mods.hinasch.unsaga.ability.IAbilitySelector;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemToolUnsaga extends ItemTool implements IItemColor,IUnsagaMaterialSelector,IAbilitySelector{

	protected ItemToolUnsaga(float attack,float speed, Set<Block> effectiveBlocksIn,ToolCategory cate) {
		super(attack,speed,ToolMaterial.STONE, effectiveBlocksIn);
		this.component = new ComponentUnsagaWeapon(cate);
		this.component.addPropertyOverrides(this);
	}

	ComponentUnsagaWeapon component;

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		// TODO 自動生成されたメソッド・スタブ
		return this.component.getColorFromItemstack(stack, tintIndex);
	}
	@Override
	public int getMaxAbilitySize() {
		// TODO 自動生成されたメソッド・スタブ
		return 4;
	}


	@Override
	public int getItemEnchantability(ItemStack is)
	{
		return this.component.getItemEnchantability(is);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int)this.component.getMaxDamage(stack);
	}

	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		this.component.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{

		this.component.getSubItems(par1, par2CreativeTabs, par3List);


	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return this.component.getUnlocalizedName(par1ItemStack);
	}
}
