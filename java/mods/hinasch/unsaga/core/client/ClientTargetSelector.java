package mods.hinasch.unsaga.core.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.net.packet.PacketSyncServerTargetHolder;
import mods.hinasch.unsaga.status.TargetHolderCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ClientTargetSelector {

	private int currentIndex = -1;
	private List<EntityLivingBase> targetList;
	private NavigableMap<Integer,EntityLivingBase> targetMap = new TreeMap<Integer,EntityLivingBase>();
	private double defaultRange = UnsagaMod.configHandler.getDefaultTargettingRange();
	private double defaultRangeV = UnsagaMod.configHandler.getDefaultTargettingRangeVertical();


	public ClientTargetSelector(){
		this.targetList = new ArrayList();
	}

	public void next(){
		this.gatherLivings();
		if(!this.targetMap.isEmpty()){
			this.findNextIndex();
		}

	}
	public int getCurrentIndex(){
		return this.currentIndex;
	}
	protected void gatherLivings(){
		UnsagaMod.logger.trace("[client target selector]gathering entities...");
		World world = ClientHelper.getWorld();
		final EntityPlayer clientPlayer = ClientHelper.getPlayer();
		List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class,clientPlayer.getEntityBoundingBox().expand(defaultRange, defaultRangeV, defaultRange) ,input -> input!=clientPlayer);
		if(!list.isEmpty()){
			this.targetMap.clear();
			list.stream().forEach(input -> targetMap.put(input.getEntityId(), input));

		}

	}

	protected void findNextIndex(){
		Entry<Integer,EntityLivingBase> entityEntry = targetMap.higherEntry(this.currentIndex);
		if(entityEntry!=null){
			this.currentIndex = entityEntry.getKey();
			this.updateTargetHolder(currentIndex);
			UnsagaMod.logger.trace("[client target selector]targetting current index:"+this.currentIndex);
			return;
		}
		this.currentIndex = -1;
		if(!this.targetMap.isEmpty()){
			Entry<Integer,EntityLivingBase> resetEntry = targetMap.higherEntry(this.currentIndex);
			this.currentIndex = resetEntry.getKey();
			this.updateTargetHolder(currentIndex);
			UnsagaMod.logger.trace("[client target selector]targetting has reset.:"+this.currentIndex);
		}

	}

	protected void updateTargetHolder(int targetid){

		if(ClientHelper.getWorld()!=null){
			Entity target = ClientHelper.getWorld().getEntityByID(targetid);
			if(target instanceof EntityLivingBase){
				if(ClientHelper.getPlayer()!=null && TargetHolderCapability.adapter.hasCapability(ClientHelper.getPlayer())){
					TargetHolderCapability.adapter.getCapability(ClientHelper.getPlayer()).updateTarget((EntityLivingBase) target);
					UnsagaMod.packetDispatcher.sendToServer(PacketSyncServerTargetHolder.create((EntityLivingBase) target));
				}
			}

		}


	}
}
