package mods.hinasch.unsaga.core.event.foodstats;

import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//ロード時に満腹度が回復してしまうエラーあり（フードスタッツにフックを用意して欲しい…）
public class FoodStatsUnsaga extends FoodStats{
//	/** The player's food level. */
//	private int foodLevel = 20;
//	/** The player's food saturation. */
//	private float foodSaturationLevel = 5.0F;
//	/** The player's food exhaustion. */
//	private float foodExhaustionLevel;
//	/** The player's food timer value. */
	private int foodTimer;
//	private int prevFoodLevel = 20;
	private final int PERHEAL_BASE = 70;

    private int prevFoodLevel = 20;

	private int currentPerHeal;

	private FoodStats parent;


	private FoodStats old;
	public FoodStatsUnsaga(FoodStats oldIn){
		UnsagaMod.logger.trace("Replace FoodStats Succeeded!", "");
		old = oldIn;
	}
	/**
	 * Args: int foodLevel, float foodSaturationModifier
	 */
	public void addStats(int par1, float par2)
	{
		old.addStats(par1, par2);
	}



	@Override
    public void addStats(ItemFood p_151686_1_, ItemStack p_151686_2_)
    {
		old.addStats(p_151686_1_, p_151686_2_);
	}

	/**
	 * Handles the food game logic.
	 */
	public void onUpdate(final EntityPlayer par1EntityPlayer)
	{
		old.onUpdate(par1EntityPlayer);
		EnumDifficulty enumdifficulty = par1EntityPlayer.worldObj.getDifficulty();


//		if (par1EntityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration") && this.foodLevel >= 18 && par1EntityPlayer.shouldHeal())
		if (old.getFoodLevel() >= 18 && par1EntityPlayer.shouldHeal())
		{
			++this.foodTimer;

			EntityLivingBase living = par1EntityPlayer;

			int perHeal = HealTimerCalculator.calcHealTimer(par1EntityPlayer);


			this.currentPerHeal = perHeal;

			UnsagaMod.logger.trace("heal", this.foodTimer,perHeal);
			if (this.foodTimer >= perHeal)
			{

				par1EntityPlayer.heal(1.0F);
				this.addExhaustion(3.0F);
				this.foodTimer = 0;
			}
		}

	}

	public int getCurrentModifierHealingAmount(){
		return this.currentPerHeal;
	}
	/**
	 * Reads food stats from an NBT object.
	 */
	public void readNBT(NBTTagCompound par1NBTTagCompound)
	{
		old.readNBT(par1NBTTagCompound);
		if(par1NBTTagCompound.hasKey("foodTickTimerUnsaga")){
			this.foodTimer = par1NBTTagCompound.getInteger("foodTickTimerUnsaga");
		}

	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound par1NBTTagCompound)
	{
		old.writeNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("foodTickTimerUnsaga", this.foodTimer);

	}

	/**
	 * Get the player's food level.
	 */
	public int getFoodLevel()
	{
		return old.getFoodLevel();
	}

//	@SideOnly(Side.CLIENT)
//	public int getPrevFoodLevel()
//	{
//		return old.getPrevFoodLevel();
//	}

	/**
	 * If foodLevel is not max.
	 */
	public boolean needFood()
	{
		return old.needFood();
	}

	/**
	 * adds input to foodExhaustionLevel to a max of 40
	 */
	public void addExhaustion(float par1)
	{
		old.addExhaustion(par1);
	}

	/**
	 * Get the player's food saturation level.
	 */
	public float getSaturationLevel()
	{
		return old.getSaturationLevel();
	}

	@SideOnly(Side.CLIENT)
	public void setFoodLevel(int par1)
	{
		old.setFoodLevel(par1);
	}

	@SideOnly(Side.CLIENT)
	public void setFoodSaturationLevel(float par1)
	{
		old.setFoodSaturationLevel(par1);
	}



}
