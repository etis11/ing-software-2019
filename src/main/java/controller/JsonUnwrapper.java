package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.semplifiedParser.SemplifiedPlayerDeserializer;
import model.Lobby;
import model.LobbyObservable;
import model.User;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import view.*;

import java.util.LinkedList;
import java.util.List;

public class JsonUnwrapper implements JsonReceiver, MessageObservable, PlayerObservable, MapObservable, LobbyObservable {


    private final Gson gson;
    private final SemplifiedPlayerDeserializer playerDeserializer;
    private final List<MessageListener> messageListeners;
    private final List<PlayerObserver> playerObservers;
    private final List<MapObserver> mapObservers;
    private final List<LobbyListener> lobbyListeners;
    private final SemplifiedGame game;

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
        CommandResponseClient response = gson.fromJson(changes, CommandResponseClient.class);
        String message = response.getMessage();
        if(response != null) notifyAllMessageListeners(message);
        notifyAllMessageListeners(response.getError());
        SemplifiedMap map = game.getMap();
        if(response.getAllTiles() != null)
            map.updateTiles(response.getAllTiles());
        //sono le stesse
        if(response.isMapChanged()) notifyAllMapObservers(game.getMap());
        if(response.getAllPlayers() != null){
            game.setPlayers(response.getAllPlayers());
        }
        if(response.arePlayersChanged()){

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

    /********************** Lobby Observable ***************************************/
    @Override
    public void attach(LobbyListener ls) {
        lobbyListeners.add(ls);
    }
}
