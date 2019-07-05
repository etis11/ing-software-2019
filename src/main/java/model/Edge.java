package model;

/**
 * Represents and edge on the dijkstra graph
 */
public class Edge {
    private final String id;
    private final Tile source;
    private final Tile destination;
    private final int weight;

    /**
     * Constructor of the class
     * @param id binds each edge with an ID
     * @param source is the starting Tile
     * @param destination is the arrival tile
     */
    public Edge(String id, Tile source, Tile destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = 1;
    }

    /**
     * Getter of the id of a Tile
     * @return string containing ID of acertain Tile
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the destination starting from a chosen tile
     * @return destination Tile
     */
    public Tile getDestination() {
        return destination;
    }

    /**
     * gets starting Tile
      * @return starting Tile
     */
    public Tile getSource() {
        return source;
    }

    /**
     * Gets the "weight" from a tile to another. By default the weight from adjacent
     * tiles is always 1
     * @return the "weight" between 2 given tiles
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Classic method that returns the string of starting and ending tile
     * @return
     */
    @Override
    public String toString() {
        return source + " " + destination;
    }


}
