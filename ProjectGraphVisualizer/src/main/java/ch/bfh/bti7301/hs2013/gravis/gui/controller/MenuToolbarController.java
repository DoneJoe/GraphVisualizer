package ch.bfh.bti7301.hs2013.gravis.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import ch.bfh.bti7301.hs2013.gravis.core.CoreException;
import ch.bfh.bti7301.hs2013.gravis.core.ICore;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.FileChooserAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory;
import ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter;
import ch.bfh.bti7301.hs2013.gravis.gui.model.IGuiModel;
import edu.uci.ics.jung.graph.util.EdgeType;
import static ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController.EventSource.*;

/**
 * @author Patrick Kofmel (kofmp1@bfh.ch)
 * 
 */
class MenuToolbarController extends Observable implements
		IMenuToolbarController {

	private final static String FILE_ERR_TITLE = "Öffnen";
	private final static String FILE_ERR_MSG = "Datei nicht gefunden: %s";
	private final static String EXIT_TITLE = "Beenden";
	private final static String EXIT_MSG = "Programm wirklich beenden?";
	private final static String APP_ERR_TITLE = "Fehler";
	private final static String APP_ERR_MSG = "In der Applikation ist ein Fehler aufgetreten: %s";

	private final ICore core;

	private final IGuiModel model;

	private FileChooserAdapter fileChooserAdapter = null;

	private ConfirmDialogAdapter confirmDialogAdapter = null;

	private MessageDialogAdapter messageDialogAdapter = null;

	private GraphPropertyDialogFactory graphPropertyDialogFactory = null;

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
	 * setConfirmDialogAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.ConfirmDialogAdapter)
	 */
	@Override
	public void setConfirmDialogAdapter(
			ConfirmDialogAdapter confirmDialogAdapter) {
		this.confirmDialogAdapter = confirmDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setMessageDialogAdapter
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.MessageDialogAdapter)
	 */
	@Override
	public void setMessageDialogAdapter(
			MessageDialogAdapter messageDialogAdapter) {
		this.messageDialogAdapter = messageDialogAdapter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.bfh.bti7301.hs2013.gravis.gui.controller.IMenuToolbarController#
	 * setGraphPropertyDialogFactory
	 * (ch.bfh.bti7301.hs2013.gravis.gui.dialog.GraphPropertyDialogFactory)
	 */
	@Override
	public void setGraphPropertyDialogFactory(
			GraphPropertyDialogFactory graphPropertyDialogFactory) {
		this.graphPropertyDialogFactory = graphPropertyDialogFactory;
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
			} else if (e.getActionCommand().equals(GRAPH_PROPERTY.toString())) {
				this.handleGraphPropertyEvent();
			} else if (e.getActionCommand().equals(EXIT.toString())) {
				this.handleExitEvent();
			} else if (e.getActionCommand().equals(NEW_CALC.toString())) {
				this.handleNewCalcEvent();
			}
		} catch (Exception ex) {
			this.messageDialogAdapter.showMessageDialog(
					String.format(APP_ERR_MSG, ex.getMessage()), APP_ERR_TITLE,
					JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * 
	 */
	private void handleNewCalcEvent() {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @throws CoreException
	 */
	private void handleExitEvent() throws CoreException {
		if (this.model.hasGraphChanged()
				&& this.handleSaveGraphEvent() != JFileChooser.APPROVE_OPTION) {
			return;
		} else if (this.confirmDialogAdapter != null) {
			int value = this.confirmDialogAdapter.showConfirmDialog(EXIT_MSG,
					EXIT_TITLE, JOptionPane.YES_NO_OPTION);

			if (value == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}

	private void handleGraphPropertyEvent() {
		JDialog dialog = this.graphPropertyDialogFactory
				.createGraphPropertyDialog(this.model.getGraph());
		dialog.setVisible(true);
	}

	/**
	 * 
	 * @throws CoreException
	 * @return int
	 * 
	 */
	private int handleSaveGraphEvent() throws CoreException {
		int saveResult = Integer.MAX_VALUE;

		if (this.fileChooserAdapter != null) {
			saveResult = this.fileChooserAdapter.showSaveDialog();

			if (saveResult == JFileChooser.APPROVE_OPTION) {
				this.core.saveGraph(this.model.getGraph(),
						this.fileChooserAdapter.getSelectedFile());
			}
		}

		return saveResult;
	}

	/**
	 * 
	 * @throws CoreException
	 */
	private void handleOpenGraphEvent() throws CoreException {
		if (this.fileChooserAdapter != null
				&& this.messageDialogAdapter != null) {
			if (this.model.hasGraphChanged()
					&& this.handleSaveGraphEvent() != JFileChooser.APPROVE_OPTION) {
				return;
			}

			while (this.fileChooserAdapter.showOpenDialog() == JFileChooser.APPROVE_OPTION) {
				File file = this.fileChooserAdapter.getSelectedFile();

				if (file.exists()) {
					this.model.setOpenGraphState(this.core.importGraph(file));

					this.setChanged();
					this.notifyObservers(this.model.getGraph());
					this.setChanged();
					this.notifyObservers(this.model.getToolBarModel(
							this.core.getAlgorithmNames(this.model.getGraph().getEdgeType())));
					
					// TODO disable step panel
					break;
				} else {
					this.messageDialogAdapter.showMessageDialog(
							String.format(FILE_ERR_MSG, file.getName()),
							FILE_ERR_TITLE, JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}

	/**
	 * 
	 * @param edgeType
	 * @throws CoreException
	 */
	private void handleNewGraphEvent(EdgeType edgeType) throws CoreException {
		if (this.model.hasGraphChanged()
				&& this.handleSaveGraphEvent() != JFileChooser.APPROVE_OPTION) {
			return;
		}

		this.model.setNewGraphState(edgeType);

		this.setChanged();
		this.notifyObservers(this.model.getGraph());
		this.setChanged();
		this.notifyObservers(this.model.getToolBarModel(
				this.core.getAlgorithmNames(this.model.getGraph().getEdgeType())));

		// TODO save graph testen
		// TODO disable step panel
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		try {
			if (e.getStateChange() == ItemEvent.SELECTED
					&& e.getSource() instanceof JComboBox<?>
					&& e.getItem() instanceof String) {
				JComboBox<?> combo = (JComboBox<?>) e.getSource();

				if (combo.getActionCommand().equals(MODE.toString())) {
					this.handleModeEvent((String) e.getItem());
				} else if (combo.getActionCommand()
						.equals(ALGORITHM.toString())) {
					this.handleAlgorithmEvent((String) e.getItem());
				}
			}
		} catch (Exception ex) {
			this.messageDialogAdapter.showMessageDialog(
					String.format(APP_ERR_MSG, ex.getMessage()), APP_ERR_TITLE,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * @param item 
	 * @throws CoreException 
	 * 
	 */
	private void handleAlgorithmEvent(String item) throws CoreException {
		if (!item.equals(IGuiModel.DEFAULT_ALGO_ENTRY)) {
			if (this.model.getStepIterator() != null) {
				this.model.getStepIterator().first();
				this.setChanged();
				this.notifyObservers(this.model.getGraph());
			}
			
			this.model.setStepIterator(this.core.calculateSteps(this.model.getGraph(), item));
			
			// TODO enable step panel
			// TODO update protocol panel
		}
	}

	/**
	 * @param item 
	 * 
	 */
	private void handleModeEvent(String item) {
		// TODO Auto-generated method stub
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		try {
			this.handleExitEvent();
		} catch (CoreException ex) {
			this.messageDialogAdapter.showMessageDialog(
					String.format(APP_ERR_MSG, ex.getMessage()), APP_ERR_TITLE,
					JOptionPane.ERROR_MESSAGE);
		}
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
