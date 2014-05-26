package ch.bfh.ti.gravis.core.algorithm;

import java.text.DecimalFormat;
import java.util.PriorityQueue;
import java.util.Stack;

import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.comparator.EdgeWeightComparator;
import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.ItemState;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import ch.bfh.ti.gravis.core.util.Partition;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;

/**
 * The Kruskal-algorithm calculates a minimal spanning forest. This
 * implementation is based on pseudocode-description at: <br />
 * <a href="http://en.wikipedia.org/wiki/Kruskal%27s_algorithm">http://en.
 * wikipedia.org/wiki/Kruskal%27s_algorithm</a> <br />
 * The algorithm is implemented with a disjoint-set data structure: <br />
 * <a href="http://en.wikipedia.org/wiki/Disjoint-set_data_structure">http://en.
 * wikipedia.org/wiki/Disjoint-set_data_structure</a> <br />
 * The DFS algorithm is used for finding cycles.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class KruskalMSF extends AbstractAlgorithm {

	// exception dialog message:

	private final static String DIRECTED_EDGE_ERR = "Es sind nur ungerichtete Graphen erlaubt!";

	// algorithm name and description:

	private final static String ALGO_NAME = "Algorithmus von Kruskal";
	private final static String ALGO_DESCRIPTION = "Der Algorithmus von Kruskal "
			+ "berechnet einen minimalen Spannbaum für einen ungerichteten Graphen. "
			+ "Es sind nur ungerichtete Graphen erlaubt. Ist der Graph unzusammenhängend,  "
			+ "so wird ein minimaler Spannbaum für jede Zusammenhangskomponente berechnet "
			+ "(minimaler aufspannender Wald). Ein Start- und Endknoten muss nicht angegeben "
			+ "werden. Die Kanten der Lösung zeigen neben dem Gewicht auch an, in welcher "
			+ "Reihenfolge sie zur Lösung hinzugefügt wurden.";

	// protocol messages:

	private final static String SELECT_E_MSG = "Die Kante %s wurde ausgewählt."
			+ LN;
	private final static String SOLVED_W_MSG = "Sie hat folgendes Gewicht: %s"
			+ LN;
	private final static String CYCLE_MSG = "Mit der Kante %s entsteht ein Zyklus."
			+ LN;
	private final static String END_MSG = "Der minimal aufspannende Wald wurde berechnet."
			+ LN;

	private int counter = 0;

	private final DecimalFormat format;

	public KruskalMSF() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.UNDIRECTED);

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
		this.validateEdgeType(graph);

		this.counter = 0;

		// initialize singleton partitions
		for (IRestrictedVertex vertex : graph.getVertices()) {
			vertex.setValue(Partition.singleton(vertex));
		}

		this.buildMSF(graph, rec);
		this.hideDiscardedEdges(graph, rec);
	}

	/**
	 * Builds the minimal spanning forest. The Partion class is used for building the MSF.
	 * The Partition class is an implementation of a disjoint-set data structure. 
	 * 
	 * @param graph
	 * @param rec
	 */
	@SuppressWarnings("unchecked")
	private void buildMSF(final IRestrictedGraph graph, final IStepRecorder rec) {
		PriorityQueue<IRestrictedEdge> prioQueue = new PriorityQueue<>(
				graph.getEdgeCount(), new EdgeWeightComparator());

		prioQueue.addAll(graph.getEdges());
		while (!prioQueue.isEmpty()) {
			// select edge with minimal weight
			IRestrictedEdge selectedEdge = prioQueue.poll();
			Pair<? extends IRestrictedVertex> pair = graph
					.getEndpoints(selectedEdge);
			Partition<IRestrictedVertex> partition1 = (Partition<IRestrictedVertex>) pair
					.getFirst().getValue();
			Partition<IRestrictedVertex> partition2 = (Partition<IRestrictedVertex>) pair
					.getSecond().getValue();
			ItemState state1 = pair.getFirst().getCurrentState();
			ItemState state2 = pair.getSecond().getCurrentState();
			String msg = String.format(SELECT_E_MSG, selectedEdge.getName());

			// hint: selected items have VISITED state
			rec.item(selectedEdge).state(VISITED).cmt(msg).tag().add();
			rec.item(pair.getFirst()).state(VISITED).tag().add();
			rec.item(pair.getSecond()).state(VISITED).tag().add();
			rec.save();

			// if partitions areMerged == true -> a cycle exists
			if (Partition.areMerged(partition1, partition2)) {
				this.showCycle(graph, rec, selectedEdge, pair);

				// restore the previous state
				rec.item(pair.getFirst()).state(state1).tag().add();
				rec.item(pair.getSecond()).state(state2).tag().add();
				rec.save();
			} else {
				msg = String.format(SOLVED_W_MSG,
						this.format.format(selectedEdge.getWeight()));

				// mark the selectedEdge with SOLVED
				rec.item(selectedEdge).state(SOLVED).res(++this.counter)
						.cmtOk().cmt(msg).tag().add();
				rec.item(pair.getFirst()).state(SOLVED).tag().add();
				rec.item(pair.getSecond()).state(SOLVED).tag().add();
				rec.save();

				partition1.merge(partition2);
			}
		}
	}

	/**
	 * Shows a cycle in the graph.
	 * 
	 * @param graph
	 * @param rec
	 * @param selectedEdge
	 * @param pair
	 */
	private void showCycle(final IRestrictedGraph graph,
			final IStepRecorder rec, final IRestrictedEdge selectedEdge,
			Pair<? extends IRestrictedVertex> pair) {

		Stack<IRestrictedGraphItem> path = new Stack<>();
		String msg = String.format(CYCLE_MSG, selectedEdge.getName());

		rec.item(selectedEdge).app(msg).tag().add();
		// reset done variable to false for all graph items
		graph.resetItemDoneVar();
		// this if clause should always be true
		if (this.cycleDFS(graph, path, pair.getFirst(), selectedEdge, pair)) {
			// mark the cycle elements with DISCARDED
			for (IRestrictedGraphItem item : path) {
				rec.item(item).state(DISCARDED).add();
			}
			rec.save();

			// mark the cycle elements with SOLVED
			while (!path.isEmpty()) {
				rec.item(path.pop()).state(SOLVED).add();
			}
		}
		graph.resetItemDoneVar();

		// discard selectedEdge
		rec.item(selectedEdge).state(DISCARDED).cmtOk().tag().dash().add();
	}

	/**
	 * Applies a recursive DFS to the given vertex and graph. A stack is used to
	 * keep track of the path between the start vertex and the current vertex.
	 * 
	 * @param graph
	 * @param path
	 * @param vertex
	 * @param pair
	 * @param selectedEdge
	 * @return true, if a cycle is found, false otherwise
	 */
	private boolean cycleDFS(final IRestrictedGraph graph,
			final Stack<IRestrictedGraphItem> path,
			final IRestrictedVertex vertex, final IRestrictedEdge selectedEdge,
			final Pair<? extends IRestrictedVertex> pair) {

		vertex.setDone(true);
		path.push(vertex);

		// visit all successors of current vertex
		for (IRestrictedVertex successor : graph.getSuccessors(vertex)) {
			IRestrictedEdge edge = graph.findEdge(vertex, successor);

			// only solved items or selected items are considered
			if ((edge.isSolved() || edge == selectedEdge)
					&& (successor.isSolved() || successor == pair.getFirst() || successor == pair
							.getSecond()) && !edge.isDone()) {

				path.push(edge);
				// if a successor is already done, a cycle exitsts
				if (!successor.isDone()) {
					edge.setDone(true);

					// recursive call of cycleDFS with successor vertex
					if (this.cycleDFS(graph, path, successor, selectedEdge,
							pair)) {
						return true;
					}

					path.pop();
				} else {
					return true;
				}
			}
		}

		// no cycle exists for successors of the current vertex
		path.pop();
		return false;
	}

	/**
	 * Hides all discarded edges in the graph.
	 * 
	 * @param graph
	 * @param rec
	 */
	private void hideDiscardedEdges(final IRestrictedGraph graph,
			final IStepRecorder rec) {
		// set end message
		rec.item(graph.getStartVertex()).cmt(END_MSG).add();

		for (IRestrictedEdge edge : graph.getEdges()) {
			if (edge.isDiscarded()) {
				rec.item(edge).notVisib().add();
			}
		}

		rec.save();
	}

	/**
	 * Checks if edge type of graph is UNDIRECTED.
	 * 
	 * @param graph
	 * @throws AlgorithmException
	 *             if edge type is DIRECTED
	 */
	private void validateEdgeType(final IRestrictedGraph graph)
			throws AlgorithmException {
		if (graph.getEdgeType() == EdgeType.DIRECTED) {
			throw new AlgorithmException(DIRECTED_EDGE_ERR, ALGO_NAME);
		}
	}

}
