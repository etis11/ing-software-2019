package Test;

import model.Tile;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeaponTileTest {
    private Tile tile;

    /**
     * Creates an empty weapon tile
     */
    @BeforeEach
    void initTile(){
        tile = new Tile(null, null, null, null, false, true);
    }

    /**
     * checks that the void constructor works properly
     */
    @Test
    void correctInitVoidConstructor(){
        tile = new Tile();

        assertNull(tile.getNorthTile(), () -> "ERROR: the north tile should be null");
        assertNull(tile.getEastTile(), () -> "ERROR: the east tile should be null");
        assertNull(tile.getSouthTile(), () -> "ERROR: the south tile should be null");
        assertNull(tile.getWestTile(), () -> "ERROR: the west tile should be null");
    }

    /**
     * Checks if the constructor with parameters works fine
     */
    @Test
    void correctInitRightConstructor(){
        Tile north = new Tile();
        Tile east = new Tile();
        Tile south = new Tile();
        Tile west = new Tile();

        tile = new Tile(north, east, south, west, false, true);

        assertSame(north, tile.getNorthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(east, tile.getEastTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(south, tile.getSouthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(west, tile.getWestTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertFalse(tile.isPresentAmmoCard(), () -> "ERROR: Should not be present");
        assertFalse(tile.canContainAmmo(), () -> "The tile should not be able to contain ammos");
       // assertTrue(tile.canContainWeapons(), () -> "ERROR: The tile should  be able to contain weapons");


    }

    /**
     * checks that is not possible to put too much weapons in the weapon tile
     */
    @Test
    void tooMuchWeapon(){
        for (int i = 0; i < 3; i++){
            try
            {
                tile.putWeaponCard(new WeaponCard());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        assertThrows(Exception.class, () -> {tile.putWeaponCard(new WeaponCard());}, () -> "ERROR:" +
                                                            " The insertion should throw an exception, it's too full" );

    }

    /**
     * Checks that a null object cant be put in the weapon's list
     */
     @Test
    void nullWeapon(){
         assertThrows(Exception.class, () -> {tile.putWeaponCard(null);}, () -> "ERROR: Trying to put a null object" );

     }

    /**
     * Checks that the weapon that is putin the tile is equal to the weapon returned by the method
     * This test doesnt work properly, the weapon class should be implemented
     */
     void correctInsertion(){
        WeaponCard w = new WeaponCard();
        try{
            tile.putWeaponCard(w);
        }
        catch (Exception e) {System.out.println(e.getMessage());};

        try{

            assertEquals(w,  tile.getWeapons().get(0));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail(()-> "An error occurred during the test");
        }
     }


}
