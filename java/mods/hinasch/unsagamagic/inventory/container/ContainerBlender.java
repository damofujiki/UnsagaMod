package mods.hinasch.unsagamagic.inventory.container;

import javax.annotation.Nullable;

import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.misc.Triplet;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.UnsagaModCore;
import mods.hinasch.unsaga.core.net.packet.PacketGuiButtonUnsaga;
import mods.hinasch.unsaga.element.newele.ElementTable;
import mods.hinasch.unsaga.init.UnsagaGui;
import mods.hinasch.unsagamagic.UnsagaMagic;
import mods.hinasch.unsagamagic.client.gui.GuiBlender;
import mods.hinasch.unsagamagic.inventory.InventoryBlender;
import mods.hinasch.unsagamagic.inventory.slot.SlotBlender;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellComponent;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import mods.hinasch.unsagamagic.spell.spellbook.SpellBookCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class ContainerBlender extends ContainerBase
{
	public UnsagaMagic core = UnsagaMod.magic;
	//	public static final String AMP = "amplifier";
	//
	//	public static final String COST = "cost";
	//	public static final String ELEMENTS = "elements";

	protected InventoryBlender invBlender;

	private InventoryPlayer invPlayer;
	private EntityPlayer owner;
	private World world;

	public ContainerBlender(EntityPlayer player,World world)
	{
		super(player, new InventoryBlender());
		int slotnum = 0;
		//is.world  = world;

		this.invBlender = (InventoryBlender)this.inv;
		this.world = player.worldObj;
		this.owner = player;
		this.invPlayer = player.inventory;

//		for(int i=0;i<3 ;i++){
//			for(int j=0;j<3 ;j++){
//				addSlotToContainer(new SlotBlender(this.invBlender,j + i * 3, 62 + j * 18, 17 + i * 18));
//			}
//		}

		player.addStat(UnsagaModCore.instance().achievements.startBlend,1);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer)
	{


		return this.owner == entityPlayer;
	}

	@Override
	public boolean isShowPlayerInv(){
		return false;
	}
	public void consumeSpellBooks(){
		InventoryHandler.create(invBlender).decrItemStack(com.google.common.collect.Range.closedOpen(0,this.invBlender.getSizeInventory()), 1);
		//		for(int i=0;i<this.invBlender.getSizeInventory();i++){
		//			ItemStack is = this.invBlender.getStackInSlot(i);
		//			if(is!=null){
		//				this.invBlender.decrStackSize(i, 1);
		//			}
		//		}
	}



	public void changeSlotStack(Slot slot,Slot slot2){
		ItemStack is = slot.getStack();
		ItemStack is2 = slot2.getStack();
		if(slot.isItemValid(is2) && slot2.isItemValid(is)){
			slot.putStack(is2);
			slot.putStack(is);
		}

	}


	@Override
	public ItemStack transferStackInSlot(Slot slot)
	{
		InventoryHandler hinvEp = InventoryHandler.create(invPlayer);
		InventoryHandler hinvBlender = InventoryHandler.create(invBlender);

		if(slot instanceof SlotBlender){
			if(slot.getStack()!=null && hinvEp.getFirstEmptySlotNum().isPresent()){
				hinvEp.swapFirstEmptySlot(slot);
			}
		}else{
			if(slot.getStack()!=null && hinvBlender.getFirstEmptySlotNum().isPresent()){
				if(SlotBlender.isItemStackValid(slot.getStack())){
					hinvBlender.swapFirstEmptySlot(slot);
				}

			}
		}
		return super.transferStackInSlot(slot);
	}

	private float calcCostAndAmp(float f){
		float base = 1.0F;
		base += base * (f/100.0F);

		return MathHelper.clamp_float(base, 0.1F, 4.0F);
	}




	public int getAmountSlotSpellBook(){

		return InventoryHandler.create(invBlender).getAmount(input -> input.getStack()!=null);
	}


	public ItemStack getBaseSpellBookItemStack(){
		return this.invBlender.getStackInSlot(0);
	}



	@Override
	public int getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaGui.Type.BLENDING.getMeta();
	}
	@Override
	public PacketGuiButtonBaseNew getPacketGuiButton(int guiID, int buttonID,
			@Nullable NBTTagCompound args) {
		// TODO 自動生成されたメソッド・スタブ
		return PacketGuiButtonUnsaga.create(UnsagaGui.Type.fromMeta(guiID),buttonID,args);
	}

	@Override
	public SimpleNetworkWrapper getPacketPipeline() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaMod.packetDispatcher;
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

	//パケット受け取った時に一緒に実効される
	@Override
	public void onPacketData() {
		UnsagaMod.logger.trace("container", this.buttonID,this.argsSent);
		if(this.buttonID==GuiBlender.DO_BLEND){
			if(this.getHeldSpellBook()!=null){
				ItemStack book = this.getHeldSpellBook();
				if(this.argsSent!=null){
					String id = this.argsSent.getString("spell");
					Spell spell = SpellRegistry.instance().get(id);
					float amp = 1.0F + this.argsSent.getFloat("amp");
					float cost = 1.0F + this.argsSent.getFloat("cost");
					int enchantCost = this.argsSent.getInteger("ench.cost");
//					if(this.ep.experienceLevel>=enchantCost || this.ep.isCreative()){
						if(!SpellBookCapability.adapter.getCapability(book).isSpellFull()){
							SpellComponent component = new SpellComponent(spell,amp,cost,true);
							SpellBookCapability.adapter.getCapability(book).addSpell(component);
							if(!this.ep.isCreative()){
								this.ep.experienceLevel -= enchantCost;
							}
						}

//					}

				}


			}
		}
//		switch(this.buttonID){
//		case GuiBlender.BUTTON_UNDO:
//			if(getBaseSpellBookItemStack()!=null){
//				if(SpellBookHelper.getCapability(this.invBlender.getStackInSlot(0)).isMixed()){
//
//					this.undoBlendedSpell();
//				}
//			}
//			break;
//		case GuiBlender.BUTTON_BLEND:
//			if(this.hasNoBlendSpell() && this.getAmountSlotSpellBook()>1){
//				UnsagaMod.logger.trace("mix", "noblended,spellbook>1");
//				if(this.getTransformedSpell(false).isPresent()){
//					UnsagaMod.logger.trace("mix", "spell is present");
//					this.doBlendSpells();
//
//				}
//
//			}
//			break;
//		}

	}


	public class SpellMixesForBlend extends Triplet<ElementTable,ElementTable,ElementTable>{


		public SpellMixesForBlend(ElementTable element,ElementTable cost,ElementTable amp){
			super(element,cost,amp);
		}

		public ElementTable getElement(){
			return this.first;
		}

		public ElementTable getCost(){
			return this.second;
		}

		public ElementTable getAmp(){
			return this.third();
		}
	}
}
