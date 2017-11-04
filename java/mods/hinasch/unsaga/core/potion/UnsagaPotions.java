package mods.hinasch.unsaga.core.potion;



import java.util.List;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.ILivingUpdateEvent;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.entity.passive.EntityShadow;
import mods.hinasch.unsaga.core.potion.ShieldProperty.ShieldEvent;
import mods.hinasch.unsaga.core.potion.StatePropertyPotion.StatePotion;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UnsagaPotions extends PropertyRegistry<PotionUnsaga>{


	public PotionUnsaga fear;
	public PotionUnsaga downDex;
	public PotionUnsaga downVit ;
	public PotionUnsaga downInt;
	public PotionUnsaga gravity;
	public PotionUnsaga upMental;
	public PotionUnsaga upInt;
	public PotionUnsaga upVit;
	public PotionUnsaga lockSlime;
	public PotionUnsaga detected;
	public PotionUnsaga coolDown ;
	public PotionUnsaga sleep;
	public PotionUnsaga lifeBoost;
	public PotionUnsaga veilFire;
	public PotionUnsaga veilWood;
	public PotionUnsaga veilEarth;
	public PotionUnsaga veilWater;
	public PotionUnsaga veilMetal;
	public PotionUnsaga waterShield;
	public PotionUnsaga selfBurning;
	public PotionUnsaga aegisShield;
	public PotionUnsaga leafShield;;
	public PotionUnsaga missileGuard;;
	public PotionUnsaga detectPlant;
	public PotionUnsaga detectGold;
	public PotionUnsaga detectTreasure;;
	public PotionUnsaga goldFinger;
	public PotionUnsaga spellMagnet;
	public PotionUnsaga shadowServant;
	public PotionUnsaga holySeal;
	public PotionUnsaga darkSeal;

	public ImmutableList<Potion> statusDownDebuffs;
	public ImmutableList<Potion> mentalDebuffs;
	public ImmutableList<Potion> bodyDebuffs;
	public List<ShieldEvent> shieldEvents = Lists.newArrayList();
	protected static UnsagaPotions INSTANCE;

	public static UnsagaPotions instance(){
		if(INSTANCE==null){
			INSTANCE = new UnsagaPotions();
		}
		return INSTANCE;
	}
	protected UnsagaPotions(){



	}

	@Override
	public void init() {


	}

	@Override
	public void preInit() {
		//		this.fear.setPotionType(new PotionType(fear.getName(),new PotionEffectFear[]{new PotionEffectFear(fear,ItemUtil.getPotionTime(30),0)}));
		this.registerObjects();

	}

	@Override
	protected void registerObjects() {
		darkSeal = this.put(PotionUnsaga.buff("darkSeal", 250,5,1));
		holySeal = this.put(PotionUnsaga.buff("holySeal", 250,5,1));
		shadowServant = this.put(PotionUnsaga.buff("shadowServant", 250,5,1));
		goldFinger = this.put(PotionUnsaga.buff("goldFinger", 250,5,1));
		spellMagnet = this.put(PotionUnsaga.buff("spellMagnet", 250,5,1));
		sleep = this.put(PotionUnsaga.badPotion("sleep", 250,5,1));
		fear = this.put(PotionUnsaga.badPotion("fear", 250,0,0));
		gravity = this.put(PotionUnsaga.badPotion("gravity", 250,8,0));
		downDex = this.put((PotionUnsaga) PotionUnsaga.badPotion("downDex", 250,1,1).registerPotionAttributeModifier(AdditionalStatus.DEXTALITY, "9eb8e2f7-9f67-4e7e-b55f-62cb060b8599", -0.10D, Statics.OPERATION_INCREMENT));
		downVit = this.put((PotionUnsaga) PotionUnsaga.badPotion("downVit", 250,2,1).registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "b1726066-1f04-45c9-8b01-8baa39bd9005", -1.0D, Statics.OPERATION_INCREMENT));
		downInt = this.put((PotionUnsaga) PotionUnsaga.badPotion("downInt", 250,3,1).registerPotionAttributeModifier(AdditionalStatus.INTELLIGENCE, "7ce9b1ce-5b19-427a-9281-4db7c90c041b", -0.10D, Statics.OPERATION_INCREMENT));
		lockSlime = this.put(PotionUnsaga.badPotion("lockSlime", 250,5,0));
		detected = this.put((PotionUnsaga) PotionUnsaga.badPotion("detected", 250,8,1).registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR, "ec8a8e69-80a1-4c22-ba72-94c790e5c7d5", -0.1D, Statics.OPERATION_INCREMENT));
		coolDown = this.put(PotionUnsaga.badPotion("coolDown", 250,1,1));
		waterShield = this.put(PotionUnsaga.buff("waterShield", 250,6,1));
		selfBurning = this.put(PotionUnsaga.buff("selfBurning", 250,6,1));
		missileGuard = this.put(PotionUnsaga.buff("missileGuard", 250,6,1));
		leafShield = this.put(PotionUnsaga.buff("leafShield", 250,6,1));
		aegisShield = this.put(PotionUnsaga.buff("aegisShield", 250,6,1));
		detectPlant = this.put(PotionUnsaga.buff("detectPlant", 250,8,1));
		detectGold = this.put(PotionUnsaga.buff("detectGold", 250,8,1));
		detectTreasure = this.put(PotionUnsaga.buff("detectTreasure", 250,8,1));
		lifeBoost = this.put((PotionUnsaga) PotionUnsaga.buff("lifeBoost", 250,7,2).registerPotionAttributeModifier(AdditionalStatus.NATURAL_HEAL_SPEED, "7ce9b1ce-5b19-427a-9281-4db7c90c041b", -10D, Statics.OPERATION_INCREMENT));
		upVit = this.put((PotionUnsaga) PotionUnsaga.buff("upVit", 250,2,0).registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, "7e1e210e-fa95-4bb0-8649-962426819a4b", 1.0D, Statics.OPERATION_INCREMENT));
		upInt = this.put((PotionUnsaga) PotionUnsaga.buff("upInt", 250,3,0).registerPotionAttributeModifier(AdditionalStatus.INTELLIGENCE, "7ce9b1ce-5b19-427a-9281-4db7c90c041e", 0.10D, Statics.OPERATION_INCREMENT));
		upMental = this.put((PotionUnsaga) PotionUnsaga.buff("upMental", 250,3,0).registerPotionAttributeModifier(AdditionalStatus.MENTAL, "7ce9b1ce-5b19-427a-9281-4fb7c90c041e", 0.10D, Statics.OPERATION_INCREMENT));
		veilFire = this.put((PotionUnsaga) PotionUnsaga.buff("veilFire", 250,6,0).registerPotionAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FIRE), "1e3b0dc5-ddec-4a94-ab07-ed4d47b7b412", 5D, Statics.OPERATION_INCREMENT));
		veilWater = this.put((PotionUnsaga) PotionUnsaga.buff("veilWater", 250,6,0).registerPotionAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WATER), "6e6097e8-0cbe-402e-8fb8-5095be33ae4e", 5D, Statics.OPERATION_INCREMENT));
		veilEarth = this.put((PotionUnsaga) PotionUnsaga.buff("veilEarth", 250,6,0).registerPotionAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.EARTH), "129b9b6a-addd-4578-a9dd-5ce8c50254af", 5D, Statics.OPERATION_INCREMENT));
		veilWood = this.put((PotionUnsaga) PotionUnsaga.buff("veilWwood", 250,6,0).registerPotionAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.WOOD), "f000b94d-f0fe-4ca8-9bfd-7171b367bd45", 5D, Statics.OPERATION_INCREMENT));
		veilMetal = this.put((PotionUnsaga) PotionUnsaga.buff("veilMetal", 250,6,0).registerPotionAttributeModifier(AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.METAL), "613cbb37-2c3f-4e57-be01-72c8bbac5ad1", 5D, Statics.OPERATION_INCREMENT));
		this.statusDownDebuffs = ImmutableList.of(MobEffects.SLOWNESS,MobEffects.WEAKNESS,MobEffects.UNLUCK,MobEffects.MINING_FATIGUE,downDex,downVit,downInt);
		this.mentalDebuffs = ImmutableList.of(MobEffects.WITHER,fear,MobEffects.BLINDNESS);
		this.bodyDebuffs = ImmutableList.of(MobEffects.POISON,MobEffects.BLINDNESS);


		for(PotionUnsaga potion:this.getProperties()){
			this.registerToGameData(potion);
		}
	}

	public void registerToGameData(PotionUnsaga... potions){
		for(PotionUnsaga p:potions){
			GameRegistry.register(p,p.getKey());
			p.initPotionType();
			GameRegistry.register(p.getPotionType(),new ResourceLocation(UnsagaMod.MODID,p.getName()));
		}
	}
	//	@Override
	//	public PotionUnsaga put(PotionUnsaga p){
	//		GameRegistry.register(p,p.getKey());
	//		p.initPotionType();
	//		UnsagaMod.logger.trace("potion", p.getPotionType());
	//		GameRegistry.register(p.getPotionType(),new ResourceLocation(UnsagaMod.MODID,p.getName()));
	//		return super.put(p);
	//	}


	@SubscribeEvent
	public void onLivingDeath(LivingDeathEvent e){
		if(e.getSource().getEntity() instanceof EntityLivingBase){
			EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
			if(attacker.isPotionActive(instance().goldFinger) && attacker.getActivePotionEffect(instance().goldFinger)!=null){
				PotionEffect effect = attacker.getActivePotionEffect(instance().goldFinger);

				float prob = 0.1F * (1.0F + (float)effect.getAmplifier());
				if(attacker.getRNG().nextFloat()<prob){
					if(WorldHelper.isServer(e.getEntityLiving().getEntityWorld())){
						ItemStack goldNugget = new ItemStack(Items.GOLD_NUGGET);
						ItemUtil.dropItem(attacker.getEntityWorld(), goldNugget, XYZPos.createFrom(e.getEntityLiving()));;
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent e){
		if(e.getSource().getEntity() instanceof EntityLivingBase){
			EntityLivingBase owner = (EntityLivingBase) e.getSource().getEntity();
			if(owner.isPotionActive(instance().shadowServant)){
				if(!e.getSource().isMagicDamage() && !e.getSource().isProjectile()){
					EntityShadow shadow = new EntityShadow(owner.getEntityWorld(),owner,e.getEntityLiving(),25);
					BlockPos pos = e.getEntityLiving().getPosition();
					shadow.setPositionAndRotation(pos.getX(), pos.getY()+1.5F, pos.getZ(), owner.rotationYaw, owner.rotationPitch);
					if(WorldHelper.isServer(owner.getEntityWorld())){
						owner.getEntityWorld().spawnEntityInWorld(shadow);
				}

				}
			}
		}
	}
	public abstract static class LivingHurtEventPotion extends  LivingHurtEventUnsagaBase{

		Function<UnsagaPotions,Potion> potion;
		public LivingHurtEventPotion(Function<UnsagaPotions,Potion> potion){
			this.potion = potion;
		}
		@Override
		public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			// TODO 自動生成されたメソッド・スタブ
			return e.getEntityLiving().isPotionActive(potion.apply(instance()));
		}

		@Override
		public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			int amp = 1;
			if(e.getEntityLiving().getActivePotionEffect(instance().selfBurning)!=null){
				amp += e.getEntityLiving().getActivePotionEffect(instance().selfBurning).getAmplifier();
				return this.processPotion(e, dsu, amp);
			}
			return dsu;
		}

		/**
		 *
		 * @param e
		 * @param dsu
		 * @param amp 1～
		 * @return
		 */
		public abstract DamageSource processPotion(LivingHurtEvent e, DamageSourceUnsaga dsu,int amp);

		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return potion.apply(instance()).getName();
		}

	}
	public static void registerEvent(){
		for(ShieldProperty shield:ShieldProperty.shields){
			instance().shieldEvents.add(new ShieldProperty.ShieldEvent(shield));
		}
		HSLibs.registerEvent(UnsagaPotions.instance());

		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				// TODO 自動生成されたメソッド・スタブ
				return e.getEntityLiving().getActivePotionEffects().stream().anyMatch(in -> in.getPotion() instanceof PotionUnsaga);
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				e.getEntityLiving().getActivePotionEffects().forEach(in ->{
					if(in.getPotion() instanceof PotionUnsaga){
						PotionUnsaga pu = (PotionUnsaga) in.getPotion();
						pu.affectOnHurt(e, dsu,in.getAmplifier());
					}

				});
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "potion events";
			}}
		);

		HSLib.core().events.livingUpdate.getEvents().add(new ILivingUpdateEvent(){

			@Override
			public void update(LivingUpdateEvent e) {
				//				UnsagaMod.logger.trace(this.getName(), "うｐだて");
				if(e.getEntityLiving() instanceof EntityLiving){
					EntityLiving living = (EntityLiving) e.getEntityLiving();
					if(living.isPotionActive(UnsagaPotions.instance().fear)){
						//						UnsagaMod.logger.trace(this.getName(), "fearかかってます");
						if(EntityStateCapability.adapter.hasCapability(living)){
							StatePotion state = (StatePotion) EntityStateCapability.adapter.getCapability(living).getState(StateRegistry.instance().statePotion);
							//							UnsagaMod.logger.trace(this.getName(),state);
							if(!state.isHasAddedFearTask() && living instanceof EntityCreature){
								state.removeTasks((EntityCreature) living);
								state.addTask((EntityCreature) living,new EntityAIAvoidEntity((EntityCreature) living, EntityPlayer.class, 10.0F, 1.0D, 1.2D));
								state.setHasAddedFearTask(true);
								UnsagaMod.logger.trace(this.getName(), "AI埋め込み成功です");
							}
						}
					}else{
						if(EntityStateCapability.adapter.hasCapability(living)){
							StatePotion state = (StatePotion) EntityStateCapability.adapter.getCapability(living).getState(StateRegistry.instance().statePotion);
							if(state.isHasAddedFearTask() &&  living instanceof EntityCreature){
								state.restoreTasks((EntityCreature) living);
								state.setHasAddedFearTask(false);
								UnsagaMod.logger.trace(this.getName(), "AI復元成功です");
							}
						}
					}

				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "potion.fear";
			}}
				);
	}
}
