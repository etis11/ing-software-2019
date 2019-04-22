package Test;

import Model.GameMap;
import Model.Room;
import Model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class GameMapTest {

    private GameMap map;

    @BeforeEach
    void initGameMap(){
        map = new GameMap();
    }

    @Test
    void correctRoomInsertion(){
        Room r = new Room();
        try{
            map.addRoom(r);
        }
        catch (NullPointerException n) {
            System.out.println(n.getMessage());
            fail(()-> "The room shouldnt be null");
        }

        assertSame(r, map.getRooms().get(0), ()-> "ERROR: the rooms should be the same");
    }

    @Test
    void correctRegenPointInsertion(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            map.addRegenPoint("red", red);
            map.addRegenPoint("blue", blue);
            map.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, map.getRedRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(blue, map.getBlueRegenPoint(), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, map.getYellowRegenPoint(), ()->"ERROR: The regen points should be the same");

    }

    @Test
    void correctRegenPointInsertionParametric(){
        Tile red = new Tile();
        Tile blue = new Tile();
        Tile yellow = new Tile();

        try{
            map.addRegenPoint("red", red);
            map.addRegenPoint("blue", blue);
            map.addRegenPoint("yellow", yellow);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "ERROR: One of the tile has been inserted in the wrong way");
        }

        assertSame(red, map.getRegenPoint("red"), ()->"ERROR: The regen points should be the same");
        assertSame(blue, map.getRegenPoint("blue"), ()->"ERROR: The regen points should be the same");
        assertSame(yellow, map.getRegenPoint("yellow"), ()->"ERROR: The regen points should be the same");
    }

    @Test
    void insertionExceptions(){
        Tile t = new Tile();
        String s = "red";
        assertThrows(NullPointerException.class, ()-> map.addRegenPoint(null, t),
                ()-> "ERROR: The method should throw an exception because the color is null");
        assertThrows(NullPointerException.class, ()-> map.addRegenPoint(s, null),
                ()-> "ERROR: the method should throw an exception because the tile is null");
    }

    @Test
    void gettingRegenPointsExceptions(){
        assertThrows(NullPointerException.class, ()-> map.getRedRegenPoint());
        assertThrows(NullPointerException.class, ()-> map.getBlueRegenPoint());
        assertThrows(NullPointerException.class, ()-> map.getYellowRegenPoint());

        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("red"));
        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("blue"));
        assertThrows(NullPointerException.class, ()-> map.getRegenPoint("yellow"));
    }
}
