package de.futjikato.gka;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

import java.util.LinkedList;
import java.util.List;

public class GraphFactory {

    private List<Edge> edges = new LinkedList<Edge>();

    private boolean isDirectional = false;

    private boolean isWeighted = false;

    public void addEdge(Edge edge) {
        if(edge.getType() != null && edge.getType().equals(Edge.DirectionType.DIRECTED)) {
            isDirectional = true;
        }

        if(edge.getWeight() != 0) {
            isWeighted = true;
        }

        edges.add(edge);
    }

    public Graph getGraph() {
        Graph graph;
        if(isDirectional && isWeighted) {
            graph = new DirectedWeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        } else if(isWeighted) {
            graph = new WeightedPseudograph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        } else if(isDirectional) {
            graph = new DirectedPseudograph<String, DefaultEdge>(DefaultEdge.class);
        } else {
            graph = new Pseudograph<String, DefaultEdge>(DefaultEdge.class);
        }

        for(Edge edge : edges) {
            // add vertices
            if(!graph.containsVertex(edge.getNodeA())) {
                graph.addVertex(edge.getNodeA());
            }
            if(edge.getNodeB() != null) {
                if(!graph.containsVertex(edge.getNodeB())) {
                    graph.addVertex(edge.getNodeB());
                }

                // add edge
                if(isDirectional && edge.getType().equals(Edge.DirectionType.UNDIRECTED )) {
                    graph.addEdge(edge.getNodeA(), edge.getNodeB());
                    graph.addEdge(edge.getNodeB(), edge.getNodeA());
                } else {
                    graph.addEdge(edge.getNodeA(), edge.getNodeB());
                }
            }
        }

        return graph;
    }
}
