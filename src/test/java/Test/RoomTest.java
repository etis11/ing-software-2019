package Test;

import model.Player;
import model.Room;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private Room room;

    /**
     * Creates an empty room
     */
    @BeforeEach
    void initRoom() {
        room = new Room();
    }

    /**
     * Checks that the new tile is in the room and that the tile now contains a reference to that room
     */
    @Test
    void correctInsertion() {
        Tile tileToAdd = new Tile();
        try {
            room.addTile(tileToAdd);
        } catch (NullPointerException n) {
            System.out.println(n.getMessage());
            fail(() -> "Error in the insertion");
        }
        assertSame(room, tileToAdd.getRoom(), () -> "ERROR: the room and the room in the tile should be the same");
        assertSame(tileToAdd, room.getTiles().get(0), () -> "ERROR: the tile should be the same tile in the room");

    }

    /**
     * test that the addTile method throws an exception if the given tile is null
     */
    @Test
    void nullTileInsertion() {
        Tile tileToAdd = null;
        assertThrows(IllegalArgumentException.class, () -> {
            room.addTile(tileToAdd);
        }, () -> "ERROR: the method should have raised an exception, since the tile is null");
    }

    /**
     * test the insertion of the player. the player inserted and the player in the room should be the same
     */
    @Test
    void playerInsertion() {
        Player p = new Player();
        Tile t = new Tile();
        t.addPlayer(p);
        try {
            room.addTile(t);
        } catch (NullPointerException n) {
            System.out.println(n.getMessage());
            fail(() -> "ERROR: something in the insertion went wrong");
        }

        assertSame(p, room.getPlayersInRoom().get(0), () -> "ERROR: the players should be the sam");
    }
}
