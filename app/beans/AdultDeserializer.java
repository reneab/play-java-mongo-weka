package beans;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Utility class to be used by Gson deserializer to build an {@link Adult} object from JSON
 * @author René
 *
 */
public class AdultDeserializer implements JsonDeserializer<Adult> {

	@Override
	public Adult deserialize(JsonElement json, Type type,
	        JsonDeserializationContext context) throws JsonParseException {

	    JsonObject jobject = (JsonObject) json;

	    return new Adult(
	    		jobject.get("age").getAsInt(),
	    		jobject.get("workclass").getAsString(),
	    		jobject.get("fnlwgt").getAsInt(),
	    		jobject.get("education").getAsString(),
	    		jobject.get("education-num").getAsInt(),
	    		jobject.get("marital-status").getAsString(),
	    		jobject.get("occupation").getAsString(),
	    		jobject.get("relationship").getAsString(),
	    		jobject.get("race").getAsString(),
	    		jobject.get("sex").getAsString(),
	    		jobject.get("capital-gain").getAsInt(),
	    		jobject.get("capital-loss").getAsInt(),
	    		jobject.get("hours-per-week").getAsInt(),
	    		jobject.get("native-country").getAsString(),
	    		jobject.get("class").getAsString()
	    		);
	}
	
}
