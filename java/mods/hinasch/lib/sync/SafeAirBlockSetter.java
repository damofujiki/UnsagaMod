package mods.hinasch.lib.sync;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SafeAirBlockSetter extends SafeBlockHandlerBase{

	public SafeAirBlockSetter(World world, BlockPos pos) {
		super(world, null, pos);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void run() {
		world.setBlockToAir(pos);

	}

}
