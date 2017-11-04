//package mods.hinasch.unsaga.core.item.armor;
//
//import java.util.List;
//
//import mods.hinasch.lib.item.ItemPropertyGetterWrapper;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.capability.IUnsagaPropertyItem;
//import mods.hinasch.unsaga.core.creativetab.CreativeTabsUnsaga;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.IComponentDisplayInfo;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import mods.hinasch.unsaga.util.UnsagaTextFormatting;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemShield;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.translation.I18n;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemShieldUnsaga extends ItemShield implements IItemColor{
//
//	public static final int SHIELD_PER = 3;
//	/**
//	 * 盾の値が参照するarmorMAtaerialの値
//	 * @return
//	 */
//	public static EntityEquipmentSlot getReferenceArmor(){
//		return EntityEquipmentSlot.HEAD;
//	}
//	protected ComponentUnsagaTool component;
//	public ItemShieldUnsaga(UnsagaMaterial material){
//		this.setMaxStackSize(1);
//		this.addPropertyOverride(new ResourceLocation("blocking"),
//				ItemPropertyGetterWrapper.of((stack,world,entityIn)->entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F));
//
//		this.component = new ComponentUnsagaTool(material,ToolCategory.SHIELD).setMaxAbilitySize(2);
//		this.component.addDisplayInfoComponents(DISPLAY_SHIELD);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.component.addPropertyOverride(this);
//		this.setMaxDamage(this.component.getMaterial().getToolMaterial().getMaxUses());
//	}
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
//	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
//
//	@Override
//	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//	{
//		return component.initWeaponCapabilities(stack, nbt);
//	}
//
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component.getColorFromItemStack(stack, tintIndex);
//	}
//
//	@Override
//	public String getItemStackDisplayName(ItemStack stack)
//	{
//		return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
//	}
//
//	@SideOnly(Side.CLIENT)
//	public CreativeTabs getCreativeTab()
//	{
//		return CreativeTabsUnsaga.tabUnsaga;
//	}
//
//
//	public static final IComponentDisplayInfo DISPLAY_SHIELD = new IComponentDisplayInfo(){
//
//		@Override
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			return is!=null && is.getItem() instanceof ItemShieldUnsaga;
//		}
//
//		@Override
//		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//			ItemShieldUnsaga shield = (ItemShieldUnsaga) is.getItem();
//			int var1 = capa.getUnsagaMaterial().getArmorMaterial().getDamageReductionAmount(ItemShieldUnsaga.getReferenceArmor());
//			dispList.add(UnsagaTextFormatting.POSITIVE+"Blocking Reduction +"+ItemShieldUnsaga.SHIELD_PER*var1+"%");
//
//		}
//	};
//}
