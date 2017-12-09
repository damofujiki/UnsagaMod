package mods.hinasch.lib.sync;

import net.minecraft.entity.EntityLivingBase;

public abstract class AsyncUpdateEvent {


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifyName == null) ? 0 : identifyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AsyncUpdateEvent other = (AsyncUpdateEvent) obj;
		if (identifyName == null) {
			if (other.identifyName != null)
				return false;
		} else if (!identifyName.equals(other.identifyName))
			return false;
		return true;
	}
	String identifyName;

	final protected EntityLivingBase sender;

	public AsyncUpdateEvent(EntityLivingBase sender,String identifyName){
		this.identifyName = identifyName;
		this.sender = sender;
	}

	public EntityLivingBase getSender(){
		return this.sender;
	}
	public void onExpire(){

	}
	/**
	 * ここでtrue返るとイベントが終了する。
	 * @return
	 */
	public abstract boolean hasFinished();
	/**
	 * メインループはここに書く
	 */
	public abstract void loop();
}
