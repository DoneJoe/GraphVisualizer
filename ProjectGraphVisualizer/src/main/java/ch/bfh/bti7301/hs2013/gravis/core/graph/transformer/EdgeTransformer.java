package ch.bfh.bti7301.hs2013.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.EdgeFactory;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;
import ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeTransformer implements Transformer<EdgeMetadata, IEdge> {

	private final EdgeFactory edgeFactory;

	public EdgeTransformer() {
		this.edgeFactory = new EdgeFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public IEdge transform(EdgeMetadata edgeMeta) {
		IEdge edge = this.edgeFactory.create();
		String color = edgeMeta.getProperty(GravisConstants.E_COLOR);

		edge.setName(edgeMeta.getId());
		edge.setCurrentColor(color == null ? GravisConstants.E_COLOR_DEFAULT : 
			ValueTransformer.transformStringToColor(color));		
		
		edge.setWeight(ValueTransformer.transformDouble(edgeMeta
				.getProperty(GravisConstants.E_WEIGHT)));
		return edge;
	}

}
