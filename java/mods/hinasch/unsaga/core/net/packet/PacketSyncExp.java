package mods.hinasch.unsaga.core.net.packet;
//package mods.hinasch.unsaga.net.packet;
//
//import io.netty.buffer.ByteBuf;
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.capability.IUnsagaExp;
//import mods.hinasch.unsaga.util.XPHelper;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public class PacketSyncExp implements IMessage{
//
//	int skillPointTotal;
//	int skillPoint;
//	int decipheringPoint;
//
//	public static PacketSyncExp create(int total,int sp,int dp){
//		return new PacketSyncExp(total,sp,dp);
//	}
//	public static PacketSyncExp create(IUnsagaExp capa){
//		return new PacketSyncExp(capa.getSkillPoint(),capa.getSkillPointLevel(),capa.getDecipheringPoint());
//	}
//	public PacketSyncExp(){
//
//	}
//	protected PacketSyncExp(int total,int skillpoint,int decipher){
//		this.skillPointTotal = total;
//		this.skillPoint = skillpoint;
//		this.decipheringPoint = decipher;
//	}
//	@Override
//	public void fromBytes(ByteBuf buf) {
//
//		this.skillPointTotal = buf.readInt();
//		this.skillPoint = buf.readInt();
//		this.decipheringPoint = buf.readInt();
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//
//		buf.writeInt(skillPointTotal);
//		buf.writeInt(skillPoint);
//		buf.writeInt(this.decipheringPoint);
//	}
//
//	public static class Handler implements IMessageHandler<PacketSyncExp,IMessage>{
//
//		@Override
//		public IMessage onMessage(PacketSyncExp message, MessageContext ctx) {
//			if(ctx.side.isClient()){
//				UnsagaMod.logger.trace(this.getClass().getName(),"called");
//				if(ClientHelper.getPlayer()!=null && XPHelper.hasCapability(ClientHelper.getPlayer())){
//					IUnsagaExp capa = XPHelper.getCapability(ClientHelper.getPlayer());
//					capa.setSkillPointLevel(message.skillPoint);
//					capa.setSkillPoint(message.skillPointTotal);
//					capa.setDecipheringPoint(message.decipheringPoint);
//					UnsagaMod.logger.trace(this.getClass().getName(),"sync!");
//				}
//			}
//			return null;
//		}
//
//	}
//}
