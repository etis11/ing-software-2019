package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class provaGson {

    public static void main(String[] args) {
        Gson g = new Gson();
        State[] states = null;
        String path = "./src/main/resources/stateMachine/stateMachine.json";
        Reader inputJson = null;
        Type listType = new TypeToken<Collection<State>>(){}.getType();
        try{
            inputJson = new BufferedReader(new FileReader(path));
            states = g.fromJson(inputJson, State[].class);
        }
        catch (Exception e){ System.out.println(e.getMessage()); e.printStackTrace();}
        System.out.println(states);
    }
}
