package mods.hinasch.unsaga.core.event;

import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.EnvironmentalManager;
import mods.hinasch.lib.world.EnvironmentalManager.EnvironmentalCondition.Type;
import mods.hinasch.unsaga.ability.Ability;
import mods.hinasch.unsaga.ability.AbilityRegistry;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventOnBed {

	Ability coldProtection = AbilityRegistry.instance().armorCold;
	Ability coldProtection2 = AbilityRegistry.instance().armorColdEx;

	@SubscribeEvent
	public void onBedEvent(final PlayerSleepInBedEvent e){

		World world = e.getEntityPlayer().worldObj;
		XYZPos pos = XYZPos.createFrom(e.getEntityPlayer());


		if(EnvironmentalManager.getCondition(world, pos, world.getBiome(pos),e.getEntityPlayer()).result==Type.COLD){

			ChatHandler.sendChatToPlayer(e.getEntityPlayer(), HSLibs.translateKey("msg.bed.cantsleep.cold"));
			e.setResult(SleepResult.OTHER_PROBLEM);


		}


	}


}
