package ch.bfh.bti7301.hs2013.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgeLabelTransformer implements Transformer<IEdge, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IEdge edge) {
		return edge.isCurrentVisible() ? (ValueTransformer
				.transformDoubleToString(edge.getWeight()) + (Double.isNaN(edge
				.getCurrentResult()) ? "" : " : "
				+ ValueTransformer.transformDoubleToString(edge
						.getCurrentResult()))) : "";
	}
}
