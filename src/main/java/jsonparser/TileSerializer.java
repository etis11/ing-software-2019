package jsonparser;

import com.google.gson.*;
import model.Player;
import model.Tile;
import model.WeaponCard;

import java.lang.reflect.Type;
import java.util.List;

public class TileSerializer implements JsonSerializer<Tile> {

    @Override
    public JsonElement serialize(Tile tile, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonTile = new JsonObject();

        jsonTile.addProperty("id", tile.getID());
        //creates a list of names of players
        List<Player> playerList = tile.getPlayers();
        JsonArray playerArray = new JsonArray();
        for (Player p: playerList)
            playerArray.add(p.getName());
        jsonTile.add("players", playerArray);

        jsonTile.addProperty("ammoTile", tile.canContainAmmo());
        jsonTile.addProperty("weaponTile", tile.canContainWeapons());

        JsonElement weapons = jsonSerializationContext.serialize(tile.getWeapons().toArray(new WeaponCard[0]), WeaponCard[].class);
        jsonTile.add("weapons", weapons);

        return jsonTile;
    }
}
