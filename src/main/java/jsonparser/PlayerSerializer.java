package jsonparser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Player;
import model.PlayerBoard;
import model.PowerUpCard;
import model.WeaponCard;
import model.clientModel.SemplifiedPlayer;

import java.lang.reflect.Type;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSerializer implements JsonSerializer<Player> {

    Player currentPlayer;



    private static final ThreadLocal<Set<Player>> cache = new ThreadLocal<Set<Player>>() {
        @Override
        protected Set<Player> initialValue() {
            return new HashSet<>();
        }
    };

    public void setCurrentPlayer(Player p) {
        currentPlayer = p;
    }

    public void resetCurrentPlayer(){
        currentPlayer = null;
    }

    @Override
    public JsonElement serialize(Player player, Type type, JsonSerializationContext jsonSerializationContext) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", player.getName());
        if (isPresent(player))
            return jsonObject;


        jsonObject.addProperty("remainingMoves", player.getRemainingMoves());

        jsonObject.addProperty("numWeaponCard", player.getNumWeapons());

        final JsonElement weaponCards;
        if (player == currentPlayer){
             weaponCards = jsonSerializationContext.serialize(player.getWeapons().toArray(new WeaponCard[0]), WeaponCard[].class);
        }
        else{
            weaponCards = jsonSerializationContext.serialize(player.getEmptyWeapons().toArray(new WeaponCard[0]), WeaponCard[].class);
        }
        jsonObject.add("weaponCards", weaponCards);

        jsonObject.addProperty("numPowerUps", player.getNumPowerUps());

        if (player == currentPlayer){
            final JsonElement powerUps = jsonSerializationContext.serialize(player.getPowerUps().toArray(new PowerUpCard[0]), PowerUpCard[].class);
            jsonObject.add("powerUps", powerUps);
        }

        if (player.getTile() != null)
            jsonObject.addProperty("tile", player.getTile().getID());

        final JsonElement playerBoard = jsonSerializationContext.serialize(player.getPlayerBoard(), PlayerBoard.class);
        jsonObject.add("playerBoard", playerBoard);

        return jsonObject;
    }


    private boolean isPresent(final Player p) {
        if(cache.get().contains(p))
            return true;
        cache.get().add(p);
        return false;

    }

    public void resetSet(){
        cache.get().clear();
    }
}
