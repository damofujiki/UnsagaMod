package mods.hinasch.unsagamagic.spell;

import java.util.List;
import java.util.Optional;

import mods.hinasch.lib.capability.ISyncCapability;

public interface ISpellBook extends ISyncCapability {


	public int getCapacity();
	public void setCapacity(int size);
	public void addSpell(Spell spell);
	public void addSpell(SpellComponent spell);
	public List<SpellComponent> getRawSpells();
	public List<Spell> getSpells();
	public SpellComponent getSpell(int index);
	public Optional<SpellComponent> getCurrentSpell();
	public void nextSpell();
	public void clear();
	public boolean isSpellFull();
	public int getIndex();
	public void setIndex(int par1);
	public void setSpells(List<SpellComponent> list);
	public boolean isCurrentSpellSame(Spell spell);
	public int getCurrentIndex();
}
