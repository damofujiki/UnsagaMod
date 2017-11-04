package mods.hinasch.unsaga.minsaga;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.minsaga.ForgingCapability.ForgeAttribute;
import mods.hinasch.unsaga.minsaga.ForgingCapability.IMinsagaForge;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventFittingProgress extends LivingHurtEventUnsagaBase{


	@Override
	public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		if(e.getSource().getEntity() instanceof EntityLivingBase){
			EntityLivingBase el = (EntityLivingBase) e.getSource().getEntity();
			return ItemUtil.isItemStackPresent(el.getHeldItemMainhand())
					&& ForgingCapability.adapter.hasCapability(el.getHeldItemMainhand());
		}

		return false;
	}

	@Override
	public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {

		EntityLivingBase el = (EntityLivingBase) e.getSource().getEntity();
		ItemStack stack = el.getHeldItemMainhand();

		IMinsagaForge capa = ForgingCapability.adapter.getCapability(stack);
		if(capa.isForged() && capa.getCurrentForge()!=null){
			ForgeAttribute attribute = capa.getCurrentForge();
			if(attribute.getFittingProgress()<attribute.getMaxFittingProgress()){
				attribute.setFittingProgress(attribute.getFittingProgress()+ (HSLib.configHandler.isDebug() ? 50 : 1));
			}

			MinsagaUtil.damageToolProcess(el,stack,capa.getDurabilityModifier(),el.getRNG());
		}






		return dsu;
	}

	@Override
	public String getName() {
		// TODO 自動生成されたメソッド・スタブ
		return "forge fitting event";
	}


}
