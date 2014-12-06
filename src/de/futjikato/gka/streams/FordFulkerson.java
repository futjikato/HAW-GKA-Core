package de.futjikato.gka.streams;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.Main;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FordFulkerson {

    protected Graph<FordFulkersonVertex, FordFulkersonEdge> graph;

    public FordFulkerson(Graph<FordFulkersonVertex, FordFulkersonEdge> graph) {
        this.graph = graph;
    }

    public static void main(String argv[]) throws IOException {
        Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile(argv[0], FordFulkerson.createGraphFactory());

        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        double flow = fordFulkerson.calculateFlow(argv[1], argv[2]);

        if(flow < 0) {
            System.err.println(String.format("Error with code %f occurred.", flow));
        } else {
            System.out.println(String.format("Optimized flow is : %f", flow));
        }
    }

    public static GraphFactory<FordFulkersonVertex, FordFulkersonEdge> createGraphFactory() {
        return new GraphFactory<FordFulkersonVertex, FordFulkersonEdge>() {
            @Override
            public FordFulkersonVertex createVertex(String name) {
                return new FordFulkersonVertex(name);
            }

            @Override
            public EdgeFactory<FordFulkersonVertex, FordFulkersonEdge> getEdgeFactory() {
                return new EdgeFactory<FordFulkersonVertex, FordFulkersonEdge>() {
                    @Override
                    public FordFulkersonEdge createEdge(FordFulkersonVertex vertex, FordFulkersonVertex v1) {
                        vertex.connect(v1);
                        v1.reverseConnect(vertex);
                        return new FordFulkersonEdge();
                    }
                };
            }
        };
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

        // build list of marked nodes
        List<FordFulkersonVertex> markedList = new LinkedList<FordFulkersonVertex>();
        markedList.add(sourceNode);

        // run step 2
        while(!markedList.isEmpty()) {
            // get node from list of marked nodes
            FordFulkersonVertex vertex = markedList.remove(0);

            // check all for O(vi)
            List<FordFulkersonVertex> neighbors = vertex.getVertices();
            for(FordFulkersonVertex neighbor : neighbors) {
                if(!neighbor.isMarked()) {
                    // edge from vertex -> neighbor
                    FordFulkersonEdge edge = graph.getEdge(vertex, neighbor);
                    double capacity = graph.getEdgeWeight(edge);
                    if(edge.getStream() < capacity) {
                        double flow = Math.min(capacity - edge.getStream(), vertex.getDelta());
                        neighbor.setMarked(FordFulkersonVertex.DIRECTION_OUT, flow, vertex);
                        markedList.add(neighbor);
                    }
                }
            }

            // check all for I(vi)
            List<FordFulkersonVertex> reverseNeighbors = vertex.getReverseVertices();
            for(FordFulkersonVertex rNeighbor : reverseNeighbors) {
                if(!rNeighbor.isMarked()) {
                    // edge from rNeighbor -> vertex
                    FordFulkersonEdge edge = graph.getEdge(rNeighbor, vertex);
                    double capacity = graph.getEdgeWeight(edge);
                    if(edge.getStream() > 0) {
                        double flow = Math.min(edge.getStream(), vertex.getDelta());
                        rNeighbor.setMarked(FordFulkersonVertex.DIRECTION_IN, flow, vertex);
                        markedList.add(rNeighbor);
                    }
                }
            }

            // if target node is marked ..
            if(targetNode.isMarked()) {
                // optimize flow
                optimizeStream(targetNode, sourceNode);
                // reset all markings except for source and restart with step 2
                resetMarkings(sourceNode);
                markedList.clear();
                markedList.add(sourceNode);
            }
        }

        return calcFlowFromStartVertex(sourceNode);
    }

    private void optimizeStream(FordFulkersonVertex targetNode, FordFulkersonVertex sourceNode) {
        FordFulkersonVertex currentVertex = targetNode;
        double targetDelta = targetNode.getDelta();

        // walk to source
        while(!currentVertex.equals(sourceNode)) {
            // back directed edge
            if(currentVertex.getMarkingDirection() == FordFulkersonVertex.DIRECTION_IN) {
                FordFulkersonEdge ffEdge = graph.getEdge(currentVertex, currentVertex.getMarkedPartner());
                // decrement
                ffEdge.setStream(ffEdge.getStream() - targetDelta);
            }

            // forward directed edge
            if(currentVertex.getMarkingDirection() == FordFulkersonVertex.DIRECTION_OUT) {
                FordFulkersonEdge ffEdge = graph.getEdge(currentVertex.getMarkedPartner(), currentVertex);
                // increment
                ffEdge.setStream(ffEdge.getStream() + targetDelta);
            }

            currentVertex = currentVertex.getMarkedPartner();
        }
    }

    protected void resetMarkings(FordFulkersonVertex sourceNode) {
        for(FordFulkersonVertex ffVertex : graph.vertexSet()) {
            if(!ffVertex.equals(sourceNode)) {
                ffVertex.resetMarking();
            }
        }
    }

    protected double calcFlowFromStartVertex(FordFulkersonVertex sourceNode) {
        // calculate flow by making at cut with source only vs. rest
        double flow = 0;
        for(FordFulkersonVertex n : sourceNode.getVertices()) {
            flow += graph.getEdge(sourceNode, n).getStream();
        }
        for(FordFulkersonVertex rn : sourceNode.getReverseVertices()) {
            flow -= graph.getEdge(rn, sourceNode).getStream();
        }

        return flow;
    }
}
