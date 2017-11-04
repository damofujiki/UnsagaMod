package mods.hinasch.unsaga.core.stats;

import java.util.ArrayList;
import java.util.List;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class UnsagaAchievementRegistry {

	public AchievementPage page;
	public List<Achievement> achieves;
	public Achievement openInv;
	public Achievement learnSkillFirst,gainAbilityFirst,masterSkill;
	public Achievement firstChest,breakChest,unlockMagicLock;
	public Achievement openVillager;
	public Achievement restoreLP,restoreOtherLP;

	public Achievement fullArmor;
	public Achievement firstSmith,steel,damascus;
	public Achievement getTablet,firstDecipher,startBlend,finishBlend;

	public Achievement bartering1,bartering2,bartering3;

	public UnsagaAchievementRegistry(){
		achieves = new ArrayList();

	}

	ItemStack iconSword;
	ItemStack iconArmor;
	public void init(){
//		this.iconSword = UnsagaMod.items.getItemStack(ToolCategory.SWORD, UnsagaMod.materials.stone, 1, 0);
//		this.iconArmor = UnsagaMod.items.getItemStack(ToolCategory.ARMOR, UnsagaMod.materials.stone, 1, 0);
//		//first
//
//		this.openInv = (Achievement) new UnsagaAchievement(0,"openInv",2,0,iconSword,null).registerStat().initIndependentStat();
//
//		//2nd
//		//etc
//		this.openVillager = (Achievement) new UnsagaAchievement(1,"openVillager",4,0,
//				new ItemStack(Items.WHEAT),this.openInv).registerStat();
//		//Sparking
//		this.learnSkillFirst  = (Achievement) new UnsagaAchievement(10,"firstSkill",0,2,iconSword, this.openInv).registerStat();
//		this.gainAbilityFirst  = (Achievement) new UnsagaAchievement(11,"firstAbility",0,4,iconArmor, this.learnSkillFirst).registerStat();
//		this.masterSkill  = (Achievement) new UnsagaAchievement(12,"masterSkill",0,6,iconSword, this.gainAbilityFirst).registerStat();
//		//chest
//		this.firstChest  = (Achievement) new UnsagaAchievement(13,"firstChest",2,2,ChestSkill.OPEN.getIcon(),this.openInv).registerStat();
//		this.breakChest  = (Achievement) new UnsagaAchievement(14,"breakChest",2,4,ChestSkill.OPEN.getIcon(), this.firstChest).registerStat();
//		this.unlockMagicLock  = (Achievement) new UnsagaAchievement(15,"unlockMagicLock",2,6,ChestSkill.OPEN.getIcon(), this.breakChest).registerStat();
//
//
//		//smith
//		this.firstSmith = (Achievement) new UnsagaAchievement(20,"firstSmith",4,2,
//				new ItemStack(Blocks.ANVIL,1),this.openVillager).registerStat();
//		this.steel = (Achievement) new UnsagaAchievement(21,"steel",4,4,
//				ItemMiscUnsaga.getProperties().steelIngot.getItemStack(1),this.firstSmith).registerStat();
//		this.damascus = (Achievement) new UnsagaAchievement(22,"damascus",4,6,
//				ItemMiscUnsaga.getProperties().damascusIngot.getItemStack(1),this.steel).registerStat();
		//spell
		this.getTablet = (Achievement) new UnsagaAchievement(30,"getTablet",0,-2,
				new ItemStack(UnsagaMagicItems.instance().tablet),this.openInv).registerStat();
//		this.firstDecipher = (Achievement) new UnsagaAchievement(30,"firstDecipher",0,-2,
//				new ItemStack(UnsagaMagicItems.instance().tablet),this.openInv).registerStat();
//		this.startBlend = (Achievement) new UnsagaAchievement(31,"startBlend",0,-4,
//				new ItemStack(UnsagaMagic.instance().items.tablet),this.firstDecipher).registerStat();
//		this.finishBlend = (Achievement) new UnsagaAchievement(32,"finishBlend",0,-6,
//				new ItemStack(UnsagaMagic.instance().items.tablet),this.startBlend).registerStat();

		//LP
		this.restoreLP = (Achievement) new UnsagaAchievement(40,"restoreLP",4,-2,
				new ItemStack(Items.BED),this.openInv).registerStat();
		this.restoreOtherLP = (Achievement) new UnsagaAchievement(41,"restoreOtherLP",4,-4,
				new ItemStack(Items.BED),this.restoreLP).registerStat();

		//Bartering
		this.bartering1 = (Achievement) new UnsagaAchievement(50,"bartering1",6,0,
				new ItemStack(Items.WHEAT),this.openVillager).registerStat();
		this.bartering2 = (Achievement) new UnsagaAchievement(51,"bartering1",6,-2,
				new ItemStack(Items.WHEAT),this.bartering1).registerStat();
		this.bartering3 = (Achievement) new UnsagaAchievement(52,"bartering1",6,-4,
				new ItemStack(Items.WHEAT),this.bartering2).registerStat();

		page = new AchievementPage("Unsaga Mod",this.getAchievesArray(achieves));
		AchievementPage.registerAchievementPage(page);
	}

	protected Achievement[] getAchievesArray(List<Achievement> list){
		return list.toArray(new Achievement[list.size()]);
	}


	public static class UnsagaAchievement extends Achievement{

		public UnsagaAchievement(int id,String name,
				int x, int y, ItemStack is,
				Achievement parent) {
			super(UnsagaMod.MODID+"."+String.valueOf(id), "unsaga."+name, x, y, is,
					parent);

			UnsagaModCore.instance().achievements.achieves.add(this);
		}

	}
}
