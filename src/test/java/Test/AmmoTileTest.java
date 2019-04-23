package Test;

import model.AmmoCard;
import model.Tile;
import model.WeaponCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AmmoTileTest {

    private Tile tile;
    
    @BeforeEach
    void initTile(){
        tile = new Tile(null, null, null, null, true, false);
    }

    /**
     * Tests if the empty constructor works. All the reference to the other tile should be null and should not be present
     * the ammo
     */
    @Test
    void correctInit(){
        tile = new Tile();

        assertNull(tile.getNorthTile(), () -> "ERROR: the north tile should be null");
        assertNull(tile.getEastTile(), () -> "ERROR: the east tile should be null");
        assertNull(tile.getSouthTile(), () -> "ERROR: the south tile should be null");
        assertNull(tile.getWestTile(), () -> "ERROR: the west tile should be null");
    }

    /**
     * Test if the costrucor with the parameters works. All the parameters should be in correctly linked to the tile.
     * The tile should not contain any ammo.
     */
    @Test
    void correctInit2(){
        Tile north = new Tile();
        Tile east = new Tile();
        Tile south = new Tile();
        Tile west = new Tile();

        tile = new Tile(north, east, south, west, true, false);

        assertSame(north, tile.getNorthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(east, tile.getEastTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(south, tile.getSouthTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertSame(west, tile.getWestTile(), () -> "ERROR: the tiles should be the same. Probably is null or is in another field");
        assertFalse(tile.isPresentAmmoCard(), () -> "ERROR: Should not be present");
        assertTrue(tile.canContainAmmo(), () -> "The tile should be able to contain ammos");
        assertFalse(tile.canContainWeapons(), () -> "ERROR: The tile should not be able to contain weapons");


    }

    /**
     * Test if the ammo that is put in the tile is the same that will be picked up
     */
    @Test
    void insertionTest(){
        AmmoCard ammo = new AmmoCard();
        try{
            tile.putAmmoCard(ammo);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try{
            assertSame(ammo, tile.pickUpAmmoCard(), () -> "ERROR: the tile that has been picked up is not the same that has been put in");
        }
        catch (Exception e){
            System.out.println(e.getMessage() );
            fail("ha lanciato un'eccezione");
        }
    }

    /**
     * Tests that a double insertion rises an exception.
     */
    @Test
    void doubleInsertionTest(){
        AmmoCard ammo1 = new AmmoCard();
        AmmoCard ammo2 = new AmmoCard();
        try{
            tile.putAmmoCard(ammo1);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            fail("errore nell'inserimento");
        }

        assertThrows(Exception.class, () -> {tile.putAmmoCard(ammo2);}, () -> "ERROR: Should throw an exception since there is already an ammo card in the tile");
    }

    /**
     * Controls that is not possible to get weapons from an ammo tile
     */
    @Test
    void tryTopGetWeapons(){
        assertThrows(Exception.class, () -> tile.getWeapons(), ()-> "ERROR: Should throw an exception because it's not a weapon tile");
    }

    /**
     * Checks that is not possible to pick up weapon in an ammo tile
     */
    @Test
    void tryToPickUpWeapon(){
        WeaponCard w = new WeaponCard();
        assertThrows(Exception.class, () -> tile.pickUpWeaponCard(w), () -> "ERROR:Should throw an exception because it's not a weapon tile");
    }


}
