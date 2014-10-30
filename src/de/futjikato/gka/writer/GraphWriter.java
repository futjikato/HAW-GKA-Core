package de.futjikato.gka.writer;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class GraphWriter {

    public void writer(String file, Graph graph) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        boolean isDirectional = (graph instanceof DirectedGraph);
        boolean isWeighted = (graph instanceof WeightedGraph);

        Set<String> vertices = graph.vertexSet();
        for(String vertex : vertices) {
            writer.write(String.format("%s;", vertex));
            writer.newLine();
        }

        Set<DefaultEdge> edges = graph.edgeSet();
        for(DefaultEdge edge : edges) {
            String conn = isDirectional ? "->" : "--";
            writer.write(String.format("%s %s %s", graph.getEdgeSource(edge), conn, graph.getEdgeTarget(edge)));

            if(isWeighted) {
                writer.write(String.format(" : %f", graph.getEdgeWeight(edge)));
            }

            writer.write(";");
            writer.newLine();
            writer.flush();
        }
    }

}
