package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.*;
import model.clientModel.CommandResponse;

public class JsonCreator implements ChangesObserver {

    private boolean prettyPrinting = false;
    private CommandResponse response;
    private final WeaponCardSerializer weaponCardSerializer;
    private transient final Gson gson;

    public JsonCreator(Match match) {
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

        response = new CommandResponse(match.getPlayers(), match.getMap().mapAsList());
    }


    public void reset() {
        response.resetMessage();
        response.resetErrorMessage();
        response.resetChangedPlayers();
        response.setMapChanged(false);
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
        return changes;
    }

    public String createJustMessage(String s) {
        return gson.toJson(s, String.class);
    }

    public String createJustError(String s){
        return gson.toJson(s, String.class);
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
