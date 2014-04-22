package ch.bfh.ti.gravis.gui.component;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;

import ch.bfh.ti.gravis.gui.model.ProtocolModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ProtocolPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 4198819175596919745L;

	private static final int ROW_NUMBER = 12;
	
	private JTextArea textArea;

	/**
	 * Create the panel.
	 */
	public ProtocolPanel() {
		this.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);

		this.textArea = new JTextArea();
		this.textArea.setDropMode(DropMode.INSERT);
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setEditable(false);
		this.textArea.setRows(ROW_NUMBER);
		scrollPane.setViewportView(this.textArea);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof ProtocolModel) {
			ProtocolModel model = (ProtocolModel) arg;
			
			if (model.isProtocolCleared()) {
				this.textArea.setText(model.getMessage());
			} else {
				this.textArea.append(model.getMessage());
			}			
		}
	}

}
