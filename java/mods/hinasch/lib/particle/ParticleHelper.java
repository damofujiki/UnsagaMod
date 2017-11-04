package mods.hinasch.lib.particle;

import java.util.Random;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.VecUtil;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.client.particle.CustomFXShockWave;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleHelper {

//	public static void spawnParticle(World world,XYZPos pos,EnumParticleTypes type){
//		world.spawnParticle(type, pos.dx, pos.dy, pos.dz, xSpeed, ySpeed, zSpeed, parameters);
//	}

	private static final CustomFXShockWave.Factory shockWaveFactory = new CustomFXShockWave.Factory();

	public static void spawnShockWave(World w,XYZPos pos){
		if(WorldHelper.isClient(w)){
			Particle entityFX = shockWaveFactory.createParticle(0, w, pos.dx, pos.dy+0.5F, pos.dz, 0, 0, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(entityFX);
		}

	}

	public static class AsyncParticleEvent extends AsyncUpdateEvent{

		final int numberOfWave;
		final int density;
		final int waitThresold;
		final EnumParticleTypes type;
		final Random rand;
		final XYZPos pos;
		final double speed;
		final World w;
		final int[] parameters;
		int currentwave = 0;
		int wait = 0;
		double currentDistance = 0;
		Vec3d baseVec;

		public AsyncParticleEvent(World w,EnumParticleTypes type,XYZPos pos,Random rand,int wavesnum,int density,int wait,double speed,int... parameters){
			super(null,"particle.wave");
			this.numberOfWave = wavesnum;
			this.density = density;
			this.type = type;
			this.pos = pos;
			this.waitThresold = wait;
			this.speed = speed;
			this.rand = rand;
			this.w = w;
			this.currentDistance = speed;
			this.parameters = parameters;
		}
		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return currentwave > this.numberOfWave;
		}

		@Override
		public void loop() {
			this.wait ++;
			if(this.wait >this.waitThresold){
				this.wait = 0;
				this.baseVec = new Vec3d(currentDistance,0,currentDistance);
				this.loopMain();
				this.currentDistance += speed;
				this.currentwave ++;
			}

			UnsagaMod.logger.trace("loop", this.currentwave);
		}

		public void loopMain(){
			for(int j=0;j<density;j++){
				Vec3d vv = VecUtil.getShakeYaw(this.baseVec, rand, 0,360);
				w.spawnParticle(type, pos.dx+vv.xCoord, pos.dy +0.5D, pos.dz+vv.zCoord, vv.scale(0.01D).xCoord, 0.05D, vv.scale(0.01D).zCoord,parameters);
			}

		}
	}
	public static enum MovingType implements IIntSerializable{
		FLOATING(1),
		/** 収束*/
		CONVERGE(2),
		/** 波動*/
		WAVE(3),
		/** 発散*/
		DIVERGE(4),
		/** 噴水*/
		FOUNTAIN(5);

		final int meta;
		private MovingType(int meta){
			this.meta = meta;
		}

		public static MovingType fromMeta(int meta){
			return HSLibs.fromMeta(ParticleHelper.MovingType.values(), meta);
		}


		public void spawnParticle(World w,XYZPos pos,EnumParticleTypes type,Random rand,int density,double speedscale,int... parameters){
			if(this==WAVE){
				if(WorldHelper.isClient(w)){
					if(parameters.length>1){
						HSLib.core().events.scannerEventPool.addEvent(new AsyncParticleEvent(w, type, pos, rand, parameters[1], density, 4, 0.8D,parameters));
					}else{
						HSLib.core().events.scannerEventPool.addEvent(new AsyncParticleEvent(w, type, pos, rand, 6, density, 4, 0.8D,parameters));
					}

				}


				return;
			}
			for(int i=0;i<density;i++){
				double dd = rand.nextGaussian() * speedscale;
				switch(this){
				case CONVERGE:
					double px = pos.dx + (rand.nextFloat()*3.0F - 1.5D);
					double py = pos.dy + (rand.nextFloat()*speedscale);
					double pz = pos.dz + (rand.nextFloat()*3.0F - 1.5D);
					Vec3d vec = (new Vec3d(pos.dx,pos.dy,pos.dz)).subtract(new Vec3d(px,py,pz)).scale(speedscale);
					w.spawnParticle(type, px, py, pz, vec.xCoord,vec.yCoord,vec.zCoord,parameters);
					break;
				case DIVERGE:
					Vec3d vecRot = VecUtil.getShake(new Vec3d(dd,dd,dd), rand, 0, 360, 0, 360);
					w.spawnParticle(type, pos.dx, pos.dy, pos.dz, vecRot.xCoord, rand.nextFloat(), vecRot.zCoord,parameters);
					break;
				case FLOATING:
					w.spawnParticle(type, pos.dx - 0.5F + rand.nextFloat(),pos.dy + 1.0F + rand.nextFloat(), pos.dz - 0.5F + rand.nextFloat(), dd, dd, dd,parameters);
					break;
				case WAVE:
				case FOUNTAIN:
					if(type==EnumParticleTypes.REDSTONE){
						w.spawnParticle(type, pos.dx, pos.dy, pos.dz, 0, 0, 0,parameters);
					}else{
						w.spawnParticle(type, pos.dx, pos.dy, pos.dz, rand.nextFloat()*0.3D, dd, rand.nextFloat()*0.3D,parameters);
					}


					break;
				default:
					break;

				}
			}


		}

		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return meta;
		}
	}
}
