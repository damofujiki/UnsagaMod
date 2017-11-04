package mods.hinasch.unsaga.minsaga;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import joptsimple.internal.Strings;
import mods.hinasch.lib.client.ClientHelper;
import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.core.HSLibEvents;
import mods.hinasch.lib.entity.ModifierHelper;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.misc.ObjectCounter;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.unsaga.core.client.gui.GuiSmithMinsaga;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga.ComponentDisplayInfo;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.minsaga.ForgingCapability.ForgeAttribute;
import mods.hinasch.unsaga.minsaga.ForgingCapability.IMinsagaForge;
import mods.hinasch.unsaga.skillpanel.EventSaveDamage;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import mods.hinasch.unsaga.util.UnsagaTextFormatting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;



public class MinsagaForgingEvent {


	public static enum Type{
		WEAPON,ARMOR;
	}

	public static abstract class HurtEventMinsaga extends LivingHurtEventUnsagaBase{

		Type type;

		@Override
		public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			if(type==Type.WEAPON){
				if(e.getSource().getEntity() instanceof EntityLivingBase){
					EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
					ItemStack held = attacker.getHeldItemMainhand();
					if(ItemUtil.isItemStackPresent(held)){
						return ForgingCapability.adapter.hasCapability(held);
					}
				}
			}
			if(type==Type.ARMOR){
				return Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream()
						.anyMatch(in -> ItemUtil.isItemStackPresent(in) && ForgingCapability.adapter.hasCapability(in));
			}
			return false;
		}

		public abstract DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu,Type type,List<ItemStack> stacks);

		@Override
		public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			if(type==Type.WEAPON){
				EntityLivingBase attacker = (EntityLivingBase) e.getSource().getEntity();
				return this.process(e, dsu,type, Lists.newArrayList(attacker.getHeldItemMainhand()));
			}
			if(type==Type.ARMOR){
				List<ItemStack> armors = Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream()
						.filter(in -> ItemUtil.isItemStackPresent(in) && ForgingCapability.adapter.hasCapability(in))
						.collect(Collectors.toList());
				return this.process(e, dsu,type, armors);
			}
			return dsu;
		}

		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return "minsaga hurt event";
		}

	}


	public static final UUID MINSAGA = UUID.fromString("46b2abe7-06fd-462c-871e-8a2cde17c2d1");
	public static final UUID MINSAGA2 = UUID.fromString("a4b3fffd-4e10-4c3a-bc39-0cbf8b7a49b3");
	public static final UUID MINSAGA3 = UUID.fromString("1b43a63e-710f-4986-aad3-19ff856f8c12");
	public static final UUID MINSAGA4 = UUID.fromString("b1d1240c-d90e-422c-9a04-2480bf71bd60");
	public static void onEquipChanged(EntityLivingBase el){
		if(ItemUtil.isItemStackPresent(el.getHeldItemMainhand())){
			ItemStack held = el.getHeldItemMainhand();
			if(ForgingCapability.adapter.hasCapability(held)){
				double attack = ForgingCapability.adapter.getCapability(held).getAttackModifier();
				AttributeModifier attackModfier = new AttributeModifier(MINSAGA, "minsaga.forging", attack, Statics.OPERATION_INCREMENT);
				ModifierHelper.refleshModifier(el, SharedMonsterAttributes.ATTACK_DAMAGE, attackModfier);
			}
		}


		double amountMelee = MinsagaUtil.getForgedArmors(el).stream().mapToDouble(in -> ForgingCapability.adapter.getCapability(in).getArmorModifier().melee()).sum();
		double amountMagic  = MinsagaUtil.getForgedArmors(el).stream().mapToDouble(in -> ForgingCapability.adapter.getCapability(in).getArmorModifier().magic()).sum();
		AttributeModifier meleeArmorModifier = new AttributeModifier(MINSAGA, "minsaga.forging", amountMelee, Statics.OPERATION_INCREMENT);
		ModifierHelper.refleshModifier(el, SharedMonsterAttributes.ARMOR, meleeArmorModifier);
		AttributeModifier magicArmorModifier = new AttributeModifier(MINSAGA, "minsaga.forging", amountMagic, Statics.OPERATION_INCREMENT);
		ModifierHelper.refleshModifier(el, AdditionalStatus.MENTAL, magicArmorModifier);

		List<MinsagaForging.Ability> abilities = MinsagaUtil.getAbilities(el);
		ObjectCounter<MinsagaForging.Ability> counter = new ObjectCounter();
		abilities.stream().forEach(in ->{
			counter.add(in);
		});
		if(el instanceof EntityPlayer){
			double amountLuck = counter.get(MinsagaForging.Ability.LOOT) * 1.0D;
			AttributeModifier luckModifier = new AttributeModifier(MINSAGA2, "minsaga.forging", amountLuck, Statics.OPERATION_INCREMENT);
			ModifierHelper.refleshModifier(el, SharedMonsterAttributes.LUCK, luckModifier);
		}


//		double amountAbyss = counter.get(MinsagaForging.Ability.ABYSS) * 1.0D;
//		AttributeModifier abyssModifier = new AttributeModifier(MINSAGA3, "minsaga.forging", amountAbyss, Statics.OPERATION_INCREMENT);
//		ModifierHelper.refleshModifier(el, AdditionalStatus.ENTITY_ELEMENTS.get(FiveElements.Type.FORBIDDEN), abyssModifier);

		double amountQuickSilver = counter.get(MinsagaForging.Ability.QUICKSILVER) * 0.5D;
		AttributeModifier quickSilverModifier = new AttributeModifier(MINSAGA4, "minsaga.forging", amountQuickSilver, Statics.OPERATION_INCREMENT);
		ModifierHelper.refleshModifier(el, AdditionalStatus.INTELLIGENCE, quickSilverModifier);
	}
	public static void registerEvents(){
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
				if(current!=null){
					dispList.add(current.getFittingProgress()+"/"+current.getMaxFittingProgress());
				}
				if(capa.isForged()){
					dispList.addAll(MinsagaUtil.getModifierStrings(capa,OptionalInt.empty()));
					dispList.add(capa.getAbilities().stream().map(in -> in.getName()).collect(Collectors.joining("/")));
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

		HSLibs.registerEvent(new EventOnHurtByForged());
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
