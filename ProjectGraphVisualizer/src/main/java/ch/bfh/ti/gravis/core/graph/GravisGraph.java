package ch.bfh.ti.gravis.core.graph;

import java.util.Collection;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
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
	 * Creates an instance of a directed GravisGraph.
	 * 
	 * @param delegate
	 */
	protected GravisGraph(Graph<IVertex, IEdge> delegate) {
		this(delegate, EdgeType.DIRECTED);
	}

	/**
	 * Creates an instance of GravisGraph.
	 * 
	 * @param delegate
	 * @param edgeType
	 */
	protected GravisGraph(Graph<IVertex, IEdge> delegate, EdgeType edgeType) {
		super(delegate);

		Objects.requireNonNull(delegate, String.format(NULL_POINTER_MSG,
				"GravisGraph", "delegate", delegate));
		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG,
				"GravisGraph", "edgeType", edgeType));
		String edgeTypeStr = edgeType == EdgeType.DIRECTED ? DIR_STR
				: UNDIR_STR;

		counter++;
		this.setName(String.format(DEFAULT_NAME, edgeTypeStr, counter));
		this.setDescription(String.format(DEFAULT_DESCRIPTION, edgeTypeStr,
				counter));
		this.setEdgeType(edgeType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#clear()
	 */
	@Override
	public void clear() {
		Collection<IVertex> vertices = this.delegate.getVertices();

		for (IVertex vertex : vertices.toArray(new IVertex[vertices.size()])) {
			this.delegate.removeVertex(vertex);
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
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.delegate.getVertexCount() == 0
				&& this.delegate.getEdgeCount() == 0;
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
		this.edgeType = Objects.requireNonNull(edgeType, String.format(
				NULL_POINTER_MSG, "setEdgeType", "edgeType", edgeType));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#setName(int)
	 */
	@Override
	public void setName(String graphName) {
		this.graphName = Objects.requireNonNull(graphName, String.format(
				NULL_POINTER_MSG, "setName", "graphName", graphName)).trim();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphEventListener#
	 * handleGraphItemsChangedEvent(ch.bfh.ti.gravis.core.graph.item.IGraphItem,
	 * ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type)
	 */
	@Override
	public void handleGraphItemsChangedEvent(final IGraphItem source, final Type type) {
		if (type == Type.START_EDITED && source instanceof IVertex
				&& ((IVertex) source).isStart()) {

			for (IVertex vertex : this.getVertices()) {
				if (vertex != source) {
					vertex.setStart(false);
				}
			}
		} else if (type == Type.END_EDITED && source instanceof IVertex
				&& ((IVertex) source).isEnd()) {

			for (IVertex vertex : this.getVertices()) {
				if (vertex != source) {
					vertex.setEnd(false);
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
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(IVertex vertex) {
		vertex.addEditGraphEventListeners(this);
		return super.addVertex(vertex);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeVertex(java.lang.Object)
	 */
	@Override
	public boolean removeVertex(IVertex vertex) {
		vertex.removeEditGraphEventListeners();
		return super.removeVertex(vertex);
	}

}
