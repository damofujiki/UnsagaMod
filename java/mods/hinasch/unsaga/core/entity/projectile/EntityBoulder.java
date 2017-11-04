package mods.hinasch.unsaga.core.entity.projectile;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import mods.hinasch.lib.entity.EntityThrowableBase;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBoulder extends EntityThrowableBase{

	public EntityBoulder(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public EntityBoulder(World par1World, EntityLivingBase par2EntityLiving)
	{
		super(par1World,par2EntityLiving);


	}

	public static final ImmutableSet<Block> DESTROYABLE = ImmutableSet.of(Blocks.GLASS_PANE,Blocks.FLOWER_POT,Blocks.GLOWSTONE,Blocks.GLASS,Blocks.GLOWSTONE);

	public Set<Block> getDestroyableBlocks(){
		return this.DESTROYABLE;
	}

	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(),this,1.0F,General.PUNCH);
	}



}
