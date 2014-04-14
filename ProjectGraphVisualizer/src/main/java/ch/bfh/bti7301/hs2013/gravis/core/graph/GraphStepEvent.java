package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.event.GraphEvent;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphStepEvent extends GraphEvent<IVertex, IEdge> {

	private final IGraphItem[] items;
	
	/**
	 * 
	 * @param graph
	 * @param items
	 */
	public GraphStepEvent(Graph<IVertex, IEdge> graph, IGraphItem[] items) {
		this(graph, null, items);
	}
	
	/**
	 * @param graph
	 * @param type
	 * @param items
	 */
	public GraphStepEvent(Graph<IVertex, IEdge> graph, GraphEvent.Type type,
			IGraphItem[] items) {
		super(graph, type);
		
		this.items = items;
	}

	/**
	 * @return IGraphItem
	 */
	public IGraphItem[] getGraphItems() {
		return this.items;
	}
	
}
