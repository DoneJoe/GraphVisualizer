package ch.bfh.bti7301.hs2013.gravis.gui.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ConfirmDialogAdapter extends AbstractDialogAdapter {

	/**
	 * @param parent
	 * 
	 */
	public ConfirmDialogAdapter(Component parent) {
		super(parent);
	}

	/**
	 * 
	 * @param message
	 * @param title
	 * @param optionType
	 * @return int
	 */
	public int showConfirmDialog(Object message,
			String title, int optionType) {
		return JOptionPane.showConfirmDialog(this.parent, message, title,
				optionType);
	}
}
