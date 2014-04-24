package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ToolBarModel  {

	private final boolean algoComboEnabled;
	
	private final boolean newCalcButtonVisible;
	
	private final boolean modeComboEnabled;
	
	/**
	 * 
	 * @param algoComboEnabled
	 * @param newCalcButtonVisible
	 * @param modeComboEnabled
	 */
	protected ToolBarModel(boolean algoComboEnabled, boolean newCalcButtonVisible, 
			boolean modeComboEnabled) {
		
		this.algoComboEnabled = algoComboEnabled;
		this.newCalcButtonVisible = newCalcButtonVisible;
		this.modeComboEnabled = modeComboEnabled;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAlgoComboEnabled() {
		return this.algoComboEnabled;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isNewCalcButtonVisible() {
		return this.newCalcButtonVisible;
	}

	/**
	 * @return modeComboEnabled
	 */
	public boolean isModeComboEnabled() {
		return this.modeComboEnabled;
	}

	
}
