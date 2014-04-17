package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.Collection;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.comparator.CurrentResultComparator;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import edu.uci.ics.jung.algorithms.util.MapBinaryHeap;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class Dijkstra extends AbstractAlgorithm {

	private final static String NEG_WEIGHT = "Dijkstra algorithm: "
			+ "negative weights are not allowed!";
	private final static String NO_START_VERTEX = "Dijkstra algorithm: no start vertex "
			+ "found in graph %s!";

	private final static String ALGO_NAME = "Dijkstra";
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

	protected Dijkstra() {
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
	public void execute(final IRestrictedGraph graph) throws AlgorithmException {
		this.checkPositiveWeights(graph.getEdges());

		// TODO reset working vars (isDone, value, enableStateComment)
		
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
				updateHandler.add(startVertex, true,
						String.format(START_VERTEX, startVertex.getName()), 0.0,
						false);
			} else {
				// initialize infinite distance from start vertex
				updateHandler.add(vertex, String.format(RESULT_INIT,
						vertex.getName(), Double.POSITIVE_INFINITY),
						Double.POSITIVE_INFINITY);
			}
		}

		this.calculateDistances(graph, vertices, startVertex, updateHandler);
		updateHandler.add(startVertex, false, END_MSG, false);
		this.updateUnreachableVertices(vertices, updateHandler);
		updateHandler.update();
	}

	/**
	 * @param graph
	 * @param vertices
	 * @param startVertex
	 * @param updateHandler
	 */
	private void calculateDistances(final IRestrictedGraph graph,
			final Collection<? extends IRestrictedVertex> vertices,
			final IRestrictedVertex startVertex, final IGraphUpdateHandler updateHandler) {

		MapBinaryHeap<IRestrictedVertex> prioQueue = new MapBinaryHeap<>(
				this.vertexResultComparator);
		prioQueue.addAll(vertices);

		while (!prioQueue.isEmpty()) {
			IRestrictedVertex selectedVertex = prioQueue.poll();

			if (selectedVertex == startVertex) {
				selectedVertex.appendComment(String.format(MIN_MSG,
						selectedVertex.getName(), selectedVertex.getNewResult()));
				updateHandler.add(selectedVertex, ItemState.ACTIVATION, true,
						selectedVertex.getNewResult(), true);
			} else {
				updateHandler.add(selectedVertex, ItemState.ACTIVATION, true,
						String.format(MIN_MSG, selectedVertex.getName(),
								selectedVertex.getCurrentResult()),
						selectedVertex.getCurrentResult(), true);
			}
			updateHandler.update();

			if (selectedVertex.getValue() != null) {
				IRestrictedEdge edge = graph.findEdge(
						(IRestrictedVertex) selectedVertex.getValue(),
						selectedVertex);
				updateHandler.add(edge, ItemState.SOLUTION, true, true);
			}
			if (this.updateEndVertexMessage(startVertex, selectedVertex,
					updateHandler)) {
				this.showShortestPath(graph, selectedVertex, updateHandler);
				return;
			}
			updateHandler.add(selectedVertex, ItemState.SOLUTION, true, true);
			this.setSuccessorMessage(graph, selectedVertex);
			updateHandler.update();

			updateHandler.add(selectedVertex, false, false);
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
	private void updateAdjacentVertexDistances(final IRestrictedGraph graph,
			final IRestrictedVertex vertex,
			final MapBinaryHeap<IRestrictedVertex> prioQueue,
			final IGraphUpdateHandler updateHandler) {
		double newDistance = 0.0;
		double oldDistance = 0.0;

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, adjacentVertex);
			newDistance = vertex.getCurrentResult() + edge.getWeight();
			oldDistance = adjacentVertex.getCurrentResult();

			if (adjacentVertex.getCurrentState() == ItemState.SOLUTION) {
				if (edge.getCurrentState() != ItemState.SOLUTION
						&& edge.getCurrentState() != ItemState.ELIMINATION) {
					updateHandler.add(edge, ItemState.ELIMINATION, true, true, true);
					updateHandler.update();
				}
			} else {
				if (newDistance < oldDistance) {
					updateHandler.add(edge, ItemState.VISIT, true, true);
					// set new predecessor for shortest path
					updateHandler.add(
							adjacentVertex,
							ItemState.VISIT,
							true,
							String.format(SHORTEST_PATH_UPDATE,
									adjacentVertex.getName(), newDistance),
							newDistance, true, vertex);

					this.updatePredecessors(graph, vertex, updateHandler,
							adjacentVertex);
				} else {
					updateHandler.add(edge, ItemState.ELIMINATION, true, true, true);
					updateHandler.add(adjacentVertex, ItemState.VISIT, true, true);
				}
				updateHandler.update();

				updateHandler.add(adjacentVertex, false, false);
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
	private void updatePredecessors(final IRestrictedGraph graph,
			final IRestrictedVertex currentVertex, final IGraphUpdateHandler updateHandler,
			final IRestrictedVertex adjacentVertex) {

		for (IRestrictedVertex predecessor : graph
				.getPredecessors(adjacentVertex)) {
			if (predecessor.getCurrentState() == ItemState.SOLUTION
					&& predecessor != currentVertex) {
				IRestrictedEdge refusedEdge = graph.findEdge(predecessor,
						adjacentVertex);

				if (refusedEdge.getCurrentState() != ItemState.ELIMINATION) {
					updateHandler.add(refusedEdge, ItemState.ELIMINATION, true, true,
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
	private boolean updateEndVertexMessage(final IRestrictedVertex startVertex,
			final IRestrictedVertex endVertex, final IGraphUpdateHandler updateHandler) {

		if (endVertex.isEnd()) {
			updateHandler.add(endVertex, ItemState.SOLUTION, true, String.format(
					SHORTEST_PATH_OK, startVertex.getName(), endVertex.getName(),
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
	private void setSuccessorMessage(final IRestrictedGraph graph,
			final IRestrictedVertex vertex) {
		StringBuilder successors = new StringBuilder();

		for (IRestrictedVertex adjacentVertex : graph.getSuccessors(vertex)) {
			successors.append(adjacentVertex.getName() + ", ");
		}

		successors.delete(Math.max(0, successors.length() - 2),
				Math.max(0, successors.length()));

		if (successors.length() != 0) {
			vertex.appendComment(String.format(SUCCESSOR_MSG,
					vertex.getName(), successors));
		}
	}

	/**
	 * @param edges
	 * @throws AlgorithmException
	 */
	private void checkPositiveWeights(
			final Collection<? extends IRestrictedEdge> edges)
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
	private void showShortestPath(final IRestrictedGraph graph,
			IRestrictedVertex selectedVertex, final IGraphUpdateHandler updateHandler) {

		for (IRestrictedEdge edge : graph.getEdges()) {
			updateHandler.add(edge, ItemState.INITIAL, false, false, false);
		}

		for (IRestrictedVertex vertex : graph.getVertices()) {
			updateHandler.add(vertex, ItemState.INITIAL, false, false, false);
		}

		updateHandler.add(selectedVertex, ItemState.SOLUTION, false, false);
		IRestrictedVertex currentVertex = null;
		while (selectedVertex.getValue() != null) {
			currentVertex = (IRestrictedVertex) selectedVertex.getValue();
			IRestrictedEdge edge = graph
					.findEdge(currentVertex, selectedVertex);
			updateHandler.add(edge, ItemState.SOLUTION, false, true);
			updateHandler.add(currentVertex, ItemState.SOLUTION, false, false);
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
				updateHandler.add(vertex, ItemState.ELIMINATION, true, false);
			}
		}
	}

}
