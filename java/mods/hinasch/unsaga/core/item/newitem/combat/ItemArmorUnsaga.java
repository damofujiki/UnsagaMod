package mods.hinasch.unsaga.core.item.newitem.combat;

import java.util.List;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsaga.ability.IAbilitySelector;
import mods.hinasch.unsaga.common.ComponentUnsagaWeapon;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorUnsaga extends ItemArmor implements IItemColor,IAbilitySelector,IUnsagaMaterialSelector{

	private static final int[] MAX_DAMAGE_ARRAY = new int[] {13, 15, 16, 11};

	public static final ArmorMaterial FAILED = EnumHelper.addArmorMaterial("failed", "", 2, new int[]{1,1,1,1}, 1, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
	private static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	ComponentUnsagaWeapon component;
	ToolCategory category;
	String categoryName;
    boolean isFailed = false;
	public ItemArmorUnsaga(ToolCategory category) {
		super(ArmorMaterial.IRON, 1, category.getEquipmentSlot());
		this.component = new ComponentUnsagaWeapon(category);
		this.component.addPropertyOverrides(this);
		this.category = category;
	}

//	@Override
//	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//	{
//		return this.component.initCapabilities(stack, nbt);
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		this.component.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
	{
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        double at = this.component.isFailed(stack) ? FAILED.getDamageReductionAmount(equipmentSlot) : this.getDamageReductionAmount(stack);
        double tough = this.component.isFailed(stack) ? FAILED.getToughness() : this.getToughness(stack);
        if (equipmentSlot == this.armorType)
        {
            multimap.put(SharedMonsterAttributes.ARMOR.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", (double)at, 0));
            multimap.put(SharedMonsterAttributes.ARMOR_TOUGHNESS.getAttributeUnlocalizedName(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor toughness", (double)tough, 0));
        }

        return multimap;
    }

	public int getDamageReductionAmount(ItemStack is){
		return this.component.getDamageReductionAmount(is, armorType);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return this.component.getColorFromItemstack(stack, tintIndex);
	}

	@Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }

	@Override
    public int getItemEnchantability(ItemStack is)
    {
        return this.component.getItemEnchantability(is);
    }

	@Override
	public int getMaxDamage(ItemStack stack)
	{

		return (int)this.component.getMaxDamage(stack);
	}
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{

		this.component.getSubItems(par1, par2CreativeTabs, par3List);


	}


	public float getToughness(ItemStack is){
		return this.component.getToughness(is);
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack armorStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		InventoryHandler inv = new InventoryHandler(playerIn.inventory);

        EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(armorStackIn);
        if(playerIn.isSneaking()){
    		if(inv.getFirstEmptyArmorSlot().isPresent()){
    			entityequipmentslot = inv.getFirstEmptyArmorSlot().get().getEquipmentSlot();
    		}
        }
        ItemStack itemstack = playerIn.getItemStackFromSlot(entityequipmentslot);

        if (itemstack != null)
        {
            playerIn.setItemStackToSlot(entityequipmentslot, armorStackIn.copy());
            ItemUtil.setStackSize(itemstack,0);
            return new ActionResult(EnumActionResult.SUCCESS, armorStackIn);
        }
        else
        {
            return new ActionResult(EnumActionResult.FAIL, armorStackIn);
        }
    }

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return this.component.getUnlocalizedName(par1ItemStack);
	}

	@Override
	public int getMaxAbilitySize() {
		// TODO 自動生成されたメソッド・スタブ
		return 4;
	}

	@Override
	public ToolCategory getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return this.category;
	}
}
