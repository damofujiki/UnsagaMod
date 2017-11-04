//package mods.hinasch.unsagamagic.item;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.stream.Collectors;
//
//import org.lwjgl.input.Keyboard;
//
//import com.mojang.realmsclient.util.Pair;
//
//import mods.hinasch.lib.iface.INBTWritable;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsagamagic.spell.Spell;
//import mods.hinasch.unsagamagic.spell.Tablets;
//import mods.hinasch.unsagamagic.spell.Tablets.MagicTablet;
//import mods.hinasch.unsagamagic.util.TabletHelper;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.text.TextFormatting;
//
//public class ItemTabletOld extends Item{
//
//
//
//	/**
//	 * 術・進行度のペア、おもにタブレットクラスの内部で使う
//	 * @author damofujiki
//	 *
//	 */
//	public static class DecipheringPair extends Pair<Spell,Integer> implements INBTWritable{
//
//		public static final RestoreFunc<DecipheringPair> RESTORE = input ->{
//			Spell spell = Spell.RESTOREFUNC.apply(input);
//			int progress = input.getInteger("progress");
//
//			return new DecipheringPair(spell,progress);
//		};
//
//
//
//		public DecipheringPair(Spell first, Integer second) {
//			super(first, second);
//			// TODO 自動生成されたコンストラクター・スタブ
//		}
//
//		public int getProgress(){
//			return this.second();
//		}
//
//		public Spell getSpell(){
//			return this.first();
//		}
//
//
//		@Override
//		public void writeToNBT(NBTTagCompound stream) {
//			this.first().writeToNBT(stream);
//			stream.setInteger("progress", this.second());
//		}
//
//
//    }
//
//	public static final String KEY_TABLET_DECIPHERING = "tablet.deciphering";
//	public static final String KEY_TABLET_ID = "tablet.id";
//
////	/**
////	 * 術・進行度のペアをMapで得る
////	 * @param is
////	 * @return
////	 */
////	public static Map<Spell,Integer> getDecipheringSet(ItemStack is){
////    	Map<Spell,Integer> map = new HashMap();
////    	if(UtilNBT.hasKey(is, KEY_TABLET_DECIPHERING)){
////    		NBTTagCompound comp = is.getTagCompound();
////    		List<DecipheringPair> list = UtilNBT.readListFromNBT(comp, KEY_TABLET_DECIPHERING, DecipheringPair.RESTORE);
////
////
////    		for(DecipheringPair s:list){
////    			map.put(s.first(), s.second());
////    		}
////    	}
////
////    	return map;
////    }
//
//	public static ItemStack getRandomTablet(Random rand){
//		ItemStack tablet = new ItemStack(UnsagaMod.magic.items.tablet,1);
//		MagicTablet id = UnsagaMod.magic.tablets().getRandomTabletID(rand);
//		if(TabletHelper.hasCapability(tablet)){
//			TabletHelper.getCapability(tablet).setProperty(id);
//		}
////		ItemTablet.setTabletID(tablet, id);
//		return tablet;
//	}
//	public static MagicTablet getTabletData(ItemStack is){
//		return UnsagaMod.magic.tablets().getTabletDataFromID(getTabletID(is));
//	}
//
//	public static int getTabletID(ItemStack is){
//		if(UtilNBT.hasKey(is, KEY_TABLET_ID)){
//			return UtilNBT.readInt(is,KEY_TABLET_ID);
//		}
//		return 0;
//	}
//
//
////    public static void setTabletID(ItemStack tablet,int id){
////    	UtilNBT.setFreeTag(tablet, "tablet.id", id);
////    }
//
//    public static void writeDecipheringMap(ItemStack is,final Map<Spell,Integer> map){
//    	List<DecipheringPair> list =map.keySet().stream().map(input ->new DecipheringPair(input,map.get(input))).collect(Collectors.toList());
//
//    	if(!list.isEmpty()){
////        	String serialized = CSVText.buildCSVFromSerializable(list);
////        	Unsaga.debug("シリアライズド："+serialized);
//        	NBTTagCompound comp = UtilNBT.getNewCompound(is);
//        	UtilNBT.writeListToNBT(list, comp, KEY_TABLET_DECIPHERING);
//
//    	}
//
//    }
//
//    public ItemTabletOld(){
//
//        this.maxStackSize = 1;
//
//
//        this.setNoRepair();
////        this.setTextureName(Unsaga.DOMAIN+":tablet");
//	}
//
//
//    @Override
//    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//
//		int id = this.getTabletID(par1ItemStack);
//		if(TabletHelper.hasCapability(par1ItemStack)){
//			MagicTablet data = TabletHelper.getCapability(par1ItemStack).getProperty();
//			if(data!=null){
//				String localized = HSLibs.translateKey("tablet."+data.getName());
//				if(localized!=null){
//					par3List.add(localized);
//				}
//			}
//		}
//		if(HSLibs.checkShiftKeyCombo(Keyboard.KEY_M)){
//			par3List.add(TextFormatting.ITALIC+HSLibs.translateKey("item.unsaga.magicTablet.info"));
//		}else{
//			par3List.add(TextFormatting.ITALIC+"Show Infomation(Shift + M)");
//		}
//
//	}
//
//
//    @Override
//    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
//    {
//
//    	List<ItemStack> list = Tablets.instance().getAllTabletData().values().stream().map(input ->{
//    		ItemStack is = new ItemStack(UnsagaMod.magic.items.tablet,1);
//    		if(TabletHelper.hasCapability(is)){
//    			TabletHelper.getCapability(is).setProperty(input);
//    		}
//
//			return is;
//    	}).collect(Collectors.toList());
//
//
//    	par3List.addAll(list);
////    	for(TabletData data:Tablets.getInstance().getAllTabletData().values()){
////
////    		ItemStack is = new ItemStack(UnsagaMagic.getItems().magicTabletNew,1);
////    		setTabletID(is, index);
////
////    		index+=1;
////    		par3List.add(is);
////    	}
//
//
//    }
//
////    @Override
////    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
////    {
////        return new ICapabilitySerializable<NBTBase>(){
////
////        	ITablet tablet = UnsagaMagicCapability.tablet().getDefaultInstance();
////			@Override
////			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
////				// TODO 自動生成されたメソッド・スタブ
////				return UnsagaMagicCapability.tablet()!=null && UnsagaMagicCapability.tablet()==capability;
////			}
////
////			@Override
////			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
////				if(UnsagaMagicCapability.tablet()!=null && UnsagaMagicCapability.tablet()==capability){
////					return (T)tablet;
////				}
////				return null;
////			}
////
////			@Override
////			public NBTBase serializeNBT() {
////				// TODO 自動生成されたメソッド・スタブ
////				return UnsagaMagicCapability.tablet().getStorage().writeNBT(UnsagaMagicCapability.tablet(), tablet, null);
////			}
////
////			@Override
////			public void deserializeNBT(NBTBase nbt) {
////				UnsagaMagicCapability.tablet().getStorage().readNBT(UnsagaMagicCapability.tablet(), tablet, null, nbt);
////			}};
////    }
//
//}
