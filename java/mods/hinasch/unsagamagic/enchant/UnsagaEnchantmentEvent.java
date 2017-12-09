package mods.hinasch.unsagamagic.enchant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.entity.ModifierHelper;
import mods.hinasch.lib.event.LivingEquipChangedEvent;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.event.EventToolTipUnsaga;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import mods.hinasch.unsagamagic.enchant.UnsagaEnchantmentCapability.EnchantmentState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UnsagaEnchantmentEvent {


	public static final UUID UUID_ENCHANT = UUID.fromString("9dbd4988-3e31-4daa-bd4e-6b6e01620d0a");
	private void checkEnchantmentExpireTime(World w,EntityLivingBase living,ItemStack is){

		if(UnsagaEnchantmentCapability.adapter.hasCapability(is)){
			UnsagaEnchantmentCapability.adapter.getCapability(is).checkExpireTime(living, w.getTotalWorldTime());
		}

	}
	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent e){

		EntityLivingBase living = e.getEntityLiving();
		World world = living.getEntityWorld();
		if(e.getEntityLiving().ticksExisted % 4 == 0 ){
			ItemUtil.getArmorInventoryList(e.getEntityLiving()).stream().filter(in -> ItemUtil.isItemStackPresent(in))
			.forEach(in -> this.checkEnchantmentExpireTime(world,living,in));
			ItemUtil.getHeldEquipmentList(e.getEntityLiving()).stream().filter(in -> ItemUtil.isItemStackPresent(in))
			.forEach(in -> this.checkEnchantmentExpireTime(world,living,in));
		}

		//		if(e.getEntityLiving() instanceof EntityPlayer){
		//			EntityPlayer ep = (EntityPlayer) e.getEntityLiving();
		//			ItemUtil.getArmorInventoryList(ep).stream().filter(in ->ItemUtil.isItemStackPresent(in))
		//			.filter(in -> UnsagaEnchantmentCapability.adapter.hasCapability(in))
		//			.forEach(in -> {
		//				UnsagaEnchantmentCapability.adapter.getCapability(in).reduceArmorRemainings();
		//				checkEnchantment(UnsagaEnchantmentRegistry.instance().armorBless,in);
		//				});
		//			ItemUtil.getHeldEquipmentList(ep).stream().filter(in ->ItemUtil.isItemStackPresent(in))
		//			.filter(in -> UnsagaEnchantmentCapability.adapter.hasCapability(in))
		//			.forEach(in -> {
		//				UnsagaEnchantmentCapability.adapter.getCapability(in).reduceHeldRemainings();
		//				checkEnchantment(UnsagaEnchantmentRegistry.instance().weaponBless,in);
		//				checkEnchantment(UnsagaEnchantmentRegistry.instance().sharpness,in);
		//				});
		//		}
	}
	//	@SubscribeEvent
	//	public void onLivingAttack(LivingAttackEvent e){
	//
	//		if(e.getSource().getEntity() instanceof EntityLivingBase){
	//			ItemStack stack = ((EntityLivingBase) e.getSource().getEntity()).getHeldItemMainhand();
	//			UnsagaMod.logger.trace(this.getClass().getName(), "called",stack);
	//			if(ItemUtil.isItemStackPresent(stack)){
	//				if(UnsagaEnchantmentCapability.adapter.hasCapability(stack)){
	//					UnsagaEnchantmentCapability.adapter.getCapability(stack).onAttack();
	//					checkEnchantment(UnsagaEnchantmentRegistry.instance().weaponBless,stack);
	//					checkEnchantment(UnsagaEnchantmentRegistry.instance().sharpness,stack);
	//				}
	//			}
	//		}
	//
	//	}


	@SubscribeEvent
	public void onEquipChanged(LivingEquipChangedEvent ev){
		EntityLivingBase living = ev.getEntityLiving();
		refleshApplyEnchantment(living);

	}

	public static void refleshApplyEnchantment(EntityLivingBase living){
		ItemStack held = living.getHeldItemMainhand();
		Supplier<Float> weaponModSupplier = () ->{
			if(ItemUtil.isItemStackPresent(held) && UnsagaEnchantmentCapability.hasCapability(held)){
				Optional<EnchantmentWeapon> ench = UnsagaEnchantmentCapability.adapter.getCapability(held).getEntries().stream().filter(in -> in.getKey() instanceof EnchantmentWeapon)
						.map(in ->(EnchantmentWeapon)in.getKey()).findFirst();
				if(ench.isPresent() && living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)!=null){
					EnchantmentState state = UnsagaEnchantmentCapability.adapter.getCapability(held).getEnchantment(ench.get());
					//				UnsagaMod.logger.trace("ench",ench.get().getAttackModifier(state.getLevel()));
					return ench.get().getAttackModifier(state.getLevel());
				}
			}
			return 0.0F;
		};
		final double modValue = weaponModSupplier.get();

		AttributeModifier weaponMod = new AttributeModifier(UUID_ENCHANT, "modifier.enchant.unsaga", modValue, Statics.OPERATION_INCREMENT);
		ModifierHelper.refleshModifier(living, SharedMonsterAttributes.ATTACK_DAMAGE, weaponMod);


		final double modValueArmor = ItemUtil.getArmorInventoryList(living).stream().filter(in -> ItemUtil.isItemStackPresent(in)).filter(in -> UnsagaEnchantmentCapability.hasCapability(in))
				.filter(in -> UnsagaEnchantmentCapability.adapter.getCapability(in).hasEnchant(UnsagaEnchantmentRegistry.instance().armorBless))
				.mapToDouble(in ->{
					EnchantmentArmor armorEnch = (EnchantmentArmor) UnsagaEnchantmentRegistry.instance().armorBless;
					EnchantmentState state = UnsagaEnchantmentCapability.adapter.getCapability(in).getEnchantment(armorEnch);
					UnsagaMod.logger.trace("called", state.getLevel());
					return armorEnch.getArmorModifer(state.getLevel());
				}).sum();





		AttributeModifier armorMod = new AttributeModifier(UUID_ENCHANT, "modifier.enchant.unsaga", modValueArmor, Statics.OPERATION_INCREMENT);
		for(General at:General.values()){
			ModifierHelper.refleshModifier(living, AdditionalStatus.GENERALS.get(at), armorMod);
		}
	}

	@SubscribeEvent
	public void hookBreakSpeed(BreakSpeed e){
		ItemStack stack = e.getEntityPlayer().getHeldItemMainhand();
		if(ItemUtil.isItemStackPresent(stack) && UnsagaEnchantmentCapability.hasCapability(stack)){
			EnchantmentState ench = UnsagaEnchantmentCapability.adapter.getCapability(stack).getEnchantment(UnsagaEnchantmentRegistry.instance().sharpness);
			if(ench!=null){
				float speed = e.getOriginalSpeed() * (1.0F + (float)ench.getLevel()*0.2F);
				e.setNewSpeed(speed);

			}

		}
	}
	//	@SubscribeEvent
	//	public void onBreakBlock(BreakEvent e){
	//		ItemStack stack = e.getPlayer().getHeldItemMainhand();
	//		if(ItemUtil.isItemStackPresent(stack)){
	//			if(UnsagaEnchantmentCapability.adapter.hasCapability(stack)){
	//				UnsagaEnchantment sharpness = UnsagaEnchantmentRegistry.instance().sharpness;
	//				UnsagaEnchantmentCapability.adapter.getCapability(stack).reduceRemainings(sharpness);
	//				checkEnchantment(UnsagaEnchantmentRegistry.instance().sharpness,stack);
	//			}
	//		}
	//	}

	//	public static void checkEnchantment(EnchantmentProperty e,ItemStack is){
	//		if(UnsagaEnchantmentCapability.adapter.getCapability(is).getExpireTime(e)<=0){
	//			Map<Enchantment,Integer> map = EnchantmentHelper.getEnchantments(is);
	//			map.remove(e.getEnchantment());
	//			EnchantmentHelper.setEnchantments(map, is);
	//		}
	//	}

	public static void registerEvent(){
		HSLibs.registerEvent(new UnsagaEnchantmentEvent());
		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				// TODO 自動生成されたメソッド・スタブ
				if(e.getSource().getEntity() instanceof EntityLivingBase){
					ItemStack held = ((EntityLivingBase)e.getSource().getEntity()).getHeldItemMainhand();
					if(held!=null){
						return e.getSource().getSourceOfDamage() instanceof EntityArrow && held.getItem() instanceof ItemBow && UnsagaEnchantmentCapability.hasCapability(held);
					}

				}

				return false;
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
				ItemStack held = ((EntityLivingBase)e.getSource().getEntity()).getHeldItemMainhand();
				float modifier = UnsagaEnchantmentCapability.adapter.getCapability(held).getBowModifier();
				if(modifier>0.0F){
					e.setAmount(e.getAmount() + modifier);
				}
				return dsu;
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "bow enchantment";
			}}
				);
		EventToolTipUnsaga.list.add(new EnchantmentTooltip());
		//		HSLib.core().events.livingHurt.getEventsMiddle().add(new LivingHurtEventUnsagaBase(){
		//
		//			@Override
		//			public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		//				return Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream().anyMatch(in -> ItemUtil.isItemStackPresent(in) && UnsagaEnchantmentCapability.adapter.hasCapability(in)
		//						&& !UnsagaEnchantmentCapability.adapter.getCapability(in).isEmpty());
		//			}
		//
		//			@Override
		//			public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
		//				Lists.newArrayList(e.getEntityLiving().getArmorInventoryList()).stream().filter(in -> ItemUtil.isItemStackPresent(in) && UnsagaEnchantmentCapability.adapter.hasCapability(in))
		//				.forEach(in ->{
		//					UnsagaEnchantmentCapability.adapter.getCapability(in).onHurt();
		//					checkEnchantment(UnsagaEnchantmentRegistry.instance().armorBless,in);
		//				});
		//				return dsu;
		//			}
		//
		//			@Override
		//			public String getName() {
		//				// TODO 自動生成されたメソッド・スタブ
		//				return "enchantment";
		//			}}
		//		);
	}
}
