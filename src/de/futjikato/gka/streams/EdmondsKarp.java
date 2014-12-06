package de.futjikato.gka.streams;

import de.futjikato.gka.Main;
import org.jgrapht.Graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Because this is just an other implementation of FordFulkerson we will use the FordFulkersonVertex and
 * FordFulkersonEdge classes.
 * We can also use the createGraphFactory method from FordFulkerson.
 */
public class EdmondsKarp extends FordFulkerson {

    public EdmondsKarp(Graph<FordFulkersonVertex, FordFulkersonEdge> graph) {
        super(graph);
    }

    public static void main(String argv[]) throws IOException {
        Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile(argv[0], FordFulkerson.createGraphFactory());

        EdmondsKarp edmondsKarp = new EdmondsKarp(graph);
        double flow = edmondsKarp.calculateFlow(argv[1], argv[2]);

        if(flow < 0) {
            System.err.println(String.format("Error with code %f occurred.", flow));
        } else {
            System.out.println(String.format("Optimized flow is : %f", flow));
        }
    }

    public double calculateFlow(String source, String target) {
        // get real source node from graph
        FordFulkersonVertex sourceNode = null;
        FordFulkersonVertex targetNode = null;
        for(FordFulkersonVertex vertex : graph.vertexSet()) {
            if(vertex.equals(source)) {
                sourceNode = vertex;
            }

            if(vertex.equals(target)) {
                targetNode = vertex;
            }
        }

        // make sure source and target exist in the graph
        if(sourceNode == null || targetNode == null) {
            return -1;
        }

        // mark start node
        sourceNode.setMarked(FordFulkersonVertex.DIRECTION_UNDEF, Double.POSITIVE_INFINITY, null);

        while(true) {
            // reset marks from last run
            for(FordFulkersonVertex ffVertex : graph.vertexSet()) {
                if(!ffVertex.equals(sourceNode)) {
                    ffVertex.resetMarking();
                    ffVertex.setInspected(false);
                }
            }

            // get way
            List<FordFulkersonVertex> way = recWalkGraph(sourceNode, targetNode);
            // check for done conditions
            if(way == null) {
                break;
            }
            // determine min
            double min = Double.POSITIVE_INFINITY;
            for(FordFulkersonVertex vertex : way) {
                if(!vertex.equals(sourceNode)) {
                    FordFulkersonEdge edge = graph.getEdge(vertex.getMarkedPartner(), vertex);
                    min = Math.min(min, vertex.getDelta());
                }
            }

            // add flow for way
            for(FordFulkersonVertex vertex : way) {
                if(!vertex.equals(sourceNode)) {
                    FordFulkersonEdge edge = graph.getEdge(vertex.getMarkedPartner(), vertex);
                    edge.setStream(edge.getStream() + min);
                }
            }
        }

        return calcFlowFromStartVertex(sourceNode);
    }

    private List<FordFulkersonVertex> recWalkGraph(FordFulkersonVertex vertex, FordFulkersonVertex targetNode) {
        List<FordFulkersonVertex> list = new LinkedList<FordFulkersonVertex>();
        list.add(vertex);
        vertex.setInspected(true);

        List<FordFulkersonVertex> path = null;
        while(path == null) {
            // get next
            FordFulkersonVertex next = getMinNeighbor(vertex);
            // check for error exit condition
            if(next == null) {
                return null;
            }

            // recursive down
            if(next.equals(targetNode)) {
                path = new LinkedList<FordFulkersonVertex>();
                path.add(next);
            } else {
                path = recWalkGraph(next, targetNode);
            }

            // mark on success
            FordFulkersonEdge edge = graph.getEdge(vertex, next);
            next.setMarked(FordFulkersonVertex.DIRECTION_OUT, graph.getEdgeWeight(edge) - edge.getStream(), vertex);
        }

        list.addAll(path);
        return list;
    }

    private FordFulkersonVertex getMinNeighbor(FordFulkersonVertex vertex) {
        List<FordFulkersonVertex> neighbors = vertex.getVertices();
        double minStream = Double.POSITIVE_INFINITY;
        FordFulkersonVertex next = null;

        for(FordFulkersonVertex neighbor : neighbors) {
            if(!neighbor.isInspected()) {
                FordFulkersonEdge edge = graph.getEdge(vertex, neighbor);
                double rest = graph.getEdgeWeight(edge) - edge.getStream();

                if(rest > 0 && rest < minStream) {
                    next = neighbor;
                }
            }
        }

        return next;
    }
}
