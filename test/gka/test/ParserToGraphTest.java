package gka.test;

import de.futjikato.gka.Main;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.Pseudograph;
import org.jgrapht.graph.WeightedPseudograph;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ParserToGraphTest {

    @Test
    public void testGraph1() throws IOException {
        Graph graph = Main.getGraphFromFile("./graphs/graph1.gka");

        Assert.assertTrue("graph1.gka wrong grpah class", graph instanceof DirectedPseudograph);

        Set<String> vertexSet = new HashSet<String>();
        vertexSet.add("a");
        vertexSet.add("b");
        vertexSet.add("c");
        vertexSet.add("d");
        vertexSet.add("e");
        vertexSet.add("f");
        vertexSet.add("g");
        vertexSet.add("h");
        vertexSet.add("i");
        vertexSet.add("j");
        vertexSet.add("k");
        Assert.assertEquals("graph1.gka vertices set wrong.", vertexSet, graph.vertexSet());

        Assert.assertEquals("graph1.gka edge set wrong.", 35, graph.edgeSet().size());
    }

    @Test
    public void testGraph2() throws IOException {
        Graph graph = Main.getGraphFromFile("./graphs/graph2.gka");

        Assert.assertTrue("graph2.gka wrong graph class", graph instanceof Pseudograph);

        Set<String> vertexSet = new HashSet<String>();
        vertexSet.add("a");
        vertexSet.add("b");
        vertexSet.add("c");
        vertexSet.add("d");
        vertexSet.add("e");
        vertexSet.add("f");
        vertexSet.add("g");
        vertexSet.add("h");
        vertexSet.add("i");
        vertexSet.add("j");
        vertexSet.add("k");
        Assert.assertEquals("graph2.gka vertices set wrong.", vertexSet, graph.vertexSet());

        Assert.assertEquals("graph2.gka edge set wrong.", 38, graph.edgeSet().size());
    }

    @Test
    public void testGraph3() throws IOException {
        Graph graph = Main.getGraphFromFile("./graphs/graph3.gka");

        Assert.assertTrue("graph3.gka wrong graph class", graph instanceof WeightedPseudograph);

        Set<String> vertexSet = new HashSet<String>();
        vertexSet.add("Kiel");
        vertexSet.add("Husum");
        vertexSet.add("Lüneburg");
        vertexSet.add("Lübeck");
        vertexSet.add("Soltau");
        vertexSet.add("Hameln");
        vertexSet.add("Paderborn");
        vertexSet.add("Detmold");
        vertexSet.add("Münster");
        vertexSet.add("Bremen");
        vertexSet.add("Minden");
        vertexSet.add("Hannover");
        vertexSet.add("Oldenburg");
        vertexSet.add("Cuxhaven");
        vertexSet.add("Bremerhaven");
        vertexSet.add("Rotenburg");
        vertexSet.add("Uelzen");
        vertexSet.add("Walsrode");
        vertexSet.add("Celle");
        vertexSet.add("Buxtehude");
        vertexSet.add("Uelzen");
        vertexSet.add("Hamburg");
        vertexSet.add("Norderstedt");
        Assert.assertEquals("graph3.gka vertices set wrong.", vertexSet, graph.vertexSet());

        Assert.assertEquals("graph3.gka edge set wrong.", 40, graph.edgeSet().size());
    }

    @Test
    public void testGraph4() throws IOException {
        Graph graph = Main.getGraphFromFile("./graphs/graph4.gka");

        Assert.assertTrue("graph4.gka wrong graph class", graph instanceof WeightedPseudograph);

        Set<String> vertexSet = new HashSet<String>();
        vertexSet.add("v1");
        vertexSet.add("v2");
        vertexSet.add("v3");
        vertexSet.add("v4");
        vertexSet.add("v5");
        vertexSet.add("v6");
        vertexSet.add("v7");
        vertexSet.add("v8");
        vertexSet.add("s");
        vertexSet.add("q");
        Assert.assertEquals("graph4.gka vertices set wrong.", vertexSet, graph.vertexSet());

        Assert.assertEquals("graph4.gka edge set wrong.", 23, graph.edgeSet().size());
    }

    @Test
    public void testGraph6() throws IOException {
        Graph graph = Main.getGraphFromFile("./graphs/graph6.gka");

        Assert.assertTrue("graph6.gka wrong graph class", graph instanceof DirectedPseudograph);

        Set<String> vertexSet = new HashSet<String>();
        vertexSet.add("1");
        vertexSet.add("2");
        vertexSet.add("3");
        vertexSet.add("4");
        vertexSet.add("5");
        vertexSet.add("6");
        vertexSet.add("7");
        vertexSet.add("8");
        vertexSet.add("9");
        vertexSet.add("10");
        vertexSet.add("11");
        vertexSet.add("12");
        Assert.assertEquals("graph6.gka vertices set wrong.", vertexSet, graph.vertexSet());

        Assert.assertEquals("graph6.gka edge set wrong.", 15, graph.edgeSet().size());
    }
}
