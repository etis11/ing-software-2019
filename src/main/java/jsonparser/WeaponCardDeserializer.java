package jsonparser;

import com.google.gson.*;
import model.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
Class used to deserialise all Weaponcards based on Effects,strategies and typology.
 */
public class WeaponCardDeserializer implements JsonDeserializer<WeaponCard> {

    /*
      Private attribute to be used when needed for the strategies
     */
    private Match match;

    /*
    The default constructor
     */
    public WeaponCardDeserializer(Match match) {
        this.match = match;
    }

    /*
    The following method is based on gson library needed to deserialise a .jSON file .
    In this case we needed it to deserialise our weaponcards!
     */
    @Override
    public WeaponCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = new Gson();
        WeaponCard wcToAdd = new WeaponCard();
        JsonObject jsonCard = jsonElement.getAsJsonObject();
        String name = jsonCard.get("NAME").getAsString();
        //System.out.println(name);
        JsonArray jsonCost = jsonCard.get("reloadCost").getAsJsonArray();
        List<String> costs = new ArrayList<>();
        for (JsonElement cost : jsonCost) {
            costs.add(cost.getAsString());
        }

        JsonArray baseEffectsJson = jsonCard.get("baseEffect").getAsJsonArray();
        JsonArray advancedEffectsJson = jsonCard.get("advancedEffect").getAsJsonArray();
        List<Effect> effectsList = parseBaseEffects(baseEffectsJson, gson);
        List<Effect> effectsListAdvanced = parseBaseEffects(advancedEffectsJson, gson);
        WeaponCard wcToadd = new WeaponCard();

        wcToAdd.setAdvancedEffect(effectsListAdvanced);
        wcToAdd.setName(name);
        wcToAdd.setBaseEffect(effectsList);
        wcToAdd.setReloadCost(costs);

        return wcToAdd;
    }

    /*
    Method used to parse from the jSON file.
    @param jsonFile is added as string
    @return List of WeaponCards with all of their characteristics
     */

    public List<WeaponCard> parseWeaponCards(String jsonFile) {
        //creates the gson parser

        Gson gson = new Gson();
        JsonArray json = gson.fromJson(new InputStreamReader(getClass().getResourceAsStream(jsonFile)), JsonArray.class);
        List<WeaponCard> wcToReturn = new ArrayList<>();
        for (JsonElement el : json) {
            WeaponCard wcToAdd = new WeaponCard();
            JsonObject jsonCard = el.getAsJsonObject();
            String name = jsonCard.get("NAME").getAsString();
            //System.out.println(name);
            JsonArray jsonCost = jsonCard.get("reloadCost").getAsJsonArray();
            List<String> costs = new ArrayList<>();
            for (JsonElement cost : jsonCost) {
                costs.add(cost.getAsString());
            }

            JsonArray baseEffectsJson = jsonCard.get("baseEffect").getAsJsonArray();
            JsonArray advancedEffectsJson = jsonCard.get("advancedEffect").getAsJsonArray();
            List<Effect> effectsList = parseBaseEffects(baseEffectsJson, gson);

            List<Effect> effectsListAdvanced = parseBaseEffects(advancedEffectsJson, gson);
            wcToAdd.setAdvancedEffect(effectsListAdvanced);
            wcToAdd.setName(name);
            wcToAdd.setBaseEffect(effectsList);
            wcToAdd.setReloadCost(costs);
            wcToReturn.add(wcToAdd);
        }
        return wcToReturn;
    }

    /*
    Method used to parse BaseEffects but AdvancedEffects also given the fact that we've treated both as same
    @param effects is a list of jSON elements
    @param gson is by default imported as needed for our method
    @return List of base and advanced effects!
     */
    private List<Effect> parseBaseEffects(JsonArray effects, Gson gson) {
        List<Effect> toReturn = new ArrayList<>();
        for (JsonElement eff : effects) {
            Effect toAdd = new Effect();
            JsonObject effect = eff.getAsJsonObject();
            //System.out.println(effect);
            List<String> costs = new ArrayList<>();
            try {
                JsonArray jsonCost = effect.get("cost").getAsJsonArray();
                for (JsonElement cost : jsonCost) {
                    costs.add(cost.getAsString());
                }
            } catch (Exception e) {

            }
            //int steps=0;
            boolean global;
            boolean optional;
            String movType = "";
            // JsonObject strategy=effect.get("strategy").getAsJsonObject();
            //int param=0;
            //if(!strategy.get("param").isJsonNull()){
            //  param=strategy.get("param").getAsInt();
            //}
            //  if(toAdd!=null){
            //    toAdd.setCost(costs);
            //  toAdd.setStrategy(getStrategyByName(strategy.get("type").getAsString(),param));}
            //System.out.println(effect);
            toAdd.setMoveTargetAndHitAll(effect.get("moveTargetAndHitAll").getAsBoolean());
            toAdd.setAlreadyMovedTarget(effect.get("alreadyMovedTarget").getAsBoolean());
            toAdd.setGlobal(effect.get("isGlobal").getAsBoolean());

            toAdd.setDamage(gson.fromJson(effect.get("damage"), HashMap.class));
            toAdd.setMarks(gson.fromJson(effect.get("marks"), HashMap.class));
            toAdd.setCanMoveShooter(effect.get("canMoveShooter").getAsBoolean());
            toAdd.setCanMoveShooter(effect.get("canMoveTarget").getAsBoolean());
            toAdd.setNumStepsShooter(effect.get("numStepsShooter").getAsInt());
            toAdd.setNumStepsTarget(effect.get("numStepsTarget").getAsInt());

            try {
                if(effect.has("optionalEffects")){
                   // for (JsonElement oe : effect.get("optionalEffects").getAsJsonArray()) {
                        JsonArray optionalEffectJson = effect.get("optionalEffects").getAsJsonArray();
                        for (JsonElement optionalJson : optionalEffectJson) {
                            OptionalEffect optionalEffect = gson.fromJson(optionalJson, OptionalEffect.class);
                            toAdd.getOptionalEffects().add(optionalEffect);
                        }
                    //}
                }
            } catch (Exception e) {
                System.out.println("qua devo aggiungere qualcosa se no sonar si imppapa");
            }
            //List<String> costs = new ArrayList<>();
            try {
                JsonArray jsonCost = effect.get("cost").getAsJsonArray();
                for (JsonElement cost : jsonCost) {
                    costs.add(cost.getAsString());
                }
            } catch (Exception e) {

            }

            JsonObject strategy = effect.get("strategy").getAsJsonObject();
            int param = 0;
            if (!strategy.get("param").isJsonNull()) {
                param = strategy.get("param").getAsInt();
            }

            if (eff != null) {
                toAdd.setCost(costs);
                toAdd.setStrategy(getStrategyByName(strategy.get("type").getAsString(), param));
            }

            toReturn.add(toAdd);
        }
        return toReturn;


    }

    /*
    Private method made by a switch-case where it select the strategy to be applied to a certain Weaponcard
    @param name is the name of the Strategy class used
    @param param is an integer used to determine the distance shooter-target needed for some strategies
    @return strategu to be applied
     */
    private AbstractTargetStrategy getStrategyByName(String name, int param) {
        AbstractTargetStrategy toReturn = null;
        switch (name) {
            case "SeeStrategy":
                toReturn = new SeeStrategy(match);
                break;
            case "FixedDistanceStrategy":
                toReturn = new FixedDistanceStrategy(param, match);
                break;
            case "RoomStrategy":
                toReturn = new RoomStrategy(match);
                break;
            case "VortexCannonStrategy":
                toReturn = new VortexCannonStrategy(match);
                break;
            case "DontSeeStrategy":
                toReturn = new DontSeeStrategy(match);
                break;
            case "FlameThrowerStrategy":
                toReturn = new FlameThrowerStrategy();
                break;
            case "BBQStrategy":
                //toReturn = new BBQStrategy(param,match);
                break;
            case "TractorBeamStrategy":
                toReturn = new TractorBeamStrategy(param, match);
                break;
            case "AdjacentStrategy":
                toReturn = new AdjacentStrategy(param, match);
                break;
            case "LaserRifleStrategy":
                toReturn = new IgnoreWallStrategy();
                break;
            case "MeleeStrategy":
                //toReturn = new MeleeStrategy( param, match);
                break;
            case "AdjacentDifferentTilesStrategy":
                toReturn = new AdjacentDifferentTilesStrategy(param, match);
                break;
            case "HellionStrategy":
                toReturn = new HellionStrategy(param, match);
                break;
            case "TorpedineStrategy":
                toReturn = new TorpedineStrategy(match);
                break;
            default:
                break;
        }
        return toReturn;
    }
}
