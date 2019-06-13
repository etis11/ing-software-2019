package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.*;

import java.util.LinkedList;
import java.util.List;

public class JsonCreator implements ChangesObserver {

    private static transient boolean prettyPrinting = false;
    private final List<Player> changedPlayers = new LinkedList<>();
//    private final List<Player> allPlayers;
    private final List<Tile> allTiles = new LinkedList<>();
    private transient final Gson gson;
    private transient final WeaponCardSerializer weaponCardSerializer;
    private String message;

    public JsonCreator() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BloodToken.class, new BloodTokenSerializer());
        gb.registerTypeAdapter(PlayerBoard.class, new PlayerBoardSerializer());
        gb.registerTypeAdapter(Player.class, new PlayerSerializer());
        gb.registerTypeAdapter(Tile.class, new TileSerializer());
        weaponCardSerializer = new WeaponCardSerializer();
        weaponCardSerializer.setPlayerModeTrue();
        weaponCardSerializer.setCurrentPlayer(null);
        gb.registerTypeAdapter(WeaponCard.class, weaponCardSerializer);
        if (prettyPrinting) gb.setPrettyPrinting();
        gson = gb.create();

    }


    public void reset() {
        changedPlayers.clear();
        changedTiles.clear();
    }


    public String createJsonWithMessage(String s) {
        setMessage(s);
        String changes = gson.toJson(this);
        resetMessage();
        return changes;
    }

    public String createTargetPlayerJson(String s, Player player) {
        weaponCardSerializer.setCurrentPlayer(player);
        setMessage(s);
        String changes = gson.toJson(this);
        resetMessage();
        return changes;
    }

    public String createJustMessage(String s) {
        return gson.toJson(s, String.class);
    }

    private void setMessage(String s) {
        message = s;
    }

    private void resetMessage() {
        message = null;
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
