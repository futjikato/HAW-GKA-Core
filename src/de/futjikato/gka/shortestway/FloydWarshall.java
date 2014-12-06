package de.futjikato.gka.shortestway;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.JGraphView;
import de.futjikato.gka.Main;
import de.futjikato.gka.Vertex;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.util.*;

public class FloydWarshall implements ShortestWayAlgo {

    private static Double INFINITE = -1d;

    private Graph graph;

    private Map<Integer, Map<Integer, Double>> distanceMatrix = new HashMap<Integer, Map<Integer, Double>>();

    private Map<Integer, Vertex> translationMap = new HashMap<Integer, Vertex>();

    private Map<Integer, Map<Integer, Integer>> transitMap = new HashMap<Integer, Map<Integer, Integer>>();

    private Double lastDistance;

    public FloydWarshall(Graph graph) {
        this.graph = graph;
    }

    public static void main(String argv[]) throws IOException {
        Graph graph = Main.getGraphFromFile(argv[0], new GraphFactory<Vertex, DefaultWeightedEdge>() {
            @Override
            public Vertex createVertex(String name) {
                return new Vertex(name);
            }

            @Override
            public EdgeFactory<Vertex, DefaultWeightedEdge> getEdgeFactory() {
                return new EdgeFactory<Vertex, DefaultWeightedEdge>() {
                    @Override
                    public DefaultWeightedEdge createEdge(Vertex vertex, Vertex v1) {
                        return new DefaultWeightedEdge();
                    }
                };
            }
        });
        FloydWarshall floydWarshall = new FloydWarshall(graph);

        List<Vertex> way = floydWarshall.findWay(argv[1], argv[2]);
        JGraphView view = JGraphView.getFrame(floydWarshall.graph);

        if(way != null) {
            view.colorVertices("red", way);
        }
    }

    @Override
    public List<Vertex> findWay(String nodeA, String nodeB) {
        initalize();

        int max = graph.vertexSet().size();
        for(int iteration = 0 ; iteration < max ; iteration++) {
            List<Integer> freezeRows = new LinkedList<Integer>();
            List<Integer> freezeCells = new LinkedList<Integer>();

            for(int rowCount = 0 ; rowCount < max ; rowCount++) {
                for(int cellCount = 0 ; cellCount < max ; cellCount++) {
                    if(rowCount == iteration) {
                        // handle complete row
                        if(distanceMatrix.get(rowCount).get(cellCount).equals(INFINITE)) {
                            freezeCells.add(cellCount);
                        }
                    } else {
                        if (cellCount == iteration) {
                            // handle cell
                            if(distanceMatrix.get(rowCount).get(cellCount).equals(INFINITE)) {
                                freezeRows.add(rowCount);
                            }
                            // cell iteration here
                            break;
                        }
                    }
                }
            }

            for(int rowCount = 0 ; rowCount < max ; rowCount++) {
                for(int cellCount = 0 ; cellCount < max ; cellCount++) {
                    if(
                        // not in current iteration row
                        rowCount != iteration && cellCount != iteration &&
                        // not in diagonal
                        rowCount != cellCount &&
                        // not in free rows
                        !freezeRows.contains(rowCount)  && !freezeCells.contains(cellCount)
                    ) {
                        double rowSum = getVal(iteration, cellCount);
                        double cellSum = getVal(rowCount, iteration);
                        double newSum = rowSum + cellSum;
                        double old = distanceMatrix.get(rowCount).get(cellCount);

                        if(old == INFINITE || old > newSum) {
                            distanceMatrix.get(rowCount).put(cellCount, newSum);
                            addWaypoint(rowCount, cellCount, iteration);
                        }
                    }
                }
            }
        }

        int nodeAIndex = -1;
        int nodeBIndex = -1;
        for(int key : translationMap.keySet()) {
            if(translationMap.get(key).equals(nodeA)) {
                nodeAIndex = key;
            } else if(translationMap.get(key).equals(nodeB)) {
                nodeBIndex = key;
            }
        }

        if(nodeAIndex == -1 || nodeBIndex == -1) {
            System.err.println("Start or Endnode not found.");
            return null;
        }

        lastDistance = distanceMatrix.get(nodeAIndex).get(nodeBIndex);
        if(lastDistance == -1) {
            return null;
        }

        List<Integer> intWay = getFullWay(nodeAIndex, nodeBIndex);
        intWay.add(nodeBIndex);
        List<Vertex> verticesWay = new LinkedList<Vertex>();

        for(int waypoint : intWay) {
            verticesWay.add(translationMap.get(waypoint));
        }

        return verticesWay;
    }

    @Override
    public boolean isValidRequest(String nodeA, String nodeB) {
        return false;
    }

    public Double getLastDistance() {
        return lastDistance;
    }

    /**
     * Algo Steps
     */

    private void initalize() {
        Set<Vertex> vertices = graph.vertexSet();

        // build translation map
        int max = 0;
        for(Vertex vertex : vertices) {
            distanceMatrix.put(max, new HashMap<Integer, Double>());
            translationMap.put(max++, vertex);
        }

        for(int rowCount = 0 ; rowCount < max ; rowCount++) {
            Map<Integer, Double> innerMap = distanceMatrix.get(rowCount);
            for(int cellCount = 0 ; cellCount < max ; cellCount++) {
                if(rowCount == cellCount) {
                    innerMap.put(cellCount, 0d);
                } else {
                    Vertex rowvertex = translationMap.get(rowCount);
                    Vertex cellVertex = translationMap.get(cellCount);

                    if (graph.containsEdge(rowvertex, cellVertex)) {
                        Object edgeObj = graph.getEdge(rowvertex, cellVertex);
                        if (!(edgeObj instanceof DefaultWeightedEdge)) {
                            throw new IllegalArgumentException("Graph must be weighted");
                        }
                        double edgeWeight = graph.getEdgeWeight(edgeObj);
                        innerMap.put(cellCount, edgeWeight);
                    } else {
                        innerMap.put(cellCount, INFINITE);
                    }
                }
            }
        }
    }

    private double getVal(int row, int cell) {
        double val = 0d;
        double cVal = distanceMatrix.get(row).get(cell);
        if(cVal != INFINITE) {
            val = cVal;
        }

        return val;
    }

    private void addWaypoint(int from, int to, int waypoint) {
        if(!transitMap.containsKey(from)) {
            transitMap.put(from, new HashMap<Integer, Integer>());
        }

        transitMap.get(from).put(to, waypoint);
    }

    private List<Integer> getFullWay(int from, int to) {
        List<Integer> way = new LinkedList<Integer>();

        if(transitMap.containsKey(from) && transitMap.get(from).containsKey(to)) {
            int waypoint = transitMap.get(from).get(to);
            way.addAll(getFullWay(from, waypoint));
            way.addAll(getFullWay(waypoint, to));
        } else {
            way.add(from);
        }

        return way;
    }
}
