package mods.hinasch.unsaga.lp;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.Maps;

import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemAxeUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemKnifeUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemSpearUnsaga;
import mods.hinasch.unsaga.core.item.newitem.combat.ItemStaffUnsaga;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;

public class LPAssociation {

	protected static LPAssociation instance;
	Map<Predicate<Item>,LPAttribute> map = Maps.newLinkedHashMap();
	Map<Predicate<DamageSource>,LPAttribute> map2 = Maps.newLinkedHashMap();
	public static LPAssociation instance(){
		if(instance==null){
			instance = new LPAssociation();
		}
		return instance;
	}
	private LPAssociation(){
		map.put(item -> item instanceof ItemStaffUnsaga, new LPAttribute(0.4F,1));
		map.put(item -> item instanceof ItemSword, new LPAttribute(0.5F,1));
		map.put(item -> item instanceof ItemAxeUnsaga, new LPAttribute(0.6F,1));
		map.put(item -> item instanceof ItemAxe, new LPAttribute(0.6F,1));
		map.put(item -> item instanceof ItemKnifeUnsaga, new LPAttribute(0.8F,1));
		map.put(item -> item instanceof ItemSpearUnsaga, new LPAttribute(0.7F,1));
//		map.put(item -> item instanceof ItemBow, new LPAttribute(0.8F,1));
		map.put(item -> item instanceof ItemPickaxe, new LPAttribute(0.4F,1));

		map2.put(ds -> ds.getEntity()==null && ds==DamageSource.magic, new LPAttribute(0.0f,0));
		map2.put(ds -> ds==DamageSource.starve, new LPAttribute(0.0f,0));
		map2.put(ds -> ds==DamageSource.fall, new LPAttribute(2.0f,3));
		map2.put(ds -> ds==DamageSource.generic, new LPAttribute(0.0f,0));
		map2.put(ds -> ds==DamageSource.hotFloor, new LPAttribute(0.1f,1));
		map2.put(ds -> ds==DamageSource.inFire, new LPAttribute(0.1f,1));
		map2.put(ds -> ds==DamageSource.onFire, new LPAttribute(0.1f,1));
		map2.put(ds -> ds==DamageSource.lava, new LPAttribute(0.1f,1));
		map2.put(ds -> ds==DamageSource.lightningBolt, new LPAttribute(2.0f,2));
		map2.put(ds -> ds==DamageSource.fallingBlock, new LPAttribute(0.1f,1));
		map2.put(ds -> ds==DamageSource.wither, new LPAttribute(0.1f,1));
		map2.put(ds -> ds.isExplosion() , new LPAttribute(1.5f,2));
		map2.put(ds -> (ds.getEntity() instanceof EntityPlayer) && ItemUtil.isItemStackNull(((EntityPlayer)ds.getEntity()).getHeldItemMainhand()) , new LPAttribute(0,0));
	}

	public Optional<LPAttribute> getData(ItemStack is){
		return map.entrySet().stream()
				.filter(entry -> entry.getKey().test(is.getItem())).map(entry -> entry.getValue()).findFirst();
	}

	public Optional<LPAttribute> getData(DamageSource ds){
		return map2.entrySet().stream()
				.filter(entry -> entry.getKey().test(ds)).map(entry -> entry.getValue()).findFirst();
	}
	public class LPAttribute{



		final float lpstr;
		public float getLpStr() {
			return lpstr;
		}
		public int getNumberOfAttack() {
			return numberOfAttack;
		}
		final int numberOfAttack;
		public LPAttribute(float lpstr, int numberOfAttack) {
			super();
			this.lpstr = lpstr;
			this.numberOfAttack = numberOfAttack;
		}
	}
}
