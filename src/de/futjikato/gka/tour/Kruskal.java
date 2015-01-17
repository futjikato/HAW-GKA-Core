package de.futjikato.gka.tour;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Kruskal {

    private final Graph<VisitableVertex, VisitableWeightedEdge> graph;

    public Kruskal(Graph<VisitableVertex, VisitableWeightedEdge> graph) {
        this.graph = graph;
    }

    public List<VisitableWeightedEdge> createMinimalSPanningTree(Set<VisitableVertex> vertexSet) {
        List<VisitableWeightedEdge> sortedEdgeList = createInput(vertexSet);
        List<VisitableWeightedEdge> mst = new LinkedList<VisitableWeightedEdge>();

        for(VisitableWeightedEdge edge : sortedEdgeList) {
            VisitableVertex s = graph.getEdgeSource(edge);
            VisitableVertex t = graph.getEdgeTarget(edge);

            if(!s.isVisited() || !t.isVisited()) {
                mst.add(edge);
                s.visit();
                t.visit();
            }
        }

        // reset visitation
        unvisitEverything();

        return mst;
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
}
