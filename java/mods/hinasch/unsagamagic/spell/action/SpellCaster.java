package mods.hinasch.unsagamagic.spell.action;

import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.common.specialaction.ActionPerformerBase;
import mods.hinasch.unsaga.common.specialaction.IActionPerformer;
import mods.hinasch.unsaga.common.specialaction.ISpecialActionBase;
import mods.hinasch.unsaga.damage.PairDamage;
import mods.hinasch.unsaga.element.FiveElements;
import mods.hinasch.unsaga.element.newele.ElementAssociationLibrary;
import mods.hinasch.unsaga.element.newele.ElementTable;
import mods.hinasch.unsaga.skillpanel.SkillPanelAPI;
import mods.hinasch.unsaga.skillpanel.SkillPanelRegistry;
import mods.hinasch.unsaga.status.TargetHolderCapability;
import mods.hinasch.unsagamagic.spell.Spell;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import mods.hinasch.unsagamagic.spell.SpellUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class SpellCaster extends ActionPerformerBase<Spell,ISpecialActionBase<SpellCaster>>{

	public static enum CastType{
		CREATIVE,ITEM,FAMILIAR,ENEMY;
	}



	final CastType type;
	final ElementTable table;

	float amp = 1.0F;
	float cost = 1.0F;


	public static final double CASTING_LIMIT = 60.0D;

	public static SpellCaster ofArticle(World w,EntityLivingBase caster,Spell spell,ItemStack magicItem){
		SpellCaster performer = new SpellCaster(w, caster, spell,CastType.ITEM);
		performer.setMagicItem(magicItem);
		return performer;
	}
	public static SpellCaster ofCreative(World w,EntityLivingBase caster,Spell spell){
		SpellCaster performer = new SpellCaster(w, caster, spell,CastType.CREATIVE);
		return performer;
	}

	public static SpellCaster ofFamiliar(World w,EntityLivingBase caster,Spell spell){
		SpellCaster performer = new SpellCaster(w, caster, spell,CastType.FAMILIAR);
		return performer;
	}

	public static SpellCaster ofEnemy(World w,EntityLivingBase caster,Spell spell){
		SpellCaster performer = new SpellCaster(w, caster, spell,CastType.ENEMY);
		return performer;
	}
	public SpellCaster(World world, EntityLivingBase performer,Spell spell,CastType type) {
		super(world, performer,spell);
		this.type = type;
		this.table = ElementAssociationLibrary.instance().calcAllElements(getWorld(), getPerformer());
		if(this.targetType==null){
			this.setTargetType(IActionPerformer.TargetType.OWNER);
		}
	}



	@Override
	public ISpecialActionBase<SpellCaster> getAction() {
		// TODO 自動生成されたメソッド・スタブ
		return SpellRegistry.instance().getSpellAction(this.getActionProperty());
	}

	public void setAmpCost(float amp,float cost){
		this.amp = amp;
		this.cost = cost;
	}
	public CastType getCastType(){
		return this.type;
	}

	public float getAmplify(){
		return MathHelper.clamp_float(amp, 0.1F, 255F);
	}

	public ElementTable getElementTable(){
		return this.table;
	}
	public FiveElements.Type getElement(){
		return this.getActionProperty().getElement();
	}

	public float getSkillPanelReduction(){
		if(SkillPanelAPI.hasPanel(getPerformer(), SkillPanelRegistry.instance().magicExpert)){
			return 0.13F * SkillPanelAPI.getHighestPanelLevel(getPerformer(), SkillPanelRegistry.instance().magicExpert).getAsInt();
		}
		return 0.0F;
	}

	@Override
	public int getCost(){
		int cost = SpellUtil.calcCost(this.getActionProperty().getCost(),this.amp);
		cost  -= (int) ((float)this.getActionProperty().getCost() * this.getSkillPanelReduction());
		return MathHelper.clamp_int(cost, 1, 65535);
	}

	public SpellCaster setMagicItem(ItemStack is){
		this.setArticle(is);
		return this;
	}

	public void cast(){
		UnsagaMod.logger.trace(this.getClass().getName(),this.getCastType());
		if(this.canCast()){
			if(this.getAction()!=null){
//				if(this.getAction().getCastSound().isPresent()){
//					this.playSound(XYZPos.createFrom(getPerformer()), this.getAction().getCastSound().get(), true);
//				}
//				this.getAction().getPrePerform().accept(this);
				EnumActionResult result = this.getAction().perform(this);
				if(result!=EnumActionResult.PASS){
					this.consumeCost();
				}
			}

		}

	}

	private boolean checkDistance(){
		if(this.getTargetType()==IActionPerformer.TargetType.POSITION ){
			if(this.getTargetCoordinate().isPresent()){
				XYZPos pos = new XYZPos(this.getTargetCoordinate().get());
				if(this.getPerformer().getDistance(pos.dx, pos.dy, pos.dz)<CASTING_LIMIT){
					return true;
				}
			}else{
				return false;
			}
		}
		if(this.getTargetType()==IActionPerformer.TargetType.TARGET){
			if(TargetHolderCapability.adapter.hasCapability(getPerformer())){
				if(TargetHolderCapability.adapter.getCapability(getPerformer()).getTarget().isPresent()){
					return TargetHolderCapability.adapter.getCapability(getPerformer()).getTarget().get().getDistanceToEntity(getPerformer())<CASTING_LIMIT;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	private boolean canCast(){
		if(!this.checkDistance()){
			return false;
		}
		switch(this.getCastType()){
		case CREATIVE:
			return true;
		case ENEMY:
			return true;
		case FAMILIAR:
			EntityPlayer ep = (EntityPlayer) this.getPerformer();
			float cost = (float)this.getActionProperty().getCost() * 0.01F * 2.0F;
			return ep.experience >= cost;
		case ITEM:
			return this.checkItemCost();
		default:
			break;

		}
		return false;
	}
	private void consumeCost(){
		switch(this.getCastType()){
		case CREATIVE:
			break;
		case ENEMY:
			break;
		case FAMILIAR:
			EntityPlayer ep = (EntityPlayer) this.getPerformer();
			float foodCost = (float)this.getCost() * 0.01F * 2.0F;
			ep.experience -= foodCost;

			break;
		case ITEM:
			if(this.getArticle().isPresent()){
				this.getArticle().get().damageItem(this.getCost(), getPerformer());
			}
			break;
		default:
			break;

		}
	}

	public boolean isBenefical(){
		return this.getAction().isBenefical();
	}
	public PairDamage getEffectModifiedStrength(){
		float hpBase = this.getActionProperty().getEffectStrength().hp();
		float lp = this.getActionProperty().getEffectStrength().lp();
		float hp = hpBase * (this.getAmplify() * this.getAmplify() > 1.0F ? this.getActionProperty().getGrowth() : 1.0F);
		hp += hpBase * (table.get(getElement())*0.1F);
		hp = MathHelper.clamp_float(hp, 1, this.getActionProperty().getEffectStrength().hp() * 3.0F * this.getActionProperty().getGrowth());

		if(this.getPerformer() instanceof IMob){
			hp = MathHelper.clamp_float(hp, 1, 10);
			if(this.isBenefical()){
				hp = MathHelper.clamp_float(hp, 5, 255);
			}
		}

		return PairDamage.of(hp, lp);
	}
	@Override
	public PairDamage getModifiedStrength() {
		// TODO 自動生成されたメソッド・スタブ
		return this.getEffectModifiedStrength();
	}

}
