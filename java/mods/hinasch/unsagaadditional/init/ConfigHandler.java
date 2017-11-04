package mods.hinasch.unsagaadditional.init;

import mods.hinasch.lib.config.ConfigBase;
import mods.hinasch.lib.config.PropertyCustomNew;

public class ConfigHandler extends ConfigBase{

	PropertyCustomNew prop;

	public boolean isEnableGenBismuth() {
		return enableGenBismuth;
	}

	public boolean isEnableGenCinnabar() {
		return enableGenCinnabar;
	}

	public boolean isEnableGenRockSalt() {
		return enableGenRockSalt;
	}

	boolean enableGenBismuth = true;
	boolean enableGenCinnabar = true;
	boolean enableGenRockSalt = true;

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ
		prop = PropertyCustomNew.newInstance();
		prop.add(0, "enable.generation.bismuth", "set true to allow generation of bismuth ores.", true);
		prop.add(1, "enable.generation.cinnabar", "set true to allow generation of cinnabar ores.", true);
		prop.add(2, "enable.generation.rockSalt", "set true to allow generation of rocksalts.", true);
		prop.adapt(this.configFile);
	}

	@Override
	public void syncConfig() {
		this.enableGenBismuth = prop.getAdaptedProperties().get(0).getBoolean();
		this.enableGenCinnabar = prop.getAdaptedProperties().get(1).getBoolean();
		this.enableGenRockSalt = prop.getAdaptedProperties().get(2).getBoolean();

		super.syncConfig();
	}
}
