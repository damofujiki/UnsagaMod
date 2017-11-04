package mods.hinasch.unsagaadditional;


import mods.hinasch.lib.config.EventConfigChanged;
import mods.hinasch.lib.misc.LogWrapper;
import mods.hinasch.unsagaadditional.net.CommonProxy;
import mods.hinasch.unsagaadditional.world.OreGeneraterGroup;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = UnsagaModAdditional.MODID  ,name = UnsagaModAdditional.NAME , version= UnsagaModAdditional.VERSION,dependencies="after:hinasch.unsaga;required-after:hinasch.lib")//,guiFactory="mods.hinasch.unsagaadditional.client.ModGuiFactory")
public class UnsagaModAdditional {

	@SidedProxy(modId= UnsagaModAdditional.MODID,clientSide = "mods.hinasch.unsagaadditional.client.ClientProxy", serverSide = "mods.hinasch.unsagaadditional.net.CommonProxy")
	public static CommonProxy proxy;
	@Instance(UnsagaModAdditional.MODID)
	public static UnsagaModAdditional instance;
	public static final String MODID = "hinasch.unsaga.additional";
	public static final String NAME = "Unsaga Mod Additional Pack";
	public static final String VERSION = "1.0.0 16/8/16";

	public static Configuration configFile;
	public static final mods.hinasch.unsagaadditional.init.ConfigHandler configHandler = new mods.hinasch.unsagaadditional.init.ConfigHandler();

	public static mods.hinasch.unsagaadditional.init.Blocks blocks = new mods.hinasch.unsagaadditional.init.Blocks();
	public static mods.hinasch.unsagaadditional.init.Items items = new mods.hinasch.unsagaadditional.init.Items();
	public static LogWrapper logger;

	public static mods.hinasch.unsagaadditional.init.MiscItemPropertyGroup miscItems = new mods.hinasch.unsagaadditional.init.MiscItemPropertyGroup();
	public static mods.hinasch.unsagaadditional.init.OreBlockPropertyGroup ores = new mods.hinasch.unsagaadditional.init.OreBlockPropertyGroup();
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = LogWrapper.create(event);
		this.configFile = new Configuration(event.getSuggestedConfigurationFile());
		this.configFile.load();
		configHandler.setConfigFile(configFile);
		configHandler.init();
		configHandler.syncConfig();

		ores.preInit();
		miscItems.preInit();
		blocks.register();
		items.register();

		this.configFile.save();
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		miscItems.init();
		ores.init();
		proxy.registerRenderers();

		EventConfigChanged.instance().addConfigHandler(MODID, configHandler);
		new OreGeneraterGroup().register();
		new mods.hinasch.unsagaadditional.init.Recipes().register();
	}
}
