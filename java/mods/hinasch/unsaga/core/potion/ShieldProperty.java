package mods.hinasch.unsaga.core.potion;

import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableList;

import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.potion.UnsagaPotions.LivingHurtEventPotion;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class ShieldProperty {

	UnsagaPotions up = UnsagaPotions.instance();
	public static final ShieldProperty WATER = new ShieldProperty(in ->in.waterShield ,0.28F, 0.0F, "textures/entity/magicshield/water.png")
			.setBlockableBehavior(in ->in.getSubTypes().contains(Sub.FREEZE));
	public static final ShieldProperty FIRE = new ShieldProperty(in -> in.selfBurning,0.28F, 0.0F, "textures/entity/magicshield/fire.png")
			.setBlockableBehavior(dsu ->dsu.isFireDamage() || !dsu.getSubTypes().isEmpty() || dsu.isMagicDamage() || dsu.isExplosion());
	public static final ShieldProperty LEAF = new ShieldProperty(in -> in.leafShield,0.15F, 0.5F, "textures/entity/magicshield/leaf.png")
			.setBlockableBehavior(dsu ->!dsu.isUnblockable() && !dsu.isMagicDamage() && !dsu.getDamageTypeUnsaga().contains(General.MAGIC));
	public static final ShieldProperty MISSILE = new ShieldProperty(in -> in.missileGuard,0.0F,1.0F, "textures/entity/magicshield/missile.png")
			.setBlockableBehavior(dsu ->dsu.getDamageTypeUnsaga().contains(General.SPEAR) && !dsu.isUnblockable());
	public static final ShieldProperty AEGIS = new ShieldProperty(in -> in.aegisShield,0.15F, 0.5F, "textures/entity/magicshield/aegis.png")
			.setBlockableBehavior(dsu->dsu.isFireDamage() || dsu.getSubTypes().contains(Sub.FIRE));

	public static final ImmutableList<ShieldProperty> shields = ImmutableList.of(WATER,FIRE,LEAF,MISSILE,AEGIS);
	public Potion getPotion() {
		return potion.apply(UnsagaPotions.instance());
	}

	public Predicate<DamageSourceUnsaga> getBlockableChecker() {
		return blockable;
	}

	public float getReduceDamage() {
		return reduceDamage;
	}

	public float getProtectProb() {
		return protectProb;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	final Function<UnsagaPotions,Potion> potion;
	Predicate<DamageSourceUnsaga> blockable;
	final float reduceDamage;
	final float protectProb;
	final ResourceLocation texture;

	public ShieldProperty(Function<UnsagaPotions,Potion> potion,float reduce,float protect,String res){
		this.potion = potion;
		this.reduceDamage = reduce;
		this.protectProb = protect;
		this.texture = new ResourceLocation(UnsagaMod.MODID,res);

	}

	public ShieldProperty setBlockableBehavior(Predicate<DamageSourceUnsaga> pre){
		this.blockable = pre;
		return this;
	}

	public static class ShieldEvent extends LivingHurtEventPotion{

		final ShieldProperty prop;
		public ShieldEvent(ShieldProperty s){
			super(s.potion);
			this.prop = s;
		}
		@Override
		public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {

			return super.apply(e, dsu) && prop.getBlockableChecker().test(dsu);
		}

		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return prop.getPotion().getName()+".shield";
		}
		@Override
		public DamageSource processPotion(LivingHurtEvent e, DamageSourceUnsaga dsu, int amp) {
			float reduce = prop.getReduceDamage() * amp;
			float amount = e.getAmount() - reduce;
			if(amount<0){
				amount = 0;
			}
			if((prop.getProtectProb() + (amp * 0.1F))>=e.getEntityLiving().getRNG().nextFloat()){
				amount = 0;
			}
			e.setAmount(amount);

			return dsu;
		}

	}
}
