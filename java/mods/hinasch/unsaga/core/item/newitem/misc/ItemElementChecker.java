package mods.hinasch.unsaga.core.item.newitem.misc;

import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.element.newele.ElementAssociationLibrary;
import mods.hinasch.unsaga.element.newele.ElementTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemElementChecker extends Item{


	public ItemElementChecker(){
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(0);


	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		IBlockState state = worldIn.getBlockState(pos);
		if(state!=null && WorldHelper.isClient(worldIn)){
			ElementTable table =  ElementAssociationLibrary.calcAllElements(worldIn, playerIn);
			ChatHandler.sendChatToPlayer(playerIn, table.getAmountAsFloatLocalized());
			return EnumActionResult.SUCCESS;
		}
        return EnumActionResult.PASS;
    }



}
