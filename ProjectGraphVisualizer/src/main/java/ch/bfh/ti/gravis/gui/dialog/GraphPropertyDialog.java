package ch.bfh.ti.gravis.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.AbstractAction;
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
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class GraphPropertyDialog extends JDialog {

	private class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 434637644370980465L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent arg) {
			GraphPropertyDialog.this.dispose();
		}
	}

	private static final long serialVersionUID = -1767385966504542230L;

	private static final String TITLE = "%s bearbeiten...";
	private static final String GRAPH_NAME_LABEL = "Name: ";
	private static final String GRAPH_DESCR_LABEL = "Beschreibung: ";
	private static final String OK = "OK";
	private static final String CANCEL = "Abbrechen";

	private JTextField txtGraphName;

	private JTextArea graphDescription;

	/**
	 * Create the dialog.
	 * 
	 * @param owner
	 * @param graph
	 */
	public GraphPropertyDialog(final IGravisGraph graph, final JFrame owner) {
		super(owner, true);

		// creates panels:

		JPanel panelGraphName = new JPanel();
		panelGraphName.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelGraphName.setLayout(new BorderLayout());

		JPanel panelGraphDescription = new JPanel();
		panelGraphDescription.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelGraphDescription.setLayout(new BorderLayout());

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(panelGraphName, BorderLayout.NORTH);
		this.getContentPane().add(panelGraphDescription, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// creates buttons:

		JButton okButton = new JButton(OK);
		okButton.addActionListener(this.createOKActionListener(graph));
		buttonPanel.add(okButton);

		JButton cancelButton = new JButton();
		Action cancelAction = new CancelAction();
		cancelButton.setAction(cancelAction);
		cancelButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CANCEL);
		cancelButton.getInputMap(JComponent.WHEN_FOCUSED).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), CANCEL);
		cancelButton.getActionMap().put(CANCEL, cancelAction);
		cancelButton.setText(CANCEL);
		buttonPanel.add(cancelButton);

		// creates graph name text field:

		JLabel lblGraphName = new JLabel(GRAPH_NAME_LABEL);
		panelGraphName.add(lblGraphName, BorderLayout.WEST);

		this.txtGraphName = new JTextField();
		GraphNameVerifier verifier = new GraphNameVerifier(
				graph.getName());
		this.txtGraphName.getDocument().addDocumentListener(
				this.createGraphNameDocumentListener(okButton, verifier));
		panelGraphName.add(this.txtGraphName, BorderLayout.CENTER);

		// creates graph description text area:

		JLabel lblGraphDescription = new JLabel(GRAPH_DESCR_LABEL);
		panelGraphDescription.add(lblGraphDescription, BorderLayout.NORTH);

		this.graphDescription = new JTextArea();
		JScrollPane areaScrollPane = new JScrollPane(this.graphDescription);
		areaScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.graphDescription.setLineWrap(true);
		this.graphDescription.setWrapStyleWord(true);
		panelGraphDescription.add(areaScrollPane, BorderLayout.CENTER);

		// prepares dialog:

		this.setTitle(String.format(TITLE, graph.getName()));
		this.setResizable(false);
		this.setBounds(100, 100, 500, 300);
		this.getRootPane().setDefaultButton(okButton);
		this.setTextFieldValues(graph, verifier);
		this.centerDialog();
	}

	/**
	 * @param okButton
	 * @param verifier 
	 * @return DocumentListener
	 */
	private DocumentListener createGraphNameDocumentListener(final JButton okButton, 
			final GraphNameVerifier verifier) {	
		
		return new DocumentListener() {			
			@Override
			public void removeUpdate(DocumentEvent arg) {
					okButton.setEnabled(verifier.verify(GraphPropertyDialog.this.txtGraphName));
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg) {
				okButton.setEnabled(verifier.verify(GraphPropertyDialog.this.txtGraphName));
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
	 * @return Ok ActionListener
	 */
	private ActionListener createOKActionListener(final IGravisGraph graph) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphPropertyDialog.this.updateTextFieldValues(graph);
			}
		};
	}

	/**
	 * @param graph
	 */
	private void updateTextFieldValues(final IGravisGraph graph) {
		// TODO mit verifier ueberpruefen!
		
		if (!this.txtGraphName.getText().trim().isEmpty()) {
			graph.setName(this.txtGraphName.getText().trim());
			graph.setDescription(this.graphDescription.getText().trim());
			this.dispose();
		}
	}

	/**
	 * 
	 * @param graph
	 * @param verifier
	 */
	private void setTextFieldValues(final IGravisGraph graph, final GraphNameVerifier verifier) {
		this.txtGraphName.setText(graph.getName());
		this.graphDescription.setText(graph.getDescription());
		this.txtGraphName.setInputVerifier(verifier);
	}

	private void centerDialog() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - this.getWidth()) / 2;
		final int y = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(x, y);
	}

}
