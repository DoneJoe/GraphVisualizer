package ch.bfh.bti7301.hs2013.gravis.core.algorithm;


import java.util.Iterator;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphUpdateHandler;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class AlgorithmDLSRecursive extends AbstractAlgorithm implements IAlgorithm {

	private final static String ALGO_NAME = "Rekursiver Depth-Last-Search Algorithmus (DLS)";
	private final static String ALGO_DESCRIPTION = "Der Graph wird in Postorder traversiert. "
			+ "Es sind sowohl gerichtete als auch ungerichtete Graphen zulässig."
			+ "Die Knoten werden in Postorder-Reihenfolge nummeriert.";
	private final static String END_MSG1 = "Der Endknoten %s wurde erreicht.";
	private final static String END_MSG2 = "Die Postorder-Traversierung wurde erfolgreich "
			+ "beendet.";

	private int counter = 0;

	protected AlgorithmDLSRecursive() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.AbstractAlgorithm#execute
	 * (ch.bfh .bti7301.hs2013.gravis.graph.IImmutableGraph)
	 */
	@Override
	public void execute(IRestrictedGraph graph) {
		IGraphUpdateHandler updateHandler = GraphFactory
				.createGraphUpdateHandler(graph);
		IRestrictedVertex lastVertex = null;
		this.counter = 0;

		for (IRestrictedVertex vertex : graph.getVertices()) {
			lastVertex = vertex;
			
			if (!vertex.isDone()) {
				boolean abort = this.visit(graph, updateHandler, vertex);

				if (abort) {
					break;
				}
			}
		}

		if (lastVertex != null) {
			lastVertex.appendToNewComment(END_MSG2);
			updateHandler.add(lastVertex, false, false);
			updateHandler.update();
		}
	}

	/**
	 * 
	 * @param graph
	 * @param updateHandler
	 * @param vertex1
	 * @return boolean
	 */
	private boolean visit(IRestrictedGraph graph,
			IGraphUpdateHandler updateHandler, IRestrictedVertex vertex1) {
		updateHandler.add(vertex1, State.VISIT, true, true, false, true);
		updateHandler.update();

		updateHandler.add(vertex1, State.VISIT, false, false);
		Iterator<? extends IRestrictedVertex> vertexIterator = graph
				.getSuccessors(vertex1).iterator();
		while (vertexIterator.hasNext()) {
			IRestrictedVertex vertex2 = vertexIterator.next();
			IRestrictedEdge edge = graph.findEdge(vertex1, vertex2);

			if (vertex2.isDone()) {
				updateHandler.add(edge, State.REFUSE, true, true, true);
				updateHandler.update();
			} else {
				updateHandler.add(edge, State.VISIT, true, true);

				boolean abort = this.visit(graph, updateHandler, vertex2);

				if (abort) {
					return true;
				}
			}
			
			this.activateVertex(graph, updateHandler, vertex1, vertexIterator);
		}

		this.tagSolvedEdges(graph, updateHandler, vertex1);
		if (this.updateEndVertexMessage(vertex1, updateHandler)) {
			return true;
		}
		updateHandler.add(vertex1, State.SOLUTION, true, ++this.counter, true);
		updateHandler.update();

		updateHandler.add(vertex1, State.SOLUTION, false, false);
		return false;
	}

	/**
	 * 
	 * @param graph
	 * @param updateHandler
	 * @param vertex1
	 * @param vertexIterator
	 */
	private void activateVertex(IRestrictedGraph graph,
			IGraphUpdateHandler updateHandler, IRestrictedVertex vertex1,
			Iterator<? extends IRestrictedVertex> vertexIterator) {

		if (vertexIterator.hasNext()) {
			updateHandler.add(vertex1, State.ACTIVATION, true, true);
			updateHandler.update();

			updateHandler.add(vertex1, State.VISIT, false, false);
		}
	}

	/**
	 * 
	 * @param graph
	 * @param updateHandler
	 * @param vertex1
	 */
	private void tagSolvedEdges(IRestrictedGraph graph,
			IGraphUpdateHandler updateHandler, IRestrictedVertex vertex1) {

		for (IRestrictedVertex vertex : graph.getSuccessors(vertex1)) {
			IRestrictedEdge edge = graph.findEdge(vertex1, vertex);
			
			if (vertex.getCurrentState() == State.SOLUTION && edge.getCurrentState() !=
					State.REFUSE) {
				updateHandler.add(edge, State.SOLUTION, true, true);
			}
		}
	}

	/**
	 * 
	 * @param endVertex
	 * @param updateHandler
	 * @return boolean
	 */
	private boolean updateEndVertexMessage(IRestrictedVertex endVertex,
			IGraphUpdateHandler updateHandler) {

		if (endVertex.isEnd()) {
			updateHandler.add(endVertex, State.SOLUTION, true, String.format(
					END_MSG1, endVertex.getId()), ++this.counter, true, false, true);
			updateHandler.update();
			
			updateHandler.add(endVertex, State.SOLUTION, false, false);
			return true;
		}
		return false;
	}

}
