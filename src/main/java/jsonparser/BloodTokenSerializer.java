package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.BloodToken;

import java.lang.reflect.Type;

public class BloodTokenSerializer implements JsonSerializer<BloodToken> {
    @Override
    public JsonElement serialize(BloodToken bloodToken, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonBlood = new JsonObject();
        jsonBlood.addProperty("owner", bloodToken.getOwner().getName());

        return jsonBlood;
    }
}
