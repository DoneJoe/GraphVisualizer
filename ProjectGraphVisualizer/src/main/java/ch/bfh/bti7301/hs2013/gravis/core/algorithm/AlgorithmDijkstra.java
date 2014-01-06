package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.Collection;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.comparator.CurrentResultComparator;
import edu.uci.ics.jung.algorithms.util.MapBinaryHeap;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AlgorithmDijkstra extends AbstractAlgorithm {

	private final static String NEG_WEIGHT = "Dijkstra algorithm: "
			+ "negative weights are not allowed!";
	private final static String NO_START_VERTEX = "Dijkstra algorithm: no start vertex "
			+ "found in graph %s!";

	private final static String ALGO_NAME = "Dijkstra-Algorithmus";
	private final static String ALGO_DESCRIPTION = "Der Dijkstra-Algorithmus findet den "
			+ "kürzesten Weg zwischen zwei Knoten. Ein Startknoten muss im Graphen vorhanden "
			+ "sein. Ist kein Endknoten vorhanden, so wird der kürteste Weg vom Startknoten "
			+ "zu allen anderen Knoten berechnet. Diese Implementation des Algorithmus "
			+ "funktioniert mit gerichteten oder ungerichteten Kanten. Negative "
			+ "Kantengewichte und Mehrfachkanten sind nicht erlaubt.";
	private final static String START_VERTEX = "%s ist der Startknoten.";
	private final static String SHORTEST_PATH_OK = "Der kürzeste Weg von %s nach "
			+ "%s wurde gefunden: %.1f";
	private final static String SHORTEST_PATH_UPDATE = "Der neue kürzeste Weg vom "
			+ " Startknoten zum Knoten %s ist: %.1f";
	private final static String RESULT_INIT = "Der Knoten %s wurde mit folgendem Wert "
			+ "initialisiert: %.1f";
	private final static String SUCCESSOR_MSG = "Der Knoten %s hat die folgenden "
			+ "Nachfolger: %s";
	private final static String MIN_MSG = "Der Knoten %s hat die folgende minimale "
			+ "Distanz: %.1f";
	private final static String END_MSG = "Die Berechnung der kürzesten Wege wurde "
			+ "erfolgreich abgeschlossen.";

	private final CurrentResultComparator vertexResultComparator;

	protected AlgorithmDijkstra() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);
		this.vertexResultComparator = new CurrentResultComparator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.AbstractAlgorithm#execute
	 * (ch.bfh.bti7301.hs2013.gravis.common.IImmutableGraph,
	 * java.util.PriorityQueue)
	 */
	@Override
	public void execute(IRestrictedGraph graph) throws AlgorithmException {
		this.checkPositiveWeights(graph.getEdges());

		Collection<? extends IRestrictedVertex> vertices = graph.getVertices();
		IRestrictedVertex startVertex = graph.getStartVertex();
		IGraphUpdateHandler updateHandler = GraphFactory
				.createGraphUpdateHandler(graph);

		if (startVertex == null) {
			throw new AlgorithmException(String.format(NO_START_VERTEX, graph));
		}

		for (IRestrictedVertex vertex : vertices) {
			if (vertex == startVertex) {
				// start vertex has distance 0
				updateHandler.set(startVertex, true,
						String.format(START_VERTEX, startVertex.getId()), 0.0,
						false);
			} else {
				// initialize infinite distance from start vertex
				updateHandler.set(vertex, String.format(RESULT_INIT,
						vertex.getId(), Double.POSITIVE_INFINITY),
						Double.POSITIVE_INFINITY);
			}
		}

		this.calculateDistances(graph, vertices, startVertex, updateHandler);
		updateHandler.set(startVertex, false, END_MSG, false);
		this.updateUnreachableVertices(vertices, updateHandler);
		updateHandler.update();
	}

	/**
	 * @param graph
	 * @param vertices
	 * @param startVertex
	 * @param updateHandler
	 */
	private void calculateDistances(IRestrictedGraph graph,
			Collection<? extends IRestrictedVertex> vertices,
			IRestrictedVertex startVertex, IGraphUpdateHandler updateHandler) {

		MapBinaryHeap<IRestrictedVertex> prioQueue = new MapBinaryHeap<>(
				this.vertexResultComparator);
		prioQueue.addAll(vertices);

		while (!prioQueue.isEmpty()) {
			IRestrictedVertex selectedVertex = prioQueue.poll();

			if (selectedVertex == startVertex) {
				selectedVertex.appendToNewComment(String.format(MIN_MSG,
						selectedVertex.getId(), selectedVertex.getNewResult()));
				updateHandler.set(selectedVertex, State.ACTIVATION, true,
						selectedVertex.getNewResult(), true);
			} else {
				updateHandler.set(selectedVertex, State.ACTIVATION, true,
						String.format(MIN_MSG, selectedVertex.getId(),
								selectedVertex.getCurrentResult()),
						selectedVertex.getCurrentResult(), true);
			}
			updateHandler.update();

			if (selectedVertex.getValue() != null) {
				IRestrictedEdge edge = graph.findEdge(
						(IRestrictedVertex) selectedVertex.getValue(),
						selectedVertex);
				updateHandler.set(edge, State.SOLUTION, true, true);
			}
			if (this.updateEndVertexMessage(startVertex, selectedVertex,
					updateHandler)) {
				this.showShortestPath(graph, selectedVertex, updateHandler);
				return;
			}
			updateHandler.set(selectedVertex, State.SOLUTION, true, true);
			this.setSuccessorMessage(graph, selectedVertex);
			updateHandler.update();

			updateHandler.set(selectedVertex, false, false);
			this.updateAdjacentVertexDistances(graph, selectedVertex,
					prioQueue, updateHandler);
		}
	}

	/**
	 * 
	 * @param graph
	 * @param vertex
	 * @param prioQueue
	 * @param updateHandler
	 */
	private void updateAdjacentVertexDistances(IRestrictedGraph graph,
			IRestrictedVertex vertex,
			MapBinaryHeap<IRestrictedVertex> prioQueue,
			IGraphUpdateHandler updateHandler) {
		double newDistance = 0.0;
		double oldDistance = 0.0;

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, adjacentVertex);
			newDistance = vertex.getCurrentResult() + edge.getWeight();
			oldDistance = adjacentVertex.getCurrentResult();

			if (adjacentVertex.getCurrentState() == State.SOLUTION) {
				if (edge.getCurrentState() != State.SOLUTION
						&& edge.getCurrentState() != State.REFUSE) {
					updateHandler.set(edge, State.REFUSE, true, true, true);
					updateHandler.update();
				}
			} else {
				if (newDistance < oldDistance) {
					updateHandler.set(edge, State.VISIT, true, true);
					// set new predecessor for shortest path
					updateHandler.set(
							adjacentVertex,
							State.VISIT,
							true,
							String.format(SHORTEST_PATH_UPDATE,
									adjacentVertex.getId(), newDistance),
							newDistance, true, vertex);

					this.updatePredecessors(graph, vertex, updateHandler,
							adjacentVertex);
				} else {
					updateHandler.set(edge, State.REFUSE, true, true, true);
					updateHandler.set(adjacentVertex, State.VISIT, true, true);
				}
				updateHandler.update();

				updateHandler.set(adjacentVertex, false, false);
				if (Double.compare(newDistance, oldDistance) != 0) {
					// adjust the position of adjacentVertex in the proirity
					// queue, if the distance has changed
					prioQueue.update(adjacentVertex);
				}
			}
		}
	}

	/**
	 * @param graph
	 * @param currentVertex
	 * @param updateHandler
	 * @param adjacentVertex
	 */
	private void updatePredecessors(IRestrictedGraph graph,
			IRestrictedVertex currentVertex, IGraphUpdateHandler updateHandler,
			IRestrictedVertex adjacentVertex) {

		for (IRestrictedVertex predecessor : graph
				.getPredecessors(adjacentVertex)) {
			if (predecessor.getCurrentState() == State.SOLUTION
					&& predecessor != currentVertex) {
				IRestrictedEdge refusedEdge = graph.findEdge(predecessor,
						adjacentVertex);

				if (refusedEdge.getCurrentState() != State.REFUSE) {
					updateHandler.set(refusedEdge, State.REFUSE, true, true,
							true);
				}
			}
		}
	}

	/**
	 * 
	 * @param startVertex
	 * @param endVertex
	 * @param updateHandler
	 * @return boolean
	 */
	private boolean updateEndVertexMessage(IRestrictedVertex startVertex,
			IRestrictedVertex endVertex, IGraphUpdateHandler updateHandler) {

		if (endVertex.isEnd()) {
			updateHandler.set(endVertex, State.SOLUTION, true, String.format(
					SHORTEST_PATH_OK, startVertex.getId(), endVertex.getId(),
					endVertex.getCurrentResult()), false);
			updateHandler.update();
			return true;
		}
		return false;
	}

	/**
	 * @param graph
	 * @param vertex
	 */
	private void setSuccessorMessage(IRestrictedGraph graph,
			IRestrictedVertex vertex) {
		StringBuilder successors = new StringBuilder();

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			successors.append(adjacentVertex.getId() + ", ");
		}

		successors.delete(Math.max(0, successors.length() - 2),
				Math.max(0, successors.length()));

		if (successors.length() != 0) {
			vertex.appendToNewComment(String.format(SUCCESSOR_MSG,
					vertex.getId(), successors));
		}
	}

	/**
	 * @param edges
	 * @throws AlgorithmException
	 */
	private void checkPositiveWeights(
			Collection<? extends IRestrictedEdge> edges)
			throws AlgorithmException {

		for (IRestrictedEdge edge : edges) {
			if (edge.getWeight() < 0) {
				throw new AlgorithmException(NEG_WEIGHT);
			}
		}
	}

	/**
	 * @param graph
	 * @param selectedVertex
	 * @param updateHandler
	 */
	private void showShortestPath(IRestrictedGraph graph,
			IRestrictedVertex selectedVertex, IGraphUpdateHandler updateHandler) {

		for (IRestrictedEdge edge : graph.getEdges()) {
			updateHandler.set(edge, State.INITIAL, false, false, false);
		}

		for (IRestrictedVertex vertex : graph.getVertices()) {
			updateHandler.set(vertex, State.INITIAL, false, false, false);
		}

		updateHandler.set(selectedVertex, State.SOLUTION, false, false);
		IRestrictedVertex currentVertex = null;
		while (selectedVertex.getValue() != null) {
			currentVertex = (IRestrictedVertex) selectedVertex.getValue();
			IRestrictedEdge edge = graph
					.findEdge(currentVertex, selectedVertex);
			updateHandler.set(edge, State.SOLUTION, false, true);
			updateHandler.set(currentVertex, State.SOLUTION, false, false);
			selectedVertex = currentVertex;
		}
	}

	/**
	 * @param vertices
	 * @param updateHandler
	 */
	private void updateUnreachableVertices(
			Collection<? extends IRestrictedVertex> vertices,
			IGraphUpdateHandler updateHandler) {

		for (IRestrictedVertex vertex : vertices) {
			if (vertex.getCurrentResult() == Double.POSITIVE_INFINITY) {
				updateHandler.set(vertex, State.REFUSE, true, false);
			}
		}
	}

}
