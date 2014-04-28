package ch.bfh.ti.gravis.gui.model;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ToolBarModel  {

	private final boolean algoComboEnabled;
	
	private final boolean newCalcButtonVisible;
	
	/**
	 * 
	 * @param algoComboEnabled
	 * @param newCalcButtonVisible
	 */
	public ToolBarModel(boolean algoComboEnabled, boolean newCalcButtonVisible) {
		
		this.algoComboEnabled = algoComboEnabled;
		this.newCalcButtonVisible = newCalcButtonVisible;
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

	
}
