package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.SemplifiedBloodToken;

import java.lang.reflect.Type;

public class BloodTokenSerializer implements JsonSerializer<SemplifiedBloodToken> {
    @Override
    public JsonElement serialize(SemplifiedBloodToken semplifiedBloodToken, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonBlood = new JsonObject();
        jsonBlood.addProperty("owner", semplifiedBloodToken.getOwner().getName());

        return jsonBlood;
    }
}
