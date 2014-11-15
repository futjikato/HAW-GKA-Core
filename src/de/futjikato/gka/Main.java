package de.futjikato.gka;

import de.futjikato.gka.reader.GKALexer;
import de.futjikato.gka.reader.GKAParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.jgrapht.Graph;

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

    public static Graph getGraphFromFile(String filename, GraphFactory<Vertex> factory) throws IOException {
        // create token stream for parser
        GKALexer lexer = new GKALexer(new ANTLRFileStream(filename));
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create parser
        GKAParser parser = new GKAParser(tokens);
        parser.setGraphFactory(factory);

        // parse
        parser.prog();

        // get edge list
        return parser.getGraphFactory().createGraph();
    }

    public static Graph getGraphFromFile(String filename) throws IOException {
        GraphFactory<Vertex> factory = new GraphFactory<Vertex>() {
            @Override
            public Vertex createVertex(String name) {
                return new Vertex(name);
            }
        };

        return getGraphFromFile(filename, factory);
    }
}
