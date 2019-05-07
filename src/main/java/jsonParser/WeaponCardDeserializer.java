package jsonParser;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import model.WeaponCard;

import java.lang.reflect.Type;
import java.util.List;

public class WeaponCardDeserializer implements JsonDeserializer<WeaponCard> {

    @Override
    public WeaponCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    public List<WeaponCard> parsingCards (){
        return null;
    }

}
