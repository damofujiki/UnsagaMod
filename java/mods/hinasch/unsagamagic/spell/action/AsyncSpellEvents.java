package mods.hinasch.unsagamagic.spell.action;

import java.util.Collections;
import java.util.List;

import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.sync.AsyncUpdateEvent;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityIceNeedle;
import mods.hinasch.unsaga.core.entity.projectile.EntitySolutionLiquid;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AsyncSpellEvents {
	public static abstract class AsyncSpellEvent extends AsyncUpdateEvent{

		final World world;
		final EntityLivingBase caster;
		final int expireTime;
		int time = 0;
		int count = 0;
		int threshold = 1;


		public AsyncSpellEvent(World world,EntityLivingBase caster,int expire){
			super(caster,"spellasync");
			this.world = world;
			this.caster = caster;
			this.expireTime = expire;
//			this.setThreshold(1);
		}

		public AsyncUpdateEvent setThreshold(int th){
			this.threshold = th;
			return this;
		}
		@Override
		public boolean hasFinished() {
			// TODO 自動生成されたメソッド・スタブ
			return time>this.expireTime;
		}

		@Override
		public void loop() {


//			if(count==0 || count%(threshold)==0){
//				this.count = 0;
				this.loopLoose();
				UnsagaMod.logger.trace("tick", time);
				time ++;
//			}
			count ++;
		}

		public abstract void loopLoose();
	}
	public static class CrimsonFlare extends AsyncSpellEvent{

		final EntityLivingBase target;
		final double damage;
		int damageCount = 0;
		public CrimsonFlare(World world, EntityLivingBase caster, EntityLivingBase target,double damage,int expire) {
			super(world, caster, expire);
			this.target = target;
			this.damage = damage;
			this.setThreshold(3);
		}

		@Override
		public void loopLoose() {
			if(damageCount>60){
				this.time = 60000;
				XYZPos pos = XYZPos.createFrom(target);
				if(WorldHelper.isServer(world)){
					this.world.createExplosion(caster, pos.dx, pos.dy, pos.dz, 4, true);
				}


			}else{
				if(damageCount>30){
					ParticleHelper.MovingType.CONVERGE.spawnParticle(world, XYZPos.createFrom(target), EnumParticleTypes.FLAME, this.world.rand, 10, 0.3D);
				}else{
					if(this.world.rand.nextInt(2)==0){
						ParticleHelper.MovingType.CONVERGE.spawnParticle(world, XYZPos.createFrom(target), EnumParticleTypes.FLAME, this.world.rand, 10, 0.3D);

					}
				}

				if(damageCount % 10 ==0){
					SoundAndSFX.playSound(world, XYZPos.createFrom(target), SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.HOSTILE,1.0F,1.0F,false);
				}
				this.target.attackEntityFrom(DamageSourceUnsaga.create(this.caster, SpellRegistry.instance().crimsonFlare.getEffectStrength().lp(), General.MAGIC), (float) damage);
				this.damageCount ++;
			}

		}

	}
	public static class IceNeedle extends AsyncSpellEvent{


		final XYZPos position;
		final float damage;

		public IceNeedle(World world, EntityLivingBase caster, XYZPos pos,int expire,float dm) {
			super(world, caster, expire);
			this.damage = dm;
			this.position = pos;
			// TODO 自動生成されたコンストラクター・スタブ
		}


		@Override
		public void loop() {
			super.loop();



		}


		@Override
		public void loopLoose() {

			if(this.time % 4 ==0){
				SoundAndSFX.playSound(world, XYZPos.createFrom(caster), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
			}
			if(this.time % 2 ==0){

				BlockPos pos = this.caster.getPosition();

				EntityIceNeedle needle = new EntityIceNeedle(this.world,this.caster);

				List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, HSLibs.getBounding(position, 10, 10),RangedHelper.getTargetSelectorFromEntityType(caster));
				if(list.isEmpty()){
					double x = this.position.dx + world.rand.nextInt(30)-15;
					double z = this.position.dz + world.rand.nextInt(30)-15;
					double y = this.position.dy + 10;
					needle.setPosition(x, y, z);
				}else{
					Collections.shuffle(list);
					EntityLivingBase target = list.get(0);
					double x = target.posX - 0.5D + world.rand.nextDouble();
					double y = target.posY+10;
					double z = target.posZ- 0.5D + world.rand.nextDouble();
					needle.setPosition(x, y, z);
				}



				needle.setDamage(this.damage);
				WorldHelper.safeSpawn(world, needle);
			}

		}

	}
	public static class StoneShower extends AsyncSpellEvent{


		final float damage;

		public StoneShower(World world, EntityLivingBase caster, int expire,float dm) {
			super(world, caster, expire);
			this.damage = dm;
			// TODO 自動生成されたコンストラクター・スタブ
		}


		@Override
		public void loop() {
			super.loop();



		}


		@Override
		public void loopLoose() {
			BlockPos pos = this.caster.getPosition();

			EntityBoulder boulder = new EntityBoulder(this.world,this.caster);
			boulder.setHeadingFromThrower(this.caster ,this.caster.rotationPitch, this.caster.rotationYaw, 0.0F, 2.0F, 1.0F);
			Vec3d lookvec = this.caster.getLookVec();
			Vec3d horivec = lookvec.scale(this.world.rand.nextFloat() * 6.0F).rotateYaw((float) Math.toRadians(this.world.rand.nextInt(2)==0 ? 90 : -90));
			Vec3d vervec = lookvec.scale(this.world.rand.nextFloat() * 3.0F).rotatePitch((float) Math.toRadians(-90));
			boulder.posX += horivec.xCoord + vervec.xCoord + lookvec.xCoord;
			boulder.posY += horivec.yCoord + vervec.yCoord+ lookvec.yCoord + 1.0F;
			boulder.posZ += horivec.zCoord + vervec.zCoord+ lookvec.zCoord;
			boulder.setDamage(this.damage);

			if(this.time % 10 ==0){
				SoundAndSFX.playSound(world, XYZPos.createFrom(caster), SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
			}
			WorldHelper.safeSpawn(world, boulder);
		}

	}

	public static class ThunderCrap extends AsyncSpellEvent{

		final SpellCaster context;
		public ThunderCrap(World world, EntityLivingBase caster, int expire,SpellCaster context) {
			super(world, caster, expire);
			this.context = context;

		}

		@Override
		public void loopLoose() {

			if(this.time % 4 ==0){
				EntitySolutionLiquid liquid = new EntitySolutionLiquid(world, caster);
				liquid.setThunderCrap();
				liquid.setDamage(this.context.getModifiedStrength().hp(), this.context.getModifiedStrength().lp());
				context.playSound(XYZPos.createFrom(caster), SoundEvents.ENTITY_EGG_THROW, false);
//				ChatHandler.sendChatToPlayer((EntityPlayer) sender, String.valueOf(caster.rotationPitch));
				liquid.setHeadingFromThrower(caster, caster.cameraPitch, caster.rotationYaw, 0, 1.0F, 0.9F);
				WorldHelper.safeSpawn(world, liquid);
			}

		}

	}
	public static class FireStorm extends AsyncSpellEvent{

		final BlockPos basePos;
		final AxisAlignedBB boundingBox;
		final float damage;
		final int range;


		int time = 0;
		public FireStorm(World world,BlockPos pos,EntityLivingBase caster,float damage,int range,int endTime){
			super(world,caster,endTime);
			this.basePos = pos;
			this.damage = damage;
			this.range = range;
			this.boundingBox = HSLibs.getBounding(new XYZPos(pos), 8.0D, 8.0D);
		}

		@Override
		public void loop() {


			super.loop();
		}

		@Override
		public void loopLoose() {


			if(this.world.rand.nextInt(2)==0){
				BlockPos pos = WorldHelper.getRandomPos(this.world.rand, basePos, range);
				ParticleHelper.MovingType.DIVERGE.spawnParticle(world, new XYZPos(pos), EnumParticleTypes.FLAME, this.world.rand, 10, 0.1D);
			}
//			if(this.time % 3 == 0){
//			UnsagaMod.logger.trace(this.getClass().getName(), "called");
			RangedHelper.create(world, this.caster, this.boundingBox)
			.setSelectorFromOrigin().setConsumer((self,in)->{
				in.attackEntityFrom(DamageSourceUnsaga.create(caster, SpellRegistry.instance().fireStorm.getEffectStrength().lp(), General.MAGIC)
						.setSubTypes(Sub.FIRE), damage);
				in.setFire(10);
			}).invoke();

		}

	}


}
