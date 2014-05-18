package ch.bfh.ti.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class EdgeNameTransformer implements Transformer<IEdge, String> {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IEdge edge) {
		return edge == null ? "" : edge.getName();
	}

}
