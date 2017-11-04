package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.block.BlockOreBase;
import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistrySimple;

public class Blocks extends BlockItemRegistry<Block>{

	public RegistrySimple<ResourceLocation,Block> ores = new RegistrySimple();

	public Blocks() {
		super(UnsagaModAdditional.MODID);
		this.setUnlocalizedNamePrefix("unsaga");
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void register() {
		// TODO 自動生成されたメソッド・スタブ
		UnsagaModAdditional.ores.getProperties().forEach(in ->{
			ores.putObject(in.getKey(), new BlockOreBase(in,() -> UnsagaMod.secureRandom));
			this.put(ores.getObject(in.getKey()), in.getName(), UnsagaMod.core.tabMisc);
		});
	}

}
