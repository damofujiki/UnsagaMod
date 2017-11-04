package mods.hinasch.lib.sync;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SafeBlockHandlerBase implements Runnable{
	protected World world;
	protected IBlockState blockdata;
	protected BlockPos pos;

	public SafeBlockHandlerBase(World world, IBlockState blockdata, BlockPos pos) {
		super();
		this.world = world;
		this.blockdata = blockdata;
		this.pos = pos;
	}
}
