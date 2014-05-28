package ch.bfh.ti.gravis.core.graph;

import java.util.Collection;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * A restricted graph gives restricted access to the vertices and edges. No add
 * or remove operations are possible. This graph operates with restricted graph
 * items. A restricted graph item restricts the operations on the item to a
 * subset of all possible operations (see specification of
 * IRestrictedGraphItem). <br />
 * All edges have the same edgeType (directed or undirected). Parallel edges and
 * hyper-edges are not possible.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IRestrictedGraph {

	/**
	 * Adds a new step for a given array of restricted graph items.
	 * 
	 * @param restrictedItems
	 *            array of restricted graph items
	 */
	public void addStep(IRestrictedGraphItem... restrictedItems);

	/**
	 * Returns true if this graph's edge collection contains <code>edge</code>.
	 * Equivalent to <code>getEdges().contains(edge)</code>.
	 * 
	 * @param edge
	 *            the edge whose presence is being queried
	 * @return true iff this graph contains an edge <code>edge</code>
	 */
	public boolean containsEdge(IRestrictedEdge edge);

	/**
	 * Returns true if this graph's vertex collection contains
	 * <code>vertex</code>. Equivalent to
	 * <code>getVertices().contains(vertex)</code>.
	 * 
	 * @param vertex
	 *            the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex <code>vertex</code>
	 */
	public boolean containsVertex(IRestrictedVertex vertex);

	/**
	 * Returns the number of edges incident to <code>vertex</code>. Special
	 * cases of interest:
	 * <ul>
	 * <li/>Incident self-loops are counted once.
	 * <li>If there is only one edge that connects this vertex to each of its
	 * neighbors (and vice versa), then the value returned will also be equal to
	 * the number of neighbors that this vertex has (that is, the output of
	 * <code>getNeighborCount</code>).
	 * <li>If the graph is directed, then the value returned will be the sum of
	 * this vertex's indegree (the number of edges whose destination is this
	 * vertex) and its outdegree (the number of edges whose source is this
	 * vertex), minus the number of incident self-loops (to avoid
	 * double-counting).
	 * </ul>
	 * <p>
	 * Equivalent to <code>getIncidentEdges(vertex).size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(IRestrictedVertex vertex);

	/**
	 * Returns an edge that connects this vertex to <code>v</code>. If this edge
	 * is not uniquely defined (that is, if the graph contains more than one
	 * edge connecting <code>v1</code> to <code>v2</code>), any of these edges
	 * may be returned. <code>findEdgeSet(v1, v2)</code> may be used to return
	 * all such edges. Returns null if either of the following is true:
	 * <ul>
	 * <li/><code>v2</code> is not connected to <code>v1</code>
	 * <li/>either <code>v1</code> or <code>v2</code> are not present in this
	 * graph
	 * </ul>
	 * <p>
	 * <b>Note</b>: for purposes of this method, <code>v1</code> is only
	 * considered to be connected to <code>v2</code> via a given <i>directed</i>
	 * edge <code>e</code> if
	 * <code>v1 == e.getSource() && v2 == e.getDest()</code> evaluates to
	 * <code>true</code>. (<code>v1</code> and <code>v2</code> are connected by
	 * an undirected edge <code>u</code> if <code>u</code> is incident to both
	 * <code>v1</code> and <code>v2</code>.)
	 * 
	 * @return an edge that connects <code>v1</code> to <code>v2</code>, or
	 *         <code>null</code> if no such edge exists (or either vertex is not
	 *         present)
	 * @see Hypergraph#findEdgeSet(Object, Object)
	 */
	public IRestrictedEdge findEdge(IRestrictedVertex v1, IRestrictedVertex v2);

	/**
	 * Returns all edges that connects this vertex to <code>v</code>. If this
	 * edge is not uniquely defined (that is, if the graph contains more than
	 * one edge connecting <code>v1</code> to <code>v2</code>), any of these
	 * edges may be returned. <code>findEdgeSet(v1, v2)</code> may be used to
	 * return all such edges. Returns null if <code>v2</code> is not connected
	 * to <code>v1</code>. <br/>
	 * Returns an empty collection if either <code>v1</code> or <code>v2</code>
	 * are not present in this graph.
	 * 
	 * <p>
	 * <b>Note</b>: for purposes of this method, <code>v1</code> is only
	 * considered to be connected to <code>v2</code> via a given <i>directed</i>
	 * edge <code>d</code> if
	 * <code>v1 == d.getSource() && v2 == d.getDest()</code> evaluates to
	 * <code>true</code>. (<code>v1</code> and <code>v2</code> are connected by
	 * an undirected edge <code>u</code> if <code>u</code> is incident to both
	 * <code>v1</code> and <code>v2</code>.)
	 * 
	 * @return a collection containing all edges that connect <code>v1</code> to
	 *         <code>v2</code>, or <code>null</code> if either vertex is not
	 *         present
	 * @see Hypergraph#findEdge(Object, Object)
	 */
	public Collection<? extends IRestrictedEdge> findEdgeSet(
			IRestrictedVertex v1, IRestrictedVertex v2);

	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType();

	/**
	 * If <code>directed_edge</code> is a directed edge in this graph, returns
	 * the destination; otherwise returns <code>null</code>. The destination of
	 * a directed edge <code>d</code> is defined to be the vertex incident to
	 * <code>d</code> for which <code>d</code> is an incoming edge.
	 * <code>directed_edge</code> is guaranteed to be a directed edge if its
	 * <code>EdgeType</code> is <code>DIRECTED</code>.
	 * 
	 * @param directed_edge
	 * @return the destination of <code>directed_edge</code> if it is a directed
	 *         edge in this graph, or <code>null</code> otherwise
	 */
	public IRestrictedVertex getDest(IRestrictedEdge directed_edge);

	/**
	 * Returns the number of edges in this graph.
	 * 
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount();

	/**
	 * Returns the number of edges of type <code>edge_type</code> in this graph.
	 * 
	 * @param edge_type
	 *            the type of edge for which the count is to be returned
	 * @return the number of edges of type <code>edge_type</code> in this graph
	 */
	public int getEdgeCount(EdgeType edge_type);

	/**
	 * Returns a view of all edges in this graph. In general, this obeys the
	 * <code>Collection</code> contract, and therefore makes no guarantees about
	 * the ordering of the vertices within the set.
	 * 
	 * @return a <code>Collection</code> view of all edges in this graph
	 */
	public Collection<? extends IRestrictedEdge> getEdges();

	/**
	 * Returns the collection of edges in this graph which are of type
	 * <code>edge_type</code>.
	 * 
	 * @param edge_type
	 *            the type of edges to be returned
	 * @return the collection of edges which are of type <code>edge_type</code>,
	 *         or <code>null</code> if the graph does not accept edges of this
	 *         type
	 * @see EdgeType
	 */
	public Collection<? extends IRestrictedEdge> getEdges(EdgeType edge_type);

	/**
	 * Returns the edge type of this graph (directed or undirected).
	 * 
	 * @return edge type
	 */
	public abstract EdgeType getEdgeType();

	/**
	 * Returns the edge type of <code>edge</code> in this graph.
	 * 
	 * @param edge
	 * @return the <code>EdgeType</code> of <code>edge</code>, or
	 *         <code>null</code> if <code>edge</code> has no defined type
	 */
	public EdgeType getEdgeType(IRestrictedEdge edge);

	/**
	 * Returns the endpoints of <code>edge</code> as a <code>Pair<V></code>.
	 * 
	 * @param edge
	 *            the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of <code>edge</code>
	 */
	public Pair<? extends IRestrictedVertex> getEndpoints(IRestrictedEdge edge);

	/**
	 * Returns the number of vertices that are incident to <code>edge</code>.
	 * For hyperedges, this can be any nonnegative integer; for edges this must
	 * be 2 (or 1 if self-loops are permitted).
	 * 
	 * <p>
	 * Equivalent to <code>getIncidentVertices(edge).size()</code>.
	 * 
	 * @param edge
	 *            the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to <code>edge</code>.
	 */
	public int getIncidentCount(IRestrictedEdge edge);

	/**
	 * Returns the collection of edges in this graph which are connected to
	 * <code>vertex</code>.
	 * 
	 * @param vertex
	 *            the vertex whose incident edges are to be returned
	 * @return the collection of edges which are connected to
	 *         <code>vertex</code>, or <code>null</code> if <code>vertex</code>
	 *         is not present
	 */
	public Collection<? extends IRestrictedEdge> getIncidentEdges(
			IRestrictedVertex vertex);

	/**
	 * Returns the collection of vertices in this graph which are connected to
	 * <code>edge</code>. Note that for some graph types there are guarantees
	 * about the size of this collection (i.e., some graphs contain edges that
	 * have exactly two endpoints, which may or may not be distinct).
	 * Implementations for those graph types may provide alternate methods that
	 * provide more convenient access to the vertices.
	 * 
	 * @param edge
	 *            the edge whose incident vertices are to be returned
	 * @return the collection of vertices which are connected to
	 *         <code>edge</code>, or <code>null</code> if <code>edge</code> is
	 *         not present
	 */
	public Collection<? extends IRestrictedVertex> getIncidentVertices(
			IRestrictedEdge edge);

	/**
	 * Returns a <code>Collection</code> view of the incoming edges incident to
	 * <code>vertex</code> in this graph.
	 * 
	 * @param vertex
	 *            the vertex whose incoming edges are to be returned
	 * @return a <code>Collection</code> view of the incoming edges incident to
	 *         <code>vertex</code> in this graph
	 */
	public Collection<? extends IRestrictedEdge> getInEdges(
			IRestrictedVertex vertex);

	/**
	 * Returns the number of vertices that are adjacent to <code>vertex</code>
	 * (that is, the number of vertices that are incident to edges in
	 * <code>vertex</code>'s incident edge set).
	 * 
	 * <p>
	 * Equivalent to <code>getNeighbors(vertex).size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(IRestrictedVertex vertex);

	/**
	 * Returns the collection of vertices which are connected to
	 * <code>vertex</code> via any edges in this graph. If <code>vertex</code>
	 * is connected to itself with a self-loop, then it will be included in the
	 * collection returned.
	 * 
	 * @param vertex
	 *            the vertex whose neighbors are to be returned
	 * @return the collection of vertices which are connected to
	 *         <code>vertex</code>, or <code>null</code> if <code>vertex</code>
	 *         is not present
	 */
	public Collection<? extends IRestrictedVertex> getNeighbors(
			IRestrictedVertex vertex);

	/**
	 * Returns the vertex at the other end of <code>edge</code> from
	 * <code>vertex</code>. (That is, returns the vertex incident to
	 * <code>edge</code> which is not <code>vertex</code>.)
	 * 
	 * @param vertex
	 *            the vertex to be queried
	 * @param edge
	 *            the edge to be queried
	 * @return the vertex at the other end of <code>edge</code> from
	 *         <code>vertex</code>
	 */
	public IRestrictedVertex getOpposite(IRestrictedVertex vertex,
			IRestrictedEdge edge);

	/**
	 * Returns a <code>Collection</code> view of the outgoing edges incident to
	 * <code>vertex</code> in this graph.
	 * 
	 * @param vertex
	 *            the vertex whose outgoing edges are to be returned
	 * @return a <code>Collection</code> view of the outgoing edges incident to
	 *         <code>vertex</code> in this graph
	 */
	public Collection<? extends IRestrictedEdge> getOutEdges(
			IRestrictedVertex vertex);

	/**
	 * Returns the number of predecessors that <code>vertex</code> has in this
	 * graph. Equivalent to <code>vertex.getPredecessors().size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose predecessor count is to be returned
	 * @return the number of predecessors that <code>vertex</code> has in this
	 *         graph
	 */
	public int getPredecessorCount(IRestrictedVertex vertex);

	/**
	 * Returns a <code>Collection</code> view of the predecessors of
	 * <code>vertex</code> in this graph. A predecessor of <code>vertex</code>
	 * is defined as a vertex <code>v</code> which is connected to
	 * <code>vertex</code> by an edge <code>e</code>, where <code>e</code> is an
	 * outgoing edge of <code>v</code> and an incoming edge of
	 * <code>vertex</code>.
	 * 
	 * @param vertex
	 *            the vertex whose predecessors are to be returned
	 * @return a <code>Collection</code> view of the predecessors of
	 *         <code>vertex</code> in this graph
	 */
	public Collection<? extends IRestrictedVertex> getPredecessors(
			IRestrictedVertex vertex);

	/**
	 * If <code>directed_edge</code> is a directed edge in this graph, returns
	 * the source; otherwise returns <code>null</code>. The source of a directed
	 * edge <code>d</code> is defined to be the vertex for which <code>d</code>
	 * is an outgoing edge. <code>directed_edge</code> is guaranteed to be a
	 * directed edge if its <code>EdgeType</code> is <code>DIRECTED</code>.
	 * 
	 * @param directed_edge
	 * @return the source of <code>directed_edge</code> if it is a directed edge
	 *         in this graph, or <code>null</code> otherwise
	 */
	public IRestrictedVertex getSource(IRestrictedEdge directed_edge);

	/**
	 * Returns the start vertex in this graph or null, if this graph is empty.
	 * If no start vertex is set in this graph, this method chooses and sets
	 * an arbitrary start vertex and returns this vertex.
	 * 
	 * @return start vertex
	 */
	public IRestrictedVertex getStartVertex();

	/**
	 * Returns the number of successors that <code>vertex</code> has in this
	 * graph. Equivalent to <code>vertex.getSuccessors().size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose successor count is to be returned
	 * @return the number of successors that <code>vertex</code> has in this
	 *         graph
	 */
	public int getSuccessorCount(IRestrictedVertex vertex);

	/**
	 * Returns a <code>Collection</code> view of the successors of
	 * <code>vertex</code> in this graph. A successor of <code>vertex</code> is
	 * defined as a vertex <code>v</code> which is connected to
	 * <code>vertex</code> by an edge <code>e</code>, where <code>e</code> is an
	 * incoming edge of <code>v</code> and an outgoing edge of
	 * <code>vertex</code>.
	 * 
	 * @param vertex
	 *            the vertex whose predecessors are to be returned
	 * @return a <code>Collection</code> view of the successors of
	 *         <code>vertex</code> in this graph
	 */
	public Collection<? extends IRestrictedVertex> getSuccessors(
			IRestrictedVertex vertex);

	/**
	 * Returns the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount();

	/**
	 * Returns a view of all vertices in this graph. In general, this obeys the
	 * <code>Collection</code> contract, and therefore makes no guarantees about
	 * the ordering of the vertices within the set.
	 * 
	 * @return a <code>Collection</code> view of all vertices in this graph
	 */
	public Collection<? extends IRestrictedVertex> getVertices();

	/**
	 * Returns the number of incoming edges incident to <code>vertex</code>.
	 * Equivalent to <code>getInEdges(vertex).size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose indegree is to be calculated
	 * @return the number of incoming edges incident to <code>vertex</code>
	 */
	public int inDegree(IRestrictedVertex vertex);

	/**
	 * Returns <code>true</code> if <code>vertex</code> is the destination of
	 * <code>edge</code>. Equivalent to
	 * <code>getDest(edge).equals(vertex)</code>.
	 * 
	 * @param vertex
	 *            the vertex to be queried
	 * @param edge
	 *            the edge to be queried
	 * @return <code>true</code> iff <code>vertex</code> is the destination of
	 *         <code>edge</code>
	 */
	public boolean isDest(IRestrictedVertex vertex, IRestrictedEdge edge);

	/**
	 * 
	 * @return true, if this graph contains no vertices and edges.
	 */
	public abstract boolean isEmpty();

	/**
	 * Returns <code>true</code> if <code>vertex</code> and <code>edge</code>
	 * are incident to each other. Equivalent to
	 * <code>getIncidentEdges(vertex).contains(edge)</code> and to
	 * <code>getIncidentVertices(edge).contains(vertex)</code>.
	 * 
	 * @param vertex
	 * @param edge
	 * @return <code>true</code> if <code>vertex</code> and <code>edge</code>
	 *         are incident to each other
	 */
	public boolean isIncident(IRestrictedVertex vertex, IRestrictedEdge edge);

	/**
	 * Returns <code>true</code> if <code>v1</code> and <code>v2</code> share an
	 * incident edge. Equivalent to <code>getNeighbors(v1).contains(v2)</code>.
	 * 
	 * @param v1
	 *            the first vertex to test
	 * @param v2
	 *            the second vertex to test
	 * @return <code>true</code> if <code>v1</code> and <code>v2</code> share an
	 *         incident edge
	 */
	public boolean isNeighbor(IRestrictedVertex v1, IRestrictedVertex v2);

	/**
	 * Returns <code>true</code> if <code>v1</code> is a predecessor of
	 * <code>v2</code> in this graph. Equivalent to
	 * <code>v1.getPredecessors().contains(v2)</code>.
	 * 
	 * @param v1
	 *            the first vertex to be queried
	 * @param v2
	 *            the second vertex to be queried
	 * @return <code>true</code> if <code>v1</code> is a predecessor of
	 *         <code>v2</code>, and false otherwise.
	 */
	public boolean isPredecessor(IRestrictedVertex v1, IRestrictedVertex v2);

	/**
	 * Returns <code>true</code> if <code>vertex</code> is the source of
	 * <code>edge</code>. Equivalent to
	 * <code>getSource(edge).equals(vertex)</code>.
	 * 
	 * @param vertex
	 *            the vertex to be queried
	 * @param edge
	 *            the edge to be queried
	 * @return <code>true</code> iff <code>vertex</code> is the source of
	 *         <code>edge</code>
	 */
	public boolean isSource(IRestrictedVertex vertex, IRestrictedEdge edge);

	/**
	 * Returns <code>true</code> if <code>v1</code> is a successor of
	 * <code>v2</code> in this graph. Equivalent to
	 * <code>v1.getSuccessors().contains(v2)</code>.
	 * 
	 * @param v1
	 *            the first vertex to be queried
	 * @param v2
	 *            the second vertex to be queried
	 * @return <code>true</code> if <code>v1</code> is a successor of
	 *         <code>v2</code>, and false otherwise.
	 */
	public boolean isSuccessor(IRestrictedVertex v1, IRestrictedVertex v2);

	/**
	 * Returns the number of outgoing edges incident to <code>vertex</code>.
	 * Equivalent to <code>getOutEdges(vertex).size()</code>.
	 * 
	 * @param vertex
	 *            the vertex whose outdegree is to be calculated
	 * @return the number of outgoing edges incident to <code>vertex</code>
	 */
	public int outDegree(IRestrictedVertex vertex);

	public void resetItemHelperVars();

	public void resetItemDoneVar();

	public void resetItemValueVar();

}
