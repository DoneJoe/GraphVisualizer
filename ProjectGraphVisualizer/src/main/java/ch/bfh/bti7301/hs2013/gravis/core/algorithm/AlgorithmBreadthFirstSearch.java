package ch.bfh.bti7301.hs2013.gravis.core.algorithm;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

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
public class AlgorithmBreadthFirstSearch extends AbstractAlgorithm {

	private final static String ALGO_NAME = "Breadth-First-Search Algorithmus (BFS)";
	private final static String ALGO_DESCRIPTION = "Die Breitensuche ist als Schleife "
			+ "implementiert. Es sind sowohl gerichtete als auch ungerichtete Graphen "
			+ "zulässig. Die Breitensuche wird für jede (schwache) Zusammenhangskomponente "
			+ "separat durchgefürt.";
	private final static String END_MSG1 = "Der Endknoten %s wurde erreicht.";
	private final static String END_MSG2 = "Die Breitensuche wurde erfolgreich beendet.";

	private int counter = 0;

	public AlgorithmBreadthFirstSearch() {
		super();
		this.setName(ALGO_NAME);
		this.setDescription(ALGO_DESCRIPTION);
		this.setGraphTypes(new EdgeType[] { EdgeType.DIRECTED,
				EdgeType.UNDIRECTED });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.algorithm.AbstractAlgorithm#execute
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph)
	 */
	@Override
	public void execute(IRestrictedGraph graph) throws AlgorithmException {
		this.counter = 0;
		Queue<IRestrictedVertex> vertexQueue = new LinkedList<>();
		Collection<? extends IRestrictedVertex> vertices = graph.getVertices();
		IRestrictedVertex startVertex = graph.getStartVertex();
		IGraphUpdateHandler updateHandler = GraphFactory
				.createGraphUpdateHandler(graph);

		startVertex.setDone(true);
		vertexQueue.offer(startVertex);
		vertices.remove(startVertex);

		for (IRestrictedVertex rVertex : vertices) {
			while (!vertexQueue.isEmpty()) {
				IRestrictedVertex selectedVertex = vertexQueue.poll();

				this.tagEdge(graph, updateHandler, selectedVertex);
				if (this.updateEndVertexMessage(selectedVertex, updateHandler)) {
					selectedVertex.appendToNewComment(END_MSG2);
					updateHandler.set(selectedVertex, false, false);
					updateHandler.update();
					return;
				}
				updateHandler.set(selectedVertex, State.SOLUTION, true,
						++this.counter, true);
				updateHandler.update();

				updateHandler.set(selectedVertex, State.SOLUTION, false, false);
				this.visitSuccessors(graph, vertexQueue, updateHandler,
						selectedVertex);
			}

			if (!rVertex.isDone()) {
				rVertex.setDone(true);
				vertexQueue.offer(rVertex);
			}
		}

		if (startVertex != null) {
			startVertex.appendToNewComment(END_MSG2);
			updateHandler.set(startVertex, false, false);
			updateHandler.update();
		}
	}

	/**
	 * 
	 * @param graph
	 * @param vertexQueue
	 * @param updateHandler
	 * @param selectedVertex
	 */
	private void visitSuccessors(IRestrictedGraph graph,
			Queue<IRestrictedVertex> vertexQueue,
			IGraphUpdateHandler updateHandler, IRestrictedVertex selectedVertex) {

		for (IRestrictedVertex successor : graph.getSuccessors(selectedVertex)) {
			IRestrictedEdge edge = graph.findEdge(selectedVertex, successor);

			if (successor.isDone()) {
				if (edge.getCurrentState() != State.REFUSE
						&& selectedVertex.getValue() != successor) {
					updateHandler.set(edge, State.REFUSE, true, true, true);
					updateHandler.update();
				}
			} else {
				updateHandler.set(edge, State.VISIT, true, true);
				updateHandler.set(successor, State.VISIT, true, true, false,
						selectedVertex, true);
				updateHandler.update();

				updateHandler.set(successor, State.VISIT, false, false);
				vertexQueue.offer(successor);
			}
		}
	}

	/**
	 * 
	 * @param graph
	 * @param updateHandler
	 * @param vertex
	 */
	private void tagEdge(IRestrictedGraph graph,
			IGraphUpdateHandler updateHandler, IRestrictedVertex vertex) {
		if (vertex.getValue() != null) {
			IRestrictedEdge edge = graph.findEdge(
					(IRestrictedVertex) vertex.getValue(), vertex);
			updateHandler.set(edge, State.SOLUTION, true, true);
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
			updateHandler.set(endVertex, State.SOLUTION, true,
					String.format(END_MSG1, endVertex.getId()), ++this.counter,
					true);
			updateHandler.update();

			updateHandler.set(endVertex, State.SOLUTION, false, false);
			return true;
		}
		return false;
	}

}
