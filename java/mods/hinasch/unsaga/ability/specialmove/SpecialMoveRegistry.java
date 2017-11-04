package mods.hinasch.unsaga.ability.specialmove;

import java.util.Map;

import com.google.common.collect.Maps;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.network.PacketSound;
import mods.hinasch.lib.network.PacketUtil;
import mods.hinasch.lib.registry.PropertyRegistry;
import mods.hinasch.unsaga.ability.specialmove.SpecialMoveInvoker.InvokeType;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialActionComponents;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialMoveArrow;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialMoveBase;
import mods.hinasch.unsaga.ability.specialmove.action.SpecialMoveRapidArrow;
import mods.hinasch.unsaga.common.specialaction.ActionBase.IAction;
import mods.hinasch.unsaga.common.specialaction.ActionCharged;
import mods.hinasch.unsaga.common.specialaction.ISpecialActionBase;
import mods.hinasch.unsaga.core.entity.StatePropertyArrow.StateArrow;
import mods.hinasch.unsaga.damage.DamageTypeUnsaga.General;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;

public class SpecialMoveRegistry extends PropertyRegistry<SpecialMove>{

	protected static SpecialMoveRegistry INSTANCE;

	Map<SpecialMove,ISpecialActionBase<SpecialMoveInvoker>> moveActionAssociation = Maps.newHashMap();


	public SpecialMove caleidoscope = new SpecialMove("caleidoscope");
	public SpecialMove smash = new SpecialMove("smash").setCost(3).setStrength(3.0F, 1.3F);
	public SpecialMove vandalize = new SpecialMove("vandalize").setCost(10).setStrength(5.0F, 1.5F).setCoolingTime(10);
	public SpecialMove chargeBlade = new SpecialMove("chargeBlade").setStrength(1.5F, 1.0F).setCoolingTime(3);
	public SpecialMove gust = new SpecialMove("gust").setCoolingTime(5).setStrength(3.0F, 1.3F);
	public SpecialMove hawkBlade = new SpecialMove("hawkBlade").setCoolingTime(4).setStrength(1.0F, 1.0F);

	public SpecialMove tomahawk = new SpecialMove("tomahawk").setCost(3).setStrength(0.5F, 1.5F);
	public SpecialMove fujiView = new SpecialMove("fujiView").setCost(10).setStrength(6.0F, 1.5F);
	public SpecialMove skyDrive = new SpecialMove("skyDrive").setCost(10).setStrength(2.5F, 2.0F).setRequireTarget(true).setCoolingTime(10);
	public SpecialMove woodChopper = new SpecialMove("woodChopper").setCost(4).setStrength(0.0F, 1.5F);
	public SpecialMove crossing = new SpecialMove("crossing");
	public SpecialMove yoyo = new SpecialMove("yoyo").setCost(3).setStrength(0.5F, 1.5F);
	public SpecialMove firewoodChopper = new SpecialMove("firewoodChopper").setStrength(1.0F, 1.5F).setCost(5);

	public SpecialMove aiming = new SpecialMove("aiming").setCost(5).setStrength(1.0F, 1.8F);
	public SpecialMove acupuncture = new SpecialMove("acupuncture").setCost(15).setStrength(2.0F, 2.5F).setCoolingTime(10);
	public SpecialMove swing = new SpecialMove("swing").setCost(8).setCoolingTime(3).setStrength(-1.0F, 1.2F);
	public SpecialMove grasshopper = new SpecialMove("grasshopper").setCost(4).setStrength(-3.0F, 0.1F);
	public SpecialMove armOfLight = new SpecialMove("armOfLight").setCost(8).setStrength(1.5F, 1.8F);

	public SpecialMove earthDragon = new SpecialMove("earthDragon").setStrength(-2.0F, 0.1F).setCost(8).setCoolingTime(3);
	public SpecialMove skullCrash = new SpecialMove("skullCrash").setStrength(0.5F, 1.0F).setCost(4);
	public SpecialMove pulverizer = new SpecialMove("pulverizer").setStrength(0.5F, 1.0F).setCost(4);
	public SpecialMove grandSlam = new SpecialMove("grandSlam").setStrength(0.0F, 0.2F).setCost(15).setCoolingTime(10);
	public SpecialMove gonger = new SpecialMove("gonger").setStrength(0.5F, 1.0F).setCost(3);
	public SpecialMove rockCrasher = new SpecialMove("rockCrash").setStrength(1.5F, 1.5F).setCost(4);

	public SpecialMove doubleShot = new SpecialMove("doubleShot");
	public SpecialMove tripleShot = new SpecialMove("tripleShot");
	public SpecialMove zapper = new SpecialMove("zapper").setStrength(4.0F, 2.5F).setCost(10);
	public SpecialMove exorcist = new SpecialMove("exorcist").setStrength(0.5F, 1.5F).setCost(7);
	public SpecialMove shadowStitching = new SpecialMove("shadowStitching").setStrength(0.0F, 1.0F).setCost(2);
	public SpecialMove phoenix = new SpecialMove("phoenix").setStrength(3.0F, 2.0F).setCost(15);
	public SpecialMove arrowRain = new SpecialMove("arrowRain");
	public SpecialMove quickChecker = new SpecialMove("quickChecker").setCost(4);

	public SpecialMove knifeThrow = new SpecialMove("knifeThrow").setCost(3).setStrength(0.5F, 2.0F);
	public SpecialMove stunner = new SpecialMove("stunner").setCost(3).setStrength(0.0F, 2.0F);
	public SpecialMove bloodMary = new SpecialMove("bloodyMary").setCost(8).setStrength(1.0F, 1.7F);
	public SpecialMove cutIn = new SpecialMove("cutIn");
	public SpecialMove blitz = new SpecialMove("blitz").setCoolingTime(4).setStrength(3.0F, 2.0F);
	public SpecialMove lightningThrust = new SpecialMove("lightningThrust").setCost(10).setStrength(1.5F, 2.0F).setCoolingTime(5);

	public SpecialMove airThrow = new SpecialMove("airThrow");
	public SpecialMove cyclone = new SpecialMove("cyclone");
	public SpecialMove callback = new SpecialMove("callback");


	public static SpecialMoveRegistry instance(){
		if(INSTANCE==null){
			INSTANCE = new SpecialMoveRegistry();
		}
		return INSTANCE;
	}
	@Override
	public void init() {
		// TODO 自動生成されたメソッド・スタブ

		this.associate();
	}

	@Override
	public void preInit() {
		// TODO 自動生成されたメソッド・スタブ
		this.registerObjects();
	}

	public SpecialMoveBase getAssociatedAction(SpecialMove move){
		return (SpecialMoveBase) this.moveActionAssociation.get(move);
	}
	protected void associate(){

		//弓系
		IAction<SpecialMoveInvoker> arrowSound = in ->{
			HSLib.core().getPacketDispatcher().sendToAllAround(PacketSound.atEntity(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, in.getPerformer()), PacketUtil.getTargetPointNear(in.getPerformer()));
			return EnumActionResult.PASS;
		};

		moveActionAssociation.put(quickChecker, SpecialActionComponents.quickChecker());
		moveActionAssociation.put(exorcist, new SpecialMoveArrow().setArrowType(StateArrow.Type.EXORCIST));
		moveActionAssociation.put(phoenix, new SpecialMoveArrow().setArrowType(StateArrow.Type.PHOENIX).addAction(arrowSound,0));
		moveActionAssociation.put(zapper, new SpecialMoveArrow().setArrowType(StateArrow.Type.ZAPPER).addAction(arrowSound,0));
		moveActionAssociation.put(doubleShot, new SpecialMoveRapidArrow(1));
		moveActionAssociation.put(tripleShot, new SpecialMoveRapidArrow(2));
		moveActionAssociation.put(shadowStitching, new SpecialMoveArrow().setArrowType(StateArrow.Type.SHADOW_STITCH));



		//槍
		moveActionAssociation.put(swing, SpecialActionComponents.swing());
		moveActionAssociation.put(grasshopper, SpecialActionComponents.grassHopper());
		moveActionAssociation.put(aiming, SpecialMoveBase.create(InvokeType.CHARGE)
				.addAction(ActionCharged.simpleChargedMelee(General.SPEAR,General.PUNCH).setChargeThreshold(15)));
//		moveActionAssociation.put(aiming, new SpecialMoveChargedAttack(InvokeType.CHARGE,General.SPEAR,General.PUNCH,General.SWORD).setChargeThreshold(15));
		moveActionAssociation.put(acupuncture,SpecialActionComponents.acupuncture());
		moveActionAssociation.put(armOfLight, SpecialActionComponents.armOfLight());


		//杖
		moveActionAssociation.put(grandSlam, SpecialActionComponents.grandslam());
		moveActionAssociation.put(earthDragon, SpecialActionComponents.earthDragon());
		moveActionAssociation.put(pulverizer, SpecialActionComponents.pulverizer());
		moveActionAssociation.put(rockCrasher, SpecialActionComponents.rockCrasher());
		moveActionAssociation.put(gonger, SpecialActionComponents.gonger());
		moveActionAssociation.put(skullCrash, SpecialActionComponents.skullCrasher());
		//剣
		moveActionAssociation.put(gust, SpecialActionComponents.gust());
		moveActionAssociation.put(hawkBlade, SpecialActionComponents.hawkBlade());//短剣でも
		moveActionAssociation.put(chargeBlade, SpecialActionComponents.chargeBlade());
		moveActionAssociation.put(smash, SpecialMoveBase.create(InvokeType.CHARGE)
				.addAction(ActionCharged.simpleChargedMelee(General.SWORD).setChargeThreshold(15)));
		moveActionAssociation.put(vandalize, SpecialActionComponents.vandalize());
		//斧

		moveActionAssociation.put(woodChopper, SpecialActionComponents.woodChopper());
		moveActionAssociation.put(fujiView, SpecialActionComponents.fujiView());
		moveActionAssociation.put(tomahawk, SpecialActionComponents.tomahawk());
		moveActionAssociation.put(skyDrive, SpecialActionComponents.skyDrive());
		moveActionAssociation.put(yoyo, SpecialActionComponents.yoyo());
		moveActionAssociation.put(firewoodChopper, SpecialActionComponents.firewoodChopper());
		//短剣

		moveActionAssociation.put(blitz, SpecialActionComponents.blitz());
		moveActionAssociation.put(bloodMary, SpecialActionComponents.bloodyMary());
		moveActionAssociation.put(stunner, SpecialActionComponents.stunner());
		moveActionAssociation.put(lightningThrust, SpecialActionComponents.lightningThrust());
		moveActionAssociation.put(knifeThrow, SpecialActionComponents.knifeThrow());

	}
	@Override
	protected void registerObjects() {

//		this.put(caleidoscope);
		this.put(smash);
		this.put(vandalize);
		this.put(chargeBlade);
		this.put(gust);

//		this.put(tomahawk);
		this.put(fujiView);
		this.put(skyDrive);
		this.put(woodChopper);
//		this.put(crossing);
		this.put(yoyo);
		this.put(firewoodChopper);

		this.put(aiming);
		this.put(acupuncture);
		this.put(swing);
		this.put(grasshopper);
		this.put(armOfLight);

		this.put(earthDragon);
		this.put(skullCrash);
		this.put(pulverizer);
		this.put(grandSlam);
		this.put(gonger);
		this.put(rockCrasher);

//		this.put(doubleShot);
//		this.put(tripleShot);
		this.put(zapper);
		this.put(exorcist);
		this.put(shadowStitching);
		this.put(phoenix);
//		this.put(arrowRain);
		this.put(quickChecker);

		this.put(knifeThrow);
		this.put(stunner);
		this.put(bloodMary);
		this.put(cutIn);
		this.put(blitz);
		this.put(lightningThrust);

		this.put(hawkBlade);

		this.put(airThrow);
		this.put(cyclone);
		this.put(callback);

	}

}
