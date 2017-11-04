package mods.hinasch.lib.client;

import java.util.concurrent.ArrayBlockingQueue;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class SoundQueueManager {


	private ArrayBlockingQueue<Event> queue = new ArrayBlockingQueue(15);

	private static SoundQueueManager INSTANCE;

	public static SoundQueueManager instance(){
		if(INSTANCE==null){
			INSTANCE = new SoundQueueManager();
		}
		return INSTANCE;
	}

	public void addQueue(Event ev){

		this.queue.offer(ev);
	}

	public static void registerEvent(){
		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				if(e.getEntityLiving() instanceof EntityPlayer){
					if(e.getEntityLiving().getEntityWorld().isRemote){
						Event ev = SoundQueueManager.instance().queue.poll();
						if(ev!=null){
							ev.run();
						}
					}
				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "synchronized soundplay";
			}});
	}



	public static class Event implements Runnable{
		SoundEvent event;
		float volume;
		float pitch;
		SoundCategory category;
		XYZPos pos;
		World world;
		public Event(SoundEvent event, float volume, float pitch, SoundCategory category, XYZPos pos, World world,
				boolean distanceDelay) {
			super();
			this.event = event;
			this.volume = volume;
			this.pitch = pitch;
			this.category = category;
			this.pos = pos;
			this.world = world;
			this.distanceDelay = distanceDelay;
		}
		boolean distanceDelay;
		@Override
		public void run() {
			world.playSound(pos.dx, pos.dy, pos.dz, event, category, volume, pitch, distanceDelay);

		}

	}
}
