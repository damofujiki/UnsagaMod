package mods.hinasch.unsaga.core.entity;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.capability.ISyncCapability;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EntityStateCapability {

	@CapabilityInject(IEntityState.class)
	public static Capability<IEntityState> CAPA;
	public static final String SYNC_ID = "unsagaStates";

	public static ICapabilityAdapterPlan<IEntityState> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IEntityState.class;
		}

		@Override
		public Class getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new Storage();
		}

	};

	public static CapabilityAdapterFrame<IEntityState> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IEntityState> adapter = adapterBase.createChildEntity("unsagaState");

	static{
		Set<Predicate> arrowEntities = Sets.newHashSet(in -> in instanceof EntityLivingBase
				,in -> in instanceof EntityArrow);
		adapter.setPredicate(ev -> arrowEntities.stream().anyMatch(in -> in.test(ev.getObject())));
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IEntityState{

		public Map<StateProperty,State> stateList = Maps.newHashMap();
		boolean init = false;
		public void init(){
			StateRegistry.instance().getProperties().forEach(in ->{
				stateList.put(in, in.createState());
			});
		}

		public State getState(StateProperty prop){
			return this.stateList.get(prop);
		}

		@Override
		public boolean hasInitialized() {
			// TODO 自動生成されたメソッド・スタブ
			return init;
		}

		@Override
		public void setInitialized(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.init = par1;
		}

		@Override
		public Set<StateProperty> getStateProperties() {
			// TODO 自動生成されたメソッド・スタブ
			return this.stateList.keySet();
		}
		@Override
		public void setState(StateProperty prop, State state) {
			this.stateList.put(prop, state);

		}

		@Override
		public NBTTagCompound getSendingData() {
			// TODO 自動生成されたメソッド・スタブ
			return UtilNBT.compound();
		}

		@Override
		public void catchSyncData(NBTTagCompound nbt) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void onPacket(PacketSyncCapability message, MessageContext ctx) {
			int id = message.getArgs().getInteger("entityid");
			String name = message.getArgs().getString("name");
			Entity entity = ClientHelper.getWorld().getEntityByID(id);
			StateProperty stateProp = StateRegistry.instance().get(name);
			if(entity!=null && stateProp!=null && adapter.hasCapability(entity)){
				State state = adapter.getCapability(entity).getState(stateProp);
				if(state instanceof ISyncCapability){
					((ISyncCapability) state).onPacket(message, ctx);
				}
			}


		}

		@Override
		public String getIdentifyName() {
			// TODO 自動生成されたメソッド・スタブ
			return SYNC_ID;
		}

	}


	public static class Storage extends CapabilityStorage<IEntityState>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IEntityState> capability, IEntityState instance,
				EnumFacing side) {
			instance.getStateProperties().forEach(in ->{
//				UnsagaMod.logger.trace(this.getClass().getName(),"save state:"+in.getName());
				State state = instance.getState(in);
				if(state.isRequireSerialize){
					NBTTagCompound child = UtilNBT.compound();
					state.writeNBT(child);
					comp.setTag(in.getKey().getResourcePath(), child);
				}

			});

		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IEntityState> capability, IEntityState instance,
				EnumFacing side) {
			StateRegistry.instance().getProperties().forEach(in ->{
//				UnsagaMod.logger.trace(this.getClass().getName(),"load state:"+in.getName());
				if(comp.hasKey(in.getKey().getResourcePath())){
					NBTTagCompound child = comp.getCompoundTag(in.getKey().getResourcePath());
					State state = in.createState();
					if(state.isRequireSerialize){
						state.readNBT(child);
						instance.setState(in, state);
					}


				}
			});

		}

	}

	public static void register(){
		PacketSyncCapability.registerSyncCapability(SYNC_ID, CAPA);
		adapter.registerAttachEvent((inst,capa,facing,ev)->{
			if(!inst.hasInitialized()){
				inst.init();
				inst.setInitialized(true);
			}
		});
	}
}
