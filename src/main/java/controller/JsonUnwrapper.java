package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jsonparser.semplifiedParser.SemplifiedPlayerDeserializer;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedMap;
import model.clientModel.SemplifiedPlayer;
import view.*;

import javax.naming.OperationNotSupportedException;
import java.util.LinkedList;
import java.util.List;

public class JsonUnwrapper implements JsonReceiver, MessageObservable, PlayerObservable, MapObservable {


    private final Gson gson;
    private final SemplifiedPlayerDeserializer playerDeserializer;
    private final List<MessageListener> messageListeners;
    private final List<PlayerObserver> playerObservers;
    private final List<MapObserver> mapObservers;
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
    }

    private void notifyAllMessageListeners(String message){
        for(MessageListener listener : messageListeners)
            listener.notify(message);
    }

    private void notifyAllMapObservers(SemplifiedMap map){
        for(MapObserver ob: mapObservers){
            ob.onMapChange(map);
        }
    }

    private void notifyAllPlayerObserver(SemplifiedPlayer player){
        for(PlayerObserver ob: playerObservers){
            notifyAllPlayerObserver(player);
        }
    }

    /*********************** Json receiver interface *********************/

    @Override
    public void sendJson(String changes) {
        CommandResponseClient response = gson.fromJson(changes, CommandResponseClient.class);
        String message = response.getMessage();
        if(response!= null) notifyAllMessageListeners(message);
        SemplifiedMap map = game.getMap();
        map.updateTiles(response.getAllTiles());
        //sono le stesse
        if(response.isMapChanged()) notifyAllMapObservers(game.getMap());
        game.setPlayers(response.getAllPlayers());
        if(response.arePlayersChanged()){

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
}
