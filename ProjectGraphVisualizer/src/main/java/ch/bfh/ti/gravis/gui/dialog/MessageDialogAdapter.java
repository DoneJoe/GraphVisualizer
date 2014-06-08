package ch.bfh.ti.gravis.gui.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

import ch.bfh.ti.gravis.gui.component.MessagePanel;

/**
 * A message dialog adapter.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MessageDialogAdapter extends AbstractDialogAdapter {

	private MessagePanel messagePanel;
	
	/**
	 * @param parent
	 * 
	 */
	public MessageDialogAdapter(Component parent) {
		super(parent);
		this.messagePanel = new MessagePanel();
	}

	public MessageDialogAdapter() {
		this(null);
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
	
	/**
	 * 
	 * @param shortMessage
	 * @param detailMessage
	 * @param title
	 */
	public void showErrorMessageDialog(String shortMessage, String detailMessage,
			String title) {
		
		this.messagePanel.setMessage(shortMessage, detailMessage);
		JOptionPane.showMessageDialog(this.parent, this.messagePanel, title,
				JOptionPane.ERROR_MESSAGE);
	}
	
}
