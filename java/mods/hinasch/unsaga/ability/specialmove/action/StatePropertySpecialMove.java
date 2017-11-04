package mods.hinasch.unsaga.ability.specialmove.action;

import java.util.Optional;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.State;
import mods.hinasch.unsaga.core.entity.StateProperty;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StatePropertySpecialMove extends StateProperty{

	public StatePropertySpecialMove(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public StateSpecialMove createState(){
		return new StateSpecialMove();
	}

	public static class StateSpecialMove extends State{

		boolean isSpecialMoveProgress = false;

		boolean isCancelFall = false;
		int hurtCancelTick = 0;

		boolean hasSetExplode = false;
		int explodeTime = 0;

		EnumParticleTypes type = EnumParticleTypes.CRIT;
		boolean isFallParticle = false;

		public StateSpecialMove() {
			super(false);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public void setSpecialMoveProgress(boolean par1){
			this.isSpecialMoveProgress = par1;

		}

		public boolean isSpecialMoveProgress(){
			return this.isSpecialMoveProgress;
		}
		public boolean isCancelFall(){
			return this.isCancelFall;
		}

		public boolean isFallParticle(){
			return this.isFallParticle;
		}
		public void setFallParticle(boolean par1){
			this.isFallParticle = par1;
		}
		public boolean isCancelHurt(){
			return this.hurtCancelTick>0;
		}

		public void setCancelFall(boolean par1){
			this.isCancelFall = par1;

		}

		public void setCancelHurt(int time){
			this.hurtCancelTick = time;
		}

		public void setScheduledExplode(int time){
			this.explodeTime = time;
			this.hasSetExplode = true;
		}

		public void cancelExplode(){
			this.explodeTime = 0;
			this.hasSetExplode = false;
		}

		public void decrExplodeTime(){
			this.explodeTime -= 1;
			if(this.explodeTime<0){
				this.explodeTime = 0;
			}
		}
	}

	public static class Events{

		@SubscribeEvent
		public void onFall(LivingFallEvent e){
			if(EntityStateCapability.adapter.hasCapability(e.getEntityLiving())){
				StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(e.getEntityLiving()).getState(StateRegistry.instance().stateSpecialMove);
				if(state.isCancelFall()){
					e.setCanceled(true);
				}
			}
		}

		@SubscribeEvent
		public void onAttack(LivingAttackEvent e){
			if(EntityStateCapability.adapter.hasCapability(e.getEntityLiving())){
				StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(e.getEntityLiving()).getState(StateRegistry.instance().stateSpecialMove);
				if(state.isCancelHurt()){
					e.setCanceled(true);
				}
			}
		}
	}

	public static Optional<StateSpecialMove> getState(EntityLivingBase e){
		if(EntityStateCapability.adapter.hasCapability(e)){
			StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(e).getState(StateRegistry.instance().stateSpecialMove);
			return Optional.of(state);
		}
		return Optional.empty();
	}
	public static void register(){
		HSLibs.registerEvent(new Events());
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(EntityStateCapability.adapter.hasCapability(e.getEntityLiving())){
					StateSpecialMove state = (StateSpecialMove) EntityStateCapability.adapter.getCapability(e.getEntityLiving()).getState(StateRegistry.instance().stateSpecialMove);
					if(state.isCancelFall() && e.getEntityLiving().onGround){
						state.setCancelFall(false);
					}

					if(state.isCancelFall()){
						state.setFallParticle(true);
					}
					if(state.isFallParticle()){
						World world = e.getEntityLiving().getEntityWorld();
						ParticleHelper.MovingType.FLOATING.spawnParticle(world, XYZPos.createFrom(e.getEntityLiving()), state.type, world.rand	, 10, 0.05D);
					}
					if(state.isFallParticle && e.getEntityLiving().onGround){
						state.setFallParticle(false);
					}

					if(state.isCancelFall()){
						e.getEntityLiving().fallDistance = 0;
					}

					if(e.getEntityLiving().ticksExisted % 5 ==0){
						if(state.hasSetExplode){
							state.decrExplodeTime();
							if(state.explodeTime<=0){
								state.cancelExplode();
								if(e.getEntityLiving().onGround){
									XYZPos pos = XYZPos.createFrom(e.getEntityLiving());
									WorldHelper.createExplosionSafe(e.getEntityLiving().getEntityWorld(),null,pos, 3, true);
								}

							}
						}
					}
				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "expire fallcancel";
			}}
		);
	}
}
