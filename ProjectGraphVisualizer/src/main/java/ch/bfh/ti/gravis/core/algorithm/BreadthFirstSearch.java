package ch.bfh.ti.gravis.core.algorithm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IRestrictedGraph;
import ch.bfh.ti.gravis.core.graph.item.ItemState;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.step.IStepRecorder;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.ti.gravis.core.util.GravisConstants.LN;
import static ch.bfh.ti.gravis.core.graph.item.ItemState.*;


/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class BreadthFirstSearch extends AbstractAlgorithm {

	private final static String ALGO_NAME = "Breitensuche (BFS)";
	private final static String ALGO_DESCRIPTION = "Die Breitensuche ist iterativ "
			+ "implementiert. Es sind sowohl gerichtete als auch ungerichtete Graphen "
			+ "zulässig. Die Breitensuche wird für jede (schwache) Zusammenhangskomponente "
			+ "separat durchgefürt.";
	
	private final static String END_MSG1 = "Der Endknoten %s wurde erreicht."
			+ LN;
	private final static String END_MSG2 = "Die Breitensuche wurde erfolgreich beendet."
			+ LN;

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
		
		// TODO implement
		
//		this.counter = 0;
//
//		Queue<IRestrictedVertex> vertexQueue = new LinkedList<>();
//		Collection<? extends IRestrictedVertex> vertices = graph.getVertices();
//		IRestrictedVertex startVertex = graph.getStartVertex();
//		IGraphUpdateHandler updateHandler = GraphFactory
//				.createGraphUpdateHandler(graph);
//
//		startVertex.setDone(true);
//		vertexQueue.offer(startVertex);
//		vertices.remove(startVertex);
//
//		for (IRestrictedVertex rVertex : vertices) {
//			while (!vertexQueue.isEmpty()) {
//				IRestrictedVertex selectedVertex = vertexQueue.poll();
//
//				this.tagEdge(graph, updateHandler, selectedVertex);
//				if (this.updateEndVertexMessage(selectedVertex, updateHandler)) {
//					selectedVertex.appendComment(END_MSG2);
//					updateHandler.add(selectedVertex, false, false);
//					updateHandler.update();
//					return;
//				}
//				updateHandler.add(selectedVertex, ItemState.SOLVED, true,
//						++this.counter, true);
//				updateHandler.update();
//
//				updateHandler.add(selectedVertex, ItemState.SOLVED, false,
//						false);
//				this.visitSuccessors(graph, vertexQueue, updateHandler,
//						selectedVertex);
//			}
//
//			if (!rVertex.isDone()) {
//				rVertex.setDone(true);
//				vertexQueue.offer(rVertex);
//			}
//		}
//
//		if (startVertex != null) {
//			startVertex.appendComment(END_MSG2);
//			updateHandler.add(startVertex, false, false);
//			updateHandler.update();
//		}
	}

//	/**
//	 * 
//	 * @param graph
//	 * @param vertexQueue
//	 * @param updateHandler
//	 * @param selectedVertex
//	 */
//	private void visitSuccessors(final IRestrictedGraph graph,
//			final Queue<IRestrictedVertex> vertexQueue,
//			final IGraphUpdateHandler updateHandler,
//			final IRestrictedVertex selectedVertex) {
//
//		for (IRestrictedVertex successor : graph.getSuccessors(selectedVertex)) {
//			IRestrictedEdge edge = graph.findEdge(selectedVertex, successor);
//
//			if (successor.isDone()) {
//				if (edge.getCurrentState() != ItemState.DISCARDED
//						&& selectedVertex.getValue() != successor) {
//					updateHandler.add(edge, ItemState.DISCARDED, true, true,
//							true);
//					updateHandler.update();
//				}
//			} else {
//				updateHandler.add(edge, ItemState.VISITED, true, true);
//				updateHandler.add(successor, ItemState.VISITED, true, true,
//						false, selectedVertex, true);
//				updateHandler.update();
//
//				updateHandler.add(successor, ItemState.VISITED, false, false);
//				vertexQueue.offer(successor);
//			}
//		}
//	}
//
//	/**
//	 * 
//	 * @param graph
//	 * @param updateHandler
//	 * @param vertex
//	 */
//	private void tagEdge(final IRestrictedGraph graph,
//			final IGraphUpdateHandler updateHandler,
//			final IRestrictedVertex vertex) {
//		if (vertex.getValue() != null) {
//			IRestrictedEdge edge = graph.findEdge(
//					(IRestrictedVertex) vertex.getValue(), vertex);
//			updateHandler.add(edge, ItemState.SOLVED, true, true);
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
//			updateHandler.add(endVertex, ItemState.SOLVED, true,
//					String.format(END_MSG1, endVertex.getName()),
//					++this.counter, true);
//			updateHandler.update();
//
//			updateHandler.add(endVertex, ItemState.SOLVED, false, false);
//			return true;
//		}
//		return false;
//	}

}
