package ch.bfh.ti.gravis.core.graph.item.edge;

import ch.bfh.ti.gravis.core.graph.item.AbstractRestrictedGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
final class RestrictedEdge extends AbstractRestrictedGraphItem implements IRestrictedEdge {

	private IEdge edge;

	RestrictedEdge(IEdge edge) {
		super(edge);
		
		this.edge = edge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.edge.IEdge#getWeight()
	 */
	@Override
	public double getWeight() {
		return this.edge.getWeight();
	}

	
}
