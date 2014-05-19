package ch.bfh.ti.gravis.core.algorithm;

import java.util.Collection;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.comparator.CurrentResultComparator;
import ch.bfh.ti.gravis.core.graph.item.ItemState;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.algorithms.util.MapBinaryHeap;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * comments end with a new line (except algorithm name)
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DijkstraDistance extends AbstractAlgorithm {

	private final static String NEG_WEIGHT = "Negative Kantengewichte sind nicht erlaubt!";
	private final static String NO_START_VERTEX = "DijkstraDistance algorithm: no start vertex "
			+ "found in graph %s!" + LN;

	private final static String ALGO_NAME = "Algorithmus von Dijkstra";
	private final static String ALGO_DESCRIPTION = "Der DijkstraDistance-Algorithmus findet den "
			+ "kürzesten Weg zwischen zwei Knoten. Ein Startknoten muss im Graphen vorhanden "
			+ "sein. Ist kein Endknoten vorhanden, so wird der kürteste Weg vom Startknoten "
			+ "zu allen anderen Knoten berechnet. Diese Implementation des Algorithmus "
			+ "funktioniert mit gerichteten oder ungerichteten Kanten. Negative "
			+ "Kantengewichte und Mehrfachkanten sind nicht erlaubt.";
	private final static String START_VERTEX = "%s ist der Startknoten."
			+ LN;
	private final static String SHORTEST_PATH_OK = "Der kürzeste Weg von %s nach "
			+ "%s wurde gefunden: %.1f" + LN;
	private final static String SHORTEST_PATH_UPDATE = "Der neue kürzeste Weg vom "
			+ " Startknoten zum Knoten %s ist: %.1f" + LN;
	private final static String RESULT_INIT = "Der Knoten %s wurde initialisiert: %.1f" + LN;
	private final static String SUCCESSOR_MSG = "Der Knoten %s hat die folgenden "
			+ "Nachfolger: %s" + LN;
	private final static String MIN_MSG = "Der Knoten %s hat die folgende minimale "
			+ "Distanz: %.1f" + LN;
	
	// TODO kuerzester Weg zeigen
	private final static String END_MSG = "Der kürzeste Weg wurde gefunden." + LN;

	private final CurrentResultComparator vertexResultComparator;

	protected DijkstraDistance() {
		super(ALGO_NAME, ALGO_DESCRIPTION);

		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);
		this.vertexResultComparator = new CurrentResultComparator();
	}

	@Override
	public void execute(final IRestrictedGraph graph, final IStepRecorder rec)
			throws AlgorithmException {

		if (graph.isEmpty()) {
			// nothing to calculate
			return;
		}
		
		// TODO erster Step: Farbe auf Initial, Message für algo start
		// TODO implementation anpassen, Exception handling

		this.checkPositiveWeights(graph.getEdges());

		Collection<? extends IRestrictedVertex> vertices = graph.getVertices();
		IRestrictedVertex startVertex = graph.getStartVertex();

//		if (startVertex == null) {
//			throw new AlgorithmException(String.format(NO_START_VERTEX, graph));
//		}

		for (IRestrictedVertex vertex : vertices) {
			if (vertex == startVertex) {

				// start vertex has distance 0
				String cmt = String.format(START_VERTEX, startVertex.getName());
				rec.item(startVertex).cmt(cmt).res(0.0).add();
			} else {
				// initialize infinite distance from start vertex
				String cmt = String.format(RESULT_INIT, vertex.getName(),
						Double.POSITIVE_INFINITY);
				rec.item(vertex).cmt(cmt).res(Double.POSITIVE_INFINITY).add();
			}
		}

		this.calculateDistances(graph, vertices, startVertex, rec);
		rec.item(startVertex).visib().cmt(END_MSG).tag().add();
		this.updateUnreachableVertices(vertices, rec);
		rec.save();
	}

	/**
	 * 
	 * @param graph
	 * @param vertices
	 * @param startVertex
	 * @param rec
	 */
	private void calculateDistances(final IRestrictedGraph graph,
			final Collection<? extends IRestrictedVertex> vertices,
			final IRestrictedVertex startVertex, final IStepRecorder rec) {

		MapBinaryHeap<IRestrictedVertex> prioQueue = new MapBinaryHeap<>(
				this.vertexResultComparator);
		prioQueue.addAll(vertices);

		while (!prioQueue.isEmpty()) {
			IRestrictedVertex selectedVertex = prioQueue.poll();

			if (selectedVertex == startVertex) {

				double res = selectedVertex.getNewResult();
				String cmt = String.format(MIN_MSG, selectedVertex.getName(),
						res);
				rec.item(selectedVertex).app(cmt).state(ACTIVATION).cmtOk()
						.res(res).tag().add();
			} else {
				
				double res = selectedVertex.getNewResult();
				String cmt = String
						.format(MIN_MSG, selectedVertex.getName(), res);
				rec.item(selectedVertex).state(ACTIVATION).cmtOk().cmt(cmt)
				.res(res).tag().add();
			}
			rec.save();

			if (selectedVertex.getValue() != null) {
				IRestrictedEdge edge = graph.findEdge(
						(IRestrictedVertex) selectedVertex.getValue(),
						selectedVertex);
				
				rec.item(edge).state(SOLUTION).tag().add();
			}
			if (this.updateEndVertexMessage(startVertex, selectedVertex, rec)) {
				this.showShortestPath(graph, selectedVertex, rec);
				return;
			}
			
			rec.item(selectedVertex).state(SOLUTION).cmtOk().tag().add();
			this.setSuccessorMessage(graph, selectedVertex);			
			rec.save();

			rec.item(selectedVertex).state(SOLUTION).notCmt().notTag().add();
			this.updateAdjacentVertexDistances(graph, selectedVertex,
					prioQueue, rec);
		}
	}

	/**
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

			if (adjacentVertex.getCurrentState() == ItemState.SOLUTION) {
				if (edge.getCurrentState() != ItemState.SOLUTION
						&& edge.getCurrentState() != ItemState.ELIMINATION) {

					rec.item(edge).state(ELIMINATION).cmtOk().tag().dash().add();
					rec.save();
				}
			} else {
				if (newDistance < oldDistance) {
					rec.item(edge).state(VISIT).cmtOk().tag().add();
					
					// update shortest path for adjacentVertex
					String cmt = String.format(SHORTEST_PATH_UPDATE,
							adjacentVertex.getName(), newDistance);
					rec.item(adjacentVertex).state(VISIT).cmtOk().cmt(cmt).tag().
						res(newDistance).value(vertex).add();

					this.updatePredecessors(graph, vertex, rec, adjacentVertex);
				} else {
					rec.item(edge).state(ELIMINATION).cmtOk().tag().dash().add();
					rec.item(adjacentVertex).state(VISIT).cmtOk().tag().add();
				}
				rec.save();
				
				rec.item(adjacentVertex).notCmt().notTag().add();
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
	 * @param rec
	 * @param adjacentVertex
	 */
	private void updatePredecessors(final IRestrictedGraph graph,
			final IRestrictedVertex currentVertex, final IStepRecorder rec,
			final IRestrictedVertex adjacentVertex) {

		for (IRestrictedVertex predecessor : graph
				.getPredecessors(adjacentVertex)) {
			if (predecessor.getCurrentState() == ItemState.SOLUTION
					&& predecessor != currentVertex) {
				IRestrictedEdge refusedEdge = graph.findEdge(predecessor,
						adjacentVertex);

				if (refusedEdge.getCurrentState() != ItemState.ELIMINATION) {
					
					rec.item(refusedEdge).state(ELIMINATION).cmtOk().tag().dash().add();
				}
			}
		}
	}

	/**
	 * 
	 * @param startVertex
	 * @param endVertex
	 * @param rec
	 * @return boolean
	 */
	private boolean updateEndVertexMessage(final IRestrictedVertex startVertex,
			final IRestrictedVertex endVertex, final IStepRecorder rec) {

		if (endVertex.isEnd()) {
			double res = endVertex.getCurrentResult();
			String cmt = String
					.format(SHORTEST_PATH_OK, startVertex.getName(),
							endVertex.getName(), res);
			
			rec.item(endVertex).state(SOLUTION).cmtOk().cmt(cmt).notTag().add();						
			rec.save();
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
			vertex.appendComment(String.format(SUCCESSOR_MSG, vertex.getName(),
					successors));
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
				// TODO user dialog message
				throw new AlgorithmException(NEG_WEIGHT, ALGO_NAME);
			}
		}
	}

	/**
	 * @param graph
	 * @param selectedVertex
	 * @param rec
	 */
	private void showShortestPath(final IRestrictedGraph graph,
			IRestrictedVertex selectedVertex, final IStepRecorder rec) {

		for (IRestrictedEdge edge : graph.getEdges()) {
			rec.item(edge).state(INITIAL).notVisib().notCmt().add();
		}

		for (IRestrictedVertex vertex : graph.getVertices()) {
			rec.item(vertex).state(INITIAL).notVisib().notCmt().add();
		}

		rec.item(selectedVertex).state(SOLUTION).visib().notCmt().add();
		
		IRestrictedVertex currentVertex = null;
		while (selectedVertex.getValue() != null) {
			currentVertex = (IRestrictedVertex) selectedVertex.getValue();
			IRestrictedEdge edge = graph
					.findEdge(currentVertex, selectedVertex);
			
			rec.item(edge).state(SOLUTION).visib().notCmt().add();
			rec.item(currentVertex).state(SOLUTION).visib().notCmt().add();
			
			selectedVertex = currentVertex;
		}
	}

	/**
	 * @param vertices
	 * @param rec
	 */
	private void updateUnreachableVertices(
			final Collection<? extends IRestrictedVertex> vertices,
			final IStepRecorder rec) {

		for (IRestrictedVertex vertex : vertices) {
			if (vertex.getCurrentResult() == Double.POSITIVE_INFINITY) {
				rec.item(vertex).state(ELIMINATION).visib().notTag().notCmt();
			}
		}
	}

}
