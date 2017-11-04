package mods.hinasch.unsaga.core.potion;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import mods.hinasch.lib.world.XYZPos;
import mods.hinasch.unsaga.core.entity.State;
import mods.hinasch.unsaga.core.entity.StateProperty;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.nbt.NBTTagCompound;

public class StatePropertyPotion extends StateProperty{

	public StatePropertyPotion(String name) {
		super(name);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public State createState(){
		return new StatePotion();
	}

	public static class StatePotion extends State{

		public StatePotion() {
			super(false);
			// TODO 自動生成されたコンストラクター・スタブ
		}

		public Optional<XYZPos> stoppedPos = Optional.empty();
		public Set<EntityAITasks.EntityAITaskEntry> backup = Sets.newHashSet();
		public EntityAIBase addedTask;
		public boolean hasAddedFearTask = false;

		public boolean isHasAddedFearTask() {
			return hasAddedFearTask;
		}

		public void setHasAddedFearTask(boolean hasAddedFearTask) {
			this.hasAddedFearTask = hasAddedFearTask;
		}

		protected void backUpTask(EntityCreature c){
			backup.addAll(c.tasks.taskEntries);
		}

		public void addTask(EntityCreature c,EntityAIBase ai){
			c.tasks.addTask(0, ai);
			this.addedTask = ai;
		}
		public void removeTasks(EntityCreature c){
			this.backUpTask(c);
			List<EntityAIBase> list = Lists.newArrayList();
			for(EntityAITasks.EntityAITaskEntry task:c.tasks.taskEntries){
				list.add(task.action);
			}
			for(EntityAIBase ai:list){
				c.tasks.removeTask(ai);
			}
		}
		public void restoreTasks(EntityCreature c){
			for(EntityAITasks.EntityAITaskEntry  entry:backup){
				c.tasks.addTask(entry.priority, entry.action);
			}
			c.tasks.removeTask(this.addedTask);
		}

		public Optional<XYZPos> getStoppedPos(){
			return this.stoppedPos;
		}
		public void setStoppedPos(XYZPos pos){
			if(pos!=null){
				this.stoppedPos = Optional.of(pos);
			}else{
				this.stoppedPos = Optional.empty();
			}
		}

		public NBTTagCompound writeNBT(NBTTagCompound tag){
			if(this.stoppedPos.isPresent()){
				this.stoppedPos.get().writeToNBT(tag);
			}
			return tag;
		}

		public NBTTagCompound readNBT(NBTTagCompound tag){
			this.setStoppedPos(XYZPos.readFromNBT(tag));
			return tag;
		}
	}
}
