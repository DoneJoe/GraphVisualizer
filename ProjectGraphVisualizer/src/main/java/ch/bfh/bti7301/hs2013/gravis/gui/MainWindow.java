package ch.bfh.bti7301.hs2013.gravis.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditingGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.VisualizationPanel;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = 8699847848182615730L;

	private final static String TITLE = "Graph Visualizer";
	private final static String FILE = "Datei";
	private final static String NEW_DIR_GRAPH = "Neuer gerichteter Graph";
	private final static String NEW_UNDIR_GRAPH = "Neuer ungerichteter Graph";
	private final static String OPEN = "Öffnen...";
	private final static String SAVE = "Speichern unter...";
	private final static String PROPERTIES = "Eigenschaften...";
	private final static String EXIT = "Beenden";
	private final static String HELP_MENU = "Hilfe";
	private final static String HELP_ITEM = "Hilfe...";
	private final static String SHORTCUTS = "Shortcuts...";
	private final static String INFO = "Info...";

	private final static String HELP_TITLE = "Hilfe zum Graph Visualizer";
	private final static String INFO_TITLE = "Info zum Graph Visualizer";
	private final static String INFO_COMMENT = "<html>"
			+ "<h3><center>Graph Visualizer</center></h3>"
			+ "<center>Ein Tool zur Visualisierung von Graphen und Algorithmen.</center>"
			+ "<br /><center>Berner Fachhochschule - Technik und Informatik</center>"
			+ "<center>Modul BTI7301: Projekt 1</center>"
			+ "<br /><center>Entwickelt von Patrick Kofmel</center>"
			+ "</html>";
	private final static String SHORTCUT_TITLE = "Shortcuts zum Graph Visualizer";
	private final static String SHORTCUT_COMMENT = "<html>"
			+ "<h3>All Modes:</h3>"
			+ "<ul>"
			+ "<li>Right-click an empty area for <b>Create Vertex</b> popup"
			+ "<li>Right-click on a Vertex for <b>Set Start Vertex, Set End Vertex, Edit Vertex, Delete Vertex</b> popup"
			+ "<li>Right-click on an Edge for <b>Edit Edge, Delete Edge</b> popup"
			+ "<li>Mousewheel scales with a crossover value of 1.0.<p>"
			+ "     - scales the graph layout when the combined scale is greater than 1<p>"
			+ "     - scales the graph view when the combined scale is less than 1"
			+ "</ul>"
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
			+ "</ul></html>";

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * 
	 * @param stepController
	 * @param menuToolbarController
	 * @param visualizationController
	 * @param model
	 */
	public MainWindow(IMenuToolbarController menuToolbarController,
			IEditingGraphEventListener visualizationController,
			IStepController stepController, IGuiModel model) {
		super(TITLE);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(this.contentPane);

		// create panels
		VisualizationPanel visualizationPanel = new VisualizationPanel(model,
				this);
		ToolBarPanel toolBar = new ToolBarPanel(menuToolbarController, model,
				visualizationPanel.getModeComboBox());
		StepPanel stepPanel = new StepPanel(stepController, model);
		ProtocolPanel protocolPanel = new ProtocolPanel();
		JPanel footerPanel = new JPanel();
		
		// add panels
		this.contentPane.add(toolBar, BorderLayout.PAGE_START);
		this.contentPane.add(visualizationPanel, BorderLayout.CENTER);
		this.contentPane.add(footerPanel, BorderLayout.SOUTH);
		footerPanel.setLayout(new BorderLayout());
		footerPanel.add(stepPanel, BorderLayout.NORTH);
		footerPanel.add(protocolPanel, BorderLayout.CENTER);
		
		// set dialog adapters
		menuToolbarController
				.setFileChooserAdapter(new FileChooserAdapter(this));
		menuToolbarController.setMessageDialogAdapter(new MessageDialogAdapter(
				this));
		menuToolbarController.setConfirmDialogAdapter(new ConfirmDialogAdapter(
				this));
		menuToolbarController
				.setGraphPropertyDialogFactory(new GraphPropertyDialogFactory(
						this));
		
		// add Observers
		menuToolbarController.addObserver(toolBar);
		menuToolbarController.addObserver(stepPanel);
		menuToolbarController.addObserver(visualizationPanel);
		menuToolbarController.addObserver(protocolPanel);
		visualizationController.addObserver(toolBar);
		visualizationController.addObserver(visualizationPanel);
		visualizationController.addObserver(stepPanel);
		stepController.addObserver(visualizationPanel);
		stepController.addObserver(stepPanel);
		stepController.addObserver(protocolPanel);

		this.createMenus(menuToolbarController, model);
		this.addWindowListener(menuToolbarController);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}

	/**
	 * 
	 * @param menuToolbarController
	 * @param model
	 */
	private void createMenus(IMenuToolbarController menuToolbarController,
			IGuiModel model) {
		// TODO Mnemonic, F1

		// create menu items
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

		// add listeners
		menuItemNewDirGraph.setActionCommand(EventSource.NEW_DIR_GRAPH
				.toString());
		menuItemNewDirGraph.addActionListener(menuToolbarController);
		menuItemNewUndirGraph.setActionCommand(EventSource.NEW_UNDIR_GRAPH
				.toString());
		menuItemNewUndirGraph.addActionListener(menuToolbarController);
		menuItemOpenGraph.setActionCommand(EventSource.OPEN_GRAPH.toString());
		menuItemOpenGraph.addActionListener(menuToolbarController);
		menuItemSaveGraph.setActionCommand(EventSource.SAVE_GRAPH.toString());
		menuItemSaveGraph.addActionListener(menuToolbarController);
		menuItemGraphProperties.setActionCommand(EventSource.GRAPH_PROPERTY
				.toString());
		menuItemGraphProperties.addActionListener(menuToolbarController);
		menuItemExit.setActionCommand(EventSource.EXIT.toString());
		menuItemExit.addActionListener(menuToolbarController);
		this.setHelpListeners(menuItemHelp, menuItemShortcuts, menuItemInfo);

		// add menu items
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

	/**
	 * @param menuItemHelp
	 * @param menuItemShortcuts
	 * @param menuItemInfo
	 */
	private void setHelpListeners(JMenuItem menuItemHelp,
			JMenuItem menuItemShortcuts, JMenuItem menuItemInfo) {

		menuItemHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Help dialog
				JOptionPane.showMessageDialog(MainWindow.this, "Help Text",
						HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemShortcuts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Shortcut dialog text auf deutsch
				JOptionPane.showMessageDialog(MainWindow.this,
						SHORTCUT_COMMENT, SHORTCUT_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Info dialog text und Formatierung
				JOptionPane.showMessageDialog(MainWindow.this, INFO_COMMENT,
						INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

}
