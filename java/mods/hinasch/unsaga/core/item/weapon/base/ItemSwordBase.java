//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//
//import com.google.common.collect.Multimap;
//
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.nbt.NBTTagCompound;
//
//public class ItemSwordBase extends ItemSword implements IComponentUnsagaTool,IItemColor,IUnsagaDamageSource{
//
//    private final float attackDamage;
//	protected UnsagaMaterial uns;
//	protected ComponentUnsagaTool component;
//	private double baseSpeed = -2.4000000953674316D;
//
//	public ItemSwordBase(UnsagaMaterial material) {
//		super(material.getToolMaterial());
//		this.attackDamage = 4.0F + material.getToolMaterial().getDamageVsEntity();
//		this.uns = material;
//		this.component = new ComponentUnsagaTool(material,ToolCategory.SWORD);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.component.addPropertyOverride(this);
//
//	}
//
//	@Override
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
//	@Override
//    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
//    {
//        return this.component.getItemAttributeModifiers(slot, attackDamage, baseSpeed, stack, ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER);
//
//    }
//
//
//	@Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
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
////    @Override
////    public EnumAction getItemUseAction(ItemStack par1ItemStack)
////    {
////    	if(UtilUnsagaTool.getCurrentWeight(par1ItemStack)>5){
////    		return EnumAction.NONE;
////    	}
////        return EnumAction.BLOCK;
////    }
//
//
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return component.getColorFromItemStack(stack, tintIndex);
//	}
//
//	@Override
//	public DamageSourceSupplier getUnsagaDamageSource() {
//		// TODO 自動生成されたメソッド・スタブ
//		return DamageHelper.DEFAULT_SWORD;
//	}
//
//	@Override
//	public void setUnsagaDamageSourceSupplier(DamageSourceSupplier par1) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public ComponentUnsagaTool getToolComponent() {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component;
//	}
//}
