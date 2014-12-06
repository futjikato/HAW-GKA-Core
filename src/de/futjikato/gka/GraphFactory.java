package de.futjikato.gka;

import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public abstract class GraphFactory<T extends Vertex, E extends DefaultEdge> {

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

    public abstract EdgeFactory<T, E> getEdgeFactory();

    public Graph<T, E> createGraph() {
        Graph<T, E> graph;
        if(isDirectional && isWeighted) {
            graph = new DirectedWeightedPseudograph<T, E>(getEdgeFactory());
        } else if(isWeighted) {
            graph = new WeightedPseudograph<T, E>(getEdgeFactory());
        } else if(isDirectional) {
            graph = new DirectedPseudograph<T, E>(getEdgeFactory());
        } else {
            graph = new Pseudograph<T, E>(getEdgeFactory());
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
                    E edge1 = graph.addEdge(vertexA, vertexB);
                    E edge2 = graph.addEdge(vertexB, vertexA);

                    if(graph instanceof WeightedGraph) {
                        WeightedGraph<T, E> wGraph = (WeightedGraph<T, E>) graph;
                        wGraph.setEdgeWeight(edge1, edge.getWeight());
                        wGraph.setEdgeWeight(edge2, edge.getWeight());
                    }

                    vertexB.connect(vertexA);
                    vertexA.connect(vertexB);
                } else {
                    E edge3 = graph.addEdge(vertexA, vertexB);

                    if(graph instanceof WeightedGraph) {
                        ((WeightedGraph<T, E>) graph).setEdgeWeight(edge3, edge.getWeight());
                    }

                    vertexA.connect(vertexB);
                }
            }
        }

        return graph;
    }
}
