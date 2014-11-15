package de.futjikato.gka.shortestway;

import de.futjikato.gka.JGraphView;

import de.futjikato.gka.Vertex;
import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.*;

public class BFS extends AbstractShortestWay {

    private HashMap<Vertex, Integer> mapping = new HashMap<Vertex, Integer>();

    private Graph<Vertex, DefaultEdge> g;

    private int max;

    public static void main(String[] argv) throws IOException {
        BFS bfs = new BFS();
        bfs.init(argv[0]);
        List<Vertex> way = bfs.findWay(argv[1], argv[2]);

        JGraphView view = JGraphView.getFrame(bfs.getGraph());

        if(way != null) {
            view.colorVertices("red", way);
        }
    }

    @Override
    public List<Vertex> findWay(String nodeA, String nodeB) {
        if(isValidRequest(nodeA, nodeB)) {
            System.err.println("Request is invalid.");
            return null;
        }

        if(nodeA.equals(nodeB)) {
            System.out.println("BFS : 0 - Target equals start vertex.");
            return null;
        }

        g = getGraph();
        buildMapping(nodeA, nodeB);

        if(max == 0) {
            System.err.println("No way found.");
            return null;
        }

        System.out.println(mapping);
        List<Vertex> way = getShorttestWay(nodeA, nodeB);
        System.out.println(way);
        return way;
    }

    @Override
    public boolean isValidRequest(String nodeA, String nodeB) {
        g = getGraph();

        if(!g.containsVertex(new Vertex(nodeA)) || !g.containsVertex(new Vertex(nodeB))) {
            return false;
        }

        return true;
    }

    private List<Vertex> getShorttestWay(String nodeA, String nodeB) {
        int i = 0;
        // create list of nodes to visit next
        Vertex next = new Vertex(nodeB);
        Vertex startNode = new Vertex(nodeA);
        Vertex lastNext = next;
        List<Vertex> shortestWay = new LinkedList<Vertex>();
        int cmax = max;

        do {
            shortestWay.add(next);
            lastNext = next;

            // get target node
            Set<DefaultEdge> edges = g.edgesOf(next);
            for (DefaultEdge e : edges) {
                Vertex target = g.getEdgeTarget(e);
                if (target.equals(next)) {
                    target = g.getEdgeSource(e);
                }
                if (target.equals(next)) {
                    continue;
                }

                if(mapping.containsKey(target)) {
                    if(mapping.get(target) < cmax) {
                        next = target;
                        cmax = mapping.get(target);
                    }
                }

                if(next.equals(startNode)) {
                    shortestWay.add(startNode);
                    Collections.reverse(shortestWay);
                    return shortestWay;
                }
            }
        } while(!next.equals(lastNext));

        return null;
    }

    private void buildMapping(String nodeA, String nodeB) {
        // initalize
        // set i to 0
        int i = 0;
        // set start node to 0
        mapping.put(new Vertex(nodeA), i);
        // create list of nodes to visit next
        List<Vertex> nextList = new LinkedList<Vertex>();
        // add start node to list
        nextList.add(new Vertex(nodeA));

        // as long as there are unvisted nodes ...
        do {
            // increment i
            i++;

            // for each node in the list of nodes to visit next ..
            Set<Vertex> neighbors = new HashSet<Vertex>();
            for(Vertex oneOfCurrent : nextList) {
                // collect all neighbors in a set
                Set<DefaultEdge> edges = g.edgesOf(oneOfCurrent);
                for(DefaultEdge e : edges) {
                    Vertex target = g.getEdgeTarget(e);
                    if(!(g instanceof DirectedGraph)) {
	                    if(target.equals(oneOfCurrent)) {
	                        target = g.getEdgeSource(e);
	                    }
                    }
                    if(target.equals(oneOfCurrent)) {
                        continue;
                    }

                    neighbors.add(target);
                }
            }

            // empty next visitation list
            nextList = new LinkedList<Vertex>();
            // mark all new nodes that are not yet marked and add them to the next-visit list
            for(Vertex n : neighbors) {
                if(!mapping.containsKey(n)) {
                    mapping.put(n, i);
                    nextList.add(n);
                }
            }

            // if the target vertex is in the list of vertices end here
            if(neighbors.contains(nodeB)) {
                System.out.println(String.format("BFS found target: %d", i));
                max = i;
                return;
            }

            // target not found
            // continue until all vertices are marked
        } while (nextList.size() > 0);
    }
}
