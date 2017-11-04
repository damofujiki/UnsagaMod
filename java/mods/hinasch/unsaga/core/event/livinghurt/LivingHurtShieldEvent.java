//package mods.hinasch.unsaga.core.event.livinghurt;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Maps;
//
//import mods.hinasch.lib.network.PacketSound;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.network.SoundPacket;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.Ability;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.core.item.armor.ItemShieldUnsaga;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.skillpanel.SkillPanels;
//import mods.hinasch.unsaga.util.ILivingHurtEventUnsaga;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//public class LivingHurtShieldEvent extends ILivingHurtEventUnsaga{
//
//	public static Map<Ability,Predicate<DamageSourceUnsaga>> map = Maps.newHashMap();
//	@Override
//	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//		if(this.isBlocking(e.getEntityLiving())){
//			UnsagaMod.logger.trace(this.getName(), "ブロックしている");
//			ItemStack shieldStack = e.getEntityLiving().getActiveItemStack();
//			if(this.isUnsagaShield(shieldStack)){
//				shieldStack.damageItem(1, e.getEntityLiving());
//				UnsagaMod.logger.trace(this.getName(), "盾を取得できました");
//				int shieldPower = AbilityHelper.getCapability(shieldStack).getUnsagaMaterial().getArmorMaterial().getDamageReductionAmount(ItemShieldUnsaga.getReferenceArmor());
//				float reductionAmount =
//						DamageHelper.calcReduceAmount(e.getAmount(), ItemShieldUnsaga.SHIELD_PER, ItemShieldUnsaga.SHIELD_PER+2, shieldPower, e.getEntityLiving().getRNG());
//				float reduced = e.getAmount() - reductionAmount;
//				UnsagaMod.logger.trace(getName(), "リダクションされた値:"+reduced);
//				e.setAmount(reduced);
//				if(e.getEntityLiving() instanceof EntityPlayer){
//					UnsagaMod.logger.trace(this.getName(), "プレイヤーでした");
//					EntityPlayer player = (EntityPlayer) e.getEntityLiving();
//
//					return SkillPanels.hasPanel(player.worldObj, player, UnsagaMod.skillPanels.shield);
//				}
//				return true;
//			}
//
//		}
//
//		return false;
//
//	}
//
//	public boolean isUnsagaShield(ItemStack shieldStack){
//		return shieldStack!=null && AbilityHelper.hasCapability(shieldStack) && AbilityHelper.getCapability(shieldStack).getToolCategory()==ToolCategory.SHIELD;
//	}
//	public boolean isBlocking(EntityLivingBase living){
//		if(living instanceof EntityPlayer){
//			return living.isActiveItemStackBlocking();
//		}
//		return living.getRNG().nextInt(10)==0;
//	}
//
//	@Override
//	public String getName() {
//		// TODO 自動生成されたメソッド・スタブ
//		return "Shield Ability Event";
//	}
//
//	@Override
//	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//		UnsagaMod.logger.trace(this.getName(), "スキルがありました");
//		int shieldLevel = (e.getEntityLiving() instanceof EntityPlayer)? SkillPanels.getHighestLevelPanel(e.getEntityLiving().getEntityWorld(), (EntityPlayer) e.getEntityLiving(), UnsagaMod.skillPanels.shield) + 1 : 1;
//		int prob = 10 + (10 * shieldLevel);
//		UnsagaMod.logger.trace(this.getName(), "shieldLevel:"+shieldLevel+" prob:"+prob);
//		List<Ability> abilities = AbilityHelper.getCapability(e.getEntityLiving().getActiveItemStack()).getAbilityList();
//		UnsagaMod.logger.trace(this.getName(), abilities);
//		if(e.getEntityLiving().getRNG().nextInt(100)<prob){
//			for(Entry<Ability,Predicate<DamageSourceUnsaga>> entry:map.entrySet()){
//				if(abilities.contains(entry.getKey())){
//					if(entry.getValue().apply(dsu)){
//						SoundPacket.sendToAllAround(PacketSound.create(1022), PacketUtil.getTargetPointNear(e.getEntityLiving()));
//						e.setAmount(0);
//					}
//				}
//			}
////			if(abilities.contains(UnsagaMod.abilities.avoidMelee) && !dsu.isUnblockable() && !dsu.isDamageAbsolute()){
////				UnsagaMod.logger.trace(getName(), "物理回避のアビリティが発動！");
////				e.setAmount(0);
////			}
////			if(abilities.contains(UnsagaMod.abilities.avoidPunchSlash) && (dsu.getDamageTypeUnsaga().contains(General.SWORD)|| dsu.getDamageTypeUnsaga().contains(General.PUNCH))){
////				UnsagaMod.logger.trace(getName(), "斬打回避のアビリティが発動！");
////				e.setAmount(0);
////			}
////			if(abilities.contains(UnsagaMod.abilities.avoidSpear) && dsu.getDamageTypeUnsaga().contains(General.SPEAR)){
////				UnsagaMod.logger.trace(getName(), "射突回避のアビリティが発動！");
////				e.setAmount(0);
////			}
//		}
//		return dsu;
//	}
//
//	@Override
//	public int getWeight(){
//		return 50;
//	}
//}
