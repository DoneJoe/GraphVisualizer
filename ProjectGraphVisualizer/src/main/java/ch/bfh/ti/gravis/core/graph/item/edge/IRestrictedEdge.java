package ch.bfh.ti.gravis.core.graph.item.edge;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IRestrictedEdge extends IRestrictedGraphItem {

	/**
	 * 
	 * @return double weight
	 */
	public abstract double getWeight();
	
}
