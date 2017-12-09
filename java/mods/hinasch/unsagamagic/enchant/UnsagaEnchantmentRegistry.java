package mods.hinasch.unsagamagic.enchant;

import mods.hinasch.lib.registry.PropertyRegistry;
import net.minecraft.inventory.EntityEquipmentSlot;

public class UnsagaEnchantmentRegistry extends PropertyRegistry<EnchantmentProperty>{

	private static UnsagaEnchantmentRegistry INSTANCE;

	public static UnsagaEnchantmentRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaEnchantmentRegistry();
		}
		return INSTANCE;
	}

	protected UnsagaEnchantmentRegistry(){

	}
	public final EntityEquipmentSlot[] slots = {EntityEquipmentSlot.MAINHAND};
	public final EntityEquipmentSlot[] slots2 = {EntityEquipmentSlot.CHEST,EntityEquipmentSlot.CHEST.FEET,EntityEquipmentSlot.CHEST.HEAD,EntityEquipmentSlot.CHEST.LEGS};
//	public ImmutableMap<Integer,EnchantmentProperty> weaponBlessMap;
//	public ImmutableMap<Integer,EnchantmentProperty> armorBlessMap;
//	public ImmutableMap<Integer,EnchantmentProperty> sharpnessMap;
	public EnchantmentProperty weaponBless = new EnchantmentWeapon("weaponBless",0.5F);//.setEnchantmentSupplier(new EnchantmentWeaponBless(slots,1));
	public EnchantmentProperty armorBless = new EnchantmentArmor("armorBless");//.setEnchantmentSupplier(new EnchantmentArmorBless(slots2,1));
	public EnchantmentProperty sharpness = new EnchantmentWeapon("sharpness",1.0F);//.setEnchantmentSupplier(new EnchantmentSharpness(slots,1));
//	public EnchantmentProperty weaponBless2 = new EnchantmentProperty("weaponBless2").setEnchantmentSupplier(new EnchantmentWeaponBless(slots,2));
//	public EnchantmentProperty armorBless2 = new EnchantmentProperty("armorBless2").setEnchantmentSupplier(new EnchantmentArmorBless(slots2,2));
//	public EnchantmentProperty sharpness2 = new EnchantmentProperty("sharpness2").setEnchantmentSupplier(new EnchantmentSharpness(slots,2));
//	public EnchantmentProperty weaponBless3 = new EnchantmentProperty("weaponBless3").setEnchantmentSupplier(new EnchantmentWeaponBless(slots,3));
//	public EnchantmentProperty armorBless3 = new EnchantmentProperty("armorBless3").setEnchantmentSupplier(new EnchantmentArmorBless(slots2,3));
//	public EnchantmentProperty sharpness3 = new EnchantmentProperty("sharpness3").setEnchantmentSupplier(new EnchantmentSharpness(slots,3));
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ


	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	@Override
	protected void registerObjects() {

//		ImmutableMap.Builder<Integer,EnchantmentProperty> map = ImmutableMap.builder();
//		ImmutableMap.Builder<Integer,EnchantmentProperty> map2 = ImmutableMap.builder();
//		ImmutableMap.Builder<Integer,EnchantmentProperty> map3 = ImmutableMap.builder();
//		for(int i=0;i<3;i++){
//			map.put(i, new EnchantmentProperty("weaponBless"+String.valueOf(i+1)).setEnchantmentSupplier(new EnchantmentWeaponBless(slots,i+1)));
//			map2.put(i, new EnchantmentProperty("armorBless"+String.valueOf(i+1)).setEnchantmentSupplier(new EnchantmentArmorBless(slots,i+1)));
//			map3.put(i, new EnchantmentProperty("sharpness"+String.valueOf(i+1)).setEnchantmentSupplier(new EnchantmentSharpness(slots,i+1)));
//		}
//
//		this.weaponBlessMap = map.build();
//		this.armorBlessMap = map2.build();
//		this.sharpnessMap = map3.build();
//
//		for(int i=0;i<3;i++){
//			this.put(this.weaponBlessMap.get(i));
//			this.put(this.armorBlessMap.get(i));
//			this.put(this.sharpnessMap.get(i));
//
//			GameRegistry.register(this.weaponBlessMap.get(i).getEnchantment());
//			GameRegistry.register(this.armorBlessMap.get(i).getEnchantment());
//			GameRegistry.register(this.sharpnessMap.get(i).getEnchantment());
//		}

//		// TODO 自動生成されたメソッド・スタブ
		this.put(weaponBless);
		this.put(armorBless);
		this.put(sharpness);
//
//
//		GameRegistry.register(weaponBless.getEnchantment());
//		GameRegistry.register(armorBless.getEnchantment());
//		GameRegistry.register(sharpness.getEnchantment());
	}



//	public static class EnchantmentWeaponBless extends EnchantmentUnsaga implements IWeaponEnchant{
//
//		protected EnchantmentWeaponBless(EntityEquipmentSlot[] slots,int tier) {
//			super(EnumEnchantmentType.WEAPON, slots,tier);
//
//		}
//
//		@Override
//	    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
//	    {
//	        return 0.5F * this.getEnchantTier();
//	    }
//
//		@Override
//		public float getBowAttackModifier(int level,EnumCreatureAttribute creatureType) {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.calcDamageByCreature(level, creatureType);
//		}
//	}
//
//	public static class EnchantmentArmorBless extends EnchantmentUnsaga{
//
//		protected EnchantmentArmorBless(EntityEquipmentSlot[] slots,int tier) {
//			super(EnumEnchantmentType.ARMOR, slots,tier);
//
//		}
//
//		@Override
//	    public int calcModifierDamage(int level, DamageSource source)
//	    {
//	        return source.canHarmInCreative() ? 0 : 1 * this.getEnchantTier();
//	    }
//	}
//
//	public static class EnchantmentSharpness extends EnchantmentUnsaga implements IWeaponEnchant{
//
//		protected EnchantmentSharpness(EntityEquipmentSlot[] slots,int tier) {
//			super(EnumEnchantmentType.ALL, slots,tier);
//
//		}
//
//		@Override
//	    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType)
//	    {
//	        return 1.0F * this.getEnchantTier();
//	    }
//
//		@Override
//		public float getBowAttackModifier(int level,EnumCreatureAttribute creatureType) {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.calcDamageByCreature(level, creatureType);
//		}
//	}


	public static void registerEvents(){

	}
}
