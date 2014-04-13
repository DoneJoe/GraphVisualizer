package ch.bfh.bti7301.hs2013.gravis.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import ch.bfh.bti7301.hs2013.gravis.core.graph.IEditableGraphEventListener;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.bti7301.hs2013.gravis.gui.controller.IStepController;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IAppModel;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.VisualizationPanel;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource;
import static ch.bfh.bti7301.hs2013.gravis.core.util.GravisConstants.*;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

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
	private final static String SAVE_AS = "Speichern unter...";
	private final static String SAVE = "Speichern";	
	private final static String PROPERTIES = "Graph Eigenschaften...";
	private final static String EXIT = "Beenden";
	private final static String HELP_MENU = "Hilfe";
	private final static String HELP_ITEM = "Hilfe...";
	private final static String SHORTCUTS = "Shortcuts...";
	private final static String INFO = "Info...";
	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten: %s";

	private static final String OPEN_ICON = "Open16.gif";
	private static final String SAVE_AS_ICON = "SaveAs16.gif";
	private static final String SAVE_ICON = "Save16.gif";
	private static final String NEW_ICON = "New16.gif";
	private static final String EDIT_ICON = "Edit16.gif";
	private static final String HELP_ICON = "Help16.gif";
	private static final String INFO_ICON = "About16.gif";

	private final static String EDITOR_PANE_TYPE = "text/html";
	private final static String HELP_TITLE = "Hilfe zum Graph Visualizer";
	private final static String HELP_TEXT = "<html><body>"
			+ "<p>Die Projektdokumentation mit Hilfe zu dieser Applikation "
			+ "befindet sich im GitHub-Repository unter:</p>"
			+ "<a href=\"https://github.com/kofmp1/GraphVisualizer/tree/master/"
			+ "ProjectGraphVisualizer/doc\">"
			+ "https://github.com/kofmp1/GraphVisualizer/tree/master/"
			+ "ProjectGraphVisualizer/doc</a></body></html>";
	private final static String INFO_TITLE = "Info zum Graph Visualizer";
	private final static String INFO_TEXT = "<html><body>"
			+ "<h3>Graph Visualizer</h3>"
			+ "Ein Tool zur Visualisierung von Graphen und Algorithmen."
			+ "<br /><br />Berner Fachhochschule - Technik und Informatik"
			+ "<br />Modul BTI7301: Projekt 1"
			+ "<br />Herbstsemester 2013/2014"
			+ "<br /><br />Entwickelt von Patrick Kofmel</body></html>";
	private final static String SHORTCUT_TITLE = "Shortcuts zum Graph Visualizer";
	private final static String SHORTCUT_TEXT = "<html><body>"
			+ "<h3>All Modes:</h3>"
			+ "<ul>"
			+ "<li>Right-click an empty area for <b>Create Vertex</b> popup"
			+ "<li>Right-click on a Vertex for <b>Set Start Vertex, Set End Vertex, "
			+ "Edit Vertex, Delete Vertex</b> popup"
			+ "<li>Right-click on an Edge for <b>Edit Edge, Delete Edge</b> popup"
			+ "<li>Mousewheel scales with a crossover value of 1.0.<p>"
			+ "     - scales the graph layout when the combined scale is greater than 1<p>"
			+ "     - scales the graph view when the combined scale is less than 1"
			+ "</ul>"
			+ "<h3>Picking Mode:</h3>"
			+ "<ul>"
			+ "<li>Left-click on a Vertex selects the vertex"
			+ "<li>Left-click elsewhere unselects all Vertices"
			+ "<li>Shift+(Left-click) on a Vertex adds/removes Vertex selection"
			+ "<li>(Left-click)+drag on a Vertex moves all selected Vertices"
			+ "<li>(Left-click)+drag elsewhere selects Vertices in a region"
			+ "<li>Shift+(Left-click)+drag adds selection of Vertices in a new region"
			+ "<li>CTRL+(Left-click) on a Vertex selects the vertex and centers the display on it"
			+ "</ul>"
			+ "<h3>Editing Mode:</h3>"
			+ "<ul>"
			+ "<li>Left-click an empty area to create a new Vertex"
			+ "<li>Left-click on a Vertex and drag to another Vertex to create a directed "
			+ "or undirected edge"
			+ "</ul>"
			+ "<h3>Transforming Mode:</h3>"
			+ "<ul>"
			+ "<li>(Left-click)+drag pans the graph"
			+ "<li>Shift+(Left-click)+drag rotates the graph"
			+ "<li>CTRL+(Left-click)+drag shears the graph"
			+ "</ul></body></html>";

	private final JEditorPane helpEditorPane, shortcutEditorPane, infoEditorPane;
	
	/**
	 * Creates the frame.
	 * 
	 * @param stepController
	 * @param menuToolbarController
	 * @param visualizationController
	 * @param model
	 * @throws IOException
	 */
	public MainWindow(IMenuToolbarController menuToolbarController,
			IEditableGraphEventListener visualizationController,
			IStepController stepController, IAppModel model) throws IOException {
		super(TITLE);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);

		// create panels
		this.helpEditorPane = this.createEditorPane(HELP_TEXT);
		this.shortcutEditorPane = this.createEditorPane(SHORTCUT_TEXT);
		this.infoEditorPane = this.createEditorPane(INFO_TEXT);
		VisualizationPanel visualizationPanel = new VisualizationPanel(model,
				this);
		ToolBarPanel toolBar = new ToolBarPanel(menuToolbarController, model);
		StepPanel stepPanel = new StepPanel(stepController, model);
		ProtocolPanel protocolPanel = new ProtocolPanel();
		JPanel footerPanel = new JPanel();

		// add panels
		contentPane.add(toolBar, BorderLayout.PAGE_START);
		contentPane.add(visualizationPanel, BorderLayout.CENTER);
		contentPane.add(footerPanel, BorderLayout.SOUTH);
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
	 * @throws IOException
	 */
	private void createMenus(IMenuToolbarController menuToolbarController,
			IAppModel model) throws IOException {
		// create menu items
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu(FILE);
		JMenuItem menuItemNewDirGraph = new JMenuItem(NEW_DIR_GRAPH,
				new ImageIcon(this.loadImage(NEW_ICON)));
		JMenuItem menuItemNewUndirGraph = new JMenuItem(NEW_UNDIR_GRAPH,
				new ImageIcon(this.loadImage(NEW_ICON)));
		JMenuItem menuItemOpenGraph = new JMenuItem(OPEN, new ImageIcon(
				this.loadImage(OPEN_ICON)));
		JMenuItem menuItemSaveGraphAs = new JMenuItem(SAVE_AS, new ImageIcon(
				this.loadImage(SAVE_AS_ICON)));
		JMenuItem menuItemSaveGraph = new JMenuItem(SAVE, new ImageIcon(
				this.loadImage(SAVE_ICON)));
		JMenuItem menuItemGraphProperties = new JMenuItem(PROPERTIES,
				new ImageIcon(this.loadImage(EDIT_ICON)));
		JMenuItem menuItemExit = new JMenuItem(EXIT);
		JMenu menuHelp = new JMenu(HELP_MENU);
		JMenuItem menuItemHelp = new JMenuItem(HELP_ITEM, new ImageIcon(
				this.loadImage(HELP_ICON)));
		JMenuItem menuItemShortcuts = new JMenuItem(SHORTCUTS, new ImageIcon(
				this.loadImage(HELP_ICON)));
		JMenuItem menuItemInfo = new JMenuItem(INFO, new ImageIcon(
				this.loadImage(INFO_ICON)));

		// set mnemonics and menu shortcuts
		menuFile.setMnemonic(KeyEvent.VK_D);
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuItemNewDirGraph.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItemNewUndirGraph.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.SHIFT_MASK));
		menuItemOpenGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menuItemSaveGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItemSaveGraphAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
		menuItemGraphProperties.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuItemShortcuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
				0));
		menuItemInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

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
		menuItemSaveGraphAs.setActionCommand(EventSource.SAVE_GRAPH_AS.toString());
		menuItemSaveGraphAs.addActionListener(menuToolbarController);
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
		menuFile.add(menuItemSaveGraphAs);
		menuFile.add(menuItemGraphProperties);
		menuFile.addSeparator();
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemHelp);
		menuHelp.add(menuItemShortcuts);
		menuHelp.add(menuItemInfo);
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
				JOptionPane.showMessageDialog(MainWindow.this,
						MainWindow.this.helpEditorPane,
						HELP_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemShortcuts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainWindow.this,
						MainWindow.this.shortcutEditorPane,
						SHORTCUT_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainWindow.this,
						MainWindow.this.infoEditorPane,
						INFO_TITLE, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	/**
	 * @param helpText
	 * @return JEditorPane
	 */
	private JEditorPane createEditorPane(String helpText) {
		JEditorPane ep = new JEditorPane(EDITOR_PANE_TYPE, helpText);

		// handle link events
		ep.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				try {
					if (e.getEventType().equals(
							HyperlinkEvent.EventType.ACTIVATED)) {

						if (Desktop.isDesktopSupported()) {
							Desktop desktop = Desktop.getDesktop();

							if (desktop.isSupported(Desktop.Action.BROWSE)) {
								desktop.browse(new URI(e.getURL().toString()));
							}
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(MainWindow.this,
							String.format(APP_ERR_MSG, ex.getMessage()),
							APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		ep.setEditable(false);
		ep.setBackground(this.getBackground());
		return ep;
	}

}
