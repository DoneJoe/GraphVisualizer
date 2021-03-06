package ch.bfh.ti.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import ch.bfh.ti.gravis.core.graph.comparator.ItemNameComparator;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;
import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.ti.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.ti.gravis.core.step.StepBuilder;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * This implementation of the {@link IRestrictedGraph} interface decorates a graph
 * of type {@link IGravisGraph}.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
final class RestrictedGraph implements IRestrictedGraph {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "RestrictedGraph.%s(): %s == %s";

	private final IGravisGraph gravisGraph;

	private final StepBuilder stepBuilder;

	private final List<IRestrictedVertex> verticesList;
	private final List<IRestrictedEdge> edgesList;

	private final BidiMap<IVertex, IRestrictedVertex> verticesMap;
	private final BidiMap<IEdge, IRestrictedEdge> edgesMap;

	private final ItemNameComparator itemComparator;

	/**
	 * Decorates the given graph.
	 * 
	 * @param graph
	 * @param stepBuilder
	 * @throws NullPointerException
	 *             if the graph or the stepBuilder is null
	 */
	RestrictedGraph(final IGravisGraph graph, final StepBuilder stepBuilder) {
		this.gravisGraph = Objects.requireNonNull(graph, String.format(
				NULL_POINTER_MSG, "RestrictedGraph", "graph", graph));
		this.stepBuilder = Objects.requireNonNull(stepBuilder, String
				.format(NULL_POINTER_MSG, "RestrictedGraph", "stepBuilder",
						stepBuilder));

		this.verticesMap = new DualHashBidiMap<>();
		this.edgesMap = new DualHashBidiMap<>();
		this.itemComparator = new ItemNameComparator();
		this.verticesList = this.getRestrictedSortedVerticesList(graph
				.getVertices());
		this.edgesList = this.getRestrictedSortedEdgesList(graph.getEdges());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#addStep(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem[])
	 */
	@Override
	public void addStep(IRestrictedGraphItem... restrictedItems) {
		Objects.requireNonNull(restrictedItems, String.format(NULL_POINTER_MSG,
				"addStep", "restrictedItems", restrictedItems));
		this.stepBuilder.addStep(this.getIGraphItems(restrictedItems));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#containsEdge(
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean containsEdge(IRestrictedEdge edge) {
		return this.gravisGraph.containsEdge(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#containsVertex
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean containsVertex(IRestrictedVertex vertex) {
		return this.gravisGraph.containsVertex(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#degree(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int degree(IRestrictedVertex vertex) {
		return this.gravisGraph.degree(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#findEdge(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public IRestrictedEdge findEdge(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.edgesMap.get(this.gravisGraph.findEdge(
				this.verticesMap.getKey(v1), this.verticesMap.getKey(v2)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#findEdgeSet(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedEdge> findEdgeSet(
			IRestrictedVertex v1, IRestrictedVertex v2) {

		return this.getRestrictedSortedEdgesList(this.gravisGraph.findEdgeSet(
				this.verticesMap.getKey(v1), this.verticesMap.getKey(v2)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getDefaultEdgeType ()
	 */
	@Override
	public EdgeType getDefaultEdgeType() {
		return this.gravisGraph.getDefaultEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getDest(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public IRestrictedVertex getDest(IRestrictedEdge directed_edge) {
		return this.verticesMap.get(this.gravisGraph.getDest(this.edgesMap
				.getKey(directed_edge)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdgeCount()
	 */
	@Override
	public int getEdgeCount() {
		return this.gravisGraph.getEdgeCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdgeCount(
	 * edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public int getEdgeCount(EdgeType edge_type) {
		return this.gravisGraph.getEdgeCount(edge_type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdges()
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getEdges() {
		return this.edgesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdges(edu.
	 * uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getEdges(EdgeType edge_type) {
		return this.getRestrictedSortedEdgesList(this.gravisGraph
				.getEdges(edge_type));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.gravisGraph.getEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEdgeType(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public EdgeType getEdgeType(IRestrictedEdge edge) {
		return this.gravisGraph.getEdgeType(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getEndpoints(
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public Pair<? extends IRestrictedVertex> getEndpoints(IRestrictedEdge edge) {
		Pair<IVertex> pair = this.gravisGraph.getEndpoints(this.edgesMap
				.getKey(edge));

		return new Pair<IRestrictedVertex>(
				this.verticesMap.get(pair.getFirst()),
				this.verticesMap.get(pair.getSecond()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getIncidentCount
	 * (ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public int getIncidentCount(IRestrictedEdge edge) {
		return this.gravisGraph.getIncidentCount(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getIncidentEdges
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getIncidentEdges(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedEdgesList(this.gravisGraph
				.getIncidentEdges(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getIncidentVertices
	 * (ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getIncidentVertices(
			IRestrictedEdge edge) {
		return this.getRestrictedSortedVerticesList(this.gravisGraph
				.getIncidentVertices(this.edgesMap.getKey(edge)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getInEdges(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getInEdges(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedEdgesList(this.gravisGraph
				.getInEdges(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getNeighborCount
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getNeighborCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getNeighborCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getNeighbors(
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getNeighbors(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedVerticesList(this.gravisGraph
				.getNeighbors(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getOpposite(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public IRestrictedVertex getOpposite(IRestrictedVertex vertex,
			IRestrictedEdge edge) {
		return this.verticesMap.get(this.gravisGraph.getOpposite(
				this.verticesMap.getKey(vertex), this.edgesMap.getKey(edge)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getOutEdges(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getOutEdges(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedEdgesList(this.gravisGraph
				.getOutEdges(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getPredecessorCount
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getPredecessorCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getPredecessorCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getPredecessors
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getPredecessors(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedVerticesList(this.gravisGraph
				.getPredecessors(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getSource(ch.
	 * bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public IRestrictedVertex getSource(IRestrictedEdge directed_edge) {
		return this.verticesMap.get(this.gravisGraph.getSource(this.edgesMap
				.getKey(directed_edge)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.common.IImmutableGraph#getStartVertex()
	 */
	@Override
	public IRestrictedVertex getStartVertex() {
		if (this.verticesList.isEmpty()) {
			return null;
		}

		IRestrictedVertex startVertex = this.verticesList.get(0);
		if (!startVertex.isStart()) {
			this.verticesMap.getKey(startVertex).setStart(true);
		}
		return startVertex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getSuccessorCount
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getSuccessorCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getSuccessorCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getSuccessors
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getSuccessors(
			IRestrictedVertex vertex) {
		return this.getRestrictedSortedVerticesList(this.gravisGraph
				.getSuccessors(this.verticesMap.getKey(vertex)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getVertexCount()
	 */
	@Override
	public int getVertexCount() {
		return this.gravisGraph.getVertexCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#getVertices()
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getVertices() {
		return this.verticesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#inDegree(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int inDegree(IRestrictedVertex vertex) {
		return this.gravisGraph.inDegree(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isDest(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isDest(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isDest(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.gravisGraph.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isIncident(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isIncident(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isIncident(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isNeighbor(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isNeighbor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isNeighbor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isPredecessor
	 * (ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isPredecessor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isPredecessor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isSource(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isSource(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isSource(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#isSuccessor(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.ti.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isSuccessor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isSuccessor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#outDegree(ch.
	 * bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int outDegree(IRestrictedVertex vertex) {
		return this.gravisGraph.outDegree(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.gravisGraph.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#resetItemHelperVars()
	 */
	@Override
	public void resetItemHelperVars() {
		this.gravisGraph.resetItemHelperVars();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#resetItemDoneVars()
	 */
	@Override
	public void resetItemDoneVars() {
		for (IVertex vertex : this.gravisGraph.getVertices()) {
			vertex.setDone(false);
		}

		for (IEdge edge : this.gravisGraph.getEdges()) {
			edge.setDone(false);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IRestrictedGraph#resetItemValueVars()
	 */
	@Override
	public void resetItemValueVars() {
		for (IVertex vertex : this.gravisGraph.getVertices()) {
			vertex.setValue(null);
		}

		for (IEdge edge : this.gravisGraph.getEdges()) {
			edge.setValue(null);
		}
	}

	/**
	 * Looks up the corresponding graph item for each given restricted graph
	 * item and returns an array of that graph items.
	 * 
	 * @param restrictedItems
	 * @return an array of IGraphItem
	 */
	private IGraphItem[] getIGraphItems(
			final IRestrictedGraphItem... restrictedItems) {

		List<IGraphItem> items = new ArrayList<>();

		for (IRestrictedGraphItem item : restrictedItems) {
			if (item instanceof IRestrictedVertex) {
				items.add(this.verticesMap.getKey(item));
			}

			if (item instanceof IRestrictedEdge) {
				items.add(this.edgesMap.getKey(item));
			}
		}

		return items.toArray(new IGraphItem[items.size()]);
	}

	/**
	 * Looks up the corresponding restricted edge for each edge in the given
	 * collection and returns a sorted list of that edges.
	 * 
	 * @param coll
	 * @return a List of IRestrictedEdge
	 */
	private List<IRestrictedEdge> getRestrictedSortedEdgesList(
			final Collection<? extends IEdge> coll) {

		List<IRestrictedEdge> edgesList = new ArrayList<>(coll.size());

		// add restricted edge instances to the edges list
		for (IEdge edge : coll) {
			if (edge != null) {
				if (this.edgesMap.containsKey(edge)) {
					edgesList.add(this.edgesMap.get(edge));
				} else {
					IRestrictedEdge restrictedEdge = EdgeFactory
							.createRestrictedEdge(edge);

					// if no corresponding restricted edge exists: add new
					// restricted edge to edgesMap
					this.edgesMap.put(edge, restrictedEdge);
					edgesList.add(restrictedEdge);
				}
			}
		}

		// sort the edges in lexicographical name order
		Collections.sort(edgesList, this.itemComparator);

		return edgesList;
	}

	/**
	 * Looks up the corresponding restricted vertex for each vertex in the given
	 * collection and returns a sorted list of that vertices.
	 * 
	 * @param coll
	 * @return a list of IRestrictedVertex
	 */
	private List<IRestrictedVertex> getRestrictedSortedVerticesList(
			final Collection<? extends IVertex> coll) {

		List<IRestrictedVertex> verticesList = new ArrayList<>(coll.size());

		// add restricted vertex instances to the vertices list
		for (IVertex vertex : coll) {
			if (vertex != null) {
				if (this.verticesMap.containsKey(vertex)) {
					verticesList.add(this.verticesMap.get(vertex));
				} else {
					IRestrictedVertex restrictedVertex = VertexFactory
							.createRestrictedVertex(vertex);

					// if no corresponding restricted vertex exists: add new
					// restricted vertex to verticesMap
					this.verticesMap.put(vertex, restrictedVertex);
					verticesList.add(restrictedVertex);
				}
			}
		}

		// sort the vertices in lexicographical name order
		Collections.sort(verticesList, this.itemComparator);

		// move the start vertex to the first position in the list
		for (int i = 0; i < verticesList.size(); i++) {
			if (verticesList.get(i).isStart()) {
				verticesList.add(0, verticesList.remove(i));
				break;
			}
		}

		return verticesList;
	}

}
