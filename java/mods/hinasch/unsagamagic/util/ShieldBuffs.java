//package mods.hinasch.unsagamagic.util;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
//import mods.hinasch.unsaga.debuff.Buff;
//import mods.hinasch.unsaga.debuff.DebuffRegistry;
//import mods.hinasch.unsaga.element.FiveElements;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.monster.EntityBlaze;
//import net.minecraft.entity.monster.EntityMagmaCube;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.entity.projectile.EntityFireball;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//public class ShieldBuffs {
//
//	private Set<ShieldBuffAttribute> shieldSet = new HashSet();
//	protected DebuffRegistry debuffs = DebuffRegistry.getInstance();
//	protected ShieldBuffAttribute shieldMissileGuard = new BuffShieldMissileGuard(debuffs.missuileGuard,true,FiveElements.Type.WOOD);
//	protected ShieldBuffAttribute shieldLeaves = new BuffShieldLeavesShield(debuffs.leavesShield,false,FiveElements.Type.WOOD);
//	protected ShieldBuffAttribute shieldWater = new BuffShieldWaterShield(debuffs.waterShield,false,FiveElements.Type.WATER);
//	protected ShieldBuffAttribute shieldAegis = new BuffShieldAegis(debuffs.aegisShield,false,FiveElements.Type.EARTH);
//
//	private static ShieldBuffs INSTANCE;
//
//	protected ShieldBuffs(){
//
//		shieldSet.add(shieldAegis);
//		shieldSet.add(shieldLeaves);
//		shieldSet.add(shieldMissileGuard);
//		shieldSet.add(shieldWater);
//	}
//	public static ShieldBuffs instance(){
//		if(INSTANCE == null){
//			INSTANCE = new ShieldBuffs();
//		}
//		return INSTANCE;
//	}
//	public class BuffShieldMissileGuard extends ShieldBuffAttribute{
//
//		public BuffShieldMissileGuard(Buff parent, boolean isGuardAll,
//				FiveElements.Type element) {
//			super(parent, isGuardAll, element);
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//		@Override //突攻撃や矢を防ぐことがある
//		public boolean isEffective(LivingHurtEvent e,DamageSourceUnsaga dsu) {
//			if(e.getSource().getSourceOfDamage() instanceof EntityArrow){
//				return true;
//			}
//
//
//
//			if(dsu.getDamageTypeUnsaga().contains(General.SPEAR)){
//				return true;
//			}
//
//			return false;
//		}
//
//
//
//	}
//	public class BuffShieldAegis extends ShieldBuffAttribute{
//
//		public BuffShieldAegis(Buff parent, boolean isGuardAll,
//				FiveElements.Type element) {
//			super(parent, isGuardAll, element);
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//		@Override //ブロック無効以外すべての攻撃が対象
//		public boolean isEffective(LivingHurtEvent e,DamageSourceUnsaga dsu) {
//			if(!e.getSource().isUnblockable()){
//				return true;
//			}
//			return false;
//		}
//
//	}
//
//	public class BuffShieldLeavesShield extends ShieldBuffAttribute{
//
//		public BuffShieldLeavesShield(Buff parent, boolean isGuardAll,
//				FiveElements.Type element) {
//			super(parent, isGuardAll, element);
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//		@Override //ブロック無効以外・魔法以外・生物の攻撃なら防ぐことがある
//		public boolean isEffective(LivingHurtEvent e,DamageSourceUnsaga dsu) {
//			if(e.getSource().getEntity() instanceof EntityLivingBase && !e.getSource().isMagicDamage() && !e.getSource().isUnblockable()){
//				return true;
//			}
//			return false;
//		}
//
//	}
//
//	public class BuffShieldWaterShield extends ShieldBuffAttribute{
//
//		public BuffShieldWaterShield(Buff parent, boolean isGuardAll,
//				FiveElements.Type element) {
//			super(parent, isGuardAll, element);
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//		@Override //とりあえず火に関係するものは防ぐことがある（発火ダメージ含む）
//		public boolean isEffective(LivingHurtEvent e,DamageSourceUnsaga dsu) {
//			if(e.getSource().getEntity() instanceof EntityBlaze || e.getSource().getEntity() instanceof EntityMagmaCube){
//				return true;
//			}
//			if(e.getSource().getEntity() instanceof EntityFireball){
//				return true;
//			}
//			if(e.getSource().isFireDamage()){
//				return true;
//			}
//
//
//			if(dsu.getSubTypes().contains(Sub.FIRE)){
//				return true;
//			}
//
//
//			return false;
//		}
//
//	}
//
//	public Set<ShieldBuffAttribute> getShields(){
//		return this.shieldSet;
//	}
//}
