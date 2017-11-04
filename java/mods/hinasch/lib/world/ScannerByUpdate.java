package mods.hinasch.lib.world;

import mods.hinasch.lib.sync.AsyncUpdateEvent;
import net.minecraft.entity.EntityLivingBase;

public class ScannerByUpdate extends AsyncUpdateEvent{

	protected ScanHelper scanner;
	public boolean expired;

	public ScannerByUpdate(EntityLivingBase sender,ScanHelper scanner){
		super(sender,"scannerAsync");
		this.scanner = scanner;
		this.expired = false;
	}
	@Override
	public boolean hasFinished() {
		// TODO 自動生成されたメソッド・スタブ
		return !scanner.hasNext();
	}

	@Override
	public void loop() {

		this.hook();
		scanner.next();
		if(!scanner.hasNext() && !this.expired){
			this.expireEvent();
			this.expired = true;
		}
	}

	public void expireEvent(){

	}
	public void hook(){

	}
}
