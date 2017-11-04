package mods.hinasch.unsaga.damage;

import com.mojang.realmsclient.util.Pair;

public class PairDamage extends Pair<Float,Float>{

	protected PairDamage(Float first, Float second) {
		super(first, second);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public float hp(){
		return this.first();
	}

	public float lp(){
		return this.second();
	}
	public static PairDamage of(float hp,float lp){
		return new PairDamage(hp,lp);

	}
}
