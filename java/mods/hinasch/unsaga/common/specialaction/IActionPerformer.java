package mods.hinasch.unsaga.common.specialaction;

import java.util.Optional;

import mods.hinasch.lib.particle.ParticleHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.damage.PairDamage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IActionPerformer<T, V> {

	enum TargetType{
		OWNER,TARGET,POSITION;
	}
	public void broadCastMessage(String msg);
	public V getAction();
	public T getActionProperty();
	public Optional<ItemStack> getArticle();
	public PairDamage getModifiedStrength();
	public EntityLivingBase getPerformer();
	public Optional<EntityLivingBase> getTarget();
	public Optional<BlockPos> getTargetCoordinate();
	public IActionPerformer.TargetType getTargetType();
	public World getWorld();
	public void playSound(XYZPos pos,SoundEvent soundIn,boolean distanceDelay);
	public void playSound(XYZPos pos,SoundEvent soundIn,boolean distanceDelay,float pitch);
	public IActionPerformer setTarget(EntityLivingBase liv);
	public void setTargetCoordinate(BlockPos pos);
	public void spawnParticle(ParticleHelper.MovingType type,Entity target,EnumParticleTypes par,int density,double speedscale);
	public void spawnParticle(ParticleHelper.MovingType type,XYZPos pos,EnumParticleTypes par,int density,double speedscale,int... params);
}
