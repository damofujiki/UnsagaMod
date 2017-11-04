package mods.hinasch.unsagamagic.client.gui;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import mods.hinasch.lib.client.GuiContainerBase;
import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.misc.XY;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.SoundAndSFX;
import mods.hinasch.lib.util.UtilNBT;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.element.newele.ElementTable;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import mods.hinasch.unsagamagic.inventory.container.ContainerBlender;
import mods.hinasch.unsagamagic.item.newitem.ItemSpellBook;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellBlend;
import mods.hinasch.unsagamagic.spell.SpellBookCapability;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBlender extends GuiContainerBase
{


	public static final int BUTTON_UNDO = -3;
	public static final int BUTTON_BLEND = -2;
	public static final int SPELLS = 30;
	public static final int BLENDS = 50;
	public static final int NEXT = 20;
	public static final int PREV = 21;
	public static final int DO_BLEND = 22;
	public static final int CLEAR = 23;
	private EntityPlayer ep;
	private ResourceLocation resourceGui = new ResourceLocation(UnsagaMod.MODID+":textures/gui/blender.png");
	private ContainerBlender containerblender;

	int pointerBlend = 0;
	int currentElement = 0;
	List<Spell> currentSpellList;

	public GuiBlender(EntityPlayer player,World world)
	{
		super(new ContainerBlender(player, world));


		//this.blenderInventory = te;

		this.ep = player;
		this.containerblender = (ContainerBlender)inventorySlots;
	}

	@Override
	public void initGui()
	{
		super.initGui();
		//		int i = width  - xSize >> 1;
		//		int j = height - ySize >> 1;


		this.updateSpellList();
		// ボタンを追加
		// GuiButton(ボタンID, ボタンの始点X, ボタンの始点Y, ボタンの幅, ボタンの高さ, ボタンに表示する文字列)
		//		this.addButton(BUTTON_BLEND, i + 10, j + 16, 48, 20 , I18n.translateToLocal("gui.blender.button.blend"));
		//		this.addButton(BUTTON_UNDO, i + 10, j + 38, 48, 20 , I18n.translateToLocal("gui.blender.button.undo"));
		//		int index = 0;
		//		FiveElements.Type element = FiveElements.Type.fromMeta(currentElement);
		//		for(Spell spell:SpellRegistry.instance().getSpellsExceptBlendedSpell(element)){
		//			XY roc = this.getIconRocation(element);
		//			int ix = 18 + (index>9 ? index/10 : index) * 16;
		//			int iy = index>9 ? 116 : 100;
		//			this.addIcon(new IconSpell(SPELLS+index,ix,iy,roc.getX(),roc.getY(),true,spell));
		//			index ++;
		//		}
		for(int i=0;i<18;i++){
			int ix = 18 + (i>8 ? i-9 : i) * 16;
			int iy = i>8 ? 116 : 100;
			this.addIcon(new IconSpell(SPELLS+i,ix,iy,0,0,true,i));
		}
		for(int i=0;i<18;i++){
			int ix = 17 + (i>8 ? i-9 : i) * 16;
			int iy = i>8 ? 28 : 12;
			this.addIcon(new IconSpellBlendQueue(BLENDS+i,ix,iy,0,0,true,i));
		}
		this.addIcon(new IconButton(DO_BLEND,130,60,32,184,true));
		this.addIcon(new IconButton(CLEAR,150,60,48,184,true));
		this.addIcon(new IconButton(PREV,70,140,16,184,true));
		this.addIcon(new IconButton(NEXT,86,140,0,184,true));
		for(int i=0;i<6;i++){
			this.addIcon(new Icon(50,8+32*i,168,16*i,168,false));
		}

	}

	public @Nullable ItemStack getHeldSpellBook(){
		if(this.ep.getHeldItemMainhand()!=null && SpellBookCapability.adapter.hasCapability(this.ep.getHeldItemMainhand())){
			return this.ep.getHeldItemMainhand();
		}
//		if(this.ep.getHeldItemOffhand()!=null && SpellBookCapability.adapter.hasCapability(this.ep.getHeldItemOffhand())){
//			return this.ep.getHeldItemOffhand();
//		}
		return null;
	}

	@Override
	public void prePacket(Icon icon){
		if(icon.id==PREV || icon.id==NEXT){
			if(icon.id==PREV){
				this.currentElement --;

			}
			if(icon.id==NEXT){
				this.currentElement ++;

			}

			this.currentElement = MathHelper.clamp_int(this.currentElement, 0, FiveElements.Type.FORBIDDEN.getMeta());
			this.updateSpellList();
		}
		if(icon.id==CLEAR){
			this.clearBlendQueue();
			this.pointerBlend = 0;
		}

		if(icon.id==DO_BLEND){
			if(this.canBlend() && this.canBlendToSpellBook()){
				this.ep.experienceLevel -= this.getEnchantCost();
				SoundAndSFX.playSound(ep.worldObj, XYZPos.createFrom(ep), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS,1.0F,1.0F,false);
			}
		}
		if(icon instanceof IconSpell){
			Spell selectedSpell = ((IconSpell) icon).getSpell(this);
			List<IconSpellBlendQueue> blend = this.getBlendQueueIcons();
			if(blend.stream().filter(in -> in.iconIndex==this.pointerBlend).findFirst().isPresent()){
				if(!this.getBlendQueueSpells().contains(selectedSpell)){
					IconSpellBlendQueue current = blend.stream().filter(in -> in.iconIndex==this.pointerBlend).findFirst().get();
					current.setSpell(selectedSpell);
					this.pointerBlend ++;
				}

			}
		}
	}

	public ElementTable calcBlendedCost(){
		return this.getBlendQueueSpells().stream().map(in -> in.getAmplifyTable()).reduce(ElementTable.ZERO, (a,b)->a.add(b));
	}
	public ElementTable calcBlendedAmplify(){
		return this.getBlendQueueSpells().stream().map(in -> in.getCostTable()).reduce(ElementTable.ZERO, (a,b)->a.add(b));
	}
	public void clearBlendQueue(){
		this.getBlendQueueIcons().forEach(in -> in.setSpell(null));
	}
	public List<IconSpellBlendQueue> getBlendQueueIcons(){
		return this.iconList.stream().filter(in -> in instanceof IconSpellBlendQueue).map(in -> (IconSpellBlendQueue)in).collect(Collectors.toList());
	}


	public Optional<Spell> getBaseSpell(){
		if(!this.getBlendQueueSpells().isEmpty() && this.getBlendQueueSpells().get(0)!=null){
			return Optional.of(this.getBlendQueueSpells().get(0));
		}
		return Optional.empty();
	}


	public @Nullable Spell getBlendedResultSpell(){
		if(this.getBaseSpell().isPresent()){
			if(this.getChangeableSpell().isPresent()){
				return this.getChangeableSpell().get();
			}
			return this.getBaseSpell().get();
		}
		return null;
	}
	public Optional<SpellBlend> getChangeableSpell(){
		if(this.getBaseSpell().isPresent()){
			Spell base = this.getBaseSpell().get();
			ElementTable table = this.calcBlendedElementTable();
			return SpellRegistry.instance().getBlendSpells().stream().filter(in -> in.canChange(base, table)).findFirst();
		}
		return Optional.empty();
	}

	public List<Spell> getBlendQueueSpells(){
		return this.getBlendQueueIcons().stream().filter(in -> in.getSpell(this).isPresent()).map(in -> in.getSpell(this).get()).collect(Collectors.toList());
	}
	public ElementTable calcBlendedElementTable(){
		return this.getBlendQueueSpells().stream().map(in -> in.getSpellMixTable()).reduce(ElementTable.ZERO,(prev,next)->prev.add(next));
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int par1,int par2)
	{
		super.drawGuiContainerForegroundLayer(par1, par2);

		ElementTable elm = this.calcBlendedElementTable();

		for(int i=0;i<FiveElements.Type.FORBIDDEN.getMeta()+1;i++){

			fontRendererObj.drawString(String.valueOf((int)elm.get(FiveElements.Type.fromMeta(i))),25+32*i, 168, 0xffffff);
		}
		//fontRenderer.drawString("Magic Blender", 58, 6, 0x404040);
		//		fontRendererObj.drawString(this.containerblender.getCurrentElement().toString(), 8, 6, 0x404040);

		//		fontRendererObj.drawString("Result:"+getSpellStr(), 8, (ySize - 106) + 2, 0x404040);
	}


	//	@Override
	//	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	//	{
	//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	//		mc.renderEngine.bindTexture(resourceGui);
	//		int xStart = width - xSize >> 1;
	//		int yStart = height - ySize >> 1;
	//		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	//	}

	public XY getIconRocation(FiveElements.Type type){
		switch(type){
		case EARTH:
			return new XY(48,168);
		case FIRE:
			return new XY(0,168);
		case FORBIDDEN:
			return new XY(80,168);
		case METAL:
			return new XY(64,168);
		case WATER:
			return new XY(16,168);
		case WOOD:
			return new XY(32,168);
		default:
			break;

		}
		return new XY(0,168);
	}

	@Override
	public NBTTagCompound getSendingArgs(){
		if(this.canBlend() && this.canBlendToSpellBook()){
			Spell spell = this.getBlendedResultSpell();
			FiveElements.Type elm = spell.getElement();
			float amp = this.calcBlendedAmplify().get(elm);
			float cost = this.calcBlendedCost().get(elm);
			NBTTagCompound nbt = UtilNBT.compound();
			nbt.setString("spell",spell.getKey().getResourcePath());
			nbt.setFloat("amp", amp);
			nbt.setFloat("cost", cost);
			nbt.setInteger("ench.cost", this.getEnchantCost());
			return nbt;
		}
		return null;
	}

	public int getEnchantCost(){
		return this.getBlendQueueSpells().size();
	}
	public boolean hasSpell(Spell spell){
		return ep.isCreative() || InventoryHandler.create(this.ep.inventory).getAllInvItems().stream()
		.filter(in -> in.getStack().getItem() instanceof ItemSpellBook)
		.anyMatch(in -> SpellBookCapability.adapter.getCapability(in.getStack()).getSpells().contains(spell));
	}

	public boolean canBlend(){
		if(this.getBaseSpell().isPresent() && this.getBlendQueueSpells().size()>=2){
			return true;
		}
		return false;
	}

	public boolean canBlendToSpellBook(){
		if(this.getHeldSpellBook()!=null && (this.getEnchantCost()<=this.ep.experienceLevel || this.ep.isCreative())){
			if(!SpellBookCapability.adapter.getCapability(getHeldSpellBook()).isSpellFull()){
				return true;
			}

		}
		return false;
	}
	@Override
	public void updateScreen()
	{
		super.updateScreen();

	}

	public void updateSpellList(){
		FiveElements.Type element = FiveElements.Type.fromMeta(currentElement);
		this.currentSpellList = SpellRegistry.instance().getSpellsExceptBlendedSpell(element);
	}
	@Override
	public List<String> getIconHoverText(Icon icon){
		List<String> list = Lists.newArrayList();
		if(icon instanceof IconSpell){

			IconSpell spelltoBlend = (IconSpell) icon;
			//			if(spelltoBlend.getSpell(this).isPresent()){
			Spell spell = spelltoBlend.getSpell(this);
			list.add(spell.getLocalized());
			list.add(String.format("Amplify:%s", spell.getAmplifyTable().getAmountAsPercentageLocalized(false)));
			list.add(String.format("Cost:%s", spell.getCostTable().getAmountAsPercentageLocalized(true)));
			list.add(String.format("Element:%s", spell.getSpellMixTable().getAmountAsIntLocalized()));
			//			}
		}
		if(icon.id==DO_BLEND){
			list.add(HSLibs.translateKey("gui.unsaga.blender.do"));
			if(this.canBlend()){
				Spell blend = this.getBlendedResultSpell();
				if(blend!=this.getBaseSpell().get()){
					list.add(UnsagaTextFormatting.SIGNIFICANT+blend.getLocalized());
				}else{
					list.add(blend.getLocalized());
				}

				list.add("Enchant Cost:"+this.getEnchantCost());
				list.add(String.format("Amplify:%s", this.calcBlendedAmplify().getAmountAsPercentageLocalized(false)));
				list.add(String.format("Cost:%s", this.calcBlendedCost().getAmountAsPercentageLocalized(true)));
			}

		}
		if(icon.id==CLEAR){
			list.add(HSLibs.translateKey("gui.unsaga.blender.clear"));
		}
		if(icon instanceof IconSpellBlendQueue){
			if(((IconSpellBlendQueue) icon).getSpell(this).isPresent()){
				list.add(((IconSpellBlendQueue) icon).getSpell(this).get().getLocalized());
			}


		}
		return list;
	}
	@Override
	public String getGuiTextureName(){
		return UnsagaMod.MODID+":textures/gui/container/spellmix.png";
	}

	@Override
	public String getGuiName(){
		return "";
	}

	public static class IconSpell extends IconButtonDisableable<GuiBlender>{

		int iconIndex;
		public IconSpell(int id, int x, int y, int u, int v, boolean hover,int index) {
			super(id, x, y, u, v, hover);

			this.iconIndex = index;
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public XY getUV(GuiBlender gui){
			FiveElements.Type type = FiveElements.Type.fromMeta(gui.currentElement);
			return gui.getIconRocation(type);
		}
		public Spell getSpell(GuiBlender gui){
			if(gui.currentSpellList.size()>this.iconIndex){
				return gui.currentSpellList.get(iconIndex);
			}
			return null;
		}

		@Override
		public boolean isVisible(GuiBlender gui){
			if(this.getSpell(gui)==null){
				return false;
			}
			return true;
		}
		@Override
		public boolean isDisabled(GuiBlender gui){
			if(this.getSpell(gui)==null){
				return true;
			}
			return !gui.hasSpell(this.getSpell(gui));
		}
	}

	public static class IconSpellBlendQueue extends IconButtonDisableable<GuiBlender>{

		final int iconIndex;
		Optional<Spell> spell = Optional.empty();
		public IconSpellBlendQueue(int id, int x, int y, int u, int v, boolean hover,int index) {
			super(id, x, y, u, v, hover);

			this.iconIndex = index;
			// TODO 自動生成されたコンストラクター・スタブ
		}

		@Override
		public XY getUV(GuiBlender gui){
			if(this.spell.isPresent()){
				return gui.getIconRocation(this.spell.get().getElement());
			}
			return gui.getIconRocation(FiveElements.Type.FORBIDDEN);
		}
		public Optional<Spell> getSpell(GuiBlender gui){
			return this.spell;
		}

		public void setSpell(Spell spell){
			if(spell!=null){
				this.spell = Optional.of(spell);
			}else{
				this.spell = Optional.empty();
			}

		}
		@Override
		public boolean isVisible(GuiBlender gui){
			if(!this.spell.isPresent()){
				return false;
			}
			return true;
		}
		@Override
		public boolean isDisabled(GuiBlender gui){
			return !this.spell.isPresent();
		}
	}
}