package mods.hinasch.unsaga.common;

import java.util.List;
import java.util.Optional;

import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.ability.IAbilitySelector;
import mods.hinasch.unsaga.ability.specialmove.SpecialMove;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import mods.hinasch.unsaga.util.ToolCategory;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBowUnsaga extends ItemBow implements IItemColor,IAbilitySelector,IUnsagaMaterialSelector{


	ComponentUnsagaWeapon component;
	public ItemBowUnsaga(){

		super();
		this.component = new ComponentUnsagaWeapon(ToolCategory.BOW);
		this.setCreativeTab(CreativeTabs.COMBAT);
		this.component.addPropertyOverrides(this);
	}

	@Override
	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
		return this.component.getColorFromItemstack(stack, tintIndex);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
	{
		this.component.addInformation(stack, playerIn, tooltip, advanced);
		tooltip.add(UnsagaTextFormatting.POSITIVE+"Bow Power +"+this.getDamageModifier(stack));
	}

    private ItemStack findAmmo(EntityPlayer player)
    {
        if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
        	InventoryHandler inv = new InventoryHandler(player.inventory);
        	Optional<ItemStack> arrow = inv.toStream(0,player.inventory.getSizeInventory())
        			.map(in -> in.getStack()).filter(is -> ItemUtil.isItemStackPresent(is) && this.isArrow(is)).findFirst();
            return arrow.isPresent() ? arrow.get() : null;
        }
    }

    public static float getDamageModifier(ItemStack is){

    	float at = UnsagaToolUtil.getMaterial(is).getToolMaterial().getDamageVsEntity() - 1.0F;
    	at = MathHelper.clamp_float(at, 0, 50.0F);
    	return at;
    }
	@Override
	public int getItemEnchantability(ItemStack is)
	{
		return UnsagaToolUtil.getItemEnchantability(is);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int)((float)this.component.getMaxDamage(stack) * 0.8F);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		this.component.getSubItems(par1, par2CreativeTabs, par3List);
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return this.component.getUnlocalizedName(par1ItemStack);
	}

//	@Override
//	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//	{
//		return this.component.initCapabilities(stack, nbt);
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	protected boolean hasSpecialMoveType(ItemStack is,InvokeType type){
		if(AbilityAPI.getLearnedSpecialMove(is).isPresent()){
			if(AbilityAPI.getLearnedSpecialMove(is).get().getAction().getInvokeTypes().contains(type)){
				return true;
			}
		}
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		if(this.hasSpecialMoveType(itemStackIn, InvokeType.RIGHTCLICK) && playerIn.isSneaking()){
			SpecialMove move = AbilityAPI.getLearnedSpecialMove(itemStackIn).get();
			SpecialMoveInvoker invoker = new SpecialMoveInvoker(worldIn, playerIn, move);
			invoker.setArticle(itemStackIn);
			invoker.setInvokeType(InvokeType.RIGHTCLICK);
			invoker.invoke();
			return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		}
		return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
	}

	public static void setArrowProperties(EntityLivingBase entityplayer,ItemStack bowStack,EntityArrow entityarrow,float charge,boolean isInfinity){
        if (charge == 1.0F)
        {
            entityarrow.setIsCritical(true);
        }

        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bowStack);

        if (j > 0)
        {
            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D + (double)getDamageModifier(bowStack));
        }

        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bowStack);

        if (k > 0)
        {
            entityarrow.setKnockbackStrength(k);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bowStack) > 0)
        {
            entityarrow.setFire(100);
        }

        bowStack.damageItem(1, entityplayer);

        if (isInfinity)
        {
            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
        }
	}
	@Override
    public void onPlayerStoppedUsing(ItemStack bowStack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean isInfinite = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowStack) > 0;
            ItemStack arrowStack = this.findAmmo(entityplayer);
            UnsagaMod.logger.trace("aarrow", timeLeft);
            int i = this.getMaxItemUseDuration(bowStack) - timeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bowStack, worldIn, (EntityPlayer)entityLiving, i, arrowStack != null || isInfinite);
            if (i < 0) return;

            if (arrowStack != null || isInfinite)
            {
                if (arrowStack == null)
                {
                    arrowStack = new ItemStack(Items.ARROW);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || (arrowStack.getItem() instanceof ItemArrow ? ((ItemArrow)arrowStack.getItem()).isInfinite(arrowStack, bowStack, entityplayer) : false);

                    if (WorldHelper.isServer(worldIn))
                    {
                        ItemArrow itemarrow = (ItemArrow)((ItemArrow)(arrowStack.getItem() instanceof ItemArrow ? arrowStack.getItem() : Items.ARROW));
                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, arrowStack, entityplayer);
                        entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);

                        this.setArrowProperties(entityplayer, bowStack, entityarrow, f, flag1);

                		if(this.hasSpecialMoveType(bowStack, InvokeType.BOW) && entityLiving.isSneaking()){
                			SpecialMove move = AbilityAPI.getLearnedSpecialMove(bowStack).get();
                			SpecialMoveInvoker invoker = new SpecialMoveInvoker(worldIn, entityLiving, move);
                			invoker.setArticle(bowStack);
                			invoker.setArrowComponent(entityarrow,arrowStack);
                			invoker.setChargedTime((int) (f*100));
                			invoker.setInvokeType(InvokeType.BOW);
                			invoker.invoke();
                		}else{
                			worldIn.spawnEntityInWorld(entityarrow);
                		}

                    }

                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    processDecreaseArrow(entityplayer, arrowStack, flag1);

                    entityplayer.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
    }

	public static void processDecreaseArrow(EntityPlayer entityplayer,ItemStack arrowStack,boolean flag1){
        if (!flag1)
        {
           ItemUtil.decrStackSize(arrowStack, 1);

            if (arrowStack.stackSize == 0)
            {
                entityplayer.inventory.deleteStack(arrowStack);
            }
        }
	}
	@Override
	public int getMaxAbilitySize(){
		return 4;
	}

	@Override
	public ToolCategory getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return ToolCategory.BOW;
	}
}
