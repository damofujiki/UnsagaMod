package mods.hinasch.unsagamagic.enchant;

import java.util.List;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga.ComponentDisplayInfo;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentCapability.EnchantmentState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentTooltip extends ComponentDisplayInfo{

	public EnchantmentTooltip() {
		super(3, (is,ep,disp,par4)->is!=null && UnsagaEnchantmentCapability.hasCapability(is));
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
		// TODO 自動生成されたメソッド・スタブ
		World world = ep.getEntityWorld();
		UnsagaEnchantmentCapability.adapter.getCapability(is).getEntries().stream()
		.sorted((o1,o2)->o1.getKey().compareTo(o2.getKey()))
		.forEach(in ->{
			EnchantmentProperty p = in.getKey();
			EnchantmentState state = in.getValue();
			String s = p.getLocalized() + " " + (state.getLevel() == 1 ? "" : HSLibs.translateKey("enchantment.level."+state.getLevel()));
			long remain = in.getValue().getExpireTime() - world.getTotalWorldTime();
			if(remain>0){
				s += "/"+String.format("Time Remaining:%d", (HSLibs.exceptZero((int) remain))/30);
			}

			dispList.add(s);
		});
	}

}
