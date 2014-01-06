package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.step.StepTransformerFactory;
import ch.bfh.bti7301.hs2013.gravis.core.step.IStep;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.event.GraphEventListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class GraphFactory {

	/**
	 * A main (no-)constructor.
	 */
	private GraphFactory() {
	}

	/**
	 * 
	 * @return a new instance of type Graph<IVertex, IEdge>
	 */
	public static Graph<IVertex, IEdge> createGraph() {
		return new SparseGraph<IVertex, IEdge>();
	}

	/**
	 * @param graph
	 * @return a new instance of type IGravisGraph
	 */
	public static IGravisGraph createIGravisGraph(Graph<IVertex, IEdge> graph) {
		return new GravisGraph(graph);
	}

	/**
	 * @return a new instance of type IGravisGraph
	 */
	public static IGravisGraph createIGravisGraph() {
		return new GravisGraph(createGraph());
	}

	/**
	 * 
	 * @param graph
	 * @return a new instance of type IObservableGravisGraph
	 */
	public static IObservableGravisGraph createObservableGraph(
			IGravisGraph graph) {
		return new ObservableGravisGraph(graph);
	}

	/**
	 * @param graph
	 * @return a new instance of type IRestrictedGraph
	 */
	public static IRestrictedGraph createRestrictedGraph(
			IObservableGravisGraph graph) {
		return new RestrictedGraph(graph);
	}

	/**
	 * 
	 * @param commandList
	 * @return a new instance of type GraphEventListener<IVertex, IEdge>
	 */
	public static GraphEventListener<IVertex, IEdge> createGravisGraphEventListener(
			List<IStep> commandList) {
		return new GravisGraphEventListener(commandList,
				StepTransformerFactory.createStepTransformer());
	}

	/**
	 * @param graph
	 * @return IGraphUpdateHandler
	 */
	public static IGraphUpdateHandler createGraphUpdateHandler(
			IRestrictedGraph graph) {
		return new GraphUpdateHandler(graph);
	}

	/**
	 * Creates a graph manager.
	 * 
	 * @return IGraphManager
	 */
	public static IGraphManager createGraphManager() {
		return new GraphManager();
	}

}
