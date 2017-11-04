package mods.hinasch.unsaga.ability;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.specialmove.SparklingPointRegistry;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AbilityLearningEvent {


	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e){
		if(e.getSource().getEntity() instanceof EntityPlayer){
			if(e.getEntityLiving() instanceof IMob){
				this.onPlayerAttackMob(e);
			}
		}
	}

	private double calcSparklingModifierBySkill(EntityPlayer player,double d){
		double base = 0.0D;
		if(SkillPanelAPI.getHighestPanelLevel(player,SkillPanelRegistry.instance().artiste).isPresent()){
			base += 0.01D * (double)SkillPanelAPI.getHighestPanelLevel(player, SkillPanelRegistry.instance().artiste).getAsInt();
		}
		return base;
	}
	private void onPlayerAttackMob(LivingAttackEvent e){
		if(WorldHelper.isClient(e.getEntityLiving().getEntityWorld())){
			return;
		}
		EntityPlayer player = (EntityPlayer) e.getSource().getEntity();
		EntityLivingBase mob = e.getEntityLiving();
		Random rand = player.getEntityWorld().rand;
		double sparkling = SparklingPointRegistry.instance().getSparklingPoint(mob);
		sparkling += this.calcSparklingModifierBySkill(player, 0.02D);
		float f = rand.nextFloat();
		if(f<sparkling || UnsagaMod.configHandler.isAlwaysSparkling()){

			ItemStack weapon = player.getHeldItem(EnumHand.MAIN_HAND);
			if(ItemUtil.isItemStackNull(weapon)){
				return;
			}
			UnsagaMod.logger.trace(this.getClass().getName(), AbilityAPI.existLearnableAbility(weapon));
			if(AbilityAPI.existLearnableAbility(weapon)){
				Optional<IAbility> learned = AbilityAPI.learnRandomAbility(player.getRNG(), weapon);
				if(learned.isPresent()){
					HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.BLOCK_ANVIL_PLACE, player), (EntityPlayerMP) player);
					String msg = HSLibs.translateKey("ability.unsaga.sparkling.specialMove", learned.get().getLocalized());
					ChatHandler.sendChatToPlayer(player, msg);
				}

			}
		}
	}
	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e){
		if(e.getSource().getEntity() instanceof EntityPlayer){
			if(e.getEntityLiving() instanceof IMob && e.getEntityLiving().getMaxHealth()>=4.0F){
				this.onPlayerKilledMob(e);
			}
		}
	}

	private void onPlayerKilledMob(LivingDeathEvent e){
		if(WorldHelper.isClient(e.getEntityLiving().getEntityWorld())){
			return;
		}
		EntityPlayer player = (EntityPlayer) e.getSource().getEntity();
		EntityLivingBase mob = e.getEntityLiving();
		Random rand = player.getEntityWorld().rand;
		double sparkling = SparklingPointRegistry.instance().getSparklingPoint(mob);
		sparkling += this.calcSparklingModifierBySkill(player, 0.05D);
		sparkling += 0.1D;
		float f = rand.nextFloat();
		UnsagaMod.logger.trace("sparkling", f,sparkling);
		if(f<sparkling || UnsagaMod.configHandler.isAlwaysSparkling()){

			List<ItemStack> list = AbilityAPI.getAllEquippedArmors(player).stream().filter(in -> AbilityAPI.existLearnableAbility(in)).collect(Collectors.toList());
//			List<ItemStack> list2 = list.stream().filter(in -> AbilityCapability.adapter.hasCapability(in)).collect(Collectors.toList());
			UnsagaMod.logger.trace("sparkling", list);
			if(!list.isEmpty()){
				ItemStack sparkItem = HSLibs.randomPick(rand, list);
				Optional<IAbility> learned = AbilityAPI.learnRandomAbility(player.getRNG(), sparkItem);
				if(learned.isPresent()){
					HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.BLOCK_ANVIL_PLACE, player), (EntityPlayerMP) player);
					String msg = HSLibs.translateKey("ability.unsaga.sparkling.passive", sparkItem.getDisplayName(),learned.get().getLocalized());
					ChatHandler.sendChatToPlayer(player, msg);
				}

			}


		}
	}
}
