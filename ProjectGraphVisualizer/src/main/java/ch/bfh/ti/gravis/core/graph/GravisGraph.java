package ch.bfh.ti.gravis.core.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.comparator.ItemNameComparator;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * This implementation of the {@link IGravisGraph} interface decorates a graph
 * of type {@link Graph}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisGraph extends GraphDecorator<IVertex, IEdge> implements
		IGravisGraph, IEditGraphEventListener {

	private static final long serialVersionUID = 7604897874620015084L;

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GravisGraph.%s(): %s == %s";

	private static final String DEFAULT_NAME = "%s Graph %d";
	private static final String DEFAULT_DESCRIPTION = "%s Graph %d";
	private static final String DIR_STR = "gerichteter";
	private static final String UNDIR_STR = "ungerichteter";

	private static int counter = 0;

	private String graphDescription;

	private String graphName;

	private EdgeType edgeType;

	/**
	 * Decorates the given graph and creates an instance of a directed
	 * GravisGraph.
	 * 
	 * @param graph
	 * @throws NullPointerException
	 *             if graph is null
	 */
	protected GravisGraph(Graph<IVertex, IEdge> graph) {
		this(Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG,
				"GravisGraph", "delegate", graph)), EdgeType.DIRECTED);
	}

	/**
	 * Decorates the given graph and creates an instance of GravisGraph with the
	 * given edge type.
	 * 
	 * @param graph
	 * @param edgeType
	 * @throws NullPointerException
	 *             if delegate or edgeType is null
	 */
	protected GravisGraph(Graph<IVertex, IEdge> graph, EdgeType edgeType) {
		super(Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG,
				"GravisGraph", "delegate", graph)));
		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG,
				"GravisGraph", "edgeType", edgeType));
		String edgeTypeStr = edgeType == EdgeType.DIRECTED ? DIR_STR
				: UNDIR_STR;

		this.setName(String.format(DEFAULT_NAME, edgeTypeStr, ++counter));
		this.setDescription(String.format(DEFAULT_DESCRIPTION, edgeTypeStr,
				counter));
		this.validateGraphItems();
		this.setEdgeType(edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.util.Collection)
	 */
	@Override
	public boolean addEdge(IEdge edge, Collection<? extends IVertex> vertices) {
		this.validateEdgeName(edge);
		return super.addEdge(edge, vertices, this.edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.util.Collection, edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public boolean addEdge(IEdge edge, Collection<? extends IVertex> vertices,
			EdgeType edge_type) {
		this.validateEdgeName(edge);
		return super.addEdge(edge, vertices, this.edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean addEdge(IEdge e, IVertex v1, IVertex v2) {
		this.validateEdgeName(e);
		return super.addEdge(e, v1, v2, this.edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object, edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public boolean addEdge(IEdge e, IVertex v1, IVertex v2, EdgeType edgeType) {
		this.validateEdgeName(e);
		return super.addEdge(e, v1, v2, this.edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(final IVertex vertex) {
		this.validateVertexName(vertex);

		// only one start vertex is allowed
		if (vertex.isStart()) {
			this.handleGraphItemsChangedEvent(vertex, Type.START_EDITED);
		}

		// only one end vertex is allowed
		if (vertex.isEnd()) {
			this.handleGraphItemsChangedEvent(vertex, Type.END_EDITED);
		}

		return super.addVertex(vertex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#clear()
	 */
	@Override
	public void clear() {
		for (IVertex vertex : this.getVertices().toArray(
				new IVertex[this.getVertexCount()])) {
			this.removeVertex(vertex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#containsEdgeName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsEdgeName(String edgeName) {
		if (edgeName == null) {
			return false;
		}

		for (IEdge edge : this.getEdges()) {
			if (edge.getName().equals(edgeName.trim())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#containsItemName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsItemName(String itemName) {
		return this.containsVertexName(itemName)
				|| this.containsEdgeName(itemName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#containsVertexName
	 * (java.lang.String)
	 */
	@Override
	public boolean containsVertexName(String vertexName) {
		if (vertexName == null) {
			return false;
		}

		for (IVertex vertex : this.getVertices()) {
			if (vertex.getName().equals(vertexName.trim())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getGraphName()
	 */
	@Override
	public String getDescription() {
		return this.graphDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.edgeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getName()
	 */
	@Override
	public String getName() {
		return this.graphName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphItemsChangedEvent(ch.bfh.ti.gravis.core.graph.item.IGraphItem,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphItemsChangedEvent(final IGraphItem source,
			final Type type) {

		if (type == Type.START_EDITED && source instanceof IVertex
				&& ((IVertex) source).isStart()) {

			// only one start vertex is allowed
			for (IVertex vertex : this.getVertices()) {
				if (vertex != source) {
					vertex.setStart(false);
				}
			}
		} else if (type == Type.END_EDITED && source instanceof IVertex
				&& ((IVertex) source).isEnd()) {

			// only one end vertex is allowed
			for (IVertex vertex : this.getVertices()) {
				if (vertex != source) {
					vertex.setEnd(false);
				}
			}
		} else if (type == Type.EDITED
				&& this.containsItemName(source.getName())) {
			// vertex name must be unique
			for (IVertex v : this.getVertices()) {
				if (v != source && source.getName().equals(v.getName())) {
					source.setName(source instanceof IVertex ? VertexFactory
							.createVertexName() : EdgeFactory.createEdgeName());
					return;
				}
			}

			// edge name must be unique
			for (IEdge e : this.getEdges()) {
				if (e != source && source.getName().equals(e.getName())) {
					source.setName(source instanceof IVertex ? VertexFactory
							.createVertexName() : EdgeFactory.createEdgeName());
					return;
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphPropertiesChangedEvent
	 * (ch.bfh.ti.gravis.core.graph.IGravisGraph,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphPropertiesChangedEvent(IGravisGraph source, Type type) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.getVertexCount() == 0 && this.getEdgeCount() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeEdge(java.lang.Object)
	 */
	@Override
	public boolean removeEdge(IEdge edge) {
		edge.removeEditGraphEventListeners(this);
		return super.removeEdge(edge);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeVertex(java.lang.Object)
	 */
	@Override
	public boolean removeVertex(final IVertex vertex) {
		vertex.removeEditGraphEventListeners(this);
		return super.removeVertex(vertex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#resetItemHelperVars()
	 */
	@Override
	public void resetItemHelperVars() {
		for (IVertex vertex : this.getVertices()) {
			vertex.resetHelperVariables();
		}

		for (IEdge edge : this.getEdges()) {
			edge.resetHelperVariables();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#setGraphName(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		this.graphDescription = Objects.requireNonNull(
				graphDescription,
				String.format(NULL_POINTER_MSG, "setDescription",
						"graphDescription", graphDescription)).trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#setEdgeType(edu.
	 * uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG,
				"setEdgeType", "edgeType", edgeType));

		if (edgeType != this.edgeType) {
			this.edgeType = edgeType;

			for (IEdge edge : this.getEdges().toArray(
					new IEdge[this.getEdgeCount()])) {

				Pair<IVertex> pair = this.getEndpoints(edge);
				this.removeEdge(edge);
				// add edges with new edgeType
				this.addEdge(edge, pair, edgeType);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#setName(int)
	 */
	@Override
	public void setName(String graphName) {
		Objects.requireNonNull(
				graphName,
				String.format(NULL_POINTER_MSG, "setName", "graphName",
						graphName)).trim();
		this.graphName = graphName.trim().isEmpty() ? this.graphName : graphName.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#forceStartVertex()
	 */
	@Override
	public void forceStartVertex() {
		boolean start = false;
		for (IVertex vertex : this.getVertices()) {
			if (vertex.isStart()) {
				start = true;
				break;
			}
		}

		if (!start && !this.isEmpty()) {
			IVertex[] vertices = this.getVertices().toArray(
					new IVertex[this.getVertexCount()]);
			Arrays.sort(vertices, new ItemNameComparator());
			// the first vertex in the list is the start vertex
			vertices[0].setStart(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/**
	 * Creates a shallow copy of this {@code GravisGraph} instance.
	 * 
	 * @return a shallow copy of this {@code GravisGraph} instance
	 */
	protected IGravisGraph copy() {
		IGravisGraph copy = GraphFactory.createGravisGraph(this.getEdgeType());

		for (IVertex v : this.getVertices())
			copy.addVertex(v);

		for (IEdge e : this.getEdges())
			copy.addEdge(e, this.getEndpoints(e), this.getEdgeType());

		copy.setName(this.getName());
		copy.setDescription(this.getDescription());
		return copy;
	}

	/**
	 * Validates all vertices and edges in this graph and corrects the invalid
	 * item properties. A valid graph has at most one start vertex and one end
	 * vertex. All item names must be unique.
	 */
	private void validateGraphItems() {
		for (IVertex vertex : this.getVertices()) {
			this.validateVertexName(vertex);

			// only one start vertex is allowed
			if (vertex.isStart()) {
				this.handleGraphItemsChangedEvent(vertex, Type.START_EDITED);
			}

			// only one end vertex is allowed
			if (vertex.isEnd()) {
				this.handleGraphItemsChangedEvent(vertex, Type.END_EDITED);
			}
		}

		for (IEdge edge : this.getEdges()) {
			this.validateEdgeName(edge);
		}
	}

	/**
	 * Checks if the new edge name is unique and corrects the name if not unique.
	 * 
	 * @param edge
	 */
	private void validateEdgeName(final IEdge edge) {
		edge.addEditGraphEventListeners(this);
		if (this.containsItemName(edge.getName())) {
			edge.setName(EdgeFactory.createEdgeName());
		}
	}

	/**
	 * Checks if the new vertex name is unique and corrects the name if not unique.
	 * 
	 * @param vertex
	 */
	private void validateVertexName(final IVertex vertex) {
		vertex.addEditGraphEventListeners(this);
		if (this.containsItemName(vertex.getName())) {
			vertex.setName(VertexFactory.createVertexName());
		}
	}
}
