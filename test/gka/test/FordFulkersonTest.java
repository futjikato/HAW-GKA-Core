package gka.test;

import de.futjikato.gka.GraphGenerator;
import de.futjikato.gka.Main;
import de.futjikato.gka.streams.FordFulkerson;
import de.futjikato.gka.streams.FordFulkersonEdge;
import de.futjikato.gka.streams.FordFulkersonVertex;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FordFulkersonTest {

    private static final int TEST_RUNS = 50;
    private static final int TEST_VERTICES = 300;
    private static final int TEST_EDGES = 2000;

    @Test
    public void testTimes() {
        GraphGenerator<FordFulkersonVertex, FordFulkersonEdge> generator = new GraphGenerator<FordFulkersonVertex, FordFulkersonEdge>() {
            @Override
            public Graph<FordFulkersonVertex, FordFulkersonEdge> createGraph() {
                return new DirectedWeightedPseudograph<FordFulkersonVertex, FordFulkersonEdge>(new EdgeFactory<FordFulkersonVertex, FordFulkersonEdge>() {
                    @Override
                    public FordFulkersonEdge createEdge(FordFulkersonVertex vertex, FordFulkersonVertex v1) {
                        return new FordFulkersonEdge();
                    }
                });
            }

            @Override
            public FordFulkersonVertex createVertex(int no) {
                return new FordFulkersonVertex(String.format("v%d", no));
            }

            @Override
            public FordFulkersonEdge createEdge(FordFulkersonVertex source, FordFulkersonVertex target) {
                source.connect(target);
                target.reverseConnect(source);
                return new FordFulkersonEdge();
            }

            @Override
            public double createEdgeWeight() {
                return 10d + ((Math.random() > 0.5d) ? 2 : 0);
            }
        };

        for(int i = 0 ; i < TEST_RUNS ; i++) {
            Graph<FordFulkersonVertex, FordFulkersonEdge> g = generator.generate(TEST_VERTICES, TEST_EDGES, true);
            generator.networkify(new FordFulkersonVertex("q"), new FordFulkersonVertex("s"));

            FordFulkerson fordFulkerson = new FordFulkerson(g);
            double flow = fordFulkerson.calculateFlow("q", "s");

            Assert.assertTrue("An error occured.", flow > 0);
            System.out.println(String.format("Result : %f", flow));
        }
    }

    @Test
    public void testGraph4() {
        try {
            Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile("./graphs/graph4.gka", FordFulkerson.createGraphFactory());

            FordFulkerson fordFulkerson = new FordFulkerson(graph);
            double flow = fordFulkerson.calculateFlow("q", "s");

            Assert.assertEquals("Flow incorrect.", 26d, flow, 0d);
        } catch (IOException e) {
            Assert.fail("IOException thrown");
            e.printStackTrace();
        }
    }

    @Test
    public void testSmallNet() {
        try {
            Graph<FordFulkersonVertex, FordFulkersonEdge> graph = Main.getGraphFromFile("./graphs/smallnet.gka", FordFulkerson.createGraphFactory());

            FordFulkerson fordFulkerson = new FordFulkerson(graph);
            double flow = fordFulkerson.calculateFlow("q", "s");

            Assert.assertEquals("Flow incorrect.", 4d, flow, 0d);
        } catch (IOException e) {
            Assert.fail("IOException thrown");
            e.printStackTrace();
        }
    }
}
