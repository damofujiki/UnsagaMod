package mods.hinasch.lib.world;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScannerNew {


	World world;
	BlockPos start;
	BlockPos base;
	BlockPos end;
	Iterable<BlockPos> iterable;

	public static ScannerNew create(){
		return new ScannerNew();
	}


	public ScannerNew base(BlockPos base){
		this.base = base;
		return this;
	}

	public ScannerNew base(Entity entity){
		this.base = entity.getPosition();
		return this;
	}
	/**
	 * xrange 2だと
	 * 左に２、右に２をとって始点含めて５ブロックとなる。
	 * */
	public ScannerNew range(int xrange,int yrange,int zrange){
		this.start = this.base.add(-xrange, -yrange, -zrange);
		this.end = this.base.add(xrange, yrange, zrange);
		return this;
	}

	public ScannerNew range(int range){
		this.start = this.base.add(-range, -range, -range);
		this.end = this.base.add(range, range, range);
		return this;
	}

	public ScannerNew to(BlockPos end){
		this.start = this.base;
		this.end = end;
		return this;
	}

	public ScannerNew ready(){
		this.iterable = BlockPos.getAllInBox(start, end);
		return this;
	}

	public Iterable<BlockPos> getIterableInstance(){
		return this.iterable;
	}
	public Stream<BlockPos> stream(){

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(this.iterable.iterator(), Spliterator.NONNULL), false);
	}

	public String toString(){

		StringBuilder builder = new StringBuilder("");
		builder.append("start:"+this.start);
		builder.append("end"+this.end);
		builder.append("hasnext:"+this.iterable.iterator().hasNext());
		return builder.toString();
	}
}
