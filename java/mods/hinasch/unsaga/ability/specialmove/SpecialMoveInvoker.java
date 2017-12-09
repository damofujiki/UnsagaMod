package mods.hinasch.unsaga.ability.specialmove;

import java.util.Optional;

import mods.hinasch.lib.iface.IExtendedReach;
import mods.hinasch.lib.item.ItemUtil;
import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialMoveBase;
import mods.hinasch.unsaga.ability.specialmove.action.StatePropertySpecialMove;
import mods.hinasch.unsaga.common.specialaction.ActionPerformerBase;
import mods.hinasch.unsaga.core.potion.UnsagaPotions;
import mods.hinasch.unsaga.damage.PairDamage;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SpecialMoveInvoker extends ActionPerformerBase<SpecialMove,SpecialMoveBase>{

	public static enum InvokeType{
		NONE,USE,CHARGE,RIGHTCLICK,BOW,SPRINT_RIGHTCLICK;
	}

	int usingTime = 0;
	InvokeType invokeType = InvokeType.NONE;
	Optional<ComponentArrow> arrowEntity = Optional.empty();
	public SpecialMoveInvoker(World world, EntityLivingBase performer,SpecialMove move) {
		super(world, performer,move);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public InvokeType getInvokeType(){
		return this.invokeType;
	}

	public void setArrowComponent(EntityArrow arrow,ItemStack arrowStack){
		this.arrowEntity = Optional.of(new ComponentArrow(arrowStack,arrow));
	}

	public Optional<ComponentArrow> getArrowComponent(){
		return this.arrowEntity;
	}
	public void swingMainHand(boolean swingSound,boolean sweepParticle){
		this.getPerformer().swingArm(EnumHand.MAIN_HAND);
		if(swingSound){
			this.playSound(XYZPos.createFrom(getPerformer()), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, false);

		}
		if(sweepParticle){
			this.spawnSweepParticle();
		}
	}
	public void spawnSweepParticle(){
		if(this.getPerformer() instanceof EntityPlayer){
			((EntityPlayer)this.getPerformer()).spawnSweepParticles();
		}
	}
	public SpecialMoveInvoker setInvokeType(InvokeType type){
		this.invokeType = type;
		return this;
	}

	public float getReach(){
		if(this.article.isPresent()){
			if(this.article.get().getItem() instanceof IExtendedReach){
				return ((IExtendedReach)this.article.get().getItem()).getReach();
			}
		}
		return 4.0F;
	}

	public int getChargedTime(){
		return this.usingTime;
	}

	public PairDamage getStrength(){
		return this.getActionProperty().getStrength();
	}
	public SpecialMoveInvoker setChargedTime(int time){
		this.usingTime = time;
		return this;
	}
	@Override
	public SpecialMoveBase getAction() {
		// TODO 自動生成されたメソッド・スタブ
		return SpecialMoveRegistry.instance().getAssociatedAction(getActionProperty());
	}


	public void invoke(){
		if(this.canInvoke()){
			if(StatePropertySpecialMove.getState(getPerformer()).isPresent()){
				StatePropertySpecialMove.getState(getPerformer()).get().setSpecialMoveProgress(true);
			}
//			this.getAction().getPrePerform().accept(this);
			EnumActionResult result = this.getAction().perform(this);

			if(result!=EnumActionResult.PASS){
				if(this.getActionProperty().getCoolingTime().isPresent()){
					int cooling = this.getActionProperty().getCoolingTime().getAsInt();
					this.getPerformer().addPotionEffect(new PotionEffect(UnsagaPotions.instance().coolDown,ItemUtil.getPotionTime(cooling),0));
				}
				this.consumeCost();
			}
		}

		if(StatePropertySpecialMove.getState(getPerformer()).isPresent()){
			StatePropertySpecialMove.getState(getPerformer()).get().setSpecialMoveProgress(false);
		}
	}

	public float getInvokerStrength(){
		if(this.getPerformer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)!=null){
			return (float) this.getPerformer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		}
		return 1.0F;
	}

	public float getModifiedStrengthHP(){
		float at = this.getInvokerStrength() + this.getActionProperty().getStrength().hp();
		return MathHelper.clamp_float(at, 0.1F, 65535F);
	}
	public boolean canInvoke(){
//
//		if(this.getTargetType()==TargetType.TARGET){
//			if(TargetHolderCapability.adapter.hasCapability(getPerformer())){
//				if(!TargetHolderCapability.adapter.getCapability(getPerformer()).getTarget().isPresent()){
//					return false;
//				}
//			}
//		}
		if(!this.getPerformer().isPotionActive(UnsagaPotions.instance().coolDown)){
			if(this.getPerformer() instanceof EntityPlayer){
				return this.checkItemCost();
			}

		}

		return false;
	}

	public float getSkillPanelReduction(){
		if(SkillPanelAPI.hasPanel(getPerformer(), SkillPanelRegistry.instance().specialMoveMaster)){
			return 0.13F * SkillPanelAPI.getHighestPanelLevel(getPerformer(), SkillPanelRegistry.instance().specialMoveMaster).getAsInt();
		}
		return 0.0F;
	}

	@Override
	public int getCost(){
		int cost = this.getActionProperty().getCost();
		cost  -= (int) ((float)this.getActionProperty().getCost() * this.getSkillPanelReduction());
		return MathHelper.clamp_int(cost, 1, 65535);
	}
	private void consumeCost(){
		if(this.getPerformer() instanceof EntityPlayer){
			if(this.getArticle().isPresent()){
				this.getArticle().get().damageItem(this.getCost(), this.getPerformer());
			}else{
				EntityPlayer ep = (EntityPlayer) this.getPerformer();
				float foodCost = (float)this.getCost() * 0.01F * 2.0F;
				if(ep.getFoodStats().getFoodLevel()>=1){
					ep.getFoodStats().addExhaustion(foodCost);
				}else{
					ep.attackEntityFrom(DamageSource.starve, foodCost * 10.0F);
				}
			}
		}

	}

	@Override
	public PairDamage getModifiedStrength() {
		// TODO 自動生成されたメソッド・スタブ
		return PairDamage.of(this.getModifiedStrengthHP(), this.getStrength().lp());
	}

	public static class ComponentArrow{
		public final ItemStack arrowStack;
		public final EntityArrow arrowEntity;

		public ComponentArrow(ItemStack stack,EntityArrow e){
			this.arrowEntity = e;
			this.arrowStack = stack;
		}
	}
}
