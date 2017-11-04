package mods.hinasch.unsagamagic.spell;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;

import mods.hinasch.lib.misc.JsonHelper.IJsonParser;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.element.newele.ElementTable;

public class JsonParserSpell implements IJsonParser{

	String name;
	ElementTable amp;
	ElementTable cost;
	ElementTable mix;
	@Override
	public void parse(JsonObject jo) {
		this.name = jo.get("Name").getAsString();
		String ampstr = jo.get("amp").getAsString();
		Splitter.on(",").split(ampstr);
		this.amp = JsonParserSpell.parseToElements(ampstr, 0.01F);
		String coststr  = jo.get("cost").getAsString();
		if(!coststr.equals("")){
			this.cost = JsonParserSpell.parseToElements(coststr, 0.01F);
		}else{
			this.cost = this.amp;
		}

		String mixstr = jo.get("mix").getAsString();
		UnsagaMod.logger.trace(name, ampstr,coststr,mixstr);
//		Preconditions.checkArgument(mixstr.equals(""),name+"'s mix table is empty.");
		this.mix = JsonParserSpell.parseToElements(mixstr, 1.0F);
	}

	public static ElementTable parseToElements(String str,float scale){
		List<Integer> list = Lists.newArrayList();
		Splitter.on(",").split(str).forEach(in ->{
			list.add(Integer.parseInt(in));
		});
		Preconditions.checkArgument(list.size() >=6,"サイズがへんです"+str);
		ElementTable table = new ElementTable(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5));
		return table.scale(scale);
	}


	public static class Blend extends JsonParserSpell{

		Spell spell;

		@Override
		public void parse(JsonObject jo) {
			this.name = jo.get("name").getAsString();
			String base = jo.get("base").getAsString();
			Preconditions.checkArgument(SpellRegistry.instance().get(base)!=null,base+" is not found!");
			this.spell = SpellRegistry.instance().get(base);
			String mixstr = jo.get("table").getAsString();
			this.mix = JsonParserSpell.parseToElements(mixstr, 1.0F);
		}
	}
}
