package mods.hinasch.unsaga.damage;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.DamageTypeHelper;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.entity.EntityStateCapability;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.core.entity.StateRegistry;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.IUnsagaDamageType;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsaga.lp.LPAssociation;
import mods.hinasch.unsaga.lp.LPAssociation.LPAttribute;
import mods.hinasch.unsaga.status.AdditionalStatus;
import mods.hinasch.unsaga.util.ToolCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceUnsaga extends EntityDamageSource{

	//	protected static Map<ToolCategory,Type> typesNormalAttack;
	protected static DamageSource dummy = new DamageSource("dummy");
	protected static Map<ToolCategory,Float> strsNormalAttack;

	protected float strLPHurt = 1.0F;
	protected int numberOfLPHurt = 1;


	protected EnumSet<DamageTypeUnsaga.General> damageTypeUnsaga = EnumSet.noneOf(General.class);

	protected EnumSet<DamageTypeUnsaga.Sub> subTypes =  EnumSet.noneOf(Sub.class);

	@Nullable
	protected DamageSource parentDamageSource;

	protected boolean isRefrain = false;

	public boolean isRefrain() {
		return isRefrain;
	}
	public DamageSourceUnsaga setRefrain(boolean isRefrain) {
		this.isRefrain = isRefrain;
		return this;
	}

	protected Entity sourceOfDamage;
	protected Entity indirectEntity;

	public int getNumberOfLPHurt() {
		return numberOfLPHurt;
	}
	public void setNumberOfLPHurt(int numberOfLPHurt) {
		this.numberOfLPHurt = numberOfLPHurt;
	}

	public static DamageSourceUnsaga create(DamageTypeHelper attackerType,Entity entity,float lpHurt,DamageTypeUnsaga.General damageType){
		return new DamageSourceUnsaga(attackerType.getString(),entity,lpHurt,damageType);
	}
	public static DamageSourceUnsaga create(Entity entity,float lpHurt,EnumSet<DamageTypeUnsaga.General> damageType){
		return new DamageSourceUnsaga(DamageTypeHelper.fromEntity(entity).getString(),entity,lpHurt,damageType);
	}

	public static DamageSourceUnsaga create(Entity entity,float lpHurt,DamageTypeUnsaga.General... damageType){
		return new DamageSourceUnsaga(DamageTypeHelper.fromEntity(entity).getString(),entity,lpHurt,damageType);
	}

	public static DamageSourceUnsaga createProjectile(Entity entity,Entity projectile,float lpHurt,DamageTypeUnsaga.General... damageType){
		return new DamageSourceUnsaga(DamageTypeHelper.fromEntity(entity).getString(),entity,lpHurt,projectile,damageType);
	}
	public static DamageSourceUnsaga fromVanilla(DamageSource vanillaDS){
		return new DamageSourceUnsaga(vanillaDS);
	}

	protected DamageSourceUnsaga(DamageTypeHelper damageType, Entity par2Entity) {
		super(damageType.getString(), par2Entity);

	}
	protected DamageSourceUnsaga(String damageType, Entity par2Entity,float lpHurt,EnumSet<DamageTypeUnsaga.General> types) {
		this(damageType, par2Entity);

		this.strLPHurt = lpHurt;
		this.setDamageTypeUnsaga(types);
	}

	protected DamageSourceUnsaga(String damageType, Entity par2Entity,float lpHurt,DamageTypeUnsaga.General... types) {
		this(damageType, par2Entity,lpHurt,EnumSet.copyOf(Lists.newArrayList(types)));
	}

	protected DamageSourceUnsaga(String damageType, Entity par2Entity,float lpHurt,Entity projectile,DamageTypeUnsaga.General... types) {
		this(damageType, par2Entity,lpHurt,types);

		this.sourceOfDamage = projectile;
		this.setProjectile();
	}
	protected DamageSourceUnsaga(DamageSource ds){

		super(ds.damageType,ds.getEntity());
		this.parentDamageSource = ds;
		this.strLPHurt = 0.3F; //素殴り

		this.createFromVanilla(ds);
	}
	protected DamageSourceUnsaga(String damageTypeIn, Entity damageSourceEntityIn) {
		super(damageTypeIn, damageSourceEntityIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}


	protected void createFromVanilla(final DamageSource ds){


		UnsagaMod.logger.trace(this.getClass().getName(), ds.getEntity(),ds.getClass(),ds.getSourceOfDamage());
		this.setDamageTypeUnsaga(ds.isMagicDamage() ? General.MAGIC : General.SWORD);

		if(DamageTypeAssociation.instance().getData(ds).isPresent()){
			List<DamageTypeAssociation.Attribute> attributes = DamageTypeAssociation.instance().getData(ds).get();
			if(DamageTypeAssociation.getGeneralByPriority(attributes).isPresent()){
				this.setDamageTypeUnsaga(DamageTypeAssociation.getGeneralByPriority(attributes).get());
			}
			if(DamageTypeAssociation.getSubs(attributes).isPresent()){
				this.setSubTypes(DamageTypeAssociation.getSubs(attributes).get());
			}



			UnsagaMod.logger.trace(this.getClass().getName(), this.getDamageTypeUnsaga(),this.getSubTypes());

		}


		if(ds.isDifficultyScaled()){
			this.setDifficultyScaled();
		}
		if(ds.getEntity() instanceof EntityLivingBase){
			this.figureEntityWeapon(ds);
		}

		this.initEntityLPStr(ds);
	}

	protected void initEntityLPStr(DamageSource ds){
		//		if(sourceEntity==null){
		//			this.setNumberOfLPHurt(0);
		//			this.setStrLPHurt(0.1F);
		//		}
		Entity sourceEntity = ds.getEntity();
		if(sourceEntity instanceof EntityLivingBase){
			EntityLivingBase attacker = (EntityLivingBase) sourceEntity;
			double lpstr = 1.0D;
			if(attacker.getEntityAttribute(AdditionalStatus.DEXTALITY)!=null){
				lpstr = attacker.getEntityAttribute(AdditionalStatus.DEXTALITY).getAttributeValue();
				this.setNumberOfLPHurt(1);

				//LP攻撃力のついた武器があれば加算（プレイヤーに限る）
				if(attacker.getHeldItemMainhand()!=null && attacker instanceof EntityPlayer){
					Item held = attacker.getHeldItemMainhand().getItem();
					if(LPAssociation.instance().getData(attacker.getHeldItemMainhand()).isPresent()){
						LPAttribute data = LPAssociation.instance().getData(attacker.getHeldItemMainhand()).get();
						lpstr += data.getLpStr();
						this.setNumberOfLPHurt(data.getNumberOfAttack());
					}

				}

				if(LPAssociation.instance().getData(ds).isPresent()){
					lpstr = LPAssociation.instance().getData(ds).get().getLpStr();
					this.setNumberOfLPHurt(LPAssociation.instance().getData(ds).get().getNumberOfAttack());
				}
				if(ds.getSourceOfDamage() instanceof EntityArrow){
					if(EntityStateCapability.adapter.hasCapability(ds.getSourceOfDamage())){
						StateArrow state = (StateArrow) EntityStateCapability.adapter.getCapability(ds.getSourceOfDamage()).getState(StateRegistry.instance().stateArrow);
						lpstr = state.getLPHurtStrength().isPresent() ? state.getLPHurtStrength().getAsDouble() : lpstr;


					}


				}
			}


			this.setStrLPHurt((float)lpstr);
		}

	}
	public void figureEntityWeapon(DamageSource ds){
		EntityLivingBase living = (EntityLivingBase)ds.getEntity();
		if(DamageTypeAssociation.instance().getData(living).isPresent()){
			List<DamageTypeAssociation.Attribute> attributes = DamageTypeAssociation.instance().getData(living).get();
			if(DamageTypeAssociation.getGeneralByPriority(attributes).isPresent()){
				this.setDamageTypeUnsaga(DamageTypeAssociation.getGeneralByPriority(attributes).get());
			}
			if(DamageTypeAssociation.getSubs(attributes).isPresent()){
				this.setSubTypes(DamageTypeAssociation.getSubs(attributes).get());
			}

		}
		UnsagaMod.logger.trace(this.getClass().getName(), this.getDamageTypeUnsaga(),this.getSubTypes());
		if(living.getHeldItemMainhand()==null && this.getDamageTypeUnsaga().isEmpty()){
			//素手
			this.setDamageTypeUnsaga(DamageTypeUnsaga.General.PUNCH);
			return;
		}
		if(living.getHeldItemMainhand()!=null){
			ItemStack held = living.getHeldItemMainhand();
			Item heldItem = held.getItem();
			if(heldItem.isItemTool(living.getHeldItemMainhand())){
				this.setDamageTypeUnsaga(DamageTypeUnsaga.General.SWORD);
			}

			if(DamageTypeAssociation.instance().getData(held).isPresent()){
				List<DamageTypeAssociation.Attribute> attributes = DamageTypeAssociation.instance().getData(held).get();
				if(DamageTypeAssociation.getGeneralByPriority(attributes).isPresent()){
					this.setDamageTypeUnsaga(DamageTypeAssociation.getGeneralByPriority(attributes).get());
				}
				if(DamageTypeAssociation.getSubs(attributes).isPresent()){
					this.setSubTypes(DamageTypeAssociation.getSubs(attributes).get());
				}
			}


			UnsagaMod.logger.trace(this.getClass().getName(), this.getDamageTypeUnsaga(),this.getSubTypes());

		}
	}
	public DamageSource getParentDamageSource() {
		return parentDamageSource;
	}

	@Nullable
	@Override
	public Entity getSourceOfDamage() {
		return sourceOfDamage;
	}



	public float getStrLPHurt() {
		return strLPHurt;
	}

	public EnumSet<DamageTypeUnsaga.Sub> getSubTypes() {
		return subTypes;
	}

	public DamageSourceUnsaga addSubTypes(EnumSet<Sub> subs){
		List<Sub> list = Lists.newArrayList(this.getSubTypes());
		list.addAll(subs);
		this.setSubTypes(EnumSet.copyOf(list));
		return this;
	}
	public DamageSourceUnsaga setSubTypes(EnumSet<Sub> subs){
		this.subTypes = subs;
		if(this.subTypes.contains(Sub.FIRE)){
			this.setFireDamage();
		}
		return this;
	}

	public DamageSourceUnsaga setSubTypes(Sub... subs){
		return this.setSubTypes(EnumSet.copyOf(Lists.newArrayList(subs)));
	}

	@Override
	public boolean isCreativePlayer()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isCreativePlayer();
		}
		return super.isCreativePlayer();
	}

	@Override
	public boolean isDamageAbsolute()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isDamageAbsolute();
		}
		return super.isDamageAbsolute();
	}

	@Override
	public boolean isDifficultyScaled()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isDifficultyScaled();
		}
		return super.isDifficultyScaled();
	}
	@Override
	public boolean isExplosion()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isExplosion();
		}
		return super.isExplosion();
	}
	@Override
	public boolean isFireDamage()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isFireDamage();
		}
		return super.isFireDamage();
	}
	@Override
	public boolean isMagicDamage()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isMagicDamage();
		}
		return super.isMagicDamage();
	}
	@Override
	public boolean isProjectile()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isProjectile();
		}
		return super.isProjectile();
	}

	@Override
	public boolean isUnblockable()
	{
		if(this.parentDamageSource!=null){
			return this.parentDamageSource.isUnblockable();
		}
		return super.isUnblockable();
	}

	public void setDamageTypeUnsaga(EnumSet<DamageTypeUnsaga.General> types){
		this.damageTypeUnsaga = types;
		if(this.damageTypeUnsaga.contains(General.MAGIC)){
			this.setMagicDamage();
		}
	}
	public void setDamageTypeUnsaga(DamageTypeUnsaga.General... types){
		this.setDamageTypeUnsaga(EnumSet.copyOf(Lists.newArrayList(types)));

	}

	public EnumSet<DamageTypeUnsaga.General> getDamageTypeUnsaga(){

		return this.damageTypeUnsaga;
	}

	public DamageSourceUnsaga setStrLPHurt(float strLPHurt) {
		this.strLPHurt = strLPHurt;
		return this;
	}

	//	protected void setSubDamageType(DamageTypeUnsaga.Sub... types){
	//
	//
	//		this.subTypes = EnumSet.copyOf(Lists.newArrayList(types));
	//	}


	public Set<IUnsagaDamageType> getAllDamageType(){
		Set<IUnsagaDamageType> set = Sets.newHashSet();
		set.addAll(this.getDamageTypeUnsaga());
		set.addAll(this.getSubTypes());
		return set;
	}



	@Override
	public String toString(){
		String str = String.format("Attacker:%s Type:[%s] SubType:[%s] LP Str:%.2f Num of LP Hurt:%d", this.getEntity(),HSLibs.joinCollection(damageTypeUnsaga, "/"),HSLibs.joinCollection(subTypes, "/"),this.getStrLPHurt(),this.getNumberOfLPHurt());
		return str;
	}
}