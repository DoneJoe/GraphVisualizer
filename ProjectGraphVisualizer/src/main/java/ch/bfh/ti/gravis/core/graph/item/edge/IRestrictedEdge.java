package ch.bfh.ti.gravis.core.graph.item.edge;

import ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem;

/**
 * This interface gives restricted access to additional edge methods. Only
 * getter methods are accessable.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IRestrictedEdge extends IRestrictedGraphItem {

	/**
	 * Returns the weight of this edge.
	 * 
	 * @return weight
	 */
	public abstract double getWeight();

}
