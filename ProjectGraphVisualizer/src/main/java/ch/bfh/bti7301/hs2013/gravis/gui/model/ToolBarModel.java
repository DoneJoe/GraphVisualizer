package ch.bfh.bti7301.hs2013.gravis.gui.model;

import javax.swing.ComboBoxModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
class ToolBarModel implements IToolBarModel {

	private boolean algoComboEnabled;
	
	private boolean newCalcButtonVisible;
	
	private ComboBoxModel<String> comboBoxModel;
	
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

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IToolBarModel#isAlgoComboVisible()
	 */
	@Override
	public boolean isAlgoComboEnabled() {
		return this.algoComboEnabled;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IToolBarModel#isNewCalcButtonVisible()
	 */
	@Override
	public boolean isNewCalcButtonVisible() {
		return this.newCalcButtonVisible;
	}

	/* (non-Javadoc)
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.model.IToolBarModel#getAlgorithmComboBoxModel()
	 */
	@Override
	public ComboBoxModel<String> getAlgorithmComboBoxModel() {
		return this.comboBoxModel;
	}


}
