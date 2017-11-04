package mods.hinasch.unsaga.core.event.livinghurt;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.UnsagaEntityAttributes;
import mods.hinasch.unsaga.lp.LPLogicManager;
import mods.hinasch.unsaga.lp.LifePoint.ILifePoint;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class LivingHurtEventLPProcess extends LivingHurtEventUnsagaBase{

	protected static final Set<DamageSource> NO_LPDAMAGE_SET = Sets.newHashSet(DamageSource.drown,DamageSource.inFire,DamageSource.onFire,DamageSource.starve,DamageSource.wither);

	Map<IParentContainer,Function<EventContainer,Float>> hooks = Maps.newHashMap();

	public Map<IParentContainer,Function<EventContainer,Float>> getHooks(){
		return this.hooks;
	}
	@Override
	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaMod.configHandler.isEnabledLifePointSystem();
	}

	@Override
	public DamageSource process(final LivingHurtEvent e, final DamageSourceUnsaga dsu) {

		ILifePoint lp = LPLogicManager.getCapability(e.getEntityLiving());
		final Entity attacker = dsu.getEntity();
		final EntityLivingBase victim = e.getEntityLiving();

		float lpAttackAmmount = new Supplier<Float>(){

			@Override
			public Float get() {
				float base = dsu.getStrLPHurt();
				/**
				 * フック用
				 */
				for(Function<EventContainer,Float> hook:hooks.values()){
					base = hook.apply(new EventContainer(e, dsu, base, dsu.getEntity()));
				}

				/**
				 * 毒ダメージは０に
				 */
				if(attacker==null && e.getSource().isMagicDamage()){
					base = 0;
				}

				/**
				 * あと一律ステータス異常でのﾀﾞﾒｰｼﾞは０に
				 */
				if(NO_LPDAMAGE_SET.contains(e.getSource())){
					base = 0;
				}
				/**
				 * 落下では致命傷に
				 */
				if(e.getSource()==DamageSource.fall){
					if(e.getAmount()>3){
						base = 1;
					}
					if(e.getAmount()>5){
						base = 2;
					}
					if(e.getAmount()>7){
						base = 3;
					}
					if(e.getAmount()>8){
						base = 6;
					}
				}
				/**
				 * 爆発でも致命傷に
				 */
				if(e.getSource().isExplosion()){
					if(e.getAmount()>3){
						base = 1;
					}
					if(e.getAmount()>5){
						base = 3;
					}
					if(e.getAmount()>7){
						base = 5;
					}
					if(e.getAmount()>9){
						base = 8;
					}

					/**
					 * 防御していれば半分以下に
					 */
					if(victim instanceof EntityPlayer && !(e.getSource().isMagicDamage() || e.getSource().isUnblockable() || e.getSource().isDamageAbsolute())){
						if(((EntityPlayer) victim).isActiveItemStackBlocking()){
							base *= 0.4F;

						}
					}
				}
				if(attacker instanceof EntityLivingBase){
					EntityLivingBase living = (EntityLivingBase) attacker;
					if(living.getEntityAttribute(UnsagaEntityAttributes.STRENGTH_LP)!=null){
						base *= living.getEntityAttribute(UnsagaEntityAttributes.STRENGTH_LP).getAttributeValue();
					}
					if(victim.getEntityAttribute(UnsagaEntityAttributes.REDUCE_LP)!=null){
						base *= victim.getEntityAttribute(UnsagaEntityAttributes.REDUCE_LP).getAttributeValue();
					}


				}

				if(HSLib.configHandler.isDebug()){
					EntityPlayer reciever = new Supplier<EntityPlayer>(){

						@Override
						public EntityPlayer get() {
							if(attacker instanceof EntityPlayer){
								return (EntityPlayer) attacker;
							}
							if(victim instanceof EntityPlayer){
								return (EntityPlayer) victim;
							}
							return null;
						}
					}.get();
					if(reciever!=null){
						ChatHandler.sendChatToPlayer(reciever, "最終的なLP Strength:"+base);
					}

				}
				return base;
			}
		}.get();
		/**
		 * LPを減らす量を決定
		 */
		float lphurtAmmount = LPLogicManager.getLPHurtAmount(e.getEntityLiving(), e.getAmount(),lpAttackAmmount,e.getEntityLiving().getRNG(),dsu);



		if(HSLib.configHandler.isDebug()){
			if(ClientHelper.getPlayer()!=null){
				if(ClientHelper.getPlayer().getDistanceToEntity(e.getEntityLiving())<20.0D){
					UnsagaMod.logger.trace("lp damage ammonut:"+lphurtAmmount);
				}
			}

		}



		/**
		 * 実際にLPを減らすプロセス
		 */
		LPLogicManager.processLPHurt(dsu.getEntity(), e.getEntityLiving(), lphurtAmmount);
		/**
		 * LPが０以上ならHPが１で止まるように
		 */
		if(lp.getLifePoint()>0){
			if(e.getEntityLiving().getHealth() - e.getAmount()<1.0F){
				if(e.getEntityLiving().getMaxHealth()>1.0){ //小スライム対策
					e.setAmount(e.getEntityLiving().getHealth()-1.0F);
				}

			}
		}
		return dsu;
	}

	@Override
	public String getName() {
		String childs = "";
		for(IParentContainer event:this.hooks.keySet()){
			childs += event.getParent().toString();
		}
		return "lp hurt process[childs:"+childs+"]";
	}

	public static interface IParentContainer<T>{

		public T getParent();
	}
	public static class EventContainer{
		public EventContainer(LivingHurtEvent parent, DamageSourceUnsaga damageSource, float baseDamage,
				Entity attacker) {
			super();
			this.parent = parent;
			this.damageSource = damageSource;
			this.baseDamage = baseDamage;
			this.attacker = attacker;
		}
		public LivingHurtEvent getParent() {
			return parent;
		}
		public DamageSourceUnsaga getDamageSource() {
			return damageSource;
		}
		public float getBaseDamage() {
			return baseDamage;
		}
		public Entity getAttacker() {
			return attacker;
		}

		public EntityLivingBase getVictim(){
			return this.parent.getEntityLiving();
		}
		protected LivingHurtEvent parent;
		protected DamageSourceUnsaga damageSource;
		protected float baseDamage;
		protected Entity attacker;

	}


}
