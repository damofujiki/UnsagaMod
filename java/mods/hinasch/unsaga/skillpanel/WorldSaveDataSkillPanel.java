package mods.hinasch.unsaga.skillpanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.item.ItemUtil.ItemStackList;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.inventory.InventorySkillPanel;
import mods.hinasch.unsaga.core.inventory.container.MatrixAdapterItemStack;
import mods.hinasch.unsaga.core.net.packet.PacketSyncSkillPanel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldSaveDataSkillPanel extends WorldSavedData{

	public static final String KEY = UnsagaMod.MODID+".skillData";
	public static final UUID UUID_DEBUG = UUID.fromString("db8ff9a2-9ef5-4371-9e98-42d694add74a");
	protected Map<UUID,ItemStackList> panelDataPerUser;

	public static WorldSaveDataSkillPanel get(World world) {
//		  // The IS_GLOBAL constant is there for clarity, and should be simplified into the right branch.
//		  MapStorage storage =world.getMapStorage();
//		  WorldSaveDataSkillPanel instance = (WorldSaveDataSkillPanel) storage.getOrLoadData(WorldSaveDataSkillPanel.class, KEY);
//
//		  if (instance == null) {
//		    instance = new WorldSaveDataSkillPanel();
//		    storage.setData(KEY, instance);
//		  }

		WorldSaveDataSkillPanel data = (WorldSaveDataSkillPanel) world.loadItemData(WorldSaveDataSkillPanel.class, KEY);
		if(data==null){
			data = new WorldSaveDataSkillPanel();
			world.setItemData(KEY, data);
		}
		  return data;
	}

	public WorldSaveDataSkillPanel(String par1Str) {
		super(par1Str);
		this.panelDataPerUser = new HashMap();
	}

	public WorldSaveDataSkillPanel() {
		this(KEY);
	}
	public void clearData(){
		this.panelDataPerUser = new HashMap();
	}

	public void dumpData(){
		for(Entry<UUID,ItemStackList> entry:this.panelDataPerUser.entrySet()){
			UnsagaMod.logger.trace("panel", entry.getKey());
			UnsagaMod.logger.trace("panel", entry.getValue());
		}
	}
	public ItemStackList getPanels(UUID uuid){
		return this.panelDataPerUser.get(HSLib.isDebug() ? UUID_DEBUG : uuid);
	}

	public void setPanels(UUID uuid,ItemStackList list){
		this.panelDataPerUser.put(HSLib.isDebug() ?UUID_DEBUG : uuid , list.getTrimmed(7));
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		UnsagaMod.logger.trace(this.getClass().getName(), "loading...");
		panelDataPerUser = Maps.newHashMap();
		if(nbt.hasKey("panelPerUser")){
			List<SkillPanelEntry> list  = UtilNBT.readListFromNBT(nbt, "panelPerUser", SkillPanelEntry.RESTORE_FUNC);
			for(SkillPanelEntry entry:list){
				panelDataPerUser.put(entry.uuid, entry.list);
			}
		}

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		UnsagaMod.logger.trace(this.getClass().getName(), "saving...");
		List<SkillPanelEntry> list = this.panelDataPerUser.entrySet().stream().map(in -> new SkillPanelEntry(in.getKey(),in.getValue()))
		.collect(Collectors.toList());
		UtilNBT.writeListToNBT(list, compound, "panelPerUser");
		return compound;
	}





	public static class SkillPanelSyncEvent{
		@SubscribeEvent
		public void onPlayerJoin(EntityJoinWorldEvent e){
			if(e.getEntity() instanceof EntityPlayer && WorldHelper.isServer(e.getWorld())){
				EntityPlayer ep = (EntityPlayer) e.getEntity();
				UnsagaMod.packetDispatcher.sendTo(PacketSyncSkillPanel.create(ep),(EntityPlayerMP) ep);
//
				InventorySkillPanel inv = new InventorySkillPanel();
//				WorldSaveDataSkillPanel data = WorldSaveDataSkillPanel.get(e.getWorld());
				ItemStackList panelList = SkillPanelAPI.getPanelStacks(ep);
				inv.applyItemStackList(panelList);
				MatrixAdapterItemStack matrix = new MatrixAdapterItemStack(inv);
				boolean lineBonus = !matrix.checkLine().isEmpty();
				SkillPanelBonus.applyBonus(matrix, ep, lineBonus);
			}
		}
	}
	public static class SkillPanelEntry implements INBTWritable{

		final UUID uuid;
		final ItemStackList list;
		public SkillPanelEntry(UUID uuid,ItemStackList panels){

			this.uuid = uuid;
			this.list = panels;
		}
		@Override
		public void writeToNBT(NBTTagCompound stream) {
			stream.setUniqueId("uuid", uuid);
			NBTTagList tagList = new NBTTagList();
			this.list.writeToNBT(tagList);
			stream.setTag("items", tagList);
			UnsagaMod.logger.trace(this.getClass().getName(), tagList);

		}

		public static final RestoreFunc<SkillPanelEntry> RESTORE_FUNC = new RestoreFunc<SkillPanelEntry>(){

			@Override
			public SkillPanelEntry apply(NBTTagCompound input) {
				UUID uuid = input.getUniqueId("uuid");
				NBTTagList tagList =UtilNBT.getTagList(input, "items");
				ItemStackList list = ItemStackList.readFromNBT(tagList, 7);
				UnsagaMod.logger.trace(this.getClass().getName(), tagList);
				return new SkillPanelEntry(uuid,list);
			}};
	}
}
