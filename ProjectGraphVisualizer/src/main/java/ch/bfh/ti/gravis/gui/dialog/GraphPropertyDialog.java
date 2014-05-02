package ch.bfh.ti.gravis.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	private static final long serialVersionUID = -1767385966504542230L;

	private static final String TITLE = "%s bearbeiten...";
	private static final String GRAPH_NAME_LABEL = "Name des Graphen:";
	private static final String GRAPH_DESCR_LABEL = "Beschreibung zum Graphen:";
	private final static String OK = "OK";
	private final static String CANCEL = "Cancel";

	private JTextField txtGraphName;

	private JTextArea textAreaGraphDescription;

	/**
	 * Create the dialog.
	 * 
	 * @param owner
	 * @param graph
	 */
	public GraphPropertyDialog(final IGravisGraph graph, final JFrame owner) {
		super(owner, true);

		this.setTitle(String.format(TITLE, graph.getName()));

		JPanel contentPanel = new JPanel();
		this.setResizable(false);
		this.setBounds(100, 100, 500, 220);
		this.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panelGraphName = new JPanel();
		contentPanel.add(panelGraphName, BorderLayout.NORTH);
		panelGraphName.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblGraphName = new JLabel(GRAPH_NAME_LABEL);
		panelGraphName.add(lblGraphName);

		this.txtGraphName = new JTextField();
		panelGraphName.add(this.txtGraphName);
		this.txtGraphName.setColumns(10);

		JPanel panelGraphDescription = new JPanel();
		contentPanel.add(panelGraphDescription, BorderLayout.CENTER);
		panelGraphDescription.setLayout(new BorderLayout(0, 0));

		JLabel lblGraphDescription = new JLabel(GRAPH_DESCR_LABEL);
		panelGraphDescription.add(lblGraphDescription, BorderLayout.NORTH);

		this.textAreaGraphDescription = new JTextArea();
		this.textAreaGraphDescription.setLineWrap(true);
		this.textAreaGraphDescription.setWrapStyleWord(true);
		panelGraphDescription.add(this.textAreaGraphDescription,
				BorderLayout.CENTER);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton(OK);
		okButton.setActionCommand(OK);
		buttonPane.add(okButton);

		JButton cancelButton = new JButton(CANCEL);
		cancelButton.setActionCommand(CANCEL);
		buttonPane.add(cancelButton);

		this.getRootPane().setDefaultButton(okButton);
		this.setTextFieldValues(graph);
		this.setListeners(graph, okButton, cancelButton);
		this.centerDialog();
	}

	/**
	 * @param graph
	 * @param okButton
	 * @param cancelButton
	 */
	private void setListeners(final IGravisGraph graph, final JButton okButton,
			final JButton cancelButton) {

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphPropertyDialog.this.updateTextFieldValues(graph);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphPropertyDialog.this.dispose();
			}
		});
	}

	/**
	 * @param graph
	 */
	private void updateTextFieldValues(final IGravisGraph graph) {
		graph.setName(this.txtGraphName.getText().trim());
		graph.setDescription(this.textAreaGraphDescription.getText().trim());
		this.dispose();
	}

	/**
	 * @param graph
	 */
	private void setTextFieldValues(final IGravisGraph graph) {
		this.txtGraphName.setText(graph.getName());
		this.textAreaGraphDescription.setText(graph.getDescription());

		this.txtGraphName.setInputVerifier(new GraphNameVerifier(
				this.txtGraphName.getText().trim()));
	}

	private void centerDialog() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		final Dimension screenSize = toolkit.getScreenSize();
		final int x = (screenSize.width - this.getWidth()) / 2;
		final int y = (screenSize.height - this.getHeight()) / 2;
		this.setLocation(x, y);
	}

}
