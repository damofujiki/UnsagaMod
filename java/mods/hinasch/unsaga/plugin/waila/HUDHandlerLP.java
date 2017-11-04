package mods.hinasch.unsaga.plugin.waila;

import java.util.List;
import java.util.stream.Collectors;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mods.hinasch.unsaga.lp.LifePoint;
import mods.hinasch.unsaga.status.AdditionalStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class HUDHandlerLP implements IWailaEntityProvider{

	@Override
	public Entity getWailaOverride(IWailaEntityAccessor paramIWailaEntityAccessor,
			IWailaConfigHandler paramIWailaConfigHandler) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<String> getWailaHead(Entity paramEntity, List<String> paramList,
			IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<String> getWailaBody(Entity paramEntity, List<String> paramList,
			IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		if(paramEntity instanceof EntityLivingBase){

			EntityLivingBase living = (EntityLivingBase) paramEntity;
			if(LifePoint.adapter.hasCapability(paramEntity)){
				int max = LifePoint.adapter.getCapability(paramEntity).getMaxLifePoint();
				int lp = LifePoint.adapter.getCapability(paramEntity).getLifePoint();
				String lpStr = String.format("LP %d/%d", lp,max);
				if(paramIWailaEntityAccessor.getPlayer().isCreative()){

					double dex = living.getEntityAttribute(AdditionalStatus.DEXTALITY).getBaseValue();
					double inte = living.getEntityAttribute(AdditionalStatus.INTELLIGENCE).getBaseValue();
					double men = living.getEntityAttribute(AdditionalStatus.MENTAL).getBaseValue();
					double lpdef = living.getEntityAttribute(AdditionalStatus.RESISTANCE_LP_HURT).getBaseValue();
					String st = String.format("Dex. %.2f/Men. %.2f/Int. %.2f/LP Hurt Res. %.2f", dex,inte,men,lpdef);
					paramList.add(st);
					String generals = "Melee Res.["+AdditionalStatus.GENERALS.values().stream()
							.map(at ->AdditionalStatus.getTypeString(at) +" " +living.getEntityAttribute(at).getBaseValue()+"/")
							.collect(Collectors.joining())+"]";
					String subs = "Elemental Res.["+AdditionalStatus.SUBS.values().stream()
							.map(at ->AdditionalStatus.getTypeString(at) +" " +living.getEntityAttribute(at).getBaseValue()+"/")
							.collect(Collectors.joining())+"]";
					paramList.add(generals);
					paramList.add(subs);
				}
				paramList.add(lpStr);
			}
		}
		return paramList;
	}

	@Override
	public List<String> getWailaTail(Entity paramEntity, List<String> paramList,
			IWailaEntityAccessor paramIWailaEntityAccessor, IWailaConfigHandler paramIWailaConfigHandler) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP paramEntityPlayerMP, Entity paramEntity,
			NBTTagCompound paramNBTTagCompound, World paramWorld) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public static void register(IWailaRegistrar registrar){
		registrar.addConfig("UnsagaMod", "hinasch.unsaga.showLP",true);

		registrar.registerBodyProvider(new HUDHandlerLP(), EntityLivingBase.class);
	}
}
