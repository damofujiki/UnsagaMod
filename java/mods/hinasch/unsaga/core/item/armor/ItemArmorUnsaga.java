//package mods.hinasch.unsaga.core.item.armor;
//
//import java.util.List;
//
//import mods.hinasch.lib.iface.ICustomModel;
//import mods.hinasch.lib.primitive.FunctionCall;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.core.client.model.ModelArmorColored;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.init.UnsagaMaterial.PairArmorTexture;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemArmor;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemArmorUnsaga extends ItemArmor implements IItemColor,ICustomModel{
//
//	protected ComponentUnsagaTool component;
//	protected final ModelArmorColored modelBiped;
//	protected final ModelArmorColored modelBiped2;
//
//
//	/** アーマーテクスチャへのパス*/
//	protected static final String PATH = UnsagaMod.MODID+":textures/models/armor/";
//	protected static PairArmorTexture TEXTURE_DEFAULT = new PairArmorTexture("armor", "armor2");
//
//	public static int getRenderIndex(UnsagaMaterial mat){
//		if(mat.isChild()){
//			if(mat.getParentMaterial()==UnsagaMod.materials.cloth){
//				return 0;
//			}
//		}
//		return mat==UnsagaMod.materials.liveSilk ? 0 : 1;
//
//	}
//
//	public ItemArmorUnsaga(UnsagaMaterial material, ToolCategory category) {
//		super(material.getArmorMaterial(), getRenderIndex(material), category.getEquipmentSlot());
//		this.component = new ComponentUnsagaTool(material,category).setMaxAbilitySize(2);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.component.addPropertyOverride(this);
//		modelBiped = new ModelArmorColored(0.5F);
//		modelBiped2 = new ModelArmorColored(1.0F);
//
//
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
//	public String getArmorTextureFilename(UnsagaMaterial mat){
//		return "armor";
//	}
//
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return component.getColorFromItemStack(stack, tintIndex);
//	}
//
//    @SideOnly(Side.CLIENT)
//    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default)
//    {
//    	modelBiped.setItemStack(itemStack).setParent(_default);
//    	modelBiped2.setItemStack(itemStack).setParent(_default);
//    	this.customModelHelper.getArmorModel(armorSlot == EntityEquipmentSlot.LEGS ? modelBiped.getParent() : modelBiped2.getParent(),entityLiving, itemStack, armorSlot);
//
//        return modelBiped;
//    }
//
//    @Override
//    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
//    {
//		String suffix = ".png";//type!= null ? "_overlay.png" : ".png";
//		ItemArmorUnsaga armorunsaga = (ItemArmorUnsaga)stack.getItem();
//		final ToolCategory unsagatype = this.component.getCategory();
//		final UnsagaMaterial mate = AbilityHelper.hasCapability(stack) ? AbilityHelper.getCapability(stack).getUnsagaMaterial() : null;
//
//		final PairArmorTexture pairTexture = FunctionCall.supplier(() -> {
//			if(mate!=null && mate.getSpecialArmorTexture(unsagatype).isPresent()){
//				return mate.getSpecialArmorTexture(unsagatype).get();
//
//			}
//			return TEXTURE_DEFAULT;
//		}).get();
//
//
//
//
//
//		if(unsagatype==ToolCategory.HELMET || unsagatype==ToolCategory.BOOTS || unsagatype==ToolCategory.ARMOR)
//		{
//			return PATH + pairTexture.first() + suffix;
//		}
//		if(unsagatype==ToolCategory.LEGGINS)
//		{
//			return PATH +pairTexture.second() + suffix;
//		}
//		UnsagaMod.logger.trace("Unknown ArmorType???");
//		return pairTexture.first() + suffix;
//	}
//
//}
