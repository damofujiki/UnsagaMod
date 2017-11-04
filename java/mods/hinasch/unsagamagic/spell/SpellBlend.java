package mods.hinasch.unsagamagic.spell;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.element.FiveElements.Type;
import mods.hinasch.unsaga.element.newele.ElementTable;

public class SpellBlend extends Spell{

	Map<Spell,ElementTable> requireMix = Maps.newHashMap();

	public SpellBlend(String name, Type type) {
		super(name, type);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
	public int getDifficulty(){
		return 999;
	}


	public Map<Spell,ElementTable> getRequireElementTable(){
		return this.requireMix;
	}

	public boolean containsBaseSpell(Spell spell){
		return this.getRequireElementTable().containsKey(spell);
	}

	public boolean canChange(Spell base,ElementTable other){
		if(this.containsBaseSpell(base)){
			ElementTable table = this.getRequireElementTable().get(base);
//			UnsagaMod.logger.trace("table", table);
			return other.isBiggerOrEqual(table);
		}
		return false;
	}
	@Override
	public void applyJson(JsonParserSpell p) {
		JsonParserSpell.Blend parser = (JsonParserSpell.Blend)p;
		this.requireMix.put(parser.spell, parser.mix);
		UnsagaMod.logger.trace(this.getName(), "applied:"+parser.spell+":"+parser.mix);
	}
}
