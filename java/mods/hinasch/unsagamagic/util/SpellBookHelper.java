//package mods.hinasch.unsagamagic.util;
//
//
//import java.util.List;
//import java.util.Optional;
//
//import com.google.common.collect.Lists;
//
//import mods.hinasch.lib.capability.CapabilityAdapterFrame;
//import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
//import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
//import mods.hinasch.lib.util.UtilNBT;
//import mods.hinasch.lib.world.XYZPos;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.Ability;
//import mods.hinasch.unsaga.ability.AbilityHelper;
//import mods.hinasch.unsaga.element.FiveElements;
//import mods.hinasch.unsaga.util.AccessoryHelper;
//import mods.hinasch.unsaga.util.AccessoryHelper.IAccessorySlot;
//import mods.hinasch.unsagamagic.item.ItemSpellBook;
//import mods.hinasch.unsagamagic.spell.Spell;
//import mods.hinasch.unsagamagic.spell.SpellRegistry;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTBase;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.EnumFacing;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.capabilities.Capability.IStorage;
//import net.minecraftforge.common.capabilities.CapabilityInject;
///**
// * ItemSpellBookから分割
// *
// *
// */
//public class SpellBookHelper {
//
//	public static class DefaultImpl implements ISpellBook{
//
//		Spell spell = SpellRegistry.instance().abyss;
//		boolean hasMixed = false;
//		float amp = 1.0F;
//		float cost = 1.0F;
//		List<Spell> history = Lists.newArrayList();
//		XYZPos usePosition;
//		@Override
//		public float getAmp() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.amp;
//		}
//
//		@Override
//		public List<Spell> getBlendedSpellsHistory() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.history;
//		}
//
//		@Override
//		public float getCost() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.cost;
//		}
//
//		@Override
//		public Spell getSpell() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.spell;
//		}
//
//		@Override
//		public XYZPos getUsePosition() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.usePosition;
//		}
//
//		@Override
//		public boolean isMixed() {
//			// TODO 自動生成されたメソッド・スタブ
//			return this.hasMixed;
//		}
//
//		@Override
//		public void setAmp(float par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.amp = par1;
//		}
//
//		@Override
//		public void setBlendedSpellsHistory(List<Spell> spells) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.history = spells;
//		}
//
//		@Override
//		public void setCost(float par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.cost = par1;
//		}
//
//		@Override
//		public void setMixed(boolean par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.hasMixed = par1;
//		}
//
//		@Override
//		public void setSpell(Spell par1) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.spell = par1;
//		}
//
//		@Override
//		public void setUsePosition(XYZPos pos) {
//			// TODO 自動生成されたメソッド・スタブ
//			this.usePosition = pos;
//		}
//
//	}
//
//	public static interface ISpellBook {
//
//		public float getAmp();
//		public List<Spell> getBlendedSpellsHistory();
//		public float getCost();
//		public Spell getSpell();
//		public XYZPos getUsePosition();
//		public boolean isMixed();
//		public void setAmp(float par1);
//		public void setBlendedSpellsHistory(List<Spell> spells);
//		public void setCost(float par1);
//		public void setMixed(boolean par1);
//		public void setSpell(Spell par1);
//		public void setUsePosition(XYZPos pos);
//	}
//
//	public static class Storage implements IStorage<ISpellBook> {
//
//		@Override
//		public void readNBT(Capability<ISpellBook> capability, ISpellBook instance, EnumFacing side, NBTBase nbt) {
//			if(nbt instanceof NBTTagCompound){
//				NBTTagCompound comp = (NBTTagCompound) nbt;
//				if(comp.hasKey("history")){
//					instance.setBlendedSpellsHistory(UtilNBT.readListFromNBT(comp, "history", Spell.RESTOREFUNC));
//				}
//				instance.setMixed(comp.getBoolean("isMixed"));
//				instance.setCost(comp.getFloat("amp"));
//				instance.setAmp(comp.getFloat("cost"));
//				instance.setSpell(SpellRegistry.instance().getSpell(comp.getString("spell")));
//			}
//
//		}
//
//		@Override
//		public NBTBase writeNBT(Capability<ISpellBook> capability, ISpellBook instance, EnumFacing side) {
//			NBTTagCompound comp = UtilNBT.compound();
//			if(!instance.getBlendedSpellsHistory().isEmpty()){
//				UtilNBT.writeListToNBT(instance.getBlendedSpellsHistory(), comp, "history");
//			}
//			comp.setBoolean("isMixed", instance.isMixed());
//			comp.setFloat("amp", instance.getAmp());
//			comp.setFloat("cost", instance.getCost());
//			comp.setString("spell", instance.getSpell().getKey().toString());
//			return comp;
//		}
//
//	}
//	@CapabilityInject(ISpellBook.class)
//	public static Capability<ISpellBook> CAPA;
//
//	private static ICapabilityAdapterPlan ica = new ICapabilityAdapterPlan(){
//
//		@Override
//		public Capability getCapability() {
//			// TODO 自動生成されたメソッド・スタブ
//			return CAPA;
//		}
//
//		@Override
//		public Class getCapabilityClass() {
//			// TODO 自動生成されたメソッド・スタブ
//			return ISpellBook.class;
//		}
//
//		@Override
//		public Class getDefault() {
//			// TODO 自動生成されたメソッド・スタブ
//			return DefaultImpl.class;
//		}
//
//		@Override
//		public IStorage getStorage() {
//			// TODO 自動生成されたメソッド・スタブ
//			return new Storage();
//		}
//	};
//	public static CapabilityAdapterFrame<ISpellBook> base = UnsagaMod.capabilityFactory.create(ica);
//	public static ComponentCapabilityAdapterItem<ISpellBook> adapter = base.createChildItem("spellBook");
//
//	static{
//		adapter.setRequireSerialize(true);
//		adapter.setPredicate(ev -> ev.getItem() instanceof ItemSpellBook);
//	}
//	/**
//	 * アクセサリスロットから魔法アイテムを探す
//	 * @param ep
//	 * @param element
//	 * @return
//	 */
//	public static ItemStack findFirstMagicItem(EntityLivingBase ep,FiveElements.Type element){
//		switch(element){
//		case FIRE:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.fire);
//		case WOOD:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.wood);
//		case WATER:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.water);
//		case EARTH:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.earth);
//		case METAL:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.metal);
//		case FORBIDDEN:
//			return SpellBookHelper.getMagicAbilityItem(ep,UnsagaMod.abilities.forbidden);
//		}
//		return null;
//	}
////
////	public static boolean hasMixed(ItemStack is){
////		if(UtilNBT.hasKey(is, ItemSpellBook.MIXKEY)){
////			return UtilNBT.readFreeTagBool(is, ItemSpellBook.MIXKEY);
////		}
////		return false;
////	}
//
//	public static ISpellBook getCapability(ItemStack is){
//		return adapter.getCapability(is);
//	}
////	/**
////	 * 術合成の履歴をセット
////	 * @param is
////	 * @param spells
////	 */
////	public static void setBlendedSpellsHistory(ItemStack is,List<Spell> spells){
////		if(spells==null)return;
////		NBTTagCompound comp = UtilNBT.getNewCompound(is);
////		UtilNBT.writeListToNBT(spells, comp, ItemSpellBook.SPELLSKEY);
////	}
//
////	public static List<Spell> getBlendedSpells(ItemStack is){
////		//Unsaga.debug(UtilNBT.readFreeStrTag(is, ItemSpellBook.SPELLSKEY));
////
////		List<Spell> spells = UtilNBT.readListFromNBT(is.getTagCompound(), ItemSpellBook.SPELLSKEY, Spell.RESTOREFUNC);
//////		List<Integer> intlist = CSVText.csvToIntList(UtilNBT.readFreeStrTag(is, ItemSpellBook.SPELLSKEY));
//////		List<Spell> spelllist = ListHelper.stream(intlist).map(new Function<Integer,Spell>(){
//////
//////			@Override
//////			public Spell apply(Integer input) {
//////				return Unsaga.magic.spellManager.getSpell(input);
//////			}}
//////		).getList();
//////
//////		for(Integer inte:intlist){
//////			spelllist.add(Unsaga.magic.spellManager.getSpell(inte));
//////		}
////		return spells;
////	}
//
//	/**
////	 * ブレンド済みの術を解体してドロップする。
////	 * @param is
////	 * @param world
////	 * @param ep
////	 */
////	public static void splitSpellsAndDrop(ItemStack is,World world,EntityPlayer ep){
////		if(UtilNBT.hasKey(is, ItemSpellBook.SPELLSKEY)){
////			List<Spell> spellList = getBlendedSpells(is);
////			for(Spell spell:spellList){
////				ItemStack newstack = new ItemStack(UnsagaMod.magic.items.spellBook,1);
////				if(SpellHelper.hasCapability(newstack)){
////					SpellHelper.getCapability(newstack).setSpell(spell);
////				}
////
////				if(!world.isRemote){
////					ep.entityDropItem(newstack,0.2F);
////				}
////
////			}
////
////			if(!world.isRemote){
////				is.stackSize --;
////			}
////
////		}
////	}
//
////	public static void setAmp(ItemStack is,float par1){
////		UtilNBT.setFreeTag(is, ItemSpellBook.AMPKEY, (float)par1);
////	}
////
////	public static void setCost(ItemStack is,float par1){
////		UtilNBT.setFreeTag(is, ItemSpellBook.COSTKEY, par1);
////	}
////
////	public static void setBlended(ItemStack is,boolean par1){
////		UtilNBT.setFreeTag(is, ItemSpellBook.MIXKEY, par1);
////	}
////
////	public static float getAmp(ItemStack is){
////		if(UtilNBT.hasKey(is, ItemSpellBook.AMPKEY)){
////			return MathHelper.clamp_float((float)UtilNBT.readFreeFloat(is, ItemSpellBook.AMPKEY),0.1F,10.0F);
////		}
////		return 1.0F;
////	}
////
////	public static float getCost(ItemStack is){
////		if(UtilNBT.hasKey(is, ItemSpellBook.COSTKEY)){
////			return MathHelper.clamp_float(UtilNBT.readFreeFloat(is, ItemSpellBook.COSTKEY),0.1F,10.0F);
////		}
////		return 1.0F;
////	}
//
//	public static ItemStack getMagicAbilityItem(final EntityLivingBase ep,final Ability ab){
////		AbilityAttacherBase helper;
////			ExtendedPlayerData data = ExtendedPlayerData.getData(ep);
//		if(ep.getHeldItemOffhand()!=null){
//			if(AbilityHelper.hasAbilityFromItemStack(ep.getHeldItemOffhand(), ab)){
//				return ep.getHeldItemOffhand();
//			}
//		}
//		if(ep instanceof EntityPlayer && AccessoryHelper.hasCapability((EntityPlayer) ep)){
//			IAccessorySlot data = AccessoryHelper.getCapability((EntityPlayer) ep);
//			Optional<ItemStack> stack = Lists.newArrayList(data.getAccessories()).stream().filter(input ->{
//				if(input!=null){
//					return AbilityHelper.hasAbilityFromItemStack(input, ab);
//				}
//				return false;
//			}).findFirst();
//			return stack.isPresent() ? stack.get() : null;
//		}
//
//
////			ItemStack firstSlot = new
//			//スロット０番目のアイテムでもok
////			if(ep.inventory.getStackInSlot(0)!=null){
////				if(ep.inventory.getStackInSlot(0).getItem() instanceof IUnsagaMaterialTool){
////					helper = AbilityAttacherBase.getAttacherFromCategory(ep, ep.inventory.getStackInSlot(0));
////					if(helper.hasAbility(ab)){
////						return ep.inventory.getStackInSlot(0);
////					}
////				}
////
////			}
//
//		return null;
//
//	}
//
////	public static boolean writeSpellToBook(EntityPlayer ep,ItemStack is,Spell spell){
////		if(ep.inventory.hasItem(Items.book)){
////			ep.inventory.consumeInventoryItem(Items.book);
////			if(!ep.worldObj.isRemote){
////
////				ItemStack newbook = new ItemStack(Unsaga.magic.items.spellBook,1);
////				SpellHelper.writeSpell(newbook,spell);
////				ChatMessageHandler.sendChatToPlayer(ep, Translation.localize("msg.tablet.write"));
////				//ep.addChatMessage("message.tablet.write");
////				ep.entityDropItem(newbook,0.2F);
////
////
////				return true;
////
////			}
////
////
////		}
////		return false;
////	}
//
////	public static void writeSpell(ItemStack is,Spell spell){
////		UtilNBT.setFreeTag(is, ItemSpellBook.MAGICKEY, spell.number);
////	}
//
////	public static Spell getSpell(ItemStack is){
////		int spellnum = UtilNBT.readFreeTag(is, ItemSpellBook.MAGICKEY);
////		return Unsaga.magic.spellManager.getSpell(spellnum);
////	}
//
//	public static boolean hasCapability(ItemStack is){
//		return is!=null && adapter.hasCapability(is);
//	}
//
////	public static Spell getSpell(ItemStack is){
////		int spellnum = UtilNBT.readFreeTag(is, MAGICKEY);
////		return Unsaga.magic.spellManager.getSpell(spellnum);
////	}
////
////	public static void writeSpell(ItemStack is,Spell spell){
////		UtilNBT.setFreeTag(is, MAGICKEY, spell.number);
////	}
//}
