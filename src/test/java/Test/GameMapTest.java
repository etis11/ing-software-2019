package Test;

import Model.GameMap;
import Model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
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

}
