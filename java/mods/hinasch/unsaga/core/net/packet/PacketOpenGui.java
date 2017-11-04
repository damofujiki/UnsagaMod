package mods.hinasch.unsaga.core.net.packet;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.init.UnsagaGui;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;



public class PacketOpenGui implements IMessage{


	protected byte guinum;
	protected XYZPos pos;
//	protected int entityid;
	protected NBTTagCompound comp;
	public NBTTagCompound getComp() {
		return comp;
	}

	public PacketOpenGui(){

	}

	public PacketOpenGui(int numberGui,XYZPos pos,NBTTagCompound... args){

		this.guinum = (byte)numberGui;
		if(args.length>0){
			this.comp = args[0];
		}else{
			this.comp = UtilNBT.compound();
		}

		pos.writeToNBT(comp);

	}

//	public PacketOpenGui(int numberGui,XYZPos pos){
//		this(numberGui);
//		this.pos = pos;
//	}
//
////	public PacketOpenGui(int numberGui,TileEntityUnsagaChest chest){
////		this(numberGui);
////		this.entityid = -1;
////		this.pos = XYZPos.tileEntityPosToXYZ(chest);
////	}
////
////	public PacketOpenGui(int numberGui,EntityUnsagaChest chest){
////		this(numberGui);
////		this.entityid = chest.getEntityId();
////		this.pos = XYZPos.entityPosToXYZ(chest);
////	}
//	public PacketOpenGui(int numberGui,EntityVillager entity){
//		this(numberGui);
//		this.entityid = entity.getEntityId();
//	}


	public static PacketOpenGui create(UnsagaGui.Type gui,XYZPos pos,NBTTagCompound... args){
		return new PacketOpenGui(gui.getMeta(),pos,args);
	}
//	public int getEntityID(){
//		return this.entityid;
//	}

	public XYZPos getPos(){
		return this.pos;
	}

	public byte getGuiNumber(){
		return this.guinum;
	}


	@Override
	public void fromBytes(ByteBuf buffer) {
		this.guinum = buffer.readByte();

		int length = buffer.readInt();
		if(length>0){
			ByteBuf bytes = buffer.readBytes(length);
			this.comp = PacketUtil.bytesToNBT(bytes);
		}
//		switch(this.guinum){
//		case guiNumber.CHEST:
//			this.entityid = buffer.readInt();
//			this.pos = XYZPos.readFromBuffer(buffer);
////			this.pos = new XYZPos(0,0,0);
////			this.pos.x = buffer.readInt();
////			this.pos.y = buffer.readInt();
////			this.pos.z = buffer.readInt();
//			break;
//		case guiNumber.BARTERING:
//		case guiNumber.SMITH:
//			this.entityid = buffer.readInt();
//			break;
//		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		buffer.writeByte(this.guinum);
		if(this.comp!=null){

			byte[] bytes = PacketUtil.nbtToBytes(comp);
			buffer.writeInt(bytes.length);
			buffer.writeBytes(bytes);
		}else{
			buffer.writeInt(-1);
		}
//		switch(this.guinum){
//		case guiNumber.CHEST:
//			buffer.writeInt(entityid);
//			this.pos.writeToBuffer(buffer);
////			buffer.writeInt(pos.x);
////			buffer.writeInt(pos.y);
////			buffer.writeInt(pos.z);
//			break;
//		case guiNumber.SMITH:
//			buffer.writeInt(this.entityid);
//			break;
//		case guiNumber.BARTERING:
//			buffer.writeInt(this.entityid);
//			break;
//		}

	}

	public static class Handler implements IMessageHandler<PacketOpenGui,IMessage>{

		@Override
		public IMessage onMessage(final PacketOpenGui message, MessageContext ctx) {
			if(ctx.side.isServer()){
				final EntityPlayerMP ep = ctx.getServerHandler().playerEntity;

//				EntityVillager villager = null;
//				XYZPos p = null;
//				switch(UnsagaMod.GuiType.fromMeta(message.getGuiNumber())){
				XYZPos p = XYZPos.readFromNBT(message.comp);
				UnsagaMod.logger.trace(this.getClass().getName(), p);
//				UnsagaGui.Type.fromMeta(message.getGuiNumber()).onGuiOpen(message,ctx,ep);
				FMLNetworkHandler.openGui(ep, UnsagaMod.instance, UnsagaGui.Type.fromMeta(message.getGuiNumber()).getMeta(), ep.worldObj,p.getX(),p.getY(),p.getZ());

//				case EQUIPMENT:
//					FMLNetworkHandler.openGui(ep, UnsagaMod.instance, GuiType.EQUIPMENT.getMeta(), ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				default:
//					break;

//				switch(message.getGuiNumber()){
//				case guiNumber.EQUIP:
//
//					FMLNetworkHandler.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.EQUIP, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				case guiNumber.SMITH:
//					Entity entity  = ep.worldObj.getEntityByID(message.getEntityID());
//					Unsaga.debug(villager);
//					if(entity instanceof EntityVillager){
//						 villager = (EntityVillager)entity;
//						ExtendedPlayerData.getData(ep).setMerchant(villager);
//						XYZPos pos = XYZPos.entityPosToXYZ(ep);
//
//						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.SMITH, ep.worldObj,pos);
//					}else{
////						XYZPos pos = message.getPos();
////						//Unsaga.debug(ep.worldObj.getBlock(pos.x, pos.y, pos.z));
////						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.SMITH, ep.worldObj,pos);
//					}
//					break;
//				case guiNumber.BARTERING:
//					villager = (EntityVillager) ep.worldObj.getEntityByID(message.getEntityID());
//					if(villager!=null){
//						ExtendedPlayerData.getData(ep).setMerchant(villager);
//						XYZPos pos = XYZPos.entityPosToXYZ(ep);
//						HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.BARTERING, ep.worldObj,pos);
//					}
//					break;
//				case guiNumber.BLENDER:
//					FMLNetworkHandler.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.BLENDER, ep.worldObj, (int)ep.posX, (int)ep.posY, (int)ep.posZ);
//					break;
//				case guiNumber.CHEST:
//					int entityid = message.getEntityID();
//					if(entityid==-1){
//						XYZPos pos = message.getPos();
//						TileEntity te = ep.worldObj.getTileEntity(pos);
//						if(te instanceof TileEntityUnsagaChest){
//							ExtendedPlayerData.getData(ep).setInteractingChest((TileEntityUnsagaChest) te);
//							HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.CHEST, ep.worldObj, pos);
//						}
//					}else{
//						Entity en = ep.worldObj.getEntityByID(message.getEntityID());
//
//						if(en instanceof EntityUnsagaChest){
//							XYZPos pos = XYZPos.entityPosToXYZ(en);
//							ExtendedPlayerData.getData(ep).setInteractingChest((EntityUnsagaChest) en);
//							HSLibs.openGui((EntityPlayer) ep, Unsaga.instance, Unsaga.guiNumber.CHEST, ep.worldObj, pos);
//						}
//					}
//
//
//					break;
//				case guiNumber.CARRIER:
//					p = XYZPos.entityPosToXYZ(ep);
//					HSLibs.openGui(ep, Unsaga.instance, guiNumber.CARRIER, ep.worldObj, p);
//					break;
//				case guiNumber.SKILLPANEL:
//					break;
//				case guiNumber.TABLET:
//					p = XYZPos.entityPosToXYZ(ep);
//					HSLibs.openGui(ep, Unsaga.instance, guiNumber.TABLET, ep.worldObj, p);
//					break;
//				}
			}
			return null;
		}

	}
}
