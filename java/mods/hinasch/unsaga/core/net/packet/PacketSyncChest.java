package mods.hinasch.unsaga.core.net.packet;
//package mods.hinasch.unsaga.net.packet;
//
//import io.netty.buffer.ByteBuf;
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.entity.EntityUnsagaChest;
//import mods.hinasch.unsaga.util.chest.ChestBehavior;
//import mods.hinasch.unsaga.util.chest.ChestBehavior.IUnsagaChest;
//import net.minecraft.entity.Entity;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public class PacketSyncChest implements IMessage{
//
//	public static final int TYPE_ENTITY = 0x01;
//	public static final int TYPE_TILEENTITY = 0x02;
//
//	int type;
//	NBTTagCompound nbt;
//	public PacketSyncChest(){
//
//	}
//
//	public PacketSyncChest(EntityUnsagaChest chest){
//		this.type = TYPE_ENTITY;
//		if(ChestBehavior.hasCapability(chest)){
//			IUnsagaChest instance = ChestBehavior.getCapability(chest);
//			this.nbt = (NBTTagCompound) ChestBehavior.capability().getStorage()
//			.writeNBT(ChestBehavior.capability(), instance, null);
//			this.nbt.setInteger("entityid", chest.getEntityId());
//		}
//	}
//
//	public PacketSyncChest(TileEntity chest){
//		this.type = TYPE_TILEENTITY;
//		if(ChestBehavior.hasCapability(chest)){
//			IUnsagaChest instance = ChestBehavior.getCapability(chest);
//			this.nbt = (NBTTagCompound) ChestBehavior.capability().getStorage()
//			.writeNBT(ChestBehavior.capability(), instance, null);
//			XYZPos pos = new XYZPos(chest.getPos());
//			pos.writeToNBT(nbt);
//		}
//	}
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		this.type = buf.readByte();
//		int length = buf.readInt();
//		ByteBuf bytes = buf.readBytes(length);
//		this.nbt = PacketUtil.bytesToNBT(bytes);
//
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		buf.writeByte(type);
//		byte[] bytes = PacketUtil.nbtToBytes(nbt);
//		buf.writeInt(bytes.length);
//		buf.writeBytes(bytes);
//
//	}
//
//	public static class Handler implements IMessageHandler<PacketSyncChest,IMessage>{
//
//		@Override
//		public IMessage onMessage(PacketSyncChest message, MessageContext ctx) {
//			if(ctx.side.isClient()){
//				switch(message.type){
//				case TYPE_ENTITY:
//					if(ClientHelper.getWorld()!=null){
//						Entity chest = ClientHelper.getWorld().getEntityByID(message.nbt.getInteger("entityid"));
//						if(chest!=null){
//							if(ChestBehavior.hasCapability(chest)){
//								UnsagaMod.logger.trace(this.getClass().getName(), "sync!(Entity)");
//								IUnsagaChest instance = ChestBehavior.getCapability(chest);
//								ChestBehavior.capability().getStorage().readNBT(ChestBehavior.capability(), instance, null,message.nbt);
//							}
//						}
//					}
//					break;
//				case TYPE_TILEENTITY:
//					if(ClientHelper.getWorld()!=null){
//						XYZPos pos = XYZPos.readFromNBT(message.nbt);
//						if(ClientHelper.getWorld().getTileEntity(pos)!=null){
//							TileEntity te = ClientHelper.getWorld().getTileEntity(pos);
//							if(te!=null){
//								if(ChestBehavior.hasCapability(te)){
//									UnsagaMod.logger.trace(this.getClass().getName(), "sync!(TE)");
//									IUnsagaChest instance = ChestBehavior.getCapability(te);
//									ChestBehavior.capability().getStorage().readNBT(ChestBehavior.capability(), instance, null,message.nbt);
//								}
//							}
//						}
//					}
//					break;
//				}
//			}
//
//			return null;
//		}
//
//	}
//}
