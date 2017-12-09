package mods.hinasch.unsaga.core.event;

import java.util.function.Function;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.CustomDropEvent;
import mods.hinasch.lib.item.CustomDropEvent.DropItemStack;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.entity.mob.EntitySignalTree;
import mods.hinasch.unsaga.core.entity.mob.EntityTreasureSlime;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.material.RawMaterialItemRegistry;
import mods.hinasch.unsagamagic.spell.tablet.TabletRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class UnsagaMobDrops {
	protected CustomDropEvent<ItemStack> dropTablet = new CustomDropEvent<ItemStack>(){


		@Override
		public boolean canDrop(DamageSource source,EntityLivingBase entityDrops,int looting){
			if(entityDrops instanceof EntityTreasureSlime){
				return true;

			}
			if(entityDrops instanceof EntityWitch){
				return true;
			}
			if(entityDrops instanceof EntityEnderman){
				return true;
			}
			//落としすぎなのでやめ
//			if(entityDrops instanceof IMob){
//				return true;
//			}
			return false;
		}
		@Override
		public Droppable<ItemStack> getDroppable(LivingDropsEvent e){

			DropItemStack drop = new DropItemStack(TabletRegistry.getRandomTabletByWeight(e.getEntityLiving().getRNG()).getStack(1));
			return drop;
		}

		@Override
		public int getDropValue(DamageSource source,EntityLivingBase entityDrops,int looting){
			int prob = 1;

			if(entityDrops instanceof EntitySlime){
				switch(this.getSlimeSize(entityDrops)){
				case 0:
					prob = 1;
					break;
				case 1:
					prob = 1;
					break;
				case 2:
					prob = 8;
					break;
				default:
					prob = 10;
					break;
				}

			}
			if(entityDrops instanceof EntityEnderman){
				prob = 10;
			}
			if(entityDrops instanceof EntityTreasureSlime){
				prob =  35;
			}
			if(entityDrops instanceof EntityWitch){
				prob = 15;
			}
			return prob + (10*looting);
		}

	};


	protected CustomDropEvent<Entity> dropChest = new CustomDropEvent<Entity>(){
		public boolean canDrop(DamageSource souce,EntityLivingBase entityDrops,int looting){
			if(entityDrops instanceof IMob){
				return true;
			}
			return false;
		}

		@Override
		public Droppable<Entity> getDroppable(LivingDropsEvent e){

			DropEntity drop = new DropEntity(new EntityUnsagaChestNew(e.getEntityLiving().getEntityWorld()));

			return drop;
		}
		public int getDropValue(DamageSource souce,EntityLivingBase entityDrops,int looting){
			int prob = 20;
			if(entityDrops instanceof EntitySlime){
				switch(this.getSlimeSize(entityDrops)){
				case 0:
					prob = 3;
					break;
				case 1:
					prob = 7;
					break;
				case 2:
					prob = 20;
					break;
				default:
					prob = 20;
					break;
				}


			}
			if(entityDrops instanceof EntityEnderman){
				prob = 30;
			}
			if(entityDrops instanceof EntitySilverfish){
				prob = 10;
			}
			prob = 20;
			return prob + (10*looting);
		}

	};

	protected CustomDropEvent<ItemStack> dropChitin = new CustomDropEvent<ItemStack>(new DropItemStack(RawMaterialItemRegistry.instance().chitin.getItemStack(1))){
		@Override
		public boolean canDrop(DamageSource source,EntityLivingBase entityDrops,int looting){

			return false;
		}

		@Override
		public int getDropValue(DamageSource source,EntityLivingBase entityDrops,int looting){
			return 30 + (10*looting);
		}
	};
	protected CustomDropEvent<ItemStack> dropNugget = new CustomDropEvent<ItemStack>(new DropItemStack(new ItemStack(Items.GOLD_NUGGET,1))){
		@Override
		public boolean canDrop(DamageSource source,EntityLivingBase entityDrops,int looting){
			EntityLivingBase living = (EntityLivingBase) source.getEntity();
			if(living!=null && living.isPotionActive(UnsagaPotions.instance().goldFinger)){
				return true;
			}
			return false;
		}

		@Override
		public int getDropValue(DamageSource source,EntityLivingBase entityDrops,int looting){
			return 30 + (8 * looting) + (HSLib.configHandler.isDebug() ? 100 : 0);
		}


	};
	protected CustomDropEvent<ItemStack> dropMush = new CustomDropEvent<ItemStack>(){
		@Override
		public boolean canDrop(DamageSource source,EntityLivingBase entityDrops,int looting){
			if(entityDrops instanceof EntityRuffleTree){
				return true;

			}
			if(entityDrops instanceof EntitySignalTree){
				return true;

			}
			return false;
		}

		@Override
		public Droppable<ItemStack> getDroppable(LivingDropsEvent e){
			Function<LivingDropsEvent,ItemStack> func = in ->{
				if(e.getEntityLiving().getRNG().nextInt(2)==0){
					return new ItemStack(Blocks.RED_MUSHROOM);
				}
				return new ItemStack(Blocks.BROWN_MUSHROOM);
			};
			ItemStack is = func.apply(e);
			DropItemStack drop = new DropItemStack(is);
			return drop;
		}


		@Override
		public int getDropValue(DamageSource source,EntityLivingBase entityDrops,int looting){
			return 100;
		}

	};
	public void init(){
		HSLib.core().addMobDrop(dropTablet.setLogger(UnsagaMod.logger,"TabletDrop"));
		HSLib.core().addMobDrop(dropChest.setLogger(UnsagaMod.logger,"Chest"));
		HSLib.core().addMobDrop(dropChitin.setLogger(UnsagaMod.logger,"Chitin"));
		HSLib.core().addMobDrop(dropMush.setLogger(UnsagaMod.logger,"Mush"));
		HSLib.core().addMobDrop(dropNugget.setLogger(UnsagaMod.logger,"nugget"));
	}
}
