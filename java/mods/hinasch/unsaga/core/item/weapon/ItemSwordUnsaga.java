//package mods.hinasch.unsaga.core.item.weapon;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.debuff.DebuffHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect.Type;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.ability.wazaeffect.wazagroup.WazaGroupSword.KaleidoScope;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemSwordBase;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.debuff.DebuffRegistry;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.world.World;
//
//public class ItemSwordUnsaga extends ItemSwordBase{
//
//	Waza smash = WazaRegistry.instance().smash;
//	Waza kaleidoscope = WazaRegistry.instance().kaleidoscope;
//	Waza gust = WazaRegistry.instance().gust;
//	Waza vandalize = WazaRegistry.instance().vandalize;
//	Waza chargeBlade = WazaRegistry.instance().chargeBlade;
//	Waza twinBlade =WazaRegistry.instance().twinBlade;
//
//	public ItemSwordUnsaga(UnsagaMaterial material) {
//		super(material);
//		this.component.getBowActionMap().put(smash, 72000);
//		this.component.getBowActionMap().put(kaleidoscope, 72000);
//		this.component.getBowActionMap().put(gust, 72000);
//		this.component.getBowActionMap().put(vandalize, 72000);
//		this.component.getBowActionMap().put(twinBlade, 72000);
//		this.component.setBlockable(true);
//	}
//
//
//	@Override
//    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
//    {
//		ContainerItemInteraction.Swing container = new ContainerItemInteraction.Swing(stack, entityLiving.getEntityWorld(), entityLiving);
//		ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.SWING);
//		if(result.getType()==EnumActionResult.SUCCESS){
//			result.getResult().perform();
//			return true;
//		}
//        return false;
//    }
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
////	@Override
////    public ActionResult<ItemStack> onItemRightClick(ItemStack stackIn, World par2World, EntityPlayer playerIn, EnumHand hand)
////    {
////
////
////		if(hand==EnumHand.OFF_HAND && !this.component.isHeavy(stackIn) && !playerIn.isSneaking()){
////			if(LivingHelper.adapter.hasCapability(playerIn)){
////				if(LivingHelper.adapter.getCapability(playerIn).getWeaponGuardCooling()<=0){
////					LivingHelper.adapter.getCapability(playerIn).resetWeaponGuardProgress();
////					playerIn.swingArm(EnumHand.OFF_HAND);
////					return new ActionResult(EnumActionResult.SUCCESS,stackIn);
////				}
////
////			}
////
////		}
////		if(playerIn.isSneaking() || AbilityHelperNew.hasAbilityFromItemStack(stackIn, chargeBlade)){
////			if(component.hasBowActionWaza(stackIn)){
////				playerIn.setActiveHand(EnumHand.MAIN_HAND);
////				return new ActionResult(EnumActionResult.SUCCESS,stackIn);
////			}
////
////			ContainerItemInteraction.RightClick container = new ContainerItemInteraction.RightClick(stackIn, par2World, playerIn, hand);
////			ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.RIGHTCLICK);
////			if(result.getType()==EnumActionResult.SUCCESS){
////				result.getResult().perform();
////				return new ActionResult(EnumActionResult.SUCCESS,stackIn);
////			}
////		}
////
////
////		return new ActionResult(EnumActionResult.PASS,stackIn);
////	}
//
////	@Override
////    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
////    {
////		if(stack==null){
////			return EnumActionResult.PASS;
////		}
////
////
////
////		if(playerIn.isSneaking() ){
////			ContainerItemInteraction.Using container = new ContainerItemInteraction.Using(stack, worldIn, playerIn, pos, hand, facing, new XYZPos(hitX,hitY,hitZ));
////			ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.USE);
////			if(result.getType()==EnumActionResult.SUCCESS){
////				result.getResult().perform();
////				return EnumActionResult.SUCCESS;
////			}
////		}
////
////		return EnumActionResult.PASS;
////    }
//
//	@Override
//	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
//	{
//		if(DebuffHelper.hasDebuff(player, DebuffRegistry.getInstance().kaleidoscope)){
//			WazaPerformer invoker = new WazaPerformer(player.getEntityWorld(), player, kaleidoscope, stack);
//			invoker.setTarget(entity);
//			KaleidoScope effect = (KaleidoScope) invoker.getEffect();
//			effect.processKaleidoScope(invoker,false);
//			return true;
//		}
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
//    			UnsagaMod.logger.trace("result", resultFind);
//    			if(worldIn.isRemote){
//    				UnsagaMod.packetDispatcher.sendToServer(PacketEntityInteractionWithItem.create(charge, ClientHelper.getMouseOverLongReach()));
//    			}
//
//    			resultFind.getResult().perform();
//
//    		}
//
//    	}
//    }
//}
