//package mods.hinasch.unsaga.core.item.weapon.base;
//
//import java.util.List;
//
//import com.google.common.collect.Multimap;
//
//import mods.hinasch.lib.client.ClientHelper;
//import mods.hinasch.lib.iface.IExtendedReach;
//import mods.hinasch.unsaga.capability.IUnsagaDamageSource;
//import mods.hinasch.unsaga.core.item.weapon.base.ComponentUnsagaTool.IComponentDisplayInfo;
//import mods.hinasch.unsaga.damage.DamageHelper;
//import mods.hinasch.unsaga.damage.DamageHelper.DamageSourceSupplier;
//import mods.hinasch.unsaga.init.UnsagaMaterial;
//import mods.hinasch.unsaga.util.MaterialAnalyzer;
//import mods.hinasch.unsaga.util.ToolCategory;
//import mods.hinasch.unsaga.util.UnsagaTextFormatting;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.renderer.color.IItemColor;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.ai.attributes.AttributeModifier;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.inventory.EntityEquipmentSlot;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.ItemSword;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.network.play.server.SPacketAnimation;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//
//public class ItemSpearBase extends ItemSword implements IComponentUnsagaTool,IItemColor,IExtendedReach,IUnsagaDamageSource{
//
//	protected ComponentUnsagaTool component;
//	protected final float weaponDamage;
//
//	public ItemSpearBase(UnsagaMaterial material) {
//		super(material.getToolMaterial());
//		this.component = new ComponentUnsagaTool(material,ToolCategory.SPEAR);
//		this.component.addDisplayInfoComponents(new IComponentDisplayInfo(){
//
//			@Override
//			public boolean predicate(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//				// TODO 自動生成されたメソッド・スタブ
//				return is!=null && is.getItem() instanceof ItemSpearBase;
//			}
//
//			@Override
//			public void addInfo(ItemStack is, EntityPlayer ep, List dispList, boolean par4) {
//				// TODO 自動生成されたメソッド・スタブ
//				dispList.add(UnsagaTextFormatting.POSITIVE+"Long Reach");
//			}}
//				);
//		this.component.addDisplayInfoComponents(ComponentUnsagaTool.COMPONENTS_TOOLS_DISPLAY);
//		this.weaponDamage = 3.0F + material.getToolMaterial().getDamageVsEntity();
//		this.setMaxDamage((int)((float)material.getToolMaterial().getMaxUses()*0.8F));
//		this.component.addPropertyOverride(this);
//	}
//
//	//	@Override
//	//	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
//	//		if(par1ItemStack!=null){
//	//			if(par3Entity instanceof EntityPlayer){
//	//				EntityPlayer ep = (EntityPlayer)par3Entity;
//	//				if(ep.swingProgressInt==1){
//	//					ItemStack is = ep.getHeldItemMainhand();
//	//					if((is!=null) && (is.getItem() instanceof IExtendedReach)){
//	//
//	//						this.doSpearAttack(is, ep, par2World);
//	//					}
//	//
//	//				}
//	//			}
//	//
//	//		}
//	//
//	//	}
//	@Override
//	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack)
//	{
//		if(MaterialAnalyzer.hasCapability(par2ItemStack) && MaterialAnalyzer.hasCapability(par1ItemStack)){
//			if(MaterialAnalyzer.getCapability(par2ItemStack).getMaterial().isPresent() && MaterialAnalyzer.getCapability(par1ItemStack).getMaterial().isPresent()){
//				return MaterialAnalyzer.getCapability(par2ItemStack).getMaterial().get()==MaterialAnalyzer.getCapability(par1ItemStack).getMaterial().get();
//			}
//		}
//
//		return false;
//	}
//	@Override
//	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack is)
//	{
//		if(!entityLiving.isSwingInProgress ){
//			float reach = ((IExtendedReach) is.getItem()).getReach();
//			RayTraceResult mop = ClientHelper.getMouseOverLongReach();
//			if(mop!=null){
//				if(mop.entityHit!=null && entityLiving instanceof EntityPlayer){
//					return this.doSpearAttack(is, (EntityPlayer) entityLiving, entityLiving.getEntityWorld());
//				}
//			}
//		}
//
//
//		return false;
//	}
//
//	protected boolean doSpearAttack(ItemStack is,EntityPlayer ep,World par2World){
//
//		float reach = ((IExtendedReach) is.getItem()).getReach();
//		RayTraceResult mop = ClientHelper.getMouseOverLongReach();
//		//		Unsaga.debug(mop);
//		if(mop!=null){
//			if(mop.entityHit!=null){
//				//				Unsaga.debug(mop);
//				float dis = ep.getDistanceToEntity(mop.entityHit);
//				if(dis<reach){
//					return this.onSpearReachedEntity(mop, reach, ep, par2World);
//
//				}
//			}
//		}
//		return false;
//	}
//
//    public void swingArm(EntityPlayer ep,EnumHand hand)
//    {
//        ItemStack stack = ep.getHeldItem(hand);
//        if (!ep.isSwingInProgress || ep.swingProgressInt < 0)
//        {
//            ep.swingProgressInt = -1;
//            ep.isSwingInProgress = true;
//            ep.swingingHand = hand;
//
//            if (ep.worldObj instanceof WorldServer)
//            {
//                ((WorldServer)ep.worldObj).getEntityTracker().sendToAllTrackingEntity(ep, new SPacketAnimation(ep, hand == EnumHand.MAIN_HAND ? 0 : 3));
//            }
//        }
//    }
//	protected boolean onSpearReachedEntity(RayTraceResult mop,float reach,EntityPlayer ep,World par2World){
//
//
//		if(mop.entityHit.hurtResistantTime==0 ){
//			AxisAlignedBB ab = mop.entityHit.getEntityBoundingBox();
//			List<Entity> list = par2World.getEntitiesWithinAABB(Entity.class, ab);
//
//			if(!list.isEmpty()){
//
//				Entity hurtEnt = list.get(0);
//				//ep.attackTargetEntityWithCurrentItem(hurtEnt);
//				if(hurtEnt!=ep){
//
//					this.swingArm(ep,EnumHand.MAIN_HAND);
//					Minecraft.getMinecraft().playerController.attackEntity(ep, hurtEnt);
//
//					return true;
//				}
//			}
//
//
//		}
//		return false;
//	}
//
//	@Override
//	public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
//	{
//		return component.initWeaponCapabilities(stack, nbt);
//	}
//
//	@Override
//	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
//		component.addUnsagaItemInfo(par1ItemStack, par2EntityPlayer, par3List, par4);
//	}
//
//
//
//	@Override
//	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
//	{
//		return this.component.getItemAttributeModifiers(equipmentSlot, weaponDamage, -3.2D, stack, ATTACK_DAMAGE_MODIFIER, ATTACK_SPEED_MODIFIER);
//	}
//
//	@Override
//	public float getReach() {
//		// TODO 自動生成されたメソッド・スタブ
//		return 8.0F;
//	}
//
//	@Override
//	public int getColorFromItemstack(ItemStack stack, int tintIndex) {
//		// TODO 自動生成されたメソッド・スタブ
//		return component.getColorFromItemStack(stack, tintIndex);
//	}
//
//	@Override
//	public DamageSourceSupplier getUnsagaDamageSource() {
//		// TODO 自動生成されたメソッド・スタブ
//		return DamageHelper.DEFAULT_SPEAR;
//	}
//
//	@Override
//	public void setUnsagaDamageSourceSupplier(DamageSourceSupplier par1) {
//		// TODO 自動生成されたメソッド・スタブ
//
//	}
//
//	@Override
//	public ComponentUnsagaTool getToolComponent() {
//		// TODO 自動生成されたメソッド・スタブ
//		return this.component;
//	}
//
//}
