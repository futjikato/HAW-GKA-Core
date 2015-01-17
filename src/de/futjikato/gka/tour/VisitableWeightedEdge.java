package de.futjikato.gka.tour;

import de.futjikato.gka.WeightNameEdge;

public class VisitableWeightedEdge extends WeightNameEdge {

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
