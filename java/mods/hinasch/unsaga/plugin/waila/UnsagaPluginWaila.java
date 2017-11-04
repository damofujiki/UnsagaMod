package mods.hinasch.unsaga.plugin.waila;

import mcp.mobius.waila.api.IWailaRegistrar;

public class UnsagaPluginWaila {


	public static void callbackRegister(IWailaRegistrar registrar){
		System.out.println("Wailaへ登録します");
		HUDHandlerLP.register(registrar);
//		HUDHandlerBlock.register(registrar);
	}
}
