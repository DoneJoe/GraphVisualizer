package ch.bfh.ti.gravis.core.algorithm;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.comparator.CurrentResultComparator;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.algorithms.util.MapBinaryHeap;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * This implementation of dijkstra algorithm is based on pseudocode-description
 * at: <br />
 * <a href="http://www-m9.ma.tum.de/Allgemeines/DijkstraCode">http://www-m9.ma.
 * tum.de/Allgemeines/DijkstraCode</a> <br />
 * A priority queue is used in order to determine the vertex with the minimum
 * distance (class MapBinaryHeap in JUNG framework). <br />
 * The method IRestrictedGraphItem.setValue(vertex) is used to store the shortest
 * predecessor of a vertex.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DijkstraDistance extends AbstractAlgorithm {

	// exception dialog message:

	private final static String NEG_WEIGHT = "Negative Kantengewichte sind nicht erlaubt!";

	// algorithm name and description:

	private final static String ALGO_NAME = "Algorithmus von Dijkstra";
	private final static String ALGO_DESCRIPTION = "Der Algorithmus von Dijkstra findet den "
			+ "kürzesten Weg zwischen zwei Knoten. Ist kein Startknoten gesetzt, so wird "
			+ "der Knoten mit dem lexikographisch kleinsten Namen als Startknoten ausgewählt. "
			+ "Ist kein Endknoten vorhanden, so wird der kürzeste Weg vom Startknoten "
			+ "zu allen anderen Knoten berechnet. Diese Implementation des Algorithmus "
			+ "funktioniert mit gerichteten und ungerichteten Graphen. Negative "
			+ "Kantengewichte und Mehrfachkanten sind nicht erlaubt.";

	// protocol messages:

	private final static String INIT_START_VERTEX = "Initialisierung des Startknotens %s: %d"
			+ LN;
	private final static String INIT_VERTICES = "Initialisierung der restlichen Knoten: %s"
			+ LN;
	private final static String SUCCESSOR_MSG = "Der Knoten %s hat die folgenden "
			+ "Nachfolger: %s" + LN;
	private final static String SHORTEST_PATH_UPDATE = "Neuer kürzester Weg zum Knoten %s: %s"
			+ LN;
	private final static String V_DISTANCE = "Er hat die folgende Distanz: %s"
			+ LN;
	private final static String SHORTEST_PATH_OK = "Der kürzeste Weg vom Startknoten %s zum "
			+ "Endknoten %s ist: %s" + LN;
	private final static String PATH_VERTICES = "Weg vom Startknoten zum Endknoten: %s"
			+ LN;
	private final static String CALC_DONE = "Der kürzeste Weg vom Startknoten %s zu allen anderen "
			+ "Knoten wurde berechnet." + LN;

	private final CurrentResultComparator vertexResultComparator;

	private final DecimalFormat format;

	protected DijkstraDistance() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);

		this.vertexResultComparator = new CurrentResultComparator();
		this.format = new DecimalFormat();
		this.format.setMinimumFractionDigits(0);
		this.format.setMaximumFractionDigits(2);
	}

	@Override
	public void execute(final IRestrictedGraph graph, final IStepRecorder rec)
			throws AlgorithmException {

		if (graph.isEmpty()) {
			// nothing to calculate
			return;
		}
		this.validatePositiveWeights(graph.getEdges());

		IRestrictedVertex startVertex = graph.getStartVertex();
		// initialize vertex distances
		for (IRestrictedVertex vertex : graph.getVertices()) {
			if (vertex == startVertex) {
				// start vertex has distance 0
				String startCmt = String.format(INIT_START_VERTEX,
						startVertex.getName(), 0);
				String cmt = String.format(INIT_VERTICES,
						this.format.format(Double.POSITIVE_INFINITY));
				rec.item(startVertex).cmt(startCmt).app(cmt).res(0.0).add();
			} else {
				// initialize infinite distance from start vertex
				rec.item(vertex).res(Double.POSITIVE_INFINITY)
						.value(startVertex).add();
			}
		}

		this.calculateDistances(graph, startVertex, rec);
	}

	/**
	 * Calculates the shortest path from start vertex to the other vertices.
	 * 
	 * @param graph
	 * @param startVertex
	 * @param rec
	 */
	private void calculateDistances(final IRestrictedGraph graph,
			final IRestrictedVertex startVertex, final IStepRecorder rec) {

		MapBinaryHeap<IRestrictedVertex> prioQueue = new MapBinaryHeap<>(
				this.vertexResultComparator);
		prioQueue.addAll(graph.getVertices());

		while (!prioQueue.isEmpty()) {
			IRestrictedVertex selectedVertex = prioQueue.poll();

			// skip unreachable vertices
			if (!Double.isInfinite(selectedVertex.getCurrentResult())) {
				// add distance message to current vertex
				if (selectedVertex != startVertex) {
					rec.item(selectedVertex)
							.app(String.format(V_DISTANCE, this.format
									.format(selectedVertex.getCurrentResult())))
							.add();
				}

				// add selected vertex and edge to solution set
				rec.item(selectedVertex).state(SOLUTION).cmtOk().tag().add();
				if (selectedVertex.getValue() != null) {
					IRestrictedEdge edge = graph.findEdge(
							(IRestrictedVertex) selectedVertex.getValue(),
							selectedVertex);

					if (edge != null) {
						rec.item(edge).state(SOLUTION).tag().add();
					}
				}

				// check if end vertex is reached
				if (this.validateEndVertex(startVertex, selectedVertex, rec)) {
					this.showShortestPath(graph, selectedVertex, rec);
					return;
				}
				this.setSuccessorMessage(graph, selectedVertex, rec);

				this.updateAdjacentVertexDistances(graph, selectedVertex,
						prioQueue, rec);
			}
		}

		// shortest path from start vertex to all other vertices has been calculatad
		String cmt = String.format(CALC_DONE, startVertex.getName());
		rec.item(startVertex).app(cmt).add();
		this.updateUnreachableVertices(graph, rec);
	}

	/**
	 * Updates the shortest path to all adjacent vertices of current solution vertex.
	 * 
	 * @param graph
	 * @param vertex
	 * @param prioQueue
	 * @param rec
	 */
	private void updateAdjacentVertexDistances(final IRestrictedGraph graph,
			final IRestrictedVertex vertex,
			final MapBinaryHeap<IRestrictedVertex> prioQueue,
			final IStepRecorder rec) {

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, adjacentVertex);
			double newDistance = vertex.getCurrentResult() + edge.getWeight();
			double oldDistance = adjacentVertex.getCurrentResult();

			if (newDistance < oldDistance) {
				// visited edge to adjacentVertex
				rec.item(edge).state(VISIT).cmtOk().tag().add();

				// update current shortest path for adjacentVertex and set shortest predecessor
				String cmt = String.format(SHORTEST_PATH_UPDATE,
						adjacentVertex.getName(),
						this.format.format(newDistance));
				rec.item(adjacentVertex).state(VISIT).cmtOk().cmt(cmt).tag()
						.res(newDistance).value(vertex).add();
				rec.save();

				this.updatePredecessors(graph, vertex, rec, adjacentVertex);

				// adjust the position of adjacentVertex in the proirity queue
				prioQueue.update(adjacentVertex);
			} else if (!edge.isElimination() && !edge.isSolution()) {
				// discard outgoing edges with not minimal path
				rec.item(edge).state(ELIMINATION).cmtOk().tag().dash().add();
				rec.save();
			}
		}
	}

	/**
	 * Updates incoming edges of current expanded vertex.
	 * 
	 * @param graph
	 * @param currentVertex
	 * @param rec
	 * @param adjacentVertex
	 */
	private void updatePredecessors(final IRestrictedGraph graph,
			final IRestrictedVertex currentVertex, final IStepRecorder rec,
			final IRestrictedVertex adjacentVertex) {

		for (IRestrictedVertex predecessor : graph
				.getPredecessors(adjacentVertex)) {
			if (predecessor.isSolution() && predecessor != currentVertex) {
				IRestrictedEdge discardedEdge = graph.findEdge(predecessor,
						adjacentVertex);

				if (!discardedEdge.isElimination()) {
					// discard incoming edges with not minimal path
					rec.item(discardedEdge).state(ELIMINATION).cmtOk().tag()
							.dash().add();
					rec.save();

				}
			}
		}
	}

	/**
	 * Checks if the end vertex has been reached.
	 * 
	 * @param startVertex
	 * @param currentVertex
	 * @param rec
	 * @return boolean
	 */
	private boolean validateEndVertex(final IRestrictedVertex startVertex,
			final IRestrictedVertex currentVertex, final IStepRecorder rec) {

		if (currentVertex.isEnd()) {
			String cmt = String.format(SHORTEST_PATH_OK, startVertex.getName(),
					currentVertex.getName(),
					this.format.format(currentVertex.getCurrentResult()));

			rec.item(currentVertex).state(SOLUTION).cmtOk().app(cmt).tag().add();
			rec.save();
			return true;
		}
		return false;
	}

	/**
	 * Sets a successor message. This message contains all successors of the current vertex.
	 * 
	 * @param graph
	 * @param vertex
	 * @param rec
	 */
	private void setSuccessorMessage(final IRestrictedGraph graph,
			final IRestrictedVertex vertex, final IStepRecorder rec) {
		StringBuilder successors = new StringBuilder();

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			successors.append(adjacentVertex.getName() + ", ");
		}
		successors.delete(Math.max(0, successors.length() - 2),
				Math.max(0, successors.length()));

		if (successors.length() != 0) {
			rec.item(vertex)
					.app(String.format(SUCCESSOR_MSG, vertex.getName(),
							successors)).add();
		}
		rec.save();
	}

	/**
	 * Checks if all edge weights are >= 0. 
	 * 
	 * @param edges
	 * @throws AlgorithmException if one or more edge weights are < 0
	 */
	private void validatePositiveWeights(
			final Collection<? extends IRestrictedEdge> edges)
			throws AlgorithmException {

		for (IRestrictedEdge edge : edges) {
			if (edge.getWeight() < 0) {
				throw new AlgorithmException(NEG_WEIGHT, ALGO_NAME);
			}
		}
	}

	/**
	 * Shows the shortest path from start vertex to end vertex in the graph.
	 * 
	 * @param graph
	 * @param selectedVertex
	 * @param rec
	 */
	private void showShortestPath(final IRestrictedGraph graph,
			IRestrictedVertex selectedVertex, final IStepRecorder rec) {

		// mark all graph elements as invisible
		for (IRestrictedEdge edge : graph.getEdges()) {
			rec.item(edge).state(INITIAL).notVisib().notCmt().add();
		}
		for (IRestrictedVertex vertex : graph.getVertices()) {
			rec.item(vertex).state(INITIAL).notVisib().notCmt().add();
		}

		IRestrictedVertex currentVertex = null;
		StringBuilder path = new StringBuilder();
		List<String> vertexNames = new ArrayList<>();

		rec.item(selectedVertex).state(SOLUTION).visib().add();
		vertexNames.add(selectedVertex.getName());
		// go back to the start vertex from the end vertex and mark elements as visible
		while (selectedVertex.getValue() != null) {
			currentVertex = (IRestrictedVertex) selectedVertex.getValue();
			IRestrictedEdge edge = graph
					.findEdge(currentVertex, selectedVertex);

			rec.item(edge).state(SOLUTION).visib().add();
			rec.item(currentVertex).state(SOLUTION).visib().add();
			vertexNames.add(currentVertex.getName());

			selectedVertex = currentVertex;
		}

		// build the path comment string
		for (int i = vertexNames.size() - 1; i >= 0; i--) {
			path.append(vertexNames.get(i) + " - ");
		}
		path.delete(Math.max(0, path.length() - 3), Math.max(0, path.length()));
		String cmt = String.format(PATH_VERTICES, path.toString());
		rec.item(selectedVertex).app(cmt).add();
		rec.save();
	}

	/**
	 * Discards all unreachable vertices and edges from the solution.
	 * 
	 * @param graph
	 * @param rec
	 */
	private void updateUnreachableVertices(final IRestrictedGraph graph,
			final IStepRecorder rec) {

		for (IRestrictedVertex vertex : graph.getVertices()) {
			// unreachable vertices have infinite distance
			if (Double.isInfinite(vertex.getCurrentResult())) {
				rec.item(vertex).state(ELIMINATION).visib().tag().notCmt()
						.add();
			}
		}

		for (IRestrictedEdge edge : graph.getEdges()) {
			Pair<? extends IRestrictedVertex> pair = graph.getEndpoints(edge);

			// discard unreachable edges
			if (Double.isInfinite(pair.getFirst().getCurrentResult())) {
				rec.item(edge).state(ELIMINATION).visib().tag().dash().notCmt()
						.add();
			}
		}

		rec.save();
	}

}
