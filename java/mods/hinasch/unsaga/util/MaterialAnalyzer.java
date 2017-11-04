//package mods.hinasch.unsaga.util;
//
//import java.util.List;
//
//import com.google.common.base.Optional;
//
//import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
//import mods.hinasch.lib.capability.CapabilityAdapterFrame;
//import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
//import mods.hinasch.lib.capability.SimpleCapabilityAttachEvent.IPreAttach;
//import mods.hinasch.lib.capability.StorageDummy;
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.UnsagaModCore;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.core.client.gui.GuiSmithUnsaga;
//import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
//import mods.hinasch.unsaga.core.item.weapon.base.Compon
//import mods.hinasch.unsaga.material.UnsagaMaterial;ds.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.villager.smith.ForgingLibrary;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.text.translation.I18n;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.Capability.IStorage;
//import net.minecraftforge.common.capabilities.CapabilityInject;
//import net.minecraftforge.event.AttachCapabilitiesEvent;
//
//public class MaterialAnalyzer {
//
//
//	@CapabilityInject(IMaterialAnalyzer.class)
//	public static Capability<IMaterialAnalyzer> CAPA;
//
//	public static ICapabilityAdapterPlan<IMaterialAnalyzer> capabilityAdapter = new ICapabilityAdapterPlan<IMaterialAnalyzer>(){
//
//		@Override
//		public Capability<IMaterialAnalyzer> getCapability() {
//			// TODO 自動生成されたメソッド・スタブ
//			return CAPA;
//		}
//
//		@Override
//		public Class<IMaterialAnalyzer> getCapabilityClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return IMaterialAnalyzer.class;
//		}
//
//		@Override
//		public Class<? extends IMaterialAnalyzer> getDefault() {
//			// TODO 自動生成されたメソッド・スタブ
//			return DefaultImpl.class;
//		}
//
//		@Override
//		public IStorage<IMaterialAnalyzer> getStorage() {
//			// TODO 自動生成されたメソッド・スタブ
//			return new StorageDummy();
//
//		}
//	};
//
//	public static CapabilityAdapterFrame<IMaterialAnalyzer> adapterBase = UnsagaMod.capabilityFactory.create(capabilityAdapter);
//	public static ComponentCapabilityAdapterItem<IMaterialAnalyzer> adapter = (ComponentCapabilityAdapterItem) adapterBase.createChildItem("materialAnalyzer").setRequireSerialize(false)
//			.setPredicate(in -> true);
//	public static IMaterialAnalyzer getCapability(ItemStack is){
//		if(is!=null){
//			return adapter.getCapability(is);
//		}
//		return null;
//	}
//
//	public static boolean hasCapability(ItemStack is){
//		if(is!=null){
//			return adapter.hasCapability(is);
//		}
//		return false;
//	}
//
//	public static interface IMaterialAnalyzer{
//		public Optional<Integer> getPositiveDamage();
//		public Optional<Integer> getWeight();
//		public Optional<ToolCategory> getCategory();
//		public void setCategory(ToolCategory category);
//		public void setPositiveDamage(int damage);
//		public void setWeight(int weight);
//		public ItemStack getParent();
//		public void setParent(ItemStack is);
//		public Optional<UnsagaMaterial> getMaterial();
//		public void setMaterial(UnsagaMaterial material);
//	}
//
//	public static class DefaultImpl implements IMaterialAnalyzer{
//
//		ItemStack is;
//		Integer weightCache;
//		Integer damage;
//		UnsagaMaterial materialCache;
//		//		Optional<Integer> positiveDamage = Optional.absent();
//		//		Optional<Integer> weight = Optional.absent();
//		//		Optional<ToolCategory> cate = Optional.absent();
//		//		Optional<UnsagaMaterial> material = Optional.absent();
//		@Override
//		public Optional<Integer> getPositiveDamage() {
//
//			Item item = is.getItem();
//			if(this.damage!=null){
//				if(is.getItem().isRepairable()){
//					float f = 1.0F - ((float)is.getItemDamage()/(float)is.getMaxDamage());
//					UnsagaMod.logger.trace("f", f);
//					return Optional.of((int)((float)damage*f));
//				}
//				return Optional.of(damage);
//			}
////			if(item.isRepairable()){
////				this.damage = is.getMaxDamage() - is.getItemDamage();
////				//				return Optional.of(is.getMaxDamage() - is.getItemDamage());
////			}
//
//			if(ForgingLibrary.find(is).isPresent()){
//				this.damage = ForgingLibrary.find(is).get().getPositiveDamage();
//				//				return Optional.of(ForgingLibrary.find(is).get().getPositiveDamage());
//			}
//
//			if(AbilityHelper.hasCapability(is)){
//				this.damage = AbilityHelper.getCapability(is).getUnsagaMaterial().getRepairPoint();
//			}
//			if(this.getParent().getItem()==UnsagaMod.items.misc){
//				this.damage = UnsagaModCore.instance().miscItems.fromMeta(this.getParent().getItemDamage()).repair;
//			}
//			if(this.damage!=null){
//				return Optional.of(damage);
//			}
//			//			if(this.positiveDamage.isPresent()){
//			//				return this.positiveDamage;
//			//			}
//			return Optional.absent();
//		}
//
//		@Override
//		public Optional<Integer> getWeight() {
//
//			if(this.weightCache!=null){
//				return Optional.of(this.weightCache);
//			}else{
//				if(this.materialCache!=null){
//					return Optional.of(this.materialCache.getWeight());
//				}
//			}
//			if(AbilityHelper.hasCapability(is)){
//				this.weightCache = AbilityHelper.getCapability(is).getWeight();
//				//				return Optional.of(AbilityHelperNew.getCapability(is).getWeight());
//			}
//			if(ForgingLibrary.find(is).isPresent()){
//
//				this.weightCache = ForgingLibrary.find(is).get().getMaterial().getWeight();
//				//				return Optional.of(ForgingLibrary.find(is).get().getMaterial().getWeight());
//			}
//			if(this.weightCache!=null){
//				return Optional.of(this.weightCache);
//			}else{
//				if(this.materialCache!=null){
//					return Optional.of(this.materialCache.getWeight());
//				}
//			}
//			//			if(this.weight.isPresent()){
//			//				return this.weight;
//			//			}
//			//			if(this.material.isPresent()){
//			//				return Optional.of(this.material.get().getWeight());
//			//			}
//			return Optional.absent();
//		}
//
//
//
//		@Override
//		public ItemStack getParent() {
//			// TODO 自動生成されたメソッド・スタブ
//			return is;
//		}
//
//		@Override
//		public void setParent(ItemStack is) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.is = is;
//		}
//
//		@Override
//		public void setPositiveDamage(int damage) {
//			// TODO 自動生成されたメソッド・スタブ
//			//			this.positiveDamage = Optional.of(damage);
//		}
//
//		@Override
//		public void setWeight(int weight) {
//			// TODO 自動生成されたメソッド・スタブ
//			//			this.positiveDamage = Optional.of(weight);
//		}
//
//		@Override
//		public Optional<ToolCategory> getCategory() {
//			if(AbilityHelper.hasCapability(is)){
//				return Optional.of(AbilityHelper.getCapability(is).getToolCategory());
//			}
//
//			//			if(this.cate.isPresent()){
//			//				return this.cate;
//			//			}
//			return Optional.absent();
//		}
//
//		@Override
//		public void setCategory(ToolCategory category) {
//			// TODO 自動生成されたメソッド・スタブ
//			//			this.cate = Optional.of(category);
//		}
//
//		@Override
//		public Optional<UnsagaMaterial> getMaterial() {
//			if(this.materialCache!=null){
//				return Optional.of(this.materialCache);
//			}
//
//
//
//			if(ForgingLibrary.find(is).isPresent()){
//				//				UnsagaMod.logger.trace("find", "見つかりました");
//				this.materialCache = ForgingLibrary.find(is).get().getMaterial();
//				//				return Optional.of(ForgingLibrary.find(is).get().getMaterial());
//			}
//			if(AbilityHelper.hasCapability(is)){
//
//				this.materialCache = AbilityHelper.getCapability(is).getUnsagaMaterial(true);
//
//
//				//				return Optional.of(AbilityHelperNew.getCapability(is).getUnsagaMaterial());
//			}
//			if(this.getParent().getItem()==UnsagaMod.items.misc){
//				this.materialCache = UnsagaModCore.instance().miscItems.fromMeta(this.getParent().getItemDamage()).getMaterial();
//			}
//			UnsagaMod.logger.trace("material", this.materialCache);
//			if(this.materialCache!=null){
//				return Optional.of(this.materialCache);
//			}
//			//			if(this.material.isPresent()){
//			//				return material;
//			//			}
//			return Optional.absent();
//		}
//
//		@Override
//		public void setMaterial(UnsagaMaterial material) {
//			// TODO 自動生成されたメソッド・スタブ
//			//			this.material = Optional.of(material);
//		}
//
//	}
//
//	public static void register(){
//		adapterBase.registerCapability();
//		adapter.registerAttachEvent(new IPreAttach<IMaterialAnalyzer,AttachCapabilitiesEvent.Item>(){
//
//			@Override
//			public void preAttach(IMaterialAnalyzer instance, Capability<IMaterialAnalyzer> capability,
//					EnumFacing facing, net.minecraftforge.event.AttachCapabilitiesEvent.Item ev) {
//				instance.setParent(ev.getItemStack());
//
//			}}
//				);
//		EventToolTipUnsaga.list.add(new IComponentDisplayInfo(){
//
//			@Override
//			public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//				if(ClientHelper.getCurrentGui() instanceof GuiSmithUnsaga){
//					return is!=null && hasCapability(is);
//				}
//				return false;
//			}
//
//			@Override
//			public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//				GuiSmithUnsaga gui = (GuiSmithUnsaga) ClientHelper.getCurrentGui();
//				int num = gui.getCurrentCategory();
//				ToolCategory current = ToolCategory.toolArray.get(num);
//				IMaterialAnalyzer capa = getCapability(is);
//				if(capa.getMaterial().isPresent() && capa.getPositiveDamage().isPresent()){
//					String formatStrSub = I18n.translateToLocalFormatted("tips.validmaterial",capa.getMaterial().get().getLocalized(),capa.getPositiveDamage().get());
//					dispList.add(UnsagaTextFormatting.PROPERTY+formatStrSub);
//					if(UnsagaMod.materials.toolAvailableMapGroup.canCraft(current, capa.getMaterial().get())){
//						String formatStr = I18n.translateToLocalFormatted("tips.possible.base",current.getLocalized());
//						dispList.add(UnsagaTextFormatting.PROPERTY+formatStr);
//					}
//				}
//			}}
//				);
//	}
//
//	//	public static class EventCapabilityAttach{
//	//		@SubscribeEvent
//	//		public void onEntityConstrucr(final AttachCapabilitiesEvent.Item e){
//	//
//	////			UnsagaMod.logger.trace("item",e.getItem());
//	//			if(true){
//	//				e.addCapability(new ResourceLocation(UnsagaMod.MODID,"materialAnalyzer"),new ICapabilityProvider(){
//	//
//	//					IMaterialAnalyzer inst = CAPA.getDefaultInstance();
//	//					@Override
//	//					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//	//						// TODO 自動生成されたメソッド・スタブ
//	//						return CAPA!=null && CAPA==capability;
//	//					}
//	//
//	//					@Override
//	//					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//	//
//	////						ForgingValue value = ForgingLibrary.find(e.getItemStack()).get();
//	////						UnsagaMod.logger.trace("material", value.getMaterial());
//	////						UnsagaMod.logger.trace("material", value.getPositiveDamage());
//	//						inst.setParent(e.getItemStack());
//	////						inst.setMaterial(value.getMaterial());
//	////						inst.setPositiveDamage(value.getPositiveDamage());
//	////						if(e.getItem().getClass()==ItemSword.class){
//	////							inst.setCategory(ToolCategory.SWORD);
//	////						}
//	//						return (CAPA!=null && CAPA==capability)?(T)inst : null;
//	//					}}
//	//				);
//	//			}
//	//		}
//	//	}
//	//	public final ItemStack is;
//
//	//	public MaterialAnalyzer(ItemStack is){
//	//		this.is = is;
//	//	}
//	//
//	//	//回復するダメージ量
//	//	public Optional<Integer> getPositiveDamage(){
//	//		Item item = (Item)this.is.getItem();
//	//		if(item.isRepairable()){
//	//			return Optional.of(is.getMaxDamage() - is.getItemDamage());
//	//		}
//	////		if(item instanceof ItemIngotsUnsaga){
//	////			NoFunctionItemRegistry.ItemProperty nofunc = NoFunctionItemRegistry.getInstance().getDataFromMeta(this.is.getItemDamage());
//	////			return Optional.of(nofunc.repair);
//	////		}
//	////		if(Unsaga.materialLibrary.find(this.is).isPresent()){
//	////			MaterialLibraryBook info = (MaterialLibraryBook) MaterialForgingLibrary.getInstance().find(this.is).get();
//	////			return Optional.of(info.damage);
//	////		}
//	//		return Optional.absent();
//	//	}
//	//
//	//	public int getWeight(){
//	////		if(UtilNBT.hasKey(this.is, "weight")){
//	////			return UtilUnsagaTool.getCurrentWeight(this.is);
//	////
//	////		}
//	//		if(this.getMaterial().isPresent()){
//	//			return this.getMaterial().get().getWeight();
//	//		}
//	//		return 0;
//	//	}
//
//	//	public UnsagaEnum.ToolCategory getCategory(){
//	//		if(this.is.getItem() instanceof IUnsagaMaterialTool){
//	//			return ((IUnsagaMaterialTool)is.getItem()).getCategory();
//	//		}
//	//		return null;
//	//	}
//	//	public boolean isValidMaterial(){
//	//		//鎧は作れない
//	//		if(this.is.getItem() instanceof ItemArmor){
//	//			return false;
//	//		}
//	//		//とりあえず素材の情報がとれたらOK
//	//
//	//		if(this.getMaterial().isPresent()){
//	//			return true;
//	//		}
//	//		return false;
//	//	}
//
//	//	protected static MaterialFinder fromToolMaterial = new MaterialFinder(){
//	//
//	//
//	//
//	//		@Override
//	//		public UnsagaMaterial apply(ItemStack is) {
//	////			Item item = (Item)is.getItem();
//	////			if(item instanceof IUnsagaMaterialTool){
//	////				return UtilUnsagaTool.getMaterial(is);
//	////
//	////			}
//	//			return null;
//	//		}
//	//
//	//		@Override
//	//		public int getPriority() {
//	//			// TODO 自動生成されたメソッド・スタブ
//	//			return 10;
//	//		}};
//	//
//	//		protected static MaterialFinder fromAllMaterials = new MaterialFinder(){
//	//
//	//			@Override
//	//			public UnsagaMaterial apply(final ItemStack is) {
//	//				Optional<UnsagaMaterial> rt = ListHelper.stream(UnsagaMaterialRegistry.getInstance().getFlatMaterials()).filter(new Predicate<UnsagaMaterial>(){
//	//
//	//					@Override
//	//					public boolean apply(UnsagaMaterial input) {
//	//						if(input.getAssociatedItemStack().isPresent()){
//	//							ItemStack associated = input.getAssociatedItemStack().get();
//	//							if(associated.isItemEqual(is)){
//	//								return true;
//	//							}
//	//						}
//	//						return false;
//	//					}}).reduce(new BinaryOperator<UnsagaMaterial>(){
//	//
//	//						@Override
//	//						public UnsagaMaterial apply(UnsagaMaterial left,
//	//								UnsagaMaterial right) {
//	//							return left;
//	//						}});
//	//				return rt.isPresent() ? rt.get() : null;
//	//			}
//	//
//	//			@Override
//	//			public int getPriority() {
//	//				// TODO 自動生成されたメソッド・スタブ
//	//				return 8;
//	//			}
//	//
//	//		};
//	//
//	//		protected static MaterialFinder fromNoFuncItems = new MaterialFinder(){
//	//
//	//			@Override
//	//			public UnsagaMaterial apply(ItemStack is) {
//	////				Item item = (Item)is.getItem();
//	////				if(item instanceof ItemIngotsUnsaga){
//	////					ItemProperty nofunc = Unsaga.noFunctionItemProperty.getDataFromMeta(is.getItemDamage());
//	////					return nofunc.getAssociatedMaterial();
//	////
//	////				}
//	//				return null;
//	//			}
//	//
//	//			@Override
//	//			public int getPriority() {
//	//				// TODO 自動生成されたメソッド・スタブ
//	//				return 6;
//	//			}
//	//
//	//		};
//	//
//	//		protected static MaterialFinder fromMaterialLibrary = new MaterialFinder(){
//	//
//	//			@Override
//	//			public UnsagaMaterial apply(ItemStack is) {
//	////				if(Unsaga.materialLibrary.find(is).isPresent()){
//	////					MaterialLibraryBook book = (MaterialLibraryBook) Unsaga.materialLibrary.find(is).get();
//	////					return book.material;
//	////
//	////				}
//	//				return null;
//	//			}
//	//
//	//			@Override
//	//			public int getPriority() {
//	//				// TODO 自動生成されたメソッド・スタブ
//	//				return 4;
//	//			}
//	//
//	//		};
//	//
//	//		public static Set<MaterialFinder> materialFinders = Sets.newHashSet(fromAllMaterials,fromMaterialLibrary,fromNoFuncItems,fromToolMaterial);
//	//
//	//		public Optional<UnsagaMaterial> getMaterial(){
//	//			final ItemStack is = this.is;
//	//			Item item = (Item)this.is.getItem();
//	//
//	//
//	//			Optional<MaterialFinder> finder = ListHelper.stream(materialFinders).filter(new Predicate<MaterialFinder>(){
//	//
//	//				@Override
//	//				public boolean apply(MaterialFinder input) {
//	//					if(input.apply(is) != null){
//	//						return true;
//	//					}
//	//					return false;
//	//				}}).reduce(ListHelper.operatorPriority);
//	//
//	//			UnsagaMaterial material = finder.isPresent() ? finder.get().apply(is) : null;
//	//
//	//
//	//			if(material!=null){
//	//				return Optional.of(material);
//	//			}
//	//
//	//			return Optional.absent();
//	//		}
//
//
//
//
//
//}
