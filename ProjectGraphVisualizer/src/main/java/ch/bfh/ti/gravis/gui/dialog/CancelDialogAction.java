package ch.bfh.ti.gravis.gui.dialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class CancelDialogAction extends AbstractAction {

	private static final long serialVersionUID = -6969975031584551383L;

	private final JDialog dialog;
	
	/**
	 * 
	 * @param dialog
	 */
	public CancelDialogAction(JDialog dialog) {
		this.dialog = dialog;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dialog.dispose();
	}

}
