package model;

import java.util.*;

/**
 * This class is used to implements all the functions regarding the graphs
 */
public class DijkstraAlgorithm {

    private final List<Tile> nodes;
    private final List<Edge> edges;
    private Set<Tile> settledNodes;
    private Set<Tile> unSettledNodes;
    private Map<Tile, Tile> predecessors;
    private Map<Tile, Integer> distance;

    public DijkstraAlgorithm(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<Tile>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }


    public void execute(Tile source) {
        settledNodes = new HashSet<Tile>();
        unSettledNodes = new HashSet<Tile>();
        distance = new HashMap<Tile, Integer>();
        predecessors = new HashMap<Tile, Tile>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Tile node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Tile node) {
        List<Tile> adjacentNodes = getNeighbors(node);
        for (Tile target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + 1) {
                distance.put(target, getShortestDistance(node)
                        + 1);
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    /**
     * Returns the neighbours of the current tile in the graph
     * @param node current node
     * @return adjacents tile
     */
    private List<Tile> getNeighbors(Tile node) {
        List<Tile> neighbors = new ArrayList<Tile>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Tile getMinimum(Set<Tile> vertexes) {
        Tile minimum = null;
        for (Tile vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Tile vertex) {
        return settledNodes.contains(vertex);
    }

    /**
     * return the shortest distance between the root and the destination
     * @param destination the tile that has to be evaluated
     * @return the shortest distance
     */
    private int getShortestDistance(Tile destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Tile> getPath(Tile target) {
        LinkedList<Tile> path = new LinkedList<Tile>();
        Tile step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}