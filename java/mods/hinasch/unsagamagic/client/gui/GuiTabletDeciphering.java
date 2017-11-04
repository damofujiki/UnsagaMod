package mods.hinasch.unsagamagic.client.gui;

import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.lib.client.GuiContainerBase;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.status.UnsagaXPCapability;
import mods.hinasch.unsagamagic.inventory.container.ContainerTabletDeciphering;
import mods.hinasch.unsagamagic.tileentity.TileEntityDecipheringTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiTabletDeciphering extends GuiContainerBase{

	public static final int BUTTON_WRITE = 1;
	public static final int BUTTON_DECIPHER= 2;
	public static final int BUTTON_CLEAR= 3;
	public static final int ICON_XP= 4;
	public EntityPlayer ep;

	public GuiTabletDeciphering(EntityPlayer ep, World w,XYZPos pos,TileEntityDecipheringTable te) {
		super(new ContainerTabletDeciphering(ep,w,pos,te));
		// TODO 自動生成されたコンストラクター・スタブ
		this.ep = ep;

		int startX = 100;
		this.addIcon(new IconButton(BUTTON_DECIPHER, startX, 10, 0, 168, true));
		this.addIcon(new IconButton(BUTTON_WRITE, startX+20, 10, 16, 168, true));
		this.addIcon(new IconButton(BUTTON_CLEAR, startX+40, 10, 32, 168, true));

		this.addIcon(ICON_XP, 185, 24, 0, 184, true);
	}


	@Override
	public String getGuiTextureName(){
		return UnsagaMod.MODID+":textures/gui/container/research.png";
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);
		UnsagaXPCapability.displayAdditionalXP(ep, fontRendererObj, EnumSet.of(UnsagaXPCapability.Type.DECIPHER),210,24,0xffffff);
	}
	@Override
	public String getGuiName(){
		return "";
	}

	@Override
	public List<String> getIconHoverText(Icon id){
		List<String> list = Lists.newArrayList();
		if(id.id==BUTTON_DECIPHER){
			list.add(HSLibs.translateKey("gui.unsaga.deciphering.decipherSpell"));
		}
		if(id.id==BUTTON_WRITE){
			list.add(HSLibs.translateKey("gui.unsaga.deciphering.write"));
		}
		if(id.id==BUTTON_CLEAR){
			list.add(HSLibs.translateKey("gui.unsaga.deciphering.clear"));
		}
		if(id.id==ICON_XP){
			list.add("Deciphering Point");
		}
		return list;
	}

	@Override
    public void initGui()
    {
        super.initGui();
		int i = width  - xSize >> 1;
		int j = height - ySize >> 1;
//        this.addButton(BUTTON_DECIPHER, i+ 80,j + 10,36, 20, "Decipher");
//        this.addButton(BUTTON_WRITE, i+120,j + 10,36, 20, "Write");
    }
}
