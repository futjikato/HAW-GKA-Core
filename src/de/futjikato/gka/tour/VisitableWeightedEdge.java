package de.futjikato.gka.tour;

import org.jgrapht.graph.DefaultWeightedEdge;

public class VisitableWeightedEdge extends DefaultWeightedEdge {

    private boolean visited = false;

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        visited = true;
    }

    public void unvisit() {
        visited = false;
    }
}
