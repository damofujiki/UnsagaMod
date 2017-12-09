package mods.hinasch.unsaga.core.net.packet;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDebugPos implements IMessage{

	XYZPos pos;
	public PacketDebugPos(){

	}

	public PacketDebugPos(BlockPos pos){
		this.pos = new XYZPos(pos);
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = XYZPos.readFromBuffer(buf);

	}

	public XYZPos getPos(){
		return this.pos;
	}
	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		pos.writeToBuffer(buf);
	}

	public static class Handler implements IMessageHandler<PacketDebugPos,IMessage>{

		@Override
		public IMessage onMessage(PacketDebugPos message, MessageContext ctx) {
			if(ctx.side.isClient()){

				UnsagaMod.proxy.setDebugPos(message.getPos());
				ChatHandler.sendChatToPlayer(ClientHelper.getPlayer(), "changed debugpos.");
			}
			return null;
		}
	}
}
