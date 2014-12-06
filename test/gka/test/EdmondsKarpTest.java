package gka.test;

import de.futjikato.gka.Main;
import de.futjikato.gka.streams.EdmondsKarp;
import de.futjikato.gka.streams.FordFulkerson;
import de.futjikato.gka.streams.FordFulkersonEdge;
import de.futjikato.gka.streams.FordFulkersonVertex;
import org.jgrapht.Graph;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EdmondsKarpTest {

    @Test
    public void testGraph4() {
        try {
            Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile("/home/moritz/java/HAW-GKA-Core/graphs/graph4.gka", FordFulkerson.createGraphFactory());

            EdmondsKarp edmondsKarp = new EdmondsKarp(graph);
            double flow = edmondsKarp.calculateFlow("q", "s");

            Assert.assertEquals("Flow incorrect.", 24d, flow, 0d);
        } catch (IOException e) {
            Assert.fail("IOException thrown");
            e.printStackTrace();
        }
    }

    @Test
    public void testSmallNet() {
        try {
            Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile("/home/moritz/java/HAW-GKA-Core/graphs/smallnet.gka", FordFulkerson.createGraphFactory());

            EdmondsKarp edmondsKarp = new EdmondsKarp(graph);
            double flow = edmondsKarp.calculateFlow("q", "s");

            Assert.assertEquals("Flow incorrect.", 4d, flow, 0d);
        } catch (IOException e) {
            Assert.fail("IOException thrown");
            e.printStackTrace();
        }
    }

}
