//package mods.hinasch.unsaga.core.item.weapon;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect.Type;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemAxeBase;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//
//public class ItemAxeUnsaga extends ItemAxeBase{
//
//
//	Waza tomahawk = WazaRegistry.instance().tomahawk;
//	Waza fujiView = WazaRegistry.instance().fujiView;
//	Waza skullCrasherAxe = WazaRegistry.instance().skullCrashAxe;
//
//	public ItemAxeUnsaga(UnsagaMaterial uns) {
//		super(uns);
//        this.component.getBowActionMap().put(tomahawk, 72000);
//        this.component.getBowActionMap().put(fujiView, 72000);
//        this.component.getBowActionMap().put(skullCrasherAxe, 72000);
//	}
//
//
//	@Override
//	public EnumAction getItemUseAction(ItemStack stack)
//	{
//
//		return this.component.getItemUseAction(stack);
//	}
//
//	@Override
//	public int getMaxItemUseDuration(ItemStack stack)
//	{
//		return this.component.getMaxItemUseDuration(stack);
//	}
//
//
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//	{
//		ContainerItemInteraction.EntityClick container = new ContainerItemInteraction.EntityClick(stack, player.worldObj, player, entity);
//		ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.ENTITY_LEFTCLICK);
//		if(result.getType()==EnumActionResult.SUCCESS){
//			result.getResult().perform();
//			return true;
//		}
//		return false;
//
//	}
//
//	public void tomahawk(World worldIn,ItemStack itemstack,EntityLivingBase entityLiving,float f){
//		//        boolean flag1 = itemstack.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.
//
//		if (!worldIn.isRemote)
//		{
//			WazaPerformer invoker = new WazaPerformer(worldIn, entityLiving,tomahawk , itemstack);
//			//            ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.arrow));
//			EntityFlyingAxe entityAxe = new EntityFlyingAxe(worldIn, entityLiving, itemstack);//itemarrow.makeTippedArrow(worldIn, itemstack, entityplayer);
//			entityAxe.setHeadingFromThrower(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, f * 3.0F, 1.0F);
//			entityAxe.setDamage(invoker.getChargeAppliedAttackDamage(true, f));
//			entityAxe.copyAxeItemStackAndDeleteHeld(itemstack);
//			entityAxe.setLPDamage(tomahawk.getAttackDamage().lp());
//			if (f == 1.0F)
//			{
//				//                entityAxe.setIsCritical(true);
//			}
//
//			int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, itemstack);
//
//			if (j > 0)
//			{
//
//				entityAxe.setDamage((float)(entityAxe.getDamage() + (double)j * 0.5D + 0.5D));
//			}
//
//			int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, itemstack);
//
//			if (k > 0)
//			{
//				//                entityAxe.setKnockbackStrength(k);
//			}
//
//			if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, itemstack) > 0)
//			{
//				entityAxe.setFire(100);
//			}
//
//			itemstack.damageItem(1, entityLiving);
//
//			//            if (flag1)
//			//            {
//			////                entityAxe.canBePickedUp = EntityArrow.PickupStatus.CREATIVE_ONLY;
//			//            }
//
//			worldIn.spawnEntityInWorld(entityAxe);
//		}
//
//		worldIn.playSound((EntityPlayer)null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (worldIn.rand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//		//
//		//        if (!flag1)
//		//        {
//		//            --itemstack.stackSize;
//		//
//		//            if (itemstack.stackSize == 0)
//		//            {
//		//                entityplayer.inventory.deleteStack(itemstack);
//		//            }
//		//        }
//
//
//
//	}
//	@Override
//    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
//    {
//
//
//    	ActionResult<Float> resultBowAction = this.component.onPlayerStoppedUsing(this, stack, worldIn, entityLiving, timeLeft);
//
//    	if(resultBowAction.getType()==EnumActionResult.SUCCESS){
//    		float charge = resultBowAction.getResult();
//    		ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(stack, worldIn, entityLiving, charge, ClientHelper.getMouseOverLongReach().entityHit);
//    		ActionResult<WazaPerformer> resultFind = this.component.findSkillInvoker(container, Type.STOPPED_USING);
//    		if(resultFind.getType()==EnumActionResult.SUCCESS){
//
//    			if(resultFind.getResult().getWaza()==tomahawk){
//    				this.tomahawk(worldIn, stack, entityLiving, charge);
//    			}else{
//        			if(worldIn.isRemote){
//        				UnsagaMod.packetDispatcher.sendToServer(PacketEntityInteractionWithItem.create(charge, ClientHelper.getMouseOverLongReach()));
//        			}
//
//        			resultFind.getResult().perform();
//    			}
//
//
//
//    		}
//
//    	}
//    }
//}
