package ch.bfh.ti.gravis.core.graph;

import java.util.Collection;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
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
		this.edgeType = edgeType;

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
		return super.addEdge(edge, vertices, edge_type == this.edgeType ? edge_type : this.edgeType);
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
		return super.addEdge(e, v1, v2, edgeType == this.edgeType ? edgeType : this.edgeType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(final IVertex vertex) {
		vertex.addEditGraphEventListeners(this);
		if (this.containsItemName(vertex.getName())) {
			vertex.setName(VertexFactory.createVertexName());
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
		Collection<IVertex> vertices = this.getVertices();

		for (IVertex vertex : vertices.toArray(new IVertex[vertices.size()])) {
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

		if (type == Type.EDITED && this.containsItemName(source.getName())) {			
			for (IVertex v : this.getVertices()) {
				if (v != source && source.getName().equals(v.getName())) {
					source.setName(source instanceof IVertex ? VertexFactory.createVertexName() : 
						EdgeFactory.createEdgeName());
					return;
				}
			}
			
			for (IEdge e : this.getEdges()) {
				if (e != source && source.getName().equals(e.getName())) {
					source.setName(source instanceof IVertex ? VertexFactory.createVertexName() : 
						EdgeFactory.createEdgeName());
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
		return this.getVertexCount() == 0
				&& this.getEdgeCount() == 0;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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
		Objects.requireNonNull(edgeType, String.format(
				NULL_POINTER_MSG, "setEdgeType", "edgeType", edgeType));
		boolean equal = edgeType == this.edgeType; 
		IGravisGraph copy = null;
		
		if (!equal) {
			copy = this.copy();
		}
		
		this.edgeType = edgeType;
		
		if (!equal) {			
			for (IEdge edge : this.getEdges().toArray(new IEdge[this.getEdgeCount()])) {
				this.removeEdge(edge);
			}
			
			for (IEdge edge : copy.getEdges()) {
		        this.addEdge(edge, copy.getEndpoints(edge));
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
		this.graphName = Objects.requireNonNull(
				graphName,
				String.format(NULL_POINTER_MSG, "setName", "graphName",
						graphName)).trim();
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
	 * @return a shallow copy of this instance of IGravisGraph
	 */
	protected IGravisGraph copy() {
		IGravisGraph copy = GraphFactory.createGravisGraph(this.edgeType);

	    for (IVertex v : this.getVertices())
	    	copy.addVertex(v);

	    for (IEdge e : this.getEdges())
	    	copy.addEdge(e, this.getEndpoints(e));
	    
	    copy.setName(this.getName());
	    copy.setDescription(this.getDescription());
		return copy;
	}

	/**
	 * @param edge
	 */
	private void validateEdgeName(IEdge edge) {
		edge.addEditGraphEventListeners(this);		
		if (this.containsItemName(edge.getName())) {
			edge.setName(EdgeFactory.createEdgeName());
		}
	}
	
}
