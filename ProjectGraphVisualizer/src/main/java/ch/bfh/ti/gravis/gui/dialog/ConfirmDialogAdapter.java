package ch.bfh.ti.gravis.gui.dialog;

import java.awt.Component;
import java.util.Objects;

import javax.swing.JOptionPane;

/**
 * A confirm dialog adapter.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ConfirmDialogAdapter extends AbstractDialogAdapter {

	private static final String NULL_POINTER_MSG = "Invalid parameter value in method "
			+ "ConfirmDialogAdapter.%s(): %s == %s";
	
	/**
	 * @param parent
	 * 
	 */
	public ConfirmDialogAdapter(Component parent) {
		super(Objects.requireNonNull(parent, String.format(
				NULL_POINTER_MSG, "ConfirmDialogAdapter", "parent",
				parent)));
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
