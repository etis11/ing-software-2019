package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph used by the dijkstra algorithm
 */
public class Graph {
    private List<Tile> vertexes;
    private List<Edge> edges;

    public Graph(List<Tile> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    /**
     * Constructor of the class
     */
    public Graph() {
        this.vertexes = new ArrayList<Tile>();
        this.edges = new ArrayList<Edge>();
    }

    /**
     * Method that gets Vertexes of a graph
     * @return list of tile-vertexes
     */
    public List<Tile> getVertexes() {
        return vertexes;
    }

    /**
     * Method that gets Edges of a graph
     * @return list of Edges
     */
    public List<Edge> getEdges() {
        return edges;
    }


}