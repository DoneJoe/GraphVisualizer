package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
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
	 * @param edgeName
	 * @return boolean
	 */
	public abstract boolean containsEdgeName(String edgeName);

	/**
	 * @param itemName
	 * @return boolean
	 */
	public abstract boolean containsItemName(String itemName);

	/**
	 * @param vertexName
	 * @return boolean
	 */
	public abstract boolean containsVertexName(String vertexName);

	/**
	 * 
	 * @return String
	 */
	public abstract String getDescription();

	/**
	 * Returns the edge type of this graph (directed or undirected).
	 * 
	 * @return EdgeType
	 */
	public abstract EdgeType getEdgeType();

	/**
	 * 
	 * @return graphName
	 */
	public abstract String getName();
	
	/**
	 * 
	 * @return true, if this graph contains no edges and vertices, false
	 *         otherwise.
	 */
	public abstract boolean isEmpty();

	/**
	 * string: trim() applied
	 * 
	 * @param graphDescription
	 */
	public abstract void setDescription(String graphDescription);
	
	/**
	 * 
	 * @param edgeType
	 */
	public abstract void setEdgeType(EdgeType edgeType);

	/**
	 * string: trim() applied
	 * 
	 * @param graphName
	 */
	public abstract void setName(String graphName);


}
