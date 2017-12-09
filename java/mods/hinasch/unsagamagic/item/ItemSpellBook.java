package mods.hinasch.unsagamagic.item;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.util.ChatHandler;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.Ability;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.ability.AbilityCapability;
import mods.hinasch.unsaga.common.specialaction.IActionPerformer;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.core.net.packet.PacketSyncActionPerform;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellComponent;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import mods.hinasch.unsagamagic.spell.StatePropertySpellCast.StateCast;
import mods.hinasch.unsagamagic.spell.action.SpellCaster;
import mods.hinasch.unsagamagic.spell.spellbook.SpellBookCapability;
import mods.hinasch.unsagamagic.spell.spellbook.SpellBookTypeRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSpellBook extends Item{


	public ItemSpellBook(){
		this.setMaxStackSize(1);
		for(FiveElements.Type type:FiveElements.Type.values()){
			this.addPropertyOverride(new ResourceLocation(type.getSpellBookIconName()), new ItemPropertyGetterSpellBook(type));
		}
	}

	public static class ItemPropertyGetterSpellBook implements IItemPropertyGetter{

		final FiveElements.Type type;
		public ItemPropertyGetterSpellBook(FiveElements.Type type){
			this.type = type;
		}
		@Override
		public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
			if(SpellBookCapability.adapter.hasCapability(stack)){
				if(SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().isPresent()){
					return SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().get().getSpell().getElement()==this.type ? 1.0F : 0;
				}
			}
			return 0;
		}

	}
	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {

		if(playerIn.isSneaking() && this.isCurrentSpellRequireCoordinate(stack)){
			if(EntityStateCapability.adapter.hasCapability(playerIn)){
				StateCast state = (StateCast) EntityStateCapability.adapter.getCapability(playerIn).getState(StateRegistry.instance().stateSpell);
				state.setSpellPoint(pos);
				if(WorldHelper.isClient(worldIn)){
					ChatHandler.sendChatToPlayer(playerIn, "you set point.");
				}

		        return EnumActionResult.SUCCESS;
			}
		}


        return EnumActionResult.PASS;
    }

	private boolean isCurrentSpellRequireCoordinate(ItemStack stack){
		if(SpellBookCapability.adapter.hasCapability(stack)){
			if(SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().isPresent()){
				if(SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().get().getSpell().isRequireCoordinate()){
					return true;
				}
			}
		}
		return false;
	}
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
    	if(SpellBookCapability.adapter.hasCapability(par1ItemStack)){
    		par3List.add("Capability:"+SpellBookCapability.adapter.getCapability(par1ItemStack).getCapacity());
    		if(SpellBookCapability.adapter.getCapability(par1ItemStack).getAcceleration()>1.0F){
    			par3List.add("Acceleration:x"+SpellBookCapability.adapter.getCapability(par1ItemStack).getAcceleration());
    		}
    		if(SpellBookCapability.adapter.getCapability(par1ItemStack).getRawSpells().isEmpty()){
    			par3List.add("Empty");
    		}else{
    			for(int i=0;i<SpellBookCapability.adapter.getCapability(par1ItemStack).getRawSpells().size();i++){
    				SpellComponent spell = SpellBookCapability.adapter.getCapability(par1ItemStack).getSpell(i);
    				par3List.addAll(spell.getLocalizedFull(SpellBookCapability.adapter.getCapability(par1ItemStack).getCurrentIndex()==i));
        			if(!spell.getSpell().getTipsType().getTips().isEmpty()){
            			par3List.addAll(spell.getSpell().getTipsType().getTips());
        			}

    			}

    		}

    	}
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(SpellBookCapability.adapter.hasCapability(stack)){
        	Optional<SpellComponent> spell = SpellBookCapability.adapter.getCapability(stack).getCurrentSpell();
        	return super.getItemStackDisplayName(stack) + " / "+(spell.isPresent() ? spell.get().getSpell().getLocalized() : "Empty");
        }
        return super.getItemStackDisplayName(stack);
    }
	@Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
		if(SpellBookCapability.adapter.hasCapability(stack) && SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().isPresent()){
			int par1 = this.getMaxItemUseDuration(stack) -  ClientHelper.getPlayer().getItemInUseCount();
	        return (double)par1 /this.getCastingTime(stack);
		}

		return 100;
    }

	public int getCastingTime(ItemStack stack){
		SpellComponent spell = SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().get();
		float reduce = 1.0F / SpellBookCapability.adapter.getCapability(stack).getAcceleration();
		return (int)((double)spell.getSpell().getBaseCastingTime() * spell.getAmplify() * reduce);
	}
	@Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {


		int var6 = this.getMaxItemUseDuration(stack) - timeLeft;
		float var7 = (float) var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

//		if ((double) var7 < 0.1D) {
//			return;
//		}

		if(SpellBookCapability.adapter.hasCapability(stack) && WorldHelper.isClient(worldIn)){

			if (var6 > this.getCastingTime(stack)) {

				UnsagaMod.packetDispatcher.sendToServer(new PacketSyncActionPerform(entityLiving));

				this.processCasting(worldIn, entityLiving, stack);
			}
		}

    }

    public void processCasting(World worldIn, EntityLivingBase entityLiving, ItemStack stack) {

    	UnsagaMod.logger.trace(this.getClass().getName(), "process");
		SpellComponent spell = SpellBookCapability.adapter.getCapability(stack).getCurrentSpell().get();
		Ability ability = spell.getSpell().getElement().getCastableAbility();
		IActionPerformer.TargetType targetType = entityLiving.isSneaking() ? IActionPerformer.TargetType.TARGET : IActionPerformer.TargetType.OWNER;
		//座標指定のいる場合
		if(spell.getSpell().isRequireCoordinate()){
			targetType = entityLiving.isSneaking() ? IActionPerformer.TargetType.TARGET : IActionPerformer.TargetType.POSITION;
		}

		final SpellCaster caster = this.createSpellCaster(worldIn, entityLiving, spell.getSpell(), ability);
		if(caster!=null){
			caster.setTargetType(targetType);
			caster.setAmpCost(spell.getAmplify(), spell.getCost());
			if(targetType==IActionPerformer.TargetType.POSITION){
				if(EntityStateCapability.adapter.hasCapability(entityLiving)){
					StateCast state = (StateCast) EntityStateCapability.adapter.getCapability(entityLiving).getState(StateRegistry.instance().stateSpell);
					if(state.getSpellPoint().isPresent()){
						caster.setTargetCoordinate(state.getSpellPoint().get());
					}

				}

			}
			caster.cast();
		}
	}


    private SpellCaster createSpellCaster(World worldIn,EntityLivingBase entityLiving,Spell spell,Ability ability){
    	SpellCaster caster = null;
		if(entityLiving instanceof EntityPlayer){
			EntityPlayer ep = (EntityPlayer) entityLiving;
			//クリエイティブ（最優先）
			if(ep.isCreative()){
				caster = SpellCaster.ofCreative(worldIn, ep, spell);
			}else{
				//ファミリアを持っているか
				if(spell.getElement().getCastableFamiliar()!=null){
					if(SkillPanelAPI.hasPanel(ep, spell.getElement().getCastableFamiliar())){
						caster = SpellCaster.ofFamiliar(worldIn, ep, spell);
					}
				}

				//魔法アイテムを探す
				Optional<ItemStack> magicItem = AbilityAPI.getAllEquippedItems(ep, false).stream().filter(in ->AbilityCapability.adapter.hasCapability(in))
				.filter(in -> AbilityCapability.adapter.getCapability(in).hasAbility(ability)).findFirst();
				if(magicItem.isPresent()){
					caster = SpellCaster.ofArticle(worldIn, ep, spell, magicItem.get());
				}
			}




		}
		return caster;
    }
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
//    	for(int i=0;i<4;i++){
//        	ItemStack is = new ItemStack(itemIn,1);
//            if(SpellBookCapability.adapter.hasCapability(is)){
//            	SpellBookCapability.adapter.getCapability(is).setCapacity(i+1);
//            }
//            subItems.add(is);
//    	}
    	for(SpellBookTypeRegistry.Property p:SpellBookTypeRegistry.instance().getProperties()){
    		subItems.add(p.getStack());
    	}
    	for(Spell spell:SpellRegistry.instance().getProperties().stream().sorted().collect(Collectors.toList())){
        	ItemStack is = new ItemStack(itemIn,1);
            if(SpellBookCapability.adapter.hasCapability(is)){
            	SpellBookCapability.adapter.getCapability(is).setCapacity(1);
            	SpellBookCapability.adapter.getCapability(is).addSpell(spell);
            }
            subItems.add(is);
    	}

    }


	@Override
    public boolean showDurabilityBar(ItemStack in)
    {
		if(ClientHelper.getPlayer().getActiveItemStack()!=null){

			ItemStack activeStack = ClientHelper.getPlayer().getActiveItemStack();
			if(hasCurrentSpell(activeStack) && hasCurrentSpell(in)){
				return SpellBookCapability.adapter.getCapability(activeStack).isCurrentSpellSame(SpellBookCapability.adapter.getCapability(in).getCurrentSpell().get().getSpell());
			}

		}

		return false;
    }

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	private boolean hasCurrentSpell(ItemStack is){
		if(SpellBookCapability.adapter.hasCapability(is)){
			return SpellBookCapability.adapter.getCapability(is).getCurrentSpell().isPresent();
		}
		return false;
	}
	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		if(SpellBookCapability.adapter.hasCapability(itemStackIn) && SpellBookCapability.adapter.getCapability(itemStackIn).getCurrentSpell().isPresent()){
			playerIn.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 0.5F,
					1.8F / (worldIn.rand.nextFloat() * 0.4F + 1.2F));

			playerIn.setActiveHand(EnumHand.MAIN_HAND);
	        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
		}


        return new ActionResult(EnumActionResult.PASS, itemStackIn);
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
    	if(this.hasCurrentSpell(stack)){
            return EnumAction.BOW;
    	}
    	return EnumAction.NONE;
    }

	@Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {


		World worldIn = player.getEntityWorld();
		BlockPos pos = player.getPosition().add(0.5D, 0, 0);
		Random rand = worldIn.rand;
		int var6 = this.getMaxItemUseDuration(stack) - count;
		if(var6 == this.getCastingTime(stack)){
			player.playSound(SoundEvents.BLOCK_NOTE_HARP,1.0F,1.0F);
		}
		if(rand.nextInt(16)==0 && WorldHelper.isClient(player.getEntityWorld())){
            for (int k = 0; k <= 1; ++k)
            {
            	for(int j=0; j <=1;++j){
            		worldIn.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.0D, (double)pos.getZ() + 0.5D, (double)((float)j + rand.nextFloat()) - 0.5D, (double)((float)k - rand.nextFloat() - 1.0F), (double)((float)j + rand.nextFloat()) - 0.5D, new int[0]);
            	}

            }

		}
    }
}
