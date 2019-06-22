package jsonparser;

import com.google.gson.*;
import javafx.scene.paint.Color;
import model.PowerUpCard;
import model.PowerUpType;

import java.lang.reflect.Type;

public class PowerUpDeserializer implements JsonDeserializer<PowerUpCard> {
    @Override
    public PowerUpCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonPowerUp = jsonElement.getAsJsonObject();

        if (!jsonPowerUp.has("Color") || !jsonPowerUp.has("PowerUpType"))
            throw new JsonParseException("Error, wrong json for the power up");

        String color = jsonPowerUp.get("Color").getAsString();
        Color powerUpColor;
        switch (color){
            case "RED":
                powerUpColor = Color.RED;
                break;
            case "BLUE":
                powerUpColor = Color.BLUE;
                break;
            case "YELLOW":
                powerUpColor = Color.YELLOW;
                break;
            default: throw  new JsonParseException("There isnt such a color in the power ups");
        }
        
        String powerUptype = jsonPowerUp.get("PowerUpType").getAsString();
        PowerUpType ptype;
        switch (powerUptype){
            case "NEWTON":
                ptype = PowerUpType.NEWTON;
                break;
            case "TELEPORTER":
                ptype = PowerUpType.TELEPORTER;
                break;
            case "TARGETINGSCOPE":
                ptype = PowerUpType.TARGETING_SCOPE;
                break;
            case "TAGBACKGRANADE":
                ptype = PowerUpType.TAGBACK_GRANADE;
                break;
            default: throw  new JsonParseException("There isnt such a power up type in the power ups");
        }

        return new PowerUpCard(powerUpColor, ptype);

    }
}
