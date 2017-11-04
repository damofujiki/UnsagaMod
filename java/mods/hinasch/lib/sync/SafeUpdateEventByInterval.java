package mods.hinasch.lib.sync;

import net.minecraft.entity.EntityLivingBase;

public abstract class SafeUpdateEventByInterval extends AsyncUpdateEvent{


	protected boolean forceFirstRun = false;
	public SafeUpdateEventByInterval(EntityLivingBase sender,String identifyName) {
		super(sender, identifyName);
		this.interval = this.forceFirstRun ? this.getIntervalThresold() : 0;
	}
	int interval = 0;


	@Override
	public void loop() {
		interval ++;
		if(interval>this.getIntervalThresold()){
			interval = 0;
			this.loopByInterval();
		}
	}

	public abstract int getIntervalThresold();
	public abstract void loopByInterval();

}
