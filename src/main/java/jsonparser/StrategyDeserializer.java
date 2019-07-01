package jsonparser;

import com.google.gson.*;
import model.*;

import java.lang.reflect.Type;

/**
 * deserializes the field strategy in the weapon json
 */
public class StrategyDeserializer implements JsonDeserializer<TargetStrategy> {


    private Match match;

    public StrategyDeserializer(Match match){
        this.match = match;
    }

    @Override
    public TargetStrategy deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        AbstractTargetStrategy toReturn = null;
        JsonObject strategyObject = jsonElement.getAsJsonObject();
        if (!strategyObject.has("type")) throw new JsonParseException("A strategy should have a type filed");
        Integer param = null;
        if (strategyObject.has("param") && !strategyObject.get("param").isJsonNull())
            param = strategyObject.get("param").getAsInt();
        switch (strategyObject.get("type").getAsString()) {
            case "SeeStrategy":
                toReturn = new SeeStrategy(match);
                break;
            case "FixedDistanceStrategy":
                if (param == null)
                    param = 0;
                toReturn = new FixedDistanceStrategy(param, match);
                break;
            case "RoomStrategy":
                toReturn = new RoomStrategy(match);
                break;
            case "VortexCannonStrategy":
                toReturn = new VortexCannonStrategy(match);
                break;
            case "DontSeeStrategy":
                toReturn = new DontSeeStrategy(match);
                break;
            case "FlameThrowerStrategy":
                toReturn = new FlameThrowerStrategy();
                break;
            case "BBQStrategy":
                toReturn = new BBQStrategy();
                break;
            case "TractorBeamStrategy":
                toReturn = new TractorBeamStrategy(match);
                break;
            case "AdjacentStrategy":
                toReturn = new AdjacentStrategy(match);
                break;
            case "LaserRifleStrategy":
                toReturn = new IgnoreWallStrategy();
                break;
            case "MeleeStrategy":
                toReturn = new MeleeStrategy( match);
                break;
            case "AdjacentDifferentTilesStrategy":
                toReturn = new AdjacentDifferentTilesStrategy(match);
                break;
            case "HellionStrategy":
                toReturn = new HellionStrategy(match);
                break;
            case "TorpedineStrategy":
                toReturn = new TorpedineStrategy(match);
                break;
            default:
                break;
        }
        return toReturn;
    }
}
