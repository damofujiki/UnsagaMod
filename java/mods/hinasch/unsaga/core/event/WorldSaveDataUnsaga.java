//package mods.hinasch.unsaga.core.event;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Random;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import com.google.common.collect.Lists;
//
//import mods.hinasch.lib.core.HSLib;
//import mods.hinasch.lib.core.event.EventEntityJoinWorld;
//import mods.hinasch.lib.primitive.Tuple;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.lib.world.WorldHelper;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.core.net.packet.PacketSyncSkillPanel;
//import mods.hinasch.unsaga.skillpanel.PanelList;
//import mods.hinasch.unsaga.skillpanel.SkillPanels.SkillPanel;
//import net.minecraft.entity.passive.EntityVillager;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldSavedData;
//
//public class WorldSaveDataUnsaga extends WorldSavedData{
//
//	public static final String KEY = "skillData";
//
//	public static final UUID UUID_DEBUG = UUID.fromString("db8ff9a2-9ef5-4371-9e98-42d694add74a");
//	protected Map<UUID,PanelList> panelDataPerUser;
//
//
//	protected Map<Integer,XYZPos> carrierMap;
//
//	public WorldSaveDataUnsaga(String par1Str) {
//		super(par1Str);
//		this.panelDataPerUser = new HashMap();
//		this.carrierMap = new HashMap();
//	}
//
//	public WorldSaveDataUnsaga() {
//		this(KEY);
//	}
//
//	public XYZPos getCarrierAddress(int id){
//		return this.carrierMap.get(id);
//	}
//	public CarrierData getRandomCarrier(Random rand,final double dis,final XYZPos pos,final int current){
////		List<Tuple<Integer,XYZPos>> newlist = ListHelper.stream(this.carrierMap.keySet()).map(new Function<Integer,Tuple<Integer,XYZPos>>(){
////
////			@Override
////			public Tuple apply(Integer id) {
////				if(carrierMap.get(id).distanceSq(pos)>dis){
////					UnsagaMod.logger.trace(this.getClass().getName(),carrierMap.get(id).distanceSq(pos));
////					if(id!=current){
////						return new Tuple(id, carrierMap.get(id));
////					}
////
////				}
////				return null;
////			}}
////		).getList();
//
//		List<Tuple<Integer,XYZPos>> newlist = this.carrierMap.keySet().stream().flatMap(in ->{
//			List<Integer> list = Lists.newArrayList();
//			if(this.carrierMap.get(in).distanceSq(pos)>dis){
//				if(in!=current){
//					list.add(in);
//				}
//			}
//			return list.stream();
//		}).map(in -> Tuple.of(in, this.carrierMap.get(in))).collect(Collectors.toList());
//
//
//
//
////		Unsaga.debug(newlist);
//		if(newlist.isEmpty()){
//			return null;
//		}
//		int rnd = rand.nextInt(newlist.size());
//		if(newlist.get(rnd)==null){
//			return null;
//		}
//		return new CarrierData(rnd,newlist.get(rnd).second);
//	}
//	public void addCarrierData(int id,EntityVillager villager){
//		if(this.carrierMap==null){
//			this.carrierMap = new HashMap();
//		}
//
//		this.carrierMap.put(id, XYZPos.createFrom(villager));
////		Unsaga.debug("Add Carrier:",id,this.carrierMap.get(id),this.getClass().getName());
//	}
//	public int getNextCarrierID(){
//		int index = 0;
//		if(this.carrierMap==null ||this.carrierMap.isEmpty()){
//			return 0;
//		}
//		for(int i=0;i<65535;i++){
//			if(!this.carrierMap.containsKey(i)){
//				return i;
//			}
//		}
//		return 0;
//	}
//
//
//	@Override
//	public void readFromNBT(NBTTagCompound var1) {
//
////		Unsaga.debug("読み込まれます",this.getClass());
//		NBTTagList tagUsers = UtilNBT.getTagList(var1, "PanelPerUser");
//		for(int i=0; i< tagUsers.tagCount() ; i++){
//			NBTWrapper wrapper = new NBTWrapper(tagUsers.getCompoundTagAt(i));
//			this.panelDataPerUser.put(wrapper.getUUID(), wrapper.getPanelList());
//
//		}
//
//		NBTTagList tagCarriers = UtilNBT.getTagList(var1, "Carriers");
//		if(this.carrierMap==null){
//			this.carrierMap = new HashMap();
//		}
//		for(int i=0; i< tagCarriers.tagCount() ; i++){
//			NBTTagCompound perCarrier = tagCarriers.getCompoundTagAt(i);
//			int id = perCarrier.getInteger("id");
//			XYZPos pos = XYZPos.strapOff(perCarrier.getString("pos"));
////			Unsaga.debug("Carrier",id,pos.toString(),this.getClass().getName());
//			if(pos!=null){
//				this.carrierMap.put(id, pos);
//			}
//
//		}
//
//	}
//
//	@Override
//	public NBTTagCompound writeToNBT(NBTTagCompound var1) {
//		UnsagaMod.logger.trace(this.getClass().getName(),"書き込まれます");
//
//		this.dumpMap();
//		if(this.panelDataPerUser!=null && !this.panelDataPerUser.isEmpty()){
//			NBTTagList tagsPerUser = new NBTTagList();
//			for(UUID userUUID:this.panelDataPerUser.keySet()){
////				Unsaga.debug(userUUID+"のを書き込みます",this.getClass());
//				PanelList panelListPerUser = this.panelDataPerUser.get(userUUID);
//				NBTWrapper wrapper = new NBTWrapper(new NBTTagCompound());
//				wrapper.setData(panelListPerUser, userUUID);
//				tagsPerUser.appendTag(wrapper.getTag());
//			}
//
////			Unsaga.debug(tagsPerUser);
//			var1.setTag("PanelPerUser", tagsPerUser);
//		}
//
//		if(this.carrierMap!=null && !this.carrierMap.isEmpty()){
//			NBTTagList tagsCarrier = new NBTTagList();
//			for(Integer id:this.carrierMap.keySet()){
//				NBTTagCompound compoundCarrier = new NBTTagCompound();
//				compoundCarrier.setInteger("id", id);
//				compoundCarrier.setString("pos", this.carrierMap.get(id).toString());
//				tagsCarrier.appendTag(compoundCarrier);
////				Unsaga.debug("Carrier-Save",id,this.carrierMap.get(id).toString(),this.getClass().getName());
//			}
//
//			var1.setTag("Carriers", tagsCarrier);
//		}
//		return var1;
//	}
//
//	public PanelList getPanelList(UUID user){
//		if(this.panelDataPerUser==null){
//			this.panelDataPerUser = new HashMap<UUID,PanelList>();
//		}
//		UUID username = HSLib.configHandler.isDebug() ? UUID_DEBUG  : user;
//		if(!this.panelDataPerUser.isEmpty() && this.panelDataPerUser.containsKey(username)){
//			return this.panelDataPerUser.get(username);
//		}
//		return null;
//	}
//
//	//debug
//	public void clearData(){
//		this.panelDataPerUser = new HashMap();
//	}
////	public void setPanels(UUID user,ItemStack[] panels){
////		UUID username = HSLib.configHandler.isDebug() ? UUID_DEBUG  : user;
////		this.setPanels(username, panels);
////	}
//
//	public void setPanels(UUID uuid,ItemStack[] panels){
//		UUID username = HSLib.configHandler.isDebug() ? UUID_DEBUG  : uuid;
//		this.panelDataPerUser.put(username, new PanelList(panels));
//	}
//	public static int getHighestLevelOfPanel(World world,EntityPlayer ep,SkillPanel panel){
//		PanelList list = getPanels(world,ep);
//		int level = SkillPanel.NOT_FOUND;
//		if(list!=null){
//			level = list.getHighest(panel);
//		}
//
//		return level;
//
//	}
//	public static WorldSaveDataUnsaga getData(World world){
//		WorldSaveDataUnsaga data = (WorldSaveDataUnsaga)world.loadItemData(WorldSaveDataUnsaga.class, WorldSaveDataUnsaga.KEY);
//		if(data ==null){
//				data = new WorldSaveDataUnsaga();
//				world.setItemData(WorldSaveDataUnsaga.KEY, data);
//		}
//
//		return data;
//	}
//
//	public static PanelList getPanels(World world,EntityPlayer ep){
//		WorldSaveDataUnsaga data = getData(world);
//		PanelList panelsEP = data.getPanelList(ep.getGameProfile().getId());
//		return panelsEP;
//	}
//	public static void clearData(World world){
//		WorldSaveDataUnsaga data = getData(world);
//		if(data !=null){
//			data.clearData();
//		}
//		data.markDirty();
//
//	}
//
//
//	public static void registerEvents(){
//		EventEntityJoinWorld.addEvent(e ->{
//			if(e.getEntity() instanceof EntityPlayer && WorldHelper.isClient(e.getWorld())){
//				EntityPlayer ep = (EntityPlayer) e.getEntity();
//				UUID uuid = ep.getGameProfile().getId();
//
//				UnsagaMod.packetDispatcher.sendToServer(PacketSyncSkillPanel.createRequest(uuid));
////				PacketSyncSkillPanel syncPanels = PacketSyncSkillPanel.getRequestPacket(uuid);
////				UnsagaMod.packetDispatcher.sendToServer(syncPanels);
//			}
//		});
//	}
////	@SubscribeEvent
////	public void onEntityJoinWorld(EntityJoinWorldEvent e){
////		if(e.getEntity() instanceof EntityPlayer && WorldHelper.isClient(e.getWorld())){
////			EntityPlayer ep = (EntityPlayer) e.getEntity();
////			UUID uuid = ep.getGameProfile().getId();
////
////			UnsagaMod.packetDispatcher.sendToServer(PacketSyncSkillPanel.createRequest(uuid));
//////			PacketSyncSkillPanel syncPanels = PacketSyncSkillPanel.getRequestPacket(uuid);
//////			UnsagaMod.packetDispatcher.sendToServer(syncPanels);
////		}
////	}
//
//
//	public void dumpMap(){
//		for(Entry<UUID,PanelList> entry:this.panelDataPerUser.entrySet()){
//			 UnsagaMod.logger.trace(entry.getKey().toString(), entry.getValue().toString());
//		}
//	}
//
//	public static class CarrierData {
//
//		public final int id;
//		public final XYZPos pos;
//		public CarrierData(int id,XYZPos pos){
//			this.id = id;
//			this.pos = pos;
//		}
//	}
//
//	public class NBTWrapper{
//		NBTTagCompound nbt;
//
//		public NBTWrapper(NBTTagCompound nbt){
//			this.nbt = nbt;
//		}
//
//		public PanelList getPanelList(){
//			NBTTagList tagPanels = UtilNBT.getTagList(nbt,"Panels");
//			PanelList newPanels = new PanelList();
//			newPanels.setItemStacks(UtilNBT.getItemStacksFromNBT(tagPanels, 7));
//			return newPanels;
//		}
//
//		public UUID getUUID(){
//			return this.nbt.getUniqueId("UUID");
//		}
//
//		public void setData(PanelList panels,UUID UUID){
//			NBTTagList tagPanels = UtilNBT.newTagList();
//			UtilNBT.writeItemStacksToNBTTag(tagPanels, panels.panels);
//			nbt.setTag("Panels", tagPanels);
//			nbt.setUniqueId("UUID", UUID);
//		}
//
//		public NBTTagCompound getTag(){
//			return this.nbt;
//		}
//
//
//	}
//
////
////
////	public static class Wrapper implements IStreamWritable{
////
////		public static final RestoreFunction<Wrapper> FUNC_RESTORE = new RestoreFunction<Wrapper>(){
////
////			@Override
////			public Wrapper apply(ByteBuffer input) {
////				int damage = input.getInt();
////				int level = input.getInt();
////				ItemStack newstack = new ItemStack(damage);
////				return null;
////			}
////		};
////		public ItemStack is;
////
////		public Wrapper(ItemStack is){
////			this.is = is;
////		}
////
////
////		@Override
////		public void writeToStream(ByteBuffer stream) {
////			stream.putInt(is.getItemDamage());
////			stream.putInt(ItemSkillPanel.getLevel(is));
////
////		}
////
////
////	}
//}
