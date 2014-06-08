package ch.bfh.ti.gravis.gui.dialog;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * This Action class disposes a given {@code JDialog} in case of an action event.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class CancelDialogAction extends AbstractAction {

	private static final long serialVersionUID = -6969975031584551383L;

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "CancelDialogAction.%s(): %s == %s";
	
	private final JDialog dialog;
	
	/**
	 * 
	 * @param dialog
	 */
	public CancelDialogAction(JDialog dialog) {
		this.dialog = Objects.requireNonNull(dialog, String.format(
				NULL_POINTER_MSG, "CancelDialogAction", "dialog",
				dialog));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.dialog.dispose();
	}

}
