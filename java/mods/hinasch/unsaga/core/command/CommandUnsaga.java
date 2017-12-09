package mods.hinasch.unsaga.core.command;

import mods.hinasch.lib.entity.ModifierHelper;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.net.packet.PacketDebugPos;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.villager.UnsagaVillagerCapability;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class CommandUnsaga extends CommandBase{

	@Override
	public String getCommandName() {
		// TODO 自動生成されたメソッド・スタブ
		return "unsagamod";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO 自動生成されたメソッド・スタブ
		return "/unsagamod delete";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length>=1){
			String str = args[0];

//			if(str.equals("mods")){
//				if(sender instanceof EntityPlayer){
//					EntityPlayer ep = (EntityPlayer) sender;
//					ChatHandler.sendChatToPlayer(ep, "Loaded Heat And Climate Mod:"+HSLib.plugin().isLoadedHAC());
//				}
//
//			}
			if(str.equals("delete")){
				if(sender instanceof EntityPlayer){
					EntityPlayer ep = (EntityPlayer) sender;
					ep.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, ep.getEntityBoundingBox().expand(30.0D, 30.0D, 30.0D))
					.forEach(living ->{
						if(living!=ep){
							living.setDead();
						}

					});
				}
			}
//			if(str.equals("togglespark")){
//				boolean b = UnsagaMod.configHandler.isAlwaysSparkling();
//				UnsagaMod.configHandler.enableAlwaysSparkling(!b);
//				ChatHandler.sendChatToPlayer((EntityPlayer) sender, "Toggled Always Spark Mode.");
//			}

			if(str.equals("pos")){

					int x = Integer.valueOf(args[1]);
					int y = Integer.valueOf(args[2]);
					int z = Integer.valueOf(args[3]);

					BlockPos pos = new BlockPos(x,y,z);
					UnsagaMod.packetDispatcher.sendTo(new PacketDebugPos(pos), (EntityPlayerMP) sender);

			}
			if(str.equals("status")){
				StringBuilder builder = new StringBuilder("");
				if(sender instanceof EntityPlayer){
					EntityPlayer ep = (EntityPlayer) sender;
					double strmodifier = ep.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
					builder.append("Attack:"+strmodifier+"/");
					ModifierHelper.dumpModifiers(SharedMonsterAttributes.ATTACK_DAMAGE, ep);
					for(General gen:General.values()){
						double at = ep.getEntityAttribute(AdditionalStatus.GENERALS.get(gen)).getAttributeValue();
						builder.append(gen.getName()+":"+at+"/");
						ModifierHelper.dumpModifiers(AdditionalStatus.GENERALS.get(gen), ep);
					}
					for(Sub sub:Sub.values()){
						if(sub!=Sub.NONE){
							double at = ep.getEntityAttribute(AdditionalStatus.SUBS.get(sub)).getAttributeValue();
							builder.append(sub.getName()+":"+at+"/");
						}
						ModifierHelper.dumpModifiers(AdditionalStatus.SUBS.get(sub), ep);
					}
					ChatHandler.sendChatToPlayer(ep,builder.toString());
				}
			}
			if(str.equals("stock")){
				if(sender instanceof EntityPlayer){
					EntityPlayer ep = (EntityPlayer) sender;
					ep.worldObj.getEntitiesWithinAABB(EntityVillager.class, ep.getEntityBoundingBox().expand(30.0D, 30.0D, 30.0D))
					.forEach(living ->{
						if(UnsagaVillagerCapability.adapter.hasCapability(living)){
							long recent = UnsagaVillagerCapability.adapter.getCapability(living).getRecentStockedTime();
							UnsagaVillagerCapability.adapter.getCapability(living).setStockedTime(recent - 24000);
						}

					});
				}
			}
//			if(str.equals("distribution") && args.length>2){
//				int level = this.parseInt(args[1],0,999);
//				if(sender instanceof EntityPlayer){
//					EntityPlayer ep = (EntityPlayer) sender;
//					ep.worldObj.getEntitiesWithinAABB(EntityVillager.class, ep.getEntityBoundingBox().expand(30.0D, 30.0D, 30.0D))
//					.forEach(villager ->{
//						if(UnsagaVillager.hasCapability(villager)){
//							UnsagaVillager.getCapability(villager).setDistributionLevel(level);
//							ChatHandler.sendChatToPlayer(ep, "set distributionLevel to "+level);
//						}
//					});
//				}
//			}
//			if(str.equals("checkevents")){
//
//				final String type = args[1];
//
//				String message = new Supplier<String>(){
//
//					@Override
//					public String get() {
//						if(type.equals("update")){
//							String eventsUpdate = "update events:";
//
//							for(ILivingUpdateEvent ev:HSLibEvents.livingUpdate.getEvents()){
//
//								eventsUpdate += ev.getName()+",";
//
//							}
//							return eventsUpdate;
//						}
//						if(type.equals("hurt")){
//							String eventsHurt = "hurt events pre:";
//							for(ILivingHurtEvent ev:HSLibEvents.livingHurt.getEventsPre()){
//
//								eventsHurt += ev.getName()+",";
//
//							}
//							eventsHurt += ">>";
//							for(ILivingHurtEvent ev:HSLibEvents.livingHurt.getEventsMiddle()){
//
//								eventsHurt += ev.getName()+",";
//
//							}
//							eventsHurt += ">>";
//							for(ILivingHurtEvent ev:HSLibEvents.livingHurt.getEventsPost()){
//
//								eventsHurt += ev.getName()+",";
//
//							}
//							return eventsHurt;
//						}
//						return "";
//					}
//				}.get();
//
//
//
//
//				if(sender instanceof EntityPlayer){
//					EntityPlayer ep  = (EntityPlayer) sender;
//					ChatHandler.sendChatToPlayer(ep, message);
//				}
//			}
//			if(str.equals("info")){
//				UnsagaMod.logger.trace(this.getClass().getName(), "called");
//				if(sender instanceof EntityLivingBase){
//					EntityLivingBase living = (EntityLivingBase) sender;
//					String message = "HealAmount:"+AbilityHelper.getHealWeight((EntityPlayer) living);
//					List<EntityVillager> list = living.worldObj.getEntitiesWithinAABB(EntityVillager.class, living.getEntityBoundingBox().expand(50D, 50D, 50D));
//					if(!list.isEmpty()){
//						OptionalInt dismax = list.stream().mapToInt(input ->{
//							if(UnsagaVillager.hasCapability(input)){
//								return UnsagaVillager.getCapability(input).getDistributionLevel();
//							}
//							return 0;
//						}
//						).max();
//
//						if(dismax.isPresent()){
//							message += " DistributionLevel:"+dismax.getAsInt();
//						}
//					}
//
//					if(sender instanceof EntityPlayer){
//						EntityPlayer ep  = (EntityPlayer) sender;
//						ChatHandler.sendChatToPlayer(ep, message);
//					}
//				}
//
//			}
		}

	}

}
