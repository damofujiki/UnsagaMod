package mods.hinasch.unsaga.lp;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

public class LPInitializeHelper {

	public static int getLPFromEntity(EntityLivingBase entity){


		if(entity instanceof EntityPlayer){
			return UnsagaMod.configHandler.getDefaultPlayerLifePoint();
		}


		float f1 = entity.getMaxHealth()/5.0F;

		if(f1 < 1.0F){
			f1 = 1.0F;
		}

		if(f1 > 256.0F){
			f1 = 256.0F;
		}
		if(entity instanceof EntityTameable){
			f1 += 3.0F;
		}
		return (int)f1;
	}
}
