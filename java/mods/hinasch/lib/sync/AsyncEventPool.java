package mods.hinasch.lib.sync;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.misc.LogWrapper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AsyncEventPool extends ILivingUpdateEvent{


	protected LogWrapper logger;
	protected Queue<AsyncUpdateEvent> eventList;
	protected final static int SIZE = 20;
	protected AsyncEventPool(){
		this.eventList = new ArrayBlockingQueue(SIZE);
	}

	public static AsyncEventPool create(){
		return new AsyncEventPool();
	}
	public AsyncEventPool setLogger(LogWrapper par1){
		this.logger = par1;
		return this;
	}
	public void addEvent(AsyncUpdateEvent e){
		if(logger!=null){
			logger.trace("Adding Event[size:"+this.eventList.size()+"]",this.eventList.toArray());
		}



		if(!this.eventList.contains(e)){
			this.eventList.offer(e);
		}



	}


	@SubscribeEvent
	@Override
	public void update(LivingUpdateEvent e) {


			if(!this.eventList.isEmpty()){

				AsyncUpdateEvent ev = this.eventList.poll();
//				UnsagaMod.logger.trace("ev", ev.sender,e.getEntityLiving());
				if(ev!=null && !ev.hasFinished()){
//					if(e.getEntityLiving()==ev.sender){
						ev.loop();
//					}

					this.eventList.offer(ev);
				}

				if(ev!=null && ev.hasFinished()){
//					UnsagaMod.logger.trace(getName(), ev.identifyName+" is expired.");
					ev.onExpire();
				}


			}
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Scanner Pool LivingUpdate";
	}

//	public static class AsyncEventThread extends Thread{
//
//		World world;
//
//		public AsyncEventThread(World world){
//			if(world!=null){
//				UnsagaMod.logger.trace(world, params);
//			}
//		}
//		@Override
//		public void run(){
//
//		}
//	}
}
