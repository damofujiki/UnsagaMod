package mods.hinasch.unsagamagic.util;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.element.newele.ElementAssociationLibrary;
import mods.hinasch.unsaga.element.newele.ElementTable;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import mods.hinasch.unsagamagic.spell.Spell;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;


public class HelperDecipherProcess {


	public static final int DECIPHERING_PROCESS_MAX = 50;


//	@Deprecated
//	public void progress(World world,EntityPlayer ep,ItemStack tablet){
//		SpellMixTable table = UnsagaMod.getWorldElementWatcher().getFiguredTable(world,ep);
//
//		Spell spell = TabletHelper.getSpell(tablet);
//		int difficultySpell = spell.difficultyDecipher;
//		FiveElements.Enums elementMagic = spell.element;
//		int elementpoint = table.getInt(elementMagic);
//		int progressDecipher = (elementpoint*3) / HSLibs.exceptZero(difficultySpell/2);
//		if(progressDecipher<2){
//			progressDecipher=2;
//		}
//		scala.Tuple3<Integer,Integer,Boolean> tuple = new scala.Tuple3<Integer, Integer, Boolean>(elementpoint, difficultySpell, true);
//		progressDecipher *= 2;
//		if(SkillPanels.hasPanel(world, ep, Unsaga.skillPanels.knowledgeAncient)){
//			int level = SkillPanels.getHighestLevelPanel(world, ep, Unsaga.skillPanels.knowledgeAncient)+1;
//			progressDecipher += (level*3);
//		}
//		if(WorldHelper.isServer(world)){
//			String mes = Translation.localize("msg.spell.deciphering.progress");
//			String formatted = String.format(mes, progressDecipher);
//			ChatUtil.addMessageNoLocalized(ep, formatted);
//		}
//
//		Unsaga.debug("解読度:"+progressDecipher+":"+tablet);
//		TabletHelper.progressDeciphering(ep, tablet, progressDecipher);
//
//	}

	public static int getProgressPoint(final World world,final EntityPlayer ep,Spell spell){
		ElementTable table = ElementAssociationLibrary.calcAllElements(world, ep);
		int difficultySpell = spell.getDifficulty();
		FiveElements.Type elementMagic = spell.getElement();
		float elementpoint = table.get(elementMagic) + 1.0F;
		int progressDecipher = (int)(5.0F* elementpoint/  (float)difficultySpell * 100.0F) ;
		if(SkillPanelAPI.hasPanel(ep, SkillPanelRegistry.instance().arcaneTongue)){
			int skill = 3 * SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().arcaneTongue).getAsInt();
			progressDecipher += skill;
		}

//		ChatHandler.sendChatToPlayer(ep, "deciphering.... "+);
		UnsagaMod.logger.trace("decipheringhelper",progressDecipher,elementpoint,table);
		return progressDecipher;
	}



	private static int getProgressDecipher(World world,EntityPlayer ep,int elementpoint,int difficultySpell){
		int base = (elementpoint*3) / HSLibs.exceptZero(difficultySpell/2);
		if(base<2){
			base=2;
		}
		base *= 2;
		if(SkillPanelAPI.hasPanel(ep, SkillPanelRegistry.instance().arcaneTongue)){
			int level = SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().arcaneTongue).getAsInt();
			base += (level*3);
		}
		return MathHelper.clamp_int(base, 4, DECIPHERING_PROCESS_MAX);
	}
	public static void progressOnTakingXP(PlayerPickupXpEvent e){
//		ExtendedPlayerData data = ExtendedPlayerData.getData(e.entityPlayer);
//		if(data.getTablet()!=null){
//			progress(e.entityPlayer.worldObj,e.entityPlayer,data.getTablet());
//		}


//	}
	}
}
