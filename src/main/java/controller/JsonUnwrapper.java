package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controller.commandpack.Command;
import jsonparser.semplifiedParser.SemplifiedPlayerDeserializer;
import model.clientModel.CommandResponse;
import model.clientModel.SemplifiedBloodToken;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedPlayer;
import view.*;

import java.util.LinkedList;
import java.util.List;

public class JsonUnwrapper implements JsonReceiver, MessageObservable, PlayerObservable, MapObservable {


    private final Gson gson;
    private final SemplifiedPlayerDeserializer playerDeserializer;
    private final List<MessageListener> messageListeners;
    private final List<PlayerObserver> playerObservers;
    private final List<MapObserver> mapObservers;

    /**
     * creates  a json wrapper. If used with RMI, should be exported
     */
    public JsonUnwrapper() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        playerDeserializer = new SemplifiedPlayerDeserializer();
        gsonBuilder.registerTypeAdapter(SemplifiedPlayer.class, playerDeserializer);
        gson = gsonBuilder.create();

        messageListeners = new LinkedList<>();
        playerObservers = new LinkedList<>();
        mapObservers = new LinkedList<>();
    }

    /*********************** Json receiver interface *********************/

    @Override
    public void sendJson(String changes) {
        CommandResponse response = gson.fromJson(changes, CommandResponse.class);


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
