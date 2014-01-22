package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent.Type;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EditingGraph extends GravisGraph implements IEditingGraph {

	private static final long serialVersionUID = 7295632383192262799L;

	private final List<IEditingGraphEventListener> listeners;

	/**
	 * @param delegate
	 */
	protected EditingGraph(Graph<IVertex, IEdge> delegate) {
		this(delegate, EdgeType.DIRECTED);
	}

	/**
	 * 
	 * @param delegate
	 * @param edgeType
	 */
	protected EditingGraph(Graph<IVertex, IEdge> delegate, EdgeType edgeType) {
		super(delegate, edgeType);
		this.listeners = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#
	 * addEditingGraphEventListener
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener)
	 */
	@Override
	public void addEditingGraphEventListener(IEditingGraphEventListener listener) {
		this.listeners.add(listener);
		
		for (IVertex vertex : this.getVertices()) {
			vertex.addEditingGraphEventListener(listener);
		}
		for (IEdge edge : this.getEdges()) {
			edge.addEditingGraphEventListener(listener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#
	 * getEditingGraphEventListeners()
	 */
	@Override
	public IEditingGraphEventListener[] getEditingGraphEventListeners() {
		return this.listeners
				.toArray(new IEditingGraphEventListener[this.listeners.size()]);
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireEditingGraphEvent(GravisGraphEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleEditingGraphEvent(event);
		}
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireNameChangedEvent(GravisGraphEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleNameChangedEvent(event);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraph#clear()
	 */
	@Override
	public void clear() {
		for (IVertex vertex : this.getVertices()) {
			vertex.removeEditingGraphEventListeners();
		}
		for (IEdge edge : this.getEdges()) {
			edge.removeEditingGraphEventListeners();
		}
		
		super.clear();
		this.fireEditingGraphEvent(new GravisGraphEvent(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraph#setDescription(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		boolean equal = this.getDescription().equals(graphDescription.trim());
		super.setDescription(graphDescription);
		
		if (!equal) {
			this.fireNameChangedEvent(new GravisGraphEvent(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraph#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String graphName) {
		boolean equal = this.getName().equals(graphName.trim());
		super.setName(graphName);
		
		if (!equal) {
			this.fireNameChangedEvent(new GravisGraphEvent(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraph#setEdgeType(edu.uci
	 * .ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		boolean equal = this.getEdgeType() == edgeType;
		super.setEdgeType(edgeType);
		
		if (!equal) {
			this.fireEditingGraphEvent(new GravisGraphEvent(this));
		}
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
		edge.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.EDGE_ADDED, edge));
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
		edge.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.EDGE_ADDED, edge));
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
		e.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.EDGE_ADDED, e));
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addEdge(java.lang.Object,
	 * java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean addEdge(IEdge e, IVertex v1, IVertex v2) {
		boolean ok = super.addEdge(e, v1, v2);
		e.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.EDGE_ADDED, e));
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(IVertex vertex) {
		boolean ok = super.addVertex(vertex);
		vertex.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.VERTEX_ADDED, vertex));
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uci.ics.jung.graph.GraphDecorator#removeEdge(java.lang.Object)
	 */
	@Override
	public boolean removeEdge(IEdge edge) {
		boolean ok = super.removeEdge(edge);
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.EDGE_REMOVED, edge));
		edge.removeEditingGraphEventListeners();
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
		this.fireEditingGraphEvent(new GravisGraphEvent(this, Type.VERTEX_REMOVED, vertex));
		vertex.removeEditingGraphEventListeners();
		return ok;
	}

}
