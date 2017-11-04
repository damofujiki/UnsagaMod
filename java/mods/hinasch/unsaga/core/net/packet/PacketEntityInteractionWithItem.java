//package mods.hinasch.unsaga.core.net.packet;
//
//import io.netty.buffer.ByteBuf;
//import mods.hinasch.lib.iface.IIntSerializable;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.AbilityRegistry;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool;
//import mods.hinasch.unsaga.core.item.weapon.base.IComponentUnsagaTool;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public class PacketEntityInteractionWithItem implements IMessage{
//
//	public static enum Type implements IIntSerializable{WEAPON(0),MARTIAL_ARTS(1);
//
//		int meta;
//
//		private Type(int meta){
//			this.meta = meta;
//		}
//		@Override
//		public int getMeta() {
//			// TODO 自動生成されたメソッド・スタブ
//			return meta;
//		}
//		public static Type fromMeta(int meta){
//			return HSLibs.fromMeta(Type.values(), meta);
//		}
//	};
//	int targetID;
//	float charge;
//	NBTTagCompound nbt;
//
//	public PacketEntityInteractionWithItem(){
//
//	}
//
//	public PacketEntityInteractionWithItem(NBTTagCompound nbt){
//		this.nbt = nbt;
//	}
//
//	public static PacketEntityInteractionWithItem createMartialArts(Entity target,Waza waza){
//		NBTTagCompound tag = UtilNBT.compound();
//
//		if(target!=null){
//			tag.setInteger("target", target.getEntityId());
//		}
//		tag.setInteger("type", Type.MARTIAL_ARTS.getMeta());
//		tag.setString("waza", waza.getKey().toString());
//		return new PacketEntityInteractionWithItem(tag);
//	}
//	public static PacketEntityInteractionWithItem create(float charge,RayTraceResult result){
//		Entity target = null;
//		NBTTagCompound nbt = UtilNBT.compound();
//		if(result!=null){
//
//			if(result.entityHit!=null){
//				nbt.setInteger("target", result.entityHit.getEntityId());
//			}
//
//			nbt.setFloat("charge", charge);
//			nbt.setInteger("type", Type.WEAPON.getMeta());
//		}
//		return new PacketEntityInteractionWithItem(nbt);
//	}
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		// TODO 自動生成されたメソッド・スタブ
//
//		int length = buf.readInt();
//		ByteBuf bytes = buf.readBytes(length);
//		this.nbt = PacketUtil.bytesToNBT(bytes);
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		// TODO 自動生成されたメソッド・スタブ
//		byte[] bytes = PacketUtil.nbtToBytes(nbt);
//		buf.writeInt(bytes.length);
//		buf.writeBytes(bytes);
//	}
//
//	public static class Handler implements IMessageHandler<PacketEntityInteractionWithItem,IMessage>{
//
//		@Override
//		public IMessage onMessage(PacketEntityInteractionWithItem message, MessageContext ctx) {
//			if(ctx.side.isServer()){
//				UnsagaMod.logger.trace(this.getClass().getName(), "パケットきました");
//				NBTTagCompound comp = message.nbt;
//				EntityPlayer player = ctx.getServerHandler().playerEntity;
//				Entity target = player.getEntityWorld().getEntityByID(comp.getInteger("target"));
//				if(target!=null){
//					switch(Type.fromMeta(comp.getInteger("type"))){
//					case MARTIAL_ARTS:
//						Waza waza = (Waza) AbilityRegistry.instance().getAbility(comp.getString("waza"));
//						WazaPerformer performer = new WazaPerformer(player.getEntityWorld(), player, waza, null);
//						performer.setTarget(target);
//						performer.perform();
//						break;
//					case WEAPON:
//						ItemStack is = player.getHeldItemMainhand();
//						if(is!=null && is.getItem() instanceof IComponentUnsagaTool && comp.hasKey("target")){
//							UnsagaMod.logger.trace(this.getClass().getName(), "コンポーネントありました");
//							if(target!=null){
//								UnsagaMod.logger.trace(this.getClass().getName(), "ターゲットありました");
//								float f = comp.getFloat("charge");
//								ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(is, player.getEntityWorld(), player, f, target);
//								ComponentUnsagaTool component = ((IComponentUnsagaTool)is.getItem()).getToolComponent();
//								ActionResult<WazaPerformer> result = component.findSkillInvoker(container, WazaEffect.Type.STOPPED_USING);
//								if(result.getType()==EnumActionResult.SUCCESS){
//									result.getResult().perform();
//								}
//							}
//
//						}
//						break;
//					default:
//						break;
//
//					}
//				}
//
//
//
//
//			}
//			return null;
//		}
//
//	}
//}
