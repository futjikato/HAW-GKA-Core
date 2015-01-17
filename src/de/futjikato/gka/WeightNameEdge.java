package de.futjikato.gka;

import org.jgrapht.graph.DefaultWeightedEdge;

public class WeightNameEdge extends DefaultWeightedEdge {

    @Override
    public String toString() {
        return Double.toString(getWeight());
    }

}
