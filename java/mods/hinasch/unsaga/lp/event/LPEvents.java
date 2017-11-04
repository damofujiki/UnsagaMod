package mods.hinasch.unsaga.lp.event;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.event.EventEntityJoinWorld;
import mods.hinasch.lib.iface.LivingHurtEventBase;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.net.packet.PacketLP;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.lp.LPLogicManager;
import mods.hinasch.unsaga.lp.LPLogicProcessor;
import mods.hinasch.unsaga.lp.LifePoint;
import mods.hinasch.unsaga.lp.LifePoint.ILifePoint;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LPEvents {

	public static void register(){
		PacketSyncCapability.registerSyncCapability(LifePoint.SYNC_ID, LifePoint.CAPA);

		HSLibs.registerEvent(new LivingDeathEventLP());
		HSLib.core().events.livingHurt.getEventsPost().add(new LivingHurtEventBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSource dsu) {
				// TODO 自動生成されたメソッド・スタブ
				return dsu instanceof DamageSourceUnsaga;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "LP Decr";
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSource dsu) {
				LPLogicProcessor.tryDecrLP(e.getEntityLiving().getRNG(), e.getEntityLiving(), (DamageSourceUnsaga) dsu, e.getAmount());
				return dsu;
			}}
		);
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(UnsagaMod.configHandler.isEnabledLifePointSystem()){
					if(LPLogicManager.hasCapability(e.getEntityLiving())){
						ILifePoint capa = LPLogicManager.getCapability(e.getEntityLiving());
						if(capa.getHurtInterval()>0){
							if(e.getEntityLiving().ticksExisted % 20 * 12 == 0){
								capa.setHurtInterval(capa.getHurtInterval() -1);
							}
						}
					}
				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "LP Hurt Interval decr. Event";
			}}
		);
		EventEntityJoinWorld.addEvent(e ->{
			if(e.getEntity() instanceof EntityLivingBase && WorldHelper.isClient(e.getEntity().getEntityWorld())
					&& LifePoint.adapter.hasCapability((EntityLivingBase) e.getEntity())){
				if(e.getEntity() instanceof EntityPlayer){
					EntityLivingBase living = (EntityLivingBase) e.getEntity();
					UnsagaMod.packetDispatcher.sendToServer(PacketLP.createRequest(living));
				}
			}
		});

		//TODO : エンティティがスポーンした時も
	}
}
