package ch.bfh.ti.gravis.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import ch.bfh.ti.gravis.core.graph.IGravisGraph;
import ch.bfh.ti.gravis.gui.verifier.GraphNameVerifier;

/**
 * A graph property dialog.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphPropertyDialog extends JDialog {

	private static final long serialVersionUID = -1767385966504542230L;
	
	private static final int BORDER = 5;
	private static final int COLS = 40;
	private static final int ROWS = 12;

	private static final String TITLE = "%s bearbeiten...";
	private static final String GRAPH_NAME_LABEL = "Name: ";
	private static final String GRAPH_DESCR_LABEL = "Beschreibung: ";
	private static final String OK = "OK";
	private static final String CANCEL = "Abbrechen";

	/**
	 * Creates the dialog.
	 * 
	 * @param owner
	 * @param graph
	 */
	public GraphPropertyDialog(final IGravisGraph graph, final JFrame owner) {
		super(owner, true);

		// create verifier and panels:

		GraphNameVerifier verifier = new GraphNameVerifier(graph.getName());

		JPanel panelGraphName = new JPanel();
		panelGraphName.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		panelGraphName.setLayout(new BorderLayout());

		JPanel panelGraphDescription = new JPanel();
		panelGraphDescription.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		panelGraphDescription.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.getContentPane().setLayout(new BorderLayout(BORDER, BORDER));
		this.getContentPane().add(panelGraphName, BorderLayout.NORTH);
		this.getContentPane().add(panelGraphDescription, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// create graph name text field:

		JLabel lblGraphName = new JLabel(GRAPH_NAME_LABEL);
		panelGraphName.add(lblGraphName, BorderLayout.WEST);

		JTextField txtGraphName = new JTextField();
		panelGraphName.add(txtGraphName, BorderLayout.CENTER);

		// create graph description text area:

		JLabel lblGraphDescription = new JLabel(GRAPH_DESCR_LABEL);
		panelGraphDescription.add(lblGraphDescription, BorderLayout.NORTH);

		JTextArea graphDescription = new JTextArea();
		graphDescription.setLineWrap(true);
		graphDescription.setWrapStyleWord(true);
		graphDescription.setColumns(COLS);
		graphDescription.setRows(ROWS);
		
		JScrollPane areaScrollPane = new JScrollPane(graphDescription);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelGraphDescription.add(areaScrollPane, BorderLayout.CENTER);

		// create cancel action and buttons:

		Action cancelAction = new CancelDialogAction(this);

		JButton okButton = new JButton(OK);
		buttonPanel.add(okButton);

		JButton cancelButton = new JButton();
		cancelButton.setAction(cancelAction);
		cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CANCEL);
		cancelButton.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), CANCEL);
		cancelButton.getActionMap().put(CANCEL, cancelAction);
		cancelButton.setText(CANCEL);
		buttonPanel.add(cancelButton);

		// set text field values:
		
		txtGraphName.setText(graph.getName());
		txtGraphName.setInputVerifier(verifier);
		graphDescription.setText(graph.getDescription());
		
		// add listeners:

		txtGraphName.getDocument().addDocumentListener(
				this.createGraphNameDocumentListener(okButton, verifier,
						txtGraphName));
		okButton.addActionListener(this.createOKActionListener(graph, verifier,
				txtGraphName, graphDescription));

		// prepare dialog:

		this.setTitle(String.format(TITLE, graph.getName()));
		this.setResizable(false);
		this.getRootPane().setDefaultButton(okButton);
		this.pack();
		this.centerDialog();
	}

	/**
	 * @param okButton
	 * @param verifier
	 * @param txtGraphName
	 * @return DocumentListener
	 */
	private DocumentListener createGraphNameDocumentListener(
			final JButton okButton, final GraphNameVerifier verifier,
			final JTextField txtGraphName) {

		return new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent arg) {
				okButton.setEnabled(verifier.verify(txtGraphName));
			}

			@Override
			public void insertUpdate(DocumentEvent arg) {
				okButton.setEnabled(verifier.verify(txtGraphName));
			}

			@Override
			public void changedUpdate(DocumentEvent arg) {
				// nothing to do
			}
		};
	}

	/**
	 * 
	 * @param graph
	 * @param verifier
	 * @param txtGraphName
	 * @param graphDescription
	 * @return ActionListener
	 */
	private ActionListener createOKActionListener(final IGravisGraph graph,
			final GraphNameVerifier verifier, final JTextField txtGraphName,
			final JTextArea graphDescription) {

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (verifier.verify(txtGraphName)) {
					graph.setName(txtGraphName.getText().trim());
					graph.setDescription(graphDescription.getText().trim());
					GraphPropertyDialog.this.dispose();
				}
			}
		};
	}

	private void centerDialog() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width - this.getWidth()) / 2;
		int y = (screenSize.height - this.getHeight()) / 2;
		
		this.setLocation(x, y);
	}

}
