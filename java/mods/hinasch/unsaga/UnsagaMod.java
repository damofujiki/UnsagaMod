package mods.hinasch.unsaga;

import java.security.SecureRandom;

import mods.hinasch.lib.capability.CapabilityAdapterFactory;
import mods.hinasch.lib.client.IGuiAttribute;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.IModBase;
import mods.hinasch.lib.misc.LogWrapper;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.unsaga.core.command.CommandSetAbility;
import mods.hinasch.unsaga.core.command.CommandTestDebuff;
import mods.hinasch.unsaga.core.command.CommandUnsaga;
import mods.hinasch.unsaga.core.net.CommonProxy;
import mods.hinasch.unsaga.core.net.packet.PacketAddExhaution;
import mods.hinasch.unsaga.core.net.packet.PacketClientScanner;
import mods.hinasch.unsaga.core.net.packet.PacketClientThunder;
import mods.hinasch.unsaga.core.net.packet.PacketDebugPos;
import mods.hinasch.unsaga.core.net.packet.PacketGuiButtonUnsaga;
import mods.hinasch.unsaga.core.net.packet.PacketLP;
import mods.hinasch.unsaga.core.net.packet.PacketLP.PacketLPHandler;
import mods.hinasch.unsaga.core.net.packet.PacketSyncActionPerform;
import mods.hinasch.unsaga.core.net.packet.PacketSyncServerTargetHolder;
import mods.hinasch.unsaga.core.net.packet.PacketSyncSkillPanel;
import mods.hinasch.unsaga.init.UnsagaConfigHandler;
import mods.hinasch.unsaga.init.UnsagaGui;
import mods.hinasch.unsaga.plugin.UnsagaPluginIntegration;
import mods.hinasch.unsagamagic.UnsagaMagic;
import net.minecraft.command.ICommandManager;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = UnsagaMod.MODID  ,name = UnsagaMod.NAME , version= UnsagaMod.VERSION,dependencies="required-after:hinasch.lib",guiFactory="mods.hinasch.unsaga.core.client.ModGuiFactoryUnsaga")
public class UnsagaMod implements IModBase{


	@SidedProxy(modId= UnsagaMod.MODID,clientSide = "mods.hinasch.unsaga.core.client.ClientProxy", serverSide = "mods.hinasch.unsaga.core.net.CommonProxy")
	public static CommonProxy proxy;
	@Instance(UnsagaMod.MODID)
	public static UnsagaMod instance;
	public static final String MODID = "hinasch.unsaga";
	public static final String NAME = "Unsaga Mod";
	public static final String VERSION = "build on 17/12/09";

	public static Configuration configFile;
	public static final UnsagaConfigHandler configHandler = new UnsagaConfigHandler();

	public static SecureRandom secureRandom = new SecureRandom();

	public static LogWrapper logger = LogWrapper.newLogger(MODID);


	public static UnsagaModCore core = UnsagaModCore.instance();
	public static UnsagaMagic magic = core.magic;



	public static CapabilityAdapterFactory capabilityFactory = new CapabilityAdapterFactory(UnsagaMod.MODID);




	public static final SimpleNetworkWrapper packetDispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	public static UnsagaPluginIntegration plugin = new UnsagaPluginIntegration();

	@EventHandler
	public void registerCommands(FMLServerStartingEvent  e){
		MinecraftServer server = e.getServer().getServer();
		ICommandManager manager = server.getCommandManager();
		ServerCommandManager smanager = (ServerCommandManager)manager;
		smanager.registerCommand(new CommandSetAbility());
		smanager.registerCommand(new CommandTestDebuff());
		smanager.registerCommand(new CommandUnsaga());
		if(HSLib.configHandler.isDebug()){
//			smanager.registerCommand(new CommandCheckRegisteredEvents());
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{

		this.configFile = new Configuration(event.getSuggestedConfigurationFile());

		this.configFile.load();
		configHandler.setConfigFile(configFile);
		configHandler.init();
		configHandler.syncConfig();

		core.preInit();
		proxy.registerEntityRenderers();
//		core.preInit();
//
//		proxy.registerEntityRenderers();
//
//		configHandler.checkValidation();
		this.configFile.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{

		core.init(e);

		proxy.registerKeyHandlers();
		proxy.registerRenderers();

		proxy.registerEvents();
//
//
//		core.init(e);
//		//HSLib.eventLivingHurt.getEventsPre().sort(null);
//
//		packetDispatcher.registerMessage(PacketSkillHandler.class, PacketSkill.class, 1, Side.SERVER);
		packetDispatcher.registerMessage(PacketLPHandler.class, PacketLP.class, 1, Side.CLIENT);
		packetDispatcher.registerMessage(PacketSyncServerTargetHolder.Handler.class, PacketSyncServerTargetHolder.class, 2, Side.SERVER);
		packetDispatcher.registerMessage(PacketGuiButtonUnsaga.Handler.class, PacketGuiButtonUnsaga.class, 3, Side.SERVER);
		packetDispatcher.registerMessage(PacketSyncCapability.Handler.class, PacketSyncCapability.class, 4, Side.CLIENT);
		packetDispatcher.registerMessage(PacketLPHandler.class, PacketLP.class, 5, Side.SERVER);
		packetDispatcher.registerMessage(PacketSyncSkillPanel.Handler.class, PacketSyncSkillPanel.class, 6, Side.CLIENT);
		packetDispatcher.registerMessage(PacketDebugPos.Handler.class, PacketDebugPos.class, 7, Side.CLIENT);
		packetDispatcher.registerMessage(PacketClientScanner.Handler.class, PacketClientScanner.class, 8, Side.CLIENT);
		packetDispatcher.registerMessage(PacketClientThunder.Handler.class, PacketClientThunder.class, 9, Side.CLIENT);
		packetDispatcher.registerMessage(PacketAddExhaution.Handler.class, PacketAddExhaution.class, 10, Side.SERVER);
		packetDispatcher.registerMessage(PacketSyncActionPerform.Handler.class, PacketSyncActionPerform.class, 11, Side.SERVER);
//		packetDispatcher.registerMessage(PacketSyncAbiltyHeldItem.Handler.class, PacketSyncAbiltyHeldItem.class, 3, Side.CLIENT);

////		packetDispatcher.registerMessage(PacketSyncEntityInfo.Handler.class, PacketSyncEntityInfo.class, 5, Side.CLIENT);
////		packetDispatcher.registerMessage(PacketSyncExp.Handler.class, PacketSyncExp.class, 5, Side.CLIENT);
//
//		packetDispatcher.registerMessage(PacketSyncSkillPanel.Handler.class, PacketSyncSkillPanel.class, 6, Side.CLIENT);
//		packetDispatcher.registerMessage(PacketSyncGui.Handler.class, PacketSyncGui.class, 7, Side.SERVER);
//		packetDispatcher.registerMessage(PacketSyncGui.Handler.class, PacketSyncGui.class, 8, Side.CLIENT);

//		packetDispatcher.registerMessage(PacketOpenGui.Handler.class, PacketOpenGui.class, 10, Side.SERVER);

//		packetDispatcher.registerMessage(PacketClientScanner.Handler.class, PacketClientScanner.class, 12, Side.CLIENT);
//		packetDispatcher.registerMessage(PacketChangeGuiMessage.Handler.class, PacketChangeGuiMessage.class, 13, Side.CLIENT);
//		packetDispatcher.registerMessage(PacketSyncSkillPanel.Handler.class, PacketSyncSkillPanel.class, 14, Side.SERVER);
//		packetDispatcher.registerMessage(PacketEntityInteractionWithItem.Handler.class, PacketEntityInteractionWithItem.class, 15, Side.SERVER);



//		PacketSyncCapability.syncCapabilityMap.put(XPHelper.DefaultImpl.ID_SYNC, XPHelper.CAPA);
//		PacketSyncCapability.syncCapabilityMap.put(DefaultIUnsagaPropertyItem.ID_SYNC, UnsagaCapability.unsagaPropertyItem());
//

		NetworkRegistry.INSTANCE.registerGuiHandler(UnsagaMod.instance, proxy);

		HSLib.core().registerGuiPacketHandler(this.getModGuiID(), this);

		plugin.checkLoadedModsInit();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event){

		this.core.postInit();


		plugin.checkLoadedMods();


	}


	@Override
	public Class<? extends IGuiAttribute> getGuiClass() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaGui.Type.class;
	}

	@Override
	public Object getModInstance() {
		// TODO 自動生成されたメソッド・スタブ
		return this.instance;
	}

	@Override
	public int getModGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
