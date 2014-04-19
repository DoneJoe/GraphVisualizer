package ch.bfh.ti.gravis.gui.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MessageDialogAdapter extends AbstractDialogAdapter {

	/**
	 * @param parent
	 * 
	 */
	public MessageDialogAdapter(Component parent) {
		super(parent);
	}

	/**
	 * 
	 * @param message
	 * @param title
	 * @param messageType
	 */
	public void showMessageDialog(Object message,
			String title, int messageType) {
		JOptionPane.showMessageDialog(this.parent, message, title,
				messageType);
	}
}
