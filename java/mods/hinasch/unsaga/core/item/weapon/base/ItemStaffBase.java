//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//import java.util.Set;
//
//import com.google.common.collect.Multimap;
//import com.google.common.collect.Sets;
//
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Blocks;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class ItemStaffBase extends ItemSword implements IComponentUnsagaTool,IItemColor,IUnsagaDamageSource{
//
//	private static final Set blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.COBBLESTONE, Blocks.DOUBLE_STONE_SLAB, Blocks.STONE_SLAB, Blocks.STONE, Blocks.SANDSTONE, Blocks.MOSSY_COBBLESTONE, Blocks.IRON_ORE, Blocks.IRON_BLOCK, Blocks.COAL_ORE, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, Blocks.ICE, Blocks.NETHERRACK, Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Blocks.REDSTONE_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.RAIL, Blocks.DETECTOR_RAIL, Blocks.GOLDEN_RAIL, Blocks.ACTIVATOR_RAIL});
//
//	public final ToolMaterial toolMaterial;
//    public float efficiencyOnProperMaterial = 4.0F;
//	protected final float weaponDamage;
//	protected ComponentUnsagaTool component;
//	public ItemStaffBase(UnsagaMaterial material) {
//		super(material.getToolMaterial());
//
//		this.component = new ComponentUnsagaTool(material,ToolCategory.STAFF);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//
//
//		this.toolMaterial = component.getMaterial().getToolMaterial();
//		this.weaponDamage = 3.0F + this.toolMaterial.getDamageVsEntity();
//		this.efficiencyOnProperMaterial = component.getMaterial().getToolMaterial().getEfficiencyOnProperMaterial();
//		this.setMaxDamage((int)((float)component.getMaterial().getToolMaterial().getMaxUses() * 0.7F));
//		this.toolClass = "pickaxe";
//		this.component.addPropertyOverride(this);
//	}
//
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return component.getColorFromItemStack(stack, tintIndex);
//	}
//
//	@Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
//
//	@Override
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
//    private String toolClass;
//    @Override
//    public int getHarvestLevel(ItemStack stack, String toolClass)
//    {
//        int level = super.getHarvestLevel(stack, toolClass);
//        if (level == -1 && toolClass != null && toolClass.equals(this.toolClass))
//        {
//            return this.toolMaterial.getHarvestLevel();
//        }
//        else
//        {
//            return level;
//        }
//    }
//
//    @Override
//    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState blockIn, BlockPos pos, EntityLivingBase entityLiving)
//    {
//        if ((double)blockIn.getBlockHardness(worldIn, pos) != 0.0D)
//        {
//        	stack.damageItem(3, entityLiving);
//        }
//
//        return true;
//    }
//
//	@Override
//	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
//	{
//		if(MaterialAnalyzer.hasCapability(par2ItemStack) && MaterialAnalyzer.hasCapability(par1ItemStack)){
//			if(MaterialAnalyzer.getCapability(par2ItemStack).getMaterial().isPresent() && MaterialAnalyzer.getCapability(par1ItemStack).getMaterial().isPresent()){
//				return MaterialAnalyzer.getCapability(par2ItemStack).getMaterial().get()==MaterialAnalyzer.getCapability(par1ItemStack).getMaterial().get();
//			}
//		}
//
//		return false;
//	}
//
//    @Override
//    public Set<String> getToolClasses(ItemStack stack)
//    {
//        return toolClass != null ? com.google.common.collect.ImmutableSet.of(toolClass) : super.getToolClasses(stack);
//    }
//
//
//
//    @Override
//    public boolean canHarvestBlock(IBlockState state)
//    {
//    	Block blockIn = state.getBlock();
//        return blockIn == Blocks.OBSIDIAN ? this.toolMaterial.getHarvestLevel() == 3 : (blockIn != Blocks.DIAMOND_BLOCK && blockIn != Blocks.DIAMOND_ORE ? (blockIn != Blocks.EMERALD_ORE && blockIn != Blocks.EMERALD_BLOCK ? (blockIn != Blocks.GOLD_BLOCK && blockIn != Blocks.GOLD_ORE ? (blockIn != Blocks.IRON_BLOCK && blockIn != Blocks.IRON_ORE ? (blockIn != Blocks.LAPIS_BLOCK && blockIn != Blocks.LAPIS_ORE ? (blockIn != Blocks.REDSTONE_ORE && blockIn != Blocks.LIT_REDSTONE_ORE ? (blockIn.getMaterial(state) == Material.ROCK ? true : (blockIn.getMaterial(state) == Material.IRON ? true : blockIn.getMaterial(state) == Material.ANVIL)) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 1) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2) : this.toolMaterial.getHarvestLevel() >= 2);
//    }
//
//    @Override
//    public float getStrVsBlock(ItemStack stack, IBlockState state)
//    {
//        return this.blocksEffectiveAgainst.contains(state.getBlock()) ? this.efficiencyOnProperMaterial : 1.0F;
//
//    }
//
//	@Override
//    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
//    {
//        return this.component.getItemAttributeModifiers(slot, weaponDamage, -2.6D, stack, ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER);
//
//    }
//
//	@Override
//	public DamageSourceSupplier getUnsagaDamageSource() {
//		// TODO 自動生成されたメソッド・スタブ
//		return DamageHelper.DEFAULT_STAFF;
//	}
//
//
//
//	@Override
//	public void setUnsagaDamageSourceSupplier(DamageSourceSupplier par1) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
////	@Override
////    @SideOnly(Side.CLIENT)
////    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
////    {
////        Set<UnsagaMaterial> set = UnsagaMod.materials.toolAvailableMapGroup.getAvailableAsFlatSet(component.getCategory());
////        for(UnsagaMaterial uns:set){
////        	ItemStack newStack = new ItemStack(itemIn,1);
////        	if(AbilityHelperNew.hasCapabilityLearning(newStack)){
////        		IUnsagaPropertyItem capa = AbilityHelperNew.getCapability(newStack);
////        		capa.setUnsagaMaterial(uns);
////        		capa.setWeight(uns.getWeight());
////        	}
////        	subItems.add(newStack);
////        }
////    }
//	@Override
//	public ComponentUnsagaTool getToolComponent() {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component;
//	}
//}
