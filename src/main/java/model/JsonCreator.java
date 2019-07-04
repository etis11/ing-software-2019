package model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.*;
import controller.CommandResponse;
import view.LobbyListener;

import java.util.List;

public class JsonCreator implements ChangesObserver, CreationGameObserver, LobbyListener, ChangesMatchObserver {

    private boolean prettyPrinting = false;
    private CommandResponse response;
    private final WeaponCardSerializer weaponCardSerializer;
    private final PlayerSerializer playerSerializer;
    private transient final Gson gson;

    public JsonCreator(){
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BloodToken.class, new BloodTokenSerializer());
        gb.registerTypeAdapter(PlayerBoard.class, new PlayerBoardSerializer());
        playerSerializer = new PlayerSerializer();
        gb.registerTypeAdapter(Player.class, playerSerializer);
        gb.registerTypeAdapter(Tile.class, new TileSerializer());
        weaponCardSerializer = new WeaponCardSerializer();
        gb.registerTypeAdapter(WeaponCard.class, weaponCardSerializer);
        if (prettyPrinting) gb.setPrettyPrinting();
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }

            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getAnnotation(Exclude.class) != null;
            }
        };
        gb.addSerializationExclusionStrategy(strategy);


        gson = gb.create();

        response = new CommandResponse();

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
        response.resetJoinedUsers();
        response.resetLeavingUsers();
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

    public String createJsonWithErrorTargetPlayer(String s, Player player){
        playerSerializer.setCurrentPlayer(player);
        response.setErrorMessage(s);
        String changes = gson.toJson(response);
        response.resetErrorMessage();
        playerSerializer.resetSet();
        playerSerializer.resetCurrentPlayer();
        return changes;
    }

    /**
     * creates a json with the information that the given player can see
     * @param s the message
     * @param player
     * @return
     */
    public String createTargetPlayerJson(String s, Player player) {
        playerSerializer.setCurrentPlayer(player);
        response.setMessage(s);
        String changes = gson.toJson(response);
//        System.out.println("Mandato a " + player.getName() + ".\n Messaggio: "+s+"\nNel creator lista changhed players:" + response.getChangedPlayers());
        response.resetMessage();
        playerSerializer.resetSet();
        playerSerializer.resetCurrentPlayer();
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

    /******************* CreationGameObserver ************************************************************/
    @Override
    public void notifyStartedGame(Match m) {
        List<Player> allPlayer = m.getPlayers();
        List<Tile> allTiles = m.getMap().mapAsList();
        for(Player p: allPlayer)
            p.attach(this);
        for(Tile t: allTiles)
            t.attach(this);
        response.setAllPlayers(allPlayer);
        response.setAllTiles(allTiles);
    }

    @Override
    public void notifyCreatedLobby(Lobby l) {
        response.setLobby(l);
    }

    @Override
    public void notifyChangedMap(String mapName) {
        response.setMap(mapName);
    }

    /******************* Lobby listener ************************************************************/

    @Override
    public void onJoin(User joinedUser) {
        response.addJoinedUser(joinedUser);
    }

    @Override
    public void onLeave(User leavingUser) {
        response.addLeavingUser(leavingUser);
    }

    @Override
    public void notifyCurrentPlayerChange(Player player) {
        response.setCurrentPlayer(player);
    }

    @Override
    public void notifySkullChange(List<List<BloodToken>> deathTrack) {
        response.setDeathTrack(deathTrack);
    }
}
