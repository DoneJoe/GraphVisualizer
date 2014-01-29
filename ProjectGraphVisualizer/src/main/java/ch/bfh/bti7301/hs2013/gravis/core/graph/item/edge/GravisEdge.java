package ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge;

import ch.bfh.bti7301.hs2013.gravis.core.graph.GravisGraphEvent;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem;
import ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class GravisEdge extends AbstractGraphItem implements IEdge {

	private double weight;

	protected GravisEdge() {
		super();

		this.weight = GravisConstants.E_WEIGHT_DEFAULT;
		this.setCurrentColor(GravisConstants.E_COLOR_DEFAULT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.AbstractGravisGraphItem#toString
	 * ()
	 */
	@Override
	public String toString() {
		;
		return super.toString();
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
		boolean equal = Double.compare(this.weight, weight) == 0;
		this.weight = weight;
		
		if (!equal) {
			this.fireEditingGraphEvent(new GravisGraphEvent(this));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem#clone()
	 */
	@Override
	public GravisEdge clone() throws CloneNotSupportedException {
		return (GravisEdge) super.clone();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.core.graph.item.AbstractGraphItem#
	 * getTaggedStrokeWidth()
	 */
	@Override
	protected float getTaggedStrokeWidth() {
		return GravisConstants.E_TAGGED_STROKE;
	}

}
