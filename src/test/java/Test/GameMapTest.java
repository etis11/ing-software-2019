package Test;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GameMapTest {

    private GameMap gameMap;
    static private String pathMap1;
    static private String pathMap2;
    static private String pathMap3;
    static private String pathMap4;


    @BeforeAll
    static void initPaths(){
        pathMap1 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "map1.json";
        pathMap2 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "map2.json";
        pathMap3 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "map3.json";
        pathMap4 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "map4.json";
    }

    @BeforeEach
    void initGameMap(){
        gameMap = new GameMap();
    }


    /**
     * Tests if the json of the maps can be de-serialised
     */
    @Test
    void correctJsonFileLoad(){
        assertDoesNotThrow(() -> GameMap.loadMap(pathMap1));
        assertDoesNotThrow(() -> GameMap.loadMap(pathMap2));
        assertDoesNotThrow(() -> GameMap.loadMap(pathMap3));
        assertDoesNotThrow(() -> GameMap.loadMap(pathMap4));

    }

    @Test
    void correctRoomInsertion(){
        Room r = new Room();
        try{
            gameMap.addRoom(r);
        }
        catch (NullPointerException n) {
            System.out.println(n.getMessage());
            fail(()-> "The room shouldnt be null");
        }

        assertSame(r, gameMap.getRooms().get(0), ()-> "ERROR: the rooms should be the same");
    }

    @Test
    void correctRegenPointInsertion(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            gameMap.addRegenPoint("red", red);
            gameMap.addRegenPoint("blue", blue);
            gameMap.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, gameMap.getRedRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(blue, gameMap.getBlueRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, gameMap.getYellowRegenPoint(), ()->"ERROR: The regen points should be the same");

    }

    @Test
    void correctRegenPointInsertionParametric(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            gameMap.addRegenPoint("red", red);
            gameMap.addRegenPoint("blue", blue);
            gameMap.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, gameMap.getRegenPoint("red"), ()->"ERROR: The regen points should be the same");
        assertSame(blue, gameMap.getRegenPoint("blue"), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, gameMap.getRegenPoint("yellow"), ()->"ERROR: The regen points should be the same");
    }

    @Test
    void insertionExceptions(){
        Tile t = new Tile();
        String s = "red";
        assertThrows(IllegalArgumentException.class, ()-> gameMap.addRegenPoint(null, t),
                ()-> "ERROR: The method should throw an exception because the color is null");
        assertThrows(IllegalArgumentException.class, ()-> gameMap.addRegenPoint(s, null),
                ()-> "ERROR: the method should throw an exception because the tile is null");
    }

    @Test
    void gettingRegenPointsExceptions(){
        assertThrows(NullPointerException.class, ()-> gameMap.getRedRegenPoint());
        assertThrows(NullPointerException.class, ()-> gameMap.getBlueRegenPoint());
        assertThrows(NullPointerException.class, ()-> gameMap.getYellowRegenPoint());

        assertThrows(NullPointerException.class, ()-> gameMap.getRegenPoint("red"));
        assertThrows(NullPointerException.class, ()-> gameMap.getRegenPoint("blue"));
        assertThrows(NullPointerException.class, ()-> gameMap.getRegenPoint("yellow"));
    }
}
