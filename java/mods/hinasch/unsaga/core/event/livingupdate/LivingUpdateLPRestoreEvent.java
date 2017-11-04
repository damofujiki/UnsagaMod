package mods.hinasch.unsaga.core.event.livingupdate;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.core.net.packet.PacketLP;
import mods.hinasch.unsaga.lp.LPLogicManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class LivingUpdateLPRestoreEvent extends ILivingUpdateEvent{

	@Override
	public void update(LivingUpdateEvent e) {
		if(e.getEntityLiving() instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer) e.getEntityLiving();
			if(ep.getSleepTimer()==99){
				if(LPLogicManager.hasCapability(ep)){
					LPLogicManager.getCapability(ep).restoreLifePoint();
					int lp = LPLogicManager.getCapability(ep).getLifePoint();
					if(WorldHelper.isServer(ep.getEntityWorld())){
						UnsagaMod.packetDispatcher.sendTo(PacketLP.create(ep, lp), (EntityPlayerMP) ep);
					}
					ep.addStat(UnsagaModCore.instance().achievements.restoreLP);
					this.restoreSameTeamLP(ep);
				}
			}
		}

	}

	public void restoreSameTeamLP(final EntityPlayer ep){
		ep.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, ep.getEntityBoundingBox().expand(50.0D, 50.0D, 50.0D))
		.stream().filter(input -> HSLibs.isSameTeam(ep, input)).forEach(input ->{
			if(LPLogicManager.hasCapability(input)){
				LPLogicManager.getCapability(input).restoreLifePoint();
				int lp = LPLogicManager.getCapability(input).getLifePoint();
				ep.addStat(UnsagaModCore.instance().achievements.restoreOtherLP);
				UnsagaMod.packetDispatcher.sendTo(PacketLP.create(input, lp), (EntityPlayerMP) ep);
			}
		});
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "restore lp event";
	}

}
