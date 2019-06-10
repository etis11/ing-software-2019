package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import model.clientModel.SemplifiedGame;

public class JsonUnwrapper implements JsonReceiver {


    private final Gson gson;
    /**
     * creates  a json wrapper. If used with RMI, should be exported
     */
    public JsonUnwrapper(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
    }

    @Override
    public void sendJson(String changes){
        gson.fromJson(changes, SemplifiedGame.class);
    }
}
