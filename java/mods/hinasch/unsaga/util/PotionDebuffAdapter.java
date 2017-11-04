package mods.hinasch.unsaga.util;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.debuff.DebuffBase;
import mods.hinasch.lib.debuff.DebuffEffectBase;
import mods.hinasch.lib.debuff.DebuffHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.misc.Tuple;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionDebuffAdapter<T> {

	public T debuff;

	@Override
	public int hashCode() {
		return debuff.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PotionDebuffAdapter){
			return ((PotionDebuffAdapter) obj).equalsInner(this);
		}
		return super.equals(obj);
	}

	public PotionDebuffAdapter(T in){
		this.debuff = in;
	}

	public T getEffect(){
		return this.debuff;
	}

	public void addEffect(EntityLivingBase living){
		PotionDebuffAdapter.addEffect(living, this.getEffectSafe());
	}
	public static class PotionEffectDebuffPair extends Tuple<PotionEffect,DebuffEffectBase>{

		public PotionEffectDebuffPair(PotionEffect f, DebuffEffectBase s) {
			super(f, s);
			// TODO 自動生成されたコンストラクター・スタブ
		}

	}
	/** 型安全版*/
	public Tuple<PotionEffect,DebuffEffectBase> getEffectSafe(){
		if(this.debuff instanceof PotionEffect){
			return Tuple.<PotionEffect,DebuffEffectBase>of((PotionEffect)debuff, null);
		}
		if(this.debuff instanceof DebuffEffectBase){
			return Tuple.<PotionEffect,DebuffEffectBase>of(null, (DebuffEffectBase)debuff);
		}
		return null;
	}
	public boolean isBadEffect(){
		if(debuff instanceof PotionEffect){
			return ((PotionEffect) debuff).getPotion().isBadEffect();
		}
		if(debuff instanceof DebuffEffectBase){
			return ((DebuffEffectBase) debuff).getDebuff().isBadEffect();
		}
		return false;
	}

	public int getAmplifier(){
		if(debuff instanceof PotionEffect){
			return ((PotionEffect) debuff).getAmplifier();
		}
		return 1;
	}

	public int getDuration(){
		if(debuff instanceof PotionEffect){
			if(((PotionEffect) debuff).getDuration()>0){
				return ((PotionEffect) debuff).getDuration()/20;
			}

		}
		if(debuff instanceof DebuffEffectBase){
			return ((DebuffEffectBase) debuff).getRemaining();
		}
		return 0;
	}
	public static <K> PotionDebuffAdapter createNew(K id,int duration,int amplifier){
		if(id instanceof Potion){
			return createNew(Tuple.<Potion,DebuffBase>of((Potion)id, null),duration,amplifier);
		}
		if(id instanceof DebuffBase){
			return createNew(Tuple.<Potion,DebuffBase>of(null, (DebuffBase)id),duration,amplifier);
		}
		return null;
	}

	/** 型安全*/
	public static PotionDebuffAdapter createNew(Tuple<Potion,DebuffBase> id,int duration,int amplifier){
		if(id.first!=null){
			return new PotionDebuffAdapter(new PotionEffect((Potion)id.first,duration,amplifier));
		}
		if(id.second!=null){
			return new PotionDebuffAdapter(((DebuffBase) id.second).createLivingDebuff(duration));
		}
		return null;
	}

	public PotionDebuffAdapter clone(int duration,int amplifier){
		if(this.getEffectSafe().first!=null){
			return new PotionDebuffAdapter(new PotionEffect((Potion)this.getEffectSafe().first.getPotion(),ItemUtil.getPotionTime(duration),amplifier));
		}
		if(this.getEffectSafe().second!=null){
			return new PotionDebuffAdapter(((DebuffBase) this.getEffectSafe().second.getDebuff()).createLivingDebuff(duration));
		}
		return null;
	}
	public static List<PotionDebuffAdapter> getActivePotionEffects(EntityLivingBase living){
		List<PotionDebuffAdapter> list = Lists.newArrayList();
		for(PotionEffect ef:living.getActivePotionEffects()){
			list.add(new PotionDebuffAdapter(ef));
		}
		for(DebuffEffectBase ef:DebuffHelper.getActiveDebuffs(living)){
			list.add(new PotionDebuffAdapter(ef));
		}
		return list;
	}
	public static void addEffect(EntityLivingBase living,Tuple<PotionEffect,DebuffEffectBase> debuffEffect){
		if(debuffEffect.first!=null){
			living.addPotionEffect((PotionEffect) debuffEffect.first);
		}
		if(debuffEffect.second!=null){
			DebuffHelper.addLivingDebuff(living, (DebuffEffectBase) debuffEffect.second);
		}
	}

	public static <V> void removeEffect(EntityLivingBase living,V debuff){
		if(debuff instanceof PotionEffect){
			living.removePotionEffect(((PotionEffect) debuff).getPotion());
		}
		if(debuff instanceof DebuffEffectBase){
			DebuffHelper.removeDebuff(living, ((DebuffEffectBase) debuff).getDebuff());
		}
	}

//	public static Set<PotionDebuffAdapter> potionDebuffToAdapterSet(Collection<Potion> potions,Collection<Debuff> debuffs){
//		Set<PotionDebuffAdapter> newSet = Sets.newHashSet();
//		for(Potion potion:potions){
//			newSet.add(PotionDebuffAdapter.createNew(Tuple.<Potion,DebuffBase>of(potion, null), 10, 10));
//		}
//		for(Debuff potion:debuffs){
//			newSet.add(PotionDebuffAdapter.createNew(Tuple.<Potion,DebuffBase>of(null, potion), 10, 10));
//		}
//		return newSet;
//	}
	public boolean equalsInner(PotionDebuffAdapter in){
		return this.getEffect()==in.getEffect();
	}
}
