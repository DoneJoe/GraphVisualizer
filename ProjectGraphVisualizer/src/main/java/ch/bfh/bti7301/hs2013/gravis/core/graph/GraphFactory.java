package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.step.StepTransformerFactory;
import ch.bfh.bti7301.hs2013.gravis.core.step.IStep;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.util.EdgeType;

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
	 * @return a new graph instance of type Graph<IVertex, IEdge>
	 */
	public static Graph<IVertex, IEdge> createGraph() {
		return new SparseGraph<IVertex, IEdge>();
	}

	/**
	 * @param graph
	 * @return a new directed graph instance of type IGravisGraph
	 */
	public static IGravisGraph createDirectedGravisGraph(Graph<IVertex, IEdge> graph) {
		return new GravisGraph(graph);
	}

	/**
	 * @return a new directed graph instance of type IGravisGraph
	 */
	public static IGravisGraph createDirectedGravisGraph() {
		return new GravisGraph(createGraph());
	}

	/**
	 * @param edgeType
	 * @return a new graph instance of type IGravisGraph
	 */
	public static IGravisGraph createGravisGraph(EdgeType edgeType) {
		return new GravisGraph(createGraph(), edgeType);
	}
	
	/**
	 * @return a new directed graph instance of type IEditGraphObservable
	 */
	public static IEditGraphObservable createDirectedEditGraphDecorator() {
		return new EditGraphDecorator(createDirectedGravisGraph());
	}
	
	/**
	 * 
	 * @param edgeType
	 * @param listeners 
	 * @return a new graph instance of type IEditGraphObservable
	 */
	public static IEditGraphObservable createEditGraphDecorator(EdgeType edgeType, 
			IEditGraphEventListener ... listeners) {
		EditGraphDecorator graph = new EditGraphDecorator(createGravisGraph(edgeType));
		
		for (IEditGraphEventListener listener : listeners) {
			graph.addEditGraphEventListener(listener);
		}
		return graph;
	}
	
	/**
	 * 
	 * @param graph
	 * @param listeners
	 * @return a new graph instance of type IEditGraphObservable
	 */
	public static IEditGraphObservable createEditGraphDecorator(IGravisGraph graph, 
			IEditGraphEventListener ... listeners) {
		EditGraphDecorator editingGraph = new EditGraphDecorator(graph);
		
		for (IEditGraphEventListener listener : listeners) {
			editingGraph.addEditGraphEventListener(listener);
		}
		return editingGraph;
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
	 * @return a new instance of type GraphEventListener<IVertex, IEdge> listen to 
	 * graph step updates
	 */
	public static GraphEventListener<IVertex, IEdge> createGraphStepListener(
			List<IStep> commandList) {
		return new GraphStepEventListener(commandList,
				StepTransformerFactory.createStepTransformer());
	}

	/**
	 * @param restricted graph
	 * @return a new instance of type IGraphUpdateHandler
	 */
	public static IGraphUpdateHandler createGraphUpdateHandler(
			IRestrictedGraph graph) {
		return new GraphUpdateHandler(graph);
	}

	/**
	 * Creates a graph manager.
	 * 
	 * @return a new instance of type IGraphIOManager
	 */
	public static IGraphIOManager createGraphManager() {
		return new GraphIOManager();
	}

}
