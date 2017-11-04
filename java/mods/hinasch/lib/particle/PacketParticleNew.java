package mods.hinasch.lib.particle;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.world.XYZPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketParticleNew implements IMessage{

	XYZPos pos;

	public PacketParticleNew(){

	}
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
