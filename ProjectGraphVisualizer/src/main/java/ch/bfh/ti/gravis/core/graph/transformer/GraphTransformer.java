package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.GraphFactory;
import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.core.util.GravisConstants;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.GraphMetadata.EdgeDefault;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphTransformer implements
		Transformer<GraphMetadata, IGravisGraph> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IGravisGraph transform(final GraphMetadata graphMeta) {
		IGravisGraph newGraph = GraphFactory.createDirectedGravisGraph();

		if (graphMeta != null) {
			EdgeType edgeType = graphMeta.getEdgeDefault() == EdgeDefault.UNDIRECTED ? EdgeType.UNDIRECTED
					: EdgeType.DIRECTED;

			newGraph.setName(graphMeta.getId());
			newGraph.setEdgeType(edgeType);

			if (graphMeta.getProperty(GravisConstants.G_DESCRIPTION) != null) {
				newGraph.setDescription(graphMeta.getProperty(
						GravisConstants.G_DESCRIPTION).trim());
			}
		}
		
		return newGraph;
	}

}
