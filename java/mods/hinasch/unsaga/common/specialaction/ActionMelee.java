package mods.hinasch.unsaga.common.specialaction;

import java.util.EnumSet;
import java.util.function.BiConsumer;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumActionResult;

public class ActionMelee implements IAction<SpecialMoveInvoker>,ISimpleMelee<SpecialMoveInvoker>{

	BiConsumer<SpecialMoveInvoker,EntityLivingBase> consumer;
	EnumSet<General> attributes;
	EnumSet<Sub> subAttributes = EnumSet.noneOf(Sub.class);

	public ActionMelee(General... attributes) {
		this.attributes = EnumSet.copyOf(Lists.newArrayList(attributes));
	}

	public ActionMelee setSubAttributes(EnumSet<Sub> sub){
		this.subAttributes = sub;
		return this;
	}
	public ActionMelee setAdditionalBehavior(BiConsumer<SpecialMoveInvoker,EntityLivingBase> consumer){
		this.consumer = consumer;
		return this;
	}

	@Override
	public EnumActionResult apply(SpecialMoveInvoker t) {
		// TODO 自動生成されたメソッド・スタブ
		return this.performSimpleAttack(t);
	}

	@Override
	public EnumSet<General> getAttributes() {
		// TODO 自動生成されたメソッド・スタブ
		return attributes;
	}

	@Override
	public EnumSet<Sub> getSubAttributes() {
		// TODO 自動生成されたメソッド・スタブ
		return subAttributes;
	}



	@Override
	public BiConsumer<SpecialMoveInvoker, EntityLivingBase> getAdditionalBehavior() {
		// TODO 自動生成されたメソッド・スタブ
		return consumer;
	}

	@Override
	public float getDamage(SpecialMoveInvoker context,EntityLivingBase target, float base) {
		// TODO 自動生成されたメソッド・スタブ
		return base;
	}

	@Override
	public float getReach(SpecialMoveInvoker context) {
		// TODO 自動生成されたメソッド・スタブ
		return context.getReach();
	}

}
