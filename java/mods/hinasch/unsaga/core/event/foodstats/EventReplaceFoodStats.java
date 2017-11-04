package mods.hinasch.unsaga.core.event.foodstats;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 *
 *
 *https://github.com/wuppy29/WuppyMods/blob/master/Harder%20Peaceful/hp_common/com/wuppy/harderpeaceful/EventManager.java
 */
public class EventReplaceFoodStats {
	@SubscribeEvent
	public void updateFood(EntityJoinWorldEvent event) {
//		if(event.getEntity() instanceof EntityPlayer){
//			UnsagaMod.logger.trace("trying to replace foodstats",this.getClass());
//			FoodStats oldStats = ((EntityPlayer)event.getEntity()).getFoodStats();
//			setFoodStats((EntityPlayer) event.getEntity(), new FoodStatsUnsaga(oldStats));
//		}
		if(event.getWorld().getGameRules().getBoolean("naturalRegeneration"))
		{
			event.getWorld().getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
		}
	}

	public static void setFoodStats(EntityPlayer player, FoodStats stats)
	{
		Field[] fields = EntityPlayer.class.getDeclaredFields();

		for (Field f : fields)
		{
			if (f.getType() == FoodStats.class)
			{
				f.setAccessible(true);
				try
				{
					f.set(player, stats);
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			}
		}
	}
}
