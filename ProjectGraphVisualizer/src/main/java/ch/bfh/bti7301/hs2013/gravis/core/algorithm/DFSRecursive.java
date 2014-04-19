package ch.bfh.bti7301.hs2013.gravis.core.algorithm;


import ch.bfh.bti7301.hs2013.gravis.core.graph.GraphFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.ItemState;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.bti7301.hs2013.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * Depth-first search (DFS) algorithm, implemented recursively
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class DFSRecursive extends AbstractAlgorithm {

	private final static String ALGO_NAME = "Depth-First-Search (DFS) rekursiv";
	private final static String ALGO_DESCRIPTION = "Der Graph wird in Preorder traversiert. "
			+ "Es sind sowohl gerichtete als auch ungerichtete Graphen zulässig."
			+ "Die Knoten werden in Preorder-Reihenfolge nummeriert.";
	private final static String END_MSG1 = "Der Endknoten %s wurde erreicht."
			 + System.lineSeparator();
	private final static String END_MSG2 = "Die Preorder-Traversierung wurde erfolgreich "
			+ "beendet." + System.lineSeparator();

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
//		
//		if (this.updateEndVertexMessage(vertex1, updateHandler)) {
//			return true;
//		}
//		updateHandler.add(vertex1, ItemState.SOLUTION, true, ++this.counter, true, false, true);
//		updateHandler.update();
//
//		updateHandler.add(vertex1, ItemState.SOLUTION, false, false);
//
//		for (IRestrictedVertex vertex2 : graph.getSuccessors(vertex1)) {
//			IRestrictedEdge edge = graph.findEdge(vertex1, vertex2);
//			
//			if (vertex2.isDone()) {
//				updateHandler.add(edge, ItemState.ELIMINATION, true, true, true);
//				updateHandler.update();
//			} else {
//				updateHandler.add(edge, ItemState.SOLUTION, true, true);
//
//				boolean abort = this.visit(graph, updateHandler, vertex2);
//				if (abort) {
//					return true;
//				}
//			}
//			
//			updateHandler.add(vertex1, ItemState.ACTIVATION, true, true);
//			updateHandler.update();
//
//			updateHandler.add(vertex1, ItemState.SOLUTION, false, false);
//		}
//
//		return false;
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
//	
}
