package mods.hinasch.lib.misc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import mods.hinasch.lib.core.HSLib;
import mods.hinasch.lib.misc.JsonHelper.IJsonApply;
import mods.hinasch.lib.misc.JsonHelper.IJsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class JsonHelper<T extends IJsonApply, V extends IJsonParser> {


	Function<JsonObject,V> parserMaker;
	Function<V,T> objectGetter;
	InputStream in;

	public JsonHelper(ResourceLocation res,Function<JsonObject,V> parserMaker,Function<V,T> objectMaker){

		this.parserMaker = parserMaker;
		this.objectGetter = objectMaker;
		try {

			in = Minecraft.getMinecraft().getResourceManager().getResource(res).getInputStream();
			HSLib.logger.trace("JsonParser", res+" is successfully loaded.");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public void parse(){
		Gson gson = new Gson();
		BufferedReader  reader = new BufferedReader (new InputStreamReader(in));
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		JsonArray ja = je.getAsJsonArray();
		for(Iterator<JsonElement> ite=ja.iterator();ite.hasNext();){
			JsonObject obj = ite.next().getAsJsonObject();
			V parser = this.parserMaker.apply(obj);
			parser.parse(obj);
			T m = this.objectGetter.apply(parser);
			if(m!=null){
				m.applyJson(parser);
				HSLib.logger.trace(this.getClass().getName(), parser.toString()+" is successfully applied.");
			}else{
				HSLib.logger.trace(this.getClass().getName(), parser.toString()+" is not found.");
			}
		}
	}


	public static interface IJsonParser{

		public void parse(JsonObject jo);
	}
	public static interface IJsonApply<T extends IJsonParser>{
		public void applyJson(T parser);
	}
}
