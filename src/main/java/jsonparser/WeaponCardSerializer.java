package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Effect;
import model.Player;
import model.WeaponCard;

import java.lang.reflect.Type;

public class WeaponCardSerializer implements JsonSerializer<WeaponCard> {

    @Override
    /**
     * This method serialize a weapon. A weapon can be seen only if it's not loaded or if the player is the same that
     * is requesting them.
     */
    public JsonElement serialize(WeaponCard weaponCard, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonWeaponCard = new JsonObject();
        serializeWeaponCard(weaponCard, jsonWeaponCard, jsonSerializationContext);
        return jsonWeaponCard;

    }

    public void serializeWeaponCard(WeaponCard weaponCard, JsonObject jsonWeaponCard, JsonSerializationContext jsonSerializationContext) {
        jsonWeaponCard.addProperty("name", weaponCard.getName());

        final JsonElement weaponCost = jsonSerializationContext.serialize(weaponCard.getReloadCost().toArray(new String[0]), String[].class);
        jsonWeaponCard.add("cost", weaponCost);

        jsonWeaponCard.addProperty("loaded", weaponCard.isLoaded());

        if (weaponCard.getBaseEffect() != null){
            final JsonElement baseEffects = jsonSerializationContext.serialize(
                    weaponCard.getBaseEffect().toArray(new Effect[0]), Effect[].class);
            jsonWeaponCard.add("baseEffect", baseEffects);
        }

        if(weaponCard.getAdvancedEffect()!= null){
            final JsonElement advancedEffects = jsonSerializationContext.serialize(
                    weaponCard.getAdvancedEffect().toArray(new Effect[0]), Effect[].class);
            jsonWeaponCard.add("advancedEffect", advancedEffects);
        }
    }
}
