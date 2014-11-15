package de.futjikato.gka;

import org.jgrapht.Graph;
import org.jgrapht.graph.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class GraphFactory<T extends Vertex> {

    private List<EdgeEntity> edges = new LinkedList<EdgeEntity>();

    private boolean isWeighted = false;

    private boolean isDirectional = false;

    public void addEdge(EdgeEntity edge) {
        if(edge.getType() != null && edge.getType().equals(EdgeEntity.DirectionType.DIRECTED)) {
            isDirectional = true;
        }

        if(edge.getWeight() != 0) {
            isWeighted = true;
        }

        edges.add(edge);
    }

    public abstract T createVertex(String name);

    public Graph createGraph() {
        Graph graph;
        if(isDirectional && isWeighted) {
            graph = new DirectedWeightedPseudograph<T, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        } else if(isWeighted) {
            graph = new WeightedPseudograph<T, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        } else if(isDirectional) {
            graph = new DirectedPseudograph<T, DefaultEdge>(DefaultEdge.class);
        } else {
            graph = new Pseudograph<T, DefaultEdge>(DefaultEdge.class);
        }

        HashMap<String, T> map = new HashMap<String, T>();

        for(EdgeEntity edge : edges) {
            // add vertices
            T vertexA;
            String vertexAName = edge.getNodeA();
            if(!map.containsKey(vertexAName)) {
                vertexA = createVertex(vertexAName);
                map.put(vertexAName, vertexA);
                graph.addVertex(vertexA);
            } else {
                vertexA = map.get(vertexAName);
            }
            if(edge.getNodeB() != null) {
                T vertexB;
                String vertexBName = edge.getNodeB();
                if(!map.containsKey(vertexBName)) {
                    vertexB = createVertex(vertexBName);
                    map.put(vertexBName, vertexB);
                    graph.addVertex(vertexB);
                } else {
                    vertexB = map.get(vertexBName);
                }

                // add edge
                if(isDirectional && edge.getType().equals(EdgeEntity.DirectionType.UNDIRECTED )) {
                    graph.addEdge(vertexA, vertexB);
                    graph.addEdge(vertexB, vertexA);

                    vertexB.connect(vertexA);
                    vertexA.connect(vertexB);
                } else {
                    graph.addEdge(vertexA, vertexB);

                    vertexA.connect(vertexB);
                }
            }
        }

        return graph;
    }
}
