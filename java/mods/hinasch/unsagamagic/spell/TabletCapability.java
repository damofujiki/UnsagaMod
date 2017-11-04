package mods.hinasch.unsagamagic.spell;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.item.newitem.ItemTablet;
import mods.hinasch.unsagamagic.item.newitem.ItemTablet.DecipheringPair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TabletCapability {
	@CapabilityInject(ITablet.class)
	public static Capability<ITablet> CAPA;
	public static final String SYNC_ID = "unsagaTablet";

	public static ICapabilityAdapterPlan<ITablet> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return ITablet.class;
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

	public static CapabilityAdapterFrame<ITablet> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<ITablet> adapter = adapterBase.createChildItem("unsagaAbilityAttachable");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof ItemTablet);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements ITablet{

		boolean init = false;
		Map<Spell,Integer> progressMap = Maps.newHashMap();
		MagicTablet tablet = TabletRegistry.instance().empty;
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
		public MagicTablet getTabletData() {
			// TODO 自動生成されたメソッド・スタブ
			return tablet;
		}

		@Override
		public void setTabletData(MagicTablet tablet) {
			// TODO 自動生成されたメソッド・スタブ
			this.tablet = tablet;
		}


		@Override
		public void progressDecipher(Spell spell, int progress) {
			int p = 0;
			if(this.progressMap.containsKey(spell)){
				p += this.progressMap.get(spell);
			}
			if(p + progress>100){
				this.progressMap.put(spell, 100);
			}else{
				this.progressMap.put(spell, p + progress);
			}

//			UnsagaMod.logger.trace("spell", this.progressMap.get(spell));
		}

		@Override
		public Map<Spell, Integer> getDecipheringProgressMap() {
			// TODO 自動生成されたメソッド・スタブ
			return this.progressMap;
		}

		@Override
		public void setDecipheringProgressMap(Map<Spell, Integer> progressList) {
			// TODO 自動生成されたメソッド・スタブ
			this.progressMap = progressList;
		}

		@Override
		public List<DecipheringPair> getAsList() {
			return this.progressMap.entrySet().stream().map(in -> new DecipheringPair(in.getKey(),in.getValue())).collect(Collectors.toList());
		}

		@Override
		public int getProgress(Spell spell) {
			if(this.progressMap.containsKey(spell)){
				return this.progressMap.get(spell);
			}
			return 0;
		}


	}

	public static class Storage extends CapabilityStorage<ITablet>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<ITablet> capability, ITablet instance, EnumFacing side) {
			// TODO 自動生成されたメソッド・スタブ
			comp.setString("tablet", instance.getTabletData().getKey().getResourcePath());
			List<DecipheringPair> list = instance.getAsList();
			if(!list.isEmpty()){
				UtilNBT.writeListToNBT(list, comp, "progress");
			}

		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<ITablet> capability, ITablet instance, EnumFacing side) {
			// TODO 自動生成されたメソッド・スタブ
			if(comp.hasKey("progress")){
				List<DecipheringPair> list = UtilNBT.readListFromNBT(comp, "progress", DecipheringPair.RESTORE);
				Map<Spell,Integer> map = Maps.newHashMap();
				list.forEach(in ->{
					map.put(in.getSpell(), in.getProgress());
				});
				instance.setDecipheringProgressMap(map);
			}
			if(comp.hasKey("tablet")){
				instance.setTabletData(TabletRegistry.instance().get(comp.getString("tablet")));
			}
		}

	}

	public static void registerEvents(){
		adapter.registerAttachEvent((inst,capa,face,ev)->{
			if(ev.getObject() instanceof ItemTablet){
				if(!inst.hasInitialized()){

				}
			}
		});
	}
}
