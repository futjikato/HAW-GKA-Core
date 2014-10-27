package de.futjikato.gka.shortestway;

import de.futjikato.gka.Main;
import org.jgrapht.Graph;

import java.io.File;
import java.io.IOException;

public abstract class AbstractShortestWay implements ShortestWayAlgo {

    private Graph graph;

    public void init(String filename) throws IOException {
        File graphFile = new File(filename);

        if(!graphFile.exists()) {
            throw new RuntimeException("Graphfile not found.");
        }

        graph = Main.getGraphFromFile(filename);
    }

    public Graph getGraph() {
        return graph;
    }

}
