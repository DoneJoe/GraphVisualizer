package ch.bfh.bti7301.hs2013.gravis.core.graph;


import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.step.StepBuilder;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
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
	public static IEditGraphObservable createDirectedEditGraphObservable() {
		return new EditGraphDecorator(createDirectedGravisGraph());
	}
	
	/**
	 * 
	 * @param edgeType
	 * @param listeners 
	 * @return a new graph instance of type IEditGraphObservable
	 */
	public static IEditGraphObservable createEditGraphObservable(EdgeType edgeType, 
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
	public static IEditGraphObservable createEditGraphObservable(IGravisGraph graph, 
			IEditGraphEventListener ... listeners) {
		EditGraphDecorator editGraph = new EditGraphDecorator(graph);
		
		for (IEditGraphEventListener listener : listeners) {
			editGraph.addEditGraphEventListener(listener);
		}
		return editGraph;
	}
	
	/**
	 * @param graph
	 * @param stepBuilder 
	 * @return a new instance of type IRestrictedGraph
	 */
	public static IRestrictedGraph createRestrictedGraph(
			IGravisGraph graph, StepBuilder stepBuilder) {
		return new RestrictedGraph(graph, stepBuilder);
	}

}
