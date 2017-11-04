package mods.hinasch.unsaga.skillpanel;

import java.util.Optional;
import java.util.OptionalInt;

import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import mods.hinasch.unsaga.core.item.newitem.SkillPanelCapability;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SkillPanelAPI {


	public static ItemStackList getPanelStacks(EntityPlayer ep){
		return WorldSaveDataSkillPanel.get(ep.getEntityWorld()).getPanels(ep.getGameProfile().getId());
	}

	public static OptionalInt getLevel(ItemStack panel){
		if(SkillPanelCapability.adapter.hasCapability(panel)){
			return OptionalInt.of(SkillPanelCapability.adapter.getCapability(panel).getLevel());
		}
		return OptionalInt.empty();
	}

	public static Optional<SkillPanel> getSkillPanel(ItemStack panel){
		if(SkillPanelCapability.adapter.hasCapability(panel)){
			return Optional.of(SkillPanelCapability.adapter.getCapability(panel).getPanel());
		}
		return Optional.empty();
	}



	public static boolean hasFamiliar(EntityPlayer ep){
		return SkillPanelRegistry.instance().familiars.stream().allMatch(in ->hasPanel(ep,in));
	}
	public static boolean hasPanel(EntityLivingBase ep,SkillPanel panel){
		if(ep instanceof EntityPlayer){
			return getHighestPanelLevel((EntityPlayer) ep,panel).isPresent();
		}
		return false;
	}
	public static OptionalInt getHighestPanelLevel(EntityLivingBase ep,SkillPanel panel){
		if(ep instanceof EntityPlayer){
			if(getPanelStacks((EntityPlayer) ep)==null){
				return OptionalInt.empty();
			}
			return getPanelStacks((EntityPlayer) ep).getRawList().stream().filter(in ->{
				if(SkillPanelCapability.adapter.hasCapability(in)){
					return SkillPanelCapability.adapter.getCapability(in).getPanel()==panel;
				}
				return false;
			}).mapToInt(in -> SkillPanelCapability.adapter.getCapability(in).getLevel()).max();
		}
		return OptionalInt.empty();
//		if(found.isPresent()){
//			return OptionalInt.of(SkillPanelCapability.adapter.getCapability(found.get()).getLevel());
//		}
//		return OptionalInt.empty();
	}
}
