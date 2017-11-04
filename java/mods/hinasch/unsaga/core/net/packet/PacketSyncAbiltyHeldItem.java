//package mods.hinasch.unsaga.core.net.packet;
//
//import com.google.common.collect.Lists;
//
//import io.netty.buffer.ByteBuf;
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.Ability;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.ability.AbilityRegistry;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public class PacketSyncAbiltyHeldItem implements IMessage{
//
//	NBTTagCompound ability;
//
//	public static PacketSyncAbiltyHeldItem create(Ability ab){
//		return new PacketSyncAbiltyHeldItem(ab);
//	}
//	public PacketSyncAbiltyHeldItem(){
//
//	}
//
//	protected PacketSyncAbiltyHeldItem(Ability ab){
//
//		this.ability = UtilNBT.compound();
//		this.ability.setString("ab", ab.getKey().toString());
//	}
//	@Override
//	public void fromBytes(ByteBuf buf) {
//		int length = buf.readInt();
//
//		ByteBuf bytes = buf.readBytes(length);
//
//		this.ability = PacketUtil.bytesToNBT(bytes);
//
//
//
//	}
//
//	@Override
//	public void toBytes(ByteBuf buf) {
//		byte[] bytes = PacketUtil.nbtToBytes(ability);
//		int length = bytes.length;
//		buf.writeInt(length);
//		buf.writeBytes(bytes);
//
//	}
//
//	public static class Handler implements IMessageHandler<PacketSyncAbiltyHeldItem,IMessage>{
//
//		@Override
//		public IMessage onMessage(PacketSyncAbiltyHeldItem message, MessageContext ctx) {
//
//			UnsagaMod.logger.trace("packet "+ctx.side.toString());
//			if(ctx.side.isClient()){
//				Ability ability = AbilityRegistry.instance().getAbility(message.ability.getString("ab"));
//				if(ability==null){
//					UnsagaMod.logger.trace("ability is null");
//					return null;
//				}
//				UnsagaMod.logger.trace(this.getClass().getName()+ability.name);
//				EntityPlayer ep = ClientHelper.getPlayer();
//				if(ep.getHeldItemMainhand()!=null){
//					ItemStack stack = ep.getHeldItemMainhand();
//					if(AbilityHelper.hasCapability(stack)){
//						AbilityHelper.getCapability(stack).setAbilityList(Lists.newArrayList(ability));
//					}
//
//
//				}
//			}
//			return null;
//		}
//
//	}
//}
