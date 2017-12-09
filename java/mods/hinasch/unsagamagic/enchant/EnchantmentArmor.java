package mods.hinasch.unsagamagic.enchant;

public class EnchantmentArmor extends EnchantmentProperty{

	public EnchantmentArmor(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public float getArmorModifer(int level){
		return 0.1F*level;
	}
}
