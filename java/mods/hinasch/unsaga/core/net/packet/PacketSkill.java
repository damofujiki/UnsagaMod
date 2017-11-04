//package mods.hinasch.unsaga.core.net.packet;
//
//import io.netty.buffer.ByteBuf;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//
//
//public class PacketSkill implements  IMessage{
//
//	protected int packetId;
//	protected XYZPos pos;
//	public static final int WOODBREAKER = 0x01;
//	public static final int DEBUG_ABILITY = 0x02;
//	public static final int THROWING = 0x03;
//
//
//
//	public static PacketSkill createWoodBreaker(XYZPos usepoint){
//		return new PacketSkill(WOODBREAKER,usepoint);
//	}
//
//	public PacketSkill(){
//
//	}
//	protected PacketSkill(int packetid){
//		this.packetId = packetid;
//	}
//	protected PacketSkill(int packetid,XYZPos pos){
//		this.packetId = packetid;
//		this.pos = pos;
//	}
//
//
//	public int getPacketID(){
//		return this.packetId;
//	}
//
//	public XYZPos getPos(){
//		return this.pos;
//	}
//
//
//	@Override
//	public void fromBytes(ByteBuf buffer) {
//		this.packetId = buffer.readInt();
//		if(this.packetId==WOODBREAKER){
//			this.pos = PacketUtil.bufferToXYZPos(buffer);
//		}
//
//	}
//	@Override
//	public void toBytes(ByteBuf buffer) {
//		buffer.writeInt(packetId);
//		if(this.packetId==WOODBREAKER){
//			PacketUtil.XYZPosToPacket(buffer, pos);
//		}
//
//	}
//
//
//	public static class PacketSkillHandler implements IMessageHandler<PacketSkill,IMessage>{
//
//		@Override
//		public IMessage onMessage(PacketSkill message, MessageContext ctx) {
//			if(ctx.side.isServer()){
//				EntityPlayer player = ctx.getServerHandler().playerEntity;
//				switch(message.getPacketID()){
//				case WOODBREAKER:
//					if(player.getHeldItemMainhand()!=null){
//						WazaPerformer helper = new WazaPerformer(player.worldObj, player, WazaRegistry.instance().woodBreakerPhoenix,player.getHeldItemMainhand() );
//						helper.setUsePoint(message.getPos());
//						helper.setPrepared(true);
//						helper.perform();
//					}
//					break;
//				case DEBUG_ABILITY:
//					break;
//				case THROWING:
//				}
//			}
//			return null;
//		}
//
//	}
//}
