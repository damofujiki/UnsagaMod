//package mods.hinasch.unsagamagic.item;
//
//import java.util.List;
//
//import mods.hinasch.lib.iface.IIconItem;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.util.Statics;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsagamagic.item.newitem.ItemSpellBook;
//import mods.hinasch.unsagamagic.spell.Spell;
//import mods.hinasch.unsagamagic.spell.SpellRegistry;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumFacing;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.Capability.IStorage;
//import net.minecraftforge.common.capabilities.CapabilityInject;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
///**
// * おもに解読画面のアイコンで使う
// * @author damofujiki
// *
// */
//public class ItemIconSpell extends Item implements IIconItem,IItemColor{
//public static class DefaultIItemIconSpell implements IItemIconSpell{
//
//    	EnumSelectAttribute select = EnumSelectAttribute.UNSELECTED;
//    	Spell spell = SpellRegistry.instance().abyss;
//    	int progress = 0;
//		@Override
//		public int getDecipheringProgress() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.progress;
//		}
//
//		@Override
//		public EnumSelectAttribute getSelectStatus() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.select;
//		}
//
//		@Override
//		public Spell getSpell() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.spell;
//		}
//
//		@Override
//		public void setDecipheringProgress(int par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.progress = par1;
//		}
//
//		@Override
//		public void setSpell(Spell spell) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.spell = spell;
//		}
//
//		@Override
//		public void setStatus(EnumSelectAttribute en) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.select = en;
//		}
//
//    };
//    public static class StorageIItemIconSpell implements IStorage<IItemIconSpell>{
//
//		@Override
//		public NBTBase writeNBT(Capability<IItemIconSpell> capability, IItemIconSpell instance, EnumFacing side) {
//			NBTTagCompound nbt = UtilNBT.compound();
//			instance.getSpell().writeToNBT(nbt);
//			nbt.setInteger("progress", instance.getDecipheringProgress());
//			return nbt;
//		}
//
//		@Override
//		public void readNBT(Capability<IItemIconSpell> capability, IItemIconSpell instance, EnumFacing side,
//				NBTBase nbt) {
//			if(nbt instanceof NBTTagCompound){
//				instance.setSpell(Spell.RESTOREFUNC.apply((NBTTagCompound) nbt));
////				instance.setSpell(SpellRegistry.instance().getSpell(((NBTTagCompound) nbt).getInteger("spell")));
//				instance.setDecipheringProgress(((NBTTagCompound) nbt).getInteger("progress"));
//			}
//
//		}
//
//    }
//	//	public ElementIcons elementalIcons;
//	public enum EnumSelectAttribute {SELECTED,UNSELECTED}
//	public static interface IItemIconSpell{
//
//    	public int getDecipheringProgress();
//    	public EnumSelectAttribute getSelectStatus();
//    	public Spell getSpell();
//    	public void setDecipheringProgress(int par1);
//    	public void setSpell(Spell spell);
//    	public void setStatus(EnumSelectAttribute en);
//    }
//	public static final int FINISHED_COLOR = 0xee0000;
//	@CapabilityInject(IItemIconSpell.class)
//	public static Capability<IItemIconSpell> CAPA;
//
//	public static final String KEY_PROGRESS = "decipheringProgress";
//	public static IItemIconSpell getCapability(ItemStack is){
//		return is.getCapability(ItemIconSpell.CAPA, null);
//	}
//    public static ItemStack createSpellIcon(Spell spell){
//		ItemStack is = new ItemStack(UnsagaMod.magic.items.iconSpell,1);
//		if(hasCapability(is)){
//			getCapability(is).setSpell(spell);
//		}
//		return is;
//	}
//
////	public static EnumSelectAttribute getSelectStatus(ItemStack is){
////
////		if(is!=null && is.getItemDamage()==1){
////			return EnumSelectAttribute.SELECTED;
////		}
////		return EnumSelectAttribute.UNSELECTED;
////	}
////
////	public static void setStatus(ItemStack is,EnumSelectAttribute en){
////		switch(en){
////		case SELECTED:
////			is.setItemDamage(1);
////			break;
////		case UNSELECTED:
////			is.setItemDamage(0);
////			break;
////		default:
////			break;
////
////		}
////	}
//
//	public static boolean hasCapability(ItemStack is){
//		return is.hasCapability(CAPA, null);
//	}
//
//	public ItemIconSpell(){
//		//this.setTextureName(Unsaga.DOMAIN+":book_spell");
////		this.elementalIcons = new ElementIcons();
//		this.setMaxDamage(10);
//		HSLibs.registerCapability(IItemIconSpell.class, new StorageIItemIconSpell(), DefaultIItemIconSpell.class);
//		ItemSpellBook.registerElementsProperties(this,input ->{
//			if(ItemIconSpell.hasCapability(input)){
//				return ItemIconSpell.getCapability(input).getSpell().getElement();
//			}
//			return null;
//		});
//	}
//
//@Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//
//		if(hasCapability(par1ItemStack)){
//			Spell spell = getCapability(par1ItemStack).getSpell();
//			par3List.add(spell.getLocalizedName());
//			par3List.add(getCapability(par1ItemStack).getDecipheringProgress()+"/100");
//		}
//
//	}
//	@Override
//    @SideOnly(Side.CLIENT)
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//
//		if(hasCapability(stack)){
//			if(getCapability(stack).getDecipheringProgress()>=100){
//				return FINISHED_COLOR;
//			}
//		}
//
//        return Statics.COLOR_NONE;
//    }
//
////    @Override
////    @SideOnly(Side.CLIENT)
////    public void registerIcons(IIconRegister par1IconRegister)
////    {
////        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
////        this.elementalIcons.registerIcons(par1IconRegister);
////    }
////
////	@Override
////	public boolean requiresMultipleRenderPasses()
////	{
////		return true;
////	}
////
////    @Override
////    public IIcon getIcon(ItemStack stack, int pass)
////    {
////
////    		if(pass==1){
////    			return this.elementalIcons.getIcon(ItemIconSpell.getSpell(stack).element);
////    		}
////
////
////		return this.itemIcon;
////    }
//
//	@Override
//    public double getDurabilityForDisplay(ItemStack stack)
//    {
//    	if(hasCapability(stack)){
//    		return (double)(100-getCapability(stack).getDecipheringProgress()) / 100.0D;
//    	}
//    	return super.getDurabilityForDisplay(stack);
//    }
//
//    //	public static int getDecipheringProgress(ItemStack is){
////		if(UtilNBT.hasKey(is, KEY_PROGRESS)){
////			return UtilNBT.readFreeTag(is, KEY_PROGRESS);
////		}
////		return 0;
////	}
////	public static void setDecipheringProgress(ItemStack is,int progress){
////		UtilNBT.initNBTIfNotInit(is);
////		UtilNBT.setFreeTag(is, KEY_PROGRESS, progress);
////	}
//    @SideOnly(Side.CLIENT)
//    @Override
//    public boolean hasEffect(ItemStack par1ItemStack)
//    {
//    	if(hasCapability(par1ItemStack)){
//    		return getCapability(par1ItemStack).getSelectStatus() == EnumSelectAttribute.SELECTED;
//    	}
//       return super.hasEffect(par1ItemStack);
//    }
//
//
//
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//        return new ICapabilitySerializable(){
//
//        	IItemIconSpell inst = CAPA.getDefaultInstance();
//			@Override
//			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//				// TODO 自動生成されたメソッド・スタブ
//				return (CAPA !=null && capability==CAPA) ?(T)inst : null;
//			}
//
//			@Override
//			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//				// TODO 自動生成されたメソッド・スタブ
//				return CAPA !=null && capability==CAPA;
//			}
//
//			@Override
//			public NBTBase serializeNBT() {
//				// TODO 自動生成されたメソッド・スタブ
//				return CAPA.getStorage().writeNBT(CAPA, inst, null);
//			}
//
//			@Override
//			public void deserializeNBT(NBTBase nbt) {
//				// TODO 自動生成されたメソッド・スタブ
//				CAPA.getStorage().readNBT(CAPA, inst, null, nbt);
//			}
//		};
//    }
//
//    @Override
//    public boolean showDurabilityBar(ItemStack stack)
//    {
//        return true;
//    }
//
//
//}
