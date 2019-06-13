package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import model.Effect;
import model.OptionalEffect;

import java.lang.reflect.Type;
import java.util.Map;

public class EffectSerializer implements JsonSerializer<Effect> {

    @Override
    public JsonElement serialize(Effect effect, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonEffect = new JsonObject();
        
        jsonEffect.addProperty("isGlobal", effect.isGlobal());

        final JsonElement effectCost = jsonSerializationContext.serialize(effect.getCost().toArray(new String[0]), String[].class);
        jsonEffect.add("cost", effectCost);

        Type mapType = new TypeToken<Map<String, Integer>>(){}.getType();

        final JsonElement damage = jsonSerializationContext.serialize(effect.getDamage(), mapType);
        jsonEffect.add("damage", damage);

        final JsonElement marks = jsonSerializationContext.serialize(effect.getMarks(), mapType);
        jsonEffect.add("marks", damage);

        final JsonElement jsonOptionalEffects = jsonSerializationContext.serialize(
                effect.getOptionalEffects().toArray(new OptionalEffect[0]), OptionalEffect[].class);
        jsonEffect.add("optionalEffects", jsonOptionalEffects);

        jsonEffect.addProperty("canMoveShooter", effect.isCanMoveShooter());
        jsonEffect.addProperty("numStepsShooter", effect.getNumStepsShooter());
        jsonEffect.addProperty("canMoveTarget", effect.isCanMoveTarget());
        jsonEffect.addProperty("numStepsTarget", effect.getNumStepsTarget());

        return jsonEffect;
    }
}
