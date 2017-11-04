package mods.hinasch.lib.util;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.client.SoundQueueManager;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SoundAndSFX {


	static Map<World,Float> clientInterval = Maps.newHashMap();
	public static SoundHandler getSoundHandler(){
		return Minecraft.getMinecraft().getSoundHandler();
	}

	public static void playPositionedSoundRecord(ResourceLocation loc,float pitch){
		getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvent.REGISTRY.getObject(loc),pitch));
	}
	public static void playPositionedSoundRecord(SoundEvent event,float pitch){
		getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(event,pitch));
	}



	@SideOnly(Side.CLIENT)
	public static void playSound(World world,XYZPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay)
	{
		playSound(world, pos.dx, pos.dy, pos.dz, soundIn, category, pitch, pitch, distanceDelay);
	}
	/**
	 * コンカレントエラーをふせぐ？
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param soundIn
	 * @param category
	 * @param volume
	 * @param pitch
	 * @param distanceDelay
	 */
	@SideOnly(Side.CLIENT)
	public static void playSound(World world,double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay)
	{

//		SafeSoundPlay runnable = new SafeSoundPlay(world, x, y, z, soundIn, category, volume, pitch, distanceDelay);

		if(WorldHelper.isClient(world)){
			SoundQueueManager.Event event = new SoundQueueManager.Event(soundIn, volume, pitch, category, new XYZPos(x, y, z),world,distanceDelay);
			SoundQueueManager.instance().addQueue(event);
		}

//		if(!RenderPlayerHook.instance().isWaiting(ClientHelper.getPlayer())){
//			RenderPlayerHook.instance().setWaiting(ClientHelper.getPlayer());
//			double d0 = Minecraft.getMinecraft().getRenderViewEntity().getDistanceSq(x, y, z);
//			PositionedSoundRecord positionedsoundrecord = new PositionedSoundRecord(soundIn, category, volume, pitch, (float)x, (float)y, (float)z);
//
//			if(!getSoundHandler().isSoundPlaying(positionedsoundrecord)){
//				if (distanceDelay && d0 > 100.0D)
//				{
//					double d1 = Math.sqrt(d0) / 40.0D;
//					getSoundHandler().playDelayedSound(positionedsoundrecord, (int)(d1 * 20.0D));
//				}
//				else
//				{
//					getSoundHandler().playSound(positionedsoundrecord);
//				}
//			}
//		}


	}

	public static void playPlayerSound(SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay)
	{
		EntityPlayer p = ClientHelper.getPlayer();
		playSound(ClientHelper.getWorld(),p.posX,p.posY,p.posZ,soundIn,category,volume,pitch,distanceDelay);
	}
	/**
	 *
	 * @param world
	 * @param pos
	 * @param blockdata
	 * @param canDrop
	 */
	public static void playBlockBreakSFX(World world,BlockPos pos,IBlockState blockdata,boolean canDrop){
		//		if(world.rand.nextInt(2)==0){ //あまりSFXが数多くなるとコンカレントエラーが出る？
		//			world.playAuxSFX(2001, pos, 0);
		//			world.playEvent(2001, pos, Block.getStateId(blockdata) + (Block.getStateId(blockdata) << 12));






		SoundAndSFX.playSFX(world, blockdata, pos, Block.getStateId(blockdata));
//		world.playEvent(2001, pos, Block.getStateId(blockdata));
		//		}

		//		PlaySFXEvent.addEvent(2001, pos,Block.getIdFromBlock(blockdata.getBlock()) + (Block.getStateId(blockdata) << 12));
		//if(!world.isRemote){

		//Unsaga.logger.log("kiteru");;
		if(WorldHelper.isServer(world)){
			SafeBlockHandler runnable = new SafeBlockHandler(world, blockdata, pos, canDrop);
			WorldHelper.getWorldServer(world).addScheduledTask(runnable);
		}

		//}
	}

	protected static void playSFX(World w,IBlockState state,BlockPos blockPosIn,int data){
        Block block = Block.getBlockById(data & 4095);

        if (block.getDefaultState().getMaterial() != Material.AIR)
        {
            SoundType soundtype = block.getSoundType(state, w, blockPosIn, null);
            SoundAndSFX.playSound(ClientHelper.getWorld(), blockPosIn.getX(), blockPosIn.getY(), blockPosIn.getZ(), soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
//            ClientHelper.getWorldClient().playSound(blockPosIn, soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
        }

        ClientHelper.getMC().effectRenderer.addBlockDestroyEffects(blockPosIn, state);
	}
	public static void playBlockBreakSFX(World world,BlockPos pos,IBlockState blockdata){
		playBlockBreakSFX(world,pos,blockdata,false);
	}

	public static void playPlaceSound(World par3World,XYZPos xyz,SoundType sound,SoundCategory category){

		par3World.playSound((double)((float)xyz.dx + 0.5F), (double)((float)xyz.dy + 0.5F), (double)((float)xyz.dz + 0.5F), sound.getBreakSound(), category,(sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F,true);
	}

	public static class SafeSoundPlay implements Runnable{

		World world;
		XYZPos pos;
		SoundEvent sound;
		SoundCategory cate;
		float volume;
		float pitch;
		boolean distancedelay;

		public SafeSoundPlay(World world,double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay){
			this.world = world;
			this.pos = new  XYZPos(x,y,z);
			this.sound = soundIn;
			this.cate = category;
			this.volume = volume;
			this.pitch = pitch;
			this.distancedelay = distanceDelay;
		}
		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			PacketSound packet =PacketSound.atPos(sound, pos);
			HSLib.core().getPacketDispatcher().sendToAllAround(packet, PacketUtil.getTargetPointNear(pos, world));
		}

	}


	public static class SafeBlockHandler extends mods.hinasch.lib.sync.SafeBlockHandlerBase{


		boolean canDrop;


		public SafeBlockHandler(World world, IBlockState blockdata, BlockPos pos, boolean canDrop) {
			super(world, blockdata, pos);
			this.canDrop = canDrop;
		}


		@Override
		public void run() {
			boolean flag = world.setBlockToAir(pos);
			if (blockdata.getBlock() != null && flag) {
				blockdata.getBlock().onBlockDestroyedByPlayer(world, pos, blockdata);

				if(!canDrop){
					blockdata.getBlock().dropBlockAsItem(world, pos, blockdata,1);
				}




			}

		}

	}

}
