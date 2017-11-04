package mods.hinasch.unsagamagic.block;

import java.util.Random;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.tileentity.TileEntityBlockStateUpdatable.BlockTileEntityShouldReflesh;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.init.UnsagaGui;
import mods.hinasch.unsagamagic.tileentity.TileEntityDecipheringTable;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDecipheringTable extends BlockContainer implements BlockTileEntityShouldReflesh{

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool TABLET = PropertyBool.create("tablet");
	public AxisAlignedBB TABLE_BB = new AxisAlignedBB(0,0,0,1.0D,0.575D,1.0D);
	public BlockDecipheringTable() {
		super(Material.WOOD);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {TABLET, FACING});
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

	@Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
		if(worldIn.getTileEntity(pos) instanceof TileEntityDecipheringTable){
			TileEntityDecipheringTable table = (TileEntityDecipheringTable) worldIn.getTileEntity(pos);
			ItemStack tablet = table.getTablet();
			if(WorldHelper.isServer(worldIn) && tablet!=null){
				ItemUtil.dropItem(worldIn, tablet, new XYZPos(pos));
			}

		}
        super.breakBlock(worldIn, pos, state);

    }
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TABLE_BB;
    }

	@Override
    public IBlockState onBlockPlaced(final World worldIn, final BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {

		UnsagaMod.logger.trace("facing:", facing);
        return this.getStateFromMeta(meta).withProperty(FACING, EnumFacing.getHorizontal(placer.getHorizontalFacing().getHorizontalIndex())).withProperty(TABLET, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
    	int i = meta & 7;

        EnumFacing enumfacing = EnumFacing.getFront(i);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        int j = meta & 8 >> 3;

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(TABLET, j == 0 ? false : true);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();



		boolean hasTablet = ((Boolean)state.getValue(TABLET));
		int j =  hasTablet ? 1 : 0;
		j = j << 3;

		return i | j;
    }
	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if(worldIn.getTileEntity(pos) instanceof TileEntityDecipheringTable){
			if(WorldHelper.isClient(worldIn)){

				HSLibs.openGuiFromClient(UnsagaGui.Type.TABLET,new XYZPos(pos));

			}
			return true;
		}


        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		UnsagaMod.logger.trace(this.getClass().getName(),"つくられました");
		return new TileEntityDecipheringTable();
	}

	@Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }


}
