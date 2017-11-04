package mods.hinasch.unsagamagic.spell.action;

import java.util.Optional;
import java.util.function.Consumer;

import mods.hinasch.unsaga.common.specialaction.ISpecialActionBase;
import net.minecraft.util.SoundEvent;


public abstract class SpellAction implements ISpecialActionBase<SpellCaster>{

	Optional<SoundEvent> castSound = Optional.empty();
	private Consumer<SpellCaster> prePerform = in ->{};
	public Optional<SoundEvent> getCastSound(){
		return this.castSound;
	}
	public SpellAction setPrePerform(Consumer<SpellCaster> p){
		this.prePerform = p;
		return this;
	}

	public Consumer<SpellCaster> getPrePerform(){
		return this.prePerform;
	}
	public SpellAction setCastSound(SoundEvent event){
		this.castSound = Optional.of(event);
		return this;
	}
}
