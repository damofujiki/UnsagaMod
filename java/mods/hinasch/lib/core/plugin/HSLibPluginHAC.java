package mods.hinasch.lib.core.plugin;

import defeatedcrow.hac.api.climate.ClimateAPI;
import defeatedcrow.hac.api.climate.DCHeatTier;
import defeatedcrow.hac.api.climate.DCHumidity;
import defeatedcrow.hac.api.climate.IClimate;
import mods.hinasch.lib.world.EnvironmentalManager;
import mods.hinasch.lib.world.EnvironmentalManager.EnvironmentalCondition;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class HSLibPluginHAC {

	public EnvironmentalCondition getCondition(World w,XYZPos pos,Biome biome,EntityLivingBase living){
		IClimate climate = ClimateAPI.calculator.getClimate(w, pos, new int[]{3,3,3});
		DCHeatTier heatTier = climate.getHeat();
		DCHumidity humid = climate.getHumidity();
		float temp = (float)heatTier.getTemp() / 25.0F;
		float hum = (float)humid.getID();
		if(!w.canBlockSeeSky(pos)){
			temp -= 0.1F;
		}
		if(w.getWorldInfo().getWorldTime()>13000){

			temp -= 0.3F;

		}
		if(w.isRaining()){
			hum += 0.5F;
			temp -= 0.5F;
		}
		return EnvironmentalManager.getConditionFromTempHumid(temp, hum);
	}
}
