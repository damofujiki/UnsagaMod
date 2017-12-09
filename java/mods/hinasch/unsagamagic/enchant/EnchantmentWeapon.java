package mods.hinasch.unsagamagic.enchant;

public class EnchantmentWeapon extends EnchantmentProperty{

	final float baseModifier;
	public EnchantmentWeapon(String name,float base) {
		super(name);
		this.baseModifier = base;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public float getAttackModifier(int level){
		return baseModifier*level;
	}
}
