package model;

import java.util.ArrayList;
import java.util.List;

import java.util.LinkedList;

import static java.util.stream.Collectors.toCollection;

/**
 * The Tile class is the element that constitutes a GameMap. A Tile contains the reference of the adjacent tiles in the map.
 * All the adjacent tiles are different ( example: northTile and SouthTile can't both point to the same object).
 * A tile contains the players that are placed in it. A boolean field indicate if contains ammo or weapons.
 * A Tile can have a reference to a tile that is not connected, but that is only separated by a wall. The "walled" fields
 * store this reference, and can be null (for example, if the wall is one of the edges of the map, of course there isn't a
 * tile in the wall),
 */
public class Tile {

    /**
     * reference to the tile placed to the north. Can be null.
     */
    private Tile northTile;

    /**
     * reference to the tile placed to the east. Can be null.
     */
    private Tile eastTile;
    /**
     * reference to the tile placed to the south. Can be null.
     */
    private Tile southTile;

    /**
     * reference to the tile placed to the west. Can be null.
     */
    private Tile westTile;

    /**
     * list of all players that are in the tile. If the player is present, there is only one occurence.
     * Can't be null. Can be a empty list. All players in the list are different.
     */
    private LinkedList<Player> players;

    /**
     * Tells if the tile can contain ammos
     */
    private boolean ammoTile;
    /**
     * ammoCard that can be picked up by a player. This field can be null
     */
    private AmmoCard ammoCard;

    /**
     * Tells if the tile can contain weapons
     */
    private boolean weaponTile;
    /**
     * weapons that can be picked in the tile
     */
    private LinkedList<WeaponCard> weapons;

    /**
     * room in which the tile is placed
     */
    private Room room;

    /**
     * Tile behind the north wall
     */
    private Tile northWalledTile;

    /**
     * tile behind the east wall
     */
    private Tile eastWalledTile;

    /**
     * tile behind the south wall
     */
    private Tile southWalledTile;

    /**
     * tile behind the west wall
     */
    private Tile westWalledTile;

    /**
     * creates a tile with all the values set to null
     */
    public Tile(){
        northTile = null;
        eastTile = null;
        southTile = null;
        westTile = null;
        players = new LinkedList<>();
        ammoTile = false;
        ammoCard = null;
        weaponTile = false;
        weapons = null;
        room = null;
    }

    /**
     * Creates a tile given the surrounding tiles. The parameters can be null.
     * @param north north tile
     * @param east east tile
     * @param south south tile
     * @param west west tile
     * @param ammoTile enables the ammo funcionality
     * @param weaponTile enables the weapon funcionality
     */
    public Tile(Tile north, Tile east, Tile south, Tile west, boolean ammoTile, boolean weaponTile){
        northTile = north;
        eastTile = east;
        southTile = south;
        westTile = west;
        players = new LinkedList<>();
        this.ammoTile = ammoTile;
        ammoCard = null;
        this.weaponTile = weaponTile;
        weapons = new LinkedList<>();
        room = null;
    }

    /**
     * returns the tile place to the east
     * @return the east tile, can be null
     */
    public Tile getEastTile() {  return eastTile; }

    /**
     * assign to the east tile a know tile.
     * @param eastTile a tile
     */
    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    /**
     * Gets the tile behind the east wall
     * @return
     */
    public Tile getEastTileBehindWall(){ return eastWalledTile;}

    /**
     * set the tile behind the east wall
     * @param eastTile tile to be set
     */
    public void setEastTileBehindWall(Tile eastTile){ this.eastWalledTile = eastTile;}

    /**
     * returns the tile place to the west
     * @return the west tile, can be null
     */
    public Tile getWestTile() {
        return westTile;
    }

    /**
     * assign to the west tile a know tile.
     * @param westTile a tile
     */
    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    /**
     * Gets the tile behind the west wall
     * @return
     */
    public Tile getWestTileBehindWall(){ return westWalledTile;}

    /**
     * set the tile behind the west wall
     * @param westTile tile to be set
     */
    public void setWestTileBehindWall(Tile westTile){ this.westWalledTile = westTile;}

    /**
     * returns the tile place to the north
     * @return the north tile, can be null
     */
    public Tile getNorthTile() {
        return northTile;
    }

    /**
     * assign to the north tile a know tile.
     * @param northTile tile to be set
     */
    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    /**
     * Gets the tile behind the north wall
     * @return the north Tile behind a wall, can be null
     */
    public Tile getNorthTileBehindWall(){ return northWalledTile;}

    /**
     * set the tile behind the north wall
     * @param northTile tile to be set
     */
    public void setNorthTileBehindWall(Tile northTile){ this.northWalledTile = northTile;}
    /**
     * returns the tile place to the south
     * @return the south tile, can be null
     */
    public Tile getSouthTile() {
        return southTile;
    }

    /**
     * assign to the south tile a know tile.
     * @param southTile a tile
     */
    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }

    /**
     * Gets the tile behind the south wall
     * @return
     */
    public Tile getSouthTileBehindWall(){ return southWalledTile;}

    /**
     * set the tile behind the south wall
     * @param southTile tile to be set
     */
    public void setSouthTileBehindWall(Tile southTile){ this.southWalledTile = southTile;}


    /**
     * Return the tile at the given direction. Return type can be null
     * @param dir should be "north", "east", "south", "west"
     * @return a tile or null
     */
    public Tile getTile(String dir){
        switch (dir){
            case "north":
                return northTile;
            case "east":
                return eastTile;

            case "south":
                return southTile;

            case "west":
                return westTile;
            default: throw new IllegalArgumentException("The direction is neither north east south or west");
        }
    }

    /**
     * Gets the tile behind the wall of a given direction. Can be null
     * @param dir should be a string "north" "east" "south" "west"
     * @return
     */
    public Tile getTileBehindWall(String dir){
        switch (dir){
            case "north":
                return northWalledTile;
            case "east":
                return eastWalledTile;

            case "south":
                return southWalledTile;
            case "west":
                return westWalledTile;
            default: throw new IllegalArgumentException("The direction is neither north east south or west");
        }
    }

    /**
     * Returns all the tiles of a given direction. The tile in which the player is always returned
     * @param dir a string that should be "north", "east", "south", "west"
     * @return a list of all tile in a given direction
     */
    public List<Tile> getTilesInDirection(String dir) {
        if (!dir.equals("north") && !dir.equals("east") && !dir.equals("south") && !dir.equals("west")) throw new IllegalArgumentException(
                "The direction given is not north east south or west");
        if (dir == null) throw new IllegalArgumentException("The direction is null");
        Tile currentTile = this;
        List<Tile> tiles = new LinkedList<>();

        String[] directions = {"north", "east", "south", "west"};
        while(currentTile != null){
            tiles.add(currentTile);
            currentTile = currentTile.getTile(dir);
        }

        return new ArrayList<>(tiles);
    }

    /**
     * Returns a list of all the tiles in a given direction. The walls are ignored. So  A->B | C would return [A,B,C], while
     * the getTilesDirection would have returned [A,B]. This list in never empty, since the tile of the player is always
     * returned
     * @param dir can only be "north", "east", "south", "west"
     * @return a list of tiles (is never empty, the tile in the player is always in).
     */
    public  List<Tile> getTilesDirectionBehindWall(String dir) {
        if (!dir.equals("north") && !dir.equals("east") && !dir.equals("south") && !dir.equals("west")) throw new IllegalArgumentException(
                "The direction given is not north east south or west");
        if (dir == null) throw new IllegalArgumentException("The direction is null");
        Tile currentTile = this;
        Tile nextTile;
        List<Tile> tiles = new LinkedList<>();
        while(currentTile!= null){
            tiles.add(currentTile);
            nextTile = currentTile.getTile(dir);
            if (nextTile == null) nextTile = currentTile.getTileBehindWall(dir);

            currentTile = nextTile;
        }
        return new ArrayList<>(tiles);

    }


    /**
     * Gets the players that are present in this tile
     * @return list of players. can be null
     */
    public LinkedList<Player> getPlayers() {
        return players;
    }




    /**
     * Tells if the player is in the tile
     * @param p the player
     * @return  true if player is in, false otherwise
     */
    public boolean isPlayerIn(Player p){
        return players.contains(p);
    }

    public void setRoom(Room r){ this.room = r;}

    /**
     *  Tells if the tile has an ammo in it or not.
     * @return True if the ammoCard is in the tile, false if not
     */
    public boolean isPresentAmmoCard() {
        return ammoCard!=null;
    }

    /**
     * Says if an ammo che be put in the tile
     * @return True if it's an ammo tile, false if not.
     */
    public boolean canContainAmmo(){ return ammoTile;};

    /**
     * Set the ammoCard field to null and return the ammo card. The boolean field is set to null
     * @return the ammo card contained in the tile
     * @throws NullPointerException if the ammo card in it is null
     * @throws Exception if the tile cant contain ammos
     */
    public AmmoCard pickUpAmmoCard() throws NullPointerException, Exception {
        if (!ammoTile) throw new Exception("This tile is not an ammo Tile");
        if (ammoCard == null){
            throw  new NullPointerException("Cant pick up a null object");
        }

        AmmoCard toBePicked = ammoCard;
        ammoCard = null;
        return toBePicked;
    }

    /**
     * This method put's an ammo card in the tile. This can work only if the tile hasn't ammoCard in it.
     * @param ammo the ammo that has to be putted in
     * @throws NullPointerException the ammo argument is null
     * @throws Exception The tile has already an ammo card or the tile is not an ammo tile
     */
    public void putAmmoCard(AmmoCard ammo) throws NullPointerException, Exception {
        if (!ammoTile) throw  new Exception("This tile is not an ammo Tile");
        if (ammo == null) throw new NullPointerException("The argument passed is null");
        if( ammoCard != null) throw new Exception("Can't add an ammoCard card in a tile that has already an ammo card");
        this.ammoCard = ammo;
    }

    /**
     * tells if it's a weapon tile
     * @return true if it's a weapon tile
     */
    public boolean canContainWapons(){ return weaponTile;}

    /**
     * Tells if there are some weapons in the tile
     * @return true if there are weapons
     */
    public boolean arePresentAmmos(){ return weapons != null;}

    /**
     * Returns a copy of the weapons in the weapon Tile
     * @return copy of weapons
     * @throws Exception The tile is not a weapon tile
     */
    public LinkedList<WeaponCard> getWeapons() throws Exception{
        if (!weaponTile) throw new Exception("This is tile is not a weapon tile");
        return weapons.stream().map(WeaponCard::new).collect(toCollection(LinkedList::new));
    }

    /**
     * Pick up the desired weapon
     * @param desired weapon that has to be picked up
     * @return the desired weapon
     * @throws Exception the tile is not a weapon tile, cant pick up a weapon
     * @throws NullPointerException the desired weapon is null
     */
    public WeaponCard pickUpWeaponCard(WeaponCard desired) throws Exception, NullPointerException{
        WeaponCard removed;
        boolean found = false;
        if (!weaponTile) throw new Exception("this is not a weapon tile");
        if (desired== null) throw  new NullPointerException("The argument passed is null");

        for( WeaponCard w: weapons){
            if (w.equals(desired)) {
                desired = w;
                found = weapons.remove(w);}
        }

        if (!found) throw new Exception("The weapon was not in the list");

        return desired;
    }

    /**
     * Puts a weapon card in the tile
     * @param toBePut the weapon that has to be put in the tile
     * @throws Exception the tile is full
     * @throws NullPointerException The weaponCard is actually null
     */
    public void putWeaponCard(WeaponCard toBePut) throws Exception, NullPointerException{
        if (weapons.size() >= 3) throw new Exception("Can't put the weapon in the tile because it's full");
        if (toBePut == null) throw new NullPointerException("The argument passed is null");

        weapons.add(toBePut);
    }

    /**
     * returns the room of the tile
     * @return room
     */
    public Room getRoom(){
        return room;
    }

    /**
     * Tells if the given player is present
     * @param p player that is wanted to know his location
     * @return True if the player is present, false if not
     */
    public boolean isPlayerPresent(Player p){
        return players.contains(p);
    }

    /**
     * adds a player to the tile
     * @param playerToAdd player that has to be placed in
     */
    public void addPlayer(Player playerToAdd){
        players.addLast(playerToAdd);
    }

    /**
     * Remove the player from the tile
     * @param playerToRemove the player that has to be removed
     * @return the player given as param
     * @throws Exception if the player was not in the tile
     */
    public Player removePlayer(Player playerToRemove) throws Exception{
        if (! players.remove(playerToRemove)) throw new Exception("The player should have been present");
        return playerToRemove; //test
    }

    //TODO mi interessa sia minima la distanza???????
    public int distance (Player target){
        int distance=0;
        if (target == null) throw new IllegalArgumentException("The given player from which calculate distance is null");
        if (this.isPlayerIn(target)){
            return 0;
        }
        if (northTile != null){
            distance = 1 + northTile.distance(target);
        }
        if (eastTile != null && distance>1 + eastTile.distance(target)){
            distance = 1 + eastTile.distance(target);
        }
        if (southTile!= null && distance>1 + southTile.distance(target)){
            distance = 1 + southTile.distance(target);
        }
        if (westTile!=null && distance>1 + westTile.distance(target)){
            distance = 1 + westTile.distance(target);
        }

        return distance;
    }

    /**
     * Gives a list with the tiles around the current tile.
     * @return
     */
    public List<Tile> adjacentTiles(){
        List<Tile> adj = new LinkedList<>();
        if (northTile!= null) adj.add(northTile);
        if (eastTile!= null) adj.add(eastTile);
        if (southTile!= null) adj.add(southTile);
        if (westTile!= null) adj.add(westTile);

        if (adj.isEmpty()) throw new IllegalStateException("This tile is not well built, it's not connected with the map");
        return adj;
    }
}
