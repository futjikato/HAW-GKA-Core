package de.futjikato.gka;

import de.futjikato.gka.reader.GKALexer;
import de.futjikato.gka.reader.GKAParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.File;
import java.io.IOException;

public final class Main {

    public static void main(String[] argv) {

        String graphFileName = argv[0];
        File graphFile = new File(graphFileName);

        if(!graphFile.exists()) {
            throw new RuntimeException("Graphfile not found.");
        }

        Graph graph;
        try {
            graph = getGraphFromFile(graphFileName);
        } catch (IOException e) {
            System.err.println("Error loading/parsing graph file.");
            e.printStackTrace();
            return;
        }

        JGraphView.getFrame(graph);
    }

    public static <V extends Vertex, E extends DefaultEdge> Graph<V, E> getGraphFromFile(String filename, GraphFactory<V, E> factory) throws IOException {
        // create token stream for parser
        GKALexer lexer = new GKALexer(new ANTLRFileStream(filename, "UTF-8"));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create parser
        GKAParser parser = new GKAParser(tokens);
        parser.setGraphFactory(factory);

        // parse
        parser.prog();

        // get edge list
        return factory.createGraph();
    }

    public static Graph getGraphFromFile(String filename) throws IOException {
        GraphFactory<Vertex, WeightNameEdge> factory = new GraphFactory<Vertex, WeightNameEdge>() {
            @Override
            public Vertex createVertex(String name) {
                return new Vertex(name);
            }

            @Override
            public EdgeFactory<Vertex, WeightNameEdge> getEdgeFactory() {
                return new EdgeFactory<Vertex, WeightNameEdge>() {
                    @Override
                    public WeightNameEdge createEdge(Vertex vertex, Vertex v1) {
                        return new WeightNameEdge();
                    }
                };
            }
        };

        return getGraphFromFile(filename, factory);
    }
}
