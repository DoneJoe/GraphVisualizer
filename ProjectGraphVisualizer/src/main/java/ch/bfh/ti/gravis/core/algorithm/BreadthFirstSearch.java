package ch.bfh.ti.gravis.core.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;

/**
 * This class implements a non-recursive version of the breadth-first search
 * (BFS) algorithm and uses a queue data structure. This implementation is based
 * on pseudocode-description at: <br />
 * <a href="http://de.wikipedia.org/wiki/Breitensuche">http://de.wikipedia.org/
 * wiki/Breitensuche</a> <br />
 * If a reachable end vertex is selected, the algorithm shows the path from the
 * start to the end vertex. If there is no reachable end vertex selected, all
 * unreachable vertices and edges are marked in the graph.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class BreadthFirstSearch extends AbstractAlgorithm {

	// algorithm name and description:

	private final static String ALGO_NAME = "Breitensuche (BFS)";
	private final static String ALGO_DESCRIPTION = "Die Breitensuche ist mit Hilfe "
			+ "einer Queue implementiert. Jeder besuchte Knoten wird aufsteigend nummeriert. "
			+ "Ist kein Startknoten gesetzt, so wird der Knoten mit dem lexikographisch "
			+ "kleinsten Namen als Startknoten ausgewählt. Der Graph wird traversiert, bis "
			+ "entweder der Endknoten (falls vorhanden) erreicht oder alle vom Start aus "
			+ "erreichbaren Knoten besucht und expandiert wurden. Es sind sowohl "
			+ "gerichtete als auch ungerichtete Graphen zulässig. Das Gewicht wird "
			+ "bei diesm Algorithmus nicht berücksichtigt.";

	// protocol messages:

	private final static String START_MSG = "%s ist der Startknoten." + LN;
	private final static String EXPANDED_V_MSG = "Der Knoten %s wird expandiert."
			+ LN;
	private final static String DISCARDED_E_MSG = "Die Kante %s ist keine Baumkante. "
			+ "Ihr Nachfolgeknoten %s wurde schon besucht." + LN;
	private final static String END_V_MSG = "Der Endknoten %s wurde erreicht."
			+ LN;
	private final static String PATH_VERTICES = "Weg vom Startknoten zum Endknoten: %s"
			+ LN;
	private final static String NO_END_V_MSG = "Alle vom Startknoten %s aus erreichbaren "
			+ "Knoten wurden besucht und expandiert." + LN;

	private int counter = 0;

	public BreadthFirstSearch() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);
	}

	@Override
	public void execute(final IRestrictedGraph graph, final IStepRecorder rec) {
		if (graph.isEmpty()) {
			// nothing to calculate
			return;
		}

		this.counter = 0;
		Queue<IRestrictedVertex> verticesQueue = new LinkedList<>();
		String msg = String.format(START_MSG, graph.getStartVertex().getName());

		// visit the start vertex
		rec.item(graph.getStartVertex()).state(VISITED).res(++this.counter)
				.cmtOk().cmt(msg).tag().add();
		rec.save();
		verticesQueue.offer(graph.getStartVertex());

		while (!verticesQueue.isEmpty()) {
			IRestrictedVertex vertex = verticesQueue.poll();
			this.expandEdge(graph, rec, vertex);

			// set expand-message to current vertex
			// hint: the SOLVED state is used for expanding vertices
			msg = String.format(EXPANDED_V_MSG, vertex.getName());
			rec.item(vertex).state(SOLVED).app(msg).tag().add();
			rec.save();

			if (vertex.isEnd()) {
				// the end vertex is reached
				this.showPathToEnd(graph, rec, vertex);
				return;
			}

			// visit the successors of the expanding vertex
			this.expandVertex(graph, rec, verticesQueue, vertex);
		}

		this.updateUnreachableVertices(graph, rec);
	}

	/**
	 * Visits the successors of the expanding vertex.
	 * 
	 * @param graph
	 * @param rec
	 * @param verticesQueue
	 * @param vertex
	 */
	private void expandVertex(final IRestrictedGraph graph,
			final IStepRecorder rec,
			final Queue<IRestrictedVertex> verticesQueue,
			final IRestrictedVertex vertex) {

		for (IRestrictedVertex successor : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, successor);

			// only visit not visited edges
			if (edge.isInitial()) {
				// only visit not visited vertices
				if (successor.isInitial()) {
					// visit incoming edge of successor vertex
					rec.item(edge).state(VISITED).cmtOk().tag().add();

					// visit successor, set predecessor and add successor to
					// queue
					rec.item(successor).state(VISITED).res(++this.counter)
							.cmtOk().tag().value(vertex).add();
					rec.save();
					verticesQueue.offer(successor);
				} else {
					// successor has been visited before -> discard this edge
					String msg = String.format(DISCARDED_E_MSG, edge.getName(),
							successor.getName());
					rec.item(edge).state(DISCARDED).tag().dash().app(msg).add();
					rec.save();
				}
			}
		}
	}

	/**
	 * The edge between the predecessor and the vertex is marked as expanded
	 * (only if the predecessor exists).
	 * 
	 * @param graph
	 * @param rec
	 * @param vertex
	 */
	private void expandEdge(final IRestrictedGraph graph,
			final IStepRecorder rec, final IRestrictedVertex vertex) {

		if (vertex.getValue() != null) {
			IRestrictedEdge edge = graph.findEdge(
					(IRestrictedVertex) vertex.getValue(), vertex);

			if (edge != null) {
				rec.item(edge).state(SOLVED).tag().add();
			}
		}
	}

	/**
	 * Discards unreachable vertices and edges.
	 * 
	 * @param graph
	 * @param rec
	 */
	private void updateUnreachableVertices(final IRestrictedGraph graph,
			final IStepRecorder rec) {

		// no end vertex has been found
		String msg = String.format(NO_END_V_MSG, graph.getStartVertex()
				.getName());
		rec.item(graph.getStartVertex()).app(msg).add();

		// discard unreachable vertices
		for (IRestrictedGraphItem vertex : graph.getVertices()) {
			if (vertex.isInitial()) {
				rec.item(vertex).state(DISCARDED).tag().add();
			}
		}

		// discard unreachable edges
		for (IRestrictedGraphItem edge : graph.getEdges()) {
			if (edge.isInitial()) {
				rec.item(edge).state(DISCARDED).tag().dash().add();
			}
		}

		rec.save();
	}

	/**
	 * Shows the path from start vertex to end vertex in the graph.
	 * 
	 * @param graph
	 * @param rec
	 * @param selectedVertex
	 */
	private void showPathToEnd(final IRestrictedGraph graph,
			final IStepRecorder rec, IRestrictedVertex selectedVertex) {

		// hide all graph elements
		for (IRestrictedEdge edge : graph.getEdges()) {
			rec.item(edge).notVisib().add();
		}
		for (IRestrictedVertex vertex : graph.getVertices()) {
			rec.item(vertex).notVisib().add();
		}

		IRestrictedVertex currentVertex = null;
		StringBuilder path = new StringBuilder();
		List<String> vertexNames = new ArrayList<>();
		String endVertexName = selectedVertex.getName();

		rec.item(selectedVertex).visib().add();
		vertexNames.add(selectedVertex.getName());
		// go back to the start vertex from the end vertex and make elements visible
		while (selectedVertex.getValue() != null) {
			currentVertex = (IRestrictedVertex) selectedVertex.getValue();
			IRestrictedEdge edge = graph
					.findEdge(currentVertex, selectedVertex);

			rec.item(edge).visib().add();
			rec.item(currentVertex).visib().add();
			vertexNames.add(currentVertex.getName());

			selectedVertex = currentVertex;
		}

		// build the path comment string
		for (int i = vertexNames.size() - 1; i >= 0; i--) {
			path.append(vertexNames.get(i) + " - ");
		}
		path.delete(Math.max(0, path.length() - 3), Math.max(0, path.length()));
		
		// show path message
		String msg = String.format(END_V_MSG, endVertexName);
		String pathMsg = String.format(PATH_VERTICES, path.toString());
		rec.item(selectedVertex).app(msg).app(pathMsg).add();
		rec.save();
	}

}
