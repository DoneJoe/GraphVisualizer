package ch.bfh.ti.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * This implementation of the {@link IEditGraphObservable} interface decorates a graph
 * of type {@link IGravisGraph}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EditGraphDecorator extends GraphDecorator<IVertex, IEdge> implements
		IEditGraphObservable {

	private static final long serialVersionUID = 7295632383192262799L;

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "EditGraphDecorator.%s(): %s == %s";
	
	private final IGravisGraph gravisGraph;

	private final List<IEditGraphEventListener> listeners;

	/**
	 * Decorates the given graph.
	 * 
	 * @param graph
	 * @throws NullPointerException
	 *             if graph is null
	 */
	protected EditGraphDecorator(IGravisGraph graph) {
		super(graph);

		Objects.requireNonNull(graph, String.format(NULL_POINTER_MSG, "EditGraphDecorator",
				"delegate", graph));
		this.gravisGraph = graph;
		this.listeners = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.util.Collection)
	 */
	@Override
	public boolean addEdge(IEdge edge, Collection<? extends IVertex> vertices) {
		if (super.addEdge(edge, vertices)) {
			edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
			this.fireGraphItemsChangedEvent(edge, Type.ADDED);
			return true;
		}
		return false;
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
		
		if (super.addEdge(edge, vertices, edge_type)) {
			edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
			this.fireGraphItemsChangedEvent(edge, Type.ADDED);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean addEdge(IEdge edge, IVertex v1, IVertex v2) {
		if (super.addEdge(edge, v1, v2)) {
			edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
			this.fireGraphItemsChangedEvent(edge, Type.ADDED);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object, edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public boolean addEdge(IEdge e, IVertex v1, IVertex v2, EdgeType edgeType) {
		if (super.addEdge(e, v1, v2, edgeType)) {
			e.addEditGraphEventListeners(this.getEditGraphEventListeners());
			this.fireGraphItemsChangedEvent(e, Type.ADDED);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphObservable#
	 * addEditGraphEventListener
	 * (ch.bfh.ti.gravis.core.graph.IEditGraphEventListener)
	 */
	@Override
	public void addEditGraphEventListener(IEditGraphEventListener listener) {
		Objects.requireNonNull(listener, String.format(NULL_POINTER_MSG, "addEditGraphEventListener",
				"listener", listener));
		this.listeners.add(listener);

		// add listener to all vertices and edges
		for (IVertex vertex : this.getVertices()) {
			vertex.addEditGraphEventListeners(listener);
		}
		
		for (IEdge edge : this.getEdges()) {
			edge.addEditGraphEventListeners(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(IVertex vertex) {
		if (super.addVertex(vertex)) {
			vertex.addEditGraphEventListeners(this.getEditGraphEventListeners());
			this.fireGraphItemsChangedEvent(vertex, Type.ADDED);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphObservable#clear()
	 */
	@Override
	public void clear() {
		for (IVertex vertex : this.getVertices()) {
			this.fireGraphItemsChangedEvent(vertex, Type.REMOVED);
			vertex.removeEditGraphEventListeners();
		}
		
		for (IEdge edge : this.getEdges()) {
			this.fireGraphItemsChangedEvent(edge, Type.REMOVED);
			edge.removeEditGraphEventListeners();
		}

		this.gravisGraph.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsEdgeName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsEdgeName(String edgeName) {
		return this.gravisGraph.containsEdgeName(edgeName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsItemName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsItemName(String itemName) {
		return this.gravisGraph.containsItemName(itemName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsVertexName
	 * (java.lang.String)
	 */
	@Override
	public boolean containsVertexName(String vertexName) {
		return this.gravisGraph.containsVertexName(vertexName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.gravisGraph.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.gravisGraph.getEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEditGraphObservable#
	 * getEditGraphEventListeners()
	 */
	@Override
	public IEditGraphEventListener[] getEditGraphEventListeners() {
		return this.listeners.toArray(new IEditGraphEventListener[this.listeners.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getName()
	 */
	@Override
	public String getName() {
		return this.gravisGraph.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.gravisGraph.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeEdge(java.lang.Object)
	 */
	@Override
	public boolean removeEdge(IEdge edge) {
		if (super.removeEdge(edge)) {
			this.fireGraphItemsChangedEvent(edge, Type.REMOVED);
			edge.removeEditGraphEventListeners(this
					.getEditGraphEventListeners());
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeVertex(java.lang.Object)
	 */
	@Override
	public boolean removeVertex(IVertex vertex) {
		if (super.removeVertex(vertex)) {
			this.fireGraphItemsChangedEvent(vertex, Type.REMOVED);
			vertex.removeEditGraphEventListeners(this
					.getEditGraphEventListeners());
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#resetItemHelperVars()
	 */
	@Override
	public void resetItemHelperVars() {
		this.gravisGraph.resetItemHelperVars();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IEditGraphObservable#setDescription(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		Objects.requireNonNull(graphDescription, String.format(NULL_POINTER_MSG, "setDescription",
				"graphDescription", graphDescription));
		
		if (!this.getDescription().equals(graphDescription.trim())) {
			this.gravisGraph.setDescription(graphDescription);
			this.fireGraphPropertiesChangedEvent(this, Type.VISUAL_EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IEditGraphObservable#setEdgeType(edu.uci
	 * .ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		Objects.requireNonNull(edgeType, String.format(NULL_POINTER_MSG, "setEdgeType",
				"edgeType", edgeType));
		
		if (this.getEdgeType() != edgeType) {
			this.gravisGraph.setEdgeType(edgeType);
			this.fireGraphPropertiesChangedEvent(this, Type.EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IEditGraphObservable#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String graphName) {
		Objects.requireNonNull(graphName, String.format(NULL_POINTER_MSG, "setName",
				"graphName", graphName));
		
		if (!graphName.trim().isEmpty() && !this.getName().equals(graphName.trim())) {
			this.gravisGraph.setName(graphName);
			this.fireGraphPropertiesChangedEvent(this, Type.VISUAL_EDITED);
		}
	}
	
	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#forceStartVertex()
	 */
	@Override
	public void forceStartVertex() {
		this.gravisGraph.forceStartVertex();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.gravisGraph.toString();
	}

	/**
	 * Fires a graph item changed event for all registered listeners.
	 * 
	 * @param source
	 * @param type
	 */
	protected void fireGraphItemsChangedEvent(IGraphItem source, Type type) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphItemsChangedEvent(source, type);
		}
	}

	/**
	 * Fires a graph property changed event for all registered listeners.
	 * 
	 * @param source
	 * @param type
	 */
	protected void fireGraphPropertiesChangedEvent(IGravisGraph source, Type type) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphPropertiesChangedEvent(source, type);
		}
	}


}
