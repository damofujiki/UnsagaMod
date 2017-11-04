package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.item.ItemProperty;
import mods.hinasch.lib.item.PropertyRegistryItem;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import net.minecraft.item.Item;

public class MiscItemPropertyGroup extends PropertyRegistryItem<MiscItemPropertyGroup.MiscItem>{

	public static final MiscItem INGOT_BISMUTH = new MiscItem(0,"ingotBismuth");
	public static final MiscItem CRYSTAL_CINNABAR = new MiscItem(1,"crystalCinnabar");
	public static final MiscItem SALT = new MiscItem(2,"salt");
	public static final MiscItem INGOT_ELECTRUM = new MiscItem(3,"ingotElectrum");
	public static final MiscItem DUST_ELECTRUM = new MiscItem(4,"dustElectrum");
	public static final MiscItem AMBER = new MiscItem(5,"gemAmber");




	public static class MiscItem extends ItemProperty{





		public MiscItem(int id, String name) {
			super(id, name);
			this.setOreDictID(name);
		}

		@Override
		public Item getItem() {
			// TODO 自動生成されたメソッド・スタブ
			return UnsagaModAdditional.items.misc;
		}



	}


	@Override
	public void registerObjects() {
		this.put(INGOT_BISMUTH);
		this.put(CRYSTAL_CINNABAR);
		this.put(SALT);
		this.put(INGOT_ELECTRUM);
		this.put(DUST_ELECTRUM);
		this.put(AMBER);
	}


	@Override
	public void preInit() {
		this.registerObjects();
	}


	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerOreDicts();
	}
}
