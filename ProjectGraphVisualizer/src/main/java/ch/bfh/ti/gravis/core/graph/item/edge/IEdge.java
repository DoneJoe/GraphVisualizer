package ch.bfh.ti.gravis.core.graph.item.edge;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.IGraphItem;

/**
 * This interface gives access to mutable edge methods.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public interface IEdge extends IGraphItem, IRestrictedEdge {

	/**
	 * Sets the weight of this edge. An edit event of type {@link Type#EDITED} is
	 * fired if {@code weight} is different from the existing value.
	 * 
	 * @param weight
	 */
	public abstract void setWeight(double weight);

}
