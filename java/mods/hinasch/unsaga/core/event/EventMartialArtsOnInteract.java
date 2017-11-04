//package mods.hinasch.unsaga.core.event;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//import com.google.common.base.Predicate;
//
//import mods.hinasch.lib.util.VecUtil;
//import mods.hinasch.lib.util.VecUtil.EnumHorizontalDirection;
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.ability.wazaeffect.wazagroup.WazaGroupMartialArts;
//import mods.hinasch.unsaga.ability.wazaeffect.wazagroup.WazaGroupMartialArts.WazaMartialArts;
//import mods.hinasch.unsaga.core.net.packet.PacketEntityInteractionWithItem;
//import mods.hinasch.unsaga.skillpanel.SkillPanels;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//
//
//public class EventMartialArtsOnInteract implements Predicate<EntityInteract>{
//
//	@SubscribeEvent
//	public void interact(EntityInteract e){
//		if(!e.getEntityPlayer().getEntityWorld().isRemote){
//			return;
//		}
//		if(e.getEntityLiving() instanceof EntityPlayer && this.apply(e) && e.getTarget() instanceof EntityLivingBase){
//			EntityPlayer ep = (EntityPlayer) e.getEntityLiving();
//			final int level = SkillPanels.getHighestLevelPanel(ep.getEntityWorld(), ep, SkillPanels.instance().punch) + 1;
//			final List<EnumHorizontalDirection> playerDirections = VecUtil.getHorizontalDirection(ep);
//			UnsagaMod.logger.trace("matrialArts", playerDirections);
//			Set<EnumHorizontalDirection> directionsKeySet = WazaGroupMartialArts.getDirectionsKeySet();
//			Optional<WazaMartialArts> wazaEffect = directionsKeySet.stream().filter(dirIn -> playerDirections.contains(dirIn))
//					.map(dirIn -> WazaGroupMartialArts.getWazaFromDirection(dirIn)).filter(waza -> waza.getRequireLevel()<=level).findFirst();
////			Optional<WazaEffect> wazaEffect = ListHelper.stream(WazaGroupMartialArts.refer.keySet()).map(new Function<EnumHorizontalDirection,WazaEffect>(){
////
////				@Override
////				public WazaEffect apply(EnumHorizontalDirection input) {
////					if(directions.contains(input) && WazaGroupMartialArts.refer.get(input).getRequireLevel()<=level){
////						UnsagaMod.logger.trace("matrialArts", WazaGroupMartialArts.refer.get(input));
////						return WazaGroupMartialArts.refer.get(input);
////					}
////					return null;
////				}}
////			).findFirst();
//			if(wazaEffect.isPresent()){
//				UnsagaMod.packetDispatcher.sendToServer(PacketEntityInteractionWithItem.createMartialArts(e.getTarget(), wazaEffect.get().getWaza()));
//				WazaPerformer performer = new WazaPerformer(ep.getEntityWorld(), ep, wazaEffect.get().getWaza(), null);
//				performer.setTarget(e.getTarget());
//				performer.perform();
//			}
//		}
//	}
//
//	@Override
//	public boolean apply(EntityInteract e) {
//		if(e.getEntityLiving().getHeldItemMainhand()==null && e.getEntityLiving().getHeldItemOffhand()==null){
//
//			if(SkillPanels.hasPanel(e.getEntityLiving().getEntityWorld(), (EntityPlayer) e.getEntityLiving(), SkillPanels.instance().punch)){
//				return true;
//			}
//		}
//		return false;
//	}
//
//
//}
