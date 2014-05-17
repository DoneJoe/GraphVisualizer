package ch.bfh.ti.gravis.gui.component;

import java.awt.FlowLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JToolBar;
import javax.swing.JButton;

import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.ToggleComboGroup;
import ch.bfh.ti.gravis.gui.model.ToolBarModel;
import static ch.bfh.ti.gravis.gui.GuiFactory.loadImage;
import static ch.bfh.ti.gravis.gui.controller.IMenuToolbarController.EventSource;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class ToolBarPanel extends JToolBar implements Observer {

	private static final long serialVersionUID = 2109148728694878673L;

	private static final String OPEN_ICON = "Open24.gif";
	private static final String OPEN_TOOLTIP = "Graph öffnen...";
	private static final String SAVE_ICON = "Save24.gif";
	private static final String SAVE_TOOLTIP = "Graph speichern";
	private static final String SAVE_AS_ICON = "SaveAs24.gif";
	private static final String SAVE_AS_TOOLTIP = "Graph speichern unter...";

	private static final String NEW_ICON = "New24.gif";
	private static final String NEW_DIR_LABEL = "G";
	private static final String NEW_DIR_TOOLTIP = "Neuer gerichteter Graph";
	private static final String NEW_UNDIR_LABEL = "U";
	private static final String NEW_UNDIR_TOOLTIP = "Neuer ungerichteter Graph";
	private static final String PROPERTIES_ICON = "GraphProperties_24.gif";
	private static final String PROPERTIES_TOOLTIP = "Graph Eigenschaften...";

	// private static final String EDIT_MODE_LABEL = "Modus: ";
	private static final String MODE_TOOLTIP = "Bearbeitungs-Modus wählen";
	private static final String PICKING_ICON = "Picking_24.png";
	private static final String PICKING_TOOLTIP = "Knoten auswählen und verschieben";
	private static final String EDITING_ICON = "Editing_24.png";
	private static final String EDITING_TOOLTIP = "Elemente hinzufügen, löschen oder bearbeiten";
	private static final String TRANSFORMING_ICON = "Transforming_24.png";
	private static final String TRANSFORMING_TOOLTIP = "Graph verschieben oder drehen";

	private static final String ALGO_TOOLTIP = "Algorithmus wählen";
	private static final String CALC_LABEL = "Neu berechnen";
	private static final String CALC_TOOLTIP = "Schritte neu berechnen";

	private final JComboBox<String> comboBoxAlgorithm;

	private final JButton btnNewCalculation;

	/**
	 * Create the panel.
	 * 
	 * @param model
	 * @param menuToolbarController
	 * @throws IOException
	 */
	public ToolBarPanel(final IMenuToolbarController menuToolbarController,
			final IAppModel model) throws IOException {

		ToggleComboGroup tglCmbGroup = model.getToggleComboGroup();

		// set layout:

		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEFT);
		this.setLayout(layout);
		this.setFloatable(false);

		// add buttons ( new, IO and property):

		JButton btnNewDirGraph = new JButton(NEW_DIR_LABEL);
		btnNewDirGraph.setIcon(new ImageIcon(loadImage(NEW_ICON)));
		btnNewDirGraph.setToolTipText(NEW_DIR_TOOLTIP);
		this.add(btnNewDirGraph);

		JButton btnNewUndirGraph = new JButton(NEW_UNDIR_LABEL);
		btnNewUndirGraph.setIcon(new ImageIcon(loadImage(NEW_ICON)));
		btnNewUndirGraph.setToolTipText(NEW_UNDIR_TOOLTIP);
		this.add(btnNewUndirGraph);

		JButton btnOpenGraph = new JButton();
		btnOpenGraph.setIcon(new ImageIcon(loadImage(OPEN_ICON)));
		btnOpenGraph.setToolTipText(OPEN_TOOLTIP);
		this.add(btnOpenGraph);

		JButton btnSaveGraph = new JButton();
		btnSaveGraph.setIcon(new ImageIcon(loadImage(SAVE_ICON)));
		btnSaveGraph.setToolTipText(SAVE_TOOLTIP);
		this.add(btnSaveGraph);

		JButton btnSaveGraphAs = new JButton();
		btnSaveGraphAs.setIcon(new ImageIcon(loadImage(SAVE_AS_ICON)));
		btnSaveGraphAs.setToolTipText(SAVE_AS_TOOLTIP);
		this.add(btnSaveGraphAs);

		JButton btnGraphProp = new JButton();
		btnGraphProp.setIcon(new ImageIcon(loadImage(PROPERTIES_ICON)));
		btnGraphProp.setToolTipText(PROPERTIES_TOOLTIP);
		this.add(btnGraphProp);

		this.addSeparator();

		// add edit mode toggle buttons and edit mode combo box:

		// JLabel lblEditMode = new JLabel(EDIT_MODE_LABEL);
		// this.add(lblEditMode);

		JToggleButton tglbtnPicking = tglCmbGroup.getModeToggleButton(Mode.PICKING);
		tglbtnPicking.setIcon(new ImageIcon(loadImage(PICKING_ICON)));
		tglbtnPicking.setToolTipText(PICKING_TOOLTIP);
		this.add(tglbtnPicking);

		JToggleButton tglbtnEditing = tglCmbGroup.getModeToggleButton(Mode.EDITING);
		tglbtnEditing.setIcon(new ImageIcon(loadImage(EDITING_ICON)));
		tglbtnEditing.setToolTipText(EDITING_TOOLTIP);
		this.add(tglbtnEditing);

		JToggleButton tglbtnTransforming = tglCmbGroup.getModeToggleButton(Mode.TRANSFORMING);
		tglbtnTransforming.setIcon(new ImageIcon(loadImage(TRANSFORMING_ICON)));
		tglbtnTransforming.setToolTipText(TRANSFORMING_TOOLTIP);
		this.add(tglbtnTransforming);

		JComboBox<?> comboBoxMode = tglCmbGroup.getModeComboBox();
		comboBoxMode.setToolTipText(MODE_TOOLTIP);
		this.add(comboBoxMode);

		this.addSeparator();

		// add algorithm combo box and new calc button:

		this.comboBoxAlgorithm = new JComboBox<>(model.getAlgorithmComboModel());
		this.comboBoxAlgorithm.setToolTipText(ALGO_TOOLTIP);
		this.add(this.comboBoxAlgorithm);

		this.btnNewCalculation = new JButton(CALC_LABEL);
		this.btnNewCalculation.setToolTipText(CALC_TOOLTIP);
		this.btnNewCalculation.setVisible(false);
		this.add(this.btnNewCalculation);

		// set other models:

		btnNewDirGraph.setModel(model.getNewDirGraphButtonModel());
		btnNewUndirGraph.setModel(model.getNewUndirGraphButtonModel());
		btnOpenGraph.setModel(model.getOpenGraphButtonModel());
		btnSaveGraph.setModel(model.getSaveGraphButtonModel());
		btnSaveGraphAs.setModel(model.getSaveGraphAsButtonModel());
		btnGraphProp.setModel(model.getGraphPropertiesButtonModel());
		this.btnNewCalculation.setModel(model.getNewCalcButtonModel());

		// add other listeners:

		comboBoxMode.setActionCommand(EventSource.MODE.toString());
		comboBoxMode.addItemListener(menuToolbarController);
		this.comboBoxAlgorithm.setActionCommand(EventSource.ALGORITHM
				.toString());
		this.comboBoxAlgorithm.addItemListener(menuToolbarController);
		this.btnNewCalculation
				.setActionCommand(EventSource.NEW_CALC.toString());
		this.btnNewCalculation.addActionListener(menuToolbarController);
		
		// disable toolbar component focus:
		
		btnNewDirGraph.setFocusable(false);
		btnNewUndirGraph.setFocusable(false);
		btnOpenGraph.setFocusable(false);
		btnSaveGraph.setFocusable(false);
		btnSaveGraphAs.setFocusable(false);
		btnGraphProp.setFocusable(false);
		tglbtnPicking.setFocusable(false);
		tglbtnEditing.setFocusable(false);
		tglbtnTransforming.setFocusable(false);		
		comboBoxMode.setFocusable(false);
		this.comboBoxAlgorithm.setFocusable(false);
		this.btnNewCalculation.setFocusable(false);
		this.setFocusable(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof ToolBarModel) {
			ToolBarModel model = (ToolBarModel) arg;

			this.comboBoxAlgorithm.setEnabled(model.isAlgoComboEnabled());
			this.btnNewCalculation.setVisible(model.isNewCalcButtonVisible());
		}
	}

}
