package de.futjikato.gka.tour;

import de.futjikato.gka.Vertex;

public class VisitableVertex extends Vertex<Vertex> {

    private boolean visited = false;

    public VisitableVertex(String name) {
        super(name);
    }

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
