package connection;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

public class GsonHelper{
	public static Type mapType = new TypeToken<Map<String, String>>(){}.getType();
	public static final String TYPE = "type";
	public static final String DATEFORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

	private static Gson CreateGson() {
		GsonBuilder builder = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy(){

			public boolean shouldSkipField(FieldAttributes fieldAttributes){
				final Expose expose = fieldAttributes.getAnnotation(Expose.class);
				return expose != null && !expose.serialize();
			}


			public boolean shouldSkipClass(Class<?> aClass){
				return false;
			}
		}).addDeserializationExclusionStrategy(new ExclusionStrategy(){

			public boolean shouldSkipField(FieldAttributes fieldAttributes){
				final Expose expose = fieldAttributes.getAnnotation(Expose.class);
				return expose != null && !expose.deserialize();
			}

			public boolean shouldSkipClass(Class<?> aClass){
				return false;
			}
		});
		builder.setDateFormat(DATEFORMAT);

		return builder.create();
	}

	public static JsonObject convertStringToJson(String value) {
		JsonElement element = GsonHelper.CreateGson().fromJson(value, JsonElement.class);
		return element.getAsJsonObject();
	}

	public static String serialize(Object obj) {
		return GsonHelper.CreateGson().toJson(obj);
	}

	public static Object deserialize(String value, Class<?> classType) {	
		return GsonHelper.CreateGson().fromJson(value, classType);
	}

	public static Object deserialize(String value, Type type) {
		return GsonHelper.CreateGson().fromJson(value, type);
	}
}