//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//
//import com.google.common.collect.Multimap;
//
//import mods.hinasch.lib.debuff.DebuffHelper;
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.IComponentDisplayInfo;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.debuff.DebuffRegistry;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.lp.LPLogicManager;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.passive.EntitySheep;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Items;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumHand;
//
//public class ItemKnifeBase extends ItemSword implements IComponentUnsagaTool,IItemColor,IUnsagaDamageSource{
//
//	protected ComponentUnsagaTool component;
//	protected final float weaponDamage;
//	public static final IComponentDisplayInfo DISPLAY_KNIFE_INFO = new IComponentDisplayInfo(){
//
//		@Override
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			return is!=null && is.getItem() instanceof ItemKnifeBase;
//		}
//
//		@Override
//		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			dispList.add("Wool Cut:Shift And Attack");
//		}
//	};
//
//
//	public ItemKnifeBase(UnsagaMaterial material) {
//		super(material.getToolMaterial());
//		this.component = new ComponentUnsagaTool(material,ToolCategory.KNIFE);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.component.addDisplayInfoComponents(DISPLAY_KNIFE_INFO);
//		this.weaponDamage = 1.0F + component.getMaterial().getToolMaterial().getDamageVsEntity();
//        this.component.addPropertyOverride(this);
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
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
//
//	@Override
//	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
//
//	@Override
//    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
//    {
//        return this.component.getItemAttributeModifiers(slot, weaponDamage, -2.065D, stack, ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER);
//
//    }
//
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//	{
//
//		if(entity instanceof EntitySheep){
//			if(player.isSneaking()){
//				EntitySheep sheep = (EntitySheep) entity;
//				Items.SHEARS.itemInteractionForEntity(stack, player, sheep, EnumHand.MAIN_HAND);
//				stack.damageItem(2, player);
//				return true;
//			}
//
//		}
//		return false;
//	}
//
//	@Override
//    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase living, EntityLivingBase p_77644_3_)
//    {
//		super.hitEntity(p_77644_1_, living, p_77644_3_);
//		if(DebuffHelper.hasDebuff(living, DebuffRegistry.getInstance().bloodyMary)){
//			living.hurtResistantTime -= 5;
//			if(LPLogicManager.hasCapability(living)){
//				LPLogicManager.getCapability(living).setHurtInterval(0);
//			}
//		}
//
//        return true;
//    }
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
//		return DamageHelper.DEFAULT_KNIFE;
//	}
//
//
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
//
//}
