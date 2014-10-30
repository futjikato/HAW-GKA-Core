package de.futjikato.gka.shortestway;

public interface ShortestWayAlgo {

    public java.util.List<String> findWay(String nodeA, String nodeB);

    public boolean isValidRequest(String nodeA, String nodeB);

}
