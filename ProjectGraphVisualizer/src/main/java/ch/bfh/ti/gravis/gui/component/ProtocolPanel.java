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
import java.awt.GridLayout;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ProtocolPanel extends JPanel implements DocumentListener {

	private static final long serialVersionUID = 4198819175596919745L;

	private static final String GRAPH_BORDER = "Beschreibung Graph";
	private static final String ALGO_BORDER = "Beschreibung Algorithmus";
	private static final String PROTOCOL_BORDER = "Protokoll";

	private static final int ROW_NUMBER = 8;

	private final JTextArea protocol, graphDescription, algorithmDescription;

	/**
	 * 
	 * @param model
	 */
	public ProtocolPanel(IAppModel model) {
		// creates panels:

		JPanel graphAlgoPanel = new JPanel();
		JPanel graphPanel = new JPanel();
		JPanel algoPanel = new JPanel();
		JPanel nestedProtocolPanel = new JPanel();
		JScrollPane graphScrollPane = new JScrollPane();
		JScrollPane algoScrollPane = new JScrollPane();
		JScrollPane protocolScrollPane = new JScrollPane();

		// sets layouts:

		this.setLayout(new GridLayout(0, 2, 0, 0));
		graphAlgoPanel.setLayout(new GridLayout(0, 2));
		graphPanel.setLayout(new BorderLayout());
		algoPanel.setLayout(new BorderLayout());
		nestedProtocolPanel.setLayout(new BorderLayout());

		// creates text areas:

		this.protocol = new JTextArea(model.getProtocolDocument());
		this.protocol.setBackground(GravisColor.WHITE_GRAY);
		this.protocol.setDropMode(DropMode.INSERT);
		this.protocol.setLineWrap(true);
		this.protocol.setWrapStyleWord(true);
		this.protocol.setEditable(false);
		this.protocol.setRows(ROW_NUMBER);

		this.graphDescription = new JTextArea(model.getGraphDocument());
		this.graphDescription.setBackground(GravisColor.WHITE_GRAY);
		this.graphDescription.setDropMode(DropMode.INSERT);
		this.graphDescription.setLineWrap(true);
		this.graphDescription.setWrapStyleWord(true);
		this.graphDescription.setEditable(false);
		this.graphDescription.setRows(ROW_NUMBER);

		this.algorithmDescription = new JTextArea(model.getAlgorithmDocument());
		this.algorithmDescription.setBackground(GravisColor.WHITE_GRAY);
		this.algorithmDescription.setDropMode(DropMode.INSERT);
		this.algorithmDescription.setLineWrap(true);
		this.algorithmDescription.setWrapStyleWord(true);
		this.algorithmDescription.setEditable(false);
		this.algorithmDescription.setRows(ROW_NUMBER);

		// adds components and panels:

		graphScrollPane.setViewportView(this.graphDescription);
		algoScrollPane.setViewportView(this.algorithmDescription);
		protocolScrollPane.setViewportView(this.protocol);

		graphPanel.add(graphScrollPane, BorderLayout.CENTER);
		algoPanel.add(algoScrollPane, BorderLayout.CENTER);
		nestedProtocolPanel.add(protocolScrollPane, BorderLayout.CENTER);

		graphAlgoPanel.add(graphPanel);
		graphAlgoPanel.add(algoPanel);
		this.add(graphAlgoPanel);
		this.add(nestedProtocolPanel);

		// sets border labels:

		graphPanel.setBorder(BorderFactory.createTitledBorder(GRAPH_BORDER));
		algoPanel.setBorder(BorderFactory.createTitledBorder(ALGO_BORDER));
		nestedProtocolPanel.setBorder(BorderFactory
				.createTitledBorder(PROTOCOL_BORDER));

		// sets panel background colors:

		this.setBackground(GravisColor.ANTIQUE);
		graphAlgoPanel.setBackground(GravisColor.ANTIQUE);
		graphPanel.setBackground(GravisColor.ANTIQUE);
		algoPanel.setBackground(GravisColor.ANTIQUE);
		nestedProtocolPanel.setBackground(GravisColor.ANTIQUE);

		// sets scroll panes

		graphScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		algoScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		protocolScrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		// adds listeners:

		this.graphDescription.getDocument().addDocumentListener(this);
		this.algorithmDescription.getDocument().addDocumentListener(this);
		this.protocol.getDocument().addDocumentListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void changedUpdate(DocumentEvent arg) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void insertUpdate(DocumentEvent arg) {
		this.updateTextAreas(arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.
	 * DocumentEvent)
	 */
	@Override
	public void removeUpdate(DocumentEvent arg) {
		this.updateTextAreas(arg);
	}

	/**
	 * @param arg
	 */
	private void updateTextAreas(final DocumentEvent arg) {
		this.graphDescription.setCaretPosition(this.graphDescription
				.getDocument().getLength());
		this.algorithmDescription.setCaretPosition(this.algorithmDescription
				.getDocument().getLength());
		this.protocol.setCaretPosition(this.protocol.getDocument().getLength());
	}

}
