package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.BloodToken;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedPlayer;

import java.lang.reflect.Type;

public class SemplifiedBloodTokenDeserializer implements JsonDeserializer<SemplifiedBloodToken> {

    private final SemplifiedGame game;

    public SemplifiedBloodTokenDeserializer(SemplifiedGame game){
        this.game = game;
    }

    @Override
    public SemplifiedBloodToken deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonToken = jsonElement.getAsJsonObject();

        SemplifiedPlayer p = game.getPlayerByName(jsonToken.get("owner").getAsString());
        return new SemplifiedBloodToken(p);
    }
}
