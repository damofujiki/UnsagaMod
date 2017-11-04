package mods.hinasch.unsaga.villager.bartering;

import java.util.OptionalInt;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.capability.StorageDummy;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.material.IUnsagaMaterialSelector;
import mods.hinasch.unsaga.material.MaterialItemAssociatedRegistry;
import mods.hinasch.unsaga.material.UnsagaMaterialTool;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class MerchandiseCapability {

	@CapabilityInject(IMerchandise.class)
	public static Capability<IMerchandise> CAPA;
	public static final String SYNC_ID = "unsagaMerchandise";

	public static ICapabilityAdapterPlan<IMerchandise> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IMerchandise.class;
		}

		@Override
		public Class getDefault() {
			// TODO 自動生成されたメソッド・スタブ
			return DefaultImpl.class;
		}

		@Override
		public IStorage getStorage() {
			// TODO 自動生成されたメソッド・スタブ
			return new StorageDummy();
		}

	};


	public static CapabilityAdapterFrame<IMerchandise> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<IMerchandise> adapter = adapterBase.createChildItem("unsagaMerchandise");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof Item);
//		adapter.setRequireSerialize(true);
	}

	public static class DefaultImpl implements IMerchandise{

		boolean isMerchandise = false;
		OptionalInt price = OptionalInt.empty();

		@Override
		public int getPrice(ItemStack stack) {
			if(!this.price.isPresent()){
				if(UnsagaMaterialTool.adapter.hasCapability(stack)){
					int base = UnsagaMaterialTool.adapter.getCapability(stack).getMaterial().price;
					base = (int)((float)base * 0.2F);
					if(stack.getItem() instanceof IUnsagaMaterialSelector){
						ToolCategory category = ((IUnsagaMaterialSelector)stack.getItem()).getCategory();
						this.price = OptionalInt.of(base + category.getCategoryPrice(base, stack));
					}
				}
				if(MaterialItemAssociatedRegistry.instance().getMaterialFromStack(stack).isPresent()){
					int base = MaterialItemAssociatedRegistry.instance().getMaterialFromStack(stack).get().price;
					base = (int)((float)base * 0.2F);
					this.price = OptionalInt.of(base + (int)((float)base * 0.3F));
				}
				if(AbilityAPI.getAttachedAbilities(stack).size()>0){
					int base = this.price.getAsInt();
					base =(int)(base * (0.05F + 0.15F*(float)AbilityAPI.getAttachedAbilities(stack).size()));
					this.price = OptionalInt.of(this.price.getAsInt()+base);
				}

			}

			return this.price.isPresent() ? this.price.getAsInt() : 0;
		}

		@Override
		public boolean isMerchadise() {
			// TODO 自動生成されたメソッド・スタブ
			return this.isMerchandise;
		}

		@Override
		public void setMerchandise(boolean par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.isMerchandise = par1;
		}

		@Override
		public void setPrice(ItemStack stack) {
			if(!this.price.isPresent()){
				if(UnsagaMaterialTool.adapter.hasCapability(stack)){
					int base = UnsagaMaterialTool.adapter.getCapability(stack).getMaterial().price;
					this.price = OptionalInt.of((int)((float)base * 0.2F));
				}
			}

		}

		@Override
		public boolean canSell(ItemStack stack) {
			if(UnsagaMaterialTool.adapter.hasCapability(stack)){
				return true;
			}
			if(MaterialItemAssociatedRegistry.instance().getMaterialFromStack(stack).isPresent()){
				return true;
			}
			return false;
		}



	}


	public static void registerAttachEvents(){
		adapter.registerAttachEvent((inst,capa,face,ev)->{
//			if(!inst.getPrice().isPresent()){
//				inst.setPrice(stack);
//			}
		});
	}
}
