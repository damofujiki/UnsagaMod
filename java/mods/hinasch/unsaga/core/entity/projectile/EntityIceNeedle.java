package mods.hinasch.unsaga.core.entity.projectile;

import mods.hinasch.lib.entity.EntityThrowableBase;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityIceNeedle extends EntityThrowableBase{

	public EntityIceNeedle(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public EntityIceNeedle(World world,EntityLivingBase caster){
		super(world, caster);
	}
	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(getThrower(), this,SpellRegistry.instance().iceNeedle.getEffectStrength().lp(), General.PUNCH).setSubTypes(Sub.FREEZE);
	}

}
