package mods.hinasch.unsagamagic.spell.tablet;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.lib.item.WeightedRandomItem;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class TabletRegistry extends PropertyRegistry<MagicTablet>{


	private static TabletRegistry INSTANCE;

	public static TabletRegistry instance(){
		if(INSTANCE==null){
			INSTANCE = new TabletRegistry();
		}
		return INSTANCE;
	}

	protected TabletRegistry(){

	}
	SpellRegistry s = SpellRegistry.instance();

	MagicTablet empty = new MagicTablet("empty",FiveElements.Type.FORBIDDEN,0);
	MagicTablet fireArtistsTablet1 = new MagicTablet("fireArtist1",FiveElements.Type.FIRE,1);
	MagicTablet fireArtistsTablet2 = new MagicTablet("fireArtist2",FiveElements.Type.FIRE,2);
	MagicTablet fireArtistsTablet3 = new MagicTablet("fireArtist3",FiveElements.Type.FIRE,3);
	MagicTablet fireArtistsTablet4 = new MagicTablet("fireArtist4",FiveElements.Type.FIRE,4);
	MagicTablet waterArtistsTablet1 = new MagicTablet("waterArtist1",FiveElements.Type.WATER,1);
	MagicTablet waterArtistsTablet2 = new MagicTablet("waterArtist2",FiveElements.Type.WATER,2);
	MagicTablet waterArtistsTablet3 = new MagicTablet("waterArtist3",FiveElements.Type.WATER,3);
	MagicTablet waterArtistsTablet4 = new MagicTablet("waterArtist4",FiveElements.Type.WATER,4);
	MagicTablet earthArtistsTablet1 = new MagicTablet("earthArtist1",FiveElements.Type.EARTH,1);
	MagicTablet earthArtistsTablet2 = new MagicTablet("earthArtist2",FiveElements.Type.EARTH,2);
	MagicTablet earthArtistsTablet3 = new MagicTablet("earthArtist3",FiveElements.Type.EARTH,3);
	MagicTablet earthArtistsTablet4 = new MagicTablet("earthArtist4",FiveElements.Type.EARTH,4);
	MagicTablet metalArtistsTablet1 = new MagicTablet("metalArtist1",FiveElements.Type.METAL,1);
	MagicTablet metalArtistsTablet2 = new MagicTablet("metalArtist2",FiveElements.Type.METAL,2);
	MagicTablet metalArtistsTablet3 = new MagicTablet("metalArtist3",FiveElements.Type.METAL,3);
	MagicTablet metalArtistsTablet4 = new MagicTablet("metalArtist4",FiveElements.Type.METAL,4);
	MagicTablet woodArtistsTablet1 = new MagicTablet("woodArtist1",FiveElements.Type.WOOD,1);
	MagicTablet woodArtistsTablet2 = new MagicTablet("woodArtist2",FiveElements.Type.WOOD,2);
	MagicTablet woodArtistsTablet3 = new MagicTablet("woodArtist3",FiveElements.Type.WOOD,3);
	MagicTablet woodArtistsTablet4 = new MagicTablet("woodArtist4",FiveElements.Type.WOOD,4);
	MagicTablet forbiddenArtistsTablet1 = new MagicTablet("forbiddenArtist1",FiveElements.Type.FORBIDDEN,1);
	MagicTablet forbiddenArtistsTablet2 = new MagicTablet("forbiddenArtist2",FiveElements.Type.FORBIDDEN,2);
	MagicTablet forbiddenArtistsTablet3 = new MagicTablet("forbiddenArtist3",FiveElements.Type.FORBIDDEN,3);
	MagicTablet forbiddenArtistsTablet4 = new MagicTablet("forbiddenArtist4",FiveElements.Type.FORBIDDEN,4);

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		fireArtistsTablet1.setSpellList(s.detectAura,s.fireArrow,s.fireVeil,s.selfBurning);
		fireArtistsTablet2.setSpellList(s.detectAura,s.fireArrow,s.lifeBoost,s.buildUp,s.fireVeil,s.selfBurning,s.heroism);
		fireArtistsTablet3.setSpellList(s.fireVeil,s.woodVeil,s.lifeBoost,s.selfBurning,s.missileGuard,s.meditation,s.holySeal,s.purify,s.firewall,s.overGrowth);
		fireArtistsTablet4.setSpellList(s.fireVeil,s.overGrowth,s.selfBurning,s.detectBlood,s.meditation,s.fireStorm,s.firewall,s.deadlyDrive,s.blaster,s.darkSeal,s.lifeBoost);
		waterArtistsTablet1.setSpellList(s.detectUndead,s.cloudCall,s.purify,s.waterVeil);
		waterArtistsTablet2.setSpellList(s.detectUndead,s.cloudCall,s.detectGold,s.waterVeil,s.purify,s.bubbleBlow,s.overGrowth);
		waterArtistsTablet3.setSpellList(s.waterVeil,s.lifeBoost,s.bubbleBlow,s.sleep,s.shock,s.waterShield,s.superSonic,s.iceNeedle);
		waterArtistsTablet4.setSpellList(s.waterVeil,s.detectUndead,s.superSonic,s.cloudCall,s.bubbleBlow,s.slowStream,s.callThunder,s.meditation,s.detectBlood,s.purify,s.iceNeedle);
		earthArtistsTablet1.setSpellList(s.detectAnimal,s.boulder,s.sleep,s.earthVeil);
		earthArtistsTablet2.setSpellList(s.detectAnimal,s.earthVeil,s.boulder,s.detectAura,s.sleep,s.armorBless,s.buildUp);
		earthArtistsTablet3.setSpellList(s.cloudCall,s.selfBurning,s.shock,s.armorBless,s.superSonic,s.aegisShield,s.waterShield,s.missileGuard,s.firewall,s.recycle);
		earthArtistsTablet4.setSpellList(s.earthVeil,s.buildUp,s.heroism,s.removeFear,s.gravity,s.armorBless,s.aegisShield,s.magicLock,s.sleep,s.metalVeil);
		metalArtistsTablet1.setSpellList(s.detectGold,s.magicLock,s.metalVeil,s.shock);
		metalArtistsTablet2.setSpellList(s.detectGold,s.metalVeil,s.sleep,s.purify,s.magicLock,s.buildUp,s.weaponBless);
		metalArtistsTablet3.setSpellList(s.detectAura,s.metalVeil,s.shock,s.selfBurning,s.weaponBless,s.holySeal,s.superSonic,s.removeFear,s.armorBless,s.fireVeil);
		metalArtistsTablet4.setSpellList(s.metalVeil,s.magicLock,s.shock,s.waterShield,s.spoil,s.removeFear,s.gravity,s.superSonic,s.deadlyDrive,s.iceNeedle,s.waterVeil);
		woodArtistsTablet1.setSpellList(s.detectPlant,s.overGrowth,s.woodVeil,s.lifeBoost);
		woodArtistsTablet2.setSpellList(s.detectPlant,s.purify,s.overGrowth,s.lifeBoost,s.heroism,s.cloudCall,s.recycle);
		woodArtistsTablet3.setSpellList(s.woodVeil,s.detectPlant,s.purify,s.overGrowth,s.cloudCall,s.removeFear,s.callThunder,s.animalCharm,s.animalCharm,s.detectAnimal);
		woodArtistsTablet4.setSpellList(s.woodVeil,s.detectPlant,s.missileGuard,s.meditation,s.callThunder,s.slowStream,s.darkSeal,s.recycle,s.lifeBoost,s.overGrowth);
		forbiddenArtistsTablet1.setSpellList(s.detectBlood);
		forbiddenArtistsTablet2.setSpellList(s.detectPlant,s.detectAnimal,s.detectUndead,s.detectBlood,s.spoil,s.buildUp,s.lifeBoost,s.darkSeal);
		forbiddenArtistsTablet3.setSpellList(s.detectUndead,s.weaponBless,s.firewall,s.spoil,s.blaster,s.blaster,s.spellMagnet,s.superSonic,s.superSonic,s.iceNeedle,s.boulder,s.cloudCall);
		forbiddenArtistsTablet4.setSpellList(s.detectBlood,s.weakness,s.spellMagnet,s.shadowServant,s.blaster,s.spoil,s.deadlyDrive,s.darkSeal);

		this.validate();
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	public static ItemStack getRandomTablet(Random rand){
		MagicTablet tablet = TabletRegistry.instance().getRandomObject(rand);
		return tablet.getStack(1);
	}

	public static MagicTablet getRandomTabletByWeight(Random rand){
		List<WeightedRandomItem<MagicTablet>> list = Lists.newArrayList();
		for(MagicTablet tablet:TabletRegistry.instance().getProperties()){
			WeightedRandomItem<MagicTablet> weighted = new WeightedRandomItem((int)Math.pow((10-(tablet.getTier()*2)), 2),tablet);
			list.add(weighted);
		}
		return WeightedRandom.getRandomItem(rand, list).getItem();

	}
	@Override
	protected void registerObjects() {
		this.put(fireArtistsTablet1);
		this.put(fireArtistsTablet2);
		this.put(fireArtistsTablet3);
		this.put(fireArtistsTablet4);
		this.put(waterArtistsTablet1);
		this.put(waterArtistsTablet2);
		this.put(waterArtistsTablet3);
		this.put(waterArtistsTablet4);
		this.put(earthArtistsTablet1);
		this.put(earthArtistsTablet2);
		this.put(earthArtistsTablet3);
		this.put(earthArtistsTablet4);
		this.put(metalArtistsTablet1);
		this.put(metalArtistsTablet2);
		this.put(metalArtistsTablet3);
		this.put(metalArtistsTablet4);
		this.put(woodArtistsTablet1);
		this.put(woodArtistsTablet2);
		this.put(woodArtistsTablet3);
		this.put(woodArtistsTablet4);
		this.put(forbiddenArtistsTablet1);
		this.put(forbiddenArtistsTablet2);
		this.put(forbiddenArtistsTablet3);
		this.put(forbiddenArtistsTablet4);

	}

	public void validate(){
		for(MagicTablet tablet:this.getProperties()){
			for(Spell spell:tablet.getSpellList()){
				com.google.common.base.Preconditions.checkNotNull(SpellRegistry.instance().get(spell.getKey().getResourcePath()),spell.getPropertyName());
			}
		}
	}
}
