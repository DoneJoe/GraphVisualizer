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
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.GravisVisualizationViewer;
import ch.bfh.bti7301.hs2013.gravis.gui.visualization.VisualizationPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JButton;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
public class MainWindow extends JFrame implements Observer {

	private static final long serialVersionUID = 8699847848182615730L;

	private final static String TITLE = "Graph Visualizer";

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

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 933, 532);
		
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("New menu");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
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
		
		this.setVisible(true);

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
