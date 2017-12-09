package mods.hinasch.unsaga.core.potion;

import com.google.common.collect.ImmutableList;

import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;

public class ShieldPropertyRegistry {

	public final ShieldProperty water = new ShieldProperty(in ->in.waterShield ,0.28F, 0.0F, "textures/entity/magicshield/water.png")
			.setBlockableBehavior(in ->in.getSubTypes().contains(Sub.FREEZE));
	public final ShieldProperty fire = new ShieldProperty(in -> in.selfBurning,0.28F, 0.0F, "textures/entity/magicshield/fire.png")
			.setBlockableBehavior(dsu ->dsu.isFireDamage() || !dsu.getSubTypes().isEmpty() || dsu.isMagicDamage() || dsu.isExplosion());
	public final ShieldProperty leaf = new ShieldProperty(in -> in.leafShield,0.15F, 0.5F, "textures/entity/magicshield/leaf.png")
			.setBlockableBehavior(dsu ->!dsu.isUnblockable() && !dsu.isMagicDamage() && !dsu.getDamageTypeUnsaga().contains(General.MAGIC));
	public final ShieldProperty missile = new ShieldProperty(in -> in.missileGuard,0.0F,1.0F, "textures/entity/magicshield/missile.png")
			.setBlockableBehavior(dsu ->dsu.getDamageTypeUnsaga().contains(General.SPEAR) && !dsu.isUnblockable());
	public final ShieldProperty aegis = new ShieldProperty(in -> in.aegisShield,0.15F, 0.5F, "textures/entity/magicshield/aegis.png")
			.setBlockableBehavior(dsu->dsu.isFireDamage() || dsu.getSubTypes().contains(Sub.FIRE));

	public final ImmutableList<ShieldProperty> shields = ImmutableList.of(water,fire,leaf,missile,aegis);

	private static ShieldPropertyRegistry INSTANCE;
	public static ShieldPropertyRegistry instance(){
		if(INSTANCE == null){
			INSTANCE = new ShieldPropertyRegistry();
		}
		return INSTANCE;
	}

	private ShieldPropertyRegistry(){

	}
}
