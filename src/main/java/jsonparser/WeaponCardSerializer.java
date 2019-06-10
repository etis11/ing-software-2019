package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Effect;
import model.Match;
import model.Player;
import model.WeaponCard;

import java.lang.reflect.Type;

public class WeaponCardSerializer implements JsonSerializer<WeaponCard> {

    private final Match match;
    private Player currentPlayer;
    private boolean playerMode;
    private boolean tileMode;

    public WeaponCardSerializer(Match m){
        this.match = m;
        playerMode = true;
        tileMode = false;
    }

    public void setCurrentPlayer(Player p){
        currentPlayer = p;
    }

    public void setPlayerModeTrue(){
        playerMode = true;
        tileMode = false;
    }

    public void setTileModeTrue(){
        playerMode = false;
        tileMode = true;
    }


    @Override
    /**
     * This method serialize a weapon. A weapon can be seen only if it's not loaded or if the player is the same that
     * is requesting them.
     */
    public JsonElement serialize(WeaponCard weaponCard, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonWeaponCard = new JsonObject();
        if (playerMode){
            if (currentPlayer.getWeapons().contains(weaponCard) || !weaponCard.isLoaded()){
                serializeWeaponCard(weaponCard, jsonWeaponCard, jsonSerializationContext);
            }
        }
        else{
            serializeWeaponCard(weaponCard, jsonWeaponCard, jsonSerializationContext);
        }

        return jsonWeaponCard;

    }

    public void serializeWeaponCard(WeaponCard weaponCard, JsonObject jsonWeaponCard, JsonSerializationContext jsonSerializationContext){
        jsonWeaponCard.addProperty("name", weaponCard.getName());

        final JsonElement weaponCost= jsonSerializationContext.serialize(weaponCard.getReloadCost(), String[].class);
        jsonWeaponCard.add("cost", weaponCost);

        jsonWeaponCard.addProperty("loaded", weaponCard.isLoaded());

        final JsonElement baseEffects= jsonSerializationContext.serialize(weaponCard.getBaseEffect(), Effect[].class);
        jsonWeaponCard.add("baseEffect", baseEffects);

        final JsonElement advancedEffects= jsonSerializationContext.serialize(weaponCard.getAdvancedEffect(), Effect[].class);
        jsonWeaponCard.add("baseEffect", advancedEffects);
    }
}
