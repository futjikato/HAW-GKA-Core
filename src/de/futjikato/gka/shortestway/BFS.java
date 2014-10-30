package de.futjikato.gka.shortestway;

import de.futjikato.gka.JGraphView;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.io.IOException;
import java.util.*;

public class BFS extends AbstractShortestWay {

    private HashMap<String, Integer> mapping = new HashMap<String, Integer>();

    private Graph<String, DefaultEdge> g;

    private int max;

    public static void main(String[] argv) throws IOException {
        BFS bfs = new BFS();
        bfs.init(argv[0]);
        List<String> way = bfs.findWay(argv[1], argv[2]);

        JGraphView view = JGraphView.getFrame(bfs.getGraph());

        if(way != null) {
            view.colorVertices("red", way);
        }
    }

    @Override
    public List<String> findWay(String nodeA, String nodeB) {
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
        List<String> way = getShorttestWay(nodeA, nodeB);
        System.out.println(way);
        return way;
    }

    private List<String> getShorttestWay(String nodeA, String nodeB) {
        int i = 0;
        // create list of nodes to visit next
        String next = nodeB;
        String lastNext = next;
        List<String> shortestWay = new LinkedList<String>();
        int cmax = max;

        do {
            shortestWay.add(next);
            lastNext = next;

            // get target node
            Set<DefaultEdge> edges = g.edgesOf(next);
            for (DefaultEdge e : edges) {
                String target = g.getEdgeTarget(e);
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

                if(next.equals(nodeA)) {
                    shortestWay.add(nodeA);
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
        mapping.put(nodeA, i);
        // create list of nodes to visit next
        List<String> nextList = new LinkedList<String>();
        // add start node to list
        nextList.add(nodeA);

        // as long as there are unvisted nodes ...
        do {
            // increment i
            i++;

            // for each node in the list of nodes to visit next ..
            Set<String> neighbors = new HashSet<String>();
            for(String oneOfCurrent : nextList) {
                // collect all neighbors in a set
                Set<DefaultEdge> edges = g.edgesOf(oneOfCurrent);
                for(DefaultEdge e : edges) {
                    String target = g.getEdgeTarget(e);
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
            nextList = new LinkedList<String>();
            // mark all new nodes that are not yet marked and add them to the next-visit list
            for(String n : neighbors) {
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
