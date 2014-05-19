package ch.bfh.ti.gravis.gui.component;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;

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
public class MenuBarPanel extends JMenuBar {

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
	private final static String LINK_ERR_MSG = "Die angeforderte Seite konnte nicht geöffnet werden!";

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
			+ "<p>Die Projektdokumentation inkl. Hilfe zu dieser Applikation "
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
	private final static String SHORTCUT_TITLE = "Edit Shortcuts zum Graph Visualizer";
	private final static String SHORTCUT_TEXT = "<html><body>"
			+ "<h3>All Modes:</h3>"
			+ "<ul>"
			+ "<li>Right-click an empty area: <b>Create Vertex</b> popup"
			+ "<li>Right-click on a vertex: <b>Set Start Vertex, Set End Vertex, "
			+ "Edit Vertex, Delete Vertex</b> popup"
			+ "<li>Right-click on an edge: <b>Edit Edge, Delete Edge</b> popup"
			+ "<li>Mousewheel scales with a crossover value of 1.0.<p>"
			+ "     - scales the graph layout when the combined scale is greater than 1<p>"
			+ "     - scales the graph view when the combined scale is less than 1"
			+ "</ul>"
			+ "<h3>Picking Mode:</h3>"
			+ "<ul>"
			+ "<li>Left-click on a vertex selects the vertex"
			+ "<li>Left-click elsewhere unselects all vertices"
			+ "<li>Shift+(Left-click) on a vertex adds/removes vertex selection"
			+ "<li>(Left-click)+drag on a vertex moves all selected vertices"
			+ "<li>(Left-click)+drag elsewhere selects vertices in a region"
			+ "<li>Shift+(Left-click)+drag adds selection of vertices in a new region"
			+ "<li>CTRL+(Left-click) on a vertex selects the vertex and centers the display on it"
			+ "</ul>"
			+ "<h3>Editing Mode:</h3>"
			+ "<ul>"
			+ "<li>Left-click an empty area to create a new vertex"
			+ "<li>Left-click on a vertex and drag to another vertex to create a directed "
			+ "or undirected edge" + "</ul>" + "<h3>Transforming Mode:</h3>"
			+ "<ul>" + "<li>(Left-click)+drag pans the graph"
			+ "<li>Shift+(Left-click)+drag rotates the graph"
			+ "<li>CTRL+(Left-click)+drag shears the graph"
			+ "</ul></body></html>";

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

		this.helpEditorPane = this.createEditorPane(mainWindow, HELP_TEXT);
		this.shortcutEditorPane = this.createEditorPane(mainWindow,
				SHORTCUT_TEXT);
		this.infoEditorPane = this.createEditorPane(mainWindow, INFO_TEXT);

		this.createMenus(menuToolbarController, model, mainWindow);
	}

	/**
	 * @param mainWindow
	 * @param helpText
	 * @return JEditorPane
	 */
	private JEditorPane createEditorPane(final JFrame mainWindow,
			final String helpText) {
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
					JOptionPane.showMessageDialog(mainWindow, LINK_ERR_MSG,
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
	 * @param mainWindow
	 * @throws IOException
	 */
	private void createMenus(
			final IMenuToolbarController menuToolbarController,
			final IAppModel model, final JFrame mainWindow) throws IOException {

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

		// set models:

		menuFile.setModel(model.getFileMenuModel());
		menuHelp.setModel(model.getHelpMenuModel());
		menuItemNewDirGraph.setModel(model.getNewDirGraphButtonModel());
		menuItemNewUndirGraph.setModel(model.getNewUndirGraphButtonModel());
		menuItemOpenGraph.setModel(model.getOpenGraphButtonModel());
		menuItemSaveGraph.setModel(model.getSaveGraphButtonModel());
		menuItemSaveGraphAs.setModel(model.getSaveGraphAsButtonModel());
		menuItemGraphProperties.setModel(model.getGraphPropertiesButtonModel());

		// set mnemonics:

		menuFile.setMnemonic(KeyEvent.VK_D);
		menuHelp.setMnemonic(KeyEvent.VK_H);

		// set menu shortcuts:

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

		// add other listeners:

		menuItemExit.setActionCommand(EventSource.EXIT.toString());
		menuItemExit.addActionListener(menuToolbarController);
		this.setHelpListeners(menuItemHelp, menuItemShortcuts, menuItemInfo,
				mainWindow);

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
	 * @param mainWindow
	 */
	private void setHelpListeners(JMenuItem menuItemHelp,
			JMenuItem menuItemShortcuts, JMenuItem menuItemInfo,
			final JFrame mainWindow) {

		menuItemHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow,
						MenuBarPanel.this.helpEditorPane, HELP_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemShortcuts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow,
						MenuBarPanel.this.shortcutEditorPane, SHORTCUT_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		menuItemInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mainWindow,
						MenuBarPanel.this.infoEditorPane, INFO_TITLE,
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

}
