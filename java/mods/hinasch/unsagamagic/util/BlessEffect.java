//package mods.hinasch.unsagamagic.util;
//
//import jline.internal.Preconditions;
//import mods.hinasch.lib.iface.INBTWritable;
//import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.util.UnsagaTextFormatting;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.text.translation.I18n;
//
///**
// * もう使わないかも…
// *
// */
//public class BlessEffect implements INBTWritable{
//
//	float amp;
//	int remaining;
//	final CustomBless bless;
//	public BlessEffect(CustomBless bless,float amp, int remaining) {
//
//		super();
//		Preconditions.checkNotNull(bless);
//		this.amp = amp;
//		this.remaining = remaining;
//		this.bless = bless;
////		UnsagaMod.logger.trace("bless", this.toString());
//	}
//
//
//
//	public float getAmp() {
//		return amp;
//	}
//
//	public CustomBless getBless() {
//		return bless;
//	}
//	public int getRemaining() {
//		return remaining;
//	}
//	public boolean isExpired(){
//		return this.remaining<=0;
//	}
//	public void decrRemaining(int step){
//		if(!this.getBless().isInfinity()){
//			this.remaining -= step;
//			UnsagaMod.logger.trace(this.getBless().getName(), this.remaining);
//			if(this.remaining<0){
//				this.remaining =0;
//			}
//		}
//
//
//	}
//	public void processing(){
//		if(!this.getBless().isInfinity()){
//			this.remaining -= 1;
//			UnsagaMod.logger.trace(this.getBless().getName(), this.remaining);
//			if(this.remaining<0){
//				UnsagaMod.logger.trace(this.getBless().getName(), "expired.");
//				this.remaining = 0;
//			}
//		}
//
//	}
//	public void setAmp(float amp) {
//		this.amp = amp;
//	}
//
//	public void setRemaining(int remaining) {
//		this.remaining = remaining;
//	}
//	@Override
//	public String toString(){
//		return this.getBless().getName()+" amp:"+this.getAmp()+" remaining:"+this.getRemaining();
//	}
//
//	public String getTips() {
//		// TODO 自動生成されたメソッド・スタブ
//		return UnsagaTextFormatting.POSITIVE+I18n.translateToLocalFormatted("msg."+this.getBless().getName(), this.getAmp(),this.getRemaining());
//	}
//	@Override
//	public void writeToNBT(NBTTagCompound stream) {
//		stream.setInteger("id", this.getBless().getId());
//		stream.setFloat("amp", amp);
//		stream.setInteger("remain", this.remaining);
//
//	}
//
//	public static RestoreFunc<BlessEffect> RESOTRE_FUNC = new RestoreFunc<BlessEffect>(){
//
//		@Override
//		public BlessEffect apply(NBTTagCompound input) {
//			CustomBless bless = CustomBlessGroup.fromID(input.getInteger("id"));
//			BlessEffect newBless = new BlessEffect(bless,input.getFloat("amp"),input.getInteger("remain"));
//			return newBless;
//		}
//	};
//}
