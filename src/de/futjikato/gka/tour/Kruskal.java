package de.futjikato.gka.tour;

import org.jgrapht.Graph;

import java.util.*;

public class Kruskal {

    private final Graph<VisitableVertex, VisitableWeightedEdge> graph;

    public Kruskal(Graph<VisitableVertex, VisitableWeightedEdge> graph) {
        this.graph = graph;
    }

    public List<VisitableWeightedEdge> createMinimalSPanningTree(Set<VisitableVertex> vertexSet) {
        List<VisitableWeightedEdge> sortedEdgeList = createInput(vertexSet);
        List<VisitableWeightedEdge> mst = new LinkedList<VisitableWeightedEdge>();

        for(VisitableWeightedEdge edge : sortedEdgeList) {
            if(cycleCheck(mst, edge)) {
                mst.add(edge);
            }
        }

        // reset visitation
        unvisitEverything();

        return mst;
    }

    public List<VisitableWeightedEdge> createMinimalSPanningTree() {
        return createMinimalSPanningTree(graph.vertexSet());
    }

    private List<VisitableWeightedEdge> createInput(Set<VisitableVertex> vertexSet) {
        List<VisitableWeightedEdge> sortedEdgeList = new ArrayList<VisitableWeightedEdge>();

        // cannot use graph.edgeSet because we may just want a mst from a subset of vertices
        for(VisitableVertex v : vertexSet) {
            Set<VisitableWeightedEdge> vEdges = graph.edgesOf(v);
            for(VisitableWeightedEdge ve : vEdges) {
                if(!ve.isVisited()) {
                    VisitableVertex s = graph.getEdgeSource(ve);
                    VisitableVertex t = graph.getEdgeTarget(ve);

                    // check if target and source are part of subset
                    if(vertexSet.contains(s) && vertexSet.contains(t)) {
                        insertedSorted(sortedEdgeList, ve);
                        ve.visit();
                    }
                }
            }
        }

        return sortedEdgeList;
    }

    private void insertedSorted(List<VisitableWeightedEdge> list, VisitableWeightedEdge edge) {
        double newEdgeWeight = graph.getEdgeWeight(edge);

        for(int i = 0 ; i < list.size() ; i++) {
            VisitableWeightedEdge current = list.get(i);
            if(graph.getEdgeWeight(current) > newEdgeWeight) {
                list.add(i, edge);
                return;
            }
        }

        list.add(edge);
    }

    private void unvisitEverything() {
        for(VisitableWeightedEdge edge : graph.edgeSet()) {
            edge.unvisit();
        }

        for(VisitableVertex vertex : graph.vertexSet()) {
            vertex.unvisit();
        }
    }

    private boolean cycleCheck(List<VisitableWeightedEdge> mst, VisitableWeightedEdge edge) {
        for(VisitableVertex vertex : graph.vertexSet()) {
            vertex.unvisit();
        }

        VisitableVertex start = graph.getEdgeSource(edge);
        VisitableVertex end = graph.getEdgeTarget(edge);

        Stack<VisitableVertex> stack = new Stack<VisitableVertex>();
        start.visit();
        stack.add(start);

        while(!stack.isEmpty()) {
            VisitableVertex current = stack.pop();
            if(current == end) {
                // cycle found
                return false;
            }

            Set<VisitableWeightedEdge> edges = graph.edgesOf(current);
            for(VisitableWeightedEdge edgeOfCurrent : edges) {
                if(mst.contains(edgeOfCurrent)) {
                    VisitableVertex toAdd = graph.getEdgeSource(edgeOfCurrent);
                    if(toAdd == current) {
                        toAdd = graph.getEdgeTarget(edgeOfCurrent);
                    }
                    if(!toAdd.isVisited()) {
                        stack.add(toAdd);
                        toAdd.visit();
                    }
                }
            }
        }

        // no cycle
        return true;
    }
}
