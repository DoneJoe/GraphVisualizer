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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.bfh.ti.gravis.core.graph.item.edge.IEdge;
import ch.bfh.ti.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.ti.gravis.core.util.ValueTransformer;
import ch.bfh.ti.gravis.gui.verifier.GraphItemNameVerifier;
import ch.bfh.ti.gravis.gui.verifier.VertexSizeVerifier;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexPropertyDialog extends JDialog {

	private static final long serialVersionUID = -6919635847499019908L;

	private static final int BORDER = 5;
	private static final int TEXT_COLS = 17;

	private static final String TITLE = "Knoten %s bearbeiten...";
	private static final String VERTEX_NAME_LABEL = "Knoten-Name: ";
	private static final String WIDTH_LABEL = "Breite: ";
	private static final String HEIGHT_LABEL = "Höhe: ";
	private final static String OK = "OK";
	private final static String CANCEL = "Abbrechen";

	private final JTextField txtVertexName;

	private final JTextField txtWidth;

	private final JTextField txtHeight;

	/**
	 * Create the dialog.
	 * 
	 * @param vertex
	 * @param owner
	 * @param vViewer
	 */
	public VertexPropertyDialog(final IVertex vertex, final JFrame owner,
			final VisualizationViewer<IVertex, IEdge> vViewer) {

		super(owner, true);

		// creates formatter and verifiers:

		DecimalFormat sizeFormat = new DecimalFormat();
		sizeFormat.setMinimumFractionDigits(0);
		sizeFormat.setMaximumFractionDigits(2);

		InputVerifier itemNameVerifier = new GraphItemNameVerifier(vertex.getName(),
				vViewer.getGraphLayout().getGraph());
		InputVerifier vertexHeightVerifier = new VertexSizeVerifier(
				sizeFormat.format(vertex.getHeight()));
		InputVerifier vertexWidthVerifier = new VertexSizeVerifier(
				sizeFormat.format(vertex.getWidth()));

		// creates panels:

		JPanel fieldPanel = new JPanel();
		fieldPanel.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		fieldPanel.setLayout(new BorderLayout(BORDER, BORDER));

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(3, 1, BORDER, BORDER));
		fieldPanel.add(labelPanel, BorderLayout.WEST);

		JPanel textFieldPanel = new JPanel();
		textFieldPanel.setLayout(new GridLayout(3, 1, BORDER, BORDER));
		fieldPanel.add(textFieldPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		this.getContentPane().setLayout(new BorderLayout(BORDER, BORDER));
		this.getContentPane().add(fieldPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		// creates text fields:

		JLabel lblVertexName = new JLabel(VERTEX_NAME_LABEL);
		labelPanel.add(lblVertexName);

		this.txtVertexName = new JTextField();
		this.txtVertexName.setColumns(TEXT_COLS);
		textFieldPanel.add(this.txtVertexName);

		JLabel lblWidth = new JLabel(WIDTH_LABEL);
		labelPanel.add(lblWidth);

		this.txtWidth = new JTextField();
		this.txtWidth.setColumns(TEXT_COLS);
		textFieldPanel.add(this.txtWidth);

		JLabel lblHeight = new JLabel(HEIGHT_LABEL);
		labelPanel.add(lblHeight);

		this.txtHeight = new JTextField();
		this.txtHeight.setColumns(TEXT_COLS);
		textFieldPanel.add(this.txtHeight);

		// creates cancel action and buttons:

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

		// sets text field values:

		this.txtVertexName.setInputVerifier(itemNameVerifier);
		this.txtWidth.setInputVerifier(vertexWidthVerifier);
		this.txtHeight.setInputVerifier(vertexHeightVerifier);

		this.txtVertexName.setText(vertex.getName());
		this.txtWidth.setText(sizeFormat.format(vertex.getWidth()));
		this.txtHeight.setText(sizeFormat.format(vertex.getHeight()));

		// adds listeners:

		DocumentListener docListener = this.createVertexDocumentListener(
				okButton, itemNameVerifier, vertexWidthVerifier);

		this.txtVertexName.getDocument().addDocumentListener(docListener);
		this.txtHeight.getDocument().addDocumentListener(docListener);
		this.txtWidth.getDocument().addDocumentListener(docListener);
		okButton.addActionListener(this.createOKActionListener(vertex, vViewer,
				itemNameVerifier, vertexHeightVerifier, vertexWidthVerifier));

		// prepares dialog:

		this.setTitle(String.format(TITLE, vertex.getName()));
		this.getRootPane().setDefaultButton(okButton);
		this.setResizable(false);
		this.pack();
	}

	/**
	 * @param okButton
	 * @param itemNameVerifier
	 * @param vertexSizeVerifier
	 * @return DocumentListener
	 */
	private DocumentListener createVertexDocumentListener(
			final JButton okButton, final InputVerifier itemNameVerifier,
			final InputVerifier vertexSizeVerifier) {

		return new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				// nothing to do
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				okButton.setEnabled(itemNameVerifier
						.verify(VertexPropertyDialog.this.txtVertexName)
						&& vertexSizeVerifier
								.verify(VertexPropertyDialog.this.txtWidth)
						&& vertexSizeVerifier
								.verify(VertexPropertyDialog.this.txtHeight));
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				okButton.setEnabled(itemNameVerifier
						.verify(VertexPropertyDialog.this.txtVertexName)
						&& vertexSizeVerifier
								.verify(VertexPropertyDialog.this.txtWidth)
						&& vertexSizeVerifier
								.verify(VertexPropertyDialog.this.txtHeight));
			}
		};
	}

	/**
	 * @param vertex
	 * @param vViewer
	 * @param itemNameVerifier
	 * @param vertexHeightVerifier
	 * @param vertexWidthVerifier
	 * @return ActionListener
	 */
	private ActionListener createOKActionListener(final IVertex vertex,
			final VisualizationViewer<IVertex, IEdge> vViewer,
			final InputVerifier itemNameVerifier,
			final InputVerifier vertexHeightVerifier,
			final InputVerifier vertexWidthVerifier) {

		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemNameVerifier
						.verify(VertexPropertyDialog.this.txtVertexName)
						&& vertexHeightVerifier
								.verify(VertexPropertyDialog.this.txtHeight)
						&& vertexWidthVerifier
								.verify(VertexPropertyDialog.this.txtWidth)) {

					vertex.setName(VertexPropertyDialog.this.txtVertexName
							.getText().trim());
					vertex.setWidth(ValueTransformer
							.toDouble(VertexPropertyDialog.this.txtWidth
									.getText().trim()));
					vertex.setHeight(ValueTransformer
							.toDouble(VertexPropertyDialog.this.txtHeight
									.getText().trim()));
					vViewer.repaint();
					VertexPropertyDialog.this.dispose();
				}
			}
		};
	}

}
