package mods.hinasch.lib.sync;

import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SafeExplode implements Runnable{

	final World world;
	final XYZPos pos;
	final Entity entity;
	final float str;
	final boolean isSmoking;

	public SafeExplode(World world,Entity entity,XYZPos pos,float str,boolean isSmoking){
		this.world = world;
		this.pos = pos;
		this.entity = entity;
		this.str = str;
		this.isSmoking = isSmoking;
	}
	@Override
	public void run() {

		world.createExplosion(entity, pos.dx, pos.dy, pos.dz, str, isSmoking);
	}

}
