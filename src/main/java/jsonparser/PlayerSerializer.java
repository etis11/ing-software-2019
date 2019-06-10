package jsonparser;

import com.google.gson.*;
import model.Player;
import model.PlayerBoard;
import model.PowerUpCard;
import model.WeaponCard;

import java.lang.reflect.Type;

public class PlayerSerializer implements JsonSerializer<Player> {
    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", player.getName());
        jsonObject.addProperty("remainingMoves", player.getRemainingMoves());

        final JsonElement weaponCards = jsonSerializationContext.serialize(player.getWeapons(), WeaponCard[].class);
        jsonObject.add("weaponCards", weaponCards);

        final JsonElement powerUps = jsonSerializationContext.serialize(player.getPowerUps(), PowerUpCard[].class);
        jsonObject.add("powerUps", powerUps);

        jsonObject.addProperty("tile", player.getTile().getID());

        final JsonElement playerBoard = jsonSerializationContext.serialize(player.getPlayerBoard(), PlayerBoard.class);
        jsonObject.add("playerBoard", playerBoard);

        return jsonObject;
    }
}
