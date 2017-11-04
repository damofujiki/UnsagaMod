package mods.hinasch.unsaga.util;

import java.util.Arrays;

import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem.ComponentCapabilityAdapterEntity;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class AccessoryHelper {


	@CapabilityInject(IAccessorySlot.class)
	public static Capability<IAccessorySlot> CAPA;

	public static ICapabilityAdapterPlan<IAccessorySlot> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IAccessorySlot.class;
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

	public static CapabilityAdapterFrame base = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterEntity<IAccessorySlot> adapter = base.createChildEntity("accessories");
	static{
		adapter.setPredicate(ev -> ev.getEntity() instanceof EntityPlayer);
		adapter.setRequireSerialize(true);

	}

	public static interface IAccessorySlot {

		public Iterable<ItemStack> getAccessoryList();
		public ItemStack[] getAccessories();
		public ItemStack getAccessory(int slot);
		public void setAccessories(Pair<ItemStack,ItemStack> accessories);
		public void setAccessory(int slot,ItemStack stack);



	}

	public static class DefaultImpl implements IAccessorySlot{

		Pair<ItemStack,ItemStack> accessories = Pair.of(null, null);


		@Override
		public ItemStack[] getAccessories() {
			ItemStack[] stacks = new ItemStack[2];
			stacks[0] = accessories.first();
			stacks[1] = accessories.second();
			return stacks;

		}

		@Override
		public ItemStack getAccessory(int slot) {
			switch(slot){
			case 0:
				return accessories.first();
			case 1:
				return accessories.second();
			}
			return null;
		}


		@Override
		public void setAccessory(int slot, ItemStack newStack) {
			switch(slot){
			case 0:
				this.accessories = Pair.of(newStack,this.accessories.second());
				return;
			case 1:
				this.accessories = Pair.of(this.accessories.first(), newStack);
				return;
			}

			return;
		}

		@Override
		public void setAccessories(Pair<ItemStack, ItemStack> accessories) {
			this.accessories = accessories;

		}

		@Override
		public Iterable<ItemStack> getAccessoryList() {
			// TODO 自動生成されたメソッド・スタブ
			return Arrays.<ItemStack>asList(this.getAccessories());
		}


	}

	public static class Storage extends CapabilityStorage<IAccessorySlot>{

		@Override
		public NBTBase writeNBT(Capability<IAccessorySlot> capability, IAccessorySlot instance, EnumFacing side) {
			NBTTagList tagList = new NBTTagList();

			UtilNBT.writeItemStacksToNBTTag(tagList, instance.getAccessories());
			return tagList;
		}

		@Override
		public void readNBT(Capability<IAccessorySlot> capability, IAccessorySlot instance, EnumFacing side, NBTBase nbt) {
			if(nbt instanceof NBTTagList){

				NBTTagList tagList = (NBTTagList) nbt;
				ItemStack[] stacks = UtilNBT.getItemStacksFromNBT(tagList, 2);
				instance.setAccessories(Pair.of(stacks[0], stacks[1]));
			}

		}

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IAccessorySlot> capability, IAccessorySlot instance,
				EnumFacing side) {
			UtilNBT.writeItemStacksToNBTTag(UtilNBT.newTagList(), instance.getAccessories());

		}

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IAccessorySlot> capability, IAccessorySlot instance,
				EnumFacing side) {
			ItemStack[] stacks = UtilNBT.getItemStacksFromNBT(UtilNBT.newTagList(), 2);
			instance.setAccessories(Pair.of(stacks[0], stacks[1]));

		}

	}
	public static boolean hasCapability(EntityPlayer ep){
		return adapter.hasCapability(ep);
	}
	public static IAccessorySlot getCapability(EntityPlayer ep){
		return adapter.getCapability(ep);
	}
}
