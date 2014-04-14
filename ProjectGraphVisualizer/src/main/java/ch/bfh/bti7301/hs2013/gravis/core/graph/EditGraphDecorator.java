package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EditGraphDecorator extends GraphDecorator<IVertex, IEdge> implements
		IEditGraphObservable {

	private static final long serialVersionUID = 7295632383192262799L;

	private final IGravisGraph gravisGraph;

	private final List<IEditGraphEventListener> listeners;

	/**
	 * 
	 * @param delegate
	 */
	protected EditGraphDecorator(IGravisGraph delegate) {
		super(delegate);

		this.gravisGraph = delegate;
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
		boolean ok = super.addEdge(edge, vertices);
		
		edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
		this.fireGraphItemsChangedEvent(edge, Type.ADDED);
		
		return ok;
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
		boolean ok = super.addEdge(edge, vertices, edge_type);
		
		edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
		this.fireGraphItemsChangedEvent(edge, Type.ADDED);
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean addEdge(IEdge edge, IVertex v1, IVertex v2) {
		boolean ok = super.addEdge(edge, v1, v2);
		
		edge.addEditGraphEventListeners(this.getEditGraphEventListeners());
		this.fireGraphItemsChangedEvent(edge, Type.ADDED);
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object, edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public boolean addEdge(IEdge e, IVertex v1, IVertex v2, EdgeType edgeType) {
		boolean ok = super.addEdge(e, v1, v2, edgeType);
		
		e.addEditGraphEventListeners(this.getEditGraphEventListeners());
		this.fireGraphItemsChangedEvent(e, Type.ADDED);
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#
	 * addEditGraphEventListener
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphEventListener)
	 */
	@Override
	public void addEditGraphEventListener(IEditGraphEventListener listener) {
		this.listeners.add(listener);

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
		boolean ok = super.addVertex(vertex);
		
		vertex.addEditGraphEventListeners(this
				.getEditGraphEventListeners());
		this.fireGraphItemsChangedEvent(vertex, Type.ADDED);
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#clear()
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
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsEdgeName(java
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
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsItemName(java
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
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsVertexName
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
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.gravisGraph.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.gravisGraph.getEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#
	 * getEditGraphEventListeners()
	 */
	@Override
	public IEditGraphEventListener[] getEditGraphEventListeners() {
		return this.listeners.toArray(new IEditGraphEventListener[this.listeners.size()]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#getName()
	 */
	@Override
	public String getName() {
		return this.gravisGraph.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#isEmpty()
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
		boolean ok = super.removeEdge(edge);
		
		this.fireGraphItemsChangedEvent(edge, Type.REMOVED);
		edge.removeEditGraphEventListeners();
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeVertex(java.lang.Object)
	 */
	@Override
	public boolean removeVertex(IVertex vertex) {
		boolean ok = super.removeVertex(vertex);
		
		this.fireGraphItemsChangedEvent(vertex, Type.REMOVED);
		vertex.removeEditGraphEventListeners();
		
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#setDescription(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		boolean equal = this.getDescription().equals(graphDescription.trim());
		
		this.gravisGraph.setDescription(graphDescription);
		if (!equal) {
			this.fireGraphPropertiesChangedEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#setEdgeType(edu.uci
	 * .ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		boolean equal = this.getEdgeType() == edgeType;
		
		this.gravisGraph.setEdgeType(edgeType);
		if (!equal) {
			this.fireGraphPropertiesChangedEvent(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditGraphObservable#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String graphName) {
		boolean equal = this.getName().equals(graphName.trim());
		
		this.gravisGraph.setName(graphName);
		if (!equal) {
			this.fireGraphPropertiesChangedEvent(this);
		}
	}

	/**
	 * 
	 * @param source
	 */
	protected void fireGraphPropertiesChangedEvent(IGravisGraph source) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphPropertiesChangedEvent(source);
		}
	}
	
	/**
	 * 
	 * @param source
	 * @param type
	 */
	protected void fireGraphItemsChangedEvent(IGraphItem source, Type type) {
		for (IEditGraphEventListener listener : this.listeners) {
			listener.handleGraphItemsChangedEvent(source, type);
		}
	}


}
