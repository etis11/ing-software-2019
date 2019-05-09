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

    public JsonArray loadWeaponCards(String cardsFilePath){

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
        return gson.fromJson(jsonFile,JsonArray.class);
    }



}

