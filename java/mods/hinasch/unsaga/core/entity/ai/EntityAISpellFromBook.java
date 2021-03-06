package mods.hinasch.unsaga.core.entity.ai;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsagamagic.item.ItemSpellBook;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import mods.hinasch.unsagamagic.spell.spellbook.SpellBookCapability;
import net.minecraft.item.ItemStack;

public class EntityAISpellFromBook extends EntityAISpell{


	public static final List<Spell> blackList = Lists.newArrayList(SpellRegistry.instance().shadowServant,SpellRegistry.instance().simulacrum);
	public static final List<Spell> castBonus = Lists.newArrayList(SpellRegistry.instance().fireArrow,SpellRegistry.instance().bubbleBlow,SpellRegistry.instance().stoneShower);
	public EntityAISpellFromBook(ISpellAI host,double moveSpeed, int interval,
			float search, int chance) {
		super(host, Lists.newArrayList(), moveSpeed, interval, search, chance);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	@Override
    public List<SpellAIData> getSpellList(){
    	ItemStack held = this.entityHost.getHeldItemMainhand();
    	if(ItemUtil.isItemStackPresent(held)){
    		if(held.getItem() instanceof ItemSpellBook && SpellBookCapability.adapter.hasCapability(held)){
    			return SpellBookCapability.adapter.getCapability(held).getSpells()
    					.stream().filter(in -> !blackList.contains(in)).map(in -> new SpellAIData(in, 30.0D, 0.0D, castBonus.contains(in)? in.getBaseCastingTime()/2:in.getBaseCastingTime())).collect(Collectors.toList());

    		}
    	}
    	return Lists.newArrayList();
    }

}
