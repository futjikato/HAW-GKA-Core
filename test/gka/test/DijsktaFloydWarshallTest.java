package gka.test;


import de.futjikato.gka.GraphGenerator;
import de.futjikato.gka.Vertex;
import de.futjikato.gka.shortestway.Dijkstra;
import de.futjikato.gka.shortestway.FloydWarshall;
import de.futjikato.gka.shortestway.dijkstra.DijkstraVertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DijsktaFloydWarshallTest {

    private static final int TEST_RUNS = 50;
    private static final int TEST_VERTICES = 300;
    private static final int TEST_EDGES = 2000;

    @Test
    public void DijkstraFWBigTest() {
        GraphGenerator<DijkstraVertex, DefaultWeightedEdge> generator = new GraphGenerator<DijkstraVertex, DefaultWeightedEdge>() {
            @Override
            public Graph<DijkstraVertex, DefaultWeightedEdge> createGraph() {
                return new DirectedWeightedPseudograph<DijkstraVertex, DefaultWeightedEdge>(DefaultWeightedEdge.class);
            }

            @Override
            public DijkstraVertex createVertex(int no) {
                return new DijkstraVertex(String.format("v%d", no));
            }

            @Override
            public DefaultWeightedEdge createEdge(DijkstraVertex source, DijkstraVertex target) {
                source.connect(target);
                return new DefaultWeightedEdge();
            }

            @Override
            public double createEdgeWeight() {
                return 10d + ((Math.random() > 0.5d) ? 2 : 0);
            }
        };

        long dijkstraTotal = 0l;
        long fwTotal = 0l;
        for(int i = 0 ; i < TEST_RUNS ; i++) {
            Graph<DijkstraVertex, DefaultWeightedEdge> g = generator.generate(TEST_VERTICES, TEST_EDGES);

            long dijkstraStart = System.currentTimeMillis();
            Dijkstra dijkstra = new Dijkstra(g);
            List<Vertex> dWay = dijkstra.findWay("v1", "v90");
            dijkstraTotal += System.currentTimeMillis() - dijkstraStart;

            long fwStart = System.currentTimeMillis();
            FloydWarshall floydWarshall = new FloydWarshall(g);
            List<Vertex> fwWay = floydWarshall.findWay("v1", "v90");
            fwTotal += System.currentTimeMillis() - fwStart;

            if(dWay != null && fwWay != null) {
                Vertex target = dWay.get(dWay.size() - 1);
                DijkstraVertex dijkstraTarget = (DijkstraVertex) target;
                double dijkstraDistance = dijkstraTarget.getDistance();

                double fwDistance = floydWarshall.getLastDistance();

                System.out.println(String.format("Dijkstra: %f | Floyd-Warshall: %f", dijkstraDistance, fwDistance));
                Assert.assertEquals("Dijsktra and Floyd-Warshall differs in shortest way!", dijkstraDistance, fwDistance, 0d);

                if(!dWay.equals(fwWay)) {
                    System.out.println(String.format("Different ways!\nFloyd-Warshall: %s\nDijkstra: %s", fwWay, dWay));
                }
            } else {
                if(dWay != null || fwWay != null) {
                    System.err.println(String.format("Dijkstra way : %s || Floyd-Warshall way: %s", dWay, fwWay));
                    Assert.fail("Dijkstra or Floyd-Warshall found a way while the other did not!");
                }
            }
        }

        System.out.println(String.format("Dijkstra time for %d graphs: %d ( AVG: %d )", TEST_RUNS, dijkstraTotal, (dijkstraTotal / TEST_RUNS)));
        System.out.println(String.format("Floyd-Warshall time for %d graphs: %d ( AVG: %d )", TEST_RUNS, dijkstraTotal, (dijkstraTotal / TEST_RUNS)));
    }

}
