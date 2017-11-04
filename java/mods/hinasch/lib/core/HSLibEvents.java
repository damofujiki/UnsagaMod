package mods.hinasch.lib.core;

import mods.hinasch.lib.core.event.EventBreakSpeed;
import mods.hinasch.lib.core.event.EventLivingHurt;
import mods.hinasch.lib.core.event.EventLivingUpdate;
import mods.hinasch.lib.entity.EventLivingDrops;
import mods.hinasch.lib.sync.AsyncEventPool;

public class HSLibEvents {
	public static EventLivingDrops drop = new EventLivingDrops();
	public static EventBreakSpeed breakSpeed = EventBreakSpeed.instance();
	public static EventLivingUpdate livingUpdate = new EventLivingUpdate();
	public static EventLivingHurt livingHurt = new EventLivingHurt();
	public static AsyncEventPool scannerEventPool = AsyncEventPool.create().setLogger(HSLib.logger);


	public static EventLivingDrops getDropEvent(){
		return drop;
	}
}
