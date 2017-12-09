package mods.hinasch.unsaga;

import mods.hinasch.lib.config.EventConfigChanged;
import mods.hinasch.lib.core.HSLibEvents;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.AbilityLearningEvent;
import mods.hinasch.unsaga.ability.specialmove.action.StatePropertySpecialMove;
import mods.hinasch.unsaga.core.event.EventOnBed;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
import mods.hinasch.unsaga.core.event.UnsagaMobDrops;
import mods.hinasch.unsaga.core.event.foodstats.EventReplaceFoodStats;
import mods.hinasch.unsaga.core.event.livingupdate.LivingUpdateLPRestoreEvent;
import mods.hinasch.unsaga.lp.event.LPEvents;
import mods.hinasch.unsaga.skillpanel.WorldSaveDataSkillPanel;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.status.AdditionalStatusEvents;
import mods.hinasch.unsaga.villager.UnsagaVillagerInteraction;

public class UnsagaModEvents {

	private static UnsagaModEvents INSTANCE;
	public static UnsagaModEvents instance(){
		if(INSTANCE==null){
			INSTANCE = new UnsagaModEvents();
		}
		return INSTANCE;
	}

	public void regiser(){


		LPEvents.register();
		AdditionalStatus.register();
		AdditionalStatusEvents.register();
		(new UnsagaMobDrops()).init();
		StatePropertySpecialMove.register();
		EventConfigChanged.instance().addConfigHandler(UnsagaMod.MODID, UnsagaMod.configHandler);
//		HSLib.core().events.livingHurt.getEventsMiddle().add(new ILivingHurtEvent(){
//
//			@Override
//			public boolean apply(LivingHurtEvent e, DamageSource dsu) {
//				UnsagaMod.logger.trace(this.getName(), "shsfhhsd1",e.getSource().getSourceOfDamage(),e.getSource().getEntity());
//				return e.getSource().getSourceOfDamage() instanceof EntityArrow;
//			}
//
//			@Override
//			public String getName() {
//				// TODO 自動生成されたメソッド・スタブ
//				return "arrow fix";
//			}
//
//			@Override
//			public DamageSource process(LivingHurtEvent e, DamageSource dsu) {
//				UnsagaMod.logger.trace(this.getName(), "shsfhhsd2");
//				EntityArrow arrow = (EntityArrow) e.getSource().getSourceOfDamage();
//				if(EntityStateCapability.adapter.hasCapability(arrow)){
//					UnsagaMod.logger.trace(this.getName(), "shsfhhsd3");
//					StateArrow state = (StateArrow) EntityStateCapability.adapter.getCapability(arrow).getState(StateRegistry.instance().stateArrow);
//					if(state.isCancelHurtRegistance()){
//						e.getEntityLiving().hurtResistantTime = 0;
//						e.getEntityLiving().hurtTime=0;
//					}
//				}
//				return dsu;
//			}}
//		);
		HSLibs.registerEvent(new EventOnBed());
		HSLibs.registerEvent(new EventToolTipUnsaga());
		HSLibs.registerEvent(new AbilityLearningEvent());
		HSLibs.registerEvent(new EventReplaceFoodStats());
		HSLibs.registerEvent(new UnsagaVillagerInteraction());
		HSLibs.registerEvent(new WorldSaveDataSkillPanel.SkillPanelSyncEvent());
		HSLibEvents.livingUpdate.getEvents().add(new LivingUpdateLPRestoreEvent());

	}
}
