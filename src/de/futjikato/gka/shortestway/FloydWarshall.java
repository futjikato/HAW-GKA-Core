package de.futjikato.gka.shortestway;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.JGraphView;
import de.futjikato.gka.Main;
import de.futjikato.gka.Vertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.util.*;

public class FloydWarshall implements ShortestWayAlgo {

    private static Double INFINITE = -1d;

    private Graph graph;

    private Map<Integer, Map<Integer, Double>> distanceMatrix = new HashMap<Integer, Map<Integer, Double>>();

    private Map<Integer, Vertex> translationMap = new HashMap<Integer, Vertex>();

    public static void main(String argv[]) throws IOException {
        FloydWarshall floydWarshall = new FloydWarshall();
        floydWarshall.graph = Main.getGraphFromFile(argv[0], new GraphFactory<Vertex>() {
            @Override
            public Vertex createVertex(String name) {
                return new Vertex(name);
            }
        });

        List<Vertex> way = floydWarshall.findWay(argv[1], argv[2]);
        System.out.println(way);
        JGraphView view = JGraphView.getFrame(floydWarshall.graph);

        if(way != null) {
            view.colorVertices("red", way);
        }
    }

    @Override
    public List<Vertex> findWay(String nodeA, String nodeB) {
        initalize();
        System.out.println(translationMap);
        System.out.println("+++++++++++++++++++++++++");
        System.out.println(distanceMatrix);
        System.out.print("+++++++++++++++++++++++++\n\n");

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
                        }
                    }
                }
            }

            System.out.println(distanceMatrix);
        }
        return null;
    }

    @Override
    public boolean isValidRequest(String nodeA, String nodeB) {
        return false;
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
}
