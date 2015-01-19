package gka.test;

import de.futjikato.gka.GraphGenerator;
import de.futjikato.gka.Main;
import de.futjikato.gka.streams.EdmondsKarp;
import de.futjikato.gka.streams.FordFulkerson;
import de.futjikato.gka.streams.FordFulkersonEdge;
import de.futjikato.gka.streams.FordFulkersonVertex;
import de.futjikato.gka.tour.MinimumSpanningTreeHeuristic;
import de.futjikato.gka.tour.VisitableVertex;
import de.futjikato.gka.tour.VisitableWeightedEdge;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category gka.test
 */
public class TourenTest {

    private static final int TEST_RUNS = 50;
    private static final int TEST_VERTICES = 800;
    private static final int TEST_EDGES = 300000;

    @Test
    public void comparison() {
        GraphGenerator<VisitableVertex, VisitableWeightedEdge> generator = new GraphGenerator<VisitableVertex, VisitableWeightedEdge>() {
            @Override
            public Graph<VisitableVertex, VisitableWeightedEdge> createGraph() {
                return new DirectedWeightedPseudograph<VisitableVertex, VisitableWeightedEdge>(new EdgeFactory<VisitableVertex, VisitableWeightedEdge>() {
                    @Override
                    public VisitableWeightedEdge createEdge(VisitableVertex vertex, VisitableVertex v1) {
                        return new VisitableWeightedEdge();
                    }
                });
            }

            @Override
            public VisitableVertex createVertex(int no) {
                return new VisitableVertex(String.format("v%d", no));
            }

            @Override
            public VisitableWeightedEdge createEdge(VisitableVertex source, VisitableVertex target) {
                source.connect(target);
                return new VisitableWeightedEdge();
            }

            @Override
            public double createEdgeWeight() {
                // weight is 10 to 14
                return 10d + (Math.random() * 4);
            }
        };

        long msthTotalTime = 0l;
        long nearestTotalTime = 0l;
        System.out.println("Start:");
        for(int i = 0 ; i < TEST_RUNS ; i++) {
            Graph<VisitableVertex, VisitableWeightedEdge> g = generator.generate(TEST_VERTICES, TEST_EDGES);

            if(!(g instanceof WeightedGraph)) {
                throw new IllegalArgumentException("Graph must be weighted.");
            }

            WeightedGraph<VisitableVertex, VisitableWeightedEdge> wg = (WeightedGraph<VisitableVertex, VisitableWeightedEdge>) g;


            long ms = System.currentTimeMillis();
            MinimumSpanningTreeHeuristic msth = new MinimumSpanningTreeHeuristic(wg);
            List<VisitableWeightedEdge> tour = msth.createTour();
            long msthTime = System.currentTimeMillis() - ms;
            msthTotalTime += msthTime;
            int mstTourLength = 0;
            for(VisitableWeightedEdge edge : tour) {
                mstTourLength += g.getEdgeWeight(edge);
            }

            ms = System.currentTimeMillis();
            /**
             * RUN NEAREST VERTEX ALGO HERE
             */
            long karpTime = System.currentTimeMillis() - ms;
            nearestTotalTime += karpTime;
            int nvTourLength = 0;
            for(VisitableWeightedEdge edge : tour) {
                nvTourLength += g.getEdgeWeight(edge);
            }

            Assert.assertEquals("Difference in tour length", mstTourLength, nvTourLength, 0d);
        }
        System.out.println("\nDone!");

        System.out.println(String.format("AVG time for MST Heuristic : %dms", msthTotalTime / TEST_RUNS));
        System.out.println(String.format("AVG time for Nearest Vertex : %dms", nearestTotalTime / TEST_RUNS));
    }

}
