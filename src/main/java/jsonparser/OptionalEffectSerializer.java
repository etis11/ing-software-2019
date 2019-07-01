package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import model.OptionalEffect;

import java.lang.reflect.Type;
import java.util.Map;

public class OptionalEffectSerializer implements JsonSerializer<OptionalEffect> {

    @Override
    public JsonElement serialize(OptionalEffect optionalEffect, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonOptionalEffect = new JsonObject();

        final JsonElement effectCost = jsonSerializationContext.serialize(optionalEffect.getCost().toArray(new String[0]), String[].class);
        jsonOptionalEffect.add("cost", effectCost);


        Type mapType = new TypeToken<Map<String, Integer>>(){}.getType();

        final JsonElement additionalDamage = jsonSerializationContext.serialize(optionalEffect.getAdditionalDamage(), mapType);
        jsonOptionalEffect.add("additionalDamage", additionalDamage);

        final JsonElement additionalMarks = jsonSerializationContext.serialize(optionalEffect.getAdditionalMarks(), mapType);
        jsonOptionalEffect.add("additionalMarks", additionalMarks);

        jsonOptionalEffect.addProperty("canMoveShooter", optionalEffect.canShooterMove());
        jsonOptionalEffect.addProperty("canMoveTarget", optionalEffect.canTargetMove());
        jsonOptionalEffect.addProperty("shooterSteps", optionalEffect.getShooterSteps());
        jsonOptionalEffect.addProperty("shooterAlreadyMoved", optionalEffect.hasShooterAlreadyMoved());
        jsonOptionalEffect.addProperty("targetAlreadyMoved", optionalEffect.isTargetAlreadyMoved());


        return jsonOptionalEffect;
    }
}
