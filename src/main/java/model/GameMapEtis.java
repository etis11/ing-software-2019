package model;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.DuplicateException;
import jsonparser.GameMapDeserializer;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Contains all the reference to the rooms and spawn point of the map.
 * There should be at least one regenPoint, The rooms should at least be an empty list.
 */
public class GameMap {

    public Graph getGraph() {
        return graph;
    }

    private Graph graph;
    private static final Logger LOGGER = Logger.getLogger(GameMap.class.getName());

    /**
     * List of the rooms in the GameMap
     */
    private List<Room> rooms;


    private static  String filePath;

    /**
     * A map that contains all the regent points
     */
    private Map<String, Tile> regenPoints;

    /**
     * Creates a game map with no rooms and with no regen points
     */
    public GameMap(){
        rooms = new ArrayList<>();
        regenPoints = new HashMap<>();
    }

    /**
     * creates a game map associated to the json file given in the path
     * @param mapPath path of the json file
     * @return
     */
    public static GameMap loadMap(String mapPath){

        //creates the gson parser
        GsonBuilder builder = new GsonBuilder();
        setFilePath(mapPath);
        builder.registerTypeAdapter(GameMap.class, new GameMapDeserializer());
        Gson customGson = builder.create();
        BufferedReader jsonFile;
        try{
            jsonFile = new BufferedReader( new FileReader(mapPath));
        }
        catch (FileNotFoundException f){
            System.out.println(f.getMessage());
            LOGGER.log(Level.WARNING,f.getMessage(),f);
            return null;
        }

        return customGson.fromJson(jsonFile, GameMap.class);
        //return new GameMapDeserializer().deserialize(customGson.fromJson(jsonFile, JsonElement.class),null,null);

    }


    /**
     * Returns the rooms in the gameMap
     * @return a copy of the list. however the rooms are not copied. Only the list
     */
    public List<Room> getRooms() {
        return new ArrayList<>(rooms);
    }

    /**
     * Add the given room to the game map
     * @param toAdd the room that has to be added. cant be null
     */
    public void addRoom(Room toAdd) {
        if (toAdd== null) throw new IllegalArgumentException("The given room is null");
        rooms.add(toAdd);
    }

    /**
     * Gets all the players that are visible for the given player
     * @param p the player that wants to know which are the other visible players
     * @return a list of all the players seen by p, EXCEPT p
     */
    public List<Player> allVisiblePlayers(Player p)  {

        List<Player> visiblePlayers = new ArrayList<>();
        for (Tile tile: this.allVisibleTiles(p) ){
            if(!tile.getPlayers().contains(p)){
                visiblePlayers.addAll(tile.getPlayers()) ;
            }
        }
        for (Player pl:p.getTile().getPlayers()){
            if(pl!=p){
                visiblePlayers.add(pl);
            }
        }
        return visiblePlayers;
    }
/**
 * Method used to return all the players that are not visible based from where the shooter is standing at
 * in the gameMap. The way the method works is by getting first the list of all players and then removing
 * all the visible players (the shooter also)
 * */
    public List<Player> allNotVisiblePlayers(Player player)  {

        List<Player> allPlayers = new LinkedList<>();
        List<Player> allNotVisiblePlayers = new LinkedList<>();
        for(Room room : this.getRooms()){
            allPlayers.addAll(room.getPlayersInRoom());
        }
        for(Player p : allPlayers){
            if(!this.allVisiblePlayers(player).contains(p)&&!p.equals(player)){
                allNotVisiblePlayers.add(p);
            }
        }
        return allNotVisiblePlayers;
    }




    /**
     * Returns all the tiles available to the chosen Player
     * @param p is the player who decides to check all the visible Tiles to him
     * @return list of all the Tiles where the player can interact(
     */
    public List<Tile> allVisibleTiles(Player p)  {
        List<Tile> visibleTiles = new ArrayList<>();

        for(Room visibRoom: visibleRooms(p)){
            visibleTiles.addAll(visibRoom.getTiles());
        }

       return  visibleTiles;
    }

    public List<Tile> VisibleTiles(Player p)  {
        List<Tile> visibleTiles = new ArrayList<>();

        for(Room visibRoom: visibleRooms(p)){
            if(!visibRoom.getTiles().contains(p.getTile())) {
                visibleTiles.addAll(visibRoom.getTiles());
            }
        }

        return  visibleTiles;
    }

    /**
     * Returns the Rooms available to Action for the chosen player
     * @param p the player that checks which Rooms are available to him during his gameTurn
     * @return all the Rooms that are visible to the player by the gameLogic
     */
    public List<Room> visibleRooms(Player p) {

        List<Room> visibRooms = new ArrayList<>();

        for (Tile tile : getAdjacentTiles(p)){
            if(!visibRooms.contains(tile.getRoom())){
                visibRooms.add(tile.getRoom());
            }

        }

        return visibRooms;
    }

    public List<Player> allAdjacentPlayers(Player p){
        List<Player> adjacendPlayers = new LinkedList<>();
        for (Tile tile: this.getAdjacentTiles(p) ){
            if(!tile.getPlayers().contains(p)){
                adjacendPlayers.addAll(tile.getPlayers()) ;
            }
        }
        return adjacendPlayers;
    }

    /**
     * Returns the tiles near the given player
     * @param p the player
     * @return all not null tiles around the player
     */
    private List<Tile> getAdjacentTiles(Player p){
        List<Tile> tiles = new ArrayList<>();
        Tile eastTile = p.getTile().getEastTile();
        Tile northTile = p.getTile().getNorthTile();
        Tile southTile = p.getTile().getSouthTile();
        Tile westTile = p.getTile().getWestTile();

        if(null!=eastTile){
            tiles.add(eastTile);
        }
        if(null!=northTile){
            tiles.add(northTile);
        }
        if(null!=southTile){
            tiles.add(southTile);
        }
        if(null!=westTile){
            tiles.add(westTile);
        }
        return tiles;
    }

    /**
     * adds the regenPoint to the game map
     * @param color the color of the regen point
     * @param regenPoint the tile
     * @throws DuplicateException If there is already a regen point with that color
     * @throws DuplicateException if that regen point is already in the game map
     * @throws IllegalArgumentException if the color or the regen point are null
     */
    public void addRegenPoint(String color, Tile regenPoint){
        if(color == null) throw  new IllegalArgumentException("The color is null");
        if(regenPoint == null) throw  new IllegalArgumentException("The regen point is null");
        if (regenPoints.containsKey(color)) throw new DuplicateException("There is already a regen point of the given color");
        if (regenPoints.containsValue(regenPoint)) throw new DuplicateException("The regen point has been already put in");
        regenPoints.put(color, regenPoint);
    }

    /**
     * Returns the red regen point
     * @return red regen point
     * @throws NullPointerException if there's not a red regen point
     */
    public Tile getRedRegenPoint(){
        Tile redRegen =  regenPoints.get("red");
        if (redRegen == null) throw new NullPointerException("There isn't a red regen point");
        return redRegen;
    }

    /**
     * Returns the blue regen point
     * @return blue regen point
     * @throws NullPointerException if it's not present
     */
    public Tile getBlueRegenPoint(){
        Tile blueRegen =  regenPoints.get("blue");
        if (blueRegen == null) throw new NullPointerException("There isn't a blue regen point");
        return blueRegen;
    }

    /**
     * Returns the yellow regen point
     * @return yellow regen point
     * @throws NullPointerException if it's not present
     */
    public Tile getYellowRegenPoint(){
        Tile yellowRegen =  regenPoints.get("yellow");
        if (yellowRegen == null) throw new NullPointerException("There isn't a yellow regen point");
        return yellowRegen;
    }

    /**
     * Returns the given color regen point
     * @param color a string that says the color of the wanted regen point
     * @return the regen point of color "color"
     * @throws IllegalArgumentException if doesn't exist the regen point of that color
     * @throws NullPointerException if the given color is null
     */
    public Tile getRegenPoint(String color){
        if(color == null) throw  new NullPointerException("The given color is null");
        Tile regenPoint = regenPoints.get(color);
        if (regenPoint == null) throw new NullPointerException("There isn't a " + color + "regen point");
        return regenPoint;
    }

    public List<Tile> getAllRegenPoints(){ return new LinkedList<>(regenPoints.values());}
/*
    public Graph<Tile, DefaultEdge> createGraph(){
        Graph<Tile, DefaultEdge> g
                = new DefaultDirectedGraph<>(DefaultEdge.class);
        for(Room room : this.getRooms()){
            for(Tile tile : room.getTiles()){
                //       System.out.println("tile id :"+tile.getID());
                if(!g.containsVertex(tile)){
                    // System.out.println("add vertex: "+tile.getID());
                    g.addVertex(tile);
                    // System.out.println("added vertex: "+tile.getID());
                }else{
                    //System.out.println("g.containsVertex : "+tile.getID());
                }
                for(Tile adjacent : tile.adjacentTiles()){
                    if(!g.containsVertex(adjacent)){
                        //   System.out.println("add vertex: "+adjacent.getID());
                        g.addVertex(adjacent);
                        // System.out.println("added vertex: "+adjacent.getID());
                    }else{
                        //System.out.println("g.containsVertex : "+adjacent.getID());
                    }
                    //System.out.println(" doing g.addEdge(tile,adjacent) "+adjacent.getID());
                    g.addEdge(tile,adjacent);
                    //System.out.println(" done g.addEdge(tile,adjacent) "+adjacent.getID());
                }
            }
        }
        return g;
    }*/

    public void createGraph(){
        this.graph = new Graph();
        int idEdge = 1;
        for(Room room : this.getRooms()){
            for(Tile tile : room.getTiles()){
                //       System.out.println("tile id :"+tile.getID());
                if(!graph.getVertexes().contains(tile)){
                    // System.out.println("add vertex: "+tile.getID());
                    graph.getVertexes().add(tile);
                    // System.out.println("added vertex: "+tile.getID());
                }else{
                    //System.out.println("g.containsVertex : "+tile.getID());
                }
                for(Tile adjacent : tile.adjacentTiles()){
                    if(!graph.getVertexes().contains(adjacent)){
                        //   System.out.println("add vertex: "+adjacent.getID());
                        graph.getVertexes().add(adjacent);
                        // System.out.println("added vertex: "+adjacent.getID());
                    }else{
                        //System.out.println("g.containsVertex : "+adjacent.getID());
                    }
                    //System.out.println(" doing g.addEdge(tile,adjacent) "+adjacent.getID());
                    graph.getEdges().add(new Edge(""+idEdge,tile,adjacent));
                    idEdge=idEdge+1;
                    //System.out.println(" done g.addEdge(tile,adjacent) "+adjacent.getID());
                }
            }
        }

    }

    public String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath1) {
        filePath = filePath1;
    }

}