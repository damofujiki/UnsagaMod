package mods.hinasch.unsaga.ability;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveRegistry;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.status.AdditionalStatus;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class AbilityRegistry extends PropertyRegistry<IAbility>{

	protected static AbilityRegistry INSTANCE;

	public static final UUID HEAL_UUID = UUID.fromString("579cc71e-ab95-4c54-af23-300047895df8");

	Map<IAttribute,AttributeModifier> baseModifierMap = Maps.newHashMap();
	public Ability dummy = new Ability("dummy");
	public Ability empty = new Ability("empty");
	public Ability healDown1 = new Ability("healDown1");
	public Ability healDown2 = new Ability("healDown2");
	public Ability healDown3 = new Ability("healDown3");
	public Ability healDown4 = new Ability("healDown4");
	public Ability healDown5 = new Ability("healDown5");
	public Ability healUp1 = new Ability("healUp1");
	public Ability healUp2 = new Ability("healUp2");
	public Ability supportFire = new Ability("supportFire");
	public Ability supportEarth = new Ability("supportEarth");
	public Ability supportMetal = new Ability("supportMetal");
	public Ability supportWater = new Ability("supportWater");
	public Ability supportWood = new Ability("supportWood");
	public Ability supportForbidden = new Ability("supportForbidden");
	public Ability spellFire = new Ability("spellFire");
	public Ability spellEarth = new Ability("spellEarth");
	public Ability spellMetal = new Ability("spellMetal");
	public Ability spellWater = new Ability("spellWater");
	public Ability spellWood = new Ability("spellWood");
	public Ability spellForbidden = new Ability("spellForbidden");
	public Ability blocking = new Ability("blocking");
	public Ability blockBlast = new Ability("blockBlast");
	public Ability blockMelee = new Ability("blockMelee");
	public Ability blockSpear = new Ability("blockSpear");
	public Ability blockBreath = new Ability("blockBreath");
	public Ability armorBruiseEx = new Ability("armorBruiseEx");
	public Ability armorFireEx = new Ability("armorFireEx");
	public Ability armorColdEx = new Ability("armorColdEx");
	public Ability armorElectricEx = new Ability("armorElectricEx");
	public Ability armorSlash = new Ability("armorSlash");
	public Ability armorBruise = new Ability("armorBruise");
	public Ability armorPierce = new Ability("armorPierce");
	public Ability armorFire = new Ability("armorFire");
	public Ability armorCold = new Ability("armorCold");
	public Ability armorElectric = new Ability("armorElectric");
	public Ability armorLight = new Ability("armorLight");
	public Ability armorDebuff = new Ability("armorDebuff");
	public Ability armorPierceBad = new Ability("armorPierceBad");
	public Ability antiPoison = new Ability("antiPoison");
	public Ability antiBlind = new Ability("antiBlind");
	public Ability antiFreeze = new Ability("antiFreeze");
	public Ability antiLevitation = new Ability("antiLevitation");
	public Ability antiSleep = new Ability("antiSleep");
	public Ability antiWither = new Ability("antiWither");
	public Ability lifeProtection = new Ability("lifeProtection");
	public Ability strengthProtection = new Ability("strProtection");
	public Ability dextalityProtection = new Ability("dexProtection");
	public Ability mindProtection = new Ability("mindProtection");
	public Ability intelligenceProtection = new Ability("intProtection");
	public Ability vitalityProtection = new Ability("vitProtection");
	public Ability superHealing = new Ability("superHealing");

	Map<IAbility,Integer> healAmountMap;
	public SpecialMoveRegistry specialArts;
	private AbilityAssociateRegistry association;

	public static AbilityRegistry instance(){
		if(INSTANCE ==null){
			INSTANCE = new AbilityRegistry();
		}
		return INSTANCE;
	}


	public static Ability empty(){
		return AbilityRegistry.instance().empty;
	}

	public Map<IAttribute,AttributeModifier> getModifierBaseMaps(){
		return this.baseModifierMap;
	}

	private void registerShields(){
		blockBreath.setBlockableDamage(in ->in.getParentDamageSource()==DamageSource.dragonBreath);
		blockMelee.setBlockableDamage(in ->!in.isUnblockable() && !in.getDamageTypeUnsaga().contains(General.MAGIC));
		blockBlast.setBlockableDamage(in ->in.isExplosion());
	}
	private void registerAntiEffects(){
		antiPoison.setAntiEffects(MobEffects.POISON);
		antiLevitation.setAntiEffects(MobEffects.LEVITATION);
		antiWither.setAntiEffects(MobEffects.WITHER);
		antiBlind.setAntiEffects(MobEffects.BLINDNESS);
		armorDebuff.setIsAntiAllDebuffs(true);
		antiSleep.setAntiEffects(UnsagaPotions.instance().sleep);
		strengthProtection.setAntiEffects(MobEffects.WEAKNESS);
		dextalityProtection.setAntiEffects(UnsagaPotions.instance().downDex);
		mindProtection.setAntiEffects(UnsagaPotions.instance().downInt);
		intelligenceProtection.setAntiEffects(UnsagaPotions.instance().downInt);
		vitalityProtection.setAntiEffects(UnsagaPotions.instance().downVit);

	}
	private void registerModifiers(){
		this.baseModifierMap.put(AdditionalStatus.NATURAL_HEAL_SPEED, new AttributeModifier(HEAL_UUID,"ability.naturlaHeal",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FIRE), new AttributeModifier(UUID.fromString("d27c283b-65a7-4cde-ba95-6f1956f4566a"),"ability.supportFire",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WATER), new AttributeModifier(UUID.fromString("7bb8d1ee-3770-4439-a499-dca4194d7aee"),"ability.supportWater",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.EARTH), new AttributeModifier(UUID.fromString("92f2d519-2fd3-4ec3-a79d-98ad983a6aad"),"ability.supportEarth",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.METAL), new AttributeModifier(UUID.fromString("86818af7-6fe6-4743-930c-c31c8edfc7f0"),"ability.supportMetal",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WOOD), new AttributeModifier(UUID.fromString("a24275a1-031d-48dd-b97a-e7a2b8d75b72"),"ability.supportWood",0,0));
		this.baseModifierMap.put(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FORBIDDEN), new AttributeModifier(UUID.fromString("46dba524-b2a4-4763-a4a5-f52dce6575d3"),"ability.supportForbidden",0,0));
		this.baseModifierMap.put(AdditionalStatus.GENERALS.get(General.SWORD), new AttributeModifier(UUID.fromString("e5d714b9-0b68-421c-9734-1dd58c31ba46"),"ability.armorSlash",0,0));
		this.baseModifierMap.put(AdditionalStatus.GENERALS.get(General.PUNCH), new AttributeModifier(UUID.fromString("fe95abf1-1c68-4bd1-9ff7-7469ad9f4402"),"ability.armorPunch",0,0));
		this.baseModifierMap.put(AdditionalStatus.GENERALS.get(General.SPEAR), new AttributeModifier(UUID.fromString("945ec805-fe23-4353-9882-0e7901bd0348"),"ability.armorSpear",0,0));
		this.baseModifierMap.put(AdditionalStatus.GENERALS.get(General.MAGIC), new AttributeModifier(UUID.fromString("878cdefc-5a6b-4f58-bb3a-ae1069044675"),"ability.armorMagic",0,0));
		this.baseModifierMap.put(AdditionalStatus.SUBS.get(Sub.FIRE), new AttributeModifier(UUID.fromString("6cad0b49-b6f0-4847-8c21-716a9d5c390f"),"ability.armorFire",0,0));
		this.baseModifierMap.put(AdditionalStatus.SUBS.get(Sub.FREEZE), new AttributeModifier(UUID.fromString("28cdddf3-e062-48a8-9ba5-f30e2c90a86d"),"ability.armorFreeze",0,0));
		this.baseModifierMap.put(AdditionalStatus.SUBS.get(Sub.ELECTRIC), new AttributeModifier(UUID.fromString("470b75f4-aded-4ee0-86bc-91f2feba5b7a"),"ability.armorElectric",0,0));
		this.baseModifierMap.put(AdditionalStatus.SUBS.get(Sub.SHOCK), new AttributeModifier(UUID.fromString("2891b53b-7ed9-4424-a80d-0726c505ad74"),"ability.armorShock",0,0));
		this.baseModifierMap.put(AdditionalStatus.RESISTANCE_LP_HURT, new AttributeModifier(UUID.fromString("95f178e8-97d3-48a5-9390-7187831ba7f1"),"ability.resitance.LP",0,0));

		healDown1.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, -5D);
		healDown2.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, -10D);
		healDown3.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, -15D);
		healDown4.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, -20D);
		healDown5.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, -25D);
		healUp1.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, 5D);
		healUp2.setAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, 10D);
		supportFire.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FIRE), 5D);
		supportEarth.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.EARTH), 5D);
		supportMetal.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.METAL), 5D);
		supportWater.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WATER), 5D);
		supportWood.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WOOD), 5D);
		supportForbidden.setAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FORBIDDEN), 5D);
		armorBruiseEx.setAttributeModifier(AdditionalStatus.GENERALS.get(General.PUNCH), 0.5D);
		armorFireEx.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.FIRE), 0.5D);
		armorColdEx.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.FREEZE), 0.5D);
		armorElectricEx.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.ELECTRIC), 0.5D);
		armorSlash.setAttributeModifier(AdditionalStatus.GENERALS.get(General.SWORD), 0.3D);
		armorBruise.setAttributeModifier(AdditionalStatus.GENERALS.get(General.PUNCH), 0.3D);
		armorPierce.setAttributeModifier(AdditionalStatus.GENERALS.get(General.SPEAR), 0.3D);
		armorFire.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.FIRE), 0.25D);
		armorCold.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.FREEZE), 0.25D);
		armorElectric.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.ELECTRIC), 0.25D);
		armorLight.setAttributeModifier(AdditionalStatus.SUBS.get(Sub.SHOCK), 0.25D);
		armorPierceBad.setAttributeModifier(AdditionalStatus.GENERALS.get(General.SPEAR), -0.125D);
		lifeProtection.setAttributeModifier(AdditionalStatus.RESISTANCE_LP_HURT, 0.1D);
	}
	@Override
	public void init() {
		this.association.init();
		this.specialArts.init();

		this.registerModifiers();
		this.registerAntiEffects();
		this.registerShields();
		this.healAmountMap = Maps.newHashMap();

		this.healAmountMap.put(healDown1, -5);
		this.healAmountMap.put(healDown2, -10);
		this.healAmountMap.put(healDown3, -15);
		this.healAmountMap.put(healDown4, -20);
		this.healAmountMap.put(healDown5, -25);
		this.healAmountMap.put(healUp1, 5);
		this.healAmountMap.put(healUp2, 10);


		HSLibs.registerEvent(new EventRefleshAbilityModifier());
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(e.getEntityLiving() instanceof EntityPlayer){
					List<Ability> abilities = AbilityAPI.getEffectiveAllPassiveAbilities(e.getEntityLiving());
					if(e.getEntityLiving().ticksExisted % 4 ==0){

						Set<Potion> antis = abilities.stream().filter(in -> !in.getAntiEffects().isEmpty())
								.flatMap(in -> in.getAntiEffects().stream()).collect(Collectors.toSet());
						antis.forEach(in ->{
							if(e.getEntityLiving().isPotionActive(in)){

								e.getEntityLiving().removeActivePotionEffect(in);
							}
						});



					}
					if(e.getEntityLiving().ticksExisted % 10 ==0){
						if(abilities.contains(armorDebuff)){
							Set<Potion> antis = e.getEntityLiving().getActivePotionEffects().stream().map(in -> in.getPotion()).filter(in -> in.isBadEffect()).collect(Collectors.toSet());
							antis.forEach(in ->{
									e.getEntityLiving().removeActivePotionEffect(in);
							});
						}
						if(AbilityAPI.getEffectiveAllAbilities(e.getEntityLiving()).contains(superHealing)){
							e.getEntityLiving().heal(0.5F);
						}
					}
				}


			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "ability event";
			}}
				);
	}

	@Override
	public void preInit() {
		this.specialArts = SpecialMoveRegistry.instance();
		this.specialArts.preInit();

		this.registerObjects();

		this.association = AbilityAssociateRegistry.instance();
		this.association.preinit();
	}


	@Override
	protected void registerObjects() {
		this.put(healDown1,healDown2,healDown3,healDown4,healDown5);
		this.put(healUp1,healUp2);
		this.put(supportEarth);
		this.put(supportFire);
		this.put(spellForbidden);
		this.put(supportMetal);
		this.put(supportWater);
		this.put(supportWood);
		this.put(spellEarth);
		this.put(spellFire);
		this.put(spellForbidden);
		this.put(spellMetal);
		this.put(spellWater);
		this.put(spellWood);
		this.put(blocking);
		this.put(blockBlast);
		this.put(blockMelee);
		this.put(blockBreath);
		this.put(armorPierce);
		this.put(armorPierceBad);
		this.put(armorBruise);
		this.put(armorBruiseEx);
		this.put(armorCold);
		this.put(armorColdEx);
		this.put(armorDebuff);
		this.put(armorElectric);
		this.put(armorElectricEx);
		this.put(armorSlash);
		this.put(armorLight);
		this.put(armorFire);
		this.put(armorFireEx);
		this.put(antiPoison);
		this.put(antiBlind);
		this.put(antiSleep);
		this.put(antiFreeze);
		this.put(antiWither);
		this.put(lifeProtection);
		this.put(strengthProtection);
		this.put(dextalityProtection);
		this.put(vitalityProtection);
		this.put(mindProtection);
		this.put(intelligenceProtection);
		this.put(superHealing);

		this.specialArts.getProperties().forEach(obj -> this.put(obj));


	}


}
