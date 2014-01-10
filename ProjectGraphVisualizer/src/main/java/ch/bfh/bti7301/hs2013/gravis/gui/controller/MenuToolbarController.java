package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Observable;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.core.graph.IGravisGraph;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ExitDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends Observable implements
		IMenuToolbarController {

	private final ICore core;

	private final IGuiModel model;

	private FileChooserAdapter fileChooserAdapter = null;
	
	private ExitDialogAdapter exitDialogAdapter = null;

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
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setFileChooserAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter)
	 */
	@Override
	public void setFileChooserAdapter(FileChooserAdapter fileChooserAdapter) {
		this.fileChooserAdapter = fileChooserAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setExitDialogAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.ExitDialogAdapter)
	 */
	@Override
	public void setExitDialogAdapter(ExitDialogAdapter exitDialogAdapter) {
		this.exitDialogAdapter = exitDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals(NEW_DIR_GRAPH.toString())) {
				this.handleNewGraphEvent(EdgeType.DIRECTED);
			} else if (e.getActionCommand().equals(NEW_UNDIR_GRAPH.toString())) {
				this.handleNewGraphEvent(EdgeType.UNDIRECTED);
			} else if (e.getActionCommand().equals(OPEN_GRAPH.toString())) {
				this.handleOpenGraphEvent();
			} else if (e.getActionCommand().equals(SAVE_GRAPH.toString())) {
				this.handleSaveGraphEvent();
			} else if (e.getActionCommand().equals(GRAPH_PROPERTIES.toString())) {
				this.handleGraphPropertiesEvent();
			} else if (e.getActionCommand().equals(EXIT.toString())) {
				this.handleExitEvent();
			}
		} catch (Exception ex) {
			// TODO exception handling
			ex.printStackTrace();
		}

	}

	private void handleExitEvent() {
		// TODO exit dialog adapter
		// TODO bei laden und speichern sperren
		// TODO prüfen, ob graph geändert wurde -> speichern
		int value = JOptionPane.showConfirmDialog(null,
				"Programm wirklich beenden?", "Beenden",
				JOptionPane.YES_NO_OPTION);
		if (value == JOptionPane.YES_OPTION) {
			System.exit(0);
		}

		// int value = JOptionPane.showConfirmDialog(null, model
		// .getResourceBundle().getString("quit.message"), model
		// .getResourceBundle().getString("app.label"),
		// JOptionPane.YES_NO_OPTION);
		// if (value == JOptionPane.YES_OPTION) {
		// System.exit(0);

		// TODO exit dialog
		// GraphPropertyDialog graphPropertyDialog = new
		// GraphPropertyDialog(model.getGraph(), this);
	}

	private void handleGraphPropertiesEvent() {
		// TODO Auto-generated method stub

	}

	private void handleSaveGraphEvent() throws CoreException {
		if (this.fileChooserAdapter != null
				&& this.fileChooserAdapter.showSaveDialog() == JFileChooser.APPROVE_OPTION) {
			this.core.saveGraph(this.model.getGraph(),
					this.fileChooserAdapter.getSelectedFile());
		}
	}

	private void handleOpenGraphEvent() throws CoreException {
		// TODO exception handling, wenn file nicht vorhanden
		if (this.fileChooserAdapter != null
				&& this.fileChooserAdapter.showOpenDialog() == JFileChooser.APPROVE_OPTION) {
			IGravisGraph graph = this.core.importGraph(this.fileChooserAdapter
					.getSelectedFile());
			this.model.setOpenGraphState(graph);

			this.setChanged();
			this.notifyObservers(this.model.getGraph());

			// TODO disable Button "neu Berechnen"
			// TODO disable step panel
		}
	}

	/**
	 * 
	 * @param edgeType
	 */
	private void handleNewGraphEvent(EdgeType edgeType) {
		this.model.setNewGraphState(edgeType);

		this.setChanged();
		this.notifyObservers(this.model.getGraph());

		// TODO disable algo-dropdown
		// TODO disable button "Neu berechnen"
		// TODO disable step panel
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		this.handleExitEvent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowOpened(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosed(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowIconified(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeiconified(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowActivated(WindowEvent e) {
		// no action
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent
	 * )
	 */
	@Override
	public void windowDeactivated(WindowEvent e) {
		// no action
	}

}
