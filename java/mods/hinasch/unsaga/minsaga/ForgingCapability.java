package mods.hinasch.unsaga.minsaga;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.minsaga.MinsagaForging.Ability;
import mods.hinasch.unsaga.minsaga.MinsagaForging.Material;
import mods.hinasch.unsaga.minsaga.MinsagaForging.ModifierPair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ForgingCapability {

	/** 最大改造回数*/
	public static final int MAX_FORGE = 4;
	public static class DefaultImpl implements IMinsagaForge{

		List<ForgeAttribute> attributes = Lists.newArrayList();



		@Override
		public void addNewForge(Material material) {
			if(attributes==null){
				this.attributes = Lists.newArrayList();

			}
			this.removeUnfinished();
			/** 改造限界の場合は上書き*/
			if(this.getForgedCount()==MAX_FORGE){
				this.attributes.remove(MAX_FORGE-1);
			}
			ForgeAttribute newForge = new ForgeAttribute();
			newForge.setForgedMaterial(material);
			newForge.setFittingProgress(0);
			newForge.setMaxFittingProgress(PROGRESS_BASE * (this.getForgedCount()+1));
			if(this.getForgedCount()>=MAX_FORGE){
				this.attributes.add(MAX_FORGE-1, newForge);
			}else{
				this.attributes.add(newForge);
			}


		}

		@Override
		public int getForgedCount() {
			if(this.attributes==null){
				return 0;
			}
			return this.attributes.size();
		}

		@Override
		public List<ForgeAttribute> getForgingAttribute() {
			// TODO 自動生成されたメソッド・スタブ
			return this.attributes;
		}

		@Override
		public boolean isForged() {
			return this.attributes!=null && !this.attributes.isEmpty();
		}

		@Override
		public void removeUnfinished() {
			if(this.attributes!=null && !this.attributes.isEmpty()){
				this.attributes.removeIf((forge)->!forge.hasFinishedFitting());
			}

		}

		@Override
		public void setForgingAttribute(List<ForgeAttribute> list) {
			this.attributes = list;
		}

		@Override
		public ForgeAttribute getCurrentForge() {
			if(this.attributes!=null && !this.attributes.isEmpty()){
				return this.attributes.get(this.getForgedCount()-1);

			}
			return null;
		}

		@Override
		public float getAttackModifier() {
			if(this.isForged()){
				return (float) this.getForgingAttribute().stream().mapToDouble(in -> in.getForgedMaterial().getAttackModifier()).sum();
			}
			return 0;
		}

		@Override
		public ModifierPair getArmorModifier() {
			if(!this.isForged()){
				return new ModifierPair(0.0F,0.0F);
			}
			float melee = (float) this.getForgingAttribute().stream().mapToDouble(in -> in.getForgedMaterial().getArmorModifier().melee()).sum();
			float magic = (float) this.getForgingAttribute().stream().mapToDouble(in -> in.getForgedMaterial().getArmorModifier().magic()).sum();
			return new ModifierPair(melee,magic);
		}

		@Override
		public float getEfficiencyModifier() {
			if(this.isForged()){
				return (float) this.getForgingAttribute().stream().mapToDouble(in -> in.getForgedMaterial().getEfficiencyModifier()).sum();
			}
			return 0;
		}

		@Override
		public int getCostModifier() {
			if(this.isForged()){
				return this.getForgingAttribute().stream().mapToInt(in -> in.getForgedMaterial().getCostModifier()).sum();
			}
			return 0;
		}

		@Override
		public int getDurabilityModifier() {
			if(this.isForged()){
				return this.getForgingAttribute().stream().mapToInt(in -> in.getForgedMaterial().getDurabilityModifier()).sum();
			}
			return 0;
		}

		@Override
		public List<Ability> getAbilities() {
			// TODO 自動生成されたメソッド・スタブ
			return this.getForgingAttribute().stream().flatMap(in -> {
				List<Ability> list = Lists.newArrayList();
				if(in.getForgedMaterial().hasAbilities()){
					list.addAll(in.getForgedMaterial().getAbilities());
				}
				return list.stream();
			}).collect(Collectors.toList());
		}

		@Override
		public int getWeightModifier() {
			if(this.isForged()){
				return this.getForgingAttribute().stream().mapToInt(in -> in.getForgedMaterial().getWeightModifier()).sum();
			}
			return 0;
		}






	}
	public static interface IStatusModifier{
		public float getAttackModifier();
		public ModifierPair getArmorModifier();
		public float getEfficiencyModifier();
		public int getCostModifier();
		public int getDurabilityModifier();
		public int getWeightModifier();
	}
	public static class ForgeAttribute implements INBTWritable{

		public static RestoreFunc<ForgeAttribute> RESTORE_FUNC = (input)->{
			ForgeAttribute at = new ForgeAttribute();
			at.setForgedMaterial(MinsagaForging.instance().getMaterial(input.getString("material")));
			at.setFittingProgress(input.getInteger("progress"));
			at.setMaxFittingProgress(input.getInteger("max"));

			return at;
		};
		private MinsagaForging.Material forgedMaterial;
		private int maxSuitCount = 0;
		private int suitCount = 0;
		public int getFittingProgress() {
			return suitCount;
		}

		public boolean hasFinishedFitting(){
			return this.getFittingProgress() >= this.getMaxFittingProgress();
		}
		public MinsagaForging.Material getForgedMaterial() {
			return forgedMaterial;
		}
		public int getMaxFittingProgress() {
			return maxSuitCount;
		}

		public void setFittingProgress(int suitCount) {
			this.suitCount = suitCount;
		}
		public void setForgedMaterial(MinsagaForging.Material forgedMaterial) {
			this.forgedMaterial = forgedMaterial;
		}
		public void setMaxFittingProgress(int maxSuitCount) {
			this.maxSuitCount = maxSuitCount;
		}



		@Override
		public void writeToNBT(NBTTagCompound stream) {
			stream.setString("material", this.forgedMaterial.getName());
			stream.setInteger("progress", this.getFittingProgress());
			stream.setInteger("max", this.getMaxFittingProgress());

		}

	}
	public static interface IMinsagaForge extends IStatusModifier{

		public List<MinsagaForging.Ability> getAbilities();
		public float getAttackModifier();
		public ModifierPair getArmorModifier();
		public float getEfficiencyModifier();
		public ForgeAttribute getCurrentForge();
		public void addNewForge(MinsagaForging.Material material);
		public int getForgedCount();
		public List<ForgeAttribute> getForgingAttribute();
		public boolean isForged();
		public void removeUnfinished();
		public void setForgingAttribute(List<ForgeAttribute> list);
	}

	public static class Storage extends CapabilityStorage<IMinsagaForge>{

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IMinsagaForge> capability, IMinsagaForge instance,
				EnumFacing side) {
			if(comp.hasKey("forgeAttribute")){
				instance.setForgingAttribute(UtilNBT.readListFromNBT(comp, "forgeAttribute", ForgeAttribute.RESTORE_FUNC));
			}

		}

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IMinsagaForge> capability, IMinsagaForge instance,
				EnumFacing side) {
			if(instance.getForgingAttribute()!=null && !instance.getForgingAttribute().isEmpty()){
				UtilNBT.writeListToNBT(instance.getForgingAttribute(), comp, "forgeAttribute");
			}

		}

	}
	/**改造を重ねるごとに50*levelする */
	public static final int PROGRESS_BASE = 50;
	public static final int EXP_BASE = 5;

	@CapabilityInject(IMinsagaForge.class)
	public static Capability<IMinsagaForge> CAPA;

	public static ICapabilityAdapterPlan<IMinsagaForge> iCapaAdapter = new ICapabilityAdapterPlan<IMinsagaForge>(){

		@Override
		public Capability<IMinsagaForge> getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class<IMinsagaForge> getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IMinsagaForge.class;
		}

		@Override
		public Class<? extends IMinsagaForge> getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage<IMinsagaForge> getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new Storage();
		}};

	public static CapabilityAdapterFrame<IMinsagaForge> base = UnsagaMod.capabilityFactory.create(iCapaAdapter);

	public static ComponentCapabilityAdapterItem<IMinsagaForge> adapter = base.createChildItem("minsagaForge");
	static{
		adapter.setPredicate((ev)->ev.getItem().isRepairable());
		adapter.setRequireSerialize(true);
	}


}
