package ch.bfh.ti.gravis.core.graph;

import java.util.Collection;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.GraphDecorator;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisGraph extends GraphDecorator<IVertex, IEdge> implements
		IGravisGraph {

	private static final long serialVersionUID = 7604897874620015084L;

	private static final String DEFAULT_NAME = "%s Graph %d";
	private static final String DEFAULT_DESCRIPTION = "%s Graph %d";
	private static final String DIR_STR = "gerichteter";
	private static final String UNDIR_STR = "ungerichteter";
	
	private static int counter = 0;

	private String graphDescription;

	private String graphName;

	private EdgeType edgeType;

	/**
	 * Creates an instance of a directed GravisGraph.
	 * 
	 * @param delegate
	 */
	protected GravisGraph(Graph<IVertex, IEdge> delegate) {
		this(delegate, EdgeType.DIRECTED);
	}

	/**
	 * Creates an instance of GravisGraph.
	 * 
	 * @param delegate
	 * @param edgeType
	 */
	protected GravisGraph(Graph<IVertex, IEdge> delegate, EdgeType edgeType) {
		super(delegate);
			
		String edgeTypeStr = edgeType == EdgeType.DIRECTED ? DIR_STR : UNDIR_STR;
		
		counter++;
		this.setName(String.format(DEFAULT_NAME, edgeTypeStr, counter));
		this.setDescription(String.format(DEFAULT_DESCRIPTION, edgeTypeStr, counter));
		this.setEdgeType(edgeType);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#clear()
	 */
	@Override
	public void clear() {
		Collection<IVertex> vertices = this.delegate.getVertices();
		
		for (IVertex vertex : vertices.toArray(new IVertex[vertices.size()])) {
			this.delegate.removeVertex(vertex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsEdgeName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsEdgeName(String edgeName) {
		for (IEdge edge : this.getEdges()) {
			if (edge.getName().equals(edgeName.trim())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsItemName(java
	 * .lang.String)
	 */
	@Override
	public boolean containsItemName(String itemName) {
		return this.containsVertexName(itemName) || this.containsEdgeName(itemName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#containsVertexName
	 * (java.lang.String)
	 */
	@Override
	public boolean containsVertexName(String vertexName) {
		for (IVertex vertex : this.getVertices()) {
			if (vertex.getName().equals(vertexName.trim())) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getGraphName()
	 */
	@Override
	public String getDescription() {
		return this.graphDescription;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getEdgeType()
	 */
	@Override
	public EdgeType getEdgeType() {
		return this.edgeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#getName()
	 */
	@Override
	public String getName() {
		return this.graphName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return this.delegate.getVertexCount() == 0
				&& this.delegate.getEdgeCount() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#setGraphName(java
	 * .lang.String)
	 */
	@Override
	public void setDescription(String graphDescription) {
		this.graphDescription = graphDescription.trim();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.ti.gravis.core.graph.IGravisGraph#setEdgeType(edu.
	 * uci.ics.jung.graph.util.EdgeType)
	 */
	@Override
	public void setEdgeType(EdgeType edgeType) {
		this.edgeType = edgeType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IGravisGraph#setName(int)
	 */
	@Override
	public void setName(String graphName) {
		this.graphName = graphName.trim();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	
}