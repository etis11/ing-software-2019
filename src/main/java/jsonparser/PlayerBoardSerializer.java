package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.BloodToken;
import model.PlayerBoard;

import java.lang.reflect.Type;

public class PlayerBoardSerializer  implements JsonSerializer<PlayerBoard> {


    @Override
    public JsonElement serialize(PlayerBoard playerBoard, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonPlayerBoard = new JsonObject();

        final JsonElement damageTokens = jsonSerializationContext.serialize(playerBoard.getDamageTokens(), BloodToken[].class);
        jsonPlayerBoard.add("damageTokens", damageTokens);

        final JsonElement marksTokens = jsonSerializationContext.serialize(playerBoard.getMarks(), BloodToken[].class);
        jsonPlayerBoard.add("marksTokens", marksTokens);

        final JsonElement killValue = jsonSerializationContext.serialize(playerBoard.getKillValue(), Integer[].class);
        jsonPlayerBoard.add("killValue", killValue);

        jsonPlayerBoard.addProperty("numBlueAmmo", playerBoard.getLoader().getNumBlueAmmo());
        jsonPlayerBoard.addProperty("numRedAmmo", playerBoard.getLoader().getNumRedAmmo());
        jsonPlayerBoard.addProperty("numYellowAmmo", playerBoard.getLoader().getNumYellowAmmo());

        return jsonPlayerBoard;
    }
}
