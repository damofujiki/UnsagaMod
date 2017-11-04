package mods.hinasch.lib.item;

import java.util.Collection;
import java.util.Random;

import mods.hinasch.lib.misc.LogWrapper;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class CustomDropEvent<T> {

	/** EntiytもしくはItemStack,Block*/
	public Droppable<T> droppable;
	public String logIdentifier;
	public LogWrapper logger;

	public CustomDropEvent(){
		this.droppable = null;
	}

	public CustomDropEvent(Droppable<T> t){
		this.droppable = t;
	}

	public boolean canDrop(DamageSource source,EntityLivingBase entityDrops,int looting){
		return false;
	}

	public int getDropValue(DamageSource source,EntityLivingBase entityDrops,int looting){
		return 0;
	}

	public Droppable<T> getDroppable(LivingDropsEvent e){

		return this.droppable;
	}

	public Entity getEntity(LivingDropsEvent e){
		return null;
	}

	public ItemStack getItemStack(LivingDropsEvent e){
		return null;
	}

	public Block getBlock(LivingDropsEvent e){
		return null;
	}
	public void drop(LivingDropsEvent e){
		EntityLivingBase entityDropped = e.getEntityLiving();
		Random rand = entityDropped.getRNG();
		World world = entityDropped.getEntityWorld();
		XYZPos positionDeath = XYZPos.createFrom(entityDropped);
		//WorldHelper wh = new WorldHelper(world);

//		T var1 = this.getDroppable(e);
		this.debug(positionDeath);

		if(this.getDroppable(e)==null)return;

		this.getDroppable(e).drop(e);
	}

	public int getSlimeSize(EntityLivingBase living){
		if(living instanceof EntitySlime){
			return ((EntitySlime) living).getSlimeSize();
		}
//		if(living instanceof EntityTreasureSlime){
//			return 2;
//		}
		return -1;
	}

	public void debug(Object... par1){
		if(this.logger!=null){
			this.logger.trace(this.logIdentifier,par1);
		}
	}

	public CustomDropEvent setLogger(LogWrapper d,String identifi){
		this.logger = d;
		this.logIdentifier = identifi;
		return this;
	}
	public static void processDrop(final LivingDropsEvent e,Collection<CustomDropEvent> drops){
		final EntityLivingBase entityToDrop = e.getEntityLiving();
		Random rand = entityToDrop.getRNG();
		World world = entityToDrop.worldObj;
		XYZPos positionDeath = XYZPos.createFrom(entityToDrop);
		//WorldHelper wh = new WorldHelper(world);
		final int prob = rand.nextInt(100);

		if(drops!=null && e.getSource().getEntity() instanceof EntityLivingBase && isKilledByPlayer((EntityLivingBase) e.getSource().getEntity())){

			drops.stream().filter(dropEvent -> dropEvent.canDrop(e.getSource(), entityToDrop, e.getLootingLevel()))
			.filter(dropEvent ->dropEvent.getDropValue(e.getSource(), entityToDrop, e.getLootingLevel())>prob)
			.forEach(dropEvent -> dropEvent.drop(e));
		}
	}


	public static boolean isKilledByPlayer(EntityLivingBase attacker){
		if(attacker instanceof EntityPlayer){
			return true;
		}
		if(attacker instanceof EntityTameable){
			if(((EntityTameable) attacker).isTamed()){
				return true;
			}
		}
		return false;
	}

	public static abstract class Droppable<T>{

		T drop;
		public Droppable(T drop){
			this.drop = drop;
		}
		public T getDrop(LivingDropsEvent e){
			return this.drop;
		}
		public abstract void drop(LivingDropsEvent e);
	}

	public static class DropItemStack extends  Droppable<ItemStack>{

		public DropItemStack(ItemStack drop) {
			super(drop);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void drop(LivingDropsEvent e) {
			World world = e.getEntityLiving().getEntityWorld();
			XYZPos positionDeath = XYZPos.createFrom(e.getEntityLiving());
			ItemStack dropStack = this.getDrop(e);
			e.getDrops().add(ItemUtil.getEntityItem(dropStack, positionDeath, world));
		}

	}
	public static class DropEntity extends  Droppable<Entity>{

		public DropEntity(Entity drop) {
			super(drop);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public void drop(LivingDropsEvent e) {
			Entity droppingEntity = this.getDrop(e);
			World world = e.getEntityLiving().getEntityWorld();
			XYZPos positionDeath = XYZPos.createFrom(e.getEntityLiving());
			droppingEntity.setPosition(positionDeath.dx, positionDeath.dy, positionDeath.dz);
			if(WorldHelper.isServer(world)){
				world.spawnEntityInWorld(droppingEntity);

			}
		}

	}
}
