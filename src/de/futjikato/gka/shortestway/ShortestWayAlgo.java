package de.futjikato.gka.shortestway;

import de.futjikato.gka.Vertex;

public interface ShortestWayAlgo {

    public java.util.List<Vertex> findWay(String nodeA, String nodeB);

    public boolean isValidRequest(String nodeA, String nodeB);

}
