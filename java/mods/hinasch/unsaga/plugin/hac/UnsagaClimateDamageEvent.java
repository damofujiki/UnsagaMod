package mods.hinasch.unsaga.plugin.hac;

import java.util.List;

import defeatedcrow.hac.api.damage.ClimateDamageEvent;
import defeatedcrow.hac.api.damage.DamageSourceClimate;
import mods.hinasch.unsaga.ability.Ability;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.ability.AbilityRegistry;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UnsagaClimateDamageEvent {


	@SubscribeEvent
	public void onClimateDamage(ClimateDamageEvent e){
//		UnsagaMod.logger.trace("called", this.getClass().getName());
		List<Ability> abilities = AbilityAPI.getEffectiveAllPassiveAbilities(e.getEntityLiving());
		float reduce = 0.0F;
		if(e.getEntityLiving() instanceof EntityUnsagaChestNew){
			reduce += 100.0F;
		}
		if(e.getSource()==DamageSourceClimate.climateColdDamage){
			reduce += 0.5F * AbilityAPI.getAbilityAmount(e.getEntityLiving(), AbilityRegistry.instance().armorCold);
			reduce += 1.0F * AbilityAPI.getAbilityAmount(e.getEntityLiving(), AbilityRegistry.instance().armorColdEx);
			if(e.getEntityLiving().isPotionActive(UnsagaPotions.instance().selfBurning)){
				reduce += 10.0F;
			}
		}
		if(e.getSource()==DamageSourceClimate.climateHeatDamage){
			reduce += 0.5F * AbilityAPI.getAbilityAmount(e.getEntityLiving(), AbilityRegistry.instance().armorFire);
			reduce += 1.0F * AbilityAPI.getAbilityAmount(e.getEntityLiving(), AbilityRegistry.instance().armorFireEx);
			if(e.getEntityLiving().isPotionActive(UnsagaPotions.instance().waterShield)){
				reduce += 10.0F;
			}
		}
		float amount = e.getAmount() - reduce;
		if(amount<=0.0F){
			e.setCanceled(true);
		}else{

			e.setAmount(amount);
		}

	}
}
