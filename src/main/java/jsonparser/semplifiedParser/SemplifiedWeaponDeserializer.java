package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.Effect;
import model.clientModel.SemplifiedEffect;
import model.clientModel.SemplifiedWeaponCard;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;

public class SemplifiedWeaponDeserializer implements JsonDeserializer<SemplifiedWeaponCard> {
    @Override
    public SemplifiedWeaponCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        System.out.println("STO DESERIALIZZANDO L'ARMA");
        JsonObject jsonWeaponCard = jsonElement.getAsJsonObject();

        final SemplifiedWeaponCard weaponCard = new SemplifiedWeaponCard();

        if(jsonWeaponCard.has("name"))
            weaponCard.setName(jsonWeaponCard.get("name").getAsString());

        if(jsonWeaponCard.has("cost")){
            String[] cost = jsonDeserializationContext.deserialize(jsonWeaponCard.get("cost"), String[].class);
            weaponCard.setCost(new LinkedList<>(Arrays.asList(cost)));
        }

        if(jsonWeaponCard.has("loaded"))
            weaponCard.setLoaded(jsonWeaponCard.get("loaded").getAsBoolean());

        if(jsonWeaponCard.has("baseEffect")){
            SemplifiedEffect[] effects = jsonDeserializationContext.deserialize(jsonWeaponCard.get("baseEffect"), SemplifiedEffect[].class);
            weaponCard.setBaseEffect(new LinkedList<>(Arrays.asList(effects)));
        }

        if(jsonWeaponCard.has("advancedEffect")){
            SemplifiedEffect[] effects = jsonDeserializationContext.deserialize(jsonWeaponCard.get("advancedEffect"), SemplifiedEffect[].class);
            weaponCard.setBaseEffect(new LinkedList<>(Arrays.asList(effects)));
        }

        return weaponCard;
    }
}
