package jsonparser;

import com.google.gson.*;
import model.GameMap;
import model.Player;
import model.Room;
import model.Tile;

import java.lang.reflect.Type;
import java.util.*;

public class GameMapDeserializer implements JsonDeserializer<GameMap> {

    /**
     * Deserialize a gameMap from a given json element.
     *
     * @param jsonElement                the json element must have a field n_rooms, which indicates the number of rooms in the map,
     *                                   a field n_rows and n_cols in order to get some information about the topology and a map attribute.
     *                                   The map attribute is a json array of tiles. A tiles MUST have an ID, and a field for each direction
     *                                   and for each direction behind wall. Should have a "room" field that indicates in which room is placed.
     * @param type                       not null
     * @param jsonDeserializationContext not null
     * @return the corresponding gameMap to the given json element.
     * @throws JsonParseException if the json doesn't have all the fields said in the jsonElement parameter.
     */
    @Override
    public GameMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonObject jsonGameMap = jsonElement.getAsJsonObject();
        GameMap gameMap = new GameMap();
        //given a tile, returns the associated json
        Map<Tile, JsonObject> jsonMatching = new HashMap<>();
        //the json should be well built
        if (!jsonGameMap.has("n_rooms"))
            throw new JsonParseException("The json file is not correct, doesn't contain the number of rooms");
        if (!jsonGameMap.has("n_rows"))
            throw new JsonParseException("The json file is not correct, doesn't contain the number of rows");
        if (!jsonGameMap.has("n_cols"))
            throw new JsonParseException("The json file is not correct, doesn't contain the number of cols");
        if (!jsonGameMap.has("map"))
            throw new JsonParseException("The json file is not correct, doesn't contain the map");
        if (!jsonGameMap.get("map").isJsonArray()) throw new JsonParseException("The field map should be an array");

        //create an arrayList where all the tiles are ordered based on the id. The tiles is initialised with null objects
        int initialCapacity = getInizialCapacity(jsonGameMap);
        List<Tile> tiles = new ArrayList<>(initialCapacity);
        fillWithNull(tiles, initialCapacity);

        //at this point, all the rooms are created
        int numRooms = jsonGameMap.get("n_rooms").getAsInt();
        List<Room> rooms = new ArrayList<>(numRooms);
        for (int i = 0; i < numRooms; i++) {
            rooms.add(i, new Room());
        }

        //get the list of tiles from the json, and for each tile, a tile is created, with already the ammo and weapon field set
        Tile currentTile;
        JsonArray tileArray = jsonGameMap.get("map").getAsJsonArray();
        for (JsonElement j : tileArray) {
            JsonObject jsonTile = j.getAsJsonObject();
            List<Player> players = new LinkedList<>();

            //gets all the fields
            int id = jsonTile.get("ID").getAsInt();
            boolean isAmmoTile = jsonTile.get("ammoTile").getAsBoolean();
            boolean isWeaponTile = jsonTile.get("weaponTile").getAsBoolean();
            int currentRoom = jsonTile.get("room").getAsInt();

            if (jsonTile.has("players")) {
                players = getPlayersFromJson(jsonTile.get("players").getAsJsonArray());
            }


            //creates a tile
            currentTile = new Tile(id, isAmmoTile, isWeaponTile);
            for (Player p : players)
                currentTile.addPlayer(p);
            tiles.set(jsonTile.get("ID").getAsInt(), currentTile);
            //adds the tile to the room indicated in the json
            rooms.get(currentRoom).addTile(currentTile);

            //if the tile has a regenPoint member, and the regenPoint is not an empty string, add the tile to the regen point list
            if (hasRegenPoint(jsonTile)) {
                gameMap.addRegenPoint(jsonTile.get("regenPoint").getAsString(), currentTile);
            }

            jsonMatching.put(currentTile, jsonTile);
        }


        //sets all north south east west and walled fields
        JsonObject currentJsonTile;
        String[] directions = {"north", "east", "south", "west"};
        String[] directionsWalled = {"northWalled", "eastWalled", "southWalled", "westWalled"};
        int tilePosition;
        for (Tile t : tiles) {
            if (t != null) {
                currentJsonTile = jsonMatching.get(t);

                //sets the tiles that can be reached
                for (String dir : directions) {
                    tilePosition = currentJsonTile.get(dir).getAsInt();
                    //if it's present a tile in the given direction, set it
                    if (tilePosition != -1) {
                        t.setTile(dir, tiles.get(tilePosition));
                    }
                }

                //sets the tiles behind the walls
                for (int i = 0; i < directionsWalled.length; i++) {
                    tilePosition = currentJsonTile.get(directionsWalled[i]).getAsInt();
                    if (tilePosition != -1) {
                        t.setTileBehindWall(directions[i], tiles.get(tilePosition));
                    }
                }
            }

        }

        for (Room r : rooms) {
            gameMap.addRoom(r);
        }
        return gameMap;
    }

    /**
     * Checks if a method has a regen point and it's not an empty string. The json should have been produced correctly.
     *
     * @param jsonTile the tile from where to get the regenPoint information
     * @return true if there is a regenPoint
     */
    private boolean hasRegenPoint(JsonObject jsonTile) {
        String memberName = "regenPoint";
        return jsonTile.has(memberName) && !jsonTile.get(memberName).getAsString().equals("");
    }

    /**
     * Tells the number of cells (active and inacctive) of a game map. ES: rows = 3, cols = 4, total cells = 12
     *
     * @param map a json object that encodes a game map
     * @return the product between the n_rows and n_cols field of the map.
     */
    private int getInizialCapacity(JsonObject map) {
        return map.get("n_rows").getAsInt() * map.get("n_cols").getAsInt();
    }

    /**
     * Fills a list of tiles null pointers
     *
     * @param tiles    the list that has to be filled
     * @param capacity the desired capacity of the list
     */
    private void fillWithNull(List<Tile> tiles, int capacity) {
        for (int i = 0; i < capacity; i++) tiles.add(i, null);
    }

    /**
     * given a json array that contains some players, return the corresponding object. In future should call a player deserializer.
     *
     * @param jsonPlayers an array of json players
     * @return a list of players, can be empty, can't be null
     */
    private List<Player> getPlayersFromJson(JsonArray jsonPlayers) {
        List<Player> players = new LinkedList<>();
        for (JsonElement j : jsonPlayers) {
            JsonObject playerJsonObject = j.getAsJsonObject();
            players.add(new Player(playerJsonObject.get("name").getAsString()));
        }
        return players;
    }
}
