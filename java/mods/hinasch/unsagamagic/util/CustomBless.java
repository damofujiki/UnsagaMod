//package mods.hinasch.unsagamagic.util;
//
//import java.util.List;
//import java.util.Map;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Maps;
//
//import mods.hinasch.lib.capability.CapabilityAdapterBase;
//import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapter;
//import mods.hinasch.lib.capability.CapabilityStorage;
//import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
//import mods.hinasch.lib.capability.ISyncCapability;
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.core.HSLib;
//import mods.hinasch.lib.network.PacketSyncCapability;
//import mods.hinasch.lib.primitive.NameAndNumberAndID;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
//import mods.hinasch.unsaga.core.event.livinghurt.LivingHurtEventLPProcess.IParentContainer;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.IComponentDisplayInfo;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.ResourceLocation;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.Capability.IStorage;
//import net.minecraftforge.common.capabilities.CapabilityInject;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//import net.minecraftforge.event.AttachCapabilitiesEvent.Item;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public abstract class CustomBless extends NameAndNumberAndID<ResourceLocation> implements IParentContainer<CustomBless>{
//
//	public static final String ID_NAME = "customBless";
//	public static class DefaultImpl implements IBlessedItem{
//
//		public static Map<CustomBless,BlessEffect> getIntializedMap(){
//
//			Map<CustomBless,BlessEffect> map = Maps.newHashMap();
//			for(CustomBless bless:CustomBlessGroup.BLESS_SET){
//				map.put(bless, new BlessEffect(bless,0,0));
//			}
//			return map;
//		}
//
//		Map<CustomBless,BlessEffect> map = getIntializedMap();
//
//		@Override
//		public void expireBless(CustomBless bless) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.getBless(bless).setRemaining(0);
//		}
//
//		@Override
//		public BlessEffect getBless(CustomBless bless) {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.getBlessedMap().get(bless);
//		}
//
//		@Override
//		public Map<CustomBless,BlessEffect> getBlessedMap() {
//			// TODO 自動生成されたメソッド・スタブ
//			return map;
//		}
//
//		@Override
//		public boolean isBlessEffective(CustomBless bless) {
//			// TODO 自動生成されたメソッド・スタブ
//			if(this.getBlessedMap().containsKey(bless)){
//				return this.getBlessedMap().get(bless).getRemaining()>0;
//			}
//			return false;
//
//		}
//
//		public void decrRemaining(CustomBless bless,int step){
//			this.map.get(bless).decrRemaining(step);
//		}
//		@Override
//		public void processing() {
//			// TODO 自動生成されたメソッド・スタブ
//			for(BlessEffect blessEffect:map.values()){
//				if(this.isBlessEffective(blessEffect.getBless())){
//					blessEffect.processing();
//				}
//
//			}
//		}
//
//		@Override
//		public void refleshBless(CustomBless bless,BlessEffect effect) {
//			// TODO 自動生成されたメソッド・スタブ
//			map.put(bless, effect);
//		}
//
//		@Override
//		public NBTTagCompound getSendingData() {
//			// TODO 自動生成されたメソッド・スタブ
//			return (NBTTagCompound) CAPA.getStorage().writeNBT(CAPA, this, null);
//		}
//
//		@Override
//		public void catchSyncData(NBTTagCompound nbt){
//			CAPA.getStorage().readNBT(CAPA, this, null, nbt);
//		}
//
//
//
//		@Override
//		public String getIdentifyName() {
//			// TODO 自動生成されたメソッド・スタブ
//			return ID_NAME;
//		}
//
//		@Override
//		public void onPacket(PacketSyncCapability message, MessageContext ctx) {
//			// TODO 自動生成されたメソッド・スタブ
//			EntityPlayer clientPlayer = ClientHelper.getPlayer();
//			EnumHand hand = message.getArgs().getBoolean("hand") ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
//			ItemStack is = clientPlayer.getHeldItem(hand);
//			if(is!=null && CustomBless.hasCapability(is)){
//				CustomBless.getCapability(is).catchSyncData(message.getNbt());
//			}
//		}
//
//	}
//
//
//
//	public static interface IBlessedItem extends ISyncCapability{
//		public void expireBless(CustomBless bless);
//		public BlessEffect getBless(CustomBless bless);
//		public Map<CustomBless,BlessEffect> getBlessedMap();
//		public boolean isBlessEffective(CustomBless bless);
//		public void processing();
//		public void refleshBless(CustomBless bless,BlessEffect effect);
//		public void decrRemaining(CustomBless bless,int step);
//	}
//
//	public static class Storage extends CapabilityStorage<IBlessedItem>{
//
//
//		@Override
//		public void writeNBT(NBTTagCompound comp, Capability<IBlessedItem> capability, IBlessedItem instance,
//				EnumFacing side) {
//			UtilNBT.writeListToNBT(instance.getBlessedMap().values(), comp, "blesses");
//
//		}
//
//		@Override
//		public void readNBT(NBTTagCompound comp, Capability<IBlessedItem> capability, IBlessedItem instance,
//				EnumFacing side) {
//			List<BlessEffect> blessEffects = UtilNBT.readListFromNBT(comp, "blesses", BlessEffect.RESOTRE_FUNC);
//			for(BlessEffect effect:blessEffects){
//				instance.refleshBless(effect.getBless(),effect);
//			}
//
//		}
//
//	}
//
//	@CapabilityInject(IBlessedItem.class)
//	public static Capability<IBlessedItem> CAPA;
//
//	public static Predicate<AttachCapabilitiesEvent.Item> predicate = new Predicate<AttachCapabilitiesEvent.Item>(){
//
//		@Override
//		public boolean apply(Item input) {
//			// TODO 自動生成されたメソッド・スタブ
//			return input.getItem().isDamageable();
//		}
//	};
//	public static ICapabilityAdapter<IBlessedItem> capabilityAdapter = new ICapabilityAdapter<IBlessedItem>(){
//
//		@Override
//		public Capability<IBlessedItem> getCapability() {
//			// TODO 自動生成されたメソッド・スタブ
//			return CAPA;
//		}
//
//		@Override
//		public Class<IBlessedItem> getCapabilityClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return IBlessedItem.class;
//		}
//
//		@Override
//		public Class<? extends IBlessedItem> getDefault() {
//			// TODO 自動生成されたメソッド・スタブ
//			return DefaultImpl.class;
//		}
//
//		@Override
//		public IStorage<IBlessedItem> getStorage() {
//			// TODO 自動生成されたメソッド・スタブ
//			return new Storage();
//		}
//	};
//
//
//	public static CapabilityAdapterBase<IBlessedItem> adapterBase = UnsagaMod.capabilityFactory.create(capabilityAdapter);
//	public static ComponentCapabilityAdapterItem<IBlessedItem> comp = (ComponentCapabilityAdapterItem<IBlessedItem>) adapterBase.createChildItem("customBless")
//			.setRequireSerialize(true).setPredicate(predicate);
//
//	public static void syncCapability(EntityPlayer ep,IBlessedItem capa,EnumHand hand){
//		if(ep instanceof EntityPlayerMP){
//			NBTTagCompound args = UtilNBT.createCompound();
//			args.setBoolean("hand", hand==EnumHand.MAIN_HAND ? true : false);
//			HSLib.getPacketDispatcher().sendTo(PacketSyncCapability.create(CAPA, capa,args), (EntityPlayerMP) ep);
//		}
//
//	}
//	public static IBlessedItem getCapability(ItemStack is){
//		return comp.getCapability(is);
//	}
//
//
//	public static boolean hasCapability(ItemStack is){
//		return comp.hasCapability(is);
//	}
//
//	public static void registerEvents(){
//		PacketSyncCapability.syncCapabilityMap.put(ID_NAME, CAPA);
//		comp.registerEvent();
//		for(CustomBless bless:CustomBlessGroup.BLESS_SET){
//			bless.registerEvent();
//		}
//		IParentContainer customBlessStatic = new IParentContainer<String>(){
//
//			@Override
//			public String getParent() {
//				// TODO 自動生成されたメソッド・スタブ
//				return "CustomBless Static method";
//			}
//		};
//		EventToolTipUnsaga.list.add(new IComponentDisplayInfo(){
//
//			@Override
//			public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//
//				IBlessedItem capa = CustomBless.getCapability(is);
//				for(CustomBless bless:CustomBlessGroup.BLESS_SET){
//					if(capa.isBlessEffective(bless)){
//						dispList.add(capa.getBless(bless).getTips());
//					}
//
//				}
//			}
//
//			@Override
//			public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//				// TODO 自動生成されたメソッド・スタブ
//				return is!=null && CustomBless.hasCapability(is);
//			}}
//		);
//
////		UnsagaMod.events.getLivingUpdateAppendable().getHooks().put(customBlessStatic, new Consumer<LivingUpdateEvent>(){
////
////			@Override
////			public void accept(LivingUpdateEvent t) {
////				for(ItemStack is:t.getEntityLiving().getHeldEquipment()){
////					if(is!=null && CustomBless.hasCapability(is)){
////						if(t.getEntityLiving().ticksExisted % 20 * 12 == 0){
////							CustomBless.getCapability(is).processing();
////						}
////
////					}
////				}
////
////			}}
////		);
//	}
//
//
//	public CustomBless(String name,int id) {
//		super(new ResourceLocation(UnsagaMod.MODID,name), "bless."+name,id);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//
//	@Override
//	public CustomBless getParent(){
//		return this;
//	}
//	@Override
//	public Class getParentClass() {
//		// TODO 自動生成されたメソッド・スタブ
//		return CustomBless.class;
//	}
//
//
//	public boolean isInfinity(){
//		return false;
//	}
//
//
//
//
//	public abstract void registerEvent();
//
//
//
//
//}
