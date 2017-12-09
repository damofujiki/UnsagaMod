package mods.hinasch.lib.core.event;

import java.util.ArrayList;
import java.util.List;

import mods.hinasch.lib.iface.LivingHurtEventBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventLivingHurt {
	/** ダメージソース決定前。ダメージソースを返すとそのダメージソースをここのインスタンスで保持。*/
	protected List<LivingHurtEventBase> eventsPre;

	/** ダメージソース決定後。ダメージソースを返しても何もない。 */
	protected List<LivingHurtEventBase> eventsMain;
	/** 最後のさいご、LPモード時のHPリミッターなどはここでやる*/
	protected List<LivingHurtEventBase> eventsPost;
	boolean flagBowAttack;

//	protected DamageSourceUnsaga unsagaDamageSource;

//	/** LPが減らないダメージ */
//	protected static final Set<DamageSource> NO_LPDAMAGE_SET = Sets.newHashSet(DamageSource.drown,DamageSource.inFire,DamageSource.onFire,DamageSource.starve,DamageSource.wither);

	public EventLivingHurt(){
		this.eventsPre = new ArrayList();
//		this.eventsPre.add(new LivingHurtEventZombieDebuffs());
//		this.eventsPre.add(new LivingHurtEventAbility());
//		this.eventsPre.add(new LivingHurtEventSwordArts());
//		this.eventsPre.add(new LivingHurtEventArrowArts());
//		this.eventsPre.add(new LivingHurtEventDebuffs());
//		this.eventsPre.add(new LivingHurtEventSpellBless());
//		this.eventsPre.add(new LivingHurtArrowHit());
//		this.eventsPre.add(new LivingHurtEventSetDamageType());



		this.eventsMain = new ArrayList();
//		this.eventsMain.add(new LivingHurtEventArmorBless());
//		this.eventsMain.add(new LivingHurtEventShield());
//		this.eventsMain.add(new LivingHurtEventPunchDamage());
//		this.eventsMain.add(new LivingHurtEventDamageFromType());
//		this.eventsMain.add(new LivingHurtEventSkillPanels());

		this.eventsPost = new ArrayList();
//		this.eventsPost.add(new LivingHurtEventLifeLimitter());


	}

	public void sortAll(){
		this.eventsPre.sort(null);
		this.eventsMain.sort(null);
		this.eventsPost.sort(null);
	}
	public List<LivingHurtEventBase> getEventsPre(){
		return this.eventsPre;
	}

	public List<LivingHurtEventBase> getEventsMiddle(){
		return this.eventsMain;
	}

	public List<LivingHurtEventBase> getEventsPost(){
		return this.eventsPost;
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent e){

		DamageSource ds = e.getSource();
		for(LivingHurtEventBase ev:this.eventsPre){
//			UnsagaMod.logger.trace("EventPre", ev.getName(),ds);
			if(ev.apply(e, ds)){
				ds = ev.process(e, ds);
			}
		}
		for(LivingHurtEventBase ev:this.eventsMain){
//			UnsagaMod.logger.trace("EventMain", ev.getName(),ds);
			if(ev.apply(e, ds)){
				ds = ev.process(e, ds);
			}
		}
		for(LivingHurtEventBase ev:this.eventsPost){
//			UnsagaMod.logger.trace("EventPost", ev.getName(),ds);
			if(ev.apply(e, ds)){
				ds = ev.process(e, ds);
			}
		}
	}
}
