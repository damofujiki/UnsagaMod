package mods.hinasch.unsaga.core.entity.mob;

import java.util.List;

import com.google.common.collect.Lists;

import mods.hinasch.unsaga.core.entity.ai.EntityAISpell;
import mods.hinasch.unsaga.core.entity.ai.EntityAISpell.SpellAIData;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPoisonEater extends EntityStormEater{

	public EntityPoisonEater(World worldIn) {
		super(worldIn);
		// TODO 自動生成されたコンストラクター・スタブ
	}

    @Override
    protected void initEntityAI()
    {

    	SpellRegistry spells = SpellRegistry.instance();
    	List<SpellAIData> spellList = Lists.newArrayList();
    	spellList.add(new SpellAIData(spells.deadlyDrive,40.0F,10.0F,30));
    	spellList.add(new SpellAIData(spells.purify,40.0F,10.0F,10));
    	spellList.add(new SpellAIData(spells.fear,50.0F,0.0F,10));
//    	spellList.add(new SpellAIData(spells.waterShield,50.0F,0.0F,10));
//        this.tasks.addTask(4, new EntityBlaze.AIFireballAttack(this));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
//        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAISpell(this,spellList,1.0D,200,20.0F,10));
//        this.tasks.addTask(5, new AIRandomFly(this));
//        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }


    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        if (super.attackEntityAsMob(entityIn))
        {
            if (entityIn instanceof EntityLivingBase)
            {
                int i = 0;

                if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL)
                {
                    i = 7;
                }
                else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                {
                    i = 15;
                }

                if (i > 0)
                {
                    ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, i * 20, 0));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
