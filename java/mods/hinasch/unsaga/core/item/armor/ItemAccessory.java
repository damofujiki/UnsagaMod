//package mods.hinasch.unsaga.core.item.armor;
//
//import java.util.List;
//
//import com.google.common.collect.Lists;
//
//import joptsimple.internal.Strings;
//import mods.hinasch.lib.item.ItemPropertyGetterWrapper;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.ability.AbilityRegistry;
//import mods.hinasch.unsaga.capability.IUnsagaPropertyItem;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool;
//import mods.hinasch.unsaga.core.net.packet.PacketOpenGui;
//import mods.hinasch.unsaga.init.UnsagaGui;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.AbstractClientPlayer;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//public class ItemAccessory extends Item implements IItemColor{
//
//
//	protected ComponentUnsagaTool component;
//
//	public ItemAccessory(UnsagaMaterial par2) {
//		super();
//		this.component = new ComponentUnsagaTool(par2, ToolCategory.ACCESSORY).setMaxAbilitySize(2);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//        this.maxStackSize = 1;
//		this.setMaxDamage(par2.getToolMaterial().getMaxUses()*2);
//
//		this.addPropertyOverride(new ResourceLocation("variant"), ItemPropertyGetterWrapper.of((stack,worldIn,entityIn) ->{
//			if(AbilityHelper.hasCapability(stack) && !AbilityHelper.getCapability(stack).getEgoName().equals(Strings.EMPTY)){
//				return 1.0F;
//			}
//			return 0;
//		}));
//		this.component.addPropertyOverride(this);
//
//	}
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
//    public String getItemStackDisplayName(ItemStack stack)
//    {
//        if(AbilityHelper.hasCapability(stack)){
//        	if(!AbilityHelper.getCapability(stack).getEgoName().equals(Strings.EMPTY)){
//        		return HSLibs.translateKey(AbilityHelper.getCapability(stack).getEgoName());
//        	}
//        }
//        return super.getItemStackDisplayName(stack);
//    }
//	@Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
//
//	@Override
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return component.initWeaponCapabilities(stack, nbt);
//    }
//
////	@Override
////    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
////    {
////		return helper.getIsRepairable(par1ItemStack, par2ItemStack) ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
////    }
//
//    @Override
//    public int getColorFromItemstack(ItemStack par1ItemStack, int par2)
//    {
//    	return component.getColorFromItemStack(par1ItemStack, par2);
//    }
//
////	@Override
////	public void registerIcons(IIconRegister par1IconRegister)
////	{
////		if(this.material.getSpecialIcon(ToolCategory.ACCESSORY).isPresent()){
////			this.itemIcon = par1IconRegister.registerIcon(Unsaga.DOMAIN+":"+this.material.getSpecialIcon(ToolCategory.ACCESSORY).get());
////			return;
////		}
////		this.itemIcon = par1IconRegister.registerIcon(Unsaga.DOMAIN+":"+this.defaultIcon);
////	}
//
////    @Override
////    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
////    {
////    	helper.getSubItems(par1, par2CreativeTabs, par3List);
////
////    }
//
//    @SideOnly(Side.CLIENT)
//    @Override
//    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
//    {
//        if(this.component.getMaterial()==UnsagaMod.materials.serpentine){
//        	AbilityRegistry abilities = AbilityRegistry.instance();
//        	ItemStack is = new ItemStack(this,1);
//        	IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//        	capa.setEgoName("item.unsaga.acs.serpentine.name.alt");
//        	capa.setAbilityList(Lists.newArrayList(abilities.healUp5,abilities.water,abilities.supportWood,abilities.fireProtection));
//        	subItems.add(is);
//        }
//        super.getSubItems(itemIn, tab, subItems);
//    }
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(ItemStack par1ItemStack, World worldIn, EntityPlayer par3EntityPlayer, EnumHand hand)
//    {
//      if(par3EntityPlayer.isSneaking()){
//	    	  AbstractClientPlayer clientPlayer = (AbstractClientPlayer)Minecraft.getMinecraft().thePlayer;
//
//	    	  if(Minecraft.getMinecraft().currentScreen !=null) return new ActionResult(EnumActionResult.FAIL,par1ItemStack);
//	    	  UnsagaMod.packetDispatcher.sendToServer(PacketOpenGui.create(UnsagaGui.Type.EQUIPMENT, XYZPos.createFrom(par3EntityPlayer)));
//      }
//      //誤動作が起きるので一旦削除
////      else{
////    	  if(AccessoryHelper.hasCapability(par3EntityPlayer)){
////    		  IAccessorySlot slot = AccessoryHelper.getCapability(par3EntityPlayer);
////    		  int index = 0;
////    		  for(ItemStack is:slot.getAccessories()){
////    			  if(is==null){
////    				  slot.setAccessory(index, par1ItemStack.copy());
////    	    		  par1ItemStack.stackSize --;
////    			  }
////    			  index ++;
////    		  }
////
////    		  return new ActionResult(EnumActionResult.SUCCESS,par1ItemStack);
////    	  }
//
////      }
//
//      return new ActionResult(EnumActionResult.PASS,par1ItemStack);
//    }
////
////	@Override
////    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
////    {
////
////
////        if(par3EntityPlayer.isSneaking()){
////	    	  AbstractClientPlayer clientPlayer = (AbstractClientPlayer)Minecraft.getMinecraft().thePlayer;
////
////	    	  if(Minecraft.getMinecraft().currentScreen !=null)return par1ItemStack;
////	    	  PacketGuiOpen pg = new PacketGuiOpen(Unsaga.guiNumber.EQUIP);
////	    	  Unsaga.packetDispatcher.sendToServer(pg);
////        }else{
////        	if(ExtendedPlayerData.getData(par3EntityPlayer).getEmptyAccessorySlot().isPresent()){
////        		int slot = ExtendedPlayerData.getData(par3EntityPlayer).getEmptyAccessorySlot().get();
////        		ExtendedPlayerData.getData(par3EntityPlayer).setAccessory(slot, par1ItemStack.copy());
////        		par1ItemStack.stackSize --;
////        	}
////        }
////
////        return par1ItemStack;
////    }
//
//
//}
