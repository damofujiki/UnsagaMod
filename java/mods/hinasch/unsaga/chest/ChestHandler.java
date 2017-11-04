package mods.hinasch.unsaga.chest;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.skillpanel.SkillPanel;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;

public class ChestHandler {

	public static BaseDifficulty DEFUSE = new BaseDifficulty(80,-0.89F,0.02F);
	public static BaseDifficulty UNLOCK = new BaseDifficulty(80,-0.89F,0.02F);
	public static BaseDifficulty DIVINE = new BaseDifficulty(80,-0.89F,0.02F);
	public static BaseDifficulty PENETRATE = new BaseDifficulty(90,-0.69F,0.02F);
	public static float getInteractionSuccessProb(BaseDifficulty base,IChestCapability chest,int panelLevel){
		int a = base.base+(int)(base.slope*(float)chest.getLevel());
		a = MathHelper.clamp_int(a, 10, 100);
		a += (panelLevel * 15);
		return a * 0.01F;
	}

	public static float getBaddestInteractionProb(BaseDifficulty base,IChestCapability chest,int panelLevel){
		float f = base.badslope * chest.getLevel();
		f -= (0.08F*panelLevel);
		f = MathHelper.clamp_float(f, 0, 100);
//		UnsagaMod.logger.trace("adgjosadgads", f);
		return f;
	}
	public static List<TrapChest> getInitializedTraps(IChestCapability inst,Random rand){
		List<TrapChest> traps = Lists.newArrayList();
		if(rand.nextFloat()<0.3F+0.0055F*inst.getLevel()){
			traps.add(TrapChestRegistry.NEEDLE);
		}
		if(inst.getLevel()>15){
			if(rand.nextFloat()<0.3F+0.0055F*inst.getLevel()){
				traps.add(TrapChestRegistry.POISON);
			}
		}
		if(inst.getLevel()>25){
			if(rand.nextFloat()<0.3F+0.0035F*inst.getLevel()){
				traps.add(TrapChestRegistry.EXPLODE);
			}
			if(rand.nextFloat()<0.3F+0.0040F*inst.getLevel()){
				traps.add(TrapChestRegistry.SLIME);
			}
		}
		return traps;
	}

	public static BaseDifficulty getBaseDifficulty(SkillPanel skill){
		if(skill==SkillPanelRegistry.instance().defuse){
			return ChestHandler.DEFUSE;
		}
		if(skill==SkillPanelRegistry.instance().locksmith){
			return ChestHandler.UNLOCK;
		}
		if(skill==SkillPanelRegistry.instance().fortuneTeller){
			return ChestHandler.DIVINE;
		}
		if(skill==SkillPanelRegistry.instance().sharpEye){
			return ChestHandler.PENETRATE;
		}
		return ChestHandler.PENETRATE;
	}

	public static EnumActionResult tryInteraction(SkillPanel skill,Random rand,IChestCapability chest,int skillLevel){
		BaseDifficulty dif = getBaseDifficulty(skill);
		if(ChestHandler.getInteractionSuccessProb(dif,chest,skillLevel)>rand.nextFloat()){
			return EnumActionResult.SUCCESS;
		}
		if(ChestHandler.getBaddestInteractionProb(dif, chest,skillLevel)>rand.nextFloat()){
			return EnumActionResult.FAIL;
		}
		return EnumActionResult.PASS;

	}
	public static class BaseDifficulty{
		final int base;
		final float slope;
		final float badslope;
		public BaseDifficulty(int base,float slope,float badslope){
			this.base = base;
			this.slope = slope;
			this.badslope = badslope;
		}
	}
}
