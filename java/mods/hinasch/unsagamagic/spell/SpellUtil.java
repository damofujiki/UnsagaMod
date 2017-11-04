package mods.hinasch.unsagamagic.spell;

import net.minecraft.util.math.MathHelper;

public class SpellUtil {


	public static int calcCost(int base,float amp){
		if(amp==1.0F){
			return base;
		}
		double f = (float)base * Math.pow(amp, 2);
		int cost =  (int) Math.ceil(f);
		return MathHelper.clamp_int(cost, 1, 65535);
	}
}
