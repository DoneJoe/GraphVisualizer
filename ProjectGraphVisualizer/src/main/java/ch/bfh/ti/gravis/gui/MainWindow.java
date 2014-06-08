package ch.bfh.ti.gravis.gui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EmptyBorder;

import ch.bfh.ti.gravis.gui.component.MenuBarPanel;
import ch.bfh.ti.gravis.gui.component.ProtocolPanel;
import ch.bfh.ti.gravis.gui.component.StepPanel;
import ch.bfh.ti.gravis.gui.component.ToolBarPanel;
import ch.bfh.ti.gravis.gui.component.VisualizationPanel;
import ch.bfh.ti.gravis.gui.controller.IMenuToolbarController;
import ch.bfh.ti.gravis.gui.controller.IStepController;
import ch.bfh.ti.gravis.gui.model.IAppModel;
import ch.bfh.ti.gravis.gui.model.MainWindowModel;
import static ch.bfh.ti.gravis.gui.GuiFactory.loadImage;

/**
 * The application main window. This class represents the main view in the
 * MVC-pattern.
 * 
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 8699847848182615730L;

	private static final int BORDER = 5;

	private final static String TITLE_NEW = "Graph Visualizer - [Neuer Graph%s]";
	private final static String TITLE_SAVED = "Graph Visualizer - [%s%s]";
	private final static String UNSAVED = " *";
	private final static String APP_ICON = "Circle-group-icon_32.png";

	/**
	 * Creates the main window.
	 * 
	 * @param stepController
	 * @param menuToolbarController
	 * @param model
	 * @throws IOException
	 */
	public MainWindow(final IMenuToolbarController menuToolbarController,
			final IStepController stepController, final IAppModel model)
			throws IOException {

		super(String.format(TITLE_NEW, ""));

		// set content pane:

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		contentPane.setLayout(new BorderLayout(BORDER, BORDER));
		this.setContentPane(contentPane);

		// create panels:

		MenuBarPanel menuBar = new MenuBarPanel(this, menuToolbarController,
				model);
		ToolBarPanel toolBar = new ToolBarPanel(menuToolbarController, model);
		VisualizationPanel visualizationPanel = new VisualizationPanel(this,
				model);
		StepPanel stepPanel = new StepPanel(stepController, model);
		ProtocolPanel protocolPanel = new ProtocolPanel(model);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		JPanel centerPanel = new JPanel();

		// add panels:

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(visualizationPanel, BorderLayout.CENTER);
		centerPanel.add(stepPanel, BorderLayout.SOUTH);
		splitPane.setTopComponent(centerPanel);
		splitPane.setBottomComponent(protocolPanel);
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(splitPane, BorderLayout.CENTER);

		// add Observers:

		model.addObserver(this);
		model.addObserver(toolBar);
		model.addObserver(visualizationPanel);
		model.addObserver(stepPanel);

		// prepare main window:

		this.setJMenuBar(menuBar);
		this.addWindowListener(menuToolbarController);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.pack();
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setIconImage(loadImage(APP_ICON));
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof MainWindowModel) {
			MainWindowModel model = (MainWindowModel) arg;

			String unsaved = model.isUnsaved() ? UNSAVED : "";
			this.setTitle(model.hasGraphFile() ? String.format(TITLE_SAVED,
					model.getFilePath(), unsaved) : String.format(TITLE_NEW,
					unsaved));
		}
	}
}
