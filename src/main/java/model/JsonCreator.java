package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.*;
import controller.CommandResponse;

import javax.naming.OperationNotSupportedException;

public class JsonCreator implements ChangesObserver {

    private boolean prettyPrinting = false;
    private CommandResponse response;
    private final WeaponCardSerializer weaponCardSerializer;
    private final PlayerSerializer playerSerializer;
    private transient final Gson gson;


    public JsonCreator(GameManager gm) {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BloodToken.class, new BloodTokenSerializer());
        gb.registerTypeAdapter(PlayerBoard.class, new PlayerBoardSerializer());
        playerSerializer = new PlayerSerializer();
        gb.registerTypeAdapter(Player.class, playerSerializer);
        gb.registerTypeAdapter(Tile.class, new TileSerializer());
        weaponCardSerializer = new WeaponCardSerializer();
        weaponCardSerializer.setPlayerModeTrue();
        weaponCardSerializer.setCurrentPlayer(null);
        gb.registerTypeAdapter(WeaponCard.class, weaponCardSerializer);
        if (prettyPrinting) gb.setPrettyPrinting();
        gson = gb.create();

        response = new CommandResponse(gm.getMatch().getPlayers(), gm.getMatch().getMap().mapAsList());
    }


    /**
     * The message and the error will be null, the players changed are empty, the map is marked as "not changed" and the
     * player serializer is reset.
     */
    public void reset() {
        response.resetMessage();
        response.resetErrorMessage();
        response.resetChangedPlayers();
        response.setPlayerChanged(false);
        response.setMapChanged(false);
        playerSerializer.resetSet();
    }


    /**
     * creates a json with the given message
     * @param s the message you want to send
     * @return json string
     */
    public String createJsonWithMessage(String s) {
        response.setMessage(s);
        String changes = gson.toJson(response);
        response.resetMessage();
        playerSerializer.resetSet();
        return changes;
    }

    /**
     * creates a json with the given error
     * @param s the error you want to communicate
     * @return a json string
     */
    public String createJsonWithError(String s){
        response.setErrorMessage(s);
        String changes = gson.toJson(response);
        response.resetErrorMessage();
        playerSerializer.resetSet();
        return changes;
    }

    /**
     * creates a json with the information that the given player can see
     * @param s the message
     * @param player
     * @return
     */
    public String createTargetPlayerJson(String s, Player player) {
        weaponCardSerializer.setCurrentPlayer(player);
        response.setMessage(s);
        String changes = gson.toJson(response);
        response.resetMessage();
        playerSerializer.resetSet();
        return changes;
    }

    public String createJustMessage(String s) {
         gson.toJson(s, String.class);
         throw  new RuntimeException("da implementare");
    }

    public String createJustError(String s){
        gson.toJson(s, String.class);
        throw new RuntimeException("da implementare");
    }

    /************************ Changes observer implementation *******************************************/

    @Override
    public void notifyTileChange(Tile t) {
        response.setMapChanged(true);
    }

    @Override
    public void notifyPlayerChange(Player p) {
        response.setPlayerChanged(true);
        response.addChangedPlayer(p);
    }
}
