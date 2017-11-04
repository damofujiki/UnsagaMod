package mods.hinasch.unsaga.lp;

import com.mojang.realmsclient.util.Pair;

import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;

public interface ILPAttackStrength {

	public LPStrPair getLPAttackStrength();
	public General getDamageType();

	public static class LPStrPair extends Pair<Float,Integer>{

		protected LPStrPair(Float first, Integer second) {
			super(first, second);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public static LPStrPair of(Float first, Integer second){
			return new LPStrPair(first,second);
		}
	}
}
