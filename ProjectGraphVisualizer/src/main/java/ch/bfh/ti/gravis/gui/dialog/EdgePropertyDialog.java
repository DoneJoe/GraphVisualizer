package ch.bfh.ti.gravis.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.ValueTransformer;
import ch.bfh.ti.gravis.gui.controller.ErrorHandler;
import ch.bfh.ti.gravis.gui.verifier.EdgeWeightVerifier;
import ch.bfh.ti.gravis.gui.verifier.GraphItemNameVerifier;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 * An edge property dialog.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class EdgePropertyDialog extends JDialog {

	private static final long serialVersionUID = -6646549637907283799L;

	private static final int BORDER = 5;
	private static final int TEXT_COLS = 16;

	private final static String TITLE = "Kante %s bearbeiten...";
	private final static String EDGE_NAME_LABEL = "Kanten-Name: ";
	private final static String EDGE_WEIGHT_LABEL = "Gewicht: ";
	private final static String OK = "OK";
	private final static String CANCEL = "Abbrechen";

	private final ErrorHandler errHandler;

	/**
	 * Creates the dialog.
	 * 
	 * @param vViewer
	 * @param owner
	 * @param edge
	 */
	public EdgePropertyDialog(final IEdge edge, final JFrame owner,
			final VisualizationViewer<IVertex, IEdge> vViewer) {

		super(owner, true);
		this.errHandler = new ErrorHandler(owner);

		// create formatter and verifiers:

		DecimalFormat weightFormat = new DecimalFormat();
		weightFormat.setMinimumFractionDigits(0);
		weightFormat.setMaximumFractionDigits(2);

		InputVerifier edgeWeightVerifier = new EdgeWeightVerifier(
				weightFormat.format(edge.getWeight()));
		InputVerifier itemNameVerifier = new GraphItemNameVerifier(
				edge.getName(), vViewer.getGraphLayout().getGraph());

		// create panels:

		JPanel fieldPanel = new JPanel();
		fieldPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		fieldPanel.setLayout(new BorderLayout(BORDER, BORDER));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(2, 1, BORDER, BORDER));
		fieldPanel.add(labelPanel, BorderLayout.WEST);

		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new GridLayout(2, 1, BORDER, BORDER));
		fieldPanel.add(textFieldPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.getContentPane().setLayout(new BorderLayout(BORDER, BORDER));
		this.getContentPane().add(fieldPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// create text fields:

		JLabel lblEdgeName = new JLabel(EDGE_NAME_LABEL);
		labelPanel.add(lblEdgeName);

		JTextField txtEdgeName = new JTextField();
		txtEdgeName.setColumns(TEXT_COLS);
		textFieldPanel.add(txtEdgeName);

		JLabel lblEdgeWeight = new JLabel(EDGE_WEIGHT_LABEL);
		labelPanel.add(lblEdgeWeight);

		JTextField txtEdgeWeight = new JTextField();
		txtEdgeWeight.setColumns(TEXT_COLS);
		textFieldPanel.add(txtEdgeWeight);

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

		txtEdgeName.setText(edge.getName());
		txtEdgeName.setInputVerifier(itemNameVerifier);
		txtEdgeWeight.setText(weightFormat.format(edge.getWeight()));
		txtEdgeWeight.setInputVerifier(edgeWeightVerifier);

		// add listeners:

		DocumentListener docListener = this.createEdgeDocumentListener(
				okButton, txtEdgeName, txtEdgeWeight, itemNameVerifier,
				edgeWeightVerifier);

		txtEdgeName.getDocument().addDocumentListener(docListener);
		txtEdgeWeight.getDocument().addDocumentListener(docListener);
		okButton.addActionListener(this.createOKActionListener(edge, vViewer,
				txtEdgeName, txtEdgeWeight, itemNameVerifier,
				edgeWeightVerifier));

		// prepare dialog:

		this.setTitle(String.format(TITLE, edge.getName()));
		this.getRootPane().setDefaultButton(okButton);
		this.setResizable(false);
		this.pack();
	}

	/**
	 * 
	 * @param okButton
	 * @param txtEdgeName
	 * @param txtEdgeWeight
	 * @param itemNameVerifier
	 * @param edgeWeightVerifier
	 * @return DocumentListener
	 */
	private DocumentListener createEdgeDocumentListener(final JButton okButton,
			final JTextField txtEdgeName, final JTextField txtEdgeWeight,
			final InputVerifier itemNameVerifier,
			final InputVerifier edgeWeightVerifier) {

		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				// nothing to do
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				try {
					okButton.setEnabled(edgeWeightVerifier
							.verify(txtEdgeWeight)
							&& itemNameVerifier.verify(txtEdgeName));
				} catch (Throwable ex) {
					errHandler.handleAppErrorExit(ex);
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				try {
					okButton.setEnabled(edgeWeightVerifier
							.verify(txtEdgeWeight)
							&& itemNameVerifier.verify(txtEdgeName));
				} catch (Throwable ex) {
					errHandler.handleAppErrorExit(ex);
				}
			}
		};
	}

	/**
	 * 
	 * @param edge
	 * @param vViewer
	 * @param txtEdgeName
	 * @param txtEdgeWeight
	 * @param itemNameVerifier
	 * @param edgeWeightVerifier
	 * @return ActionListener
	 */
	private ActionListener createOKActionListener(final IEdge edge,
			final VisualizationViewer<IVertex, IEdge> vViewer,
			final JTextField txtEdgeName, final JTextField txtEdgeWeight,
			final InputVerifier itemNameVerifier,
			final InputVerifier edgeWeightVerifier) {

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (edgeWeightVerifier.verify(txtEdgeWeight)
							&& itemNameVerifier.verify(txtEdgeName)) {

						edge.setName(txtEdgeName.getText().trim());
						edge.setWeight(ValueTransformer.toDouble(txtEdgeWeight
								.getText().trim()));
						vViewer.repaint();
						EdgePropertyDialog.this.dispose();
					}
				} catch (Throwable ex) {
					errHandler.handleAppErrorExit(ex);
				}
			}
		};
	}

}
