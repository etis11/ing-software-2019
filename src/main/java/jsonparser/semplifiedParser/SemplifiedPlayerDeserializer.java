package jsonparser.semplifiedParser;

import com.google.gson.JsonDeserializer;
import model.clientModel.SemplifiedGame;
import model.clientModel.SemplifiedPlayer;

public class SemplifiedPlayerDeserializer implements JsonDeserializer<SemplifiedPlayer> {

    private final SemplifiedGame game;

    public SemplifiedPlayerDeserializer(SemplifiedGame game){
        this.game = game;
    }
    
}
