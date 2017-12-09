package mods.hinasch.unsaga.core.entity.mob;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.core.entity.ai.EntityAISpell;
import mods.hinasch.unsaga.core.entity.ai.EntityAISpell.ISpellAI;
import mods.hinasch.unsaga.core.entity.ai.EntityAISpell.SpellAIData;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySignalTree extends EntityMob implements ISpellAI{

	public EntitySignalTree(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
    protected void applyEntityAttributes()
    {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0F);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0F);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

	@Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EntityRuffleTree.PLANT;
    }

    protected void initEntityAI()
    {
    	SpellRegistry spells = SpellRegistry.instance();
    	List<SpellAIData> spellList = Lists.newArrayList();
    	spellList.add(new SpellAIData(spells.callThunder,40.0F,10.0F,20));
    	spellList.add(new SpellAIData(spells.bubbleBlow,40.0F,10.0F,10));
    	spellList.add(new SpellAIData(spells.waterShield,50.0F,0.0F,18));

        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(3, new EntityAISpell(this,spellList,1.0D,200,20.0F,10));
    }

	@Override
	public boolean canCastSpell() {
		// TODO 自動生成されたメソッド・スタブ
		return true;
	}

}
