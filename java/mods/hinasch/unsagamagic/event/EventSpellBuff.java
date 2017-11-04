//package mods.hinasch.unsagamagic.event;
//
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.util.ILivingHurtEventUnsaga;
//import mods.hinasch.unsagamagic.util.ShieldBuffs;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//public class EventSpellBuff extends ILivingHurtEventUnsaga {
//
//
//
//	ShieldBuffs shieldBuffs = ShieldBuffs.instance();
//	//	@SubscribeEvent
//	//	public void onPlayerHurtDebuff(LivingHurtEvent e){
//	//
//	//		for(BuffShieldBase shield:shieldSet){
//	//			shield.checkShield(e);
//	//		}
//	//
//	//
//	//
//	//	}
//
//	@Override
//	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//		// TODO 自動生成されたメソッド・スタブ
//		return true;
//	}
//
//	@Override
//	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//		shieldBuffs.getShields().forEach(in -> in.checkShield(e, dsu));
//		return dsu;
//	}
//
//	@Override
//	public String getName() {
//		// TODO 自動生成されたメソッド・スタブ
//		return "Buff Shield Event";
//	}
//}
