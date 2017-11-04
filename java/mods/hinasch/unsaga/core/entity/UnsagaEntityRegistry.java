package mods.hinasch.unsaga.core.entity;

import mods.hinasch.lib.registry.PropertyElementWithID;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.entity.mob.EntityTreasureSlime;
import mods.hinasch.unsaga.core.entity.passive.EntityShadow;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.entity.projectile.EntityBeam;
import mods.hinasch.unsaga.core.entity.projectile.EntityBlaster;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityBubbleBlow;
import mods.hinasch.unsaga.core.entity.projectile.EntityBullet;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
import mods.hinasch.unsaga.core.entity.projectile.EntitySolutionLiquid;
import mods.hinasch.unsaga.core.entity.projectile.EntityThrowingKnife;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class UnsagaEntityRegistry extends PropertyRegistry<UnsagaEntityRegistry.Property>{

	private static UnsagaEntityRegistry INSTANCE;

	public static UnsagaEntityRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new UnsagaEntityRegistry();

		}
		return INSTANCE;
	}
	public Property flyingAxe = new Property(1,"flyingAxe",EntityFlyingAxe.class);
	public Property throwingKnife = new Property(2,"throwingKnife",EntityThrowingKnife.class);
	public Property boulder = new Property(3,"boulder",EntityBoulder.class);
	public Property bubbleBlow = new Property(4,"bubbleBlow",EntityBubbleBlow.class);
	public Property shadow = new Property(5,"shadow",EntityShadow.class);
	public Property arrow = new Property(6,"arrow",EntityCustomArrow.class);
	public Property chest = new Property(7,"chest",EntityUnsagaChestNew.class);
	public Property treasureSlime = new Property(8,"treasureSlime",EntityTreasureSlime.class);
	public Property liquidBall = new Property(9,"liquidBall",EntitySolutionLiquid.class);
	public Property ruffleTree = new Property(10,"ruffleTree",EntityRuffleTree.class);
	public Property bullet = new Property(11,"bullet",EntityBullet.class);
	public Property beam = new Property(12,"beam",EntityBeam.class);
	public Property blaster = new Property(13,"blaster",EntityBlaster.class);
	public class Property extends PropertyElementWithID{

		public Class getEntityClass() {
			return entityClass;
		}

		public Property setEntityClass(Class entityClass) {
			this.entityClass = entityClass;
			return this;
		}

		public boolean isSendsVelocityUpdates() {
			return sendsVelocityUpdates;
		}

		public Property setSendsVelocityUpdates(boolean sendsVelocityUpdates) {
			this.sendsVelocityUpdates = sendsVelocityUpdates;
			return this;
		}

		public int getUpdateFreq() {
			return updateFreq;
		}

		public Property setUpdateFreq(int updateFreq) {
			this.updateFreq = updateFreq;
			return this;
		}

		public int getTrackingRange() {
			return trackingRange;
		}

		public Property setTrackingRange(int trackingRange) {
			this.trackingRange = trackingRange;
			return this;
		}

		public Class entityClass;
		public boolean sendsVelocityUpdates = true;
		public int updateFreq = 5;
		public int trackingRange = 128;


		public Property(int id,String name,Class<? extends Entity> clazz){
			super(new ResourceLocation(name),name,id);
			this.entityClass = clazz;
			//			UnsagaEntityRegistry.map.putObject(new ResourceLocation(name), this);
		}



		@Override
		public Class getParentClass() {
			// TODO 自動生成されたメソッド・スタブ
			return this.getClass();
		}


	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		EntityRegistry.registerEgg(EntityUnsagaChestNew.class, 0xff33ff, 0);
		EntityRegistry.registerEgg(EntityTreasureSlime.class, 0xff33ff, 0);
		EntityRegistry.registerEgg(EntityRuffleTree.class, 0xff33ff, 0);
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();

		for(UnsagaEntityRegistry.Property prop:this.getProperties()){
			EntityRegistry.registerModEntity(prop.getEntityClass(), prop.getName(), prop.getId(), UnsagaMod.instance, prop.getTrackingRange(), prop.getUpdateFreq(), prop.isSendsVelocityUpdates());
		}

	}

	@Override
	protected void registerObjects() {
		this.put(boulder);
		this.put(bubbleBlow);
		this.put(shadow);
		this.put(flyingAxe);
		this.put(throwingKnife);
		this.put(arrow);
		this.put(chest);
		this.put(treasureSlime);
		this.put(liquidBall);
		this.put(ruffleTree);
		this.put(bullet);
		this.put(beam);
		this.put(blaster);
	}
}
