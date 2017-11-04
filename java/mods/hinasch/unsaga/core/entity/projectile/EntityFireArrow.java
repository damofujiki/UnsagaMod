package mods.hinasch.unsaga.core.entity.projectile;

import java.util.ArrayList;
import java.util.Random;

import com.google.common.collect.Lists;

import mods.hinasch.lib.entity.EntityThrowableBase;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class EntityFireArrow extends EntityThrowableBase{

	protected boolean isAmplified = false;

	public EntityFireArrow(World par1World) {
		super(par1World);

	}

	public EntityFireArrow(World par1World, EntityLivingBase par2EntityLiving)
	{
		super(par1World,par2EntityLiving);


	}


	public void drawParticles(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(par5Random.nextInt(5)==0){
			return;
		}
		int var6=0;
		double var7 = (double)((float)par2 + 0.5F);
		double var9 = (double)((float)par3 + 0.7F);
		double var11 = (double)((float)par4 + 0.5F);
		double var13 = 0.2199999988079071D;
		double var15 = 0.27000001072883606D;

		if (var6 == 1)
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, var7 - var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
		}
		else if (var6 == 2)
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, var7 + var15, var9 + var13, var11, 0.0D, 0.0D, 0.0D);
		}
		else if (var6 == 3)
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, var7, var9 + var13, var11 - var15, 0.0D, 0.0D, 0.0D);
		}
		else if (var6 == 4)
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, var7, var9 + var13, var11 + var15, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var7, var9, var11, 0.0D, 0.0D, 0.0D);
			par1World.spawnParticle(EnumParticleTypes.FLAME, var7, var9, var11, 0.0D, 0.0D, 0.0D);
		}
	}

	boolean checked = false;
	@Override
	public void onUpdate(){

		super.onUpdate();
		this.drawParticles(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj.rand);
		if(this.isInWater()){
			this.setDead();
		}
		if(this.isInLava() && !this.isAmplified){
			this.isAmplified = true;
			this.setDamage(this.getDamage()*1.4F);

		}


	}

	public void onBlockDestroyed(BlockPos pos) {
		IBlockState state = worldObj.getBlockState(pos);
		Block bid = state.getBlock();
		int bmd = bid.getMetaFromState(state);
		Block block = bid;
		if(block == Blocks.AIR) {
			return;
		}
		worldObj.playEvent(2001, pos, Block.getIdFromBlock(bid) + (bmd  << 12));
		if(!worldObj.isRemote){
			boolean flag = worldObj.setBlockToAir(pos);
			if (block != null && flag) {
				block.onBlockDestroyedByPlayer(worldObj, pos, state);

			}
		}
	}
	public ArrayList<Block> desloyable = Lists.newArrayList(Blocks.ICE,Blocks.FROSTED_ICE,Blocks.PACKED_ICE);
	@Override
	protected void onImpact(RayTraceResult var1) {

		this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
		if(var1.typeOfHit==RayTraceResult.Type.ENTITY){
			if(var1.entityHit!=null){
				Entity hitEntity = var1.entityHit;
				DamageSourceUnsaga ds = DamageSourceUnsaga.createProjectile(getThrower(), this, 1, General.MAGIC);
				ds.setSubTypes(Sub.FIRE);
				ds.setFireDamage().setMagicDamage();
				ds.setDamageBypassesArmor();
				hitEntity.attackEntityFrom(ds, this.getDamage());
				hitEntity.setFire(6);
				this.setDead();
			}
		}
		if(var1.typeOfHit==RayTraceResult.Type.BLOCK){
			XYZPos pos = XYZPos.fromMovingObjectPos(var1);

			worldObj.setLightFor(EnumSkyBlock.BLOCK,pos, 0xff);
			worldObj.checkLightFor(EnumSkyBlock.BLOCK, pos.east());
			worldObj.checkLightFor(EnumSkyBlock.BLOCK,pos.west());
			worldObj.checkLightFor(EnumSkyBlock.BLOCK,pos.down());
			worldObj.checkLightFor(EnumSkyBlock.BLOCK,pos.up());
			worldObj.checkLightFor(EnumSkyBlock.BLOCK,pos.north());
			worldObj.checkLightFor(EnumSkyBlock.BLOCK,pos.south());

			if(WorldHelper.getMaterial(worldObj, new XYZPos(pos))==Material.VINE){
				this.onBlockDestroyed(pos);
			}
			if(desloyable.contains(WorldHelper.getBlock(this.worldObj,pos))){
//				this.onBlockDestroyed(pos,true);
			}
			//WorldHelper wh = new WorldHelper(this.worldObj);
//			if(worldObj.isAirBlock(pos.up())){
//				worldObj.setBlockState(pos.up(),Blocks.fire.getDefaultState());
//			}
			if(!WorldHelper.getMaterial(worldObj,new XYZPos(pos)).isReplaceable()){
				this.setDead();
			}
		}

	}

	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(), this, this.getDamage(), General.MAGIC).setSubTypes(Sub.FIRE);
	}



}
