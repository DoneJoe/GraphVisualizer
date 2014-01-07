package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem.State;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.ObservableGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class ObservableGravisGraph extends ObservableGraph<IVertex, IEdge> implements
		IObservableGravisGraph {

	private static final long serialVersionUID = -4250831846033989666L;

	private IGravisGraph gravisGraph;

	/**
	 * @param delegate
	 */
	protected ObservableGravisGraph(IGravisGraph delegate) {
		super(delegate);

		this.gravisGraph = delegate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.gravisGraph.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#clear()
	 */
	@Override
	public void clear() {
		this.gravisGraph.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#getGraphName()
	 */
	@Override
	public String getDescription() {
		return this.gravisGraph.getDescription();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#setGraphName(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphName) {
		this.gravisGraph.setDescription(graphName);
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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#setName(int)
	 */
	@Override
	public void setName(String graphName) {
		this.gravisGraph.setName(graphName);
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph#setEdgeType(edu.
	 * uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		this.gravisGraph.setEdgeType(edgeType);
	}

	@Override
	public void updateState(State state, IGraphItem... graphItems) {
		for (IGraphItem item : graphItems) {
			item.setNewState(state);
		}

		this.fireGraphEvent(new GravisGraphEvent(this, graphItems));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IGraphItemUpdate#updateState(
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem[])
	 */
	@Override
	public void updateState(IGraphItem... graphItems) {
		this.fireGraphEvent(new GravisGraphEvent(this, graphItems));
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

}
