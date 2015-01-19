package gka.test;

import de.futjikato.gka.Main;
import de.futjikato.gka.tour.Kruskal;
import de.futjikato.gka.tour.MinimumSpanningTreeHeuristic;
import de.futjikato.gka.tour.VisitableVertex;
import de.futjikato.gka.tour.VisitableWeightedEdge;
import org.junit.Assert;
import org.jgrapht.Graph;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class KruskalTest {

    @Test
    public void testWithGraph3() throws IOException {
        Graph<VisitableVertex, VisitableWeightedEdge> graph = Main.getGraphFromFile("./graphs/graph5.gka", MinimumSpanningTreeHeuristic.createGraphFactory());
        Kruskal kruskal = new Kruskal(graph);
        List<VisitableWeightedEdge> mst = kruskal.createMinimalSPanningTree();

        double total = 0;
        for(VisitableWeightedEdge edge : mst) {
            total += graph.getEdgeWeight(edge);
        }

        Assert.assertEquals("Incorrect MST total weight.", 13, total, 0d);
    }

}
