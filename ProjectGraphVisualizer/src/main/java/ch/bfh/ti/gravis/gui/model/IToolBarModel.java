package ch.bfh.ti.gravis.gui.model;

import javax.swing.ComboBoxModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public interface IToolBarModel {

	/**
	 * 
	 * @return boolean
	 */
	public boolean isAlgoComboEnabled();
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isNewCalcButtonVisible();

	/**
	 * @return ComboBoxModel<String>
	 */
	public ComboBoxModel<String> getAlgorithmComboBoxModel();
}
