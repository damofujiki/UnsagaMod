//package mods.hinasch.unsagamagic.item;
//
//import java.util.List;
//
//import org.lwjgl.input.Keyboard;
//
//import mods.hinasch.lib.util.ChatHandler;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.world.WorldHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//public class ItemKnowledgeBook extends Item{
//
//
//	@Override
//    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//    {
////		if(worldIn.getBlockState(pos).getBlock()==Blocks.crafting_table){
////			UnsagaMod.packetDispatcher.sendToServer(PacketOpenGui.create(UnsagaGui.Type.TABLET));
////			return EnumActionResult.SUCCESS;
////		}
//		if(playerIn.isSneaking()){
//			if(WorldHelper.isServer(worldIn)){
//				UnsagaMod.getWorldElementWatcher().figureElements(worldIn, playerIn);
//				ChatHandler.sendChatToPlayer(playerIn, UnsagaMod.getWorldElementWatcher().getElementsTableFromWorldByString());
////				par3EntityPlayer.addChatMessage(UnsagaMagic.worldElement.getWorldElementInfo());
//				return EnumActionResult.SUCCESS;
//			}
//		}
//
//        return EnumActionResult.PASS;
//    }
//
//	@Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		if(HSLibs.checkShiftKeyCombo(Keyboard.KEY_M)){
//			par3List.add(HSLibs.translateKey("item.unsaga.knowledgeBook.info"));
//		}else{
//			par3List.add("Show Infomation(Shift + M)");
//		}
//	}
//}
