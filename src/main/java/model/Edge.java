package model;

public class Edge  {
    private final String id;
    private final Tile source;
    private final Tile destination;
    private final int weight;

    public Edge(String id, Tile source, Tile destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = 1;
    }

    public String getId() {
        return id;
    }
    public Tile getDestination() {
        return destination;
    }

    public Tile getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}
