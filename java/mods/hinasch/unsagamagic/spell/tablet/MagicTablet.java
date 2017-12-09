package mods.hinasch.unsagamagic.spell.tablet;

import java.util.List;

import com.google.common.collect.ImmutableList;

import mods.hinasch.lib.registry.PropertyElementBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import mods.hinasch.unsagamagic.spell.Spell;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MagicTablet extends PropertyElementBase implements Comparable<MagicTablet>{

	ImmutableList<Spell> spellList = ImmutableList.of();
//	Map<Spell,Integer> difficultyMap = Maps.newHashMap();
	final FiveElements.Type element;
	final int tier;
	public MagicTablet(String name,FiveElements.Type type,int tier) {
		super(new ResourceLocation(name), name);
		this.element = type;
		this.tier = tier;
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void setSpellList(Spell... spells){
		this.spellList = ImmutableList.copyOf(spells);
	}

	public List<Spell> getSpellList(){
		return this.spellList;
	}

	public int getTier(){
		return this.tier;
	}

	public FiveElements.Type getElement(){
		return this.element;
	}

	@Override
	public Class getParentClass() {
		// TODO 自動生成されたメソッド・スタブ
		return MagicTablet.class;
	}

	public String getLocalized(){
		return HSLibs.translateKey("tablet."+this.getPropertyName());
	}

	public ItemStack getStack(int amount){
		ItemStack stack = new ItemStack(UnsagaMagicItems.instance().tablet,amount);
		if(TabletCapability.adapter.hasCapability(stack)){
			TabletCapability.adapter.getCapability(stack).setTabletData(this);
		}
		return stack;
	}
	@Override
	public int compareTo(MagicTablet o) {
		if(this.getTier()>o.getTier()){
			return 1;
		}
		if(this.getTier()==o.getTier()){
			return 0;
		}
		return -1;
	}
}
