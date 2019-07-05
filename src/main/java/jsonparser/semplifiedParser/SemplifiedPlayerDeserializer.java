package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.PowerUpCard;
import model.clientModel.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class SemplifiedPlayerDeserializer implements JsonDeserializer<SemplifiedPlayer> {

    private static final ThreadLocal<Map<String, SemplifiedPlayer>> cache = new ThreadLocal<Map<String,SemplifiedPlayer>>() {
        @Override
        protected Map<String, SemplifiedPlayer> initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public SemplifiedPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

//        if (!jsonPlayer.has("name") || !jsonPlayer.has("remainingMoves") || !jsonPlayer.has("numWeaponCard")
//                || !jsonPlayer.has("weaponCards") || !jsonPlayer.has("numPowerUps") || !jsonPlayer.has("powerUps")
//                || !jsonPlayer.has("tile") || !jsonPlayer.has("playerBoard"))
//            throw new JsonParseException("This json player doesnt have a name");

        SemplifiedPlayer player;
        if (jsonElement.isJsonPrimitive()) {
            final JsonPrimitive primitive = jsonElement.getAsJsonPrimitive();
            return getOrCreate(primitive.getAsString());
        }

        JsonObject jsonPlayer = jsonElement.getAsJsonObject();

        player = getOrCreate(jsonPlayer.get("name").getAsString());

        if (jsonPlayer.has("remainingMoves"))
            player.setRemainingMoves(jsonPlayer.get("remainingMoves").getAsInt());
        if (jsonPlayer.has("numWeaponCard"))
            player.setNumWeaponCard(jsonPlayer.get("numWeaponCard").getAsInt());
        if (jsonPlayer.has("numPowerUps"))
            player.setNumPowerUps(jsonPlayer.get("numPowerUps").getAsInt());
        if (jsonPlayer.has("tile"))
            player.setTile(jsonPlayer.get("tile").getAsInt());

        if (jsonPlayer.has("weaponCards")){
            SemplifiedWeaponCard[] weaponCards = jsonDeserializationContext.deserialize(
                    jsonPlayer.getAsJsonArray("weaponCards"), SemplifiedWeaponCard[].class);
            player.setWeaponCards(weaponCards);
        }

        //this could give problems on the view.gui for order
        if (jsonPlayer.has("powerUps")){
            PowerUpCard[] powerUpCards = jsonDeserializationContext.deserialize(
                    jsonPlayer.getAsJsonArray("powerUps"), PowerUpCard[].class);
            player.setPowerUpCards(powerUpCards);
        }

        if (jsonPlayer.has("playerBoard")){
            SemplifiedPlayerBoard playerBoard = jsonDeserializationContext.deserialize(
                    jsonPlayer.get("playerBoard").getAsJsonObject(), SemplifiedPlayerBoard.class);
            player.setPlayerBoard(playerBoard);

        }

        return player;
    }

    private SemplifiedPlayer getOrCreate(final String name) {
        SemplifiedPlayer player = cache.get().get(name);
        if (player == null) {
            player = new SemplifiedPlayer();
            player.setName(name);
            cache.get().put(name, player);
        }
        return player;
    }

    public void resetMap(){
        cache.get().clear();
    }
}
