package mods.hinasch.lib.entity;

import java.util.Set;

import com.google.common.collect.Sets;

import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class EntityThrowableBase extends EntityThrowable
{

	private float damage = 1.0F;
	private int knockbackModifier = 0;

    public EntityThrowableBase(World worldIn)
    {
        super(worldIn);

    }

    public EntityThrowableBase(World worldIn, double x, double y, double z)
    {
        super(worldIn);
    }

    public EntityThrowableBase(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }




    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.03F;
    }

	public void setDamage(float par1){
		this.damage = par1;
	}

	public float getDamage(){
		return this.damage;
	}

	public void setKnockBackModifier(int par1){
		this.knockbackModifier = par1;
	}

	public int getKnockBackModifier(){
		return this.knockbackModifier;
	}

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
	@Override
    protected void onImpact(RayTraceResult result){
		if(result.typeOfHit==RayTraceResult.Type.BLOCK){
			this.onImpactBlock(result);
		}
		if(result.typeOfHit==RayTraceResult.Type.ENTITY){
			this.onImpactEntity(result);
		}
    }

	public void onImpactBlock(RayTraceResult result){
		if(WorldHelper.isClient(worldObj)){
			this.spawnParticle();
		}
		IBlockState state = this.worldObj.getBlockState(result.getBlockPos());
		if(state.getBlock()!=Blocks.AIR ){
			if(this.getDestroyableBlocks().contains(state.getBlock()) || this.getDestroyableMaterials().contains(state.getMaterial())){
				SoundAndSFX.playBlockBreakSFX(worldObj,result.getBlockPos(),state);
	    		worldObj.setBlockToAir(result.getBlockPos());
			}
		}

		if(!state.getMaterial().blocksMovement()){
			return;
		}
	}

	public void spawnParticle(){
		this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 1.0D, 0.0D, 0.0D);
	}

	public void onImpactEntity(RayTraceResult result){
		if(WorldHelper.isClient(worldObj)){
			this.spawnParticle();
		}

		DamageSource ds = this.getDamageSource(result);
		if(ds!=null){
			result.entityHit.attackEntityFrom(ds, this.getDamage());
			if(result.entityHit instanceof EntityLivingBase){
				if(this.getKnockBackModifier()>0){
					result.entityHit.addVelocity((double)(-MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F) * (float)this.getKnockBackModifier() * 0.5F), 0.1D,
							(double)(MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F) * (float)this.getKnockBackModifier() * 0.5F));
				}


			}
		}

	}
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
    	super.writeEntityToNBT(tagCompound);
        ///////////////////
        tagCompound.setFloat("attackDamage", this.damage);
        tagCompound.setInteger("knockback", this.knockbackModifier);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
    	super.readEntityFromNBT(tagCompund);
        this.damage = tagCompund.getFloat("attackDamage");
        this.knockbackModifier = tagCompund.getInteger("knockback");

    }


	public abstract DamageSource getDamageSource(RayTraceResult result);
	public Set<Block> getDestroyableBlocks(){
		return Sets.newHashSet();
	}
	public Set<Material> getDestroyableMaterials(){
		return Sets.newHashSet();
	}

//	public void playSFXBlockDestroyed(BlockPos pos,boolean isMelting) {
//		IBlockState state = this.worldObj.getBlockState(pos);
//
//		int meta = state.getBlock().getMetaFromState(state);
//		Block bid = state.getBlock();
//		if(state.getBlock()==Blocks.AIR) {
//			return;
//		}
//
//
//		if(!worldObj.isRemote){
//			boolean flag = worldObj.setBlockToAir(pos);
//			if (bid != null && flag) {
//				bid.onBlockDestroyedByPlayer(worldObj, pos, state);
//
//			}
//		}else{
//			if(isMelting){
//				XYZPos p = new XYZPos(pos);
//				worldObj.playSound(p.dx, p.dy, p.dz, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT
//						, 1.0F, 1.0F, true);
//			}else{
//				worldObj.playEvent(2001, pos, Block.getIdFromBlock(bid) + (meta  << 12));
//			}
//		}
//	}

}