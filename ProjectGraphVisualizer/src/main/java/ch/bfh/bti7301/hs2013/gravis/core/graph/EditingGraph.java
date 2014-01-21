package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class EditingGraph extends GravisGraph implements IEditingGraph {

	private static final long serialVersionUID = 7295632383192262799L;

	/**
	 * @param delegate
	 */
	protected EditingGraph(Graph<IVertex, IEdge> delegate) {
		super(delegate, EdgeType.DIRECTED);
	}

	/**
	 * 
	 * @param delegate
	 * @param edgeType
	 */
	protected EditingGraph(Graph<IVertex, IEdge> delegate, EdgeType edgeType) {
		super(delegate, edgeType);
		
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#
	 * setEditingGraphEventListener
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener)
	 */
	@Override
	public void setEditingGraphEventListener(IEditingGraphEventListener listener) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraph#
	 * getEditingGraphEventListener()
	 */
	@Override
	public IEditingGraphEventListener getEditingGraphEventListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
