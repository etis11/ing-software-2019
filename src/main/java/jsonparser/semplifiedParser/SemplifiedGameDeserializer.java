package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedTile;

import java.lang.reflect.Type;

public class SemplifiedGameDeserializer implements JsonDeserializer<SemplifiedGame> {

    private final SemplifiedGame game;

    public SemplifiedGameDeserializer(SemplifiedGame game) {
        this.game = game;
    }

    @Override
    public SemplifiedGame deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonGame = jsonElement.getAsJsonObject();
        String message = null;

        if (jsonGame.has("message"))
            message = jsonDeserializationContext.deserialize(jsonGame.get("message"), String.class);

        game.setMessage(message);

        if (jsonGame.has("changedPlayers")) {
            SemplifiedPlayer[] players = jsonDeserializationContext.deserialize(jsonGame.get("changedPlayers").getAsJsonArray(), SemplifiedPlayer[].class);
        }

        if (jsonGame.has("changedTiles")) {
            SemplifiedTile[] changedTiles = jsonDeserializationContext.deserialize(jsonGame.get("changedTiles").getAsJsonArray(), SemplifiedTile[].class);
        }

        return null;
    }
}

