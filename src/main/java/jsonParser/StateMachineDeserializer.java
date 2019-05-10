package jsonParser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import model.State;

import java.lang.reflect.Type;

public class StateMachineDeserializer implements JsonDeserializer<State> {

    @Override
    public State deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

    }
}
