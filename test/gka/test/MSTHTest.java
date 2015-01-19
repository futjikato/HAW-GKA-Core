package gka.test;

import de.futjikato.gka.Main;
import de.futjikato.gka.tour.MinimumSpanningTreeHeuristic;
import de.futjikato.gka.tour.VisitableVertex;
import de.futjikato.gka.tour.VisitableWeightedEdge;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author moritzspindelhirn
 * @todo Documentation
 * @category gka.test
 */
public class MSTHTest {

    @Test
    public void testOnGraph5() {
        try {
            Graph<VisitableVertex, VisitableWeightedEdge> graph = Main.getGraphFromFile("./graphs/graph5.gka", MinimumSpanningTreeHeuristic.createGraphFactory());

            if(!(graph instanceof WeightedGraph)) {
                throw new IllegalArgumentException("Graph must be weighted.");
            }
            WeightedGraph<VisitableVertex, VisitableWeightedEdge> wGraph = (WeightedGraph<VisitableVertex, VisitableWeightedEdge>) graph;


            MinimumSpanningTreeHeuristic msth = new MinimumSpanningTreeHeuristic(wGraph);
            List<VisitableWeightedEdge> tour = msth.createTour();

            int length = 0;
            for(VisitableWeightedEdge edge : tour) {
                length += graph.getEdgeWeight(edge);
            }

            Assert.assertEquals("Tour incomplete.", 9, tour.size());
            Assert.assertEquals("Tour length incorrect.", 26, length);
        } catch (IOException e) {
            Assert.fail("IOException thrown");
            e.printStackTrace();
        }
    }

}
