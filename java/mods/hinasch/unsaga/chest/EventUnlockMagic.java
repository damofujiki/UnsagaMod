package mods.hinasch.unsaga.chest;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.entity.projectile.EntityBlaster;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityBubbleBlow;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.SoundEvents;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventUnlockMagic {


	@SubscribeEvent
	public void onAttack(LivingAttackEvent ev){

		if(ev.getEntityLiving() instanceof EntityUnsagaChestNew){
			if(ev.getSource().getEntity() instanceof EntityPlayer){
				UnsagaMod.logger.trace(this.getClass().getName(), ev.getSource().isMagicDamage());
				if(this.isUnlockableEntity(ev.getSource().getSourceOfDamage()) || ev.getSource().isMagicDamage()){
					this.unlockMagicLock((EntityUnsagaChestNew)ev.getEntityLiving(), (EntityPlayer)ev.getSource().getEntity());
					ev.setCanceled(true);
				}
//				if(ev.getSource().isMagicDamage() || this.isFireArrow(ev.getSource().getSourceOfDamage())){
//					if(this.unlockMagicLock((EntityUnsagaChestNew)ev.getEntityLiving(), (EntityPlayer)ev.getSource().getEntity())){
//						ev.setCanceled(true);
//					}
//				}
			}
		}

	}

	@Deprecated
	private boolean isUnlockableEntity(Entity entity){
		if(entity instanceof EntityBoulder){
			return true;
		}
		if(entity instanceof EntityCustomArrow && EntityStateCapability.adapter.hasCapability(entity)){
			StateArrow.Type type = ((EntityCustomArrow)entity).getArrowType();
			return type==StateArrow.Type.MAGIC_ARROW;
		}
		if(entity instanceof EntityBlaster){
			return true;
		}
		if(entity instanceof EntityBubbleBlow){
			return true;
		}
		if(entity instanceof EntityPotion){
			return true;
		}
		return false;
	}


	public boolean unlockMagicLock(EntityUnsagaChestNew chest,EntityPlayer ep){
		if(WorldHelper.isServer(ep.getEntityWorld())){
			if(chest.getEntityWorld().rand.nextInt(3)==0){
				HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.BLOCK_ANVIL_LAND,chest), (EntityPlayerMP) ep);
				ChatHandler.sendChatToPlayer(ep, HSLibs.translateKey("gui.unsaga.chest.failed.unlock.magicLock"));
			}else{
				if(ChestCapability.adapterEntity.hasCapability(chest) && ChestCapability.adapterEntity.getCapability(chest).hasMagicLocked()){
					ChestCapability.adapterEntity.getCapability(chest).setMagicLocked(false);
					chest.sync(ep);
					HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.ENTITY_PLAYER_LEVELUP,chest), (EntityPlayerMP) ep);
					ChatHandler.sendChatToPlayer(ep, HSLibs.translateKey("gui.unsaga.chest.success.unlock.magicLock"));
					return true;
				}
			}

		}
		return false;
	}
}
