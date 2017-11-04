package mods.hinasch.unsagamagic.spell;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.item.newitem.ItemSpellBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SpellBookCapability {

	@CapabilityInject(ISpellBook.class)
	public static Capability<ISpellBook> CAPA;
	public static final String SYNC_ID = "unsagaSpellBook";

	public static ICapabilityAdapterPlan<ISpellBook> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return ISpellBook.class;
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

	public static CapabilityAdapterFrame<ISpellBook> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<ISpellBook> adapter = adapterBase.createChildItem("unsagaSpellBook");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof ItemSpellBook);
		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements ISpellBook{

		int size = 0;
		int index = 0;
		List<SpellComponent> list = Lists.newArrayList();
		@Override
		public int getCapacity() {
			// TODO 自動生成されたメソッド・スタブ
			return size;
		}

		@Override
		public void setCapacity(int size) {
			// TODO 自動生成されたメソッド・スタブ
			this.size = size;
		}

		@Override
		public void addSpell(Spell spell) {
			// TODO 自動生成されたメソッド・スタブ
			this.addSpell(new SpellComponent(spell,1.0F,1.0F,false));
		}

		@Override
		public List<SpellComponent> getRawSpells() {
			// TODO 自動生成されたメソッド・スタブ
			return list;
		}

		@Override
		public Optional<SpellComponent> getCurrentSpell() {
			this.rangeCheck();
			return !this.list.isEmpty() ? Optional.of(this.list.get(index) ): Optional.empty();
		}

		private void rangeCheck(){
			if(this.index>=this.getRawSpells().size()){
				this.index = 0;
			}
		}

		@Override
		public void nextSpell() {
			// TODO 自動生成されたメソッド・スタブ
			this.index += 1;
			this.rangeCheck();
		}

		@Override
		public void clear() {
			// TODO 自動生成されたメソッド・スタブ
			this.list.clear();
		}

		@Override
		public boolean isSpellFull() {
			// TODO 自動生成されたメソッド・スタブ
			return this.list.size()>=this.getCapacity();
		}

		@Override
		public int getIndex() {
			// TODO 自動生成されたメソッド・スタブ
			return this.index;
		}

		@Override
		public void setIndex(int par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.index = par1;
		}

		@Override
		public void setSpells(List<SpellComponent> list) {
			// TODO 自動生成されたメソッド・スタブ
			this.list = list;
		}

		@Override
		public boolean isCurrentSpellSame(Spell spell) {
			// TODO 自動生成されたメソッド・スタブ
			if(this.getCurrentSpell().isPresent()){
				return this.getCurrentSpell().get().getSpell()==spell;
			}
			return false;
		}

		@Override
		public NBTTagCompound getSendingData() {
			return (NBTTagCompound) CAPA.getStorage().writeNBT(CAPA, this, null);
		}

		@Override
		public void catchSyncData(NBTTagCompound nbt) {
			CAPA.getStorage().readNBT(CAPA, this, null, nbt);

		}

		@Override
		public void onPacket(PacketSyncCapability message, MessageContext ctx) {
			int order = message.getArgs().getInteger("order");
			if(order==1){
				if(ctx.side.isServer()){
					EntityPlayer player = ctx.getServerHandler().playerEntity;
					ItemStack stack = player.getHeldItemMainhand();
					if(ItemUtil.isItemStackPresent(stack) && stack.getItem() instanceof ItemSpellBook){
						SpellBookCapability.changeSpell(stack);
					}
				}
			}


		}

		@Override
		public String getIdentifyName() {
			// TODO 自動生成されたメソッド・スタブ
			return SYNC_ID;
		}

		@Override
		public void addSpell(SpellComponent spell) {
			if(!this.isSpellFull()){
				this.list.add(spell);
			}
		}

		@Override
		public SpellComponent getSpell(int index) {
			// TODO 自動生成されたメソッド・スタブ
			return this.list.get(index);
		}

		@Override
		public int getCurrentIndex() {
			// TODO 自動生成されたメソッド・スタブ
			return this.index;
		}

		@Override
		public List<Spell> getSpells() {
			// TODO 自動生成されたメソッド・スタブ
			return this.getRawSpells().stream().map(in -> in.getSpell()).collect(Collectors.toList());
		}

	}

	public static void changeSpell(ItemStack stack){
		if(adapter.hasCapability(stack)){
			adapter.getCapability(stack).nextSpell();
		}
	}
	public static void register(){
		adapter.registerAttachEvent();
		PacketSyncCapability.registerSyncCapability(SYNC_ID, CAPA);
	}
	public static class Storage extends CapabilityStorage<ISpellBook>{

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<ISpellBook> capability, ISpellBook instance,
				EnumFacing side) {
			// TODO 自動生成されたメソッド・スタブ
			comp.setByte("index",(byte)instance.getIndex());
			comp.setByte("size", (byte)instance.getCapacity());
			UtilNBT.writeListToNBT(instance.getRawSpells(), comp, "spells");
		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<ISpellBook> capability, ISpellBook instance,
				EnumFacing side) {
			if(comp.hasKey("index")){
				instance.setIndex(comp.getByte("index"));
			}
			if(comp.hasKey("size")){
				instance.setCapacity(comp.getByte("size"));
			}
			if(comp.hasKey("spells")){
				instance.setSpells(UtilNBT.readListFromNBT(comp, "spells", SpellComponent.RESTORE));
			}
		}

	}
}
