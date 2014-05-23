package ch.bfh.ti.gravis.core.algorithm;


import java.util.Iterator;

import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.item.ItemState;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class PostorderRecursive extends AbstractAlgorithm {

	private final static String ALGO_NAME = "Postorder-Traversierung";
	private final static String ALGO_DESCRIPTION = "Der Graph wird in Postorder traversiert. "
			+ "Es sind sowohl gerichtete als auch ungerichtete Graphen zulässig."
			+ "Die Knoten werden in Postorder-Reihenfolge nummeriert.";
	
	private final static String END_MSG1 = "Der Endknoten %s wurde erreicht."
			 + LN;
	private final static String END_MSG2 = "Die Postorder-Traversierung wurde erfolgreich "
			+ "beendet." + LN;

	private int counter = 0;

	protected PostorderRecursive() {
		super(ALGO_NAME, ALGO_DESCRIPTION);
		this.addEdgeType(EdgeType.DIRECTED);
		this.addEdgeType(EdgeType.UNDIRECTED);
	}

	@Override
	public void execute(final IRestrictedGraph graph, final IStepRecorder rec) {
		
		// TODO implement
		
//		IGraphUpdateHandler updateHandler = GraphFactory
//				.createGraphUpdateHandler(graph);
//		IRestrictedVertex lastVertex = null;
//		this.counter = 0;
//
//		for (IRestrictedVertex vertex : graph.getVertices()) {
//			lastVertex = vertex;
//			
//			if (!vertex.isDone()) {
//				boolean abort = this.visit(graph, updateHandler, vertex);
//
//				if (abort) {
//					break;
//				}
//			}
//		}
//
//		if (lastVertex != null) {
//			lastVertex.appendComment(END_MSG2);
//			updateHandler.add(lastVertex, false, false);
//			updateHandler.update();
//		}
	}

//	/**
//	 * 
//	 * @param graph
//	 * @param updateHandler
//	 * @param vertex1
//	 * @return boolean
//	 */
//	private boolean visit(final IRestrictedGraph graph,
//			final IGraphUpdateHandler updateHandler, final IRestrictedVertex vertex1) {
//		updateHandler.add(vertex1, ItemState.VISIT, true, true, false, true);
//		updateHandler.update();
//
//		updateHandler.add(vertex1, ItemState.VISIT, false, false);
//		Iterator<? extends IRestrictedVertex> vertexIterator = graph
//				.getSuccessors(vertex1).iterator();
//		while (vertexIterator.hasNext()) {
//			IRestrictedVertex vertex2 = vertexIterator.next();
//			IRestrictedEdge edge = graph.findEdge(vertex1, vertex2);
//
//			if (vertex2.isDone()) {
//				updateHandler.add(edge, ItemState.ELIMINATION, true, true, true);
//				updateHandler.update();
//			} else {
//				updateHandler.add(edge, ItemState.VISIT, true, true);
//
//				boolean abort = this.visit(graph, updateHandler, vertex2);
//
//				if (abort) {
//					return true;
//				}
//			}
//			
//			this.activateVertex(graph, updateHandler, vertex1, vertexIterator);
//		}
//
//		this.tagSolvedEdges(graph, updateHandler, vertex1);
//		if (this.updateEndVertexMessage(vertex1, updateHandler)) {
//			return true;
//		}
//		updateHandler.add(vertex1, ItemState.SOLUTION, true, ++this.counter, true);
//		updateHandler.update();
//
//		updateHandler.add(vertex1, ItemState.SOLUTION, false, false);
//		return false;
//	}
//
//	/**
//	 * 
//	 * @param graph
//	 * @param updateHandler
//	 * @param vertex1
//	 * @param vertexIterator
//	 */
//	private void activateVertex(final IRestrictedGraph graph,
//			final IGraphUpdateHandler updateHandler, final IRestrictedVertex vertex1,
//			final Iterator<? extends IRestrictedVertex> vertexIterator) {
//
//		if (vertexIterator.hasNext()) {
//			updateHandler.add(vertex1, ItemState.ACTIVATION, true, true);
//			updateHandler.update();
//
//			updateHandler.add(vertex1, ItemState.VISIT, false, false);
//		}
//	}
//
//	/**
//	 * 
//	 * @param graph
//	 * @param updateHandler
//	 * @param vertex1
//	 */
//	private void tagSolvedEdges(final IRestrictedGraph graph,
//			final IGraphUpdateHandler updateHandler, final IRestrictedVertex vertex1) {
//
//		for (IRestrictedVertex vertex : graph.getSuccessors(vertex1)) {
//			IRestrictedEdge edge = graph.findEdge(vertex1, vertex);
//			
//			if (vertex.getCurrentState() == ItemState.SOLUTION && edge.getCurrentState() !=
//					ItemState.ELIMINATION) {
//				updateHandler.add(edge, ItemState.SOLUTION, true, true);
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param endVertex
//	 * @param updateHandler
//	 * @return boolean
//	 */
//	private boolean updateEndVertexMessage(final IRestrictedVertex endVertex,
//			final IGraphUpdateHandler updateHandler) {
//
//		if (endVertex.isEnd()) {
//			updateHandler.add(endVertex, ItemState.SOLUTION, true, String.format(
//					END_MSG1, endVertex.getName()), ++this.counter, true, false, true);
//			updateHandler.update();
//			
//			updateHandler.add(endVertex, ItemState.SOLUTION, false, false);
//			return true;
//		}
//		return false;
//	}

}
