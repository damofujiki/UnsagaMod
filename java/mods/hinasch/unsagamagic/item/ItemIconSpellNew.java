package mods.hinasch.unsagamagic.item;

import java.util.List;

import mods.hinasch.lib.capability.CapabilityAdapterFactory.ICapabilityAdapterPlan;
import mods.hinasch.lib.capability.CapabilityAdapterFrame;
import mods.hinasch.lib.capability.CapabilityStorage;
import mods.hinasch.lib.capability.ComponentCapabilityAdapterItem;
import mods.hinasch.lib.iface.IIconItem;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * おもに解読画面のアイコンで使う
 * @author damofujiki
 *
 */
public class ItemIconSpellNew extends Item implements IIconItem{
public static class DefaultImpl implements IItemIconSpell{

    	SelectAttribute select = SelectAttribute.UNSELECTED;
    	Spell spell = SpellRegistry.instance().empty;
    	int progress = 0;
		@Override
		public int getDecipheringProgress() {
			// TODO 自動生成されたメソッド・スタブ
			return this.progress;
		}

		@Override
		public SelectAttribute getSelectStatus() {
			// TODO 自動生成されたメソッド・スタブ
			return this.select;
		}

		@Override
		public Spell getSpell() {
			// TODO 自動生成されたメソッド・スタブ
			return this.spell;
		}

		@Override
		public void setDecipheringProgress(int par1) {
			// TODO 自動生成されたメソッド・スタブ
			this.progress = par1;
		}

		@Override
		public void setSpell(Spell spell) {
			// TODO 自動生成されたメソッド・スタブ
			this.spell = spell;
		}

		@Override
		public void setStatus(SelectAttribute en) {
			// TODO 自動生成されたメソッド・スタブ
			this.select = en;
		}

    };
	//	public ElementIcons elementalIcons;
	public enum SelectAttribute {SELECTED,UNSELECTED}

	public static interface IItemIconSpell{

    	public int getDecipheringProgress();
    	public SelectAttribute getSelectStatus();
    	public Spell getSpell();
    	public void setDecipheringProgress(int par1);
    	public void setSpell(Spell spell);
    	public void setStatus(SelectAttribute en);
    }
	public static class Storage extends CapabilityStorage<IItemIconSpell>{

		@Override
		public void readNBT(NBTTagCompound comp, Capability<IItemIconSpell> capability, IItemIconSpell instance,
				EnumFacing side) {
			// TODO 自動生成されたメソッド・スタブ
			if(comp.hasKey("id")){
				instance.setSpell(SpellRegistry.instance().get(comp.getString("id")));
			}
			if(comp.hasKey("progress")){
				instance.setDecipheringProgress(comp.getInteger("progress"));
			}
		}

		@Override
		public void writeNBT(NBTTagCompound comp, Capability<IItemIconSpell> capability, IItemIconSpell instance,
				EnumFacing side) {
			comp.setString("id", instance.getSpell().getKey().getResourcePath());
			comp.setInteger("progress", instance.getDecipheringProgress());

		}

	}
	public static final int FINISHED_COLOR = 0xee0000;
	@CapabilityInject(IItemIconSpell.class)
	public static Capability<IItemIconSpell> CAPA;

	public static ICapabilityAdapterPlan<IItemIconSpell> ica = new ICapabilityAdapterPlan(){

		@Override
		public Capability getCapability() {
			// TODO 自動生成されたメソッド・スタブ
			return CAPA;
		}

		@Override
		public Class getCapabilityClass() {
			// TODO 自動生成されたメソッド・スタブ
			return IItemIconSpell.class;
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

	public static CapabilityAdapterFrame<IItemIconSpell> adapterBase = UnsagaMod.capabilityFactory.create(ica);
	public static ComponentCapabilityAdapterItem<IItemIconSpell> adapter = adapterBase.createChildItem("unsagaAbilityAttachable");

	static{
		adapter.setPredicate(ev -> ev.getObject() instanceof ItemIconSpellNew);
		adapter.setRequireSerialize(true);
	}
//	public static EnumSelectAttribute getSelectStatus(ItemStack is){
//
//		if(is!=null && is.getItemDamage()==1){
//			return EnumSelectAttribute.SELECTED;
//		}
//		return EnumSelectAttribute.UNSELECTED;
//	}
//
//	public static void setStatus(ItemStack is,EnumSelectAttribute en){
//		switch(en){
//		case SELECTED:
//			is.setItemDamage(1);
//			break;
//		case UNSELECTED:
//			is.setItemDamage(0);
//			break;
//		default:
//			break;
//
//		}
//	}

	public static boolean hasCapability(ItemStack is){
		return is.hasCapability(CAPA, null);
	}

	public static void registerEvents(){
		adapter.registerAttachEvent();
	}

    public static ItemStack createSpellIcon(Spell spell){
		ItemStack is = new ItemStack(UnsagaMagicItems.instance().iconSpell,1);
		if(adapter.hasCapability(is)){
			adapter.getCapability(is).setSpell(spell);
		}
		return is;
	}

	public ItemIconSpellNew(){
		//this.setTextureName(Unsaga.DOMAIN+":book_spell");
//		this.elementalIcons = new ElementIcons();
		this.setMaxDamage(10);
		this.addPropertyOverride(new ResourceLocation("selected"), new IItemPropertyGetter(){

			@Override
			public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
				if(adapter.hasCapability(stack)){
					return adapter.getCapability(stack).getSelectStatus()==SelectAttribute.SELECTED ? 1.0F : 0;
				}
				return 0;
			}}
		);
//		HSLibs.registerCapability(IItemIconSpell.class, new StorageIItemIconSpell(), DefaultIItemIconSpell.class);
//		ItemSpellBook.registerElementsProperties(this,input ->{
//			if(ItemIconSpell.hasCapability(input)){
//				return ItemIconSpell.getCapability(input).getSpell().getElement();
//			}
//			return null;
//		});
	}

@Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {

		if(adapter.hasCapability(par1ItemStack)){
			Spell spell = adapter.getCapability(par1ItemStack).getSpell();
			par3List.add(spell.getLocalizedByFullText());
			par3List.add(adapter.getCapability(par1ItemStack).getDecipheringProgress()+"/100");
		}

	}
//	@Override
//    @SideOnly(Side.CLIENT)
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//
//		if(hasCapability(stack)){
//			if(adapter.getCapability(stack).getDecipheringProgress()>=100){
//				return FINISHED_COLOR;
//			}
//		}
//
//        return Statics.COLOR_NONE;
//    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void registerIcons(IIconRegister par1IconRegister)
//    {
//        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
//        this.elementalIcons.registerIcons(par1IconRegister);
//    }
//
//	@Override
//	public boolean requiresMultipleRenderPasses()
//	{
//		return true;
//	}
//
//    @Override
//    public IIcon getIcon(ItemStack stack, int pass)
//    {
//
//    		if(pass==1){
//    			return this.elementalIcons.getIcon(ItemIconSpell.getSpell(stack).element);
//    		}
//
//
//		return this.itemIcon;
//    }

	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	if(hasCapability(stack)){
    		return (double)(100-adapter.getCapability(stack).getDecipheringProgress()) / 100.0D;
    	}
    	return super.getDurabilityForDisplay(stack);
    }

    //	public static int getDecipheringProgress(ItemStack is){
//		if(UtilNBT.hasKey(is, KEY_PROGRESS)){
//			return UtilNBT.readFreeTag(is, KEY_PROGRESS);
//		}
//		return 0;
//	}
//	public static void setDecipheringProgress(ItemStack is,int progress){
//		UtilNBT.initNBTIfNotInit(is);
//		UtilNBT.setFreeTag(is, KEY_PROGRESS, progress);
//	}
    @SideOnly(Side.CLIENT)
    @Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	if(adapter.hasCapability(par1ItemStack)){
    		return adapter.getCapability(par1ItemStack).getDecipheringProgress()>=100;
    	}
    	return false;
    }


    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }


}
