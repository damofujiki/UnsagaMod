package mods.hinasch.unsagamagic.inventory.container;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

import mods.hinasch.lib.container.ContainerBase;
import mods.hinasch.lib.container.SlotIcon;
import mods.hinasch.lib.container.inventory.InventoryBase;
import mods.hinasch.lib.container.inventory.InventoryHandler;
import mods.hinasch.lib.container.inventory.SlotPlayer;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketGuiButtonBaseNew;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketSyncCapability;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.net.packet.PacketGuiButtonUnsaga;
import mods.hinasch.unsaga.init.UnsagaGui;
import mods.hinasch.unsaga.status.UnsagaXPCapability;
import mods.hinasch.unsagamagic.client.gui.GuiTabletDeciphering;
import mods.hinasch.unsagamagic.inventory.InventoryTabletDeciphering;
import mods.hinasch.unsagamagic.inventory.slot.SlotBook;
import mods.hinasch.unsagamagic.inventory.slot.SlotTabletDeciphering;
import mods.hinasch.unsagamagic.item.UnsagaMagicItems;
import mods.hinasch.unsagamagic.item.newitem.ItemIconSpellNew;
import mods.hinasch.unsagamagic.item.newitem.ItemTablet;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellBookCapability;
import mods.hinasch.unsagamagic.spell.MagicTablet;
import mods.hinasch.unsagamagic.spell.TabletCapability;
import mods.hinasch.unsagamagic.tileentity.TileEntityDecipheringTable;
import mods.hinasch.unsagamagic.util.HelperDecipherProcess;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ContainerTabletDeciphering extends ContainerBase{

	public static final int DECIPHERING_THRESHOLD = 10;
	//	public static final int DECIPHERING_INTERVAL = 1000;
	protected InventoryTabletDeciphering invTablet;
	protected IInventory invSpells;
	protected EntityPlayer ep;
	protected World world;
	protected TileEntityDecipheringTable table;
	public ContainerTabletDeciphering(EntityPlayer epIn, World worldIn,XYZPos posIn,TileEntityDecipheringTable teIn) {
		super(epIn, new InventoryTabletDeciphering(2,worldIn,posIn,teIn));
		this.invTablet = (InventoryTabletDeciphering) this.inv;
		this.invTablet.setParent(this);
		this.world = epIn.worldObj;
		this.ep = epIn;
		this.table = teIn;
		this.invSpells = new InventoryBase(16);

		table.setUser(ep);


		int j = 10;
		for(int i=0;i<2;i++){

			switch(i){
			case 0:
				addSlotToContainer(new SlotTabletDeciphering(this.inv,0, 16 + i * 20,14));
				break;
			case 1:
				addSlotToContainer(new SlotBook(this.inv,1, 16 + i * 20,14));
				break;
			}

		}

		int offsetY  = 0;
		int var2 = 0;
		for(int i=0;i<16 ;i++){

			//			if(i>7){
			//				var2 = i-8;
			//				offsetY = 16;
			//			}else{
			//				var2 = i;
			//			}
			var2 = i>7 ? i-8 : i;
			offsetY = i>7 ? 16 : 0;
			addSlotToContainer(new SlotIcon(this.invSpells,i, 10 + var2 * 18,40+offsetY));
		}

		this.invTablet.setInventorySlotContents(0,table.getTablet());

		UnsagaXPCapability.syncAdditionalXP(this.ep);
	}


	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{


		this.table.setUser(null);
		this.table.setTablet(this.invTablet.getStackInSlot(0));


		//		IBlockState state = this.world.getBlockState(table.getPos()).withProperty(BlockDecipheringTable.TABLET, table.getTablet()!=null);
		//
		//		if(WorldHelper.isClient(world)){
		//			UnsagaMod.logger.trace(this.getClass().getName(),"client");
		//			this.world.setBlockState(table.getPos(), state);
		//		}

		super.onContainerClosed(par1EntityPlayer);
	}

	public void onTabletSlotChanged(IInventory p_75130_1_)
	{

		//		Unsaga.debug(this.inv.getStackInSlot(InventoryTabletDeciphering.INV_TABLET));
		if(this.inv.getStackInSlot(0)!=null){

			UnsagaMod.logger.trace(this.getClass().getName(),"ある");
			ItemStack tablet = this.inv.getStackInSlot(0);

			if(TabletCapability.adapter.hasCapability(tablet)){
				MagicTablet tabletData = TabletCapability.adapter.getCapability(tablet).getTabletData();
				UnsagaMod.logger.trace(this.getClass().getSimpleName(), tabletData.toString());

				Map<Spell,Integer> decipheringProgress = TabletCapability.adapter.getCapability(tablet).getDecipheringProgressMap();

				this.changeSpellSlot(tabletData.getSpellList(),tablet);
			}



		}
		//		Unsaga.debug("changed");

	}

	public void changeSpellSlot(List<Spell> spells, ItemStack tablet){
		int index = 0;
		this.clearSpellSlots();
		for(final Spell spell:spells){
			UnsagaMod.logger.trace(this.getClass().getName(), spell);
			ItemStack is = ItemIconSpellNew.createSpellIcon(spell);
			int progress = TabletCapability.adapter.getCapability(tablet).getProgress(spell);
			if(ItemIconSpellNew.adapter.hasCapability(is)){
				ItemIconSpellNew.adapter.getCapability(is).setDecipheringProgress(progress);
			}
			this.invSpells.setInventorySlotContents(index, is);

			index ++;
		}

	}

	public int getProgressPoint(Map<Spell,Integer> decipheringProgress,Spell spell){
		int base =  decipheringProgress.containsKey(spell) ? decipheringProgress.get(spell) : 0;
		base = base > 100 ? 100 : base;
		return base;
	}
	public void clearSpellSlots(){
		//		Consumer<InventoryStatus> clearInvConsumer =input ->{
		//			int num = input.getStackNumber();
		//			input.getParent().setInventorySlotContents(num, null);
		//
		//		};
		InventoryHandler.create(this.invSpells).toStream(0, this.invSpells.getSizeInventory())
		.forEach(in ->{
			int num = in.getStackNumber();
			in.getParent().setInventorySlotContents(num, null);
		});


	}
	@Override
	public PacketGuiButtonBaseNew getPacketGuiButton(int guiID, int buttonID,
			NBTTagCompound args) {
		// TODO 自動生成されたメソッド・スタブ
		return PacketGuiButtonUnsaga.create(UnsagaGui.Type.fromMeta(guiID),buttonID,args);
	}

	@Override
	public boolean isSpereadSlotItemsOnContainerClosed(EntityPlayer par1EntityPlayer,ItemStack is){
		if(is!=null && is.getItem() instanceof ItemTablet){
			return false;
		}
		return true;
	}

	@Override
	public SimpleNetworkWrapper getPacketPipeline() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaMod.packetDispatcher;
	}

	@Override
	public int getGuiID() {
		// TODO 自動生成されたメソッド・スタブ
		return UnsagaGui.Type.TABLET.getMeta();
	}

	@Override
	public void onPacketData() {
		if(this.getSelectedSlot().isPresent()){
			int slotnum = this.getSelectedSlot().getAsInt();
			ItemStack icon = this.invSpells.getStackInSlot(slotnum);
			if(ItemIconSpellNew.hasCapability(icon)){
				ItemStack bookStack = this.getBookSlot();
				ItemStack tablet = this.getTabletSlot();
				Spell spell = ItemIconSpellNew.adapter.getCapability(icon).getSpell();
				switch(buttonID){
				case GuiTabletDeciphering.BUTTON_DECIPHER:
					this.onPushDecipheringButton(spell, tablet, slotnum);
					break;
				case GuiTabletDeciphering.BUTTON_WRITE:
					this.onPushWritingButton(spell, slotnum);
					break;
				case GuiTabletDeciphering.BUTTON_CLEAR:
					this.onPushClearButton(spell, slotnum);
					break;
				}
			}


		}



	}

	public ItemStack getBookSlot(){
		return this.inv.getStackInSlot(1);
	}
	public ItemStack getTabletSlot(){
		return this.inv.getStackInSlot(0);
	}

	public void onPushClearButton(Spell spell,int slotnum){
		if(this.getBookSlot()!=null && SpellBookCapability.adapter.hasCapability(getBookSlot())){
			SpellBookCapability.adapter.getCapability(getBookSlot()).clear();
		}
	}
	/**
	 * 転写ボタンを押した時
	 * @param spell
	 * @param bookStack
	 * @param slotnum
	 */
	public void onPushWritingButton(Spell spell,int slotnum){
		if(this.getBookSlot()!=null && SpellBookCapability.adapter.hasCapability(getBookSlot())){
			if(TabletCapability.adapter.hasCapability(getTabletSlot())){
				if(TabletCapability.adapter.getCapability(getTabletSlot()).getProgress(spell)>=100){
					SpellBookCapability.adapter.getCapability(this.getBookSlot()).addSpell(spell);
				}
			}
		}
		//		if(bookStack!=null){
		//			ItemStack icon = this.inv.getStackInSlot(slotnum);
		//			if(ItemIconSpell.hasCapability(icon)){
		//				if(ItemIconSpell.getCapability(icon).getDecipheringProgress()>=100){
		//					this.inv.decrStackSize(InventoryTabletDeciphering.INV_BOOK, 1);
		//					ItemStack newSpellBook = new ItemStack(UnsagaMod.magic.items.spellBook,1);
		//					if(SpellBookHelper.hasCapability(newSpellBook)){
		//						SpellBookHelper.getCapability(newSpellBook).setSpell(spell);
		//					}
		//
		//					this.inv.setInventorySlotContents(InventoryTabletDeciphering.INV_RESULT, newSpellBook);
		//				}
		//			}
		//
		//
		//		}else{
		//
		//			this.changeMessage(ep, I18n.translateToLocal("msg.decipher.fail.nobook"));
		//		}
	}

	/**
	 * 解読ボタンをおした時
	 * @param spell
	 * @param tablet
	 * @param slotnum
	 */
	public void onPushDecipheringButton(Spell spell,ItemStack tablet,int slotnum){

		if(WorldHelper.isClient(world)){
			return;
		}
		boolean canDecipher = this.ep.capabilities.isCreativeMode ? true :
			UnsagaXPCapability.hasCapability(ep) && UnsagaXPCapability.getCapability(ep).getDecipheringPoint()>DECIPHERING_THRESHOLD;

			ItemStack iconSelected = this.invSpells.getStackInSlot(slotnum);

			if(tablet!=null && canDecipher && ItemIconSpellNew.hasCapability(iconSelected)){
				HSLib.core().getPacketDispatcher().sendTo(PacketSound.atPos(SoundEvents.BLOCK_NOTE_HARP, XYZPos.createFrom(ep)), (EntityPlayerMP) ep);

				//解読前
				int progress_pre = TabletCapability.adapter.getCapability(tablet).getProgress(spell);
				//今回の解読値
				int progress = HelperDecipherProcess.getProgressPoint(world, ep, spell);
				//解読後
				int progress_post = progress_pre + progress;

				progress_post = MathHelper.clamp_int(progress_post, 0, 100);
				ItemIconSpellNew.adapter.getCapability(this.invSpells.getStackInSlot(slotnum)).setDecipheringProgress(progress_post);
				if(TabletCapability.adapter.hasCapability(tablet)){
					TabletCapability.adapter.getCapability(tablet).progressDecipher(spell, progress);


					//						if(ItemIconSpellNew.hasCapability(iconSelected)){
					//							ItemIconSpellNew.adapter.getCapability(iconSelected).setDecipheringProgress(progress_post);
					//
					//						}




				}





				//				this.changeMessage(ep, I18n.translateToLocalFormatted("msg.decipher.progress", progress));

				int pointpre = UnsagaXPCapability.getCapability(ep).getDecipheringPoint();
				if(!ep.capabilities.isCreativeMode){
					UnsagaXPCapability.adapter.getCapability(ep).setDecipheringPoint(pointpre-DECIPHERING_THRESHOLD);
					HSLib.core().getPacketDispatcher().sendTo(PacketSyncCapability.create(UnsagaXPCapability.CAPA,
							UnsagaXPCapability.getCapability(ep)), (EntityPlayerMP) ep);


				}

				//			ExtendedPlayerData.getData(ep).setRecentDecipheredTime(world.getTotalWorldTime());
				return;
			}
			//			if(!canDecipher){
			//				this.changeMessage(ep, I18n.translateToLocal("msg.decipher.fail"));
			//
			//				//			long timeToDecipher = new Supplier<Long>(){
			//				//
			//				//				@Override
			//				//				public Long get() {
			//				//					long timeToDecipher = world.getTotalWorldTime()-ExtendedPlayerData.getData(ep).getRecentDecipheredTime();
			//				//					if(timeToDecipher>DECIPHERING_INTERVAL){
			//				//						timeToDecipher = DECIPHERING_INTERVAL;
			//				//					}
			//				//					timeToDecipher = DECIPHERING_INTERVAL -timeToDecipher;
			//				//					return timeToDecipher;
			//				//				}
			//				//			}.get();
			//				//
			//				//			//パケットで送るためにintにする
			//				//			int timeToDecipherInt = (int) timeToDecipher;
			//				//			if(ep instanceof EntityPlayerMP){
			//				//				PacketChangeGuiMessage pcgm = new PacketChangeGuiMessage(0,timeToDecipherInt);
			//				//				Unsaga.packetDispatcher.sendTo(pcgm, (EntityPlayerMP) ep);
			//				//			}
			//			}
	}
	/**
	 * 1 - 10
	 * @return
	 */
	public OptionalInt getSelectedSlot(){
		return InventoryHandler.create(this.invSpells).toStream(0,this.invSpells.getSizeInventory())
				.filter(st ->{
					ItemStack is = st.getStack();
					if(ItemUtil.isItemStackPresent(is) && ItemIconSpellNew.adapter.hasCapability(is)){
						return ItemIconSpellNew.adapter.getCapability(is).getSelectStatus()==ItemIconSpellNew.SelectAttribute.SELECTED;
					}
					return false;
				})
				.mapToInt(st -> st.getStackNumber()).findFirst();

	}

	@Deprecated
	@SideOnly(Side.CLIENT)
	public void syncDecipheringMap(NBTTagCompound nbt){
//		UnsagaMod.logger.trace(this.getClass().getName(), "sync!");
//		int slotnum = nbt.getInteger("slot");
//		int progress = nbt.getInteger("progress");
//		ItemStack syncicon = this.inv.getStackInSlot(slotnum);
//		if(ItemIconSpellNew.adapter.hasCapability(syncicon)){
//			ItemIconSpellNew.adapter.getCapability(syncicon).setDecipheringProgress(progress);
//		}
//		if(nbt.hasKey("map")){
//			List<DecipheringPair> list = UtilNBT.readListFromNBT(nbt, "map", DecipheringPair.RESTORE);
//			TabletHelper.getCapability(getTabletSlot()).setDecipheringMap(TabletHelper.listToMap(list));
//		}

	}

	protected void unselectSpells(){
		InventoryHandler.create(this.invSpells).toStream(0,this.invSpells.getSizeInventory())
		.forEach(input -> {
			int num = input.getStackNumber();
			ItemStack iconSpell = input.getStack();
			if(iconSpell!=null && ItemIconSpellNew.hasCapability(iconSpell)){

				ItemIconSpellNew.adapter.getCapability(iconSpell).setStatus(ItemIconSpellNew.SelectAttribute.UNSELECTED);
			}
		}
				);
	}

	@Override
	public ItemStack transferStackInSlot(Slot slot)
	{
		InventoryHandler invEP = InventoryHandler.create(this.ep.inventory);

		if(slot.getStack()!=null){
			if(slot instanceof SlotTabletDeciphering){
				if(invEP.getFirstMergeableOrEmptySlot(slot.getStack()).isPresent()){
					invEP.merge(invEP.getFirstMergeableOrEmptySlot(slot.getStack()).getAsInt(), slot);
					this.inv.decrStackSize(0, 1);
					return null;
				}
			}
			if(slot instanceof SlotBook){
				if(invEP.getFirstMergeableOrEmptySlot(slot.getStack()).isPresent()){
					invEP.merge(invEP.getFirstMergeableOrEmptySlot(slot.getStack()).getAsInt(), slot);
					this.onTabletSlotChanged(null);
					this.inv.decrStackSize(1, 1);
					return null;
				}
			}

			if(slot instanceof SlotPlayer){
				if(slot.getStack().getItem()==UnsagaMagicItems.instance().tablet){
					if(this.getTabletSlot()==null){
						this.inv.setInventorySlotContents(0, slot.getStack().copy());
						slot.putStack(null);
						this.onTabletSlotChanged(null);
						return null;
					}

				}
				if(slot.getStack().getItem()==UnsagaMagicItems.instance().spellBook){
					if(this.getBookSlot()==null){
						this.inv.setInventorySlotContents(1, slot.getStack().copy());
						slot.putStack(null);
						this.onTabletSlotChanged(null);
						return null;
					}

				}
			}

		}

		return null;
	}

	@Override
	public void onSlotClick(int rawSlotNumber,int containerSlotNumber,int clickButton,ClickType clickTypeIn,EntityPlayer ep){
		ItemStack is = this.getSlot(rawSlotNumber).getStack();
		if(is!=null && is.getItem() instanceof ItemIconSpellNew && ItemIconSpellNew.hasCapability(is)){
			this.playClickSound();
			this.unselectSpells();
			ItemIconSpellNew.adapter.getCapability(is).setStatus(ItemIconSpellNew.SelectAttribute.SELECTED);
		}
		//		Unsaga.debug(slotNumber,clickButton,is,this.getClass());
	}
}
