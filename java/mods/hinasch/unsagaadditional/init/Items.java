package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.item.ItemNoFunction;
import mods.hinasch.lib.item.ItemProperty;
import mods.hinasch.lib.item.PropertyRegistryItem;
import mods.hinasch.lib.registry.BlockItemRegistry;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsagaadditional.UnsagaModAdditional;
import net.minecraft.item.Item;

public class Items extends BlockItemRegistry<Item>{

	public Item misc;

	public Items() {
		super(UnsagaModAdditional.MODID);
		this.setUnlocalizedNamePrefix("unsaga");
	}

	@Override
	public void register() {
		// TODO 自動生成されたメソッド・スタブ

		this.misc = this.put(new ItemNoFunction("unsaga"){

			@Override
			public PropertyRegistryItem<? extends ItemProperty> getItemProperties() {
				// TODO 自動生成されたメソッド・スタブ
				return UnsagaModAdditional.miscItems;
			}

		}, "misc", UnsagaMod.core.tabMisc);

	}


}
