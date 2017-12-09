package mods.hinasch.unsaga.core.client;

import mods.hinasch.lib.util.HSLibs;
import mods.hinasch.unsaga.UnsagaMod;
import mods.hinasch.unsaga.core.client.event.EventMouseClicked;
import mods.hinasch.unsaga.core.client.event.EventRenderLivingEffect;
import mods.hinasch.unsaga.core.client.event.EventRenderGameOverlay;
import mods.hinasch.unsaga.core.client.render.entity.RenderEntityChest;
import mods.hinasch.unsaga.core.client.render.entity.RenderFireWall;
import mods.hinasch.unsaga.core.client.render.entity.RenderRuffleTree;
import mods.hinasch.unsaga.core.client.render.entity.RenderShadow;
import mods.hinasch.unsaga.core.client.render.entity.RenderSignalTree;
import mods.hinasch.unsaga.core.client.render.entity.RenderStormEater;
import mods.hinasch.unsaga.core.client.render.entity.RenderTreasureSlime;
import mods.hinasch.unsaga.core.client.render.projectile.RenderBeam;
import mods.hinasch.unsaga.core.client.render.projectile.RenderBullet;
import mods.hinasch.unsaga.core.client.render.projectile.RenderCustomArrow;
import mods.hinasch.unsaga.core.client.render.projectile.RenderThrowable;
import mods.hinasch.unsaga.core.client.render.projectile.RenderThrowableItemNew;
import mods.hinasch.unsaga.core.entity.mob.EntityPoisonEater;
import mods.hinasch.unsaga.core.entity.mob.EntityRuffleTree;
import mods.hinasch.unsaga.core.entity.mob.EntitySignalTree;
import mods.hinasch.unsaga.core.entity.mob.EntityStormEater;
import mods.hinasch.unsaga.core.entity.mob.EntityTreasureSlime;
import mods.hinasch.unsaga.core.entity.passive.EntityBeam;
import mods.hinasch.unsaga.core.entity.passive.EntityFireWall;
import mods.hinasch.unsaga.core.entity.passive.EntityShadow;
import mods.hinasch.unsaga.core.entity.passive.EntityUnsagaChestNew;
import mods.hinasch.unsaga.core.entity.projectile.EntityBlaster;
import mods.hinasch.unsaga.core.entity.projectile.EntityBoulder;
import mods.hinasch.unsaga.core.entity.projectile.EntityBubbleBlow;
import mods.hinasch.unsaga.core.entity.projectile.EntityBullet;
import mods.hinasch.unsaga.core.entity.projectile.EntityCustomArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityFireArrow;
import mods.hinasch.unsaga.core.entity.projectile.EntityFlyingAxe;
import mods.hinasch.unsaga.core.entity.projectile.EntityIceNeedle;
import mods.hinasch.unsaga.core.entity.projectile.EntitySolutionLiquid;
import mods.hinasch.unsaga.core.entity.projectile.EntityThrowingKnife;
import mods.hinasch.unsaga.core.net.CommonProxy;
import mods.hinasch.unsaga.element.EventRenderText;
import mods.hinasch.unsaga.init.UnsagaItemRegistry;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

	ModelAttacher modelAttacher;
	mods.hinasch.unsagamagic.client.ModelAttacher modelAttacherMagic;
	KeyBindingUnsaga keyBindings;
	BlockPos debugPos = BlockPos.ORIGIN;

	@Override
	public void registerRenderers() {
		// TODO 自動生成されたメソッド・スタブ
		this.modelAttacher = new ModelAttacher();
		this.modelAttacher.register();
		this.modelAttacherMagic = new mods.hinasch.unsagamagic.client.ModelAttacher();
		this.modelAttacherMagic.register();





//		this.keyBindings = new KeyBindingUnsaga();
//		HSLibs.registerEvent(this.keyBindings);

//		HSLibs.registerEvent(new EvnetMouseClicked());

	}

	@Override
	public BlockPos getDebugPos(){
		return this.debugPos;
	}
	@Override
	public void setDebugPos(BlockPos pos){
		this.debugPos = pos;
	}
	@Override
	public void registerKeyHandlers() {
		this.keyBindings = new KeyBindingUnsaga();
		HSLibs.registerEvent(this.keyBindings);

	}

	@Override
	public void registerEvents(){
		HSLibs.registerEvent(new EventRenderText());
		HSLibs.registerEvent(EventRenderGameOverlay.RenderEnemyStatus.getEvent());
		HSLibs.registerEvent(EventRenderGameOverlay.RenderPlayerStatus.getEvent());
		HSLibs.registerEvent(new EventRenderLivingEffect());
		HSLibs.registerEvent(new EventMouseClicked());
	}
	@Override
	public KeyBindingUnsaga getKeyBindings(){
		return this.keyBindings;
	}
	@Override
	public void registerEntityRenderers(){
		ItemStack axe = UnsagaItemRegistry.instance().getItemStack(UnsagaItemRegistry.instance().axe, UnsagaMod.core.materialsNew.iron, 0);
		RenderingRegistry.registerEntityRenderingHandler(EntityFlyingAxe.class, manager ->new RenderThrowableItemNew(manager,axe));
		RenderingRegistry.registerEntityRenderingHandler(EntityCustomArrow.class, manager ->new RenderCustomArrow(manager));
//		RenderingRegistry.registerEntityRenderingHandler(EntityDamageableItem.class, manager ->new RenderItemAttackable(manager,Minecraft.getMinecraft().getRenderItem()));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoulder.class, manager -> new RenderThrowable("textures/entity/projectiles/boulder.png",manager));
//		RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, manager -> new RenderThrowableItem<EntityFireArrow>(manager,1.0F,Items.FIRE_CHARGE,0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySolutionLiquid.class, manager -> new RenderThrowable("textures/entity/projectiles/slimeball.png",manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlaster.class, manager -> new RenderThrowable("textures/entity/projectiles/blaster.png",manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBubbleBlow.class, manager -> new RenderThrowable("textures/entity/projectiles/bubble.png",manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityShadow.class, manager -> new RenderShadow(manager,1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTreasureSlime.class, manager -> new RenderTreasureSlime(manager, new ModelSlime(16), 0.25F));
		RenderingRegistry.registerEntityRenderingHandler(EntityUnsagaChestNew.class, manager -> new RenderEntityChest(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, manager -> new RenderBullet(manager, 1.0F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRuffleTree.class, manager -> new RenderRuffleTree(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, manager -> new RenderBeam(manager));
		ItemStack knife = UnsagaItemRegistry.instance().getItemStack(UnsagaItemRegistry.instance().knife, UnsagaMod.core.materialsNew.iron, 0);
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowingKnife.class, manager -> new RenderThrowableItemNew(manager,knife));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireWall.class, manager -> new RenderFireWall(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntitySignalTree.class, manager -> new RenderSignalTree(manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityIceNeedle.class, manager -> new RenderThrowable("textures/entity/projectiles/needle.png",manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireArrow.class, manager -> new RenderThrowable("textures/entity/projectiles/fireball.png",manager));
		RenderingRegistry.registerEntityRenderingHandler(EntityStormEater.class, manager -> new RenderStormEater(manager,EntityStormEater.class));
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonEater.class, manager -> new RenderStormEater(manager,EntityPoisonEater.class));
	}
}
