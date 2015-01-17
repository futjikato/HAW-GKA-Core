package de.futjikato.gka.tour;

import de.futjikato.gka.GraphFactory;
import de.futjikato.gka.JGraphView;
import de.futjikato.gka.Main;
import org.jgrapht.EdgeFactory;
import org.jgrapht.Graph;
import org.jgrapht.WeightedGraph;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class MinimumSpanningTreeHeuristic {

    private final WeightedGraph<VisitableVertex, VisitableWeightedEdge> graph;

    public static void main(String argv[]) throws IOException {
        Graph<VisitableVertex, VisitableWeightedEdge> graph = Main.getGraphFromFile(argv[0], MinimumSpanningTreeHeuristic.createGraphFactory());
        if(!(graph instanceof WeightedGraph)) {
            throw new IllegalArgumentException("Graph must be weighted.");
        }

        WeightedGraph<VisitableVertex, VisitableWeightedEdge> wGraph = (WeightedGraph<VisitableVertex, VisitableWeightedEdge>) graph;
        MinimumSpanningTreeHeuristic msth = new MinimumSpanningTreeHeuristic(wGraph);

        List<VisitableWeightedEdge> tour = msth.createTour();

        JGraphView view = JGraphView.getFrame(graph);

        System.out.println(tour);
        if(tour != null) {
            view.colorEdges("red", tour);
        }
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

    /**
     * Main method to create a tour.
     *
     * @param tourVertices
     * @return
     */
    public List<VisitableWeightedEdge> createTour(Set<VisitableVertex> tourVertices) {
        // create MST with Kruskal
        Kruskal kruskal = new Kruskal(graph);
        List<VisitableWeightedEdge> mst = kruskal.createMinimalSPanningTree(tourVertices);

        // double mst edges
        List<VisitableWeightedEdge> tour = doubleEdges(mst);

        // optimize
        List<VisitableWeightedEdge> optTour = optimizeTour(tour);

        return optTour;
    }

    public List<VisitableWeightedEdge> createTour() {
        return createTour(graph.vertexSet());
    }

    /**
     * Double all edges in the list.
     *
     * @param mst
     * @return A new list with the complete tour ( edges + copies )
     */
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

    /**
     * Optimize the tour by creating direct connections where possible.
     *
     * @param oldTour the original tour. This list will not be edited but a new one created.
     * @return new optimized tour
     */
    private List<VisitableWeightedEdge> optimizeTour(List<VisitableWeightedEdge> oldTour) {
        List<VisitableWeightedEdge> tour = new CopyOnWriteArrayList<VisitableWeightedEdge>();
        tour.addAll(oldTour);

        /**
         * Find an edge that still has a copy.
         * Find an edge that is connected to the same target or source.
         * Check if that other edge has also a copy.
         *   true -> Check if a direct connection between the two vertices exists.
         *     false -> Mark the inital edge as vistited and go on.
         *     true  -> Remove the two edges and connect the two sources or targets directly.
         *
         * Too complex ?
         */
        boolean doOpt = true;
        while(doOpt) {
            doOpt = false;
            mainloop:
            for(VisitableWeightedEdge edge1 : tour) {
                if(!edge1.isVisited() && hasCopy(edge1, tour)) {
                    VisitableVertex source1 = graph.getEdgeSource(edge1);
                    VisitableVertex target1 = graph.getEdgeTarget(edge1);

                    for(VisitableWeightedEdge edge2 : tour) {
                        VisitableVertex source2 = graph.getEdgeSource(edge2);
                        VisitableVertex target2 = graph.getEdgeTarget(edge2);

                        if((source1 == source2 && target1 != target2) ||
                           (source1 != source2 && target1 == target2) &&
                           hasCopy(edge2, tour)) {

                            edge1.visit();
                            if(source1 == source2) {
                                VisitableWeightedEdge replacementEdge = graph.getEdge(target1, target2);
                                if(replacementEdge == null) {
                                    replacementEdge = graph.getEdge(target2, target1);
                                }

                                // graph is not complete
                                if(replacementEdge == null) {
                                    doOpt = true;
                                    break mainloop;
                                }

                                tour.remove(edge1);
                                tour.remove(edge2);
                                tour.add(replacementEdge);
                            } else {
                                VisitableWeightedEdge replacementEdge = graph.getEdge(source1, source2);
                                if(replacementEdge == null) {
                                    replacementEdge = graph.getEdge(source2, source1);
                                }

                                // graph is not complete
                                if(replacementEdge == null) {
                                    doOpt = true;
                                    break mainloop;
                                }

                                tour.remove(edge1);
                                tour.remove(edge2);
                                tour.add(replacementEdge);
                            }

                            doOpt = true;
                            break mainloop;
                        }
                    }
                }
            }
        }

        return tour;
    }

    /**
     * Check if a copy of the edge exists in the given list.
     *
     * @param edge
     * @param list
     * @return true if a copy exists. Otherwise false
     */
    protected boolean hasCopy(VisitableWeightedEdge edge, List<VisitableWeightedEdge> list) {
        for(VisitableWeightedEdge checkEdge : list) {
            if(
                edge != checkEdge &&
                graph.getEdgeSource(edge) == graph.getEdgeSource(checkEdge) &&
                graph.getEdgeTarget(edge) == graph.getEdgeTarget(checkEdge)
            ) {
                return true;
            }
        }

        return false;
    }
}
