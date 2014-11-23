package de.futjikato.gka;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public abstract class GraphGenerator<V, E> {

    private class VertexTuple {
        V a;

        V b;

        public VertexTuple(V a, V b) {
            this.a = a;
            this.b = b;
        }

        public V getA() {
            return a;
        }

        public V getB() {
            return b;
        }
    }

    private Graph<V, E> graph;

    public Graph<V, E> generate(int vertexCount, int edgeCount) {
        graph = createGraph();

        int vertices = 0;
        while(vertices < vertexCount) {
            V vertex = createVertex(vertices);
            if(!graph.containsVertex(vertex)) {
                graph.addVertex(vertex);
                vertices++;
            }
        }

        Set<V> vertexSet = graph.vertexSet();
        List<VertexTuple> cartesian;
        if(graph instanceof DirectedGraph) {
            cartesian = createCartesian(vertexSet, false);
        } else {
            cartesian = createCartesian(vertexSet, true);
        }
        Collections.shuffle(cartesian);

        if(edgeCount > cartesian.size()) {
            throw new IllegalArgumentException("Edge count exceeds maximum amount of possible edges");
        }

        int edges = 0;
        while(edges < edgeCount) {
            VertexTuple tuple = cartesian.remove(0);
            if(graph instanceof WeightedGraph) {
                E e = createEdge(tuple.getA(), tuple.getB());
                graph.addEdge(tuple.getA(), tuple.getB(), e);
                ((WeightedGraph<V, E>) graph).setEdgeWeight(e, createEdgeWeight());
            } else {
                graph.addEdge(tuple.getA(), tuple.getB(), createEdge(tuple.getA(), tuple.getB()));
            }
            edges++;
        }

        return graph;
    }

    private List<VertexTuple> createCartesian(Set<V> vertices, boolean asymmetric) {
        List<VertexTuple> tupleList = new LinkedList<VertexTuple>();
        for (V vertexA : vertices) {
            for(V vertexB : vertices) {
                if(!vertexA.equals(vertexB)) {
                    if(asymmetric && tupleList.contains(new VertexTuple(vertexB, vertexA))) {
                        continue;
                    }
                    tupleList.add(new VertexTuple(vertexA, vertexB));
                }
            }
        }

        return tupleList;
    }

    public abstract Graph<V, E> createGraph();

    public abstract V createVertex(int no);

    public abstract E createEdge(V source, V target);

    public abstract double createEdgeWeight();
}
