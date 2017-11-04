package mods.hinasch.unsaga.core.item.newitem.combat;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.iface.LivingHurtEventBase;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.world.WorldHelper;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.ability.AbilityAPI;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.material.UnsagaMaterial;
import mods.hinasch.unsaga.material.UnsagaMaterialTool;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import mods.hinasch.unsaga.util.LivingHurtEventUnsagaBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EventShield {

	public static LivingHurtEventBase shieldGuard = new LivingHurtEventUnsagaBase(){

		@Override
		public boolean apply(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			// TODO 自動生成されたメソッド・スタブ
			return e.getEntityLiving().isActiveItemStackBlocking();
		}

		@Override
		public String getName() {
			// TODO 自動生成されたメソッド・スタブ
			return "Enemy's Melee Attack Guard Event";
		}

		private float getShieldProb(EntityPlayer ep,int shieldPower){
			float base = shieldPower * 0.01F;
			if(SkillPanelAPI.hasPanel(ep, SkillPanelRegistry.instance().shield)){
				float multiply = 1.0F + (0.15F * (float)SkillPanelAPI.getHighestPanelLevel(ep, SkillPanelRegistry.instance().shield).getAsInt());
				base *= multiply;
			}
			return MathHelper.clamp_float(base, 0, 0.99F);
		}

		@Override
		public DamageSource process(LivingHurtEvent e, DamageSourceUnsaga dsu) {
			ItemStack shield = e.getEntityLiving().getActiveItemStack();
			if(UnsagaMaterialTool.adapter.hasCapability(shield)){
				UnsagaMaterial m = UnsagaMaterialTool.adapter.getCapability(shield).getMaterial();
				if(!dsu.isUnblockable()){

					float reduce = e.getAmount() * ((float)m.getShieldPower() * 0.01F);
					float reduced = MathHelper.clamp_float(e.getAmount() - reduce, 0.01F, 65535F);
					e.setAmount(reduced);
					UnsagaMod.logger.trace("shield", e.getAmount(),reduce,reduced);
				}
				if(e.getEntityLiving() instanceof EntityPlayer && SkillPanelAPI.hasPanel((EntityPlayer) e.getEntityLiving(),SkillPanelRegistry.instance().shield)){
					this.processAvoidDamage(e, shield,m.getShieldPower(), dsu);
				}


			}
			return dsu;
		}
		private void processAvoidDamage(LivingHurtEvent e, ItemStack shield,int shieldPower,DamageSourceUnsaga dsu){
			if(AbilityAPI.getAttachedPassiveAbilities(shield).stream().filter(in->in.getBlockableDamage()!=null)
					.anyMatch(in -> in.getBlockableDamage().test(dsu))){

				EntityPlayer ep = (EntityPlayer) e.getEntityLiving();
				float prob = this.getShieldProb(ep, shieldPower);
				if(ep.getRNG().nextFloat()<prob){
					e.setAmount(0);
					if(WorldHelper.isServer(ep.getEntityWorld())){
						HSLib.core().getPacketDispatcher().sendTo(PacketSound.atEntity(SoundEvents.BLOCK_ANVIL_LAND, ep), (EntityPlayerMP) ep);
					}

				}

			}
		}};

		public static LivingHurtEventBase shieldDamage = new LivingHurtEventBase(){

			@Override
			public boolean apply(LivingHurtEvent e, DamageSource dsu) {
				// TODO 自動生成されたメソッド・スタブ
				return e.getEntityLiving().isActiveItemStackBlocking() && !dsu.isUnblockable();
			}

			//バニラのままでは盾へのダメージが適用されないため
			protected void damageShield(float damage,EntityLivingBase living)
			{

				ItemStack shield = living.getActiveItemStack();
				UnsagaMod.logger.trace("shield",damage,shield);
				if (shield != null && shield.getItem() instanceof ItemShieldUnsaga)
				{
					int itemDamage = 0;
					if(damage>0.0F){
						itemDamage = 1 + (int)Math.floor(damage);
					}else{
						itemDamage = living.getEntityWorld().rand.nextInt(3)+1;
					}
					shield.damageItem(itemDamage,living);


					if (ItemUtil.getStackSize(living.getActiveItemStack()) <= 0)
					{
						EnumHand enumhand = living.getActiveHand();
						if(living instanceof EntityPlayer){
							net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) living, living.getActiveItemStack(), enumhand);
						}


						if (enumhand == EnumHand.MAIN_HAND)
						{
							living.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, (ItemStack)null);
						}
						else
						{
							living.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, (ItemStack)null);
						}

						living.resetActiveHand();
						living.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + living.getEntityWorld().rand.nextFloat() * 0.4F);
					}
				}
			}

			@Override
			public String getName() {
				// TODO 自動生成されたメソッド・スタブ
				return "Shield Events";
			}

			@Override
			public DamageSource process(LivingHurtEvent e, DamageSource dsu) {
				this.damageShield(e.getAmount(), e.getEntityLiving());
				return dsu;
			}
		};

		public static void registerEvents(){
			HSLib.core().events.livingHurt.getEventsMiddle().add(shieldDamage);
			HSLib.core().events.livingHurt.getEventsMiddle().add(shieldGuard);
		}
}
