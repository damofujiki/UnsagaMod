package mods.hinasch.unsaga.skillpanel;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.core.item.newitem.combat.EventShield;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventSkillPanel {

	public static void register(){
		EventShield.registerEvents();
		HSLibs.registerEvent(new EventSaveDamage());
		HSLibs.registerEvent(new EventPacifist());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				// TODO 自動生成されたメソッド・スタブ
				return e.getSource().getEntity() instanceof EntityPlayer && !dsu.isProjectile() && !dsu.isMagicDamage() && !dsu.isExplosion();
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				EntityPlayer ep = (EntityPlayer) e.getSource().getEntity();
				if(SkillPanelAPI.hasPanel(ep,SkillPanelRegistry.instance().punch) && ItemUtil.isItemStackNull(ep.getHeldItemMainhand())){
					int level = SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().punch).getAsInt();
					float amount = 3.0F + (0.5F * level);
					e.setAmount(amount);
				}
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "skillPanel.MartialArts";
			}

		});
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {

				return e.getEntityLiving() instanceof EntityPlayer;
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				EntityPlayer ep = (EntityPlayer) e.getEntityLiving();
				SkillPanelRegistry.instance().damageAgainstSkills.stream().forEach(in ->{
					if(in.getDamageAgainst().contains(dsu.getParentDamageSource())){
						if(SkillPanelAPI.hasPanel(ep, in)){
							int level = SkillPanelAPI.getHighestPanelLevel(ep, in).getAsInt();
							float base = e.getAmount();
							float amount = base - (base * 0.125F * level);
							e.setAmount(amount);
						}

					}
				});
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "skillpanel.damageAgainst";
			}}
		);
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {

				return e.getSource().getEntity() instanceof EntityPlayer;
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				EntityPlayer ep = (EntityPlayer) e.getSource().getEntity();
				SkillPanelRegistry.instance().negativeSkills1.forEach(in ->{
					if(SkillPanelAPI.hasPanel(ep, in.first()) && in.second().test(e.getEntityLiving())){
						e.setAmount(e.getAmount() * 0.5F);
					}
				});
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "skillpanel.damageAgainst";
			}}
		);
	}
}
