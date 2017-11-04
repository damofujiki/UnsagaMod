package mods.hinasch.unsaga.status;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventAdditionalStatusAppliedDamage extends LivingHurtEventUnsagaBase{

	@Override
	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		// TODO 自動生成されたメソッド・スタブ
		return dsu.isMagicDamage();
	}

	@Override
	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		double mental = e.getEntityLiving().getEntityAttribute(AdditionalStatus.MENTAL).getAttributeValue();
		double additional = e.getAmount() * (1.0D - mental);
		double amount = e.getAmount() + additional;

		if(dsu.getEntity() instanceof EntityLivingBase){
			EntityLivingBase attacker = (EntityLivingBase) dsu.getEntity();
			double intel = attacker.getEntityAttribute(AdditionalStatus.INTELLIGENCE).getAttributeValue();
			double additional_intel = e.getAmount() * (intel - 1.0D);
			UnsagaMod.logger.trace(this.getName(), "status",additional,additional_intel);
			amount += additional_intel;
		}

		e.setAmount((float) amount);
		return dsu;
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "mental & intel applied";
	}

}
