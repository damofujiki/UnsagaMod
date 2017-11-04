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
//import net.minecraft.item.ItemAxe;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//
//public class ItemAxeBase extends ItemAxe implements IItemColor,IUnsagaDamageSource,IComponentUnsagaTool{
//
//	protected UnsagaMaterial uns;
//	protected ComponentUnsagaTool component;
//	protected ItemAxeBase(final UnsagaMaterial uns) {
//
//        super(ToolMaterial.IRON);
//        this.uns = uns;
//        this.damageVsEntity = 3.0F + uns.getToolMaterial().getDamageVsEntity() + uns.getSuitedModifier(ToolCategory.AXE);
//        this.canRepair = true;
//        this.component = new ComponentUnsagaTool(uns,ToolCategory.AXE);
//        this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//        this.component.addPropertyOverride(this);
//        this.setMaxDamage(uns.getToolMaterial().getMaxUses());
//
//
//	}
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
//	@Override
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
////	@Override
////    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
////    {
////        return component.getItemAttributeModifiers(this.ATTACK_DAMAGE_MODIFIER, damageVsEntity);
////    }
//
//	@Override
//    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
//    {
//        return this.component.getItemAttributeModifiers(slot, damageVsEntity, -3.0D, stack, ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER);
//
//    }
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component.getColorFromItemStack(stack, tintIndex);
//	}
//	@Override
//	public DamageSourceSupplier getUnsagaDamageSource() {
//		// TODO 自動生成されたメソッド・スタブ
//		return DamageHelper.DEFAULT_AXE;
//	}
//	@Override
//	public void setUnsagaDamageSourceSupplier(DamageSourceSupplier par1) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//	@Override
//	public ComponentUnsagaTool getToolComponent() {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component;
//	}
//
////	@Override
////	public EnumAction getItemUseAction(ItemStack par1ItemStack)
////	{
////
////		if(AbilityHelperNew.hasAbilityFromItemStack(par1ItemStack,UnsagaMod.abilities.tomahawk)){
////
////			return EnumAction.BOW;
////		}
////		return EnumAction.NONE;
////	}
//}
