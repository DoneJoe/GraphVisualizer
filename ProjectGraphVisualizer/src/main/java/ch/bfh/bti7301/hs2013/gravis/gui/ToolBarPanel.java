package ch.bfh.bti7301.hs2013.gravis.gui;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JToolBar;
import javax.swing.JButton;

import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IToolBarModel;
import static ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants.*;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
	private static final String PROPERTIES_ICON = "Edit24.gif";	
	private static final String PROPERTIES_TOOLTIP = "Graph Eigenschaften...";
	
	private static final String EDIT_MODE_LABEL = "Bearbeitungsmodus:";
	private static final String MODE_TOOLTIP = "Bearbeitungs-Modus wählen";
	private static final String ALGO_TOOLTIP = "Algorithmus wählen";
	private static final String CALC_LABEL = "Neu berechnen";
	private static final String CALC_TOOLTIP = "Schritte neu berechnen";

	private JComboBox<String> comboBoxAlgorithm;
	
	private JButton btnNewCalculation;
	
	/**
	 * Create the panel.
	 * 
	 * @param model 
	 * @param menuToolbarController 
	 * @param comboMode 
	 * @throws IOException 
	 */
	public ToolBarPanel(IMenuToolbarController menuToolbarController, IGuiModel model,
			JComboBox<?> comboMode) throws IOException {
		this.setFloatable(false);
		
		FlowLayout layout = new FlowLayout();
		layout.setAlignment(FlowLayout.LEADING);
		this.setLayout(layout);
		
		JButton btnNewDirGraph = new JButton(NEW_DIR_LABEL);
		btnNewDirGraph.setIcon(new ImageIcon(this.loadImage(NEW_ICON)));
		btnNewDirGraph.setToolTipText(NEW_DIR_TOOLTIP);
		this.add(btnNewDirGraph);
		
		JButton btnNewUndirGraph = new JButton(NEW_UNDIR_LABEL);
		btnNewUndirGraph.setIcon(new ImageIcon(this.loadImage(NEW_ICON)));
		btnNewUndirGraph.setToolTipText(NEW_UNDIR_TOOLTIP);
		this.add(btnNewUndirGraph);
		
		JButton btnOpenGraph = new JButton();
		btnOpenGraph.setIcon(new ImageIcon(this.loadImage(OPEN_ICON)));
		btnOpenGraph.setToolTipText(OPEN_TOOLTIP);
		this.add(btnOpenGraph);
		
		JButton btnSaveGraph = new JButton();
		btnSaveGraph.setIcon(new ImageIcon(this.loadImage(SAVE_ICON)));
		btnSaveGraph.setToolTipText(SAVE_TOOLTIP);
		this.add(btnSaveGraph);
		
		JButton btnSaveGraphAs = new JButton();
		btnSaveGraphAs.setIcon(new ImageIcon(this.loadImage(SAVE_AS_ICON)));
		btnSaveGraphAs.setToolTipText(SAVE_AS_TOOLTIP);
		this.add(btnSaveGraphAs);
		
		JButton btnGraphProp = new JButton();
		btnGraphProp.setIcon(new ImageIcon(this.loadImage(PROPERTIES_ICON)));
		btnGraphProp.setToolTipText(PROPERTIES_TOOLTIP);
		this.add(btnGraphProp);
		
		JLabel lblEditMode = new JLabel(EDIT_MODE_LABEL);
		this.add(lblEditMode);
		
		JComboBox<?> comboBoxMode = comboMode;
		comboBoxMode.setToolTipText(MODE_TOOLTIP);
		this.add(comboBoxMode);
		
		this.comboBoxAlgorithm = new JComboBox<>();
		this.comboBoxAlgorithm.setToolTipText(ALGO_TOOLTIP);
		this.comboBoxAlgorithm.setEnabled(false);
		this.comboBoxAlgorithm.setModel(model.getAlgorithmComboModel());
		this.add(this.comboBoxAlgorithm);
		
		this.btnNewCalculation = new JButton(CALC_LABEL);
		this.btnNewCalculation.setToolTipText(CALC_TOOLTIP);
		this.btnNewCalculation.setVisible(false);
		this.add(this.btnNewCalculation);

		// add listeners
		btnOpenGraph.setActionCommand(EventSource.OPEN_GRAPH.toString());
		btnOpenGraph.addActionListener(menuToolbarController);
		btnSaveGraph.setActionCommand(EventSource.SAVE_GRAPH.toString());
		btnSaveGraph.addActionListener(menuToolbarController);
		btnSaveGraphAs.setActionCommand(EventSource.SAVE_GRAPH_AS.toString());
		btnSaveGraphAs.addActionListener(menuToolbarController);
		btnNewDirGraph.setActionCommand(EventSource.NEW_DIR_GRAPH.toString());
		btnNewDirGraph.addActionListener(menuToolbarController);
		btnNewUndirGraph.setActionCommand(EventSource.NEW_UNDIR_GRAPH.toString());
		btnNewUndirGraph.addActionListener(menuToolbarController);
		btnGraphProp.setActionCommand(EventSource.GRAPH_PROPERTY.toString());
		btnGraphProp.addActionListener(menuToolbarController);
		comboBoxMode.setActionCommand(EventSource.MODE.toString());
		comboBoxMode.addItemListener(menuToolbarController);
		this.comboBoxAlgorithm.setActionCommand(EventSource.ALGORITHM.toString());
		this.comboBoxAlgorithm.addItemListener(menuToolbarController);
		this.btnNewCalculation.setActionCommand(EventSource.NEW_CALC.toString());
		this.btnNewCalculation.addActionListener(menuToolbarController);
	}

	/**
	 * Loads an icon ressource with the given name.
	 * 
	 * @param iconName
	 * @return Image
	 * @throws IOException
	 */
	private Image loadImage(String iconName) throws IOException {
		return ImageIO.read(this.getClass().getResourceAsStream(
				IMAGES_DIR + iconName));
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof IToolBarModel) {
			IToolBarModel model = (IToolBarModel) arg;
			
			this.comboBoxAlgorithm.setModel(model.getAlgorithmComboBoxModel());
			this.comboBoxAlgorithm.setEnabled(model.isAlgoComboEnabled());
			this.btnNewCalculation.setVisible(model.isNewCalcButtonVisible());
		}
	}

}
