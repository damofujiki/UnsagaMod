//package mods.hinasch.unsaga.core.event;
//
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaEffect;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool;
//import mods.hinasch.unsaga.core.item.weapon.base.IComponentUnsagaTool;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.ContainerItemInteraction;
//import mods.hinasch.unsaga.util.LivingHelper;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.Vec3d;
//import net.minecraft.world.World;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//public class EventUnsagaWeapon {
//
//	@SubscribeEvent
//	public void onRightClickedBlockWithItem(RightClickBlock ev){
//
//		EntityPlayer playerIn = ev.getEntityPlayer();
//		ItemStack stack = ev.getItemStack();
//		World worldIn = ev.getWorld();
//		BlockPos pos = ev.getPos();
//		EnumHand hand = ev.getHand();
//		EnumFacing facing = ev.getFace();
//		Vec3d vec = ev.getHitVec();
//
//		if(stack!=null && stack.getItem() instanceof IComponentUnsagaTool){
//			if(playerIn.isSneaking()){
//				ComponentUnsagaTool component = ((IComponentUnsagaTool)stack.getItem()).getToolComponent();
//				if(ToolCategory.weaponsExceptBow.contains(component.getCategory())){
//					ContainerItemInteraction.Using container = new ContainerItemInteraction.Using(stack, worldIn, playerIn, pos, hand, facing, new XYZPos(vec.xCoord,vec.yCoord,vec.zCoord));
//					ActionResult<WazaPerformer> result = component.findSkillInvoker(container, WazaEffect.Type.USE);
//					if(result.getType()==EnumActionResult.SUCCESS){
//						result.getResult().perform();
//
//					}
//				}
//				ev.setCanceled(true);
//			}
//		}
//
//
//	}
//
//	@SubscribeEvent
//	public void onRightClickedWithItem(RightClickItem ev){
//		EntityPlayer playerIn = ev.getEntityPlayer();
//		ItemStack stackIn = ev.getItemStack();
//		World worldIn = ev.getWorld();
//		BlockPos pos = ev.getPos();
//		EnumHand hand = ev.getHand();
//		EnumFacing facing = ev.getFace();
//
//
//		UnsagaMod.logger.trace("weapon", stackIn,hand );
//		if(stackIn!=null && stackIn.getItem() instanceof IComponentUnsagaTool){
//			ComponentUnsagaTool component = ((IComponentUnsagaTool)stackIn.getItem()).getToolComponent();
//			if(component.isBlockable()){
//				if(hand==EnumHand.OFF_HAND && !component.isHeavy(stackIn) && !playerIn.isSneaking()){
//					if(LivingHelper.adapter.hasCapability(playerIn)){
//						if(LivingHelper.adapter.getCapability(playerIn).getWeaponGuardCooling()<=0){
//							LivingHelper.adapter.getCapability(playerIn).resetWeaponGuardProgress();
//							playerIn.swingArm(EnumHand.OFF_HAND);
//							ev.setCanceled(true);
//						}
//
//					}
//
//				}
//			}
//			if(playerIn.isSneaking()){
//
//
//				if(ToolCategory.weaponsExceptBow.contains(component.getCategory())){
//					if(component.hasBowActionWaza(stackIn)){
//						playerIn.setActiveHand(hand);
//						ev.setCanceled(true);
//					}
//					ContainerItemInteraction.RightClick container = new ContainerItemInteraction.RightClick(stackIn, worldIn, playerIn, hand);
//					ActionResult<WazaPerformer> result = component.findSkillInvoker(container, WazaEffect.Type.RIGHTCLICK);
//					if(result.getType()==EnumActionResult.SUCCESS){
//						result.getResult().perform();
//						ev.setCanceled(true);
//					}
//				}
//
//			}
//		}
//
//	}
//}
