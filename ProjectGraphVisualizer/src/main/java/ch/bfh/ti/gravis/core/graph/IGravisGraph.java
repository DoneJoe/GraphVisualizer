package ch.bfh.ti.gravis.core.graph;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * This interface extends the JUNG {@link Graph} interface and adds some additional
 * methods. The edgeType is either directed or undirected for all edges (depends
 * on the edge type of this graph). Parallel edges and hyper-edges are not
 * allowed. All vertices and edges in this graph must have a unique name.
 * Only one start and one end vertex is allowed.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IGravisGraph extends Graph<IVertex, IEdge> {

	/**
	 * Removes all vertices and edges from this graph. After the call of this
	 * method the graph is empty.
	 */
	public abstract void clear();

	/**
	 * Returns true if this graph contains an edge with name equal to the given
	 * edgeName.
	 * 
	 * @param edgeName
	 * @return true if this graph contains an edge with name equal to the given
	 *         edgeName
	 */
	public abstract boolean containsEdgeName(String edgeName);

	/**
	 * Returns true if this graph contains a vertex or an edge with name equal
	 * to the given itemName.
	 * 
	 * @param itemName
	 * @return true if this graph contains a vertex or an edge with name equal
	 * to the given itemName
	 */
	public abstract boolean containsItemName(String itemName);

	/**
	 * Returns true if this graph contains a vertex with name equal to the given
	 * vertexName.
	 * 
	 * @param vertexName
	 * @return true if this graph contains a vertex with name equal to the given
	 *         vertexName
	 */
	public abstract boolean containsVertexName(String vertexName);

	/**
	 * 
	 * @return the graph description
	 */
	public abstract String getDescription();

	/**
	 * Returns the edge type of this graph (directed or undirected).
	 * 
	 * @return  the edge type of this graph
	 */
	public abstract EdgeType getEdgeType();

	/**
	 * 
	 * @return the name of this graph
	 */
	public abstract String getName();

	/**
	 * Returns <tt>true</tt> if this graph contains no vertices and edges.
	 * 
	 * @return <tt>true</tt> if this graph contains no vertices and edges
	 */
	public abstract boolean isEmpty();

	/**
	 * Sets a description with additional information about the graph.
	 * 
	 * @param graphDescription
	 * @throws NullPointerException if graphDescription is null          
	 */
	public abstract void setDescription(String graphDescription);

	/**
	 * Changes the edgeType for all edges in this graph.
	 * 
	 * @param edgeType
	 * @throws NullPointerException if edgeType is null          
	 */
	public abstract void setEdgeType(EdgeType edgeType);

	/**
	 * Sets the name of this graph.
	 * 
	 * @param graphName
	 * @throws NullPointerException if graphName is null          
	 */
	public abstract void setName(String graphName);

	/**
	 * Resets the item helper variables for all vertices and edges in this
	 * graph. This method calls
	 * {@link IRestrictedGraphItem#resetHelperVariables} for all vertices and
	 * edges.
	 */
	public abstract void resetItemHelperVars();

	/**
	 * Forces the existence of a start vertex. If no start vertex is set, this
	 * method chooses and sets an arbitrary start vertex. If this graph is
	 * empty, no changes are performed.
	 */
	public abstract void forceStartVertex();
}
