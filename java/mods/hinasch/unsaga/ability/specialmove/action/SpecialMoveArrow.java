package mods.hinasch.unsaga.ability.specialmove.action;

import mods.hinasch.lib.particle.ParticleHelper.MovingType;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;

public class SpecialMoveArrow extends SpecialMoveBase{

	StateArrow.Type type;
	public SpecialMoveArrow() {
		super(InvokeType.BOW);
		this.addAction(new IAction<SpecialMoveInvoker>(){

			@Override
			public EnumActionResult apply(SpecialMoveInvoker context) {
				float f = context.getChargedTime() * 0.01F;
				EntityCustomArrow arrow = new EntityCustomArrow(context.getWorld(),context.getPerformer());
				arrow.setAim(context.getPerformer(), context.getPerformer().rotationPitch, context.getPerformer().rotationYaw, 0.0F, f * 3.0F, 1.0F);
				arrow.setArrowType(getType());
				ItemStack arrowStack = context.getArrowComponent().get().arrowStack.copy();
				arrowStack.stackSize = 1;
				arrow.setArrowStack(arrowStack);
				arrow.pickupStatus = context.getArrowComponent().get().arrowEntity.pickupStatus;
				arrow.setDamage(context.getModifiedStrengthHP());
				context.spawnParticle(MovingType.CONVERGE, XYZPos.createFrom(context.getPerformer()), type.getParticle(), 10, 0.05D);
				if(WorldHelper.isServer(context.getWorld())){
					context.getWorld().spawnEntityInWorld(arrow);

				}
				return EnumActionResult.SUCCESS;
			}}
		);
	}

	public StateArrow.Type getType(){
		return type;
	}

	public SpecialMoveArrow setArrowType(StateArrow.Type type){
		this.type = type;
		return this;
	}



}
