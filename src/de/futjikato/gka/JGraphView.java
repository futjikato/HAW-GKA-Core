package de.futjikato.gka;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.model.mxICell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

public class JGraphView extends JApplet {

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private HashMap<String, mxICell> vertexToCell;

    private mxGraph mxg;

    public static JGraphView getFrame(Graph graph) {
        JGraphView applet = new JGraphView(graph);
        applet.init();

        JFrame frame = new JFrame();
        frame.getContentPane().add(applet);
        frame.setTitle("JGraphT Adapter to JGraph Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        return applet;
    }

    public void colorVertices(String color, Collection<Vertex> vertices) {
        Object[] cells = new Object[vertices.size()];
        int i = 0;
        for(Vertex vertex : vertices) {
            cells[i++] = vertexToCell.get(vertex);
        }

        mxg.setCellStyles(mxConstants.STYLE_FILLCOLOR, color, cells);
    }

    public JGraphView(Graph graph) {
        // create a visualization using JGraph, via an adapter
        JGraphXAdapter jgxAdapter;
        if(graph instanceof WeightedGraph) {
            jgxAdapter = new JGraphXAdapter<String, DefaultWeightedEdge>(graph);
        } else {
            jgxAdapter = new JGraphXAdapter<String, DefaultEdge>(graph);
        }

        vertexToCell = jgxAdapter.getVertexToCellMap();
        mxGraphComponent mxGraphComponent = new mxGraphComponent(jgxAdapter);
        mxg = mxGraphComponent.getGraph();
        getContentPane().add(mxGraphComponent);
        resize(DEFAULT_SIZE);

        // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);
        layout.execute(jgxAdapter.getDefaultParent());
    }
}
