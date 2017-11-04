package mods.hinasch.unsagamagic.block;

import java.util.Random;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsagamagic.tileentity.TileEntityHolySeal;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockHolySeal extends BlockContainer{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.3D,0.0D,0.3D,0.7D,0.2D,0.7D);
	protected BlockHolySeal() {
		super(Material.CIRCUITS);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return AABB;
	}
	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		// TODO 自動生成されたメソッド・スタブ
		return new TileEntityHolySeal();
	}
	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState worldIn, World pos, BlockPos state, Random rand)
    {
        super.randomDisplayTick(worldIn, pos, state, rand);

        if (rand.nextInt(10) == 0)
        {
            pos.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)state.getX() + rand.nextFloat()), (double)((float)state.getY() + 1.1F), (double)((float)state.getZ() + rand.nextFloat()), 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		if(worldIn.getTileEntity(pos) instanceof TileEntityHolySeal){
			TileEntityHolySeal holySeal = (TileEntityHolySeal) worldIn.getTileEntity(pos);
			ItemStack book = holySeal.getBook();
			ItemUtil.dropItem(worldIn, book, new XYZPos(pos));
		}

		super.breakBlock(worldIn, pos, state);

    }
}
