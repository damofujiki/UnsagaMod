package mods.hinasch.lib.entity;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class RangedHelper<T> {

	List<AxisAlignedBB> boundings = Lists.newArrayList();
	final World world;
	final Entity origin;
	T parent;
	BiPredicate<RangedHelper<T>,EntityLivingBase> selector = (self,target) -> true;
	BiConsumer<RangedHelper<T>,EntityLivingBase> consumer = (self,target)->{};
	boolean ignoreSelf = true;
	boolean flag = false;

	public static <E> RangedHelper<E> create(World world,Entity origin,List<AxisAlignedBB> list){
		return new RangedHelper(world, origin, list);
	}
	public static <E> RangedHelper<E> create(World world,Entity origin,AxisAlignedBB... list){
		return new RangedHelper(world, origin, Lists.newArrayList(list));
	}
	public static <E> RangedHelper<E> create(World world,Entity origin,double aabb){
		return new RangedHelper(world, origin, aabb);
	}

	public static <E> RangedHelper<E> create(World world,Entity origin,double x,double y,double z){
		return new RangedHelper(world, origin, x,y,z);
	}
	public RangedHelper(World world,Entity origin,AxisAlignedBB aabb){
		this(world,origin,Lists.newArrayList(aabb));
	}

	public RangedHelper(World world,Entity origin,double range){
		this(world,origin,origin.getEntityBoundingBox().expandXyz(range));
	}

	public RangedHelper(World world,Entity origin,double x,double y,double z){
		this(world,origin,origin.getEntityBoundingBox().expand(x, y, z));
	}

	public RangedHelper(World world2, Entity origin2, List<AxisAlignedBB> list) {
		this.world = world2;
		this.origin = origin2;
		this.boundings = list;
	}
	public List<AxisAlignedBB> getBounding(){
		return this.boundings;
	}
	public RangedHelper<T> setParent(T parent){
		this.parent = parent;
		return this;
	}
	public RangedHelper<T> setIgnoreSelf(boolean par1){
		this.ignoreSelf = par1;
		return this;
	}

	public T getParent(){
		return this.parent;
	}
	public boolean invoke(){

		Set<EntityLivingBase> list = Sets.newHashSet();

		for(AxisAlignedBB bb:this.boundings){
			this.world.getEntitiesWithinAABB(EntityLivingBase.class, bb).forEach(in->{
				if(ignoreSelf){
					if(in!=origin && this.selector.test(this, in)){
						list.add(in);
					}
				}else{
					if(this.selector.test(this, in)){
						list.add(in);
					}

				}

			});
		}
		if(list.isEmpty()){
			flag = false;
		}else{
			flag = true;
		}

		list.forEach(in ->{

			consumer.accept(this, in);
		});
		return flag;
	}

	public boolean getFlag(){
		return this.flag;
	}

	public void setFlag(boolean par1){
		this.flag = par1;
	}
	public RangedHelper<T> setSelector(BiPredicate<RangedHelper<T>,EntityLivingBase> selector){
		this.selector = selector;
		return this;
	}
	public RangedHelper<T> setConsumer(BiConsumer<RangedHelper<T>,EntityLivingBase> consumer){
		this.consumer = consumer;
		return this;
	}

	/** 軸となるエンティティによってセレクターを自動設定する。*/
	public RangedHelper<T> setSelectorFromOrigin(){
		this.setSelector((self,target)->{
			if(self.origin instanceof EntityPlayer){
				return IMob.MOB_SELECTOR.apply(target);
			}
			return !IMob.MOB_SELECTOR.apply(target);
		});
		return this;
	}
}
