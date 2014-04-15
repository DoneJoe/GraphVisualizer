package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.collections15.map.HashedMap;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.comparator.EdgeWeightComparator;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.Partition;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class AlgorithmKruskalMinSpanningForest extends AbstractAlgorithm {

	private final static String DIRECTED_EDGE = "Kruskal algorithm: DIRECTED "
			+ "edges are not allowed!";

	private final static String ALGO_NAME = "Algorithmus von Kruskal";
	private final static String ALGO_DESCRIPTION = "Diese Implementation "
			+ "berechnet einen minimalen Spannbaum für einen gegebenen Graphen. "
			+ "Der Graph muss ungerichtet sein. Ein Start- und Endknoten muss nicht "
			+ "angegeben werden. Die Kanten der Lösung zeigen neben dem Gewicht auch "
			+ "an, in welcher Reihenfolge sie zur Lösung hinzugefügt wurden. Besteht "
			+ "der Graph aus mehreren Zusammenhangskomponenten, so wird ein minimaler "
			+ "Spannbaum für jede Komponente berechnet.";
	private final static String MIN_EDGE = "Die Kante %s hat im Moment das kleinste  "
			+ "Gewicht: %.1f";
	private final static String CIRCLE_EDGE = "Die Kante %s kann nicht zur Lösung "
			+ "hinzugefügt werden, da sonst ein Kreis ensteht.";
	private final static String END_MSG = "Der minimale Spannwald wurde erfolgreich "
			+ "berechnet.";

	private int counter = 0;

	public AlgorithmKruskalMinSpanningForest() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.UNDIRECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.AbstractAlgorithm#execute
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph)
	 */
	@Override
	public void execute(final IRestrictedGraph graph) throws AlgorithmException {
		this.checkEdgeType(graph);

		this.counter = 0;
		Map<IRestrictedVertex, Partition<IRestrictedVertex>> partitionMap = new HashedMap<>();
		Collection<? extends IRestrictedVertex> vertices = graph.getVertices();
		PriorityQueue<IRestrictedEdge> prioQueue = new PriorityQueue<>(
				vertices.size(), new EdgeWeightComparator());
		IGraphUpdateHandler updateHandler = GraphFactory
				.createGraphUpdateHandler(graph);

		for (IRestrictedVertex vertex : vertices) {
			partitionMap.put(vertex, Partition.singleton(vertex));
		}

		prioQueue.addAll(graph.getEdges());
		IRestrictedEdge selectedEdge = buildSpanningForrest(graph,
				partitionMap, prioQueue, updateHandler);

		if (selectedEdge != null) {
			selectedEdge.appendComment(END_MSG);
			updateHandler.add(selectedEdge);
			updateHandler.update();
		}
	}

	/**
	 * 
	 * @param graph
	 * @param partitionMap
	 * @param prioQueue
	 * @param updateHandler
	 * @return IRestrictedEdge
	 */
	private IRestrictedEdge buildSpanningForrest(final IRestrictedGraph graph,
			final Map<IRestrictedVertex, Partition<IRestrictedVertex>> partitionMap,
			final PriorityQueue<IRestrictedEdge> prioQueue,
			final IGraphUpdateHandler updateHandler) {
		IRestrictedEdge selectedEdge = null;

		while (!prioQueue.isEmpty()) {
			selectedEdge = prioQueue.poll();
			Pair<? extends IRestrictedVertex> pair = graph
					.getEndpoints(selectedEdge);

			updateHandler.add(
					selectedEdge,
					State.ACTIVATION,
					true,
					String.format(MIN_EDGE, selectedEdge.getName(),
							selectedEdge.getWeight()), true);
			updateHandler.add(pair.getFirst(), State.ACTIVATION, true, true);
			updateHandler.add(pair.getSecond(), State.ACTIVATION, true, true);
			updateHandler.update();

			updateHandler.add(pair.getFirst(), false, false);
			updateHandler.add(pair.getSecond(), false, false);
			if (!Partition.areMerged(partitionMap.get(pair.getFirst()).find(),
					partitionMap.get(pair.getSecond()).find())) {
				updateHandler.add(selectedEdge, State.SOLUTION, true,
						++this.counter, true);
				if (!pair.getFirst().isDone()) {
					updateHandler.add(pair.getFirst(), State.SOLUTION, true,
							true, false, true);
				} else {
					updateHandler.add(pair.getFirst(), State.SOLUTION, true,
							true);
				}
				if (!pair.getSecond().isDone()) {
					updateHandler.add(pair.getSecond(), State.SOLUTION, true,
							true, false, true);
				} else {
					updateHandler.add(pair.getSecond(), State.SOLUTION, true,
							true);
				}
				updateHandler.update();

				updateHandler.add(pair.getFirst(), false, false);
				updateHandler.add(pair.getSecond(), false, false);
				partitionMap.get(pair.getFirst()).merge(
						partitionMap.get(pair.getSecond()));
			} else {
				updateHandler.add(selectedEdge, State.REFUSE, true,
						String.format(CIRCLE_EDGE, selectedEdge.getName()), true,
						true);
				updateHandler.update();

				updateHandler.add(selectedEdge, false);
				updateHandler
						.add(pair.getFirst(), State.SOLUTION, false, false);
				updateHandler.add(pair.getSecond(), State.SOLUTION, false,
						false);
			}
		}

		return selectedEdge;
	}

	/**
	 * @param graph
	 * @throws AlgorithmException
	 */
	private void checkEdgeType(final IRestrictedGraph graph)
			throws AlgorithmException {

		if (graph.getEdgeType() == EdgeType.DIRECTED) {
			throw new AlgorithmException(DIRECTED_EDGE);
		}
	}

}
