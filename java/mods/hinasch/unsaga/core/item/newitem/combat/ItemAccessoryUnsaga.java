package mods.hinasch.unsaga.core.item.newitem.combat;

import com.google.common.collect.Sets;

import mods.hinasch.unsaga.common.ItemToolUnsaga;
import mods.hinasch.unsaga.util.ToolCategory;

public class ItemAccessoryUnsaga extends ItemToolUnsaga{

	public ItemAccessoryUnsaga() {
		super(0, -2.8F, Sets.newHashSet(),ToolCategory.ACCESSORY);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public ToolCategory getCategory() {
		// TODO 自動生成されたメソッド・スタブ
		return ToolCategory.ACCESSORY;
	}
}
