package jsonParser;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.State;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StateMachineDeserializer implements JsonDeserializer<State[]> {

    /**
     * A strategy that ignores the "possibleNextState" field
     */
    ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            if (field.getDeclaringClass() == State.class && field.getName().equals("possibleNextState")) {
                return true;
            }
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    /**
     * Deserialize a json array of states. And array of states is returned by the method
     * @param jsonElement the json containing the state array
     * @param type State[] class
     * @param jsonDeserializationContext
     * @return an array of states with the adjacency list all set
     * @throws JsonParseException
     */
    @Override
    public State[] deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        GsonBuilder gb = new GsonBuilder();
        //this gson parser ignores the possible next state
        Gson gson = gb.addDeserializationExclusionStrategy(strategy).create();
        // list of the states with the adjacency list empty
        State[] statesArray;
        statesArray=  gson.fromJson(jsonElement, State[].class);

        //now i create a map that creates a correlation between a state and his name
        Map<String, State> stateMap = createStateMap(statesArray);
        System.out.println(stateMap.keySet());

        Gson gson2 = new Gson();
        //i need to know know the key for each state
        for(JsonElement j : jsonElement.getAsJsonArray()){
            //gets the map in the state array. It's a string string maps
            final JsonObject jsonStateMap = j.getAsJsonObject().get("possibleNextState").getAsJsonObject();
            //gets the name of the current state parsed
            final String stateName = j.getAsJsonObject().get("name").getAsString();
            //gets the type of the map for the json conversion
            Type mapType = new TypeToken<Map<String,String >>(){}.getType();
            //creates the map
            Map<String, String> map = gson2.fromJson(jsonStateMap, mapType);
            Collection<String> keys = map.keySet();
            State currentState = stateMap.get(stateName);
            currentState.allocatePossibleNextState();
            for(String key: keys){
                currentState.addProxState(key, stateMap.get(map.get(key)));
            }
        }

        return statesArray;
    }

    /**
     * Creates a Map<String, State> from an array of states
     * @param stateArray
     * @return
     */
    private Map<String, State> createStateMap(State[] stateArray){
        Map<String, State> map = new HashMap<>();
        for(State s: stateArray) map.put(s.getName(), s);
        return map;
    }
}
