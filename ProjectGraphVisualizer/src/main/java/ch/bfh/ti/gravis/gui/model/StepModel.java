package ch.bfh.ti.gravis.gui.model;

/**
 * This model class is used for updating the step panel.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class StepModel {

	private final boolean progressBarEnabled, spinnerEnabled,
			progressIndeterminate;

	/**
	 * 
	 * @param spinnerEnabled
	 * @param progressBarEnabled
	 * @param progressIndeterminate
	 */
	public StepModel(boolean spinnerEnabled, boolean progressBarEnabled,
			boolean progressIndeterminate) {

		this.progressBarEnabled = progressBarEnabled;
		this.spinnerEnabled = spinnerEnabled;
		this.progressIndeterminate = progressIndeterminate;
	}

	/**
	 * 
	 * @param spinnerEnabled
	 * @param progressBarEnabled
	 */
	public StepModel(boolean spinnerEnabled, boolean progressBarEnabled) {
		this(spinnerEnabled, progressBarEnabled, false);
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

	/**
	 * @return boolean
	 */
	public boolean isProgressIndeterminate() {
		return this.progressIndeterminate;
	}

}
