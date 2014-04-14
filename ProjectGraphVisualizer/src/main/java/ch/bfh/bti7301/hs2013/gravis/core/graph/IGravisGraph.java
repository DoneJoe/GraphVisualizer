package ch.bfh.bti7301.hs2013.gravis.core.graph;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGravisGraph extends Graph<IVertex, IEdge> {

	/**
	 * Removes all vertices and edges from the graph. After the call of this
	 * method the method isEmpty() returns true.
	 */
	public abstract void clear();

	/**
	 * 
	 * @return true, if this graph contains no edges and vertices, false
	 *         otherwise.
	 */
	public abstract boolean isEmpty();

	/**
	 * 
	 * @return String
	 */
	public abstract String getDescription();

	/**
	 * 
	 * @param graphDescription
	 */
	public abstract void setDescription(String graphDescription);

	/**
	 * 
	 * @return graphName
	 */
	public abstract String getName();

	/**
	 * 
	 * @param graphName
	 */
	public abstract void setName(String graphName);

	/**
	 * Returns the edge type of this graph (directed or undirected).
	 * 
	 * @return EdgeType
	 */
	public abstract EdgeType getEdgeType();
	
	/**
	 * 
	 * @param edgeType
	 */
	public abstract void setEdgeType(EdgeType edgeType);

	/**
	 * @param vertexName
	 * @return boolean
	 */
	public abstract boolean containsVertexName(String vertexName);
	
	/**
	 * @param edgeName
	 * @return boolean
	 */
	public abstract boolean containsEdgeName(String edgeName);

	/**
	 * @param itemName
	 * @return boolean
	 */
	public abstract boolean containsItemName(String itemName);


}
