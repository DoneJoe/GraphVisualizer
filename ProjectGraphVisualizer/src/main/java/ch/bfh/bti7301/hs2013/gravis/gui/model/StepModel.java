package ch.bfh.bti7301.hs2013.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepModel implements IStepModel {

	private final int stepValue;
	
	private final int stepMaximum;
	
	/**
	 * @param stepValue
	 * @param stepMaximum
	 */
	public StepModel(int stepValue, int stepMaximum) {
		this.stepValue = stepValue;
		this.stepMaximum = stepMaximum;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IStepModel#getStepValue()
	 */
	@Override
	public int getStepValue() {
		return this.stepValue;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IStepModel#getStepMaximum()
	 */
	@Override
	public int getStepMaximum() {
		return this.stepMaximum;
	}

}
