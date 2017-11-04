package mods.hinasch.unsaga.core.potion;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.entity.RangedHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.registry.IPropertyElement;
import mods.hinasch.lib.util.VecUtil;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.potion.ShieldProperty.ShieldEvent;
import mods.hinasch.unsaga.core.potion.StatePropertyPotion.StatePotion;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PotionUnsaga extends Potion implements IPropertyElement{


	public static final ResourceLocation TEXTURE = new ResourceLocation(UnsagaMod.MODID,"textures/gui/container/status_effects.png");
	public static PotionUnsaga of(String name,boolean isBadEffectIn, int liquidColorIn,int u,int v){
		return new PotionUnsaga(name, isBadEffectIn, liquidColorIn,u,v);
	}

	public static PotionUnsaga badPotion(String name, int liquidColorIn,int u,int v){
		return new PotionUnsaga(name, true, liquidColorIn,u,v);
	}

	public static PotionUnsaga buff(String name, int liquidColorIn,int u,int v){
		return new PotionUnsaga(name, false, liquidColorIn,u,v);
	}
	ResourceLocation key;
	PotionType type;
	protected PotionUnsaga(String name,boolean isBadEffectIn, int liquidColorIn,int u,int v) {
		super(isBadEffectIn, 250);
		this.setIconIndex(u, v);
//		this.setRegistryName(new ResourceLocation(UnsagaMod.MODID,"potion."+name));
		this.setPotionName("unsaga.potion."+name);
		this.key = new ResourceLocation(UnsagaMod.MODID,name);

	}

	@Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int p_76394_2_)
    {
		PotionEffect effect = entityLivingBaseIn.getActivePotionEffect(this);
		if(effect!=null){
			int amplifier = effect.getAmplifier();
			World world = entityLivingBaseIn.getEntityWorld();
			if(this==UnsagaPotions.instance().holySeal){
				RangedHelper.create(world, entityLivingBaseIn, entityLivingBaseIn.getEntityBoundingBox().expandXyz(10.0D))
				.setSelector((self,target)->target.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD)
				.setConsumer((self,in)->{
					Vec3d v = in.getPositionVector().subtract(entityLivingBaseIn.getPositionVector()).normalize().scale(0.1D);
					in.motionX += v.xCoord;
					in.motionZ += v.zCoord;
				}).invoke();

			}
			if(this==UnsagaPotions.instance().darkSeal){
				RangedHelper.create(world, entityLivingBaseIn, entityLivingBaseIn.getEntityBoundingBox().expandXyz(10.0D))
				.setSelector((self,target)->target.getCreatureAttribute()!=EnumCreatureAttribute.UNDEAD)
				.setConsumer((self,in)->{
					Vec3d v = in.getPositionVector().subtract(entityLivingBaseIn.getPositionVector()).normalize().scale(0.1D);
					in.motionX += v.xCoord;
					in.motionZ += v.zCoord;
				}).invoke();

			}

			if(this==UnsagaPotions.instance().sleep){
				if(EntityStateCapability.adapter.hasCapability(entityLivingBaseIn)){
					StatePotion state = (StatePotion) EntityStateCapability.adapter.getCapability(entityLivingBaseIn).getState(StateRegistry.instance().statePotion);
					if(state.getStoppedPos().isPresent()){
						XYZPos p = state.getStoppedPos().get();
						entityLivingBaseIn.setPosition(p.dx, entityLivingBaseIn.posY, p.dz);
					}else{
						state.setStoppedPos(XYZPos.createFrom(entityLivingBaseIn));
					}
				}

//				entityLivingBaseIn.motionX = 0;
//				if(entityLivingBaseIn.motionY>0){
//					entityLivingBaseIn.motionY = 0;
//				}
//				entityLivingBaseIn.motionZ = 0;
			}
			if(this==UnsagaPotions.instance().gravity){
				if(entityLivingBaseIn.posY>0){
					entityLivingBaseIn.motionY -= 1.0D;
				}
			}
			if(this==UnsagaPotions.instance().spellMagnet){
				int amp = 1 + amplifier;
				world.getEntitiesWithinAABB(Entity.class, entityLivingBaseIn.getEntityBoundingBox().expandXyz(8.0D * amp),in -> in!=entityLivingBaseIn)
				.forEach(in ->{
					Vec3d vec = VecUtil.getHeadingToEntityVec(in,entityLivingBaseIn).normalize().scale(0.2D);
					in.setVelocity(vec.xCoord, vec.yCoord >0 ? 0 : vec.yCoord, vec.zCoord);
				});
			}

			if(this==UnsagaPotions.instance().waterShield && entityLivingBaseIn.isBurning()){
				entityLivingBaseIn.extinguish();
			}
		}
    }
	public void affectOnHurt(LivingHurtEvent e,DamageSourceUnsaga dsu,int amplifier){
		if(this==UnsagaPotions.instance().gravity){
			if(dsu.getParentDamageSource()==DamageSource.fall){
				float damage = e.getAmount() * 1.5F;
				e.setAmount(damage);
			}
		}
		if(this==UnsagaPotions.instance().selfBurning){
			if(dsu.getEntity()!=null){
				DamageSourceUnsaga fireDamage = DamageSourceUnsaga.create(e.getEntityLiving(), 0.5F, General.MAGIC).setSubTypes(Sub.FIRE);
				dsu.getEntity().attackEntityFrom(fireDamage, 1.0F+amplifier);
				dsu.getEntity().setFire(10 * amplifier);
			}
		}

		for(ShieldEvent ev:UnsagaPotions.instance().shieldEvents){
			if(ev.apply(e, dsu)){
				ev.processPotion(e, dsu, amplifier);
			}
		}
	}
	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public int getStatusIconIndex() {
		ClientHelper.bindTextureToTextureManager(TEXTURE);
		return super.getStatusIconIndex();
	}

	@Override
	public boolean hasStatusIcon() {
		return true;
	}

	@Override
	public ResourceLocation getKey() {
		// TODO 自動生成されたメソッド・スタブ
		return this.key;
	}

	public void initPotionType(){
		if(this.type==null){
			this.type = new PotionType("unsaga."+this.getName(),new PotionEffect[]{new PotionEffect(this,ItemUtil.getPotionTime(10),0)});
		}

	}

	public void setPotionType(PotionType type){
		this.type = type;
	}

	public PotionType getPotionType(){
		return this.type;
	}


}
