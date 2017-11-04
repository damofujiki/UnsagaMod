package mods.hinasch.unsaga.core.entity.projectile;

import java.util.List;

import mods.hinasch.lib.entity.EntityThrowableBase;
import mods.hinasch.lib.world.ScanHelper;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.WorldHelper.ICustomChecker;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBubbleBlow extends EntityThrowableBase{

	public EntityBubbleBlow(World par1World) {
		super(par1World);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public EntityBubbleBlow(World par1World, EntityLivingBase par2EntityLiving)
	{
		super(par1World,par2EntityLiving);


	}

	@Override
    public void onUpdate()
    {
    	super.onUpdate();
    	if(this.isInLava()){
    		List<XYZPos> list = WorldHelper.findNear(worldObj, XYZPos.createFrom(this), 3, 3, new ICustomChecker(){

				@Override
				public boolean apply(World world, XYZPos pos, ScanHelper scan) {
					if(scan.getBlock()==Blocks.LAVA){
						return true;
					}
					if(scan.getBlock()==Blocks.FLOWING_LAVA){
						return true;
					}
					return false;
				}}
    		);

    		if(!list.isEmpty()){
        		this.onIntoLava(list.get(0),this.worldObj.getBlockState(list.get(0)));
    		}

    	}

    }

	@Override
	public void spawnParticle(){
        float f = (float)this.getEntityBoundingBox().minY;
        for (int j = 0; j < 8; ++j)
        {
            float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double)f1, (double)(f + 0.8F), this.posZ + (double)f2, this.motionX, this.motionY, this.motionZ, new int[0]);
        }
	}

	public void onImpactEntity(RayTraceResult result){
		super.onImpactEntity(result);
		result.entityHit.extinguish();
	}
	protected void onIntoLava(XYZPos pos,IBlockState state){

		if(!(state.getBlock() instanceof BlockLiquid)){
			return;
		}
		int level = (Integer)state.getValue(BlockLiquid.LEVEL);
        if(level==0){
        	this.worldObj.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
        }else if(level <= 4){
        	this.worldObj.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
        }

        this.playEffect(worldObj, pos);
	}

    protected void playEffect(World p_149799_1_, XYZPos pos)
    {
        p_149799_1_.playSound(pos.dx+0.5D,pos.dy+0.5D,pos.dz+0.5D, SoundEvents.BLOCK_LAVA_EXTINGUISH,SoundCategory.AMBIENT, 0.5F, 2.6F + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8F,true);

        for (int l = 0; l < 8; ++l)
        {
            p_149799_1_.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.dx + Math.random(), pos.dy + 1.2D, pos.dz + Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }
	@Override
	public DamageSource getDamageSource(RayTraceResult result) {
		// TODO 自動生成されたメソッド・スタブ
		return DamageSourceUnsaga.createProjectile(this.getThrower(),this,SpellRegistry.instance().bubbleBlow.getEffectStrength().lp(),General.PUNCH);
	}
}
