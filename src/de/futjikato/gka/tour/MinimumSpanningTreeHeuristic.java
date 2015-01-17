package de.futjikato.gka.tour;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.Main;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MinimumSpanningTreeHeuristic {

    private final WeightedGraph<VisitableVertex, VisitableWeightedEdge> graph;

    public static void main(String argv[]) throws IOException {
        Graph<VisitableVertex, VisitableWeightedEdge> graph = Main.getGraphFromFile(argv[0], MinimumSpanningTreeHeuristic.createGraphFactory());
        if(!(graph instanceof WeightedGraph)) {
            throw new IllegalArgumentException("Graph must be weighted.");
        }

        WeightedGraph<VisitableVertex, VisitableWeightedEdge> wGraph = (WeightedGraph<VisitableVertex, VisitableWeightedEdge>) graph;
        MinimumSpanningTreeHeuristic msth = new MinimumSpanningTreeHeuristic(wGraph);
    }

    public static GraphFactory<VisitableVertex, VisitableWeightedEdge> createGraphFactory() {
        return new GraphFactory<VisitableVertex, VisitableWeightedEdge>() {
            @Override
            public VisitableVertex createVertex(String name) {
                return new VisitableVertex(name);
            }

            @Override
            public EdgeFactory<VisitableVertex, VisitableWeightedEdge> getEdgeFactory() {
                return new EdgeFactory<VisitableVertex, VisitableWeightedEdge>() {

                    @Override
                    public VisitableWeightedEdge createEdge(VisitableVertex vertex, VisitableVertex v1) {
                        vertex.connect(v1);
                        return new VisitableWeightedEdge();
                    }
                };
            }
        };
    }

    public MinimumSpanningTreeHeuristic(WeightedGraph<VisitableVertex, VisitableWeightedEdge> graph) {
        this.graph = graph;
    }

    public void createTour(Set<VisitableVertex> tourVertices) {
        // create MST with Kruskal
        Kruskal kruskal = new Kruskal(graph);
        List<VisitableWeightedEdge> mst = kruskal.createMinimalSPanningTree(tourVertices);

        // double mst edges
        List<VisitableWeightedEdge> tour = doubleEdges(mst);

        List<VisitableWeightedEdge> optTour = optimizeTour(tour);
    }

    private List<VisitableWeightedEdge> doubleEdges(List<VisitableWeightedEdge> mst) {
        List<VisitableWeightedEdge> tour = new LinkedList<VisitableWeightedEdge>();
        tour.addAll(mst);

        for(VisitableWeightedEdge edge : mst) {
            VisitableVertex source = graph.getEdgeSource(edge);
            VisitableVertex target = graph.getEdgeTarget(edge);

            VisitableWeightedEdge edgeCopy = graph.addEdge(source, target);
            graph.setEdgeWeight(edgeCopy, graph.getEdgeWeight(edge));

            tour.add(edgeCopy);
        }

        return tour;
    }

    private List<VisitableWeightedEdge> optimizeTour(List<VisitableWeightedEdge> oldTour) {
        List<VisitableWeightedEdge> tour = new LinkedList<VisitableWeightedEdge>();
        tour.addAll(oldTour);

        for(VisitableWeightedEdge edge : tour) {
            VisitableVertex source = graph.getEdgeSource(edge);
            VisitableVertex target = graph.getEdgeTarget(edge);
        }
    }
}
