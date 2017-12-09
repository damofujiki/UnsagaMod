package mods.hinasch.unsagamagic.spell.action;

import java.util.List;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.common.specialaction.ActionStatusEffect;
import mods.hinasch.unsaga.status.AdditionalStatus;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;

public class SpellActionStatusEffect extends ActionStatusEffect<SpellCaster>{
	public SpellActionStatusEffect(boolean isDebuff, List<Potion> potions) {
		super(isDebuff, potions);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public SpellActionStatusEffect(boolean isDebuff,Potion... potions){
		super(isDebuff, potions);
	}

	@Override
	public EnumActionResult apply(SpellCaster context) {
		if(context.getTarget().isPresent()){
			XYZPos pos = XYZPos.createFrom(context.getTarget().get());
			if(this.isDebuff()){
				context.playSound(pos, SoundEvents.ENTITY_ENDERMEN_TELEPORT, false);
			}else{
				context.playSound(pos, SoundEvents.ENTITY_PLAYER_LEVELUP, false);
			}
		}

		return super.apply(context);
	}
	@Override
	public int decideEffectLevel(SpellCaster context){
		return MathHelper.clamp_int((int)context.getAmplify() -1, 0, 3);
	}

	@Override
	public int decideEffectDuration(SpellCaster context){
		int sec = ItemUtil.getPotionTime((int)(context.getActionProperty().getDuration() * context.getAmplify()));
		double intel = context.getPerformer().getEntityAttribute(AdditionalStatus.INTELLIGENCE).getAttributeValue();

		return (int)((double)sec * intel);
	}

//	public static SpellActionBase of(boolean isDebuff,Potion... potion){
//		SpellActionBase spell = new SpellActionBase();
//		spell.addAction(new ActionStatusEffect.SpellActionStatusEffect(isDebuff,potion));
//		return spell;
//	}
}
