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
	 * @return a new instance of type Graph<IVertex, IEdge>
	 */
	public static Graph<IVertex, IEdge> createGraph() {
		return new SparseGraph<IVertex, IEdge>();
	}

	/**
	 * @param graph
	 * @return a new instance of type IGravisGraph
	 */
	public static IGravisGraph createGravisGraph(Graph<IVertex, IEdge> graph) {
		return new GravisGraph(graph);
	}

	/**
	 * @return a new instance of type IGravisGraph
	 */
	public static IGravisGraph createGravisGraph() {
		return new GravisGraph(createGraph());
	}

	/**
	 * @param edgeType
	 * @return IGravisGraph
	 */
	public static IGravisGraph createGravisGraph(EdgeType edgeType) {
		return new GravisGraph(createGraph(), edgeType);
	}
	
	/**
	 * @return directed IEditableGraph
	 */
	public static IEditableGraph createEditingGraph() {
		return new EditableGraph(createGravisGraph());
	}
	
	/**
	 * 
	 * @param edgeType
	 * @param listeners 
	 * @return IEditableGraph
	 */
	public static IEditableGraph createEditingGraph(EdgeType edgeType, 
			IEditableGraphEventListener ... listeners) {
		EditableGraph editingGraph = new EditableGraph(createGravisGraph(edgeType));
		
		for (IEditableGraphEventListener listener : listeners) {
			editingGraph.addEditableGraphEventListener(listener);
		}
		return editingGraph;
	}
	
	/**
	 * 
	 * @param graph
	 * @param listeners
	 * @return IEditableGraph
	 */
	public static IEditableGraph createEditingGraph(IGravisGraph graph, 
			IEditableGraphEventListener ... listeners) {
		EditableGraph editingGraph = new EditableGraph(graph);
		
		for (IEditableGraphEventListener listener : listeners) {
			editingGraph.addEditableGraphEventListener(listener);
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
	 * @return a new instance of type GraphEventListener<IVertex, IEdge>
	 */
	public static GraphEventListener<IVertex, IEdge> createGravisGraphEventListener(
			List<IStep> commandList) {
		return new GraphStepEventListener(commandList,
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
