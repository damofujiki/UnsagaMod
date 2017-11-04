package mods.hinasch.lib.world;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.sync.SafeUpdateEventByInterval;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractConnectScanner extends SafeUpdateEventByInterval{


	protected Set<BlockPos> scheduledBreakPool = Sets.newConcurrentHashSet();
	final protected ImmutableSet<IBlockState> baseBlocks;

	Set<BlockPos> nextCheckPos = Sets.newHashSet();
	boolean checked = false;
	/**
	 * 始点
	 */
	final protected BlockPos startPoint;
//	protected WorldHelper helper;


	protected int index = 0;
	final protected World world;
	final protected int length;

	Iterator<BlockPos> iterator;
	/**
	 * これを見て終わるかどうか判断
	 */
	public boolean finish = false;

	public AbstractConnectScanner(World world,Set<IBlockState> compareBlock,BlockPos startpoint,int length,EntityLivingBase sender){
		super(sender,"connectedScanner");
		this.baseBlocks = ImmutableSet.copyOf(compareBlock);
		this.nextCheckPos.add(startpoint);
		this.scheduledBreakPool.add(startpoint);
		this.startPoint = startpoint;
		this.length = length;
		this.world = world;
//		this.helper = new WorldHelper(world);
	}


	public void checkConnectedPos(){
		if(index<this.length){
			Set<BlockPos> prevCheckPos = Sets.newHashSet(this.nextCheckPos);
			this.nextCheckPos.clear();
			for(BlockPos p:prevCheckPos){
				for(BlockPos addedPos:Lists.newArrayList(p.up(),p.down(),p.west(),p.east(),p.north(),p.south())){
					IBlockState state = this.world.getBlockState(addedPos);
					if(!this.scheduledBreakPool.contains(addedPos)){
						if(this.baseBlocks.stream().anyMatch(in ->in.getBlock()==state.getBlock())){
							this.nextCheckPos.add(addedPos);
						}
					}
				}
			}
			this.scheduledBreakPool.addAll(this.nextCheckPos);
			index++;
		}else{
			this.iterator = this.scheduledBreakPool.iterator();
			this.checked = true;
		}

	}

	@Override
	public void loopByInterval(){

		if(!this.checked){
			this.checkConnectedPos();
			return;
		}
		if(this.iterator.hasNext()){

			BlockPos breakPos = this.iterator.next();
			UnsagaMod.logger.trace("ite", breakPos);
			IBlockState state = this.world.getBlockState(breakPos);
			if(state.getBlock()!=Blocks.AIR){
				this.onCheckScheduledPos(world, state, breakPos);
			}
		}

//		if(this.index<this.length){
//			List<BlockPos> listToRemove = new ArrayList();
//			List<BlockPos> listToAdd = new ArrayList();
//			for(BlockPos aPos:scheduledBreakPool){
//				Block block = world.getBlockState(aPos).getBlock();
//				//Block block = world.getBlock(pos.x, pos.y, pos.z);
//				if(block!=Blocks.AIR ){
//
//					this.onCheckScheduledPos(world, world.getBlockState(aPos), aPos);
//				}
//
//				listToRemove.add(aPos);
//				for(BlockPos rotate:XYZPos.around){
//					BlockPos rotatedPos = aPos.add(rotate);
//					IBlockState ib = world.getBlockState(rotatedPos);
////					PairID roundBlockData = new PairID(ib.getBlock(),Block.getStateId(ib));
////					PairID pairCompare = new PairID(compareBlock);
////
//
//					this.addToList(baseBlocks, ib, rotatedPos, listToAdd);
//
////					if(pairCompare.equalsOrSameBlock(roundBlockData)){
////						listToAdd.add(addedPos);
////					}
////					if((compareBlock.getBlock() instanceof BlockRedstoneOre) && (roundBlockData.getBlockObject() instanceof BlockRedstoneOre)){
////						listToAdd.add(addedPos);
////					}
//
//				}
//
//			}
//			//消去を同時にやると排他関係で怒られるので、分けてやる
//			for(BlockPos rm:listToRemove){
//				scheduledBreakPool.remove(rm);
//			}
//
//			for(BlockPos ad:listToAdd){
//				scheduledBreakPool.add(ad);
//			}
//
//
//		}else{
//			this.finish = true;
//			return;
//		}
//		this.index += 1;


	}


	@Override
	public boolean hasFinished(){
		return this.iterator!=null && !this.iterator.hasNext();
	}

	public void addToList(List<IBlockState> blockToCompare,IBlockState checkBlock,BlockPos rotatedPos,List<BlockPos> listToAdd){

		for(IBlockState aBlockState:blockToCompare){
			if(aBlockState.getBlock()==checkBlock.getBlock()){
				listToAdd.add(rotatedPos);
			}
		}

		return;
	}

	abstract public void onCheckScheduledPos(World world,IBlockState currentBlock,BlockPos currentPos);

//	public static class XYZPool extends Pool<BlockPos>{
//
//		public XYZPool(int size) {
//			super(size, new XYZPos(0,0,0));
//
//		}
//
//		@Override
//		public boolean equalElements(BlockPos left,BlockPos right){
//			return left==right;
//		}
//	}
}
