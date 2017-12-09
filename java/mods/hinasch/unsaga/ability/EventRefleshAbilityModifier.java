package mods.hinasch.unsaga.ability;

import mods.hinasch.lib.entity.ModifierHelper;
import mods.hinasch.lib.event.LivingEquipChangedEvent;
import mods.hinasch.unsaga.UnsagaMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventRefleshAbilityModifier {

	@SubscribeEvent
	public void onEquipChanged(LivingEquipChangedEvent ev){
		EntityLivingBase el = ev.getEntityLiving();
		if(el instanceof EntityPlayer){
			AbilityRegistry.instance().getModifierBaseMaps().entrySet().forEach(entry ->{

				double d = AbilityAPI.getAllAbilityModifiers(el).stream().filter(in -> in.getFirst()==entry.getKey())
						.mapToDouble(in -> in.getSecond()).peek(in -> UnsagaMod.logger.trace(entry.getKey().getAttributeUnlocalizedName(), in)).sum();

//				if(d>0.0F){
					AttributeModifier mod = new AttributeModifier(entry.getValue().getID(),entry.getValue().getName(),d,entry.getValue().getOperation());
					UnsagaMod.logger.trace("modifier", "apply",mod);
					ModifierHelper.refleshModifier(el, entry.getKey(), mod);
//				}

			});



//			AttributeModifier healModifier = new AttributeModifier(UUID.fromString("562a1c22-6d62-423a-9cc2-55fc15ba6525"), "healAttribute", -amount * 1.5D, Statics.OPERATION_INCREMENT);
//			ModifierHelper.refleshModifier(el, AdditionalStatus.NATURAL_HEAL_SPEED, healModifier);

		}
	}
}
