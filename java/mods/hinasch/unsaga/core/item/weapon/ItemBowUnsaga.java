//package mods.hinasch.unsaga.core.item.weapon;
//
//
//import java.util.Optional;
//
//import mods.hinasch.unsaga.UnsagaMod;
//import mods.hinasch.unsaga.ability.wazaeffect.WazaPerformer;
//import mods.hinasch.unsaga.ability.wazaeffect.wazagroup.WazaBowBase;
//import mods.hinasch.unsaga.ability.wazaeffect.wazagroup.WazaGroupBow;
//import mods.hinasch.unsaga.core.item.weapon.base.ItemBowBase;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import net.minecraft.enchantment.EnchantmentHelper;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.entity.projectile.EntityArrow;
//import net.minecraft.init.Enchantments;
//import net.minecraft.init.Items;
//import net.minecraft.init.SoundEvents;
//import net.minecraft.item.ItemArrow;
//import net.minecraft.item.ItemStack;
//import net.minecraft.stats.StatList;
//import net.minecraft.util.SoundCategory;
//import net.minecraft.world.World;
//
//public class ItemBowUnsaga extends ItemBowBase{
//
//	public ItemBowUnsaga(UnsagaMaterial material) {
//		super(material);
//		// TODO 自動生成されたコンストラクター・スタブ
//	}
//
//
//    @Override
//    public void onPlayerStoppedUsing(final ItemStack bowstack, final World worldIn, final EntityLivingBase entityLiving, int timeLeft)
//    {
//        if (entityLiving instanceof EntityPlayer)
//        {
//            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
//            boolean flagCreativeorInfinity = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bowstack) > 0;
//            ItemStack itemstack = this.arrowFinder.findInventoryArrow(entityplayer);
//
//            int duration = this.getMaxItemUseDuration(bowstack) - timeLeft;
//            duration = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bowstack, worldIn, (EntityPlayer)entityLiving, duration, itemstack != null || flagCreativeorInfinity);
//            if (duration < 0) return;
//
//            if (itemstack != null || flagCreativeorInfinity)
//            {
//                if (itemstack == null)
//                {
//                    itemstack = new ItemStack(Items.ARROW);
//                }
//
//                final float f = usingDurationToDrawPower(duration);
//
//                if ((double)f >= 0.1D)
//                {
//                    boolean isCreativeArrow = flagCreativeorInfinity && itemstack.getItem() instanceof ItemArrow; //Forge: Fix consuming custom arrows.
//
//                    if (!worldIn.isRemote)
//                    {
//                        final Optional<WazaPerformer> wazaPerformer = this.component.getBowSkillInvoker(bowstack, worldIn, entityLiving, f);
//
//                        UnsagaMod.logger.trace("bowinvoker", wazaPerformer);
//                        final Optional<WazaBowBase> wazaEffect = this.getWazaEffectFromPerformer(wazaPerformer);
//
//						if(wazaEffect.isPresent()){
//							wazaPerformer.get().setCharge(f);
//							if(wazaEffect.get().preShoot(wazaPerformer.get(), entityLiving)==WazaGroupBow.Result.CANCEL){
//
//								return;
//							}
//						}
//
//                        ItemArrow itemarrow = (ItemArrow)((ItemArrow)(itemstack.getItem() instanceof ItemArrow ? itemstack.getItem() : Items.ARROW));
//                        EntityArrow entityarrow = itemarrow.createArrow(worldIn, itemstack, entityplayer);
//                        entityarrow.setAim(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, f * 3.0F, 1.0F);
//
//						if(wazaEffect.isPresent()){
//							wazaPerformer.get().setCharge(f);
//							wazaEffect.get().onArrowConstructed(entityarrow);
//						}
//                        this.postSuccessFoundArrow(itemstack, worldIn, entityLiving, timeLeft);
//                        if (f == 1.0F)
//                        {
//                            entityarrow.setIsCritical(true);
//                        }
//
//                        int enchantPower = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bowstack);
//
//                        if (enchantPower > 0)
//                        {
//                            entityarrow.setDamage(entityarrow.getDamage() + (double)enchantPower * 0.5D + 0.5D);
//                        }
//
//                        int enchantKnockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bowstack);
//
//                        if (enchantKnockback > 0)
//                        {
//                            entityarrow.setKnockbackStrength(enchantKnockback);
//                        }
//
//                        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bowstack) > 0)
//                        {
//                            entityarrow.setFire(100);
//                        }
//
//                        bowstack.damageItem(1, entityplayer);
//
//                        if (isCreativeArrow)
//                        {
//                            entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
//                        }
//
//                        worldIn.spawnEntityInWorld(entityarrow);
//                    }
//
//                    worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
//
//                    if (!isCreativeArrow)
//                    {
//                        --itemstack.stackSize;
//
//                        if (itemstack.stackSize == 0)
//                        {
//                            entityplayer.inventory.deleteStack(itemstack);
//                        }
//                    }
//
//                    entityplayer.addStat(StatList.getObjectUseStats(this));
//                }
//            }
//        }
//    }
//
//    private Optional<WazaBowBase> getWazaEffectFromPerformer(Optional<WazaPerformer> wazaPerformer){
//		if(wazaPerformer.isPresent()){
//			if(wazaPerformer.get().getEffect() instanceof WazaBowBase){
//				return Optional.of((WazaBowBase)wazaPerformer.get().getEffect());
//			}
//		}
//		return Optional.empty();
//    }
//}
