package jsonparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {

    public String loadWeaponCards(String cardsFilePath){

        //Gson gson = new Gson();
        // builder.create();
       BufferedReader jsonFile=null;
        String s = null;
        StringBuilder sb = new StringBuilder();
        try{
            jsonFile = new BufferedReader( new FileReader(cardsFilePath));
            while ((s = jsonFile.readLine()) != null) {
                sb.append(s).append("\n");
            }
        }
        catch (FileNotFoundException f){
            System.out.println(cardsFilePath+" does not exist");
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
finally{
            if(jsonFile!=null){
                try {
                    jsonFile.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //  Type listType = new TypeToken<List<String>>() {}.getType();
        //    List<WeaponCard>  javaArrayListFromGSON = gson.fromJson(jsonFile, new TypeToken<List<WeaponCard>>(){}.getType());
        // gson.fromJson(jsonFile,JsonArray.class);
        return sb.toString();
    }
}