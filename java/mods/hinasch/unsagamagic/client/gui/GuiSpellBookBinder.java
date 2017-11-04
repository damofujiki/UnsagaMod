package mods.hinasch.unsagamagic.client.gui;

import mods.hinasch.lib.client.GuiContainerBase;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.inventory.container.ContainerSpellBookBinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class GuiSpellBookBinder extends GuiContainerBase{

	public GuiSpellBookBinder(EntityPlayer ep,ItemStack binder) {
		super(new ContainerSpellBookBinder(ep,binder));

	}

	@Override
	public String getGuiTextureName(){
		return UnsagaMod.MODID+":textures/gui/container/binder.png";
	}
	@Override
	public String getGuiName(){
		return "Edited Spell Book";
	}
}
