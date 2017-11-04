package mods.hinasch.unsagamagic.enchant;
import java.util.Map;

import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UnsagaEnchantmentEvent {

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e){

		if(e.getSource().getEntity() instanceof EntityLivingBase){
			ItemStack stack = ((EntityLivingBase) e.getSource().getEntity()).getHeldItemMainhand();
			UnsagaMod.logger.trace(this.getClass().getName(), "called",stack);
			if(ItemUtil.isItemStackPresent(stack)){
				if(UnsagaEnchantmentCapability.adapter.hasCapability(stack)){
					UnsagaEnchantmentCapability.adapter.getCapability(stack).onAttack();
					checkEnchantment(UnsagaEnchantmentRegistry.instance().weaponBless,stack);
					checkEnchantment(UnsagaEnchantmentRegistry.instance().sharpness,stack);
				}
			}
		}

	}
	@SubscribeEvent
	public void hookBreakSpeed(BreakSpeed e){
		ItemStack stack = e.getEntityPlayer().getHeldItemMainhand();
		if(ItemUtil.isItemStackPresent(stack)){
			Enchantment sharpness = UnsagaEnchantmentRegistry.instance().sharpness.getEnchantment();
			if(EnchantmentHelper.getEnchantmentLevel(sharpness, stack)>0){
				float speed = e.getOriginalSpeed() * (1.0F + EnchantmentHelper.getEnchantmentLevel(sharpness, stack)*0.2F);
				e.setNewSpeed(speed);
			}
		}
	}
	@SubscribeEvent
	public void onBreakBlock(BreakEvent e){
		ItemStack stack = e.getPlayer().getHeldItemMainhand();
		if(ItemUtil.isItemStackPresent(stack)){
			if(UnsagaEnchantmentCapability.adapter.hasCapability(stack)){
				UnsagaEnchantment sharpness = UnsagaEnchantmentRegistry.instance().sharpness;
				UnsagaEnchantmentCapability.adapter.getCapability(stack).reduceRemainings(sharpness);
				checkEnchantment(UnsagaEnchantmentRegistry.instance().sharpness,stack);
			}
		}
	}

	public static void checkEnchantment(UnsagaEnchantment e,ItemStack is){
		if(UnsagaEnchantmentCapability.adapter.getCapability(is).getEnchantmentRemaining(e)<=0){
			Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(is);
			map.remove(e.getEnchantment());
			EnchantmentHelper.setEnchantments(map, is);
		}
	}

	public static void registerEvent(){
		HSLibs.registerEvent(new UnsagaEnchantmentEvent());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				return Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream().anyMatch(in -> ItemUtil.isItemStackPresent(in) && UnsagaEnchantmentCapability.adapter.hasCapability(in)
						&& !UnsagaEnchantmentCapability.adapter.getCapability(in).isEmpty());
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream().filter(in -> ItemUtil.isItemStackPresent(in) && UnsagaEnchantmentCapability.adapter.hasCapability(in))
				.forEach(in ->{
					UnsagaEnchantmentCapability.adapter.getCapability(in).onHurt();
					checkEnchantment(UnsagaEnchantmentRegistry.instance().armorBless,in);
				});
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "enchantment";
			}}
		);
	}
}
