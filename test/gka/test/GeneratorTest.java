package gka.test;

import de.futjikato.gka.GraphGenerator;
import de.futjikato.gka.Vertex;
import de.futjikato.gka.shortestway.dijkstra.DijkstraVertex;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.DirectedWeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

public class GeneratorTest {

    @Test
    public void testGeneratorDirected() {
        GraphGenerator<Vertex, DefaultEdge> generator = new GraphGenerator<Vertex, DefaultEdge>() {
            @Override
            public Graph<Vertex, DefaultEdge> createGraph() {
                return new DirectedPseudograph<Vertex, DefaultEdge>(DefaultEdge.class);
            }

            @Override
            public Vertex createVertex(int no) {
                return new Vertex(String.format("v%d", no));
            }

            @Override
            public DefaultEdge createEdge(Vertex source, Vertex target) {
                return new DefaultEdge();
            }

            @Override
            public double createEdgeWeight() {
                return 1d;
            }
        };

        Graph<Vertex, DefaultEdge> g = generator.generate(10, 20);
        Assert.assertEquals("Invalid amount of vertices", 10, g.vertexSet().size());
        Assert.assertEquals("Invalid amount of edges", 20, g.edgeSet().size());
    }

    public void testGeneratorBig() {
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
                return Math.random() * 10 + 10;
            }
        };

        Graph<DijkstraVertex, DefaultWeightedEdge> g = generator.generate(100, 6000);
        Assert.assertEquals("Invalid amount of vertices", 100, g.vertexSet().size());
        Assert.assertEquals("Invalid amount of edges", 6000, g.edgeSet().size());
    }
}
