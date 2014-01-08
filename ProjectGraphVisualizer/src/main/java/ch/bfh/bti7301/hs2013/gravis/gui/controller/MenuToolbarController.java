package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Observable;

import javax.swing.JOptionPane;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends Observable implements IMenuToolbarController {

	private ICore core;

	private IGuiModel model;

	/**
	 * @param core
	 * @param model
	 */
	protected MenuToolbarController(ICore core, IGuiModel model) {
		this.core = core;
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO implement
		if (e.getActionCommand().equals(NEW_DIR_GRAPH.toString())) {
			JOptionPane.showMessageDialog(null, NEW_DIR_GRAPH);
		} else if (e.getActionCommand().equals(NEW_UNDIR_GRAPH.toString())) {
			JOptionPane.showMessageDialog(null, NEW_UNDIR_GRAPH);
		} else if (e.getActionCommand().equals(OPEN_GRAPH.toString())) {
			try {
				IGravisGraph graph = this.core.importGraph(new File(
						"D:/Daten/Documents/Programmierung/Java/"
						+ "Eclipse_BFH_Project_1/GraphVisualizer/"
						+ "ProjectGraphVisualizer/graph_templates/"
						+ "DijkstraSampleGraph2.graphml"));
				this.model.setGraph(graph);
				this.setChanged();
				this.notifyObservers(graph);
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//JOptionPane.showMessageDialog(null, OPEN_GRAPH);
		} else if (e.getActionCommand().equals(SAVE_GRAPH.toString())) {
			JOptionPane.showMessageDialog(null, SAVE_GRAPH);
		} else if (e.getActionCommand().equals(GRAPH_PROPERTIES.toString())) {
			JOptionPane.showMessageDialog(null, GRAPH_PROPERTIES);
		}

	}

}
