package mods.hinasch.unsaga.core.entity;

import java.util.OptionalDouble;

import mods.hinasch.lib.iface.IIntSerializable;
import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveRegistry;
import mods.hinasch.unsaga.damage.DamageSourceUnsaga;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.Sub;
import mods.hinasch.unsagamagic.spell.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;

public class StatePropertyArrow extends StateProperty{

	public StatePropertyArrow(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public State createState(){
		return new StateArrow();
	}

	public static class StateArrow extends State{


		public StateArrow() {
			super(true);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public static enum Type implements IIntSerializable{
			NONE(0),MAGIC_ARROW(1),EXORCIST(2),SHADOW_STITCH(3),ZAPPER(4)
			,PHOENIX(5);

			final int meta;
			private Type(int meta){
				this.meta = meta;
			}
			@Override
			public int getMeta() {
				// TODO 自動生成されたメソッド・スタブ
				return this.meta;
			}

			public float getDamage(EntityLivingBase victim,float amount){
				if(this==EXORCIST && victim.getCreatureAttribute()==EnumCreatureAttribute.UNDEAD){
					return amount * 1.5F;
				}
				return amount;
			}
			public DamageSourceUnsaga getDamageSource(EntityLivingBase attacker,Entity arrow){
				DamageSourceUnsaga dsu = DamageSourceUnsaga.createProjectile(attacker, arrow, this.getLPStrength(), General.SPEAR);
				if(this==EXORCIST){
					dsu.setSubTypes(Sub.SHOCK);
				}
				if(this==MAGIC_ARROW){
					dsu.setDamageTypeUnsaga(General.MAGIC);
					dsu.setMagicDamage();
				}
				return dsu;
			}

			public float getLPStrength(){
				SpecialMoveRegistry reg = SpecialMoveRegistry.instance();
				switch(this){
				case EXORCIST:
					return reg.exorcist.getStrength().lp();
				case MAGIC_ARROW:
					return SpellRegistry.instance().fireArrow.getEffectStrength().lp();
				case NONE:
					break;
				case PHOENIX:

					return reg.phoenix.getStrength().lp();
				case SHADOW_STITCH:
					return 0.0F;
				case ZAPPER:
					return reg.zapper.getStrength().lp();
				default:
					break;

				}
				return 1.0F;
			}
			public EnumParticleTypes getParticle(){
				switch(this){
				case PHOENIX:
					return EnumParticleTypes.FLAME;
				case EXORCIST:
					return EnumParticleTypes.VILLAGER_HAPPY;
				case MAGIC_ARROW:
					return EnumParticleTypes.FLAME;
				case NONE:
					break;
				case SHADOW_STITCH:
					return EnumParticleTypes.PORTAL;
				case ZAPPER:
					return EnumParticleTypes.SPELL;
				default:
					break;

				}
				return EnumParticleTypes.CRIT;
			}
			public static Type fromMeta(int meta){
				return HSLibs.fromMeta(Type.values(), meta);
			}
		}

		boolean isCancelHurtRegistance = false;
		Type type = Type.NONE;
		OptionalDouble lpStr = OptionalDouble.empty();

		public void setType(StateArrow.Type type){
			this.type = type;;
		}
		public StateArrow.Type getType(){
			return this.type;
		}

		public boolean isCancelHurtRegistance(){
			return this.isCancelHurtRegistance;
		}

		public void setCancelHurtRegistance(boolean par1){
			this.isCancelHurtRegistance = par1;
		}


		public OptionalDouble getLPHurtStrength(){
			return this.lpStr;
		}

		public void setLPHurtStrength(float par1){
			this.lpStr = OptionalDouble.of(par1);
		}

		@Override
		public NBTTagCompound writeNBT(NBTTagCompound tag){
			if(this.lpStr.isPresent()){
				tag.setFloat("lpstr", (float) lpStr.getAsDouble());
			}
			tag.setInteger("type", this.type.getMeta());
			tag.setBoolean("cancelRegist", this.isCancelHurtRegistance);
			return tag;
		}

		@Override
		public NBTTagCompound readNBT(NBTTagCompound tag){
			if(tag.hasKey("lpstr")){
				this.lpStr = OptionalDouble.of(tag.getFloat("lpstr"));
			}
			if(tag.hasKey("type")){
				this.type = Type.fromMeta(tag.getInteger("type"));
			}
			if(tag.hasKey("cancelRegist")){
				this.isCancelHurtRegistance = tag.getBoolean("cancelRegist");
			}
			return tag;
		}
	}
}
