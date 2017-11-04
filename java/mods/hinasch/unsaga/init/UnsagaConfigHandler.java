package mods.hinasch.unsaga.init;

import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import mods.hinasch.lib.config.ConfigBase;
import mods.hinasch.lib.config.PropertyCustomNew;
import mods.hinasch.lib.config.SplittedStringWrapper;
import mods.hinasch.lib.iface.IParser;
import mods.hinasch.lib.world.XYZPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UnsagaConfigHandler extends ConfigBase{

	public static class OreSetting{
		public boolean enableCopper = true;
		public boolean enableLead = true;
		public boolean enableSilver = true;
		public boolean enableSapphire = true;
		public boolean enableRuby = true;
		public boolean enableSerpentine = true;
	}

	boolean enableAlwaysSparkling = false;
	PropertyCustomNew prop;
	boolean enableLP = true;
	boolean enableRenderNearMonsterLP = false;
	boolean enableOreGeneration = true;

	public void enableAlwaysSparkling(boolean par1){
		this.enableAlwaysSparkling = par1;
	}

	public boolean isAlwaysSparkling(){
		return this.enableAlwaysSparkling;
	}


	int skillXPMultiply = 1;

	int decipheringXPMultiply = 1;

	boolean enableChestGeneration =  true;
	OreSetting oreSetting = new OreSetting();

	int chestGenerationWeight = 3;


	int defaultPlayerLP = 8;

	Map<String,Integer> materialRenderColorOverrides = Maps.newHashMap();
	double defaultTargettingRange = 20.0D;
	double defaultTargettingRangeVertical = 10.0D;

	XYZPos renderLPOffset = new XYZPos(0,0,0);

	XYZPos renderDebuffOffset = new XYZPos(0,0,0);


	IParser<XYZPos,int[]> parseXYZ = new IParser<XYZPos,int[]>(){

		@Override
		public XYZPos parse(int[] in) {
			if(in.length>=3){
				return new XYZPos(in[0],in[1],in[2]);

			}
			return new XYZPos(0,0,0);
		}
	};



	public int getChestGenerationWeight() {
		return chestGenerationWeight;
	}


	public int getDecipheringXPMultiply() {
		return decipheringXPMultiply;
	}

	public int getDefaultPlayerLifePoint(){
		return this.defaultPlayerLP;
	}
	public double getDefaultTargettingRange() {
		return defaultTargettingRange;
	}

	public double getDefaultTargettingRangeVertical() {
		return defaultTargettingRangeVertical;
	}

	@SideOnly(Side.CLIENT)
	public Map<String, Integer> getMaterialRenderColorOverrides() {
		return materialRenderColorOverrides;
	}
	public OreSetting getOreSetting() {
		return oreSetting;
	}

	public XYZPos getRenderDebuffOffset() {
		return renderDebuffOffset;
	}

	public XYZPos getRenderLPOffset() {
		return renderLPOffset;
	}

	public int getSkillXPMultiply() {
		return skillXPMultiply;
	}

	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

		prop = PropertyCustomNew.newInstance();
		prop.add(0,"enable.LPSystem", "Set True To Enable Life Point(LP) System.", true);
		prop.add(1,"default.LP.player", "Default LP of Player.", 6);
		prop.add(2,"offset.playerLP","offset of rendering player LP.(x,y)", Lists.newArrayList(0,0),Integer.class);
		prop.add(3,"offset.playerDebuffs", "offset of rendering debuff icons.(x,y)", Lists.newArrayList(0,0),Integer.class);
		prop.add(4,"default.targettingRange", "default targetting range of player.(horizontal,vertical)",Lists.newArrayList(20.0D,10.0D),Double.class);
		prop.add(5,"enable.render.nearMonsterLP", "set true to enable render monster's LP near.", false);
		prop.add(6, "overrides.materialRenderColor", "custom material render colors.(name:color)", Lists.newArrayList("damascus:0x726250"),String.class);
		prop.add(7,"enable.generation.ores", "set true to allow generation of ores.", true);
		prop.add(8, "enable.generation.chests", "set true to allow generation of chets.", true);
		prop.add(9, "weight.generation.chest", "chest generation weight.(1-10)",3 );
		prop.add(10, "enable.generation.copper", "set true to allow generation of copper ores.", true);
		prop.add(11, "enable.generation.lead", "set true to allow generation of lead ores.", true);
		prop.add(12, "enable.generation.silver", "set true to allow generation of silver ores.", true);
		prop.add(13, "enable.generation.sapphire", "set true to allow generation of sapphire ores.", true);
		prop.add(14, "enable.generation.ruby", "set true to allow generation of ruby ores.", true);
		prop.add(15, "enable.generation.serpentine", "set true to allow generation of serpentine stones.", true);
		prop.add(16, "xp.skill.multiply", "multiply skill point xp by this.", 1);
		prop.add(17, "xp.deciphering.multiply", "multiply deciphering point xp by this.", 1);
		prop.adapt(this.configFile);


	}

	public boolean isEnableChestGeneration() {
		return enableChestGeneration;
	}

	public boolean isEnabledLifePointSystem(){
		return this.enableLP;
	}

	public boolean isEnableOreGeneration() {
		return enableOreGeneration;
	}

	public boolean isEnableRenderNearMonsterLP() {
		return enableRenderNearMonsterLP;
	}

	@Override
	public void syncConfig() {



		enableLP = prop.getAdaptedProperties().get(0).getBoolean();
		defaultPlayerLP = prop.getAdaptedProperties().get(1).getInt();
		this.renderLPOffset = parseXYZ.parse(prop.getAdaptedProperties().get(2).getIntList());
		renderDebuffOffset = parseXYZ.parse(prop.getAdaptedProperties().get(3).getIntList());
		defaultTargettingRange = prop.getAdaptedProperties().get(4).getDoubleList()[0];
		defaultTargettingRangeVertical = prop.getAdaptedProperties().get(4).getDoubleList()[1];
		this.enableRenderNearMonsterLP = prop.getAdaptedProperties().get(5).getBoolean();
		this.enableChestGeneration = prop.getAdaptedProperties().get(8).getBoolean();
		this.enableOreGeneration = prop.getAdaptedProperties().get(7).getBoolean();
		this.chestGenerationWeight = prop.getAdaptedProperties().get(9).getInt();
		String[] strs = prop.getAdaptedProperties().get(6).getStringList();
		SplittedStringWrapper<String,Integer> splitters = new SplittedStringWrapper<String,Integer>("materialRenderColor",strs,2,":"){

			@Override
			public String parseKey(String[] str) throws Exception {
				// TODO 自動生成されたメソッド・スタブ
				return str[0];
			}

			@Override
			public Integer parseValue(String[] str) throws Exception {
				// TODO 自動生成されたメソッド・スタブ
				return Integer.decode(str[1]);
			}
		};

		this.materialRenderColorOverrides = splitters.parse().get();


		this.oreSetting.enableCopper = prop.getAdaptedProperties().get(10).getBoolean();
		this.oreSetting.enableLead = prop.getAdaptedProperties().get(11).getBoolean();
		this.oreSetting.enableSilver = prop.getAdaptedProperties().get(12).getBoolean();
		this.oreSetting.enableSapphire = prop.getAdaptedProperties().get(13).getBoolean();
		this.oreSetting.enableRuby = prop.getAdaptedProperties().get(14).getBoolean();
		this.oreSetting.enableSerpentine = prop.getAdaptedProperties().get(15).getBoolean();


//		if(UnsagaMod.materials.isMaterialLoaded()){
//			this.checkValidation();
//		}
		super.syncConfig();

	}
}
