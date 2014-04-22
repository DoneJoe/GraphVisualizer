package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class StepModel {

	private final int stepValue, stepMaximum;
	
	private final boolean progressBarEnabled, spinnerEnabled;
	
	/**
	 * 
	 * @param stepValue
	 * @param stepMaximum
	 * @param spinnerEnabled
	 * @param progressBarEnabled
	 */
	protected StepModel(int stepValue, int stepMaximum, boolean spinnerEnabled, 
			boolean progressBarEnabled) {
		
		this.stepValue = stepValue;
		this.stepMaximum = stepMaximum;
		this.progressBarEnabled = progressBarEnabled;
		this.spinnerEnabled = spinnerEnabled;
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

	/**
	 * @return boolean
	 */
	public boolean isSpinnerEnabled() {
		return this.spinnerEnabled;
	}

	/**
	 * @return boolean
	 */
	public boolean isProgressBarEnabled() {
		return this.progressBarEnabled;
	}

}
