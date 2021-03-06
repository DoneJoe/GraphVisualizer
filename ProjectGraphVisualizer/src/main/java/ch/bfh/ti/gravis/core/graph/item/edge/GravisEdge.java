package ch.bfh.ti.gravis.core.graph.item.edge;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * This implementation of the {@link IEdge} interface gives access to mutable
 * edge methods.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisEdge extends AbstractGraphItem implements IEdge {

	private double weight;

	protected GravisEdge() {
		this.setWeight(GravisConstants.E_WEIGHT_DEFAULT);
		this.setCurrentColor(GravisConstants.E_COLOR_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEdge#getWeight()
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.IEdge#setWeight(int)
	 */
	@Override
	public void setWeight(double weight) {
		if (Double.compare(this.getWeight(), weight) != 0) {
			this.weight = weight;
			this.fireGraphItemsChangedEvent(this, Type.EDITED);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem#createItemName()
	 */
	@Override
	protected String createItemName() {
		return EdgeFactory.createEdgeName();
	}

}
