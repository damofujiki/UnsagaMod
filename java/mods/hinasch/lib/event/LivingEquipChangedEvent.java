package mods.hinasch.lib.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;

public class LivingEquipChangedEvent extends LivingEvent{

	public LivingEquipChangedEvent(EntityLivingBase entity) {
		super(entity);
//		UnsagaMod.logger.trace(this.getClass().getName(), "called");
	}

}
