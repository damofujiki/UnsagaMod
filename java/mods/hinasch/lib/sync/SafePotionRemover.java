package mods.hinasch.lib.sync;

import java.util.Set;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class SafePotionRemover implements Runnable{

	final Set<Potion> potions;
	public SafePotionRemover(Set<Potion> potions, EntityLivingBase living) {
		super();
		this.potions = potions;
		this.living = living;
	}

	final EntityLivingBase living;

	@Override
	public void run() {
		for(Potion p:potions){
			living.removePotionEffect(p);
		}

	}

}
