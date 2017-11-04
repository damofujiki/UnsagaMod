package mods.hinasch.lib.core;

import mods.hinasch.lib.item.ItemAir;
import mods.hinasch.lib.item.ItemDebug;
import mods.hinasch.lib.item.ItemIcon;
import mods.hinasch.lib.registry.BlockItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HSLibItems extends BlockItemRegistry<Item>{


	public Item itemDebugArmor;
	public Item itemAir;
	public ItemIcon itemParticleIcon;
//	public ItemIconBuff itemIconBuff;
	public HSLibItems( ) {
		super(HSLib.MODID);
		this.setUnlocalizedNamePrefix("hinasch");
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void register() {
		this.itemAir = this.put(new ItemAir(){
			@Override
		    public int getEntityLifespan(ItemStack itemStack, World world)
		    {
		        return 1;
		    }

		}, "air", null);
		this.itemDebugArmor = this.put(new ItemDebug(), "armor.debug", null);
//		this.itemIconBuff = (ItemIconBuff) this.put(new ItemIconBuff(), "iconBuff", null);
//		this.itemIconBuff = new ItemIconBuff();
//		this.itemParticleIcon = (ItemIcon) new ItemIcon().setRegistryName("iconParticle");
//		GameRegistry.register(itemIconBuff.setRegistryName("iconBuff"));
//		GameRegistry.register(itemParticleIcon);
//		GameRegistry.register(itemDebugArmor.setRegistryName("armor.debug"));
		if(HSLib.configHandler.isDebug()){

				this.itemDebugArmor.setCreativeTab(CreativeTabs.COMBAT);

		}
	}

}
