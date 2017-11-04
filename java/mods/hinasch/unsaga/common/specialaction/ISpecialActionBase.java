package mods.hinasch.unsaga.common.specialaction;

import java.util.function.Consumer;

import net.minecraft.util.EnumActionResult;

public interface ISpecialActionBase<T extends IActionPerformer> {

	public EnumActionResult perform(T context);
	public boolean isBenefical();
	public Consumer<T> getPrePerform();
}
