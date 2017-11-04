package mods.hinasch.unsaga.villager.bartering;

import com.mojang.realmsclient.util.Pair;

import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class BarteringUtil {

	public static int getDiscountPrice(int basePrice,PriceDownUpPair discounts){
		int price = basePrice;
		price -= (int)((float)basePrice*0.05F*(float)discounts.priceDown());
		price += (int)((float)basePrice*0.05F*(float)discounts.priceUp());
		price = MathHelper.clamp_int(price, 1, Integer.MAX_VALUE);
		return price;
	}


	public static int calcNextTransactionThreshold(int distLV){
		return (distLV+1) * (distLV>10 ? 2000 : distLV>5 ? 1000 : 500);
	}
	public static PriceDownUpPair applyDiscount(EntityPlayer ep){

		int priceDown = SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().monger).getAsInt();
		if(SkillPanelAPI.hasPanel(ep, SkillPanelRegistry.instance().fashionable)){
			priceDown += 1;
		}
		int priceUp = 0;
		return new PriceDownUpPair(priceDown,priceUp);
	}

	public static PriceDownUpPair applyGratuity(EntityPlayer ep){

		int priceUp = SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().maharaja).getAsInt();
		if(SkillPanelAPI.hasPanel(ep, SkillPanelRegistry.instance().fashionable)){
			priceUp += 1;
		}
		int priceDown = 0;
		return new PriceDownUpPair(priceDown,priceUp);
	}

	public static class PriceDownUpPair extends Pair<Integer,Integer>{

		public PriceDownUpPair(Integer priceDown, Integer priceUp) {
			super(priceUp, priceDown);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public int priceUp(){
			return this.first();
		}

		public int priceDown(){
			return this.second();
		}
	}
}
