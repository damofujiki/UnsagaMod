package mods.hinasch.unsaga.core.block;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.init.UnsagaBlockRegistry;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterials;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockUnsagaStone extends Block{

	public static final PropertyEnum<BlockUnsagaStone.EnumType> VARIANT = PropertyEnum.<BlockUnsagaStone.EnumType>create("variant", BlockUnsagaStone.EnumType.class);


	public BlockUnsagaStone(Material materialIn) {
		super(materialIn);
		this.setHardness(1.5F);

	}

	@Override
    public Material getMaterial(IBlockState state)
    {
        EnumType type = EnumType.fromMeta(this.getMetaFromState(state));
        if(type==EnumType.SERPENTINE || type==EnumType.SERPENTINE_SMOOTH){
        	return Material.ROCK;
        }
        return Material.IRON;
    }

	@Override
    public MapColor getMapColor(IBlockState state)
    {
        return ((BlockUnsagaStone.EnumType)state.getValue(VARIANT)).getMapColor();
    }
    @Override
    public int damageDropped(IBlockState state)
    {
        return ((BlockUnsagaStone.EnumType)state.getValue(VARIANT)).getMeta();
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (BlockUnsagaStone.EnumType blockstone$enumtype : BlockUnsagaStone.EnumType.values())
        {
            list.add(new ItemStack(itemIn, 1, blockstone$enumtype.getMeta()));
        }
    }
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockUnsagaStone.EnumType.fromMeta(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockUnsagaStone.EnumType)state.getValue(VARIANT)).getMeta();
    }
    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }
	public static enum EnumType implements IIntSerializable,IStringSerializable{
		SERPENTINE(0,MapColor.GREEN,"serpentine"),SERPENTINE_SMOOTH(1,MapColor.GREEN,"serpentine_smooth","polishedSerpentine")
		,LEAD(2,MapColor.IRON,"lead","cubeLead"),METEORIC_IRON(3,MapColor.IRON,"meteoric_iron","cubeMeteoricIron")
		,DAMASCUS(4,MapColor.IRON,"damascus","cubeDamascus"),SILVER(5,MapColor.IRON,"silver","cubeSilver")
		,FAERIE_SILVER(6,MapColor.IRON,"faerie_silver","cubeFaerieSilver"),COPPER(7,MapColor.IRON,"copper","cubeCopper")
		,STEEL(8,MapColor.IRON,"steel","cubeSteel"),STEEL2(9,MapColor.IRON,"steel2","cubeSteel2")
		,METEORITE(10,MapColor.STONE,"meteorite","cubeMeteorite");

		UnsagaMaterials m = UnsagaMod.core.materialsNew;
		int meta;
		String name;
		MapColor color;
		String unlocalized;
		public MapColor getMapColor() {
			return color;
		}
		private EnumType (int meta,MapColor color,String name,String unlName){
			this.meta = meta;
			this.name = name;
			this.color = color;
			this.unlocalized =unlName;
		}

		private EnumType (int meta,MapColor color,String name){
			this(meta, color, name, name);
		}
		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return this.name;
		}

		public static List<String> getJsonNames(){
			String[] strs = new String[EnumType.values().length];
			Arrays.stream(EnumType.values()).forEach(in ->{
				strs[in.getMeta()] = in.getUnlocalizedName();
			});
			return Lists.newArrayList(strs);
		}
		public String getUnlocalizedName(){
			return this.unlocalized;
		}
		@Override
		public int getMeta() {
			// TODO 自動生成されたメソッド・スタブ
			return this.meta;
		}
		public ItemStack getBaseItem(int amount){
			ItemStack is = this.getBaseItem();
			ItemUtil.setStackSize(is, amount);
			return is;
		}
		public ItemStack getBaseItem(){
			ItemStack rt = null;
			switch(this){
			case COPPER:
				rt = RawMaterialItemRegistry.instance().copper.getItemStack(1);
				break;
			case DAMASCUS:
				rt = RawMaterialItemRegistry.instance().damascus.getItemStack(1);
				break;
			case FAERIE_SILVER:
				rt = RawMaterialItemRegistry.instance().faerieSilver.getItemStack(1);
				break;
			case LEAD:
				rt = RawMaterialItemRegistry.instance().lead.getItemStack(1);
				break;
			case METEORIC_IRON:
				rt = RawMaterialItemRegistry.instance().meteoricIron.getItemStack(1);
				break;
			case SERPENTINE:
				break;
			case SERPENTINE_SMOOTH:
				break;
			case SILVER:
				rt = RawMaterialItemRegistry.instance().silk.getItemStack(1);
				break;
			case STEEL:
				rt = RawMaterialItemRegistry.instance().steel1.getItemStack(1);
				break;
			case STEEL2:
				rt = RawMaterialItemRegistry.instance().steel2.getItemStack(1);
				break;
			case METEORITE:
				rt = RawMaterialItemRegistry.instance().meteorite.getItemStack(1);
				break;
			default:
				break;

			}
			return rt;
		}
		public ItemStack getStack(int amount){
			return new ItemStack(UnsagaBlockRegistry.instance().stonesAndMetals,amount,this.getMeta());
		}
		public static EnumType fromMeta(int meta){
			return HSLibs.fromMeta(EnumType.values(), meta);
		}
	}
}
