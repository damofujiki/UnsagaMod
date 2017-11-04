//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//import java.util.Random;
//
//import mods.hinasch.lib.core.HSLibEvents;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.IComponentDisplayInfo;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.ILivingHurtEventUnsaga;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.Items;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemArrow;
//import net.minecraft.item.ItemBow;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.stats.StatList;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//public class ItemBowBase extends ItemBow implements IItemColor{
//
//	protected ComponentUnsagaTool component;
//	protected ComponentArrowFinder arrowFinder;
//	public ItemBowBase(UnsagaMaterial material){
//
//		this.component = new ComponentUnsagaTool(material,ToolCategory.BOW);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.component.addDisplayInfoComponents(DISPLAY_BOW);
//        this.component.addPropertyOverride(this);
//		this.arrowFinder = new ComponentArrowFinder();
//	}
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return component.getColorFromItemStack(stack, tintIndex);
//	}
//
//	@Override
//    public int getItemEnchantability()
//    {
//        return this.component.getMaterial().getToolMaterial().getEnchantability();
//    }
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
//	public static class ComponentArrowFinder{
//	    public boolean isItemArrow(ItemStack stack)
//	    {
//	        return this.func_185058_h_(stack);
//	    }
//
//	    public ItemStack findInventoryArrow(EntityPlayer player)
//	    {
//	        if (this.isItemArrow(player.getHeldItem(EnumHand.OFF_HAND)))
//	        {
//	            return player.getHeldItem(EnumHand.OFF_HAND);
//	        }
//	        else if (this.isItemArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
//	        {
//	            return player.getHeldItem(EnumHand.MAIN_HAND);
//	        }
//	        else
//	        {
//	            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
//	            {
//	                ItemStack itemstack = player.inventory.getStackInSlot(i);
//
//	                if (this.isItemArrow(itemstack))
//	                {
//	                    return itemstack;
//	                }
//	            }
//
//	            return null;
//	        }
//	    }
//
//	    public boolean func_185058_h_(ItemStack stack)
//	    {
//	        return stack != null && stack.getItem() instanceof ItemArrow;
//	    }
//	}
////    /**
////     * クッションメソッド
////     * @param stack
////     * @return
////     */
////    protected boolean isItemArrow(ItemStack stack)
////    {
////        return this.func_185058_h_(stack);
////    }
////
////    protected ItemStack findInventoryArrow(EntityPlayer player)
////    {
////        if (this.isItemArrow(player.getHeldItem(EnumHand.OFF_HAND)))
////        {
////            return player.getHeldItem(EnumHand.OFF_HAND);
////        }
////        else if (this.isItemArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
////        {
////            return player.getHeldItem(EnumHand.MAIN_HAND);
////        }
////        else
////        {
////            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
////            {
////                ItemStack itemstack = player.inventory.getStackInSlot(i);
////
////                if (this.isItemArrow(itemstack))
////                {
////                    return itemstack;
////                }
////            }
////
////            return null;
////        }
////    }
//
//    /**
//     * How long it takes to use or consume an item
//     */
//    @Override
//    public int getMaxItemUseDuration(ItemStack par1ItemStack)
//    {
//        return 72000;
//    }
//
//	@Override
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
//    /**
//     * returns the action that specifies what animation to play when the items is being used
//     */
//    @Override
//    public EnumAction getItemUseAction(ItemStack par1ItemStack)
//    {
//        return EnumAction.BOW;
//    }
//
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
//    {
//        boolean flag = this.arrowFinder.findInventoryArrow(playerIn) != null;
//
//        ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
//        if (ret != null) return ret;
//
//        if (!playerIn.capabilities.isCreativeMode && !flag)
//        {
//            return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn);
//        }
//        else
//        {
//            playerIn.setActiveHand(hand);
//            return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
//        }
//    }
//
//    public static float usingDurationToDrawPower(int p_185059_0_)
//    {
//        float f = (float)p_185059_0_ / 20.0F;
//        f = (f * f + f * 2.0F) / 3.0F;
//
//        if (f > 1.0F)
//        {
//            f = 1.0F;
//        }
//
//        return f;
//    }
//
//    @Override
//    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
//    {
//        if (entityLiving instanceof EntityPlayer)
//        {
//            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
//            boolean flagCreativeorInfinity = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
//            ItemStack itemstack = this.arrowFinder.findInventoryArrow(entityplayer);
//
//            int i = this.getMaxItemUseDuration(stack) - timeLeft;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, itemstack != null || flagCreativeorInfinity);
//            if (i < 0) return;
//
//            if (itemstack != null || flagCreativeorInfinity)
//            {
//                if (itemstack == null)
//                {
//                    itemstack = new ItemStack(Items.ARROW);
//                }
//
//                float f = usingDurationToDrawPower(i);
//
//                if ((double)f >= 0.1D)
//                {
//                    boolean foundArrow = flagCreativeorInfinity && itemstack.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.
//
//                    if (!worldIn.isRemote)
//                    {
//
//                        ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW));
//                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
//                        entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
//
//                        this.postSuccessFoundArrow(itemstack, worldIn, entityLiving, timeLeft);
//                        if (f == 1.0F)
//                        {
//                            entityarrow.setIsCritical(true);
//                        }
//
//                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
//
//                        if (j > 0)
//                        {
//                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
//                        }
//
//                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
//
//                        if (k > 0)
//                        {
//                            entityarrow.setKnockbackStrength(k);
//                        }
//
//                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0)
//                        {
//                            entityarrow.setFire(100);
//                        }
//
//                        stack.damageItem(1, entityplayer);
//
//                        if (foundArrow)
//                        {
//                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
//                        }
//
//                        worldIn.spawnEntityInWorld(entityarrow);
//                    }
//
//                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//
//                    if (!foundArrow)
//                    {
//                        --itemstack.stackSize;
//
//                        if (itemstack.stackSize == 0)
//                        {
//                            entityplayer.inventory.deleteStack(itemstack);
//                        }
//                    }
//
//                    entityplayer.addStat(StatList.getObjectUseStats(this));
//                }
//            }
//        }
//    }
//
//    public void postSuccessFoundArrow(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft){
//
//    }
//
//    public static float getModifiedBowDamage(float base,int modifier,Random rand){
//
//    	return DamageHelper.calcReduceAmount(base, 8, 16, modifier, rand);
//    }
//    public static final IComponentDisplayInfo DISPLAY_BOW = new IComponentDisplayInfo(){
//
//		@Override
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			return is!=null && is.getItem() instanceof ItemBowBase;
//		}
//
//		@Override
//		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			if(AbilityHelper.hasCapability(is)){
//				UnsagaMaterial mate = AbilityHelper.getCapability(is).getUnsagaMaterial();
//				String mes = HSLibs.translateKey("tips.bow.modifier",mate.getBowModifier());
//				dispList.add(mes);
//			}
//		}
//	};
//
//	public static void registerEvents(){
//		HSLibEvents.livingHurt.getEventsMiddle().add(new ILivingHurtEventUnsaga(){
//
//			@Override
//			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//				if(dsu.getEntity() instanceof EntityLivingBase){
//					EntityLivingBase attacker = (EntityLivingBase) dsu.getEntity();
//					return dsu.getSourceOfDamage() instanceof EntityArrow &&attacker.getHeldItemMainhand()!=null && attacker.getHeldItemMainhand().getItem() instanceof ItemBowBase;
//				}
//				return false;
//			}
//
//			@Override
//			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//				EntityLivingBase shooter = (EntityLivingBase) dsu.getEntity();
//				float modifier = ((ItemBowBase)shooter.getHeldItemMainhand().getItem()).component.getMaterial().getBowModifier();
////				UnsagaMod.logger.trace(getName(), e.getAmount(),modifier);
//				e.setAmount(e.getAmount() + modifier);
////				UnsagaMod.logger.trace(getName(), e.getAmount(),modifier);
//				return dsu;
//			}
//
//			@Override
//			public String getName() {
//				// TODO 自動生成されたメソッド・スタブ
//				return "bow modifier";
//			}}
//	);
//	}
//}
