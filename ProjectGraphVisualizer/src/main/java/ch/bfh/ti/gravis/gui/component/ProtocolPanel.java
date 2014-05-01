package ch.bfh.ti.gravis.gui.component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.DropMode;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.bfh.ti.gravis.core.util.GravisColor;
import ch.bfh.ti.gravis.gui.model.IAppModel;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ProtocolPanel extends JPanel implements DocumentListener {

	private static final long serialVersionUID = 4198819175596919745L;

	private static final String BORDER_LABEL = "Protokoll";
	
	private static final int ROW_NUMBER = 8;
	
	private JTextArea protocol;

	/**
	 * 
	 * @param model
	 */
	public ProtocolPanel(IAppModel model) {
		this.setLayout(new BorderLayout(0, 0));
		this.setBorder(BorderFactory.createTitledBorder(BORDER_LABEL));
		this.setBackground(GravisColor.ANTIQUE);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);

		this.protocol = new JTextArea(model.getProtocolDocument());
		this.protocol.setBackground(GravisColor.WHITE_GRAY);
		this.protocol.setDropMode(DropMode.INSERT);
		this.protocol.setLineWrap(true);
		this.protocol.setWrapStyleWord(true);
		this.protocol.setEditable(false);
		this.protocol.setRows(ROW_NUMBER);		
		scrollPane.setViewportView(this.protocol);
		
		model.getProtocolDocument().addDocumentListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent arg) {
		// nothing to do
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent arg) {
		this.protocol.setCaretPosition(this.protocol.getDocument().getLength());
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent arg) {
		this.protocol.setCaretPosition(this.protocol.getDocument().getLength());
	}

}
