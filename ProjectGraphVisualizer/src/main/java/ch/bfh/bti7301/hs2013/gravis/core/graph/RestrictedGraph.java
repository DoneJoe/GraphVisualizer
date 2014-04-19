package ch.bfh.bti7301.hs2013.gravis.core.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

import ch.bfh.bti7301.hs2013.gravis.core.graph.comparator.ItemNameComparator;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.VertexFactory;
import ch.bfh.bti7301.hs2013.gravis.core.step.StepBuilder;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
final class RestrictedGraph implements IRestrictedGraph {

	private final IGravisGraph gravisGraph;

	private final StepBuilder stepBuilder;

	private final List<IRestrictedVertex> verticesList;
	private final List<IRestrictedEdge> edgesList;

	private final BidiMap<IVertex, IRestrictedVertex> verticesMap;
	private final BidiMap<IEdge, IRestrictedEdge> edgesMap;

	private final ItemNameComparator itemComparator;

	/**
	 * @param graph
	 * @param stepBuilder
	 */
	RestrictedGraph(final IGravisGraph graph, final StepBuilder stepBuilder) {
		this.gravisGraph = graph;
		this.stepBuilder = stepBuilder;
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#addStep(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem[])
	 */
	@Override
	public void addStep(IRestrictedGraphItem... restrictedItems) {
		this.stepBuilder.addStep(this.getIGraphItems(restrictedItems));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#containsEdge(
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean containsEdge(IRestrictedEdge edge) {
		return this.gravisGraph.containsEdge(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#containsVertex
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean containsVertex(IRestrictedVertex vertex) {
		return this.gravisGraph.containsVertex(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#degree(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int degree(IRestrictedVertex vertex) {
		return this.gravisGraph.degree(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#findEdge(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public IRestrictedEdge findEdge(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.edgesMap.get(this.gravisGraph.findEdge(
				this.verticesMap.getKey(v1), this.verticesMap.getKey(v2)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#findEdgeSet(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getDefaultEdgeType
	 * ()
	 */
	@Override
	public EdgeType getDefaultEdgeType() {
		return this.gravisGraph.getDefaultEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getDest(ch.bfh
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdgeCount()
	 */
	@Override
	public int getEdgeCount() {
		return this.gravisGraph.getEdgeCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdgeCount(
	 * edu.uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public int getEdgeCount(EdgeType edge_type) {
		return this.gravisGraph.getEdgeCount(edge_type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdges()
	 */
	@Override
	public Collection<? extends IRestrictedEdge> getEdges() {
		return this.edgesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdges(edu.
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.gravisGraph.getEdgeType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEdgeType(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public EdgeType getEdgeType(IRestrictedEdge edge) {
		return this.gravisGraph.getEdgeType(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getEndpoints(
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getIncidentCount
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public int getIncidentCount(IRestrictedEdge edge) {
		return this.gravisGraph.getIncidentCount(this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getIncidentEdges
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getIncidentVertices
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getInEdges(ch
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getNeighborCount
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getNeighborCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getNeighborCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getNeighbors(
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getOpposite(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getOutEdges(ch
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getPredecessorCount
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getPredecessorCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getPredecessorCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getPredecessors
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getSource(ch.
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
	 * @see ch.bfh.bti7301.hs2013.gravis.common.IImmutableGraph#getStartVertex()
	 */
	@Override
	public IRestrictedVertex getStartVertex() {
		if (this.verticesList.isEmpty()) {
			return null;
		}

		return this.verticesList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getSuccessorCount
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int getSuccessorCount(IRestrictedVertex vertex) {
		return this.gravisGraph.getSuccessorCount(this.verticesMap
				.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getSuccessors
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getVertexCount()
	 */
	@Override
	public int getVertexCount() {
		return this.gravisGraph.getVertexCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#getVertices()
	 */
	@Override
	public Collection<? extends IRestrictedVertex> getVertices() {
		return this.verticesList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#inDegree(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public int inDegree(IRestrictedVertex vertex) {
		return this.gravisGraph.inDegree(this.verticesMap.getKey(vertex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isDest(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isDest(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isDest(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
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
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isIncident(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isIncident(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isIncident(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isNeighbor(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isNeighbor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isNeighbor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isPredecessor
	 * (ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isPredecessor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isPredecessor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isSource(ch.bfh
	 * .bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IRestrictedEdge)
	 */
	@Override
	public boolean isSource(IRestrictedVertex vertex, IRestrictedEdge edge) {
		return this.gravisGraph.isSource(this.verticesMap.getKey(vertex),
				this.edgesMap.getKey(edge));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#isSuccessor(ch
	 * .bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex,
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IRestrictedVertex)
	 */
	@Override
	public boolean isSuccessor(IRestrictedVertex v1, IRestrictedVertex v2) {
		return this.gravisGraph.isSuccessor(this.verticesMap.getKey(v1),
				this.verticesMap.getKey(v2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.IRestrictedGraph#outDegree(ch.
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

	/**
	 * @param restrictedItems
	 * @return IGraphItem[]
	 */
	private IGraphItem[] getIGraphItems(
			final IRestrictedGraphItem ... restrictedItems) {
		
		List<IGraphItem> iGraphItems = new ArrayList<>();

		for (IRestrictedGraphItem item : restrictedItems) {
			if (item instanceof IRestrictedVertex) {
				iGraphItems.add(this.verticesMap.getKey(item));
			}

			if (item instanceof IRestrictedEdge) {
				iGraphItems.add(this.edgesMap.getKey(item));
			}
		}

		return iGraphItems.toArray(new IGraphItem[iGraphItems.size()]);
	}

	/**
	 * @param coll
	 * @return List<IRestrictedEdge>
	 */
	private List<IRestrictedEdge> getRestrictedSortedEdgesList(
			final Collection<? extends IEdge> coll) {

		List<IRestrictedEdge> edgesList = new ArrayList<>(coll.size());

		// Adds restricted edge instances to the edges list
		for (IEdge edge : coll) {
			if (edge != null) {
				if (this.edgesMap.containsKey(edge)) {
					edgesList.add(this.edgesMap.get(edge));
				} else {
					IRestrictedEdge restrictedEdge = EdgeFactory
							.createRestrictedEdge(edge);

					this.edgesMap.put(edge, restrictedEdge);
					edgesList.add(restrictedEdge);
				}
			}
		}

		// Sorts the vertices in lexicographical id order
		Collections.sort(edgesList, this.itemComparator);

		return edgesList;
	}

	/**
	 * @param coll
	 * @return List<IRestrictedVertex>
	 */
	private List<IRestrictedVertex> getRestrictedSortedVerticesList(
			final Collection<? extends IVertex> coll) {

		List<IRestrictedVertex> verticesList = new ArrayList<>(coll.size());

		// Adds restricted vertex instances to the vertices list
		for (IVertex vertex : coll) {
			if (vertex != null) {
				if (this.verticesMap.containsKey(vertex)) {
					verticesList.add(this.verticesMap.get(vertex));
				} else {
					IRestrictedVertex restrictedVertex = VertexFactory
							.createRestrictedVertex(vertex);

					this.verticesMap.put(vertex, restrictedVertex);
					verticesList.add(restrictedVertex);
				}
			}
		}

		// Sorts the vertices in lexicographical id order
		Collections.sort(verticesList, this.itemComparator);

		// moves the start vertex to the first position in the list
		for (int i = 0; i < verticesList.size(); i++) {
			if (verticesList.get(i).isStart()) {
				verticesList.add(0, verticesList.remove(i));
				break;
			}
		}

		return verticesList;
	}

}
