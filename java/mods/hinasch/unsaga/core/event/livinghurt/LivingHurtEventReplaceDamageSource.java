//package mods.hinasch.unsaga.core.event.livinghurt;
//
//import java.util.EnumSet;
//
//import com.google.common.base.Supplier;
//
//import mods.hinasch.lib.iface.ILivingHurtEvent;
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
//import mods.hinasch.unsaga.util.TaggedArrowHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//public class LivingHurtEventReplaceDamageSource extends ILivingHurtEvent{
//
//	@Override
//	public boolean apply(LivingHurtEvent e, DamageSource dsu) {
//		// TODO 自動生成されたメソッド・スタブ
//		return true;
//	}
//
//	@Override
//	public String getName() {
//		// TODO 自動生成されたメソッド・スタブ
//		return "replace DamageSource To Custom DamageSource Event";
//	}
//
//	@Override
//	public DamageSource process(final LivingHurtEvent e, final DamageSource dsu) {
//		DamageSourceUnsaga newDS = new Supplier<DamageSourceUnsaga>(){
//
//			@Override
//			public DamageSourceUnsaga get() {
//				if(dsu instanceof DamageSourceUnsaga){
//					return (DamageSourceUnsaga) dsu;
//				}
//				if(dsu.getSourceOfDamage() instanceof EntityArrow){
//					EntityArrow arrow = (EntityArrow) dsu.getSourceOfDamage();
//					if(TaggedArrowHelper.hasCapability(arrow)){
//						if(dsu.getEntity() instanceof EntityLivingBase){
//							return TaggedArrowHelper.getCapability(arrow).getUnsagaDamageSource().apply((EntityLivingBase) dsu.getEntity());
//						}else{
//							return TaggedArrowHelper.getCapability(arrow).getUnsagaDamageSource().apply(null);
//						}
//					}
//				}
//				if(dsu.getEntity()!=null){
//					Entity attacker = dsu.getEntity();
//					if(attacker instanceof EntityLivingBase){
//
//
//						EntityLivingBase living = (EntityLivingBase) attacker;
//
//						ItemStack stack = living.getHeldItemMainhand();
//						if(stack!=null){
//							if(stack.getItem() instanceof IUnsagaDamageSource){
//								return ((IUnsagaDamageSource)stack.getItem()).getUnsagaDamageSource().apply(living);
//							}
//						}
//						if(stack==null){
//							return DamageHelper.DEFAULT_PUNCH.apply(living);
//						}
//
//					}
//				}
//
//
//				return DamageSourceUnsaga.fromVanilla(dsu);
//			}
//		}.get();
//		//		HSLib.logger.trace("kiteru");
//		float modified = e.getAmount() + DamageTypeUnsaga.getDamageModifierFromType(newDS.getDamageTypeUnsaga()!=null ? newDS.getDamageTypeUnsaga() : EnumSet.of(General.SWORD) ,e.getEntityLiving(), e.getAmount());
//		if(!newDS.getSubTypes().isEmpty()){
//			modified = modified + DamageTypeUnsaga.getDamageModifierFromType(newDS.getSubTypes(), e.getEntityLiving(), modified);
//		}
//		e.setAmount(modified);
//		if(e.getAmount()<0){
//			e.setAmount(0);
//		}
//
//
//
//
//		return newDS;
//	}
//
//
//	@Override
//	public int getWeight() {
//		// TODO 自動生成されたメソッド・スタブ
//		return 30;
//	}
//
//}
