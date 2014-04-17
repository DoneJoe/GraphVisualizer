package ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge;

import java.util.Objects;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisEdge extends AbstractGraphItem implements IEdge {

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
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEdge#getWeight()
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.IEdge#setWeight(int)
	 */
	@Override
	public void setWeight(double weight) {
		boolean equal = Double.compare(this.getWeight(), weight) == 0;
		this.weight = weight;
		
		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IGraphItem#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Exception handling bei null values
		Objects.requireNonNull(name);
		boolean equal = this.getName() == null ? false : this.getName().equals(name.trim());
		this.edgeName = name.trim();

		if (!equal) {
			this.fireGraphItemsChangedEvent(this);
		}		
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.IRestrictedGraphItem#getName()
	 */
	@Override
	public String getName() {
		return this.edgeName;
	}

}
