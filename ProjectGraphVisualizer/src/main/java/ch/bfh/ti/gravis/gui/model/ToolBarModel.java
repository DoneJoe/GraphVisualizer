package ch.bfh.ti.gravis.gui.model;

/**
 * This model class is used for updating the toolbar panel.
 * 
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
