package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.ValueTransformer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class VertexColorStringTransformer implements
		Transformer<IVertex, String> {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IVertex vertex) {
		return ValueTransformer.colorToString(vertex.getCurrentColor());
	}

}
