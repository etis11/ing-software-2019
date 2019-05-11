package model;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import jsonParser.StateMachineDeserializer;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class provaGson {

    private static ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            if (field.getName().equals("possibleNextState")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    public static void main(String[] args) {
        GsonBuilder gb = new GsonBuilder().registerTypeAdapter(State[].class, new StateMachineDeserializer());
        State[] states = {};
        try{

            states = gb.create().fromJson(
                            new FileReader("./src/main/resources/stateMachine/stateMachine.json"), State[].class);
        }
        catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            f.printStackTrace();
        }
        for(State s: states) System.out.println(s.getName());
    }

}
