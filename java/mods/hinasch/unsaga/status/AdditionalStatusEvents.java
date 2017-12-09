package mods.hinasch.unsaga.status;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.LivingHurtEventBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class AdditionalStatusEvents {

	public static void register(){
		//バニラのダメージソースを入れ替え
		HSLib.core().events.livingHurt.getEventsPre().add(new EventReplaceDamageSource());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new EventAdditionalStatusAppliedDamage());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new EventDisabledLifePoint());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSource dsu) {
				// TODO 自動生成されたメソッド・スタブ
				return dsu instanceof DamageSourceUnsaga;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "Apply Additional Status Effect";
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSource ds) {
				DamageSourceUnsaga dsu = (DamageSourceUnsaga) ds;
				float applied = (float) AdditionalStatus.getAppliedDamage(dsu.getAllDamageType(), e.getEntityLiving(), e.getAmount(),dsu.getReduceOperation());
				e.setAmount(applied);

				return dsu;
			}}
		);

		if(HSLib.isDebug()){
			HSLib.core().events.livingHurt.getEventsPost().add(new LivingHurtEventBase(){

				@Override
				public boolean apply(LivingHurtEvent e, DamageSource dsu) {
					// TODO 自動生成されたメソッド・スタブ
					return dsu instanceof DamageSourceUnsaga;
				}

				@Override
				public String getName() {
					// TODO 自動生成されたメソッド・スタブ
					return "Display DamageSource";
				}

				@Override
				public DamageSource process(LivingHurtEvent e, DamageSource dsu) {

					if(!HSLibs.findPlayerFromBatllers(dsu, e.getEntityLiving()).isEmpty()){
						String m = dsu.toString();
						String m2 = String.format("HP Damage:%.2f", e.getAmount());
						String mes = m+m2;
						HSLibs.broadcastToPlayers(HSLibs.findPlayerFromBatllers(dsu, e.getEntityLiving()), mes);
					}

					return dsu;
				}}
			);
		}

	}
}
