package controller;

import com.google.gson.JsonElement;

public class JsonUnwrapper implements JsonReceiver {


    /**
     * creates  a json wrapper. If used with RMI, should be exported
     */
    public JsonUnwrapper(){

    }

    @Override
    public void sendJson(String changes){

    }
}
