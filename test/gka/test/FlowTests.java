package gka.test;

import de.futjikato.gka.GraphGenerator;
import de.futjikato.gka.streams.EdmondsKarp;
import de.futjikato.gka.streams.FordFulkerson;
import de.futjikato.gka.streams.FordFulkersonEdge;
import de.futjikato.gka.streams.FordFulkersonVertex;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

public class FlowTests {

    private static final int TEST_RUNS = 5;
    private static final int TEST_VERTICES = 800;
    private static final int TEST_EDGES = 300000;

    @Test
    public void comparison() {
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
                if(no == 1) {
                    return new FordFulkersonVertex("q");
                } else if(no == 2) {
                    return new FordFulkersonVertex("s");
                } else {
                    return new FordFulkersonVertex(String.format("v%d", no));
                }
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

        long fordTotalTime = 0l;
        long karpTotalTime = 0l;
        System.out.println("Start:");
        for(int i = 0 ; i < TEST_RUNS ; i++) {
            Graph<FordFulkersonVertex, FordFulkersonEdge> g = generator.generate(TEST_VERTICES, TEST_EDGES);

            long ms = System.currentTimeMillis();
            FordFulkerson fordFulkerson = new FordFulkerson(g);
            double fordFlow = fordFulkerson.calculateFlow("q", "s");
            long fordTime = System.currentTimeMillis() - ms;
            fordTotalTime += fordTime;

            ms = System.currentTimeMillis();
            EdmondsKarp edmondsKarp = new EdmondsKarp(g);
            double karpFlow = edmondsKarp.calculateFlow("q", "s");
            long karpTime = System.currentTimeMillis() - ms;
            karpTotalTime += karpTime;

            System.out.print(String.format(".(%f/%f)", fordFlow, karpFlow));
            Assert.assertEquals("Difference in flow", karpFlow, fordFlow, 0d);
        }
        System.out.println("\nDone!");

        System.out.println(String.format("AVG time for FordFulkerson : %dms", fordTotalTime));
        System.out.println(String.format("AVG time for EdmondsKarp : %dms", karpTotalTime));
    }

}
