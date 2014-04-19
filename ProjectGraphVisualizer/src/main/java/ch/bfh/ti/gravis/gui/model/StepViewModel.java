package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepViewModel implements IStepViewModel {

	private final int stepValue;
	
	private final int stepMaximum;
	
	/**
	 * @param stepValue
	 * @param stepMaximum
	 */
	public StepViewModel(int stepValue, int stepMaximum) {
		this.stepValue = stepValue;
		this.stepMaximum = stepMaximum;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IStepViewModel#getStepValue()
	 */
	@Override
	public int getStepValue() {
		return this.stepValue;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IStepViewModel#getStepMaximum()
	 */
	@Override
	public int getStepMaximum() {
		return this.stepMaximum;
	}

}
