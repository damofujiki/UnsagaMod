package mods.hinasch.unsaga.ability.specialmove.action;

import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

public class RangedBoundingBoxMakerGroup {
	public static class RangeSurroundings implements Function<SpecialMoveInvoker,List<AxisAlignedBB>>{

		final double horizontal;
		final double vertical;
		public RangeSurroundings(double horizontal,double vertical){
			this.horizontal = horizontal;
			this.vertical = vertical;
		}
		@Override
		public List<AxisAlignedBB> apply(SpecialMoveInvoker t) {
			List<AxisAlignedBB> list = Lists.newArrayList();
			list.add(t.getPerformer().getEntityBoundingBox().expand(horizontal,vertical,horizontal));
			return list;
		}

	}

	public static class RangeSwing implements Function<SpecialMoveInvoker,List<AxisAlignedBB>>{

		/** 多くするほど当たり判定が細かい*/
		final int resolution;
		public RangeSwing(int resolution){
			this.resolution = resolution;
		}
		@Override
		public List<AxisAlignedBB> apply(SpecialMoveInvoker context) {
			List<AxisAlignedBB> list = Lists.newArrayList();
			EntityLivingBase el = context.getPerformer();
			int rotatePar = 180 / this.resolution;
			for(int i=0;i<(int)context.getReach();i++){
				Vec3d v1 = el.getLookVec().normalize().scale(i+1);

				for(int r=0;r<resolution+i;r++){
					Vec3d v2 = v1.rotateYaw((float) Math.toRadians(90-(rotatePar*r)));
					AxisAlignedBB bb = el.getEntityBoundingBox().expand(1.5F, 0, 1.5F).offset(v2.xCoord,v2.yCoord,v2.zCoord);
					list.add(bb);
				}
			}


			return list;
		}

	}
}
