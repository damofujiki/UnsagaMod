package mods.hinasch.unsaga.damage;

import java.util.Random;

import com.google.common.base.Function;

import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.entity.EntityLivingBase;


public class DamageHelper {
//	addMap(ToolCategory.SWORD, Type.SWORD,0.3F);
//	addMap(ToolCategory.SPEAR, Type.SPEAR,0.5F);
//	addMap(ToolCategory.STAFF, Type.PUNCH,0.2F);
//	addMap(ToolCategory.BOW, Type.SPEAR,0.5F);
//	addMap(ToolCategory.AXE, Type.SWORDPUNCH,0.3F);
//	addMap(ToolCategory.KNIFE,Type.SPEAR,0.5F);
	public static interface DamageSourceSupplier extends Function<EntityLivingBase,DamageSourceUnsaga>{

	}
	public static final DamageSourceSupplier DEFAULT_SWORD = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.3F, General.SWORD);
		}
	};

	public static final DamageSourceSupplier DEFAULT_AXE = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.3F, General.SWORD,General.PUNCH);
		}
	};
	public static final DamageSourceSupplier DEFAULT_PICKAXE = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.3F, General.SPEAR,General.PUNCH);
		}
	};
	public static final DamageSourceSupplier DEFAULT_SPEAR = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.5F, General.SPEAR);
		}
	};

	public static final DamageSourceSupplier DEFAULT_STAFF = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.2F, General.PUNCH);
		}
	};

	public static final DamageSourceSupplier DEFAULT_BOW = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.6F, General.SPEAR);
		}
	};

	public static final DamageSourceSupplier DEFAULT_KNIFE = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.5F, General.SPEAR,General.SWORD);
		}
	};

	public static final DamageSourceSupplier DEFAULT_PUNCH = new DamageSourceSupplier(){

		@Override
		public DamageSourceUnsaga apply(EntityLivingBase input) {
			// TODO 自動生成されたメソッド・スタブ
			return DamageSourceUnsaga.create(input, 0.2F, General.PUNCH);
		}
	};


	public static float calcReduceAmount(float base,int perMin,int perMax,int level,Random rand){
		float par = ((rand.nextInt(perMax-perMin)+perMin)*level)*0.01F;
		if(par>0.9F){
			par = 0.9F;
		}
		return base * par;
	}
}
