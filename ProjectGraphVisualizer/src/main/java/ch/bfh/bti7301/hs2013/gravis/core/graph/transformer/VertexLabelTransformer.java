package ch.bfh.bti7301.hs2013.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexLabelTransformer implements Transformer<IVertex, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IVertex vertex) {
		return vertex.getName()
				+ (Double.isNaN(vertex.getCurrentResult()) ? "" : ": "
						+ ValueTransformer.round2Decimals(vertex
								.getCurrentResult()));
	}

}
