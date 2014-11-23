package de.futjikato.gka.shortestway;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.JGraphView;
import de.futjikato.gka.Main;
import de.futjikato.gka.Vertex;
import de.futjikato.gka.reader.GKALexer;
import de.futjikato.gka.reader.GKAParser;
import de.futjikato.gka.shortestway.dijkstra.DijkstraVertex;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.util.*;

public class Dijkstra implements ShortestWayAlgo {

    private Graph graph;

    private List<DijkstraVertex> orderedQueue = new LinkedList<DijkstraVertex>();

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    @Override
    public List<Vertex> findWay(String nodeA, String nodeB) {
        if(!isValidRequest(nodeA, nodeB)) {
            return null;
        }

        DijkstraVertex endNode = null;
        DijkstraVertex startNode = initialize(nodeA);
        DijkstraVertex currentNode = startNode;
        orderedQueue.add(currentNode);

        while (currentNode != null && (endNode == null || !endNode.isFinished())) {
            currentNode.markFinished();
            orderedQueue.remove(currentNode);

            List<DijkstraVertex> next = currentNode.getNext();
            for(DijkstraVertex dijVertex : next) {
                Object edgeObj = graph.getEdge(currentNode, dijVertex);
                if(!(edgeObj instanceof DefaultWeightedEdge)) {
                    throw new IllegalArgumentException("Graph must be weighted");
                }
                double edgeWeight = graph.getEdgeWeight(edgeObj);
                double w = currentNode.getDistance() + edgeWeight;

                if(dijVertex.equals(nodeB)) {
                    endNode = dijVertex;
                }

                if(dijVertex.getDistance() == Integer.MAX_VALUE) {
                    dijVertex.setDistance(w);
                    dijVertex.setPredecessor(currentNode);
                }

                if(dijVertex.getDistance() > w) {
                    dijVertex.setDistance(w);
                    dijVertex.setPredecessor(currentNode);
                }

                if(orderedQueue.contains(dijVertex)) {
                    orderedQueue.remove(dijVertex);
                }
                addInOrder(dijVertex);
            }

            if(orderedQueue.size() > 0) {
                currentNode = orderedQueue.get(0);
            } else {
                currentNode = null;
            }
        }

        if(endNode == null || !endNode.isFinished()) {
            System.err.println(String.format("End node: %s ", endNode));
            return null;
        }

        currentNode = endNode;
        List<Vertex> way = new ArrayList<Vertex>();
        while(!currentNode.equals(startNode)) {
            way.add(currentNode);
            currentNode = currentNode.getPredecessor();
        }
        way.add(startNode);

        Collections.reverse(way);
        return way;
    }

    @Override
    public boolean isValidRequest(String nodeA, String nodeB) {
        Set<Vertex> all = graph.vertexSet();
        boolean foundA = false;
        boolean foundB = false;

        for(Vertex t : all) {
            if(t.equals(nodeA)) {
                foundA = true;
            }

            if(t.equals(nodeB)) {
                foundB = true;
            }
        }

        return foundA && foundB;
    }

    public static void main(String argv[]) throws IOException {
        Graph graph = Main.getGraphFromFile(argv[0], new GraphFactory<Vertex>() {
            @Override
            public Vertex createVertex(String name) {
                return new DijkstraVertex(name);
            }
        });
        Dijkstra dijkstra = new Dijkstra(graph);

        List<Vertex> way = dijkstra.findWay(argv[1], argv[2]);
        System.out.println(way);
        Vertex target = way.get(way.size() - 1);
        DijkstraVertex dijkstraTarget = (DijkstraVertex) target;
        System.out.println(String.format("Shortest way : %f", dijkstraTarget.getDistance()));
        JGraphView view = JGraphView.getFrame(dijkstra.graph);

        if(way != null) {
            view.colorVertices("red", way);
        }
    }

    /**
     * Algo Stuff
     */

    private DijkstraVertex initialize(String nodeA) {
        Set<DijkstraVertex> vertices = graph.vertexSet();
        DijkstraVertex startNode = null;
        for(DijkstraVertex vertex : vertices) {
            if(vertex.toString().equals(nodeA)) {
                vertex.setDistance(0);
                vertex.setPredecessor(vertex);
                startNode = vertex;
            } else {
                vertex.setDistance(Integer.MAX_VALUE);
            }
        }

        return startNode;
    }

    private void addInOrder(DijkstraVertex vertex) {
        for(int i = 0 ; i < orderedQueue.size() ; i++) {
            DijkstraVertex vertexi = orderedQueue.get(i);
            if(vertex.getDistance() < vertexi.getDistance()) {
                orderedQueue.add(i, vertex);
                return;
            }
        }

        orderedQueue.add(vertex);
    }
}
