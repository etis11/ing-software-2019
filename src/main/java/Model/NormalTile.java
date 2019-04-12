package Model;

import java.util.List;

/**
 * A NormalTile is a tile that contains an ammo (but doesn't contain any weapon). A tile can or cant have a ammoCard.
 * Is always true isPresentAmmo() <==> getAmmoCard() != null
 */
public class NormalTile extends Tile {

    /**
     * presenAmmo <==> ammoCard != null
      */

    /**
     * True if the ammo card is present, False if the ammo card is null.
     */
    private boolean presentAmmo;
    /**
     * ammoCard that can be picked up by a player. This field can be null
     */
    private AmmoCard ammoCard;

    /**
     * creates a empty tile with no reference to other tile
     */
    public NormalTile(){
        super();
        presentAmmo = false;
        ammoCard = null;
    }

    /**
     * creates a tile in relation with the other tile placed in the map. All the arguments can be null
     * @param north north tile
     * @param east  south tile
     * @param west  west tile
     * @param south south tile
     */
    public NormalTile(Tile north, Tile east, Tile west, Tile south){
        super(north, east, west, south);
        presentAmmo = false;
        ammoCard = null;
    }


    /**
     * Set the ammoCard field to null and return the ammo card. The boolean field is set to null
     * @return the ammo card contained in the tile
     * @throws NullPointerException if the ammo card in it is null
     */
    public AmmoCard pickUpAmmoCard() throws NullPointerException {
        if (ammoCard == null){
            throw  new NullPointerException("Cant pick up a null object");
        }
        AmmoCard toBePicked = ammoCard;
        presentAmmo = false;
        ammoCard = null;
        return toBePicked;
    }


    /**
     * This method put's an ammo card in the tile. This can work only if the tile hasn't ammoCard in it.
     * @param ammo the ammo that has to be putted in
     * @throws NullPointerException the ammo argument is null
     * @throws Exception The tile has already an ammo card
     */
    public void putAmmoCard(AmmoCard ammo) throws NullPointerException, Exception {
        if (ammo == null) throw new NullPointerException("The argument passed is null");
        if( ammoCard != null) throw new Exception("Can't add an ammoCard card in a tile that has already an ammo card");
        this.ammoCard = ammo;
        presentAmmo = true;
    }



}
