package mods.hinasch.unsagamagic.spell;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.misc.JsonHelper.IJsonApply;
import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.damage.PairDamage;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.element.newele.ElementTable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class Spell extends PropertyElementBase implements INBTWritable,Comparable<Spell>,IJsonApply<JsonParserSpell>{


	final FiveElements.Type element;
	ElementTable ampTable = ElementTable.ZERO;
	ElementTable costTable = ElementTable.ZERO;
	ElementTable mixTable = ElementTable.ZERO;
	float growth = 1.0F;
	int baseDuration = 30;
	int difficulty = 10;
	int baseCastingTime = 10;
	int cost = 10;
	PairDamage damagePair = PairDamage.of(1, 1);
	boolean isRequireSetPoint = false;
	TipsType tipsType = TipsType.NONE;

	public static enum TipsType{
		TARGETTABLE,POINT,POINT_TARGET,NONE,ITEM;

		public List<String> getTips(){
			List<String> list = Lists.newArrayList();
			switch(this){
			case ITEM:
				list.add(HSLibs.translateKey("spell.tips.item"));
				break;
			case NONE:
				break;
			case POINT:
				list.add(HSLibs.translateKey("spell.tips.point1"));
				list.add(HSLibs.translateKey("spell.tips.point2"));
				break;
			case POINT_TARGET:
				list.add(HSLibs.translateKey("spell.tips.point1"));
				list.add(HSLibs.translateKey("spell.tips.point2.alt"));
				break;
			case TARGETTABLE:
				list.add(HSLibs.translateKey("spell.tips.target"));
				break;
			default:
				break;

			}
			if(!list.isEmpty()){
				if(GuiScreen.isShiftKeyDown()){

				}else{
					list.clear();
					list.add("Press Shift To Display Tips.");
				}
			}
			return list;
		}
	}
	public static RestoreFunc<Spell> RESTORE = new RestoreFunc<Spell>(){

		@Override
		public Spell apply(NBTTagCompound input) {
			String id = input.getString("id");
			return SpellRegistry.instance().get(id);
		}
	};
	public Spell(String name,FiveElements.Type type) {
		super(new ResourceLocation(name), name);
		this.element = type;
	}

	public Spell setDuration(int duration){
		this.baseDuration = duration;
		return this;
	}

	public int getDuration(){
		return this.baseDuration;
	}

	public Spell setTipsType(TipsType type){
		this.tipsType = type;
		return this;
	}

	public TipsType getTipsType(){
		return this.tipsType;
	}
	public Spell setStrength(float hp,float lp){
		this.damagePair = PairDamage.of(hp, lp);
		return this;
	}

	public boolean isRequireCoordinate(){
		return this.isRequireSetPoint;
	}

	public Spell setRequireCoordinate(boolean par1){
		this.isRequireSetPoint = par1;
		return this;
	}
	/**
	 */
	public PairDamage getEffectStrength(){
		return this.damagePair;
	}
	public int getBaseCastingTime(){
		return this.baseCastingTime;
	}

	public Spell setBaseCastingTime(int par1){
		this.baseCastingTime = par1;
		return this;
	}
	public String getLocalized(){
		return HSLibs.translateKey("spell."+this.getName());
	}

	/*: 消費MP、属性も含む*/
	public String getLocalizedByFullText(){
		String str1 = this.getLocalized() + "[" + this.getElement().getLocalized() + "]";
		String str2 = HSLibs.translateKey("spell.unsaga.tooltip.cost", this.getCost());
		return str1+"/"+str2;
	}
	public int getDifficulty(){
		return this.difficulty;
	}

	public int getCost(){
		return this.cost;
	}

	public Spell setCost(int par1){
		this.cost = par1;
		return this;
	}
	public Spell setDifficulty(int par1){
		this.difficulty = par1;
		return this;
	}
	public FiveElements.Type getElement(){
		return this.element;
	}

	public ElementTable getCostTable(){
		return this.costTable;
	}
	public ElementTable getAmplifyTable(){
		return this.ampTable;
	}
	public ElementTable getSpellMixTable(){
		return this.mixTable;
	}
	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return Spell.class;
	}

	public Spell setGrowth(float g){
		this.growth = g;
		return this;
	}

	/**
	 * 威力成長率。amplify * 成長率
	 * @return
	 */
	public float getGrowth(){
		return this.growth;
	}
	@Override
	public void writeToNBT(NBTTagCompound stream) {
		stream.setString("id", this.getKey().getResourcePath());

	}

	@Override
	public int compareTo(Spell o) {
		return this.getElement().compareTo(o.getElement());
	}

	@Override
	public void applyJson(JsonParserSpell parser) {
		this.ampTable = parser.amp;
		this.costTable = parser.cost;
		this.mixTable = parser.mix;
		UnsagaMod.logger.trace(name, " successfully applied:"+this.ampTable+"/"+this.costTable+"/"+this.mixTable);
	}


}
