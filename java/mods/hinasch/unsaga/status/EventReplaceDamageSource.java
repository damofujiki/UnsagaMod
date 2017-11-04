package mods.hinasch.unsaga.status;

import mods.hinasch.lib.iface.LivingHurtEventBase;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventReplaceDamageSource extends LivingHurtEventBase{


	@Override
	public boolean apply(LivingHurtEvent e, DamageSource dsu) {
		// TODO 自動生成されたメソッド・スタブ
		return !(dsu instanceof DamageSourceUnsaga);
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Replace DamageSource";
	}

	@Override
	public DamageSource process(LivingHurtEvent e, DamageSource ds) {

		DamageSourceUnsaga dsu = DamageSourceUnsaga.fromVanilla(ds);
		UnsagaMod.logger.trace(getName(), ds.getSourceOfDamage(),ds.getEntity());
		//矢技の場合
		if(ds.getSourceOfDamage() instanceof EntityCustomArrow && ds.getEntity() instanceof EntityLivingBase){
			StateArrow.Type type = ((EntityCustomArrow)ds.getSourceOfDamage()).getArrowType();
			dsu = type.getDamageSource((EntityLivingBase)ds.getEntity(), ds.getSourceOfDamage());
			float damage = type.getDamage(e.getEntityLiving(), e.getAmount());
			e.setAmount(damage);
		}
		return dsu;
	}

}
