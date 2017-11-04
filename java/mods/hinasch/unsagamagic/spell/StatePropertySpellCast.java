package mods.hinasch.unsagamagic.spell;

import java.util.Optional;

import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.common.specialaction.IActionPerformer.TargetType;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.IEntityState;
import mods.hinasch.unsaga.core.entity.State;
import mods.hinasch.unsaga.core.entity.StateProperty;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.status.TargetHolderCapability;
import mods.hinasch.unsagamagic.spell.action.SpellCaster;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StatePropertySpellCast extends StateProperty{

	public StatePropertySpellCast(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public State createState(){
		return new StateCast();
	}

	public static StateCast getStateCast(EntityLivingBase el){
		return (StateCast) EntityStateCapability.adapter.getCapability(el).getState(StateRegistry.instance().stateSpell);
	}
	public static class StateCast extends State implements ISyncCapability{

		int castingTime = 0;
		Optional<SpellCaster> castingSpell = Optional.empty();
		Optional<BlockPos> spellPoint = Optional.empty();
		public StateCast() {
			super(false);

		}

		public void decrCastingTime(){
			this.castingTime -= 1;
			UnsagaMod.logger.trace("castingtime", this.castingSpell.get(),this.castingTime);
			if(this.castingTime<0){
				this.castingTime = 0;
			}
		}
		public void setCastingTime(int time){
			this.castingTime = time;
		}
		public int getCastingTime(){
			return this.castingTime;
		}
		public Optional<SpellCaster> getCastingSpell(){
			return this.castingSpell;
		}
		public void setCastingSpell(SpellCaster spell){
			if(spell==null){
				this.castingSpell = Optional.empty();
			}else{
				this.castingSpell = Optional.of(spell);
			}

		}
		public void setSpellPoint(BlockPos pos){
			this.spellPoint = Optional.of(pos);
		}

		public Optional<BlockPos> getSpellPoint(){
			return this.spellPoint;
		}

		@Override
		public NBTTagCompound getSendingData() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public void catchSyncData(NBTTagCompound nbt) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onPacket(PacketSyncCapability message, MessageContext ctx) {

			Entity host = ClientHelper.getWorld().getEntityByID(message.getArgs().getInteger("entityid"));
			String spellName = message.getArgs().getString("spellName");
			int targetID = message.getArgs().getInteger("targetID");
			int castTime = message.getArgs().getInteger("castTime");
			Spell spell = SpellRegistry.instance().get(spellName);
			Entity target = ClientHelper.getWorld().getEntityByID(targetID);
			if(spell!=null && target instanceof EntityLivingBase && host instanceof EntityLivingBase){
				SpellCaster caster = SpellCaster.ofEnemy(ClientHelper.getWorld(), (EntityLivingBase) host, spell);
				caster.playSound(XYZPos.createFrom(host), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, false);
				caster.setTargetType(caster.isBenefical() ? TargetType.OWNER : TargetType.TARGET);
    			TargetHolderCapability.adapter.getCapability(host).updateTarget((EntityLivingBase) target);
    			this.setCastingSpell(caster);
    			this.setCastingTime(castTime);
			}

		}

		@Override
		public String getIdentifyName() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}
	}


	public static void sendSyncMobCastToClient(EntityLivingBase mob,EntityLivingBase target,Spell spell,int castTime){
		if(mob!=null && target!=null){
			NBTTagCompound args = UtilNBT.compound();
			args.setInteger("entityid", mob.getEntityId());
			args.setString("name", StateRegistry.instance().stateSpell.getKey().getResourcePath());
			args.setInteger("targetID", target.getEntityId());
			args.setInteger("castTime", castTime);
			args.setString("spellName", spell.getKey().getResourcePath());
			IEntityState inst = EntityStateCapability.adapter.getCapability(mob);
			HSLib.core().getPacketDispatcher().sendToAllAround(PacketSyncCapability.create(EntityStateCapability.CAPA, inst, args),PacketUtil.getTargetPointNear(mob));
		}


	}

	public static void register(){
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(!(e.getEntityLiving() instanceof EntityPlayer)){
					if(StatePropertySpellCast.getStateCast(e.getEntityLiving()).getCastingTime()<=0
							&& StatePropertySpellCast.getStateCast(e.getEntityLiving()).getCastingSpell().isPresent()){
						SpellCaster caster = StatePropertySpellCast.getStateCast(e.getEntityLiving()).getCastingSpell().get();
						caster.broadCastMessage(HSLibs.translateKey("msg.unsaga.enemy.cast.end", e.getEntityLiving().getName(),caster.getActionProperty().getLocalized()));
						caster.cast();
						this.resetCast(e.getEntityLiving());
					}
					if(StatePropertySpellCast.getStateCast(e.getEntityLiving()).getCastingTime()>0){
						if(e.getEntityLiving().ticksExisted %3 ==0){
							StatePropertySpellCast.getStateCast(e.getEntityLiving()).decrCastingTime();

						}
						if(e.getEntityLiving().ticksExisted %5 ==0){
//							HSLib.core().getPacketDispatcher().sendToAllAround(PacketParticle.create(XYZPos.createFrom(e.getEntityLiving()), EnumParticleTypes.SPELL, 10), PacketUtil.getTargetPointNear(e.getEntityLiving()));
							ParticleHelper.MovingType.FLOATING.spawnParticle(e.getEntityLiving().getEntityWorld(), XYZPos.createFrom(e.getEntityLiving())
									, EnumParticleTypes.ENCHANTMENT_TABLE, e.getEntityLiving().getRNG(), 10, 0.05D);
						}
					}

				}

			}
		    private void resetCast(EntityLivingBase el){
		    	StatePropertySpellCast.getStateCast(el).setCastingSpell(null);
		    	StatePropertySpellCast.getStateCast(el).setCastingTime(0);
		    }
			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "enemy spell cast";
			}}
		);
	}
}
