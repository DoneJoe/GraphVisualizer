package ch.bfh.bti7301.hs2013.gravis.core.graph.transformer;

import org.apache.commons.collections15.Transformer;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class EdgeToolTipTransformer implements Transformer<IEdge, String> {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections15.Transformer#transform(java.lang.Object)
	 */
	@Override
	public String transform(IEdge edge) {
		return "Kante " + edge.getName();
	}

}
