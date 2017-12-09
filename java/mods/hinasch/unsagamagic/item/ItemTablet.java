package mods.hinasch.unsagamagic.item;

import java.util.List;
import java.util.stream.Collectors;

import com.mojang.realmsclient.util.Pair;

import mods.hinasch.lib.iface.INBTWritable;
import mods.hinasch.lib.util.UtilNBT.RestoreFunc;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import mods.hinasch.unsagamagic.spell.tablet.MagicTablet;
import mods.hinasch.unsagamagic.spell.tablet.TabletCapability;
import mods.hinasch.unsagamagic.spell.tablet.TabletRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemTablet extends Item{

	public static class DecipheringPair extends Pair<Spell,Integer> implements INBTWritable{

		public static final RestoreFunc<DecipheringPair> RESTORE = input ->{
			String id = input.getString("id");
			Spell spell = SpellRegistry.instance().get(id);
			int progress = input.getInteger("progress");

			return new DecipheringPair(spell,progress);
		};



		public DecipheringPair(Spell first, Integer second) {
			super(first, second);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public int getProgress(){
			return this.second();
		}

		public Spell getSpell(){
			return this.first();
		}


		@Override
		public void writeToNBT(NBTTagCompound stream) {
			this.first().writeToNBT(stream);
			stream.setInteger("progress", this.second());
		}


    }

	public ItemTablet(){

		this.setMaxStackSize(1);
	}
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {

    	List<ItemStack> list = TabletRegistry.instance().getProperties().stream().sorted().map(input ->{
    		ItemStack is = new ItemStack(UnsagaMagicItems.instance().tablet,1);
    		if(TabletCapability.adapter.hasCapability(is)){
    			TabletCapability.adapter.getCapability(is).setTabletData(input);
    		}

			return is;
    	}).collect(Collectors.toList());


    	par3List.addAll(list);


    }


    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {


    	if(TabletCapability.adapter.hasCapability(par1ItemStack)){
    		MagicTablet data = TabletCapability.adapter.getCapability(par1ItemStack).getTabletData();
    		par3List.add(data.getLocalized()+"["+data.getElement().getLocalized()+"]");
    		par3List.add("Tier:"+data.getTier());
    	}
//		if(TabletHelper.hasCapability(par1ItemStack)){
//			MagicTablet data = TabletHelper.getCapability(par1ItemStack).getProperty();
//			if(data!=null){
//				String localized = HSLibs.translateKey("tablet."+data.getName());
//				if(localized!=null){
//					par3List.add(localized);
//				}
//			}
//		}
//		if(HSLibs.checkShiftKeyCombo(Keyboard.KEY_M)){
//			par3List.add(TextFormatting.ITALIC+HSLibs.translateKey("item.unsaga.magicTablet.info"));
//		}else{
//			par3List.add(TextFormatting.ITALIC+"Show Infomation(Shift + M)");
//		}

	}
}
