//package mods.hinasch.unsaga.core.item.weapon;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect.Type;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemSpearBase;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.world.World;
//
//public class ItemSpearUnsaga extends ItemSpearBase{
//
//
//	Waza acupuncture = WazaRegistry.instance().acupuncture;
//	Waza aiming = WazaRegistry.instance().aiming;
//	Waza tripleSupremacy = WazaRegistry.instance().tripleSupremacy;
//	public ItemSpearUnsaga(UnsagaMaterial material) {
//		super(material);
//
//		this.component.getBowActionMap().put(acupuncture, 72000);
//		this.component.getBowActionMap().put(aiming, 72000);
//		this.component.getBowActionMap().put(tripleSupremacy, 72000);
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
////    public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
////    {
////
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
////
////		return new ActionResult(EnumActionResult.PASS,par1ItemStack);
////	}
//
////	@Override
////    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
////    {
////		if(stack==null){
////			return EnumActionResult.PASS;
////		}
////
////		if(playerIn.isSneaking()){
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
//
//    @Override
//    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityLivingBase entityLiving, int timeLeft)
//    {
//    	ActionResult<Float> resultBowAction = this.component.onPlayerStoppedUsing(this, par1ItemStack, par2World, entityLiving, timeLeft);
//
//    	if(resultBowAction.getType()==EnumActionResult.SUCCESS){
//    		float charge = resultBowAction.getResult();
//    		ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(par1ItemStack, par2World, entityLiving, charge, ClientHelper.getMouseOverLongReach().entityHit);
//    		ActionResult<WazaPerformer> resultFind = this.component.findSkillInvoker(container, Type.STOPPED_USING);
//    		if(resultFind.getType()==EnumActionResult.SUCCESS){
//    			UnsagaMod.logger.trace("result", resultFind);
//    			if(par2World.isRemote){
//    				UnsagaMod.packetDispatcher.sendToServer(PacketEntityInteractionWithItem.create(charge, ClientHelper.getMouseOverLongReach()));
//    			}
//
//    			resultFind.getResult().perform();
//
//    		}
//
//    	}
////    	this.bowAction.onPlayerStoppedUsing(this, par1ItemStack, par2World, entityLiving, timeLeft);
////		int ac = 20;
////		int j = this.getMaxItemUseDuration(par1ItemStack) - timeLeft;
////		if(ClientHelper.getMouseOverLongReach()!=null && ClientHelper.getMouseOverLongReach().entityHit!=null){
////			ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(par1ItemStack, par2World, entityLiving, j,ClientHelper.getMouseOverLongReach().entityHit);
////			ActionResult<InvokerWaza> result = this.component.findSkillInvoker(container, WazaEffect.Type.STOPPED_USING);
////			if(result.getType()==EnumActionResult.SUCCESS){
////				result.getResult().doSkill();
////			}
////		}
//
//		return;
//	}
//}
