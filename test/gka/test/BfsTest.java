package gka.test;

import de.futjikato.gka.shortestway.BFS;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BfsTest {

    @Test
    public void testShorttestWay1() throws IOException {
        BFS bfs = new BFS();
        bfs.init("./graphs/testline.gka");
        List<String> way = bfs.findWay("a", "e");
        List<String> expected = new ArrayList<String>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("d");
        expected.add("e");

        Assert.assertEquals("BFS way is not correct", expected, way);
    }

}
