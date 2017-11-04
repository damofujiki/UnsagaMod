package mods.hinasch.unsaga.core.inventory.container;

import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.container.inventory.InventoryBase;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ContainerUnsagaMap extends ContainerBase{

	public ContainerUnsagaMap(EntityPlayer ep) {
		super(ep, new InventoryBase(1));

	}

	@Override
	public boolean isShowPlayerInv(){
		return false;
	}

	@Override
	public PacketGuiButtonBaseNew getPacketGuiButton(int guiID, int buttonID, NBTTagCompound args) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public SimpleNetworkWrapper getPacketPipeline() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public int getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

	@Override
	public void onPacketData() {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}
}
