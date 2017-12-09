package mods.hinasch.unsaga.core.entity;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.registry.PropertyElementWithID;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.mob.EntityPoisonEater;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.entity.mob.EntitySignalTree;
import mods.hinasch.unsaga.core.entity.mob.EntityStormEater;
import mods.hinasch.unsaga.core.entity.mob.EntityTreasureSlime;
import mods.hinasch.unsaga.core.entity.passive.EntityBeam;
import mods.hinasch.unsaga.core.entity.passive.EntityFireWall;
import mods.hinasch.unsaga.core.entity.passive.EntityShadow;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.entity.projectile.EntityBlaster;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityBubbleBlow;
import mods.hinasch.unsaga.core.entity.projectile.EntityBullet;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityFireArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
import mods.hinasch.unsaga.core.entity.projectile.EntityIceNeedle;
import mods.hinasch.unsaga.core.entity.projectile.EntitySolutionLiquid;
import mods.hinasch.unsaga.core.entity.projectile.EntityThrowingKnife;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.DungeonHooks;
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
	public Property fireWall = new Property(14,"fireWall",EntityFireWall.class);
	public Property signalTree = new Property(15,"signalTree",EntitySignalTree.class).setUpdateFreq(3);
	public Property iceNeedle = new Property(16,"iceNeedle",EntityIceNeedle.class);
	public Property fireArrow = new Property(17,"fireArrow",EntityFireArrow.class);
	public Property stormEater = new Property(18,"stormEater",EntityStormEater.class).setUpdateFreq(3);
	public Property poisonEater = new Property(19,"poisonEater",EntityPoisonEater.class).setUpdateFreq(3);

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
		this.registerSpawn();
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();

		for(UnsagaEntityRegistry.Property prop:this.getProperties()){
			EntityRegistry.registerModEntity(prop.getEntityClass(), prop.getPropertyName(), prop.getId(), UnsagaMod.instance, prop.getTrackingRange(), prop.getUpdateFreq(), prop.isSendsVelocityUpdates());
		}

	}

	private void registerSpawn(){
		List<Biome> forests = Lists.newArrayList();
		Biome.REGISTRY.forEach(in ->{
			List<BiomeDictionary.Type> types = Lists.newArrayList(BiomeDictionary.getTypesForBiome(in));
			if(types.contains(BiomeDictionary.Type.FOREST) || types.contains(BiomeDictionary.Type.JUNGLE)){
				if(!types.contains(BiomeDictionary.Type.DRY)){
					forests.add(in);
				}

			}
		});

		Biome[] biomes = forests.toArray(new Biome[forests.size()]);

		List<Biome> hills = Lists.newArrayList();
		Biome.REGISTRY.forEach(in ->{
			List<BiomeDictionary.Type> types = Lists.newArrayList(BiomeDictionary.getTypesForBiome(in));
			if(types.contains(BiomeDictionary.Type.MOUNTAIN) || types.contains(BiomeDictionary.Type.OCEAN)){
				hills.add(in);
			}
		});

		Biome[] biomes2 = hills.toArray(new Biome[hills.size()]);
		int dens = UnsagaMod.configHandler.getDensityMonsterSpawn();
		dens = MathHelper.clamp_int(dens, 1, 100);
		if(biomes.length>0){
			EntityRegistry.addSpawn(EntitySignalTree.class, dens*4, 1, 3, EnumCreatureType.MONSTER, biomes);
			EntityRegistry.addSpawn(EntityRuffleTree.class, dens, 1, 1, EnumCreatureType.MONSTER, biomes);
		}
		if(biomes2.length>0){
			EntityRegistry.addSpawn(EntityStormEater.class, dens*3, 1, 3, EnumCreatureType.MONSTER, biomes2);
			EntityRegistry.addSpawn(EntityPoisonEater.class, dens*3, 1, 3, EnumCreatureType.MONSTER, biomes2);
		}


//		EntityRegistry.addSpawn(EntityStormEater.class, 40, 1, 3, EnumCreatureType.MONSTER, biomes);
//		EntityRegistry.addSpawn(EntityPoisonEater.class, 40, 1, 3, EnumCreatureType.MONSTER, biomes);
		DungeonHooks.addDungeonMob(this.signalTree.getPropertyName(),50);
		DungeonHooks.addDungeonMob(this.ruffleTree.getPropertyName(),10);
		DungeonHooks.addDungeonMob(this.stormEater.getPropertyName(),30);
		DungeonHooks.addDungeonMob(this.poisonEater.getPropertyName(),30);

		EntityRegistry.registerEgg(EntityUnsagaChestNew.class, 0x5b3e22, 0);
		EntityRegistry.registerEgg(EntityTreasureSlime.class, 0xdb2266, 0);

		EntityRegistry.registerEgg(EntityRuffleTree.class, 0x967f5e, 0);
		EntityRegistry.registerEgg(EntitySignalTree.class, 0x353dad, 0xd11919);
		EntityRegistry.registerEgg(EntityStormEater.class, 0x6c6f9d, 0x999999);
		EntityRegistry.registerEgg(EntityPoisonEater.class, 0xad134e, 0x0a2375);
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
		this.put(fireWall);
		this.put(signalTree);
		this.put(iceNeedle);
		this.put(fireArrow);
		this.put(stormEater);
		this.put(poisonEater);
	}
}
