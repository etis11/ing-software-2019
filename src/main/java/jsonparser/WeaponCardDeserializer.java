package jsonparser;

import com.google.gson.*;
import model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeaponCardDeserializer implements JsonDeserializer<WeaponCard> {

    private Match match;

    public WeaponCardDeserializer (Match match){
        this.match = match;
    }

    @Override
    public WeaponCard deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        jsonObject obj = jsonElement.getAsJsonObject();
        String name = obj.get("NAME").getAsString();
        String[] reloadCost = jsonDeserializationContext.deserialize(obj.get("reloadCost"), String[].class);
        Effect[] baseEffect = jsonDeserializationContext.deserialize(obj.get("baseEffect"), Effect[].class);
        List<Effect> baseEffectLinked = new LinkedList<>(baseEffect);
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

            JsonArray baseEffectsJson=jsonCard.get("baseEffect").getAsJsonArray();
            JsonArray advancedEffectsJson=jsonCard.get("advancedEffect").getAsJsonArray();
            List<Effect> effectsList=parseBaseEffects(baseEffectsJson);

            List<Effect> effectsListAdvanced=parseBaseEffects(advancedEffectsJson);
            wcToAdd.setAdvancedEffect(effectsListAdvanced);
            wcToAdd.setName(name);
            wcToAdd.setBaseEffect(effectsList);
            wcToAdd.setReloadCost(costs);
            wcToReturn.add(wcToAdd);
        }
        return wcToReturn;
    }
    private List<Effect> parseBaseEffects(JsonArray effects){
        List<Effect> toReturn=new ArrayList<>();
        for(JsonElement eff:effects){
            JsonObject effect = eff.getAsJsonObject();
            System.out.println(effect);
            String type=effect.get("type").getAsString();
            List<String>costs= new ArrayList<>();
            try{
                JsonArray jsonCost=effect.get("cost").getAsJsonArray();
                for(JsonElement cost:jsonCost){
                    costs.add(cost.getAsString());
                }
            }catch (Exception e){

            }
            int damage=0;
            int mark=0;
            int steps=0;
            boolean global;
            boolean optional;
            String movType="";
            Effect toAdd=null;
            switch (type) {
                case "Damage":
                    damage = effect.get("damage").getAsInt();
                    //global = effect.get("isGlobal").getAsBoolean();
                    //  optional =effect.get("isOptional").getAsBoolean();
                    toAdd=new DamageEffect(damage,false,false);
                    break;
                case "Mark":
                    mark=effect.get("marks").getAsInt();
                    //    global = effect.get("isGlobal").getAsBoolean();
                    //  optional =effect.get("isOptional").getAsBoolean();
                    toAdd=new MarksEffect(mark,false,false);
                    break;
                case "Movement":
                    movType=effect.get("movementType").getAsString();
                    //      global = effect.get("isGlobal").getAsBoolean();
                    //  optional =effect.get("isOptional").getAsBoolean();
                    steps=effect.get("steps").getAsInt();
                    toAdd=new MovementEffect(steps,movType,false,false);
                    break;
                default:
                    break;
            }
            JsonObject strategy=effect.get("strategy").getAsJsonObject();
            int param=0;
            if(!strategy.get("param").isJsonNull()){
                param=strategy.get("param").getAsInt();
            }
            if(toAdd!=null){
            toAdd.setCost(costs);
            toAdd.setStrategy(getStrategyByName(strategy.get("type").getAsString(),param));}
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    private AbstractTargetStrategy getStrategyByName(String name,int param){
        AbstractTargetStrategy toReturn=null;
        switch (name){
            case "SeeStrategy":
                toReturn=new SeeStrategy(match);
                break;
            case "FixedDistanceStrategy":
                toReturn=new FixedDistanceStrategy(param,match);
                break;
            case "RoomStrategy":
                toReturn=new RoomStrategy(match);
                break;
            case "VortexCannonStrategy":
                //toReturn=new VortexCannonStrategy(param, match);
                break;
            case "DontSeeStrategy":
                //toReturn = new DontSeeStrategy(match);
                break;
            case "FlameThrowerStrategy":
                toReturn = new FlameThrowerStrategy();
                break;
            case "BBQStrategy":
                //toReturn = new BBQStrategy(param,match);
                break;
            case "TractorBeamStrategy":
                //toReturn = new TractorBeamStrategy(param, match);
                break;
            case "AdjacentStrategy":
                //toReturn = new AdjacentStrategy(param , match);
                break;
            case "LaserRifleStrategy":
                toReturn = new IgnoreWallStrategy();
                break;
            case "MeleeStrategy":
                //toReturn = new MeleeStrategy( param, match);
                break;
            default:
                break;
        }
        return toReturn;
    }

}
