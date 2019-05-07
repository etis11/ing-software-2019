package jsonParser;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonFileReader {



    public  List<WeaponCard> loadWeaponCards(String cardsFilePath){
        //creates the gson parser
  //      GsonBuilder builder = new GsonBuilder();
    //    builder.registerTypeAdapter(ArrayList.class, new WeaponCardDeserializer());
        Gson gson = new Gson();
        // builder.create();
        BufferedReader jsonFile;
        try{
            jsonFile = new BufferedReader( new FileReader(cardsFilePath));
        }
        catch (FileNotFoundException f){
            System.out.println(cardsFilePath+" does not exist");
            return null;
        }
      //  Type listType = new TypeToken<List<String>>() {}.getType();
    //    List<WeaponCard>  javaArrayListFromGSON = gson.fromJson(jsonFile, new TypeToken<List<WeaponCard>>(){}.getType());
        JsonArray  json=gson.fromJson(jsonFile,JsonArray.class);
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
          //  System.out.print("AAAAAAAAAAAA"+ advancedEffectsJson.size());

            List<Effect> effectsListAdvanced=parseBaseEffects(advancedEffectsJson);
            wcToAdd.setAdvancedEffect(effectsListAdvanced);
            wcToAdd.setNAME(name);
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
            toAdd.setCost(costs);
            toAdd.setStrategy(getStrategyByName(strategy.get("type").getAsString(),param));
            toReturn.add(toAdd);
        }
        return toReturn;
    }

    private AbstractTargetStrategy getStrategyByName(String name,int param){
        AbstractTargetStrategy toReturn=null;
        switch (name){
            case "SeeStrategy":
                toReturn=new SeeStrategy();
                break;
            case "FixedDistanceStrategy":
                toReturn=new FixedDistanceStrategy(param);
                break;
            case "RoomStrategy":
                toReturn=new RoomStrategy();
                break;
            case "VortexCannonStrategy":
                toReturn=new VortexCannonStrategy(param);
                break;
            case "DontSeeStrategy":
                toReturn = new DontSeeStrategy();
                break;
            case "FlameThrowerStrategy":
                toReturn = new FlameThrowerStrategy();
                break;
            case "BBQStrategy":
                toReturn = new BBQStrategy(param);
                break;
            case "TractorBeamStrategy":
                toReturn = new TractorBeamStrategy(param);
                break;
            case "AdjacentStrategy":
                toReturn = new AdjacentStrategy(param);
                break;
            case "LaserRifleStrategy":
                toReturn = new LaserRifleStrategy();
                break;
            case "MeleeStrategy":
                toReturn = new MeleeStrategy(param);
                break;
            default:
                break;
        }
        return toReturn;
    }

}

