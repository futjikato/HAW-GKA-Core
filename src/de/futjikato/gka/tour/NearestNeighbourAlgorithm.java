package de.futjikato.gka.tour;

import de.futjikato.gka.JGraphView;
import de.futjikato.gka.Main;
import org.jgrapht.*;

import java.io.IOException;
import java.util.*;

public class NearestNeighbourAlgorithm {

    private static WeightedGraph<VisitableVertex, VisitableWeightedEdge> graph;
    private static List<VisitableWeightedEdge> tour = new LinkedList<VisitableWeightedEdge>();
    // Workaround since we need to know with which vertex we started
    private static VisitableVertex firstVertex = null;

    public NearestNeighbourAlgorithm(WeightedGraph<VisitableVertex, VisitableWeightedEdge> graph) {
        this.graph = graph;
    }

    public static void main(String argv[]) throws IOException {
        /**
         * Generate graph from user input.
         */
        Graph<VisitableVertex, VisitableWeightedEdge> graph = Main.getGraphFromFile(argv[0], MinimumSpanningTreeHeuristic.createGraphFactory());
        if(!(graph instanceof WeightedGraph)) {
            throw new IllegalArgumentException("Graph must be weighted.");
        }

        WeightedGraph<VisitableVertex, VisitableWeightedEdge> weightedGraph = (WeightedGraph<VisitableVertex, VisitableWeightedEdge>) graph;
        NearestNeighbourAlgorithm nearestNeighbourAlgorithm = new NearestNeighbourAlgorithm(weightedGraph);

        /**
         * Pick startVertex randomly
         */
        Random randomGenerator = new Random();
        Set<VisitableVertex> verticesSet = graph.vertexSet();
        VisitableVertex[] verticesArray = verticesSet.toArray(new VisitableVertex[verticesSet.size()]);
        Integer size = verticesSet.size();
        /**
         * Generates random Integer between 0 (incl) and size (excl)
         */
        int randomIndex = randomGenerator.nextInt(size);
        firstVertex = verticesArray[randomIndex];

        System.out.println("Picking " + firstVertex + " as start point.");

        /**
         * Find tour with algorithm
         */
        nearestNeighbourAlgorithm.findTour(firstVertex);

        JGraphView view = JGraphView.getFrame(graph);

        System.out.println(tour);
        if(tour != null) {
            view.colorEdges("red", tour);
        }
    }


    private static List<VisitableWeightedEdge> findTour(VisitableVertex startVertex) {

        if (startVertex.isVisited()) {
            throw new IllegalArgumentException("StartVertex already visited.");
        }

        startVertex.visit();

        /**
         * If no vertex is visitable anymore we have completed our tour
         */
        Set<VisitableVertex> allVertices = graph.vertexSet();
        Set<VisitableVertex> visitableVertices = new HashSet<VisitableVertex>();
        for (VisitableVertex v : allVertices) {
            if (!v.isVisited()) {
                visitableVertices.add(v);
            }
        }

        /**
         * Termination of tour, adding edge from last vertex  (now startVertex) to first vertex
         */
        if (visitableVertices.isEmpty()) {
            VisitableWeightedEdge terminationEdge = graph.getEdge(startVertex, firstVertex);
            tour.add(terminationEdge);
            return tour;
        }

        Set<VisitableWeightedEdge> incidentEdges = graph.edgesOf(startVertex);

        /**
         * Get edges and their weight with target unequal startVertex and unvisited
         */

        HashMap<VisitableWeightedEdge, Double> relevantEdges = new HashMap<VisitableWeightedEdge, Double>();
        for (VisitableWeightedEdge e : incidentEdges) {
            VisitableVertex edgeSource = graph.getEdgeSource(e);
            VisitableVertex edgeTarget = graph.getEdgeTarget(e);

            /**
            * Need to check for otherVertex especially within undirected graphs
            */
            VisitableVertex otherVertex = null;
            if (edgeSource != startVertex) {
                otherVertex = edgeSource;
            } else {
                otherVertex = edgeTarget;
            }

            if (!otherVertex.isVisited()) {
                relevantEdges.put(e, graph.getEdgeWeight(e));
            }
        }

        /**
         * When relevantEdges is empty graph is not complete or another error occurred
         */
        if (relevantEdges.isEmpty()) {
            throw new IllegalArgumentException("Graph needs to be complete.");
        }

        /**
         * Get next vertex with shortest way within above edges
         */
        Double weightShortestWay = Double.POSITIVE_INFINITY;
        VisitableWeightedEdge nextEdge = null;
        for (Map.Entry entry : relevantEdges.entrySet()) {
            if ((Double) entry.getValue() < weightShortestWay) {
                weightShortestWay = (Double) entry.getValue();
                nextEdge = (VisitableWeightedEdge) entry.getKey();
            }
        }

        tour.add(nextEdge);
        VisitableVertex nextVertex;
        if (graph.getEdgeSource(nextEdge) != startVertex) {
            nextVertex = graph.getEdgeSource(nextEdge);
        } else {
            nextVertex = graph.getEdgeTarget(nextEdge);
        }

        System.out.println("Next vertex is " + nextVertex + ", next edge is " + nextEdge);
        /**
         * Recursive call
         */
        findTour(nextVertex);

        /**
         * Correct return statement only when all vertices are visited
         */
        return null;
    }

    public List<VisitableWeightedEdge> findTour() {
        /**
         * Pick startVertex randomly
         */
        Random randomGenerator = new Random();
        Set<VisitableVertex> verticesSet = graph.vertexSet();
        VisitableVertex[] verticesArray = verticesSet.toArray(new VisitableVertex[verticesSet.size()]);
        Integer size = verticesSet.size();
        /**
         * Generates random Integer between 0 (incl) and size (excl)
         */
        int randomIndex = randomGenerator.nextInt(size);
        firstVertex = verticesArray[randomIndex];

        /**
         * Find tour with algorithm
         */
        return NearestNeighbourAlgorithm.findTour(firstVertex);
    }
}