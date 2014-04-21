package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepModel {

	private final int stepValue;
	
	private final int stepMaximum;
	
	/**
	 * @param stepValue
	 * @param stepMaximum
	 */
	protected StepModel(int stepValue, int stepMaximum) {
		this.stepValue = stepValue;
		this.stepMaximum = stepMaximum;
	}

	/**
	 * 
	 * @return int
	 */
	public int getStepValue() {
		return this.stepValue;
	}

	/**
	 * 
	 * @return int
	 */
	public int getStepMaximum() {
		return this.stepMaximum;
	}

}
