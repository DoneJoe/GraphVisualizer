package ch.bfh.ti.gravis.gui.model;

import javax.swing.ComboBoxModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class ToolBarViewModel implements IToolBarViewModel {

	private final boolean algoComboEnabled;
	
	private final boolean newCalcButtonVisible;
	
	private final ComboBoxModel<String> comboBoxModel;
	
	/**
	 * 
	 * @param comboBoxModel 
	 * @param algoComboEnabled
	 * @param newCalcButtonVisible
	 */
	protected ToolBarViewModel(ComboBoxModel<String> comboBoxModel, boolean algoComboEnabled, 
			boolean newCalcButtonVisible) {
		this.comboBoxModel = comboBoxModel;
		this.algoComboEnabled = algoComboEnabled;
		this.newCalcButtonVisible = newCalcButtonVisible;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IToolBarViewModel#isAlgoComboVisible()
	 */
	@Override
	public boolean isAlgoComboEnabled() {
		return this.algoComboEnabled;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IToolBarViewModel#isNewCalcButtonVisible()
	 */
	@Override
	public boolean isNewCalcButtonVisible() {
		return this.newCalcButtonVisible;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.ti.gravis.gui.model.IToolBarViewModel#getAlgorithmComboBoxModel()
	 */
	@Override
	public ComboBoxModel<String> getAlgorithmComboBoxModel() {
		return this.comboBoxModel;
	}


}
