package ch.bfh.ti.gravis.core.graph.item.edge;

import java.util.Objects;

import ch.bfh.ti.gravis.core.graph.IEditGraphEventListener.Type;
import ch.bfh.ti.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.ti.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisEdge extends AbstractGraphItem implements IEdge {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "GravisEdge.%s(): %s == %s";
	
	private static final String LABEL = "e";
	
	private static int counter = 0;
	
	private String edgeName;

	private double weight;

	protected GravisEdge() {
		this.setName(LABEL + String.valueOf(++counter));
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
		boolean equal = Double.compare(this.getWeight(), weight) == 0;
		this.weight = weight;
		
		if (!equal) {
			this.fireGraphItemsChangedEvent(this, Type.EDITED);
		}
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.graph.item.IGraphItem#setName(java.lang.String)
	 */
	@Override
	public void setName(final String name) {
		Objects.requireNonNull(name, String.format(
				NULL_POINTER_MSG, "setName", "name",
				name));
		boolean equal = this.getName() == null ? false : this.getName().equals(name.trim());
		this.edgeName = name.trim();

		if (!equal) {
			this.fireGraphItemsChangedEvent(this, Type.EDITED);
		}		
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.core.graph.item.IRestrictedGraphItem#getName()
	 */
	@Override
	public String getName() {
		return this.edgeName;
	}

}
