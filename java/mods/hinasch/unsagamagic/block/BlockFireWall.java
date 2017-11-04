package mods.hinasch.unsagamagic.block;

import java.util.Random;

import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsagamagic.tileentity.TileEntityFireWall;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFireWall extends BlockContainer {

	//	public IIcon[] iconArray;
	//	private IIcon[] theIcon;
	protected static final AxisAlignedBB BOUNDING = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
//	public static final PropertyInteger FIRE_STR = PropertyInteger.create("fire_str", 0, 15);

	public BlockFireWall() {
		super(Material.FIRE);

	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}



	//	@Override
	//	public IIcon getIcon(int par1, int par2)
	//	{
	//		return par1 != 0 && par1 != 1 ? this.theIcon[1] : this.theIcon[0];
	//	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos,IBlockState state, Entity entityIn) {
		{
			if(entityIn instanceof EntityLivingBase && worldIn.getTileEntity(pos) instanceof TileEntityFireWall){
				TileEntityFireWall fireWall = (TileEntityFireWall) worldIn.getTileEntity(pos);
				EntityLivingBase living = (EntityLivingBase)entityIn;
				if(!living.isPotionActive(MobEffects.FIRE_RESISTANCE)){
					float damage = (float)fireWall.getFireStrength();
					damage = MathHelper.clamp_float(damage, 2.0F, 9.0F);
					living.attackEntityFrom(DamageSource.inFire, damage);
					living.setFire(5);
					float yaw = MathHelper.wrapDegrees(entityIn.rotationYaw + 180.0F);
					float i = 1.0F;
					living.addVelocity((double)(-MathHelper.sin((yaw) * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));


				}
			}
			if(entityIn instanceof EntityThrowable || entityIn instanceof IProjectile || entityIn instanceof EntityFireball){
				entityIn.setDead();
			}
		}



	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand)
	{
		Blocks.FIRE.randomDisplayTick(worldIn, pos, state,rand);

	}

	//	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5, ItemStack par6ItemStack)
	//	{
	//		return true;
	//	}

	//	@Override
	//    public void registerIcons(IconRegister par1IconRegister)
	//    {
	//        this.iconArray = new Icon[] {par1IconRegister.registerIcon(this.getTextureName() + "_layer_0"), par1IconRegister.registerIcon(this.getTextureName() + "_layer_1")};
	//    }

	@Override
	public void neighborChanged(IBlockState state,World worldIn ,BlockPos pos, Block blockIn)
	{


			this.triggerLavaMixEffects((World) worldIn,new XYZPos(pos));
			worldIn.setBlockToAir(pos);


	}

	@Override
	public boolean isCollidable()
	{
		return true;
	}

//	@Override
//	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
//	{
//		if (worldIn.provider.getDimension() > 0 || worldIn.getBlockState(pos.down()).getBlock() != Blocks.OBSIDIAN || !Blocks.PORTAL.func_176548_d(worldIn,pos))
//		{
//
//			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn) + worldIn.rand.nextInt(10));
//
//		}
//	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}




	protected void triggerLavaMixEffects(World par1World, XYZPos pos)
	{
		par1World.playSound(pos.dx + 0.5F, pos.dy + 0.5F, pos.dz + 0.5F, SoundEvents.BLOCK_LAVA_EXTINGUISH,SoundCategory.AMBIENT, 0.5F, 2.6F + (par1World.rand.nextFloat() - par1World.rand.nextFloat()) * 0.8F,true);

		for (int l = 0; l < 8; ++l)
		{
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.dx + Math.random(),pos.dy + 1.2D,pos.dz + Math.random(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	//	@Override
	//	public SpellMixTable getElements() {
	//		// TODO 自動生成されたメソッド・スタブ
	//		return new SpellMixTable(1.0F,0,0,-0.5F,0,0);
	//	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityFireWall();
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
