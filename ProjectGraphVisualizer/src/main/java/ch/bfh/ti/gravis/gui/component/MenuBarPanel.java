package ch.bfh.ti.gravis.gui.component;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController.EventSource;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import static ch.bfh.ti.gravis.gui.GuiFactory.loadImage;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MenuBarPanel extends JMenuBar implements Observer {

	private static final long serialVersionUID = 8070798287573948533L;

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
	private static final String PROPERTIES_ICON = "GraphProperties_16.gif";
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
			+ "or undirected edge" + "</ul>" + "<h3>Transforming Mode:</h3>"
			+ "<ul>" + "<li>(Left-click)+drag pans the graph"
			+ "<li>Shift+(Left-click)+drag rotates the graph"
			+ "<li>CTRL+(Left-click)+drag shears the graph"
			+ "</ul></body></html>";

	private final JFrame mainWindow;

	private final JEditorPane helpEditorPane, shortcutEditorPane,
			infoEditorPane;

	/**
	 * 
	 * @param mainWindow
	 * @param menuToolbarController
	 * @param model
	 * @throws IOException
	 */
	public MenuBarPanel(final JFrame mainWindow,
			final IMenuToolbarController menuToolbarController,
			final IAppModel model) throws IOException {

		this.mainWindow = mainWindow;
		this.helpEditorPane = this.createEditorPane(HELP_TEXT);
		this.shortcutEditorPane = this.createEditorPane(SHORTCUT_TEXT);
		this.infoEditorPane = this.createEditorPane(INFO_TEXT);

		this.createMenus(menuToolbarController, model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable source, Object arg) {
		// TODO menubar update observer

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
					// TODO Exception stack trace
					JOptionPane.showMessageDialog(MenuBarPanel.this.mainWindow,
							String.format(APP_ERR_MSG, ex.getMessage()),
							APP_ERR_TITLE, JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		ep.setEditable(false);
		ep.setBackground(this.getBackground());
		return ep;
	}

	/**
	 * 
	 * @param menuToolbarController
	 * @param model
	 * @throws IOException
	 */
	private void createMenus(
			final IMenuToolbarController menuToolbarController,
			final IAppModel model) throws IOException {

		// TODO add models

		// create menu items:

		JMenu menuFile = new JMenu(FILE);
		JMenuItem menuItemNewDirGraph = new JMenuItem(NEW_DIR_GRAPH,
				new ImageIcon(loadImage(NEW_ICON)));
		JMenuItem menuItemNewUndirGraph = new JMenuItem(NEW_UNDIR_GRAPH,
				new ImageIcon(loadImage(NEW_ICON)));
		JMenuItem menuItemOpenGraph = new JMenuItem(OPEN, new ImageIcon(
				loadImage(OPEN_ICON)));
		JMenuItem menuItemSaveGraphAs = new JMenuItem(SAVE_AS, new ImageIcon(
				loadImage(SAVE_AS_ICON)));
		JMenuItem menuItemSaveGraph = new JMenuItem(SAVE, new ImageIcon(
				loadImage(SAVE_ICON)));
		JMenuItem menuItemGraphProperties = new JMenuItem(PROPERTIES,
				new ImageIcon(loadImage(PROPERTIES_ICON)));
		JMenuItem menuItemExit = new JMenuItem(EXIT);
		JMenu menuHelp = new JMenu(HELP_MENU);
		JMenuItem menuItemHelp = new JMenuItem(HELP_ITEM, new ImageIcon(
				loadImage(HELP_ICON)));
		JMenuItem menuItemShortcuts = new JMenuItem(SHORTCUTS, new ImageIcon(
				loadImage(HELP_ICON)));
		JMenuItem menuItemInfo = new JMenuItem(INFO, new ImageIcon(
				loadImage(INFO_ICON)));

		// set mnemonics:

		menuFile.setMnemonic(KeyEvent.VK_D);
		menuHelp.setMnemonic(KeyEvent.VK_H);

		// menu shortcuts:

		menuItemNewDirGraph.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItemNewUndirGraph.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_N, ActionEvent.SHIFT_MASK));
		menuItemOpenGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				ActionEvent.CTRL_MASK));
		menuItemSaveGraph.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItemSaveGraphAs.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_S, ActionEvent.SHIFT_MASK | ActionEvent.CTRL_MASK));
		menuItemGraphProperties.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				ActionEvent.ALT_MASK));
		menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		menuItemShortcuts.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,
				0));
		menuItemInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));

		// add listeners:

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
		menuItemSaveGraphAs.setActionCommand(EventSource.SAVE_GRAPH_AS
				.toString());
		menuItemSaveGraphAs.addActionListener(menuToolbarController);
		menuItemGraphProperties.setActionCommand(EventSource.GRAPH_PROPERTY
				.toString());
		menuItemGraphProperties.addActionListener(menuToolbarController);
		menuItemExit.setActionCommand(EventSource.EXIT.toString());
		menuItemExit.addActionListener(menuToolbarController);
		this.setHelpListeners(menuItemHelp, menuItemShortcuts, menuItemInfo);

		// add menu items:

		this.add(menuFile);
		this.add(menuHelp);
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
	 * @param menuItemHelp
	 * @param menuItemShortcuts
	 * @param menuItemInfo
	 */
	private void setHelpListeners(JMenuItem menuItemHelp,
			JMenuItem menuItemShortcuts, JMenuItem menuItemInfo) {

		menuItemHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MenuBarPanel.this.mainWindow,
						MenuBarPanel.this.helpEditorPane, HELP_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemShortcuts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MenuBarPanel.this.mainWindow,
						MenuBarPanel.this.shortcutEditorPane, SHORTCUT_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MenuBarPanel.this.mainWindow,
						MenuBarPanel.this.infoEditorPane, INFO_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

}
