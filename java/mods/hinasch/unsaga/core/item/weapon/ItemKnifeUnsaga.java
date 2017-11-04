//package mods.hinasch.unsaga.core.item.weapon;
//
//import java.util.UUID;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.core.HSLib;
//import mods.hinasch.lib.debuff.DebuffHelper;
//import mods.hinasch.lib.network.PacketUtil;
//import mods.hinasch.lib.particle.PacketParticle;
//import mods.hinasch.lib.util.Statics;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect.Type;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.core.entity.projectile.EntityThrowingKnife;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemKnifeBase;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
//import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumParticleTypes;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//
//public class ItemKnifeUnsaga extends ItemKnifeBase{
//
//	Waza knifeThrow = WazaRegistry.instance().knifeThrow;
//	Waza stunner = WazaRegistry.instance().stunner;
//	Waza bloodyMary = WazaRegistry.instance().bloodyMary;
//	Waza lightningThrust = WazaRegistry.instance().lightningThrust;
//	Waza blitz = WazaRegistry.instance().blitz;
//	public ItemKnifeUnsaga(UnsagaMaterial material) {
//		super(material);
//		component.getBowActionMap().put(stunner, 72000);
//		component.getBowActionMap().put(bloodyMary, 72000);
//		component.getBowActionMap().put(knifeThrow, 72000);
//		component.getBowActionMap().put(lightningThrust, 72000);
////		component.getBowActionMap().put(blitz, 72000);
//		component.setBlockable(true);
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
//	public void throwKnife(World worldIn,ItemStack itemstack,EntityLivingBase entityLiving,float f){
//		//        boolean flag1 = itemstack.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.
//
//		if (!worldIn.isRemote)
//		{
//			WazaPerformer invoker = new WazaPerformer(worldIn, entityLiving,knifeThrow , itemstack);
//			//            ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.arrow));
//			EntityThrowingKnife entityKnife = new EntityThrowingKnife(worldIn, entityLiving, itemstack);//itemarrow.makeTippedArrow(worldIn, itemstack, entityplayer);
//			entityKnife.setHeadingFromThrower(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, f * 3.0F, 1.0F);
//			entityKnife.setDamage(invoker.getChargeAppliedAttackDamage(true, f));
//			entityKnife.copyAxeItemStackAndDeleteHeld(itemstack);
//			entityKnife.setLPDamage(knifeThrow.getAttackDamage().lp());
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
//				entityKnife.setDamage((float)(entityKnife.getDamage() + (double)j * 0.5D + 0.5D));
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
//				entityKnife.setFire(100);
//			}
//
//			itemstack.damageItem(1, entityLiving);
//
//			//            if (flag1)
//			//            {
//			////                entityAxe.canBePickedUp = EntityArrow.PickupStatus.CREATIVE_ONLY;
//			//            }
//
//			worldIn.spawnEntityInWorld(entityKnife);
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
//
//	@Override
//    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
//    {
//
//    	ActionResult<Float> resultBowAction = this.component.onPlayerStoppedUsing(this, stack, worldIn, entityLiving, timeLeft);
//
//    	if(resultBowAction.getType()==EnumActionResult.SUCCESS){
//    		float charge = resultBowAction.getResult();
//    		ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(stack, worldIn, entityLiving, charge, ClientHelper.getMouseOverLongReach().entityHit);
//    		ActionResult<WazaPerformer> resultFind = this.component.findSkillInvoker(container, Type.STOPPED_USING);
//    		if(resultFind.getType()==EnumActionResult.SUCCESS){
//       			UnsagaMod.logger.trace("result", resultFind);
//    			if(resultFind.getResult().getWaza()==knifeThrow){
//    				this.throwKnife(worldIn,stack,entityLiving,charge);
//    			}else{
//        			if(worldIn.isRemote){
//        				UnsagaMod.packetDispatcher.sendToServer(PacketEntityInteractionWithItem.create(charge, ClientHelper.getMouseOverLongReach()));
//        			}
//    			}
//
//
//
//    			resultFind.getResult().perform();
//
//    		}
//
//    	}
//    }
//
////	@Override
////    public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
////    {
////		if(hand==EnumHand.OFF_HAND && !this.component.isHeavy(par1ItemStack) && !par3EntityPlayer.isSneaking()){
////			if(LivingHelper.adapter.hasCapability(par3EntityPlayer)){
////				if(LivingHelper.adapter.getCapability(par3EntityPlayer).getWeaponGuardCooling()<=0){
////					LivingHelper.adapter.getCapability(par3EntityPlayer).resetWeaponGuardProgress();
////					par3EntityPlayer.swingArm(EnumHand.OFF_HAND);
////					return new ActionResult(EnumActionResult.SUCCESS,par1ItemStack);
////				}
////
////			}
////
////		}
////		if(par3EntityPlayer.isSneaking()){
////			if(this.component.hasBowActionWaza(par1ItemStack)){
////				par3EntityPlayer.setActiveHand(EnumHand.MAIN_HAND);
////				return new ActionResult(EnumActionResult.SUCCESS,par1ItemStack);
////			}
////			ContainerItemInteraction.RightClick container = new ContainerItemInteraction.RightClick(par1ItemStack, par2World, par3EntityPlayer, hand);
////			ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.RIGHTCLICK);
////			if(result.getType()==EnumActionResult.SUCCESS){
////				result.getResult().perform();
////				return new ActionResult(EnumActionResult.SUCCESS,par1ItemStack);
////			}
////		}
////
////		return new ActionResult(EnumActionResult.PASS,par1ItemStack);
////	}
//
//
//
//	public static final UUID MODIFIER = UUID.fromString("a9284922-e3cf-4867-b5ad-5e46659b6f40");
//	public static final AttributeModifier NO_KNOCKBACK = new AttributeModifier(MODIFIER, "modifier.unsaga.knockback", 1.0D, Statics.OPERATION_INCREMENT);
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//	{
//		if(DebuffHelper.hasDebuff(player, UnsagaMod.debuffs.bloodyMary) && entity instanceof EntityLivingBase){
//			EntityLivingBase living = (EntityLivingBase) entity;
//			if(living.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getModifier(NO_KNOCKBACK.getID())==null){
//				living.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(NO_KNOCKBACK);
//			}
//			if(player.getRNG().nextInt(3)==0){
////				PacketParticle pp = new PacketParticle(EnumParticleTypes.REDSTONE,living.getEntityId(),3);
//				HSLib.core().packetParticle.sendToAllAround(PacketParticle.create(XYZPos.createFrom(entity),EnumParticleTypes.REDSTONE, 10), PacketUtil.getTargetPointNear(entity));
//			}
//			entity.playSound(SoundEvents.ENTITY_IRONGOLEM_HURT, 1.0F, 1.0F / (player.worldObj.rand.nextFloat() * 0.4F + 1.2F) + 1.0F * 0.5F);
//			float at = (float)player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
//			entity.attackEntityFrom(DamageSourceUnsaga.create(player,bloodyMary.getAttackDamage().lp(), General.SPEAR), at + bloodyMary.getAttackDamage().hp());
//			return true;
//		}
//
//		ContainerItemInteraction.EntityClick container = new ContainerItemInteraction.EntityClick(stack, player.worldObj, player, entity);
//		ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.ENTITY_LEFTCLICK);
//		if(result.getType()==EnumActionResult.SUCCESS){
//			result.getResult().perform();
//			return true;
//		}
//		return false;
//
//	}
//}
