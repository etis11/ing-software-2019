package jsonParser;

import com.google.gson.*;
import model.GameMap;
import model.Room;
import model.Tile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {

    /**
     * Deserialize a gameMap from a given json element.
     * @param jsonElement the json element must have a field n_rooms, which indicates the number of rooms in the map,
     *                    a field n_rows and n_cols in order to get some information about the topology and a map attribute.
     *                    The map attribute is a json array of tiles. A tiles MUST have an ID, and a field for each direction
     *                    and for each direction behind wall. Should have a "room" field that indicates in which room is placed.
     * @param type not null
     * @param jsonDeserializationContext not null
     * @return the corresponding gameMap to the given json element.
     * @throws JsonParseException if the json doesn't have all the fields said in the jsonElement parameter.
     */
    @Override
    public GameMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonGameMap = jsonElement.getAsJsonObject();
        GameMap gameMap = new GameMap();
        //given a tile, returns the associated json
        Map<Tile, JsonObject> jsonMatching = new HashMap<>();
        //the json should be well built
        if (!jsonGameMap.has("n_rooms")) throw new JsonParseException("The json file is not correct, doesn't contain the number of rooms");
        if (!jsonGameMap.has("n_rows")) throw new JsonParseException("The json file is not correct, doesn't contain the number of rows");
        if (!jsonGameMap.has("n_cols")) throw new JsonParseException("The json file is not correct, doesn't contain the number of cols");
        if (!jsonGameMap.has("map")) throw new JsonParseException("The json file is not correct, doesn't contain the map");
        if(!jsonGameMap.get("map").isJsonArray()) throw new JsonParseException("The field map should be an array");

        //create an arrayListt where all the tiles are ordered based on the id. The tiles is initialised with null objects
        int initialCapacity = jsonGameMap.get("n_rows").getAsInt()*jsonGameMap.get("n_cols").getAsInt();
        List<Tile> tiles = new ArrayList<>( initialCapacity);
        for(int i = 0; i < initialCapacity; i++) tiles.add(i, null);

        //at this point, all the rooms are created
        int num_rooms = jsonGameMap.get("n_rooms").getAsInt();
        List<Room> rooms = new ArrayList<>(num_rooms);
        for(int i = 0; i < num_rooms; i++){
            rooms.add(i, new Room());
        }

        //get the list of tiles from the json, and for each tile, a tile is created, with already the ammo and weapon field set
        Tile currentTile;
        JsonArray tileArray = jsonGameMap.get("map").getAsJsonArray();
        for(JsonElement j : tileArray){
            JsonObject jsonTile = j.getAsJsonObject();

            //gets all the fields
            boolean isAmmoTile = jsonTile.get("ammoTile").getAsBoolean();
            boolean isWeaponTile = jsonTile.get("weaponTile").getAsBoolean();
            int current_room = jsonTile.get("room").getAsInt();

            //creates a tile
            currentTile = new Tile(isAmmoTile, isWeaponTile);
            tiles.add(jsonTile.get("ID").getAsInt(), currentTile );
            //adds the tile to the room indicated in the json;
            rooms.get(current_room).addTile(currentTile);

            //if the tile has a regenPoint member, and the regenPoint is not an empty string, add the tile to the regen point list
            if(jsonTile.has("regenPoint") && !jsonTile.get("regenPoint").getAsString().equals("")){
                gameMap.addRegenPoint(jsonTile.get("regenPoint").getAsString(), currentTile);
            }

            jsonMatching.put(currentTile, jsonTile);
        }


        //sets all north south east west and walled fields
        JsonObject currentJsonTile;
        String[] directions = {"north", "east", "south", "west"};
        String[] directionsWalled =  {"northWalled", "eastWalled", "southWalled", "westWalled"};
        int tilePosition;
        for(Tile t: tiles){
            currentJsonTile = jsonMatching.get(t);

            //sets the tiles that can be reached
            for(String dir : directions){
                tilePosition =currentJsonTile.get(dir).getAsInt();
                //if it's present a tile in the given direction, set it
                if ( tilePosition!= -1){
                    t.setTile(dir, tiles.get(tilePosition));
                }
            }

            //sets the tiles behind the walls
            for(int i = 0; i < directionsWalled.length; i++){
                tilePosition =currentJsonTile.get(directionsWalled[i]).getAsInt();
                if ( tilePosition!= -1){
                    t.setTileBehindWall(directions[i], tiles.get(tilePosition));
                }
            }
        }

        for(Room r: rooms){
            gameMap.addRoom(r);
        }
        return gameMap;
    }
}
