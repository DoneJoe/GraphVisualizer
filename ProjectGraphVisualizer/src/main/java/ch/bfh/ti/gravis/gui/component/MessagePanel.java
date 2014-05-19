package ch.bfh.ti.gravis.gui.component;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 *
 */
public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1400113914507976962L;

	private static final int BORDER = 20;
	private static final int COLS = 50;
	private static final int ROWS = 20;
	
	private JLabel shortMessageLbl;
	
	private JTextArea messageTextArea;
	
	public MessagePanel() {
		this("", "");
	}
	
	/**
	 * 
	 * @param shortMessage
	 * @param detailMessage
	 */
	public MessagePanel(final String shortMessage, final String detailMessage) {
		this.setLayout(new BorderLayout(BORDER, BORDER));
		
		this.shortMessageLbl = new JLabel(shortMessage);
		this.add(this.shortMessageLbl, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);
		
		this.messageTextArea = new JTextArea();
		this.messageTextArea.setText(detailMessage);
		this.messageTextArea.setEditable(false);
		this.messageTextArea.setRows(ROWS);
		this.messageTextArea.setColumns(COLS);
		this.messageTextArea.setCaretPosition(0);
		scrollPane.setViewportView(this.messageTextArea);
	}

	/**
	 * @param shortMessage
	 * @param detailMessage
	 */
	public void setMessage(String shortMessage, String detailMessage) {
		this.shortMessageLbl.setText(shortMessage);
		this.messageTextArea.setText(detailMessage);
		this.messageTextArea.setCaretPosition(0);
	}

}
