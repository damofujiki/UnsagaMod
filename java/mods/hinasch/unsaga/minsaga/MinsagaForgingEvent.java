package mods.hinasch.unsaga.minsaga;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import joptsimple.internal.Strings;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.HSLibEvents;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.core.client.gui.GuiSmithMinsaga;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga.ComponentDisplayInfo;
import mods.hinasch.unsaga.minsaga.ForgingCapability.ForgeAttribute;
import mods.hinasch.unsaga.minsaga.ForgingCapability.IMinsagaForge;
import mods.hinasch.unsaga.skillpanel.EventSaveDamage;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;



public class MinsagaForgingEvent {


	public static enum Type{
		WEAPON,ARMOR;
	}

//	public static abstract class HurtEventMinsaga extends LivingHurtEventUnsagaBase{
//
//		Type type;
//
//		@Override
//		public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//			switch(type){
//			case ARMOR:
//				return ItemUtil.getArmorInventoryList(e.getEntityLiving()).stream()
//						.anyMatch(in -> ItemUtil.isItemStackPresent(in) && ForgingCapability.adapter.hasCapability(in));
//			case WEAPON:
//				if(e.getSource().getEntity() instanceof EntityLivingBase){
//					EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
//					ItemStack held = attacker.getHeldItemMainhand();
//					if(ItemUtil.isItemStackPresent(held)){
//						return ForgingCapability.adapter.hasCapability(held);
//					}
//				}
//				break;
//			default:
//				break;
//
//			}
//			return false;
//		}
//
//		public abstract DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu,Type type,List<ItemStack> stacks);
//
//		@Override
//		public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
//			switch(type){
//			case ARMOR:
//				List<ItemStack> armors = Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream()
//				.filter(in -> ItemUtil.isItemStackPresent(in) && ForgingCapability.adapter.hasCapability(in))
//				.collect(Collectors.toList());
//		return this.process(e, dsu,type, armors);
//			case WEAPON:
//				EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
//				return this.process(e, dsu,type, Lists.newArrayList(attacker.getHeldItemMainhand()));
//			default:
//				break;
//
//			}
//			return dsu;
//		}
//
//		@Override
//		public String getName() {
//			// TODO 自動生成されたメソッド・スタブ
//			return "minsaga hurt event";
//		}
//
//	}


	public static void registerEvents(){
		HSLibs.registerEvent(new EventRefleshMinsagaForged());
		EventToolTipUnsaga.list.add(new ComponentDisplayInfo(6,
				(is,ep,dispList,par4)->is!=null && ForgingCapability.adapter.hasCapability(is) && ForgingCapability.adapter.getCapability(is).isForged()){

			@Override
			public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
				IMinsagaForge capa = ForgingCapability.adapter.getCapability(is);
				String forgedMaterials = capa.getForgingAttribute().stream().map((in)->{

					String color = Strings.EMPTY;
					if(in.hasFinishedFitting()){
						color = UnsagaTextFormatting.POSITIVE.toString();
					}
					return color + in.getForgedMaterial().getLocalized() + TextFormatting.GRAY;
				}).collect(Collectors.joining("/"));
				dispList.add(forgedMaterials);
				ForgeAttribute current = capa.getCurrentForge();
				if(GuiScreen.isShiftKeyDown()){
					if(current!=null){
						dispList.add(current.getFittingProgress()+"/"+current.getMaxFittingProgress());
					}
					if(capa.isForged()){
						dispList.addAll(MinsagaUtil.getModifierStrings(capa,OptionalInt.empty()));
						dispList.add(capa.getAbilities().stream().map(in -> in.getName()).collect(Collectors.joining("/")));
					}
				}else{
					dispList.add("[ShiftKey]Customized Tool Info");
				}

			}

		});


		EventToolTipUnsaga.list.add(new ComponentDisplayInfo(7,(is,ep,dispList,par4)->{
			if(ClientHelper.getCurrentGui() instanceof GuiSmithMinsaga){
				return is!=null && MinsagaForging.instance().getMaterialFromItemStack(is).isPresent();
			}
			return false;
		}){

			@Override
			public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
				// TODO 自動生成されたメソッド・スタブ
				Optional<MinsagaForging.Material> material = MinsagaForging.instance().getMaterialFromItemStack(is);
				if(material.isPresent()){
					dispList.add(HSLibs.translateKey("word.minsaga.material")+":"+material.get().getLocalized());
					List<String> strings = MinsagaUtil.getModifierStrings(material.get(),OptionalInt.of(material.get().getRepairDamage()));
					dispList.addAll(strings);
					if(material.get().hasAbilities()){
						//						String ab = material.get().getAbilities().stream().map(in -> in.getName()).collect(Collectors.joining("/"));
						dispList.add(UnsagaTextFormatting.PROPERTY+material.get().getAbilities().stream().map(in -> in.getName()).collect(Collectors.joining("/")));
					}
				}
			}}
				);


//		HurtEventMinsaga ev = new HurtEventMinsaga(){
//
//			@Override
//			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu, Type type,List<ItemStack> stacks) {
//				EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
//				double amount = stacks.stream().mapToDouble(in ->{
//					if(in!=null && ForgingCapability.adapter.hasCapability(in)){
//						IMinsagaForge capa = ForgingCapability.adapter.getCapability(in);
//						if(capa.isForged()){
//							float reduce = e.getSource().isMagicDamage() ? capa.getArmorModifier().magic() : capa.getArmorModifier().melee();
//
//							if(capa.getAbilities().contains(MinsagaForging.Ability.METEOR)){
//								if(e.getSource().isExplosion()){
//									reduce += (e.getAmount() * 0.3F);
//								}
//							}
//							return reduce;
//						}
//					}
//
//					return 0.0F;
//				}).sum();
//
//				if(attacker instanceof EntityLivingBase){
//					ItemStack is = attacker.getHeldItemMainhand();
//					if(is!=null && ForgingCapability.adapter.hasCapability(is)){
//						IMinsagaForge capa = ForgingCapability.adapter.getCapability(is);
//						if(capa.isForged()){
//							amount -= capa.getAttackModifier();
//						}
//					}
//				}
//				return null;
//			}
//		};


		//		UnsagaMod.events.getLivingHurtEventAppendable().getHooks().put(() -> new String("minsaga.modifier"), container ->{
		//			double amount = Lists.newArrayList(container.getVictim().getArmorInventoryList()).stream().mapToDouble(in ->{
		//				if(in!=null && ForgingCapability.adapter.hasCapability(in)){
		//					IMinsagaForge capa = ForgingCapability.adapter.getCapability(in);
		//					if(capa.isForged()){
		//						float reduce = container.getDamageSource().isMagicDamage() ? capa.getArmorModifier().magic() : capa.getArmorModifier().melee();
		//
		//						if(capa.getAbilities().contains(MinsagaForging.Ability.METEOR)){
		//							if(container.getDamageSource().isExplosion()){
		//								reduce += (container.getBaseDamage() * 0.3F);
		//							}
		//						}
		//						return reduce;
		//					}
		//				}
		//
		//				return 0.0F;
		//			}).sum();
		//
		//			if(container.getAttacker() instanceof EntityLivingBase){
		//				EntityLivingBase living = (EntityLivingBase) container.getAttacker();
		//				ItemStack is = living.getHeldItemMainhand();
		//				if(is!=null && ForgingCapability.adapter.hasCapability(is)){
		//					IMinsagaForge capa = ForgingCapability.adapter.getCapability(is);
		//					if(capa.isForged()){
		//						amount -= capa.getAttackModifier();
		//					}
		//				}
		//			}
		//			return MathHelper.clamp_float((float) (container.getBaseDamage()-amount), 0.0F, 65535F);
		//		});

		HSLib.core().events.livingHurt.getEventsMiddle().add(new EventFittingProgress());

		HSLibs.registerEvent(new EventApplyForgedAbility());
		HSLibs.registerEvent(new EventHarvestPlus());
		HSLibs.registerEvent(new EventItemUseFinished());
		/**
		 * かなとこでの修理
		 */
		HSLibs.registerEvent(new EventAnvil());

		/**
		 * 採掘速度の実装
		 */
		HSLibEvents.breakSpeed.addEvent(ev -> {
			ItemStack is = ev.getEntityPlayer().getHeldItemMainhand();
			if(is!=null && ForgingCapability.adapter.hasCapability(is)){
				float speed = ev.getOriginalSpeed() + ForgingCapability.adapter.getCapability(is).getEfficiencyModifier();
				if(ev.getEntityPlayer().isInWater() && MinsagaUtil.getAbilities(ev.getEntityPlayer()).contains(MinsagaForging.Ability.SEA)){
					speed *= 2.0F;
				}
				ev.setNewSpeed(speed);
			}
		});

		/**
		 * 強度の実装
		 */
		EventSaveDamage.events.add(living ->{
			if(living instanceof EntityPlayer){
				EntityPlayer ep = (EntityPlayer) living;
				World world = ep.getEntityWorld();
				ItemStack is = living.getHeldItemMainhand();
				if(is!=null && ForgingCapability.adapter.hasCapability(is)){
					MinsagaUtil.damageToolProcess(ep,is,ForgingCapability.adapter.getCapability(is).getDurabilityModifier(),ep.getRNG());
				}

			}
		});
		//		LivingHelper.registerEquipmentsChangedEvent(living -> {
		//
		//		});
	}
}
