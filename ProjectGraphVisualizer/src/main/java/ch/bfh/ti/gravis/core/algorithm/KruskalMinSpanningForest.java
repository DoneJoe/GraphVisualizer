package ch.bfh.ti.gravis.core.algorithm;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.collections15.map.HashedMap;

import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.comparator.EdgeWeightComparator;
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
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class KruskalMinSpanningForest extends AbstractAlgorithm {

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

	private final static String MIN_EDGE = "Die Kante %s hat im Moment das kleinste  "
			+ "Gewicht: %.1f" + LN;
	private final static String CIRCLE_EDGE = "Die Kante %s kann nicht zur Lösung "
			+ "hinzugefügt werden, da sonst ein Kreis ensteht."
			+ LN;
	private final static String END_MSG = "Der minimale Spannwald wurde erfolgreich "
			+ "berechnet." + LN;

	private int counter = 0;

	public KruskalMinSpanningForest() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.UNDIRECTED);
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

		// TODO implement

		
		// Map<IRestrictedVertex, Partition<IRestrictedVertex>> partitionMap =
		// new HashedMap<>();
		// Collection<? extends IRestrictedVertex> vertices =
		// graph.getVertices();
		// PriorityQueue<IRestrictedEdge> prioQueue = new PriorityQueue<>(
		// vertices.size(), new EdgeWeightComparator());
		// IGraphUpdateHandler updateHandler = GraphFactory
		// .createGraphUpdateHandler(graph);
		//
		// for (IRestrictedVertex vertex : vertices) {
		// partitionMap.put(vertex, Partition.singleton(vertex));
		// }
		//
		// prioQueue.addAll(graph.getEdges());
		// IRestrictedEdge selectedEdge = buildSpanningForrest(graph,
		// partitionMap, prioQueue, updateHandler);
		//
		// if (selectedEdge != null) {
		// selectedEdge.appendComment(END_MSG);
		// updateHandler.add(selectedEdge);
		// updateHandler.update();
		// }
	}

	/**
	 * @param graph
	 * @throws AlgorithmException
	 */
	private void validateEdgeType(final IRestrictedGraph graph)
			throws AlgorithmException {
		if (graph.getEdgeType() == EdgeType.DIRECTED) {
			throw new AlgorithmException(DIRECTED_EDGE_ERR, ALGO_NAME);
		}
	}

	// /**
	// *
	// * @param graph
	// * @param partitionMap
	// * @param prioQueue
	// * @param updateHandler
	// * @return IRestrictedEdge
	// */
	// private IRestrictedEdge buildSpanningForrest(final IRestrictedGraph
	// graph,
	// final Map<IRestrictedVertex, Partition<IRestrictedVertex>> partitionMap,
	// final PriorityQueue<IRestrictedEdge> prioQueue,
	// final IGraphUpdateHandler updateHandler) {
	// IRestrictedEdge selectedEdge = null;
	//
	// while (!prioQueue.isEmpty()) {
	// selectedEdge = prioQueue.poll();
	// Pair<? extends IRestrictedVertex> pair = graph
	// .getEndpoints(selectedEdge);
	//
	// updateHandler.add(
	// selectedEdge,
	// ItemState.ACTIVATED,
	// true,
	// String.format(MIN_EDGE, selectedEdge.getName(),
	// selectedEdge.getWeight()), true);
	// updateHandler.add(pair.getFirst(), ItemState.ACTIVATED, true, true);
	// updateHandler.add(pair.getSecond(), ItemState.ACTIVATED, true, true);
	// updateHandler.update();
	//
	// updateHandler.add(pair.getFirst(), false, false);
	// updateHandler.add(pair.getSecond(), false, false);
	// if (!Partition.areMerged(partitionMap.get(pair.getFirst()).find(),
	// partitionMap.get(pair.getSecond()).find())) {
	// updateHandler.add(selectedEdge, ItemState.SOLVED, true,
	// ++this.counter, true);
	// if (!pair.getFirst().isDone()) {
	// updateHandler.add(pair.getFirst(), ItemState.SOLVED, true,
	// true, false, true);
	// } else {
	// updateHandler.add(pair.getFirst(), ItemState.SOLVED, true,
	// true);
	// }
	// if (!pair.getSecond().isDone()) {
	// updateHandler.add(pair.getSecond(), ItemState.SOLVED, true,
	// true, false, true);
	// } else {
	// updateHandler.add(pair.getSecond(), ItemState.SOLVED, true,
	// true);
	// }
	// updateHandler.update();
	//
	// updateHandler.add(pair.getFirst(), false, false);
	// updateHandler.add(pair.getSecond(), false, false);
	// partitionMap.get(pair.getFirst()).merge(
	// partitionMap.get(pair.getSecond()));
	// } else {
	// updateHandler.add(selectedEdge, ItemState.DISCARDED, true,
	// String.format(CIRCLE_EDGE, selectedEdge.getName()), true,
	// true);
	// updateHandler.update();
	//
	// updateHandler.add(selectedEdge, false);
	// updateHandler
	// .add(pair.getFirst(), ItemState.SOLVED, false, false);
	// updateHandler.add(pair.getSecond(), ItemState.SOLVED, false,
	// false);
	// }
	// }
	//
	// return selectedEdge;
	// }
	//
	// /**
	// * @param graph
	// * @throws AlgorithmException
	// */
	// private void checkEdgeType(final IRestrictedGraph graph)
	// throws AlgorithmException {
	//
	// if (graph.getEdgeType() == EdgeType.DIRECTED) {
	// throw new AlgorithmException(DIRECTED_EDGE_ERR);
	// }
	// }

}
