//package mods.hinasch.unsagamagic.item;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import mods.hinasch.unsaga.element.FiveElements;
//import mods.hinasch.unsaga.element.FiveElements.Type;
//import mods.hinasch.unsaga.skillpanel.SkillPanels;
//import mods.hinasch.unsaga.skillpanel.SkillPanels.SkillPanel;
//import mods.hinasch.unsagamagic.item.ItemSpellBook.CastingResult;
//import mods.hinasch.unsagamagic.spell.Spell;
//import mods.hinasch.unsagamagic.util.SpellBookHelper;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//
//public class CastingRequirementGroup {
//
//	protected List<CastingRequirement> spellSettingList = new ArrayList();
//
//	//詠唱可能な状態の設定
//	public static final CastingRequirement DUMMY = new CastingRequirement("dummy",-1);
//	//クリエイティブモード
////	public static final CastingRequirement CREATIVE = CastingRequirement.of("creativeInvoke", 10,
////			(ep,is,spell) ->ep instanceof EntityPlayer && ((EntityPlayer)ep).capabilities.isCreativeMode);
//
//	public static final CastingRequirement CREATIVE = new CastingRequirement("creativeInvoke",10){
//		@Override
//		public CastingResult getResult(EntityLivingBase ep,ItemStack is,Spell spell){
//			if(ep instanceof EntityPlayer && ((EntityPlayer)ep).capabilities.isCreativeMode){
//				return new CastingResult(true,null,this);
//			}
//			return CastingResult.FAILED;
//		}
//	};
//	//アイテムの消費しての詠唱 TODO:ファミリア
//	public static final CastingRequirement FROM_ITEM = new CastingRequirement("invokeByFamiliar",2){
//
//
//		@Override
//		public CastingResult getResult(EntityLivingBase ep,ItemStack is,Spell spell){
//			if(SpellBookHelper.findFirstMagicItem(ep, spell.element)!=null){
//				return new CastingResult(true,SpellBookHelper.findFirstMagicItem(ep, spell.element),this);
//			}
//			return CastingResult.FAILED;
//		}
//	};
//
//	public static final CastingRequirement FROM_FAMILIAR = new CastingRequirement("invokeByFamiliar",2){
//		@Override
//		public CastingResult getResult(EntityLivingBase ep,ItemStack is,Spell spell){
//
//			FiveElements.Type spellElement = spell.element;
//			if(ep instanceof EntityPlayer && spellElement!=null && spellElement!=Type.FORBIDDEN){
//				SkillPanel familiarRequire = spellElement.getFamiliar();
//				if(familiarRequire!=null && SkillPanels.hasPanel(ep.worldObj, (EntityPlayer) ep, familiarRequire)){
//					return CastingResult.FAMILIAR;
//				}
//			}
//			return CastingResult.FAILED;
//		}
//	};
//
//	public CastingRequirementGroup(){
//		this.spellSettingList.add(CREATIVE);
//		this.spellSettingList.add(FROM_ITEM);
//		this.spellSettingList.add(FROM_FAMILIAR);
//	}
//
//	public List<CastingRequirement> getRequirements(){
//		return this.spellSettingList;
//	}
//
//	public static class CastingRequirement{
//		/**
//		 * 大きいほど優先度高い
//		 */
//		public final int priority;
//		public final String name;
//		ICastingRequirement func;
//
//		@Deprecated
//		public static CastingRequirement of(String name,int prior,ICastingRequirement func){
//
//			return new CastingRequirement(name,prior,func){
//				@Override
//				public CastingResult getResult(EntityLivingBase ep,ItemStack is,Spell spell){
//					if(this.func.apply(ep, is, spell)){
//						new CastingResult(true,null,this);
//					}
//					return CastingResult.FAILED;
//				}
//
//			};
//		}
//		protected CastingRequirement(String name,int prior,ICastingRequirement... func){
//			this.priority = prior;
//			this.name = name;
//			if(func.length>0){
//				this.func = func[0];
//			}
//		}
//		public CastingResult getResult(EntityLivingBase ep,ItemStack is,Spell spell){
//			return CastingResult.FAILED;
//		}
//
//		@Override
//		public String toString(){
//			return this.name;
//		}
//	}
//
//	public static interface ICastingRequirement{
//		public boolean apply(EntityLivingBase ep,ItemStack is,Spell spell);
//	}
//
//}
