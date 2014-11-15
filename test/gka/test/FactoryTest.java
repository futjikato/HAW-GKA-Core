package gka.test;

import de.futjikato.gka.EdgeEntity;
import de.futjikato.gka.GraphFactory;
import org.jgrapht.Graph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest {

    private GraphFactory factory;

    private EdgeEntity createEdge(String nodeA, String nodeB, int weight, boolean directed) {
        EdgeEntity edge = new EdgeEntity();
        edge.setNodeA(nodeA);
        edge.setNodeB(nodeB);

        if(directed) {
            edge.setType(EdgeEntity.DirectionType.DIRECTED);
        } else {
            edge.setType(EdgeEntity.DirectionType.UNDIRECTED);
        }

        if(weight != 0) {
            edge.setWeight(weight);
        }

        return edge;
    }

    @Before
    public void setUp() {
        factory = new GraphFactory();
    }

    @Test
    public void testFactorySimple() {
        factory.addEdge(createEdge("a", "b", 0, false));
        factory.addEdge(createEdge("b", "c", 0, false));
        factory.addEdge(createEdge("c", "a", 0, false));
        factory.addEdge(createEdge("c", "b", 0, false));
        factory.addEdge(createEdge("d", "e", 0, false));
        factory.addEdge(createEdge("a", "e", 0, false));

        Graph g = factory.createGraph();
        Assert.assertTrue("Edge without weight and undirected creates something different then Pseudograph.", g instanceof Pseudograph);
    }

    @Test
    public void testFactoryWeighted() {
        factory.addEdge(createEdge("a", "b", 0 , false));
        factory.addEdge(createEdge("b", "c", 10, false));
        factory.addEdge(createEdge("c", "a", 0 , false));
        factory.addEdge(createEdge("c", "b", 20, false));
        factory.addEdge(createEdge("d", "e", 0 , false));
        factory.addEdge(createEdge("a", "e", 30, false));

        Graph g = factory.createGraph();
        Assert.assertTrue("Edge with weight and undirected creates something different then SimpleWeightedGraph.", g instanceof WeightedPseudograph);
    }

    @Test
    public void testFactoryDirected() {
        factory.addEdge(createEdge("a", "b", 0, true));
        factory.addEdge(createEdge("b", "c", 0, true));
        factory.addEdge(createEdge("c", "a", 0, true));

        Graph g = factory.createGraph();
        Assert.assertTrue("Directed edges creates something different then SimpleWeightedGraph.", g instanceof DirectedPseudograph);
    }

    @Test
    public void testFactoryDirectedWeighted() {
        factory.addEdge(createEdge("a", "b", 10, true));
        factory.addEdge(createEdge("b", "c", 0, true));
        factory.addEdge(createEdge("c", "a", 20, true));
        factory.addEdge(createEdge("c", "b", 0, true));
        factory.addEdge(createEdge("d", "e", 30, true));
        factory.addEdge(createEdge("a", "e", 0, true));

        Graph g = factory.createGraph();
        Assert.assertTrue("Complex edges create something different then SimpleWeightedGraph.", g instanceof DirectedWeightedPseudograph);
    }
}