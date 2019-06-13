package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.PowerUpCard;
import model.clientModel.*;

import java.lang.reflect.Type;

public class SemplifiedPlayerDeserializerConModifiche implements JsonDeserializer<SemplifiedPlayer> {

    private final SemplifiedGame game;

    public SemplifiedPlayerDeserializerConModifiche(SemplifiedGame game) {
        this.game = game;
    }

    @Override
    public SemplifiedPlayer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonPlayer = jsonElement.getAsJsonObject();
        if (!jsonPlayer.has("name") || !jsonPlayer.has("remainingMoves") || !jsonPlayer.has("numWeaponCard")
                || !jsonPlayer.has("weaponCards") || !jsonPlayer.has("numPowerUps") || !jsonPlayer.has("powerUps")
                || !jsonPlayer.has("tile") || !jsonPlayer.has("playerBoard"))
            throw new JsonParseException("This json player doesnt have a name");

        SemplifiedPlayer player = game.getPlayerByName(jsonPlayer.get("name").getAsString());
        //simple assignment
        player.setRemainingMoves(jsonPlayer.get("remainingMoves").getAsInt());
        player.setNumWeaponCard(jsonPlayer.get("numWeaponCard").getAsInt());
        player.setNumPowerUps(jsonPlayer.get("numPowerUps").getAsInt());
        player.setTile(jsonPlayer.get("tile").getAsInt());

        SemplifiedWeaponCard[] weaponCards = jsonDeserializationContext.deserialize(
                jsonPlayer.getAsJsonArray("weaponCards"), SemplifiedWeaponCard[].class);
        player.setWeaponCards(weaponCards);

        //this could give problems on the gui for order
        PowerUpCard[] powerUpCards = jsonDeserializationContext.deserialize(
                jsonPlayer.getAsJsonArray("powerUps"), PowerUpCard[].class);
        player.setPowerUpCards(powerUpCards);

        JsonObject jsonPlayerBoard = jsonPlayer.get("playerBoard").getAsJsonObject();

        if (!jsonPlayerBoard.has("damageTokens") || !jsonPlayerBoard.has("marksTokens")
                || !jsonPlayerBoard.has("killValue") || !jsonPlayerBoard.has("numBlueAmmo")
                || !jsonPlayerBoard.has("numRedAmmo") || !jsonPlayerBoard.has("numYellowAmmo"))
            throw new JsonParseException("Player board json is missing some fields");

        SemplifiedPlayerBoard playerBoard = player.getPlayerBoard();

        SemplifiedBloodToken[] damageTokens = jsonDeserializationContext.deserialize(
                jsonPlayerBoard.getAsJsonArray("damageTokens"), SemplifiedBloodToken[].class);
        //playerBoard.setDamageTokens(damageTokens);

        SemplifiedBloodToken[] marksTokens = jsonDeserializationContext.deserialize(
                jsonPlayerBoard.getAsJsonArray("marksTokens"), SemplifiedBloodToken[].class);
        playerBoard.setMarksTokens(marksTokens);

        Integer[] killValue = jsonDeserializationContext.deserialize(
                jsonPlayerBoard.getAsJsonArray("killValue"), Integer[].class);
        playerBoard.setKillValue(killValue);

        playerBoard.setNumBlueAmmo(jsonPlayerBoard.get("numBlueAmmo").getAsInt());
        playerBoard.setNumRedAmmo(jsonPlayerBoard.get("numRedAmmo").getAsInt());
        playerBoard.setNumYellowAmmo(jsonPlayerBoard.get("numYellowAmmo").getAsInt());
        return null;
    }
}