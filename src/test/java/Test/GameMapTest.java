package Test;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

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
                + File.separatorChar + "maps"+File.separatorChar+"map1.json";
        pathMap2 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "maps"+File.separatorChar+"map2.json";
        pathMap3 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar + "maps"+File.separatorChar+"map3.json";
        pathMap4 = "." +File.separatorChar + "src"+ File.separatorChar + "main" + File.separatorChar + "resources"
                + File.separatorChar +"maps"+File.separatorChar+ "map4.json";
    }

    @BeforeEach
    void initGameMap(){
        gameMap = new GameMap();
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
    void allMapsHaveRedRegenPoints(){
        assertDoesNotThrow(()->GameMap.loadMap(pathMap1).getRegenPoint("red"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap2).getRegenPoint("red"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap3).getRegenPoint("red"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap4).getRegenPoint("red"));
    }

    @Test
    void allMapsHaveBlueRegenPoints(){
        assertDoesNotThrow(()->GameMap.loadMap(pathMap1).getRegenPoint("blue"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap2).getRegenPoint("blue"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap3).getRegenPoint("blue"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap4).getRegenPoint("blue"));
    }


    @Test
    void allMapsHaveYellowRegenPoints(){
        assertDoesNotThrow(()->GameMap.loadMap(pathMap1).getRegenPoint("yellow"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap2).getRegenPoint("yellow"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap3).getRegenPoint("yellow"));
        assertDoesNotThrow(()->GameMap.loadMap(pathMap4).getRegenPoint("yellow"));
    }


    @Test
    void allMapsWalkable(){

        assertTrue(isMapWalkable(pathMap1), ()-> "ERROR: map1 has not the right number of tiles");
        assertTrue(isMapWalkable(pathMap2), ()-> "ERROR: map2 has not the right number of tiles");
        assertTrue(isMapWalkable(pathMap3), ()-> "ERROR: map3 has not the right number of tiles");
        assertTrue(isMapWalkable(pathMap4), ()-> "ERROR: map4 has not the right number of tiles");

    }

    private static boolean isMapWalkable(String path){
        GameMap gameMap = GameMap.loadMap(path);
        Tile current;
        Tile toPut;
        Stack<Tile> stack = new Stack<>();
        int numOfTiles = 0;
        Map<Tile, Boolean> encounteredTiles = new HashMap<>();
        String[] directions = {"north", "east", "south", "west"};

        Tile startingTile = gameMap.getRedRegenPoint();

        //walking in the map
        stack.push(startingTile);
        encounteredTiles.put(startingTile, true);
        while(!stack.empty()){
            current = stack.pop();
            //System.out.println(current.getID());
            numOfTiles += 1;
            for(String dir : directions) {
                toPut = current.getTile(dir);
                //if the tile is not null and the i didn't encountered it (so it's not in the map)
                if (toPut != null && encounteredTiles.get(toPut) == null) {
                    encounteredTiles.put(toPut, true);
                    stack.push(toPut);
                }
            }
        }
        //switch case for every map. every map has a different number of tiles. the numOftiles counted must be the one expected
       // System.out.println("\n\n" +numOfTiles + "\n\n");
        if (path.equals(pathMap1))
            return( numOfTiles == 10 );
        if (path.equals(pathMap2))
            return( numOfTiles == 11 );
        if (path.equals(pathMap3))
            return( numOfTiles == 12 );
        if (path.equals(pathMap4))
            return( numOfTiles == 11 );
        return false;
    }

    @Test
    void allRegenPointCorrect(){
        assertTrue(areAllRegenPointPresent(pathMap1), () -> "ERROR: A weapon tile is not a regen point");
        assertTrue(areAllRegenPointPresent(pathMap2), () -> "ERROR: A weapon tile is not a regen point");
        assertTrue(areAllRegenPointPresent(pathMap3), () -> "ERROR: A weapon tile is not a regen point");
        assertTrue(areAllRegenPointPresent(pathMap4), () -> "ERROR: A weapon tile is not a regen point");
    }

    /**
     * checks that every ammo tile is part of the regen points
     * @param path
     * @return
     */
    private static boolean areAllRegenPointPresent(String path){
        GameMap gameMap = GameMap.loadMap(path);
        Tile current;
        Tile toPut;
        Stack<Tile> stack = new Stack<>();
        Map<Tile, Boolean> encounteredTiles = new HashMap<>();
        String[] directions = {"north", "east", "south", "west"};
        List<Tile> weaponTiles = new LinkedList<>();
        List<Tile> regenPoints = gameMap.getAllRegenPoints();


        Tile startingTile = gameMap.getRedRegenPoint();

        //walking in the map
        stack.push(startingTile);
        encounteredTiles.put(startingTile, true);
        while(!stack.empty()){
            current = stack.pop();
            if (current.canContainWeapons()) weaponTiles.add(current);
            //System.out.println(current.getID());
            for(String dir : directions) {
                toPut = current.getTile(dir);
                //if the tile is not null and the i didn't encountered it (so it's not in the map)
                if (toPut != null && encounteredTiles.get(toPut) == null) {
                    encounteredTiles.put(toPut, true);
                    stack.push(toPut);
                }
            }
        }

        //for each weapon tile, check if it's in the regen point list
        if( weaponTiles.size() != regenPoints.size()) return false;
        //there must be 3 regen points
        if(weaponTiles.size() != 3) return false;

        //remove the tile from the regen points list
        for(Tile t: weaponTiles){
            if(!regenPoints.contains(t)) return false;
            regenPoints.remove(t);
        }

        return true;
    }

    @Test
    void jGraphTest(){
        GameMap map = GameMap.loadMap(pathMap1);
        System.out.println("size "+map.getRooms().size());

//duhet te shtohet kur te behen lidhjet e tile-ve gjate inicializimit te lojes
        map.createGraph();
        Graph g = map.getGraph();
        System.out.println("map created");
        System.out.println("vertex size "+g.getVertexes().size());
        assertTrue(g.getVertexes().contains(map.getRooms().get(0).getTiles().get(0)));
       // g.getVertexes().forEach( t -> System.out.println("id : "+t.getID()) );
        List<Tile> allTiles = new LinkedList<>();
        for(Room room : map.getRooms()){
            allTiles.addAll(room.getTiles());
        }
        Tile t2 = allTiles.stream().filter(t -> t.getID()==2).findFirst().get();
        Tile t6 = allTiles.stream().filter(t -> t.getID()==6).findFirst().get();
        Tile t7 = allTiles.stream().filter(t -> t.getID()==7).findFirst().get();
        Tile t5 = allTiles.stream().filter(t -> t.getID()==5).findFirst().get();
        Tile t0 = allTiles.stream().filter(t -> t.getID()==0).findFirst().get();
        Tile t1 = allTiles.stream().filter(t -> t.getID()==1).findFirst().get();
        Tile t10 = allTiles.stream().filter(t -> t.getID()==10).findFirst().get();
        Tile t4 = allTiles.stream().filter(t -> t.getID()==4).findFirst().get();

        DijkstraAlgorithm dijkstraShortestPath;
        dijkstraShortestPath = new DijkstraAlgorithm(g);


        List<Integer> ids = new LinkedList<>();
   //     GraphPath<Tile, DefaultEdge> graphPath = dijkstraShortestPath.getPath(t1, t10);
   //     for ( Tile tile : graphPath.getVertexList()){
   //         ids.add(tile.getID());
   //         System.out.println("id : "+ids);
   //     }

        for ( Tile tile : g.getVertexes()){
            ids.add(tile.getID());
           // System.out.println("id : "+ids);
            for(Tile tile2 : g.getVertexes()){
                if(!tile2.equals(tile)){
             //       System.out.println("tile ID: "+tile.getID()+" tile2 ID: "+tile2.getID());
                    dijkstraShortestPath.execute(tile);
                    assertTrue(dijkstraShortestPath.getPath(tile2).size()>=0,"dijkstraShortestPath.getPathWeight(tile,tile2)>=0");
                }
            }
        }

        dijkstraShortestPath.execute(t4);
        System.out.println("La distanza tra t4-t6 "+dijkstraShortestPath.getPath(t6).size());
        int firstDist = dijkstraShortestPath.getPath(t6).size();
        System.out.println(firstDist);
        dijkstraShortestPath.execute(t1);
        assertTrue(dijkstraShortestPath.getPath(t6).size()==firstDist,"ERROR: Distances should be equal");
        System.out.println("La distanza tra t1-t10 "+dijkstraShortestPath.getPath(t10).size());



assertNotNull(dijkstraShortestPath.getPath(t6),"its not null dude");
    }

}
