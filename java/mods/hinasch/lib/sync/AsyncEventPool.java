package mods.hinasch.lib.sync;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.misc.LogWrapper;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

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



		this.eventList.offer(e);


	}


	@Override
	public void update(LivingUpdateEvent e) {


			if(!this.eventList.isEmpty()){

				AsyncUpdateEvent ev = this.eventList.poll();
				if(ev!=null && !ev.hasFinished()){
					ev.loop();
					this.eventList.offer(ev);
				}

				if(ev!=null && ev.hasFinished()){
					ev.onExpire();
				}


			}
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Scanner Pool LivingUpdate";
	}
}
