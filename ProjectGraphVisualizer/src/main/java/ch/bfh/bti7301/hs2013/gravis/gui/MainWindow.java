package ch.bfh.bti7301.hs2013.gravis.gui;

import java.awt.BorderLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IVisualizationController;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.VisualizationPanel;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.GridLayout;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 8699847848182615730L;

	private final static String TITLE = "Graph Visualizer";
	private final static String FILE = "Datei";
	private final static String NEW_DIR_GRAPH = "Neuer gerichteter Graph";
	private final static String NEW_UNDIR_GRAPH = "Neuer ungerichteter Graph";
	private final static String OPEN = "Öffnen...";
	private final static String SAVE = "Speichern...";
	private final static String PROPERTIES = "Eigenschaften...";
	private final static String EXIT = "Beenden";
	private final static String HELP_MENU = "Hilfe";
	private final static String HELP_ITEM = "Hilfe...";
	private final static String SHORTCUTS = "Shortcuts...";
	private final static String INFO = "Info...";
	
	private final static String SHORTCUT_COMMENT = "<html>"
			+ "<h3>All Modes:</h3>"
			+ "<ul>"
			+ "<li>Right-click an empty area for <b>Create Vertex</b> popup"
			+ "<li>Right-click on a Vertex for <b>Set Start Vertex, Set End Vertex, Edit Vertex, Delete Vertex</b> popup"
			+ "<li>Right-click on an Edge for <b>Edit Edge, Delete Edge</b> popup"
			+ "<li>Mousewheel scales with a crossover value of 1.0.<p>"
			+ "     - scales the graph layout when the combined scale is greater than 1<p>"
			+ "     - scales the graph view when the combined scale is less than 1"
			+

			"</ul>"
			+ "<h3>Editing Mode:</h3>"
			+ "<ul>"
			+ "<li>Left-click an empty area to create a new Vertex"
			+ "<li>Left-click on a Vertex and drag to another Vertex to create an undirected or directed edge"
			+ "</ul>"
			+ "<h3>Picking Mode:</h3>"
			+ "<ul>"
			+ "<li>Mouse1 on a Vertex selects the vertex"
			+ "<li>Mouse1 elsewhere unselects all Vertices"
			+ "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"
			+ "<li>Mouse1+drag on a Vertex moves all selected Vertices"
			+ "<li>Mouse1+drag elsewhere selects Vertices in a region"
			+ "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"
			+ "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it"
			+ "</ul>"
			+ "<h3>Transforming Mode:</h3>"
			+ "<ul>"
			+ "<li>Mouse1+drag pans the graph"
			+ "<li>Mouse1+Shift+drag rotates the graph"
			+ "<li>Mouse1+CTRL(or Command)+drag shears the graph"
			+ "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"
			+ "</ul>" + "</html>";

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * 
	 * @param stepController
	 * @param visualizationController
	 * @param menuToolbarController
	 * @param model
	 */
	public MainWindow(IMenuToolbarController menuToolbarController,
			IVisualizationController visualizationController,
			IStepController stepController, IGuiModel model) {
		super(TITLE);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);
		
		ToolBarPanel toolBar = new ToolBarPanel(menuToolbarController, model);
		VisualizationPanel visualizationPanel = new VisualizationPanel(visualizationController, 
				model);
		JPanel footerPanel = new JPanel();
		StepPanel stepPanel = new StepPanel(stepController, model);
		ProtocolPanel protocolPanel = new ProtocolPanel();
		
		this.contentPane.add(toolBar, BorderLayout.NORTH);
		this.contentPane.add(visualizationPanel, BorderLayout.CENTER);
		this.contentPane.add(footerPanel, BorderLayout.SOUTH);
		footerPanel.setLayout(new GridLayout(2, 0, 0, 0));
		footerPanel.add(stepPanel);
		footerPanel.add(protocolPanel);
		menuToolbarController.addObserver(visualizationPanel);
		
		this.createMenus(menuToolbarController, model);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	/**
	 * 
	 * @param menuToolbarController
	 * @param model
	 */
	private void createMenus(IMenuToolbarController menuToolbarController, IGuiModel model) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu(FILE);
		JMenuItem menuItemNewDirGraph = new JMenuItem(NEW_DIR_GRAPH);
		JMenuItem menuItemNewUndirGraph = new JMenuItem(NEW_UNDIR_GRAPH);
		JMenuItem menuItemOpenGraph = new JMenuItem(OPEN);
		JMenuItem menuItemSaveGraph = new JMenuItem(SAVE);
		JMenuItem menuItemGraphProperties = new JMenuItem(PROPERTIES);
		JMenuItem menuItemExit = new JMenuItem(EXIT);
		JMenu menuHelp = new JMenu(HELP_MENU);
		JMenuItem menuItemHelp = new JMenuItem(HELP_ITEM);
		JMenuItem menuItemShortcuts = new JMenuItem(SHORTCUTS);
		JMenuItem menuItemInfo = new JMenuItem(INFO);
		
		model.setNewDirGraphModel(menuItemNewDirGraph.getModel());
		// TODO add models
		
		menuItemNewDirGraph.setActionCommand(EventSource.NEW_DIR_GRAPH.toString());
		menuItemNewDirGraph.addActionListener(menuToolbarController);
		menuItemNewUndirGraph.setActionCommand(EventSource.NEW_UNDIR_GRAPH.toString());
		menuItemNewUndirGraph.addActionListener(menuToolbarController);
		menuItemOpenGraph.setActionCommand(EventSource.OPEN_GRAPH.toString());
		menuItemOpenGraph.addActionListener(menuToolbarController);
		menuItemSaveGraph.setActionCommand(EventSource.SAVE_GRAPH.toString());
		menuItemSaveGraph.addActionListener(menuToolbarController);
		menuItemGraphProperties.setActionCommand(EventSource.GRAPH_PROPERTIES.toString());
		menuItemGraphProperties.addActionListener(menuToolbarController);
		
		this.setJMenuBar(menuBar);
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		menuFile.add(menuItemNewDirGraph);
		menuFile.add(menuItemNewUndirGraph);
		menuFile.add(menuItemOpenGraph);
		menuFile.add(menuItemSaveGraph);
		menuFile.add(menuItemGraphProperties);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHelp);
		menuHelp.add(menuItemShortcuts);
		menuHelp.add(menuItemInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		// GraphPropertyDialog graphPropertyDialog = new
		// GraphPropertyDialog(model.getGraph(), this);
	}

}
