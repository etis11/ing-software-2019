package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.semplifiedParser.SemplifiedPlayerDeserializer;
import model.LobbyObservable;
import model.Match;
import model.User;
import model.clientModel.*;
import view.*;

import java.util.LinkedList;
import java.util.List;

public class JsonUnwrapper implements JsonReceiver, MessageObservable, PlayerObservable, MapObservable, LobbyObservable, MatchObservable {


    private final Gson gson;
    private final SemplifiedPlayerDeserializer playerDeserializer;
    private final List<MessageListener> messageListeners;
    private final List<PlayerObserver> playerObservers;
    private final List<MapObserver> mapObservers;
    private final List<MatchObserver> matchObservers;
    private final List<LobbyListener> lobbyListeners;
    private final SemplifiedGame game;

    private String oldMapName;
    private SemplifiedPlayer oldCurrentPlayer;
    private List<List<SemplifiedBloodToken>> oldDeathTrack;

    /**
     * creates  a json wrapper. If used with RMI, should be exported
     */
    public JsonUnwrapper(SemplifiedGame game) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        playerDeserializer = new SemplifiedPlayerDeserializer();
        gsonBuilder.registerTypeAdapter(SemplifiedPlayer.class, playerDeserializer);
        gson = gsonBuilder.create();

        this.game = game;
        messageListeners = new LinkedList<>();
        playerObservers = new LinkedList<>();
        mapObservers = new LinkedList<>();
        lobbyListeners = new LinkedList<>();
        matchObservers = new LinkedList<>();

        oldDeathTrack = new LinkedList<>();
        oldCurrentPlayer = new SemplifiedPlayer();
    }

    private void notifyAllMessageListeners(String message){
        for(MessageListener listener : messageListeners)
            listener.notifyMessage(message);
    }

    private void notifyAllMapObservers(SemplifiedMap map){
        for(MapObserver ob: mapObservers){
            ob.onMapChange(map);
        }
    }

    private void notifyCurrentPlayerChange(SemplifiedPlayer p){
        for (MatchObserver mo: matchObservers) mo.onCurrentPlayerChange(p);
    }

    private void notifyDeathTrackChange(List<List<SemplifiedBloodToken>> deathTrack){
        for(MatchObserver mo: matchObservers) mo.onSkullChange(deathTrack);
    }

    private void notifyAllPlayerObserver(SemplifiedPlayer player){
        for(PlayerObserver ob: playerObservers){
            ob.onPlayerChange(player);
        }
    }

    private void notifyAllLobbyJoinListeners(User user){
        for(LobbyListener lb: lobbyListeners){
            lb.onJoin(user);
        }
    }

    private void notifyAllLobbyLeaveListeners(User user){
        for(LobbyListener lb: lobbyListeners){
            lb.onLeave(user);
        }
    }


    /*********************** Json receiver interface *********************/

    @Override
    public void sendJson(String changes) {
        /**
         * poi da cancellare
         */
        System.out.println(changes);

        CommandResponseClient response = gson.fromJson(changes, CommandResponseClient.class);
        String message = response.getMessage();
        if(message!= null)
            notifyAllMessageListeners(message);

        String mapName = response.getMapName();
        if (mapName != null && !mapName.equals(oldMapName)){
            for(MapObserver m : mapObservers)
                m.onTypeMapChange(mapName);
        }

        String error = response.getError();
        if (error != null)
            notifyAllMessageListeners(error);

        SemplifiedMap map = game.getMap();
        if(response.getAllTiles() != null){
            map.updateTiles(response.getAllTiles());
        }
        if(response.isMapChanged())
            notifyAllMapObservers(game.getMap());
        if(response.getAllPlayers() != null){
            game.setPlayers(response.getAllPlayers());
        }


        if(response.arePlayersChanged()){
            List<SemplifiedPlayer> changedPlayers = response.getChangedPlayers();
            for(SemplifiedPlayer p: changedPlayers)
                notifyAllPlayerObserver(p);
        }

        if (response.getJoiningUsers() != null){
            for(User u: response.getJoiningUsers()){
                notifyAllLobbyJoinListeners(u);
            }
        }

        if(response.getLeavingUsers() != null){
            for(User u: response.getLeavingUsers()){
                notifyAllLobbyLeaveListeners(u);
            }
        }

        //if the new current player has a different name of the old current player,
        SemplifiedPlayer currentPlayer = response.getCurrentPlayer();

        if (!currentPlayer.equals(oldCurrentPlayer)){
            notifyCurrentPlayerChange(currentPlayer);
            oldCurrentPlayer = currentPlayer;
        }

        List<List<SemplifiedBloodToken>> deathTrack = response.getDeathTrack();
        if ( deathTrack != null &&  oldDeathTrack.size() != deathTrack.size())
            notifyDeathTrackChange(deathTrack);

        playerDeserializer.resetMap();
    }

    /************************ message observable    *******************************/
    @Override
    public void attachMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    /******************** Player observable ***************************************/

    @Override
    public void attachPlayerObserver(PlayerObserver playerObserver) {
        playerObservers.add(playerObserver);
    }

    /********************** Map Observable ***************************************/
    @Override
    public void attachMapObserver(MapObserver mapObserver) {
        mapObservers.add(mapObserver);
    }

    /********************** Match Observable ***************************************/
    @Override
    public void attachMatchObserver(MatchObserver matchObserver) {
        matchObservers.add(matchObserver);
    }

    /********************** Lobby Observable ***************************************/
    @Override
    public void attach(LobbyListener ls) {
        lobbyListeners.add(ls);
    }

}
