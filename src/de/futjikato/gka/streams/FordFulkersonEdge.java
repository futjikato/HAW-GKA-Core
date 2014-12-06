package de.futjikato.gka.streams;

import org.jgrapht.graph.DefaultWeightedEdge;

public class FordFulkersonEdge extends DefaultWeightedEdge {

    private double stream = 0;

    public double getStream() {
        return stream;
    }

    protected void setStream(double stream) {
        this.stream = stream;
    }
}
