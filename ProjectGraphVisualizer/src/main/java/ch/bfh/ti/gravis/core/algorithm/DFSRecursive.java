package ch.bfh.ti.gravis.core.algorithm;

import java.util.List;
import java.util.Stack;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;

/**
 * This class implements a recursive version of the depth-first search (DFS)
 * algorithm. This implementation is based on pseudocode-descriptions at: <br />
 * <a href="http://en.wikipedia.org/wiki/Depth-first_search">http://en.wikipedia.
 * org/wiki/Depth-first_search</a> <br />
 * <a href="http://www.imn.htwk-leipzig.de/~waldmann/edu/ws04/graph/folien/graph/node45.html">
 * http://www.imn.htwk-leipzig.de/~waldmann/edu/ws04/graph/folien/graph/node45.html</a> <br />
 * If a reachable end vertex has been selected, the algorithm shows the path from the start
 * to the end vertex. All unreachable vertices are marked in the graph.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DFSRecursive extends AbstractAlgorithm {

	// algorithm name and description:

	private final static String ALGO_NAME = "Tiefensuche (DFS)";
	private final static String ALGO_DESCRIPTION = "Dies ist eine rekursive Implementation "
			+ "von DFS. Der Graph wird in Preorder traversiert und jeder "
			+ "Knoten aufsteigend nummeriert. Ist kein Startknoten gesetzt, so wird "
			+ "der Knoten mit dem lexikographisch kleinsten Namen als Startknoten ausgewählt. "
			+ "Der Graph wird traversiert bis entweder der Endknoten (falls vorhanden) erreicht "
			+ "oder alle vom Start aus erreichbaren Knoten besucht wurden. Es sind sowohl "
			+ "gerichtete als auch ungerichtete Graphen zulässig. Das Gewicht wird "
			+ "bei diesm Algorithmus nicht berücksichtigt.";

	// protocol messages:

	private final static String START_MSG = "%s ist der Startknoten." + LN;
	private final static String VISITED_V_MSG = "Der Knoten %s wurde besucht."
			+ LN;
	private final static String VISITED_E_MSG = "Die Kante %s wurde besucht."
			+ LN;
	private final static String BACK_V_MSG = "Zurück zum Knoten %s." + LN;
	private final static String DISCARDED_E_MSG = "Die Kante %s ist keine Baumkante."
			+ LN;
	private final static String END_V_MSG = "Der Endknoten %s wurde erreicht."
			+ LN;
	private final static String PATH_VERTICES = "Weg vom Startknoten zum Endknoten: %s"
			+ LN;
	private final static String NO_END_V_MSG = "Alle vom Startknoten %s aus erreichbaren "
			+ "Knoten wurden besucht." + LN;

	private int counter = 0;

	/**
	 * Constructor
	 */
	protected DFSRecursive() {
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
		Stack<IRestrictedVertex> path = new Stack<>();
		String msg = String.format(START_MSG, graph.getStartVertex().getName());

		rec.item(graph.getStartVertex()).cmt(msg).add();
		// start recursive dfs
		if (this.dfs(graph, rec, path, graph.getStartVertex())) {
			this.showPathToEndMsg(graph, rec, path);
		} else {
			this.updateUnreachableVertices(graph, rec);
		}
	}

	/**
	 * Applies a recursive dfs to the given vertex and graph.
	 * 
	 * @param graph
	 * @param rec
	 * @param path stores the visited vertices from start to end vertex
	 * @param vertex
	 * @return true, if end vertex has been located, false otherwise
	 */
	private boolean dfs(final IRestrictedGraph graph, final IStepRecorder rec,
			Stack<IRestrictedVertex> path, final IRestrictedVertex vertex) {

		// visit the current vertex
		String msg = String.format(VISITED_V_MSG, vertex.getName());
		rec.item(vertex).state(SOLVED).tag().res(++this.counter).app(msg).add();
		rec.save();
		path.push(vertex);

		if (vertex.isEnd()) {
			// stop recursion if end vertex has been reached
			return true;
		}

		// visit each successor of current vertex
		for (IRestrictedVertex successor : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, successor);

			// only visit not visited edges
			if (edge.isInitial()) {
				// only visit not visited vertices
				if (successor.isInitial()) {
					// visit incoming edge of successor vertex
					msg = String.format(VISITED_E_MSG, edge.getName());
					rec.item(edge).state(SOLVED).tag().app(msg).add();

					// recursive call of dfs with successor vertex
					if (this.dfs(graph, rec, path, successor)) {
						return true;
					}

					// activate the already visitied vertex
					msg = String.format(BACK_V_MSG, vertex.getName());
					rec.item(vertex).state(ACTIVATED).app(msg).add();
					rec.save();
					rec.item(vertex).state(SOLVED).add();
				} else {
					// successor has been visited before -> discard this edge
					msg = String.format(DISCARDED_E_MSG, edge.getName());
					rec.item(edge).state(DISCARDED).tag().dash().app(msg).add();
					rec.save();
				}
			}
		}

		// no end vertex found in successors of current vertex
		path.pop();
		return false;
	}

	/**
	 * Shows a message: the path from start vertex to end vertex.
	 * 
	 * @param graph
	 * @param rec
	 * @param path
	 */
	private void showPathToEndMsg(final IRestrictedGraph graph,
			final IStepRecorder rec, final List<IRestrictedVertex> path) {

		StringBuilder pathVertices = new StringBuilder();
		String endVertexName = "";

		// builds the path string
		for (IRestrictedVertex vertex : path) {
			pathVertices.append(vertex.getName() + " - ");

			if (vertex.isEnd()) {
				endVertexName = vertex.getName();
			}
		}

		pathVertices.delete(Math.max(0, pathVertices.length() - 3),
				Math.max(0, pathVertices.length()));
		// show the path message
		String msg = String.format(END_V_MSG, endVertexName);
		String pathMsg = String.format(PATH_VERTICES, pathVertices.toString());
		rec.item(graph.getStartVertex()).app(msg).app(pathMsg).add();
		rec.save();
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

}