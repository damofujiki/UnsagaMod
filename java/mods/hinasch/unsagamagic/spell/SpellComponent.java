package mods.hinasch.unsagamagic.spell;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import net.minecraft.nbt.NBTTagCompound;

public class SpellComponent implements INBTWritable{

	final Spell spell;
	final float amplify;
	final float cost;
	final boolean hasMixed;

	public Spell getSpell(){
		return this.spell;
	}
	public float getAmplify() {
		return amplify;
	}
	public float getCost() {
		return cost;
	}
	public boolean hasMixed() {
		return hasMixed;
	}

	public List<String> getLocalizedFull(boolean highlight){
		List<String> list = Lists.newArrayList();
		if(highlight){
			String str = spell.getLocalizedByFullText();
			if(this.hasMixed()){
				str += "->" + SpellUtil.calcCost(spell.getCost(), getAmplify());
			}
			list.add(UnsagaTextFormatting.SIGNIFICANT+str);
		}else{
			list.add(spell.getLocalizedByFullText());
		}

		if(this.hasMixed()){
			list.add(String.format("Amplify:%.2f Cost:%.2f", this.getAmplify(),this.getCost()));
		}
		return list;
	}
	public SpellComponent(Spell spell,float amp,float cost,boolean hasMixed){
		this.spell = spell;
		this.amplify = amp;
		this.cost = cost;
		this.hasMixed = hasMixed;
	}
	@Override
	public void writeToNBT(NBTTagCompound stream) {
		stream.setString("name",spell.getKey().getResourcePath());
		stream.setFloat("amp", amplify);
		stream.setFloat("cost", cost);
		stream.setBoolean("hasMixed", hasMixed);
	}

	public static RestoreFunc<SpellComponent> RESTORE = new RestoreFunc<SpellComponent>(){

		@Override
		public SpellComponent apply(NBTTagCompound input) {
			String id = input.getString("name");
			Spell spell = SpellRegistry.instance().get(id);
			float amp = input.getFloat("amp");
			float cost = input.getFloat("cost");
			boolean hasMixed = input.getBoolean("hasMixed");
			return new SpellComponent(spell,amp,cost,hasMixed);
		}
	};
}
