//package mods.hinasch.unsagamagic.util;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import com.google.common.collect.Maps;
//
//import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
//import mods.hinasch.lib.capability.CapabilityAdapterFrame;
//import mods.hinasch.lib.capability.CapabilityStorage;
//import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
//import mods.hinasch.lib.util.ChatHandler;
//import mods.hinasch.lib.util.HSLibs;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.lib.world.WorldHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.UnsagaModCore;
//import mods.hinasch.unsagamagic.item.ItemTabletOld;
//import mods.hinasch.unsagamagic.item.ItemTabletOld.DecipheringPair;
//import mods.hinasch.unsagamagic.spell.Spell;
//import mods.hinasch.unsagamagic.spell.Tablets;
//import mods.hinasch.unsagamagic.spell.Tablets.MagicTablet;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumFacing;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.Capability.IStorage;
//import net.minecraftforge.common.capabilities.CapabilityInject;
//
//public class TabletHelper {
//
//
//	public static class DefaultImpl implements ITablet{
//
//		Map<Spell, Integer> map = Maps.newHashMap();
//		MagicTablet property = Tablets.instance().earthArtistsTablet1;
//		boolean isDeciphered = false;
//		@Override
//		public Map<Spell, Integer> getDecipheringMap() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.map;
//		}
//
//		@Override
//		public MagicTablet getProperty() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.property;
//		}
//
//		@Override
//		public boolean isDeciphered() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.isDeciphered;
//		}
//
//		@Override
//		public void setDeciphered(boolean par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.isDeciphered = par1;
//		}
//
//		@Override
//		public void setDecipheringMap(Map<Spell, Integer> par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.map = par1;
//		}
//
//		@Override
//		public void setProperty(MagicTablet par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.property = par1;
//		}
//
//	}
//
//	public static interface ITablet {
//
//
//		public Map<Spell,Integer> getDecipheringMap();
//		public MagicTablet getProperty();
//		public boolean isDeciphered();
//		public void setDeciphered(boolean par1);
//		public void setDecipheringMap(Map<Spell,Integer> par1);
//		public void setProperty(MagicTablet par1);
//	}
//	public static class Storage extends CapabilityStorage<ITablet>{
//
//
//
//		@Override
//		public void readNBT(NBTTagCompound comp, Capability<ITablet> capability, ITablet instance, EnumFacing side) {
//			instance.setProperty(Tablets.instance().getTabletDataFromID(comp.getInteger("tabletID")));
//			instance.setDeciphered(comp.getBoolean("isDeciphered"));
//			if(comp.hasKey("decipheringMap")){
//				List<DecipheringPair> list = UtilNBT.readListFromNBT(comp, "decipheringMap", DecipheringPair.RESTORE);
//				Map<Spell,Integer> map = TabletHelper.listToMap(list);
//				instance.setDecipheringMap(map);
//			}
//		}
//
//		@Override
//		public void writeNBT(NBTTagCompound comp, Capability<ITablet> capability, ITablet instance, EnumFacing side) {
//			comp.setInteger("tabletID",instance.getProperty().getId());
//			if(!instance.getDecipheringMap().isEmpty()){
//				List<DecipheringPair> list = TabletHelper.mapToList(instance.getDecipheringMap());
//				UtilNBT.writeListToNBT(list, comp, "decipheringMap");
//			}
//			comp.setBoolean("isDeciphered", instance.isDeciphered());
//		}
//
//	}
//
//	@CapabilityInject(ITablet.class)
//	public static Capability<ITablet> CAPA;
//	public static ICapabilityAdapterPlan<ITablet> ica = new ICapabilityAdapterPlan(){
//
//		@Override
//		public Capability getCapability() {
//			// TODO 自動生成されたメソッド・スタブ
//			return CAPA;
//		}
//
//		@Override
//		public Class getCapabilityClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return ITablet.class;
//		}
//
//		@Override
//		public Class getDefault() {
//			// TODO 自動生成されたメソッド・スタブ
//			return DefaultImpl.class;
//		}
//
//		@Override
//		public IStorage getStorage() {
//			// TODO 自動生成されたメソッド・スタブ
//			return new Storage();
//		}
//	};
//	public static CapabilityAdapterFrame<ITablet> base = UnsagaMod.capabilityFactory.create(ica);
//	public static ComponentCapabilityAdapterItem<ITablet> adapter = base.createChildItem("tablet");
//
//	static{
//		adapter.setRequireSerialize(true);
//		adapter.setPredicate(ev -> ev.getItem() instanceof ItemTabletOld);
//	}
//	public static ITablet getCapability(ItemStack is){
//		return adapter.getCapability(is);
//	}
//
//	public static boolean hasCapability(ItemStack is){
//		return is!=null && adapter.hasCapability(is);
//	}
//	public static Map<Spell,Integer> listToMap(List<DecipheringPair> list){
//		Map<Spell,Integer> map = Maps.newHashMap();
//		list.stream().forEach(in -> map.put(in.first(),in.second()));
//		return map;
//	}
//
//	public static List<DecipheringPair> mapToList(Map<Spell,Integer> map){
//		List<DecipheringPair> list = map.entrySet().stream()
//				.map(entry ->new DecipheringPair(entry.getKey(),entry.getValue()))
//				.collect(Collectors.toList());
//		return list;
//	}
//
//	/**
//	 * 解読を進行させる。
//	 * @param ep
//	 * @param is
//	 * @param progress
//	 */
//	public static void progressDeciphering(EntityPlayer ep,ItemStack is,int progress){
//		if(WorldHelper.isServer(ep.worldObj)){
//			is.damageItem(-progress, ep);
//			if(is.getItemDamage()<=0){
//				is.setItemDamage(0);
//				if(TabletHelper.hasCapability(is)){
//					TabletHelper.getCapability(is).setDeciphered(true);
//				}
//
//				ChatHandler.sendChatToPlayer(ep,HSLibs.translateKey("msg.spell.decipher.finished"));
//				ep.addStat(UnsagaModCore.instance().achievements.firstDecipher, 1);
//				//ep.addChatMessage("finished deciphring the magic tablet.");
//			}
//		}
//
//
//	}
//
//
//}
