package mods.hinasch.unsaga.core.net.packet;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.ability.specialmove.SpecialMove;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketInteractEntity implements IMessage{

	public int getEntityID() {
		return entityid;
	}

	public SpecialMove getMove() {
		return move;
	}

	int entityid;
	SpecialMove move;
	NBTTagCompound comp;

	public PacketInteractEntity(){

	}

	public PacketInteractEntity(int entityid,SpecialMove move){
		this.entityid = entityid;
		this.move = move;
	}
	@Override
	public void fromBytes(ByteBuf buf) {
		int length = buf.readInt();
		ByteBuf bytes = buf.readBytes(length);
		this.comp = PacketUtil.bytesToNBT(bytes);
		this.entityid = this.comp.getInteger("entityid");
		String key = this.comp.getString("move");
		if(SpecialMoveRegistry.instance().get(key)!=null){
			this.move = SpecialMoveRegistry.instance().get(key);
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		this.comp = UtilNBT.compound();
		this.comp.setInteger("entityid", entityid);
		this.comp.setString("move", this.move.getKey().getResourcePath());
		byte[] bytes = PacketUtil.nbtToBytes(comp);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
	}

	public static class Handler implements IMessageHandler<PacketInteractEntity,IMessage>{

		@Override
		public IMessage onMessage(PacketInteractEntity message, MessageContext ctx) {
			if(ctx.side.isServer()){
				int entityid = message.getEntityID();
				SpecialMove move = message.getMove();
				EntityPlayer ep = ctx.getServerHandler().playerEntity;
				World world = ep.getEntityWorld();
				Entity entity = world.getEntityByID(entityid);
				if(move!=null && entity!=null){

				}
			}
			return null;
		}

	}
}
