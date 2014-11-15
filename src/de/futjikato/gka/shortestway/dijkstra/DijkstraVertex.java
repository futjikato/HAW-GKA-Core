package de.futjikato.gka.shortestway.dijkstra;

import de.futjikato.gka.Vertex;

import java.util.LinkedList;
import java.util.List;

public class DijkstraVertex extends Vertex {

    private double distance;

    private DijkstraVertex predecessor;

    private boolean finished = false;

    public DijkstraVertex(String name) {
        super(name);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public DijkstraVertex getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(DijkstraVertex predecessor) {
        this.predecessor = predecessor;
    }

    public void markFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public List<DijkstraVertex> getNext() {
        List<Vertex> neighbors = getVertices();
        List<DijkstraVertex> next = new LinkedList<DijkstraVertex>();

        for(Vertex check : neighbors) {
            if(check instanceof DijkstraVertex) {
                DijkstraVertex dijkstraVertex = (DijkstraVertex) check;
                if(!dijkstraVertex.finished) {
                    next.add(dijkstraVertex);
                }
            }
        }

        return next;
    }
}
