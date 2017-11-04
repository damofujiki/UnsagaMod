//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.OptionalInt;
//import java.util.Random;
//import java.util.Set;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//import com.google.common.base.Function;
//import com.google.common.collect.HashMultimap;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import com.google.common.collect.Multimap;
//
//import joptsimple.internal.Strings;
//import mods.hinasch.lib.core.HSLib;
//import mods.hinasch.lib.item.ItemPropertyGetterWrapper;
//import mods.hinasch.lib.primitive.CollectorUtil;
//import mods.hinasch.lib.primitive.ListHelper;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.abnew.AbilityRegistry;
//import mods.hinasch.unsaga.material.UnsagaMaterial;
//import mods.hinasch.unsaga.material.UnsagaMaterials;
//import mods.hinasch.unsaga.util.ToolCategory;
//import mods.hinasch.unsaga.util.UnsagaTextFormatting;
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.creativetab.CreativeTabs;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.SharedMonsterAttributes;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.EnumAction;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.stats.StatList;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.EnumActionResult;
//import net.minecraft.util.EnumFacing;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.translation.I18n;
//import net.minecraft.world.World;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//
///**
// * ツール類に関してをまとめたもの。
// * ツールにインスタンスを持たせる
// *
// */
//public class ComponentUnsagaTool {
//
//	private AbilityRegistry registry = UnsagaMod.abilities;
//
//	public static Random random = new Random();
//
//	public static final UUID WEIGHT_EFFECTED_SPEED = UUID.fromString("a46c987d-cb19-4f36-bb57-25098ce49450");
//	private static final int WHITE = 0xFFFFFF;
//	public static final int THRESHOLD_WEIGHT = 5;
//	public static final double SPEED_MODIFIER = 20.0D;
//	public static final int WEAPON_MAXABILITY = 1;
//
//	public static final ComponentDisplayAbility DISPLAY_ABILITY = new ComponentDisplayAbility();
//
//	protected  Map<Ability,Integer> bowActionMap = Maps.newHashMap();
//
//	boolean isBlockable = false;
//	public boolean isBlockable() {
//		return isBlockable;
//	}
//
//	public void setBlockable(boolean isBlockable) {
//		this.isBlockable = isBlockable;
//	}
//
//	List<IComponentDisplayInfo> displayInfoComponents = Lists.newArrayList();
//
//	public float func_185059_b(int p_185059_0_)
//	{
//		float f = (float)p_185059_0_ / 20.0F;
//		f = (f * f + f * 2.0F) / 3.0F;
//
//		if (f > 2.0F)
//		{
//			f = 2.0F;
//		}
//
//		return f;
//	}
//
//	/**
//	 * １アイテムでバリアントを変えたかった時のなごり
//	 * @param item
//	 */
//	public void addPropertyOverride(Item item){
//        item.addPropertyOverride(this.getMaterial().getKey(), ItemPropertyGetterWrapper.of((stack,world,entity)->1));
//        String variant = this.getMaterial().getVariantProperty(getCategory());
//        if(variant!=null){
//            item.addPropertyOverride(new ResourceLocation(variant), ItemPropertyGetterWrapper.of((stack,world,entity)->1));
//        }
//        if(this.getMaterial().isChild()){
//        	String parentVariant = this.getMaterial().getParentMaterial().getVariantProperty(getCategory());
//        	if(parentVariant!=null){
//                item.addPropertyOverride(new ResourceLocation(parentVariant),ItemPropertyGetterWrapper.of((stack,world,entity)->1));
//        	}
//            item.addPropertyOverride(this.getMaterial().getParentMaterial().getKey(), ItemPropertyGetterWrapper.of((stack,world,entity)->1));
//        }
//	}
//	public ActionResult<Float> onPlayerStoppedUsing(Item parent,ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
//	{
//		if (entityLiving instanceof EntityPlayer)
//		{
//			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
//			//            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.infinity, stack) > 0;
//			ItemStack itemstack = entityLiving.getHeldItemMainhand();
//
//			int i = parent.getMaxItemUseDuration(stack) - timeLeft;
//			//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, (EntityPlayer)entityLiving, i, itemstack != null || flag);
//			if (i < 0) return new ActionResult(EnumActionResult.FAIL, 0.0F);
//
//			if (itemstack != null)
//			{
//				//                if (itemstack == null)
//				//                {
//				//                    itemstack = new ItemStack(Items.arrow);
//				//                }
//
//				float f = func_185059_b(i);
//
//				if ((double)f >= 0.1D)
//				{
//					if(entityLiving instanceof EntityPlayer){
//						((EntityPlayer) entityLiving).addStat(StatList.getObjectUseStats(parent));
//					}
//					return new ActionResult(EnumActionResult.SUCCESS, f);
//
//
//
//				}
//			}
//		}
//		return new ActionResult(EnumActionResult.PASS, 0.0F);
//	}
//
//	public int getMaxItemUseDuration(ItemStack stack)
//	{
//		OptionalInt rt = bowActionMap.keySet().stream().filter(ab -> AbilityHelper.hasAbilityFromItemStack(stack,ab)).mapToInt(ab -> bowActionMap.get(ab))
//				.findFirst();
//		return rt.isPresent() ? rt.getAsInt() : 0;
//	}
//
//	public boolean hasBowActionWaza(ItemStack stack){
//		return bowActionMap.keySet().stream().anyMatch(ab -> AbilityHelper.hasAbilityFromItemStack(stack,ab));
//	}
//	public EnumAction getItemUseAction(ItemStack stack)
//	{
//		return bowActionMap.keySet().stream().anyMatch(ab -> AbilityHelper.hasAbilityFromItemStack(stack,ab))?
//				EnumAction.BOW : EnumAction.NONE;
//
//	}
//	public Map<Ability,Integer> getBowActionMap(){
//		return this.bowActionMap;
//	}
//	public UnsagaMaterial getMaterial() {
//		return uns;
//	}
//
//	public ToolCategory getCategory() {
//		return category;
//	}
//
//	private final UnsagaMaterial uns;
//	private final ToolCategory category;
//	public int getMaxAbilitySize() {
//		return maxAbilitySize;
//	}
//
//	public ComponentUnsagaTool setMaxAbilitySize(int maxAbilitySize) {
//		this.maxAbilitySize = maxAbilitySize;
//		return this;	}
//
//	private int maxAbilitySize;
//
//	public ComponentUnsagaTool(UnsagaMaterial material,ToolCategory category){
//
//
//		this.uns = material;
//		this.category = category;
//		this.maxAbilitySize = WEAPON_MAXABILITY;
//
//	}
//
//	public Optional<WazaPerformer> getBowSkillInvoker(final ItemStack stack,final World worldIn,final EntityLivingBase entityLiving,final float f){
//
//        Optional<WazaPerformer> skilleffect = (Optional<WazaPerformer>) WazaGroupBow.getAssociatedMap().keySet().stream()
//        		.filter(input -> WazaGroupBow.getWazaEffectFromTag(input)!=null)
//        		.map(input -> WazaGroupBow.getWazaEffectFromTag(input))
//        		.filter(input -> AbilityHelper.hasAbilityFromItemStack(stack,input.getWaza()) && entityLiving.isSneaking())
//        		.map(input -> new WazaPerformer(worldIn,entityLiving , input.getWaza(), stack).setCharge(f)).findFirst();
//        return skilleffect;
//	}
//    public net.minecraftforge.common.capabilities.ICapabilityProvider initWeaponCapabilities(ItemStack stack, NBTTagCompound nbt)
//    {
//    	class ProviderUnsagaWeapon implements ICapabilitySerializable<NBTBase>{
//
//    		IUnsagaPropertyItem instanceMaterial = UnsagaCapability.unsagaPropertyItem().getDefaultInstance();
//			@Override
//			public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
//				if(UnsagaCapability.unsagaPropertyItem()!=null){
//					return capability==UnsagaCapability.unsagaPropertyItem();
//				}
//				return false;
//			}
//
//			@Override
//			public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
//				if(UnsagaCapability.unsagaPropertyItem()!=null){
//					if(capability==UnsagaCapability.unsagaPropertyItem()){
//						instanceMaterial.setUnsagaMaterial(uns);
////						UnsagaMod.logger.trace("init", uns);
//						instanceMaterial.setToolCategory(category);
//						instanceMaterial.initMaxAbility(getMaxAbilitySize());
//						if(!instanceMaterial.hasInitialized()){
//
//							instanceMaterial.setWeight(uns.getWeight());
//
//
//
//							if(uns==UnsagaMod.materials.failed){
//								Set<UnsagaMaterial> set = UnsagaMaterials.getInstance().toolAvailableMapGroup.getAvailableAsFlatSet(category);
//								UnsagaMaterial subm= UnsagaMod.materials.getFlatMaterials().stream()
//										.filter(input -> input.getRank()<=9).collect(CollectorUtil.toRandomGet())
//										.diff(set).getWeighedRandom(random, input ->ItemSelectorGroup.calcWeightFromRank(input.getRank(),50), UnsagaMod.materials.feather);
//								instanceMaterial.setUnsagaSubMaterial(com.google.common.base.Optional.of(subm));
//								instanceMaterial.setWeight(instanceMaterial.getSubMaterial().get().getWeight());
//							}
//							instanceMaterial.setInitialized(true);
//						}
//
//						return (T)instanceMaterial;
//
//					}
//				}
//				return null;
//			}
//
//			@Override
//			public NBTBase serializeNBT() {
//
//
//				return UnsagaCapability.unsagaPropertyItem().getStorage().writeNBT(UnsagaCapability.unsagaPropertyItem(), instanceMaterial, null);
//			}
//
//			@Override
//			public void deserializeNBT(NBTBase nbt) {
//
//				UnsagaCapability.unsagaPropertyItem().getStorage().readNBT(UnsagaCapability.unsagaPropertyItem(), instanceMaterial, null, nbt);
//			}
//
//    	}
//        return new ProviderUnsagaWeapon();
//    }
//
//
//    public Multimap getItemAttributeModifiers(EntityEquipmentSlot slot,float weaponDamage,double baseSpeed,ItemStack stack,UUID uuidDamage,UUID uuidSpeed)
//    {
//        Multimap<String, AttributeModifier> multimap =  HashMultimap.create();
//
//        if (slot == EntityEquipmentSlot.MAINHAND)
//        {
//            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(uuidDamage, "Weapon modifier", (double)weaponDamage, 0));
//            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(uuidSpeed, "Weapon modifier", baseSpeed, 0));
//            if(AbilityHelper.hasCapability(stack)){
//
//            	int weight = AbilityHelper.getCapability(stack).getWeight();
//
//            	if(weight>0){
//            		double mod = getSpeedModifierFromWeight(weight);
//                	multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(WEIGHT_EFFECTED_SPEED, "Weapon modifier", mod, 0));
//
//            	}
//
//            }
//
//        }
//
//        return multimap;
//    }
//
//    /** これは違うとこに動かしてもいい*/
//    public static void refleshWeightModifier(ItemStack newstack,int weight){
//		double mod = ComponentUnsagaTool.getSpeedModifierFromWeight(weight);
//		newstack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName()
//				, new AttributeModifier(ComponentUnsagaTool.WEIGHT_EFFECTED_SPEED, "Weapon modifier", mod, 0));
//    }
//    public static double getSpeedModifierFromWeight(int weight){
//    	return  -((double)weight)/SPEED_MODIFIER;
//    }
//    public int getColorFromItemStack(final ItemStack par1ItemStack, int pass)
//    {
////    	boolean multipass = new Function<ItemStack,Boolean>(){
////			@Override
////			public Boolean apply(ItemStack input) {
//////		    	if(par1ItemStack.getItem() instanceof ItemBowUnsaga){
//////		    		return false;
//////		    	}
//////		    	if(par1ItemStack.getItem() instanceof ItemStaffUnsaga){
//////		    		UnsagaMaterial mate = UtilUnsagaTool.getMaterial(par1ItemStack);
//////		    		if(mate.isChild()){
//////		    			if(mate.getParentMaterial()==Unsaga.materials.categorywood){
//////		    				return false;
//////		    			}
//////		    		}
//////
//////		    	}
//////		    	if(par1ItemStack.getItem() instanceof ItemAccessory){
//////		    		return false;
//////		    	}
////				return true;
////			}
////		}.apply(par1ItemStack);
//
//		boolean multipass = true;
//
//
//        if((multipass && pass==0)||(!multipass)){
//        	if(AbilityHelper.hasCapability(par1ItemStack)){
//        		IUnsagaPropertyItem capa = AbilityHelper.getCapability(par1ItemStack);
//        		if(capa.getUnsagaMaterial().getRenderColor().isPresent()){
//        			return capa.getUnsagaMaterial().getRenderColor().get();
//        		}
//
//        	}
//        }
//        return WHITE;
//    }
//
//    public void addDisplayInfoComponents(IComponentDisplayInfo... components){
//    	this.displayInfoComponents.addAll(Lists.newArrayList(components));
//    }
////    public void addUnsagaItemInfo(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List infoList, boolean par4) {
////
////    	this.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, infoList, par4, null);
////    }
//
//    public void addUnsagaItemInfo(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List infoList, boolean par4) {
////    	String lang = Minecraft.getMinecraft().gameSettings.language;
//
//    	if(!this.displayInfoComponents.isEmpty()){
//    		for(IComponentDisplayInfo component:this.displayInfoComponents){
//    			if(component.predicate(par1ItemStack, par2EntityPlayer, infoList, par4)){
//        			component.addInfo(par1ItemStack, par2EntityPlayer, infoList, par4);
//    			}
//
//    		}
//    	}
////    	if(par1ItemStack!=null && AbilityHelperNew.hasCapabilityLearning(par1ItemStack)){
////    		final IUnsagaPropertyItem capa = AbilityHelperNew.getCapabilityLearning(par1ItemStack);
////    		UnsagaMaterial material = new Function<IUnsagaPropertyItem,UnsagaMaterial>(){
////
////				@Override
////				public UnsagaMaterial apply(IUnsagaPropertyItem input) {
////					if(input.getUnsagaMaterial()==UnsagaMod.materials.failed){
////						if(input.getSubMaterial().isPresent()){
////							return input.getSubMaterial().get();
////						}
////					}
////					return input.getUnsagaMaterial();
////				}
////			}.apply(capa);
////    		infoList.add(I18n.translateToLocal(I18n.translateToLocal("material."+material.getName())));
////    		if(HSLib.configHandler.isDebug()){
////    			infoList.add("Weight:"+capa.getWeight());
////    		}
////			String weightString = new Function<ItemStack,String>(){
////
////				@Override
////				public String apply(ItemStack input) {
////					if(!HSLib.configHandler.isDebug()){
////						return capa.getWeight()>THRESHOLD_WEIGHT ? "W:Heavy" : "W:Light";
////					}
////					return null;
////				}
////			}.apply(par1ItemStack);
////			if(weightString!=null)infoList.add(weightString);
////			if(par1ItemStack.getItem() instanceof ItemShieldUnsaga){
////				ItemShieldUnsaga shield = (ItemShieldUnsaga) par1ItemStack.getItem();
////				int var1 = capa.getUnsagaMaterial().getArmorMaterial().getDamageReductionAmount(ItemShieldUnsaga.getReferenceArmor());
////				infoList.add(UnsagaTextFormatting.POSITIVE+"Blocking Reduction +"+ItemShieldUnsaga.SHIELD_PER*var1+"%");
////			}
////			displayAbilities(par1ItemStack, par2EntityPlayer, infoList, par4);
////    	}
////		if(itemInfo!=null){
////			infoList.add(itemInfo);
////		}
//    }
//
////    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
////    {
////		MaterialAnalyzer info = new MaterialAnalyzer(par2ItemStack);
////		if(!info.getMaterial().isPresent()){
////			return false;
////		}
////
////        return UtilUnsagaTool.getMaterial(par1ItemStack) == info.getMaterial().get() ? true : false;
////    }
//
//    @Deprecated
//    public void getSubItems(final Item par1, CreativeTabs tab, List subItemList)
//    {
//    	if(uns.hasSubMaterials()){
//
//    		List<ItemStack> stacks = ListHelper.stream(uns.getSubMaterials().values()).map(new Function<UnsagaMaterial,ItemStack>(){
//
//				@Override
//				public ItemStack apply(UnsagaMaterial input) {
//	    			ItemStack is = new ItemStack(par1,1,0);
////	    			UtilUnsagaTool.initWeapon(is,input.getName(),input.getWeight());
//
//	    			return is;
//
//				}}
//    		).getList();
//    		subItemList.addAll(stacks);
//
//    	}else{
//
//    		ItemStack is = new ItemStack(par1,1,0);
////			UtilUnsagaTool.initWeapon(is,materialItem.getName(),materialItem.getWeight());
//
//    		subItemList.add(is);
//    	}
//
//    }
//
//    /**
//     *
//     * TIPSと表示条件をまとめたインターフェイス
//     *
//     */
//    public static interface IComponentDisplayInfo{
//
//    	public boolean predicate(ItemStack is, EntityPlayer ep, final List dispList, boolean par4);
//
//    	public void addInfo(ItemStack is, EntityPlayer ep, final List dispList, boolean par4);
//    }
//
//    public static class ComponentDisplayAbility implements IComponentDisplayInfo{
//
//    	public AbilityRegistry registry = UnsagaMod.abilities;
//        public IComponentDisplayInfo displayAbilityInherit = new IComponentDisplayInfo(){
//
//
//    		@Override
//    		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//    			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//    			return registry.getInheritAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).isPresent();
//
//    		}
//
//    		@Override
//    		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//
//    			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//    			String str = Strings.EMPTY;
//        		List<String> strListInherit = registry.getInheritAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).get().stream()
//        				.map(ability -> UnsagaTextFormatting.POSITIVE+ability.getLocalized()).collect(Collectors.toList());
//        		dispList.addAll(strListInherit);
//
//    		}
//    	};
//        public IComponentDisplayInfo displayAbilityCreative = new IComponentDisplayInfo(){
//
//    		@Override
//    		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//    			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//    			return ep.capabilities.isCreativeMode && registry.getAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).isPresent();
//
//    		}
//
//    		@Override
//    		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//    			if(AbilityHelper.hasCapability(is) && !AbilityHelper.getCapability(is).getEgoName().equals(Strings.EMPTY)){
//    				return;
//    			}
//
//    			if(GuiScreen.isShiftKeyDown()){
//        			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//        			List<String> strListOnCreative = registry.getAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).get().stream()
//        			.map(ability -> UnsagaTextFormatting.PROPERTY_LOCKED+"("+ability.getLocalized()+")").collect(Collectors.toList());
//
//            		dispList.addAll(strListOnCreative);
//    			}else{
//    				dispList.add("Show Potential Abilities(Press Shift)");
//    				dispList.add("Show Ability Info(Press Shift+M)");
//    			}
//
//
//    		}
//    	};
//        public IComponentDisplayInfo displayAbilities = new ComponentDisplayInfo((is,ep,list,sw)->{
//			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//			return !capa.getAbilityList().isEmpty();
//        }){
//
//    		@Override
//    		public void addInfo(ItemStack is, EntityPlayer ep, final List dispList, boolean par4) {
//    			IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//    			capa.getAbilityList().forEach(ability -> dispList.add(UnsagaTextFormatting.PROPERTY+ability.getLocalized()));
//
//
//    		}
//    	};
//
//		@Override
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			// TODO 自動生成されたメソッド・スタブ
//			return is!=null && AbilityHelper.hasCapability(is);
//		}
//
//		@Override
//		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			if(this.displayAbilityInherit.predicate(is, ep, dispList, par4)){
//				this.displayAbilityInherit.addInfo(is, ep, dispList, par4);
//			}
//
//			if(this.displayAbilityCreative.predicate(is, ep, dispList, par4)){
//				this.displayAbilityCreative.addInfo(is, ep, dispList, par4);
//			}
//			if(this.displayAbilities.predicate(is, ep, dispList, par4)){
//				this.displayAbilities.addInfo(is, ep, dispList, par4);
//			}
//
//		}
//
//    }
//
//
//
//    public static final IComponentDisplayInfo DISPLAY_MATERIAL_WEIGHT = new ComponentDisplayInfo((is,ep,list,sw)-> is!=null && AbilityHelper.hasCapability(is)){
//
//
//		@Override
//		public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//    		final IUnsagaPropertyItem capa = AbilityHelper.getCapability(is);
//    		UnsagaMaterial material = this.getMaterialFromCapability(capa);
//			dispList.add(I18n.translateToLocal(I18n.translateToLocal("material."+material.getName())));
//    		if(HSLib.configHandler.isDebug()){
//    			dispList.add("Weight:"+capa.getWeight());
//    		}
//			String weightString = this.getDisplayMessage(is, capa.getWeight());
//			if(weightString!=null){
//				dispList.add(weightString);
//			}
//		}
//
//		public String getDisplayMessage(ItemStack input,int weight) {
//			if(!HSLib.configHandler.isDebug()){
//				return weight>THRESHOLD_WEIGHT ? "W:Heavy" : "W:Light";
//			}
//			return null;
//		}
//
//		public UnsagaMaterial getMaterialFromCapability(IUnsagaPropertyItem input) {
//			if(input.getUnsagaMaterial()==UnsagaMod.materials.failed){
//				if(input.getSubMaterial().isPresent()){
//					return input.getSubMaterial().get();
//				}
//			}
//			return input.getUnsagaMaterial();
//		}
//	};
//
//	public static boolean isHeavy(ItemStack is){
//		if(is!=null && AbilityHelper.hasCapability(is)){
//			return AbilityHelper.getCapability(is).getWeight()>THRESHOLD_WEIGHT;
//		}
//		return false;
//	}
//
////    public void displayAbilities(ItemStack is, EntityPlayer ep, final List dispList, boolean par4){
////    	//最初からついてるアビリティを表示
////    	//Unsaga.debug(this.category,this.hashCode());
////    	IUnsagaPropertyItem capa = AbilityHelperNew.getCapabilityLearning(is);
////    	if(registry.getInheritAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).isPresent()){
////			String str = "";
////    		List<String> strListInherit = ListHelper.stream(registry.getInheritAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).get()).map(new Function<Ability,String>(){
////
////				@Override
////				public String apply(Ability input) {
////					return TextFormatting.AQUA+input.getName(ClientHelper.getCurrentLanguage());
////				}
////
////    		}).getList();
////    		dispList.addAll(strListInherit);
////
////
////		}
////    	//クリエイティヴではアビリティを全部表示
////    	if(ep.capabilities.isCreativeMode && registry.getAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).isPresent()){
////    		List<String> strListOnCreative = ListHelper.stream(registry.getAbilities(capa.getToolCategory(), capa.getUnsagaMaterial()).get()).map(new Function<Ability,String>(){
////
////				@Override
////				public String apply(Ability input) {
////					return TextFormatting.GRAY+input.getName(ClientHelper.getCurrentLanguage());
////				}
////
////    		}).getList();
////
////
////    		dispList.addAll(strListOnCreative);
////    	}
////    	//あとから引き出したアビリティを表示
////    	if(!capa.getAbilityList().isEmpty()){
////			ListHelper.stream(capa.getAbilityList()).forEach(new Consumer<Ability>(){
////
////				@Override
////				public void accept(Ability input) {
////					dispList.add(UnsagaTextFormatting.PROPERTY+input.getName(ClientHelper.getCurrentLanguage()));
////
////				}}
////			);
////    	}
////
////    }
//
//	public static final IComponentDisplayInfo[] COMPONENTS_TOOLS_DISPLAY = {DISPLAY_MATERIAL_WEIGHT,DISPLAY_ABILITY};
//
//    public ActionResult<WazaPerformer> findSkillInvoker(ContainerItemInteraction container,WazaEffect.Type type)
//    {
//
//
//    	UnsagaMod.logger.trace("さがします");
//		Optional<WazaEffect> pickedWazaEffect = AbilityHelper.getSkillMelee(type, container.stack, container.player, container.world, XYZPos.createFrom(container.player));
//		if(pickedWazaEffect.isPresent()){
//			WazaPerformer invoker = new WazaPerformer(container.world, container.player, pickedWazaEffect.get().getWaza(), container.stack);
//
//			if(invoker!=null){
//		    	UnsagaMod.logger.trace("invokerが用意できました",type);
//				switch(type){
//				case SWING:
//					return new ActionResult(EnumActionResult.SUCCESS,invoker);
//				case ENTITY_LEFTCLICK:
//			    	UnsagaMod.logger.trace("発見");
//					Entity target = ((ContainerItemInteraction.EntityClick)container).target;
//					if(target instanceof EntityLivingBase){
//						invoker.setTarget((EntityLivingBase) target);
////						if(pickedSkillEffect.isRequirePrepare()){
////							invoker.prepareSkill();
////							return new ActionResult(EnumActionResult.PASS,invoker);
////						}else{
////							return new ActionResult(EnumActionResult.SUCCESS,invoker);
////						}
//						return new ActionResult(EnumActionResult.SUCCESS,invoker);
//					}else{
//						return new ActionResult(EnumActionResult.PASS,invoker);
//					}
//				case RIGHTCLICK:
//					return new ActionResult(EnumActionResult.SUCCESS,invoker);
//				case STOPPED_USING:
//					invoker.setTarget(((ContainerItemInteraction.Stopped)container).target);
//					invoker.setCharge(((ContainerItemInteraction.Stopped)container).charge);
//					return new ActionResult(EnumActionResult.SUCCESS,invoker);
//				case USE:
//					BlockPos pos = ((ContainerItemInteraction.Using)container).blockPos;
//					invoker.setUsePoint(new XYZPos(pos));
//					return new ActionResult(EnumActionResult.SUCCESS,invoker);
//				case BOW:
//					break;
//				default:
//					break;
//				}
//			}
//		}
//		return new ActionResult(EnumActionResult.PASS,null);
//    }
//
//
//    public static class ContainerItemInteraction{
//
//    	public ContainerItemInteraction(ItemStack stack, World world, EntityLivingBase player) {
//			super();
//			this.stack = stack;
//			this.world = world;
//			this.player = player;
//		}
//		protected final ItemStack stack;
//    	protected final World world;
//    	protected final EntityLivingBase player;
//
//
//    	public static class Swing extends ContainerItemInteraction{
//
//			public Swing(ItemStack stack, World world, EntityLivingBase player) {
//				super(stack, world, player);
//				// TODO 自動生成されたコンストラクター・スタブ
//			}
//
//    	}
//        public static class Using extends ContainerItemInteraction{
//
//
//    		public Using(ItemStack stack, World world, EntityLivingBase player, BlockPos pos,EnumHand hand, EnumFacing facing,
//    				XYZPos hitPosition) {
//    			super(stack, world, player);
//    			this.hand = hand;
//    			this.facing = facing;
//    			this.hitPosition = hitPosition;
//    			this.blockPos = pos;
//    		}
//    		protected final EnumHand hand;
//        	protected final EnumFacing facing;
//        	protected final XYZPos hitPosition;
//        	protected final BlockPos blockPos;
//
//
//        }
//
//        public static class EntityClick extends ContainerItemInteraction{
//
//        	public EntityClick(ItemStack stack, World world, EntityLivingBase player, Entity target) {
//				super(stack, world, player);
//				this.target = target;
//			}
//
//			protected Entity target;
//        }
//        public static class RightClick extends ContainerItemInteraction{
//
//        	public RightClick(ItemStack stack, World world, EntityLivingBase player, EnumHand hand) {
//				super(stack, world, player);
//				this.hand = hand;
//			}
//
//			protected final EnumHand hand;
//        }
//
//
//        public static class Stopped extends ContainerItemInteraction{
//
//        	public Stopped(ItemStack stack, World world, EntityLivingBase player, float charge,Entity target) {
//				super(stack, world, player);
//				this.charge = charge;
//				this.target = target;
//			}
//
//			protected final float charge;
//			protected final Entity target;
//        }
//    }
//
//
//
//
//    public static abstract class ComponentDisplayInfo implements  IComponentDisplayInfo{
//
//    	IPredicateDisplayInfo predicate;
//
//
//    	public ComponentDisplayInfo(IPredicateDisplayInfo predicate){
//
//    		this.predicate =predicate;
//
//    	}
//		@Override
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//			return this.predicate.predicate(is, ep, dispList, par4);
//		}
//
//
//
//
//    }
//
//
//    public static interface IPredicateDisplayInfo{
//
//		public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) ;
//    }
//}
