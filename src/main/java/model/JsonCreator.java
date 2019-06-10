package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import jsonparser.BloodTokenSerializer;
import jsonparser.PlayerBoardSerializer;
import jsonparser.PlayerSerializer;
import jsonparser.WeaponCardSerializer;

import java.util.LinkedList;
import java.util.List;

public class JsonCreator implements ChangesObserver {

    private final List<Player> changedPlayers = new LinkedList<>();
    private final List<Tile> changedTiles = new LinkedList<>();
    private String message;

    private transient final Gson gson;
    private transient final WeaponCardSerializer weaponCardSerializer;

    public JsonCreator(){
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BloodToken.class, new BloodTokenSerializer());
        gb.registerTypeAdapter(PlayerBoard.class, new PlayerBoardSerializer());
        gb.registerTypeAdapter(Player.class, new PlayerSerializer());
        weaponCardSerializer = new WeaponCardSerializer();
        weaponCardSerializer.setPlayerModeTrue();
        weaponCardSerializer.setCurrentPlayer(null);
        gb.registerTypeAdapter(WeaponCard.class, weaponCardSerializer);
        gson = gb.create();

    }


    public void reset(){
        changedPlayers.clear();
        changedTiles.clear();
    }


    public String createJsonWithMessage(String s){
        setMessage(s);
        String changes = gson.toJson(this);
        resetMessage();
        return  changes;
    }

    public String createTargetPlayerJson(String s, Player player){
        weaponCardSerializer.setCurrentPlayer(player);
        setMessage(s);
        String changes = gson.toJson(this);
        resetMessage();
        return changes;
    }

    public String createJustMessage(String s){
        return gson.toJson(s, String.class);
    }

    private void setMessage(String s){ message = s;}

    private  void resetMessage(){ message = null;}

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
