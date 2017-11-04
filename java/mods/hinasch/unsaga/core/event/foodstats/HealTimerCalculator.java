package mods.hinasch.unsaga.core.event.foodstats;

import java.util.Arrays;
import java.util.List;

import mods.hinasch.lib.world.EnvironmentalManager;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import mods.hinasch.unsaga.status.AdditionalStatus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class HealTimerCalculator {



	public static int calcHealTimer(EntityLivingBase playerIn){

		final XYZPos pos = XYZPos.createFrom(playerIn);
		final World world = playerIn.worldObj;
		Biome biome =world.getBiome(pos);
		List<BiomeDictionary.Type> types  = Arrays.asList(BiomeDictionary.getTypesForBiome(biome));
		int base = (int) playerIn.getEntityAttribute(AdditionalStatus.NATURAL_HEAL_SPEED).getAttributeValue();

		if(playerIn instanceof EntityPlayer){
			if(!SkillPanelAPI.hasPanel((EntityPlayer) playerIn, SkillPanelRegistry.instance().adaptability)){
				if(EnvironmentalManager.getCondition(world,pos,playerIn.worldObj.getBiome(pos),playerIn).isHarsh){
					base += 50;
				}
			}
		}

		base = MathHelper.clamp_int(base, 10, 200);
		return base;
	}
}
