package mods.hinasch.unsaga.core.net.packet;

import io.netty.buffer.ByteBuf;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsagamagic.item.ItemSpellBook;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketSyncActionPerform implements IMessage{

	int entityid;
	public PacketSyncActionPerform(){

	}

	public PacketSyncActionPerform(EntityLivingBase performer){
		this.entityid = performer.getEntityId();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		int id = buf.readInt();

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO 自動生成されたメソッド・スタブ
		buf.writeInt(this.entityid);
	}

	public int getEntityID(){
		return this.entityid;
	}

	public static class Handler implements IMessageHandler<PacketSyncActionPerform,IMessage>{

		@Override
		public IMessage onMessage(PacketSyncActionPerform message, MessageContext ctx) {
			if(ctx.side==Side.SERVER){
				World world = ctx.getServerHandler().playerEntity.getEntityWorld();
				EntityPlayer ep = ctx.getServerHandler().playerEntity;
				ItemStack held = ep.getHeldItemMainhand();
				if(ItemUtil.isItemStackPresent(held) && held.getItem() instanceof ItemSpellBook){
					ItemSpellBook item = (ItemSpellBook) held.getItem();
					item.processCasting(world, ep, held);
				}
			}
			return null;
		}

	}
}
