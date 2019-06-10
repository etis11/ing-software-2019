package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class JsonCreator implements ChangesObserver {

    private final List<Player> changedPlayers = new LinkedList<>();
    private final List<Tile> changedTiles = new LinkedList<>();
    private final Gson gson;

    public JsonCreator(){
        GsonBuilder gb = new GsonBuilder();
        gson = gb.create();

    }


    public void reset(){
        changedPlayers.clear();
        changedTiles.clear();
    }


    public String createJsonWithMessage(String s){
         return gson.toJson(this);
    }

/************************ Changes observer implementation *******************************************/

    @Override
    public void notifyTileChange(Tile t) {
        changedTiles.remove(t);
        changedTiles.add(t);
    }

    @Override
    public void notifyPlayerChange(Player p) {
        changedPlayers.remove(p);
        changedPlayers.add(p);
    }
}
