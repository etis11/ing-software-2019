package jsonparser.semplifiedParser;

import com.google.gson.*;
import model.AmmoCard;
import model.clientModel.SemplifiedPlayer;
import model.clientModel.SemplifiedTile;
import model.clientModel.SemplifiedWeaponCard;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;

public class SemplifiedTileDeserializer  implements JsonDeserializer<SemplifiedTile> {
    @Override
    public SemplifiedTile deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {


        JsonObject jsonTile = jsonElement.getAsJsonObject();
        SemplifiedTile tile = new SemplifiedTile();

        if(jsonTile.has("id"))
            tile.setId(jsonTile.get("id").getAsInt());

        if(jsonTile.has("ammoTile"))
            tile.setAmmoTile(jsonTile.get("ammoTile").getAsBoolean());

        if(jsonTile.has("weaponTile"))
            tile.setAmmoTile(jsonTile.get("weaponTile").getAsBoolean());

        if(jsonTile.has("players")){
            SemplifiedPlayer[] players = jsonDeserializationContext.deserialize(jsonTile.get("players").getAsJsonArray(), SemplifiedPlayer[].class);
            tile.setPlayers(new LinkedList<>(Arrays.asList(players)));
        }

        if (jsonTile.has("ammoCard"))
            tile.setAmmoCard(jsonDeserializationContext.deserialize(jsonTile.get("ammoCard").getAsJsonObject(), AmmoCard.class));

        if(jsonTile.has("weapons")){
            SemplifiedWeaponCard[] weaponCards = jsonDeserializationContext.deserialize(jsonTile.get("weapons").getAsJsonObject(), SemplifiedWeaponCard.class);
            tile.setWeapons(Arrays.asList(weaponCards));
        }
        return tile;
    }
}
