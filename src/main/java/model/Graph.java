package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Graph used by the dijkstra alghoritm
 */
public class Graph {
    private List<Tile> vertexes;
    private List<Edge> edges;

    public Graph(List<Tile> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public Graph() {
        this.vertexes = new ArrayList<Tile>();
        this.edges = new ArrayList<Edge>();
    }

    public List<Tile> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }


}