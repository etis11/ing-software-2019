package Test;

import Model.AmmoCard;
import Model.NormalTile;
import Model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NormalTileTest {

    private NormalTile normalTile;

    @BeforeEach
    void initTile(){
        normalTile = new NormalTile();
    }
    /**
     * Tests if the empty constructor works. All the reference to the other tile should be null and should not be present
     * the ammo
     */
    @Test
    void correctInit(){
        normalTile = new NormalTile();

        assertNull(normalTile.getNorthTile(), () -> "ERROR: the north tile should be null");
        assertNull(normalTile.getEastTile(), () -> "ERROR: the east tile should be null");
        assertNull(normalTile.getSouthTile(), () -> "ERROR: the south tile should be null");
        assertNull(normalTile.getWestTile(), () -> "ERROR: the west tile should be null");
        assertFalse(normalTile.isPresentAmmoCard(), () -> "ERROR: the west tile should not be present");
    }

    /**
     * Test if the costrucor with the parameters works. All the parameters should be in correctly linked to the tile.
     * The tile should not contain any ammo.
     */
    @Test
    void correctInit2(){
        Tile north = new normalTile();
        Tile east = new normalTile();
        Tile south = new normalTile();
        Tile west = new normalTile();

        normalTile = new NormalTile(north, east, south, west);

        assertSame(north, normalTile.getNorthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(east, normalTile.getEastTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(south, normalTile.getSouthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(west, normalTile.getWestTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertFalse(normalTile.isPresentAmmoCard(), () -> "ERROR: the west tile should not be present");

    }

    /**
     * Test if the ammo that is put in the tile is the same that will be picked up
     */
    @Test
    void insertionTest(){
        AmmoCard ammo = new AmmoCard();
        normalTile.putAmmoCard(ammo);
        assertSame(ammo, normalTile.pickUpAmmoCard(), () -> "ERROR: the tile that has been picked up is not the same that has been put in")
    }

    /**
     * Tests that a double insertion rises an exception.
     */
    @Test
    void doubleInsertionTest(){

    }


}
