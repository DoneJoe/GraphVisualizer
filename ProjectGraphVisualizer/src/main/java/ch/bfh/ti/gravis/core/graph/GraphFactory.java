package ch.bfh.ti.gravis.core.graph;

import java.awt.Point;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * The static factory methods in this class create graph instances of different
 * types.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public final class GraphFactory {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GraphFactory.%s(): %s == %s";

	/**
	 * Hides the default constructor.
	 */
	private GraphFactory() {
	}

	/**
	 * 
	 * @return a new graph instance of type Graph<IVertex, IEdge>
	 */
	public static Graph<IVertex, IEdge> createGraph() {
		// permits both directed and undirected edges and no parallel edges, no
		// hyper-edges
		return new SparseGraph<IVertex, IEdge>();
	}

	/**
	 * @param graph
	 * @return a new directed graph instance of type IGravisGraph
	 * @throws NullPointerException
	 *             if graph is null
	 */
	protected static IGravisGraph createDirectedGravisGraph(
			Graph<IVertex, IEdge> graph) {
		Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG,
				"createDirectedGravisGraph", "graph", graph));
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
	 * @throws NullPointerException
	 *             if edgeType is null
	 */
	public static IGravisGraph createGravisGraph(EdgeType edgeType) {
		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG,
				"createGravisGraph", "edgeType", edgeType));
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
	  * @throws NullPointerException
	 *             if edgeType or listeners is null
	 */
	public static IEditGraphObservable createEditGraphObservable(
			EdgeType edgeType, IEditGraphEventListener... listeners) {

		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG,
				"createEditGraphObservable", "edgeType", edgeType));
		Objects.requireNonNull(listeners, String.format(NULL_POINTER_MSG,
				"createEditGraphObservable", "listeners", listeners));
		EditGraphDecorator graph = new EditGraphDecorator(
				createGravisGraph(edgeType));

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
	  * @throws NullPointerException
	 *             if edgeType or listeners is null
	 */
	public static IEditGraphObservable createEditGraphObservable(
			IGravisGraph graph, IEditGraphEventListener... listeners) {

		Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG,
				"createEditGraphObservable", "graph", graph));
		Objects.requireNonNull(listeners, String.format(NULL_POINTER_MSG,
				"createEditGraphObservable", "listeners", listeners));
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
	  * @throws NullPointerException
	 *             if graph or stepBuilder is null
	 */
	public static IRestrictedGraph createRestrictedGraph(IGravisGraph graph,
			StepBuilder stepBuilder) {

		Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG,
				"createRestrictedGraph", "graph", graph));
		Objects.requireNonNull(stepBuilder, String.format(NULL_POINTER_MSG,
				"createRestrictedGraph", "stepBuilder", stepBuilder));
		return new RestrictedGraph(graph, stepBuilder);
	}

	/**
	 * @return a hardcoded undirected sample graph of type IEditGraphObservable
	 */
	public static IEditGraphObservable createUndirectedSampleGraph() {
		IEditGraphObservable newGraph = createEditGraphObservable(EdgeType.UNDIRECTED);
		VertexFactory vFactory = new VertexFactory();
		EdgeFactory eFactory = new EdgeFactory();

		// sets graph name and description:

		newGraph.setName("Ungerichteter Sample-Graph");
		newGraph.setDescription("Dieser ungerichtete Sample-Graph eignet sich zur Visualisierung von "
				+ "allen in der Applikation zur Verfügung stehenden Algorithmen. Der Graph kann "
				+ "beliebig bearbeitet werden: Neue Knoten und Kanten hinzufügen, Knoten und "
				+ "Kanten löschen, Start- und Endknoten festlegen, Knoten- und Kantennamen "
				+ "ändern, Kantengewichte ändern, Grösse der Knoten ändern, Position der Knoten "
				+ "ändern. Weitere Sample-Graphen befinden sich im App-Ordner \""
				+ GravisConstants.SAMPLES_DIR + "\".");

		// creates vertices:

		IVertex v1 = vFactory.create();
		IVertex v2 = vFactory.create();
		IVertex v3 = vFactory.create();
		IVertex v4 = vFactory.create();
		IVertex v5 = vFactory.create();
		IVertex v6 = vFactory.create();

		// sets vertex locations:

		v1.setStart(true);
		v1.setLocation(new Point(350, 60));
		v2.setLocation(new Point(60, 200));
		v3.setLocation(new Point(350, 200));
		v4.setLocation(new Point(640, 200));
		v5.setLocation(new Point(205, 340));
		v6.setLocation(new Point(495, 340));

		// add vertices to graph:

		newGraph.addVertex(v1);
		newGraph.addVertex(v2);
		newGraph.addVertex(v3);
		newGraph.addVertex(v4);
		newGraph.addVertex(v5);
		newGraph.addVertex(v6);

		// creates edges:

		IEdge e1 = eFactory.create();
		IEdge e2 = eFactory.create();
		IEdge e3 = eFactory.create();
		IEdge e4 = eFactory.create();
		IEdge e5 = eFactory.create();
		IEdge e6 = eFactory.create();
		IEdge e7 = eFactory.create();
		IEdge e8 = eFactory.create();
		IEdge e9 = eFactory.create();

		// sets edge names:

		e1.setName("eAB");
		e2.setName("eAC");
		e3.setName("eAD");
		e4.setName("eBC");
		e5.setName("eCD");
		e6.setName("eBE");
		e7.setName("eCE");
		e8.setName("eCF");
		e9.setName("eDF");

		// sets edge weights:

		e1.setWeight(8.0);
		e2.setWeight(2.0);
		e3.setWeight(4.0);
		e4.setWeight(7.0);
		e5.setWeight(1.0);
		e6.setWeight(2.0);
		e7.setWeight(3.0);
		e8.setWeight(9.0);
		e9.setWeight(5.0);

		// add edges to graph:

		newGraph.addEdge(e1, v1, v2);
		newGraph.addEdge(e2, v1, v3);
		newGraph.addEdge(e3, v1, v4);
		newGraph.addEdge(e4, v2, v3);
		newGraph.addEdge(e5, v4, v3);
		newGraph.addEdge(e6, v2, v5);
		newGraph.addEdge(e7, v3, v5);
		newGraph.addEdge(e8, v3, v6);
		newGraph.addEdge(e9, v4, v6);

		return newGraph;
	}

}
