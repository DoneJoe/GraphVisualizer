package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.event.GraphEvent.Type;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EditingGraph extends GraphDecorator<IVertex, IEdge> implements
		IEditingGraph {

	private static final long serialVersionUID = 7295632383192262799L;

	private final IGravisGraph gravisGraph;

	private final List<IEditingGraphEventListener> listeners;

	/**
	 * 
	 * @param delegate
	 */
	protected EditingGraph(IGravisGraph delegate) {
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
		edge.addEditingGraphEventListener(this.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GraphStepEvent(this, Type.EDGE_ADDED,
				edge));
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
		this.fireEditingGraphEvent(new GraphStepEvent(this, Type.EDGE_ADDED,
				edge));
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
		this.fireEditingGraphEvent(new GraphStepEvent(this, Type.EDGE_ADDED,
				e));
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
		this.fireEditingGraphEvent(new GraphStepEvent(this, Type.EDGE_ADDED,
				e));
		return ok;
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
	 * @see edu.uci.ics.jung.graph.GraphDecorator#addVertex(java.lang.Object)
	 */
	@Override
	public boolean addVertex(IVertex vertex) {
		boolean ok = super.addVertex(vertex);
		vertex.addEditingGraphEventListener(this
				.getEditingGraphEventListeners());
		this.fireEditingGraphEvent(new GraphStepEvent(this,
				Type.VERTEX_ADDED, vertex));
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#clear()
	 */
	@Override
	public void clear() {
		for (IVertex vertex : this.getVertices()) {
			vertex.removeEditingGraphEventListeners();
		}
		for (IEdge edge : this.getEdges()) {
			edge.removeEditingGraphEventListeners();
		}

		this.gravisGraph.clear();
		this.fireEditingGraphEvent(new GraphStepEvent(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsEdgeId(java
	 * .lang.String)
	 */
	@Override
	public boolean containsEdgeId(String edgeId) {
		return this.gravisGraph.containsEdgeId(edgeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsItemId(java
	 * .lang.String)
	 */
	@Override
	public boolean containsItemId(String itemId) {
		return this.gravisGraph.containsItemId(itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#containsVertexId
	 * (java.lang.String)
	 */
	@Override
	public boolean containsVertexId(String vertexId) {
		return this.gravisGraph.containsVertexId(vertexId);
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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#
	 * getEditingGraphEventListeners()
	 */
	@Override
	public IEditingGraphEventListener[] getEditingGraphEventListeners() {
		return this.listeners
				.toArray(new IEditingGraphEventListener[this.listeners.size()]);
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
		this.fireEditingGraphEvent(new GraphStepEvent(this,
				Type.EDGE_REMOVED, edge));
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
		this.fireEditingGraphEvent(new GraphStepEvent(this,
				Type.VERTEX_REMOVED, vertex));
		vertex.removeEditingGraphEventListeners();
		return ok;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#setDescription(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		boolean equal = this.getDescription().equals(graphDescription.trim());
		this.gravisGraph.setDescription(graphDescription);

		if (!equal) {
			this.fireNameChangedEvent(new GraphStepEvent(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#setEdgeType(edu.uci
	 * .ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		boolean equal = this.getEdgeType() == edgeType;
		this.gravisGraph.setEdgeType(edgeType);

		if (!equal) {
			this.fireEditingGraphEvent(new GraphStepEvent(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String graphName) {
		boolean equal = this.getName().equals(graphName.trim());
		this.gravisGraph.setName(graphName);

		if (!equal) {
			this.fireNameChangedEvent(new GraphStepEvent(this));
		}
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireEditingGraphEvent(GraphStepEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleEditingGraphEvent(event);
		}
	}

	/**
	 * 
	 * @param event
	 */
	protected void fireNameChangedEvent(GraphStepEvent event) {
		for (IEditingGraphEventListener listener : this.listeners) {
			listener.handleNameChangedEvent(event);
		}
	}

}
