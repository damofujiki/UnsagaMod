package mods.hinasch.unsagamagic.spell;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.misc.JsonHelper;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.lib.sync.SafeSpawner;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.common.specialaction.ActionAnimalCharm;
import mods.hinasch.unsaga.common.specialaction.ActionAsyncEvent;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.common.specialaction.ActionCure;
import mods.hinasch.unsaga.common.specialaction.ActionHealing;
import mods.hinasch.unsaga.common.specialaction.ActionItem.SpellActionBless;
import mods.hinasch.unsaga.common.specialaction.ActionItem.SpellActionRepair;
import mods.hinasch.unsaga.common.specialaction.ActionList;
import mods.hinasch.unsaga.common.specialaction.ActionRangedAttack;
import mods.hinasch.unsaga.common.specialaction.ActionRangedAttack.PlayerBoundingBox;
import mods.hinasch.unsaga.common.specialaction.ActionTargettable;
import mods.hinasch.unsaga.common.specialaction.ActionThunder;
import mods.hinasch.unsaga.common.specialaction.ActionWorld;
import mods.hinasch.unsaga.common.specialaction.ISpecialActionBase;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemStaffUnsaga;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentRegistry;
import mods.hinasch.unsagamagic.spell.Spell.TipsType;
import mods.hinasch.unsagamagic.spell.action.AsyncSpellEvents;
import mods.hinasch.unsagamagic.spell.action.SpellActionBase;
import mods.hinasch.unsagamagic.spell.action.SpellActionComponents;
import mods.hinasch.unsagamagic.spell.action.SpellActionStatusEffect;
import mods.hinasch.unsagamagic.spell.action.SpellCaster;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class SpellRegistry extends PropertyRegistry<Spell>{

	private static SpellRegistry INSTANCE;

	public static SpellRegistry instance(){
		if(INSTANCE==null){
			INSTANCE = new SpellRegistry();
		}
		return INSTANCE;
	}

	protected SpellRegistry(){

	}

	Map<Spell,ISpecialActionBase<SpellCaster>> actionAssociation = Maps.newHashMap();
	public Spell empty = new Spell("empty",FiveElements.Type.EARTH);

	//Fire
	public Spell fireVeil = new Spell("fireVeil",FiveElements.Type.FIRE).setDifficulty(45).setBaseCastingTime(25).setCost(2).setTipsType(TipsType.TARGETTABLE);
	public Spell detectAura = new Spell("detectAura",FiveElements.Type.FIRE).setDifficulty(15).setBaseCastingTime(30).setCost(5).setDuration(30);
	public Spell fireArrow = new Spell("fireArrow",FiveElements.Type.FIRE).setDifficulty(15).setBaseCastingTime(25).setCost(3).setStrength(5.0F, 1.0F);
	public Spell selfBurning = new Spell("selfBurning",FiveElements.Type.FIRE).setTipsType(TipsType.TARGETTABLE).setDifficulty(45).setBaseCastingTime(25).setCost(5);
	public Spell heroism = new Spell("heroism",FiveElements.Type.FIRE).setTipsType(TipsType.TARGETTABLE).setDifficulty(60).setBaseCastingTime(50).setCost(8);
	public Spell firewall = new Spell("fireWall",FiveElements.Type.FIRE).setTipsType(TipsType.POINT).setDifficulty(75).setBaseCastingTime(75).setCost(10);
	public Spell fireStorm = new Spell("fireStorm",FiveElements.Type.FIRE).setTipsType(TipsType.TARGETTABLE).setDifficulty(120).setBaseCastingTime(70).setCost(20).setStrength(5.0F, 1.5F);
	public Spell holySeal = new Spell("holySeal",FiveElements.Type.FIRE).setTipsType(TipsType.TARGETTABLE).setDifficulty(75).setBaseCastingTime(70).setCost(12);
	public Spell crimsonFlare = new SpellBlend("crimsonFlare",FiveElements.Type.FIRE).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(100).setBaseCastingTime(30).setStrength(5.0F, 1.5F).setCost(30);

	//Earth
	public Spell earthVeil = new Spell("earthVeil",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(15).setBaseCastingTime(25).setCost(2);
	public Spell detectAnimal = new Spell("detectAnimal",FiveElements.Type.EARTH).setDifficulty(45).setBaseCastingTime(30).setDuration(30);
	public Spell boulder = new Spell("boulder",FiveElements.Type.EARTH).setDifficulty(30).setBaseCastingTime(25).setCost(3).setStrength(5.0F, 1.0F);
	public Spell buildUp = new Spell("buildUp",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(60).setBaseCastingTime(75).setCost(10);
	public Spell sleep = new Spell("sleep",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(40).setBaseCastingTime(50).setCost(8);
	public Spell removeFear = new Spell("removeFear",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(60).setBaseCastingTime(25).setCost(8);
	public Spell animalCharm = new Spell("animalCharm",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(100).setBaseCastingTime(70).setCost(10);
	public Spell aegisShield = new Spell("aegisShield",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setDifficulty(120).setBaseCastingTime(65).setCost(12);
	public Spell superPower = new SpellBlend("superPower",FiveElements.Type.EARTH).setBaseCastingTime(70).setCost(30);
	public Spell fear = new SpellBlend("fear",FiveElements.Type.EARTH).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(75).setCost(10);
	public Spell stoneShower = new SpellBlend("stoneShower",FiveElements.Type.EARTH).setBaseCastingTime(75).setCost(20).setStrength(4.0F, 2.0F);

	//Metal
	public Spell metalVeil = new Spell("metalVeil",FiveElements.Type.METAL).setTipsType(TipsType.TARGETTABLE).setDifficulty(45).setBaseCastingTime(15).setCost(2);
	public Spell detectGold = new Spell("detectGold",FiveElements.Type.METAL).setDifficulty(15).setBaseCastingTime(70).setCost(8);
	public Spell magicLock = new Spell("magicLock",FiveElements.Type.METAL).setTipsType(TipsType.TARGETTABLE).setDifficulty(15).setBaseCastingTime(70).setCost(8);
	public Spell shock = new Spell("shock",FiveElements.Type.METAL).setDifficulty(60).setBaseCastingTime(35).setCost(8).setStrength(4, 0.5F);
	public Spell gravity = new Spell("gravity",FiveElements.Type.METAL).setTipsType(TipsType.TARGETTABLE).setDifficulty(60).setBaseCastingTime(50).setCost(8);
	public Spell armorBless = new Spell("armorBless",FiveElements.Type.METAL).setTipsType(TipsType.ITEM).setDifficulty(60).setBaseCastingTime(70).setCost(10);
	public Spell weaponBless = new Spell("weaponBless",FiveElements.Type.METAL).setTipsType(TipsType.ITEM).setDifficulty(60).setBaseCastingTime(70).setCost(10);
	public Spell superSonic = new Spell("superSonic",FiveElements.Type.METAL).setDifficulty(90).setBaseCastingTime(50).setCost(12).setStrength(8, 0.5F);
	public Spell detectTreasure = new SpellBlend("detectTreasure",FiveElements.Type.METAL).setBaseCastingTime(80).setCost(15);
	public Spell sharpness = new SpellBlend("sharpness",FiveElements.Type.METAL).setTipsType(TipsType.ITEM).setBaseCastingTime(70).setCost(12);
	public Spell goldFinger = new SpellBlend("goldFinger",FiveElements.Type.METAL).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(75).setCost(15);

	//Water
	public Spell waterVeil = new Spell("waterVeil",FiveElements.Type.WATER).setTipsType(TipsType.TARGETTABLE).setDifficulty(45).setCost(2).setBaseCastingTime(15);
	public Spell detectUndead = new Spell("detectUndead",FiveElements.Type.WATER).setDifficulty(15).setCost(5).setBaseCastingTime(30).setDuration(30);
	public Spell cloudCall = new Spell("cloudCall",FiveElements.Type.WATER).setDifficulty(15).setCost(5).setBaseCastingTime(70);
	public Spell purify = new Spell("purify",FiveElements.Type.WATER).setTipsType(TipsType.TARGETTABLE).setDifficulty(30).setCost(10).setStrength(2, 0).setBaseCastingTime(50).setGrowth(1.1F);
	public Spell bubbleBlow = new Spell("bubbleBlow",FiveElements.Type.WATER).setDifficulty(60).setCost(3).setBaseCastingTime(25).setStrength(5.0F, 0.2F);
	public Spell waterShield = new Spell("waterShield",FiveElements.Type.WATER).setTipsType(TipsType.TARGETTABLE).setDifficulty(60).setCost(8).setBaseCastingTime(70);
	public Spell iceNeedle = new Spell("iceNeedle",FiveElements.Type.WATER).setDifficulty(90).setCost(12).setBaseCastingTime(90);
	public Spell slowStream = new Spell("slowStream",FiveElements.Type.WATER).setDifficulty(135).setCost(12).setBaseCastingTime(90);
	public Spell thunderCrap = new SpellBlend("thunderCrap",FiveElements.Type.WATER).setBaseCastingTime(90).setCost(18).setStrength(3.0F, 1.5F);
	public Spell reflesh = new SpellBlend("reflesh",FiveElements.Type.WATER).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(80).setCost(14).setStrength(5, 0);
	public Spell iceNine = new SpellBlend("iceNine",FiveElements.Type.WATER).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(70).setCost(12);
	public Spell simulacrum = new SpellBlend("simulacrum",FiveElements.Type.WATER).setTipsType(TipsType.POINT).setBaseCastingTime(75).setCost(15).setRequireCoordinate(true);

	//Wood
	public Spell woodVeil = new Spell("woodVeil",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setDifficulty(45).setBaseCastingTime(25).setCost(2);
	public Spell detectPlant = new Spell("detectPlant",FiveElements.Type.WOOD).setDifficulty(15).setBaseCastingTime(30).setCost(5).setDuration(30);
	public Spell overGrowth = new Spell("overGrowth",FiveElements.Type.WOOD).setDifficulty(45).setBaseCastingTime(70).setCost(10);
	public Spell lifeBoost = new Spell("lifeBoost",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setDifficulty(40).setBaseCastingTime(65).setCost(8);
	public Spell recycle = new Spell("recycle",FiveElements.Type.WOOD).setTipsType(TipsType.ITEM).setDifficulty(60).setBaseCastingTime(70).setCost(10).setStrength(10, 0);
	public Spell missileGuard = new Spell("missileGuard",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setDifficulty(75).setBaseCastingTime(70).setCost(12);
	public Spell meditation = new Spell("meditation",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setDifficulty(100).setBaseCastingTime(80).setCost(12).setStrength(3, 0);
	public Spell callThunder = new Spell("callThunder",FiveElements.Type.WOOD).setTipsType(TipsType.POINT_TARGET).setDifficulty(120).setBaseCastingTime(45).setCost(12).setStrength(5.0F, 1.0F).setRequireCoordinate(true);
	public Spell leafShield = new SpellBlend("leafShield",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(75);
	public Spell resting = new SpellBlend("resting",FiveElements.Type.WOOD).setTipsType(TipsType.TARGETTABLE).setBaseCastingTime(80).setCost(15).setStrength(5, 0);
	public Spell godThunder = new SpellBlend("godThunder",FiveElements.Type.WOOD).setTipsType(TipsType.POINT_TARGET).setBaseCastingTime(80).setCost(15).setStrength(8.0F, 2.0F).setRequireCoordinate(true);

	//Forbidden
	public Spell detectBlood = new Spell("detectBlood",FiveElements.Type.FORBIDDEN).setDifficulty(120).setBaseCastingTime(30).setCost(6).setDuration(30);
	public Spell darkSeal = new Spell("darkSeal",FiveElements.Type.FORBIDDEN).setTipsType(TipsType.TARGETTABLE).setDifficulty(150).setBaseCastingTime(50).setCost(12);
	public Spell weakness = new Spell("weakness",FiveElements.Type.FORBIDDEN).setTipsType(TipsType.TARGETTABLE).setDifficulty(150).setBaseCastingTime(45).setCost(5);
	public Spell spellMagnet = new Spell("spellMagnet",FiveElements.Type.FORBIDDEN).setTipsType(TipsType.TARGETTABLE).setDifficulty(150).setBaseCastingTime(70).setCost(10);
	public Spell spoil = new Spell("spoil",FiveElements.Type.FORBIDDEN).setTipsType(TipsType.TARGETTABLE).setDifficulty(150).setBaseCastingTime(55).setCost(8).setDuration(45);
	public Spell deadlyDrive = new Spell("deadlyDrive",FiveElements.Type.FORBIDDEN).setDifficulty(150).setBaseCastingTime(90).setCost(20);
	public Spell blaster = new Spell("blaster",FiveElements.Type.FORBIDDEN).setDifficulty(240).setBaseCastingTime(35).setCost(15).setStrength(6.0F, 1.0F);
	public Spell shadowServant = new Spell("shadowServant",FiveElements.Type.FORBIDDEN).setTipsType(TipsType.TARGETTABLE).setDifficulty(250).setBaseCastingTime(100).setCost(30);

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerAssociations();
		this.mergeJson();
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	@Override
	protected void registerObjects() {
		this.put(fireVeil);
		this.put(detectAura);
		this.put(fireArrow);
		this.put(selfBurning);
		this.put(heroism);
		this.put(firewall);
		this.put(fireStorm);
		this.put(holySeal);
		this.put(crimsonFlare);

		this.put(earthVeil);
		this.put(sleep);
		this.put(detectAnimal);
		this.put(boulder);
		this.put(buildUp);
		this.put(removeFear);
		this.put(animalCharm);
		this.put(aegisShield);
		this.put(superPower);
		this.put(fear);
		this.put(stoneShower);

		this.put(metalVeil);
		this.put(detectGold);
		this.put(magicLock);
		this.put(gravity);
		this.put(shock);
		this.put(armorBless);
		this.put(weaponBless);
		this.put(superSonic);
		this.put(detectTreasure);
		this.put(sharpness);
		this.put(goldFinger);

		this.put(waterVeil);
		this.put(detectUndead);
		this.put(cloudCall);
		this.put(purify);
		this.put(bubbleBlow);
		this.put(waterShield);
		this.put(iceNeedle);
		this.put(slowStream);
		this.put(thunderCrap);
		this.put(reflesh);
		this.put(iceNine);
		this.put(simulacrum);

		this.put(woodVeil);
		this.put(detectPlant);
		this.put(overGrowth);
		this.put(lifeBoost);
		this.put(recycle);
		this.put(missileGuard);
		this.put(meditation);
		this.put(callThunder);
		this.put(leafShield);
		this.put(resting);
		this.put(godThunder);

		this.put(detectBlood);
		this.put(darkSeal);
		this.put(weakness);
		this.put(spellMagnet);
		this.put(deadlyDrive);
		this.put(spoil);
		this.put(blaster);
		this.put(shadowServant);
	}

	public List<Spell> getSpellsExceptBlendedSpell(FiveElements.Type elm){
		return this.getProperties().stream().filter(in -> in.getElement()==elm).filter(in -> !(in instanceof SpellBlend))
		.collect(Collectors.toList());
	}


	public void registerAssociations(){
		UnsagaPotions up = UnsagaPotions.instance();

		SpellActionBase spell = new SpellActionBase();
		spell.addAction(new SpellActionStatusEffect(false,up.veilFire));
		//Veil系
		this.actionAssociation.put(fireVeil, createBuffSpell(false,up.veilFire));
		this.actionAssociation.put(waterVeil, createBuffSpell(false,up.veilWater));
		this.actionAssociation.put(woodVeil, createBuffSpell(false,up.veilWood));
		this.actionAssociation.put(earthVeil, createBuffSpell(false,up.veilEarth));
		this.actionAssociation.put(metalVeil, createBuffSpell(false,up.veilMetal));

		//バフ系
		this.actionAssociation.put(lifeBoost, createBuffSpell(false,up.lifeBoost));
		this.actionAssociation.put(heroism, createBuffSpell(false,MobEffects.STRENGTH));
		this.actionAssociation.put(buildUp, createBuffSpell(false,MobEffects.HASTE,up.upVit));
		SpellActionBase superPowerBase = this.createTargettableSpell(list ->{
			list.addAction(new ActionCure(up.statusDownDebuffs));
			list.addAction(new SpellActionStatusEffect(false,MobEffects.STRENGTH,MobEffects.HEALTH_BOOST,MobEffects.JUMP_BOOST));
			return list;
		});
		this.actionAssociation.put(superPower, superPowerBase);
		this.actionAssociation.put(shadowServant, createBuffSpell(false,up.shadowServant));
		this.actionAssociation.put(holySeal, createBuffSpell(false,up.holySeal));
		this.actionAssociation.put(darkSeal, createBuffSpell(false,up.darkSeal));

		//盾系
		this.actionAssociation.put(selfBurning, createBuffSpell(false,up.selfBurning));
		this.actionAssociation.put(waterShield, createBuffSpell(false,up.waterShield));
		this.actionAssociation.put(leafShield, createBuffSpell(false,up.leafShield));
		this.actionAssociation.put(aegisShield, createBuffSpell(false,up.aegisShield));
		this.actionAssociation.put(missileGuard, createBuffSpell(false,up.missileGuard));

		//非同期系
		IAction<SpellCaster> soundFire = new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster t) {
				if(t.getTarget().isPresent()){
					t.playSound(XYZPos.createFrom(t.getTarget().get()), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, false);
				}
				return EnumActionResult.PASS;
			}
		};
		SpellActionBase fireStormBase = this.createTargettableSpell(in ->{
			in.addAction(soundFire);
			in.addAction(new ActionAsyncEvent<SpellCaster>().setEventFactory(context ->{
				return new AsyncSpellEvents.FireStorm(context.getWorld(), context.getTarget().get().getPosition(), context.getPerformer(), context.getEffectModifiedStrength().hp(), 8, 60).setThreshold(40);
			}));
			return in;
		});
		this.actionAssociation.put(fireStorm, fireStormBase);
//		this.actionAssociation.put(fireStorm, new SpellAsync.Targettable(true).setEventFactory((context,target)->{
//
//			return new SpellAsync.FireStorm(context.getWorld(), target.getPosition(), context.getPerformer(), context.getEffectModifiedStrength().hp(), 8, 60).setThreshold(40);
//		}).setCastSound(SoundEvents.ENTITY_GHAST_SHOOT));
		ActionAsyncEvent<SpellCaster> asyncStoneShower = new ActionAsyncEvent<SpellCaster>().setEventFactory(context ->{
			return new AsyncSpellEvents.StoneShower(context.getWorld(), context.getPerformer(), 60, context.getEffectModifiedStrength().hp()).setThreshold(40);
		});
		SpellActionBase stoneShowerBase = new SpellActionBase();
		stoneShowerBase.addAction(asyncStoneShower);
		this.actionAssociation.put(stoneShower, stoneShowerBase);

		ActionAsyncEvent<SpellCaster> asyncThunderCrap = new ActionAsyncEvent<SpellCaster>().setEventFactory(context ->{
			return new AsyncSpellEvents.ThunderCrap(context.getWorld(), context.getPerformer(), 10, context).setThreshold(50);
		});
		SpellActionBase thunderCrapBase = new SpellActionBase();
		thunderCrapBase.addAction(asyncThunderCrap);
		this.actionAssociation.put(thunderCrap, thunderCrapBase);
//		this.actionAssociation.put(stoneShower, new SpellAsync().setEventFactory((context,target)->{
//
//			return new SpellAsync.StoneShower(context.getWorld(), context.getPerformer(), 60, context.getEffectModifiedStrength().hp()).setThreshold(40);
//		}).setCastSound(SoundEvents.ENTITY_GHAST_SHOOT));
		SpellActionBase crimsonFlareBase = this.createTargettableSpell(in ->{
			in.addAction(new ActionAsyncEvent<SpellCaster>().setEventFactory(context ->{
				if(context.getTarget().isPresent()){
					return new AsyncSpellEvents.CrimsonFlare(context.getWorld(), context.getPerformer(),context.getTarget().get(), context.getEffectModifiedStrength().hp(),300).setThreshold(50);
				}
				return null;
			}));
			return in;
		});
		this.actionAssociation.put(crimsonFlare, crimsonFlareBase);
//		this.actionAssociation.put(crimsonFlare, new SpellAsync.Targettable(false).setEventFactory((context,target)->{
//
//			return new SpellAsync.CrimsonFlare(context.getWorld(), context.getPerformer(),target, context.getEffectModifiedStrength().hp(),300).setThreshold(50);
//		}));

		//回復系
		IAction<SpellCaster> healSound = new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster context) {
				if(context.getTarget().isPresent()){
					context.playSound(XYZPos.createFrom(context.getTarget().get()), SoundEvents.ENTITY_PLAYER_LEVELUP, false);
				}

				return EnumActionResult.PASS;
			}
		};
		SpellActionBase purifyBase = this.createTargettableSpell(list ->{
			list.addAction(healSound);
			list.addAction(new ActionHealing());
			list.addAction(new IAction<SpellCaster>(){
				@Override
				public EnumActionResult apply(SpellCaster context) {
					if(context.getAmplify()>=2.0F && context.getTarget().isPresent()){
						for(Potion potion:UnsagaPotions.instance().bodyDebuffs){
							context.getTarget().get().removePotionEffect(potion);
						}
					}
					return EnumActionResult.SUCCESS;
				}}
			);
			return list;
		});


		this.actionAssociation.put(purify, purifyBase);
		SpellActionBase meditationBase = this.createTargettableSpell(list ->{
			list.addAction(healSound);
			list.addAction(new ActionHealing());
			list.addAction(new ActionCure(up.statusDownDebuffs));
			list.addAction(new SpellActionStatusEffect(false,up.upInt,up.upMental));
			return list;
		});
		this.actionAssociation.put(meditation, meditationBase);
		SpellActionBase restingBase = this.createTargettableSpell(list ->{
			list.addAction(healSound);
			list.addAction(new ActionHealing());
			list.addAction(new ActionCure(up.mentalDebuffs));
			return list;
		});
		this.actionAssociation.put(resting, restingBase);
		SpellActionBase refleshBase = this.createTargettableSpell(list ->{
			list.addAction(healSound);
			list.addAction(new ActionHealing());
			list.addAction(new ActionCure());
			return list;
		});
		this.actionAssociation.put(reflesh, refleshBase);

		//デテクト系
		int duration = ItemUtil.getPotionTime(15);
		ActionRangedAttack<SpellCaster> rangedDebuff = new ActionRangedAttack().setAttackFlag(false);




		double distance = 30.0D;
		this.actionAssociation.put(detectPlant, this.createRangedDebuffSpell(true,distance,(self,target)->{
			return target.getCreatureAttribute()==EntityRuffleTree.PLANT;
		} ,up.detected,MobEffects.GLOWING));
		this.actionAssociation.put(detectAnimal, this.createRangedDebuffSpell(true,distance,(self,target)->{
			return target instanceof EntityAnimal;
		} ,up.detected,MobEffects.GLOWING));
		this.actionAssociation.put(detectUndead, this.createRangedDebuffSpell(true,distance,(self,target)->{
			return target.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD;
		} ,up.detected,MobEffects.GLOWING));
		this.actionAssociation.put(detectBlood, this.createRangedDebuffSpell(true,distance,(self,target)->{
			return target.getCreatureAttribute()!=EnumCreatureAttribute.UNDEAD;
		} ,up.detected,MobEffects.GLOWING));
		this.actionAssociation.put(detectAura, this.createRangedDebuffSpell(true,distance,(self,target)->{
			return target.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()>=7.0;
		} ,up.detected,MobEffects.GLOWING));

		//範囲攻撃系


		SpellActionBase shockBase = new SpellActionBase();
		shockBase.addAction(SpellActionComponents.SHOCK_EFFECT);
		shockBase.addAction(new ActionRangedAttack(General.MAGIC).setBoundingBoxFunction(new PlayerBoundingBox(6.0D)).setSubDamageType(Sub.ELECTRIC));
		this.actionAssociation.put(shock, shockBase);

		SpellActionBase superSonicBase = new SpellActionBase();
		superSonicBase.addAction(SpellActionComponents.SHOCK_EFFECT);
		superSonicBase.addAction(new ActionRangedAttack<SpellCaster>(General.MAGIC).setBoundingBoxFunction(new PlayerBoundingBox(10.0D)).setSubDamageType(Sub.SHOCK)
				.setSubBehavior((self,target)->{
					if(target.getCreatureAttribute()==EnumCreatureAttribute.ARTHROPOD){
						target.addPotionEffect(new PotionEffect(up.sleep,ItemUtil.getPotionTime(15),0));
					}
				}
		));
		this.actionAssociation.put(superSonic, superSonicBase);
//		this.actionAssociation.put(shock, (new SpellRanged(5.0D)).addHurtBehavior(EnumSet.of(DamageTypeUnsaga.General.MAGIC),EnumSet.of(DamageTypeUnsaga.Sub.ELECTRIC)).setPrePerform(effect));
//		this.actionAssociation.put(superSonic, (new SpellRanged(8.0D)).addHurtBehavior(EnumSet.of(DamageTypeUnsaga.General.MAGIC),EnumSet.of(DamageTypeUnsaga.Sub.SHOCK))
//				.addDebuffBehavior(in -> in.getCreatureAttribute()==EnumCreatureAttribute.ARTHROPOD,up.sleep).setPrePerform(effect));
		//デバフ系
		this.actionAssociation.put(sleep, this.createBuffSpell(true,UnsagaPotions.instance().sleep));
		this.actionAssociation.put(slowStream, this.createRangedDebuffSpell(true,20.0D,null,MobEffects.SLOWNESS));
		SpellActionBase deadlyDriveBase = this.createRangedDebuffSpell(true,12.0D,null,MobEffects.SLOWNESS,MobEffects.WEAKNESS,up.downDex,up.downInt,up.downVit);
		deadlyDriveBase.addAction(new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster context) {
				context.playSound(XYZPos.createFrom(context.getPerformer()), SoundEvents.ENTITY_WITHER_SPAWN, false);
				return EnumActionResult.PASS;
			}
		}, 0);
		this.actionAssociation.put(deadlyDrive, deadlyDriveBase);
		this.actionAssociation.put(spoil, this.createBuffSpell(true, MobEffects.POISON));
		this.actionAssociation.put(magicLock, this.createBuffSpell(true,up.lockSlime));

		//その他
		this.actionAssociation.put(animalCharm, this.createTargettableSpell(in ->{
			in.addAction(new ActionAnimalCharm());
			return in;

		}));
		this.actionAssociation.put(simulacrum, new SpellActionBase().addAction(context->{
			if(context.getTargetCoordinate().isPresent()){
				context.playSound(new XYZPos(context.getTargetCoordinate().get()), SoundEvents.ENTITY_SHULKER_SHOOT, false);
				BlockPos pos = context.getTargetCoordinate().get().up();
				EntitySnowman snowman = new EntitySnowman(context.getWorld());
				snowman.setPosition(pos.getX(),pos.getY(),pos.getZ());
				if(WorldHelper.isServer(context.getWorld())){
					SafeSpawner spawner = new SafeSpawner(context.getWorld(), snowman);
					WorldHelper.getWorldServer(context.getWorld()).addScheduledTask(spawner);
				}
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.PASS;
		}));
		//アイテム系
		this.actionAssociation.put(recycle, new SpellActionBase().addAction(new SpellActionRepair(in -> in.getItem() instanceof Item)));
		this.actionAssociation.put(weaponBless, new SpellActionBase().addAction(new SpellActionBless(UnsagaEnchantmentRegistry.instance().weaponBless
				,in -> in.getItem() instanceof Item)));
		this.actionAssociation.put(armorBless, new SpellActionBase().addAction(new SpellActionBless(UnsagaEnchantmentRegistry.instance().armorBless
				,in -> in.getItem() instanceof ItemArmor)));
		this.actionAssociation.put(armorBless, new SpellActionBase().addAction(new SpellActionBless(UnsagaEnchantmentRegistry.instance().sharpness
				,in ->!(in.getItem() instanceof ItemStaffUnsaga))));

		//ワールド系


		this.actionAssociation.put(overGrowth, SpellActionComponents.overGrowth());

		this.actionAssociation.put(iceNine, new SpellActionBase().addAction(new ActionWorld(8,3).setWorldConsumer(new ActionWorld.Freezer())));

		//探索系

		this.actionAssociation.put(detectGold, SpellActionComponents.detectGold());
		this.actionAssociation.put(detectTreasure, SpellActionComponents.detectGold());

		//天気
		this.actionAssociation.put(cloudCall, new SpellActionBase().addAction(new IAction<SpellCaster>(){

			@Override
			public EnumActionResult apply(SpellCaster context) {
				if(!context.getWorld().isRaining()){
					BlockPos pos = context.getPerformer().getPosition();
					context.getWorld().playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 1.0F, 1.0F, false);
					context.getWorld().getWorldInfo().setRaining(true);
					return EnumActionResult.SUCCESS;
				}
				return EnumActionResult.PASS;
			}})
		);
		ActionThunder<SpellCaster> thunderAction = new ActionThunder<SpellCaster>(ActionThunder.Type.NORMAL);
		ActionThunder<SpellCaster> thunderActionExplode = new ActionThunder<SpellCaster>(ActionThunder.Type.EXPLODE);
		this.actionAssociation.put(callThunder, new SpellActionBase().addAction(new ActionTargettable(thunderAction)));
		this.actionAssociation.put(godThunder, new SpellActionBase().addAction(new ActionTargettable(thunderActionExplode)));

		//飛び道具系

		this.actionAssociation.put(fireArrow, SpellActionComponents.fireArrow());
		this.actionAssociation.put(blaster,SpellActionComponents.blaster() );
		this.actionAssociation.put(boulder, SpellActionComponents.boulder());
		this.actionAssociation.put(bubbleBlow, SpellActionComponents.bubbleBlow());
	}

	public List<SpellBlend> getBlendSpells(){
		return this.getProperties().stream().filter(in -> in instanceof SpellBlend).map(in -> (SpellBlend)in).collect(Collectors.toList());
	}
	private SpellActionBase createRangedDebuffSpell(boolean isDebuff,double range,@Nullable BiPredicate<RangedHelper<SpellCaster>,EntityLivingBase> selector,Potion... potions){
		return SpellActionComponents.createRangedDebuffSpell(isDebuff, range, selector, potions);
	}
	private SpellActionBase createBuffSpell(boolean isDebuff,Potion... potions){
		return SpellActionComponents.createBuffSpell(isDebuff, potions);
	}
	private SpellActionBase createTargettableSpell(UnaryOperator<ActionList> uo){
		return SpellActionComponents.createTargettableSpell(uo);
	}
	public void mergeJson(){
		ResourceLocation res = new ResourceLocation(UnsagaMod.MODID,"data/spell.json");
		JsonHelper<Spell,JsonParserSpell> helper = new JsonHelper<Spell,JsonParserSpell>(res,in -> new JsonParserSpell(),in -> this.get(in.name));
		helper.parse();

		ResourceLocation res2 = new ResourceLocation(UnsagaMod.MODID,"data/spellblend.json");
		JsonHelper<SpellBlend,JsonParserSpell.Blend> helper2 = new JsonHelper<SpellBlend,JsonParserSpell.Blend>(res2,in -> new JsonParserSpell.Blend(),in -> (SpellBlend)this.get(in.name));
		helper2.parse();
	}
	public ISpecialActionBase<SpellCaster> getSpellAction(Spell spell){
		return this.actionAssociation.get(spell);
	}
}
