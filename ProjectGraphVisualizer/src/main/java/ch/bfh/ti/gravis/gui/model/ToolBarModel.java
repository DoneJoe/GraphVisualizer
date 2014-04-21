package ch.bfh.ti.gravis.gui.model;

import javax.swing.ComboBoxModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class ToolBarModel  {

	private final boolean algoComboEnabled;
	
	private final boolean newCalcButtonVisible;
	
	private final ComboBoxModel<String> comboBoxModel;
	
	/**
	 * 
	 * @param comboBoxModel 
	 * @param algoComboEnabled
	 * @param newCalcButtonVisible
	 */
	protected ToolBarModel(ComboBoxModel<String> comboBoxModel, boolean algoComboEnabled, 
			boolean newCalcButtonVisible) {
		this.comboBoxModel = comboBoxModel;
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

	
	public ComboBoxModel<String> getAlgorithmComboBoxModel() {
		return this.comboBoxModel;
	}


}
