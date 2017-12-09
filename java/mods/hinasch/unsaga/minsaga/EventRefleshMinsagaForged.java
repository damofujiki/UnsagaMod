package mods.hinasch.unsaga.minsaga;

import java.util.List;
import java.util.UUID;

import mods.hinasch.lib.entity.ModifierHelper;
import mods.hinasch.lib.event.LivingEquipChangedEvent;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.misc.ObjectCounter;
import mods.hinasch.lib.util.Statics;
import mods.hinasch.unsaga.status.AdditionalStatus;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventRefleshMinsagaForged {

	public static final UUID MINSAGA = UUID.fromString("46b2abe7-06fd-462c-871e-8a2cde17c2d1");
	public static final UUID MINSAGA2 = UUID.fromString("a4b3fffd-4e10-4c3a-bc39-0cbf8b7a49b3");
	public static final UUID MINSAGA3 = UUID.fromString("1b43a63e-710f-4986-aad3-19ff856f8c12");
	public static final UUID MINSAGA4 = UUID.fromString("b1d1240c-d90e-422c-9a04-2480bf71bd60");
	@SubscribeEvent
	public void onEquipChanged(LivingEquipChangedEvent ev){
		EntityLivingBase el = ev.getEntityLiving();
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
}
