package mods.hinasch.unsaga.status;

import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventDisabledLifePoint extends LivingHurtEventUnsagaBase{

	@Override
	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		// TODO 自動生成されたメソッド・スタブ
		return !UnsagaMod.configHandler.isEnabledLifePointSystem();
	}

	@Override
	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		if(e.getSource().getEntity() instanceof EntityLivingBase){
			EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
			double glazeProb = (1.0D - attacker.getEntityAttribute(AdditionalStatus.DEXTALITY).getAttributeValue()) * 0.15D;
			double critProb = (attacker.getEntityAttribute(AdditionalStatus.DEXTALITY).getAttributeValue()-  1.0D) * 0.15D;
			glazeProb = MathHelper.clamp_double(glazeProb, 0, 0.99D);
			critProb = MathHelper.clamp_double(critProb, 0, 0.99D);
			float prob = attacker.getRNG().nextFloat();
			if(glazeProb>prob){
				if(attacker instanceof EntityPlayer){
					ChatHandler.sendChatToPlayer((EntityPlayer) attacker, "Attack Glazed...");
				}
				if(e.getEntityLiving() instanceof EntityPlayer){
					ChatHandler.sendChatToPlayer((EntityPlayer) e.getEntityLiving(), "Enemy's Attack Glazed.");
				}
				e.setAmount(e.getAmount() * 0.5F);
			}
			if(critProb>prob){
				if(attacker instanceof EntityPlayer){
					ChatHandler.sendChatToPlayer((EntityPlayer) attacker, "Critical!!");
				}
				e.setAmount(e.getAmount() * 2.0F);
			}
		}
		// TODO 自動生成されたメソッド・スタブ
		return dsu;
	}

	private void sendMessage(EntityPlayer ep,String message){

	}
	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "Event Disabled LP";
	}

}
