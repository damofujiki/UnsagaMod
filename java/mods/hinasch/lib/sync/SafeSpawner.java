package mods.hinasch.lib.sync;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SafeSpawner implements Runnable{


	World world;
	Entity entity;


	public SafeSpawner(World w,Entity entity){
		this.world = w;
		this.entity = entity;

	}


	@Override
	public void run() {

		this.world.spawnEntityInWorld(entity);




	}

}
