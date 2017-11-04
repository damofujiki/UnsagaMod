package mods.hinasch.unsagaadditional.client;

import mods.hinasch.unsagaadditional.net.CommonProxy;

public class ClientProxy extends CommonProxy{

	ModelAttacher modelAttacher = new ModelAttacher();

	@Override
	public void registerRenderers() {
		// TODO 自動生成されたメソッド・スタブ
		this.modelAttacher.register();
	}
}
