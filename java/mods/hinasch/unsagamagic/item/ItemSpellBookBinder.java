package mods.hinasch.unsagamagic.item;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.init.UnsagaGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSpellBookBinder  extends Item{

	public static final int MAX_BINDER = 16;

	public ItemSpellBookBinder(){
		this.setMaxStackSize(1);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer ep, EnumHand hand)
    {
    	XYZPos pos = XYZPos.createFrom(ep);
    	HSLibs.openGui(ep,UnsagaMod.instance, UnsagaGui.Type.BINDER.getMeta(), worldIn, pos);
        return new ActionResult(EnumActionResult.SUCCESS,itemStackIn);
    }
}
