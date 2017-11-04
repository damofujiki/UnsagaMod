//package mods.hinasch.unsaga.core.item.weapon;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.waza.Waza;
//import mods.hinasch.unsaga.ability.waza.WazaRegistry;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect.Type;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemStaffBase;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.world.World;
//
//public class ItemStaffUnsaga extends ItemStaffBase{
//
//	Waza skullCrash = WazaRegistry.instance().skullCrash;
//	public ItemStaffUnsaga(UnsagaMaterial material) {
//		super(material);
//		this.component.getBowActionMap().put(skullCrash, 72000);
//		this.component.setBlockable(true);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//	@Override
//	public EnumAction getItemUseAction(ItemStack stack)
//	{
//
//		return this.component.getItemUseAction(stack);
//	}
//
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
////
////		return new ActionResult(EnumActionResult.PASS,par1ItemStack);
////	}
//
//    @Override
//    public void onPlayerStoppedUsing(ItemStack itemStack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
//    {
//    	ActionResult<Float> resultBowAction = this.component.onPlayerStoppedUsing(this, itemStack, worldIn, entityLiving, timeLeft);
//
//    	if(resultBowAction.getType()==EnumActionResult.SUCCESS){
//    		float charge = resultBowAction.getResult();
//    		ContainerItemInteraction.Stopped container = new ContainerItemInteraction.Stopped(itemStack, worldIn, entityLiving, charge, ClientHelper.getMouseOverLongReach().entityHit);
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
//
//		return;
//	}
//
////	@Override
////    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
////    {
////
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
////	@Override
////	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
////	{
////		ContainerItemInteraction.EntityClick container = new ContainerItemInteraction.EntityClick(stack, player.worldObj, player, entity);
////		ActionResult<WazaPerformer> result = this.component.findSkillInvoker(container, WazaEffect.Type.ENTITY_LEFTCLICK);
////		if(result.getType()==EnumActionResult.SUCCESS){
////			result.getResult().perform();
////			return true;
////		}
////		return false;
////
////	}
//}
