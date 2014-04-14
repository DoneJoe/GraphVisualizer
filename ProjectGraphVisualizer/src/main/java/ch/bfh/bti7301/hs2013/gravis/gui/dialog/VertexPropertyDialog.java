package ch.bfh.bti7301.hs2013.gravis.gui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ch.bfh.bti7301.hs2013.gravis.core.graph.item.edge.IEdge;
import ch.bfh.bti7301.hs2013.gravis.core.graph.item.vertex.IVertex;
import ch.bfh.bti7301.hs2013.gravis.core.util.ValueTransformer;
import ch.bfh.bti7301.hs2013.gravis.gui.verifier.GraphItemIdVerifier;
import ch.bfh.bti7301.hs2013.gravis.gui.verifier.VertexSizeVerifier;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class VertexPropertyDialog extends JDialog {

	private static final long serialVersionUID = -6919635847499019908L;

	private static final String TITLE = "Knoten %s bearbeiten...";
	private static final String VERTEX_NAME_LABEL = "Knoten-Name:              ";
	private static final String WIDTH_LABEL = "Breite:";
	private static final String HEIGHT_LABEL = "Höhe:";
	private final static String OK = "OK";
	private final static String CANCEL = "Cancel";
	
	private JTextField txtVertexName;

	private JTextField txtWidth;

	private JTextField txtHeight;

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

		this.setTitle(String.format(TITLE, vertex.getId()));

		JPanel contentPanel = new JPanel();
		this.setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblVertexName = new JLabel(VERTEX_NAME_LABEL);
		contentPanel.add(lblVertexName);
		this.txtVertexName = new JTextField();
		contentPanel.add(this.txtVertexName);
		JLabel lblWidth = new JLabel(WIDTH_LABEL);
		contentPanel.add(lblWidth);
		this.txtWidth = new JTextField();
		contentPanel.add(this.txtWidth);
		JLabel lblHeight = new JLabel(HEIGHT_LABEL);
		contentPanel.add(lblHeight);
		this.txtHeight = new JTextField();
		contentPanel.add(this.txtHeight);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton(OK);
		okButton.setActionCommand(OK);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton(CANCEL);
		cancelButton.setActionCommand(CANCEL);
		buttonPane.add(cancelButton);

		this.setTextFieldValues(vertex, vViewer);
		this.setListeners(vertex, vViewer, okButton, cancelButton);

		this.pack();
	}

	/**
	 * @param vertex
	 * @param vViewer
	 * @param okButton
	 * @param cancelButton
	 */
	private void setListeners(final IVertex vertex,
			final VisualizationViewer<IVertex, IEdge> vViewer,
			JButton okButton, JButton cancelButton) {

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VertexPropertyDialog.this
						.updateTextFieldValues(vertex, vViewer);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VertexPropertyDialog.this.dispose();
			}
		});
	}

	/**
	 * @param vertex
	 * @param vViewer
	 */
	private void updateTextFieldValues(final IVertex vertex,
			final VisualizationViewer<IVertex, IEdge> vViewer) {
		
		vertex.setId(this.txtVertexName.getText().trim());
		vertex.setWidth(ValueTransformer.transformDouble(this.txtWidth.getText().trim()));
		vertex.setHeight(ValueTransformer.transformDouble(this.txtHeight.getText().trim()));
		vViewer.repaint();
		this.dispose();
	}

	/**
	 * @param vertex
	 * @param vViewer
	 */
	private void setTextFieldValues(IVertex vertex,
			VisualizationViewer<IVertex, IEdge> vViewer) {

		this.txtVertexName.setText(vertex.getId());
		this.txtWidth.setText(String.valueOf(new Double(vertex.getWidth())
				.intValue()));
		this.txtHeight.setText(String.valueOf(new Double(vertex.getHeight())
				.intValue()));

		this.txtVertexName.setInputVerifier(new GraphItemIdVerifier(
				this.txtVertexName.getText().trim(), vertex, vViewer
						.getGraphLayout().getGraph()));
		this.txtWidth.setInputVerifier(new VertexSizeVerifier(this.txtWidth
				.getText().trim()));
		this.txtHeight.setInputVerifier(new VertexSizeVerifier(this.txtHeight
				.getText().trim()));
	}

}
