package jsonparser;

import com.google.gson.*;
import model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeaponCardDeserializer implements JsonDeserializer<WeaponCard> {

    private Match match;

    public WeaponCardDeserializer (Match match){
        this.match = match;
    }

    @Override
    public WeaponCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = new Gson();
        WeaponCard wcToAdd= new WeaponCard();
        JsonObject jsonCard = jsonElement.getAsJsonObject();
        String name=jsonCard.get("NAME").getAsString();
        System.out.println(name);
        JsonArray jsonCost=jsonCard.get("reloadCost").getAsJsonArray();
        List<String>costs= new ArrayList<>();
        for(JsonElement cost:jsonCost){
            costs.add(cost.getAsString());
        }

        JsonObject baseEffectsJson=jsonCard.get("baseEffect").getAsJsonObject();
        JsonObject advancedEffectsJson=jsonCard.get("advancedEffect").getAsJsonObject();
        Effect effectsList=parseBaseEffects(baseEffectsJson,gson);

        Effect effectsListAdvanced=parseBaseEffects(advancedEffectsJson,gson);
        wcToAdd.setAdvancedEffect(effectsListAdvanced);
        wcToAdd.setName(name);
        wcToAdd.setBaseEffect(effectsList);
        wcToAdd.setReloadCost(costs);

        return wcToAdd;
    }

    public  List<WeaponCard> parseWeaponCards(String jsonFile){
        //creates the gson parser

        Gson gson = new Gson();
        JsonArray json = gson.fromJson(jsonFile,JsonArray.class) ;
        List<WeaponCard> wcToReturn=new ArrayList<>();
        for (JsonElement el: json){
            WeaponCard wcToAdd= new WeaponCard();
            JsonObject jsonCard = el.getAsJsonObject();
            String name=jsonCard.get("NAME").getAsString();
            System.out.println(name);
            JsonArray jsonCost=jsonCard.get("reloadCost").getAsJsonArray();
            List<String>costs= new ArrayList<>();
            for(JsonElement cost:jsonCost){
                costs.add(cost.getAsString());
            }

            JsonObject baseEffectsJson=jsonCard.get("baseEffect").getAsJsonObject();
            JsonObject advancedEffectsJson=jsonCard.get("advancedEffect").getAsJsonObject();
            Effect effectsList=parseBaseEffects(baseEffectsJson,gson);

            Effect effectsListAdvanced=parseBaseEffects(advancedEffectsJson,gson);
            wcToAdd.setAdvancedEffect(effectsListAdvanced);
            wcToAdd.setName(name);
            wcToAdd.setBaseEffect(effectsList);
            wcToAdd.setReloadCost(costs);
            wcToReturn.add(wcToAdd);
        }
        return wcToReturn;
    }
    private Effect parseBaseEffects(JsonObject effect,Gson gson) {
        Effect eff =new Effect();

        System.out.println(effect);
        eff.setGlobal(effect.get("isGlobal").getAsBoolean());


        eff.setMarks(gson.fromJson(effect.get("marks"), HashMap.class));
        eff.setDamage(gson.fromJson(effect.get("damage"),HashMap.class));
        try {
            for (JsonElement oe : effect.get("optionalEffects").getAsJsonArray()) {
                eff.getOptionalEffects().add(gson.fromJson(effect.get("optionalEffects"), OptionalEffect.class));
            }
        }catch(Exception e){

        }
        eff.setCanMoveShooter(effect.get("isGlobal").getAsBoolean());
        List<String> costs = new ArrayList<>();
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
            eff.setCost(costs);
            eff.setStrategy(getStrategyByName(strategy.get("type").getAsString(),param));}
        return eff;

    }

        private AbstractTargetStrategy getStrategyByName (String name,int param){
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
                    toReturn=new VortexCannonStrategy(param, match);
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
                    toReturn = new AdjacentStrategy(param , match);
                    break;
                case "LaserRifleStrategy":
                    toReturn = new IgnoreWallStrategy();
                    break;
                case "MeleeStrategy":
                    //toReturn = new MeleeStrategy( param, match);
                    break;
                case "AdjacentDifferentTilesStrategy":
                    toReturn = new AdjacentDifferentTilesStrategy(param,match);
                    break;
                case "HellionStrategy":
                    toReturn = new HellionStrategy(param,match);
                    break;
                default:
                    break;
            }
            return toReturn;
        }



}
